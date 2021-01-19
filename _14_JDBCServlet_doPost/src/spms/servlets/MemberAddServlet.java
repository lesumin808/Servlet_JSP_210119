package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {

	// /member/list => /member/add로 get으로 링크를 이동한다
	// 브라우저에 입력 폼을 제공한다
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원등록</title></head>");
		out.println("<body><h1>회원등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br/>");
		out.println("이메일: <input type='text' name='email'><br/>");
		out.println("암호: <input type='password' name='password'><br/>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}

	// doGet에서 submit을 하면 action='add' method='post'으로 처리되므로
	// 결고 다시 /member/add로 post 요청이 들어온다
	// DB에 새로운 사용자를 등록한다
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		서블릿은 utf-8이 아니라 유니코드로 문자열을 처리한다
//		그러므로 utf-8로 인식하겠다는 설정을 해야 한다
		req.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sqlInsert = "INSERT INTO members(email,pwd," + "\r\n" +
						   "mname,cre_date,mod_date)" + "\r\n" +
						   "VALUES(?,?,?,NOW(),NOW())";
		String url = "jdbc:mysql://localhost/studydb?serverTimezone=UTC";
		String id = "study";
		String pwd = "study";
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection(url, id, pwd);
			stmt = conn.prepareStatement(sqlInsert);
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("password"));
			stmt.setString(3, req.getParameter("name"));
			stmt.executeUpdate();
			
			// 바로 add -> list이동(리다이렉트)
			resp.sendRedirect("list");
			
			/*
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원등록결과</title></head>");
			out.println("<body>");
			out.println("<p>등록성공입니다!</p>");
			out.println("</body></html>");
			*/
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











