package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
import com.abm.mainet.workManagement.dto.MilestoneEntryDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.MileStoneService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.validator.MilestoneValidator;
import com.abm.mainet.workManagement.ui.validator.MilestoneValidatorDet;

/**
 * @author Saiprasad.Vengurlekar
 * @since 22 march 2018
 */

@Component
@Scope("session")
public class PhysicalMilestoneModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private WmsProjectMasterService projectMasterService;
	
	@Autowired
	private MileStoneService mileStoneService;
	
	@Autowired
	private WorkDefinitionService workDefinationService;

	private String mileStoneFlag;

	private String saveMode;

	private Long orgId;

	private MileStoneDTO mileStoneDTO;

	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();

	private List<WmsProjectMasterDto> projectList = new ArrayList<>();

	private List<MileStoneDTO> mileStoneList;

	private List<MileStoneDTO> milestoneDtoList = new ArrayList<>();
	private String removedIds;

	private MilestoneEntryDto milestoneEntryDto;

	private List<MilestoneEntryDto> milestoneEntryDtos;
	
	private String mileStoneName;
	private Long mileStoneId;
	
	private Long workId;
	private String workName;
	
	private BigDecimal WorkEstAmt;
	
	public BigDecimal getWorkEstAmt() {
		return WorkEstAmt;
	}

	public void setWorkEstAmt(BigDecimal workEstAmt) {
		WorkEstAmt = workEstAmt;
	}

	public String getMileStoneName() {
		return mileStoneName;
	}

	public void setMileStoneName(String mileStoneName) {
		this.mileStoneName = mileStoneName;
	}

	public Long getMileStoneId() {
		return mileStoneId;
	}

	public void setMileStoneId(Long mileStoneId) {
		this.mileStoneId = mileStoneId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public List<MilestoneEntryDto> getMilestoneEntryDtos() {
		return milestoneEntryDtos;
	}

	public void setMilestoneEntryDtos(List<MilestoneEntryDto> milestoneEntryDtos) {
		this.milestoneEntryDtos = milestoneEntryDtos;
	}

	public MilestoneEntryDto getMilestoneEntryDto() {
		return milestoneEntryDto;
	}

	public void setMilestoneEntryDto(MilestoneEntryDto milestoneEntryDto) {
		this.milestoneEntryDto = milestoneEntryDto;
	}

	public String getMileStoneFlag() {
		return mileStoneFlag;
	}

	public void setMileStoneFlag(String mileStoneFlag) {
		this.mileStoneFlag = mileStoneFlag;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public List<WmsProjectMasterDto> getProjectMasterList() {
		return projectMasterList;
	}

	public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public MileStoneDTO getMileStoneDTO() {
		return mileStoneDTO;
	}

	public void setMileStoneDTO(MileStoneDTO mileStoneDTO) {
		this.mileStoneDTO = mileStoneDTO;
	}

	public List<MileStoneDTO> getMileStoneList() {
		return mileStoneList;
	}

	public void setMileStoneList(List<MileStoneDTO> mileStoneList) {
		this.mileStoneList = mileStoneList;
	}

	public List<MileStoneDTO> getMilestoneDtoList() {
		return milestoneDtoList;
	}

	public void setMilestoneDtoList(List<MileStoneDTO> milestoneDtoList) {
		this.milestoneDtoList = milestoneDtoList;
	}

	public String getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(String removedIds) {
		this.removedIds = removedIds;
	}

	public List<WmsProjectMasterDto> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<WmsProjectMasterDto> projectList) {
		this.projectList = projectList;
	}

	@Override
	public boolean saveForm() {

		Long projId = mileStoneDTO.getProjId();
		Long workId = mileStoneDTO.getWorkId();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Date todayDate = new Date();
		for (MileStoneDTO dto : milestoneDtoList) {
			if (dto.getMileId() == null) {
				dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				dto.setCreatedBy(empId);
				dto.setCreatedDate(todayDate);
				dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				dto.setProjId(projId);
				dto.setMileStoneType(mileStoneDTO.getMileStoneType());
				dto.setMileStoneId(mileStoneDTO.getMileStoneId());
				if (workId != null)
					dto.setWorkId(workId);

			} else {
				dto.setUpdatedBy(empId);
				dto.setUpdatedDate(todayDate);
				dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				dto.setProjId(projId);
				dto.setMileStoneType(mileStoneDTO.getMileStoneType());
				dto.setMileStoneId(mileStoneDTO.getMileStoneId());
				if (workId != null)
					dto.setWorkId(workId);
			}

			validateBean(dto, MilestoneValidator.class);
			if (hasValidationErrors()) {
				return false;
			}
		}
		
		//128984 and 123976 To validate whether two milestone should not have same name.
		validateBean(milestoneDtoList, MilestoneValidatorDet.class);
		if (hasValidationErrors()) {
			setMilestoneEntryDtos(mileStoneService.getMilestoneInfo(projId, workId, orgId));
			WorkDefinitionDto findAllWorkDefinitionById = workDefinationService.findAllWorkDefinitionById(workId);
			setWorkId(findAllWorkDefinitionById.getWorkId());
			setWorkName(findAllWorkDefinitionById.getWorkName());
			//mileStoneService.getMilestoneByMileNm(projId, workId, milestoneDtoList.get(0).getMileStoneId(), UserSession.getCurrent().getOrganisation().getOrgid());
			return false;
		}
		if (saveMode.equals(MainetConstants.MODE_CREATE)) {
			ApplicationContextProvider.getApplicationContext().getBean(MileStoneService.class)
					.addMilestoneEntry(milestoneDtoList);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("leadlift.master.save"));
		} else {
			String ids = getRemovedIds();
			ApplicationContextProvider.getApplicationContext().getBean(MileStoneService.class)
					.saveAndUpdateMilestone(milestoneDtoList, ids);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("leadlift.master.update"));
		}

		return true;
	}

	public List<WmsProjectMasterDto> projectData() {

		List<Long> projId = new ArrayList<>();

		List<Object[]> projIdss = projectMasterService.getAllProjectAssociatedByMileStone(
				UserSession.getCurrent().getOrganisation().getOrgid(), getMileStoneFlag());
		List<Long> list = null;
		Map<Long, List<Long>> projWorkId = new HashMap<>();
		for (Object[] lId : projIdss) {
			Long proj = (Long) lId[0];
			if (projWorkId.containsKey(proj)) {
				list = projWorkId.get(proj);
				if (lId[1] != null) {
					list.add((Long) lId[1]);
					projWorkId.put(proj, list);
				}
			} else {
				list = new ArrayList<>();
				list.add((Long) lId[1]);
				projWorkId.put(proj, list);
			}

			projId.add(proj);
		}
		List<WmsProjectMasterDto> projectMasList = null;

		if (!projId.isEmpty()) {
			projectMasList = projectMasterService
					.getAllProjectMaster(UserSession.getCurrent().getOrganisation().getOrgid(), projId);
			if (projectMasList != null && !projectMasList.isEmpty()) {
				for (WmsProjectMasterDto project : projectMasList) {
					for (Object[] listOfObject : projIdss) {
						if (project.getProjId().longValue() == (long) Double.parseDouble(listOfObject[0].toString())) {
							if (listOfObject[1] != null) {
								Long workId = (Long) listOfObject[1];
								project.getWorkIdList().add(workId);

							}
						}

					}

				}
			}
		}
		if(projectMasList!=null && !projectMasList.isEmpty()) {
		Collections.reverse(projectMasList);
		setProjectList(projectMasList);
		}
		return projectMasList;
	}

	
}