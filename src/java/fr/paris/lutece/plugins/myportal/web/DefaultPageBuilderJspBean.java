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

import fr.paris.lutece.plugins.myportal.business.DefaultPageBuilderHome;
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetComponent;
import fr.paris.lutece.plugins.myportal.service.DefaultPageBuilderService;
import fr.paris.lutece.plugins.myportal.service.MyPortalResourceIdService;
import fr.paris.lutece.plugins.myportal.service.WidgetService;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * DefaultPageBuilderJspBean
 *
 */
public class DefaultPageBuilderJspBean extends PluginAdminPageJspBean
{
    public static final String RIGHT_DEFAULT_PAGE_BUILDER = "MYPORTAL_DEFAULT_PAGE_BUILDER";

    // CONSTANTS
    private static final String ZERO = "0";

    // PARAMETERS
    private static final String PARAMETER_ID_WIDGET_COMPONENT = "id_widget_component";
    private static final String PARAMETER_ID_WIDGET = "id_widget";
    private static final String PARAMETER_WIDGET_COLUMN = "widget_column";
    private static final String PARAMETER_WIDGET_ORDER = "widget_order";
    private static final String PARAMETER_COLUMN = "column";
    private static final String PARAMETER_PAGE_INDEX = "page_index";
    private static final String PARAMETER_COLUMN_FIXED = "column_fixed";

    // PROPERTIES
    private static final String PROPERTY_PAGE_TITLE_BUILD_DEFAULT_PAGE = "myportal.build_default_page.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_WIDGETS_LIST = "myportal.build_default_page_widgets_list.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ADVANCED_PARAMETERS = "myportal.manage_advanced_parameters.pageTitle";
    private static final String PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE = "myportal.listWidgets.itemsPerPage";

    // MARKS
    private static final String MARK_MAP_WIDGET_COMPONENTS = "map_widget_components";
    private static final String MARK_COLUMN_COUNT = "column_count";
    private static final String MARK_LIST_AVAILABLE_COLUMNS = "list_available_columns";
    private static final String MARK_MAP_AVAILABLE_ORDERS = "map_available_orders";
    private static final String MARK_MAP_COLUMN_ORDER_STATUS = "map_column_order_status";
    private static final String MARK_WIDGETS_LIST = "widgets_list";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_PERMISSION_MANAGE_ADVANCED_PARAMETERS = "permission_manage_advanced_parameters";
    private static final String MARK_COLUMN_FIXED = "column_fixed";

    // MESSAGES
    private static final String MESSAGE_ERROR = "myportal.message.error";

    // TEMPLATES
    private static final String TEMPLATE_BUILD_DEFAULT_PAGE = "/admin/plugins/myportal/build_default_page.html";
    private static final String TEMPLATE_WIDGETS_LIST = "/admin/plugins/myportal/build_default_page_widgets_list.html";
    private static final String TEMPLATE_MANAGE_ADVANCED_PARAMETERS = "/admin/plugins/myportal/manage_advanced_parameters.html";

    // JSP
    private static final String JSP_BUILD_DEFAULT_PAGE = "BuildDefaultPage.jsp";
    private static final String JSP_BUILD_DEFAULT_PAGE_WIDGETS_LIST = "BuildDefaultPageWidgetsList.jsp";
    private static final String JSP_URL_MANAGE_ADVANCED_PARAMETERS = "jsp/admin/plugins/myportal/ManageAdvancedParameters.jsp";
    private DefaultPageBuilderService _service = DefaultPageBuilderService.getInstance(  );
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Get the page to build the default page
     * @param request {@link HttpServletRequest}
     * @return a html code
     */
    public String getBuildDefaultPage( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_BUILD_DEFAULT_PAGE );

