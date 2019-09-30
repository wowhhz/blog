package com.acefet.blog.util.datatables;

import lombok.Data;

@Data
public class DataTableColumns {
	
	private String data;
	private String name;
	private boolean searchable;
	private boolean orderable;
	private DataTableSearch search;

	public DataTableColumns(String data, String name,
							Boolean searchable, Boolean orderable,
							String searchValue,Boolean searchRegex) {
		this.data = data;
		this.name = name;
		this.searchable = searchable;
		this.orderable = orderable;
		DataTableSearch search = new DataTableSearch(searchValue,searchRegex);
		this.search = search;
	}
}
