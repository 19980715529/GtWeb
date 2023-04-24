package com.smallchill.core.toolbox.grid;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.meta.IQuery;

public class GridManager {
	private final static GridManager me = new GridManager();

	private IGrid defaultGridFactory = Cst.me().getDefaultGridFactory();

	public static GridManager me() {
		return me;
	}

	private GridManager() {}

	public void setDefaultGridFactory(IGrid defaultGridFactory) {
		this.defaultGridFactory = defaultGridFactory;
	}
	
	public static Object paginate(String dbName, Integer page, Integer rows, String source, String para, String sort, String order, IQuery intercept, BladeController ctrl) {
		return me.defaultGridFactory.paginate(dbName, page, rows, source, para, sort, order, intercept, ctrl);
	}

	public static Object paginateBySelf(String dbName, Integer page, Integer rows, String source, String para, String sort, String order, IQuery intercept, BladeController ctrl) {
		return me.defaultGridFactory.paginateBySelf(dbName, page, rows, source, para, sort, order, intercept, ctrl);
	}

}
