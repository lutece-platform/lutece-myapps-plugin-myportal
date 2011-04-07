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

import fr.paris.lutece.plugins.myportal.business.Style;
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetStatusEnum;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * This is an implentation of a page builder. Other implementation can be
 * injected using Spring IoC
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
    private static final String RETURN_FALSE = "return false;";
    private static final String CLASS_MYPORTAL_PORTLET_HEADER = "myportal-portlet-header";
    private static final String CLASS_MYPORTAL_PORTLET_CONTENT = "myportal-portlet-content";
    private static final String CLASS_MYPORTAL_ICON = "myportal-icon";
    private static final String CLASS_UI_ICON_PLUS = "ui-icon ui-icon-plus";
    private static final String CLASS_UI_ICON_CIRCLE_PLUS = "ui-icon ui-icon-circle-plus";
    private static final String CLASS_UI_ICON_PENCIL = "ui-icon ui-icon-pencil";
    private static final String CLASS_UI_ICON_ARROW_4 = "ui-icon ui-icon-arrow-4";
    private static final String CLASS_ICON_CLOSE = "ui-icon ui-icon-circle-close icon-close";
    private static final String CLASS_CEEBOX = "ceebox";
    private static final String CLASS_MYPORTAL_TAB = "myportal-tab";

    // PARAMETERS
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_TAB_INDEX = "tab_index";
    private static final String PARAMETER_COLUMN_STYLE = "column_style_";
    private static final String PARAMETER_ID_WIDGET = "id_widget";

    // ACTIONS
    private static final String ACTION_BROWSE_CATEGORIES = "browse_categories";
    private static final String ACTION_EDIT_TAB = "edit_tab";
    private static final String ACTION_EDIT_WIDGET = "edit_widget";
    private static final String ACTION_ADD_TAB = "add_tab";

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
    private static final String ATTRIBUTE_ONCLICK = "onclick";

    // JSP
    private static final String JSP_RUNSTANDALONEAPP = "jsp/site/RunStandaloneApp.jsp";
    private static final String JSP_DO_REMOVE_WIDGET = "jsp/site/plugins/myportal/DoRemoveWidget.jsp";
    private WidgetContentService _widgetContentService;
    private WidgetService _widgetService;

    /**
     * Build the page given a page config and a LuteceUser
     *
     * @param pageConfig a {@link PageConfig}
     * @param user a {@link LuteceUser}
     * @param request {@link HttpServletRequest}
     * @return the page
     */
    public String buildPage( PageConfig pageConfig, LuteceUser user, HttpServletRequest request )
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
            sbJsContent.append( buildTabLinks( strUrl, nTab, tab, JSP_RUNSTANDALONEAPP )
                                    .replaceAll( NEWLINE_CHAR, StringUtils.EMPTY ) );
            sbJsContent.append( END_JS_DOCUMENT_WRITE );
            XmlUtil.addElement( sb, TAG_JS, sbJsContent.toString(  ),
                buildAttributes( ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_JS ) );

            /* Tab when JS is off */
            strUrl = AppPathService.getPortalUrl(  ) + QUESTION_MARK + PARAMETER_PAGE + EQUAL +
                MyPortalPlugin.PLUGIN_NAME + strUrl;
            XmlUtil.addElement( sb, TAG_NOJS, buildTabLinks( strUrl, nTab, tab, AppPathService.getPortalUrl(  ) ) );

            nTab++;
        }

        buildAddTabLink( sb );

        XmlUtil.endElement( sb, TAG_UL );

        nTab = 1;

        for ( TabConfig tab : listTabs )
        {
            buildTabContent( tab, sb, nTab, user, request );
            nTab++;
        }

        return sb.toString(  );
    }

    /**
     * Build the html code for tab links
     *
     * @param strUrl the url of the tab link
     * @param nTabIndex the tab index
     * @param tab the tab
     * @param strBaseUrl the base url
     * @return the html code
     */
    private String buildTabLinks( String strUrl, int nTabIndex, TabConfig tab, String strBaseUrl )
    {
        StringBuffer sbContent = new StringBuffer(  );
        XmlUtil.beginElement( sbContent, TAG_LI, buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_TAB ) );
        XmlUtil.addElement( sbContent, TAG_A, tab.getName(  ), buildAttributes( ATTRIBUTE_HREF, strUrl ) );

        // Url for adding a new widget
        UrlItem urlAddWidget = new UrlItem( strBaseUrl );
        urlAddWidget.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        urlAddWidget.addParameter( PARAMETER_ACTION, ACTION_BROWSE_CATEGORIES );
        urlAddWidget.addParameter( PARAMETER_TAB_INDEX, nTabIndex );

        // Attributes of the link to add a new widget
        Map<String, String> listAttributes = buildAttributes( ATTRIBUTE_HREF, urlAddWidget.getUrlWithEntity(  ) );
        listAttributes.put( ATTRIBUTE_CLASS, CLASS_MYPORTAL_ICON + SPACE + CLASS_CEEBOX );

        XmlUtil.beginElement( sbContent, TAG_A, listAttributes );
        XmlUtil.addElement( sbContent, TAG_SPAN, HTML_SPACE, buildAttributes( ATTRIBUTE_CLASS, CLASS_UI_ICON_PLUS ) );
        XmlUtil.endElement( sbContent, TAG_A );

        // Url for editing a tab
        UrlItem urlEditTab = new UrlItem( strBaseUrl );
        urlEditTab.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        urlEditTab.addParameter( PARAMETER_ACTION, ACTION_EDIT_TAB );
        urlEditTab.addParameter( PARAMETER_TAB_INDEX, nTabIndex );

        // Attributes of the link to edit a tab
        Map<String, String> listAttributesEditTab = buildAttributes( ATTRIBUTE_HREF, urlEditTab.getUrlWithEntity(  ) );
        listAttributesEditTab.put( ATTRIBUTE_CLASS, CLASS_MYPORTAL_ICON + SPACE + CLASS_CEEBOX );

        XmlUtil.beginElement( sbContent, TAG_A, listAttributesEditTab );
        XmlUtil.addElement( sbContent, TAG_SPAN, HTML_SPACE, buildAttributes( ATTRIBUTE_CLASS, CLASS_UI_ICON_PENCIL ) );
        XmlUtil.endElement( sbContent, TAG_A );

        XmlUtil.endElement( sbContent, TAG_LI );

        return sbContent.toString(  );
    }

    /**
     * Build the add tab link
     * @param sb the content of the html code
     */
    private void buildAddTabLink( StringBuffer sb )
    {
        // JS ON
        UrlItem urlForJs = new UrlItem( JSP_RUNSTANDALONEAPP );
        urlForJs.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        urlForJs.addParameter( PARAMETER_ACTION, ACTION_ADD_TAB );

        Map<String, String> listAttributesAddTab = buildAttributes( ATTRIBUTE_HREF, urlForJs.getUrlWithEntity(  ) );
        listAttributesAddTab.put( ATTRIBUTE_CLASS, CLASS_CEEBOX );

        // Build the url
        StringBuffer sbJs = new StringBuffer(  );
        XmlUtil.beginElement( sbJs, TAG_LI );
        XmlUtil.beginElement( sbJs, TAG_A, listAttributesAddTab );
        XmlUtil.addElement( sbJs, TAG_SPAN, HTML_SPACE, buildAttributes( ATTRIBUTE_CLASS, CLASS_UI_ICON_CIRCLE_PLUS ) );
        XmlUtil.endElement( sbJs, TAG_A );
        XmlUtil.endElement( sbJs, TAG_LI );

        StringBuilder sbJsContent = new StringBuilder(  );

        sbJsContent.append( BEGIN_JS_DOCUMENT_WRITE );
        sbJsContent.append( sbJs.toString(  ).replaceAll( NEWLINE_CHAR, StringUtils.EMPTY ) );
        sbJsContent.append( END_JS_DOCUMENT_WRITE );

        // JS OFF
        UrlItem url = new UrlItem( AppPathService.getPortalUrl(  ) );
        url.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        url.addParameter( PARAMETER_ACTION, ACTION_ADD_TAB );
        listAttributesAddTab = buildAttributes( ATTRIBUTE_HREF, url.getUrlWithEntity(  ) );
        listAttributesAddTab.put( ATTRIBUTE_CLASS, CLASS_CEEBOX );

        StringBuffer sbContent = new StringBuffer(  );
        XmlUtil.beginElement( sbContent, TAG_LI );
        XmlUtil.beginElement( sbContent, TAG_A, listAttributesAddTab );
        XmlUtil.addElement( sbContent, TAG_SPAN, HTML_SPACE,
            buildAttributes( ATTRIBUTE_CLASS, CLASS_UI_ICON_CIRCLE_PLUS ) );
        XmlUtil.endElement( sbContent, TAG_A );
        XmlUtil.endElement( sbContent, TAG_LI );

        // Main html content
        XmlUtil.addElement( sb, TAG_JS, sbJsContent.toString(  ), buildAttributes( ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_JS ) );
        XmlUtil.addElement( sb, TAG_NOJS, sbContent.toString(  ) );
    }

    /**
     * Build the html code for widget links
     *
     * @param nTabIndex the tab index
     * @param widget the widget
     * @return the html code
     */
    private String buildWidgetLinks( int nTabIndex, Widget widget )
    {
        StringBuffer sbContent = new StringBuffer(  );

        if ( widget.getStatus(  ) != WidgetStatusEnum.MANDATORY.getId(  ) )
        {
            UrlItem urlRemoveWidget = new UrlItem( JSP_DO_REMOVE_WIDGET );
            urlRemoveWidget.addParameter( PARAMETER_ID_WIDGET, widget.getIdWidget(  ) );

            Map<String, String> listAttributesRemoveWidget = buildAttributes( ATTRIBUTE_HREF, urlRemoveWidget.getUrl(  ) );
            listAttributesRemoveWidget.put( ATTRIBUTE_ONCLICK, RETURN_FALSE );

            UrlItem urlEditWidget = new UrlItem( AppPathService.getPortalUrl(  ) );
            urlEditWidget.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
            urlEditWidget.addParameter( PARAMETER_ACTION, ACTION_EDIT_WIDGET );
            urlEditWidget.addParameter( PARAMETER_TAB_INDEX, nTabIndex );
            urlEditWidget.addParameter( PARAMETER_ID_WIDGET, widget.getIdWidget(  ) );

            Map<String, String> listAttributesEditWidget = buildAttributes( ATTRIBUTE_HREF, urlEditWidget.getUrl(  ) );
            listAttributesEditWidget.put( ATTRIBUTE_ONCLICK, RETURN_FALSE );

            XmlUtil.beginElement( sbContent, TAG_A, listAttributesRemoveWidget );
            XmlUtil.addElement( sbContent, TAG_SPAN, StringUtils.EMPTY,
                buildAttributes( ATTRIBUTE_CLASS, CLASS_ICON_CLOSE ) );
            XmlUtil.endElement( sbContent, TAG_A );

            XmlUtil.beginElement( sbContent, TAG_A, listAttributesEditWidget );
            XmlUtil.addElement( sbContent, TAG_SPAN, StringUtils.EMPTY,
                buildAttributes( ATTRIBUTE_CLASS, CLASS_UI_ICON_ARROW_4 ) );
            XmlUtil.endElement( sbContent, TAG_A );
        }

        XmlUtil.addElement( sbContent, TAG_SPAN, widget.getName(  ) );

        return sbContent.toString(  );
    }

    /**
     * Build the tab content
     *
     * @param tab the tab
     * @param sb the content of the htlm code
     * @param nTab the index of the tab
     * @param user the {@link LuteceUser}
     * @param request {@link HttpServletRequest}
     */
    private void buildTabContent( TabConfig tab, StringBuffer sb, int nTab, LuteceUser user, HttpServletRequest request )
    {
        int nNbColumns = DefaultPageBuilderService.getInstance(  ).getColumnCount(  );
        List<StringBuffer> listCol = new ArrayList<StringBuffer>(  );

        for ( int nColumn = 1; nColumn <= nNbColumns; nColumn++ )
        {
            StringBuffer sbCol = new StringBuffer(  );
            Style style = getColumnStyle( nColumn );

            if ( style != null )
            {
                XmlUtil.beginElement( sbCol, TAG_DIV, buildAttributes( ATTRIBUTE_CLASS, style.getCssClass(  ) ) );
            }
            else
            {
                XmlUtil.beginElement( sbCol, TAG_DIV );
            }

            listCol.add( sbCol );
        }

        StringBuilder sbContent = new StringBuilder(  );

        for ( WidgetConfig widgetConfig : tab.getWidgetList(  ) )
        {
            Widget widget = _widgetService.getWidget( widgetConfig.getWidgetId(  ) );

            if ( ( widget != null ) && ( widgetConfig.getColumn(  ) <= nNbColumns ) )
            {
                StringBuffer sbWidget = listCol.get( widgetConfig.getColumn(  ) - 1 );

                Map<String, String> listAttributes = buildAttributes( ATTRIBUTE_CLASS, widget.getCssClass(  ) );
                listAttributes.put( ATTRIBUTE_ID, ID_WIDGET + widget.getIdWidget(  ) );
                XmlUtil.beginElement( sbWidget, TAG_DIV, listAttributes );
                XmlUtil.addElement( sbWidget, TAG_DIV, buildWidgetLinks( nTab, widget ),
                    buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_PORTLET_HEADER ) );
                XmlUtil.addElement( sbWidget, TAG_DIV,
                    _widgetContentService.getWidgetContent( widgetConfig.getWidgetId(  ), user, request ),
                    buildAttributes( ATTRIBUTE_CLASS, CLASS_MYPORTAL_PORTLET_CONTENT ) );
                XmlUtil.endElement( sbWidget, TAG_DIV );
            }
        }

        for ( int i = 0; i < nNbColumns; i++ )
        {
            XmlUtil.endElement( listCol.get( i ), TAG_DIV );
            sbContent.append( listCol.get( i ) );
        }

        XmlUtil.addElement( sb, TAG_DIV, sbContent.toString(  ), buildAttributes( ATTRIBUTE_ID, ID_TAB + nTab ) );
    }

    /**
     * Build the attributes for the tag
     *
     * @param strName  the attribute name
     * @param strValue the attribute value
     * @return a map
     */
    private Map<String, String> buildAttributes( String strName, String strValue )
    {
        Map<String, String> listAttributes = new HashMap<String, String>(  );
        listAttributes.put( strName, strValue );

        return listAttributes;
    }

    /**
     * Get the column style from the column number
     *
     * @param nColumn the column number
     * @return the {@link Style}
     */
    private Style getColumnStyle( int nColumn )
    {
        Style style = null;
        ReferenceItem columnStyle = DefaultPageBuilderService.getInstance(  )
                                                             .getPageBuilderParameterDefaultValue( PARAMETER_COLUMN_STYLE +
                nColumn );

        if ( ( columnStyle != null ) && StringUtils.isNotBlank( columnStyle.getName(  ) ) &&
                StringUtils.isNumeric( columnStyle.getName(  ) ) )
        {
            int nIdStyle = Integer.parseInt( columnStyle.getName(  ) );
            style = StyleService.getInstance(  ).getColumnStyle( nIdStyle );
        }

        return style;
    }

    /**
     * Set the widget content service
     * @param widgetContentService the widget content service
     */
    public void setWidgetContentService( WidgetContentService widgetContentService )
    {
        _widgetContentService = widgetContentService;
    }

    /**
     * Set the widget service
     * @param widgetService the widget content service
     */
    public void setWidgetService( WidgetService widgetService )
    {
        _widgetService = widgetService;
    }
}
