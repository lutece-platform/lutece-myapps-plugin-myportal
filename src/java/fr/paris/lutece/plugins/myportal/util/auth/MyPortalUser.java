/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.paris.lutece.plugins.myportal.util.auth;

import fr.paris.lutece.portal.service.security.LuteceUser;

/**
 *
 * @author pierre
 */
public class MyPortalUser extends LuteceUser
{

    public MyPortalUser( String strUserName )
    {

        super( strUserName , new MyPortalAuthentication() );
    }
}
