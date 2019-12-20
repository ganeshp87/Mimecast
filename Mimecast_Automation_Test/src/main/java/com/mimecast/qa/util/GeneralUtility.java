package com.mimecast.qa.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

public class GeneralUtility {

	public static boolean compareHashSet(HashSet<String> hs1,HashSet<String>hs2){
		System.out.println("HashSet value 1:: " + hs1);
		System.out.println("HashSet value 2:: " + hs2);
		boolean isHashsetEqual= false;
		int count=0;
		
		if(hs1.size()!=hs2.size()){
			System.out.println("Size of the hash set are not equal");
			
			for(String hs1_key:hs1){
				if(hs2.contains(hs1_key)){
					System.out.println("Matched HashSet 1:: " +hs1_key+ " in Haashset 2" );
				}else{
					System.out.println("Failed doesnot match HashSet_1:: "+ hs1_key+ " in HashSet2");
					count++;
				}
			 }
			if(hs2.size()>hs1.size()){
				for(String hs2_key:hs2){
					if(hs1.contains(hs2_key)){
						System.out.println("Matched HashSet 2:: " +hs2_key+ " in Haashset 1" );
					}else{
						System.out.println("Failed doesnot match HashSet_2:: "+ hs2_key+ " in HashSet1");
						count++;
					}
				 }
			}
		} else{
			for(String hs1_key: hs1){
				if(hs2.contains(hs1_key)){
					isHashsetEqual=true;
					System.out.println("Matched HashSet 1:: " +hs1_key+ " in Haashset 2" );
				}else{
					System.out.println("Failed doesnot match HashSet_1:: "+ hs1_key+ " in HashSet2");
					count++;
				}
			}
			
			for(String hs2_key: hs2){
				if(hs1.contains(hs2_key)){
					isHashsetEqual=true;
					System.out.println("Matched HashSet 2:: " +hs2_key+ " in Haashset 1" );
				}else{
					System.out.println("Failed doesnot match HashSet_2:: "+ hs2_key+ " in HashSet1");
					count++;
				}
			}
			if(count>0){
				isHashsetEqual=false;
			}
		}
		
		return isHashsetEqual;
		
	}
	
}
