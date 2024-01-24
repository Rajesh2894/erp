package com.abm.mainet.care.ui.controller;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.report.ComplaintReportDTO;
import com.abm.mainet.care.dto.report.ComplaintReportRequestDTO;
import com.abm.mainet.care.service.ICareReportService;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.ComplaintReportModel;
import com.abm.mainet.care.ui.validator.GrievanceAgeingReportValidator;
import com.abm.mainet.care.ui.validator.GrievanceReportValidator;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * The {@code GrievanceReportController} class is MVC controller to control
 * Complaint report request
 * 
 * @author sanket.joshi
 * 
 * @see com.abm.mainet.care.service.CareReportService
 *
 */
@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT)
public class GrievanceReportController extends AbstractFormController<ComplaintReportModel> {

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private ICareReportService careReportService;

	@Autowired
	private ICareRequestService careRequestService;

	/**
	 * Operational Ward Zone is different from department to department.
	 * 
	 * @param deptId Department ID to retrieve Ward Zone.
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_REPORT_WARDZONE, method = RequestMethod.POST)
	public ModelAndView getOperationalWardZonePrefixName(
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId) {

		ComplaintReportModel model = getModel();
		
		
		//check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())) {
			String prefixName = tbDepartmentService.findDepartmentPrefixName(deptId,
					UserSession.getCurrent().getOrganisation().getOrgid());
                model.setKdmcEnv(MainetConstants.FlagY);
                if(StringUtils.isEmpty(prefixName)){
    				this.getModel().setPrefixName(MainetConstants.Common_Constant.CWZ);
    			}else{
    				this.getModel().setPrefixName(prefixName);
    			}
        }else {
                //Defect #110037-->Ward zone will be displayed based on CWZ prefix 
                 this.getModel().setPrefixName(MainetConstants.Common_Constant.CWZ);
        }
		
		if (this.getModel().getPrefixName() != null) {
			return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_REPORT_WARDZONE,
					MainetConstants.CommonConstants.COMMAND, model);
		} else {

			model.getCareReportRequest().setCodIdOperLevel1(null);
			model.getCareReportRequest().setCodIdOperLevel2(null);
			model.getCareReportRequest().setCodIdOperLevel3(null);
			model.getCareReportRequest().setCodIdOperLevel4(null);
			model.getCareReportRequest().setCodIdOperLevel5(null);

			/*
			 * model.getCareReportRequest().setCareWardNo1(null);
			 * model.getCareReportRequest().setCareWardNoEng(null);
			 * model.getCareReportRequest().setCareWardNoReg(null);
			 * model.getCareReportRequest().setCareWardNo1(null);
			 * model.getCareReportRequest().setCareWardNoEng1(null);
			 * model.getCareReportRequest().setCareWardNoReg1(null);
			 */

