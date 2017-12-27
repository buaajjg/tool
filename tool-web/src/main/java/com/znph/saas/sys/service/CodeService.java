package com.znph.saas.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.vo.CodeSearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author minco
 * @since 2017-11-24
 */
public interface CodeService extends IService<Code> {
	
	Map<String, Object> searchList(CodeSearchVo codeSearchVo);
	
	Map<String, Object> getCodeInfo(Long id);
	
	Code getCode(Long id);
	
	/**
	 * 获取每张表对应接口数量
	 * @param tableIds
	 * @return
	 * @author Minco
	 * @date 2017年11月28日
	 */
	Map<Long, Long> getTableCodeNumber(Long... tableIds);
	
	Map<String, Object> getPreAddInfo(Long tableId);
	
}
