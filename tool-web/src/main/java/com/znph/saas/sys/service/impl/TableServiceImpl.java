package com.znph.saas.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.znph.core.common.util.Collections;
import com.znph.saas.constant.DictContant;
import com.znph.saas.sys.entity.Table;
import com.znph.saas.sys.mapper.TableMapper;
import com.znph.saas.sys.service.CodeService;
import com.znph.saas.sys.service.DictService;
import com.znph.saas.sys.service.TableService;
import com.znph.saas.sys.service.ZnphCodeService;
import com.znph.saas.sys.vo.TableSearchVo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author minco
 * @since 2017-11-28
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements TableService {
	
	@Resource
	private CodeService codeService;
	
	@Resource
	private DictService dictService;
	
	@Resource
	private ZnphCodeService znphCodeService;

	@Override
	public Map<String, Object> searchTable(TableSearchVo tableSearchVo){
		Map<String, Object> map = new HashMap<>();

		List<Table> list = baseMapper.searchList(tableSearchVo);
		
		if(Collections.isNotEmpty(list)) {
			
			Long[] tableIdArray = list.stream().map(Table::getId).collect(Collectors.toList()).toArray(new Long[0]);
			Map<Long, Long> map2 = codeService.getTableCodeNumber(tableIdArray);
			for (Table table : list) {
				Long num = map2.get(table.getId());
				if(num != null) {
					table.setNumber(num.intValue());
				}else {
					table.setNumber(0);
				}
			}
		}
		
		int total = baseMapper.searchTotal(tableSearchVo);

		map.put("list", list);
		map.put("total", total);
		map.put("d_host", dictService.getDictList(DictContant.D_HOST));
		
		return map;
	}

	@Override
	public Integer existTableName(String tableName) {
		
		EntityWrapper<Table> ew = new EntityWrapper<>();
		ew.eq("table_name", tableName.trim());
		
		return selectCount(ew);
	}

	
}
