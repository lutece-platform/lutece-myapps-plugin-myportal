/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import java.util.List;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetFilter;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.business.WidgetStatusEnum;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;
import fr.paris.lutece.portal.service.cache.CacheService;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * Widget Service class : retrieve Widget description from a cache
 */
public class WidgetService extends AbstractCacheableService
{
    private static final String SERVICE_NAME = "MyPortal Widget Service";

    // PREFIX
    private static final String PREFIX_ICON_RESOURCE = "icon_resource_";
    private static final String PREFIX_WIDGETS_BY_CATEGORIES = "widgets_by_categories_";
    private static final String PREFIX_ESSENTIAL_WIDGETS = "essential_widgets";
    private static final String PREFIX_NEW_WIDGETS = "new_widgets";
    
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
        String strWidgetId = Integer.toString( nWidgetId );
        Widget widget = (Widget) getFromCache( strWidgetId );

        if ( widget == null )
        {
        	Plugin plugin = PluginService.getPlugin( MyPortalPlugin.PLUGIN_NAME );
            widget = WidgetHome.findByPrimaryKey( nWidgetId, plugin );
            putInCache( strWidgetId, widget );
        }

        return widget;
    }
    
    /**
     * Get the image resource
     * @param nWidgetId the id widget
     * @param plugin {@link Plugin}
     * @return an {@link ImageResource}
     */
    public ImageResource getIconResource( int nWidgetId, Plugin plugin )
    {
    	ImageResource img = (ImageResource) getFromCache( PREFIX_ICON_RESOURCE + nWidgetId );
    	if ( img == null )
    	{
    		img = WidgetHome.getIconResource( nWidgetId, plugin );
    		putInCache( PREFIX_ICON_RESOURCE + nWidgetId, img );
    	}
    	return img;
    }
    
    /**
     * Check if the mime type is correct or not (list of correct mime types on
     * the <b>myportal.properties</b>.
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
     * @param plugin {@link Plugin}
     * @return a list of {@link Widget}
     */
    @SuppressWarnings("unchecked")
	public List<Widget> getWidgetsByCategoryId( int nCategoryId, Plugin plugin )
    {
    	List<Widget> listWidgets = (List<Widget>) getFromCache( PREFIX_WIDGETS_BY_CATEGORIES + nCategoryId );
    	if ( listWidgets == null )
    	{
    		WidgetFilter wFilter = new WidgetFilter(  );
        	wFilter.setIdCategory( nCategoryId );
        	wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
        	wFilter.setIsWideSearch( false );
        	listWidgets = WidgetHome.getWidgetsByFilter( wFilter, plugin );
        	putInCache( PREFIX_WIDGETS_BY_CATEGORIES + nCategoryId, listWidgets );
    	}
    	return listWidgets;
    }
    
    /**
     * Get the list of essential widgets
     * @param nCategoryId the id category
     * @param plugin {@link Plugin}
     * @return a list of {@link Widget}
     */
    @SuppressWarnings("unchecked")
	public List<Widget> getEssentialWidgets( Plugin plugin )
    {
    	List<Widget> listWidgets = (List<Widget>) getFromCache( PREFIX_ESSENTIAL_WIDGETS );
    	if ( listWidgets == null )
    	{
    		WidgetFilter wFilter = new WidgetFilter(  );
        	wFilter.setIsEssential( WidgetFilter.FILTER_TRUE );
        	wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
        	wFilter.setIsWideSearch( false );
        	listWidgets = WidgetHome.getWidgetsByFilter( wFilter, plugin );
        	putInCache( PREFIX_ESSENTIAL_WIDGETS, listWidgets );
    	}
    	return listWidgets;
    }
    
    /**
     * Get the list of new widgets
     * @param nCategoryId the id category
     * @param plugin {@link Plugin}
     * @return a list of {@link Widget}
     */
    @SuppressWarnings("unchecked")
	public List<Widget> getNewWidgets( Plugin plugin )
    {
    	List<Widget> listWidgets = (List<Widget>) getFromCache( PREFIX_NEW_WIDGETS );
    	if ( listWidgets == null )
    	{
    		WidgetFilter wFilter = new WidgetFilter(  );
        	wFilter.setIsNew( WidgetFilter.FILTER_TRUE );
        	wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
        	wFilter.setIsWideSearch( false );
        	listWidgets = WidgetHome.getWidgetsByFilter( wFilter, plugin );
        	putInCache( PREFIX_NEW_WIDGETS, listWidgets );
    	}
    	return listWidgets;
    }
    
    /**
     * Get the list of widgets given a name
     * @param strName the name
     * @param plugin {@link Plugin}
     * @return a list of {@link Widget}
     */
    public List<Widget> getWidgetsByName( String strName, Plugin plugin )
    {
		WidgetFilter wFilter = new WidgetFilter(  );
    	wFilter.setName( strName );
    	wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
    	wFilter.setIsWideSearch( false );
    	return WidgetHome.getWidgetsByFilter( wFilter, plugin );
    }
    
    /**
     * Create a new widget. If the cache is enable, it will reset the cache.
     * @param widget The {@link Widget}
     * @param plugin {@link Plugin}
     */
    public void createWidget( Widget widget, Plugin plugin )
    {
    	resetCache(  );
    	WidgetHome.create( widget, plugin );
    }
    
    /**
     * Remove a widget. If the cache is enable, it will reset the cache.
     * @param nWidgetId the widget ID
     * @param plugin {@link Plugin}
     */
    public void removeWidget( int nWidgetId, Plugin plugin )
    {
    	resetCache(  );
    	WidgetContentService.instance(  ).removeCache( Integer.toString( nWidgetId ) );
    	WidgetHome.remove( nWidgetId, plugin );
    }
    
    /**
     * Update a widget. If the cache is enable, it will reset the cache.
     * @param widget The {@link Widget}
     * @param bUpdateIcon true if it must also update the icon, false otherwise
     * @param plugin {@link Plugin}
     */
    public void updateWidget( Widget widget, boolean bUpdateIcon, Plugin plugin )
    {
    	resetCache(  );
    	WidgetContentService.instance(  ).removeCache( Integer.toString( widget.getIdWidget(  ) ) );
    	WidgetHome.update( widget, bUpdateIcon, plugin );
    }
}
