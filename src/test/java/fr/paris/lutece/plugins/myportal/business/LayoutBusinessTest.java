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
import fr.paris.lutece.plugins.myportal.business.Layout;
import fr.paris.lutece.plugins.myportal.business.LayoutHome;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.plugin.Plugin;

public class LayoutBusinessTest extends LuteceTestCase
{
    private final static int IDLAYOUT1 = 1;
    private final static int IDLAYOUT2 = 2;
    private final static String NAME1 = "Name1";
    private final static String NAME2 = "Name2";
    private final static String DESCRIPTION1 = "Description1";
    private final static String DESCRIPTION2 = "Description2";
    private final static String LAYOUT1 = "Layout1";
    private final static String LAYOUT2 = "Layout2";

    public void testBusiness(  )
    {
        Plugin plugin = PluginService.getPlugin( "myportal" );
        
        // Initialize an object
        Layout layout = new Layout();
        layout.setIdLayout( IDLAYOUT1 );
        layout.setName( NAME1 );
        layout.setDescription( DESCRIPTION1 );
        layout.setLayout( LAYOUT1 );

        // Create test
        LayoutHome.create( layout , plugin );
        Layout layoutStored = LayoutHome.findByPrimaryKey( layout.getIdLayout() , plugin );
        assertEquals( layoutStored.getIdLayout() , layout.getIdLayout() );
        assertEquals( layoutStored.getName() , layout.getName() );
        assertEquals( layoutStored.getDescription() , layout.getDescription() );
        assertEquals( layoutStored.getLayout() , layout.getLayout() );

        // Update test
        layout.setIdLayout( IDLAYOUT2 );
        layout.setName( NAME2 );
        layout.setDescription( DESCRIPTION2 );
        layout.setLayout( LAYOUT2 );
        LayoutHome.update( layout , plugin );
        layoutStored = LayoutHome.findByPrimaryKey( layout.getIdLayout() , plugin );
        assertEquals( layoutStored.getIdLayout() , layout.getIdLayout() );
        assertEquals( layoutStored.getName() , layout.getName() );
        assertEquals( layoutStored.getDescription() , layout.getDescription() );
        assertEquals( layoutStored.getLayout() , layout.getLayout() );

        // List test
        LayoutHome.getLayoutsList( plugin );

        // Delete test
        LayoutHome.remove( layout.getIdLayout() , plugin );
        layoutStored = LayoutHome.findByPrimaryKey( layout.getIdLayout() , plugin );
        assertNull( layoutStored );
        
    }

}