<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%	session.removeAttribute("input1");
	session.removeAttribute("search1");  
	response.sendRedirect("./list.do");	
%>
	

</body>
</html>