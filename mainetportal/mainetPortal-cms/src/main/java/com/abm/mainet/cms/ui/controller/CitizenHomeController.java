
package com.abm.mainet.cms.ui.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.dto.NewsLetterSubscriptionDTO;
import com.abm.mainet.cms.service.IEIPHomePageImageService;
import com.abm.mainet.cms.service.INewsLetterSubscriptionService;
import com.abm.mainet.cms.service.IVIEWQuickLinkService;
import com.abm.mainet.cms.ui.listener.SessionListener;
import com.abm.mainet.cms.ui.model.CitizenHomeModel;
import com.abm.mainet.cms.ui.model.EIPMenuManager;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicationStatusDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IApplicationService;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IWorkflowActionService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.MenuRoleEntitlement;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@Controller
@RequestMapping("/CitizenHome.html")
public class CitizenHomeController extends AbstractFormController<CitizenHomeModel> {
    private static final Logger LOG = Logger.getLogger(CitizenHomeController.class);

    private static final String ACTIONS = "actions";
    private static final String ACTION_HISTORY = "ActionHistoryTemp";
    private static final String ACTION_HISTORY_LOGIN = "ActionHistoryTempLogin";
    private static final String APPSTATUS = "appStatus";

    @Autowired
    private IEIPHomePageImageService iEIPHomePageImageService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private ICommonBRMSService iCommonBRMSService;

    @Autowired
    private IWorkflowActionService workflowActionService;
    
    @Autowired
    private IApplicationService applicationService;
    
    @Autowired
    private INewsLetterSubscriptionService subscriptionService;

    @Autowired
    private IEntitlementService iEntitlementService;
   
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {
    	LOG.info("User session Data inside Citizen Home index method " + UserSession.getCurrent().getOrganisation());
        if (isRestrictClickJacking()) {
            response.addHeader("X-FRAME-OPTIONS", MainetConstants.DENY);
        }
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();

        final CitizenHomeModel model = getModel();
        
        //D128606 code added for DSCL MDDA dashboard
        //This code is to integrate MDDA and Traffic Dashboard API 
        final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ENVIRNMENT_VARIABLE.MDD_DASHBOARD, MainetConstants.ENVIRNMENT_VARIABLE.ENV,
				UserSession.getCurrent().getOrganisation());
        
		if (lookup != null && StringUtils.isNotBlank(lookup.getOtherField()) && "Y".equalsIgnoreCase(lookup.getOtherField())) {
			try {
            	model.getMDDAApiResponse();
            	//model.getTrafficUpdate();
            	request.getSession().setAttribute("sensorList", model.getLevelData("SNS"));
    		} catch (Exception e) {
    			LOG.error("MDDAApi not working :: ", e);
    			// TODO: handle exception
    		}
		}
        
		LOG.info("getMDDAApiResponse method executed"); 
        
