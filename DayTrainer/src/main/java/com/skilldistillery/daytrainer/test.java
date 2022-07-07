package com.skilldistillery.daytrainer;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
		
		String a = "0001090000";

		System.out.println(a);
		int res = Integer.parseInt(a);
		System.out.println(res);
		
		

	}
	
    public static String decodeString(String s, int start, int stop, int freq) {
		String sequence = s.substring(start, stop);
		
		//build sequence
		StringBuilder sb = new StringBuilder(sequence.length() * freq);
		for(int i = 0; i < freq; ++i) {
			sb.append(sequence);
		}
				
		//Find start of count
		start = start - (freq + "").length() - 1;
		
		String decoded = s.substring(0, start) + sb.toString() + s.substring(stop + 1);
        return decoded;
    }
    
    public static int[] findDecodeSequence(String s) {
    	
    	int start = -1;
    	int stop = -1;
    	
    	//Find sequence
    	for(int i = 0; i < s.length(); ++i) {
    		char c = s.charAt(i);
    		if(c == '[')
    			start = i;
    		if(c == ']')
    			stop = i;
    		
    		if(start != -1 && stop != -1)
    			break;
    	}
    	
    	//find freq
    	String freq = "";
    	for(int i = start - 1; i >= 0; --i) {
    		if(Character.isDigit(s.charAt(i))) {
    			freq = s.charAt(i) + freq;
    		}
    		else {
    			break;
    		}
    	}
    	if(start != -1) {
    		return new int[]{start, stop, Integer.parseInt(freq)};
    	}
    	
    	return null;
    }

}
