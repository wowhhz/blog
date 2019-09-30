package com.acefet.blog.util.datatables;

import lombok.Data;

@Data
public class DataTableOrder {
	
	private int column;
	private String dir;
	private String orderName;

	public DataTableOrder(int column, String dir,String orderName) {
		this.column = column;
		this.dir = dir;
		this.orderName = orderName;
	}
}
