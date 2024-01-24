package com.abm.mainet.disastermanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class DailyWeatherDTO implements Serializable{
	private static final long serialVersionUID = 2110704719241314572L;
	
	private String minTemperature;
	
	private String maxTemperature;
	
	private String humidity; 
	
	private String windSpeed;
	
	private String rainFall;
	
	private String rainSpeed ;
	
	private String department;
	
	private String employee;
	
	private Date date;
	
	private String fromTime;

	private String toTime;


	public String getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(String minTemperature) {
		this.minTemperature = minTemperature;
	}

	public String getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(String maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getRainFall() {
		return rainFall;
	}

	public void setRainFall(String rainFall) {
		this.rainFall = rainFall;
	}

	public String getRainSpeed() {
		return rainSpeed;
	}

	public void setRainSpeed(String rainSpeed) {
		this.rainSpeed = rainSpeed;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

}
