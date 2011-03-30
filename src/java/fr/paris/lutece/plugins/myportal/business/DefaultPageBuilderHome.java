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

import java.util.List;


/**
 *
 * DefaultPageBuilderHome
 *
 */
public final class DefaultPageBuilderHome
{
    // Static variable pointed at the DAO instance
    private static final String BEAN_MYPORTAL_DEFAULTPAGEBUILDERDAO = "myportal.defaultPageBuilderDAO";
    private static Plugin _plugin = PluginService.getPlugin( MyPortalPlugin.PLUGIN_NAME );
    private static IDefaultPageBuilderDAO _dao = (IDefaultPageBuilderDAO) SpringContextService.getPluginBean( MyPortalPlugin.PLUGIN_NAME,
            BEAN_MYPORTAL_DEFAULTPAGEBUILDERDAO );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DefaultPageBuilderHome(  )
    {
    }

    /**
     * Get a new PK
     *
     * @return the new PK
     */
    public static int newPrimaryKey(  )
    {
        return _dao.newPrimaryKey( _plugin );
    }

    /**
     * Creation of an instance of widgetComponent
     *
     * @param widgetComponent The instance of the widgetComponent which contains the informations to store
     */
    public static void create( WidgetComponent widgetComponent )
    {
        _dao.insert( widgetComponent, _plugin );
    }

    /**
     * Update of the widgetComponent which is specified in parameter
     *
     * @param widgetComponent The instance of the widgetComponent which contains the informations to update
     *
     */
    public static void update( WidgetComponent widgetComponent )
    {
        _dao.store( widgetComponent, _plugin );
    }

    /**
     * Remove the widgetComponent whose identifier is specified in parameter
     *
     * @param nIdWidgetComponent The widgetComponent id
     */
    public static void remove( int nIdWidgetComponent )
    {
        _dao.delete( nIdWidgetComponent, _plugin );
    }

    /**
     * Delete the records that have a column > to the given nColumnMax
     *
     * @param nColumnMax the column max
     */
    public static void removeByColumnMax( int nColumnMax )
    {
        _dao.deleteByColumnMax( nColumnMax, _plugin );
    }

    /**
     * Remove the widgetComponent whose identifier is specified in parameter
     */
    public static void removeAll(  )
    {
        _dao.deleteAll( _plugin );
    }

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a widgetComponent whose identifier is specified in parameter
     *
     * @param nIdWidgetComponent The widgetComponent primary key
     * @return an instance of widgetComponent
     */
    public static WidgetComponent findByPrimaryKey( int nIdWidgetComponent )
    {
        return _dao.load( nIdWidgetComponent, _plugin );
    }

    /**
     * Loads the data of all the widgetComponent
     *
     * @return the list which contains the data of all the widgetComponent
     */
    public static List<WidgetComponent> findAll(  )
    {
        return _dao.selectAllWidgetComponents( _plugin );
    }

    /**
     * Loads the data of all the widgetComponent
     * @param filter a search by criteria
     * @return the list which contains the data of all the widgetComponent
     */
    public static List<WidgetComponent> findByFilter( WidgetComponentFilter filter )
    {
        return _dao.selectWidgetComponents( filter, _plugin );
    }

    /**
     * Finds the max order for all columns.
     * @return the max order
     */
    public static int findMaxOrder(  )
    {
        return _dao.selectMaxOrder( _plugin );
    }

    /**
     * Finds the max order for the column.
     * @param nColumn the column
     * @return the max order
     */
    public static int findMaxOrder( int nColumn )
    {
        return _dao.selectMaxOrder( nColumn, _plugin );
    }

    /**
     * Find the columns list
     * @return a list of integer
     */
    public static List<Integer> findColumns(  )
    {
        return _dao.selectColumns( _plugin );
    }

    /**
     * Return the widget ids list
     * @return the widget ids list
     */
    public static List<Integer> findWidgetIds(  )
    {
        return _dao.selectWidgetIds( _plugin );
    }
}
