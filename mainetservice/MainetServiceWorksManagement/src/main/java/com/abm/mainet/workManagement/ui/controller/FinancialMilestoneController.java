package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
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
 * @author vishwajeet.kumar
 * @since 22 March 2018
 */
@Controller
@RequestMapping("/FinancialMilestone.html")
public class FinancialMilestoneController extends AbstractFormController<PhysicalMilestoneModel> {

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
        this.getModel().setMileStoneFlag(MainetConstants.FlagF);
        this.getModel().setCommonHelpDocs("FinancialMilestone.html");
        
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
        this.getModel().getProjectList().forEach(obj -> {
            if (obj.getProjId().longValue() == projId.longValue()) {
                workId.addAll(obj.getWorkIdList());
            }
        });
        if (!workId.isEmpty())
            defDtoList = workDefinationService
                    .findAllWorkByWorkList(UserSession.getCurrent().getOrganisation().getOrgid(), workId);
        Collections.reverse(defDtoList);//work order chnage by Suhel 34782

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
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
        List<WmsProjectMasterDto> projectDtoList = null;
        Long currentOrgid = UserSession.getCurrent().getOrganisation().getOrgid();
        String flag = this.getModel().getMileStoneFlag();

        if (projId != 0 && workId == null) {
            projectDtoList = mileStoneService.findALLMileStoneList(projId, currentOrgid, flag);

        } else if (projId != 0 && workId != null) {
            projectDtoList = mileStoneService.findALLMileStoneListwithWorkId(projId, workId, currentOrgid, flag);

        }
        if (projectDtoList != null) {
            Set<WmsProjectMasterDto> proejectSet = new HashSet<>();
            proejectSet.addAll(projectDtoList);
            projectDtoList = new ArrayList<>();
            projectDtoList.addAll(proejectSet);
        }
        return projectDtoList;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_FINMILESTONE)
    public ModelAndView milestoneCreate(
            @RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
            final HttpServletRequest request) {
        PhysicalMilestoneModel model = this.getModel();
        model.getMileStoneDTO().setMileStoneType(MainetConstants.FlagF);
        model.setSaveMode(MainetConstants.MODE_CREATE);
        this.getModel().projectData();
        return new ModelAndView(MainetConstants.WorksManagement.ADD_FINMILESTONE, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.EDIT_MILESTONE, method = RequestMethod.POST)
    public ModelAndView editMilestoneData(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
            @RequestParam(name = MainetConstants.WorksManagement.FORM_MODE, required = false) String formMode,
            @RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId,
            @RequestParam(name = MainetConstants.WorksManagement.WORK_NAME, required = false) final String workName) {

        PhysicalMilestoneModel model = this.getModel();
        model.setSaveMode(formMode);
        model.getMileStoneDTO().setProjId(projId);
        model.getMileStoneDTO().setProjName(projectMasterService.getProjectMasterByProjId(projId).getProjNameEng());
        model.getMileStoneDTO().setProjNameReg(projectMasterService.getProjectMasterByProjId(projId).getProjNameReg());

        if (workId != null)
            model.getMileStoneDTO().setWorkId(workId);
        if (!workName.equalsIgnoreCase(MainetConstants.WorksManagement.NULL) && !workName.isEmpty())
            model.getMileStoneDTO().setWorkName(workName);

        List<MileStoneDTO> milestoneDtos = mileStoneService.editMilestone(projId, model.getMileStoneDTO().getWorkId(),
                model.getMileStoneFlag(), UserSession.getCurrent().getOrganisation().getOrgid());
        if (!milestoneDtos.isEmpty()) {
            model.getMileStoneDTO().setMileStoneType(milestoneDtos.get(0).getMileStoneType());
            model.setMilestoneDtoList(milestoneDtos);
        }
        return new ModelAndView(MainetConstants.WorksManagement.ADD_FINMILESTONE, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.CHECK_MILESTONE, method = RequestMethod.POST)
    public boolean checkMilestone(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) final Long projId,
            @RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId) {

        PhysicalMilestoneModel model = this.getModel();
        return mileStoneService.checkMilestone(projId, model.getMileStoneFlag(),
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    /**
     * Used to check For Project is defines or not
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
    
    // #76883
    @ResponseBody
 	@RequestMapping(params = "getWorkDetails", method = RequestMethod.POST)
 	public String getWorkOrderDate(@RequestParam(name = MainetConstants.WorksManagement.WORK_ID, required = false) final Long workId) {
 		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
 		MilestoneEntryDto milestoneEntryDto = new MilestoneEntryDto();
 		TenderWorkDto tenderDto = initiationService.findContractByWorkId(workId);
 		if(tenderDto != null) {
 		if(tenderDto.getContractId() !=null) {
 		WorkOrderDto workorderDto = workOrderService.fetchWorkOrderByContId(tenderDto.getContractId(), orgId);
 		milestoneEntryDto.setWorkOrdCommDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(workorderDto.getStartDate()));
 		}
 		}
 		return milestoneEntryDto.getWorkOrdCommDate();		
 	}
		
    @ResponseBody
	@RequestMapping(params = "getworkAmount", method = RequestMethod.POST)
	public Double getworkEstAmount(final HttpServletRequest request,
		   @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
		   @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
		
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			
			WorkDefinitionDto defDto = workDefinationService.findWorkDefinitionByWorkId(workId,orgId);
	
		    Double amount = (defDto.getWorkEstAmt()).doubleValue();

	        return amount;
		    }

		}
