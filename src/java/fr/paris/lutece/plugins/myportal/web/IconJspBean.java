/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import fr.paris.lutece.plugins.myportal.business.icon.Icon;
import fr.paris.lutece.plugins.myportal.business.icon.IconHome;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.upload.MultipartHttpServletRequest;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.fileupload.FileItem;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * class IconJspBean
 *
 */
public class IconJspBean extends PluginAdminPageJspBean
{
    //	templates
    private static final String TEMPLATE_MANAGE_ICON = "admin/plugins/myportal/manage_icon.html";
    private static final String TEMPLATE_CREATE_ICON = "admin/plugins/myportal/create_icon.html";
    private static final String TEMPLATE_MODIFY_ICON = "admin/plugins/myportal/modify_icon.html";

    //	Markers
    private static final String MARK_ICON_LIST = "icon_list";
    private static final String MARK_ICON = "icon";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";

    //	parameters form
    private static final String PARAMETER_ID_ICON = "id_icon";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_ID_FILE = "id_file";
    private static final String PARAMETER_PAGE_INDEX = "page_index";
    private static final String PARAMETER_WIDTH = "width";
    private static final String PARAMETER_HEIGHT = "height";
    private static final String PARAMETER_CANCEL = "cancel";

    //	 other constants
    private static final String EMPTY_STRING = "";

    //	message
    private static final String MESSAGE_CONFIRM_REMOVE_ICON = "myportal.message.confirm_remove_icon";
    private static final String MESSAGE_MANDATORY_FIELD = "myportal.message.mandatory.field";
    private static final String MESSAGE_NUMERIC_FIELD = "myportal.message.numeric_field";

    //	properties
    private static final String PROPERTY_ITEM_PER_PAGE = "myportal.itemsPerPage";
    private static final String PROPERTY_MANAGE_ICON = "myportal.manage_icon.page_title";
    private static final String PROPERTY_MODIFY_ICON = "myportal.modify_icon.page_title";
    private static final String PROPERTY_CREATE_ICON = "myportal.create_icon.page_title";
    private static final String FIELD_NAME = "myportal.create_icon.label_name";
    private static final String FIELD_WIDTH = "myportal.create_icon.label_width";
    private static final String FIELD_HEIGHT = "myportal.create_icon.label_height";
    private static final String FIELD_FILE = "myportal.create_icon.label_file";

    //Jsp Definition
    private static final String JSP_MANAGE_ICON = "jsp/admin/plugins/myportal/ManageIcons.jsp";
    private static final String JSP_DO_REMOVE_ICON = "jsp/admin/plugins/myportal/DoRemoveIcon.jsp";
    private static final String REGEX_ID = "^[\\d]+$";

