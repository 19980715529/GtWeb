package com.smallchill.core.meta;

import java.util.Map;

import com.smallchill.core.constant.ConstCurd;

public abstract class MetaManager extends MetaTool implements IMeta,ConstCurd{

	public Class<? extends MetaIntercept> intercept() {
		return null;
	}

	public String paraPrefix() {
		return null;
	}

	public Map<String, Object> switchMap() {
		return null;
	}

}
