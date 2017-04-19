package org.codelikethewind.simpleejb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codelikethewind.ejbserver.RemoteEJB;

@SuppressWarnings("serial")
@WebServlet("/SimpleEJB")
public class SimpleServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		RemoteEJB ejb = getRemoteEJB();
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>" + ejb.helloWorld("World") + "</h1>");
		out.println("</body>");
		out.println("</html>");
	}
	
	public RemoteEJB getRemoteEJB(){
	    final Hashtable props = new Hashtable();
	    props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	 
	    Context context;
	    RemoteEJB ejb = null;
	 
	    try {
	        context = new InitialContext(props); 
	        ejb = (RemoteEJB) context.lookup("ejb:/ejb-server-side//MyEJB!" + RemoteEJB.class.getName());

	    } catch (NamingException e) {
	        e.printStackTrace();
	    }
	 
	    return ejb;
	 }
}
