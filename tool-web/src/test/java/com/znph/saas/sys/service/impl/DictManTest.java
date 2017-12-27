package com.znph.saas.sys.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.znph.saas.sys.entity.Dict;
import com.znph.saas.sys.service.DictService;

/**
 * Created by zhiboliu2 on 2017/9/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml"})
public class DictManTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private DictService dictService;
    @Test
    public void getDict(){
        try {
            List<Dict> list = dictService.getDictList("d_sex");
            System.out.println(JSON.toJSON(list));
            list = dictService.getDictListQuery("d_sex");
            System.out.println(JSON.toJSON(list));
            list = dictService.getDictListSel("d_sex");
            System.out.println(JSON.toJSON(list));
            String sex = dictService.getDict("d_sex", "3").getName();
            System.out.println(sex);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
