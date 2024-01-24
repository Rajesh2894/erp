package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_bpms_land_Surroundings")
public class LicenseApplicationLandSurroundingsEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Land_sch_id")
    private LicenseApplicationLandSchedule landSchId;

    @Column(name = "PocketName")
    private String pocketName;

    @Column(name = "North")
    private String north;

    @Column(name = "South")
    private String south;

    @Column(name = "East")
    private String east;

    @Column(name = "West")
    private String west;
    
    @Column(name = "FIELD_DECISION")
	private String decision;
	
	@Column(name = "FIELD_REMARK")
	private String resolutionComments;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LicenseApplicationLandSchedule getLandSchId() {
		return landSchId;
	}

	public void setLandSchId(LicenseApplicationLandSchedule landSchId) {
		this.landSchId = landSchId;
	}

	public String getPocketName() {
		return pocketName;
	}

	public void setPocketName(String pocketName) {
		this.pocketName = pocketName;
	}

	public String getNorth() {
		return north;
	}

	public void setNorth(String north) {
		this.north = north;
	}

	public String getSouth() {
		return south;
	}

	public void setSouth(String south) {
		this.south = south;
	}

	public String getEast() {
		return east;
	}

	public void setEast(String east) {
		this.east = east;
	}

	public String getWest() {
		return west;
	}

	public void setWest(String west) {
		this.west = west;
	}
	
	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public String[] getPkValues() {
		return new String[] { "NL", "tb_bpms_land_Surroundings", "id" };
	}

    
}