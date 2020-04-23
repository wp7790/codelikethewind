package org.codelikethewind.embedded.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;


@Singleton
@Startup
public class StartupBean {

    public static final String DEPLOYMENT_ID = "org.codelikethewind:simple-process:0.0.1-SNAPSHOT";



    @PostConstruct
    public void init() {
    	String[] gav = DEPLOYMENT_ID.split(":");

    	// Deploy the kjar here
    	
    }

}
