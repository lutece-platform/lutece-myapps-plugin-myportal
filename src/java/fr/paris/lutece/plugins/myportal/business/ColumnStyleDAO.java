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
package fr.paris.lutece.plugins.myportal.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class provides Data Access methods for Style objects
 *
 */
public final class ColumnStyleDAO implements IWidgetStyleDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_style ) FROM myportal_column_style";
    private static final String SQL_QUERY_SELECT = "SELECT id_style, name, css_class FROM myportal_column_style WHERE id_style = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO myportal_column_style ( id_style, name, css_class ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM myportal_column_style WHERE id_style = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE myportal_column_style SET id_style = ?, name = ?, css_class = ? WHERE id_style = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_style, name, css_class FROM myportal_column_style";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * @param style instance of the Style object to insert
     * @param plugin The plugin
     */
    public void insert( Style style, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        style.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, style.getId(  ) );
        daoUtil.setString( 2, style.getName(  ) );
        daoUtil.setString( 3, style.getCssClass(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the style from the table
     * @param nId The identifier of the style
     * @param plugin The plugin
     * @return the instance of the Style
     */
    public Style load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        Style style = null;

        if ( daoUtil.next(  ) )
        {
            style = new Style(  );

            style.setId( daoUtil.getInt( 1 ) );
            style.setName( daoUtil.getString( 2 ) );
            style.setCssClass( daoUtil.getString( 3 ) );
        }

        daoUtil.free(  );

        return style;
    }

    /**
     * Delete a record from the table
     * @param nStyleId The identifier of the style
     * @param plugin The plugin
     */
    public void delete( int nStyleId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nStyleId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param style The reference of the style
     * @param plugin The plugin
     */
    public void store( Style style, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, style.getId(  ) );
        daoUtil.setString( 2, style.getName(  ) );
        daoUtil.setString( 3, style.getCssClass(  ) );
        daoUtil.setInt( 4, style.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the styles and returns them as a List
     * @param plugin The plugin
     * @return The List which contains the data of all the styles
     */
    public List<Style> selectStylesList( Plugin plugin )
    {
        List<Style> styleList = new ArrayList<Style>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Style style = new Style(  );

            style.setId( daoUtil.getInt( 1 ) );
            style.setName( daoUtil.getString( 2 ) );
            style.setCssClass( daoUtil.getString( 3 ) );

            styleList.add( style );
        }

        daoUtil.free(  );

        return styleList;
    }
}
