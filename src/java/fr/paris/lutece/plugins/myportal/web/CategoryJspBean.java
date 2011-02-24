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

import fr.paris.lutece.plugins.myportal.business.Category;
import fr.paris.lutece.plugins.myportal.business.CategoryHome;
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
public class CategoryJspBean extends PluginAdminPageJspBean
{

    ////////////////////////////////////////////////////////////////////////////
    // Constants
    // Right
    public static final String RIGHT_MANAGE_MYPORTAL_CATEGORY = "MYPORTAL_CATEGORY_MANAGEMENT";
    // Parameters
    private static final String PARAMETER_CATEGORY_ID_CATEGORY = "category_id_category";
    private static final String PARAMETER_CATEGORY_ID_PARENT = "category_id_parent";
    private static final String PARAMETER_CATEGORY_NAME = "category_name";
    private static final String PARAMETER_CATEGORY_DESCRIPTION = "category_description";
    private static final String PARAMETER_CATEGORY_PAGE_INDEX = "category_page_index";
    private static final String PARAMETER_PAGE_INDEX = "page_index";
    // templates
    private static final String TEMPLATE_MANAGE_CATEGORYS = "/admin/plugins/myportal/manage_category.html";
    private static final String TEMPLATE_CREATE_CATEGORY = "/admin/plugins/myportal/create_category.html";
    private static final String TEMPLATE_MODIFY_CATEGORY = "/admin/plugins/myportal/modify_category.html";
// Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_CATEGORYS = "myportal.manage_categorys.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_CATEGORY = "myportal.modify_category.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_CATEGORY = "myportal.create_category.pageTitle";
    // Markers
    private static final String MARK_CATEGORY_LIST = "category_list";
    private static final String MARK_CATEGORY = "category";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    // Jsp Definition
    private static final String JSP_DO_REMOVE_CATEGORY = "jsp/admin/plugins/myportal/DoRemoveCategory.jsp";
    private static final String JSP_MANAGE_CATEGORYS = "jsp/admin/plugins/myportal/ManageCategorys.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_CATEGORYS = "ManageCategorys.jsp";
    // Properties
    private static final String PROPERTY_DEFAULT_LIST_CATEGORY_PER_PAGE = "myportal.listCategorys.itemsPerPage";
    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_CATEGORY = "myportal.message.confirmRemoveCategory";
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
    public String getManageCategories(HttpServletRequest request)
    {
        setPageTitleProperty(PROPERTY_PAGE_TITLE_MANAGE_CATEGORYS);

        _strCurrentPageIndex = Paginator.getPageIndex(request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex);
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt(PROPERTY_DEFAULT_LIST_CATEGORY_PER_PAGE, 50);
        _nItemsPerPage = Paginator.getItemsPerPage(request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage);
        UrlItem url = new UrlItem(JSP_MANAGE_CATEGORYS);
        String strUrl = url.getUrl();
        Collection<Category> listCATEGORYs = CategoryHome.getCategorysList(getPlugin());
        Paginator paginator = new Paginator((List<Category>) listCATEGORYs, _nItemsPerPage, strUrl, PARAMETER_PAGE_INDEX, _strCurrentPageIndex);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put(MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage);
        model.put(MARK_PAGINATOR, paginator);
        model.put(MARK_CATEGORY_LIST, paginator.getPageItems());

        HtmlTemplate templateList = AppTemplateService.getTemplate(TEMPLATE_MANAGE_CATEGORYS, getLocale(), model);

        return getAdminPage(templateList.getHtml());
    }

    /**
     * Returns the form to create a category
     *
     * @param request The Http request
     * @return the html code of the category form
     */
    public String getCreateCategory(HttpServletRequest request)
    {
        setPageTitleProperty(PROPERTY_PAGE_TITLE_CREATE_CATEGORY);

        Map<String, Object> model = new HashMap<String, Object>();

        HtmlTemplate template = AppTemplateService.getTemplate(TEMPLATE_CREATE_CATEGORY, getLocale(), model);

        return getAdminPage(template.getHtml());
    }