        Map<String, List<WidgetComponent>> mapWidgetComponents = _service.getAllSetWidgetComponents(  );
        ReferenceItem columnFixed = DefaultPageBuilderService.getInstance(  )
                                                             .getWidgetParameterDefaultValue( PARAMETER_COLUMN_FIXED );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_MAP_WIDGET_COMPONENTS, mapWidgetComponents );
        model.put( MARK_COLUMN_COUNT, _service.getColumnCount(  ) );
        model.put( MARK_MAP_AVAILABLE_ORDERS, getMapAvailableOrders(  ) );
        model.put( MARK_LIST_AVAILABLE_COLUMNS, getListAvailableColumns(  ) );
        model.put( MARK_MAP_COLUMN_ORDER_STATUS, _service.getOrderedColumnsStatus(  ) );
        model.put( MARK_COLUMN_FIXED, columnFixed.getName(  ) );
        model.put( MARK_PERMISSION_MANAGE_ADVANCED_PARAMETERS,
            RBACService.isAuthorized( MyPortalResourceIdService.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                MyPortalResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_BUILD_DEFAULT_PAGE, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Get the page of widgets list
     * @param request {@link HttpServletRequest}
     * @return a html code
     */
    public String getWidgetsListPage( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_WIDGETS_LIST );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        UrlItem url = new UrlItem( JSP_BUILD_DEFAULT_PAGE_WIDGETS_LIST );
        String strUrl = url.getUrl(  );
        Collection<Widget> listWidgets = WidgetService.instance(  ).getWidgetsList(  );
        LocalizedPaginator paginator = new LocalizedPaginator( (List<Widget>) listWidgets, _nItemsPerPage, strUrl,
                PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_NB_ITEMS_PER_PAGE, Integer.toString( _nItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_WIDGETS_LIST, paginator.getPageItems(  ) );
        model.put( MARK_LIST_AVAILABLE_COLUMNS, getListAvailableColumns(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_WIDGETS_LIST, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
    * Reorders columns
    * @param request the request
    * @return url
    */
    public String doReorderColumn( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strColumnName = request.getParameter( PARAMETER_COLUMN );

        if ( StringUtils.isNotBlank( strColumnName ) )
        {
            int nColumn = 0;

            try
            {
                nColumn = Integer.parseInt( strColumnName );
                _service.doReorderColumn( nColumn );
                strUrl = JSP_BUILD_DEFAULT_PAGE;
            }
            catch ( NumberFormatException nfe )
            {
                AppLogService.error( "DefaultPageBuilderJspBean.doReorderColumn : " + nfe.getMessage(  ), nfe );

                strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Moves the widgetComponent
     * @param request the request
     * @return url
     */
    public String doMoveWidgetComponent( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strIdWidgetComponent = request.getParameter( PARAMETER_ID_WIDGET_COMPONENT );
        WidgetComponent widgetComponent = null;
        int nOldOrder = 1;
        int nOldColumn = 1;
        boolean bCreate = false;

        if ( StringUtils.isNotBlank( strIdWidgetComponent ) && StringUtils.isNumeric( strIdWidgetComponent ) )
        {
            int nWidgetComponentId = Integer.parseInt( strIdWidgetComponent );
            widgetComponent = DefaultPageBuilderHome.findByPrimaryKey( nWidgetComponentId );
            nOldOrder = widgetComponent.getOrder(  );
            nOldColumn = widgetComponent.getColumn(  );
        }
        else
        {
            String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );

            if ( StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) )
            {
                bCreate = true;
                widgetComponent = new WidgetComponent(  );

                int nWidgetId = Integer.parseInt( strIdWidget );
                widgetComponent.setIdWidget( nWidgetId );
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
            }
        }

        if ( StringUtils.isBlank( strUrl ) )
        {
            String strOrder = request.getParameter( PARAMETER_WIDGET_ORDER );
            String strColumn = request.getParameter( PARAMETER_WIDGET_COLUMN );

            if ( StringUtils.isNotBlank( strOrder ) && StringUtils.isNumeric( strOrder ) &&
                    StringUtils.isNotBlank( strColumn ) && StringUtils.isNumeric( strColumn ) )
            {
                int nOrder = Integer.parseInt( strOrder );
                int nColumn = Integer.parseInt( strColumn );

                widgetComponent.setOrder( nOrder );
                widgetComponent.setColumn( nColumn );

                _service.doMoveWidgetComponent( widgetComponent, nOldColumn, nOldOrder, bCreate );

                strUrl = JSP_BUILD_DEFAULT_PAGE;
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
            }
        }

        return strUrl;
    }

    /**
     * Remove a widget component from the default page
     * @param request {@link HttpServletRequest}
     * @return the url return
     */
    public String doRemoveWidgetComponent( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strWidgetComponentId = request.getParameter( PARAMETER_ID_WIDGET_COMPONENT );

        if ( StringUtils.isNotBlank( strWidgetComponentId ) && StringUtils.isNumeric( strWidgetComponentId ) )
        {
            int nWidgetComponentId = Integer.parseInt( strWidgetComponentId );
            _service.doRemoveWidgetComponent( nWidgetComponentId );
            strUrl = JSP_BUILD_DEFAULT_PAGE;
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Returns the advanced parameters management interface
     * @param request {@link HttpServletRequest}
     * @return the interface
     */
    public String getManageAdvancedParameters( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( MyPortalResourceIdService.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    MyPortalResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return getBuildDefaultPage( request );
        }

        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_ADVANCED_PARAMETERS );

        Map<String, Object> model = DefaultPageBuilderService.getInstance(  ).getManageAdvancedParameters( getUser(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_ADVANCED_PARAMETERS, getLocale(  ),
                model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Modify the widget parameter default values
     * @param request {@link HttpServletRequest}
     * @return the url return
     * @throws AccessDeniedException access denied if the user does not have the permission
     */
    public String doModifyWidgetParameterDefaultValues( HttpServletRequest request )
        throws AccessDeniedException
    {
        if ( !RBACService.isAuthorized( MyPortalResourceIdService.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    MyPortalResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            throw new AccessDeniedException(  );
        }

        ReferenceList listParams = DefaultPageBuilderService.getInstance(  ).getWidgetParamDefaultValues(  );

        for ( ReferenceItem param : listParams )
        {
            String strParamValue = request.getParameter( param.getCode(  ) );

            if ( StringUtils.isBlank( strParamValue ) )
            {
                strParamValue = ZERO;
            }

            param.setName( strParamValue );
            DefaultPageBuilderService.getInstance(  ).updateWidgetParameterDefaultValue( param );
        }

        return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_ADVANCED_PARAMETERS;
    }

    /**
     * Returns list with available column
     * @return all available columns
     */
    private ReferenceList getListAvailableColumns(  )
    {
        ReferenceList refList = new ReferenceList(  );

        // add empty item
        refList.addItem( StringUtils.EMPTY, StringUtils.EMPTY );

        for ( int nColumnIndex = 1; nColumnIndex <= _service.getColumnCount(  ); nColumnIndex++ )
        {
            refList.addItem( nColumnIndex, Integer.toString( nColumnIndex ) );
        }

        return refList;
    }

    /**
     * Builds all refList order for all columns
     * @return the map with column id as key
     */
    private Map<String, ReferenceList> getMapAvailableOrders(  )
    {
        Map<String, ReferenceList> mapAvailableOrders = new HashMap<String, ReferenceList>(  );

        // get columns
        for ( Integer nColumn : DefaultPageBuilderHome.findColumns(  ) )
        {
            // get orders
            mapAvailableOrders.put( nColumn.toString(  ), getListAvailableOrders( nColumn ) );
        }

        return mapAvailableOrders;
    }

    /**
     * Orders reference list for the given column
     * @param nColumn column
     * @return the refList
     */
    private ReferenceList getListAvailableOrders( int nColumn )
    {
        ReferenceList refList = new ReferenceList(  );

        // add empty item
        refList.addItem( StringUtils.EMPTY, StringUtils.EMPTY );

        int nMaxOrder = DefaultPageBuilderHome.findMaxOrder( nColumn );

        for ( int nOrder = 1; nOrder <= nMaxOrder; nOrder++ )
        {
            refList.addItem( nOrder, Integer.toString( nOrder ) );
        }

        return refList;
    }
}
