package com.mimecast.qa.validations;

import java.util.HashSet;
import org.apache.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mimecast.qa.util.GeneralUtility;

public class PostCodeValidation {
	static final Logger logger = Logger.getLogger(PostCodeValidation.class);
	
	public boolean isAcutalVsExpectedIdentical(JSONObject actualPostCodeResObj, JSONObject expectedPostCodeResObj, String testType) throws JSONException {
		boolean resultflag=false;
		boolean compareResult=false;
		HashSet<String> actualHashSet=new HashSet<String>(); 
		HashSet<String> expectedHashSet=new HashSet<String>(); 
		if(testType.equals("Positive")){
			Object result = actualPostCodeResObj.get("result"); 
			if(result instanceof JSONArray ){
			JSONArray actualPostCodes = actualPostCodeResObj.getJSONArray("result");
			JSONArray exceptedPostCodes = expectedPostCodeResObj.getJSONArray("result");
			actualHashSet.add(actualPostCodeResObj.getString("status"));
			for(int i=0;i<actualPostCodes.length();i++){
			  actualHashSet.add(actualPostCodes.getJSONObject(i).getString("postcode"));
			  actualHashSet.add(actualPostCodes.getJSONObject(i).getString("country"));
			  actualHashSet.add(actualPostCodes.getJSONObject(i).getString("region"));
			  actualHashSet.add(actualPostCodes.getJSONObject(i).getString("admin_district"));
			}
			logger.info("Actual Hash Set " + actualHashSet);
			
			expectedHashSet.add(actualPostCodeResObj.getString("status"));
			for(int i=0;i<exceptedPostCodes.length();i++){
			  expectedHashSet.add(exceptedPostCodes.getJSONObject(i).getString("postcode"));
			  expectedHashSet.add(exceptedPostCodes.getJSONObject(i).getString("country"));
			  expectedHashSet.add(exceptedPostCodes.getJSONObject(i).getString("region"));
			  expectedHashSet.add(exceptedPostCodes.getJSONObject(i).getString("admin_district"));
			 }
			}else{
				  actualHashSet.add(actualPostCodeResObj.getString("status"));
				  actualHashSet.add(actualPostCodeResObj.getJSONObject("result").getString("postcode"));
				  actualHashSet.add(actualPostCodeResObj.getJSONObject("result").getString("country"));
				  actualHashSet.add(actualPostCodeResObj.getJSONObject("result").getString("region"));
				  actualHashSet.add(actualPostCodeResObj.getJSONObject("result").getString("admin_district"));
				
				logger.info("Actual Hash Set " + actualHashSet);
				
				  expectedHashSet.add(expectedPostCodeResObj.getString("status"));
				  expectedHashSet.add(expectedPostCodeResObj.getJSONObject("result").getString("postcode"));
				  expectedHashSet.add(expectedPostCodeResObj.getJSONObject("result").getString("country"));
				  expectedHashSet.add(expectedPostCodeResObj.getJSONObject("result").getString("region"));
				  expectedHashSet.add(expectedPostCodeResObj.getJSONObject("result").getString("admin_district"));
			}
			logger.info("Actual Hash Set " + expectedHashSet);
			compareResult=GeneralUtility.compareHashSet(actualHashSet, expectedHashSet);
			
		}else{
			
			if(actualPostCodeResObj.getString("status").equals("404")){
				actualHashSet.add(actualPostCodeResObj.getString("error"));
				expectedHashSet.add(expectedPostCodeResObj.getString("error"));
			}else{
				actualHashSet.add(actualPostCodeResObj.getString("result"));
				expectedHashSet.add(expectedPostCodeResObj.getString("result"));
			}
			
			compareResult=GeneralUtility.compareHashSet(actualHashSet, expectedHashSet);
		}
		
		if(compareResult){
			resultflag=true;
		}
		
		return resultflag;
	}

	public boolean isAcutalVsExpectedPostCodeIdentical(JSONObject actualPostCodeResObj) {

		try {
			if(actualPostCodeResObj.getString("status").equals("400")){
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
