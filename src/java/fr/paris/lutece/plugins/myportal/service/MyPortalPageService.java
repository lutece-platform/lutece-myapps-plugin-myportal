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
package fr.paris.lutece.plugins.myportal.service;

import fr.paris.lutece.plugins.myportal.business.UserPageConfig;
import fr.paris.lutece.plugins.myportal.business.UserPageConfigHome;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 *
 * MyPortalPageService
 *
 */
public class MyPortalPageService
{
    private static final String BEAN_MYPORTAL_PAGEBUILDER = "myportal.pageBuilder";
    private static final String DEFAULT_GUID = "default";
    private IPageBuilder _pageBuilder = (IPageBuilder) SpringContextService.getPluginBean( MyPortalPlugin.PLUGIN_NAME,
            BEAN_MYPORTAL_PAGEBUILDER );

    /**
     * Gets the page for a given user
     * @param user The user
     * @return The page
     */
    public String getUserPage( LuteceUser user )
    {
        PageConfig pageConfig = getPageConfigUser( user );

        if ( pageConfig == null )
        {
            pageConfig = getDefaultPageConfig(  );
        }

        return _pageBuilder.buildPage( pageConfig, user );
    }

    /**
     * Get the page config of the given user
     * @param user a {@link LuteceUser}
     * @return a {@link PageConfig}
     */
    private PageConfig getPageConfigUser( LuteceUser user )
    {
        UserPageConfig userConf = UserPageConfigHome.findByPrimaryKey( user.getName(  ) );

        if ( userConf == null )
        {
            userConf = UserPageConfigHome.findByPrimaryKey( DEFAULT_GUID );
            userConf.setUserGuid( user.getName(  ) );
            UserPageConfigHome.create( userConf );
        }

        return PageConfigJsonUtil.parseJson( userConf.getUserPageConfig(  ) );
    }

    /**
     * Get the default page config
     * @return a {@link PageConfig}
     */
    private PageConfig getDefaultPageConfig(  )
    {
        UserPageConfig userConf = UserPageConfigHome.findByPrimaryKey( DEFAULT_GUID );

        return PageConfigJsonUtil.parseJson( userConf.getUserPageConfig(  ) );
    }

    /**
     * Add a widget to a page of an user
     * @param user The user
     * @param nIdWidget The widget ID
     * @param nTabIndex The tab index
     * @param nColumn The column
     */
    public void addWidget( LuteceUser user, int nIdWidget, int nTabIndex, int nColumn )
    {
        PageConfig pageConfig = getPageConfigUser( user );
        TabConfig tab = null;

        int nIndex = 1;

        for ( TabConfig tabConfig : pageConfig.getTabList(  ) )
        {
            if ( nIndex == nTabIndex )
            {
                tab = tabConfig;

                break;
            }

            nIndex++;
        }

        if ( tab != null )
        {
            List<WidgetConfig> listWidgets = tab.getWidgetList(  );
            WidgetConfig widget = new WidgetConfig(  );
            widget.setWidgetId( nIdWidget );
            widget.setColumn( nColumn );
            listWidgets.add( widget );
            updateConfig( user, pageConfig );
        }
    }

    /**
     * Remove a widget from an user's page
     * @param user The user
     * @param nIdWidget The widget ID
     */
    public void removeWidget( LuteceUser user, int nIdWidget )
    {
        PageConfig pageConfig = getPageConfigUser( user );

        for ( TabConfig tab : pageConfig.getTabList(  ) )
        {
            List<WidgetConfig> listWidgets = tab.getWidgetList(  );

            for ( int i = 0; i < listWidgets.size(  ); i++ )
            {
                WidgetConfig widget = listWidgets.get( i );

                if ( widget.getWidgetId(  ) == nIdWidget )
                {
                    listWidgets.remove( i );
                    updateConfig( user, pageConfig );

                    return;
                }
            }
        }
    }

    /**
     * Update an user page config
     * @param user the user
     * @param pageConfig the page config
     */
    private void updateConfig( LuteceUser user, PageConfig pageConfig )
    {
        UserPageConfig userConf = new UserPageConfig(  );
        userConf.setUserGuid( user.getName(  ) );
        userConf.setUserPageConfig( PageConfigJsonUtil.buildJson( pageConfig ) );
        UserPageConfigHome.update( userConf );
    }

    /**
     * Set a page config to the given user
     * @param usr the {@link LuteceUser}
     * @param strUserPageConfig the user page config
     */
    public void setPageConfigUser( LuteceUser usr, String strUserPageConfig )
    {
        UserPageConfig userPageConfig = new UserPageConfig(  );
        userPageConfig.setUserGuid( usr.getName(  ) );
        userPageConfig.setUserPageConfig( strUserPageConfig );
        UserPageConfigHome.update( userPageConfig );
    }

    /**
     * Get the list of tabs of the given user
     * @param user the {@link LuteceUser}
     * @return a list of {@link TabConfig}
     */
    public List<TabConfig> getTabList( LuteceUser user )
    {
        PageConfig pageConfig = getPageConfigUser( user );

        return pageConfig.getTabList(  );
    }

    /**
     * Add a new tab to the given user
     * @param user the {@link LuteceUser}
     * @param strTabName the name of the new tab
     */
    public void addTab( LuteceUser user, String strTabName )
    {
        PageConfig pageConfig = getPageConfigUser( user );
        TabConfig tab = new TabConfig(  );
        tab.setName( strTabName );
        pageConfig.getTabList(  ).add( tab );
        updateConfig( user, pageConfig );
    }
}
