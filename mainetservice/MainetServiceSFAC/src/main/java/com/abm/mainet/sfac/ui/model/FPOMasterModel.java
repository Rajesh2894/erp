package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.FPOMasterService;

@Component
@Scope("session")
public class FPOMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = -7700148528591696271L;

	@Autowired
	private FPOMasterService FPOMasterService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private TbDepartmentService departmentService;

	FPOMasterDto dto = new FPOMasterDto();

	private List<BankMasterEntity> banks = new ArrayList<>();

	private List<Object[]> bankName = new ArrayList<>();

	private List<CBBOMasterDto> iaNameList = new ArrayList<>();

	private List<CBBOMasterDto> cbboMasterList = new ArrayList<>();

	private List<FPOMasterDto> fpoMasterDtoList = new ArrayList<>();

	private List<FPOMasterDto> masterDtoList = new ArrayList<>();

	List<DesignationBean> designlist = new ArrayList<>();

	List<LookUp> allocationCatgList = new ArrayList<>();
	List<LookUp> allocationSubCatgList = new ArrayList<>();

	private String cbboMasterName;
	private String iaMasterName;
	private Boolean speciCategorycheck;
	private String viewMode;
	private boolean womenCentric;
	private String appStatus;

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private String dupFpoName;

	private String dupCompRegNo;

	private String showUdyogDet;

	public List<Object[]> getBankName() {
		return bankName;
	}

	public void setBankName(List<Object[]> bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the dto
	 */
	public FPOMasterDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(FPOMasterDto dto) {
		this.dto = dto;
	}

	/**
	 * @return the banks
	 */
	public List<BankMasterEntity> getBanks() {
		return banks;
	}

	/**
	 * @param banks the banks to set
	 */
	public void setBanks(List<BankMasterEntity> banks) {
		this.banks = banks;
	}

	/**
	 * @return the cbboMasterName
	 */
	public String getCbboMasterName() {
		return cbboMasterName;
	}

	/**
	 * @param cbboMasterName the cbboMasterName to set
	 */
	public void setCbboMasterName(String cbboMasterName) {
		this.cbboMasterName = cbboMasterName;
	}

	/**
	 * @return the iaMasterName
	 */
	public String getIaMasterName() {
		return iaMasterName;
	}

	/**
	 * @param iaMasterName the iaMasterName to set
	 */
	public void setIaMasterName(String iaMasterName) {
		this.iaMasterName = iaMasterName;
	}

	/**
	 * @return the cbboMasterList
	 */
	public List<CBBOMasterDto> getCbboMasterList() {
		return cbboMasterList;
	}

	/**
	 * @param cbboMasterList the cbboMasterList to set
	 */
	public void setCbboMasterList(List<CBBOMasterDto> cbboMasterList) {
		this.cbboMasterList = cbboMasterList;
	}

	/**
	 * @return the speciCategorycheck
	 */
	public Boolean getSpeciCategorycheck() {
		return speciCategorycheck;
	}

	/**
	 * @param speciCategorycheck the speciCategorycheck to set
	 */
	public void setSpeciCategorycheck(Boolean speciCategorycheck) {
		this.speciCategorycheck = speciCategorycheck;
	}

	/**
	 * @return the faYears
	 */
	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	/**
	 * @param faYears the faYears to set
	 */
	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	/**
	 * @return the viewMode
	 */
	public String getViewMode() {
		return viewMode;
	}

	/**
	 * @param viewMode the viewMode to set
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * @return the designlist
	 */
	public List<DesignationBean> getDesignlist() {
		return designlist;
	}

	/**
	 * @param designlist the designlist to set
	 */
	public void setDesignlist(List<DesignationBean> designlist) {
		this.designlist = designlist;
	}

	/**
	 * @return the womenCentric
	 */
	public boolean isWomenCentric() {
		return womenCentric;
	}

	/**
	 * @param womenCentric the womenCentric to set
	 */
	public void setWomenCentric(boolean womenCentric) {
		this.womenCentric = womenCentric;
	}

	/**
	 * @return the fpoMasterDtoList
	 */
	public List<FPOMasterDto> getFpoMasterDtoList() {
		return fpoMasterDtoList;
	}

	/**
	 * @param fpoMasterDtoList the fpoMasterDtoList to set
	 */
	public void setFpoMasterDtoList(List<FPOMasterDto> fpoMasterDtoList) {
		this.fpoMasterDtoList = fpoMasterDtoList;
	}

	/**
	 * @return the masterDtoList
	 */
	public List<FPOMasterDto> getMasterDtoList() {
		return masterDtoList;
	}

	/**
	 * @param masterDtoList the masterDtoList to set
	 */
	public void setMasterDtoList(List<FPOMasterDto> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}

	/**
	 * @return the allocationCatgList
	 */
	public List<LookUp> getAllocationCatgList() {
		return allocationCatgList;
	}

	/**
	 * @param allocationCatgList the allocationCatgList to set
	 */
	public void setAllocationCatgList(List<LookUp> allocationCatgList) {
		this.allocationCatgList = allocationCatgList;
	}

	/**
	 * @return the allocationSubCatgList
	 */
	public List<LookUp> getAllocationSubCatgList() {
		return allocationSubCatgList;
	}

	/**
	 * @param allocationSubCatgList the allocationSubCatgList to set
	 */
	public void setAllocationSubCatgList(List<LookUp> allocationSubCatgList) {
		this.allocationSubCatgList = allocationSubCatgList;
	}

	/**
	 * @return the iaNameList
	 */
	public List<CBBOMasterDto> getIaNameList() {
		return iaNameList;
	}

	/**
	 * @param iaNameList the iaNameList to set
	 */
	public void setIaNameList(List<CBBOMasterDto> iaNameList) {
		this.iaNameList = iaNameList;
	}

	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the dupFpoName
	 */
	public String getDupFpoName() {
		return dupFpoName;
	}

	/**
	 * @param dupFpoName the dupFpoName to set
	 */
	public void setDupFpoName(String dupFpoName) {
		this.dupFpoName = dupFpoName;
	}

	/**
	 * @return the dupCompRegNo
	 */
	public String getDupCompRegNo() {
		return dupCompRegNo;
	}

	/**
	 * @param dupCompRegNo the dupCompRegNo to set
	 */
	public void setDupCompRegNo(String dupCompRegNo) {
		this.dupCompRegNo = dupCompRegNo;
	}

	/**
	 * @return the showUdyogDet
	 */
	public String getShowUdyogDet() {
		return showUdyogDet;
	}

	/**
	 * @param showUdyogDet the showUdyogDet to set
	 */
	public void setShowUdyogDet(String showUdyogDet) {
		this.showUdyogDet = showUdyogDet;
	}

	@Override
	public boolean saveForm() throws FrameworkException {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		FPOMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setDeptId(deptId);
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setAppStatus(MainetConstants.FlagP);
			//for cbbo aproval
			mastDto.setApproved(MainetConstants.FlagA);
			
			mastDto.getFpoMasterDetailDto().forEach(detDto -> {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});
			mastDto.getFpoBankDetailDto().forEach(bnkDto -> {
				bnkDto.setCreatedBy(createdBy);
				bnkDto.setCreatedDate(newDate);
				bnkDto.setOrgId(org.getOrgid());
				bnkDto.setLgIpMac(lgIp);
			});

			mastDto.getFpoAdministrativeDto().forEach(admDto -> {
				admDto.setCreatedBy(createdBy);
				admDto.setCreatedDate(newDate);
				admDto.setOrgId(org.getOrgid());
				admDto.setLgIpMac(lgIp);
			});
			if (StringUtils.isEmpty(mastDto.getIaName()))
				mastDto.setIaName(iaMasterName);
			this.getIaNameList().forEach(dto -> {
				if (getDto().getIaId().equals(dto.getIaId()))
					getDto().setCbboId(dto.getCbboId());
			});
			mastDto = FPOMasterService.saveAndUpdateApplication(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.fpo.master.save.msg") + mastDto.getFpoRegNo() + " "
					+ getAppSession().getMessage("sfac.applicationNo") + " " + mastDto.getApplicationId());
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setDeptId(deptId);
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setAppStatus(MainetConstants.FlagP);
			//for cbbo aproval
			mastDto.setApproved(MainetConstants.FlagA);
			
			mastDto.getFpoMasterDetailDto().forEach(detailDto -> {
				if (detailDto.getCreatedBy() == null || detailDto.getCreatedBy() == 0) {
					detailDto.setCreatedBy(createdBy);
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				} else {
					detailDto.setUpdatedBy(createdBy);
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				}
			});

			mastDto.getFpoBankDetailDto().forEach(bnkDto -> {
				if (bnkDto.getCreatedBy() == null) {
					bnkDto.setCreatedBy(createdBy);
					bnkDto.setCreatedDate(newDate);
					bnkDto.setOrgId(org.getOrgid());
					bnkDto.setLgIpMac(lgIp);
				} else {
					bnkDto.setUpdatedBy(createdBy);
					bnkDto.setUpdatedDate(newDate);
					bnkDto.setOrgId(org.getOrgid());
					bnkDto.setLgIpMac(lgIp);
				}
			});

			mastDto.getFpoAdministrativeDto().forEach(admDto -> {
				if (admDto.getCreatedBy() == null) {
					admDto.setCreatedBy(createdBy);
					admDto.setCreatedDate(newDate);
					admDto.setOrgId(org.getOrgid());
					admDto.setLgIpMac(lgIp);
				} else {
					admDto.setUpdatedBy(createdBy);
					admDto.setUpdatedDate(newDate);
					admDto.setOrgId(org.getOrgid());
					admDto.setLgIpMac(lgIp);
				}
			});
			if (StringUtils.isEmpty(mastDto.getIaName()))
				mastDto.setIaName(iaMasterName);
			this.getIaNameList().forEach(dto -> {
				if (getDto().getIaId().equals(dto.getIaId()))
					getDto().setCbboId(dto.getCbboId());
			});
			mastDto = FPOMasterService.updateFpoDetails(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.fpo.master.update.msg") + mastDto.getFpoRegNo()
					+ " " + getAppSession().getMessage("sfac.applicationNo") + " " + mastDto.getApplicationId());
		}
		setDto(mastDto);
		return true;
	}

}
