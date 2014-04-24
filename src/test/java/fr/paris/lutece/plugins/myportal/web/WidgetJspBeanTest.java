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
package fr.paris.lutece.plugins.myportal.web;

import fr.paris.lutece.plugins.myportal.business.Widget;
import fr.paris.lutece.plugins.myportal.business.WidgetHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.MokeHttpServletRequest;

import java.util.List;


/**
 *
 * WidgetJspBeanTest
 *
 */
public class WidgetJspBeanTest extends LuteceTestCase
{
    // PARAMETERS
    private static final String PARAMETER_ID_WIDGET = "id_widget";

    /**
     * Test of getManageWidgets method of class fr.paris.lutece.plugins.myportal.web.WidgetJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetManageWidgets(  ) throws AccessDeniedException
    {
        System.out.println( "getManageWidgets" );

        MokeHttpServletRequest request = new MokeHttpServletRequest(  );

        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
        request.registerAdminUserWithRigth( user, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

        WidgetJspBean instance = new WidgetJspBean(  );
        instance.init( request, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

        String result = instance.getManageWidgets( request );

        assertNotNull( result );
    }

    /**
     * Test of getCreateWidget method of class fr.paris.lutece.plugins.myportal.web.WidgetJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetCreateWidget(  ) throws AccessDeniedException
    {
        System.out.println( "getCreateWidget" );

        MokeHttpServletRequest request = new MokeHttpServletRequest(  );

        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
        request.registerAdminUserWithRigth( user, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

        WidgetJspBean instance = new WidgetJspBean(  );
        instance.init( request, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

        String result = instance.getCreateWidget( request );

        assertNotNull( result );
    }

    /**
     * Test of getConfirmRemoveWidget method of class fr.paris.lutece.plugins.myportal.web.WidgetJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetConfirmRemoveWidget(  ) throws AccessDeniedException
    {
        System.out.println( "getConfirmRemoveWidget" );

        MokeHttpServletRequest request = new MokeHttpServletRequest(  );

        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
        request.registerAdminUserWithRigth( user, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

        WidgetJspBean instance = new WidgetJspBean(  );
        instance.init( request, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

        String result = instance.getConfirmRemoveWidget( request );

        assertNotNull( result );
    }

    /**
     * Test of getModifyWidget method of class fr.paris.lutece.plugins.myportal.web.WidgetJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetModifyWidget(  ) throws AccessDeniedException
    {
        System.out.println( "getModifyWidget" );

        List<Widget> listWidgets = (List<Widget>) WidgetHome.getWidgetsList(  );

        if ( listWidgets.size(  ) > 0 )
        {
            Widget widget = listWidgets.get( 0 );
            MokeHttpServletRequest request = new MokeHttpServletRequest(  );
            request.addMokeParameters( PARAMETER_ID_WIDGET, Integer.toString( widget.getIdWidget(  ) ) );

            AdminUser user = AdminUserHome.findUserByLogin( "admin" );
            user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
            request.registerAdminUserWithRigth( user, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

            WidgetJspBean instance = new WidgetJspBean(  );
            instance.init( request, WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET );

            String result = instance.getModifyWidget( request );

            assertNotNull( result );
        }
    }
}
