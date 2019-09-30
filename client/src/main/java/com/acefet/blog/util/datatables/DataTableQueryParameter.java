package com.acefet.blog.util.datatables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.acefet.blog.util.JSONParam;
import com.acefet.blog.util.Page;
import lombok.Data;

@Data
public class DataTableQueryParameter {
	
	private int draw = 0;

	private List<DataTableColumns> columns;
	private List<DataTableOrder> orders;
	private int start = 0;
	private int length = Page.PAGESIZE_DEFAULT;
	private DataTableSearch search;

	private Map<String,String> jsonMap;
	private Page page;
	
	
	
	public DataTableQueryParameter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataTableQueryParameter(Map<String,String> jsonMap) {
		super();
		this.jsonMap = jsonMap;
		parse();
	}

	/**
	 * jsonparam json参数转换为对象
	 */
	public void parse(){

		draw = Integer.parseInt(jsonMap.get("draw"));
		start = Integer.parseInt(jsonMap.get("start"));
		length = Integer.parseInt(jsonMap.get("length"));

		String _searchValue = jsonMap.get("search[value]");
		Boolean _searchRegex = Boolean.parseBoolean(jsonMap.get("search[regex]"));

		columns = new ArrayList<DataTableColumns>();
		orders = new ArrayList<DataTableOrder>();
		search = new DataTableSearch(_searchValue,_searchRegex);

		int colLen = 0,orderLen = 0;
		while(null!=jsonMap.get("columns["+colLen+"][data]")){
			String data = jsonMap.get("columns["+colLen+"][data]");
			String name = jsonMap.get("columns["+colLen+"][name]");
			Boolean searchable = Boolean.parseBoolean(jsonMap.get("columns["+colLen+"][searchable]"));
			Boolean orderable = Boolean.parseBoolean(jsonMap.get("columns["+colLen+"][orderable]"));
			String searchValue = jsonMap.get("columns["+colLen+"][search][value]");
			Boolean searchRegex = Boolean.parseBoolean(jsonMap.get("columns["+colLen+"][search][regex]"));

			DataTableColumns column = new DataTableColumns(data,name,searchable,orderable,searchValue,searchRegex);
			columns.add(column);

			colLen++;
		}
		setColumns(columns);
		while(null!=jsonMap.get("order["+orderLen+"][column]")){
			Integer orderColumn = Integer.parseInt(jsonMap.get("order["+orderLen+"][column]"));
			String orderDir = jsonMap.get("order["+orderLen+"][dir]");
			//原datatables控件排序图标有问题，用相反的方式进行调整
			if(orderDir.equals("asc")){
				orderDir="desc";
			}else{
				orderDir="asc";
			}
			String orderName = getColumns().get(orderColumn).getName();

			DataTableOrder order = new DataTableOrder(orderColumn,orderDir,orderName);
			orders.add(order);
			orderLen++;
		}
		setOrders(orders);

		page = new Page();
		page.setStartRecord(getStart());
		page.setPageSize(getLength());
		page.setCurrentPage(getStart()/getLength());

	}

	/**
	 * 转换列排序字符
	 * @return
	 */
	public String getOrdersToString(){
		List<DataTableOrder> orders = getOrders();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < orders.size(); i++) {
			if(!getColumns().get(orders.get(i).getColumn()).isOrderable())continue;
			if(buffer.toString().length()>0)buffer.append(",");
			buffer.append(getColumns().get(orders.get(i).getColumn()).getData()+" "+orders.get(i).getDir());
		}
		return buffer.toString();
	}


}
