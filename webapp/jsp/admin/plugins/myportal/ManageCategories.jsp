<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="myportalCategory" scope="session" class="fr.paris.lutece.plugins.myportal.web.CategoryJspBean" />

<% myportalCategory.init( request, myportalCategory.RIGHT_MANAGE_MYPORTAL_CATEGORY ); %>
<%= myportalCategory.getManageCategories( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