    /**
     * Process the data capture form of a new category
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateCategory(HttpServletRequest request)
    {
        Category category = new Category();



        if (request.getParameter(PARAMETER_CATEGORY_ID_PARENT).equals(""))
        {
            return AdminMessageService.getMessageUrl(request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP);
        }
        int nIdParent = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY_ID_PARENT));
        category.setIdParent(nIdParent);


        if (request.getParameter(PARAMETER_CATEGORY_NAME).equals(""))
        {
            return AdminMessageService.getMessageUrl(request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP);
        }
        category.setName(request.getParameter(PARAMETER_CATEGORY_NAME));


        if (request.getParameter(PARAMETER_CATEGORY_DESCRIPTION).equals(""))
        {
            return AdminMessageService.getMessageUrl(request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP);
        }
        category.setDescription(request.getParameter(PARAMETER_CATEGORY_DESCRIPTION));

        CategoryHome.create(category, getPlugin());
        return JSP_REDIRECT_TO_MANAGE_CATEGORYS;
    }

    /**
     * Manages the removal form of a category whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveCategory(HttpServletRequest request)
    {
        int nId = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY_ID_CATEGORY));
        UrlItem url = new UrlItem(JSP_DO_REMOVE_CATEGORY);
        url.addParameter(PARAMETER_CATEGORY_ID_CATEGORY, nId);
        return AdminMessageService.getMessageUrl(request, MESSAGE_CONFIRM_REMOVE_CATEGORY, url.getUrl(), AdminMessage.TYPE_CONFIRMATION);
    }

    /**
     * Handles the removal form of a category
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage categorys
     */
    public String doRemoveCategory(HttpServletRequest request)
    {
        int nId = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY_ID_CATEGORY));
        CategoryHome.remove(nId, getPlugin());

        return JSP_REDIRECT_TO_MANAGE_CATEGORYS;
    }

    /**
     * Returns the form to update info about a category
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyCategory(HttpServletRequest request)
    {
        setPageTitleProperty(PROPERTY_PAGE_TITLE_MODIFY_CATEGORY);
        int nId = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY_ID_CATEGORY));
        Category category = CategoryHome.findByPrimaryKey(nId, getPlugin());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put(MARK_CATEGORY, category);

        HtmlTemplate template = AppTemplateService.getTemplate(TEMPLATE_MODIFY_CATEGORY, getLocale(), model);

        return getAdminPage(template.getHtml());
    }

    /**
     * Process the change form of a category
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyCategory(HttpServletRequest request)
    {
        int nId = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY_ID_CATEGORY));
        Category category = CategoryHome.findByPrimaryKey(nId, getPlugin());
        if (request.getParameter(PARAMETER_CATEGORY_ID_CATEGORY).equals(""))
        {
            return AdminMessageService.getMessageUrl(request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP);
        }
        int nIdCategory = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY_ID_CATEGORY));
        category.setIdCategory(nIdCategory);
        if (request.getParameter(PARAMETER_CATEGORY_ID_PARENT).equals(""))
        {
            return AdminMessageService.getMessageUrl(request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP);
        }
        int nIdParent = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY_ID_PARENT));
        category.setIdParent(nIdParent);
        if (request.getParameter(PARAMETER_CATEGORY_NAME).equals(""))
        {
            return AdminMessageService.getMessageUrl(request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP);
        }
        category.setName(request.getParameter(PARAMETER_CATEGORY_NAME));
        if (request.getParameter(PARAMETER_CATEGORY_DESCRIPTION).equals(""))
        {
            return AdminMessageService.getMessageUrl(request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP);
        }
        category.setDescription(request.getParameter(PARAMETER_CATEGORY_DESCRIPTION));
        CategoryHome.update(category, getPlugin());
        return JSP_REDIRECT_TO_MANAGE_CATEGORYS;
    }
}
