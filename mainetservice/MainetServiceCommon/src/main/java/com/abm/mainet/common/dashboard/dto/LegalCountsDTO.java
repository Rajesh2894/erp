package com.abm.mainet.common.dashboard.dto;

public class LegalCountsDTO {

	private int closed;
	private int pending;
	private int received;
	private String year;

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "LegalCountsDTO [closed=" + closed + ", pending=" + pending + ", received=" + received + ", year=" + year
				+ "]";
	}

}
