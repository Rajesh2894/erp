package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

public class BirthReceiptDTO implements Serializable  {
	
	private static final long serialVersionUID = -4100833798784520499L;
	
	private Date birthDate;
	private String brChildName;
	private Long rmRcptno;
    private Date rmDate;	
	private String rmReceivedfrom;
    private Double rmAmount;
    private Date deathDate;
	private String deathName;
    
    
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBrChildName() {
		return brChildName;
	}
	public void setBrChildName(String brChildName) {
		this.brChildName = brChildName;
	}
	public Long getRmRcptno() {
		return rmRcptno;
	}
	public void setRmRcptno(Long rmRcptno) {
		this.rmRcptno = rmRcptno;
	}
	public Date getRmDate() {
		return rmDate;
	}
	public void setRmDate(Date rmDate) {
		this.rmDate = rmDate;
	}
	public String getRmReceivedfrom() {
		return rmReceivedfrom;
	}
	public void setRmReceivedfrom(String rmReceivedfrom) {
		this.rmReceivedfrom = rmReceivedfrom;
	}
	public Double getRmAmount() {
		return rmAmount;
	}
	public void setRmAmount(Double rmAmount) {
		this.rmAmount = rmAmount;
	}
	public Date getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	public String getDeathName() {
		return deathName;
	}
	public void setDeathName(String deathName) {
		this.deathName = deathName;
	}
    
    

}
