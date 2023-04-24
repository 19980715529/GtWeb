package com.smallchill.core.beetl;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.smallchill.common.beetl.BeetlRegister;

public class MyBeetlGroupUtilConfiguration extends BeetlGroupUtilConfiguration {

	public void initOther() {

		BeetlTemplate.registerTemplate(groupTemplate);
		BeetlRegister.registerTemplate(groupTemplate);

	}

}
