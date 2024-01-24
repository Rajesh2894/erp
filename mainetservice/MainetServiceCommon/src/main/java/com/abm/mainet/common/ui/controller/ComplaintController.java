package com.abm.mainet.common.ui.controller;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ComplaintGrid;
import com.abm.mainet.common.dto.ComplaintTypeBean;
import com.abm.mainet.common.dto.DepartmentComplaintBean;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.ui.model.ComplaintModel;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.HttpHelper;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author ritesh.patil
 *
 */
@Controller
@RequestMapping("/Complaint.html")
public class ComplaintController extends AbstractFormController<ComplaintModel> {

    @Autowired
    private IComplaintTypeService iComplaintService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("Complaint.html");
        return new ModelAndView(MainetConstants.COMPLAINT_MASTER_LIST, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "compId", required = false) final Long compId,
            @RequestParam(value = "type", required = false) final String modeType,ModelMap modelMap) {

        final ComplaintModel complaintModel = getModel();
        complaintModel.setCommonHelpDocs("Complaint.html");
        populateModel(compId, complaintModel, modeType);
        String isKdmcEnv = MainetConstants.N_FLAG;
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_PRODUCT)) {
        	isKdmcEnv = MainetConstants.Y_FLAG;
        }
        modelMap.put("kdmcEnv", isKdmcEnv);
        return new ModelAndView("complaintMaster/form", MainetConstants.FORM_NAME, complaintModel);
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param DepartmentComplaint
     */
    private void populateModel(final Long compId, final ComplaintModel complaintModel, final String modeType) {

        if (compId == null) {
            complaintModel.setDepartmentComplaint(new DepartmentComplaintBean());
            complaintModel.setModeType(MainetConstants.Complaint.MODE_CREATE);
            complaintModel.setDeptList(
                    iComplaintService.getNotcomplainedDepartment(UserSession.getCurrent().getOrganisation().getOrgid(),
                            MainetConstants.MENU.A));
            complaintModel.getDepartmentComplaint().setHiddeValue(MainetConstants.Complaint.MODE_CREATE);
        } else {
            complaintModel.setDepartmentComplaint(iComplaintService.findById(compId, UserSession.getCurrent().getLanguageId()));
            if (MainetConstants.Complaint.MODE_VIEW.equals(modeType)) {
                complaintModel.setModeType(MainetConstants.Complaint.MODE_VIEW);
                complaintModel.getDepartmentComplaint().setHiddeValue(MainetConstants.Complaint.MODE_VIEW);
            } else {
                complaintModel.setModeType(MainetConstants.Complaint.MODE_EDIT);
                complaintModel.getDepartmentComplaint().setHiddeValue(MainetConstants.Complaint.MODE_EDIT);
            }
        }
    }

    /**
     * Get Complaint Grid data
     * @param request
     * @return
     */
    @RequestMapping(params = "getGridData", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        final List<ComplaintGrid> complaintGrids = iComplaintService
                .findAllComplaintRecords(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MENU.Y);
        if (complaintGrids != null) {
            for (final ComplaintGrid complaintGrid : complaintGrids) {
                if (UserSession.getCurrent().getLanguageId() != MainetConstants.ENGLISH) {
                    complaintGrid.setDeptName(complaintGrid.getDeptNameReg());
                }
            }

        }
        return getModel().paginate(httpServletRequest, page, rows, complaintGrids);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(final HttpServletRequest request, final Exception exception) {
        logger.error("Exception found : ", exception);
        final boolean asyncRequest = HttpHelper.isAjaxRequest(request);
        if (asyncRequest) {
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        } else {
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
        }
    }

    /**
     * this method is used for check is there any application pending for complaint type or not. if yes than complaint type can
     * not be deleted.
     * @param comId
     * @return
     */
    @RequestMapping(params = "checkPendingApplication", method = RequestMethod.POST)
    public @ResponseBody boolean checkPendingForComplaint(@RequestParam("comId") Long comId) {
        boolean isPending = false;
        isPending = iComplaintService.isApplicaitonPendingForComplaintType(comId);
        return isPending;
    }

    /**
     * this service is used to check if any complaints is active or not.
     * @param deptCompId
     * @return it complaints is active than return true else return false.
     */
    @RequestMapping(params = "checkActiveComplaints", method = RequestMethod.POST)
    public @ResponseBody boolean checkActiveComplaints(@RequestParam("deptCompId") Long deptCompId) {
        boolean isComplaintsActive = false;
        isComplaintsActive = iComplaintService.isComplaintsActiveForDepartment(deptCompId);
        return isComplaintsActive;
    }

    /**
     * this service is used to inactive complaints department.
     * @param deptCompId
     */
    @ResponseBody
    @RequestMapping(params = "inactiveComplaintDepartment", method = RequestMethod.POST)
    public boolean inactiveComplaintDepartment(@RequestParam("deptCompId") Long deptCompId) {
        iComplaintService.inactiveComplaintDepartment(deptCompId);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, params = { "doDeletion" })
    public boolean doItemDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
            final HttpServletRequest request) {
        bindModel(request);
        List<ComplaintTypeBean> item = this.getModel().getDepartmentComplaint().getComplaintTypesList();
        List<ComplaintTypeBean> complaintTypeBeanList = item.stream()
                .filter(c -> c.getCompId() != id).collect(Collectors.toList());
        if (!complaintTypeBeanList.isEmpty()) {
            this.getModel().getDepartmentComplaint().setComplaintTypesList(complaintTypeBeanList);
        }
        return true;
    }
}
