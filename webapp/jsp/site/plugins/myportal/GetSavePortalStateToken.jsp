<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="myPortalApp" scope="request" class="fr.paris.lutece.plugins.myportal.web.MyPortalApp" />

<%= myPortalApp.getSavePortalStateToken( request ) %>