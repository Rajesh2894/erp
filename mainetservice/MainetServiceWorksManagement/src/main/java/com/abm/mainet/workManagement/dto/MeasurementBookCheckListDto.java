package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.TbScrutinyLabels;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class MeasurementBookCheckListDto implements Serializable {

	private static final long serialVersionUID = 6275016165240861242L;

	private Long mbcId;

	private Date mbcInspectionDate;

	private String mbcDrawingNo;

	private Long locId;

	private String mbcCheckId;

	private Long mbId;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private List<String> checkListSelect = new ArrayList<>();
	private List<MeasurementBookCheckListDetailDto> mbChkListDetails = new ArrayList<>();

	private List<TbScrutinyLabels> classACheckList = new ArrayList<>();
	private List<TbScrutinyLabels> checkListColumnBeams = new ArrayList<>();
	private List<TbScrutinyLabels> checkListBrickWork = new ArrayList<>();
	private List<TbScrutinyLabels> checkListPlastering = new ArrayList<>();
	private List<TbScrutinyLabels> checkListWaterSupply = new ArrayList<>();

	private String checkListItems;

	public String getCheckListItems() {
		return checkListItems;
	}

	public void setCheckListItems(String checkListItems) {
		this.checkListItems = checkListItems;
	}

	public List<String> getCheckListSelect() {
		return checkListSelect;
	}

	public void setCheckListSelect(List<String> checkListSelect) {
		this.checkListSelect = checkListSelect;
	}

	public Long getMbcId() {
		return mbcId;
	}

	public void setMbcId(Long mbcId) {
		this.mbcId = mbcId;
	}

	public Date getMbcInspectionDate() {
		return mbcInspectionDate;
	}

	public void setMbcInspectionDate(Date mbcInspectionDate) {
		this.mbcInspectionDate = mbcInspectionDate;
	}

	public String getMbcDrawingNo() {
		return mbcDrawingNo;
	}

	public void setMbcDrawingNo(String mbcDrawingNo) {
		this.mbcDrawingNo = mbcDrawingNo;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getMbcCheckId() {
		return mbcCheckId;
	}

	public void setMbcCheckId(String mbcCheckId) {
		this.mbcCheckId = mbcCheckId;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public List<TbScrutinyLabels> getClassACheckList() {
		return classACheckList;
	}

	public void setClassACheckList(List<TbScrutinyLabels> classACheckList) {
		this.classACheckList = classACheckList;
	}

	public List<TbScrutinyLabels> getCheckListColumnBeams() {
		return checkListColumnBeams;
	}

	public void setCheckListColumnBeams(List<TbScrutinyLabels> checkListColumnBeams) {
		this.checkListColumnBeams = checkListColumnBeams;
	}

	public List<TbScrutinyLabels> getCheckListBrickWork() {
		return checkListBrickWork;
	}

	public void setCheckListBrickWork(List<TbScrutinyLabels> checkListBrickWork) {
		this.checkListBrickWork = checkListBrickWork;
	}

	public List<TbScrutinyLabels> getCheckListPlastering() {
		return checkListPlastering;
	}

	public void setCheckListPlastering(List<TbScrutinyLabels> checkListPlastering) {
		this.checkListPlastering = checkListPlastering;
	}

	public List<TbScrutinyLabels> getCheckListWaterSupply() {
		return checkListWaterSupply;
	}

	public void setCheckListWaterSupply(List<TbScrutinyLabels> checkListWaterSupply) {
		this.checkListWaterSupply = checkListWaterSupply;
	}

	public List<MeasurementBookCheckListDetailDto> getMbChkListDetails() {
		return mbChkListDetails;
	}

	public void setMbChkListDetails(List<MeasurementBookCheckListDetailDto> mbChkListDetails) {
		this.mbChkListDetails = mbChkListDetails;
	}

}
