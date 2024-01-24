package com.abm.mainet.quartz.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.quartz.service.IQuartzSchedulerMasterService;
import com.abm.mainet.quartz.ui.model.QuartzSchedulerMasterModel;

/**
 *
 * @author Vivek.Kumar
 * @since 04-May-2015
 */
@Controller
@RequestMapping("/QuartzSchedulerMaster.html")
public class QuartzSchedulerMasterController extends AbstractEntryFormController<QuartzSchedulerMasterModel> {

    @Autowired
    private IQuartzSchedulerMasterService iQuartzSchedulerMasterService;

    private static Logger logger = Logger.getLogger(QuartzSchedulerMasterController.class);

    final static String DEFAULT_EXCEPTION_VIEW = "defaultExceptionFormView";

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanUp(httpServletRequest);
        ModelAndView modelAndView = null;
        this.getModel().setCommonHelpDocs("QuartzSchedulerMaster.html");
        try {
            getModel().initializeMasterForm();
            modelAndView = new ModelAndView("BatchJobsMasterGrid", MainetConstants.FORM_NAME, getModel());
        } catch (final Exception ex) {

            modelAndView = new ModelAndView(DEFAULT_EXCEPTION_VIEW,
                    MainetConstants.FORM_NAME, getModel());
            logger.error(MainetConstants.EXCEPTION_OCCURED, ex);
        }

        return modelAndView;
    }

    @RequestMapping(params = "jobName", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> findJobsByDepartment(
            final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        List<LookUp> lookUps = null;
        try {
            lookUps = iQuartzSchedulerMasterService.findJobNamesByDepartment(getModel().getDepartmentForQuartz());
            getModel().setAvailableJobsForDept(lookUps);
        } catch (final Exception ex) {
            logger.error("Error Occurred while finding Job:", ex);
        }

        return lookUps;
    }

    @RequestMapping(params = "availableJobs", method = RequestMethod.GET)
    public @ResponseBody List<LookUp> getAvailableJobsForDept(final HttpServletRequest httpServletRequest) {

        return getModel().getAvailableJobsForDept();
    }

    @RequestMapping(params = "batchJobList", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<QuartzSchedulerMaster> showBatchJobList(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {

        return getModel().paginate(httpServletRequest, page, rows,

                getModel().getFinalList());
    }

    @Override
    public ModelAndView addForm(final HttpServletRequest httpServletRequest) {
        if (getModel().getDepartmentForQuartz() != null) {
            getModel().setDepartmentForQuartz(null);
        }
        return defaultResult();
    }

    @Override
    public ModelAndView editForm(final long rowId,
            final HttpServletRequest httpServletRequest) {

        getModel().editForm(rowId);

        getModel().setEditMode(true);

        return new ModelAndView("BatchJobsMasterEdit", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "searchJobs", method = RequestMethod.POST)
    public @ResponseBody String searchJobsByDepartment(final HttpServletRequest httpServletRequest,
            @RequestParam("deptCode") final String deptCode) {

        String isJobsAvailable = MainetConstants.Common_Constant.NO;
        getModel().setDepartmentForQuartz(deptCode);
        try {
            final List<QuartzSchedulerMaster> finalList = iQuartzSchedulerMasterService.queryListOfJobs(getModel(),
                    UserSession.getCurrent().getOrganisation());

            getModel().setFinalList(finalList);

            if ((finalList != null) && !finalList.isEmpty()) {
                isJobsAvailable = MainetConstants.Common_Constant.YES;
            }

        } catch (final Exception ex) {
            logger.error("Error occurred while searching available Jobs:", ex);
        }

        return isJobsAvailable;
    }

    @Override
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        final QuartzSchedulerMasterModel model = getModel();
        ModelAndView modelAndView = null;
        try {
            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            } else {
                if (MainetConstants.COMMON_STATUS.FAIL.equalsIgnoreCase(model.getIsSuccess())) {
                    modelAndView = jsonResult(JsonViewObject.successResult("Something wrong,Please contact to Administrator!"));
                } else {
                    modelAndView = customDefaultMyResult("BatchJobsMasterEdit");
                }

            }
        } catch (final RuntimeException ex) {

            logger.error("Exception Occurred during Jobs Modification: ", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        return modelAndView;

    }

}
