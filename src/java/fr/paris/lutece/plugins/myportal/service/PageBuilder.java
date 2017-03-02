/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
import fr.paris.lutece.plugins.myportal.business.WidgetsTag;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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


    // PARAMETERS
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_TAB_INDEX = "tab_index";
    private static final String PARAMETER_COLUMN_STYLE = "column_style_";

    // ACTIONS
    private static final String ACTION_BROWSE_CATEGORIES = "browse_categories";
    private static final String ACTION_EDIT_TAB = "edit_tab";
    private static final String NEWLINE_CHAR = "(\\r|\\n)";

    // JSP
    private static final String JSP_RUNSTANDALONEAPP = "jsp/site/RunStandaloneApp.jsp";
    private WidgetContentService _widgetContentService;
    private WidgetService _widgetService;
    
    //MARKER
    private static final String MARK_URL_ADDWIDGET="urlAddWidget";
    private static final String MARK_URL_EDITTAB="urlEditTab";

    private static final String MARK_TAB_NAME="tabName";    
    private static final String MARK_ID_WIDGET="idWidget";
    private static final String MARK_CSS_WIDGET="cssWidget";
    private static final String MARK_TAB_INDEX="tabIndex";
    private static final String MARK_WIDGET_NAME="WidgetName";
    private static final String MARK_WIDGET_CONTENT= "contentWidget";
    private static final String MARK_LIST_WIDGET_TAG= "listWidgetTag";

    
    //Template
    
    private static final String TEMPLATE_TAB_LINKS ="/skin/plugins/myportal/widget/tab_links.html";
    private static final String TEMPLATE_WIDGET ="/skin/plugins/myportal/widget/widget.html";
    private static final String TEMPLATE_WIGET_PAGE ="/skin/plugins/myportal/widget/widgets_page.html";

    
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

        for ( TabConfig tab : listTabs )
        {
           
            StringBuilder sbContent = new StringBuilder(  );
            sbContent.append( buildTabLinks( nTab, tab, JSP_RUNSTANDALONEAPP, request.getLocale( ) ).replaceAll( NEWLINE_CHAR, StringUtils.EMPTY ) );
            sb.append(sbContent);
            nTab++;
        }

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
    private String buildTabLinks(  int nTabIndex, TabConfig tab, String strBaseUrl, Locale locale )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
                
        
        model.put( MARK_TAB_NAME, tab.getName(  ) );
        
        // Url for adding a new widget
        UrlItem urlAddWidget = new UrlItem( strBaseUrl );
        urlAddWidget.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        urlAddWidget.addParameter( PARAMETER_ACTION, ACTION_BROWSE_CATEGORIES );
        urlAddWidget.addParameter( PARAMETER_TAB_INDEX, nTabIndex );
        
        model.put( MARK_URL_ADDWIDGET, urlAddWidget.getUrlWithEntity(  ) );

        // Url for editing a tab
        UrlItem urlEditTab = new UrlItem( strBaseUrl );
        urlEditTab.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        urlEditTab.addParameter( PARAMETER_ACTION, ACTION_EDIT_TAB );
        urlEditTab.addParameter( PARAMETER_TAB_INDEX, nTabIndex );
        model.put( MARK_URL_EDITTAB, urlEditTab.getUrlWithEntity(  ) );
        model.put(MARK_TAB_INDEX, nTabIndex);
        
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TAB_LINKS, locale, model );
        
        return template.getHtml();
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
        Map<String, Object> model = new HashMap<String, Object>( );

        List<StringBuffer> listCol = new ArrayList<StringBuffer>(  );
        List<WidgetsTag> listTag = new ArrayList<WidgetsTag>(  );
       
        
        for ( int nColumn = 1; nColumn <= nNbColumns; nColumn++ )
        {
            Style style = getColumnStyle( nColumn );
            WidgetsTag wdTag= new WidgetsTag();
            if ( style != null )
            {
            	wdTag.setCssClass(style.getCssClass( ));
            	
            }
            else
            {
            	wdTag.setCssClass(StringUtils.EMPTY);
            }
            listTag.add(wdTag);
            listCol.add(new StringBuffer());

        }       

        for ( WidgetConfig widgetConfig : tab.getWidgetList(  ) )
        {
        	
            Widget widget = _widgetService.getWidget( widgetConfig.getWidgetId(  ) );
            StringBuffer sbWidget = listCol.get( widgetConfig.getColumn(  ) - 1 );
            
            model.put( MARK_ID_WIDGET, widgetConfig.getWidgetId(  ) );

            if ( ( widget != null ) && ( widgetConfig.getColumn(  ) <= nNbColumns ) )
            {

                model.put( MARK_CSS_WIDGET, widget.getCssClass(  ) );               
                model.put( MARK_TAB_INDEX, nTab );
                model.put( MARK_WIDGET_NAME, widget.getName( ) );
                model.put( MARK_WIDGET_CONTENT, _widgetContentService.getWidgetContent( widgetConfig.getWidgetId(  ), user, request ) );
                
              
            }
            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_WIDGET, request.getLocale( ), model );
            sbWidget.append(template.getHtml( ));
            
        }

        for ( int i = 0; i < nNbColumns; i++ )
        {
        	WidgetsTag wdTag = listTag.get( i );
        	wdTag.setContent(listCol.get(i).toString());
        }
        

        model.put(MARK_LIST_WIDGET_TAG, listTag);
        model.put( MARK_TAB_INDEX, nTab );
        
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_WIGET_PAGE, request.getLocale( ), model );
        
        sb.append( new StringBuffer(template.getHtml()) );
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
