package com.abm.mainet.common.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GeoPositionDTO DTO is the container for the geolocation information.
 * @author Vardan.Savarde
 *
 */
@XmlRootElement(name = "GeoPosition")
public class GeoPositionDTO {
	/**
	 * The coordinates attribute contains a set of geographic coordinates together
	 * with their associated accuracy, as well as a set of other optional 
	 * attributes such as altitude and speed.
	 */
	@Valid
	@NotNull
	private GeoCoordinatesDTO coordinates;
	/**
	 * The timestamp attribute represents the time in milliseconds when the 
	 * GeoPositionDTO object was acquired
	 */
	private long timestamp;
	/**
	 * The address of the nearest street of the current position.
	 */
	private String streetAddress;
	/**
	 * @return the coordinates
	 */
	public GeoCoordinatesDTO getCoordinates() {
		return coordinates;
	}
	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(GeoCoordinatesDTO coordinates) {
		this.coordinates = coordinates;
	}
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public static GeoPositionDTO dummy() {
		GeoPositionDTO geoPosition = new GeoPositionDTO();
		geoPosition.setCoordinates(GeoCoordinatesDTO.dummy());
		geoPosition.setTimestamp(12341243321432L);
		geoPosition.setStreetAddress("Street Address");
		return geoPosition;
	}
	
}
