package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String no = req.getParameter("no");
		String sqlSelect = "SELECT mno,email,mname,cre_date " + "From members WHERE mno=" + no;
		
		//등록한 parameter을 가져올 수 있음
		String driver = this.getInitParameter("driver");
		String url = this.getInitParameter("url");
		String id = this.getInitParameter("id");
		String pwd = this.getInitParameter("pwd");

		try {
			//1) mysql 드라이버 로딩
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pwd);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			rs.next();// 처음에는 1행 이전을 가리키니까 1행으로 위치 이동
			
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원정보</title></head>");
			out.println("<body><h1>회원정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' value='" + no + "' readonly><br/>");
			out.println("이름: <input type='text' name='name' value='" + rs.getString("mname")+ "'<br/>");
			out.println("이메일: <input type='text' name='email' value='" + rs.getString("email")+ "'<br/>");
			out.println("이름: <input type='text' name='name'" + " value='" + rs.getString("mname") + "'><br/>");
			out.println("가입일: " + rs.getDate("cre_date") + "<br/>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='취소'" + " onclick='location.href=\"list\"'>");
			out.println("</form>");
			out.println("</body></html>");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)
					rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(stmt != null)
					stmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
