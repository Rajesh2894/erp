package com.abm.mainet.common.master.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.ui.model.IssuanceCounterModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.service.ITaskService;

@Controller
@RequestMapping("/IssuanceCounter.html")
public class IssuanceCounterController extends AbstractFormController<IssuanceCounterModel> {

    @Autowired
    private ITaskService taskService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request) {
        this.sessionCleanup(request);
        this.getModel().bind(request);
        return new ModelAndView("IssuanceCounter", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(params = "getIssuanceDataList")
    public @ResponseBody List<UserTaskDTO> gridDataList(@RequestParam("status") String status, HttpServletRequest request,
            Model model) throws Exception {
        List<UserTaskDTO> list = new ArrayList<>();
        try {

            UserSession userSession = UserSession.getCurrent();
            TaskSearchRequest taskSearchRequest = new TaskSearchRequest();
            taskSearchRequest.setEmpId(userSession.getEmployee().getEmpId());
            taskSearchRequest.setLangId(userSession.getLanguageId());
            taskSearchRequest.setStatus(status);
            taskSearchRequest.setOrgId(userSession.getOrganisation().getOrgid());
            list = taskService.fetchClosedTaskList(taskSearchRequest);
        } catch (Exception ex) {
        }

        return list;
    }
    
     //#139419
    @RequestMapping(params = "getIssuedDataList")
    public @ResponseBody List<UserTaskDTO> issuedGridDataList(@RequestParam("status") String status,HttpServletRequest request,
            Model model) throws Exception {
        List<UserTaskDTO> list = new ArrayList<>();
        try {

            UserSession userSession = UserSession.getCurrent();
            TaskSearchRequest taskSearchRequest = new TaskSearchRequest();
            taskSearchRequest.setEmpId(userSession.getEmployee().getEmpId());
            taskSearchRequest.setLangId(userSession.getLanguageId());
            taskSearchRequest.setStatus(status);
            taskSearchRequest.setOrgId(userSession.getOrganisation().getOrgid());
            list = taskService.fetchIssuedTaskList(taskSearchRequest);
            list.get(0).setFlag(MainetConstants.FlagY);
     
        } catch (Exception ex) {
        }

        return list;
    }

}
