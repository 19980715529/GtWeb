package com.smallchill.core.toolbox.grid;

import java.io.Serializable;
import java.util.List;

import com.smallchill.core.toolbox.Func;

@SuppressWarnings("serial")
public class BladePage<T> implements Serializable{

	/** 返回结果集 **/
	private List<T> rows;

	/** 返回的总页数 **/
	private long total;

	/** 返回的当前页数 **/
	private long page;

	/** 每页显示的记录数 **/
	private long pageSize;

	/** 一共有多少数据 **/
	private long records;

	/** 当前页起始行号 **/
	private long rowBegin;

	/** 当前页结束行号 **/
	private long rowEnd;

	public BladePage(List<T> rows, long page, long pageSize, long records) {
		setRows(rows);
		setPage(page);
		setPageSize(pageSize);
		setRecords(records);
		onInit();
	}

	private void onInit() {
		this.rowBegin = (page - 1) * pageSize + 1;
		this.rowEnd = page * pageSize;
		long pages = Func.toLong(records / pageSize, 0);
		this.total = (records % this.pageSize == 0L) ? pages : (pages + 1);
	}
	
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
		if (page < 1)
			this.page = 1;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 1)
			this.pageSize = 5;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records < 0 ? 0 : records;
	}

	public long getRowBegin() {
		return rowBegin;
	}

	public long getRowEnd() {
		return rowEnd;
	}

}
