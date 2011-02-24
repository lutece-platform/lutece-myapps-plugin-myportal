

<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="mypage" scope="session" class="fr.paris.lutece.plugins.mypage.web.MyPageJspBean" />

<%
    mypage.init( request, mypage.RIGHT_MANAGE_MYPORTAL_CATEGORY );
    response.sendRedirect( mypage.doCreateWidget( request ) );
%>

