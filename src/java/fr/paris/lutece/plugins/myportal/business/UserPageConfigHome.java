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
package fr.paris.lutece.plugins.myportal.business;

import fr.paris.lutece.plugins.myportal.service.MyPortalPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 *
 * This class provides instances management methods (create, find, ...) for UserPageConfig objects
 *
 */
public final class UserPageConfigHome
{
    // Static variable pointed at the DAO instance
    private static final String BEAN_MYPORTAL_USERPAGECONFIGDAO = "myportal.userPageConfigDAO";
    private static Plugin _plugin = PluginService.getPlugin( MyPortalPlugin.PLUGIN_NAME );
    private static IUserPageConfigDAO _dao = (IUserPageConfigDAO) SpringContextService.getPluginBean( MyPortalPlugin.PLUGIN_NAME,
            BEAN_MYPORTAL_USERPAGECONFIGDAO );

    /**
     * Private constructor - this class need not be instantiated
     */
    private UserPageConfigHome( )
    {
    }

    /**
     * Create an instance of the userPageConfig class
     * 
     * @param userPageConfig
     *            The instance of the UserPageConfig which contains the informations to store
     * @return The instance of userPageConfig which has been created with its primary key.
     */
    public static UserPageConfig create( UserPageConfig userPageConfig )
    {
        _dao.insert( userPageConfig, _plugin );

        return userPageConfig;
    }

    /**
     * Update of the userPageConfig which is specified in parameter
     * 
     * @param userPageConfig
     *            The instance of the UserPageConfig which contains the data to store
     * @return The instance of the userPageConfig which has been updated
     */
    public static UserPageConfig update( UserPageConfig userPageConfig )
    {
        _dao.store( userPageConfig, _plugin );

        return userPageConfig;
    }

    /**
     * Remove the userPageConfig whose identifier is specified in parameter
     * 
     * @param strUserGuid
     *            The user guid
     */
    public static void remove( String strUserGuid )
    {
        _dao.delete( strUserGuid, _plugin );
    }

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a userPageConfig whose identifier is specified in parameter
     * 
     * @param strUserGuid
     *            The userPageConfig primary key
     * @return an instance of UserPageConfig
     */
    public static UserPageConfig findByPrimaryKey( String strUserGuid )
    {
        return _dao.load( strUserGuid, _plugin );
    }
}
