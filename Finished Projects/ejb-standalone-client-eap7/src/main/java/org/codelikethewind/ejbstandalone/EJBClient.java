package org.codelikethewind.ejbstandalone;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.codelikethewind.ejbserver.RemoteEJB;

public class EJBClient {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws NamingException {
		// TODO Auto-generated method stub
		
		 final Hashtable jndiProperties = new Hashtable();
         jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
         Context context = new InitialContext(jndiProperties);
         RemoteEJB ejb = (RemoteEJB)context.lookup("ejb:/ejb-server-side/MyEJB!" + RemoteEJB.class.getName());
         System.out.println(ejb.helloWorld("T.O."));

	}

}
