package org.codelikethewind.ejbserver;

import javax.ejb.Stateless;

@Stateless
public class MyEJB implements RemoteEJB {

	public String helloWorld(String name){
		return "Hello " + name;
	}

}
