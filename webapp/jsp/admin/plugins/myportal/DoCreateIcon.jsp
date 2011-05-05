<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:useBean id="myportalIcon" scope="session" class="fr.paris.lutece.plugins.myportal.web.IconJspBean" />
<% 
	myportalIcon.init( request, fr.paris.lutece.plugins.myportal.web.WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET); 
	response.sendRedirect(myportalIcon.doCreateIcon(request) );
%>
