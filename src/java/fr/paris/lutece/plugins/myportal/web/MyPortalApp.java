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

import fr.paris.lutece.plugins.myportal.business.Category;
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.service.CategoryService;
import fr.paris.lutece.plugins.myportal.service.DefaultPageBuilderService;
import fr.paris.lutece.plugins.myportal.service.MyPortalPageService;
import fr.paris.lutece.plugins.myportal.service.MyPortalPlugin;
import fr.paris.lutece.plugins.myportal.service.WidgetService;
import fr.paris.lutece.plugins.myportal.util.auth.MyPortalUser;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides a simple implementation of an XPage
 */
public class MyPortalApp implements XPageApplication
{
    // CONSTANTS
    private static final String LINE = "-";
    private static final String BEAN_MYPORTAL_WIDGETSERVICE = "myportal.widgetService";

    // TEMPLATES
    private static final String TEMPLATE_MYPORTAL_PAGE = "skin/plugins/myportal/myportal.html";
    private static final String TEMPLATE_BROWSE_CATEGORIES = "skin/plugins/myportal/browse_categories.html";
    private static final String TEMPLATE_BROWSE_CATEGORIES_WIDGETS = "skin/plugins/myportal/browse_categories_widgets.html";
    private static final String TEMPLATE_MYPORTAL_NAVIGATION = "skin/plugins/myportal/myportal_navigation.html";
    private static final String TEMPLATE_MYPORTAL_ADD_TAB = "skin/plugins/myportal/myportal_add_tab.html";
    private static final String TEMPLATE_MYPORTAL_EDIT_TAB = "skin/plugins/myportal/myportal_edit_tab.html";
    private static final String TEMPLATE_MYPORTAL_EDIT_WIDGET = "skin/plugins/myportal/myportal_edit_widget.html";
    private static final String TEMPLATE_BROWSE_ESSENTIAL_WIDGETS = "skin/plugins/myportal/browse_essential_widgets.html";
    private static final String TEMPLATE_BROWSE_NEW_WIDGETS = "skin/plugins/myportal/browse_new_widgets.html";
    private static final String TEMPLATE_SEARCH_WIDGETS = "skin/plugins/myportal/search_widgets.html";

    // PARAMETERS
    private static final String PARAMETER_ID_WIDGET = "id_widget";
    private static final String PARAMETER_TAB_NAME = "tab_name";
    private static final String PARAMETER_WIDGET = "widget";
    private static final String PARAMETER_COLUMN = "column";
    private static final String PARAMETER_PORTAL_STATE = "portalState";
    private static final String PARAMETER_CATEGORY_ID_CATEGORY = "category_id_category";
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_SEARCH_WIDGETS_NAME = "search_widgets_name";
    private static final String PARAMETER_TAB_INDEX = "tab_index";
    private static final String PARAMETER_PAGE = "page";

    // MARKS
    private static final String MARK_WIDGETS = "widgets";
    private static final String MARK_WIDGETS_LIST = "widgets_list";
    private static final String MARK_LIST_TAB = "tabs_list";
    private static final String MARK_BASE_URL = "base_url";
    private static final String MARK_TAB_NAME = "tab_name";
    private static final String MARK_CATEGORIES_LIST = "categories_list";
    private static final String MARK_CATEGORY_ID_CATEGORY = "category_id_category";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_WIDGETS_LIST_HTML = "widgets_list_html";
    private static final String MARK_MYPORTAL_NAVIGATION_CONTENT = "myportal_navigation_content";
    private static final String MARK_ACTION = "action";
    private static final String MARK_PAGINATOR_URL_FOR_JS = "paginator_url_for_js";
    private static final String MARK_SEARCH_WIDGETS_NAME = "search_widgets_name";
    private static final String MARK_TAB_INDEX = "tab_index";
    private static final String MARK_USER_WIDGET_IDS = "user_widget_ids";
    private static final String MARK_WIDGET = "widget";
    private static final String MARK_NB_COLUMNS = "nb_columns";

    // PROPERTIES
    private static final String PROPERTY_PAGE_PATH = "myportal.myportal.pagePathLabel";
    private static final String PROPERTY_PAGE_TITLE = "myportal.myportal.pageTitle";
    private static final String PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE_IN_FO = "myportal.listWidgets.itemsPerPageInFO";
    private static final String PROPERTY_URL_RETURN = "myportal.urlReturn";
    private static final String PROPERTY_DEFAULT_COLUMN = "myportal.defaultColumn";

