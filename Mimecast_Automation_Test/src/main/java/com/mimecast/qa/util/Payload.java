package com.mimecast.qa.util;

import com.mimecast.qa.base.BaseBackEndTest;


public class Payload extends BaseBackEndTest{
     public static String postData(){
		String body="{"+
				    "\"postcodes\":\"[\"PR3 0SG\", \"M45 6GN\", \"EX165BL\"]"
				    +"}";
		return body;
	}

}
