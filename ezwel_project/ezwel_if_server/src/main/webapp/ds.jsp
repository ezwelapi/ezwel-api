<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%
/*************************************************************************************************************
 * 디비연결 셋팅
 *************************************************************************************************************/
//디비연결 변수[게시물]
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
PreparedStatement pstmt = null;

/*************************************************************************************************************
 * 디비연결 및 쿼리 질의 [START]
 *************************************************************************************************************/
try{

	Context initCtx = new InitialContext();
	Context envCtx = (Context)initCtx.lookup("java:/comp/env");
	DataSource ds = (DataSource)envCtx.lookup("ezwelDS");
	
	conn = ds.getConnection();
	
	pstmt = conn.prepareStatement("select count(*) from dual");
	rs = pstmt.executeQuery();
	   
	while(rs.next()) { //레코드를 이동시킨다. 
		out.println("While Test");
	}
	
	out.println("DataSource lookup Success!!");
} catch(Exception e) {
	out.println("DataSource lookup Fail!!");
	e.printStackTrace();
} finally {
  if (rs != null) try { rs.close(); } catch(SQLException ex) { e.printStackTrace(); }
  if (stmt != null) try { stmt.close(); } catch(SQLException ex) { e.printStackTrace(); }
  if (conn != null) try { conn.close(); } catch(SQLException ex) { e.printStackTrace(); }
}
%>