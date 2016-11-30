package com.flora.domain.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页及排序属性
 */
public class BaseQueryVO implements Serializable {

	/**
	 * 数据库第一条记录从0开始
	 */
	protected transient Integer startIndex;

	protected transient Integer pageSize;

	protected transient Integer currentPage;

	protected transient List<String> orderFieldList;

	protected transient List<String> orderFieldTypeList;

	protected String dt; // 统计日期，格式yyyyMMdd

	protected String dtStartEq; // 统计日期>=，格式yyyyMMdd

	protected String dtEndEq; // 统计日期<=，格式yyyyMMdd

	protected String dtEndNe; // 统计日期<，格式yyyyMMdd

	public BaseQueryVO() {

	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.initStartIndex();
	}

	public List<String> getOrderFieldList() {
		return orderFieldList;
	}

	public void setOrderFieldList(List<String> orderFieldList) {
		this.orderFieldList = orderFieldList;
	}

	public List<String> getOrderFieldTypeList() {
		return orderFieldTypeList;
	}

	public void setOrderFieldTypeList(List<String> orderFieldTypeList) {
		this.orderFieldTypeList = orderFieldTypeList;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
		this.initStartIndex();
	}

	public void initStartIndex() {
		if (currentPage != null && pageSize != null) {
			startIndex = (currentPage - 1) * pageSize;
		}
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getDtStartEq() {
		return dtStartEq;
	}

	public void setDtStartEq(String dtStartEq) {
		this.dtStartEq = dtStartEq;
	}

	public String getDtEndNe() {
		return dtEndNe;
	}

	public void setDtEndNe(String dtEndNe) {
		this.dtEndNe = dtEndNe;
	}

	public String getDtEndEq() {
		return dtEndEq;
	}

	public void setDtEndEq(String dtEndEq) {
		this.dtEndEq = dtEndEq;
	}
}
