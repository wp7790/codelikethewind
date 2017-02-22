package org.codelikethewind.simpleejb;

import javax.ejb.Stateless;

@Stateless
public class MyEJB {

	public String helloWorld(String name) {
		return "Hello " + name;
	}

}
