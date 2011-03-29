<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="myportalCategory" scope="session" class="fr.paris.lutece.plugins.myportal.web.CategoryJspBean" />

<% myportalCategory.init( request, myportalCategory.RIGHT_MANAGE_MYPORTAL_CATEGORY ); %>
<% response.sendRedirect( myportalCategory.doRemoveCategory( request ) ); %>
