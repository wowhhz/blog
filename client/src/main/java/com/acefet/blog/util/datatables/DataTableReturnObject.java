package com.acefet.blog.util.datatables;

import com.acefet.blog.util.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DataTableReturnObject {

	private int draw;
	private List data;
	private Page page;
	private long recordsTotal;
	private long recordsFiltered;

	
	public DataTableReturnObject(Integer draw, List data,
								 Integer recordsTotal, Integer recordsFiltered) {
		super();
		this.draw = draw;
		this.data = data;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;

	}

	public DataTableReturnObject(Page page, List data, Integer draw) {
		super();
		this.draw = draw;
		this.data = data;
		this.recordsTotal = page.getTotalRecord();
		this.recordsFiltered = page.getTotalRecord();
		this.page = page;
	}

	public DataTableReturnObject(List data,DataTableQueryParameter tablePara) {
		super();
		this.recordsTotal = data.size();
		this.recordsFiltered = data.size();
		this.draw = tablePara.getDraw();
		this.data = data;
	}

	public DataTableReturnObject(Page page,DataTableQueryParameter tablePara, List data) {
		super();
		this.recordsTotal = page.getTotalRecord();
		this.recordsFiltered = page.getTotalRecord();
		this.draw = tablePara.getDraw();
		this.data = data;
		this.page = page;
	}


	public DataTableReturnObject(org.springframework.data.domain.Page page,DataTableQueryParameter tablePara){
		super();
		this.draw = tablePara.getDraw();
		this.data = (page==null ? new ArrayList() : page.getContent());
		this.recordsTotal = (page==null ? 0 : page.getTotalElements());
		this.recordsFiltered = (page==null ? 0 : page.getTotalElements()); //page.getNumberOfElements();
	}

	public Map toMap(){
		//JSONObject jso = new JSONObject();
		Map jso = new HashMap();
		jso.put("recordsTotal", getRecordsTotal());
		jso.put("recordsFiltered", getRecordsFiltered());
		jso.put("draw", getDraw());
		jso.put("data", getData());
		return jso;
	}

	
}
