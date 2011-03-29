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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 *
 * IDefaultPageBuilderDAO
 *
 */
public interface IDefaultPageBuilderDAO
{
    /**
     * Get a new PK
     *
     * @param plugin {@link Plugin}
     * @return the new PK
     */
    int newPrimaryKey( Plugin plugin );

    /**
     * Insert a new record in the table.
     *
     * @param widgetComponent instance of the widgetComponent object to insert
     * @param plugin {@link Plugin}
     */
    void insert( WidgetComponent widgetComponent, Plugin plugin );

    /**
     * Load the data of the IndexerAction  from the table
     *
     * @param nIdWidgetComponent the ID of the widget component
     * @param plugin {@link Plugin}
     * @return the instance of the WidgetComponent
     */
    WidgetComponent load( int nIdWidgetComponent, Plugin plugin );

    /**
     * Delete a record from the table
     *
     * @param nIdWidgetComponent the ID of the widget component
     * @param plugin {@link Plugin}
     */
    void delete( int nIdWidgetComponent, Plugin plugin );

    /**
     * Delete all record from the table
     *
     * @param plugin {@link Plugin}
     */
    void deleteAll( Plugin plugin );

    /**
     * Update the widgetComponent in the table
     *
     * @param widgetComponent instance of the widgetComponent object to update
     * @param plugin {@link Plugin}
     */
    void store( WidgetComponent widgetComponent, Plugin plugin );

    /**
     * Finds all WidgetComponent
     *
     * @param plugin {@link Plugin}
     * @return all WidgetComponent
     */
    List<WidgetComponent> selectAllWidgetComponents( Plugin plugin );

    /**
     * Finds all WidgetComponent matching filter
     *
     * @param filter the filter
     * @param plugin {@link Plugin}
     * @return all WidgetComponent matching filter
     */
    List<WidgetComponent> selectWidgetComponents( WidgetComponentFilter filter, Plugin plugin );

    /**
     * Returns the max order value, for all columns
     *
     * @param plugin {@link Plugin}
     * @return the max order
     */
    int selectMaxOrder( Plugin plugin );

    /**
     * Returns the max order value, for the given column
     *
     * @param plugin {@link Plugin}
     * @param nColumn the column
     * @return the max order
     */
    int selectMaxOrder( int nColumn, Plugin plugin );

    /**
     * Returns the columns list
     *
     * @param plugin {@link Plugin}
     * @return the columns list
     */
    List<Integer> selectColumns( Plugin plugin );

    /**
     * Return the widget ids list
     *
     * @param plugin {@link Plugin}
     * @return the widget ids list
     */
    List<Integer> selectWidgetIds( Plugin plugin );
}
