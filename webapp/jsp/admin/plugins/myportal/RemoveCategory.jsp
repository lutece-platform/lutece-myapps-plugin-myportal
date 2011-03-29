<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="myportalCategory" scope="session" class="fr.paris.lutece.plugins.myportal.web.CategoryJspBean" />

<% myportalCategory.init( request, fr.paris.lutece.plugins.myportal.web.WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET ); %>
<% response.sendRedirect( myportalCategory.getConfirmRemoveCategory ( request ) ); %>
