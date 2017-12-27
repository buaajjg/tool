package com.znph.core.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.znph.core.common.bean.Result;
import com.znph.core.common.message.CodeMessage;
import com.znph.core.common.property.CodeMessageHolder;



/**
* @author Minco
* @date 2017年8月4日 上午11:12:21
* 
*/
public class MyHandlerExceptionResolver  implements HandlerExceptionResolver  {
	
	Logger logger = LoggerFactory.getLogger(MyHandlerExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {
			Result result = null;			
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter writer = null;
			try {			
				writer = response.getWriter();	
				if(ex instanceof BaseException) {
					BaseException baseException = BaseException.class.cast(ex);
					CodeMessage codeMessage = CodeMessageHolder.get(baseException.getCodeMessageIdentity());
					if (codeMessage != null) {
						String message = codeMessage.getMessage(baseException.getArgs());
						result = Result.failure(message, codeMessage.getCode());
					} else {
						result = Result.failure(baseException.getLocalizedMessage());
					}
				}else if(ex instanceof AuthenticationException){
					result = Result.failure(ex.getMessage());
				}else {
					//result = Result.failure("系统异常");
					result = Result.failure(ex.getMessage());
				}				
				writer.write(JSON.toJSONString(result));
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}finally{				
				if(writer != null){
					writer.flush();
					writer.close();
				}							
			}

		return null;
	}

}