        final Employee employee = UserSession.getCurrent().getEmployee();
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            request.setAttribute("pageName", UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            request.setAttribute("pageName", UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }
        if (MainetConstants.FlagY.equals(UserSession.getCurrent().getOrganisation().getDefaultStatus())) {
            request.setAttribute("headerFlag", "N");
        } else {
            request.setAttribute("headerFlag", "Y");
        }
        if ((employee != null) && (employee.getEmpGender() != null)) {
            if (employee.getEmpGender().length() > 1) {
                employee.setEmpGender(model.getNonHierarchicalLookUpObject(new Long(employee.getEmpGender())).getLookUpCode());
            }
        }
        if((employee != null) && (employee.getEmpphotopath() != null) && !employee.getEmpphotopath().isEmpty()) {
        	
         	model.setLogInUserImage(Utility.getImageDetails(employee.getEmpphotopath()));
         	request.getSession().setAttribute("profileImageName", model.getLogInUserImage());
        }else {
        	 request.getSession().setAttribute("profileImageName", MainetConstants.BLANK);
        }
        if ((employee != null) && (employee.getEmploginname() != null)
                && employee.getEmploginname().equals(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
            try {
                if ((UserSession.getCurrent().getSlidingImgLookUps() == null)
                        || UserSession.getCurrent().getSlidingImgLookUps().isEmpty()) {
                    UserSession.getCurrent().setSlidingImgLookUps(iEIPHomePageImageService.getProfileImages(model.getOutputPath(),
                            model.getFileNetClient(), UserSession.getCurrent().getOrganisation(), MainetConstants.FlagS));
                }
            } catch (final Exception e) {
                LOG.error("File not available on filenet", e);
            }
            LOG.info("before  getAnnouncement method executed"); 
            model.getAnnouncement();
            model.getAllNotices();
           model.getMayorAndCommissionerProfileList();
           
           LOG.info("after  getMayorAndCommissionerProfileList method executed"); 
        }

        try {
            if ((UserSession.getCurrent().getLogoImagesList() == null)
                    || UserSession.getCurrent().getLogoImagesList().isEmpty()) {
                UserSession.getCurrent().setLogoImagesList(iEIPHomePageImageService.getProfileImages(model.getOutputPath(),
                        model.getFileNetClient(), UserSession.getCurrent().getOrganisation(), MainetConstants.FlagL));
            }
        } catch (final Exception e) {
            LOG.error("File not available on filenet", e);
        }
        LOG.info("after  getAnnouncement method executed"); 
        if ((employee != null) && (employee.getEmploginname() != null)
                && !employee.getEmploginname().equals(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
        	LOG.info("User session Data  in If" + UserSession.getCurrent().getOrganisation());
	        	String userAgent = request.getHeader("User-Agent"); 
				if(userAgent==null){
					userAgent="userAgent";
				} 
	        	if(!userAgent.equals(UserSession.getCurrent().getUserAgent())) {
	        		LOG.info("Inside User Agent for session invalidate "); 
	        		 request.getSession(false).invalidate();
	        		 return new ModelAndView("redirect:/CitizenHome.html");
	        	}
            ((EIPMenuManager) UserSession.getCurrentMenuManager()).setCitizenType(false);
            ((EIPMenuManager) UserSession.getCurrentMenuManager()).setAgencyType(false);

            boolean isCitizen = false;
            boolean isAgency = false;
            final String userType = getLoginUserType();
            if (MainetConstants.NEC.CITIZEN.equals(userType)) {
                isCitizen = true;
                ((EIPMenuManager) UserSession.getCurrentMenuManager()).setCitizenType(true);
            } else if (MainetConstants.QueryAttributes.AGENCY.equals(userType)) {
                isAgency = true;
                ((EIPMenuManager) UserSession.getCurrentMenuManager()).setAgencyType(true);
            }

            if ((employee != null) && isCitizen) {
                ((EIPMenuManager) UserSession.getCurrentMenuManager()).setUserType("Citizen");
                ((EIPMenuManager) UserSession.getCurrentMenuManager())
                        .setServiceType(ApplicationSession.getInstance().getMessage("eip.serviceCTZType"));
                employee.setEmppiservername(Utility.getClientIpAddress(request));
                employee.setEmpisecuritykey(request.getRequestedSessionId());
                iEmployeeService.setEmployeeLoggedInFlag(employee);

                if (UserSession.getCurrent().getRedirecturl() != null) {
                    final String Rdurl = UserSession.getCurrent().getRedirecturl();
                    UserSession.getCurrent().setRedirecturl(null);
                    return new ModelAndView("redirect:" + Rdurl);
                }
                final Long groupid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.DEFAULT_CITIZEN, UserSession.getCurrent().getOrganisation().getOrgid());
    	        MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(groupid != null ? groupid : employee.getGmid(),  UserSession.getCurrent().getOrganisation().getOrgid());
                return new ModelAndView("CitizenHomeEIP", MainetConstants.FORM_NAME, new CitizenHomeModel());

            } else if ((employee != null) && isAgency) {
                ((EIPMenuManager) UserSession.getCurrentMenuManager()).setUserType("Agency");
                ((EIPMenuManager) UserSession.getCurrentMenuManager())
                        .setServiceType(ApplicationSession.getInstance().getMessage("eip.serviceAGNType"));

                if (((UserSession.getCurrent().getEmployee().getIsUploaded() != null)
                        && UserSession.getCurrent().getEmployee().getIsUploaded().equals(MainetConstants.IsUploaded.UPLOADED)
                        && UserSession.getCurrent().getEmployee().getAuthStatus().equals("P"))

                        || ((UserSession.getCurrent().getEmployee().getIsUploaded() != null)
                                && UserSession.getCurrent().getEmployee().getIsUploaded()
                                        .equals(MainetConstants.IsUploaded.UPLOADED)
                                && ((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                                        && UserSession.getCurrent().getEmployee().getAuthStatus()
                                                .equals(MainetConstants.AuthStatus.APPROVED)))

                        || ((UserSession.getCurrent().getEmployee().getIsUploaded() != null)
                                && UserSession.getCurrent().getEmployee().getIsUploaded()
                                        .equals(MainetConstants.IsUploaded.UPLOADED)
                                && ((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                                        && UserSession.getCurrent().getEmployee().getAuthStatus().equals("D"))))

                {
                    return new ModelAndView("AgencyHome", MainetConstants.FORM_NAME, new CitizenHomeModel());
                } else {
                    return new ModelAndView("redirect:AgencyDOCVerification.html");
                }

            } else {

                ((EIPMenuManager) UserSession.getCurrentMenuManager()).setUserType("Employee");
                ((EIPMenuManager) UserSession.getCurrentMenuManager())
                        .setServiceType(ApplicationSession.getInstance().getMessage("eip.serviceDEPTType"));

                this.getModel().getAllNodes();
                if (((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                        && UserSession.getCurrent().getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.APPROVED))
                        && null != UserSession.getCurrent().getEmployee().getDesignation()
                        && UserSession.getCurrent().getEmployee().getDesignation().getDsgname().equalsIgnoreCase("ADMIN")) {
                }

                if (((UserSession.getCurrent().getEmployee().getIsUploaded() != null)
                        && UserSession.getCurrent().getEmployee().getIsUploaded().equals(MainetConstants.IsUploaded.UPLOADED)
                        && (UserSession.getCurrent().getEmployee().getAuthStatus() == null))

                        || ((UserSession.getCurrent().getEmployee().getIsUploaded() != null)
                                && UserSession.getCurrent().getEmployee().getIsUploaded()
                                        .equals(MainetConstants.IsUploaded.UPLOADED)
                                && UserSession.getCurrent().getEmployee().getAuthStatus().equals("D"))

                        || ((UserSession.getCurrent().getEmployee().getIsUploaded() != null)
                                && UserSession.getCurrent().getEmployee().getIsUploaded()
                                        .equals(MainetConstants.IsUploaded.UPLOADED)
                                && ((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                                        && UserSession.getCurrent().getEmployee().getAuthStatus()
                                                .equals(MainetConstants.AuthStatus.APPROVED))))

                {

                    MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(UserSession.getCurrent().getEmployee().getGmid(),  UserSession.getCurrent().getOrganisation().getOrgid());
                    //changes for language change ends --->
                    return new ModelAndView("DeptHomeEIP", MainetConstants.FORM_NAME, getModel());

                } else {
                    // return new ModelAndView("redirect:DeptDocVerification.html");
                    return new ModelAndView("DeptHomeEIP", MainetConstants.FORM_NAME, getModel());
                }

            }
        } else {
        	LOG.info("User session Data  in else" + UserSession.getCurrent().getOrganisation()); 
            // df# 121961 fotter counts needs to be shown in all Pages
            request.getSession().setAttribute("totalRegisUser", iEmployeeService.findCountOfRegisteredEmployee());
            request.getSession().setAttribute("activeOrg", iOrganisationService.findCountOfActiveOrg());
            request.getSession().setAttribute("loggedInUser", iEmployeeService.findCountOfLoggedInUser());
            request.getSession().setAttribute("activeuser", SessionListener.getActiveSessions());
            // df# 121961 fotter counts needs to be shown in all Pages
            LOG.info("After setting all session  attributes" + UserSession.getCurrent().getOrganisation());
            if (((EIPMenuManager) UserSession.getCurrentMenuManager()).getOnlineCznServiceFlag()
                    .equalsIgnoreCase(MainetConstants.MENU.TRUE)
                    && (((EIPMenuManager) UserSession.getCurrentMenuManager()).getCitizenMenuString() == null)) {
                ((EIPMenuManager) UserSession.getCurrentMenuManager()).arrangeMenus(MainetConstants.DEFAULT_USER);
            }

            ((EIPMenuManager) UserSession.getCurrentMenuManager()).setUserType("Visitor");
            LOG.info("Before get Announcement method" + UserSession.getCurrent().getOrganisation());
            // getModel().getAboutUs();
            getModel().getAnnouncement();
            LOG.info("Before  iviewQuickLinkService object creation" );
            final IVIEWQuickLinkService iviewQuickLinkService = ApplicationContextProvider.getApplicationContext()
                    .getBean(IVIEWQuickLinkService.class);

            final CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
            if ((UserSession.getCurrent().getQuickLinkEng() == null)
                    || UserSession.getCurrent().getQuickLinkEng().isEmpty()) {

                UserSession.getCurrent().setQuickLinkEng(
                        iviewQuickLinkService.getQuickLink(UserSession.getCurrent().getOrganisation(), token,
                                MainetConstants.ENGLISH));
            }

            if ((UserSession.getCurrent().getQuickLinkReg() == null)
                    || UserSession.getCurrent().getQuickLinkReg().isEmpty()) {
                UserSession.getCurrent().setQuickLinkReg(
                        iviewQuickLinkService.getQuickLink(UserSession.getCurrent().getOrganisation(), token,
                                MainetConstants.MARATHI));
            }

            getModel().loadProfileData(request);
            LOG.info("after  loadProfileData method executed" +UserSession.getCurrent().getOrganisation()); 
            request.setAttribute("metaKeywords", UserSession.getCurrent().getKeywords());
			/* return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, model); */
            String viewName=findActiveTemplate();
            LOG.info("View Name and session data >>>"+viewName +"     >>>" +UserSession.getCurrent().getOrganisation()); 
            return new ModelAndView(viewName, MainetConstants.FORM_NAME, model);
			/*
			 * if(UserSession.getCurrent().getOrganisation().getOrgid()==176) { return new
			 * ModelAndView(getViewName(), MainetConstants.FORM_NAME, model); }else { return
			 * new ModelAndView("CitizenHomeSmartcity", MainetConstants.FORM_NAME, model); }
			 */
		
            
        }

    }
    
    private String findActiveTemplate() {
        String activeProfile="";
        String viewForm="CitizenHome"; // write default form
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps("SMP",
                 UserSession.getCurrent().getOrganisation());
        if(null != lookUps){
	        Optional<LookUp> profile=lookUps.stream().filter(l -> StringUtils.isNotBlank(l.getOtherField()) && StringUtils.equalsIgnoreCase("Y", l.getOtherField())).findAny();
	        if(profile.isPresent()) activeProfile=profile.get().getLookUpCode();
	        if(StringUtils.isNotBlank(activeProfile) && StringUtils.equalsIgnoreCase("M", activeProfile))
	        viewForm=ApplicationSession.getInstance().getMessage(UserSession.getCurrent().getOrganisation().getOrgShortNm());
        }
        LOG.info("View form name: " + viewForm + " orgID: " + UserSession.getCurrent().getOrganisation().getOrgid());
        return viewForm;
 }
    
    @RequestMapping(method = RequestMethod.POST, params = "dispNonLogInMenu")
    public String dispNonLogInMenu(@RequestParam("dispNonLogInMenu") final String dispNonLogInMenu,
            final HttpServletRequest request, final HttpServletResponse response) {
        if (((EIPMenuManager) UserSession.getCurrentMenuManager()).getOnlineCznServiceFlag().equals(MainetConstants.MENU.FALSE)) {
            ((EIPMenuManager) UserSession.getCurrentMenuManager()).setOnlineCznServiceFlag(MainetConstants.MENU.TRUE);
        }
        LOG.info("before getViewName method executed"); 
        return getViewName();
        
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "complete")
    public ModelAndView completeForm() {
        return new ModelAndView("HomePage", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.GET, params = "EditUserProfile")
    public ModelAndView editUserProfile() {
    	FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
    	        final Employee currentEmployee = UserSession.getCurrent().getEmployee();
        getModel().setCurrentEmpForEditProfile(currentEmployee);
        getModel().setEmpGender();
        if (currentEmployee.getEmpMName() == null) {
            getModel().setEmpNameForEditProfile(
                    currentEmployee.getEmpname() + MainetConstants.WHITE_SPACE + currentEmployee.getEmpLName());
        } else {
            getModel().setEmpNameForEditProfile(
                    currentEmployee.getEmpname() + MainetConstants.WHITE_SPACE + currentEmployee.getEmpMName()
                            + MainetConstants.WHITE_SPACE
                            + currentEmployee.getEmpLName());
            getModel().setHiddEmailId(currentEmployee.getEmpemail());
            getModel().setHiddMobNo(currentEmployee.getEmpmobno());
        }
        LOG.info("after editUserProfile method executed"); 
        return new ModelAndView("CitizenEditProfile", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.GET, params = "viewEmialId")
    public ModelAndView showEmailIdForm(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("citizenUpdateEmail", MainetConstants.FORM_NAME, getModel());
    }
	/* It is malfunction code so it will be re-written as per RJ Sir
	 * @RequestMapping(params = "UpdateEmailID", method = RequestMethod.POST)
	 * public @ResponseBody String updateEmilId(final HttpServletRequest request,
	 * 
	 * @RequestParam("updateEmailId") final String newMailId) { bindModel(request);
	 * getModel(); String result = null;
	 * 
	 * if ((newMailId != null) || (newMailId != MainetConstants.operator.EMPTY)) {
	 * if (!getModel().isUniqueEmailAddress(newMailId)) { result =
	 * ApplicationSession.getInstance().getMessage("Employee.uniqueEMailId"); } else
	 * {
	 * 
	 * result = getModel().setEmailId(newMailId); } }
	 * 
	 * return result; }
	 */

    @RequestMapping(params = "CitizenEditSave", method = RequestMethod.POST)
    public ModelAndView editUserProfileSave(final HttpServletRequest request) {
        bindModel(request);
        final CitizenHomeModel model = getModel();
        final Employee modifiedEmployee = model.getCurrentEmpForEditProfile();
        if (modifiedEmployee.getTitle() == 0L) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.title"));
        }
        LOG.info("after  editUserProfileSave method executed"); 
        if (modifiedEmployee.getEmpGender().equals(MainetConstants.Common_Constant.NUMBER.ZERO)) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.gender"));
        } /*
           * else { modifiedEmployee.setEmpGender( model.getNonHierarchicalLookUpObject(new
           * Long(modifiedEmployee.getEmpGender())).getLookUpCode()); }
           */
        if ((modifiedEmployee.getEmpemail() != null) && !(modifiedEmployee.getEmpemail().isEmpty())) {
            if (!(Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(modifiedEmployee.getEmpemail()).matches())) {
                model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.eMail"));
            }else if(!modifiedEmployee.getEmpemail().equals(model.getHiddEmailId())){
            	 model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.eMail.tampered"));	
            }
        }
        LOG.info("after  getEmpemail method executed"); 
        if (modifiedEmployee.getEmpname().isEmpty()) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.firstName"));
        }
        if (modifiedEmployee.getEmpLName().isEmpty()) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.lastName"));
        }
        if (modifiedEmployee.getEmpdob() == null) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.dob"));
        }
        LOG.info("after  addValidationError method executed"); 
        if (modifiedEmployee.getEmpdob() != null) {

            Calendar dob = Calendar.getInstance();
            dob.setTime(modifiedEmployee.getEmpdob());
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
                age--;
            } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < dob
                            .get(Calendar.DAY_OF_MONTH)) {
                age--;
            }

            if (age < 18) {
                model.addValidationError(getApplicationSession().getMessage("citizen.login.reg.dob.error1"));
            }
        }
        if (modifiedEmployee.getEmpAddress().isEmpty() || modifiedEmployee.getEmpAddress() == null) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.permaAddress"));
        }
        if (modifiedEmployee.getEmpAddress1().isEmpty() || modifiedEmployee.getEmpAddress1() == null) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.Address1"));
        }
        LOG.info("after  addValidationError method executed"); 
        if (modifiedEmployee.getPincode().isEmpty() || modifiedEmployee.getPincode() == null) {
            model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.permaPinCode"));
        }
        if (!modifiedEmployee.getPincode().isEmpty() && modifiedEmployee.getPincode() != null) {
            final int pin = String.valueOf(modifiedEmployee.getPincode()).length();
            if (pin < 6) {
                model.addValidationError(getApplicationSession().getMessage("citizen.editProfile.per.pincode.length.error"));
            }
        }

        if (!(StringUtils.isEmpty(modifiedEmployee.getEmpAddress())) && (modifiedEmployee.getPincode() != null)
                && !(StringUtils.isEmpty(modifiedEmployee.getEmpCorAddress1()))) {
            modifiedEmployee.setAddFlag(MainetConstants.YES);
        }
        
        
        if (((modifiedEmployee.getVoterNo() != null) && !(modifiedEmployee.getVoterNo().isEmpty()))) {
            if (!(Pattern.compile(MainetConstants.VOTERID).matcher(modifiedEmployee.getVoterNo()).matches()) || (modifiedEmployee.getVoterNo().length()!= 10)) {
            	model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.voter"));
            }
			/*
			 * if(modifiedEmployee.getVoterNo().length()!= 10) {
			 * model.addValidationError(getApplicationSession().getMessage(
			 * "eip.citizen.editUserProile.voter")); }
			 */
        }
        LOG.info("after addValidationError method executed");
        if ((modifiedEmployee.getIdentityNo() != null) && !(modifiedEmployee.getIdentityNo().isEmpty())) {
            if (!(Pattern.compile(MainetConstants.ALPHA_NUMERIC).matcher(modifiedEmployee.getIdentityNo()).matches())) {
            	model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.identity"));           
            }
            if(!(modifiedEmployee.getIdentityNo().length()>=10 && modifiedEmployee.getIdentityNo().length()<=12)) {
        		model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.identity"));
        	} 
        }
        
        if (((modifiedEmployee.getPanCardNo() != null) && !(modifiedEmployee.getPanCardNo().isEmpty()))) {
            if (!(Pattern.compile(MainetConstants.PAN).matcher(modifiedEmployee.getPanCardNo()).matches()) || (modifiedEmployee.getPanCardNo().length()!=10)) {
            	model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.pan"));
            }
			/*
			 * if(modifiedEmployee.getPanCardNo().length()!=10) {
			 * model.addValidationError(getApplicationSession().getMessage(
			 * "eip.citizen.editUserProile.pan")); }
			 */
        }
        
        if (((modifiedEmployee.getPassportNo() != null) && !(modifiedEmployee.getPassportNo().isEmpty()))) {
            if (!(Pattern.compile(MainetConstants.PASSPORT).matcher(modifiedEmployee.getPassportNo()).matches()) || (modifiedEmployee.getPassportNo().length()!= 8)) {
            	model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.passport"));
            }
			/*
			 * if(modifiedEmployee.getPassportNo().length()!= 8) {
			 * model.addValidationError(getApplicationSession().getMessage(
			 * "eip.citizen.editUserProile.passport")); }
			 */
        }
        
        if (((modifiedEmployee.getLicenseNo() != null) && !(modifiedEmployee.getLicenseNo().isEmpty()))) {
            if (!(Pattern.compile(MainetConstants.DRIVING_LICENSE).matcher(modifiedEmployee.getLicenseNo()).matches()) || (modifiedEmployee.getLicenseNo().length()!=16)) {
            	model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.licence"));
            }
			/*
			 * if(modifiedEmployee.getLicenseNo().length()!=16) {
			 * model.addValidationError(getApplicationSession().getMessage(
			 * "eip.citizen.editUserProile.licence")); }
			 */
        }
        
        if ((modifiedEmployee.getEmpuid() != null) && !(modifiedEmployee.getEmpuid().isEmpty())) {
            if (!(Pattern.compile(MainetConstants.NUMERIC).matcher(modifiedEmployee.getEmpuid()).matches())) {
            	model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.uid"));
            }
            if(modifiedEmployee.getEmpuid().length()!=12) {
        		model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.uid"));
        	}
        }
		if (modifiedEmployee.getEmpmobno() != null && (!modifiedEmployee.getEmpmobno().equals(model.getHiddMobNo()))) {
			model.addValidationError(getApplicationSession().getMessage("eip.citizen.editUserProile.mob.tampared"));
		}
		if (modifiedEmployee.getSpouse() == null) {
			modifiedEmployee.setSpFirstName("");
			modifiedEmployee.setSpLastName("");
			modifiedEmployee.setChildren(0);
		}
        if(StringUtils.isNotEmpty(model.getHiddEmailId()))
        	modifiedEmployee.setEmpemail(model.getHiddEmailId());
        if(StringUtils.isNotEmpty(model.getHiddEmailId()))
        	modifiedEmployee.setEmpmobno(model.getHiddMobNo());
      
        if (!model.hasValidationErrors()) {

            model.saveEditedInformation(modifiedEmployee);

            return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("citizen.editProfile.successMsg")));

        }
        return defaultMyResultEdit(model);
    }

    private ModelAndView defaultMyResultEdit(final CitizenHomeModel model) {
        model.getAppSession().setLangId(UserSession.getCurrent().getLanguageId());
        model.getCurrentEmpForEditProfile().setEmpemail(model.getHiddEmailId());
             model.getCurrentEmpForEditProfile().setEmpmobno(model.getHiddMobNo());
        final ModelAndView mv = new ModelAndView("CitizenEditProfileValidn", MainetConstants.FORM_NAME, model);

        final BindingResult bindingResult = model.getBindingResult();

        if (bindingResult != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        }

        return mv;
    }

    private boolean isRestrictClickJacking() {
        Boolean restrictClickJacking = false;
        final String restrictClickJackingCheck = getApplicationSession()
                .getMessage(MainetConstants.SECURITY_FLAG.CLICK_JACKING_CHECK);
        if ((restrictClickJackingCheck != null) && restrictClickJackingCheck.equals(MainetConstants.YES)) {
            restrictClickJacking = true;
        }
        return restrictClickJacking;
    }

    @RequestMapping(method = RequestMethod.POST, params = "sessionHit")
    @ResponseBody
    public String SessionActivation() {
        return "hit";
    }

    @RequestMapping(method = RequestMethod.GET, params = "editAgencyLoginUserProfile")
    public ModelAndView editAgencyLoginUserProfile() {
        final Employee currentEmployee = UserSession.getCurrent().getEmployee();
        getModel().setCurrentEmpForEditProfile(currentEmployee);
        getModel().setEmpGender();
        if (currentEmployee.getEmpMName() == null) {
            getModel().setEmpNameForEditProfile(
                    currentEmployee.getEmpname() + MainetConstants.WHITE_SPACE + currentEmployee.getEmpLName());
        } else {
            getModel().setEmpNameForEditProfile(
                    currentEmployee.getEmpname() + MainetConstants.WHITE_SPACE + currentEmployee.getEmpMName()
                            + MainetConstants.WHITE_SPACE
                            + currentEmployee.getEmpLName());
        }
        LOG.info("after setCurrentEmpForEditProfile method executed");
        return new ModelAndView("EditAgencyLoginProfile", MainetConstants.FORM_NAME, getModel());
    }

    private String getLoginUserType() {

        String loginUserType = null;
        final Long emplType = UserSession.getCurrent().getEmployee().getEmplType();
        if (null != emplType) {
            final Organisation organisation = ApplicationSession.getInstance().getSuperUserOrganization();
            final List<LookUp> emplLookUps = CommonMasterUtility.getListLookup(MainetConstants.NEC.PARENT, organisation);
            for (final LookUp lookUp : emplLookUps) {
                if (lookUp.getLookUpId() == emplType) {

                    if (MainetConstants.NEC.CITIZEN.equals(lookUp.getLookUpCode())) {
                        loginUserType = MainetConstants.NEC.CITIZEN;
                        break;
                    }
                }
            }
            if (loginUserType == null) {
                loginUserType = MainetConstants.QueryAttributes.AGENCY;
            }
        }
        return loginUserType;
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "budgetFinance")
    public ModelAndView budgetFinance() {
        return new ModelAndView("BudgetFinance", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "quickLinks")
    public ModelAndView quickLinks() {
        return new ModelAndView("QuickLinks", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "importantLinks")
    public ModelAndView importantLinks() {
        return new ModelAndView("ImportantLinks", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "publicNotices")
    public ModelAndView publicNotices() {
        return new ModelAndView("PublicNotices", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "newsAndEvent")
    public ModelAndView newsAndEvent() {

        return new ModelAndView("NewsAndEvent", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "usefullLink")
    public ModelAndView newLink() {

        return new ModelAndView("UsefullLink", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.GET, params = "schemes")
    public ModelAndView schemes() {

        return new ModelAndView("Schemes", MainetConstants.FORM_NAME, getModel());
    }
	
    /* ---------- KDMC starts ---------- */
    @RequestMapping(method = RequestMethod.GET, params = "contactUs")
    public ModelAndView contactUs() {

        return new ModelAndView("ContactUs", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "impNotices")
    public ModelAndView impNotices() {
    	if(getModel().getPublicNotices() ==null || getModel().getPublicNotices().isEmpty()) {
    	 getModel().getAllNotices();
    	}
    	LOG.info("after impNotices method executed");
        return new ModelAndView("ImportantNotices", MainetConstants.FORM_NAME, getModel());
    }
    
    
    @RequestMapping(method = RequestMethod.GET, params = "tenderNotices")
    public ModelAndView tenderNotices() {

        return new ModelAndView("TenderNotices", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "onGoingProjects")
    public ModelAndView onGoingProjects() {
    	LOG.info("after onGoingProjects method executed");
        return new ModelAndView("OnGoingProjects", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "getQuotationNotices")
    public ModelAndView getQuotationNotices() {
    	LOG.info("after getQuotationNotices method executed");
        return new ModelAndView("QuotationsNoticeList", MainetConstants.FORM_NAME, getModel());
    }
    
    /* ---------- KDMC ends ---------- */

    @RequestMapping(method = RequestMethod.POST, params = "getChecklist")
    public ModelAndView fetchChecklist(final HttpServletRequest request, @RequestParam("serviceCode") String serviceCode,
            @RequestParam("serviceUrl") String serviceUrl, @RequestParam("serviceName") String serviceName) {
        getModel().setServiceURL(serviceUrl);
        getModel().setServiceName(serviceName);
		getModel().setServiceCode(serviceCode);
        final WSRequestDTO initReqDto = new WSRequestDTO();
        initReqDto.setModelName(MainetConstants.Property.CHECK_LIST_MODEL);
        final WSResponseDTO initResponse = iCommonBRMSService.initializeModel(initReqDto);
        if (initResponse.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(initResponse.getWsStatus())) {
            final List<Object> checklistObj = JersyCall.castResponse(initResponse, CheckListModel.class, 0);
            final CheckListModel checkListModel = (CheckListModel) checklistObj.get(0);

            checkListModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            checkListModel.setServiceCode(serviceCode);

            // BRMS call for fetching checklist
            List<DocumentDetailsVO> checkListList = iCommonBRMSService.getChecklist(checkListModel);

            // set document serial number
            Long docSerialNo = 1L;
            for (final DocumentDetailsVO docSr : checkListList) {
                docSr.setDocumentSerialNo(docSerialNo);
                docSerialNo++;
            }
            this.getModel().setCheckList(checkListList);
        }
        return new ModelAndView("serviceCheckList", MainetConstants.FORM_NAME, getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = "storeServiceURL")
    public void storeServiceURL(@RequestParam(value = "serviceURL") String serviceURL, HttpServletRequest request) {
        this.getModel().setService("Y");
        request.getSession().setAttribute("serviceURL", serviceURL);

    }

    @RequestMapping(method = RequestMethod.GET, params = "applicationStatus")
    public ModelAndView getApplicationStatus(HttpServletRequest request) {
        return new ModelAndView("ApplicationStatusForm", MainetConstants.FORM_NAME, getModel());

    }
    
    @RequestMapping(method = RequestMethod.GET, params = "wardOffice")
    public ModelAndView getApplicationStatusWithLogin(HttpServletRequest request) {
        return new ModelAndView("ApplicationStatusFormLogin", MainetConstants.FORM_NAME, getModel());

    }
    @RequestMapping(method = RequestMethod.POST, params = "viewFormHistoryDetails")
    public ModelAndView showHistoryDetails(@RequestParam("appId") final Long appId,
            final HttpServletRequest httpServletRequest, ModelMap modelMap) {
        ApplicationStatusDTO appstatus = applicationService.getApplicationStatus(appId,
                UserSession.getCurrent().getLanguageId());
        modelMap.put(APPSTATUS, appstatus);
        return new ModelAndView(ACTION_HISTORY, MainetConstants.FORM_NAME, modelMap);
    }
    @RequestMapping(method = RequestMethod.POST, params = "viewFormHistoryDetailslogin")
    public ModelAndView showHistoryDetailsLogin(@RequestParam("appId") final Long appId,
            final HttpServletRequest httpServletRequest, ModelMap modelMap) {
        ApplicationStatusDTO appstatus = applicationService.getApplicationStatus(appId,
                UserSession.getCurrent().getLanguageId());
        modelMap.put(APPSTATUS, appstatus);
        return new ModelAndView(ACTION_HISTORY_LOGIN, MainetConstants.FORM_NAME, modelMap);
    }
    @RequestMapping(params = "subscribe", method = RequestMethod.POST)
    public @ResponseBody String subscribe(HttpServletRequest request,@RequestParam(value ="email") String email) {
        this.bindModel(request);
        String isSubscribe=null;
        NewsLetterSubscriptionDTO subscription = this.getModel().getSubscription();
        subscription.setEmailId(email);
        subscription.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        subscription.setSubscriptionStartDate(new Date());
        subscription.setStatus(MainetConstants.IsLookUp.STATUS.YES);
        boolean result = subscriptionService.validateSubscriber(subscription);
        if (result) {
            subscriptionService.subscribeNewsLetter(subscription);
            isSubscribe="success";
        } else {
         isSubscribe="fail";
        }
        return isSubscribe;
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "forgotOTPView")
    public ModelAndView forgotOTPView(HttpServletRequest request) {
        return new ModelAndView("ForgotOTPView", MainetConstants.FORM_NAME, getModel());

    }
    
    
    @RequestMapping(method = RequestMethod.GET, params = "OfficerProfile")
    public ModelAndView mayorProfile(@RequestParam(value ="profileType") String profileType) {
    	if(profileType!=null && profileType.contentEquals(MainetConstants.DEPT_SHORT_NAME.MAYOR_PROFILE)) {
    		getModel().setMayorOrCommisnrFlag(MainetConstants.DEPT_SHORT_NAME.MAYOR);
    	}else if(profileType!=null && profileType.contentEquals(MainetConstants.DEPT_SHORT_NAME.COMMISSIONER_PROFILE)){//commissioner profile
    		getModel().setMayorOrCommisnrFlag(MainetConstants.DEPT_SHORT_NAME.DEPUTY_MAYOR);
    	}else {
    		//if  any
    	}
    	LOG.info("before CommissinorAndMayorProfileView method executed");
        return new ModelAndView("CommissinorAndMayorProfileView", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "BillpaymentForm")
    public ModelAndView getbillpaymentform(HttpServletRequest request) {
        return new ModelAndView("BillpaymentForm", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "ApplicationstatusForm")
    public ModelAndView getapplicationstatusform(HttpServletRequest request) {
        return new ModelAndView("ApplicationstatusForm", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "GrievancestatusForm")
    public ModelAndView getgrievancestatusform(HttpServletRequest request) {
        return new ModelAndView("GrievancestatusForm", MainetConstants.FORM_NAME, getModel());
    }
}