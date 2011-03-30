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
package fr.paris.lutece.plugins.myportal.web;

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetStatusEnum;
import fr.paris.lutece.plugins.myportal.service.CategoryService;
import fr.paris.lutece.plugins.myportal.service.StyleService;
import fr.paris.lutece.plugins.myportal.service.WidgetService;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandlerService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.upload.MultipartHttpServletRequest;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage  Category, Widget, features
 * ( manage, create, modify, remove )
 */
public class WidgetJspBean extends PluginAdminPageJspBean
{
    // Right
    public static final String RIGHT_MANAGE_MYPORTAL_WIDGET = "MYPORTAL_WIDGET_MANAGEMENT";

    // Parameters
    private static final String PARAMETER_ID_WIDGET = "id_widget";
    private static final String PARAMETER_WIDGET_NAME = "widget_name";
    private static final String PARAMETER_WIDGET_DESCRIPTION = "widget_description";
    private static final String PARAMETER_ID_CATEGORY = "id_category";
    private static final String PARAMETER_ID_STYLE = "id_style";
    private static final String PARAMETER_WIDGET_TYPE = "widget_type";
    private static final String PARAMETER_WIDGET_ICON = "widget_icon";
    private static final String PARAMETER_WIDGET_CONFIG_DATA = "widget_config_data";
    private static final String PARAMETER_PAGE_INDEX = "page_index";
    private static final String PARAMETER_WIDGET_STATUS = "widget_status";
    private static final String PARAMETER_UPDATE_FILE = "update_file";
    private static final String PARAMETER_IS_ESSENTIAL = "is_essential";
    private static final String PARAMETER_IS_NEW = "is_new";

    // templates
    private static final String TEMPLATE_MANAGE_WIDGETS = "/admin/plugins/myportal/manage_widgets.html";
    private static final String TEMPLATE_CREATE_WIDGET = "/admin/plugins/myportal/create_widget.html";
    private static final String TEMPLATE_MODIFY_WIDGET = "/admin/plugins/myportal/modify_widget.html";

    // Properties
    private static final String PROPERTY_PAGE_TITLE_MANAGE_WIDGETS = "myportal.manage_widgets.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_WIDGET = "myportal.modify_widget.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_WIDGET = "myportal.create_widget.pageTitle";
    private static final String PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE = "myportal.listWidgets.itemsPerPage";
    private static final String PROPERTY_ACCEPTED_ICON_FORMATS = "myportal.acceptedIconFormats";

    // Markers
    private static final String MARK_WIDGET_LIST = "widget_list";
    private static final String MARK_WIDGET = "widget";
    private static final String MARK_WIDGET_TYPES_LIST = "widget_types_list";
    private static final String MARK_CATEGORIES_LIST = "categories_list";
    private static final String MARK_STYLES_LIST = "widget_styles_list";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_STATUS_DRAFT = "status_draft";
    private static final String MARK_STATUS_MANDATORY = "status_mandatory";
    private static final String MARK_STATUS_PUBLIC = "status_public";

    // Jsp Definition
    private static final String JSP_URL_DO_REMOVE_WIDGET = "jsp/admin/plugins/myportal/DoRemoveWidget.jsp";
    private static final String JSP_URL_MANAGE_WIDGETS = "jsp/admin/plugins/myportal/ManageWidgets.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_WIDGETS = "ManageWidgets.jsp";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_WIDGET = "myportal.message.confirmRemoveWidget";
    private static final String MESSAGE_ERROR = "myportal.message.error";
    private static final String MESSAGE_ERROR_ICON_FORMAT_NOT_CORRECT = "myportal.message.errorIconFormatNotCorrect";

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

        UrlItem url = new UrlItem( JSP_URL_MANAGE_WIDGETS );
        String strUrl = url.getUrl(  );
        Collection<Widget> listWidgets = WidgetService.instance(  ).getWidgetsList(  );
        LocalizedPaginator paginator = new LocalizedPaginator( (List<Widget>) listWidgets, _nItemsPerPage, strUrl,
                PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_NB_ITEMS_PER_PAGE, Integer.toString( _nItemsPerPage ) );
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
        model.put( MARK_CATEGORIES_LIST, CategoryService.getInstance(  ).getCategories(  ) );
        model.put( MARK_WIDGET_TYPES_LIST, WidgetHandlerService.instance(  ).getHandlers(  ) );
        model.put( MARK_STYLES_LIST, StyleService.getInstance(  ).getWidgetStyles(  ) );
        model.put( MARK_STATUS_DRAFT, WidgetStatusEnum.DRAFT.getId(  ) );
        model.put( MARK_STATUS_MANDATORY, WidgetStatusEnum.MANDATORY.getId(  ) );
        model.put( MARK_STATUS_PUBLIC, WidgetStatusEnum.PUBLIC.getId(  ) );

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
        String strUrl = StringUtils.EMPTY;
        String strWidgetName = request.getParameter( PARAMETER_WIDGET_NAME );
        String strWidgetDescription = request.getParameter( PARAMETER_WIDGET_DESCRIPTION );
        String strWidgetType = request.getParameter( PARAMETER_WIDGET_TYPE );
        String strIdCategory = request.getParameter( PARAMETER_ID_CATEGORY );
        String strIdStyle = request.getParameter( PARAMETER_ID_STYLE );
        String strWidgetStatus = request.getParameter( PARAMETER_WIDGET_STATUS );

