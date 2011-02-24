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


import fr.paris.lutece.plugins.myportal.business.Layout;
import fr.paris.lutece.plugins.myportal.business.LayoutHome;
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
public class LayoutJspBean extends PluginAdminPageJspBean
{

    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // Right
    public static final String RIGHT_MANAGE_MYPORTAL = "MYPORTAL_MANAGEMENT";
    
    // Parameters
    private static final String PARAMETER_LAYOUT_ID_LAYOUT="layout_id_layout";
    private static final String PARAMETER_LAYOUT_NAME="layout_name";
    private static final String PARAMETER_LAYOUT_DESCRIPTION="layout_description";
    private static final String PARAMETER_LAYOUT_LAYOUT="layout_layout";
    private static final String PARAMETER_LAYOUT_PAGE_INDEX = "layout_page_index";  
    private static final String PARAMETER_PAGE_INDEX = "page_index";   

    // templates
    private static final String TEMPLATE_MANAGE_LAYOUTS="/admin/plugins/myportal/manage_layout.html";
    private static final String TEMPLATE_CREATE_LAYOUT="/admin/plugins/myportal/create_layout.html";
    private static final String TEMPLATE_MODIFY_LAYOUT="/admin/plugins/myportal/modify_layout.html";


// Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_LAYOUTS = "myportal.manage_layouts.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_LAYOUT = "myportal.modify_layout.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_LAYOUT = "myportal.create_layout.pageTitle";

    // Markers
    private static final String MARK_LAYOUT_LIST = "layout_list";
    private static final String MARK_LAYOUT = "layout";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";    
    
    
    // Jsp Definition
    private static final String JSP_DO_REMOVE_LAYOUT = "jsp/admin/plugins/myportal/DoRemoveLayout.jsp";
    private static final String JSP_MANAGE_LAYOUTS = "jsp/admin/plugins/myportal/ManageLayouts.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_LAYOUTS = "ManageLayouts.jsp";

    // Properties
    private static final String PROPERTY_DEFAULT_LIST_LAYOUT_PER_PAGE = "myportal.listLayouts.itemsPerPage";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_LAYOUT = "myportal.message.confirmRemoveLayout";
  
    //Variables
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;    
    
    /**
     * Returns the list of layout
     *
     * @param request The Http request
     * @return the layouts list
     */
    public String getManageLayouts( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_LAYOUTS );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX , _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_LAYOUT_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );
        UrlItem url = new UrlItem(  JSP_MANAGE_LAYOUTS);
        String strUrl = url.getUrl(  );
        Collection<Layout> listLAYOUTs = LayoutHome.getLayoutsList( getPlugin(  ) );
        Paginator paginator = new Paginator( (List<Layout>) listLAYOUTs, _nItemsPerPage, strUrl , PARAMETER_PAGE_INDEX, _strCurrentPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LAYOUT_LIST, paginator.getPageItems(  ));
        
        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_LAYOUTS, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Returns the form to create a layout
     *
     * @param request The Http request
     * @return the html code of the layout form
     */
    public String getCreateLayout( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_LAYOUT  );

        Map<String, Object> model = new HashMap<String, Object>(  );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_LAYOUT, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new layout
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateLayout( HttpServletRequest request )
    {
        Layout layout = new Layout(  );
             
                   	 
        
        if ( request.getParameter( PARAMETER_LAYOUT_NAME ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			layout.setName( request.getParameter( PARAMETER_LAYOUT_NAME ) );
			
        
        if ( request.getParameter( PARAMETER_LAYOUT_DESCRIPTION ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			layout.setDescription( request.getParameter( PARAMETER_LAYOUT_DESCRIPTION ) );
			
        
        if ( request.getParameter( PARAMETER_LAYOUT_LAYOUT ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			layout.setLayout( request.getParameter( PARAMETER_LAYOUT_LAYOUT ) );
			
      LayoutHome.create( layout, getPlugin(  ) );
      return JSP_REDIRECT_TO_MANAGE_LAYOUTS;
    }
    
    /**
     * Manages the removal form of a layout whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveLayout( HttpServletRequest request )
    {
        	int nId = Integer.parseInt( request.getParameter( PARAMETER_LAYOUT_ID_LAYOUT ) );
          UrlItem url = new UrlItem( JSP_DO_REMOVE_LAYOUT );
          url.addParameter( PARAMETER_LAYOUT_ID_LAYOUT, nId );
          return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_LAYOUT, url.getUrl(  ),AdminMessage.TYPE_CONFIRMATION );
    }
    /**
     * Handles the removal form of a layout
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage layouts
     */
    public String doRemoveLayout( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_LAYOUT_ID_LAYOUT ) );
        LayoutHome.remove( nId, getPlugin(  ) );
        
        return JSP_REDIRECT_TO_MANAGE_LAYOUTS;
    }
    
    /**
     * Returns the form to update info about a layout
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyLayout( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_LAYOUT );
        int nId = Integer.parseInt( request.getParameter( PARAMETER_LAYOUT_ID_LAYOUT ) );
        Layout layout =LayoutHome.findByPrimaryKey( nId, getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_LAYOUT, layout );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_LAYOUT, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the change form of a layout
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyLayout( HttpServletRequest request )
    {
      int nId = Integer.parseInt( request.getParameter( PARAMETER_LAYOUT_ID_LAYOUT ) ); 
      Layout layout =LayoutHome.findByPrimaryKey( nId, getPlugin(  ) );
        if ( request.getParameter( PARAMETER_LAYOUT_ID_LAYOUT ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
      int nIdLayout = Integer.parseInt( request.getParameter( PARAMETER_LAYOUT_ID_LAYOUT ) );
			layout.setIdLayout( nIdLayout );
        if ( request.getParameter( PARAMETER_LAYOUT_NAME ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 layout.setName( request.getParameter( PARAMETER_LAYOUT_NAME ) );
        if ( request.getParameter( PARAMETER_LAYOUT_DESCRIPTION ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 layout.setDescription( request.getParameter( PARAMETER_LAYOUT_DESCRIPTION ) );
        if ( request.getParameter( PARAMETER_LAYOUT_LAYOUT ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 layout.setLayout( request.getParameter( PARAMETER_LAYOUT_LAYOUT ) );
        LayoutHome.update( layout, getPlugin(  ) );
        return JSP_REDIRECT_TO_MANAGE_LAYOUTS;
    }
}