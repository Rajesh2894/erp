package com.abm.mainet.mobile.dto;


/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
*/
//@Entity
public class OpinionPollDTO{
    
	/*
	 * @Id
	 * 
	 * @Column(name = "id")
	 */
	long id;
	//@Column(name = "eng")
	String eng;
	//@Column(name = "reg")
	String reg;
	//@Column(name = "ocount")
	long ocount;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEng() {
		return eng;
	}
	public void setEng(String eng) {
		this.eng = eng;
	}
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public long getOcount() {
		return ocount;
	}
	public void setOcount(long ocount) {
		this.ocount = ocount;
	}
	
}
