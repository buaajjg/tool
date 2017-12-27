package com.znph.saas.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.znph.core.common.util.Collections;
import com.znph.core.common.util.Strings;
import com.znph.saas.sys.entity.Dict;
import com.znph.saas.sys.mapper.DictMapper;
import com.znph.saas.sys.service.DictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author minco
 * @since 2017-09-20
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final Map<String, List<Dict>> dictMap = new HashMap<>();

    public Dict getDict(String tableName, String code) {
        Map<String, Dict> map = getDictMap(tableName);
        if (map == null) {
            return null;
        }
        if(Strings.isBlank(code)) {
        	return new Dict();
        }
        Dict dict = map.get(code);
        return dict == null ? new Dict() : dict;
    }

    public String getDictName(String tableName, String code) {

        return getDict(tableName, code).getName();
    }

    public List<Dict> getDictList(String tableName) {
        List<Dict> list = dictMap.get(tableName);
        if (list == null) {
            EntityWrapper<Dict> ew = new EntityWrapper<Dict>();
            ew.eq("table_name", tableName);
            ew.orderBy("code");
            list = baseMapper.selectList(ew);
            dictMap.put(tableName, list);

            if (list == null) {
                list = new ArrayList<Dict>();
            }
        }
        return list;
    }
    
    public Map<String, List<Dict>> getDictList(String[] tableNames) {
    	Map<String, List<Dict>> map = Collections.hashMap();
    	for (String tableName : tableNames) {
    		List<Dict> list = getDictList(tableName);
    		if(Collections.isNotEmpty(list)) {
    			map.put(tableName, list);
    		}
		}
    	return map;
    }
    
    

    private Map<String, Dict> getDictMap(String tableName) {
        List<Dict> list = getDictList(tableName);
        if (list == null)
            return null;
        Map<String, Dict> map = new HashMap<>();
        for (int i = 0; i < list.size(); ++i) {
            Dict dict = list.get(i);
            map.put(dict.getCode(), dict);
        }
        return map;
    }


    public List<Dict> getDictListQuery(String tableName) {
        List<Dict> list = new ArrayList<>();
        list.add(new Dict("", "全部", "", ""));
        list.addAll(getDictList(tableName));
        return list;
    }

    public List<Dict> getDictListSel(String tableName) {
        List<Dict> list = new ArrayList<>();
        list.add(new Dict("", "请选择", "", ""));
        list.addAll(getDictList(tableName));
        return list;
    }

    public String getMoreTitel(String tableName, String codes) {
    	
       	String titles = "";
    	if(Strings.isNotBlank(codes)) {    
    		String[] codeArray = codes.split(",");		
        	for (String code : codeArray) {
        		titles += ","+getDictName(tableName,code);
    		}
        	titles = titles.substring(1);
    	}
    	   	   	
        return titles;
    }
}
