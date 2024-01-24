package com.abm.mainet.cfc.scrutiny.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.scrutiny.ui.model.ApplicationAuthorizationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;

/**
 * @author Vivek.Kumar
 * @since 06 April 2016
 */
@Controller
@RequestMapping("/ApplicationAuthorization.html")
public class ApplicationAuthorizationController extends AbstractController<ApplicationAuthorizationModel> {

    private static final String REDIRECT = "redirect:/";
    private static final String REQUEST_PARAM = "?viewApplicationAndDoScrutiny";
    private static final String ERROR_MESSAGE = "Error Occurred while request processing for Application Authorization for Application No.=";
    private static final String SERVICE_URL_NOT_CONFIGURED = "Service action Url is not configured in Service Master against serviceId=";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthorizationController.class);

    @Resource
    private CommonService commonService;

    @Autowired
    private ITaskService taskService;
    
    @Resource
    private ServiceMasterService serviceMasterService;
    
    @Autowired
	private IWorkflowTaskService iWorkflowTaskService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model model, HttpServletRequest httpServletRequest) throws Exception {
        String eventName = httpServletRequest.getParameterMap().get("eventName")[0];
        final String isBpmActive = ApplicationSession.getInstance().getMessage("bpm.enabled");
        UserSession userSession = UserSession.getCurrent();
        List<UserTaskDTO> list = new ArrayList<>();
        if (isBpmActive.equals(MainetConstants.Common_Constant.YES)) {

            TaskSearchRequest taskRequest = new TaskSearchRequest();
            taskRequest.setEmpId(userSession.getEmployee().getEmpId());
            taskRequest.setOrgId(userSession.getOrganisation().getOrgid());
            taskRequest.setEventName(eventName);
            taskRequest.setStatus(MainetConstants.WorkFlow.Status.PENDING);
            list = taskService.getTaskList(taskRequest);
        }
        model.addAttribute("applicationList", list);
        model.addAttribute("level", eventName);
        return defaultResult();
    }

    /**
     * main scrutiny request handler method,when request made from department dash board for scrutiny that will be proceed from
     * here and redirect to service specific view
     * @param applId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView redirectToViewAndDoScrutiny(@RequestParam final long appNo,
            @RequestParam("actualTaskId") final Long actualTaskId, @RequestParam("workflowId") final Long workflowId,
            final HttpServletRequest request)

    {

        bindModel(request);
        String actionURL = null;
        try {
            getModel().setTaskId(actualTaskId);
            List<String> paramList = null;
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)) {
				UserTaskDTO currentTask = iWorkflowTaskService.findByTaskId(actualTaskId);
				paramList = serviceMasterService.getServiceActionUrlParams(currentTask.getServiceId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				paramList.add(MainetConstants.INDEX.ONE, currentTask.getServiceId().toString());
				paramList.add(MainetConstants.INDEX.TWO, currentTask.getReferenceId());
			} else {
				paramList = commonService.findServiceActionUrl(appNo,
						UserSession.getCurrent().getOrganisation().getOrgid());
			}
            UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID,
                    actualTaskId.toString());
            UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID,
                    appNo + MainetConstants.BLANK);
            UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.CFC_URL,
                    paramList.get(MainetConstants.INDEX.ZERO));
            UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.SM_SERVICE_ID,
                    paramList.get(MainetConstants.INDEX.ONE));
            UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.WORK_FLOW_ID,
            		workflowId.toString());
            UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.REFERENCE_ID,
            		paramList.get(MainetConstants.INDEX.TWO) == null ? "" : paramList.get(MainetConstants.INDEX.TWO));
            getModel().getWorkflowActionDto().setApplicationId(appNo);
            getModel().getWorkflowActionDto().setTaskId(actualTaskId);
            
            if ((paramList.get(MainetConstants.INDEX.ZERO) == null)
                    || paramList.get(MainetConstants.INDEX.ZERO).toString().isEmpty()) {
                throw new FrameworkException(SERVICE_URL_NOT_CONFIGURED + paramList.get(MainetConstants.INDEX.ONE));
            } else {
                actionURL = REDIRECT + paramList.get(MainetConstants.INDEX.ZERO).toString() + REQUEST_PARAM;
            }
            
            String serviceCode = serviceMasterService.fetchServiceShortCode(Long.valueOf(paramList.get(1)), UserSession.getCurrent().getOrganisation().getOrgid());
            getModel().setServiceCode(serviceCode);

        } catch (final Exception ex) {
            LOGGER.error(ERROR_MESSAGE + appNo, ex);
            return defaultExceptionFormView();
        }
        return new ModelAndView(actionURL);
    }

}
