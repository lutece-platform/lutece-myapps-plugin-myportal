/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
 *
 * This class provides Data Access methods for Category objects
 *
 */
public final class CategoryDAO implements ICategoryDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_category ) FROM myportal_category";
    private static final String SQL_QUERY_SELECT = "SELECT id_category, name, description FROM myportal_category WHERE id_category = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO myportal_category ( id_category, name, description ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM myportal_category WHERE id_category = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE myportal_category SET id_category = ?, name = ?, description = ? WHERE id_category = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_category, name, description FROM myportal_category ORDER BY name ASC";
    private static final String SQL_QUERY_SELECT_FIRST_CATEGORY = "SELECT id_category, name, description FROM myportal_category ORDER BY name ASC LIMIT 1";

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    public void insert( Category category, Plugin plugin )
    {
        int nIndex = 1;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        category.setIdCategory( newPrimaryKey( plugin ) );

        daoUtil.setInt( nIndex++, category.getIdCategory(  ) );
        daoUtil.setString( nIndex++, category.getName(  ) );
        daoUtil.setString( nIndex++, category.getDescription(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public Category load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        Category category = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            category = new Category(  );

            category.setIdCategory( daoUtil.getInt( nIndex++ ) );
            category.setName( daoUtil.getString( nIndex++ ) );
            category.setDescription( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return category;
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nCategoryId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nCategoryId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void store( Category category, Plugin plugin )
    {
        int nIndex = 1;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( nIndex++, category.getIdCategory(  ) );
        daoUtil.setString( nIndex++, category.getName(  ) );
        daoUtil.setString( nIndex++, category.getDescription(  ) );
        daoUtil.setInt( nIndex++, category.getIdCategory(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public Collection<Category> selectCategoriesList( Plugin plugin )
    {
        Collection<Category> categoryList = new ArrayList<Category>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            Category category = new Category(  );

            category.setIdCategory( daoUtil.getInt( nIndex++ ) );
            category.setName( daoUtil.getString( nIndex++ ) );
            category.setDescription( daoUtil.getString( nIndex++ ) );

            categoryList.add( category );
        }

        daoUtil.free(  );

        return categoryList;
    }

    /**
     * {@inheritDoc}
     */
    public Category selectFirstCategory( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_FIRST_CATEGORY, plugin );
        daoUtil.executeQuery(  );

        Category category = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            category = new Category(  );

            category.setIdCategory( daoUtil.getInt( nIndex++ ) );
            category.setName( daoUtil.getString( nIndex++ ) );
            category.setDescription( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return category;
    }
}
