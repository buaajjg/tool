package com.znph.saas.sys.service.impl;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhiboliu2 on 2017/9/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class BeanUtilTest extends AbstractJUnit4SpringContextTests {
    @Test
    public void testCopyPropertiesExclude() throws Exception{

        System.out.println(JSONObject.toJSONString(new Date()));
    }

}
