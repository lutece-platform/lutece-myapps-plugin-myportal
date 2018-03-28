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

import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.business.page.WidgetConfig;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * PageConfigJsonUtil
 *
 */
public final class PageConfigJsonUtil
{
    private static final String ATTR_PAGE = "page";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_TABS = "tabs";
    private static final String ATTR_WIDGETS = "widgets";
    private static final String ATTR_ID = "id";
    private static final String ATTR_STATE = "state";
    private static final String ATTR_COLUMN = "column";

    /**
     * Constructor
     */
    private PageConfigJsonUtil( )
    {
    }

    /**
     * Create a PageConfig object from a JSON flow
     * 
     * @param jsonflow
     *            The JSON flow
     * @return A PageConfig object
     */
    public static PageConfig parseJson( String jsonflow )
    {
        PageConfig pageConfig = new PageConfig( );

        try
        {
            JSONTokener tokener = new JSONTokener( jsonflow );
            JSONObject json = (JSONObject) tokener.nextValue( );
            JSONObject jsonPage = (JSONObject) json.get( ATTR_PAGE );
            pageConfig.setName( jsonPage.getString( ATTR_NAME ) );

            JSONArray jsonTabs = jsonPage.getJSONArray( ATTR_TABS );
            List<TabConfig> listTabs = new ArrayList<TabConfig>( );

            for ( int i = 0; i < jsonTabs.size( ); i++ )
            {
                JSONObject jsonTab = jsonTabs.getJSONObject( i );
                TabConfig tab = new TabConfig( );
                tab.setName( jsonTab.getString( ATTR_NAME ) );

                JSONArray jsonWidgets = jsonTab.getJSONArray( ATTR_WIDGETS );
                List<WidgetConfig> listWidgets = new ArrayList<WidgetConfig>( );

                for ( int j = 0; j < jsonWidgets.size( ); j++ )
                {
                    JSONObject jsonWidget = jsonWidgets.getJSONObject( j );
                    WidgetConfig widget = new WidgetConfig( );
                    widget.setWidgetId( jsonWidget.getInt( ATTR_ID ) );
                    widget.setWidgetState( jsonWidget.getInt( ATTR_STATE ) );
                    widget.setColumn( jsonWidget.getInt( ATTR_COLUMN ) );
                    listWidgets.add( widget );
                }

                tab.setWidgetList( listWidgets );
                listTabs.add( tab );
            }

            pageConfig.setTabList( listTabs );
        }
        catch( JSONException e )
        {
            throw new RuntimeException( "JSON Parsing Error : " + e.getMessage( ), e );
        }

        return pageConfig;
    }

    /**
     * Build a JSON flow from a PageConfig object
     * 
     * @param pageConfig
     *            The Page Config object
     * @return The JSON flow
     */
    static String buildJson( PageConfig pageConfig )
    {
        JSONObject json = new JSONObject( );
        JSONObject jsonPage = new JSONObject( );
        JSONArray jsonTabs = new JSONArray( );

        for ( TabConfig tab : pageConfig.getTabList( ) )
        {
            JSONObject jsonTab = new JSONObject( );
            JSONArray jsonWidgets = new JSONArray( );

            if ( tab.getWidgetList( ) != null )
            {
                for ( WidgetConfig widget : tab.getWidgetList( ) )
                {
                    JSONObject jsonWidget = new JSONObject( );
                    jsonWidget.accumulate( ATTR_ID, widget.getWidgetId( ) );
                    jsonWidget.accumulate( ATTR_STATE, widget.getWidgetState( ) );
                    jsonWidget.accumulate( ATTR_COLUMN, widget.getColumn( ) );
                    jsonWidgets.add( jsonWidget );
                }
            }

            jsonTab.accumulate( ATTR_NAME, tab.getName( ) );
            jsonTab.accumulate( ATTR_WIDGETS, jsonWidgets );
            jsonTabs.add( jsonTab );
        }

        jsonPage.accumulate( ATTR_NAME, pageConfig.getName( ) );
        jsonPage.accumulate( ATTR_TABS, jsonTabs );
        json.accumulate( ATTR_PAGE, jsonPage );

        return json.toString( );
    }
}
