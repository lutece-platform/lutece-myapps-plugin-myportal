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
import fr.paris.lutece.plugins.myportal.business.WidgetFilter;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.business.WidgetStatusEnum;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.portal.PortalService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;


/**
 * Widget Service class : retrieve Widget description from a cache
 */
public final class WidgetService extends AbstractCacheableService
{
    private static final String SERVICE_NAME = "MyPortal Widget Service";

    // CACHE
    private static final String CACHE_ESSENTIAL_WIDGETS = "[essential widgets list]";
    private static final String CACHE_NEW_WIDGETS = "[new widgets list]";

    // CONSTANTS
    private static final String COMMA = ",";
    private static final String TRUE = "true";

    // PROPERTIES
    private static final String PROPERTY_ACCEPTED_ICON_FORMATS = "myportal.acceptedIconFormats";
    private static final String PROPERTY_CACHE_WIDGETSERVICE_ENABLE = "myportal.cache.widgetService.enable";
    private static WidgetService _singleton;

    /** Private constructor */
    private WidgetService(  )
    {
        String strCacheEnable = AppPropertiesService.getProperty( PROPERTY_CACHE_WIDGETSERVICE_ENABLE, TRUE );
        boolean bCacheEnable = TRUE.equalsIgnoreCase( strCacheEnable );

        if ( bCacheEnable )
        {
            initCache( getName(  ) );
        }
        else
        {
            PortalService.registerCacheableService( getName(  ), this );
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
    public static synchronized WidgetService instance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new WidgetService(  );
        }

        return _singleton;
    }

    /**
     * Returns widgets description from the cache
     * @param nWidgetId The Widget ID
     * @return The widget object
     */
    public Widget getWidget( int nWidgetId )
    {
        String strKey = getKey( nWidgetId );
        Widget widget = (Widget) getFromCache( strKey );

        if ( widget == null )
        {
            widget = WidgetHome.findByPrimaryKey( nWidgetId );
            putInCache( strKey, widget );
        }

        return widget;
    }

    private String getKey( int nId )
    {
        StringBuilder sbKey = new StringBuilder();
        sbKey.append("[widget:").append(nId).append("]");
        return sbKey.toString();
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
     * Get the image resource
     * @param nWidgetId the id widget
     * @return an {@link ImageResource}
     */
    public ImageResource getIconResource( int nWidgetId )
    {
        String strKey = getIconKey(nWidgetId);
        ImageResource img = (ImageResource) getFromCache( strKey );

        if ( img == null )
        {
            img = WidgetHome.getIconResource( nWidgetId );
            putInCache( strKey, img );
        }

        return img;
    }

    private String getIconKey( int nId )
    {
        StringBuilder sbKey = new StringBuilder();
        sbKey.append("[icon:").append(nId).append("]");
        return sbKey.toString();
    }

    /**
     * Check if the mime type is correct or not (list of correct mime types on
     * the <b>myportal.properties</b>).
     * @param strMimeType the mime type to check
     * @return true if it is correct, false otherwise
     */
    public boolean isIconMimeTypeCorrect( String strMimeType )
    {
        boolean bIsCorrect = false;
        String strAcceptedFormats = AppPropertiesService.getProperty( PROPERTY_ACCEPTED_ICON_FORMATS );

        if ( StringUtils.isNotBlank( strAcceptedFormats ) )
        {
            String[] listAcceptedFormats = strAcceptedFormats.split( COMMA );

            for ( String strFormat : listAcceptedFormats )
            {
                if ( strFormat.equals( strMimeType ) )
                {
                    bIsCorrect = true;

                    break;
                }
            }
        }

        return bIsCorrect;
    }

    /**
     * Get the list of widgets given an id category
     * @param nCategoryId the id category
     * @return a list of {@link Widget}
     */
    public List<Widget> getWidgetsByCategoryId( int nCategoryId )
    {
        String strKey = getCategoryListKey( nCategoryId );
        List<Widget> listWidgets = (List<Widget>) getFromCache( strKey );

        if ( listWidgets == null )
        {
            WidgetFilter wFilter = new WidgetFilter(  );
            wFilter.setIdCategory( nCategoryId );
            wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
            wFilter.setIsWideSearch( false );
            listWidgets = WidgetHome.getWidgetsByFilter( wFilter );
            putInCache( strKey, listWidgets );
        }

        return listWidgets;
    }

    private String getCategoryListKey( int nId )
    {
        StringBuilder sbKey = new StringBuilder();
        sbKey.append("[category widget list:").append(nId).append("]");
        return sbKey.toString();
    }

    /**
     * Get the list of essential widgets
     * @return a list of {@link Widget}
     */
    public List<Widget> getEssentialWidgets(  )
    {
        List<Widget> listWidgets = (List<Widget>) getFromCache( CACHE_ESSENTIAL_WIDGETS );

        if ( listWidgets == null )
        {
            WidgetFilter wFilter = new WidgetFilter(  );
            wFilter.setIsEssential( WidgetFilter.FILTER_TRUE );
            wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
            wFilter.setIsWideSearch( false );
            listWidgets = WidgetHome.getWidgetsByFilter( wFilter );
            putInCache( CACHE_ESSENTIAL_WIDGETS, listWidgets );
        }

        return listWidgets;
    }

    /**
     * Get the list of new widgets
     * @return a list of {@link Widget}
     */
    public List<Widget> getNewWidgets(  )
    {
        List<Widget> listWidgets = (List<Widget>) getFromCache( CACHE_NEW_WIDGETS );

        if ( listWidgets == null )
        {
            WidgetFilter wFilter = new WidgetFilter(  );
            wFilter.setIsNew( WidgetFilter.FILTER_TRUE );
            wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
            wFilter.setIsWideSearch( false );
            listWidgets = WidgetHome.getWidgetsByFilter( wFilter );
            putInCache( CACHE_NEW_WIDGETS, listWidgets );
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
        resetCache(  );
        WidgetHome.create( widget );
    }

    /**
     * Remove a widget. If the cache is enable, it will reset the cache.
     * @param nWidgetId the widget ID
     */
    public void removeWidget( int nWidgetId )
    {
        resetCache(  );
        WidgetContentService.instance(  ).removeCache( nWidgetId );
        WidgetHome.remove( nWidgetId );
    }

    /**
     * Update a widget. If the cache is enable, it will reset the cache.
     * @param widget The {@link Widget}
     * @param bUpdateIcon true if it must also update the icon, false otherwise
     */
    public void updateWidget( Widget widget, boolean bUpdateIcon )
    {
        resetCache(  );
        WidgetContentService.instance(  ).removeCache( widget.getIdWidget(  ) );
        WidgetHome.update( widget, bUpdateIcon );
    }
}
