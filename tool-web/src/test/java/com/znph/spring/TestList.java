package com.znph.spring;
/**
* @author Minco
* @date 2017年11月28日 下午2:15:28
* 
*/

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.znph.saas.sys.entity.Table;

public class TestList {
	
	public static void Main(String[] args) {
		
    	Table table2 = new Table();
    	table2.setId(312L);
    	Table table3 = new Table();
    	table3.setId(312222L);
    	Table table1 = new Table();
    	table1.setId(312111L);
    	List<Table> list1 = Arrays.asList(table1,table2,table3);
		Long[] aa = list1.stream().map(Table::getId).collect(Collectors.toList()).toArray(new Long[0]);
		System.out.println(aa);
	}

}
