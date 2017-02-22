package org.codelikethewind.simpleejb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codelikethewind.simpleejb.MyEJB;

@SuppressWarnings("serial")
@WebServlet("/SimpleEJB")
public class SimpleServlet extends HttpServlet {
	
	@EJB
	MyEJB ejb;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>" + ejb.helloWorld("TiOluwa") + "</h1>");
		out.println("</body>");
		out.println("</html>");
	}
}
