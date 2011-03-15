<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="defaultPageBuilder" scope="request" class="fr.paris.lutece.plugins.myportal.web.DefaultPageBuilderJspBean" />

<% defaultPageBuilder.init( request, defaultPageBuilder.RIGHT_DEFAULT_PAGE_BUILDER ) ; %>
<% response.sendRedirect( defaultPageBuilder.doRemoveWidgetComponent( request ) ); %>
