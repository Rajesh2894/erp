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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.service.AllocationOfBlocksService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class AllocationOfBlocksModel extends AbstractFormModel {

	@Autowired
	private IOrganisationService orgService;

	private static final long serialVersionUID = -340063804259175404L;

	private List<TbDepartment> departmentList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private BlockAllocationDto blockAllocationDto = new BlockAllocationDto();

	private List<BlockAllocationDto> blockAllocationDtoList = new ArrayList<>();

	@Autowired
	private AllocationOfBlocksService allocationOfBlocksService;

	List<TbOrganisation> orgList = new ArrayList<>();

	List<CommonMasterDto> commonMasterDtoList = new ArrayList<>();

	List<CBBOMasterDto> cbboMasterList = new ArrayList<>();

	private String saveMode;

	List<LookUp> stateList = new ArrayList<>();

	List<LookUp> districtList = new ArrayList<>();

	List<LookUp> blockList = new ArrayList<>();
	
	List<LookUp> allocationCatgList = new ArrayList<>();
	List<LookUp> allocationSubCatgList = new ArrayList<>();
	List<LookUp> allcSubCatgTargetList = new ArrayList<>();

	private String showEdit;
	private String viewMode;

	private String OrgShortNm;

	private Long orgIaId;

	/**
	 * @return the departmentList
	 */
	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	/**
	 * @param departmentList the departmentList to set
	 */
	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
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
	 * @return the blockAllocationDto
	 */
	public BlockAllocationDto getBlockAllocationDto() {
		return blockAllocationDto;
	}

	/**
	 * @param blockAllocationDto the blockAllocationDto to set
	 */
	public void setBlockAllocationDto(BlockAllocationDto blockAllocationDto) {
		this.blockAllocationDto = blockAllocationDto;
	}

	/**
	 * @return the blockAllocationDtoList
	 */
	public List<BlockAllocationDto> getBlockAllocationDtoList() {
		return blockAllocationDtoList;
	}

	/**
	 * @param blockAllocationDtoList the blockAllocationDtoList to set
	 */
	public void setBlockAllocationDtoList(List<BlockAllocationDto> blockAllocationDtoList) {
		this.blockAllocationDtoList = blockAllocationDtoList;
	}

	/**
	 * @return the orgList
	 */
	public List<TbOrganisation> getOrgList() {
		return orgList;
	}

	/**
	 * @param orgList the orgList to set
	 */
	public void setOrgList(List<TbOrganisation> orgList) {
		this.orgList = orgList;
	}

	/**
	 * @return the commonMasterDtoList
	 */
	public List<CommonMasterDto> getCommonMasterDtoList() {
		return commonMasterDtoList;
	}

	/**
	 * @param commonMasterDtoList the commonMasterDtoList to set
	 */
	public void setCommonMasterDtoList(List<CommonMasterDto> commonMasterDtoList) {
		this.commonMasterDtoList = commonMasterDtoList;
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
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
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
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	/**
	 * @return the blockList
	 */
	public List<LookUp> getBlockList() {
		return blockList;
	}

	/**
	 * @param blockList the blockList to set
	 */
	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

	/**
	 * @return the showEdit
	 */
	public String getShowEdit() {
		return showEdit;
	}

	/**
	 * @param showEdit the showEdit to set
	 */
	public void setShowEdit(String showEdit) {
		this.showEdit = showEdit;
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
	 * @return the orgShortNm
	 */
	public String getOrgShortNm() {
		return OrgShortNm;
	}

	/**
	 * @param orgShortNm the orgShortNm to set
	 */
	public void setOrgShortNm(String orgShortNm) {
		OrgShortNm = orgShortNm;
	}

	/**
	 * @return the orgIaId
	 */
	public Long getOrgIaId() {
		return orgIaId;
	}

	/**
	 * @param orgIaId the orgIaId to set
	 */
	public void setOrgIaId(Long orgIaId) {
		this.orgIaId = orgIaId;
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
	 * @return the allcSubCatgTargetList
	 */
	public List<LookUp> getAllcSubCatgTargetList() {
		return allcSubCatgTargetList;
	}

	/**
	 * @param allcSubCatgTargetList the allcSubCatgTargetList to set
	 */
	public void setAllcSubCatgTargetList(List<LookUp> allcSubCatgTargetList) {
		this.allcSubCatgTargetList = allcSubCatgTargetList;
	}

	@Override
	public boolean saveForm() {

		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		String lgIp = employee.getEmppiservername();
		Date newDate = new Date();
		BlockAllocationDto mastDto = getBlockAllocationDto();
		Organisation npmaOrg = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.NPMA);
		Organisation iaOrg = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(employee.getEmpId());
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(npmaOrg.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setLangId(langId);
			mastDto.setStatus(MainetConstants.FlagA);

			mastDto.getTargetDetDto().forEach(detDto -> {
				detDto.setCreatedBy(employee.getEmpId());
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(npmaOrg.getOrgid());
				detDto.setLgIpMac(lgIp);
				detDto.setStatus(MainetConstants.FlagA);
			});

			mastDto.getBlockDetailDto().forEach(detDto -> {
				detDto.setCreatedBy(employee.getEmpId());
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(iaOrg.getOrgid());
				detDto.setLgIpMac(lgIp);
				detDto.setStatus(MainetConstants.FlagA);
			});

		} else {
			mastDto.setUpdatedBy(employee.getEmpId());
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(npmaOrg.getOrgid());

			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			mastDto.setLangId(langId);

			mastDto.getTargetDetDto().forEach(detailDto -> {
				if (detailDto.getCreatedBy() == null) {
					detailDto.setCreatedBy(employee.getEmpId());
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(npmaOrg.getOrgid());
					detailDto.setLgIpMac(lgIp);
					detailDto.setStatus(MainetConstants.FlagA);
				} else {
					detailDto.setUpdatedBy(employee.getEmpId());
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(npmaOrg.getOrgid());
					detailDto.setLgIpMac(lgIp);
					detailDto.setStatus(MainetConstants.FlagA);
					
				}
			});

			mastDto.getBlockDetailDto().forEach(detailDto -> {
				if (detailDto.getCreatedBy() == null) {
					detailDto.setCreatedBy(employee.getEmpId());
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(iaOrg.getOrgid());
					detailDto.setLgIpMac(lgIp);
					detailDto.setStatus(MainetConstants.FlagA);
				} else {
					detailDto.setUpdatedBy(employee.getEmpId());
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(iaOrg.getOrgid());
					detailDto.setLgIpMac(lgIp);
					detailDto.setStatus(MainetConstants.FlagA);
				}
			});

		}

		allocationOfBlocksService.saveBlockDetails(mastDto, this);
		if (this.getSaveMode().equals(MainetConstants.FlagA))
			this.setSuccessMessage(getAppSession().getMessage("sfac.block.form.save"));
		else
			this.setSuccessMessage(getAppSession().getMessage("sfac.block.form.update"));
		return true;
	}

}
