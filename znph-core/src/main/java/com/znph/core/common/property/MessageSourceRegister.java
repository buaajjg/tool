package com.znph.core.common.property;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;



public class MessageSourceRegister extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		CodeMessageHolder.putAll(props);
	}
	
}
