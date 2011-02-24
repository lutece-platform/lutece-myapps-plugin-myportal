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

import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.plugin.Plugin;

public class WidgetBusinessTest extends LuteceTestCase
{
    private final static int IDWIDGET1 = 1;
    private final static int IDWIDGET2 = 2;
    private final static String NAME1 = "Name1";
    private final static String NAME2 = "Name2";
    private final static String DESCRIPTION1 = "Description1";
    private final static String DESCRIPTION2 = "Description2";
    private final static int IDCATEGORY1 = 1;
    private final static int IDCATEGORY2 = 2;
    private final static String WIDGETTYPE1 = "WidgetType1";
    private final static String WIDGETTYPE2 = "WidgetType2";
    private final static String ICONURL1 = "IconUrl1";
    private final static String ICONURL2 = "IconUrl2";
    private final static int ISMOVABLE1 = 1;
    private final static int ISMOVABLE2 = 2;
    private final static int ISREMOVABLE1 = 1;
    private final static int ISREMOVABLE2 = 2;
    private final static int ISRESIZABLE1 = 1;
    private final static int ISRESIZABLE2 = 2;

    public void testBusiness(  )
    {
        Plugin plugin = PluginService.getPlugin( "myportal" );
        
        // Initialize an object
        Widget widget = new Widget();
        widget.setIdWidget( IDWIDGET1 );
        widget.setName( NAME1 );
        widget.setDescription( DESCRIPTION1 );
        widget.setIdCategory( IDCATEGORY1 );
        widget.setWidgetType( WIDGETTYPE1 );
        widget.setIconUrl( ICONURL1 );
        widget.setIsMovable( ISMOVABLE1 );
        widget.setIsRemovable( ISREMOVABLE1 );
        widget.setIsResizable( ISRESIZABLE1 );

        // Create test
        WidgetHome.create( widget , plugin );
        Widget widgetStored = WidgetHome.findByPrimaryKey( widget.getIdWidget() , plugin );
        assertEquals( widgetStored.getIdWidget() , widget.getIdWidget() );
        assertEquals( widgetStored.getName() , widget.getName() );
        assertEquals( widgetStored.getDescription() , widget.getDescription() );
        assertEquals( widgetStored.getIdCategory() , widget.getIdCategory() );
        assertEquals( widgetStored.getWidgetType() , widget.getWidgetType() );
        assertEquals( widgetStored.getIconUrl() , widget.getIconUrl() );
        assertEquals( widgetStored.getIsMovable() , widget.getIsMovable() );
        assertEquals( widgetStored.getIsRemovable() , widget.getIsRemovable() );
        assertEquals( widgetStored.getIsResizable() , widget.getIsResizable() );

        // Update test
        widget.setIdWidget( IDWIDGET2 );
        widget.setName( NAME2 );
        widget.setDescription( DESCRIPTION2 );
        widget.setIdCategory( IDCATEGORY2 );
        widget.setWidgetType( WIDGETTYPE2 );
        widget.setIconUrl( ICONURL2 );
        widget.setIsMovable( ISMOVABLE2 );
        widget.setIsRemovable( ISREMOVABLE2 );
        widget.setIsResizable( ISRESIZABLE2 );
        WidgetHome.update( widget , plugin );
        widgetStored = WidgetHome.findByPrimaryKey( widget.getIdWidget() , plugin );
        assertEquals( widgetStored.getIdWidget() , widget.getIdWidget() );
        assertEquals( widgetStored.getName() , widget.getName() );
        assertEquals( widgetStored.getDescription() , widget.getDescription() );
        assertEquals( widgetStored.getIdCategory() , widget.getIdCategory() );
        assertEquals( widgetStored.getWidgetType() , widget.getWidgetType() );
        assertEquals( widgetStored.getIconUrl() , widget.getIconUrl() );
        assertEquals( widgetStored.getIsMovable() , widget.getIsMovable() );
        assertEquals( widgetStored.getIsRemovable() , widget.getIsRemovable() );
        assertEquals( widgetStored.getIsResizable() , widget.getIsResizable() );

        // List test
        WidgetHome.getWidgetsList( plugin );

        // Delete test
        WidgetHome.remove( widget.getIdWidget() , plugin );
        widgetStored = WidgetHome.findByPrimaryKey( widget.getIdWidget() , plugin );
        assertNull( widgetStored );
        
    }

}