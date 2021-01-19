package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
		Connection conn = null; //MySQL 서버 연결
		Statement stmt = null; // sql문
		ResultSet rs = null; // select 결과
		
		String sqlSelect = "SELECT mno,mname,email,cre_date " + "FROM members " + "ORDER BY mno ASC";
		String mySqlUrl = "jdbc:mysql://localhost/studydb?serverTimezone=UTC";
		String id = "study";
		String pwd = "study";
		
		try {
			// 1) MySQL 제어 객체를 로딩
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			// 2) MySql과 연결
			conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			// 3) sql문 객체 생성
			stmt = conn.createStatement();
			//4) sql문 전송 후 결과 얻기
			rs = stmt.executeQuery(sqlSelect);
			// 5) 결과를 브라우저에 전송
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			while(rs.next()) {
				out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getDate(4) + "<br>");
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
