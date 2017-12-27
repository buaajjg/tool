package com.znph.saas.sys.utils;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.znph.core.common.util.Strings;
import com.znph.core.common.utlis.JsonUtils;
import com.znph.saas.sys.dto.CriterionPageDto;

/**
 * CriterionDto解析
 * 
 * @author Minco
 * @date 2017年12月1日 下午2:26:37
 * 
 */
public class ConditionalAnalysis {

	private static final String tablePre = "auto_";

	public static String select(String tableName) {
		
		return select(tableName, null,null,null);
	}
	
	public static String select(String tableName, Map<Object, Object> where) {
		
		return select(tableName, where,null,null);
	}

	public static String select(String tableName, Map<Object, Object> where,Map<Object, Object> orderby) {

		return select(tableName, where,orderby,null);
	}
	
	public static String selectCount(String tableName, Map<Object, Object> where) {
		
		if (Strings.isBlank(tableName)) {
			return null;
		}
		StringBuffer sBuffer = new StringBuffer();
		
		String selectStr = selectTableCount(tableName);
		sBuffer.append(selectStr);
		if(where != null && (!where.isEmpty())) {
			String whereStr = whereAnalysis(where);
			sBuffer.append(whereStr);
		}
		
		return sBuffer.toString();
	}

	public static String select(String tableName, Map<Object, Object> where,Map<Object, Object> orderby, CriterionPageDto page) {
		
		if (Strings.isBlank(tableName)) {
			return null;
		}
		StringBuffer sBuffer = new StringBuffer();
		
		String selectStr = selectTable(tableName);
		sBuffer.append(selectStr);
		if(where != null && (!where.isEmpty())) {
			String whereStr = whereAnalysis(where);
			sBuffer.append(whereStr);
		}
		if(orderby != null && (!orderby.isEmpty())) {
			String orderbyStr = orderbyAnalysis(orderby);
			sBuffer.append(orderbyStr);
		}
		
		if((where == null || where.isEmpty()) && page == null){
			String pageStr = pageAnalysis(new CriterionPageDto());
			sBuffer.append(pageStr);
		}
		
		if(page != null) {
			String pageStr = pageAnalysis(page);
			sBuffer.append(pageStr);
		}
		
		
		return sBuffer.toString();
	}

	public static String update(String tableName, Map<Object, Object> content, Map<Object, Object> where) {
		
		if(Strings.isBlank(tableName) || content.isEmpty() || where.isEmpty()) {
			return null;
		}
		String updateStr = updateTable(tableName);
		String contentStr = updateContentAnalysis(content);
		String whereStr = whereAnalysis(where);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(updateStr).append(contentStr).append(whereStr);

		return stringBuffer.toString();
	}

	public static String insert(String tableName, Map<Object, Object> content) {
		
		if(Strings.isBlank(tableName) || content.isEmpty() ) {
			return null;
		}
		String insertStr = insertTable(tableName);
		String contentStr = insertContentAnalysis(content);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(insertStr).append(contentStr);

		return stringBuffer.toString();
	}

	public static String delete(String tableName, Map<Object, Object> where) {
		
		if(Strings.isBlank(tableName) || where.isEmpty() ) {
			return null;
		}
		String deleteStr = deleteTable(tableName);
		String whereStr = whereAnalysis(where);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(deleteStr).append(whereStr);

		return stringBuffer.toString();
	}

	private static String selectTable(String table) {

		StringBuffer sb = new StringBuffer("select * from ");
		sb.append(tablePre).append(table);
		return sb.toString();
	}
	
	private static String selectTableCount(String table) {

		StringBuffer sb = new StringBuffer("select count(*) from ");
		sb.append(tablePre).append(table);
		return sb.toString();
	}

	private static String updateTable(String table) {

		StringBuffer sb = new StringBuffer("update ");
		sb.append(tablePre).append(table).append(" set ");
		return sb.toString();
	}

	private static String insertTable(String table) {

		StringBuffer sb = new StringBuffer("insert into ");
		sb.append(tablePre).append(table);
		return sb.toString();
	}