        if ( StringUtils.isNotBlank( strWidgetName ) && StringUtils.isNotBlank( strWidgetDescription ) &&
                StringUtils.isNotBlank( strIdCategory ) && StringUtils.isNumeric( strIdCategory ) &&
                StringUtils.isNotBlank( strIdStyle ) && StringUtils.isNumeric( strIdStyle ) &&
                StringUtils.isNotBlank( strWidgetStatus ) && StringUtils.isNumeric( strWidgetStatus ) &&
                StringUtils.isNotBlank( strWidgetType ) )
        {
            int nIdCategory = Integer.parseInt( strIdCategory );
            int nIdStyle = Integer.parseInt( strIdStyle );
            int nWidgetStatus = Integer.parseInt( strWidgetStatus );
            String strConfigData = request.getParameter( PARAMETER_WIDGET_CONFIG_DATA );
            boolean bIsEssential = StringUtils.isNotBlank( request.getParameter( PARAMETER_IS_ESSENTIAL ) );
            boolean bIsNew = StringUtils.isNotBlank( request.getParameter( PARAMETER_IS_NEW ) );

            Widget widget = new Widget(  );
            widget.setName( strWidgetName );
            widget.setDescription( strWidgetDescription );
            widget.setIdCategory( nIdCategory );
            widget.setIdStyle( nIdStyle );
            widget.setWidgetType( strWidgetType );
            widget.setConfigData( strConfigData );
            widget.setStatus( nWidgetStatus );
            widget.setIsEssential( bIsEssential );
            widget.setIsNew( bIsNew );

            try
            {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                FileItem itemIcon = multiRequest.getFile( PARAMETER_WIDGET_ICON );

                if ( itemIcon != null )
                {
                    String strMimeType = itemIcon.getContentType(  );

                    if ( WidgetService.instance(  ).isIconMimeTypeCorrect( strMimeType ) )
                    {
                        byte[] bytes = itemIcon.get(  );
                        widget.setIconContent( bytes );
                        widget.setIconMimeType( strMimeType );
                        WidgetService.instance(  ).createWidget( widget );

                        strUrl = JSP_REDIRECT_TO_MANAGE_WIDGETS;
                    }
                    else
                    {
                        Object[] listParam = { AppPropertiesService.getProperty( PROPERTY_ACCEPTED_ICON_FORMATS ) };
                        strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_ICON_FORMAT_NOT_CORRECT,
                                listParam, AdminMessage.TYPE_STOP );
                    }
                }
                else
                {
                    widget.setIconContent( null );
                    widget.setIconMimeType( StringUtils.EMPTY );
                    WidgetService.instance(  ).createWidget( widget );

                    strUrl = JSP_REDIRECT_TO_MANAGE_WIDGETS;
                }
            }
            catch ( Exception e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Manages the removal form of a widget whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveWidget( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strWidgetId = request.getParameter( PARAMETER_ID_WIDGET );

        if ( StringUtils.isNotBlank( strWidgetId ) && StringUtils.isNumeric( strWidgetId ) )
        {
            int nId = Integer.parseInt( strWidgetId );
            UrlItem url = new UrlItem( JSP_URL_DO_REMOVE_WIDGET );
            url.addParameter( PARAMETER_ID_WIDGET, nId );

            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_WIDGET, url.getUrl(  ),
                    AdminMessage.TYPE_CONFIRMATION );
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Handles the removal form of a widget
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage widgets
     */
    public String doRemoveWidget( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strWidgetId = request.getParameter( PARAMETER_ID_WIDGET );

        if ( StringUtils.isNotBlank( strWidgetId ) && StringUtils.isNumeric( strWidgetId ) )
        {
            int nWidgetId = Integer.parseInt( strWidgetId );
            WidgetService.instance(  ).removeWidget( nWidgetId );

            strUrl = JSP_REDIRECT_TO_MANAGE_WIDGETS;
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
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

        String strUrl = StringUtils.EMPTY;
        String strWidgetId = request.getParameter( PARAMETER_ID_WIDGET );

        if ( StringUtils.isNotBlank( strWidgetId ) && StringUtils.isNumeric( strWidgetId ) )
        {
            int nWidgetId = Integer.parseInt( strWidgetId );
            Widget widget = WidgetService.instance(  ).getWidget( nWidgetId );

            if ( widget != null )
            {
                Map<String, Object> model = new HashMap<String, Object>(  );
                model.put( MARK_WIDGET, widget );
                model.put( MARK_CATEGORIES_LIST, CategoryService.getInstance(  ).getCategories(  ) );
                model.put( MARK_WIDGET_TYPES_LIST, WidgetHandlerService.instance(  ).getHandlers(  ) );
                model.put( MARK_STYLES_LIST, StyleService.getInstance(  ).getWidgetStyles(  ) );
                model.put( MARK_STATUS_DRAFT, WidgetStatusEnum.DRAFT.getId(  ) );
                model.put( MARK_STATUS_MANDATORY, WidgetStatusEnum.MANDATORY.getId(  ) );
                model.put( MARK_STATUS_PUBLIC, WidgetStatusEnum.PUBLIC.getId(  ) );

                HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_WIDGET, getLocale(  ), model );

                strUrl = getAdminPage( template.getHtml(  ) );
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Process the change form of a widget
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyWidget( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strWidgetId = request.getParameter( PARAMETER_ID_WIDGET );
        String strWidgetName = request.getParameter( PARAMETER_WIDGET_NAME );
        String strWidgetDescription = request.getParameter( PARAMETER_WIDGET_DESCRIPTION );
        String strWidgetType = request.getParameter( PARAMETER_WIDGET_TYPE );
        String strIdCategory = request.getParameter( PARAMETER_ID_CATEGORY );
        String strIdStyle = request.getParameter( PARAMETER_ID_STYLE );
        String strWidgetStatus = request.getParameter( PARAMETER_WIDGET_STATUS );

        if ( StringUtils.isNotBlank( strWidgetName ) && StringUtils.isNotBlank( strWidgetDescription ) &&
                StringUtils.isNotBlank( strIdCategory ) && StringUtils.isNumeric( strIdCategory ) &&
                StringUtils.isNotBlank( strIdStyle ) && StringUtils.isNumeric( strIdStyle ) &&
                StringUtils.isNotBlank( strWidgetStatus ) && StringUtils.isNumeric( strWidgetStatus ) &&
                StringUtils.isNotBlank( strWidgetId ) && StringUtils.isNumeric( strWidgetId ) &&
                StringUtils.isNotBlank( strWidgetType ) )
        {
            int nWidgetId = Integer.parseInt( strWidgetId );
            Widget widget = WidgetService.instance(  ).getWidget( nWidgetId );

            if ( widget != null )
            {
                int nIdCategory = Integer.parseInt( strIdCategory );
                int nIdStyle = Integer.parseInt( strIdStyle );
                int nWidgetStatus = Integer.parseInt( strWidgetStatus );
                String strConfigData = request.getParameter( PARAMETER_WIDGET_CONFIG_DATA );
                boolean bUpdateIcon = StringUtils.isNotBlank( request.getParameter( PARAMETER_UPDATE_FILE ) );
                boolean bIsEssential = StringUtils.isNotBlank( request.getParameter( PARAMETER_IS_ESSENTIAL ) );
                boolean bIsNew = StringUtils.isNotBlank( request.getParameter( PARAMETER_IS_NEW ) );

                widget.setName( strWidgetName );
                widget.setDescription( strWidgetDescription );
                widget.setIdCategory( nIdCategory );
                widget.setIdStyle( nIdStyle );
                widget.setWidgetType( strWidgetType );
                widget.setConfigData( strConfigData );
                widget.setStatus( nWidgetStatus );
                widget.setIsEssential( bIsEssential );
                widget.setIsNew( bIsNew );

                if ( bUpdateIcon )
                {
                    strUrl = doModifyWidgetWithIcon( request, widget );
                }
                else
                {
                    WidgetService.instance(  ).updateWidget( widget, bUpdateIcon );
                    strUrl = JSP_REDIRECT_TO_MANAGE_WIDGETS;
                }
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Do modify the widget and its icon
     * @param request {@link HttpServletRequest}
     * @param widget the {@link Widget}
     * @return the url return
     */
    private String doModifyWidgetWithIcon( HttpServletRequest request, Widget widget )
    {
        String strUrl = StringUtils.EMPTY;

        try
        {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            FileItem itemIcon = multiRequest.getFile( PARAMETER_WIDGET_ICON );

            if ( itemIcon != null )
            {
                String strMimeType = itemIcon.getContentType(  );

                if ( WidgetService.instance(  ).isIconMimeTypeCorrect( strMimeType ) )
                {
                    byte[] bytes = itemIcon.get(  );
                    widget.setIconContent( bytes );
                    widget.setIconMimeType( strMimeType );
                    WidgetService.instance(  ).updateWidget( widget, true );
                    strUrl = JSP_REDIRECT_TO_MANAGE_WIDGETS;
                }
                else
                {
                    Object[] listParam = { AppPropertiesService.getProperty( PROPERTY_ACCEPTED_ICON_FORMATS ) };
                    strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_ICON_FORMAT_NOT_CORRECT,
                            listParam, AdminMessage.TYPE_STOP );
                }
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }

        return strUrl;
    }
}