    // ACTIONS
    private static final String ACTION_BROWSE_CATEGORIES = "browse_categories";
    private static final String ACTION_BROWSE_ESSENTIAL_WIDGETS = "browse_essential_widgets";
    private static final String ACTION_BROWSE_NEW_WIDGETS = "browse_new_widgets";
    private static final String ACTION_SEARCH_WIDGETS = "search_widgets";
    private static final String ACTION_ADD_TAB = "add_tab";
    private static final String ACTION_EDIT_TAB = "edit_tab";
    private static final String ACTION_EDIT_WIDGET = "edit_widget";

    // JSP
    private static final String JSP_URL_BROWSE_CATEGORIES_WIDGETS = "jsp/site/plugins/myportal/BrowseCategoriesWidgets.jsp";
    private static final String JSP_URL_BROWSE_ESSENTIAL_WIDGETS = "jsp/site/plugins/myportal/BrowseEssentialWidgets.jsp";
    private static final String JSP_URL_BROWSE_NEW_WIDGETS = "jsp/site/plugins/myportal/BrowseNewWidgets.jsp";
    private static final String JSP_URL_SEARCH_WIDGETS = "jsp/site/plugins/myportal/SearchWidgets.jsp";

    // private fields
    private MyPortalPageService _pageService = MyPortalPageService.getInstance(  );
    private WidgetService _widgetService = (WidgetService) SpringContextService.getPluginBean( MyPortalPlugin.PLUGIN_NAME,
            BEAN_MYPORTAL_WIDGETSERVICE );