    //	session fields
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_ITEM_PER_PAGE, 15 );
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Return management icon ( list of icon )
     * @param request The Http request
     * @return Html management icon
     */
    public String getManageIcon( HttpServletRequest request )
    {
        HashMap model = new HashMap(  );
        List<Icon> listDirectoryXsl = IconHome.getListIcons( getPlugin(  ) );
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        LocalizedPaginator paginator = new LocalizedPaginator( listDirectoryXsl, _nItemsPerPage,
                getJspManageIcon( request ), PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );

        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_NB_ITEMS_PER_PAGE, EMPTY_STRING + _nItemsPerPage );
        model.put( MARK_ICON_LIST, paginator.getPageItems(  ) );
        setPageTitleProperty( PROPERTY_MANAGE_ICON );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_ICON, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Gets the icon creation page
     * @param request The HTTP request
     * @return The directory xsl creation page
     */
    public String getCreateIcon( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_CREATE_ICON );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_ICON, getLocale(  ) );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Perform the icon creation
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doCreateIcon( HttpServletRequest request )
    {
        if ( request.getParameter( PARAMETER_CANCEL ) == null )
        {
            Icon icon = new Icon(  );
            String strError = getIconData( request, icon );

            if ( strError != null )
            {
                return strError;
            }

            IconHome.create( icon, getPlugin(  ) );
        }

        return getJspManageIcon( request );
    }

    /**
     * Gets the icon modification page
     * @param request The HTTP request
     * @throws AccessDeniedException the {@link AccessDeniedException}
     * @return The icon creation page
     */
    public String getModifyIcon( HttpServletRequest request )
        throws AccessDeniedException
    {
        Icon icon;
        String strIdIcon = request.getParameter( PARAMETER_ID_ICON );
        int nIdIcon = convertStringToInt( strIdIcon );
        icon = IconHome.findByPrimaryKey( nIdIcon, getPlugin(  ) );

        if ( ( icon == null ) )
        {
            throw new AccessDeniedException(  );
        }

        HashMap model = new HashMap(  );

        model.put( MARK_ICON, icon );
        setPageTitleProperty( PROPERTY_MODIFY_ICON );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_ICON, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Perform the icon modification
     * @param request The HTTP request
     * @throws AccessDeniedException the {@link AccessDeniedException}
     * @return The URL to go after performing the action
     */
    public String doModifyIcon( HttpServletRequest request )
        throws AccessDeniedException
    {
        if ( request.getParameter( PARAMETER_CANCEL ) == null )
        {
            Icon icon;
            String strIdIcon = request.getParameter( PARAMETER_ID_ICON );
            int nIdIcon = convertStringToInt( strIdIcon );
            icon = IconHome.findByPrimaryKey( nIdIcon, getPlugin(  ) );

            if ( ( icon == null ) )
            {
                throw new AccessDeniedException(  );
            }

            String strError = getIconData( request, icon );

            if ( strError != null )
            {
                return strError;
            }

            if ( icon.getValue(  ) != null )
            {
                IconHome.update( icon, getPlugin(  ) );
            }
            else
            {
                IconHome.updateMetadata( icon, getPlugin(  ) );
            }
        }

        return getJspManageIcon( request );
    }

    /**
     * Gets the confirmation page of delete icon
     * @param request The HTTP request
     * @throws AccessDeniedException the {@link AccessDeniedException}
     * @return the confirmation page of delete directory xsl
     */
    public String getConfirmRemoveIcon( HttpServletRequest request )
        throws AccessDeniedException
    {
        String strIdIcon = request.getParameter( PARAMETER_ID_ICON );
        int nIdIcon = convertStringToInt( strIdIcon );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_ICON );
        url.addParameter( PARAMETER_ID_ICON, strIdIcon );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ICON, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Perform the icon supression
     * @param request The HTTP request
     * @throws AccessDeniedException the {@link AccessDeniedException}
     * @return The URL to go after performing the action
     */
    public String doRemoveIcon( HttpServletRequest request )
        throws AccessDeniedException
    {
        String strIdIcon = request.getParameter( PARAMETER_ID_ICON );
        int nIdIcon = convertStringToInt( strIdIcon );

        IconHome.remove( nIdIcon, getPlugin(  ) );

        return getJspManageIcon( request );
    }

    /**
     * Get the request data and if there is no error insert the data in the icon object specified in parameter.
     * return null if there is no error or else return the error page url
     * @param  request the request
     * @param icon the Icon Object
     * @return null if there is no error or else return the error page url
     */
    private String getIconData( HttpServletRequest request, Icon icon )
    {
        String strError = EMPTY_STRING;
        String strName = request.getParameter( PARAMETER_NAME );
        String strWidth = request.getParameter( PARAMETER_WIDTH );
        String strHeight = request.getParameter( PARAMETER_HEIGHT );

        int nWidth = convertStringToInt( strWidth );
        int nHeight = convertStringToInt( strHeight );

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        FileItem fileItem = multipartRequest.getFile( PARAMETER_ID_FILE );

        if ( ( strName == null ) || strName.trim(  ).equals( EMPTY_STRING ) )
        {
            strError = FIELD_NAME;
        }
        else if ( ( icon.getValue(  ) == null ) &&
                ( ( fileItem == null ) ||
                ( ( fileItem.getName(  ) == null ) && EMPTY_STRING.equals( fileItem.getName(  ) ) ) ) )
        {
            strError = FIELD_FILE;
        }

        //Mandatory fields
        if ( !strError.equals( EMPTY_STRING ) )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( strError, getLocale(  ) ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        if ( ( strWidth != null ) && ( !strWidth.trim(  ).equals( EMPTY_STRING ) ) && ( nWidth == -1 ) )
        {
            strError = FIELD_WIDTH;
        }
        else if ( ( strHeight != null ) && ( !strHeight.trim(  ).equals( EMPTY_STRING ) ) && ( nHeight == -1 ) )
        {
            strError = FIELD_HEIGHT;
        }

        if ( !strError.equals( EMPTY_STRING ) )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( strError, getLocale(  ) ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_NUMERIC_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        icon.setName( strName );

        if ( ( fileItem != null ) && ( fileItem.getName(  ) != null ) && !EMPTY_STRING.equals( fileItem.getName(  ) ) )
        {
            icon.setValue( fileItem.get(  ) );
            icon.setMimeType( fileItem.getContentType(  ) );
        }
        else
        {
            icon.setValue( null );
        }

        icon.setWidth( nWidth );
        icon.setHeight( nHeight );

        return null;
    }

    /**
     * return the url of manage export format
     * @param request the request
     * @return the url of manage export format
     */
    private String getJspManageIcon( HttpServletRequest request )
    {
        return AppPathService.getBaseUrl( request ) + JSP_MANAGE_ICON;
    }

    /**
     * convert a string to int
     *
     * @param strParameter
     * the string parameter to convert
     * @return the conversion
     */
    public static int convertStringToInt( String strParameter )
    {
        int nIdParameter = -1;

        try
        {
            if ( ( strParameter != null ) && strParameter.matches( REGEX_ID ) )
            {
                nIdParameter = Integer.parseInt( strParameter );
            }
        }
        catch ( NumberFormatException ne )
        {
            AppLogService.error( ne );
        }

        return nIdParameter;
    }
}
