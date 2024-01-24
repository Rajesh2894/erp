package com.abm.mainet.materialmgmt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MM_BIN_LOCATION_MAPPING")
public class BinLocDefMapping {

	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MAPID", nullable = false)
	private Long mapId;
	
	@JoinColumn(name= "BIN_DEF_ID")
	@ManyToOne
	private BinDefMasEntity binDefMasEntity;

	public Long getMapId() {
		return mapId;
	}

	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}

	public BinDefMasEntity getBinDefMasEntity() {
		return binDefMasEntity;
	}

	public void setBinDefMasEntity(BinDefMasEntity binDefMasEntity) {
		this.binDefMasEntity = binDefMasEntity;
	}
	
	
	public static String[] getPkValues() {
        return new String[] { "MM", "MM_BIN_LOCATION_MAPPING", "MAPID" };
    }

	
}
