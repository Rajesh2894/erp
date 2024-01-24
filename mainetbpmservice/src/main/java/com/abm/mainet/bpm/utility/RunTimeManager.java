package com.abm.mainet.bpm.utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.remote.client.api.RemoteRestRuntimeEngineBuilder;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;

import com.abm.mainet.bpm.domain.BpmDeployment;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.config.ApplicationProperties;

/**
 * 
 * 
 * @author sanket.joshi
 *
 */
public class RunTimeManager {
	private static Map<String,RuntimeEngine> runtimeEnginebuilder= new HashMap<String,RuntimeEngine>();
	
	private RunTimeManager() {
		
	}
	/**
     * Configure jBPM Remote Runtime Engine
     * 
     * @param properties
     * @param parameter
     * @return
     * @throws MalformedURLException
     */
    public static RuntimeEngine getRuntimeEngineInstance(ApplicationProperties properties, BpmDeployment deployment)
            throws FrameworkException {


        try {
        	if(deployment!=null && runtimeEnginebuilder.get(deployment.toString()) == null) {
	            // Following synchronized is added to ensure that runtime engine is loaded only once
	           synchronized (RunTimeManager.class) {
	        	   String urlStr = properties.getJbpmKieWbUrl();
		            Integer timeout = properties.getJbpmKieWbTimeout();
		
		            // This is always Admin users
		            String usareName = properties.getJbpmKieWbUsername();
		            String password = properties.getJbpmKieWbPassword();
		            URL url = new URL(urlStr);
		            RemoteRestRuntimeEngineBuilder builder = RemoteRuntimeEngineFactory.newRestBuilder().addUrl(url).addTimeout(timeout)
                    .addUserName(usareName).addPassword(password);
		            if (properties.isJbpmTaskInsecure()) {
		                // Configuring Remote Runtime Engine to manage task security.
		            	builder.disableTaskSecurity();
		            }
		            if (deployment != null) {
		                // Configuring Remote Runtime Engine with deployment
		            	builder.addDeploymentId(deployment.toString());
		            }
		            runtimeEnginebuilder.put(deployment.toString(), builder.build()) ;

	           }
        		
        	}
            

            
        } catch (MalformedURLException e) {
            throw new FrameworkException(e);
        }
        return runtimeEnginebuilder.get(deployment.toString());
    }

}
