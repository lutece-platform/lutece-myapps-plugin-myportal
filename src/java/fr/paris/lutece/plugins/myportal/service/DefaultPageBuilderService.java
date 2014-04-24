/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.myportal.service;

import fr.paris.lutece.plugins.myportal.business.DefaultPageBuilderHome;
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetComponent;
import fr.paris.lutece.plugins.myportal.business.WidgetComponentFilter;
import fr.paris.lutece.plugins.myportal.business.parameter.PageBuilderParameterHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * DefaultPageBuilderService
 *
 */
public final class DefaultPageBuilderService
{
    // CONSTANTS
    private static final int CONSTANTE_FIRST_ORDER = 1;
    private static final int CONSTANTE_DEFAULT_COLUMN_COUNT = 3;
    private static final String BEAN_MYPORTAL_WIDGETSERVICE = "myportal.widgetService";

    // PARAMETERS
    private static final String PARAMETER_NB_COLUMNS = "nb_columns";

    // MARKS
    private static final String MARK_LIST_PARAM_DEFAULT_VALUES = "list_param_default_values";
    private static final String MARK_NB_COLUMNS = "nb_columns";
    private static final String MARK_COLUMNS_STYLE = "column_styles";
    private static DefaultPageBuilderService _singleton;
    private WidgetService _widgetService = (WidgetService) SpringContextService.getPluginBean( MyPortalPlugin.PLUGIN_NAME,
            BEAN_MYPORTAL_WIDGETSERVICE );

    /**
     * Private Constructor
     */
    private DefaultPageBuilderService(  )
    {
    }

