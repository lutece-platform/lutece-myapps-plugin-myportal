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
 * This class provides instances management methods (create, find, ...) for Category objects
 */

public final class CategoryHome
{

    // Static variable pointed at the DAO instance

    private static ICategoryDAO _dao = ( ICategoryDAO ) SpringContextService.getPluginBean( "myportal", "categoryDAO" );


    /**
     * Private constructor - this class need not be instantiated
     */

    private CategoryHome(  )
    {
    }

    /**
     * Create an instance of the category class
     * @param category The instance of the Category which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of category which has been created with its primary key.
     */

    public static Category create( Category category, Plugin plugin )
    {
        _dao.insert( category, plugin );

        return category;
    }


    /**
     * Update of the category which is specified in parameter
     * @param category The instance of the Category which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  category which has been updated
     */

    public static Category update( Category category, Plugin plugin )
    {
        _dao.store( category, plugin );

        return category;
    }


    /**
     * Remove the category whose identifier is specified in parameter
     * @param nCategoryId The category Id
     * @param plugin the Plugin
     */


    public static void remove( int nCategoryId, Plugin plugin )
    {
        _dao.delete( nCategoryId, plugin );
    }


    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a category whose identifier is specified in parameter
     * @param nKey The category primary key
     * @param plugin the Plugin
     * @return an instance of Category
     */

    public static Category findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin);
    }


    /**
     * Load the data of all the category objects and returns them in form of a collection
     * @param plugin the Plugin
     * @return the collection which contains the data of all the category objects
     */

    public static Collection<Category> getCategorysList( Plugin plugin )
    {
        return _dao.selectCategorysList( plugin );
    }

}

