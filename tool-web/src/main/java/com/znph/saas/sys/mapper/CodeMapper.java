package com.znph.saas.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.vo.CodeSearchVo;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author minco
 * @since 2017-11-28
 */
public interface CodeMapper extends BaseMapper<Code> {
	
    List<Code> searchList(CodeSearchVo codeSearchVo);

    int searchTotal(CodeSearchVo codeSearchVo);
    
    List<Code> getCodeVoInfo(Long id);
    

}