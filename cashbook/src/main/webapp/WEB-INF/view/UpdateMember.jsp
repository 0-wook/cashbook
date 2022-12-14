<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%
	Member member = (Member)request.getAttribute("member");

	//디버깅
	System.out.println(member +"<--member UpdateMember.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<jsp:include page ="./upMenu.jsp"></jsp:include>
<style>
	.bottom {margin-bottom:60px;}
	.top {margin-top:50px;}
</style>
</head>
<body>
	<div  class = "container">
		<h1 class = "bottom top text-center">정보 수정하기</h1>
		<form method = "post" action = "<%=request.getContextPath()%>/UpdateMemberController">
			<table class="table table-bordered text-center">
				<colgroup>
					<col width = "20%">
					<col width = "*">
				</colgroup>
				<tr>
					<th class="table-info">Id</th>
					<td><input type = "text" name="memberId" readonly = "readonly" value="<%=member.getMemberId()%>"  class = "form-control"></td>
				</tr>
				<tr>
					<th class="table-info">이름</th>
					<td><input type = "text" name="memberName" value="<%=member.getMemberName()%>"  class = "form-control"></td>
				</tr>
				<tr>
					<th class="table-info">주소</th>
					<td><input type = "text" name="memberAddress" value="<%=member.getMemberAddress()%>"  class = "form-control"></td>
				</tr>
				<tr>
					<th class="table-info">성별</th>
					<td>
						<select name = "memberGender" class ="form-control"> 
							<%
								if("남".equals(member.getMemberGender())){
							%>
									<option value="<%=member.getMemberGender()%>" selected="selected"><%=member.getMemberGender()%></option>
									<option value="여">여</option>	
							<%
								} else {
							%>
									<option value="남">남</option>
									<option value="<%=member.getMemberGender()%>" selected="selected"><%=member.getMemberGender()%></option>
							<%
								}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<th class="table-info">휴대폰 번호</th>
					<td><input type = "text" name="memberPhone" value="<%=member.getMemberPhone()%>"  class = "form-control"></td>
				</tr>
				<tr>
					<th class="table-info">가입 날짜</th>
					<td><input type = "text" name="createDate" readonly = "readonly" value="<%=member.getCreateDate()%>"  class = "form-control"></td>
				</tr>
				<tr>
					<th class="table-info">현재 비밀번호</th>
					<td><input type = "password" name="memberPw"  class = "form-control"></td>
				</tr>
				<tr>
					<th class="table-info">변경할 비밀번호</th>
					<td><input type = "password" name="ChangeMemberPw"  class = "form-control"></td>
				</tr>
				<tr>
					<th class="table-info">비밀번호 확인</th>
					<td><input type = "password" name="CheckMemberPw"  class = "form-control"></td>
				</tr>
			</table>
			<a href="<%=request.getContextPath()%>/SelectMemberOneController"  class="btn btn-secondary">이전</a>
			<button type = "submit"  class="btn btn-info float-right">수정</button>
		</form>
	</div>
</body>
</html>