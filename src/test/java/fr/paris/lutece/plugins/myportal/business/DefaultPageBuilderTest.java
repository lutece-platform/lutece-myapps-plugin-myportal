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

import fr.paris.lutece.test.LuteceTestCase;


/**
 *
 * CategoryTest
 *
 */
public class DefaultPageBuilderTest extends LuteceTestCase
{
    private static final int COLUMN1 = 1;
    private static final int COLUMN2 = 2;
    private static final int IDWIDGET1 = 1;
    private static final int IDWIDGET2 = 2;
    private static final int ORDER1 = 1;
    private static final int ORDER2 = 2;

    /**
     * Test business of class fr.paris.lutece.plugins.myportal.business.DefaultPageBuilder
     */
    public void testBusiness(  )
    {
        // Initialize an object
        WidgetComponent widgetComponent = new WidgetComponent(  );
        widgetComponent.setIdWidgetComponent( DefaultPageBuilderHome.newPrimaryKey(  ) );
        widgetComponent.setColumn( COLUMN1 );
        widgetComponent.setIdWidget( IDWIDGET1 );
        widgetComponent.setOrder( ORDER1 );

        // Create test
        DefaultPageBuilderHome.create( widgetComponent );

        WidgetComponent widgetComponentStored = DefaultPageBuilderHome.findByPrimaryKey( widgetComponent.getIdWidgetComponent(  ) );
        assertEquals( widgetComponentStored.getIdWidgetComponent(  ), widgetComponent.getIdWidgetComponent(  ) );
        assertEquals( widgetComponentStored.getIdWidget(  ), widgetComponent.getIdWidget(  ) );
        assertEquals( widgetComponentStored.getOrder(  ), widgetComponent.getOrder(  ) );

        // Update test
        widgetComponent.setColumn( COLUMN2 );
        widgetComponent.setIdWidget( IDWIDGET2 );
        widgetComponent.setOrder( ORDER2 );
        DefaultPageBuilderHome.update( widgetComponent );
        widgetComponentStored = DefaultPageBuilderHome.findByPrimaryKey( widgetComponent.getIdWidgetComponent(  ) );
        assertEquals( widgetComponentStored.getIdWidgetComponent(  ), widgetComponent.getIdWidgetComponent(  ) );
        assertEquals( widgetComponentStored.getIdWidget(  ), widgetComponent.getIdWidget(  ) );
        assertEquals( widgetComponentStored.getOrder(  ), widgetComponent.getOrder(  ) );

        // List test
        DefaultPageBuilderHome.findAll(  );
        DefaultPageBuilderHome.findColumns(  );
        DefaultPageBuilderHome.findMaxOrder(  );

        // Delete test
        DefaultPageBuilderHome.remove( widgetComponent.getIdWidgetComponent(  ) );
        widgetComponentStored = DefaultPageBuilderHome.findByPrimaryKey( widgetComponent.getIdWidgetComponent(  ) );
        assertNull( widgetComponentStored );
    }
}
