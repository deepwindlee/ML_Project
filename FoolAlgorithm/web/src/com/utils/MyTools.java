package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTools {
	
	/**
	 *  �ж�email�Ƿ���Ч
	 */
	public static boolean validateEmail(String email) {
		 boolean flag = false;
		 int pos = email.indexOf("@");
		 if (pos == -1 || pos == 0 || pos == email.length() - 1) {
		 return false;
		 }
		 String[] strings = email.split("@");
		 if (strings.length != 2) {
		 return false;
		 }
		 CharSequence cs = strings[0];
		 for (int i = 0; i < cs.length(); i++) {
		 char c = cs.charAt(i);
		 if (!Character.isLetter(c) && !Character.isDigit(c)) {
		 return false;
		 }
		 }
		 pos = strings[1].indexOf(".");
		 if (pos == -1 || pos == 0 || pos == email.length() - 1) {
		 return false;
		 }
		 strings = strings[1].split(".");
		 for (int j = 0; j < strings.length; j++) {
		 cs = strings[j];
		 if (cs.length() == 0) {
		 return false;
		 }
		 for (int i = 0; i < cs.length(); i++) {
		 char c = cs.charAt(i);
		 if (!Character.isLetter(c) && !Character.isDigit(c)) {
		 return false;
		 }
		 }

		 }
		 return true;
		 }
	
	/**
	 * �ַ���ת����
	 */
	public static int strToint(String value){
		int i=-1;
		if(value==null||value.equals(""))
			return i;
		try{
			i=Integer.parseInt(value);
		}catch(NumberFormatException e){
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
	
	/**
	 * ����ת��
	 */
    public  static String  toChinese(String value) {
    	if (value == null)
    		return "";
    	try {
            value = new String(value.getBytes("ISO8859_1"), "utf-8");
            return value;
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * ת��ʱ���ʽ
     */
	public static String changeTime(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy��dd�� HH:mm:ss");
		return format.format(date);		
	}
}
