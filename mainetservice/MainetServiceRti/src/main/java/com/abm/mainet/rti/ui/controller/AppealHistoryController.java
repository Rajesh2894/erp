package com.abm.mainet.rti.ui.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.AppealHistoryModel;

@Controller
@RequestMapping("/AppealHistory.html")
public class AppealHistoryController extends AbstractFormController<AppealHistoryModel> {

    @Autowired
    IRtiApplicationDetailService rtiApplicationDetailService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        AppealHistoryModel model = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long lookup = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("FDE", "RAC", orgId);
        List<RtiApplicationFormDetailsReqDTO> rtiDetail = rtiApplicationDetailService.getForwardDeptList(lookup, orgId);
        model.setRtiList(rtiDetail);
        return defaultResult();

    }

    @RequestMapping(params = { "view" }, method = { RequestMethod.POST })
    public ModelAndView viewRtiForm(HttpServletRequest request,
            @RequestParam("applicationId") final Long applicationId,
            @RequestParam(value = "type", required = false) String type) {

        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().bind(request);
        final AppealHistoryModel model = this.getModel();
        Organisation org = UserSession.getCurrent().getOrganisation();
        RtiApplicationFormDetailsReqDTO rtiDTO = model.getRtiList().stream()
                .filter(dto -> applicationId.equals(dto.getApmApplicationId())).findAny().orElse(null);
        rtiDTO.setOrgId(org.getOrgid());
        if (type.equals("V")) {
            model.setViewEditFlag("V");
        } else {
            model.setViewEditFlag("E");
        }
        model.setReqDTO(rtiDTO);
        model.getReqDTO().setLocationName(
                rtiApplicationDetailService.getlocationNameById(Long.valueOf(model.getReqDTO().getRtiLocationId()),
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("AppealHistoryForm", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "getLocationByDepartment", method = RequestMethod.POST)
    @ResponseBody
    public Set<LookUp> getLocationByDepartment(
            @RequestParam(value = "deptId", required = true) Long deptId) {
        final RtiApplicationFormDetailsReqDTO dto = this.getModel().getReqDTO();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        dto.setOrgId(orgId);
        dto.setDeptId(deptId);
        Set<LookUp> locList = rtiApplicationDetailService.getDeptLocation(orgId, deptId);
        this.getModel().setLocations(locList);
        return locList;
    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveRtiApplication(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        AppealHistoryModel model = this.getModel();
        model.bind(httpServletRequest);
        ModelAndView mv = null;
        if (model.validateInputs()) {
            if (model.saveForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

            } else
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
        mv = new ModelAndView("AppealHistoryFormValdin", MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }

    @RequestMapping(params = { "back" }, method = { RequestMethod.POST })
    public ModelAndView backToSearch(HttpServletRequest request) {
        AppealHistoryModel model = this.getModel();
        model.bind(request);
        ModelAndView mv = new ModelAndView("AppealHistoryValdin", MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }
}
