package com.znph.spring;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.znph.core.common.util.Collections;
import com.znph.core.common.util.Dates;

/**
* @author Minco
* @date 2017年9月23日 下午2:05:34
* 
*/
public class JunitTest {
	
	static Logger logger = LoggerFactory.getLogger(JunitTest.class);
    
    @Test
    public void test2() {
    	List<String> list = Arrays.asList("aa","aa");
    	list.isEmpty();
    	System.out.println(Collections.isEmpty(list));
      	System.out.println(list.isEmpty());
    	String aa = "aa";
    	String aString = String.format("含氮量:%s;含磷量：%s;含钾量：%s;含有机质:%s", aa,aa,aa,aa);
    	System.out.println(aString);
    	
    	Map<String,String> map = Collections.hashMap();
    	map.put("name", "今晚打老虎");
    	System.out.println(map.isEmpty());
    	

    	
    }

    @Test
    public void test3() {
    	String valid_date  = "2005.05.06-2017.08.09";
    	System.out.println(Dates.parse(valid_date, "yyyy.MM.dd"));
    	Date value = new Date();

    	
		List<Class<?>> list = Arrays.asList(String.class,Integer.class,Double.class,Float.class,Short.class,Long.class,Boolean.class);
		if(list.contains(value.getClass())) {
			System.out.println(value);
		}

    }



}
