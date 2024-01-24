/**
 * 
 */
package com.abm.mainet.swm.ui.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IWasteCollectorService;
import com.abm.mainet.swm.ui.model.WasteCollectorModel;
import com.ibm.icu.math.BigDecimal;

/**
 * @author sarojkumar.yadav
 *
 */
@Controller
@RequestMapping("WasteCollector.html")
public class WasteCollectorController extends AbstractFormController<WasteCollectorModel> {

    private static final Logger logger = Logger.getLogger(WasteCollectorController.class);
    @Autowired
    private IFileUploadService fileUpload;
    @Resource
    private ILocationMasService iLocationMasService;
    @Autowired
    private IWasteCollectorService iWasteCollectorService;
    @Autowired
    private ServiceMasterService serviceMaster;
    @Autowired
    private IVehicleMasterService vehicleMasterService;
    @Autowired
    private IMRFMasterService mRFMasterService;
    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;
    @Resource
    private IFileUploadService fileUploadService;

    @RequestMapping(method = RequestMethod.POST, params = "showDetails")
    public ModelAndView viewConNDemoliationApplication(final HttpServletRequest httpServletRequest,
            @RequestParam("appNo") Long applicationId, @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId, @RequestParam(value = "workflowId", required = false) final Long workflowId,
            @RequestParam(value = "taskName", required = false) final String taskName) throws ParseException {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().bind(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setTaskId(actualTaskId);
        this.getModel().getWorkflowActionDto().setReferenceId(applicationId.toString());
        this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
        this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
        Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class).findById(workflowId).getCurrentOrgId();
        this.getModel().setCollectorReqDTO(iWasteCollectorService.getApplicationDetailsByApplicationId(applicationId, parentOrgId));

        List<CFCAttachment> docsList = iChecklistVerificationService.getAttachDocumentByDocumentStatus(applicationId, "", parentOrgId);
        if (!docsList.isEmpty())
            this.getModel().getCollectorReqDTO().setDocsList(docsList);
        this.getModel().setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        this.getModel().setMrfList(mRFMasterService.serchMrfCenter(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));

        return new ModelAndView("WasteCollectorFormView", MainetConstants.CommonConstants.COMMAND, getModel());

    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("WasteCollector.html");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final WasteCollectorModel wasteCollectorModel = this.getModel();
        wasteCollectorModel.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        final ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
        getModel().setService(service);
        getModel().setDeptId(getModel().getService().getTbDepartment().getDpDeptid());
        return index();
    }

    @RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
    public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        bindModel(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ModelAndView modelAndView = null;
        try {
            if (getModel().validateInputs()) {
                findApplicableCheckListAndCharges(orgId, httpServletRequest);
                getModel().setEnableSubmit(true);
                getModel().setEnableCheckList(false);
            }

            if (getModel().getBindingResult() != null) {
                modelAndView = new ModelAndView("WasteCollectorForm", MainetConstants.CommonConstants.COMMAND, getModel());
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            modelAndView = new ModelAndView("WasteCollectorForm", MainetConstants.CommonConstants.COMMAND, getModel());
            getModel().getBindingResult().addError(new ObjectError("", ex.getMessage()));
            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
                    getModel().getBindingResult());
        }
        return modelAndView;
    }

