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
import fr.paris.lutece.plugins.myportal.service.CategoryService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
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
 * This class provides the user interface to manage  Category, Widget, features
 * ( manage, create, modify, remove )
 */
public class CategoryJspBean extends PluginAdminPageJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    // Right
    public static final String RIGHT_MANAGE_MYPORTAL_CATEGORY = "MYPORTAL_CATEGORY_MANAGEMENT";

    // Parameters
    private static final String PARAMETER_CATEGORY_ID_CATEGORY = "category_id_category";
    private static final String PARAMETER_CATEGORY_NAME = "category_name";
    private static final String PARAMETER_CATEGORY_DESCRIPTION = "category_description";
    private static final String PARAMETER_PAGE_INDEX = "page_index";

    // templates
    private static final String TEMPLATE_MANAGE_CATEGORIES = "/admin/plugins/myportal/manage_categories.html";
    private static final String TEMPLATE_CREATE_CATEGORY = "/admin/plugins/myportal/create_category.html";
    private static final String TEMPLATE_MODIFY_CATEGORY = "/admin/plugins/myportal/modify_category.html";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_CATEGORIES = "myportal.manage_categories.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_CATEGORY = "myportal.modify_category.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_CATEGORY = "myportal.create_category.pageTitle";

    // Markers
    private static final String MARK_CATEGORY_LIST = "category_list";
    private static final String MARK_CATEGORY = "category";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";

    // Jsp Definition
    private static final String JSP_DO_REMOVE_CATEGORY = "jsp/admin/plugins/myportal/DoRemoveCategory.jsp";
    private static final String JSP_MANAGE_CATEGORYS = "jsp/admin/plugins/myportal/ManageCategories.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_CATEGORIES = "ManageCategories.jsp";

    // Properties
    private static final String PROPERTY_DEFAULT_LIST_CATEGORY_PER_PAGE = "myportal.listCategorys.itemsPerPage";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_CATEGORY = "myportal.message.confirmRemoveCategory";
    private static final String MESSAGE_ERROR = "myportal.message.error";
    private static final String MESSAGE_CANNOT_REMOVE_CATEGORY = "myportal.message.cannotRemoveCategory";

    //Variables
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Returns the list of category
     *
     * @param request The Http request
     * @return the categorys list
     */
    public String getManageCategories( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_CATEGORIES );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_CATEGORY_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        UrlItem url = new UrlItem( JSP_MANAGE_CATEGORYS );
        String strUrl = url.getUrl(  );
        Collection<Category> listCategories = CategoryService.getInstance(  ).getCategoriesList(  );
        LocalizedPaginator paginator = new LocalizedPaginator( (List<Category>) listCategories, _nItemsPerPage, strUrl,
                PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_CATEGORY_LIST, paginator.getPageItems(  ) );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_CATEGORIES, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Returns the form to create a category
     *
     * @param request The Http request
     * @return the html code of the category form
     */
    public String getCreateCategory( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_CATEGORY );

        Map<String, Object> model = new HashMap<String, Object>(  );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_CATEGORY, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new category
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateCategory( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strName = request.getParameter( PARAMETER_CATEGORY_NAME );
        String strDescription = request.getParameter( PARAMETER_CATEGORY_DESCRIPTION );

        if ( StringUtils.isNotBlank( strName ) && StringUtils.isNotBlank( strDescription ) )
        {
            Category category = new Category(  );
            category.setName( strName );
            category.setDescription( strDescription );

            CategoryService.getInstance(  ).createCategory( category );

            strUrl = JSP_REDIRECT_TO_MANAGE_CATEGORIES;
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Manages the removal form of a category whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveCategory( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strCategoryId = request.getParameter( PARAMETER_CATEGORY_ID_CATEGORY );

        if ( StringUtils.isNotBlank( strCategoryId ) && StringUtils.isNumeric( strCategoryId ) )
        {
            int nId = Integer.parseInt( strCategoryId );
            UrlItem url = new UrlItem( JSP_DO_REMOVE_CATEGORY );
            url.addParameter( PARAMETER_CATEGORY_ID_CATEGORY, nId );

            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_CATEGORY, url.getUrl(  ),
                    AdminMessage.TYPE_CONFIRMATION );
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Handles the removal form of a category
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage categorys
     */
    public String doRemoveCategory( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strCategoryId = request.getParameter( PARAMETER_CATEGORY_ID_CATEGORY );

        if ( StringUtils.isNotBlank( strCategoryId ) && StringUtils.isNumeric( strCategoryId ) )
        {
            int nId = Integer.parseInt( strCategoryId );

            if ( !CategoryService.getInstance(  ).removeCategory( nId ) )
            {
                strUrl = JSP_REDIRECT_TO_MANAGE_CATEGORIES;
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CANNOT_REMOVE_CATEGORY,
                        AdminMessage.TYPE_STOP );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Returns the form to update info about a category
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyCategory( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_CATEGORY );

        String strUrl = StringUtils.EMPTY;
        String strCategoryId = request.getParameter( PARAMETER_CATEGORY_ID_CATEGORY );

        if ( StringUtils.isNotBlank( strCategoryId ) && StringUtils.isNumeric( strCategoryId ) )
        {
            int nId = Integer.parseInt( strCategoryId );
            Category category = CategoryService.getInstance(  ).findByPrimaryKey( nId );

            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( MARK_CATEGORY, category );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_CATEGORY, getLocale(  ), model );

            strUrl = getAdminPage( template.getHtml(  ) );
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Process the change form of a category
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyCategory( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strCategoryId = request.getParameter( PARAMETER_CATEGORY_ID_CATEGORY );

        if ( StringUtils.isNotBlank( strCategoryId ) && StringUtils.isNumeric( strCategoryId ) )
        {
            String strName = request.getParameter( PARAMETER_CATEGORY_NAME );
            String strDescription = request.getParameter( PARAMETER_CATEGORY_DESCRIPTION );

            if ( StringUtils.isNotBlank( strName ) && StringUtils.isNotBlank( strDescription ) )
            {
                int nId = Integer.parseInt( strCategoryId );
                Category category = CategoryService.getInstance(  ).findByPrimaryKey( nId );

                if ( category != null )
                {
                    category.setName( strName );
                    category.setDescription( strDescription );
                    CategoryService.getInstance(  ).updateCategory( category );

                    strUrl = JSP_REDIRECT_TO_MANAGE_CATEGORIES;
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
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }
}
