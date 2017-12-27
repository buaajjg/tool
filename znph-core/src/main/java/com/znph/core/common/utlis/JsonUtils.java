package com.znph.core.common.utlis;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
* @author Minco
* @date 2017年9月22日 下午3:01:09
* 
*/
public class JsonUtils {
	
	    public static final SerializerFeature[] features = {
	    		SerializerFeature.WriteMapNullValue,
	            SerializerFeature.WriteNullListAsEmpty,
	            SerializerFeature.WriteNullBooleanAsFalse,
	            SerializerFeature.WriteNullStringAsEmpty,
	            SerializerFeature.WriteDateUseDateFormat
	    };



	

}
