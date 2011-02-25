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

import fr.paris.lutece.plugins.myportal.business.CategoryHome;
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandlerService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage  Category,
Widget,
Layout
features ( manage, create, modify, remove )
 */
public class WidgetJspBean extends PluginAdminPageJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    // Right
    public static final String RIGHT_MANAGE_MYPORTAL_WIDGET = "MYPORTAL_WIDGET_MANAGEMENT";

    // Parameters
    private static final String PARAMETER_WIDGET_ID_WIDGET = "widget_id_widget";
    private static final String PARAMETER_WIDGET_NAME = "widget_name";
    private static final String PARAMETER_WIDGET_DESCRIPTION = "widget_description";
    private static final String PARAMETER_WIDGET_ID_CATEGORY = "widget_id_category";
    private static final String PARAMETER_WIDGET_WIDGET_TYPE = "widget_widget_type";
    private static final String PARAMETER_WIDGET_ICON_URL = "widget_icon_url";
    private static final String PARAMETER_WIDGET_IS_MOVABLE = "widget_is_movable";
    private static final String PARAMETER_WIDGET_IS_REMOVABLE = "widget_is_removable";
    private static final String PARAMETER_WIDGET_IS_RESIZABLE = "widget_is_resizable";
    private static final String PARAMETER_WIDGET_CONFIG_DATA = "widget_config_data";
    private static final String PARAMETER_PAGE_INDEX = "page_index";

    // templates
    private static final String TEMPLATE_MANAGE_WIDGETS = "/admin/plugins/myportal/manage_widgets.html";
    private static final String TEMPLATE_CREATE_WIDGET = "/admin/plugins/myportal/create_widget.html";
    private static final String TEMPLATE_MODIFY_WIDGET = "/admin/plugins/myportal/modify_widget.html";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_WIDGETS = "myportal.manage_widgets.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_WIDGET = "myportal.modify_widget.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_WIDGET = "myportal.create_widget.pageTitle";

    // Markers
    private static final String MARK_WIDGET_LIST = "widget_list";
    private static final String MARK_WIDGET = "widget";
    private static final String MARK_WIDGET_TYPES_LIST = "widget_types_list";
    private static final String MARK_CATEGORIES_LIST = "categories_list";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";

    // Jsp Definition
    private static final String JSP_DO_REMOVE_WIDGET = "jsp/admin/plugins/myportal/DoRemoveWidget.jsp";
    private static final String JSP_MANAGE_WIDGETS = "jsp/admin/plugins/myportal/ManageWidgets.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_WIDGETS = "ManageWidgets.jsp";

    // Properties
    private static final String PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE = "myportal.listWidgets.itemsPerPage";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_WIDGET = "myportal.message.confirmRemoveWidget";

    //Variables
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Returns the list of widget
     *
     * @param request The Http request
     * @return the widgets list
     */
    public String getManageWidgets( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_WIDGETS );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        UrlItem url = new UrlItem( JSP_MANAGE_WIDGETS );
        String strUrl = url.getUrl(  );
        Collection<Widget> listWIDGETs = WidgetHome.getWidgetsList( getPlugin(  ) );
        Paginator paginator = new Paginator( (List<Widget>) listWIDGETs, _nItemsPerPage, strUrl, PARAMETER_PAGE_INDEX,
                _strCurrentPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_WIDGET_LIST, paginator.getPageItems(  ) );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_WIDGETS, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Returns the form to create a widget
     *
     * @param request The Http request
     * @return the html code of the widget form
     */
    public String getCreateWidget( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_WIDGET );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_CATEGORIES_LIST, CategoryHome.getCategories(  ) );
        model.put( MARK_WIDGET_TYPES_LIST, WidgetHandlerService.instance(  ).getHandlers(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_WIDGET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new widget
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateWidget( HttpServletRequest request )
    {
        Widget widget = new Widget(  );

        if ( request.getParameter( PARAMETER_WIDGET_NAME ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setName( request.getParameter( PARAMETER_WIDGET_NAME ) );

        if ( request.getParameter( PARAMETER_WIDGET_DESCRIPTION ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setDescription( request.getParameter( PARAMETER_WIDGET_DESCRIPTION ) );

        if ( request.getParameter( PARAMETER_WIDGET_ID_CATEGORY ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdCategory = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_CATEGORY ) );
        widget.setIdCategory( nIdCategory );

        if ( request.getParameter( PARAMETER_WIDGET_WIDGET_TYPE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setWidgetType( request.getParameter( PARAMETER_WIDGET_WIDGET_TYPE ) );

        if ( request.getParameter( PARAMETER_WIDGET_ICON_URL ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setIconUrl( request.getParameter( PARAMETER_WIDGET_ICON_URL ) );

        if ( request.getParameter( PARAMETER_WIDGET_IS_MOVABLE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIsMovable = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_IS_MOVABLE ) );
        widget.setIsMovable( nIsMovable );

        if ( request.getParameter( PARAMETER_WIDGET_IS_REMOVABLE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIsRemovable = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_IS_REMOVABLE ) );
        widget.setIsRemovable( nIsRemovable );

        if ( request.getParameter( PARAMETER_WIDGET_IS_RESIZABLE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIsResizable = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_IS_RESIZABLE ) );
        widget.setIsResizable( nIsResizable );

        String strConfigData = request.getParameter( PARAMETER_WIDGET_CONFIG_DATA );
        widget.setConfigData( strConfigData );

        WidgetHome.create( widget, getPlugin(  ) );

        return JSP_REDIRECT_TO_MANAGE_WIDGETS;
    }

    /**
     * Manages the removal form of a widget whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveWidget( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_WIDGET ) );
        UrlItem url = new UrlItem( JSP_DO_REMOVE_WIDGET );
        url.addParameter( PARAMETER_WIDGET_ID_WIDGET, nId );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_WIDGET, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Handles the removal form of a widget
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage widgets
     */
    public String doRemoveWidget( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_WIDGET ) );
        WidgetHome.remove( nId, getPlugin(  ) );

        return JSP_REDIRECT_TO_MANAGE_WIDGETS;
    }

    /**
     * Returns the form to update info about a widget
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyWidget( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_WIDGET );

        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_WIDGET ) );
        Widget widget = WidgetHome.findByPrimaryKey( nId, getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_WIDGET, widget );
        model.put( MARK_CATEGORIES_LIST, CategoryHome.getCategories(  ) );
        model.put( MARK_WIDGET_TYPES_LIST, WidgetHandlerService.instance(  ).getHandlers(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_WIDGET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the change form of a widget
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyWidget( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_WIDGET ) );
        Widget widget = WidgetHome.findByPrimaryKey( nId, getPlugin(  ) );

        if ( request.getParameter( PARAMETER_WIDGET_ID_WIDGET ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdWidget = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_WIDGET ) );
        widget.setIdWidget( nIdWidget );

        if ( request.getParameter( PARAMETER_WIDGET_NAME ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setName( request.getParameter( PARAMETER_WIDGET_NAME ) );

        if ( request.getParameter( PARAMETER_WIDGET_DESCRIPTION ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setDescription( request.getParameter( PARAMETER_WIDGET_DESCRIPTION ) );

        if ( request.getParameter( PARAMETER_WIDGET_ID_CATEGORY ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdCategory = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_ID_CATEGORY ) );
        widget.setIdCategory( nIdCategory );

        if ( request.getParameter( PARAMETER_WIDGET_WIDGET_TYPE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setWidgetType( request.getParameter( PARAMETER_WIDGET_WIDGET_TYPE ) );

        if ( request.getParameter( PARAMETER_WIDGET_ICON_URL ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        widget.setIconUrl( request.getParameter( PARAMETER_WIDGET_ICON_URL ) );

        if ( request.getParameter( PARAMETER_WIDGET_IS_MOVABLE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIsMovable = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_IS_MOVABLE ) );
        widget.setIsMovable( nIsMovable );

        if ( request.getParameter( PARAMETER_WIDGET_IS_REMOVABLE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIsRemovable = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_IS_REMOVABLE ) );
        widget.setIsRemovable( nIsRemovable );

        if ( request.getParameter( PARAMETER_WIDGET_IS_RESIZABLE ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIsResizable = Integer.parseInt( request.getParameter( PARAMETER_WIDGET_IS_RESIZABLE ) );
        widget.setIsResizable( nIsResizable );
        WidgetHome.update( widget, getPlugin(  ) );

        return JSP_REDIRECT_TO_MANAGE_WIDGETS;
    }
}
