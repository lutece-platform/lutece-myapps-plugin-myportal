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
import java.util.List;


/**
 *
 * DefaultPageBuilderDAO
 *
 */
public class DefaultPageBuilderDAO implements IDefaultPageBuilderDAO
{
    // SQL QUERIES
    private static final String SQL_QUERY_NEW_PK = " SELECT max( a.id_widget_component ) FROM myportal_default_page a ";
    private static final String SQL_QUERY_MAX_ORDER = " SELECT max( a.widget_order ) FROM myportal_default_page a ";
    private static final String SQL_QUERY_MAX_ORDER_COLUMN = SQL_QUERY_MAX_ORDER + " WHERE a.widget_column = ? ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM myportal_default_page ";
    private static final String SQL_QUERY_DELETE_BY_ID_WIDGET_CONFIG = SQL_QUERY_DELETE +
        " WHERE id_widget_component = ? ";
    private static final String SQL_QUERY_DELETE_BY_COLUMN_MAX = SQL_QUERY_DELETE + " WHERE widget_column > ? ";
    private static final String SQL_QUERY_SELECT = " SELECT DISTINCT a.id_widget_component, a.id_widget, a.widget_order, a.widget_column, b.name, c.name " +
        " FROM myportal_default_page a INNER JOIN myportal_widget b ON a.id_widget = b.id_widget " +
        " INNER JOIN myportal_widget_style INNER JOIN myportal_widget_style c ON b.id_style = c.id_style ";
    private static final String SQL_QUERY_ORDER_BY_COLUMN_AND_ORDER = " ORDER BY a.widget_column, a.widget_order ";
    private static final String SQL_QUERY_SELECT_ALL = SQL_QUERY_SELECT + SQL_QUERY_ORDER_BY_COLUMN_AND_ORDER;
    private static final String SQL_QUERY_SELECT_COLUMNS = " SELECT a.widget_column FROM myportal_default_page a GROUP BY a.widget_column ";
    private static final String SQL_QUERY_SELECT_WIDGET_IDS = " SELECT DISTINCT a.id_widget FROM myportal_default_page a GROUP BY a.id_widget ";
    private static final String SQL_QUERY_FILTER_COLUMN = " a.widget_column = ? ";
    private static final String SQL_QUERY_FILTER_ORDER = " a.widget_order = ? ";
    private static final String SQL_QUERY_FILTER_ID_WIDGET_COMPONENT = " a.id_widget_component = ? ";
    private static final String SQL_QUERY_SELECT_BY_PRIMARY_KEY = SQL_QUERY_SELECT + " WHERE " +
        SQL_QUERY_FILTER_ID_WIDGET_COMPONENT;
    private static final String SQL_QUERY_INSERT = " INSERT INTO myportal_default_page( id_widget_component, id_widget, widget_order, widget_column ) " +
        " VALUES(?,?,?,?) ";
    private static final String SQL_QUERY_UPDATE = " UPDATE myportal_default_page " +
        " SET id_widget = ?, widget_order = ?, widget_column = ? WHERE id_widget_component = ? ";
    private static final String SQL_QUERY_KEYWORD_WHERE = "  WHERE ";
    private static final String SQL_QUERY_KEYWORD_AND = " AND ";

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
    public void delete( int nIdWidgetComponent, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_WIDGET_CONFIG, plugin );

        daoUtil.setInt( 1, nIdWidgetComponent );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteByColumnMax( int nColumnMax, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_COLUMN_MAX, plugin );

