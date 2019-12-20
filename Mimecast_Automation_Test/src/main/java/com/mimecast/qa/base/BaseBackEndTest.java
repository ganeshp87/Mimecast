 
package com.mimecast.qa.base;

import com.mimecast.qa.environment.EnvironmentConfig;

public class BaseBackEndTest extends BaseTest {

	@Override
	protected void initEnviroenment(String env) {
		System.out.println("Initilizing enviroenment:: " + env);
		EnvironmentConfig.initEnvironment(env);
		
	}

	
	
}
