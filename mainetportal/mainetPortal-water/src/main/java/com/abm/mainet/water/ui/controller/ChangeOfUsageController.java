package com.abm.mainet.water.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.ChangeOfUsageResponseDTO;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.service.IChangeOfUsageService;
import com.abm.mainet.water.ui.model.ChangeOfUsageModel;

/**
 * The Class ChangeOfUsageController.
 */
@Controller
@RequestMapping("/ChangeOfUsage.html")
public class ChangeOfUsageController extends AbstractFormController<ChangeOfUsageModel> {

    /** The service master. */
    @Autowired
    private transient IServiceMasterService serviceMaster;

    @Autowired
    private IChangeOfUsageService iChangeOfUsageService;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfUsageController.class);

    /**
     * Index.
     *
     * @param request the request
     * @return the model and view
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        try {

            getModel().initializeApplicantDetail();

            final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final PortalService portalService = serviceMaster.getPortalServiceMaster(MainetConstants.ServiceShortCode.WATER_CHANGE_USAGE, orgId);
            getModel().setServiceId(portalService.getServiceId());
            getModel().setDeptId(portalService.getPsmDpDeptid());
            getModel().setCommonHelpDocs("ChangeOfUsage.html");

        } catch (final Exception ex) {

            throw new FrameworkException(ex);
        }

        return defaultResult();
    }

    /**
     * Gets the connection record.
     *
     * @param request the request
     * @return the connection record
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "getConnectionInfo")
    public ModelAndView getConnectionRecord(final HttpServletRequest request) {
        bindModel(request);

        final ChangeOfUsageModel model = getModel();
        model.validateApplicantDetail();
        if (!model.hasValidationErrors()) {

            try {

                final ChangeOfUsageRequestDTO requestDTO = model.getRequestDTO();
                final ChangeOfUsageRequestDTO requestVo = requestDTO;
                requestVo.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                requestVo.setServiceId(model.getServiceId());
                requestVo.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
                requestVo.setLangId((long) UserSession.getCurrent().getLanguageId());
                requestVo.setConnectionNo(requestDTO.getConnectionNo());

                final ChangeOfUsageResponseDTO response = iChangeOfUsageService.fetchConnectionData(requestVo);
                if (response.getStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                    model.setResultFound(true);
                    model.setDualReturn(false);
                    final ChangeOfUsageDTO dto = getModel().getRequestDTO().getChangeOfUsage();
                    final CustomerInfoDTO customerInfoDTO = response.getCustomerInfoDTO();
                    dto.setOldTrmGroup1(customerInfoDTO.getTrmGroup1());
                    dto.setOldTrmGroup2(customerInfoDTO.getTrmGroup2());
                    dto.setOldTrmGroup3(customerInfoDTO.getTrmGroup3());
                    dto.setOldTrmGroup4(customerInfoDTO.getTrmGroup4());
                    dto.setOldTrmGroup5(customerInfoDTO.getTrmGroup5());
                    model.setCustomerInfoDTO(customerInfoDTO);
                    requestDTO.setChangeOfUsage(dto);
                    requestDTO.setConnectionSize(customerInfoDTO.getCsCcnsize());
                } else {
                    getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.discon.norecod",
                            new Object[] { requestDTO.getConnectionNo() }));
                }
            } catch (final IOException e) {
                LOGGER.error("Exception while fetching the old connection Record for change of Usage", e);
            }
        }
        return defaultMyResult();

    }

    /**
     * Do get applicable check list and charges.
     *
     * @param request the request
     * @return the model and view
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
    public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest request) {
        bindModel(request);
        final ChangeOfUsageModel model = getModel();
        model.validateApplicantDetail();
        model.validateChangeDetail();
        if (!model.hasValidationErrors()) {

            try {

                final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
                model.findApplicableCheckListAndCharges(model.getServiceId(), orgId);
                model.setEnableSubmit(true);
            } catch (final Exception ex) {
                throw new FrameworkException(ex);
            }
            model.setDualReturn(false);
            model.setEnableSubmit(true);
        }

        return defaultMyResult();
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appId") final long appId, final HttpServletRequest httpServletRequest) throws Exception {  
        getModel().bind(httpServletRequest);
        
        ChangeOfUsageModel model = this.getModel();
        try {
        	 final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
             ChangeOfUsageRequestDTO requestDto = new ChangeOfUsageRequestDTO();
             final ChangeOfUsageRequestDTO response = iChangeOfUsageService.getAppicationDetails(appId,orgId);
            
             if(response.getChangeOfUsage().getCisId() != 0) {
            	 model.setApplicantDetailDto(response.getApplicant());
                 model.setRequestDTO(response);
             }
             
        }
       catch(Exception exception) {
    	   throw new FrameworkException(exception);
       }
        return new ModelAndView("changeOfUasgeApplicationView", MainetConstants.FORM_NAME, getModel());
    }
}
