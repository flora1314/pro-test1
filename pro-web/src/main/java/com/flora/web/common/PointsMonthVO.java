package com.flora.web.common;

import java.util.Date;

public class PointsMonthVO {

	Integer dataSource = 1;

	// 数据类型：1，发放；2，消耗；3，过期
	Integer dataType;

	// 渠道
	Integer platform;

	// 门店ID
	Long storeId;

	// 是否包含空门店数据
	Boolean includeEmptyShop = false;

	// 用户级别
	Integer userLevel;

	// 时间
	Date statisticDate;

	// 查询条件

	Date statisticStart;// 开始时间

	Date statisticEnd;// 结束时间

	Integer currentPage = 1;

	Integer pageSize = 100;

	public Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Date getStatisticStart() {
		return statisticStart;
	}

	public void setStatisticStart(Date statisticStart) {
		this.statisticStart = statisticStart;
	}

	public Date getStatisticEnd() {
		return statisticEnd;
	}

	public void setStatisticEnd(Date statisticEnd) {
		this.statisticEnd = statisticEnd;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Boolean getIncludeEmptyShop() {
		return includeEmptyShop;
	}

	public void setIncludeEmptyShop(Boolean includeEmptyShop) {
		this.includeEmptyShop = includeEmptyShop;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
