/**
 * 
 */
package com.abm.mainet.workManagement.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateReportService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.ui.model.ContractAgreementPrintModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/ContractAgreementPrint.html")
public class ContractAgreementPrintController extends AbstractFormController<ContractAgreementPrintModel> {

    @Autowired
    IContractAgreementService iContractAgreementService;

    @Autowired
    private TenderInitiationService tenderInitiationService;

    @Autowired
    WmsProjectMasterService projectMasterService;

    @Autowired
    private WorkEstimateReportService service;

    @Autowired
    private TbDepartmentService tbdepartmentservice;

    @Autowired
    private WorkDefinitionService workdefinitionservice;

    /**
     * Used to default Contract Agreement Print Summary page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<ContractAgreementSummaryDTO> summaryDTOList = new ArrayList<>();
        List<Long> deptIds = projectMasterService.getAllProjectAssociatedDeptList(orgId);
        try {

            for (Long deptId : deptIds) {
                List<ContractAgreementSummaryDTO> dtos = iContractAgreementService
                        .getContractAgreementSummaryData(orgId, null, null, deptId, null, null, null);
                if (!dtos.isEmpty()) {
                    for (ContractAgreementSummaryDTO dto : dtos) {
                        if (dto.getContLoaNo() != null && !dto.getContLoaNo().isEmpty()) {
                            summaryDTOList.add(dto);
                        }

                    }
                }
            }
            this.getModel().setContractSummaryDTOList(summaryDTOList);
            this.getModel().setCommonHelpDocs("ContractAgreementPrint.html");
            //#159581 The NIT (Notice Inviting Tender) option should be hidden in the Agreement Print page
           if(Utility.isEnvPrefixAvailable( UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA))
        	   this.getModel().setSudaEnv(MainetConstants.FlagY);
           else
        	   this.getModel().setSudaEnv(MainetConstants.FlagN);
            

        } catch (Exception e) {
            throw new FrameworkException(e);
        }
        return defaultResult();
    }

    /**
     * Used to get Contract Details By contId
     * 
     * @param contId
     * @return
     * @return ContractAgreementSummaryDTO
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GETCONTRACT_DETAILS, method = RequestMethod.POST)
    public String contractDetailsBycontId(@RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) Long contId) {
        ContractAgreementPrintModel model = this.getModel();
        for (ContractAgreementSummaryDTO contractAgreementSummaryDTO : model.getContractSummaryDTOList()) {
            if (contractAgreementSummaryDTO.getContId() == contId.longValue()) {
                model.setContractAgreementSummaryDTO(contractAgreementSummaryDTO);
                break;
            }
        }
        return model.getContractAgreementSummaryDTO().getContFromDate() + MainetConstants.operator.COMMA
                + model.getContractAgreementSummaryDTO().getContToDate() + MainetConstants.operator.COMMA
                + model.getContractAgreementSummaryDTO().getContp2Name();

    }

    /**
     * this method is used to Print Contract Agreement Summary Print
     * 
     * @param request
     * 
     */
    @RequestMapping(params = MainetConstants.WorksManagement.PRINT_CONTRACT_DET, method = RequestMethod.POST)
    public ModelAndView printContractAgreement(final HttpServletRequest request,
            @RequestParam(value = "", required = false) String tenderNo) {
        ContractAgreementPrintModel model = this.getModel();
        ContractAgreementSummaryDTO contractSummaryDTO = model.getContractAgreementSummaryDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setLangId(UserSession.getCurrent().getLanguageId());
        TenderWorkDto tenderWorkDto = tenderInitiationService
                .getTenderDetailsByLoaNumber(contractSummaryDTO.getContLoaNo(), orgId);
        if (tenderWorkDto != null) {
            iContractAgreementService.updateContractMapFlag(contractSummaryDTO.getContId(),
                    UserSession.getCurrent().getEmployee().getEmpId());
            tenderWorkDto.setTndDate((UtilityService.convertDateToDDMMYYYY(tenderWorkDto.getTenderDate())));
            model.setTenderWorkDto(tenderWorkDto);
            tenderInitiationService.updateContractId(contractSummaryDTO.getContId(), tenderWorkDto.getWorkId(),
                    UserSession.getCurrent().getEmployee().getEmpId());
            ApplicationContextProvider.getApplicationContext().getBean(WorkEstimateService.class).updateContractId(
                    tenderWorkDto.getWorkId(),
                    contractSummaryDTO.getContId(), UserSession.getCurrent().getEmployee().getEmpId());
            WorkDefinitionDto workdefinitiondto = workdefinitionservice.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
            Long worktype = workdefinitiondto.getWorkType();
            Department department = tbdepartmentservice.findDepartmentById(workdefinitiondto.getDeptId());
            Long deptId = department.getDpDeptid();
            model.setWorkMasterDtosList(service.findAbstractSheetReport(tenderWorkDto.getWorkId(), deptId, worktype, orgId));
            BigDecimal amount = new BigDecimal(0);
            for (WorkEstimateMasterDto dto : this.getModel().getWorkMasterDtosList()) {
                if (dto.getWorkEstimAmount() != null) {
                    amount = amount.add(dto.getWorkEstimAmount());
                } else {
                    dto.setWorkEstimAmount(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT));
                }

            }
            model.setTotalAmount((amount.setScale(2, BigDecimal.ROUND_UP).toString()));
        } else {
            this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("wms.IssueInContractPrint"));
            return defaultResult();
        }
        return new ModelAndView(MainetConstants.WorksManagement.CONTRACT_PRINT, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.PRINT_NOTICE, method = RequestMethod.POST)
    public ModelAndView printNoticeInvitingTender(HttpServletRequest httpServletRequest,
            @RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) Long contId) {

        Long workId = tenderInitiationService.findWorkByWorkId(contId).getWorkId();
        TenderWorkDto tenderDTO = tenderInitiationService.findNITAndPqDocFormDetailsByWorkId(workId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setPrintNoticeInvintingTender(tenderDTO);
        this.getModel().setMode("CAP");
        return new ModelAndView(MainetConstants.WorksManagement.NOTICE_INVITING_TENDER, MainetConstants.FORM_NAME,
                this.getModel());
    }
}
