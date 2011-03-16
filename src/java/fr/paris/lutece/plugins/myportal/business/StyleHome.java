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
package fr.paris.lutece.plugins.myportal.business;

import fr.paris.lutece.plugins.myportal.service.MyPortalPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;


/**
 *
 * This class provides instances management methods (create, find, ...) for Style objects
 *
 */
public final class StyleHome
{
    // Static variable pointed at the DAO instance
    private static final String BEAN_MYPORTAL_STYLEDAO = "myportal.styleDAO";
    private static Plugin _plugin = PluginService.getPlugin( MyPortalPlugin.PLUGIN_NAME );
    private static IStyleDAO _dao = (IStyleDAO) SpringContextService.getPluginBean( MyPortalPlugin.PLUGIN_NAME,
            BEAN_MYPORTAL_STYLEDAO );

    /**
     * Private constructor - this class need not be instantiated
     */
    private StyleHome(  )
    {
    }

    /**
     * Generates a new primary key
     * @return The new primary key
     */
    public static int newPrimaryKey(  )
    {
        return _dao.newPrimaryKey( _plugin );
    }

    /**
     * Create an instance of the style class
     * @param style The instance of the Style which contains the informations to store
     * @return The  instance of style which has been created with its primary key.
     */
    public static Style create( Style style )
    {
        _dao.insert( style, _plugin );

        return style;
    }

    /**
     * Update of the style which is specified in parameter
     * @param style The instance of the Style which contains the data to store
     * @return The instance of the  style which has been updated
     */
    public static Style update( Style style )
    {
        _dao.store( style, _plugin );

        return style;
    }

    /**
     * Remove the style whose identifier is specified in parameter
     * @param nStyleId The style Id
     */
    public static void remove( int nStyleId )
    {
        _dao.delete( nStyleId, _plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a style whose identifier is specified in parameter
     * @param nKey The style primary key
     * @return an instance of Style
     */
    public static Style findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the style objects and returns them in form of a list
     * @return the list which contains the data of all the style objects
     */
    public static List<Style> getStylesList(  )
    {
        return _dao.selectStylesList( _plugin );
    }

    /**
     * Load the data of all the style objects and returns them in form of a list
     * @return the list which contains the data of all the style objects
     */
    public static ReferenceList getStyles(  )
    {
        ReferenceList list = new ReferenceList(  );

        for ( Style style : _dao.selectStylesList( _plugin ) )
        {
            list.addItem( style.getId(  ), style.getName(  ) );
        }

        return list;
    }
}
