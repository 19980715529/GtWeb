package com.smallchill.game.model.request;

import java.util.ArrayList;
import java.util.List;

public class BaseRequest {
	private Integer from;
	private Integer size=10 ;
	private Integer page=1 ;
	private Long records;
	private List<OrderByRequest> orderBy=new ArrayList<OrderByRequest>();
	
	public BaseRequest() {
	}

	public Integer getFrom() {
		from = (page-1) * size;
		return from;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<OrderByRequest> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderByRequest orderBy) {
		this.orderBy.add(orderBy);
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}
}