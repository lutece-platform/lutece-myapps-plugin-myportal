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
import fr.paris.lutece.plugins.myportal.business.Category;
import fr.paris.lutece.plugins.myportal.business.CategoryHome;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.plugin.Plugin;

public class CategoryBusinessTest extends LuteceTestCase
{
    private final static int IDCATEGORY1 = 1;
    private final static int IDCATEGORY2 = 2;
    private final static int IDPARENT1 = 1;
    private final static int IDPARENT2 = 2;
    private final static String NAME1 = "Name1";
    private final static String NAME2 = "Name2";
    private final static String DESCRIPTION1 = "Description1";
    private final static String DESCRIPTION2 = "Description2";

    public void testBusiness(  )
    {
        Plugin plugin = PluginService.getPlugin( "myportal" );
        
        // Initialize an object
        Category category = new Category();
        category.setIdCategory( IDCATEGORY1 );
        category.setIdParent( IDPARENT1 );
        category.setName( NAME1 );
        category.setDescription( DESCRIPTION1 );

        // Create test
        CategoryHome.create( category , plugin );
        Category categoryStored = CategoryHome.findByPrimaryKey( category.getIdCategory() , plugin );
        assertEquals( categoryStored.getIdCategory() , category.getIdCategory() );
        assertEquals( categoryStored.getIdParent() , category.getIdParent() );
        assertEquals( categoryStored.getName() , category.getName() );
        assertEquals( categoryStored.getDescription() , category.getDescription() );

        // Update test
        category.setIdCategory( IDCATEGORY2 );
        category.setIdParent( IDPARENT2 );
        category.setName( NAME2 );
        category.setDescription( DESCRIPTION2 );
        CategoryHome.update( category , plugin );
        categoryStored = CategoryHome.findByPrimaryKey( category.getIdCategory() , plugin );
        assertEquals( categoryStored.getIdCategory() , category.getIdCategory() );
        assertEquals( categoryStored.getIdParent() , category.getIdParent() );
        assertEquals( categoryStored.getName() , category.getName() );
        assertEquals( categoryStored.getDescription() , category.getDescription() );

        // List test
        CategoryHome.getCategorysList( plugin );

        // Delete test
        CategoryHome.remove( category.getIdCategory() , plugin );
        categoryStored = CategoryHome.findByPrimaryKey( category.getIdCategory() , plugin );
        assertNull( categoryStored );
        
    }

}