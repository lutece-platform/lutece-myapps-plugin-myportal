<jsp:useBean id="myportalAddContent" scope="request" class="fr.paris.lutece.plugins.myportal.web.MyPortalApp" />

<%
    response.sendRedirect( myportalAddContent.doAddContent( request ));
%>