    /**
     * Returns the content of the page myportal.
     * @param request The http request
     * @param nMode The current mode
     * @param plugin The plugin object
     * @return the {@link XPage}
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException Message displayed if an exception occurs
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException
    {
        XPage page = new XPage(  );

        page.setTitle( I18nService.getLocalizedString( PROPERTY_PAGE_TITLE, request.getLocale(  ) ) );
        page.setPathLabel( I18nService.getLocalizedString( PROPERTY_PAGE_PATH, request.getLocale(  ) ) );

        String strAction = request.getParameter( PARAMETER_ACTION );

        if ( StringUtils.isNotBlank( strAction ) )
        {
            if ( ACTION_ADD_TAB.equals( strAction ) )
            {
                page.setContent( getMyPortalAddTab( request ) );
            }
            else if ( ACTION_EDIT_TAB.equals( strAction ) )
            {
                page.setContent( getMyPortalEditTab( request ) );
            }
            else if ( ACTION_EDIT_WIDGET.equals( strAction ) )
            {
                page.setContent( getMyPortalEditWidget( request ) );
            }
            else
            {
                page.setContent( getMyPortalNavigation( request ) );
            }
        }
        else
        {
            String strWidgets = _pageService.getUserPage( getUser( request ), request );
            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( MARK_WIDGETS, strWidgets );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYPORTAL_PAGE, request.getLocale(  ), model );
            page.setContent( template.getHtml(  ) );
        }

        return page;
    }

    /**
     * Get the content of the page MyPortalNavigation
     * @param request {@link HttpServletRequest}
     * @return the html code
     */
    public String getMyPortalNavigation( HttpServletRequest request )
    {
        String strHtml = StringUtils.EMPTY;
        String strAction = request.getParameter( PARAMETER_ACTION );

        if ( StringUtils.isNotBlank( strAction ) )
        {
            String strNavigationContentHtml = StringUtils.EMPTY;
            String strName = request.getParameter( PARAMETER_SEARCH_WIDGETS_NAME );
            String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );

            if ( strName == null )
            {
                strName = StringUtils.EMPTY;
            }

            Map<String, Object> model = new HashMap<String, Object>(  );

            if ( ACTION_BROWSE_CATEGORIES.equals( strAction ) )
            {
                strNavigationContentHtml = getBrowseCategories( request );
            }
            else if ( ACTION_BROWSE_ESSENTIAL_WIDGETS.equals( strAction ) )
            {
                strNavigationContentHtml = getBrowseEssentialWidgets( request );
            }
            else if ( ACTION_BROWSE_NEW_WIDGETS.equals( strAction ) )
            {
                strNavigationContentHtml = getBrowseNewWidgets( request );
            }
            else if ( ACTION_SEARCH_WIDGETS.equals( strAction ) )
            {
                strNavigationContentHtml = getSearchWidgets( request );
            }

            String strBaseUrl = AppPathService.getBaseUrl( request );
            model.put( MARK_BASE_URL, strBaseUrl );
            model.put( MARK_ACTION, strAction );
            model.put( MARK_MYPORTAL_NAVIGATION_CONTENT, strNavigationContentHtml );
            model.put( MARK_SEARCH_WIDGETS_NAME, strName );
            model.put( MARK_TAB_INDEX, strTabIndex );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYPORTAL_NAVIGATION,
                    request.getLocale(  ), model );
            strHtml = template.getHtml(  );
        }

        return strHtml;
    }

    /**
     * Get Browse categories popup
     * @param request The HTTP request
     * @return The page
     */
    public String getBrowseCategories( HttpServletRequest request )
    {
        String strCategoryId = request.getParameter( PARAMETER_CATEGORY_ID_CATEGORY );

        if ( StringUtils.isBlank( strCategoryId ) )
        {
            Category category = CategoryService.getInstance(  ).findFirstCategory(  );
            strCategoryId = Integer.toString( category.getIdCategory(  ) );
        }

        String strBaseUrl = AppPathService.getBaseUrl( request );
        String strWidgetsListHtml = getBrowseCategoriesWidgets( request );
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_BASE_URL, strBaseUrl );
        model.put( MARK_CATEGORIES_LIST, CategoryService.getInstance(  ).getCategoriesList(  ) );
        model.put( MARK_CATEGORY_ID_CATEGORY, strCategoryId );
        model.put( MARK_WIDGETS_LIST_HTML, strWidgetsListHtml );
        model.put( MARK_TAB_INDEX, strTabIndex );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_BROWSE_CATEGORIES, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Get Add Widget Content
     * @param request The HTTP request
     * @return The page
     */
    public String getBrowseCategoriesWidgets( HttpServletRequest request )
    {
        String strHtml = StringUtils.EMPTY;
        String strCategoryId = request.getParameter( PARAMETER_CATEGORY_ID_CATEGORY );
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );
        List<Widget> listWidgets;
        Category category = null;

        if ( StringUtils.isNotBlank( strCategoryId ) && StringUtils.isNumeric( strCategoryId ) )
        {
            int nCategoryId = Integer.parseInt( strCategoryId );
            category = CategoryService.getInstance(  ).findByPrimaryKey( nCategoryId );
        }
        else
        {
            category = CategoryService.getInstance(  ).findFirstCategory(  );
            strCategoryId = Integer.toString( category.getIdCategory(  ) );
        }

        if ( category != null )
        {
            listWidgets = _widgetService.getWidgetsByCategoryId( category.getIdCategory(  ) );
        }
        else
        {
            listWidgets = new ArrayList<Widget>(  );
        }

        List<TabConfig> listTabs = _pageService.getTabList( getUser( request ) );

        UrlItem url = new UrlItem( AppPathService.getPortalUrl(  ) );
        url.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        url.addParameter( PARAMETER_ACTION, ACTION_BROWSE_CATEGORIES );
        url.addParameter( PARAMETER_CATEGORY_ID_CATEGORY, strCategoryId );
        url.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        UrlItem urlForJs = new UrlItem( JSP_URL_BROWSE_CATEGORIES_WIDGETS );
        urlForJs.addParameter( PARAMETER_CATEGORY_ID_CATEGORY, strCategoryId );
        urlForJs.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        // Paginator
        String strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, "1" );
        int nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE_IN_FO, 50 );
        LocalizedPaginator paginator = new LocalizedPaginator( listWidgets, nDefaultItemsPerPage, url.getUrl(  ),
                Paginator.PARAMETER_PAGE_INDEX, strCurrentPageIndex, request.getLocale(  ) );
        String strBaseUrl = AppPathService.getBaseUrl( request );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_BASE_URL, strBaseUrl );
        model.put( MARK_WIDGETS_LIST, paginator.getPageItems(  ) );
        model.put( MARK_NB_ITEMS_PER_PAGE, Integer.toString( nDefaultItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LIST_TAB, listTabs );
        model.put( MARK_PAGINATOR_URL_FOR_JS, urlForJs.getUrl(  ) );
        model.put( MARK_TAB_INDEX, strTabIndex );
        model.put( MARK_USER_WIDGET_IDS, _widgetService.getUserWidgetIds( getUser( request ) ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_BROWSE_CATEGORIES_WIDGETS,
                request.getLocale(  ), model );

        strHtml = template.getHtml(  );

        return strHtml;
    }

    /**
     * Process add content - add a widget to the page
     * @param request The HTTP request
     * @return the forward url
     */
    public String doAddWidget( HttpServletRequest request )
    {
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );
        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );

        if ( StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) &&
                StringUtils.isNotBlank( strTabIndex ) && StringUtils.isNumeric( strTabIndex ) )
        {
            int nIdWidget = Integer.parseInt( strIdWidget );
            int nColumn = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_COLUMN, 1 );
            int nTabIndex = Integer.parseInt( strTabIndex );

            _pageService.addWidget( getUser( request ), nIdWidget, nTabIndex, nColumn );
        }

        return AppPropertiesService.getProperty( PROPERTY_URL_RETURN );
    }

    /**
     * Edit a widget
     * @param request The HTTP request
     * @return the forward url
     */
    public String doEditWidget( HttpServletRequest request )
    {
        String strIdTab = request.getParameter( PARAMETER_TAB_INDEX );
        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );
        String strColumn = request.getParameter( PARAMETER_COLUMN );

        if ( StringUtils.isNotBlank( strIdTab ) && StringUtils.isNumeric( strIdTab ) &&
                StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) &&
                StringUtils.isNotBlank( strColumn ) && StringUtils.isNumeric( strColumn ) )
        {
            int nIdTab = Integer.parseInt( strIdTab );
            int nIdWidget = Integer.parseInt( strIdWidget );
            int nColumn = Integer.parseInt( strColumn );
            _pageService.editWidget( getUser( request ), nIdTab, nIdWidget, nColumn );
        }

        return AppPropertiesService.getProperty( PROPERTY_URL_RETURN );
    }

    /**
     * Process delete - remove a widget from the page
     * @param request The HTTP request
     * @return the forward url
     */
    public String doRemoveWidget( HttpServletRequest request )
    {
        String strWidget = request.getParameter( PARAMETER_WIDGET );
        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );

        if ( StringUtils.isNotBlank( strWidget ) )
        {
            String strWidgetCssId = PARAMETER_WIDGET + LINE;
            int nIdWidget = Integer.parseInt( strWidget.substring( strWidgetCssId.length(  ) ) );

            _pageService.removeWidget( getUser( request ), nIdWidget );
        }
        else if ( StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) )
        {
            int nIdWidget = Integer.parseInt( strIdWidget );

            _pageService.removeWidget( getUser( request ), nIdWidget );
        }

        return AppPropertiesService.getProperty( PROPERTY_URL_RETURN );
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
     * Get the essential widgets list html
     * @param request {@link HttpServletRequest}
     * @return the html code
     */
    public String getBrowseEssentialWidgets( HttpServletRequest request )
    {
        List<Widget> listWidgets = _widgetService.getEssentialWidgets(  );
        List<TabConfig> listTabs = _pageService.getTabList( getUser( request ) );
        String strBaseUrl = AppPathService.getBaseUrl( request );
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );

        UrlItem url = new UrlItem( AppPathService.getPortalUrl(  ) );
        url.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        url.addParameter( PARAMETER_ACTION, ACTION_BROWSE_ESSENTIAL_WIDGETS );
        url.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        UrlItem urlForJs = new UrlItem( JSP_URL_BROWSE_ESSENTIAL_WIDGETS );
        urlForJs.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        // Paginator
        String strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, "1" );
        int nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE_IN_FO, 50 );
        LocalizedPaginator paginator = new LocalizedPaginator( listWidgets, nDefaultItemsPerPage, url.getUrl(  ),
                Paginator.PARAMETER_PAGE_INDEX, strCurrentPageIndex, request.getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_BASE_URL, strBaseUrl );
        model.put( MARK_WIDGETS_LIST, paginator.getPageItems(  ) );
        model.put( MARK_NB_ITEMS_PER_PAGE, Integer.toString( nDefaultItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LIST_TAB, listTabs );
        model.put( MARK_PAGINATOR_URL_FOR_JS, urlForJs.getUrl(  ) );
        model.put( MARK_TAB_INDEX, strTabIndex );
        model.put( MARK_USER_WIDGET_IDS, _widgetService.getUserWidgetIds( getUser( request ) ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_BROWSE_ESSENTIAL_WIDGETS,
                request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Get the new widgets list html
     * @param request {@link HttpServletRequest}
     * @return the html code
     */
    public String getBrowseNewWidgets( HttpServletRequest request )
    {
        List<Widget> listWidgets = _widgetService.getNewWidgets(  );
        List<TabConfig> listTabs = _pageService.getTabList( getUser( request ) );
        String strBaseUrl = AppPathService.getBaseUrl( request );
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );

        UrlItem url = new UrlItem( AppPathService.getPortalUrl(  ) );
        url.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        url.addParameter( PARAMETER_ACTION, ACTION_BROWSE_NEW_WIDGETS );
        url.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        UrlItem urlForJs = new UrlItem( JSP_URL_BROWSE_NEW_WIDGETS );
        urlForJs.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        // Paginator
        String strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, "1" );
        int nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE_IN_FO, 50 );
        LocalizedPaginator paginator = new LocalizedPaginator( listWidgets, nDefaultItemsPerPage, url.getUrl(  ),
                Paginator.PARAMETER_PAGE_INDEX, strCurrentPageIndex, request.getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_BASE_URL, strBaseUrl );
        model.put( MARK_WIDGETS_LIST, paginator.getPageItems(  ) );
        model.put( MARK_NB_ITEMS_PER_PAGE, Integer.toString( nDefaultItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LIST_TAB, listTabs );
        model.put( MARK_PAGINATOR_URL_FOR_JS, urlForJs.getUrl(  ) );
        model.put( MARK_TAB_INDEX, strTabIndex );
        model.put( MARK_USER_WIDGET_IDS, _widgetService.getUserWidgetIds( getUser( request ) ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_BROWSE_NEW_WIDGETS, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * Get the new widgets list html
     * @param request {@link HttpServletRequest}
     * @return the html code
     */
    public String getSearchWidgets( HttpServletRequest request )
    {
        String strName = request.getParameter( PARAMETER_SEARCH_WIDGETS_NAME );
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );

        if ( strName == null )
        {
            strName = StringUtils.EMPTY;
        }

        List<Widget> listWidgets = _widgetService.getWidgetsByName( strName );
        List<TabConfig> listTabs = _pageService.getTabList( getUser( request ) );
        String strBaseUrl = AppPathService.getBaseUrl( request );

        UrlItem url = new UrlItem( AppPathService.getPortalUrl(  ) );
        url.addParameter( PARAMETER_PAGE, MyPortalPlugin.PLUGIN_NAME );
        url.addParameter( PARAMETER_ACTION, ACTION_SEARCH_WIDGETS );
        url.addParameter( PARAMETER_SEARCH_WIDGETS_NAME, strName );
        url.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        UrlItem urlForJs = new UrlItem( JSP_URL_SEARCH_WIDGETS );
        urlForJs.addParameter( PARAMETER_SEARCH_WIDGETS_NAME, strName );
        urlForJs.addParameter( PARAMETER_TAB_INDEX, strTabIndex );

        // Paginator
        String strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, "1" );
        int nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_WIDGET_PER_PAGE_IN_FO, 50 );
        LocalizedPaginator paginator = new LocalizedPaginator( listWidgets, nDefaultItemsPerPage, url.getUrl(  ),
                Paginator.PARAMETER_PAGE_INDEX, strCurrentPageIndex, request.getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_BASE_URL, strBaseUrl );
        model.put( MARK_WIDGETS_LIST, paginator.getPageItems(  ) );
        model.put( MARK_NB_ITEMS_PER_PAGE, Integer.toString( nDefaultItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LIST_TAB, listTabs );
        model.put( MARK_PAGINATOR_URL_FOR_JS, urlForJs.getUrl(  ) );
        model.put( MARK_TAB_INDEX, strTabIndex );
        model.put( MARK_USER_WIDGET_IDS, _widgetService.getUserWidgetIds( getUser( request ) ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_SEARCH_WIDGETS, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Gets the user from the request
     * @param request The HTTP user
     * @return The Lutece User
     */
    private LuteceUser getUser( HttpServletRequest request )
    {
        LuteceUser user = null;

        if ( SecurityService.isAuthenticationEnable(  ) )
        {
            try
            {
                user = SecurityService.getInstance(  ).getRemoteUser( request );
            }
            catch ( UserNotSignedException ue )
            {
                AppLogService.error( ue.getMessage(  ), ue );
            }
        }

        if ( user == null )
        {
            user = new MyPortalUser( "Anonymous" );
        }

        return user;
    }

    /**
     * Get the content of the page getMyPortalAddTab
     * @param request {@link HttpServletRequest}
     * @return the html code
     */
    public String getMyPortalAddTab( HttpServletRequest request )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYPORTAL_ADD_TAB, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Process add tab
     * @param request The HTTP request
     * @return the forward url
     */
    public String doAddTab( HttpServletRequest request )
    {
        String strTabName = request.getParameter( PARAMETER_TAB_NAME );
        _pageService.addTab( getUser( request ), strTabName );

        return AppPropertiesService.getProperty( PROPERTY_URL_RETURN );
    }

    /**
     * Get the content of editing a tab
     * @param request {@link HttpServletRequest}
     * @return the html code
     */
    public String getMyPortalEditTab( HttpServletRequest request )
    {
        String strHtml = StringUtils.EMPTY;
        String strTabId = request.getParameter( PARAMETER_TAB_INDEX );

        if ( StringUtils.isNotBlank( strTabId ) && StringUtils.isNumeric( strTabId ) )
        {
            int nIdTab = Integer.parseInt( strTabId );

            List<TabConfig> listTabs = _pageService.getTabList( getUser( request ) );
            TabConfig tabConfig = listTabs.get( nIdTab - 1 );

            Map<String, Object> model = new HashMap<String, Object>(  );

            model.put( MARK_TAB_INDEX, nIdTab );
            model.put( MARK_TAB_NAME, tabConfig.getName(  ) );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYPORTAL_EDIT_TAB, request.getLocale(  ),
                    model );

            strHtml = template.getHtml(  );
        }

        return strHtml;
    }

    /**
     * Edit a tab
     * @param request The HTTP request
     * @return the forward url
     */
    public String doEditTab( HttpServletRequest request )
    {
        String strTabNewName = request.getParameter( PARAMETER_TAB_NAME );
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );

        if ( StringUtils.isNotBlank( strTabIndex ) && StringUtils.isNumeric( strTabIndex ) )
        {
            int nIdTab = Integer.parseInt( strTabIndex );

            _pageService.editTab( getUser( request ), strTabNewName, nIdTab );
        }

        return AppPropertiesService.getProperty( PROPERTY_URL_RETURN );
    }

    /**
     * Delete a tab
     * @param request The HTTP request
     * @return the forward url
     */
    public String doDelTab( HttpServletRequest request )
    {
        String strTabIndex = request.getParameter( PARAMETER_TAB_INDEX );

        if ( StringUtils.isNotBlank( strTabIndex ) && StringUtils.isNumeric( strTabIndex ) )
        {
            int nIdTab = Integer.parseInt( strTabIndex );

            // The first tab cannot be deleted
            if ( nIdTab != 1 )
            {
                _pageService.delTab( getUser( request ), nIdTab );
            }
        }

        return AppPropertiesService.getProperty( PROPERTY_URL_RETURN );
    }

    /**
     * Get the content of editing a widget
     * @param request {@link HttpServletRequest}
     * @return the html code
     */
    public String getMyPortalEditWidget( HttpServletRequest request )
    {
        String strHtml = StringUtils.EMPTY;
        String strIdTab = request.getParameter( PARAMETER_TAB_INDEX );
        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );

        if ( StringUtils.isNotBlank( strIdTab ) && StringUtils.isNumeric( strIdTab ) &&
                StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) )
        {
            int nIdTab = Integer.parseInt( strIdTab );
            int nIdWidget = Integer.parseInt( strIdWidget );
            int nNbColumns = DefaultPageBuilderService.getInstance(  ).getColumnCount(  );

            Widget widget = _widgetService.getWidget( nIdWidget );

            if ( widget != null )
            {
                Map<String, Object> model = new HashMap<String, Object>(  );

                model.put( MARK_TAB_INDEX, nIdTab );
                model.put( MARK_WIDGET, widget );
                model.put( MARK_NB_COLUMNS, nNbColumns );

                HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYPORTAL_EDIT_WIDGET,
                        request.getLocale(  ), model );

                strHtml = template.getHtml(  );
            }
        }

        return strHtml;
    }

    /**
     * Get tabs of my portal used by MyPortal.jsp
     * @param request The HTTP request
     * @return tabs
     */
    public String getTabs( HttpServletRequest request )
    {
        return _pageService.getUserPage( getUser( request ), request );
    }
}
