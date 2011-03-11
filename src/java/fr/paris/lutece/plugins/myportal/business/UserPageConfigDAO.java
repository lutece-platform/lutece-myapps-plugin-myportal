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
package fr.paris.lutece.plugins.myportal.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 *
 * This class provides Data Access methods for UserPageConfig objects
 *
 */
public final class UserPageConfigDAO implements IUserPageConfigDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT user_guid, user_pageconfig FROM myportal_user_pageconfig WHERE user_guid = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO myportal_user_pageconfig ( user_guid, user_pageconfig ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM myportal_user_pageconfig WHERE user_guid = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE myportal_user_pageconfig SET user_pageconfig = ? WHERE user_guid = ?";

    /**
     * Insert a new record in the table.
     * @param userPageConfig instance of the UserPageConfig object to insert
     * @param plugin The plugin
     */
    public void insert( UserPageConfig userPageConfig, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setString( 1, userPageConfig.getUserGuid(  ) );
        daoUtil.setString( 2, userPageConfig.getUserPageConfig(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the userPageConfig from the table
     * @param strUserGuid The identifier of the userPageConfig
     * @param plugin The plugin
     * @return the instance of the UserPageConfig
     */
    public UserPageConfig load( String strUserGuid, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString( 1, strUserGuid );
        daoUtil.executeQuery(  );

        UserPageConfig userPageConfig = null;

        if ( daoUtil.next(  ) )
        {
            userPageConfig = new UserPageConfig(  );

            userPageConfig.setUserGuid( daoUtil.getString( 1 ) );
            userPageConfig.setUserPageConfig( daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return userPageConfig;
    }

    /**
     * Delete a record from the table
     * @param nUserPageConfigId The identifier of the userPageConfig
     * @param plugin The plugin
     */
    public void delete( int nUserPageConfigId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nUserPageConfigId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param userPageConfig The reference of the userPageConfig
     * @param plugin The plugin
     */
    public void store( UserPageConfig userPageConfig, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, userPageConfig.getUserPageConfig(  ) );
        daoUtil.setString( 2, userPageConfig.getUserGuid(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
