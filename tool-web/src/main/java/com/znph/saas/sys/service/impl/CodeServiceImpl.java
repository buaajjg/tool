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
import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.entity.Table;
import com.znph.saas.sys.mapper.CodeMapper;
import com.znph.saas.sys.service.CodeService;
import com.znph.saas.sys.service.DictService;
import com.znph.saas.sys.service.TableService;
import com.znph.saas.sys.vo.CodeSearchVo;

/**
 * @author minco
 * @since 2017-11-24
 */
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {

	@Resource
	private DictService dictService;

	@Resource
	private TableService tableService;

	@Override
	public Map<String, Object> searchList(CodeSearchVo codeSearchVo) {
		Map<String, Object> map = new HashMap<>();

		List<Code> list = baseMapper.searchList(codeSearchVo);
		int total = baseMapper.searchTotal(codeSearchVo);

		map.put("list", list);
		map.put("total", total);
		map.put("d_have", dictService.getDictList(DictContant.D_HAVE));

		return map;
	}

	@Override
	public Map<String, Object> getCodeInfo(Long id) {

		Map<String, Object> map = new HashMap<>();
		Code code = getCode(id);
		map.put("code", code);
		map.put(DictContant.D_CODE_TYPE, dictService.getDictList(DictContant.D_CODE_TYPE));
		return map;
	}

	@Override
	public Map<Long, Long> getTableCodeNumber(Long... tableIds) {

		EntityWrapper<Code> ew = new EntityWrapper<>();
		ew.in("table_id", tableIds);
		List<Code> list = selectList(ew);
		Map<Long, Long> map = list.stream().collect(Collectors.groupingBy(Code::getTableId, Collectors.counting()));

		return map;
	}

	@Override
	public Map<String, Object> getPreAddInfo(Long tableId) {

		Table table = tableService.selectById(tableId);
		Map<String, Object> tableMap = Collections.hashMap();
		tableMap.put("tableId", table.getId());
		tableMap.put("tableName", table.getTableName());
		tableMap.put(DictContant.D_CODE_TYPE, dictService.getDictList(DictContant.D_CODE_TYPE));

		return tableMap;
	}

	@Override
	public Code getCode(Long id) {
		Code code = new Code();
		if (id != null) {
			List<Code> list = baseMapper.getCodeVoInfo(id);
			if (Collections.isNotEmpty(list)) {
				code = list.get(0);
			}
		}
		return code;
	}

}
