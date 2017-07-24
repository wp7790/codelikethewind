package org.codelikethewind.embedded.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;


@Singleton
@Startup
public class StartupBean {
	
    public static final String GROUP = "org.codelikethewind";
    public static final String ARTIFACT = "simple-process";
    public static final String VERSION = "0.0.1-SNAPSHOT";


    @PostConstruct
    public void init() {
    	
    	// Deploy the kjar here
    	
    }

}
