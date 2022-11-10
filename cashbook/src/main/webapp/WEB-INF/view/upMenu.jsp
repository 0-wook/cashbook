<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 다른 페이지의 부분으로 사용되는 페이지-->
   <!-- 메인 메뉴 -->


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

<style>
   .nav-wrap { height: 70px; padding: 10px 0 0 15px; box-shadow: 0 6px 20px 0 rgb(0 0 0 / 20%); }
   .logo-a { color:#6495ed; font-size:2em; font-weight:500;}
   .logo-a:hover { color:#6495ed; text-decoration:none; }
   .rightNav { display:inline-block; margin:15px; }
   .nav-a { color:#6495ed; margin:0 10px; }
   
    @media (max-width:570px){
       .nav-wrap{ text-align:center; }
       .logo-a{ display:block; }
       .rightNav{ float:none !important; } 
   }
</style>

</head>
<body>
   <div class="container-fluid nav-wrap">
      <a href="<%=request.getContextPath()%>/CashBookListByMonthController" class="logo-a">Gagyebu</a>
      
      <div class="float-right rightNav">
      <!-- 로그인했을 경우에만 -->
      <c:if test="${not empty sessionMemberId}">
      <a href="<%=request.getContextPath()%>/SelectMemberOneController?MemberId=<%=session.getAttribute("sessionMemberId")%>">[<%=session.getAttribute("sessionMemberId")%>]</a>님 반갑습니다. &nbsp;&nbsp;
      <a href = "<%=request.getContextPath()%>/LogoutController">로그아웃</a>
      </c:if>
      </div>
   </div>
   
</body>
</html>