/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.dto.StateAreaZoneCategoryDto;
import com.abm.mainet.sfac.service.CBBOMasterService;

/**
 * @author pooja.maske
 *
 */

@Component
@Scope("session")
public class CBBOMasterModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9019791036703798713L;

	@Autowired
	private CBBOMasterService cbboMasterService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private TbDepartmentService departmentService;

	CBBOMasterDto masterDto = new CBBOMasterDto();

	List<CBBOMasterDto> cbboMasterList = new ArrayList<>();

	List<CBBOMasterDto> cbboMastDtoList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private StateAreaZoneCategoryDto stateAreaZoneCatgDto = new StateAreaZoneCategoryDto();

	List<IAMasterDto> iaMasterDtoList = new ArrayList<>();

	private List<CBBOMasterDto> iaNameList = new ArrayList<>();

	private String iaMastName;

	private String cbboName;

	private Long cbId;
	
	private Long iId;
	
	private String viewMode;
	List<DesignationBean> designlist = new ArrayList<>();
	private String removeContactDetIds;

	List<LookUp> stateList = new ArrayList<>();

	private String iaAllYr;

	private String cbboAppyr;

	/**
	 * @return the masterDto
	 */
	public CBBOMasterDto getMasterDto() {
		return masterDto;
	}

	/**
	 * @param masterDto the masterDto to set
	 */
	public void setMasterDto(CBBOMasterDto masterDto) {
		this.masterDto = masterDto;
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
	 * @return the stateAreaZoneCatgDto
	 */
	public StateAreaZoneCategoryDto getStateAreaZoneCatgDto() {
		return stateAreaZoneCatgDto;
	}

	/**
	 * @param stateAreaZoneCatgDto the stateAreaZoneCatgDto to set
	 */
	public void setStateAreaZoneCatgDto(StateAreaZoneCategoryDto stateAreaZoneCatgDto) {
		this.stateAreaZoneCatgDto = stateAreaZoneCatgDto;
	}

	/**
	 * @return the iaMasterDtoList
	 */
	public List<IAMasterDto> getIaMasterDtoList() {
		return iaMasterDtoList;
	}

	/**
	 * @param iaMasterDtoList the iaMasterDtoList to set
	 */
	public void setIaMasterDtoList(List<IAMasterDto> iaMasterDtoList) {
		this.iaMasterDtoList = iaMasterDtoList;
	}

	/**
	 * @return the iaMastName
	 */
	public String getIaMastName() {
		return iaMastName;
	}

	/**
	 * @param iaMastName the iaMastName to set
	 */
	public void setIaMastName(String iaMastName) {
		this.iaMastName = iaMastName;
	}

	/**
	 * @return the cbboMastDtoList
	 */
	public List<CBBOMasterDto> getCbboMastDtoList() {
		return cbboMastDtoList;
	}

	/**
	 * @param cbboMastDtoList the cbboMastDtoList to set
	 */
	public void setCbboMastDtoList(List<CBBOMasterDto> cbboMastDtoList) {
		this.cbboMastDtoList = cbboMastDtoList;
	}

	@Override
	public boolean saveForm() {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.CBBO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long masId = UserSession.getCurrent().getEmployee().getMasId();
		Date date = new Date();
		CBBOMasterDto mastDto = getMasterDto();
		mastDto.setEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		mastDto.setMobileNo(UserSession.getCurrent().getEmployee().getEmpmobno());
		if (StringUtils.isNotEmpty(UserSession.getCurrent().getEmployee().getEmpname())
				&& StringUtils.isNotEmpty(UserSession.getCurrent().getEmployee().getEmplname()))
			mastDto.setUserName(UserSession.getCurrent().getEmployee().getEmpname() + ""
					+ UserSession.getCurrent().getEmployee().getEmplname());
		else
			mastDto.setUserName(UserSession.getCurrent().getEmployee().getEmpname());
		if ((mastDto.getCreatedBy() == null && mastDto.getIaId() == null)
				|| (mastDto.getIaId() != null && masId != null && !(mastDto.getIaId().equals(masId)))) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(date);
			mastDto.setLgIpMac(lgIp);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setDeptId(deptId);
			mastDto.setCbboId(null);
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setAppStatus(MainetConstants.FlagP);

			mastDto.getCbboDetDto().forEach(contactDto -> {
				contactDto.setCreatedBy(createdBy);
				contactDto.setCreatedDate(date);
				contactDto.setOrgId(org.getOrgid());
				contactDto.setLgIpMac(lgIp);
				contactDto.setCbboId(null);
				contactDto.setCbboDId(null);
				contactDto.setStatus(MainetConstants.FlagA);
			});
			if (UserSession.getCurrent().getEmployee().getMasId() != null)
				mastDto.setIaId(UserSession.getCurrent().getEmployee().getMasId());
			else
				mastDto.setIaId(createdBy);
			mastDto = cbboMasterService.saveCbboMasterDetails(mastDto);
			if (null != mastDto.getCbboUniqueId() && null != mastDto.getApplicationId())
				this.setSuccessMessage(
						getAppSession().getMessage("sfac.cbbo.master.save.msg") + " " + mastDto.getCbboUniqueId() + " "
								+ getAppSession().getMessage("sfac.applicationNo") + " " + mastDto.getApplicationId());
			else
				this.setSuccessMessage(
						getAppSession().getMessage("sfac.cbbo.master.save.msg") + " " + mastDto.getCbboUniqueId());
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(date);
			mastDto.setLgIpMacUpd(lgIp);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setDeptId(deptId);
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			if (mastDto.getCreatedBy() == 0) {
				mastDto.setCreatedBy(createdBy);
			}
			mastDto.setAppStatus(MainetConstants.FlagP);

			mastDto.getCbboDetDto().forEach(contactDto -> {
				if (contactDto.getCreatedBy() == null) {
					contactDto.setCreatedBy(createdBy);
					contactDto.setCreatedDate(date);
					contactDto.setOrgId(org.getOrgid());
					contactDto.setLgIpMac(lgIp);
					contactDto.setStatus(MainetConstants.FlagA);
				} else {
					contactDto.setUpdatedBy(createdBy);
					contactDto.setUpdatedDate(date);
					contactDto.setOrgId(org.getOrgid());
					contactDto.setLgIpMac(lgIp);
				}
			});
			if (UserSession.getCurrent().getEmployee().getMasId() != null)
				mastDto.setIaId(UserSession.getCurrent().getEmployee().getMasId());
			else
				mastDto.setIaId(createdBy);
			mastDto = cbboMasterService.updateCbboMasterDetails(mastDto);
			this.setSuccessMessage(
					getAppSession().getMessage("sfac.cbbo.master.updated.msg") + " " + mastDto.getCbboUniqueId() + " "
							+ getAppSession().getMessage("sfac.applicationNo") + " " + mastDto.getApplicationId());
		}
		setMasterDto(mastDto);
		List<Long> removedContDetIdsList = getRemovedContactDetList();

		if (removedContDetIdsList != null && !removedContDetIdsList.isEmpty()) {
			cbboMasterService.inactiveRemovedContactDetails(getMasterDto(), removedContDetIdsList);
		}
		return true;
	}

	/**
	 * @return
	 */
	private List<Long> getRemovedContactDetList() {

		List<Long> removeYearIdList = null;
		String conIds = getRemoveContactDetIds();
		if (conIds != null && !conIds.isEmpty()) {
			removeYearIdList = new ArrayList<>();
			String contArray[] = conIds.split(MainetConstants.operator.COMMA);
			for (String id : contArray) {
				removeYearIdList.add(Long.valueOf(id));
			}
		}
		return removeYearIdList;

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
	 * @return the removeContactDetIds
	 */
	public String getRemoveContactDetIds() {
		return removeContactDetIds;
	}

	/**
	 * @param removeContactDetIds the removeContactDetIds to set
	 */
	public void setRemoveContactDetIds(String removeContactDetIds) {
		this.removeContactDetIds = removeContactDetIds;
	}

	/**
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the iaAllYr
	 */
	public String getIaAllYr() {
		return iaAllYr;
	}

	/**
	 * @param iaAllYr the iaAllYr to set
	 */
	public void setIaAllYr(String iaAllYr) {
		this.iaAllYr = iaAllYr;
	}

	/**
	 * @return the cbboAppyr
	 */
	public String getCbboAppyr() {
		return cbboAppyr;
	}

	/**
	 * @param cbboAppyr the cbboAppyr to set
	 */
	public void setCbboAppyr(String cbboAppyr) {
		this.cbboAppyr = cbboAppyr;
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
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	/**
	 * @return the cbId
	 */
	public Long getCbId() {
		return cbId;
	}

	/**
	 * @param cbId the cbId to set
	 */
	public void setCbId(Long cbId) {
		this.cbId = cbId;
	}

	/**
	 * @return the iId
	 */
	public Long getiId() {
		return iId;
	}

	/**
	 * @param iId the iId to set
	 */
	public void setiId(Long iId) {
		this.iId = iId;
	}

	
	
}
