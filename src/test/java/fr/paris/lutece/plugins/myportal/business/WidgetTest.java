/*
 * Copyright (c) 2002-2010, Mairie de Paris
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


/**
 *
 * WidgetTest
 *
 */
public class WidgetTest extends LuteceTestCase
{
    private static final String CONFIGDATA1 = "ConfigData1";
    private static final String CONFIGDATA2 = "ConfigData2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final int IDICON1 = 1;
    private static final int IDICON2 = 2;
    private static final int IDCATEGORY1 = 1;
    private static final int IDCATEGORY2 = 2;
    private static final int IDSTYLE1 = 0;
    private static final int IDSTYLE2 = 1;
    private static final boolean ISESSENTIAL1 = true;
    private static final boolean ISESSENTIAL2 = false;
    private static final boolean ISNEW1 = true;
    private static final boolean ISNEW2 = false;
    private static final String NAME1 = "Name1";
    private static final String NAME2 = "Name2";
    private static final int STATUS1 = 1;
    private static final int STATUS2 = 2;
    private static final String WIDGETTYPE1 = "WIDGETTYPE1";
    private static final String WIDGETTYPE2 = "WIDGETTYPE2";

    /**
     * Test business of class fr.paris.lutece.plugins.myportal.business.Widget
     */
    public void testBusiness(  )
    {
        // Initialize an object
        Widget widget = new Widget(  );
        widget.setIdWidget( WidgetHome.newPrimaryKey(  ) );
        widget.setConfigData( CONFIGDATA1 );
        widget.setDescription( DESCRIPTION1 );
        widget.setIdIcon( IDICON1 );
        widget.setIdCategory( IDCATEGORY1 );
        widget.setIdStyle( IDSTYLE1 );
        widget.setIsEssential( ISESSENTIAL1 );
        widget.setIsNew( ISNEW1 );
        widget.setName( NAME1 );
        widget.setStatus( STATUS1 );
        widget.setWidgetType( WIDGETTYPE1 );

        // Create test
        WidgetHome.create( widget );

        Widget widgetStored = WidgetHome.findByPrimaryKey( widget.getIdWidget(  ) );
        assertEquals( widgetStored.getIdWidget(  ), widget.getIdWidget(  ) );
        assertEquals( widgetStored.getConfigData(  ), widget.getConfigData(  ) );
        assertEquals( widgetStored.getDescription(  ), widget.getDescription(  ) );
        assertEquals( widgetStored.getIdIcon(  ), widget.getIdIcon(  ) );
        assertEquals( widgetStored.getIdCategory(  ), widget.getIdCategory(  ) );
        assertEquals( widgetStored.getIdStyle(  ), widget.getIdStyle(  ) );
        assertEquals( widgetStored.getIsEssential(  ), widget.getIsEssential(  ) );
        assertEquals( widgetStored.getIsNew(  ), widget.getIsNew(  ) );
        assertEquals( widgetStored.getName(  ), widget.getName(  ) );
        assertEquals( widgetStored.getStatus(  ), widget.getStatus(  ) );
        assertEquals( widgetStored.getName(  ), widget.getName(  ) );
        assertEquals( widgetStored.getWidgetType(  ), widget.getWidgetType(  ) );

        // Update test
        widget.setConfigData( CONFIGDATA2 );
        widget.setDescription( DESCRIPTION2 );
        widget.setIdIcon( IDICON2 );
        widget.setIdCategory( IDCATEGORY2 );
        widget.setIdStyle( IDSTYLE2 );
        widget.setIsEssential( ISESSENTIAL2 );
        widget.setIsNew( ISNEW2 );
        widget.setName( NAME2 );
        widget.setStatus( STATUS2 );
        widget.setWidgetType( WIDGETTYPE2 );
        WidgetHome.update( widget );
        widgetStored = WidgetHome.findByPrimaryKey( widget.getIdWidget(  ) );
        assertEquals( widgetStored.getIdWidget(  ), widget.getIdWidget(  ) );
        assertEquals( widgetStored.getConfigData(  ), widget.getConfigData(  ) );
        assertEquals( widgetStored.getDescription(  ), widget.getDescription(  ) );
        assertEquals( widgetStored.getIdIcon(  ), widget.getIdIcon(  ) );
        assertEquals( widgetStored.getIdCategory(  ), widget.getIdCategory(  ) );
        assertEquals( widgetStored.getIdStyle(  ), widget.getIdStyle(  ) );
        assertEquals( widgetStored.getIsEssential(  ), widget.getIsEssential(  ) );
        assertEquals( widgetStored.getIsNew(  ), widget.getIsNew(  ) );
        assertEquals( widgetStored.getName(  ), widget.getName(  ) );
        assertEquals( widgetStored.getStatus(  ), widget.getStatus(  ) );
        assertEquals( widgetStored.getName(  ), widget.getName(  ) );
        assertEquals( widgetStored.getWidgetType(  ), widget.getWidgetType(  ) );

        // List test
        WidgetHome.getWidgetsList(  );

        // Delete test
        WidgetHome.remove( widget.getIdWidget(  ) );
        widgetStored = WidgetHome.findByPrimaryKey( widget.getIdWidget(  ) );
        assertNull( widgetStored );
    }
}
