package com.smallchill.core.toolbox.grid;

import java.util.List;

/**
 * EasyGrid封装bean
 */
public class EasyGrid<E> {
	/** 总记录  **/
	private long total; 
	
	/** 显示的记录  **/
	private List<E> rows; 

	/** 开始记录  **/
	private int from;

	/** 结束记录  **/
	private int size;

	/** 当前页  **/
	private int nowpage; 

	/** 每页显示的记录数  **/
	private int pagesize; 

	
	public EasyGrid() {

	}
	
	public EasyGrid(long total, List<E> rows, int nowpage, int pagesize) {
		super();
		this.total = total;
		this.rows = rows;
		this.nowpage = nowpage;
		this.pagesize = pagesize;
		onInit();
	}

	public void onInit(){
        //计算开始的记录和结束的记录  
        this.from = (this.nowpage - 1) * this.pagesize;
        this.size = this.pagesize;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

}
