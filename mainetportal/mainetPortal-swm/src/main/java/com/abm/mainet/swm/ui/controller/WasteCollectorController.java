/**
 * 
 */
package com.abm.mainet.swm.ui.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.LocationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.CollectorResDTO;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.service.IWasteCollectorService;
import com.abm.mainet.swm.ui.model.WasteCollectorModel;

/**
 * @author sarojkumar.yadav
 *
 */
@Controller
@RequestMapping("WasteCollector.html")
public class WasteCollectorController extends AbstractFormController<WasteCollectorModel> {

    private static final Logger logger = Logger.getLogger(WasteCollectorController.class);

    @Autowired
    private IPortalServiceMasterService iPortalService;
    @Autowired
    private IWasteCollectorService iWasteCollectorService;
    @Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        bindModel(httpServletRequest);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final WasteCollectorModel wasteCollectorModel = this.getModel();
        final Long serviceId = iPortalService
                .getServiceId(MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE, orgId);
        final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
        wasteCollectorModel
                .setLocList(locationService.getLocationByOrgId(orgId, UserSession.getCurrent().getLanguageId()));
        wasteCollectorModel.setServiceId(serviceId);
        wasteCollectorModel.setDeptId(deptId);
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
                modelAndView = new ModelAndView("WasteCollectorForm", MainetConstants.FORM_NAME, getModel());
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                        getModel().getBindingResult());
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            modelAndView = new ModelAndView("WasteCollectorForm", MainetConstants.FORM_NAME, getModel());
            getModel().getBindingResult().addError(new ObjectError("", ex.getMessage()));
            modelAndView.addObject(
                    BindingResult.MODEL_KEY_PREFIX
                            + ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
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
            final List<?> docs = JersyCall.castResponse(response, DocumentDetailsVO.class);
            List<DocumentDetailsVO> checklist = (List<DocumentDetailsVO>) docs;
            getModel().setCheckList(checklist);
        } else {
            throw new FrameworkException("Problem while fetching checklist data for SWM rate .");
        }
        initReqDTO = new WSRequestDTO();
        initReqDTO.setModelName(MainetConstants.SolidWasteManagement.SWMRATEMASTER_MODEL);
        Long vehicleId = collectorModel.getCollectorReqDTO().getCollectorDTO().getVehicleType();
        try {
            List<ChargeDetailDTO> chargeDetailList = iWasteCollectorService.getApplicableCharges(initReqDTO, vehicleId, orgId);
            if (chargeDetailList != null && !chargeDetailList.isEmpty()) {
                collectorModel.setChargesInfo(chargeDetailList);
                collectorModel.setAmountToPay(chargesToPay(collectorModel.getChargesInfo(),
                        collectorModel.getCollectorReqDTO().getCollectorDTO()));
                collectorModel.setIsFree(MainetConstants.Common_Constant.NO);
                collectorModel.getOfflineDTO().setAmountToShow(collectorModel.getAmountToPay());
                if (collectorModel.getAmountToPay() == 0.0d) {
                    throw new FrameworkException("Service charge amountToPay cannot be "
                            + collectorModel.getAmountToPay() + " if service configured as Chargeable");
                }
            } else {
                throw new FrameworkException("Problem while fetching application charges for SWM rate .");
            }
        } catch (FrameworkException exp) {
            collectorModel.setIsFree(MainetConstants.Common_Constant.FREE);
        }
    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveWasteCollection(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        final WasteCollectorModel model = getModel();
        ModelAndView mv = null;
        List<DocumentDetailsVO> docs = getModel().getCheckList();
        docs = fileUpload.convertFileToByteString(docs);
        getModel().getCollectorReqDTO().setDocumentList(docs);
        if (model.validateInputs()) {
            final WasteCollectorDTO collectorDTO = model.getCollectorReqDTO().getCollectorDTO();
            final CollectorReqDTO collectorReqDTO = model.getCollectorReqDTO();
            collectorDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            collectorDTO.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
            collectorDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            collectorDTO.setCreatedDate(new Date());
            collectorDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            collectorDTO.setCollectionStatus(MainetConstants.PAYMENT_STATUS.PEND_ING);
            collectorDTO.setPayFlag(false);
            collectorReqDTO.setPayAmount(model.getAmountToPay());
            collectorReqDTO.setServiceId(model.getServiceId());
            collectorReqDTO.setDeptId(model.getDeptId());
            collectorReqDTO.setCollectorDTO(collectorDTO);
            model.setCollectorReqDTO(collectorReqDTO);

            try {
                final CollectorResDTO responseDTO = iWasteCollectorService.saveWasteCollector(getModel().getCollectorReqDTO());
                model.setApplicationNo(responseDTO.getApplicationNo());
                /*
                 * final ApplicationPortalMaster applicationMaster = saveApplcationMaster(model.getServiceId(),
                 * responseDTO.getApplicationNo()); iPortalServiceMasterService.saveApplicationMaster(applicationMaster,
                 * model.getCharges(), FileUploadUtility.getCurrent().getFileMap().entrySet().size());
                 */
                if (model.saveForm()) {
                    mv = jsonResult(JsonViewObject.successResult("Your Application No. is :" + responseDTO.getApplicationNo() + " , Proceed to continue for Payment."));
                }
            } catch (final Exception e) {
                logger.error("Error while reading value from response: " + e.getMessage(), e);
                mv = new ModelAndView("defaultExceptionView");
            }

        } else {
            mv = new ModelAndView("WasteCollectorValidn", MainetConstants.FORM_NAME, model);
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model);
        }
        return mv;
    }

    @SuppressWarnings("unused")
    private ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo) throws Exception {
        final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
        applicationMaster.setPamApplicationId(applicationNo);
        applicationMaster.setSmServiceId(serviceId);
        applicationMaster.setPamApplicationDate(new Date());
        applicationMaster.updateAuditFields();
        return applicationMaster;
    }

    /**
     * method to display service charge details
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "showSWMChargeDetails")
    public ModelAndView showSWMChargeDetails(final HttpServletRequest httpServletRequest, final Model model) {
        WasteCollectorModel wasteCollectorModel = this.getModel();
        return new ModelAndView("ChargesDetailSWM", MainetConstants.FORM_NAME, wasteCollectorModel);
    }

    private double chargesToPay(final List<ChargeDetailDTO> charges, final WasteCollectorDTO dto) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        BigDecimal amount = BigDecimal.valueOf(amountSum);
        BigDecimal trip = new BigDecimal(dto.getNoTrip());
        return (amount.multiply(trip)).doubleValue();
    }
}
