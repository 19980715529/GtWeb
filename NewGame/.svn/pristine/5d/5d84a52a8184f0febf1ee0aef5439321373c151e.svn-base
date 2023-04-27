package com.smallchill.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.core.base.controller.CurdController;
import com.smallchill.core.meta.IMeta;
import com.smallchill.system.meta.factory.AttachFactory;
import com.smallchill.system.model.Attach;

@Controller
@RequestMapping("/attach")
public class AttachController extends CurdController<Attach>{

	@Override
	protected Class<? extends IMeta> metaFactoryClass() {
		
		return AttachFactory.class;
	}

}
