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

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetFilter;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.plugins.myportal.business.WidgetStatusEnum;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * Widget Service class : retreive Widget description from a cache
 */
public class WidgetService extends AbstractCacheableService
{
    private static WidgetService _singleton = new WidgetService(  );
    private static final String SERVICE_NAME = "MyPortal Widget Service";

    // CONSTANTS
	private static final String COMMA = ",";
	
    // PROPERTIES
    private static final String PROPERTY_ACCEPTED_ICON_FORMATS = "myportal.acceptedIconFormats";

    /** Private constructor */
    private WidgetService(  )
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
    public static WidgetService instance(  )
    {
        return _singleton;
    }

    /**
     * Returns widgets description from the cache
     * @param id The Widget ID
     * @return The widget object
     */
    public Widget getWidget( int id )
    {
        String strWidgetId = "" + id;
        Widget widget = (Widget) getFromCache( strWidgetId );

        if ( widget == null )
        {
        	Plugin plugin = PluginService.getPlugin( MyPortalPlugin.PLUGIN_NAME );
            widget = WidgetHome.findByPrimaryKey( id, plugin );
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
    	return WidgetHome.getIconResource( nWidgetId, plugin );
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
    public List<Widget> getWidgetsByCategoryId( int nCategoryId, Plugin plugin )
    {
    	WidgetFilter wFilter = new WidgetFilter(  );
    	wFilter.setIdCategory( nCategoryId );
    	wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
    	wFilter.setIsWideSearch( false );
    	return WidgetHome.getWidgetsByFilter( wFilter, plugin );
    }
    
    /**
     * Get the list of widgets
     * @param plugin {@link Plugin}
     * @return a collection of {@link Widget}
     */
    public Collection<Widget> getWidgetsList( Plugin plugin )
    {
    	return WidgetHome.getWidgetsList( plugin );
    }
    
    /**
     * Get the list of essential widgets
     * @param nCategoryId the id category
     * @param plugin {@link Plugin}
     * @return a list of {@link Widget}
     */
    public List<Widget> getEssentialWidgets( Plugin plugin )
    {
    	WidgetFilter wFilter = new WidgetFilter(  );
    	wFilter.setIsEssential( WidgetFilter.FILTER_TRUE );
    	wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
    	wFilter.setIsWideSearch( false );
    	return WidgetHome.getWidgetsByFilter( wFilter, plugin );
    }
    
    /**
     * Get the list of new widgets
     * @param nCategoryId the id category
     * @param plugin {@link Plugin}
     * @return a list of {@link Widget}
     */
    public List<Widget> getNewWidgets( Plugin plugin )
    {
    	WidgetFilter wFilter = new WidgetFilter(  );
    	wFilter.setIsNew( WidgetFilter.FILTER_TRUE );
    	wFilter.setStatus( WidgetStatusEnum.PUBLIC.getId(  ) );
    	wFilter.setIsWideSearch( false );
    	return WidgetHome.getWidgetsByFilter( wFilter, plugin );
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
}
