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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for Category objects
 */

public final class CategoryDAO implements ICategoryDAO
{
	
	// Constants
	
	private static final String SQL_QUERY_NEW_PK = "SELECT max( id_category ) FROM myportal_category";
	private static final String SQL_QUERY_SELECT = "SELECT id_category, id_parent, name, description FROM myportal_category WHERE id_category = ?";
	private static final String SQL_QUERY_INSERT = "INSERT INTO myportal_category ( id_category, id_parent, name, description ) VALUES ( ?, ?, ?, ? ) ";
	private static final String SQL_QUERY_DELETE = "DELETE FROM myportal_category WHERE id_category = ? ";
	private static final String SQL_QUERY_UPDATE = "UPDATE myportal_category SET id_category = ?, id_parent = ?, name = ?, description = ? WHERE id_category = ?";
	private static final String SQL_QUERY_SELECTALL = "SELECT id_category, id_parent, name, description FROM myportal_category";


	
	/**
	 * Generates a new primary key
         * @param plugin The Plugin
	 * @return The new primary key
	 */
    
	public int newPrimaryKey( Plugin plugin)
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK , plugin  );
		daoUtil.executeQuery();

		int nKey;

		if( !daoUtil.next() )
		{
			// if the table is empty
			nKey = 1;
		}

		nKey = daoUtil.getInt( 1 ) + 1;
		daoUtil.free();

		return nKey;
	}




	/**
	 * Insert a new record in the table.
	 * @param category instance of the Category object to insert
         * @param plugin The plugin
	 */

	public void insert( Category category, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT , plugin );
                
		category.setIdCategory( newPrimaryKey( plugin ) );
                
                daoUtil.setInt ( 1, category.getIdCategory ( ) );
                daoUtil.setInt ( 2, category.getIdParent ( ) );
                daoUtil.setString ( 3, category.getName ( ) );
                daoUtil.setString ( 4, category.getDescription ( ) );

		daoUtil.executeUpdate();
		daoUtil.free();
	}


	/**
	 * Load the data of the category from the table
	 * @param nId The identifier of the category
         * @param plugin The plugin
	 * @return the instance of the Category
	 */


        public Category load( int nId, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT , plugin );
		daoUtil.setInt( 1 , nId );
		daoUtil.executeQuery();

		Category category = null;

		if ( daoUtil.next() )
		{
			category = new Category();

                        category.setIdCategory( daoUtil.getInt(  1 ) );
                        category.setIdParent( daoUtil.getInt(  2 ) );
                        category.setName( daoUtil.getString(  3 ) );
                        category.setDescription( daoUtil.getString(  4 ) );
		}

		daoUtil.free();
		return category;
	}


	/**
	 * Delete a record from the table
         * @param nCategoryId The identifier of the category
         * @param plugin The plugin
	 */

	public void delete( int nCategoryId, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE , plugin );
		daoUtil.setInt( 1 , nCategoryId );
		daoUtil.executeUpdate();
		daoUtil.free();
	}


	/**
	 * Update the record in the table
	 * @param category The reference of the category
         * @param plugin The plugin
	 */

	public void store( Category category, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE , plugin );
                
        daoUtil.setInt( 1, category.getIdCategory( ) );
        daoUtil.setInt( 2, category.getIdParent( ) );
        daoUtil.setString( 3, category.getName( ) );
        daoUtil.setString( 4, category.getDescription( ) );
        daoUtil.setInt( 5, category.getIdCategory( ) );
                
		daoUtil.executeUpdate( );
		daoUtil.free( );
	}



	/**
	 * Load the data of all the categorys and returns them as a collection
         * @param plugin The plugin
	 * @return The Collection which contains the data of all the categorys
	 */

        public Collection<Category> selectCategorysList( Plugin plugin )
	{
		Collection<Category> categoryList = new ArrayList<Category>(  );
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL , plugin );
		daoUtil.executeQuery(  );

		while ( daoUtil.next(  ) )
		{
                Category category = new Category(  );

                    category.setIdCategory( daoUtil.getInt( 1 ) );
                    category.setIdParent( daoUtil.getInt( 2 ) );
                    category.setName( daoUtil.getString( 3 ) );
                    category.setDescription( daoUtil.getString( 4 ) );

                categoryList.add( category );
		}

		daoUtil.free();
		return categoryList;
	}

}
