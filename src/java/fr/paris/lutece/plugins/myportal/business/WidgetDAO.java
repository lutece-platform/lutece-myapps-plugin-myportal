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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * This class provides Data Access methods for Widget objects
 */
public final class WidgetDAO implements IWidgetDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_widget ) FROM myportal_widget";
    private static final String SQL_QUERY_SELECT = "SELECT a.id_widget, a.name, a.description, a.id_category, a.widget_type, a.icon_mime_type, a.config_data, a.status, b.name, a.id_style, c.name, c.css_class FROM myportal_widget a, myportal_category b, myportal_widget_style c WHERE a.id_category = b.id_category AND a.id_style = c.id_style AND a.id_widget = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO myportal_widget ( id_widget, name, description, id_category, widget_type, icon_content, icon_mime_type, config_data , id_style, status ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM myportal_widget WHERE id_widget = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE myportal_widget SET id_widget = ?, name = ?, description = ?, id_category = ?, widget_type = ?, icon_content = ?, icon_mime_type = ?, config_data = ?, id_style = ?, status = ? WHERE id_widget = ? ";
    private static final String SQL_QUERY_UPDATE_WITHOUT_ICON = "UPDATE myportal_widget SET id_widget = ?, name = ?, description = ?, id_category = ?, widget_type = ?, config_data = ?, id_style = ?, status = ? WHERE id_widget = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT a.id_widget, a.name, a.description, a.id_category, a.widget_type, a.icon_mime_type, a.config_data, a.status, b.name, a.id_style, c.name, c.css_class FROM myportal_widget a, myportal_category b, myportal_widget_style c WHERE a.id_category = b.id_category AND a.id_style = c.id_style";
    private static final String SQL_QUERY_SELECT_RESOURCE_IMAGE = " SELECT icon_content, icon_mime_type FROM myportal_widget WHERE id_widget = ? ";

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
     * @param widget instance of the Widget object to insert
     * @param plugin The plugin
     */
    public void insert( Widget widget, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nIndex = 1;
        widget.setIdWidget( newPrimaryKey( plugin ) );

        daoUtil.setInt( nIndex++, widget.getIdWidget(  ) );
        daoUtil.setString( nIndex++, widget.getName(  ) );
        daoUtil.setString( nIndex++, widget.getDescription(  ) );
        daoUtil.setInt( nIndex++, widget.getIdCategory(  ) );
        daoUtil.setString( nIndex++, widget.getWidgetType(  ) );
        daoUtil.setBytes( nIndex++, widget.getIconContent(  ) );
        daoUtil.setString( nIndex++, widget.getIconMimeType(  ) );
        daoUtil.setString( nIndex++, widget.getConfigData(  ) );
        daoUtil.setInt( nIndex++, widget.getIdStyle(  ) );
        daoUtil.setInt( nIndex++, widget.getStatus(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the widget from the table
     * @param nId The identifier of the widget
     * @param plugin The plugin
     * @return the instance of the Widget
     */
    public Widget load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        Widget widget = null;

        if ( daoUtil.next(  ) )
        {
        	int nIndex = 1;
            widget = new Widget(  );

            widget.setIdWidget( daoUtil.getInt( nIndex++ ) );
            widget.setName( daoUtil.getString( nIndex++ ) );
            widget.setDescription( daoUtil.getString( nIndex++ ) );
            widget.setIdCategory( daoUtil.getInt( nIndex++ ) );
            widget.setWidgetType( daoUtil.getString( nIndex++ ) );
            widget.setIconMimeType( daoUtil.getString( nIndex++ ) );
            widget.setConfigData( daoUtil.getString( nIndex++ ) );
            widget.setStatus( daoUtil.getInt( nIndex++ ) );
            widget.setCategory( daoUtil.getString( nIndex++ ) );
            widget.setIdStyle( daoUtil.getInt( nIndex++ ) );
            widget.setStyle( daoUtil.getString( nIndex++ ) );
            widget.setCssClass( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return widget;
    }

    /**
     * Delete a record from the table
     * @param nWidgetId The identifier of the widget
     * @param plugin The plugin
     */
    public void delete( int nWidgetId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nWidgetId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param widget The reference of the widget
     * @param plugin The plugin
     */
    public void store( Widget widget, boolean bUpdateIcon, Plugin plugin )
    {
    	String strSQL = bUpdateIcon ? SQL_QUERY_UPDATE : SQL_QUERY_UPDATE_WITHOUT_ICON;
        DAOUtil daoUtil = new DAOUtil( strSQL, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, widget.getIdWidget(  ) );
        daoUtil.setString( nIndex++, widget.getName(  ) );
        daoUtil.setString( nIndex++, widget.getDescription(  ) );
        daoUtil.setInt( nIndex++, widget.getIdCategory(  ) );
        daoUtil.setString( nIndex++, widget.getWidgetType(  ) );
        if ( bUpdateIcon )
        {
        	if ( widget.getIconContent(  ) == null )
        	{
        		daoUtil.setBytes( nIndex++, null );
        		daoUtil.setString( nIndex++, StringUtils.EMPTY );
        	}
        	else
        	{
                daoUtil.setBytes( nIndex++, widget.getIconContent(  ) );
                daoUtil.setString( nIndex++, widget.getIconMimeType(  ) );
        	}
        }
        daoUtil.setString( nIndex++, widget.getConfigData(  ) );
        daoUtil.setInt( nIndex++, widget.getIdStyle(  ) );
        daoUtil.setInt( nIndex++, widget.getStatus(  ) );
        daoUtil.setInt( nIndex++, widget.getIdWidget(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the widgets and returns them as a collection
     * @param plugin The plugin
     * @return The Collection which contains the data of all the widgets
     */
    public Collection<Widget> selectWidgetsList( Plugin plugin )
    {
        Collection<Widget> widgetList = new ArrayList<Widget>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
        	int nIndex = 1;
            Widget widget = new Widget(  );

            widget.setIdWidget( daoUtil.getInt( nIndex++ ) );
            widget.setName( daoUtil.getString( nIndex++ ) );
            widget.setDescription( daoUtil.getString( nIndex++ ) );
            widget.setIdCategory( daoUtil.getInt( nIndex++ ) );
            widget.setWidgetType( daoUtil.getString( nIndex++ ) );
            widget.setIconMimeType( daoUtil.getString( nIndex++ ) );
            widget.setConfigData( daoUtil.getString( nIndex++ ) );
            widget.setStatus( daoUtil.getInt( nIndex++ ) );
            widget.setCategory( daoUtil.getString( nIndex++ ) );
            widget.setIdStyle( daoUtil.getInt( nIndex++ ) );
            widget.setStyle( daoUtil.getString( nIndex++ ) );
            widget.setCssClass( daoUtil.getString( nIndex++ ) );
            widgetList.add( widget );
        }

        daoUtil.free(  );

        return widgetList;
    }
    
    /**
     * {@inheritDoc}
     */
    public ImageResource getIconResource( int nWidgetId, Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE_IMAGE, plugin );
        daoUtil.setInt( 1, nWidgetId );
        daoUtil.executeQuery(  );

        ImageResource image = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            image = new ImageResource(  );
            image.setImage( daoUtil.getBytes( nIndex++ ) );
            image.setMimeType( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return image;
    }
}
