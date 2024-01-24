package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.workManagement.dto.ContractCompletionDto;
import com.abm.mainet.workManagement.dto.SearchDTO;
import com.abm.mainet.workManagement.dto.SummaryDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.CompletionCertificateService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.model.CompletionCertificateModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Controller
@RequestMapping("/CompletionCertificate.html")
public class CompletionCertificateController extends AbstractFormController<CompletionCertificateModel> {

    @Autowired
    private WmsProjectMasterService projectMasterService;

    @Autowired
    private WorkDefinitionService workDefinationService;

    @Autowired
    TenderInitiationService tenderInitiationService;

    @Autowired
    IContractAgreementService iContractAgreementService;
    
    @Autowired
    private TbOrganisationService tbOrganisationService;

    /**
     * Used to default Completion Certificate Summary page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(orgId));
        this.getModel().setCommonHelpDocs("CompletionCertificate.html");
        List<WorkDefinitionDto> workDefinitionDtos = workDefinationService.findAllCompletedWorks(orgId);
        List<ContractCompletionDto> completionDtos = new ArrayList<>();
        if (!workDefinitionDtos.isEmpty()) {
            for (WorkDefinitionDto workDefinitionDto : workDefinitionDtos) {
                ContractCompletionDto completionDto = new ContractCompletionDto();
                completionDto.setCompletionNo(workDefinitionDto.getWorkCompletionNo());
                completionDto.setWorkId(workDefinitionDto.getWorkId());
                completionDto.setCompletionDate(
                        UtilityService.convertDateToDDMMYYYY(workDefinitionDto.getWorkCompletionDate()));
                completionDto.setWorkName(workDefinitionDto.getWorkName());
                completionDto.setProjName(workDefinitionDto.getProjName());
                TenderWorkDto workDto = tenderInitiationService.findContractByWorkId(workDefinitionDto.getWorkId());
                if (workDto != null) {
                    completionDto.setContractorName(workDto.getVendorName());
                }
                completionDtos.add(completionDto);
            }
            this.getModel().setCompletionDtos(completionDtos);
        }
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
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
            @RequestParam(MainetConstants.Common_Constant.FLAG) String flag) {
        if (flag.equals(MainetConstants.FlagC)) {
            return workDefinationService.findAllCompletedWorksByProjId(projId, MainetConstants.FlagC,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            return workDefinationService.findAllCompletedWorksByProjId(projId, MainetConstants.FlagN,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }
    }

    /**
     * @param projId
     * @param workId
     * @param completionNo
     * @return completionDtoList
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_COMPLETION_DATA, method = RequestMethod.POST)
    public List<ContractCompletionDto> getCompletionDtoList(
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
            @RequestParam(MainetConstants.WorksManagement.COMPLETION_NO) String completionNo,
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<WorkDefinitionDto> workDefinitionDtos = workDefinationService.filterCompletionRecords(projId, workId,
                completionNo, orgId);

        List<ContractCompletionDto> completionDtos = new ArrayList<>();
        if (!workDefinitionDtos.isEmpty()) {
            for (WorkDefinitionDto workDefinitionDto : workDefinitionDtos) {
                ContractCompletionDto completionDto = new ContractCompletionDto();
                completionDto.setCompletionNo(workDefinitionDto.getWorkCompletionNo());
                completionDto.setWorkId(workDefinitionDto.getWorkId());
                completionDto.setCompletionDate(
                        UtilityService.convertDateToDDMMYYYY(workDefinitionDto.getWorkCompletionDate()));
                completionDto.setWorkName(workDefinitionDto.getWorkName());
                completionDto.setProjName(workDefinitionDto.getProjName());
                TenderWorkDto workDto = tenderInitiationService.findContractByWorkId(workDefinitionDto.getWorkId());
                if (workDto != null) {
                    completionDto.setContractorName(workDto.getVendorName());
                }
                completionDtos.add(completionDto);
            }
        }
        return completionDtos;
    }

    /**
     * Used to get Completion Certificate Form
     * 
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.GET_COMPLETIONFORM)
    public ModelAndView getCompletionCertificateForm(final HttpServletRequest request) {
        sessionCleanup(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(orgId));
        return new ModelAndView(MainetConstants.WorksManagement.COMPLETIONFORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * Used to get All Work and Contract Details By workId
     * 
     * @param workId
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_WORK_DETAILS, method = RequestMethod.POST)
    public ContractCompletionDto getAllWorkDetailsByworkId(
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TenderWorkDto workDto = tenderInitiationService.findContractByWorkId(workId);
        ContractMastDTO contractDTO = iContractAgreementService.findById(workDto.getContractId(), orgId);
        ContractCompletionDto completionDto = new ContractCompletionDto();
        completionDto.setContractStartDate(
                UtilityService.convertDateToDDMMYYYY(contractDTO.getContractDetailList().get(0).getContFromDate()));
        completionDto.setContractEndDate(
                UtilityService.convertDateToDDMMYYYY(contractDTO.getContractDetailList().get(0).getContToDate()));
        completionDto.setContractNo(contractDTO.getContNo());
        completionDto.setWorkName(workDto.getWorkName());
        completionDto.setProjName(workDto.getProjectName());
        completionDto.setVendorId(workDto.getVenderId());
        completionDto.setProjDeptId(workDto.getProjDeptId());
        completionDto.setWorkId(workId);
        completionDto.setWorkCode(workDto.getWorkCode());
        completionDto.setLocId(workDto.getLocId());
        completionDto.setWorkCategory(workDto.getWorkCategory());
        completionDto.setAssetCategory(CommonMasterUtility.findLookUpDesc("ACL", orgId, workDto.getWorkCategory()));
        /* REMOVE AS PER SUDA UAT */
        /*
         * completionDto.setWorkStartDate(workDto.getWorkPlannedDate()); completionDto.setWorkEndDate(workDto.getWorkEndDate())
         */;
        completionDto.setWorkTypeDesc(
                CommonMasterUtility.findLookUpDesc(MainetConstants.WorksManagement.WRT, orgId, workDto.getWorkType()));
        completionDto.setContractorName(workDto.getVendorName());
        this.getModel().setContractCompletionDto(completionDto);
        return completionDto;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_ASSET_DETAIL, method = RequestMethod.POST)
    public SummaryDto updateAssetDetails(
            @RequestParam(MainetConstants.WorksManagement.ASSET_SERIAL_NO) String astSerialNo) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setAstSerialNo(astSerialNo);
        searchDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        List<SummaryDto> summaryDto = ApplicationContextProvider.getApplicationContext()
                .getBean(CompletionCertificateService.class)
                .getAssetDetails(searchDTO);
        if (summaryDto != null && !summaryDto.isEmpty()) {
            this.getModel().setSummaryDto(summaryDto.get(0));
            return summaryDto.get(0);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_COMPLETIONDATA, method = RequestMethod.POST)
    public ModelAndView getCompletionCertificateData(
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
    	Long orgId =  UserSession.getCurrent().getOrganisation().getOrgid();
        TenderWorkDto workDto = tenderInitiationService.findContractByWorkId(workId);
        ContractMastDTO contractDTO = iContractAgreementService.findById(workDto.getContractId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        //Defect #92409  Fields are set those are required to get Completion certificate date on report.
        this.getModel().setWorkName(workDto.getWorkName());
        this.getModel().setVendorName(workDto.getVendorName());
        this.getModel().setWorkorderNo(workDto.getWorkCode());
        int languageId = UserSession.getCurrent().getLanguageId();
        String[] split;
        if(languageId == 1) {
        	split = UserSession.getCurrent().getOrganisation().getONlsOrgname().split(" ");
        	this.getModel().setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        }else {
        	split= UserSession.getCurrent().getOrganisation().getONlsOrgnameMar().split(" ");
        	this.getModel().setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }
        this.getModel().setUlbName(split[0]);
        this.getModel().setWorkDate(Utility.dateToString(new Date(), "dd/MM/YYYY"));
        
        TbOrganisation organisation = tbOrganisationService.findById(orgId);
        LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(organisation.getOrgCpdIdState());
        
           
        for (ContractCompletionDto completionDto : this.getModel().getCompletionDtos()) {
            if (workId == completionDto.getWorkId().longValue()) {
                completionDto.setContDate(UtilityService.convertDateToDDMMYYYY(contractDTO.getContDate()));
                this.getModel().setContractCompletionDto(completionDto);
                break;
            }
        }
        if(UserSession.getCurrent().getLanguageId() == 1) {
            this.getModel().getContractCompletionDto().setOrgState(lookUp.getDescLangFirst());}
            else if(UserSession.getCurrent().getLanguageId() == 2) {
            this.getModel().getContractCompletionDto().setOrgState(lookUp.getDescLangSecond());}
        return new ModelAndView(MainetConstants.WorksManagement.COMPLETION_FORM, MainetConstants.FORM_NAME,
                this.getModel());

    }
    
    //Defect #93342
    @ResponseBody
    @RequestMapping(params = "saveAndPrintCompletionReport", method = RequestMethod.POST)
    public Map<String, Object> saveAndPrintCompletionReport(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        this.getModel().saveForm();

        Map<String, Object> object = new LinkedHashMap<String, Object>();
        object.put("sucessMsg", this.getModel().getSuccessMessage());
        object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());

        object.put(MainetConstants.WorksManagement.WORK_ID, this.getModel().getContractCompletionDto().getWorkId());
      
        return object;

    }

}
