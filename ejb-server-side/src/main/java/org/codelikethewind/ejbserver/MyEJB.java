package org.codelikethewind.ejbserver;

public class MyEJB implements RemoteEJB {

	public String helloWorld(String name){
		return "Hello " + name;
	}

}
