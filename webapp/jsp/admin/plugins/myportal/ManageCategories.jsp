<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="myportalCategory" scope="session" class="fr.paris.lutece.plugins.myportal.web.CategoryJspBean" />

<% myportalCategory.init( request, fr.paris.lutece.plugins.myportal.web.WidgetJspBean.RIGHT_MANAGE_MYPORTAL_WIDGET ); %>
<%= myportalCategory.getManageCategories( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
