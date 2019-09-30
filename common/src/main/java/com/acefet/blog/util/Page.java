package com.acefet.blog.util;


public class Page {
	/**
	 * 默认每页记录数
	 */
	public static int PAGESIZE_DEFAULT = 10;
	/**
	 * 默认起始页
	 */
	public static int CURRENTPAGE_DEFAULT = 1;
	
	/**
	 * 分页记录数大小
	 */
	private int pageSize = PAGESIZE_DEFAULT;
	/**
	 * 当前页
	 */
	private int currentPage = CURRENTPAGE_DEFAULT;
	/**
	 * 所有页数
	 */
	private int totalPage = 1;
	
	/**
	 * 所有记录数
	 */
	private int totalRecord = 0;
	
	/**
	 * 开始记录行
	 */
	private int startRecord = 0;
	
	

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	
	public void setPageInfo(int allRecord){
		totalRecord = allRecord;
		totalPage = allRecord/getPageSize()+(allRecord % getPageSize() == 0 ? 0 : 1);
		if(currentPage>totalPage)currentPage=totalPage;
	}


	
	

}
