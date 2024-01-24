package com.abm.mainet.cfc.scrutiny.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.scrutiny.ui.model.ScrutinyLabelModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;

/**
 * @author Rajendra.Bhujbal
 */
@Controller
@RequestMapping("/ScrutinyLabelView.html")
public class ScrutinyLabelController extends AbstractController<ScrutinyLabelModel> {

    @Resource
    IFileUploadService fileUploadService;
    
    @Autowired
	private ServiceMasterService serviceMasterService;
    
    @Autowired
	private TbDepartmentService tbDepartmentService;
    
    @Autowired
	private IWorkflowTaskService iWorkflowTaskService;
    
    @Autowired
    private GroupMasterService groupMasterService;
    
    @Autowired
    private IEmployeeService employeeService;
    
    @Autowired
	private IWorkflowRequestService requestService;
    
    @Autowired
    private ITaskService taskService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrutinyLabelController.class);
    
    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
    	binder.setAutoGrowCollectionLimit(10000);
    }

    @RequestMapping(params = "setViewData", method = RequestMethod.POST)
    public ModelAndView populateViewData(@RequestParam final long appId, @RequestParam final long serviceId,
            @RequestParam final long taskId,
            final HttpServletRequest httpServletRequest) {
        fileUploadService.sessionCleanUpForFileUpload();
        this.getModel().setWokflowDecision(null);
        this.getModel().setScrutinyDecisionRemark(null);
        try {
            this.getModel().setTaskId(taskId);
            if (UserSession.getCurrent() != null) {

                getModel().populateScrutinyViewData(appId, serviceId, UserSession.getCurrent());
              
                ModelAndView mv = new ModelAndView("populateViewData", MainetConstants.CommonConstants.COMMAND, getModel());
                
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
						MainetConstants.ENV_PSCL)) {
					final String shortCode = serviceMasterService.fetchServiceShortCode(serviceId,
							UserSession.getCurrent().getOrganisation().getOrgid());
					List<String> roleList = hideRejectionMsgforPSCLRoadCutting(serviceId, mv);
					mv.addObject("roleList", roleList);
					mv.addObject("shortCode",shortCode);
				}
                return mv;

            } else {
                return defaultExceptionView();
            }
        } catch (final RuntimeException e) {
            LOGGER.error("Exception Occur in populateViewData() ScrutinyLabelController ", e);
            return defaultExceptionView();
        }

    }

	public List<String> hideRejectionMsgforPSCLRoadCutting(final long serviceId, ModelAndView mv) {
		List<LookUp> lookups = CommonMasterUtility.getLookUps(MainetConstants.RoadCuttingConstant.SRP, UserSession.getCurrent().getOrganisation());
		List<String> rolecodeslist = new ArrayList<String>(); 
		for(LookUp lookup : lookups){
			rolecodeslist.add(lookup.getLookUpCode());
		}
		return rolecodeslist;
	}

    @RequestMapping(params = "getLabelList", method = RequestMethod.POST)
    public @ResponseBody List<Long> getLabelList(@RequestParam final long labelId, final HttpServletRequest httpServletRequest) {
        return getModel().getLabelList(labelId);
    }

    @RequestMapping(params = "getLabelValueByQuery", method = RequestMethod.POST)
    public @ResponseBody Object getLabelValueByQuery(@RequestParam final long labelId, @RequestParam final String labelValue,
            final HttpServletRequest httpServletRequest) {
        return getModel().getLabelValueByQuery(labelId, labelValue);
    }

    @RequestMapping(params = "saveLableValue", method = RequestMethod.POST)
    public @ResponseBody Object saveLableValue(@RequestParam final long labelId, @RequestParam final String labelValue,
            @RequestParam final long level, final HttpServletRequest httpServletRequest) {
        return getModel().saveLableValue(labelId, labelValue, level);
    }
    
    @RequestMapping(params = "saveLableRemark", method = RequestMethod.POST)
    public @ResponseBody Object saveLableRemark(@RequestParam final long labelId, @RequestParam final String resolutionComments,
            @RequestParam final long level, final HttpServletRequest httpServletRequest) {
        return getModel().saveLableRemark(labelId, resolutionComments, level);
    }

    @RequestMapping(params = "getOpenFormValue", method = RequestMethod.POST)
    public @ResponseBody String getOpenFormValue(@RequestParam final long labelId, @RequestParam final long level,
            final HttpServletRequest httpServletRequest) {
        return getModel().getOpenFormValue(labelId, level);
    }

    @RequestMapping(params = "saveScrutinyValue", method = RequestMethod.POST)
    public ModelAndView saveScrutinyValue(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final ScrutinyLabelModel model = getModel();
        try {
            if (model.saveScrutinyLabels()) {
                return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
            } else {
                return jsonResult(JsonViewObject.failureResult(getModel().getSuccessMessage()));
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Exception Occur in saveScrutinyValue() ScrutinyLabelController ", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }
    }

    @RequestMapping(params = "setViewDataFromModule", method = RequestMethod.POST)
    public ModelAndView populateViewDataFromModule(final HttpServletRequest httpServletRequest, @RequestParam final long appId,
            @RequestParam("labelId") final long labelId, @RequestParam("serviceId") final long serviceId) {
        getModel().populateScrutinyViewData(appId, serviceId, UserSession.getCurrent());
        return new ModelAndView("populateViewData", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(params = "submitScrutinyValue", method = RequestMethod.POST)
    public ModelAndView submitScrutinyValue(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final ScrutinyLabelModel model = getModel();
        try {
        	
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && model.getWokflowDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
					&& (!model.isScrutinyMetered()&& model.hasValidationErrors())) {
        		
        		ModelAndView mv = defaultResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

				return mv;
        	}
        	
        	//1st check LOI generated or not
        	if(model.getWokflowDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
					&& (!model.isScrutinyLoiGenerated()&& model.hasValidationErrors())) {
        		
        		ModelAndView mv = defaultResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

				return mv;
        	}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)) {
				UserTaskDTO userTaskdto = iWorkflowTaskService.findByTaskId(model.getTaskId());

				if (MainetConstants.WorkFlow.Decision.SEND_BACK.equalsIgnoreCase(model.getWokflowDecision())
						|| MainetConstants.WorkFlow.Decision.SEND_BACK_TO_APPLICANT.equalsIgnoreCase(model.getWokflowDecision())) {

					if (model.sendTobackEmpl()) {
						return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
					} else {
						return jsonResult(JsonViewObject.failureResult(getModel().getSuccessMessage()));
					}
				} else if (MainetConstants.WorkFlow.Decision.FORWARD_FOR_CLARIFICATION.equalsIgnoreCase(model.getWokflowDecision())
						|| (MainetConstants.WorkFlow.Decision.FORWARD_FOR_CLARIFICATION.equalsIgnoreCase(userTaskdto.getDeptNameReg())
							&& !MainetConstants.WorkFlow.Decision.FORWARD_FOR_CLARIFICATION.equalsIgnoreCase(model.getWokflowDecision()))) //This condition is to let child application(child to which application has been forwarded for clarification) to take action
				{
					try {
						if (model.forwardForClarification()) {
							return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
						} else {
							return jsonResult(JsonViewObject.failureResult(getModel().getSuccessMessage()));
						}
					} catch (final Exception ex) {
						ex.printStackTrace();
						LOGGER.error("Exception Occur in sendToback() ScrutinyLabelController ", ex);
						return jsonResult(JsonViewObject.failureResult(ex));
					}
				} else {

					if (model.submitScrutinyLabels()) {
						return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
					} else {
						return jsonResult(JsonViewObject.failureResult(getModel().getSuccessMessage()));
					}
				}
			} else {

				if (model.submitScrutinyLabels()) {
					return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
				} else {
					return jsonResult(JsonViewObject.failureResult(getModel().getSuccessMessage()));
				}
			}
        } catch (final Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Exception Occur in submitScrutinyValue() ScrutinyLabelController ", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }
    }

    @RequestMapping(params = "sendToback", method = RequestMethod.POST)
    public ModelAndView sendToback(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        httpServletRequest.getAttribute("level");
        final ScrutinyLabelModel model = getModel();
        try {
            if (model.sendTobackEmpl()) {
                return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
            } else {
                return jsonResult(JsonViewObject.failureResult(getModel().getSuccessMessage()));
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Exception Occur in sendToback() ScrutinyLabelController ", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }
    }
    
    @RequestMapping(params = "viewRemark", method = RequestMethod.POST)
    public ModelAndView viewRemark(final HttpServletRequest httpServletRequest,  @RequestParam("slLabelId") final Long slLabelId,  @RequestParam("levels") final Long levels) {
    	bindModel(httpServletRequest);
    	getModel().setSlLabelId(slLabelId);
    	getModel().setLevels(levels);
        return new ModelAndView("viewRemark", MainetConstants.CommonConstants.COMMAND, getModel());
    }
    
    @RequestMapping(params = "approvedByInitiator", method = RequestMethod.POST)
    public ModelAndView approvedByInitiator(final HttpServletRequest httpServletRequest,@RequestParam("taskId") final Long taskId) {
        bindModel(httpServletRequest);
        /*httpServletRequest.getAttribute("level");*/
        final ScrutinyLabelModel model = getModel();
        model.setTaskId(taskId);
        try {
            if (model.approvedByApplicant()) {
                return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
            } else {
                return jsonResult(JsonViewObject.failureResult(getModel().getSuccessMessage()));
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Exception Occur in sendToback() ScrutinyLabelController ", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }
    }
    
    @RequestMapping(params = "roles", method = RequestMethod.POST)
	public @ResponseBody List<GroupMaster> getRoles(final HttpServletRequest httpServletRequest,
			@RequestParam("dpDeptId")final Long deptId) {
		
		List<GroupMaster> roles = groupMasterService.getRoles(UserSession.getCurrent().getOrganisation().getOrgid(), deptId);
		return roles;
		
	}
    
    @RequestMapping(params = "employees", method = RequestMethod.POST)
	public @ResponseBody List<Employee> getEmployees(final HttpServletRequest httpServletRequest,
			@RequestParam("roleId")final Long roleId) {
		
    	List<Employee> employees = employeeService.getAllListEmployeeByGmId(UserSession.getCurrent().getOrganisation().getOrgid(), roleId);
    	if(CollectionUtils.isNotEmpty(employees)) {
    		Collections.sort(employees, Comparator.comparing(Employee::getEmpname));
    	}
		return employees;
		
	}
    
    @RequestMapping(params = "getServices", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> populateServices(@RequestParam("deptId") final Long deptId, final HttpServletRequest request) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<LookUp> servicelookupList =new ArrayList<>(); 
        List<Object[]> list =serviceMasterService.findAllActiveServicesForDepartment(orgId, deptId);
		LookUp lookup = null;
		for (final Object[] servicelookup : list) {
			lookup = new LookUp();
			lookup.setLookUpId(Long.parseLong(servicelookup[0].toString()));
			if (null != servicelookup[1]&& UserSession.getCurrent().getLanguageId() == 1) {
				lookup.setDescLangFirst(servicelookup[1].toString());
			}
			if (null != servicelookup[2] && UserSession.getCurrent().getLanguageId() == 2) {
				lookup.setDescLangFirst(servicelookup[2].toString());
			}
			servicelookupList.add(lookup);
		}
		return servicelookupList;
    }
    

}
