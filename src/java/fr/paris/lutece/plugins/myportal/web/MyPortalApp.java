/*
 * Copyright (c) 2002-2009, Mairie de Paris
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
package fr.paris.lutece.plugins.myportal.web;

import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.service.MyPortalPageService;
import fr.paris.lutece.plugins.myportal.util.auth.MyPortalUser;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides a simple implementation of an XPage
 */
public class MyPortalApp implements XPageApplication
{
    private static final String TEMPLATE_MYPORTAL_PAGE = "skin/plugins/myportal/myportal.html";
    private static final String TEMPLATE_ADD_CONTENT = "skin/plugins/myportal/add_content.html";
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_ID_WIDGET = "id_widget";
    private static final String PARAMETER_WIDGET = "widget";
    private static final String PARAMETER_TAB = "tab";
    private static final String PARAMETER_COLUMN = "column";
    private static final String MARK_WIDGETS = "widgets";
    private static final String MARK_WIDGETS_LIST = "widgets_list";
    private static final String MARKER_BASE_URL = "base_url";
    private static final String PROPERTY_PAGE_PATH = "myportal.pagePathLabel";
    private static final String PROPERTY_PAGE_TITLE = "myportal.pageTitle";
    private static final String PARAMETER_PORTAL_STATE = "portalState";

    // private fields
    private Plugin _plugin;
    private MyPortalPageService _pageService = new MyPortalPageService(  );

    /**
     * Returns the content of the page myportal.
     * @param request The http request
     * @param nMode The current mode
     * @param plugin The plugin object
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException Message displayed if an exception occurs
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException
    {
        XPage page = new XPage(  );

        String strPluginName = request.getParameter( PARAMETER_PAGE );
        _plugin = PluginService.getPlugin( strPluginName );

        page.setTitle( AppPropertiesService.getProperty( PROPERTY_PAGE_TITLE ) );
        page.setPathLabel( AppPropertiesService.getProperty( PROPERTY_PAGE_PATH ) );

        String strWidgets = _pageService.getUserPage( getUser( request ) );
        HashMap model = new HashMap(  );
        model.put( MARK_WIDGETS, strWidgets );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYPORTAL_PAGE, request.getLocale(  ), model );
        page.setContent( template.getHtml(  ) );

        return page;
    }

    /**
     * Get Add Content popup
     * @param request The HTTP request
     * @return The page
     */
    public String getAddWidget( HttpServletRequest request )
    {
        HashMap model = new HashMap(  );

        String strBaseUrl = ( request != null ) ? AppPathService.getBaseUrl( request ) : "";
        model.put( MARKER_BASE_URL, strBaseUrl );
        model.put( MARK_WIDGETS_LIST, WidgetHome.getWidgetsList( _plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_ADD_CONTENT, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Process add content - add a widget to the page
     * @param request The HTTP request
     * @return the forward url
     */
    public String doAddWidget( HttpServletRequest request )
    {
        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );
        String strColumn = request.getParameter( PARAMETER_COLUMN );

        int nIdWidget = Integer.parseInt( strIdWidget );
        int nColumn = Integer.parseInt( strColumn );
        int nTab = 1;

        _pageService.addWidget( getUser( request ), nIdWidget, nTab, nColumn );

        return "../../Portal.jsp?page=myportal"; // todo : use properties conf to permit url rewriting
    }

    /**
     * Process delete - remove a widget from the page
     * @param request The HTTP request
     * @return the forward url
     */
    public String doRemoveWidget( HttpServletRequest request )
    {
        String strWidget = request.getParameter( PARAMETER_WIDGET );

        int nIdWidget = Integer.parseInt( strWidget.substring( "widget-".length(  ) ) );

        _pageService.removeWidget( getUser( request ), nIdWidget );

        return "Widget removed"; // todo : use properties conf to permit url rewriting
    }

    /**
     * Process save portal state
     * @param request The HTTP request
     * @return the forward url
     */
    public String doSavePortalState( HttpServletRequest request )
    {
        String strJson = request.getParameter( PARAMETER_PORTAL_STATE );

        _pageService.setPageConfigUser( getUser( request ), strJson );

        return "portal state saved!"; // todo : use properties conf to permit url rewriting
    }

    /**
     * Gets the user from the request
     * @param request The HTTP user
     * @return The Lutece User
     */
    private LuteceUser getUser( HttpServletRequest request )
    {
        LuteceUser user;

        ////////////////////////////////////////////////////////////////////////
        // Temporary code
        String strUser = request.getParameter( "user" );

        if ( strUser != null )
        {
            user = new MyPortalUser( strUser );
        }
        else
        {
            user = new MyPortalUser( "Anonymous" );
        }

        return user;
    }
}
