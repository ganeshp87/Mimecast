package com.mimecast.qa.api;

import java.io.IOException;
import org.apache.http.ParseException;
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

public class PostCodes extends BaseBackEndTest{
	@Test(dataProvider ="PostCodesAPI")
	public void test_PostCodes(String param, String requestBody,String expectedResponseBody,String testType,String httpMethod,DataProviderIndex index)throws JSONException{
	  final SoftAssert s_stands = new SoftAssert();
	  
	  APIUtility apiutility = new APIUtility();
	  PostCodeValidation postCodeValidation = new PostCodeValidation();
		boolean resultFlag=false;
		
		JSONObject actualPostCodeResObj = apiutility.getPostCodeResponse(param);
		System.out.println("API Response from  PostCode is:: " + actualPostCodeResObj);
		
		JSONObject expectedPostCodeResObj =apiutility.stringToObject(expectedResponseBody);
		
		System.out.println("API Expected Response Object is:: " + expectedPostCodeResObj);
		
		resultFlag=postCodeValidation.isAcutalVsExpectedIdentical(actualPostCodeResObj,expectedPostCodeResObj,testType);
		
		s_stands.assertTrue(resultFlag);
		s_stands.assertAll();
	}

	@Test(dataProvider ="PostAPI")
	public void test_PostAPI(String param, String requestBody,String expectedResponseBody,String testType,String httpMethod,DataProviderIndex index)throws JSONException, ParseException, IOException{
	  final SoftAssert s_stands = new SoftAssert();
	  APIUtility apiutility = new APIUtility();
	  PostCodeValidation postCodeValidation = new PostCodeValidation();
		boolean resultFlag=false;
		
		JSONObject actualPostCodeResObj = apiutility.post_PostCode(param,httpMethod,requestBody);
		System.out.println("API Response from  PostCode is:: " + actualPostCodeResObj);
		
		resultFlag=postCodeValidation.isAcutalVsExpectedPostCodeIdentical(actualPostCodeResObj);
		
		s_stands.assertTrue(resultFlag);
		s_stands.assertAll();
		
	}
	
	@DataProvider(name="PostAPI")
	public Object[][] getSingleSearchData() throws BiffException, IOException
	{
		String envName=EnvironmentConfig.getInstance().getCurrentEnvironment();
		if(envName.trim().equalsIgnoreCase("PROD")){
			String[][] data = new ExcelUtility().getTableArray("\\src\\main\\resources\\com\\mimecast\\qa\\data\\TestDatas.xls", "CreatePostCodesPROD", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}else{
			String [][] data = new ExcelUtility().getTableArray("\\src\\main\\resources\\com\\mimecast\\qa\\data\\TestDatas.xls", "CreatePostCodesQA", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}
	}
	
	@DataProvider(name="PostCodesAPI")
	public Object[][] getPostCodesData() throws BiffException, IOException
	{
		String envName=EnvironmentConfig.getInstance().getCurrentEnvironment();
		if(envName.trim().equalsIgnoreCase("PROD")){
			String[][] data = new ExcelUtility().getTableArray("\\src\\main\\resources\\com\\mimecast\\qa\\data\\TestDatas.xls", "PostCodesPROD", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}else{
			String [][] data = new ExcelUtility().getTableArray("\\src\\main\\resources\\com\\mimecast\\qa\\data\\TestDatas.xls", "PostCodesQA", "AddTestData");
			return DataProviderUtility.getDataProviderWithIndex(data);
		}
		
	}
	
	
}
