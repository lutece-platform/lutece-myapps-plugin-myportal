/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.myportal.util.auth;

import fr.paris.lutece.portal.service.security.LoginRedirectException;
import fr.paris.lutece.portal.service.security.LuteceAuthentication;
import fr.paris.lutece.portal.service.security.LuteceUser;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author pierre
 */
public class MyPortalAuthentication implements LuteceAuthentication
{
    /**
     * {@inheritDoc }
     */
    public String getAuthServiceName(  )
    {
        return "MyPortal Authentication";
    }

    /**
     * {@inheritDoc }
     */
    public String getAuthType( HttpServletRequest hsr )
    {
        return "MyPortal Authentication";
    }

    /**
     * {@inheritDoc }
     */
    public LuteceUser login( String string, String string1, HttpServletRequest hsr )
        throws LoginException, LoginRedirectException
    {
        return new MyPortalUser( string );
    }

    /**
     * {@inheritDoc }
     */
    public void logout( LuteceUser lu )
    {
    }

    /**
     * {@inheritDoc }
     */
    public LuteceUser getAnonymousUser(  )
    {
        return new MyPortalUser( "Anonymous" );
    }

    /**
     * {@inheritDoc }
     */
    public boolean isUserInRole( LuteceUser lu, HttpServletRequest hsr, String string )
    {
        return true;
    }

    /**
     * {@inheritDoc }
     */
    public String[] getRolesByUser( LuteceUser lu )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    public boolean isExternalAuthentication(  )
    {
        return true;
    }

    /**
     * {@inheritDoc }
     */
    public boolean isDelegatedAuthentication(  )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    public LuteceUser getHttpAuthenticatedUser( HttpServletRequest hsr )
    {
        return new MyPortalUser( "Anonymous" );
    }

    /**
     * {@inheritDoc }
     */
    public String getLoginPageUrl(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public String getDoLoginUrl(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public String getDoLogoutUrl(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public String getNewAccountPageUrl(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public String getViewAccountPageUrl(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public String getLostPasswordPageUrl(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public String getAccessDeniedTemplate(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public String getAccessControledTemplate(  )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    public boolean isUsersListAvailable(  )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    public Collection<LuteceUser> getUsers(  )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    public LuteceUser getUser( String string )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    public boolean isMultiAuthenticationSupported(  )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    public String getIconUrl(  )
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**
     * {@inheritDoc }
     */
    public String getName(  )
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**
     * {@inheritDoc }
     */
    public String getPluginName(  )
    {
        return "";
    }
}
