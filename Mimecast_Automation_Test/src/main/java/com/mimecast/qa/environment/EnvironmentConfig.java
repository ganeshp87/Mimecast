package com.mimecast.qa.environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentConfig {

	private static EnvironmentConfig s_instance;
	private Properties envProps;
	private String currentEnv;
	
	
	private EnvironmentConfig(Properties props){
		envProps=props;
	}
	
	private EnvironmentConfig(Properties props,String env){
		envProps=props;
		currentEnv=env;
	}
	
	
	public synchronized static void initEnvironment(String env){
		if(s_instance !=null){
			throw new RuntimeException("Trying to reinitize env with:" + env);
		}
		Properties props = new Properties();
	   try{
		   FileInputStream path = new FileInputStream(getFilePath(env));
		   props.load(path);
		   
		}catch(IOException e){
			throw new RuntimeException("Unable to intialize environment." + e);
		}
		s_instance = new EnvironmentConfig(props,env);
	}
	
	public String getCurrentEnvironment(){
		return currentEnv;
	}
	
	public boolean isPRODEnv(){
		return getCurrentEnvironment().equalsIgnoreCase("PROD");
	}
	
	public static EnvironmentConfig getInstance(){
		if(s_instance == null){
			throw new RuntimeException("Environment not intialized");
		}
		return s_instance;
	}
	
	public String getValueForKey(String key){
		return envProps.getProperty(key);
	}
	
	public static String getFilePath(String env){
        return new StringBuffer(System.getProperty("user.dir")).
		      append(File.separator).append("src").append(File.separator).
		      append("test").append(File.separator).append("resources").append(File.separator).
		      append("com").append(File.separator). append("mimecast").append(File.separator).append("qa").append(File.separator).
		      append("environment").append(File.separator).append(env).append("config.properties").toString();
	         }
}
