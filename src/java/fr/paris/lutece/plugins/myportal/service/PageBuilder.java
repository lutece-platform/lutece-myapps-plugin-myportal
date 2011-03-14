/*
 * Copyright (c) 2002-2010, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.myportal.service;

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.xml.XmlUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * This is an implentation of a page builder. Other implementation can be injected using Spring IoC
 *
 */
public class PageBuilder implements IPageBuilder
{
    // CONSTANTS
    private static final String QUESTION_MARK = "?";
    private static final String EQUAL = "=";
    private static final String ANCHOR = "#";
    private static final String ID_TAB = "tabs-";
    private static final String ID_WIDGET = "widget-";
    private static final String CLASS_MYPORTAL_COLUMN = "myportal-column";
    private static final String CLASS_MYPORTAL_COLUMN_FIXED = "myportal-column-fixed";
    private static final String CLASS_MYPORTAL_PORTLET_HEADER = "myportal-portlet-header";
    private static final String CLASS_MYPORTAL_PORTLET_CONTENT = "myportal-portlet-content";

    // PARAMETERS
    private static final String PARAMETER_PAGE = "page";

    // HTML CONSTANTS
    private static final String BEGIN_LI = "<li>";
    private static final String BEGIN_A = "<a href=\"";
    private static final String BEGIN_JS_DOCUMENT_WRITE = "document.write('";
    private static final String END_LI = "</li>";
    private static final String END_A = "</a>";
    private static final String END_JS_DOCUMENT_WRITE = "');";
    private static final String END_TAG = "\">";

    // TAGS
    private static final String TAG_UL = "ul";
    private static final String TAG_LI = "li";
    private static final String TAG_A = "a";
    private static final String TAG_JS = "script";
    private static final String TAG_NOJS = "noscript";
    private static final String TAG_DIV = "div";

    // ATTRIBUTES
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_HREF = "href";
    private static final String ATTRIBUTE_ID = "id";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String ATTRIBUTE_VALUE_JS = "text/javascript";

    /**
     * Build the page given a page config and a LuteceUser
     * @param pageConfig a {@link PageConfig}
     * @param user a {@link LuteceUser}
     * @return the page
     */
    public String buildPage( PageConfig pageConfig, LuteceUser user )
    {
        StringBuffer sb = new StringBuffer(  );

        List<TabConfig> listTabs = pageConfig.getTabList(  );

        int nTab = 1;
        XmlUtil.beginElement( sb, TAG_UL );

        for ( TabConfig tab : listTabs )
        {
            // Tab when JS is on
            String strUrl = ANCHOR + ID_TAB + nTab;
            StringBuilder sbContent = new StringBuilder(  );
            sbContent.append( BEGIN_JS_DOCUMENT_WRITE );
            sbContent.append( BEGIN_LI );
            sbContent.append( BEGIN_A );
            sbContent.append( strUrl );
            sbContent.append( END_TAG );
            sbContent.append( tab.getName(  ) );
            sbContent.append( END_A );
            sbContent.append( END_LI );
            sbContent.append( END_JS_DOCUMENT_WRITE );
            XmlUtil.addElement( sb, TAG_JS, sbContent.toString(  ),
                buildAttributes( ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_JS ) );

            // Tab when JS is off 
            strUrl = AppPathService.getPortalUrl(  ) + QUESTION_MARK + PARAMETER_PAGE + EQUAL +
                MyPortalPlugin.PLUGIN_NAME + strUrl;
            XmlUtil.beginElement( sb, TAG_NOJS );
            XmlUtil.beginElement( sb, TAG_LI );
            XmlUtil.addElement( sb, TAG_A, tab.getName(  ), buildAttributes( ATTRIBUTE_HREF, strUrl ) );
            XmlUtil.endElement( sb, TAG_LI );
            XmlUtil.endElement( sb, TAG_NOJS );

            nTab++;
        }

        XmlUtil.endElement( sb, TAG_UL );

        nTab = 1;

        for ( TabConfig tab : listTabs )
        {
            StringBuffer sbCol1 = new StringBuffer(  );
            StringBuffer sbCol2 = new StringBuffer(  );
            StringBuffer sbCol3 = new StringBuffer(  );
            XmlUtil.beginElement( sbCol1, TAG_DIV, buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_COLUMN ) );
            XmlUtil.beginElement( sbCol2, TAG_DIV, buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_COLUMN ) );
            XmlUtil.beginElement( sbCol3, TAG_DIV, buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_COLUMN_FIXED ) );

            StringBuilder sbContent = new StringBuilder(  );

            for ( WidgetConfig widgetConfig : tab.getWidgetList(  ) )
            {
                StringBuffer sbWidget = sbCol1;
                Widget widget = WidgetService.instance(  ).getWidget( widgetConfig.getWidgetId(  ) );

                if ( widget != null )
                {
                    switch ( widgetConfig.getColumn(  ) )
                    {
                        case 2:
                            sbWidget = sbCol2;

                            break;

                        case 3:
                            sbWidget = sbCol3;

                            break;

                        default:
                            break;
                    }

                    Map<String, String> listAttributes = buildAttributes( ATTRIBUTE_CLASS, widget.getCssClass(  ) );
                    listAttributes.put( ATTRIBUTE_ID, ID_WIDGET + widget.getIdWidget(  ) );
                    XmlUtil.beginElement( sbWidget, TAG_DIV, listAttributes );
                    XmlUtil.addElement( sbWidget, TAG_DIV, widget.getName(  ),
                        buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_PORTLET_HEADER ) );
                    XmlUtil.addElement( sbWidget, TAG_DIV,
                        WidgetContentService.instance(  ).getWidgetContent( widgetConfig.getWidgetId(  ), user ),
                        buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_PORTLET_CONTENT ) );
                    XmlUtil.endElement( sbWidget, TAG_DIV );
                }
            }

            XmlUtil.endElement( sbCol1, TAG_DIV );
            XmlUtil.endElement( sbCol2, TAG_DIV );
            XmlUtil.endElement( sbCol3, TAG_DIV );

            sbContent.append( sbCol1 );
            sbContent.append( sbCol2 );
            sbContent.append( sbCol3 );

            XmlUtil.addElement( sb, TAG_DIV, sbContent.toString(  ), buildAttributes( ATTRIBUTE_ID, ID_TAB + nTab ) );

            nTab++;
        }

        return sb.toString(  );
    }

    /**
     * Build the attributes for the tag
     * @param strName the attribute name
     * @param strValue the attribute value
     * @return a map
     */
    private Map<String, String> buildAttributes( String strName, String strValue )
    {
        Map<String, String> listAttributes = new HashMap<String, String>(  );
        listAttributes.put( strName, strValue );

        return listAttributes;
    }
}
