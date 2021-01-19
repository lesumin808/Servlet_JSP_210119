package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/update")
@SuppressWarnings("serial")
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String no = req.getParameter("no");
		String sqlSelect = "SELECT mno,email,mname,cre_date " + "\r\n" +
						   "FROM members WHERE mno=" + "\r\n" + no;
		
		ServletContext sc = this.getServletContext();
		String driver = sc.getInitParameter("driver");
		String url = sc.getInitParameter("url");
		String id = sc.getInitParameter("username");
		String pwd = sc.getInitParameter("password");

		try {			
			Class.forName(driver);	// mysql드라이버 로딩
			conn = DriverManager.getConnection(url, id, pwd);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			rs.next();				// 처음에는 1행 이전을 가리키니까 1행으로 위치 이동
			
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원 정보</title></head>");
			out.println("<body><h1>회원 정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' value='" + no + 
						"' readonly><br/>");
			out.println("이름: <input type='text' name='name'" +
						" value='" + rs.getString("mname") + "'><br/>");
			out.println("이메일: <input type='text' name='email'" +
						" value='" + rs.getString("email") + "'><br/>");
			out.println("가입일: " + rs.getDate("cre_date") + "<br/>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='취소'" +
						" onclick='location.href=\"list\"'>");
			out.println("</form>");
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");// 한글이 안 깨짐
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sqlUpdate = "UPDATE members SET email=?," +"\r\n" +
							"mname=?,mod_date=NOW() WHERE mno=?";
		
		ServletContext sc = this.getServletContext();
		String driver = sc.getInitParameter("driver");
		String url = sc.getInitParameter("url");
		String id = sc.getInitParameter("username");
		String pwd = sc.getInitParameter("password");
		
		try {
			Class.forName(driver);	// mysql객체를 로딩
			conn = DriverManager.getConnection(url, id, pwd);
			stmt = conn.prepareStatement(sqlUpdate);
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(req.getParameter("no")));
			stmt.execute();
			
			resp.sendRedirect("list");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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







