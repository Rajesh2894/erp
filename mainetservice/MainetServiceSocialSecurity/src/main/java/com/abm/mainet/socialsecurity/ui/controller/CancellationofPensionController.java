/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.service.CancellationOfPensionService;
import com.abm.mainet.socialsecurity.ui.dto.CancelPensionDto;
import com.abm.mainet.socialsecurity.ui.model.CancelPensionModel;

/**
 * @author priti.singh
 *
 */
@Controller
@RequestMapping("/CancellationofPension.html")
public class CancellationofPensionController extends AbstractFormController<CancelPensionModel> {

    @Autowired
    private CancellationOfPensionService cancellationOfPensionService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        List<CancelPensionDto> getAllData = cancellationOfPensionService
                .getAllData(UserSession.getCurrent().getOrganisation().getOrgid());
        if (CollectionUtils.isNotEmpty(getAllData)) {
            getAllData.forEach(singleData -> {
                StringBuilder benefnoNname = new StringBuilder();
                if (StringUtils.isNotBlank(singleData.getNameofApplicant())) {
                    benefnoNname.append(singleData.getNameofApplicant());
                }

                if (StringUtils.isNotBlank(singleData.getBeneficiaryno())) {
                    benefnoNname.append(" - " + singleData.getBeneficiaryno());
                }

                singleData.setBenefnoNname(benefnoNname.toString());

            });
        }
        this.getModel().setCancelPensionDtoList(getAllData);

        return index();

    }

    // save cancellation of pension
    @RequestMapping(params = "saveCancelPension", method = RequestMethod.POST)
    public ModelAndView saveCancelPension(final Model model, final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;
        getModel().bind(httpServletRequest);
        CancelPensionModel pensionmodel = this.getModel();
        if (pensionmodel.saveForm()) {
            respObj = JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("social.sec.save.success"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("social.sec.notsave.success"));
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

    }

    // get data from dropdown of name and beneficiary number
    @RequestMapping(params = "getPensionDetails", method = RequestMethod.POST)
    public ModelAndView getPensionDetails(final HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        CancelPensionModel model = this.getModel();
        CancelPensionDto dto = model.getCancelPensionDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        dto.setOrgId(orgId);
        SocialSecurityApplicationForm entity = cancellationOfPensionService.findPensionDetails(dto);
        ServiceMaster service1 = serviceMaster.getServiceMaster(entity.getSelectSchemeName(), orgId);
        if (entity != null) {
            /* dto.setApplicationId(Long.valueOf(entity.getApmApplicationId())); */
            dto.setLastDateofLifeCerti(entity.getLastDateofLifeCerti());
            dto.setSelectSchemeNamedesc(service1.getSmServiceName());
            dto.setMobNum(entity.getMobNum());

        } else {
            model.addValidationError(ApplicationSession.getInstance().getMessage("social.sec.dropdown"));
        }
        return defaultMyResult();
    }
}
