package com.abm.mainet.tradeLicense.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class LicenseReprintDetailsDto extends RequestDTO {
	private static final long serialVersionUID = 6816027960395238999L;

	private List<TradeMasterDetailDTO> tradeMasterDetailDTO = new ArrayList<>();
	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	private Long orgId;
	private String dateDesc;
	private TbLoiMas tbLoiMas = new TbLoiMas();
	private String issuanceDateDesc;
	private String licenseFromDateDesc;
	private String viewMode;
	private Long rmRcptno;
	private BigDecimal rmAmount;
	private List<TbLoiMas> tbLoiMasList = new ArrayList<>();
	private String loiDateDesc;

	private String payMode;

	private List<TbApprejMas> apprejMasList = new ArrayList<>();

	private List<CFCAttachment> documentsList = new ArrayList<>();

	private String imagePath;
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private Long applicationNo;
	private String trdLicno;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	/*
	 * private List<TradeLicensePrintReportDTO> tradeLicensePrintReportDTO = new
	 * ArrayList<>();
	 */

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public List<TradeMasterDetailDTO> getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(List<TradeMasterDetailDTO> tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	/*
	 * public List<TradeLicensePrintReportDTO> getTradeLicensePrintReportDTO() {
	 * return tradeLicensePrintReportDTO; }
	 * 
	 * public void setTradeLicensePrintReportDTO(List<TradeLicensePrintReportDTO>
	 * tradeLicensePrintReportDTO) { this.tradeLicensePrintReportDTO =
	 * tradeLicensePrintReportDTO; }
	 */

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public TbLoiMas getTbLoiMas() {
		return tbLoiMas;
	}

	public void setTbLoiMas(TbLoiMas tbLoiMas) {
		this.tbLoiMas = tbLoiMas;
	}

	public String getIssuanceDateDesc() {
		return issuanceDateDesc;
	}

	public void setIssuanceDateDesc(String issuanceDateDesc) {
		this.issuanceDateDesc = issuanceDateDesc;
	}

	public String getLicenseFromDateDesc() {
		return licenseFromDateDesc;
	}

	public void setLicenseFromDateDesc(String licenseFromDateDesc) {
		this.licenseFromDateDesc = licenseFromDateDesc;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
	}

	public Long getRmRcptno() {
		return rmRcptno;
	}

	public void setRmRcptno(Long rmRcptno) {
		this.rmRcptno = rmRcptno;
	}

	public BigDecimal getRmAmount() {
		return rmAmount;
	}

	public void setRmAmount(BigDecimal rmAmount) {
		this.rmAmount = rmAmount;
	}

	public List<TbLoiMas> getTbLoiMasList() {
		return tbLoiMasList;
	}

	public void setTbLoiMasList(List<TbLoiMas> tbLoiMasList) {
		this.tbLoiMasList = tbLoiMasList;
	}

	public String getLoiDateDesc() {
		return loiDateDesc;
	}

	public void setLoiDateDesc(String loiDateDesc) {
		this.loiDateDesc = loiDateDesc;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getTrdLicno() {
		return trdLicno;
	}

	public void setTrdLicno(String trdLicno) {
		this.trdLicno = trdLicno;
	}

	public List<CFCAttachment> getDocumentsList() {
		return documentsList;
	}

	public void setDocumentsList(List<CFCAttachment> documentsList) {
		this.documentsList = documentsList;
	}

}