	private static String deleteTable(String table) {

		StringBuffer sb = new StringBuffer("delete from ");
		sb.append(tablePre).append(table);
		return sb.toString();
	}

	private static String insertContentAnalysis(Map<Object, Object> content) {
		content.put("del_flag", 1);
		content.put("create_by", 1);
		content.put("create_date", new Date());
		content.put("update_by", 1);
		content.put("update_date", new Date());
		
		
		StringBuffer columBuffer = new StringBuffer(" (id, ");
		StringBuffer valueBuffer = new StringBuffer(" VALUES ( ");

		String json = JSON.toJSONString(content, JsonUtils.features);
		json = json.substring(1, json.length() - 1);
		String[] entryArray = json.split(",");
		for (int i = 0; i < entryArray.length; i++) {
			String entry = entryArray[i];
			String[] keyValueArray = entry.split(":");

			String key = keyValueArray[0].substring(1, keyValueArray[0].length() - 1);
			columBuffer.append(key + ",");

			if (keyValueArray.length > 2) {
				int first = entry.indexOf(":");
				valueBuffer.append(entry.substring(first + 1) + ",");
			} else {
				valueBuffer.append(keyValueArray[1] + ",");
			}
		}
		valueBuffer.deleteCharAt(valueBuffer.length() - 1).append(" ) ");
		columBuffer.deleteCharAt(columBuffer.length() - 1).append(" ) ").append(valueBuffer);

		return columBuffer.toString();
	}

	private static String updateContentAnalysis(Map<Object, Object> content) {
		content.put("update_by", 1);
		content.put("update_date", new Date());
		
		StringBuffer sb = new StringBuffer();
		String json = JSON.toJSONString(content, JsonUtils.features);
		json = json.substring(1, json.length() - 1);
		String[] entryArray = json.split(",");
		for (int i = 0; i < entryArray.length; i++) {
			String entry = entryArray[i];
			String[] keyValueArray = entry.split(":");
			String key = keyValueArray[0].substring(1, keyValueArray[0].length() - 1);
			sb.append(key + "=");
			if (keyValueArray.length > 2) {
				int first = entry.indexOf(":");
				sb.append(entry.substring(first + 1) + ",");
			} else {
				sb.append(keyValueArray[1] + ",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	private static String whereAnalysis(Map<Object, Object> where) {

		StringBuffer sb = new StringBuffer(" where ");
		String json = JSON.toJSONString(where, JsonUtils.features);
		json = json.substring(1, json.length() - 1);
		String[] entryArray = json.split(",");
		for (int i = 0; i < entryArray.length; i++) {
			String entry = entryArray[i];
			String[] keyValueArray = entry.split(":");
			String key = keyValueArray[0].substring(1, keyValueArray[0].length() - 1);
			sb.append(key + "=");
			if (keyValueArray.length > 2) {
				int first = entry.indexOf(":");
				sb.append(entry.substring(first + 1) + " and ");
			} else {
				sb.append(keyValueArray[1] + " and ");
			}
		}
		String whereString = sb.toString();
		int lastIndex = whereString.lastIndexOf("and");
		whereString = whereString.substring(0, lastIndex);

		return whereString;
	}

	private static String orderbyAnalysis(Map<Object, Object> orderby) {

		StringBuffer sb = new StringBuffer(" order by ");
		String json = JSON.toJSONString(orderby, JsonUtils.features);
		json = json.substring(1, json.length() - 1);
		String[] entryArray = json.split(",");
		for (int i = 0; i < entryArray.length; i++) {
			String entry = entryArray[i];
			String[] keyValueArray = entry.split(":");
			String key = keyValueArray[0].substring(1, keyValueArray[0].length() - 1);
			String value = keyValueArray[1].substring(1, keyValueArray[1].length() - 1);
			sb.append(key).append(" ").append(value).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	private static String pageAnalysis(CriterionPageDto page) {

		page.getIndex();

		StringBuffer sb = new StringBuffer(" limit  ");
		sb.append(page.getIndex()).append(",").append(page.getSize());

		return sb.toString();
	}

}
