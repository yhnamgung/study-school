package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogInProcess")
public class LogInProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	
	String driver="oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		actionDo(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		actionDo(request,response);
	}
	private void actionDo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		String id,pw,name,phone,gender,dbpw;
		id=request.getParameter("id");
		pw=request.getParameter("pw");
		name= "";
		phone= "";
		gender= "";
		dbpw="";
		
		String query="select * from member where id=? and pw=?";
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, uid, upw);
						
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			resultSet = pstmt.executeQuery();

			if(resultSet.next()) {
				id=resultSet.getString("id");
				dbpw=resultSet.getString("pw");
				name=resultSet.getString("name");
				phone=resultSet.getString("phone");
				gender=resultSet.getString("gender");
			}else {
				System.out.println("해당되는 id와 pw로 검색하니까 안나옴!!");
				writer.println( "[{\"results\":\"fail\",\"desc\":\"아이디 또는 비밀번호가 맞지 않습니다.\"}]" );
				writer.close();
				return;
			}

			
			HttpSession session=request.getSession();
			session.setAttribute("name", name);
			session.setAttribute("id", id);
			session.setAttribute("pw", pw);
			session.setMaxInactiveInterval(60*60);
			
			System.out.println(session.getAttribute("name"));
			System.out.println(session.getAttribute("id"));
			System.out.println(session.getAttribute("pw"));
		
			writer.println( "[{\"results\":\"ok\",\"desc\":\"비밀번호가 합격!\"}]" );
			writer.close();
			
						
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet!=null) resultSet.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}