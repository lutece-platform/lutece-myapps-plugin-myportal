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
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetFilter;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.business.WidgetStatusEnum;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;
import fr.paris.lutece.plugins.myportal.service.cache.WidgetCacheService;
import fr.paris.lutece.portal.service.cache.CacheService;
import fr.paris.lutece.portal.service.cache.ICacheKeyService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.PortalJspBean;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Widget Service class : retrieve Widget description from a cache
 */
public class WidgetService
{
    // CACHE KEYS
    private static final String KEY_ESSENTIAL_WIDGETS = "essential_widgets";
    private static final String KEY_NEW_WIDGETS = "new_widgets";
    private static final String KEY_WIDGET = "widget";
    private static final String KEY_ICON = "icon";
    private static final String KEY_CATEGORY_WIDGET_LIST = "category_widgets";

    // CONSTANTS
    private static final String COMMA = ",";
    private static final String TRUE = "true";
    private static final String NONE = "-";

    // PROPERTIES
    private static final String PROPERTY_ACCEPTED_ICON_FORMATS = "myportal.acceptedIconFormats";
    private static final String PROPERTY_CACHE_WIDGETSERVICE_ENABLE = "myportal.cache.widgetService.enable";

    // VARIABLES
    private ICacheKeyService _cksWidget;
    private ICacheKeyService _cksIcon;
    private ICacheKeyService _cksEssentialWidgets;
    private ICacheKeyService _cksNewWidgets;
    private ICacheKeyService _cksCategoryWidgets;
    private WidgetContentService _widgetContentService;
    private WidgetCacheService _cacheWidget = WidgetCacheService.getInstance(  );

    /**
     * constructor
     */
    public WidgetService(  )
    {
        init(  );
    }

    /**
     * Init Service
     */
    private void init(  )
    {
        String strCacheEnable = AppPropertiesService.getProperty( PROPERTY_CACHE_WIDGETSERVICE_ENABLE, TRUE );
        boolean bCacheEnable = TRUE.equalsIgnoreCase( strCacheEnable );

        if ( bCacheEnable )
        {
            _cacheWidget.initCache(  );
        }
        else
        {
            CacheService.registerCacheableService( _cacheWidget );
        }
    }

    /**
     * Returns widgets description from the cache
     * @param nWidgetId The Widget ID
     * @return The widget object
     */
    public Widget getWidget( int nWidgetId )
    {
        String strKey = getKey( nWidgetId );
        Widget widget = (Widget) _cacheWidget.getFromCache( strKey );

        if ( widget == null )
        {
            widget = WidgetHome.findByPrimaryKey( nWidgetId );
            _cacheWidget.putInCache( strKey, widget );
        }

        return widget;
    }

    /**
     * Load the data of all the widget objects and returns them in form of a collection
     * @return the collection which contains the data of all the widget objects
     */
    public Collection<Widget> getWidgetsList(  )
    {
        return WidgetHome.getWidgetsList(  );
    }


    /**
     * Get the list of widgets given an id category
     * @param nCategoryId the id category
     * @return a list of {@link Widget}
     */
    public List<Widget> getWidgetsByCategoryId( int nCategoryId )
    {
        String strKey = getCategoryListKey( nCategoryId );
        List<Widget> listWidgets = (List<Widget>) _cacheWidget.getFromCache( strKey );

        if ( listWidgets == null )
        {
            WidgetFilter wFilter = new WidgetFilter(  );
            wFilter.setIdCategory( nCategoryId );
            wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
            wFilter.setIsWideSearch( false );
            listWidgets = WidgetHome.getWidgetsByFilter( wFilter );
            _cacheWidget.putInCache( strKey, listWidgets );
        }

        return listWidgets;
    }

    /**
     * Get the list of essential widgets
     * @return a list of {@link Widget}
     */
    public List<Widget> getEssentialWidgets(  )
    {
        String strKey = getEssentialWidgetsKey(  );
        List<Widget> listWidgets = (List<Widget>) _cacheWidget.getFromCache( strKey );

        if ( listWidgets == null )
        {
            WidgetFilter wFilter = new WidgetFilter(  );
            wFilter.setIsEssential( WidgetFilter.FILTER_TRUE );
            wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
            wFilter.setIsWideSearch( false );
            listWidgets = WidgetHome.getWidgetsByFilter( wFilter );
            _cacheWidget.putInCache( strKey, listWidgets );
        }

        return listWidgets;
    }

    /**
     * Get the list of new widgets
     * @return a list of {@link Widget}
     */
    public List<Widget> getNewWidgets(  )
    {
        String strKey = getNewWidgetsKey(  );
        List<Widget> listWidgets = (List<Widget>) _cacheWidget.getFromCache( strKey );

        if ( listWidgets == null )
        {
            WidgetFilter wFilter = new WidgetFilter(  );
            wFilter.setIsNew( WidgetFilter.FILTER_TRUE );
            wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
            wFilter.setIsWideSearch( false );
            listWidgets = WidgetHome.getWidgetsByFilter( wFilter );
            _cacheWidget.putInCache( strKey, listWidgets );
        }

        return listWidgets;
    }

    /**
     * Get the list of widgets given a name
     * @param strName the name
     * @return a list of {@link Widget}
     */
    public List<Widget> getWidgetsByName( String strName )
    {
        WidgetFilter wFilter = new WidgetFilter(  );
        wFilter.setName( strName );
        wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
        wFilter.setIsWideSearch( false );

        return WidgetHome.getWidgetsByFilter( wFilter );
    }

