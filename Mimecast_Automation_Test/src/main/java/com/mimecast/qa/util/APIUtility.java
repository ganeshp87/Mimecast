package com.mimecast.qa.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;
import com.mimecast.qa.environment.EnvironmentConfig;

public class APIUtility {

	public JSONObject getPostCodeResponse(String Param){
		
		String url = EnvironmentConfig.getInstance().getValueForKey("BASE_URL");
		StringBuffer completeUrl = new StringBuffer(url);
		completeUrl=completeUrl.append(Param);
		System.out.println("Complete URL" + completeUrl);
		JSONObject response = getAPIResponse(completeUrl.toString());
		return response;
	}
	
	public JSONObject getAPIResponse(String url){
		String responseMessage = "";
		try{
			URL completeURL= new URL(url);
			HttpURLConnection conn = (HttpURLConnection) completeURL.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.connect();
			/*if(conn.getResponseCode()!=200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			*/
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while((output=br.readLine())!=null){
				responseMessage = responseMessage + output.toString();
			}
			System.out.println("PostCode API Response is " + responseMessage);
			conn.disconnect();
			JSONObject responseMessageJSON;
			try{
				responseMessageJSON = new JSONObject(responseMessage);
			}catch(JSONException e){
				System.out.println("JsonException occoured");
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
			System.out.println("EAC Schema Response is " + responseMessage);
			conn.disconnect();
			return responseMessage;
	}

	public JSONObject stringToObject(String expectedResponseBody) {
		JSONObject responseMessageJSON;
		try{
			responseMessageJSON = new JSONObject(expectedResponseBody);
		}catch(JSONException e){
			System.out.println("JsonException occoured");
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
