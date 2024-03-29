package com.study.jsp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDao {
	
	public static final int MEMBER_NONEXISTENT=0;
	public static final int MEMBER_EXISTENT=1;
	public static final int MEMBER_JOIN_FAIL=0;
	public static final int MEMBER_JOIN_SUCCESS=1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD=0;
	public static final int MEMBER_LOGIN_SUCCESS=1;
	public static final int MEMBER_LOGIN_IS_NOT=-1;
	
	private static MemberDao instance = new MemberDao();
	DataSource dataSource=null;	
	private MemberDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MemberDao getInstance() {
		return instance;
	}
	
	public int insertMember(MemberDto dto) {
		int ri = 0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String query = "insert into members values (?,?,?,?,?,?,?,?,?)";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4,dto.getName());
			pstmt.setString(5, dto.geteMail());
			pstmt.setString(6, dto.getAddress());
			pstmt.setString(7, dto.getPoint());
			pstmt.setString(8, "-");
			pstmt.setTimestamp(9, dto.getrDate());
			
			pstmt.executeQuery();
			ri = MemberDao.MEMBER_JOIN_SUCCESS;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public int confirmId(String id) {
		int ri =0;
		
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet set =null;
		String query = "select userid from members where userid=?";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			set = pstmt.executeQuery();
			
			if(set.next()) {
				ri=MemberDao.MEMBER_EXISTENT; //1
			}else {
				ri = MemberDao.MEMBER_NONEXISTENT; //0
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(set!=null)set.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public int userCheck(String id, String pw) {
		int ri =0;
		String dbPw;
		
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet set =null;
		String query = "select userpw from members where userid=?";
		
		try {
			con = dataSource.getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, id);
			set=pstmt.executeQuery();
			
			if(set.next()) {
				dbPw = set.getString("userpw");
				if(dbPw.equals(pw)) {
					System.out.println("login OK");
					ri = MemberDao.MEMBER_LOGIN_SUCCESS;
				}else {
					System.out.println("login fail");
					ri = MemberDao.MEMBER_LOGIN_PW_NO_GOOD;
				}
			}else {
				System.out.println("login fail");
				ri = MemberDao.MEMBER_LOGIN_IS_NOT;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(set!=null)set.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public MemberDto getMember(String id) {
		MemberDto dto=null;
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query="select * from members where userid=?";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			set = pstmt.executeQuery();
			
			if(set.next()) {
				dto = new MemberDto();
				dto.setId(set.getString("userid"));
				dto.setPw(set.getString("userpw"));
				dto.setName(set.getString("username"));
				dto.seteMail(set.getString("useremail"));
				dto.setrDate(set.getTimestamp("rdate"));
				dto.setAddress(set.getString("useraddress"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(set!=null)set.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}
		
	public int updateMember(MemberDto dto) {
		int ri=0;
		
		Connection con =null;
		PreparedStatement pstmt=null;
		String query = "update members set userpw=?, usereMail=?, useraddress=? where userid=?";
		
		try {
			con = dataSource.getConnection();
		
			pstmt =con.prepareStatement(query);
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.geteMail());
			pstmt.setString(3, dto.getAddress());
			pstmt.setString(4, dto.getId());
			ri = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
			
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public int deleteMember(MemberDto dto) {
		int ri=0;
		Connection con =null;
		PreparedStatement pstmt=null;
		String query = "delete from members where userid=?";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getId());
			ri=pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
			
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	
	
}
