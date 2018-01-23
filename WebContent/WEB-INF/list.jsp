<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.vo.*" %>
<%@ page import = "java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="g2c" method="post">
	<input type="hidden" name="a" value="add">
	<table border=1 width=500>
		<tr>
			<td>이름</td><td><input type="text" name="name"></td>
			<td>비밀번호</td><td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td colspan=4><textarea name="content" cols=60 rows=5></textarea></td>
		</tr>
		<tr>
			<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
		</tr>
	</table>
	</form>
	<br/>
<% 
	List<GuestBookVO> l = (List<GuestBookVO>) request.getAttribute("l");
	
	if(l!=null){
		for(GuestBookVO e : l) { %>
			<table width=510 border=1>
				<tr>
					<td><%= e.getNo() %></td>
					<td><%= e.getName() %></td>
					<td><%= e.getDate() %></td>
					<td><a href="g2c?a=deleteform&no=<%= e.getNo()%>">삭제</a></td>
				</tr>
				<tr>
					<td colspan=4><%= e.getContent() %></td>
				</tr>
			</table>
		    <br/>
<%		} 
	}%>
</body>
</html>