    /**
     * Return the unique instance
     * @return The instance
     */
    public static synchronized DefaultPageBuilderService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new DefaultPageBuilderService(  );
        }

        return _singleton;
    }

    /**
     * Returns the column count, with {@link DefaultPageBuilderService#PROPERTY_COLUMN_COUNT}.
     * Default is {@link DefaultPageBuilderService#CONSTANTE_DEFAULT_COLUMN_COUNT}
     * @return the column count
     */
    public int getColumnCount(  )
    {
        int nNbColumns = CONSTANTE_DEFAULT_COLUMN_COUNT;
        ReferenceItem nbColumns = getPageBuilderParameterDefaultValue( PARAMETER_NB_COLUMNS );

        if ( ( nbColumns != null ) && StringUtils.isNotBlank( nbColumns.getName(  ) ) &&
                StringUtils.isNumeric( nbColumns.getName(  ) ) )
        {
            nNbColumns = Integer.parseInt( nbColumns.getName(  ) );
        }

        return nNbColumns;
    }

    /**
     *
     * @param nColumn the column id
     * @return all WidgetComponent for this column
     */
    public List<WidgetComponent> getWidgetComponents( int nColumn )
    {
        WidgetComponentFilter filter = new WidgetComponentFilter(  );
        filter.setFilterColumn( nColumn );

        List<WidgetComponent> listWidgetComponents = DefaultPageBuilderHome.findByFilter( filter );

        return listWidgetComponents;
    }

    /**
     * Moves the widgetComponent.
     * @param widgetComponent to move, with new values
     * @param nOldColumn previous column id
     * @param nOldOrder previous order
     * @param bCreate <code>true</code> if this is a new widgetComponent, <code>false</code> otherwise.
     */
    public void doMoveWidgetComponent( WidgetComponent widgetComponent, int nOldColumn, int nOldOrder, boolean bCreate )
    {
        int nColumn = widgetComponent.getColumn(  );
        int nOrder = widgetComponent.getOrder(  );

        WidgetComponentFilter filter = new WidgetComponentFilter(  );
        filter.setFilterColumn( nColumn );

        List<WidgetComponent> listColumnWidgetComponent = DefaultPageBuilderHome.findByFilter( filter );

        if ( ( listColumnWidgetComponent != null ) && !listColumnWidgetComponent.isEmpty(  ) )
        {
            // sort by order
            Collections.sort( listColumnWidgetComponent );

            int nMaxOrder = listColumnWidgetComponent.get( listColumnWidgetComponent.size(  ) - 1 ).getOrder(  );

            if ( ( nOldColumn == 0 ) || ( nOldColumn != nColumn ) )
            {
                // was not in this column before, put to the end
                widgetComponent.setOrder( nMaxOrder + 1 );
            }
            else
            {
                if ( nOrder == 0 )
                {
                    nOrder = nMaxOrder + 1;
                }
                else
                {
                    updateWidgetComponentColumns( listColumnWidgetComponent, widgetComponent, nOrder, nOldOrder );
                }

                widgetComponent.setOrder( nOrder );
                widgetComponent.setColumn( nColumn );
            }
        }
        else
        {
            widgetComponent.setOrder( 1 );
        }

        if ( bCreate )
        {
            DefaultPageBuilderHome.create( widgetComponent );
        }
        else
        {
            DefaultPageBuilderHome.update( widgetComponent );
        }
    }

    /**
     * Finds all widgetComponent with column and order set.
     * @return a map where key is the column id, and value is the column's widgetComponent list.
     */
    public Map<String, List<WidgetComponent>> getAllSetWidgetComponents(  )
    {
        Map<String, List<WidgetComponent>> mapWidgetComponents = new HashMap<String, List<WidgetComponent>>(  );

        List<WidgetComponent> listWidgetComponents = DefaultPageBuilderHome.findAll(  );

        for ( WidgetComponent widgetComponent : listWidgetComponents )
        {
            int nColumn = widgetComponent.getColumn(  );

            String strColumn = Integer.toString( nColumn );

            // find this column list
            List<WidgetComponent> listWidgetComponentsColumn = mapWidgetComponents.get( strColumn );

            if ( listWidgetComponentsColumn == null )
            {
                // the list does not exist, create it
                listWidgetComponentsColumn = new ArrayList<WidgetComponent>(  );
                mapWidgetComponents.put( strColumn, listWidgetComponentsColumn );
            }

            listWidgetComponentsColumn.add( widgetComponent );
        }

        return mapWidgetComponents;
    }

    /**
     * Reorders column's widgetComponent
     * @param nColumn the column to reorder
     */
    public void doReorderColumn( int nColumn )
    {
        int nOrder = CONSTANTE_FIRST_ORDER;

        for ( WidgetComponent wc : getWidgetComponents( nColumn ) )
        {
            wc.setOrder( nOrder++ );
            DefaultPageBuilderHome.update( wc );
        }
    }

    /**
     * Remove widget Component
     * @param nIdWidgetComponent the ID of the widget component
     */
    public void doRemoveWidgetComponent( int nIdWidgetComponent )
    {
        DefaultPageBuilderHome.remove( nIdWidgetComponent );
    }

    /**
     * Remove widget Components that are in a column > to the given column max
     * @param nColumnMax the column max
     */
    public void doRemoveByColumnMax( int nColumnMax )
    {
        DefaultPageBuilderHome.removeByColumnMax( nColumnMax );
    }

    /**
     * Builds the map to with column id as key, and <code>true</code> as value if column is well ordered, <code>false</code> otherwise.
     * @return the map
     */
    public Map<String, Boolean> getOrderedColumnsStatus(  )
    {
        Map<String, Boolean> mapOrderedStatus = new HashMap<String, Boolean>(  );
        List<Integer> listColumns = DefaultPageBuilderHome.findColumns(  );

        for ( Integer nIdColumn : listColumns )
        {
            mapOrderedStatus.put( nIdColumn.toString(  ), isWellOrdered( nIdColumn ) );
        }

        return mapOrderedStatus;
    }

    /**
     * Get the model to manage the advanced parameters
     * @param user the current {@link AdminUser}
     * @return the model
     */
    public Map<String, Object> getManageAdvancedParameters( AdminUser user )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_LIST_PARAM_DEFAULT_VALUES, getPageBuilderParamDefaultValues(  ) );
        model.put( MARK_NB_COLUMNS, getColumnCount(  ) );
        model.put( MARK_COLUMNS_STYLE, StyleService.getInstance(  ).getColumnStyles(  ) );

        return model;
    }

    /**
     * Get the list of widget parameter default values
     * @return a {@link ReferenceList}
     */
    public ReferenceList getPageBuilderParamDefaultValues(  )
    {
        return PageBuilderParameterHome.findAll(  );
    }

    /**
     * Get the parameter
     * @param strParameterKey the parameter key
     * @return a {@link ReferenceItem}
     */
    public ReferenceItem getPageBuilderParameterDefaultValue( String strParameterKey )
    {
        return PageBuilderParameterHome.findByKey( strParameterKey );
    }

    /**
     * Get the list of widgets that are not currently put on the default page builder
     * @return the list of widgets
     */
    public Collection<Widget> getWidgetsList(  )
    {
        Collection<Widget> listWidgets = new ArrayList<Widget>(  );
        List<Integer> listWidgetIds = DefaultPageBuilderHome.findWidgetIds(  );

        for ( Widget widget : _widgetService.getPublicMandatoryWidgets(  ) )
        {
            boolean bHasWidget = false;

            for ( int nWidgetId : listWidgetIds )
            {
                if ( nWidgetId == widget.getIdWidget(  ) )
                {
                    bHasWidget = true;

                    break;
                }
            }

            if ( !bHasWidget )
            {
                listWidgets.add( widget );
            }
        }

        return listWidgets;
    }

    /**
     * Update a widget parameter default value
     * @param param the parameter
     */
    public void updatePageBuilderParameterDefaultValue( ReferenceItem param )
    {
        PageBuilderParameterHome.update( param );
    }

    /**
     * Remove all column styles
     */
    public void removeAllColumnStyleFromPageBuilderParameter(  )
    {
        PageBuilderParameterHome.removeAllColumnStyles(  );
    }

    /**
     * Add a new parameter
     * @param param the parameter
     */
    public void addNewPageBuilderParameter( ReferenceItem param )
    {
        PageBuilderParameterHome.create( param );
    }

    /**
     * Determines if the column is well ordered
     * @param nColumn the column id
     * @return true if well ordered, <code>false</code> otherwise.
     */
    private boolean isWellOrdered( int nColumn )
    {
        int nOrder = CONSTANTE_FIRST_ORDER;

        for ( WidgetComponent dc : getWidgetComponents( nColumn ) )
        {
            if ( nOrder != dc.getOrder(  ) )
            {
                return false;
            }

            nOrder++;
        }

        return true;
    }

    /**
     * Update the widgetComponent columns
     * @param listColumnWidgetComponent the list of widget component
     * @param widgetComponent the widget component to change the order
     * @param nOrder the new order
     * @param nOldOrder the old order
     */
    private void updateWidgetComponentColumns( List<WidgetComponent> listColumnWidgetComponent,
        WidgetComponent widgetComponent, int nOrder, int nOldOrder )
    {
        if ( nOrder < nOldOrder )
        {
            for ( WidgetComponent wc : listColumnWidgetComponent )
            {
                if ( !wc.equals( widgetComponent ) )
                {
                    int nCurrentOrder = wc.getOrder(  );

                    if ( ( nCurrentOrder >= nOrder ) && ( nCurrentOrder < nOldOrder ) )
                    {
                        wc.setOrder( nCurrentOrder + 1 );
                        DefaultPageBuilderHome.update( wc );
                    }
                }
            }
        }
        else if ( nOrder > nOldOrder )
        {
            for ( WidgetComponent wc : listColumnWidgetComponent )
            {
                if ( !wc.equals( widgetComponent ) )
                {
                    int nCurrentOrder = wc.getOrder(  );

                    if ( ( nCurrentOrder <= nOrder ) && ( nCurrentOrder > nOldOrder ) )
                    {
                        wc.setOrder( nCurrentOrder - 1 );
                        DefaultPageBuilderHome.update( wc );
                    }
                }
            }
        }
    }
}