    /**
     * Create a new widget. If the cache is enable, it will reset the cache.
     * @param widget The {@link Widget}
     */
    public void createWidget( Widget widget )
    {
        _cacheWidget.resetCache(  );
        WidgetHome.create( widget );
    }

    /**
     * Remove a widget. If the cache is enable, it will reset the cache.
     * @param nWidgetId the widget ID
     */
    public void removeWidget( int nWidgetId )
    {
        _cacheWidget.resetCache(  );
        _widgetContentService.removeCache( nWidgetId );
        WidgetHome.remove( nWidgetId );
    }

    /**
     * Update a widget. If the cache is enable, it will reset the cache.
     * @param widget The {@link Widget}
     */
    public void updateWidget( Widget widget )
    {
        _cacheWidget.resetCache(  );
        _widgetContentService.removeCache( widget.getIdWidget(  ) );
        WidgetHome.update( widget );
    }

    /**
     * Get the widget IDs from a given LuteceUser
     * @param user the {@link LuteceUser}
     * @return a list of widget IDs
     */
    public List<Integer> getUserWidgetIds( LuteceUser user )
    {
        List<Integer> listWidgetIds = new ArrayList<Integer>(  );
        UserPageConfig userConf = UserPageConfigHome.findByPrimaryKey( user.getName(  ) );

        if ( userConf != null )
        {
            PageConfig pageConfig = PageConfigJsonUtil.parseJson( userConf.getUserPageConfig(  ) );

            for ( TabConfig tabConfig : pageConfig.getTabList(  ) )
            {
                for ( WidgetConfig widgetConfig : tabConfig.getWidgetList(  ) )
                {
                    listWidgetIds.add( widgetConfig.getWidgetId(  ) );
                }
            }
        }

        return listWidgetIds;
    }

    /**
     * Get the list of public or mandatory widgets
     * @return the list widgets
     */
    public List<Widget> getPublicMandatoryWidgets(  )
    {
        return WidgetHome.getPublicMandatoryWidgets(  );
    }

    // SETTERS
    /**
     * Set the cache key service
     * @param cacheKeyService the _cacheKeyService to set
     */
    public void setWidgetCacheKeyService( ICacheKeyService cacheKeyService )
    {
        _cksWidget = cacheKeyService;
    }

    /**
     * Set the cache key service
     * @param cacheKeyService the _cacheKeyService to set
     */
    public void setIconCacheKeyService( ICacheKeyService cacheKeyService )
    {
        _cksIcon = cacheKeyService;
    }

    /**
     * Set the cache key service
     * @param cacheKeyService the _cacheKeyService to set
     */
    public void setEssentialWidgetsCacheKeyService( ICacheKeyService cacheKeyService )
    {
        _cksEssentialWidgets = cacheKeyService;
    }

    /**
     * Set the cache key service
     * @param cacheKeyService the _cacheKeyService to set
     */
    public void setNewWidgetsCacheKeyService( ICacheKeyService cacheKeyService )
    {
        _cksNewWidgets = cacheKeyService;
    }

    /**
     * Set the cache key service
     * @param cacheKeyService the _cacheKeyService to set
     */
    public void setCategoryWidgetsCacheKeyService( ICacheKeyService cacheKeyService )
    {
        _cksCategoryWidgets = cacheKeyService;
    }

    /**
     * Set the widget content service
     * @param widgetContentService the widget content service
     */
    public void setWidgetContentService( WidgetContentService widgetContentService )
    {
        _widgetContentService = widgetContentService;
    }

    // BUILD CACHE KEYS

    /**
     * Get the cache key for the widget
     * @param nId the id
     * @return the key
     */
    private String getKey( int nId )
    {
        Map<String, String> mapParams = new HashMap<String, String>(  );
        mapParams.put( KEY_WIDGET, Integer.toString( nId ) );

        return _cksWidget.getKey( mapParams, PortalJspBean.MODE_HTML, null );
    }

    /**
     * Get the cache key for the icon
     * @param nId the id
     * @return the key
     */
    private String getIconKey( int nId )
    {
        Map<String, String> mapParams = new HashMap<String, String>(  );
        mapParams.put( KEY_ICON, Integer.toString( nId ) );

        return _cksIcon.getKey( mapParams, PortalJspBean.MODE_HTML, null );
    }

    /**
     * Get the cache key for the category list
     * @param nId the id
     * @return the key
     */
    private String getCategoryListKey( int nId )
    {
        Map<String, String> mapParams = new HashMap<String, String>(  );
        mapParams.put( KEY_CATEGORY_WIDGET_LIST, Integer.toString( nId ) );

        return _cksCategoryWidgets.getKey( mapParams, PortalJspBean.MODE_HTML, null );
    }

    /**
     * Get the cache key for the list of essential widgets
     * @return the key
     */
    private String getEssentialWidgetsKey(  )
    {
        Map<String, String> mapParams = new HashMap<String, String>(  );
        mapParams.put( KEY_ESSENTIAL_WIDGETS, NONE );

        return _cksEssentialWidgets.getKey( mapParams, PortalJspBean.MODE_HTML, null );
    }

    /**
     * Get the cache key for the list of new widgets
     * @return the key
     */
    private String getNewWidgetsKey(  )
    {
        Map<String, String> mapParams = new HashMap<String, String>(  );
        mapParams.put( KEY_NEW_WIDGETS, NONE );

        return _cksNewWidgets.getKey( mapParams, PortalJspBean.MODE_HTML, null );
    }
}
