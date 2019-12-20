package com.mimecast.qa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.mimecast.qa.environment.EnvironmentConfig;

public class APIUtility {
	static final Logger logger = Logger.getLogger(APIUtility.class);
	public JSONObject getPostCodeResponse(String Param){
		
		String url = EnvironmentConfig.getInstance().getValueForKey("BASE_URL");
		StringBuffer completeUrl = new StringBuffer(url);
		completeUrl=completeUrl.append(Param);
		logger.info("Complete URL " + completeUrl);
		JSONObject response = getAPIResponse(completeUrl.toString());
		return response;
	}
	
	public JSONObject getAPIResponse(String url){
		String responseMessage = "";
		BufferedReader br=null;
		try{
			URL completeURL= new URL(url);
			HttpURLConnection conn = (HttpURLConnection) completeURL.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.connect();
			/*
			 * if(conn.getResponseCode()!=200){ throw new
			 * RuntimeException("Failed : HTTP error code : " + conn.getResponseCode()); }
			 */
			if(conn.getResponseCode()==404) {
				InputStream error = conn.getErrorStream();
				 br = new BufferedReader(new InputStreamReader((error)));
			}
			else {
				 br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			}
			
			String output;
			while((output=br.readLine())!=null){
				responseMessage = responseMessage + output.toString();
			}
			logger.info("PostCode API Response is " + responseMessage);
			conn.disconnect();
			JSONObject responseMessageJSON;
			try{
				responseMessageJSON = new JSONObject(responseMessage);
			}catch(JSONException e){
				logger.info("JsonException occoured");
				responseMessageJSON=null;
			}
			return responseMessageJSON;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAPIResponseInString(String url) throws IOException{
		    String responseMessage = "";
			URL completeURL= new URL(url);
			HttpURLConnection conn = (HttpURLConnection) completeURL.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while((output=br.readLine())!=null){
				responseMessage = responseMessage + output.toString();
			}
			logger.info("EAC Schema Response is " + responseMessage);
			conn.disconnect();
			return responseMessage;
	}

	public JSONObject stringToObject(String expectedResponseBody) {
		JSONObject responseMessageJSON;
		try{
			responseMessageJSON = new JSONObject(expectedResponseBody);
		}catch(JSONException e){
			logger.info("JsonException occoured");
			responseMessageJSON=null;
		}
		return responseMessageJSON;
	}

	public JSONObject post_PostCode(String param, String httpMethod, String requestBody) throws ParseException, IOException {
		JSONObject expectedPostCodeResObj=null;
		String url = EnvironmentConfig.getInstance().getValueForKey("BASE_URL");
		if(httpMethod.equals("POST")){
			String result = "";
	        HttpPost post = new HttpPost(url);
	        // send a JSON data
	        post.setEntity(new StringEntity(Payload.postData()));
	        try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(post)) {

	            result = EntityUtils.toString(response.getEntity());
	            expectedPostCodeResObj=this.stringToObject(result);
	        }
	  }
		return expectedPostCodeResObj;
   }
	
}
