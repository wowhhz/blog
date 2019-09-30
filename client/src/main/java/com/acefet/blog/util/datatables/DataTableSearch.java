package com.acefet.blog.util.datatables;

import lombok.Data;

@Data
public class DataTableSearch {

	private String value = "";
	private boolean regex = false;

	public DataTableSearch(String value, boolean regex) {
		this.value = value;
		this.regex = regex;
	}
}
