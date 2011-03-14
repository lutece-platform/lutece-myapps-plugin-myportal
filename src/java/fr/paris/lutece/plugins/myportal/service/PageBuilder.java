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
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

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
    private static final String HTML_SPACE = "&nbsp;";
    private static final String SPACE = " ";
    private static final String NEWLINE_CHAR = "(\\r|\\n)";
    private static final String ID_TAB = "tabs-";
    private static final String ID_WIDGET = "widget-";
    private static final String CLASS_MYPORTAL_COLUMN = "myportal-column";
    private static final String CLASS_MYPORTAL_COLUMN_FIXED = "myportal-column-fixed";
    private static final String CLASS_MYPORTAL_PORTLET_HEADER = "myportal-portlet-header";
    private static final String CLASS_MYPORTAL_PORTLET_CONTENT = "myportal-portlet-content";
    private static final String CLASS_MYPORTAL_ICON = "myportal-icon";
    private static final String CLASS_UI_ICON_PLUS = "ui-icon ui-icon-plus";
    private static final String CLASS_UI_ICON_PENCIL = "ui-icon ui-icon-pencil";
    private static final String CLASS_CEEBOX = "ceebox";

    // PARAMETERS
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_TAB_INDEX = "tab_index";

    // ACTIONS
    private static final String ACTION_BROWSE_CATEGORIES = "browse_categories";

    // HTML CONSTANTS
    private static final String BEGIN_JS_DOCUMENT_WRITE = "document.write('";
    private static final String END_JS_DOCUMENT_WRITE = "');";

    // TAGS
    private static final String TAG_UL = "ul";
    private static final String TAG_LI = "li";
    private static final String TAG_A = "a";
    private static final String TAG_JS = "script";
    private static final String TAG_NOJS = "noscript";
    private static final String TAG_DIV = "div";
    private static final String TAG_SPAN = "span";

    // ATTRIBUTES
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_HREF = "href";
    private static final String ATTRIBUTE_ID = "id";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String ATTRIBUTE_VALUE_JS = "text/javascript";

    // JSP
    private static final String JSP_MYPORTAL_NAVIGATION = "jsp/site/plugins/myportal/MyPortalNavigation.jsp";

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
            /* Tab when JS is on */
            StringBuilder sbJsContent = new StringBuilder(  );

            // Url of the tab link
            String strUrl = ANCHOR + ID_TAB + nTab;
            sbJsContent.append( BEGIN_JS_DOCUMENT_WRITE );
            sbJsContent.append( buildTabLinks( strUrl, nTab, tab ).replaceAll( NEWLINE_CHAR, StringUtils.EMPTY ) );
            sbJsContent.append( END_JS_DOCUMENT_WRITE );
            XmlUtil.addElement( sb, TAG_JS, sbJsContent.toString(  ),
                buildAttributes( ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_JS ) );

            /* Tab when JS is off */
            strUrl = AppPathService.getPortalUrl(  ) + QUESTION_MARK + PARAMETER_PAGE + EQUAL +
                MyPortalPlugin.PLUGIN_NAME + strUrl;
            XmlUtil.addElement( sb, TAG_NOJS, buildTabLinks( strUrl, nTab, tab ) );

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
     * Build the html code for tab links
     * @param strUrl the url of the tab link
     * @param nTabIndex the tab index
     * @param tab the tab
     * @return the html code
     */
    private String buildTabLinks( String strUrl, int nTabIndex, TabConfig tab )
    {
        // Url for adding a new widget
        UrlItem urlAddWidget = new UrlItem( JSP_MYPORTAL_NAVIGATION );
        urlAddWidget.addParameter( PARAMETER_ACTION, ACTION_BROWSE_CATEGORIES );
        urlAddWidget.addParameter( PARAMETER_TAB_INDEX, nTabIndex );

        // Attributes of the link to add a new widget
        Map<String, String> listAttributes = buildAttributes( ATTRIBUTE_HREF, urlAddWidget.getUrlWithEntity(  ) );
        listAttributes.put( ATTRIBUTE_CLASS, CLASS_MYPORTAL_ICON + SPACE + CLASS_CEEBOX );

        StringBuffer sbContent = new StringBuffer(  );
        XmlUtil.beginElement( sbContent, TAG_LI );
        XmlUtil.addElement( sbContent, TAG_A, tab.getName(  ), buildAttributes( ATTRIBUTE_HREF, strUrl ) );
        XmlUtil.beginElement( sbContent, TAG_A, listAttributes );
        XmlUtil.addElement( sbContent, TAG_SPAN, HTML_SPACE, buildAttributes( ATTRIBUTE_CLASS, CLASS_UI_ICON_PLUS ) );
        XmlUtil.endElement( sbContent, TAG_A );
        XmlUtil.beginElement( sbContent, TAG_A, listAttributes );
        XmlUtil.addElement( sbContent, TAG_SPAN, HTML_SPACE, buildAttributes( ATTRIBUTE_CLASS, CLASS_UI_ICON_PENCIL ) );
        XmlUtil.endElement( sbContent, TAG_A );
        XmlUtil.endElement( sbContent, TAG_LI );

        return sbContent.toString(  );
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
