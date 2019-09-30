package com.acefet.blog.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



public class FormBean {

	/**
	 * 查询类型
	 */
	private String searchtype;
	/**
	 * 查询值
	 */
	private String searchvalue;
	/**
	 * 查询类型Map(String key,String name)
	 */
	private Map searchSelectMap;
	/**
	 * 排序类型(String asc\desc)
	 */
	private String orderType;
	/**
	 * 排序变量
	 */
	private String orderStr;
	/**
	 * 下一个排序类型
	 */
	private String orderTypeNext;

	/**
	 * 分页列表值
	 */
	private List currentPages = new ArrayList();
	/**
	 * 分页值
	 */
	private int currentPage = Page.CURRENTPAGE_DEFAULT;

	/**
	 * 每页大小
	 */
	private int pageSize = Page.PAGESIZE_DEFAULT;


	public FormBean() {
		currentPages = Arrays.asList(new Integer[]{10,25,50,100});
	}

	public String getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public Map getSearchSelectMap() {
		return searchSelectMap;
	}

	public void setSearchSelectMap(Map searchSelectMap) {
		this.searchSelectMap = searchSelectMap;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public String getOrderTypeNext() {
		return orderTypeNext;
	}

	public void setOrderTypeNext(String orderTypeNext) {
		this.orderTypeNext = orderTypeNext;
	}

	public List getCurrentPages() {
		return currentPages;
	}

	public void setCurrentPages(List currentPages) {
		this.currentPages = currentPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
