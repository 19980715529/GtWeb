package com.smallchill.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.core.base.controller.CurdController;
import com.smallchill.core.meta.IMeta;
import com.smallchill.system.meta.factory.ParameterFactory;
import com.smallchill.system.model.Parameter;

@Controller
@RequestMapping("/parameter")
public class ParameterContorller extends CurdController<Parameter>{

	@Override
	protected Class<? extends IMeta> metaFactoryClass() {
		
		return ParameterFactory.class;
	}
	
}
