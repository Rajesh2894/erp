/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name="TB_CHANGE_BLOCK_MASTER")
public class ChangeofBlockEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8224775928161086222L;
	
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "COB_ID", unique = true, nullable = false)
	private Long cobId;
	
	

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_CHANGE_BLOCK_MASTER", "COB_ID" };
	}

}
