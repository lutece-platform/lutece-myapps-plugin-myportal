<jsp:useBean id="myPortalApp" scope="request" class="fr.paris.lutece.plugins.myportal.web.MyPortalApp" />

<%
    response.sendRedirect( myPortalApp.doDelTab( request ));
%>
