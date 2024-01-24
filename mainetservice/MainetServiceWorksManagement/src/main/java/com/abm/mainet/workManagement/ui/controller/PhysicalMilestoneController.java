package com.abm.mainet.workManagement.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
import com.abm.mainet.workManagement.dto.MilestoneEntryDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.MileStoneService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.ui.model.PhysicalMilestoneModel;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author Saiprasad.Vengurlekar And Vishwajeet
 * @since 22 march 2018
 */

@Controller
@RequestMapping("/Milestone.html")
public class PhysicalMilestoneController extends AbstractFormController<PhysicalMilestoneModel> {

	@Autowired
	private WmsProjectMasterService projectMasterService;

	@Autowired
	private WorkDefinitionService workDefinationService;

	@Autowired
	private MileStoneService mileStoneService;


	@Autowired
	private TenderInitiationService initiationService;
	
	@Autowired
	private WorkOrderService workOrderService;
	
	/**
	 * Used to default Physical Milestone Summary page
	 * 
	 * @param httpServletRequest
	 * @return defaultResult
	 * @throws Exception
	 */

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) throws Exception {
		sessionCleanup(httpServletRequest);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setMileStoneFlag(MainetConstants.FlagP);
		this.getModel().setOrgId(currentOrgId);
		this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));
		return defaultResult();
	}

	/**
	 * Used to get All Active WorksName By ProjectId
	 * 
	 * @param orgId
	 * @param projId
	 * @return workDefinationDto
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
	public List<WorkDefinitionDto> getAllActiveWorksNameByProjectId(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {

		List<WorkDefinitionDto> obj = workDefinationService
				.findAllWorkDefinationByProjId(UserSession.getCurrent().getOrganisation().getOrgid(), projId);

		return obj;

	}

	/**
	 * Used to get All Active getAllActiveWorksName
	 * 
	 * @param projId
	 * @return obj
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.WORKNAME_BYPROJ, method = RequestMethod.POST)
	public List<WorkDefinitionDto> getAllActiveWorksName(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {
		List<Long> workId = new ArrayList<>();
		List<WorkDefinitionDto> defDtoList = new ArrayList<>();
		if (!this.getModel().getProjectList().isEmpty()) {
			for (WmsProjectMasterDto projDto : this.getModel().getProjectList()) {
				if (projDto.getProjId().longValue() == projId.longValue()) {
					workId.addAll(projDto.getWorkIdList());
				}
			}
		}

		if (!workId.isEmpty()) {
			defDtoList = workDefinationService
					.findAllWorkByWorkList(UserSession.getCurrent().getOrganisation().getOrgid(), workId);
			Collections.reverse(defDtoList);
		}

		return defDtoList;
	}

	/**
	 * @param projId
	 * @param workId
	 * @param mileStoneFlag
	 * @return projectDtoList
	 */

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.GET_MILESTONES, method = RequestMethod.POST)
	public List<WmsProjectMasterDto> physicalMilestoneList(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId,
			@RequestParam(MainetConstants.WorksManagement.mileStoneFlag) String mileStoneFlag) {
		List<WmsProjectMasterDto> projectDtoList = new ArrayList<>();
		Long currentOrgid = UserSession.getCurrent().getOrganisation().getOrgid();
		String flag = this.getModel().getMileStoneFlag();
		if (projId != 0 && workId == null) {
			projectDtoList = mileStoneService.findALLMileStoneList(projId, currentOrgid, flag);

		} else if (projId != 0 && workId != null) {
			projectDtoList = mileStoneService.findALLMileStoneListwithWorkId(projId, workId, currentOrgid, flag);

		}
		if (!projectDtoList.isEmpty()) {
			Set<WmsProjectMasterDto> proejectSet = new HashSet<>();
			proejectSet.addAll(projectDtoList);
			projectDtoList = new ArrayList<>();
			projectDtoList.addAll(proejectSet);
		}

		return projectDtoList;
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_PHY_MILESTONES)
	public ModelAndView physicalMilestoneCreate(
			@RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
			final HttpServletRequest request) {
		PhysicalMilestoneModel model = this.getModel();
		model.getMileStoneDTO().setMileStoneType(MainetConstants.FlagP);
		model.setSaveMode(MainetConstants.MODE_CREATE);
		this.getModel().projectData();
		return new ModelAndView(MainetConstants.WorksManagement.ADD_PHY_MILESTONES, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.EDIT_MILESTONE, method = RequestMethod.POST)
	public ModelAndView editPhysicalMilestoneData(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
			@RequestParam(name = MainetConstants.WorksManagement.FORM_MODE, required = false) String formMode,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_NAME, required = false) final String workName) {

		PhysicalMilestoneModel model = this.getModel();
		model.setSaveMode(formMode);
		model.getMileStoneDTO().setProjId(projId);
		model.getMileStoneDTO().setProjName(projectMasterService.getProjectMasterByProjId(projId).getProjNameEng());
		if (workId != null)
			model.getMileStoneDTO().setWorkId(workId);

		if (!workName.equalsIgnoreCase(MainetConstants.WorksManagement.NULL) && !workName.isEmpty())
			model.getMileStoneDTO().setWorkName(workName);
		// #89031
		/*
		 * List<MileStoneDTO> milestoneDtos = mileStoneService.editMilestone(projId,
		 * workId, model.getMileStoneFlag(),
		 * UserSession.getCurrent().getOrganisation().getOrgid()); if
		 * (!milestoneDtos.isEmpty()) {
		 * model.getMileStoneDTO().setMileStoneType(milestoneDtos.get(0).
		 * getMileStoneType()); model.setMilestoneDtoList(milestoneDtos);
		 * model.getMileStoneDTO().setMileStoneName(milestoneDtos.get(0).
		 * getMileStoneName()); }
		 */
		List<MilestoneEntryDto> milestoneEntryDtos = new ArrayList<MilestoneEntryDto>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		milestoneEntryDtos = mileStoneService.getMilestoneInfo(projId, workId, orgId);
		this.getModel().setMilestoneEntryDtos(milestoneEntryDtos);

		return new ModelAndView(MainetConstants.WorksManagement.ADD_PHY_MILESTONES, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.CHECK_MILESTONE, method = RequestMethod.POST)
	public boolean checkMilestone(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId) {

		PhysicalMilestoneModel model = this.getModel();
		boolean valid = false;

		List<MileStoneDTO> milestoneDtos = mileStoneService.editMilestone(projId, workId, model.getMileStoneFlag(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (!milestoneDtos.isEmpty()) {
			valid = true;
		}
		return valid;
	}

	/**
	 * Used to check check For Project is defines or not
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.CHECK_PROJ, method = RequestMethod.POST)
	public String checkProjectDefinedOrNot(final HttpServletRequest httpServletRequest) {
		String defaultVal = MainetConstants.Common_Constant.NO;
		List<WmsProjectMasterDto> project = this.getModel().projectData();
		if (project != null && !project.isEmpty()) {
			defaultVal = MainetConstants.Common_Constant.YES;
		}
		return defaultVal;

	}

	@RequestMapping(method = RequestMethod.POST, params = "openMilestoneForm")
	public ModelAndView MilestoneCreate(
			@RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
			final HttpServletRequest request) {

		PhysicalMilestoneModel model = this.getModel();
		model.getMileStoneDTO().setMileStoneType(MainetConstants.FlagP);
		model.setSaveMode(MainetConstants.MODE_CREATE);
		this.getModel().projectData();
		return new ModelAndView("AddMilestone", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "saveMileStoneEntry", method = RequestMethod.POST)
	public ModelAndView saveMilestoneEntry(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId,
			@RequestParam(name = "milestoneName", required = false) final String milestoneName,
			@RequestParam(name = "milestonePer", required = false) final BigDecimal milestonePer) {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Date todayDate = new Date();

		MilestoneEntryDto milestoneEntryDto = new MilestoneEntryDto();
		milestoneEntryDto.setProjId(projId);
		milestoneEntryDto.setWorkId(workId);
		milestoneEntryDto.setMilestoneName(milestoneName);
		milestoneEntryDto.setMilestonePer(milestonePer);
		milestoneEntryDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		milestoneEntryDto.setOrgId(orgId);
		milestoneEntryDto.setCreatedBy(empId);
		milestoneEntryDto.setCreatedDate(todayDate);
		mileStoneService.saveMilestoneEntry(milestoneEntryDto);

		PhysicalMilestoneModel model = this.getModel();
		model.getMileStoneDTO().setMileStoneType(MainetConstants.FlagP);
		model.setSaveMode(MainetConstants.MODE_CREATE);
		this.getModel().projectData();
		return new ModelAndView("AddMilestone", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getMilestoneInfo", method = RequestMethod.POST)
	public @ResponseBody List<MilestoneEntryDto> getMileStoneEntryData(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
		List<MilestoneEntryDto> milestoneEntryDtos = new ArrayList<MilestoneEntryDto>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		milestoneEntryDtos = mileStoneService.getMilestoneInfo(projId, workId, orgId);
		return milestoneEntryDtos;
	}

	@ResponseBody
	@RequestMapping(params = "getDataByMilestoneName", method = RequestMethod.POST)
	public ModelAndView getDataByMileName(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_NAME, required = false) final String workName,
			@RequestParam(name = "milestoneName", required = false) final Long mileStoneId) {

		PhysicalMilestoneModel model = this.getModel();

		model.getMileStoneDTO().setProjId(projId);
		model.getMileStoneDTO().setProjName(projectMasterService.getProjectMasterByProjId(projId).getProjNameEng());
		if (workId != null)
			model.getMileStoneDTO().setWorkId(workId);

		if (!workName.equalsIgnoreCase(MainetConstants.WorksManagement.NULL) && !workName.isEmpty())
			model.getMileStoneDTO().setWorkName(model.getMileStoneDTO().getWorkName());

		List<MileStoneDTO> milestoneDtos = mileStoneService.getMilestoneByMileNm(projId, workId, mileStoneId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (!milestoneDtos.isEmpty()) {
			model.getMileStoneDTO().setMileStoneType(milestoneDtos.get(0).getMileStoneType());
			model.setMilestoneDtoList(milestoneDtos);
			model.getMileStoneDTO().setMileStoneId(milestoneDtos.get(0).getMileStoneId());
		} else {
			model.getMileStoneDTO().setMileStoneType(this.getModel().getMileStoneFlag());
			model.setMilestoneDtoList(milestoneDtos);
			model.getMileStoneDTO().setMileStoneId(mileStoneId);
		}

		List<MilestoneEntryDto> milestoneEntryDtos = new ArrayList<MilestoneEntryDto>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		milestoneEntryDtos = mileStoneService.getMilestoneInfo(projId, workId, orgId);
		this.getModel().setMilestoneEntryDtos(milestoneEntryDtos);

		return new ModelAndView(MainetConstants.WorksManagement.ADD_PHY_MILESTONES, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "getCreateDataByMilestoneName", method = RequestMethod.POST)
	public ModelAndView getCreateDataByMileName(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId,
			@RequestParam(name = "milestoneName", required = false) final Long milestoneId) {

		PhysicalMilestoneModel model = this.getModel();

		model.getMileStoneDTO().setProjId(projId);
		model.getMileStoneDTO().setProjName(projectMasterService.getProjectMasterByProjId(projId).getProjNameEng());
		if (workId != null)
			model.getMileStoneDTO().setWorkId(workId);

		List<MileStoneDTO> milestoneDtos = mileStoneService.getMilestoneByMileNm(projId, workId, milestoneId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (!milestoneDtos.isEmpty()) {
			model.getMileStoneDTO().setMileStoneType(milestoneDtos.get(0).getMileStoneType());
			model.setMilestoneDtoList(milestoneDtos);
			model.getMileStoneDTO().setMileStoneId(milestoneDtos.get(0).getMileStoneId());
		}

		List<MilestoneEntryDto> milestoneEntryDtos = new ArrayList<MilestoneEntryDto>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		milestoneEntryDtos = mileStoneService.getMilestoneInfo(projId, workId, orgId);
		this.getModel().setMilestoneEntryDtos(milestoneEntryDtos);

		return new ModelAndView(MainetConstants.WorksManagement.ADD_PHY_MILESTONES, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "getTotalPer", method = RequestMethod.POST)
	public String getMilestonePer(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId,
			@RequestParam(name = "milestoneName", required = false) final String milestoneName,
			@RequestParam(name = "milestonePer", required = false) final BigDecimal milestonePer) {

		BigDecimal totalPer = mileStoneService.getMileStonePer(projId, workId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		if (totalPer != null) {
			totalPer = totalPer.add(milestonePer);
		}
		BigDecimal per = new BigDecimal(100);

		if (totalPer != null && totalPer.compareTo(per) > 0) {
			return "false";
		} else {
			return "true";
		}

	}
	
	// #76883
	@ResponseBody
	@RequestMapping(params = "getWorkDetails", method = RequestMethod.POST)
	public String getWorkOrderDate(@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		MilestoneEntryDto milestoneEntryDto = new MilestoneEntryDto();
		TenderWorkDto tenderDto = initiationService.findContractByWorkId(workId);		
		if(tenderDto != null && tenderDto.getContractId() != null) {
		WorkOrderDto workorderDto = workOrderService.fetchWorkOrderByContId(tenderDto.getContractId(), orgId);
		milestoneEntryDto.setWorkOrdCommDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(workorderDto.getStartDate()));
		 }		
		return milestoneEntryDto.getWorkOrdCommDate();		
	}
	
	@ResponseBody
	@RequestMapping(params = "checkMilestoneData", method = RequestMethod.POST)
	public boolean checkMilestoneData(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
			@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId,
			@RequestParam(name = "milestoneName", required = false) final String milestoneName,
			@RequestParam(name = "milestonePer", required = false) final BigDecimal milestonePer) {
		
		return mileStoneService.
				checkMilestoneEntry(projId, workId, UserSession.getCurrent().getOrganisation().getOrgid(), milestoneName);

	}

	@ResponseBody
	@RequestMapping(params = "saveupdateMileStone", method = RequestMethod.POST)
	public ModelAndView saveupdateMileStone(final HttpServletRequest request) {
		bindModel(request);
		PhysicalMilestoneModel model = this.getModel();
		Long projId = model.getMilestoneEntryDto().getProjId();
		Long workId = model.getMilestoneEntryDto().getWorkId();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Date todayDate = new Date();
		UserSession.getCurrent().getOrganisation().getOrgid();
        List<MilestoneEntryDto> dto = model.getMilestoneEntryDtos();
        for (MilestoneEntryDto mileDto : dto) {
			if (mileDto.getMileId() == null) {
				mileDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				mileDto.setCreatedBy(empId);
				mileDto.setCreatedDate(todayDate);
				mileDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				mileDto.setProjId(projId);
				mileDto.setMilestoneName(mileDto.getMilestoneName());
				mileDto.setMilestonePer(mileDto.getMilestonePer());
				if (workId != null)
					mileDto.setWorkId(workId);

			} 
			
			}
        mileStoneService.addMilestoneEntryData(dto);
        model.getMileStoneDTO().setMileStoneType(MainetConstants.FlagP);
		model.setSaveMode(MainetConstants.MODE_CREATE);
		this.getModel().projectData();
		return new ModelAndView("AddMilestone", MainetConstants.FORM_NAME, this.getModel());
		}
	}

