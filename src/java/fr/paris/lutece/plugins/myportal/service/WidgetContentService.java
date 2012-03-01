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

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.service.cache.WidgetContentCacheService;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandler;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandlerService;
import fr.paris.lutece.portal.service.cache.CacheService;
import fr.paris.lutece.portal.service.cache.ICacheKeyService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.PortalJspBean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * WidgetContentService store widget content into a cache
 */
public class WidgetContentService
{
    // CONSTANTS
    private static final String TRUE = "true";

    // CACHE KEYS
    private static final String KEY_WIDGET = "widget";

    // PROPERTIES
    private static final String PROPERTY_CACHE_WIDGETCONTENTSERVICE_ENABLE = "myportal.cache.widgetContentService.enable";

    // VARIABLES
    private ICacheKeyService _cksWidgetContent;
    private WidgetContentCacheService _cacheWidgetContent = WidgetContentCacheService.getInstance(  );

    /**
     * Constructor
     */
    public WidgetContentService(  )
    {
        init(  );
    }

    /**
     * Init service
     */
    private void init(  )
    {
        String strCacheEnable = AppPropertiesService.getProperty( PROPERTY_CACHE_WIDGETCONTENTSERVICE_ENABLE, TRUE );
        boolean bCacheEnable = TRUE.equalsIgnoreCase( strCacheEnable );

        if ( bCacheEnable )
        {
            _cacheWidgetContent.initCache(  );
        }
        else
        {
            CacheService.registerCacheableService( _cacheWidgetContent );
        }
    }

    /**
     * Get the widget content
     * @param nIdWidget The Widget ID
     * @param user The Lutece user
     * @param request {@link HttpServletRequest}
     * @return The widget Content
     */
    public String getWidgetContent( int nIdWidget, LuteceUser user, HttpServletRequest request )
    {
        Widget widget = WidgetHome.findByPrimaryKey( nIdWidget );
        String strType = widget.getWidgetType(  );
        WidgetHandler handler = WidgetHandlerService.instance(  ).getHandler( strType );
        String strKey = handler.isCustomizable(  ) ? getKey( nIdWidget, user ) : getKey( nIdWidget );
        String widgetContent = (String) _cacheWidgetContent.getFromCache( strKey );

        if ( widgetContent == null )
        {
            widgetContent = handler.renderWidget( widget, user, request );
            _cacheWidgetContent.putInCache( strKey, widgetContent );
        }

        return widgetContent;
    }

    /**
     * Remove the cache by a given name
     * @param nId the name of the cache to remove
     */
    public void removeCache( int nId )
    {
        removeCache( nId, null );
    }

    /**
     * Remove the cache by a given name
     * @param nId the name of the cache to remove
     * @param user the {@link LuteceUser}
     */
    public void removeCache( int nId, LuteceUser user )
    {
        String strKey = ( user != null ) ? getKey( nId, user ) : getKey( nId );
        _cacheWidgetContent.removeCache( strKey );
    }

    /**
     * Set the cache key service
     * @param cacheKeyService the _cacheKeyService to set
     */
    public void setWidgetContentCacheKeyService( ICacheKeyService cacheKeyService )
    {
        _cksWidgetContent = cacheKeyService;
    }

    /**
     * Get the cache key for a widget
     * @param nId the id
     * @param user the {@link LuteceUser}
     * @return the key
     */
    private String getKey( int nId, LuteceUser user )
    {
        Map<String, String> mapParams = new HashMap<String, String>(  );
        mapParams.put( KEY_WIDGET, Integer.toString( nId ) );

        return _cksWidgetContent.getKey( mapParams, PortalJspBean.MODE_HTML, user );
    }

    /**
     * Get the cache key for a widget
     * @param nId the id
     * @return the key
     */
    private String getKey( int nId )
    {
        return getKey( nId, null );
    }
}
