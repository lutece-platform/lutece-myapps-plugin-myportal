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
package fr.paris.lutece.plugins.myportal;

import fr.paris.lutece.plugins.myportal.business.CategoryTest;
import fr.paris.lutece.plugins.myportal.business.ColumnStyleTest;
import fr.paris.lutece.plugins.myportal.business.DefaultPageBuilderTest;
import fr.paris.lutece.plugins.myportal.business.UserPageConfigTest;
import fr.paris.lutece.plugins.myportal.business.WidgetStyleTest;
import fr.paris.lutece.plugins.myportal.business.WidgetTest;
import fr.paris.lutece.plugins.myportal.business.parameter.PageBuilderParameterTest;
import fr.paris.lutece.plugins.myportal.service.DefaultPageBuilderServiceTest;
import fr.paris.lutece.plugins.myportal.service.WidgetImgProviderTest;
import fr.paris.lutece.plugins.myportal.service.handler.WidgetHandlerServiceTest;
import fr.paris.lutece.plugins.myportal.web.CategoryJspBeanTest;
import fr.paris.lutece.plugins.myportal.web.DefaultPageBuilderJspBeanTest;
import fr.paris.lutece.plugins.myportal.web.MyPortalAdminDashboardComponentTest;
import fr.paris.lutece.plugins.myportal.web.WidgetJspBeanTest;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * This class is the main test suite for the package fr.paris.lutece.plugins.myportal
 */
public final class AllTests
{
    /**
     * A set of tests
     * @return Test the tests
     */
    public static Test suite(  )
    {
        TestSuite suite = new TestSuite( "*** Tests Plugin MyPortal " );

        //$JUnit-BEGIN$
        suite.addTest( new TestSuite( PageBuilderParameterTest.class ) );
        suite.addTest( new TestSuite( CategoryTest.class ) );
        suite.addTest( new TestSuite( DefaultPageBuilderTest.class ) );
        suite.addTest( new TestSuite( WidgetStyleTest.class ) );
        suite.addTest( new TestSuite( ColumnStyleTest.class ) );
        suite.addTest( new TestSuite( UserPageConfigTest.class ) );
        suite.addTest( new TestSuite( WidgetTest.class ) );
        suite.addTest( new TestSuite( WidgetHandlerServiceTest.class ) );
        suite.addTest( new TestSuite( DefaultPageBuilderServiceTest.class ) );
        suite.addTest( new TestSuite( WidgetImgProviderTest.class ) );
        suite.addTest( new TestSuite( CategoryJspBeanTest.class ) );
        suite.addTest( new TestSuite( DefaultPageBuilderJspBeanTest.class ) );
        suite.addTest( new TestSuite( MyPortalAdminDashboardComponentTest.class ) );
        suite.addTest( new TestSuite( WidgetJspBeanTest.class ) );

        //$JUnit-END$
        return suite;
    }
}
