/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cherupelli.srikanth
 * @since 20 Jan 2023
 */
public class ChequeModeInfoDto implements Serializable{

	private static final long serialVersionUID = -3333943640050559958L;

	private Long chequeAmount;
	
	private Long chequeNumber;
	
	private Long chequeMicr;
	
	private String chequeDate;
	
	private Long  chequeBank;


	public Long getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(Long chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public Long getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(Long chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Long getChequeMicr() {
		return chequeMicr;
	}

	public void setChequeMicr(Long chequeMicr) {
		this.chequeMicr = chequeMicr;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public Long getChequeBank() {
		return chequeBank;
	}

	public void setChequeBank(Long chequeBank) {
		this.chequeBank = chequeBank;
	}
	
	
	
}
