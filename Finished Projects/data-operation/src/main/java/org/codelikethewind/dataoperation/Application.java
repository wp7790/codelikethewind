package org.codelikethewind.dataoperation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DeploymentService;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.scanner.MavenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
@ImportResource(value= {
		"classpath:config/jee-tx-context.xml",
		"classpath:config/jpa-context.xml", 
		"classpath:config/jbpm-context.xml"})
public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	
	private static final int THREAD_POOL_SIZE = Integer.parseInt(System.getProperty("thread.pool.size", "10"));	
	public static final String KJAR = "org.kie.example:project1:1.0.1-SNAPSHOT";
	
	public static void main(String[] args) {
		
		System.setProperty("org.kie.txm.factory.class", "org.kie.spring.persistence.KieSpringTransactionManagerFactory");
		
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        DeploymentService deploymentService = (DeploymentService) ctx.getBean("deploymentService");
        
        try {	
    		deployKjar(deploymentService, KJAR);
        	
    		List<DataOperationSpec> specList = DataRetriever.findAllActiveProcesses();
        	
    		performDataOperation(specList);        	
    	} catch (Throwable e) {
    		LOG.error("Error while executing the data operation ...", e);
    	} finally {
    		ctx.close();
    	}
    }
	
	private static void performDataOperation(List<DataOperationSpec> specList) {
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		LOG.info("\n\t>>>> Starting the data operation ... THREAD Pool Size: {}", THREAD_POOL_SIZE);
		
		Long start = System.currentTimeMillis();
		try {
			for(DataOperationSpec spec: specList) {
				executor.execute(new DataOperationTask(spec));
			}
			executor.shutdown();
			boolean executorShutdownTimedOut = executor.awaitTermination(12, TimeUnit.HOURS);
			LOG.info(">>> Executor shutdowm timed out : {}", !executorShutdownTimedOut);
		} catch (InterruptedException e) {
		    LOG.error("Data Operation Tasks interrupted.", e);
		} finally {
		    if (!executor.isTerminated()) {
		        LOG.error("Cancelling non-finished tasks ...");
		    }
		    executor.shutdownNow();
		    LOG.info(">>> Executor shutdown finished");
		    
		    Long end = System.currentTimeMillis();
			LOG.info(">>> Performed Data Operation on {} process instances in {} (ms)", specList.size(), (end - start));
		}
	}
	
	
	private static final Pattern GAV_PATTERN = Pattern.compile(".+:.+:.+");
	
	/**
	 * Deploy the KJAR
	 * 
	 * @param deploymentService
	 * @param gav
	 * @throws IOException 
	 */
	private static void deployKjar(DeploymentService deploymentService, String gav) throws IOException {
		 
		if(gav == null || !GAV_PATTERN.matcher(gav).matches()) {
			throw new IllegalStateException("Cannont deploy KJAR. Invalid GAV '"+gav+"'");
		}
		
		LOG.info("\n\t>>>> Deploying {} ....", gav);
		String [] tmp = gav.split(":");
		loadKjarFromClasspath(tmp[0], tmp[1], tmp[2]);
		KModuleDeploymentUnit unit = new KModuleDeploymentUnit(tmp[0], tmp[1], tmp[2]);
		deploymentService.deploy(unit);
		LOG.info("\n\t>>>> Succesfully deployed {} ", gav);
	}
	
	private static ReleaseId loadKjarFromClasspath(String G, String A, String V) throws IOException {
        KieFileSystem kfs = KieServices.Factory.get().newKieFileSystem();
        ReleaseId id = KieServices.Factory.get().newReleaseId(G, A, V);
        kfs.generateAndWritePomXML(id);
        byte[] pom = kfs.read("pom.xml");
        InputStream kjarIs = Application.class.getClassLoader()
                        .getResourceAsStream(String.format("META-INF/%s-%s.jar", A, V));
        byte[] kjar = IOUtils.toByteArray(kjarIs);
        MavenRepository.getMavenRepository().installArtifact(id, kjar, pom);
        return id;
	}
}
