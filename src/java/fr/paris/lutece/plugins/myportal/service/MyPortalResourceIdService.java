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

import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;

/**
 *
 * MyPortalResourceIdService
 *
 */
public class MyPortalResourceIdService extends ResourceIdService
{
    public static final String RESOURCE_TYPE = "MYPORTAL";
    public static final String PERMISSION_MANAGE_ADVANCED_PARAMETERS = "MANAGE_ADVANCED_PARAMETERS";
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "myportal.permission.label.resource_type_myportal";
    private static final String PROPERTY_LABEL_MANAGE_ADVANCED_PARAMETERS = "myportal.permission.label.manage_advanced_parameters";

    /**
     * Constructor
     */
    public MyPortalResourceIdService( )
    {
        setPluginName( MyPortalPlugin.PLUGIN_NAME );
    }

    /**
     * Initializes the service
     */
    public void register( )
    {
        ResourceType rt = new ResourceType( );
        rt.setResourceIdServiceClass( MyPortalResourceIdService.class.getName( ) );
        rt.setPluginName( MyPortalPlugin.PLUGIN_NAME );
        rt.setResourceTypeKey( RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = new Permission( );
        p.setPermissionKey( PERMISSION_MANAGE_ADVANCED_PARAMETERS );
        p.setPermissionTitleKey( PROPERTY_LABEL_MANAGE_ADVANCED_PARAMETERS );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

    /**
     * Returns a list of resource ids
     *
     * @param locale
     *            The current locale
     * @return A list of resource ids
     */
    public ReferenceList getResourceIdList( Locale locale )
    {
        return null;
    }

    /**
     * Returns the Title of a given resource
     *
     * @param strId
     *            The Id of the resource
     * @param locale
     *            The current locale
     * @return The Title of a given resource
     */
    public String getTitle( String strId, Locale locale )
    {
        return StringUtils.EMPTY;
    }
}
