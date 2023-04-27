package com.smallchill.core.toolbox.grid;

import java.util.List;
import java.util.Map;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.meta.IQuery;

@SuppressWarnings("unchecked")
public class JqGridFactory extends BaseGridFactory {

	public JqGrid<Map<String, Object>> paginate(String dbName, Integer page, Integer rows,
			String source, String para, String sort, String order,
			IQuery intercept, BladeController ctrl) {
		
		BladePage<Map<String, Object>> list = (BladePage<Map<String, Object>>) super.basePaginate(dbName, page, rows, source, para, sort, order, intercept, ctrl);
		
		List<Map<String, Object>> _rows = list.getRows();
		long _total = list.getTotal();
		long _page = list.getPage();
		long _records = list.getRecords();
		
		JqGrid<Map<String, Object>> grid = new JqGrid<>(_rows, _total, _page, _records);
		return grid;
		
	}

	public JqGrid<Map<String, Object>> paginateBySelf(String dbName, Integer page, Integer rows,
			String source, String para, String sort, String order,
			IQuery intercept, BladeController ctrl) {
		
		BladePage<Map<String, Object>> list = (BladePage<Map<String, Object>>) super.basePaginateBySelf(dbName, page, rows, source, para, sort, order, intercept, ctrl);
		
		List<Map<String, Object>> _rows = list.getRows();
		long _total = list.getTotal();
		long _page = list.getPage();
		long _records = list.getRecords();
		
		JqGrid<Map<String, Object>> grid = new JqGrid<>(_rows, _total, _page, _records);
		return grid;
		
	}

}
