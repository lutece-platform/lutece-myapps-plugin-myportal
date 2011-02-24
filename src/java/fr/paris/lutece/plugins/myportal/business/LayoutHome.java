/*
 * Copyright (c) 2002-2009, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.Collection;

/**
 * This class provides instances management methods (create, find, ...) for Layout objects
 */

public final class LayoutHome
{

    // Static variable pointed at the DAO instance

    private static ILayoutDAO _dao = ( ILayoutDAO ) SpringContextService.getPluginBean( "myportal", "layoutDAO" );


    /**
     * Private constructor - this class need not be instantiated
     */

    private LayoutHome(  )
    {
    }

    /**
     * Create an instance of the layout class
     * @param layout The instance of the Layout which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of layout which has been created with its primary key.
     */

    public static Layout create( Layout layout, Plugin plugin )
    {
        _dao.insert( layout, plugin );

        return layout;
    }


    /**
     * Update of the layout which is specified in parameter
     * @param layout The instance of the Layout which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  layout which has been updated
     */

    public static Layout update( Layout layout, Plugin plugin )
    {
        _dao.store( layout, plugin );

        return layout;
    }


    /**
     * Remove the layout whose identifier is specified in parameter
     * @param nLayoutId The layout Id
     * @param plugin the Plugin
     */


    public static void remove( int nLayoutId, Plugin plugin )
    {
        _dao.delete( nLayoutId, plugin );
    }


    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a layout whose identifier is specified in parameter
     * @param nKey The layout primary key
     * @param plugin the Plugin
     * @return an instance of Layout
     */

    public static Layout findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin);
    }


    /**
     * Load the data of all the layout objects and returns them in form of a collection
     * @param plugin the Plugin
     * @return the collection which contains the data of all the layout objects
     */

    public static Collection<Layout> getLayoutsList( Plugin plugin )
    {
        return _dao.selectLayoutsList( plugin );
    }

}

