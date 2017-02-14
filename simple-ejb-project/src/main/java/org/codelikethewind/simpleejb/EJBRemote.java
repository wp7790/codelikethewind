package org.codelikethewind.simpleejb;

import javax.ejb.Local;

@Local
public interface EJBRemote {
	
	public String helloWorld(String name);

}
