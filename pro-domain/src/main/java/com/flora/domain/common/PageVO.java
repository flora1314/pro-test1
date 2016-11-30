package com.flora.domain.common;

import java.io.Serializable;
import java.util.List;

public class PageVO<T>
  implements Serializable
{
  private static final long serialVersionUID = -3101101529316214943L;
  private static final int INIT_SIZE = 10;
  private int pageSize = 10;
  private int totalCount;
  private int currentPage;
  private List<T> data;
  private String unit = "条";
  private Object extInfo;

  public PageVO()
  {
  }

  public PageVO(int currentPage)
  {
    this.currentPage = currentPage;
  }

  public PageVO(int currentPage, int pageSize) {
    this.currentPage = currentPage;
    this.pageSize = pageSize;
  }

  public int getStartIndex()
  {
    return (getCurrentPage() - 1) * this.pageSize;
  }

  public int getEndIndex()
  {
    return getCurrentPage() * this.pageSize;
  }

  public boolean isFirstPage()
  {
    return getCurrentPage() <= 1;
  }

  public boolean isLastPage()
  {
    return getCurrentPage() >= getPageCount();
  }

  public int getNextPage()
  {
    if (isLastPage()) {
      return getCurrentPage();
    }
    return getCurrentPage() + 1;
  }

  public int getPreviousPage()
  {
    if (isFirstPage()) {
      return 1;
    }
    return getCurrentPage() - 1;
  }

  public int getCurrentPage()
  {
    if (this.currentPage == 0) {
      this.currentPage = 1;
    }
    return this.currentPage;
  }

  public int getPageCount()
  {
    if (this.totalCount % this.pageSize == 0) {
      return this.totalCount / this.pageSize;
    }
    return this.totalCount / this.pageSize + 1;
  }

  public int getTotalCount()
  {
    return this.totalCount;
  }

  public void setCurrentPage(int currentPage)
  {
    this.currentPage = currentPage;
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public boolean hasNextPage()
  {
    return getCurrentPage() < getPageCount();
  }

  public boolean hasPreviousPage()
  {
    return getCurrentPage() > 1;
  }

  public List<T> getResult()
  {
    return this.data;
  }

  public void setResult(List<T> data)
  {
    this.data = data;
  }

  public void setTotalCount(int totalCount)
  {
    this.totalCount = totalCount;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }
  public String getUnit() {
    return this.unit;
  }

	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public Object getExtInfo() {
		return extInfo;
	}
	
	public void setExtInfo(Object extInfo) {
		this.extInfo = extInfo;
	}

}