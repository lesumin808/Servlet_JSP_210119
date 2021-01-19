package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		Connection conn = null;		// MySQL 서버 연결
		Statement stmt = null;		// sql문
		ResultSet rs = null;		// select 결과
		
		String sqlSelect = "SELECT mno,mname,email,cre_date " + "\r\n" +
						   "FROM members " + "\r\n" +
						   "ORDER BY mno ASC";
		String mySqlUrl = "jdbc:mysql://localhost/studydb?serverTimezone=UTC"; // 5.1.x 이후
		String id = "study";
		String pwd = "study";
		try {
			// 1) MySQL 제어 객체를 로딩
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			// 2) MySQL과 연결
			conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			// 3) sql문 객체 생성
			stmt = conn.createStatement();
			// 4) sql문 전송 후 결과 얻기
			rs = stmt.executeQuery(sqlSelect);
			// 5) 결과를 브라우저에 전송
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원 목록</h1>");
			/*
			 *      /add : /add
			 *      add : _08_JDBCServlet_Add/member/list
			 *          => _08_JDBCServlet_Add/member/add
			 * */
			/* 컨텍스트 루트(Context Root) ==> 기본 경로
			 * 1) Apache tomcat의 주소 : http://localhost:9999 
			 * 2) 웹어플리케이션 : _08_JDBCServlet_Add
			 * 3) 컨텍스트 루트 경로 : http://localhost:9999/_08_JDBCServlet_Add
			 *   3-1) <a href='/add'>   => 절대경로 (웹서버 루트 기준)
			 *      /는 웹서버 경로에서 add를 추가
			 *      http://localhost:9999/add
			 *   3-2) <a href='add'>	=> 상대경로 (현재 경로 기준)
			 *      현재 경로의 마지막 경로만 교체
			 *      http://localhost:9999/_08_JDBCServlet_Add/member/list
			 *         =>
			 *      http://localhost:9999/_08_JDBCServlet_Add/member/add
			 * */
			out.println("<p><a href='add'>신규 회원</p>");
			//out.println("<p><a href='/_08_JDBCServlet_Add/member/add'>신규 회원</p>");
			while(rs.next()) {
				out.println(rs.getInt("mno") + ", " +
							"<a href='update?no=" + rs.getInt("mno") + "'>" + // /member/update?no=1
							rs.getString("mname") + ", " + 
							rs.getString("email") + ", " + 
							rs.getDate("cre_date") + "<br/>");
			}
			out.println("</body></html>");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)
					rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(stmt != null)
					stmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}


















