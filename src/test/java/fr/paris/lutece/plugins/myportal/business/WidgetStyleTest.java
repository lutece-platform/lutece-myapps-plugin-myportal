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
 * StyleTest
 *
 */
public class WidgetStyleTest extends LuteceTestCase
{
    private static final String NAME1 = "Name1";
    private static final String NAME2 = "Name2";
    private static final String CSSCLASS1 = "CssClass1";
    private static final String CSSCLASS2 = "CssClass2";

    /**
     * Test business of class fr.paris.lutece.plugins.myportal.business.Style
     */
    public void testBusiness(  )
    {
        // Initialize an object
        Style style = new Style(  );
        style.setId( WidgetStyleHome.newPrimaryKey(  ) );
        style.setName( NAME1 );
        style.setCssClass( CSSCLASS1 );

        // Create test
        WidgetStyleHome.create( style );

        Style styleStored = WidgetStyleHome.findByPrimaryKey( style.getId(  ) );
        assertEquals( styleStored.getId(  ), style.getId(  ) );
        assertEquals( styleStored.getName(  ), style.getName(  ) );
        assertEquals( styleStored.getCssClass(  ), style.getCssClass(  ) );

        // Update test
        style.setName( NAME2 );
        style.setCssClass( CSSCLASS2 );
        WidgetStyleHome.update( style );
        styleStored = WidgetStyleHome.findByPrimaryKey( style.getId(  ) );
        assertEquals( styleStored.getId(  ), style.getId(  ) );
        assertEquals( styleStored.getName(  ), style.getName(  ) );
        assertEquals( styleStored.getCssClass(  ), style.getCssClass(  ) );

        // List test
        WidgetStyleHome.getStyles(  );
        WidgetStyleHome.getStylesList(  );

        // Delete test
        WidgetStyleHome.remove( style.getId(  ) );
        styleStored = WidgetStyleHome.findByPrimaryKey( style.getId(  ) );
        assertNull( styleStored );
    }
}
