package com.abm.mainet.water.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.service.IChangeOfOwnershipService;
import com.abm.mainet.water.ui.model.ChangeOfOwnershipModel;
import com.abm.mainet.water.ui.model.ChangeOfUsageModel;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/ChangeOfOwnership.html")
public class ChangeOfOwnershipController extends AbstractFormController<ChangeOfOwnershipModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfOwnershipController.class);

    @Autowired
    private IChangeOfOwnershipService iChangeOfOwnershipService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        try {
            getModel().initializeApplicantDetail();
            getModel().setCommonHelpDocs("ChangeOfOwnership.html");
        } catch (final Exception ex) {
            LOGGER.error("Error Occurred while rendering Form:", ex);
            return defaultExceptionView();
        }

        return defaultResult();
    }

    /**
     * 
     * @param httpServletRequest
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = { RequestMethod.POST }, params = "getConnectionRecords")
    public ModelAndView getConnectionRecord(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ChangeOfOwnershipModel model = getModel();
        ChangeOfOwnerResponseDTO outPutObject = null;
        try {
            model.setCheckList(null);
            model.setChargesInfo(null);
            model.setAmountToPay(0d);
            model.setIsFree(null);
            getModel().setEnableSubmit(false);
            final ChangeOfOwnerRequestDTO requestVo = model.getChangeOwnerMaster();
            requestVo.setOrgnId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestVo.setServiceId(getServiceId(MainetConstants.ServiceShortCode.WATER_CHANGE_OWNER));
            getModel().setServiceId(requestVo.getServiceId());
            requestVo.setUserEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            requestVo.setLangId(UserSession.getCurrent().getLanguageId());
            requestVo.setConnectionNo(model.getChangeOwnerResponse().getConnectionNumber());

            outPutObject = iChangeOfOwnershipService.fetchOldConnectionData(requestVo);
            getModel().setChangeOwnerResponse(outPutObject);
            if (outPutObject != null) {
                if (MainetConstants.YES.equals(outPutObject.getCanApplyOrNot())) {
                    getModel().getChangeOwnerResponse().setErrorFlag(MainetConstants.NO);
                    getModel().setValidConnectionNo(outPutObject.getConnectionNumber());
                    getModel().getChangeOwnerMaster().setCsIdn(outPutObject.getConId());
                    getModel().setChangeOwnerResponse(outPutObject);
                    if (outPutObject.getCooOtitle() != null) {
                        outPutObject.setOldOwnerFullName(
                                getOldOwnerFullName(outPutObject.getOldOwnerFullName(), outPutObject.getCooOtitle()));
                    }
                } else {
                    getModel().addValidationError(ApplicationSession.getInstance().getMessage("changeofowner.searchCriteria")+ MainetConstants.WHITE_SPACE + requestVo.getConnectionNo()
							+ MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("changeofowner.searchCriteria.append"));
                    getModel().setChangeOwnerResponse(outPutObject);
                    getModel().getChangeOwnerResponse().setErrorFlag(MainetConstants.YES);

                }
                if(outPutObject.getErrMsg()!=null)
					getModel().addValidationError(outPutObject.getErrMsg());
            }
        } catch (final Exception e) {
            LOGGER.error("Exception while fetching the old connection Record for change of ownership", e);
            return defaultExceptionFormView();
        }
        final ModelAndView modelAndView = new ModelAndView("OldOwnerDetails", MainetConstants.REQUIRED_PG_PARAM.COMMAND, getModel());
        if (getModel().getBindingResult() != null) {
            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX
                    + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }

        return modelAndView;

    }

    @RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
    public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ModelAndView modelAndView = null;
        try {

            if (!getModel().validateFormData(getModel().getChangeOwnerMaster())) {
                getModel().findApplicableCheckListAndCharges(
                		MainetConstants.ServiceShortCode.WATER_CHANGE_OWNER, orgId);
                getModel().setEnableSubmit(true);

            }
            modelAndView = new ModelAndView("OldOwnerDetails", MainetConstants.REQUIRED_PG_PARAM.COMMAND, getModel());
            if (getModel().getBindingResult() != null) {
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX
                        + MainetConstants.FORM_NAME, getModel().getBindingResult());
            }
        } catch (final Exception ex) {
            modelAndView = defaultExceptionFormView();
            LOGGER.error("Problem while finding checklist and charges:", ex);
        }

        return modelAndView;
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "getSelectedData")
    public @ResponseBody ChangeOfOwnerResponseDTO getSelectedData(final HttpServletRequest httpServletRequest,
            @RequestParam("id") final int index) {
        bindModel(httpServletRequest);
        final ChangeOfOwnershipModel model = getModel();
        ChangeOfOwnerResponseDTO data = null;
        final List<ChangeOfOwnerResponseDTO> response = model.getChangeOwnerMaster().getResponseDto();
        if ((model.getChangeOwnerMaster() != null) && (response != null) && !response.isEmpty()) {
            data = response.get(index);

            model.getChangeOwnerMaster().setCooOtitle(data.getCooOtitle());
            model.getChangeOwnerMaster().setCooOname(data.getCooOname());
            model.getChangeOwnerMaster().setCooOomname(data.getCooOomname());
            model.getChangeOwnerMaster().setCooOolname(data.getCooOolname());
            model.getChangeOwnerMaster().setCooUidNo(data.getCooUidNo());
            model.getChangeOwnerMaster().setCsIdn(data.getConId());

        }
        return data;

    }

    private String getOldOwnerFullName(final String name, final long title) {

        return getModel().titleDesc(title) + MainetConstants.WHITE_SPACE + name;
    }

    
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appId") final long appId, final HttpServletRequest httpServletRequest) throws Exception {  
    	 getModel().bind(httpServletRequest);
         
    	 ChangeOfOwnershipModel model = this.getModel();
         try {
         	 final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
         	ChangeOfOwnerRequestDTO requestDto = new ChangeOfOwnerRequestDTO();
         	
         	requestDto = iChangeOfOwnershipService.getAppicationDetails(appId,orgId );
             
             if(requestDto!=null) {
            	 model.setChangeOwnerMaster(requestDto);
            	 model.setApplicantDetailDto(requestDto.getApplicant());
            	 model.setAdditionalOwners(requestDto.getAdditionalOwners());
            	 model.setChangeOwnerResponse(requestDto.getOldOwnerInfo());
            	 
             }
             
         }
        catch(Exception exception) {
     	   throw new FrameworkException(exception);
        }
         return new ModelAndView("changeOwnershipViewApplication", MainetConstants.FORM_NAME, getModel());
    }
        
}
