package com.abm.mainet.orgnization.chart.dto;

import java.io.Serializable;

public class OrgnizationChartDto implements Serializable {

	private static final long serialVersionUID = -169391065289957727L;
	
	private String id;
	private String name;
	private String parent;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getParent() {
		return parent;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}

}
