/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.service.IAMasterService;

/**
 * @author pooja.maske
 *
 */

@Component
@Scope("session")
public class IAMasterFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 7719688906995951683L;

	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private TbDepartmentService departmentService;

	private List<TbFinancialyear> faYears = new ArrayList<>();
	private IAMasterDto iaMasterDto = new IAMasterDto();
	private List<IAMasterDto> iaMasterDtoList = new ArrayList<>();
	private List<IAMasterDto> iaDetailDtoList = new ArrayList<>();
	private String viewMode;
	List<DesignationBean> designlist = new ArrayList<>();
	private String removeContactDetIds;
	List<LookUp> stateList = new ArrayList<>();
	
	private String dupIaName;
	
	private String dupIaShortNm;

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
	 * @return the iaMasterDto
	 */
	public IAMasterDto getIaMasterDto() {
		return iaMasterDto;
	}

	/**
	 * @param iaMasterDto the iaMasterDto to set
	 */
	public void setIaMasterDto(IAMasterDto iaMasterDto) {
		this.iaMasterDto = iaMasterDto;
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
	 * @return the iaDetailDtoList
	 */
	public List<IAMasterDto> getIaDetailDtoList() {
		return iaDetailDtoList;
	}

	/**
	 * @param iaDetailDtoList the iaDetailDtoList to set
	 */
	public void setIaDetailDtoList(List<IAMasterDto> iaDetailDtoList) {
		this.iaDetailDtoList = iaDetailDtoList;
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
	 * @return the dupIaName
	 */
	public String getDupIaName() {
		return dupIaName;
	}

	/**
	 * @param dupIaName the dupIaName to set
	 */
	public void setDupIaName(String dupIaName) {
		this.dupIaName = dupIaName;
	}

	
	/**
	 * @return the dupIaShortNm
	 */
	public String getDupIaShortNm() {
		return dupIaShortNm;
	}

	/**
	 * @param dupIaShortNm the dupIaShortNm to set
	 */
	public void setDupIaShortNm(String dupIaShortNm) {
		this.dupIaShortNm = dupIaShortNm;
	}

	@Override
	public boolean saveForm() throws FrameworkException {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.IA);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		IAMasterDto mastDto = getIaMasterDto();
		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setDeptId(deptId);
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());

			mastDto.getIaDetailDto().forEach(contactDto -> {
				contactDto.setCreatedBy(createdBy);
				contactDto.setCreatedDate(newDate);
				contactDto.setOrgId(org.getOrgid());
				contactDto.setLgIpMac(lgIp);
				contactDto.setStatus(MainetConstants.FlagA);
			});
			mastDto = iaMasterService.saveAndUpdateApplication(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.ia.master.save.msg"));
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setDeptId(deptId);
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());

			mastDto.getIaDetailDto().forEach(contactDto -> {
				if (contactDto.getCreatedBy() == null) {
					contactDto.setCreatedBy(createdBy);
					contactDto.setCreatedDate(newDate);
					contactDto.setOrgId(org.getOrgid());
					contactDto.setLgIpMac(lgIp);
					contactDto.setStatus(MainetConstants.FlagA);
				} else {
					contactDto.setUpdatedBy(createdBy);
					contactDto.setUpdatedDate(newDate);
					contactDto.setOrgId(org.getOrgid());
					contactDto.setLgIpMac(lgIp);
				}
			});
			mastDto = iaMasterService.updateIaMasterDetail(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.ia.master.update.msg"));
		}
		List<Long> removedContDetIdsList = getRemovedContactDetList();
		if (removedContDetIdsList != null && !removedContDetIdsList.isEmpty()) {
			iaMasterService.inactiveRemovedContactDetails(getIaMasterDto(), removedContDetIdsList);
		}
		setIaMasterDto(mastDto);

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
	

}
