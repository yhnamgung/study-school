<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.study.jsp.MemberDTO" %>
<%@ page import="com.study.jsp.MemberDAO" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	MemberDAO memberDAO = new MemberDAO();
	ArrayList<MemberDTO> DTOs = memberDAO.memberSelect();
	//where문 조건이 여러개가 되면 select를 여러개 만들어놓고 쓸 수 있음
	for(int i=0;i<DTOs.size();i++){
		MemberDTO DTO = DTOs.get(i);
		String id = DTO.getId();
		String pw = DTO.getPw();
		String name = DTO.getName();
		String phone = DTO.getPhone();
		String gender = DTO.getGender();
		
		out.println("아이디 : "+id + ", 비밀번호 : "+pw+", 이름 : "+name+", 연락처 : "+phone+", 성별 : "+gender+"<br>");
		
	}
%>

</body>
</html>