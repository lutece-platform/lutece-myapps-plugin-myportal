/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
import fr.paris.lutece.plugins.myportal.business.WidgetComponent;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * MyPortalPageService
 *
 */
public final class MyPortalPageService
{
    // PROPERTIES
    private static final String PROPERTY_PAGECONFIG_NAME = "myportal.defaultPageBuilder.pageConfig.name";
    private static final String PROPERTY_TABCONFIG_NAME = "myportal.defaultPageBuilder.tabConfig.name";
    private static final String BEAN_MYPORTAL_PAGEBUILDER = "myportal.pageBuilder";
    private static MyPortalPageService _singleton;
    private IPageBuilder _pageBuilder = (IPageBuilder) SpringContextService.getPluginBean( MyPortalPlugin.PLUGIN_NAME,
            BEAN_MYPORTAL_PAGEBUILDER );

    /**
     * Constructor
     */
    private MyPortalPageService(  )
    {
    }

    /**
     * Get the instance of MyPortalPageService
     * @return the instance of MyPortalPageService
     */
    public static MyPortalPageService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new MyPortalPageService(  );
        }

        return _singleton;
    }

    /**
     * Gets the page for a given user
     * @param user The user
     * @param request {@link HttpServletRequest}
     * @return The page
     */
    public String getUserPage( LuteceUser user, HttpServletRequest request )
    {
        PageConfig pageConfig = getPageConfigUser( user );

        return _pageBuilder.buildPage( pageConfig, user, request );
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
            int nNbColumns = DefaultPageBuilderService.getInstance(  ).getColumnCount(  );
            PageConfig pageConfig = new PageConfig(  );
            pageConfig.setName( AppPropertiesService.getProperty( PROPERTY_PAGECONFIG_NAME ) );

            List<TabConfig> listTabConfigs = new ArrayList<TabConfig>(  );
            TabConfig tabConfig = new TabConfig(  );
            tabConfig.setName( AppPropertiesService.getProperty( PROPERTY_TABCONFIG_NAME ) );

            List<WidgetConfig> listWidgetConfigs = new ArrayList<WidgetConfig>(  );

            for ( int nColumn = 1; nColumn <= nNbColumns; nColumn++ )
            {
                List<WidgetComponent> listWidgetComponents = DefaultPageBuilderService.getInstance(  )
                                                                                      .getWidgetComponents( nColumn );

                for ( WidgetComponent widgetComponent : listWidgetComponents )
                {
                    WidgetConfig widgetConfig = new WidgetConfig(  );
                    widgetConfig.setWidgetId( widgetComponent.getIdWidget(  ) );
                    widgetConfig.setColumn( nColumn );
                    listWidgetConfigs.add( widgetConfig );
                }
            }

            tabConfig.setWidgetList( listWidgetConfigs );
            listTabConfigs.add( tabConfig );
            pageConfig.setTabList( listTabConfigs );
            userConf = new UserPageConfig(  );
            userConf.setUserGuid( user.getName(  ) );
            userConf.setUserPageConfig( PageConfigJsonUtil.buildJson( pageConfig ) );

            /*
            userConf = UserPageConfigHome.findByPrimaryKey( DEFAULT_GUID );
            userConf.setUserGuid( user.getName(  ) );
             */
            UserPageConfigHome.create( userConf );
        }

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

    /**
     * change tab name
     * @param user the {@link LuteceUser}
     * @param strTabNewName the new name of the tab
     * @param nIdTab the id tab
     */
    public void editTab( LuteceUser user, String strTabNewName, int nIdTab )
    {
        PageConfig pageConfig = getPageConfigUser( user );
        List<TabConfig> listTabs = pageConfig.getTabList(  );
        TabConfig tabConfig = listTabs.get( nIdTab - 1 );
        tabConfig.setName( strTabNewName );

        String strJson = PageConfigJsonUtil.buildJson( pageConfig );
        setPageConfigUser( user, strJson );
    }

    /**
     * del tab name
     * @param user the {@link LuteceUser}
     * @param nIdTab the id tab
     */
    public void delTab( LuteceUser user, int nIdTab )
    {
        PageConfig pageConfig = getPageConfigUser( user );
        List<TabConfig> listTabs = pageConfig.getTabList(  );
        listTabs.remove( nIdTab - 1 );

        String strJson = PageConfigJsonUtil.buildJson( pageConfig );
        setPageConfigUser( user, strJson );
    }

    /**
     * Edit a widget
     * @param user the {@link LuteceUser}
     * @param nIdTab the ID tab
     * @param nIdWidget the ID widget
     * @param nColumn the column
     */
    public void editWidget( LuteceUser user, int nIdTab, int nIdWidget, int nColumn )
    {
        PageConfig pageConfig = getPageConfigUser( user );
        List<TabConfig> listTabs = pageConfig.getTabList(  );
        TabConfig tabConfig = listTabs.get( nIdTab - 1 );
        List<WidgetConfig> listWidgets = tabConfig.getWidgetList(  );

        for ( int i = 0; i < listWidgets.size(  ); i++ )
        {
            WidgetConfig widget = listWidgets.get( i );

            if ( widget.getWidgetId(  ) == nIdWidget )
            {
                listWidgets.remove( i );

                break;
            }
        }

        WidgetConfig widgetConfig = new WidgetConfig(  );
        widgetConfig.setWidgetId( nIdWidget );
        widgetConfig.setColumn( nColumn );
        listWidgets.add( widgetConfig );

        String strJson = PageConfigJsonUtil.buildJson( pageConfig );
        setPageConfigUser( user, strJson );
    }
}
