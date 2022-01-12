package org.codelikethewind.ejbserver;

import javax.ejb.Remote;

@Remote
public interface RemoteEJB {
	String helloWorld(String name);
}
