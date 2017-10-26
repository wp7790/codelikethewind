package org.codelikethewind.simpleejb.ejb;

import javax.ejb.Stateless;

@Stateless
public class MyEJB {

	public String helloWorld(String name) {
		return "Hello " + name;
	}

}
