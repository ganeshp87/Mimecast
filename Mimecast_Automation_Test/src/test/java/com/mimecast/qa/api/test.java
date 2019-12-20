package com.mimecast.qa.api;

public class test {
    public static boolean validate(String username) {
    	
    	 int min =6;
    	 int max=16;
    	 int special=0;
    	 int upCount=0;
    	 int loCount=0;
    	 int digit=0;
    	 
    	 if(username.length()>=min&&username.length()<=max && !username.contains(" ")&& username.matches("^[A-Z -].*$")&& !username.endsWith("-")){
    	        return true;

    	    }
    	 
    	    return false;
    	   
    }    
    public static void main(String[] args) {
        System.out.println(validate("Mike-Standish"));  //Valid username
        System.out.println(validate("Mike Standish")); // Invalid username
    }

   }