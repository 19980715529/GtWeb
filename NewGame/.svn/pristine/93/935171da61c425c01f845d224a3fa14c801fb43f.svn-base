package com.smallchill.core.toolbox.grid;

import java.util.List;
import java.util.Map;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.meta.IQuery;

@SuppressWarnings("unchecked")
public class LigerGridFactory extends BaseGridFactory {

	public LigerGrid<Map<String, Object>> paginate(String dbName, Integer page, Integer rows,
			String source, String para, String sort, String order,
			IQuery intercept, BladeController ctrl) {
		BladePage<Map<String, Object>> list = (BladePage<Map<String, Object>>) super.basePaginate(dbName, page, rows, source, para, sort, order, intercept, ctrl);
		
		int _pageNum = (int) list.getPage();
		int _pageSize = (int) list.getPageSize();
		int _totalPage = (int) list.getTotal();
		long _totalSize = list.getRecords();
		List<Map<String, Object>> _rows = list.getRows();
		
		LigerGrid<Map<String, Object>> grid = new LigerGrid<>(_pageNum, _pageSize, _totalPage, _totalSize, _rows);
		return grid;
	}

	@Override
	public Object paginateBySelf(String dbName, Integer page, Integer rows,
			String source, String para, String sort, String order,
			IQuery intercept, BladeController ctrl) {
		// TODO Auto-generated method stub
		return null;
	}

}
