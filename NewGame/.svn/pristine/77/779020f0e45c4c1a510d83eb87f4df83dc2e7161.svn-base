package com.smallchill.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.core.base.controller.CurdController;
import com.smallchill.core.meta.IMeta;
import com.smallchill.system.meta.factory.OLogFactory;
import com.smallchill.system.model.OperationLog;

@Controller
@RequestMapping("/olog")
public class OLogController extends CurdController<OperationLog>{

	@Override
	protected Class<? extends IMeta> metaFactoryClass() {
		
		return OLogFactory.class;
	}

}
