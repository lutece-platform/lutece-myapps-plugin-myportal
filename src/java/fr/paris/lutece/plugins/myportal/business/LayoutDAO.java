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
 * This class provides Data Access methods for Layout objects
 */

public final class LayoutDAO implements ILayoutDAO
{
	
	// Constants
	
	private static final String SQL_QUERY_NEW_PK = "SELECT max( id_layout ) FROM myportal_layout";
	private static final String SQL_QUERY_SELECT = "SELECT id_layout, name, description, layout FROM myportal_layout WHERE id_layout = ?";
	private static final String SQL_QUERY_INSERT = "INSERT INTO myportal_layout ( id_layout, name, description, layout ) VALUES ( ?, ?, ?, ? ) ";
	private static final String SQL_QUERY_DELETE = "DELETE FROM myportal_layout WHERE id_layout = ? ";
	private static final String SQL_QUERY_UPDATE = "UPDATE myportal_layout SET id_layout = ?, name = ?, description = ?, layout = ? WHERE id_layout = ?";
	private static final String SQL_QUERY_SELECTALL = "SELECT id_layout, name, description, layout FROM myportal_layout";


	
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
	 * @param layout instance of the Layout object to insert
         * @param plugin The plugin
	 */

	public void insert( Layout layout, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT , plugin );
                
		layout.setIdLayout( newPrimaryKey( plugin ) );
                
                daoUtil.setInt ( 1, layout.getIdLayout ( ) );
                daoUtil.setString ( 2, layout.getName ( ) );
                daoUtil.setString ( 3, layout.getDescription ( ) );
                daoUtil.setString ( 4, layout.getLayout ( ) );

		daoUtil.executeUpdate();
		daoUtil.free();
	}


	/**
	 * Load the data of the layout from the table
	 * @param nId The identifier of the layout
         * @param plugin The plugin
	 * @return the instance of the Layout
	 */


        public Layout load( int nId, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT , plugin );
		daoUtil.setInt( 1 , nId );
		daoUtil.executeQuery();

		Layout layout = null;

		if ( daoUtil.next() )
		{
			layout = new Layout();

                        layout.setIdLayout( daoUtil.getInt(  1 ) );
                        layout.setName( daoUtil.getString(  2 ) );
                        layout.setDescription( daoUtil.getString(  3 ) );
                        layout.setLayout( daoUtil.getString(  4 ) );
		}

		daoUtil.free();
		return layout;
	}


	/**
	 * Delete a record from the table
         * @param nLayoutId The identifier of the layout
         * @param plugin The plugin
	 */

	public void delete( int nLayoutId, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE , plugin );
		daoUtil.setInt( 1 , nLayoutId );
		daoUtil.executeUpdate();
		daoUtil.free();
	}


	/**
	 * Update the record in the table
	 * @param layout The reference of the layout
         * @param plugin The plugin
	 */

	public void store( Layout layout, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE , plugin );
                
        daoUtil.setInt( 1, layout.getIdLayout( ) );
        daoUtil.setString( 2, layout.getName( ) );
        daoUtil.setString( 3, layout.getDescription( ) );
        daoUtil.setString( 4, layout.getLayout( ) );
        daoUtil.setInt( 5, layout.getIdLayout( ) );
                
		daoUtil.executeUpdate( );
		daoUtil.free( );
	}



	/**
	 * Load the data of all the layouts and returns them as a collection
         * @param plugin The plugin
	 * @return The Collection which contains the data of all the layouts
	 */

        public Collection<Layout> selectLayoutsList( Plugin plugin )
	{
		Collection<Layout> layoutList = new ArrayList<Layout>(  );
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL , plugin );
		daoUtil.executeQuery(  );

		while ( daoUtil.next(  ) )
		{
                Layout layout = new Layout(  );

                    layout.setIdLayout( daoUtil.getInt( 1 ) );
                    layout.setName( daoUtil.getString( 2 ) );
                    layout.setDescription( daoUtil.getString( 3 ) );
                    layout.setLayout( daoUtil.getString( 4 ) );

                layoutList.add( layout );
		}

		daoUtil.free();
		return layoutList;
	}

}
