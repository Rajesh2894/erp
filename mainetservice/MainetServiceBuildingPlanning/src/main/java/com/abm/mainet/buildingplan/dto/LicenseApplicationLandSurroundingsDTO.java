package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;

public class LicenseApplicationLandSurroundingsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

    private LicenseApplicationLandScheduleDTO landSchId;

    private String pocketName;

    private String north;

    private String south;

    private String east;

    private String west;
    
    private String decision;
    
    private String resolutionComments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LicenseApplicationLandScheduleDTO getLandSchId() {
		return landSchId;
	}

	public void setLandSchId(LicenseApplicationLandScheduleDTO landSchId) {
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

}
