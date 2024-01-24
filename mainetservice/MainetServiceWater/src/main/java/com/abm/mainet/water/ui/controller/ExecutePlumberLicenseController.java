package com.abm.mainet.water.ui.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.ui.model.ExecutePlumberLicenseModel;

/**
 * @author Arun.Chavda
 *
 */

@Controller
@RequestMapping("/ExecutePlumberLicense.html")
public class ExecutePlumberLicenseController extends AbstractFormController<ExecutePlumberLicenseModel> {

    @Autowired
    ICFCApplicationMasterService cfcService;

    @Resource
    private ServiceMasterService serviceMasterService;

    private static Logger log = Logger.getLogger(ExecutePlumberLicenseController.class);

    @RequestMapping(method = RequestMethod.POST, params = "showDetails")
    public ModelAndView showReconnectionExecutionDetails(final HttpServletRequest httpServletRequest,
            @RequestParam("appNo") final Long applicationId, @RequestParam("actualTaskId") final long actualTaskId) {
    	
        bindModel(httpServletRequest);
        final ExecutePlumberLicenseModel model = getModel();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setOrgId(orgId);
        model.setTaskId(actualTaskId);
        final TbCfcApplicationMstEntity cfcApplicationMst = cfcService.getCFCApplicationByApplicationId(applicationId,
                orgId);
        model.setApplicationId(cfcApplicationMst.getApmApplicationId());
        model.setApplicationDate(cfcApplicationMst.getApmApplicationDate());
        final Employee employee = UserSession.getCurrent().getEmployee();
        model.setApprovedBy(employee.getEmpname());
        model.setEmpId(employee.getEmpId());
        model.setApprovalDate(new Date());
        final ServiceMaster serviceMaster = serviceMasterService
                .getServiceMasterByShortCode(MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE, orgId);
        model.setServiceId(serviceMaster.getSmServiceId());
        model.setServiceName(serviceMaster.getSmServiceName());
        if(serviceMaster.getTbDepartment()!=null)
        	model.setDeptName(serviceMaster.getTbDepartment().getDpDeptdesc());

        if (cfcApplicationMst.getApmMname() != null) {
            model.setApplicantFullName(cfcApplicationMst.getApmFname() + MainetConstants.WHITE_SPACE
                    + cfcApplicationMst.getApmMname() + MainetConstants.WHITE_SPACE + cfcApplicationMst.getApmLname());
        } else {
            model.setApplicantFullName(
                    cfcApplicationMst.getApmFname() + MainetConstants.WHITE_SPACE + cfcApplicationMst.getApmLname());
        }
        return new ModelAndView("ExecutePlumberLicense", MainetConstants.CommonConstants.COMMAND, getModel());

    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ExecutePlumberLicenseModel model = getModel();

        try {
            if (model.saveForm()) {
                return jsonResult(JsonViewObject.successResult("Application Updated Successfully"));
            }
        } catch (final Exception ex) {
            log.error("Error Occured During Save Data", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "printPlumberLicense", method = RequestMethod.POST)
    public ModelAndView printPlumberLicense(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final ExecutePlumberLicenseModel model = getModel();

        ModelAndView mv = null;
        mv = new ModelAndView("PlumberLicenseLetterPrint", MainetConstants.FORM_NAME, model);
       // mv = new ModelAndView("PlumberLicenseLetter", MainetConstants.FORM_NAME, model);
       
       
        mv.addObject(MainetConstants.CommonConstants.COMMAND, model);

        return mv;
    }

}