        daoUtil.setInt( 1, nColumnMax );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void insert( WidgetComponent widgetComponent, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setInt( 1, newPrimaryKey( plugin ) );
        setInsertOrUpdateValues( 2, widgetComponent, daoUtil );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public WidgetComponent load( int nIdWidgetComponent, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_PRIMARY_KEY, plugin );

        daoUtil.setInt( 1, nIdWidgetComponent );

        daoUtil.executeQuery(  );

        WidgetComponent widgetComponent = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            widgetComponent = new WidgetComponent(  );
            widgetComponent.setIdWidgetComponent( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setIdWidget( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setOrder( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setColumn( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setWidgetName( daoUtil.getString( nIndex++ ) );
            widgetComponent.setStyleName( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return widgetComponent;
    }

    /**
     * {@inheritDoc}
     */
    public List<WidgetComponent> selectAllWidgetComponents( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );

        daoUtil.executeQuery(  );

        List<WidgetComponent> listWidgetComponents = new ArrayList<WidgetComponent>(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            WidgetComponent widgetComponent = new WidgetComponent(  );
            widgetComponent.setIdWidgetComponent( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setIdWidget( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setOrder( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setColumn( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setWidgetName( daoUtil.getString( nIndex++ ) );
            widgetComponent.setStyleName( daoUtil.getString( nIndex++ ) );
            listWidgetComponents.add( widgetComponent );
        }

        daoUtil.free(  );

        return listWidgetComponents;
    }

    /**
     * {@inheritDoc}
     */
    public int selectMaxOrder( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_MAX_ORDER, plugin );

        int nMaxOrder = 0;

        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nMaxOrder = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        return nMaxOrder;
    }

    /**
     * {@inheritDoc}
     */
    public int selectMaxOrder( int nColumn, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_MAX_ORDER_COLUMN, plugin );

        int nMaxOrder = 0;

        daoUtil.setInt( 1, nColumn );

        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nMaxOrder = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        return nMaxOrder;
    }

    /**
     * {@inheritDoc}
     */
    public List<WidgetComponent> selectWidgetComponents( WidgetComponentFilter filter, Plugin plugin )
    {
        StringBuilder sbSQL = new StringBuilder( SQL_QUERY_SELECT );
        buildSQLFilter( sbSQL, filter );
        sbSQL.append( SQL_QUERY_ORDER_BY_COLUMN_AND_ORDER );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ), plugin );

        applySQLFilter( daoUtil, 1, filter );

        daoUtil.executeQuery(  );

        List<WidgetComponent> listWidgetComponents = new ArrayList<WidgetComponent>(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            WidgetComponent widgetComponent = new WidgetComponent(  );
            widgetComponent.setIdWidgetComponent( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setIdWidget( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setOrder( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setColumn( daoUtil.getInt( nIndex++ ) );
            widgetComponent.setWidgetName( daoUtil.getString( nIndex++ ) );
            widgetComponent.setStyleName( daoUtil.getString( nIndex++ ) );
            listWidgetComponents.add( widgetComponent );
        }

        daoUtil.free(  );

        return listWidgetComponents;
    }

    /**
     * {@inheritDoc}
     */
    public void store( WidgetComponent widgetComponent, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nIndex = 1;
        nIndex = setInsertOrUpdateValues( 1, widgetComponent, daoUtil );
        daoUtil.setInt( nIndex, widgetComponent.getIdWidgetComponent(  ) );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public List<Integer> selectColumns( Plugin plugin )
    {
        List<Integer> listColumns = new ArrayList<Integer>(  );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_COLUMNS, plugin );

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            listColumns.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free(  );

        return listColumns;
    }

    /**
     * {@inheritDoc}
     */
    public List<Integer> selectWidgetIds( Plugin plugin )
    {
        List<Integer> listWidgetIds = new ArrayList<Integer>(  );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_WIDGET_IDS, plugin );

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            listWidgetIds.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free(  );

        return listWidgetIds;
    }

    /**
     * Sets daoUtil values from componnet
     * @param nStartIndex the start index
     * @param widgetComponent the component
     * @param daoUtil daoutil
     * @return the end index
     */
    private int setInsertOrUpdateValues( int nStartIndex, WidgetComponent widgetComponent, DAOUtil daoUtil )
    {
        int nIndex = nStartIndex;
        daoUtil.setInt( nIndex++, widgetComponent.getIdWidget(  ) );
        daoUtil.setInt( nIndex++, widgetComponent.getOrder(  ) );
        daoUtil.setInt( nIndex++, widgetComponent.getColumn(  ) );

        return nIndex;
    }

    /**
     * Builds sql filter
     * @param sbSQL the buffer
     * @param filter the filter
     */
    private void buildSQLFilter( StringBuilder sbSQL, WidgetComponentFilter filter )
    {
        List<String> listFilters = new ArrayList<String>(  );

        if ( filter.containsFilterOrder(  ) )
        {
            listFilters.add( SQL_QUERY_FILTER_ORDER );
        }

        if ( filter.containsFilterColumn(  ) )
        {
            listFilters.add( SQL_QUERY_FILTER_COLUMN );
        }

        if ( !listFilters.isEmpty(  ) )
        {
            sbSQL.append( SQL_QUERY_KEYWORD_WHERE );

            boolean bFirstFilter = true;

            for ( String strFilter : listFilters )
            {
                sbSQL.append( strFilter );

                if ( !bFirstFilter )
                {
                    sbSQL.append( SQL_QUERY_KEYWORD_AND );
                }
                else
                {
                    bFirstFilter = false;
                }
            }
        }
    }

    /**
     * Add daoUtil parameters
     * @param daoUtil daoUtil
     * @param nStartIndex start index
     * @param filter the filter to apply
     * @return end index
     */
    private int applySQLFilter( DAOUtil daoUtil, int nStartIndex, WidgetComponentFilter filter )
    {
        int nIndex = nStartIndex;

        if ( filter.containsFilterOrder(  ) )
        {
            daoUtil.setInt( nIndex++, filter.getFilterOrder(  ) );
        }

        if ( filter.containsFilterColumn(  ) )
        {
            daoUtil.setInt( nIndex++, filter.getFilterColumn(  ) );
        }

        return nIndex;
    }
}
