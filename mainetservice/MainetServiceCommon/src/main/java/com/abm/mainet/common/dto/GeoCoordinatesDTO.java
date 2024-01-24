package com.abm.mainet.common.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * The geographic coordinate reference system used by the attributes in this interface
 * is the World Geodetic System (2d) [WGS84]. No other reference system is supported.
 * @author Vardan.Savarde
 *
 */
public class GeoCoordinatesDTO {
	/**
	 * WGS84 geographic coordinate (latitude) specified in decimal degrees
	 */
	@NotNull
	private BigDecimal latitude;
	/**
	 * WGS84 geographic coordinate (longitude) specified in decimal degrees
	 */
	@NotNull
	private BigDecimal longitude;
	/**
	 * the height of the position, specified in meters above the [WGS84] ellipsoid.
	 * If the implementation cannot provide altitude information, the value of this 
	 * attribute MUST be null.
	 */
	private BigDecimal altitude;
	/**
	 * the accuracy level of the latitude and longitude coordinates. 
	 * It is specified in meters and MUST be supported by all implementations.
	 * The value of the coordAccuracy attribute MUST be a non-negative real number.
	 */
	private BigDecimal coordAccuracy;
	/**
	 * attribute is specified in meters. If the implementation cannot provide altitude
	 * information, the value of this attribute MUST be null. Otherwise, the value of
	 * the altitudeAccuracy attribute MUST be a non-negative real number.
	 */
	private BigDecimal altitudeAccuracy;
	/**
	 * The direction attribute denotes the direction of travel of the hosting device and
	 * is specified in degrees, where 0° ≤ heading < 360°, counting clockwise relative
	 * to the true north. If the implementation cannot provide heading information,
	 * the value of this attribute MUST be null. If the hosting device is stationary
	 * (i.e. the value of the speed attribute is 0), then the value of the heading
	 * attribute MUST be negative.
	 */
	private BigDecimal direction;
	/**
	 * The speed attribute denotes the magnitude of the horizontal component of the hosting
	 * device's current velocity and is specified in meters per second. If the implementation
	 * cannot provide speed information, the value of this attribute MUST be null. Otherwise,
	 * the value of the speed attribute MUST be a non-negative real number
	 */
	private BigDecimal speed;
	/**
	 * @return the latitude
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the altitude
	 */
	public BigDecimal getAltitude() {
		return altitude;
	}
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(BigDecimal altitude) {
		this.altitude = altitude;
	}
	/**
	 * @return the coordAccuracy
	 */
	public BigDecimal getCoordAccuracy() {
		return coordAccuracy;
	}
	/**
	 * @param coordAccuracy the coordAccuracy to set
	 */
	public void setCoordAccuracy(BigDecimal coordAccuracy) {
		this.coordAccuracy = coordAccuracy;
	}
	/**
	 * @return the altitudeAccuracy
	 */
	public BigDecimal getAltitudeAccuracy() {
		return altitudeAccuracy;
	}
	/**
	 * @param altitudeAccuracy the altitudeAccuracy to set
	 */
	public void setAltitudeAccuracy(BigDecimal altitudeAccuracy) {
		this.altitudeAccuracy = altitudeAccuracy;
	}
	/**
	 * @return the direction
	 */
	public BigDecimal getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(BigDecimal direction) {
		this.direction = direction;
	}
	/**
	 * @return the speed
	 */
	public BigDecimal getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	
	public static GeoCoordinatesDTO dummy() {
		GeoCoordinatesDTO coords = new GeoCoordinatesDTO();
		coords.setLatitude(BigDecimal.valueOf(12.421d));
		coords.setLongitude(BigDecimal.valueOf(523.123d));
		return coords;
	}
}
