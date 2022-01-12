package org.codelikethewind.ejbstandalone;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.codelikethewind.ejbserver.RemoteEJB;
import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

public class EJBClientRunnable {
	
	public static void main(String[] args) throws NamingException {
		// TODO Auto-generated method stub
		String host = "";
		String port = "";
		String username = "";
		String password = "";
		
		 try {
			 host = args[0];
		     port = args[1];
		     username = args[0];
		     password = args[1];
		     
		     initEJBClient(host, port, username, password);
				sayHello();
		    }
		    catch (ArrayIndexOutOfBoundsException e){
		        System.out.println("ArrayIndexOutOfBoundsException caught");
		    }
		    finally {

		    }
	}
	
	public static void initEJBClient(String host, String port, String username, String password) {
        Properties p = new Properties();
        p.put("remote.connections", "node1");
        p.put("remote.connection.node1.host", host);  // the host, replace if necessary
        p.put("remote.connection.node1.port", port);  // the default remoting port, replace if necessary
        
        p.put("remote.connection.node1.username", username);  
        p.put("remote.connection.node1.password", password);  

        p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false"); // the server defaults to SSL_ENABLED=false

        EJBClientConfiguration cc = new PropertiesBasedEJBClientConfiguration(p);
        ContextSelector<EJBClientContext> selector = new ConfigBasedEJBClientContextSelector(cc);
        EJBClientContext.setSelector(selector);
	}
	
	public static void sayHello() throws NamingException {
		Properties contextProperties = new Properties();
	    contextProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	    Context context = new InitialContext(contextProperties);
	    RemoteEJB ejb = (RemoteEJB)context.lookup("ejb:/ejb-server-side/MyEJB!" + RemoteEJB.class.getName());
	    System.out.println(ejb.helloWorld("World"));
	}
}
