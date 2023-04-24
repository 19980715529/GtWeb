package com.smallchill.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.core.base.controller.CurdController;
import com.smallchill.core.meta.IMeta;
import com.smallchill.system.meta.factory.LLogFactory;
import com.smallchill.system.model.LoginLog;

@Controller
@RequestMapping("/llog")
public class LLogController extends CurdController<LoginLog>{

	@Override
	protected Class<? extends IMeta> metaFactoryClass() {
		
		return LLogFactory.class;
	}

}
