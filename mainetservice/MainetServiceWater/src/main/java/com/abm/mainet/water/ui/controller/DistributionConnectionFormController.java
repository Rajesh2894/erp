package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.Prefix;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.DistributionConnectionFormModel;

/**
 * @author deepika.pimpale
 *
 */
@Controller
@RequestMapping("/DistributionConnectionForm.html")
public class DistributionConnectionFormController extends AbstractFormController<DistributionConnectionFormModel> {

    @Autowired
    NewWaterConnectionService waterService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        final DistributionConnectionFormModel model = getModel();
        final ScrutinyLableValueDTO lableDTO = model.getLableValueDTO();
        final UserSession session = UserSession.getCurrent();
        final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
        final Long serviceId = Long.valueOf(httpServletRequest.getParameter("serviceId"));
        final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
        final String lableValue = httpServletRequest.getParameter("labelVal");
        final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
        lableDTO.setApplicationId(appId);
        model.setServiceId(serviceId);
        lableDTO.setUserId(session.getEmployee().getEmpId());
        lableDTO.setOrgId((session.getOrganisation().getOrgid()));
        lableDTO.setLangId((long) session.getLanguageId());
        lableDTO.setLableId(lableId);
        lableDTO.setLableValue(lableValue);
        lableDTO.setLevel(level);
        model.setConnectionDetailsInfo(appId, serviceId, session.getOrganisation().getOrgid());
        
        return super.index();

    }

    @RequestMapping(params = "CalculateCCNSize", method = RequestMethod.POST)
    public ModelAndView calculateCCNSize(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        final DistributionConnectionFormModel model = getModel();
        ModelAndView mv = null;
        if (model.validateFieldCalculation(model)) {
            final Double quantityReqd = model.getcalculateQuantityRequired(model.getCsmrInfo());
            final Double diameter = model.calculateDiameter(model.getCsmrInfo(), quantityReqd);
            final Double ccnSize = waterService.getDiameterSlab(diameter, UserSession.getCurrent().getOrganisation());
            model.getCsmrInfo().setCsCcnsize(ccnSize.longValue());
        }

        mv = new ModelAndView("DistributionConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }

    @Override
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final DistributionConnectionFormModel model = getModel();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
    		if(model.getCsmrInfo().getCsCcnsize() == null) {
    			if(model.getCsmrInfo().getRecommendedCcnSize() != null && !"".equals(model.getCsmrInfo().getRecommendedCcnSize()))
    				model.getCsmrInfo().setCsCcnsize(Long.valueOf(model.getCsmrInfo().getRecommendedCcnSize()));
    		}
    	}
        if (model.validateInputs()) {
            if (model.saveForm()) {

            }
        }
        return defaultMyResult();
    }
    
    @RequestMapping(params = "calculateCCNSizeAndDischargeRate", method = RequestMethod.POST)
    public @ResponseBody String calculateCCNSizeAndDischargeRate(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        final DistributionConnectionFormModel model = getModel();
        ModelAndView mv = null;
        LookUp lookUp = null;
        Double[] rate = model.calculateDischargeRateAndCcnSize(model.getCsmrInfo());

        if(rate[0] != null && rate[1] != null) {
        	model.getCsmrInfo().getDistribution().setRcDistccndif(rate[0]);
        	String cpdValue = String.valueOf(rate[1]);
        	int langId = model.getCfcAddressEntity().getLangId().intValue();
        	lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(cpdValue, PrefixConstants.WATERMODULEPREFIX.CSZ, langId);
        	model.getCsmrInfo().setCsCcnsize(lookUp.getLookUpId());
        }
        
        StringBuilder result = new StringBuilder();
        if(rate[0] != null) {
        	result.append(rate[0]);
        }
        result.append(MainetConstants.operator.COMMA);
        
        if(rate[1] != null && lookUp != null) {
        	result.append(rate[1]);
        	result.append(MainetConstants.operator.COMMA);
        	result.append(lookUp.getLookUpId());
        }
       
        return result.toString();
    }

}
