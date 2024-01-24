package com.abm.mainet.common.workflow.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "TB_WORKFLOW_DESCRIPTION")
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Description implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="ID")
	private Long id;
	
	
	@Column(name="DESCRIPTION_TEXT")
	private String description;
	
	//private Map<String, String> properties;
	public Description( String name) {
	      this.description = name;
	    
	   }

	public Description( ) {
	      
	   }
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getPkValues() {
		return new String[] { "COM", "TB_WORKFLOW_DESCRIPTION", "ID"};
	}	
}
