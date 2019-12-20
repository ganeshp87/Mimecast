package com.mimecast.qa.api;


/**
 * @ Ganesh Palanisamy
 * 
 */
import java.io.IOException;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.mimecast.qa.base.BaseBackEndTest;
import com.mimecast.qa.environment.EnvironmentConfig;
import com.mimecast.qa.util.APIUtility;
import com.mimecast.qa.util.DataProviderIndex;
import com.mimecast.qa.util.DataProviderUtility;
import com.mimecast.qa.util.ExcelUtility;
import com.mimecast.qa.validations.PostCodeValidation;
import jxl.read.biff.BiffException;



public class PostCodesTest extends BaseBackEndTest{
	
	static final Logger logger = Logger.getLogger(PostCodesTest.class);
	
	
	/**
	 * The below test case is used to test the GET post code details.
	 * @param parm,request body,expectedResponsebody,httpmethod
	 * @return boolean value
	 * 
	 */
	
	@Test(dataProvider ="PostCodesAPI")
	public void test_PostCodes(String param, String requestBody,String expectedResponseBody,String testType,String httpMethod,DataProviderIndex index)throws JSONException{
	  final SoftAssert s_stands = new SoftAssert();
	  
	  APIUtility apiutility = new APIUtility();
	  PostCodeValidation postCodeValidation = new PostCodeValidation();
		boolean resultFlag=false;
		
		JSONObject actualPostCodeResObj = apiutility.getPostCodeResponse(param);
		logger.info("API Response from  PostCode is:: " + actualPostCodeResObj);
		
		JSONObject expectedPostCodeResObj =apiutility.stringToObject(expectedResponseBody);
		
		logger.info("API Expected Response Object is:: " + expectedPostCodeResObj);
		
		resultFlag=postCodeValidation.isAcutalVsExpectedIdentical(actualPostCodeResObj,expectedPostCodeResObj,testType);
		
		s_stands.assertTrue(resultFlag);
		s_stands.assertAll();
	}

	/**
	 * The below test case is used to test the POST postcode details.
	 * @param parm,request body,expectedResponsebody,httpmethod
	 * @return boolean value
	 * 
	 */
	
	@Test(dataProvider ="PostAPI")
	public void test_PostAPI(String param, String requestBody,String expectedResponseBody,String testType,String httpMethod,DataProviderIndex index)throws JSONException, ParseException, IOException{
	  final SoftAssert s_stands = new SoftAssert();
	  APIUtility apiutility = new APIUtility();
	  PostCodeValidation postCodeValidation = new PostCodeValidation();
	  boolean resultFlag=false;
	  JSONObject actualPostCodeResObj = apiutility.post_PostCode(param,httpMethod,requestBody);
		
	  resultFlag=postCodeValidation.isAcutalVsExpectedPostCodeIdentical(actualPostCodeResObj);
		
		s_stands.assertTrue(resultFlag);
		s_stands.assertAll();
		
	}
	
	/**
	 * The below method is a Dataprovider.
	 * 
	 */
	
	
	@DataProvider(name="PostAPI")
	public Object[][] getSingleSearchData() throws BiffException, IOException
	{
		String envName=EnvironmentConfig.getInstance().getCurrentEnvironment();
		String path = EnvironmentConfig.getInstance().getValueForKey("DATA_PATH");
		if(envName.trim().equalsIgnoreCase("PROD")){
			String[][] data = new ExcelUtility().getTableArray(path, "CreatePostCodesPROD", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}else{
			String [][] data = new ExcelUtility().getTableArray(path, "CreatePostCodesQA", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}
	}
	
	/**
	 * The below method is a Dataprovider.
	 * 
	 */
	
	@DataProvider(name="PostCodesAPI")
	public Object[][] getPostCodesData() throws BiffException, IOException
	{
		String path = EnvironmentConfig.getInstance().getValueForKey("DATA_PATH");
		String envName=EnvironmentConfig.getInstance().getCurrentEnvironment();
		if(envName.trim().equalsIgnoreCase("PROD")){
			String[][] data = new ExcelUtility().getTableArray(path, "PostCodesPROD", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}else{
			String [][] data = new ExcelUtility().getTableArray(path, "PostCodesQA", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}
		
	}
	
	
}
