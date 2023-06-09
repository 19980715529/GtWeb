package com.smallchill.core.toolbox.grid;

import java.util.List;

/**
 * LigerGrid封装bean
 */
public final class LigerGrid<E> {
	/** 当前页号   **/
	private int pageNum;

	/** 每页记录数   **/
	private int pageSize;

	/** 总页数   **/
	private int totalPage;

	/** 总记录数   **/
	private long totalSize;

	/** 结果集   **/
	private List<E> rows;

	/** 当前页起始行号   **/
	private long rowBegin;

	/** 当前页结束行号   **/
	private long rowEnd;

	public LigerGrid(int pageNum, int pageSize, int totalPage, long totalSize, List<E> rows) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalSize = totalSize;
		this.rows = rows;
		onInit();
	}

	private void onInit() {
        //计算开始的记录和结束的记录  
		this.rowBegin = (pageNum - 1) * pageSize + 1;
		this.rowEnd = pageNum * pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		if (pageNum < 1)
			this.pageNum = 1;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 1)// 不合法时，默认至少3条数据
			this.pageSize = 3;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize < 0 ? 0 : totalSize;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public long getRowBegin() {
		return rowBegin;
	}

	public long getRowEnd() {
		return rowEnd;
	}
}
