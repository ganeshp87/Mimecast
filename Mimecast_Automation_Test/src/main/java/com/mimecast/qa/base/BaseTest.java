 
package com.mimecast.qa.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public abstract class BaseTest{
	
  protected abstract void initEnviroenment(String env);
	
    @BeforeSuite(alwaysRun=true)
	@Parameters({"test.env"})
	public void initSuite(String env){
		initEnviroenment(env);
	}
	
	
}
