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

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandler;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandlerService;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;
import fr.paris.lutece.portal.service.cache.CacheService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * WidgetContentService store widget content into a cache
 */
public final class WidgetContentService extends AbstractCacheableService
{
    private static final String SERVICE_NAME = "MyPortal Widget Content Service";

    // CONSTANTS
    private static final String TRUE = "true";

    // CACHES
    private static final String CACHE_WIDGET = "[widget:";
    private static final String CACHE_END = "]";

    // PROPERTIES
    private static final String PROPERTY_CACHE_WIDGETCONTENTSERVICE_ENABLE = "myportal.cache.widgetContentService.enable";
    private static WidgetContentService _singleton;

    /** Private constructor */
    private WidgetContentService(  )
    {
    }

    /**
     * {@inheritDoc }
     */
    public String getName(  )
    {
        return SERVICE_NAME;
    }

    /**
     * Gets the unique instance of the service
     * @return The unique instance
     */
    public static synchronized WidgetContentService instance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new WidgetContentService(  );
        }

        return _singleton;
    }

    /**
     * Init service
     */
    public void init(  )
    {
        String strCacheEnable = AppPropertiesService.getProperty( PROPERTY_CACHE_WIDGETCONTENTSERVICE_ENABLE, TRUE );
        boolean bCacheEnable = TRUE.equalsIgnoreCase( strCacheEnable );

        if ( bCacheEnable )
        {
            initCache( getName(  ) );
        }
        else
        {
            CacheService.registerCacheableService( this );
        }
    }

    /**
     * Get the widget content
     * @param nWidgetId The Widget ID
     * @param user The Lutece user
     * @param request {@link HttpServletRequest}
     * @return The widget Content
     */
    public String getWidgetContent( int nWidgetId, LuteceUser user, HttpServletRequest request )
    {
        String strKey = getKey( nWidgetId );
        Map<String, String> mapWidgetContent = (Map<String, String>) getFromCache( strKey );
        String strWidget = StringUtils.EMPTY;

        if ( mapWidgetContent == null )
        {
            mapWidgetContent = new HashMap<String, String>(  );

            Widget widget = WidgetHome.findByPrimaryKey( nWidgetId );
            String strType = widget.getWidgetType(  );
            WidgetHandler handler = WidgetHandlerService.instance(  ).getHandler( strType );
            strWidget = handler.renderWidget( widget, user, request );
            mapWidgetContent.put( user.getName(  ), strWidget );
            putInCache( strKey, mapWidgetContent );
        }
        else
        {
            strWidget = mapWidgetContent.get( user.getName(  ) );

            if ( strWidget == null )
            {
                removeCache( nWidgetId );

                Widget widget = WidgetHome.findByPrimaryKey( nWidgetId );
                String strType = widget.getWidgetType(  );
                WidgetHandler handler = WidgetHandlerService.instance(  ).getHandler( strType );
                strWidget = handler.renderWidget( widget, user, request );
                mapWidgetContent.put( user.getName(  ), strWidget );
                putInCache( strKey, mapWidgetContent );
            }
        }

        return strWidget;
    }

    /**
     * Remove the cache by a given name
     * @param nId the name of the cache to remove
     */
    public void removeCache( int nId )
    {
        try
        {
            if ( isCacheEnable(  ) && ( getCache(  ) != null ) )
            {
                String strKey = getKey( nId );
                getCache(  ).remove( strKey );
            }
        }
        catch ( IllegalStateException e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }
    }

    /**
     * Get the cache key for a widget
     * @param nId the id
     * @return the key
     */
    private String getKey( int nId )
    {
        StringBuilder sbKey = new StringBuilder(  );
        sbKey.append( CACHE_WIDGET );
        sbKey.append( nId );
        sbKey.append( CACHE_END );

        return sbKey.toString(  );
    }
}
