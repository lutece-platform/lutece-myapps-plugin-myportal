<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="myportalWidget" scope="session" class="fr.paris.lutece.plugins.myportal.web.WidgetJspBean" />

<% myportalWidget.init( request, myportalWidget.RIGHT_MANAGE_MYPORTAL_WIDGET ); %>
<% response.sendRedirect( myportalWidget.doCreateWidget( request ) ); %>
