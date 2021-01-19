package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {//genericservlet과의 차이점: get요청과 post요청을 분기해주는 요청을 추가(doGet(), doPost())

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원등록</title></head>");
		out.println("<body><h1>회원등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름 : <input type='text' name='name'><br/>");
		out.println("이메일 : <input type='text' name='email'><br/>");
		out.println("암호 : <input type='password' name='password'><br/>");
		out.println("<input type='submit' value='추가'><br/>");
		out.println("<input type='reset' value='취소'><br/>");
		out.println("</form>");
		out.println("</body></html>");
	}
	
}
