package com.abm.mainet.common.dto;

/**
 * @author cherupelli.srikanth
 *
 */
public class QrBasedModeInfoDto {

	private Long qrAmount;
	private Long qrTranactionId;
	private String qrHost;
	
	
	public Long getQrAmount() {
		return qrAmount;
	}
	public void setQrAmount(Long qrAmount) {
		this.qrAmount = qrAmount;
	}
	public Long getQrTranactionId() {
		return qrTranactionId;
	}
	public void setQrTranactionId(Long qrTranactionId) {
		this.qrTranactionId = qrTranactionId;
	}
	public String getQrHost() {
		return qrHost;
	}
	public void setQrHost(String qrHost) {
		this.qrHost = qrHost;
	}
	
	
}
