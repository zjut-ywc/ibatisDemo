/*
 * @(#)JSONUtil.java        1.0 2010-6-26
 *
 * Copyright (c) 2010 SunYard System Engineering CoLtd.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * SunYard System Engineering CoLtd. ("Confidential Information").  
 * You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered 
 * into with SunYard.
 */
package com.zjutywc.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class JSONUtil {
	
	

	public static String strToJSONForDistll1(String sb) {
		StringBuffer json = new StringBuffer(500);
		//json.append("{\"success\":true,\"data\":{");
		String[] data=null;
		if(sb!=null){
			data=sb.split("#");
		}
	  
		if (data != null && data.length > 1) {
			int length=data.length;
			String[] key = null;
			for (int i=0;i<length;i++) {
				String aa = data[i];
				
				if(aa!=null){ 
					String[] aaArr=aa.split("@");
					if(aaArr!=null&&aaArr.length>1){
					String a0= (String)  aaArr[0];
					String a1= (String)  aaArr[1];
					//System.out.println(""+a0);
					//System.out.println(""+a1);
					if(!"".equals(a0)&&!"".equals(a1)){
						json.append("").append(a0).append(":")
						.append(convertNull(a1 )).append(",");
					}
					}
				}
				
				
			}
			json.deleteCharAt(json.length() - 1);
		}
		//json.append("}}");
		return json.toString();
	}
	
 
	public static String strToJSONForDistll2(String sb) {
		StringBuffer json = new StringBuffer(500);
		//json.append("{\"success\":true,\"data\":{");
		String[] data=null;
		if(sb!=null){
			data=sb.split("@");
		}
		//【二苯醚|1.5#二苯醚|9.8#|80#甲苯|21.8#|70#|31.5#甲苯|42#乙醇|155#甲醇|110#二苯醚|250#|100#|60#甲醇|59.3#|54.3#二苯醚|54.2#二苯醚|60#二苯醚|13.9#硝基苯|4.9#|75#甲苯|15.76#二苯醚|23.8#乙醇|343.5#二苯醚|12#铁粉|400#|86#乙醇|1.5#|212#|264.3#二苯醚|56#铁粉|59.6#铁粉|99.2#】
        //实例抛弃第一个分隔符前面的数据，从第2个分割符后面取，同时抛弃最后一个分割符的数据。
		if (data != null && data.length > 1) {
			int length=data.length;
			String[] key = null;
			for (int i=1;i<length-1;i++) {
				String aa = data[i];
				
				if(aa!=null){ 
					String[] aaArr=aa.split("#");
					if(aaArr!=null&&aaArr.length>1){
					String a0= (String)  aaArr[0];
					String a1= (String)  aaArr[1];
					//System.out.println(""+a0);
					//System.out.println(""+a1);
					if(!"".equals(a0)&&!"".equals(a1)){
						json.append("").append(a1).append(":")
						.append(convertNull(a0 )).append(",");
					}
					}
				}
				
				
			}
			json.deleteCharAt(json.length() - 1);
		}
		//json.append("}}");
		return json.toString();
	}
	
	
	/**
	 * 把HashMap中的数据转换成JSON数据 用于查看具体的一张业务工单 也就是要满足ExtJs是Form.load的参数格式
	 */
	public static String oneHmToJson(HashMap<String, Object> data) {
		StringBuffer json = new StringBuffer(200);
		json.append("{\"success\":true,\"data\":{");
		if (data != null && data.size() > 0) {
			Iterator<String> keys = data.keySet().iterator();
			String key = null;
			while (keys.hasNext()) {
				key = keys.next();
				json.append("\"").append(key).append("\":\"")
						.append(convertNull(data.get(key))).append("\",");
			}
			json.deleteCharAt(json.length() - 1);
		}
		json.append("}}");
		return json.toString();
	}

	/**
	 * null值转空值
	 */
	private static String convertNull(Object data) {
		return data == null ? "" : data.toString();
	}

	/**
	 * 把List中的HashMap数据转换成JSON数据 用于处理多条记录
	 */
	public static String listToJSON(List<HashMap<String, Object>> rows) {
		StringBuffer json = new StringBuffer(400);
		json.append("{\"total\":");
		json.append(rows.size());
		json.append(",\"rows\":[");
		if (rows != null && rows.size() > 0) {
			HashMap<String, Object> row = null;
			for (int i = 0, len = rows.size(); i < len; i++) {
				row = rows.get(i);
				Iterator<String> rowI = row.keySet().iterator();
				json.append("{");
				String key = null;
				while (rowI.hasNext()) {
					key = rowI.next();
					json.append("\"").append(key);
					json.append("\":\"");
					json.append(convertNull(row.get(key)));
					json.append("\",");
				}
				json.deleteCharAt(json.length() - 1);
				json.append("},");
			}
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]}");
		return json.toString();
	}

	/**
	 * 把List中的HashMap数据转换成JSON数据 用于处理多条记录
	 */
	public static String listsToJSON(List<HashMap<String, Object>> rows,
			int allLength) {

		StringBuffer json = new StringBuffer(400);
		json.append("{\"total\":");
		json.append(allLength);
		json.append(",\"rows\":[");
		if (rows != null && rows.size() > 0) {
			HashMap<String, Object> row = null;
			for (int i = 0, len = rows.size(); i < len; i++) {
				row = rows.get(i);
				Iterator<String> rowI = row.keySet().iterator();
				json.append("{");
				String key = null;
				while (rowI.hasNext()) {
					key = rowI.next();
					json.append("\"").append(key);
					json.append("\":\"");
					json.append(convertNull(row.get(key)));
					json.append("\",");
				}
				json.deleteCharAt(json.length() - 1);
				json.append("},");
			}
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]}");
		// System.out.println(json.toString());
		return json.toString();
	}

	/**
	 * 转换ExtJs识别JSON树
	 */
	public static String extjsTree(List<HashMap<String, Object>> rows) {

		StringBuffer json = new StringBuffer(100);
		json.append("[");

		if (rows != null && rows.size() > 0) {
			HashMap<String, Object> row = null;
			for (int i = 0, len = rows.size(); i < len; i++) {
				row = rows.get(i);
				json.append("{id:").append(row.get("ID")).append(",");
				json.append("text:\"").append(row.get("TEXT")).append("\",");
				json.append("leaf:true,");
				json.append("key:\"").append(row.get("QTIP")).append("\"},");
			}
		}
		json.deleteCharAt(json.length() - 1);
		json.append("]");
		return json.toString();
	}

	/**
	 * 转换ExtJs识别JSON树
	 */
	public static String groupTree(List<HashMap<String, Object>> rows) {

		StringBuffer json = new StringBuffer(100);
		json.append("[");

		if (rows != null && rows.size() > 0) {
			HashMap<String, Object> row = null;
			for (int i = 0, len = rows.size(); i < len; i++) {
				row = rows.get(i);
				json.append("{id:").append(row.get("ID")).append(",");
				json.append("key:\"").append(row.get("KEY")).append("\",");
				if (((String) row.get("ISSELECTED")).equals("1")) {
					json.append("checked:false,");
				}
				json.append("oflag:").append(row.get("OFLAG")).append(",");
				json.append("text:\"").append(row.get("TEXT")).append("\"");
				json.append(",pinyin:\"").append(row.get("PINYIN"))
						.append("\"");
				if (row.get("LEAF") != null) {
					json.append(",leaf:").append(row.get("LEAF"));
				}
				json.append("},");
			}
		}

		json.deleteCharAt(json.length() - 1);
		json.append("]");
		return json.toString();
	}

	/**
	 * JSON格式数据转换为集合
	 */
	public static HashMap<String, String> jsonToHashMap(String json) {

		HashMap<String, String> oneData = null;

		char first = json.charAt(0);

		char last = json.charAt(json.length() - 1);

		if (first == '{' && last == '}') {
			// 去掉{}
			json = trimQu(json);
			String[] params = json.split(",");
			oneData = new HashMap<String, String>();
			String[] hm = null;
			for (int i = 0, len = params.length; i < len; i++) {
				hm = params[i].split(":");
				oneData.put(trimQu(hm[0]), trimQu(hm[1]));
			}
		}
		return oneData;
	}

	/**
	 * 去掉字符串的第一个和最后一个字符
	 */
	private static String trimQu(String s) {
		s = (s != null && s.length() > 2) ? s.substring(1, s.length() - 1) : "";
		return s;
	}
	
	
	/**
	* 将JSON字符串处理成javascript可以识别的
	*
	* @param ors
	* @return
	*/
	public static String dealString4JSON(String ors) {
	       ors = ors == null ? "" : ors;
	       StringBuffer buffer = new StringBuffer(ors);
	       ///在替换的时候不要使用 String.replaceAll("\\","\\\\"); 这样不会达到替换的效果 因为这些符号有特殊的意义,在正则     ///表达式里要用到
	       int i = 0;
	       while (i < buffer.length()) {
	        if (buffer.charAt(i) == '\'' || buffer.charAt(i) == '\\') {
	         buffer.insert(i, '\\');
	         i += 2;
	        } else {
	         i++;
	        }
	      }
	       return buffer.toString().replaceAll("\r", "</br>").replaceAll("\n", "</br>");
	}


	/**
	 * 过滤特殊字符
	 */
	public static String quote(String string) {
		if (string == null || string.length() == 0) {
			return "\"\"";
		}
		char b;
		char c = 0;
		int i;
		int len = string.length();
		StringBuffer sb = new StringBuffer(len + 4);
		String t;

		//sb.append('"');
		for (i = 0; i < len; i += 1) {
			b = c;
			c = string.charAt(i);
			switch (c) {
			case '\\':
			case '"':
				sb.append('\\');
				sb.append(c);
				break;
			case '/':
				if (b == '<') {
					sb.append('\\');
				}
				sb.append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\'':
				sb.append("");
				break;
			default:
				if (c < ' ') {
					t = "000" + Integer.toHexString(c);
					sb.append("\\u" + t.substring(t.length() - 4));
				} else {
					sb.append(c);
				}
			}
		}
		//sb.append('"');
		return sb.toString();
	}
	/**
	 * 返回json格式的错误信息
	 * */
	public static String errorInjson(String errorCode,String returnCode,String errorInfo) {
		   
		return "{\"result\": \"['"+errorCode+"','"+returnCode+"','"+errorInfo+"']\"}" ;
	}

	public static void main(String[] args) {

	}
}
