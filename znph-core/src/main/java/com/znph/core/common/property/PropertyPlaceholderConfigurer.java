package com.znph.core.common.property;

import java.util.Properties;
import java.util.Scanner;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;


public class PropertyPlaceholderConfigurer
		extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	private boolean unresolvablePlaceholderToEmpty = false;
	private boolean unresolvablePlaceholderInput = true;
	private Scanner scanner;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		PropertyHolder.putAll(props);
	}

	@Override
	protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
		String resolvePlaceholder = null;
		if(placeholder != null && !"".equals(placeholder)){
			 resolvePlaceholder = super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
			if (resolvePlaceholder == null) {
				if (unresolvablePlaceholderInput) {
					System.out.println("-------------------------------------------------");
					System.out.println("please input required property: " + placeholder);
					scanner = new Scanner(System.in);
					String nextLine = scanner.nextLine();
					resolvePlaceholder = nextLine;
					System.out.println("scanned value of " + placeholder + ": " + nextLine);
					System.out.println("-------------------------------------------------");
				}
			}
			if (resolvePlaceholder == null) {
				if (unresolvablePlaceholderToEmpty) {
					resolvePlaceholder = "";
				}
			}
			
		}

		return resolvePlaceholder;
	}

	public void setUnresolvablePlaceholderToEmpty(boolean unresolvablePlaceholderToEmpty) {
		this.unresolvablePlaceholderToEmpty = unresolvablePlaceholderToEmpty;
	}

	public void setUnresolvablePlaceholderInput(boolean unresolvablePlaceholderInput) {
		this.unresolvablePlaceholderInput = unresolvablePlaceholderInput;
	}

}
