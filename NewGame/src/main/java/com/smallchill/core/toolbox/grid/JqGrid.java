package com.smallchill.core.toolbox.grid;

import java.util.List;

/**
 * JqGrid封装bean
 */
public class JqGrid<E> {
	/** 返回结果集  **/
	private List<E> rows;

	/** 返回的总页数  **/
	private long total;

	/** 返回的当前页数  **/
	private long page;

	/** 一共有多少数据  **/
	private long records;

	public JqGrid() {

	}
	
	public JqGrid(List<E> rows, long total, long page, long records) {
		super();
		this.rows = rows;
		this.total = total;
		this.page = page;
		this.records = records;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "JqGrid{" +
				"rows=" + rows +
				", total=" + total +
				", page=" + page +
				", records=" + records +
				'}';
	}
}