    /**
     *
     * @param serviceId
     * @param orgId
     */
    @SuppressWarnings("unchecked")
    private void findApplicableCheckListAndCharges(final long orgId, final HttpServletRequest httpServletRequest) {

        // [START] BRMS call initialize model
        getModel().bind(httpServletRequest);
        final WasteCollectorModel collectorModel = getModel();
        WSRequestDTO initReqDTO = new WSRequestDTO();
        initReqDTO.setModelName(MainetConstants.SolidWasteManagement.CHECK_LIST_MODEL);
        final WSResponseDTO response = iWasteCollectorService.getChecklist(initReqDTO, orgId);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            List<DocumentDetailsVO> checklistData = (List<DocumentDetailsVO>) response.getResponseObj();
            getModel().setAttachments(checklistData);
        } else {
            throw new FrameworkException("Problem while fetching checklist data for SWM rate .");
        }
        initReqDTO = new WSRequestDTO();
        initReqDTO.setModelName(MainetConstants.SolidWasteManagement.SWMRATEMASTER_MODEL);
        Long vehicleId = collectorModel.getCollectorReqDTO().getCollectorDTO().getVehicleType();
        try {
            List<ChargeDetailDTO> chargeDetailList = iWasteCollectorService.getApplicableCharges(initReqDTO, vehicleId, orgId);
            collectorModel.setChargesInfo(chargeDetailList);
            double amountToPay = chargesToPay(collectorModel.getChargesInfo(),
                    collectorModel.getCollectorReqDTO().getCollectorDTO());
            if (!this.getModel().getCollectorReqDTO().getCollectorDTO().getComplainNo().isEmpty()) {
                amountToPay = amountToPay * 1.2;
            }
            collectorModel.setAmountToPay(amountToPay);
            collectorModel.setIsFree(MainetConstants.Common_Constant.NO);
            collectorModel.getOfflineDTO().setAmountToShow(collectorModel.getAmountToPay());
            if (collectorModel.getAmountToPay() == 0.0d) {
                throw new FrameworkException("Service charge amountToPay cannot be " + collectorModel.getAmountToPay()
                        + " if service configured as Chageable");
            }
        } catch (FrameworkException exp) {
            collectorModel.setIsFree(MainetConstants.Common_Constant.FREE);
        }
    }

    /**
     * method to display service charge details
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "showSWMChargeDetails")
    public ModelAndView showRnLChargesDetails(final HttpServletRequest httpServletRequest, final Model model) {
        WasteCollectorModel wasteCollectorModel = this.getModel();
        return new ModelAndView("ChargesDetailSWM", MainetConstants.CommonConstants.COMMAND, wasteCollectorModel);
    }

    private double chargesToPay(final List<ChargeDetailDTO> charges, final WasteCollectorDTO dto) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        BigDecimal amount = new BigDecimal(amountSum);
        BigDecimal trip = new BigDecimal(dto.getNoTrip());
        double totalAmount = (amount.multiply(trip)).doubleValue();
        return totalAmount;
    }

    /**
     * serchVehicleNo
     * @param vehicleTypeId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
    public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
            final HttpServletRequest httpServletRequest) {
        List<VehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> data = new HashMap<>();
        if (result != null && !result.isEmpty()) {
            result.forEach(vdata -> {
                data.put(vdata.getVeId(), vdata.getVeNo());
            });

        }
        return data;
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "saveFinalDecision")
    public @ResponseBody String saveFinalDecision(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, ModelMap modelMap) {

        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        getModel().bind(httpServletRequest);
        WasteCollectorModel model = this.getModel();
        String decision = this.getModel().getWorkflowActionDto().getDecision();

        Long applicationId = this.getModel().getCollectorReqDTO().getCollectorDTO().getApplicationId();

        WorkflowTaskAction departmentAction = model.getWorkflowActionDto();
        departmentAction.setTaskId(model.getTaskId());
        departmentAction.setApplicationId(applicationId);
        departmentAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        departmentAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        departmentAction.setCreatedDate(new Date());
        departmentAction.setDateOfAction(new Date());
        departmentAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        departmentAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

        CollectorReqDTO collectorReqDTO = iWasteCollectorService.getApplicationDetailsByApplicationId(applicationId, orgId);

        model.setAttachments(fileUploadService.setFileUploadMethod(model.getCollectorReqDTO().getDocumentList()));
        collectorReqDTO.getApplicantDetailDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        collectorReqDTO.getCollectorDTO().setVehicleNo(model.getCollectorReqDTO().getCollectorDTO().getVehicleNo());
        collectorReqDTO.getCollectorDTO().setMrfId(model.getCollectorReqDTO().getCollectorDTO().getMrfId());
        collectorReqDTO.getCollectorDTO().setEmpName(model.getCollectorReqDTO().getCollectorDTO().getEmpName());
        collectorReqDTO.getCollectorDTO().setPickUpDate(model.getCollectorReqDTO().getCollectorDTO().getPickUpDate());
        collectorReqDTO.getCollectorDTO().setLgIpMacUpd(Utility.getMacAddress());
        model.getCollectorReqDTO().setPayAmount(collectorReqDTO.getCollectorDTO().getPayAmount());

        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationId, collectorReqDTO.getApplicantDetailDto().getOrgId());
        if (!attachmentId.isEmpty())
            departmentAction.setAttachementId(attachmentId);

        String response = iWasteCollectorService.updateProcess(departmentAction, model.getTaskId(), "WasteCollector.html",
                MainetConstants.FlagU, MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE, collectorReqDTO, decision);
        return response;
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "WasteApproval")
    public ModelAndView approvalLetter(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        Long applicationId = this.getModel().getCollectorReqDTO().getCollectorDTO().getApplicationId();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        CollectorReqDTO collectorReqDTO = iWasteCollectorService.getApplicationDetailsByApplicationId(applicationId, orgId);
        this.getModel().setCollectorReqDTO(collectorReqDTO);
        if (collectorReqDTO.getCollectorDTO().getVehicleNo() != null) {
            String veNumber = vehicleMasterService.getVehicleByVehicleId(collectorReqDTO.getCollectorDTO().getVehicleNo()).getVeNo();
            this.getModel().setVehicleNumber(veNumber);
        }
        return new ModelAndView("WasteApproval", MainetConstants.FORM_NAME, this.getModel());
    }
}
