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


/**
 * WidgetContentService store widget content into a cache
 */
public final class WidgetContentService extends AbstractCacheableService
{
    private static final String SERVICE_NAME = "MyPortal Widget Content Service";

    // CONSTANTS
    private static final String TRUE = "true";

    // PROPERTIES
    private static final String PROPERTY_CACHE_WIDGETCONTENTSERVICE_ENABLE = "myportal.cache.widgetContentService.enable";
    private static WidgetContentService _singleton;

    /** Private constructor */
    private WidgetContentService(  )
    {
        String strCacheEnable = AppPropertiesService.getProperty( PROPERTY_CACHE_WIDGETCONTENTSERVICE_ENABLE, TRUE );
        boolean bCacheEnable = TRUE.equalsIgnoreCase( strCacheEnable );

        if ( bCacheEnable )
        {
            initCache( getName(  ) );
        }
        else
        {
            CacheService.registerCacheableService( getName(  ), this );
        }
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
     * Get the widget content
     * @param nWidgetId The Widget ID
     * @param user The Lutece user
     * @return The widget Content
     */
    public String getWidgetContent( int nWidgetId, LuteceUser user )
    {
        String strWidgetId = Integer.toString( nWidgetId );
        String strWidget = (String) getFromCache( strWidgetId );

        if ( strWidget == null )
        {
            Widget widget = WidgetHome.findByPrimaryKey( nWidgetId );
            String strType = widget.getWidgetType(  );
            WidgetHandler handler = WidgetHandlerService.instance(  ).getHandler( strType );
            strWidget = handler.renderWidget( widget.getConfigData(  ), user );
            putInCache( strWidgetId, strWidget );
        }

        return strWidget;
    }

    /**
     * Remove the cache by a given name
     * @param strName the name of the cache to remove
     */
    public void removeCache( String strName )
    {
        try
        {
            getCache(  ).remove( strName );
        }
        catch ( IllegalStateException e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }
    }
}
