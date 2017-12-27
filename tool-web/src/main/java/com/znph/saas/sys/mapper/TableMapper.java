package com.znph.saas.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.znph.saas.sys.entity.Table;
import com.znph.saas.sys.vo.TableSearchVo;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author minco
 * @since 2017-11-28
 */
public interface TableMapper extends BaseMapper<Table> {
	
    List<Table> searchList(TableSearchVo tableSearchVo);

    int searchTotal(TableSearchVo tableSearchVo);

}