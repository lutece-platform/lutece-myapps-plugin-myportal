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

import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.image.ImageResourceManager;
import fr.paris.lutece.portal.service.image.ImageResourceProvider;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;


/**
 *
 * MyPortalImgProvider
 *
 */
public class WidgetImgProvider implements ImageResourceProvider
{
    private static final String IMAGE_RESOURCE_TYPE_ID = "myportal_widget_icon";
    private static WidgetImgProvider _singleton;

    /**
     * Contructor
     */
    public WidgetImgProvider(  )
    {
    }

    /**
     * Get the instance of {@link WidgetImgProvider}
     *
     * @return a {@link WidgetImgProvider}
     */
    public static synchronized WidgetImgProvider getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new WidgetImgProvider(  );
        }

        return _singleton;
    }

    /**
     * Init the provider
     */
    public void init(  )
    {
        register(  );
    }

    /**
     * Register the provider to the manager
     */
    public void register(  )
    {
        ImageResourceManager.registerProvider( this );
    }

    /**
     * Returns the resource type Id
     *
     * @return The resource type Id
     */
    public String getResourceTypeId(  )
    {
        return IMAGE_RESOURCE_TYPE_ID;
    }

    /**
     * Get the image resource given an widget Id
     *
     * @param nWidgetId the Id of the widget
     * @return an {@link ImageResource}
     */
    public ImageResource getImageResource( int nWidgetId )
    {
        Plugin plugin = PluginService.getPlugin( MyPortalPlugin.PLUGIN_NAME );

        return WidgetService.instance(  ).getIconResource( nWidgetId, plugin );
    }
}