			return null;
		}
	}

	/**
	 * Complaint type form view
	 * 
	 * @param deptId Department ID to retrieve Ward Zone.
	 * @return
	 */

	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_REPORT_COMPLAINT_TYPE, method = RequestMethod.POST)
	public ModelAndView getComplaintTypes(@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId) {
		Set<DepartmentComplaintTypeDTO> complaintTypes = careRequestService.getDepartmentComplaintTypeByDepartmentId(
				deptId, UserSession.getCurrent().getOrganisation().getOrgid());
		if (complaintTypes != null && !complaintTypes.isEmpty()) {
			Set<LookUp> complaintTypes1 = new LinkedHashSet<>();
			complaintTypes.stream().sorted(Comparator.comparing(DepartmentComplaintTypeDTO::getComplaintDesc)).forEach(c -> {
                LookUp detData = new LookUp();
                detData.setDescLangFirst(c.getComplaintDesc());
                detData.setDescLangSecond(c.getComplaintDescReg());
                detData.setLookUpId(c.getCompId());
                complaintTypes1.add(detData);
            });
			this.getModel().setComplaintTypes(complaintTypes1);
			return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_REPORT_COMPLAINT_TYPE,
					MainetConstants.CommonConstants.COMMAND, getModel());
		} else {
			return null;
		}
	}

	/**
	 * Complaint Department wise report filter form.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE, method = RequestMethod.POST)
	public ModelAndView getDepartmentWiseComplaintReport(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		//check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		}
		// Defect #131217- check whether environment is dscl or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			this.getModel().setEnvFlag(MainetConstants.ENV_DSCL);
		}
		getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT
				+ MainetConstants.operator.QUE_MARK + MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * Complaint Department wise report view.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE_REPORT, method = RequestMethod.POST)
	public ModelAndView viewDepartmentWiseComplaintReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE);
		}
		ModelAndView modelAndView = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		crr.setOrgId(orgId);
		crr.setLangId(UserSession.getCurrent().getLanguageId());
		
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'D')  {
			model.setComplaintReport(careReportService.getDepartmentWiseComplaintReport(crr));
		if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_DEPT_WISE_REPORT));
		model.displayWardZoneColoumns();
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
		}else if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'S') {
			model.setComplaintReport(careReportService.getDepartmentWiseComplaintReport(crr));
		if (model.getComplaintReport().getComplaintList() == null
				|| model.getComplaintReport().getComplaintList().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_DEPT_WISE_SUMMARY_REPORT));
		model.displayWardZoneColoumns();
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE_SUMMARY_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());			
		}else if(!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId)) {
			model.setComplaintReport(careReportService.getDepartmentWiseComplaintReport(crr));
		if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_DEPT_WISE_REPORT));
		model.displayWardZoneColoumns();
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_WISE_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
		
		}
		//check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setKdmcEnv(MainetConstants.FlagY);
		}
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setDsclEnv(MainetConstants.FlagY);
		}
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL,
				UserSession.getCurrent().getOrganisation().getOrgid())){
			model.setAsclEnv(MainetConstants.FlagY);
		}
		return modelAndView;
	}

	/**
	 * Complaint Department and status wise report filter form
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE, method = RequestMethod.POST)
	public ModelAndView getDepartmentStatusWiseComplaintReport(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		}
		//Defect #131217- check whether environment is dscl or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			this.getModel().setEnvFlag(MainetConstants.ENV_DSCL);
		}
		getModel().setCommonHelpDocs(
				MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT + MainetConstants.operator.QUE_MARK
						+ MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * Complaint Department and status wise report view.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE_REPORT, method = RequestMethod.POST)
	public ModelAndView viewDepartmentStatusWiseComplaintReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE);
		}
		ModelAndView modelAndView = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		crr.setOrgId(orgId);
		crr.setLangId(UserSession.getCurrent().getLanguageId());
		
	if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'D') {
			model.setComplaintReport(careReportService.getDepartmentAndStatusWiseComplaintReport(crr));
		if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_DEPT_STAT_WISE_REPORT));
		model.displayWardZoneColoumns();
		model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
		model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
		model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
		model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
		model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}else if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'S') {
		model.setComplaintReport(careReportService.getDepartmentAndStatusWiseComplaintReport(crr));
		if (model.getComplaintReport().getComplaintList() == null
				|| model.getComplaintReport().getComplaintList().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_DEPT_STAT_WISE_SUMMARY_REPORT));
		model.displayWardZoneColoumns();
		model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
		model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
		model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
		model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
		model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE_SUMMARY_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
		
	}else if(!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId)) {
		model.setComplaintReport(careReportService.getDepartmentAndStatusWiseComplaintReport(crr));
	if (model.getComplaintReport().getComplaints() == null || model.getComplaintReport().getComplaints().isEmpty()) {
		getModel().addValidationError(
				getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
		return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE);
	}
	model.getComplaintReport().setTitle(ApplicationSession.getInstance()
			.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_DEPT_STAT_WISE_REPORT));
	model.displayWardZoneColoumns();
	modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_DEPT_STAT_WISE_REPORT,
			MainetConstants.CommonConstants.COMMAND, getModel());
	}
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setKdmcEnv(MainetConstants.FlagY);
		}
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setDsclEnv(MainetConstants.FlagY);
		}
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL,
				UserSession.getCurrent().getOrganisation().getOrgid())){
			this.getModel().setAsclEnv(MainetConstants.FlagY);
		}
		return modelAndView;
	
	}

	/**
	 * Complaint feedback report filter form.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCEFEEDBACK, method = RequestMethod.POST)
	public ModelAndView getComplaintFeedbackReport(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		}
		getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT
				+ MainetConstants.operator.QUE_MARK + MainetConstants.ServiceCareCommon.Report.GRIEVANCEFEEDBACK);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCEFEEDBACK,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * Complaint feedback report.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_FEEDBACK_REPORT, method = RequestMethod.POST)
	public ModelAndView viewComplaintFeedbackReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCEFEEDBACK);
		}
		ModelAndView modelAndView = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		crr.setOrgId(orgId);
		crr.setLangId(UserSession.getCurrent().getLanguageId());
		
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) && crr.getReportName() == 'D') {
			model.setComplaintReport(careReportService.getComplaintFeedbackReport(crr));
		if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCEFEEDBACK);
		}
		model.getCareReportRequest().setFeedbackRating(crr.getFeedbackRating());
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_FEEDBACK_REPORT));
		
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_FEEDBACK_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
		}
		else if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'S') {
			model.setComplaintReport(careReportService.getComplaintFeedbackReport(crr));
			if (model.getComplaintReport().getComplaints() == null
					|| model.getComplaintReport().getComplaints().isEmpty()) {
				getModel().addValidationError(
						getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
				return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCEFEEDBACK);
			}
			model.getComplaintReport().setTitle(ApplicationSession.getInstance()
					.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_FEEDBACK_REPORT_SUMMARY));
			
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_FEEDBACK_SUMMARY_REPORT,
					MainetConstants.CommonConstants.COMMAND, getModel());
			} else if (!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId)) {
				model.setComplaintReport(careReportService.getComplaintFeedbackReport(crr));
				if (model.getComplaintReport().getComplaints() == null
						|| model.getComplaintReport().getComplaints().isEmpty()) {
					getModel().addValidationError(
							getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
					return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCEFEEDBACK);
				}
				model.getCareReportRequest().setFeedbackRating(crr.getFeedbackRating());
				model.getComplaintReport().setTitle(ApplicationSession.getInstance()
						.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_FEEDBACK_REPORT));
				
				modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_FEEDBACK_REPORT,
						MainetConstants.CommonConstants.COMMAND, getModel());
			}
		
		// check whether environment is kdmc or not
			if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
						UserSession.getCurrent().getOrganisation().getOrgid())) {
					model.setKdmcEnv(MainetConstants.FlagY);
				}
			if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
					UserSession.getCurrent().getOrganisation().getOrgid())) {
				model.setDsclEnv(MainetConstants.FlagY);
			}
			if (CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL,
					UserSession.getCurrent().getOrganisation().getOrgid())){
				model.setAsclEnv(MainetConstants.FlagY);
			}
			return	modelAndView;
	}

	/**
	 * Complaint ageing report filter form.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING, method = RequestMethod.POST)
	public ModelAndView getComplaintAgeingReport(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT
				+ MainetConstants.operator.QUE_MARK + MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING_REPORT, method = RequestMethod.POST)
	public ModelAndView viewComplaintAgeingReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceAgeingReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING);
		}

		ModelAndView modelAndView = null;

		if (crr.getReportType() == 1) {
			crr.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			crr.setLangId(UserSession.getCurrent().getLanguageId());
			model.setComplaintReport(careReportService.getComplaintSummaryAgeingReport(crr));
			if (model.getComplaintReport().getComplaints() == null
					|| model.getComplaintReport().getComplaintSummary().isEmpty()) {
				getModel().addValidationError(
						getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
				return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING);
			}
			model.getComplaintReport().setTitle(ApplicationSession.getInstance()
					.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_AGEING_SUMMARY_REPORT));
			//#131783
			model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
			model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
			model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
			model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
			model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING_SUMMARY_REPORT,
					MainetConstants.CommonConstants.COMMAND, getModel());
		}
		if (crr.getReportType() == 2) {
			crr.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			crr.setLangId(UserSession.getCurrent().getLanguageId());
			model.setComplaintReport(careReportService.getComplaintDetailedAgeingReport(crr));
			if (model.getComplaintReport().getComplaints() == null
					|| model.getComplaintReport().getComplaintsGroups().isEmpty()) {
				getModel().addValidationError(
						getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
				return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING);
			}
			model.getComplaintReport().setTitle(ApplicationSession.getInstance()
					.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_AGEING_DETAILED_REPORT));
			//#131783
			model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
			model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
			model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
			model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
			model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_AGEING_DETAILED_REPORT,
					MainetConstants.CommonConstants.COMMAND, getModel());
		}
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setKdmcEnv(MainetConstants.FlagY);
		}
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setDsclEnv(MainetConstants.FlagY);
		}
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL,
				UserSession.getCurrent().getOrganisation().getOrgid())){
			model.setAsclEnv(MainetConstants.FlagY);
		}
		return modelAndView;
	}

	/**
	 * Complaint Department and SLA wise report filter form
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE, method = RequestMethod.POST)
	public ModelAndView getSlaWiseComplaintReport(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		// check whether environment is kdmc or not
				if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
						UserSession.getCurrent().getOrganisation().getOrgid())) {
					this.getModel().setKdmcEnv(MainetConstants.FlagY);
				}
		getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT
				+ MainetConstants.operator.QUE_MARK + MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * Complaint Department and SLA wise report view.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE_REPORT, method = RequestMethod.POST)
	public ModelAndView viewDepartmentSlaWiseComplaintReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceReportValidator.class);
	
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE);
		}
		ModelAndView modelAndView = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		crr.setOrgId(orgId);
		crr.setLangId(UserSession.getCurrent().getLanguageId());
		
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'D') { 
			model.setComplaintReport(careReportService.getSlaWiseComplaintReport(crr));
			if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_SLA_WISE_REPORT));	
		model.displayWardZoneColoumns();
		model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
		model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
		model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
		model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
		model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
		}else if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'S') {
			model.setComplaintReport(careReportService.getSlaWiseComplaintReport(crr));
			if (model.getComplaintReport().getComplaintList() == null
					|| model.getComplaintReport().getComplaintList().isEmpty()) {
				getModel().addValidationError(
						getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
				return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE);
			}
			model.getComplaintReport().setTitle(ApplicationSession.getInstance()
					.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_SLA_WISE_SUMMARY_REPORT));
			model.displayWardZoneColoumns();
			model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
			model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
			model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
			model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
			model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE_SUMMARY_REPORT,
					MainetConstants.CommonConstants.COMMAND, getModel());	
		}else if (!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId)) {
			model.setComplaintReport(careReportService.getSlaWiseComplaintReport(crr));
			if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_SLA_WISE_REPORT));	
		model.displayWardZoneColoumns();
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SLA_WISE_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
		}
		
		// check whether environment is kdmc or not
				if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
						UserSession.getCurrent().getOrganisation().getOrgid())) {
					model.setKdmcEnv(MainetConstants.FlagY);
				}
				if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
						UserSession.getCurrent().getOrganisation().getOrgid())) {
					model.setDsclEnv(MainetConstants.FlagY);
				}
				return modelAndView;
		}

	/**
	 * Complaint Department wise report filter form.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_GRADING, method = RequestMethod.POST)
	public ModelAndView getGradingComplaintReport(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT
				+ MainetConstants.operator.QUE_MARK + MainetConstants.ServiceCareCommon.Report.GRIEVANCE_GRADING);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_GRADING,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_GRADING_REPORT, method = RequestMethod.POST)
	public ModelAndView viewComplaintGradingReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		// model.validateBean(crr, GrievanceAgeingReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_GRADING);
		}

		ModelAndView modelAndView = null;

		if (crr.getReportType() == 1) {

		}
		if (crr.getReportType() == 2) {
			// crr.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			crr.setLangId(UserSession.getCurrent().getLanguageId());
			model.setComplaintReport(careReportService.getDepartmentWiseGradingReport(crr));
			if (model.getComplaintReport().getComplaintGradeSummary() == null
					|| model.getComplaintReport().getComplaintGradeSummary().isEmpty()) {
				getModel().addValidationError(
						getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
				return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_GRADING);
			}
			model.getComplaintReport().setTitle(ApplicationSession.getInstance()
					.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_GRADING_DETAILED_REPORT));
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_GRADING_DEPT_WISE_REPORT,
					MainetConstants.CommonConstants.COMMAND, getModel());
		}
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setKdmcEnv(MainetConstants.FlagY);
		}
		return modelAndView;
	}

	/**
	 * ComplaintType/SErviceType wise report filter form.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SERVICETYPE_WISE, method = RequestMethod.POST)
	public ModelAndView getComplaintDeptAndServiceWise(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		getModel().setCommonHelpDocs(
				MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT + MainetConstants.operator.QUE_MARK
						+ MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SERVICETYPE_WISE);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SERVICETYPE_WISE,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * Complaint Department and ServiceType wise report view.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SERVICETYPE_WISE_REPORT, method = RequestMethod.POST)
	public ModelAndView viewDepartmentServiceTypeWiseComplaintReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SERVICETYPE_WISE);
		}
		crr.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		crr.setLangId(UserSession.getCurrent().getLanguageId());
		model.setComplaintReport(careReportService.getDepartmentWiseComplaintReport(crr));
		if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SERVICETYPE_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance().getMessage(
				MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_REPORT_DEPT_SERVICE_COMP_WISE_REPORT));
		model.displayWardZoneColoumns();
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setKdmcEnv(MainetConstants.FlagY);
		}
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SERVICETYPE_WISE_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}
	
	
	/**
	 * ComplaintType/SErviceType/ModeType wise report filter form.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERTYPE_WISE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getComplaintDeptAndServiceWiseAndUserWise(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		getModel().setCommonHelpDocs(
				MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT + MainetConstants.operator.QUE_MARK
						+ MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERTYPE_WISE);
		//make employee list i.e raised by
		List<EmployeeBean> employeeList = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class).getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
	        employeeList.forEach(employee -> {
	            employee.setEmpname(employee.getFullName());
	            employee.setEmpmobno(String.valueOf(employee.getEmpId()));
	        });
	        employeeList.sort(Comparator.comparing(EmployeeBean::getEmpname));
	        EmployeeBean empBeanP = new EmployeeBean();
	        empBeanP.setEmpname("PORTAL");
	        empBeanP.setEmpmobno("WB");
	        EmployeeBean empBeanM = new EmployeeBean();
	        empBeanM.setEmpname("MOBILE");
	        empBeanM.setEmpmobno("MA");
	        employeeList.add(empBeanP);
	        employeeList.add(empBeanM);
	        this.getModel().setEmpList(employeeList);
	    	// check whether environment is kdmc or not
			if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
					UserSession.getCurrent().getOrganisation().getOrgid())) {
				this.getModel().setKdmcEnv(MainetConstants.FlagY);
			}	
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERTYPE_WISE,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * complaint-SmsAndEmail-History-Report-form Generation.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SMS_EMAIL_HISTORY, method = RequestMethod.POST)
	public ModelAndView generateSmsAndEmailReportForm(HttpServletRequest request) {
		// Session Cleanup
		sessionCleanup(request);
		bindModel(request);
		getModel().setCommonHelpDocs(
				MainetConstants.ServiceCareCommon.Report.GRIEVANCEREPORT + MainetConstants.operator.QUE_MARK
						+ MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SMS_EMAIL_HISTORY_FORM);
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SMS_EMAIL_HISTORY_FORM,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * complaint-SmsAndEmail-History-Report-form Generation.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "grievanceSmsEmailHistoryReport", method = RequestMethod.POST)
	public ModelAndView generateSmsAndEmailReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		model.setComplaintReport(new ComplaintReportDTO());
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SMS_EMAIL_HISTORY_FORM);
		}
		crr.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		crr.setLangId(UserSession.getCurrent().getLanguageId());
		if (crr.getAlertType() == null) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.ALERTTYPE));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SMS_EMAIL_HISTORY_FORM);
		}
		model.setSmsEmailDTO(careReportService.getSmsEmailHistoryByDeptIAndDate(crr));
		if (model.getSmsEmailDTO() == null || model.getSmsEmailDTO().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SMS_EMAIL_HISTORY_FORM);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance()
				.getMessage(MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_SMS_EMAIL_HISTORY_REPORT));
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setKdmcEnv(MainetConstants.FlagY);
		}
		return new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_SMS_EMAIL_HISTORY,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * Complaint Department, ServiceType,Mode wise report view.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERETYPE_WISE_REPORT, method = RequestMethod.POST)
	public ModelAndView viewDepartmentServiceUserTypeWiseComplaintReport(HttpServletRequest request) {
		bindModel(request);
		ComplaintReportModel model = this.getModel();
		ComplaintReportRequestDTO crr = model.getCareReportRequest();
		model.validateBean(crr, GrievanceReportValidator.class);
		if (model.hasValidationErrors()) {
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERTYPE_WISE);
		}
		ModelAndView modelAndView = null;
		Long  orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		crr.setOrgId(orgId);
		crr.setLangId(UserSession.getCurrent().getLanguageId());
		
		
       //check whether environment is kdmc or not
        if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())) {
        	String prefixName = tbDepartmentService.findDepartmentPrefixName(crr.getDepartment(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
                model.setKdmcEnv(MainetConstants.FlagY);
                if(StringUtils.isEmpty(prefixName)){
    				this.getModel().setPrefixName(MainetConstants.Common_Constant.CWZ);
    			}else{
    				this.getModel().setPrefixName(prefixName);
    			}
        }else {
                //Defect #110037-->Ward zone will be displayed based on CWZ prefix 
                 this.getModel().setPrefixName(MainetConstants.Common_Constant.CWZ);
        }
        // #129060
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) &&  crr.getReportName() == 'D') {
			model.setComplaintReport(careReportService.getDepartmentWiseComplaintReport(crr));
		if (model.getComplaintReport().getComplaints() == null
				|| model.getComplaintReport().getComplaints().isEmpty()) {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERTYPE_WISE);
		}
		model.getComplaintReport().setTitle(ApplicationSession.getInstance().getMessage(
				MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_REPORT_DEPT_SERVICE_MODE_COMP_WISE_REPORT));     
		model.displayWardZoneColoumns();
		model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
		model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
		model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
		model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
		model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERETYPE_WISE_REPORT,
				MainetConstants.CommonConstants.COMMAND, getModel());
		}else if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId) && crr.getReportName() == 'S') {
			model.setComplaintReport(careReportService.getUserWiseComplaintSummaryReport(crr));
			if (model.getComplaintReport().getComplaintList() == null
					|| model.getComplaintReport().getComplaintList().isEmpty()) {
				getModel().addValidationError(
						getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
				return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERTYPE_WISE);
			}
			model.getComplaintReport().setTitle(ApplicationSession.getInstance().getMessage(
					MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_REPORT_DEPT_SERVICE_MODE_COMP_WISE_SUMMARY_REPORT));     
			model.displayWardZoneColoumns();
			model.getCareReportRequest().setCareWardNo1(model.getComplaintReport().getCodIdOperLevel1());
			model.getCareReportRequest().setCareWardNo2(model.getComplaintReport().getCodIdOperLevel2());
			model.getCareReportRequest().setCareWardNo3(model.getComplaintReport().getCodIdOperLevel3());
			model.getCareReportRequest().setCareWardNo4(model.getComplaintReport().getCodIdOperLevel4());
			model.getCareReportRequest().setCareWardNo5(model.getComplaintReport().getCodIdOperLevel5());
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERETYPE_WISE_SUMMARY_REPORT,
					MainetConstants.CommonConstants.COMMAND, getModel());
		}else if (!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,orgId)) {
			model.setComplaintReport(careReportService.getDepartmentWiseComplaintReport(crr));
			if (model.getComplaintReport().getComplaints() == null
					|| model.getComplaintReport().getComplaints().isEmpty()) {
				getModel().addValidationError(getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			return customDefaultMyResult(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERTYPE_WISE);
			}
			model.getComplaintReport().setTitle(ApplicationSession.getInstance().getMessage(
					MainetConstants.ServiceCareCommon.Report.Titel.GRIEVANCE_REPORT_DEPT_SERVICE_MODE_COMP_WISE_REPORT));     
			model.displayWardZoneColoumns();   
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.Report.GRIEVANCE_USERETYPE_WISE_REPORT,
					MainetConstants.CommonConstants.COMMAND, getModel());
		}       
		// check whether environment is kdmc or not
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,
				UserSession.getCurrent().getOrganisation().getOrgid())) {
			model.setKdmcEnv(MainetConstants.FlagY);
		}
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
				UserSession.getCurrent().getOrganisation().getOrgid())){
			this.getModel().setDsclEnv(MainetConstants.FlagY);
		}
		return modelAndView;
	}

}
