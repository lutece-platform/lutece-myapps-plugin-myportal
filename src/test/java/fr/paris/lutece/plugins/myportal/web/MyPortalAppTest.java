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

import java.util.Collections;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.myportal.business.UserPageConfig;
import fr.paris.lutece.plugins.myportal.business.UserPageConfigHome;
import fr.paris.lutece.plugins.myportal.business.page.PageConfig;
import fr.paris.lutece.plugins.myportal.business.page.TabConfig;
import fr.paris.lutece.plugins.myportal.service.PageConfigJsonUtil;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.MokeHttpServletRequest;

/**
 *
 * MyPortalAppTest
 *
 */
public class MyPortalAppTest extends LuteceTestCase
{
    public void testDoSavePortalState( ) throws Exception
    {
        MyPortalApp app = new MyPortalApp( );

        UserPageConfig originalConfig = UserPageConfigHome.findByPrimaryKey( "Anonymous" );
        String strJson = originalConfig.getUserPageConfig( );
        PageConfig pageConfig = PageConfigJsonUtil.parseJson( strJson );
        pageConfig.setTabList( Collections.<TabConfig> emptyList( ) );
        String strNewJson = PageConfigJsonUtil.buildJson( pageConfig );
        assertNotSame( "If the JSON are the same, the modification is not tested", strNewJson, strJson );

        try
        {
            MokeHttpServletRequest request = new MokeHttpServletRequest( );
            request.addMokeParameters( "portalState", strNewJson );
            String strReturnCode = app.doSavePortalState( request );
            JsonNode actual = new ObjectMapper( ).readTree( strReturnCode );
            JsonNode expected = new ObjectMapper( ).readTree( "{\"result\":\"SAVED\",\"status\":\"OK\"}" );
            assertEquals( expected, actual );

            String strNewJsonDb = UserPageConfigHome.findByPrimaryKey( "Anonymous" ).getUserPageConfig( );
            assertEquals( strNewJson, strNewJsonDb );
        }
        finally
        {
            // Restore the original config in case other tests depend on it
            UserPageConfigHome.update( originalConfig );
        }

    }

    private void ParseErrorDoSavePortalState( String strParseErrorJson ) throws Exception
    {
        MyPortalApp app = new MyPortalApp( );

        UserPageConfig originalConfig = UserPageConfigHome.findByPrimaryKey( "Anonymous" );

        try
        {
            MokeHttpServletRequest request = new MokeHttpServletRequest( );
            request.addMokeParameters( "portalState", strParseErrorJson );
            String strReturnCode = app.doSavePortalState( request );
            JsonNode actual = new ObjectMapper( ).readTree( strReturnCode );
            assertEquals( "the status field should be", "ERROR", actual.get( "status" ).asText( ) );
            assertEquals( "the errorCode field should be", "PARSE_ERROR", actual.get( "errorCode" ).asText( ) );
            assertTrue( "The message field should have some error message", actual.get( "message" ).asText( ).length( ) > 0 );

            String strNewJsonDb = UserPageConfigHome.findByPrimaryKey( "Anonymous" ).getUserPageConfig( );
            assertEquals( "When in error, the database value shouldn't be modified", originalConfig.getUserPageConfig( ), strNewJsonDb );
        }
        finally
        {
            // Restore the original config in case other tests depend on it
            UserPageConfigHome.update( originalConfig );
        }

    }

    public void testDoSavePortalStateNull( ) throws Exception
    {
        ParseErrorDoSavePortalState( null );
    }

    public void testDoSavePortalStateIncomplete( ) throws Exception
    {
        ParseErrorDoSavePortalState( "{}" );
    }

    public void testDoSavePortalStateMalformed( ) throws Exception
    {
        ParseErrorDoSavePortalState( "{]" );
    }

    public void testDoSavePortalStateNonObjectArray( ) throws Exception
    {
        ParseErrorDoSavePortalState( "[1,2,3]" );
    }

    public void testDoSavePortalStateNonObjectLiteralString( ) throws Exception
    {
        ParseErrorDoSavePortalState( "hello" );
    }

    public void testDoSavePortalStateNonObjectLiteralNumber( ) throws Exception
    {
        ParseErrorDoSavePortalState( "42" );
    }
}
