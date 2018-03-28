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

import fr.paris.lutece.plugins.myportal.business.WidgetComponent;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.test.LuteceTestCase;

import java.util.List;
import java.util.Map;


/**
 *
 * DefaultPageBuilderServiceTest
 *
 */
public class DefaultPageBuilderServiceTest extends LuteceTestCase
{
	private DefaultPageBuilderService defaultPageBuilderService =  SpringContextService.getBean(DefaultPageBuilderService.BEAN_NAME);
	
    /**
     * Test of getWidgetComponents method of class fr.paris.lutece.plugins.myportal.service.DefaultPageBuilderService
     */
    public void testGetWidgetComponents(  )
    {
        System.out.println( "getWidgetComponents" );

        int nNbColumns = defaultPageBuilderService.getColumnCount(  );

        for ( int i = 1; i <= nNbColumns; i++ )
        {
            List<WidgetComponent> result = defaultPageBuilderService.getWidgetComponents( i );
            assertNotNull( result );
        }
    }

    /**
     * Test of getAllSetWidgetComponents method of class fr.paris.lutece.plugins.myportal.service.DefaultPageBuilderService
     */
    public void testGetAllSetWidgetComponents(  )
    {
        System.out.println( "getAllSetWidgetComponents" );

        Map<String, List<WidgetComponent>> result = defaultPageBuilderService.getAllSetWidgetComponents(  );
        assertNotNull( result );
    }

    /**
     * Test of getOrderedColumnsStatus method of class fr.paris.lutece.plugins.myportal.service.DefaultPageBuilderService
     */
    public void testGetOrderedColumnsStatus(  )
    {
        System.out.println( "getOrderedColumnsStatus" );

        Map<String, Boolean> result = defaultPageBuilderService.getOrderedColumnsStatus(  );
        assertNotNull( result );
    }

    /**
     * Test of getManageAdvancedParameters method of class fr.paris.lutece.plugins.myportal.service.DefaultPageBuilderService
     */
    public void testGetManageAdvancedParameters(  )
    {
        System.out.println( "getManageAdvancedParameters" );

        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );

        Map<String, Object> result = defaultPageBuilderService.getManageAdvancedParameters( user );
        assertNotNull( result );
    }
}
