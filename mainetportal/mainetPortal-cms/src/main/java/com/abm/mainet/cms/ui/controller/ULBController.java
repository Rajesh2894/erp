package com.abm.mainet.cms.ui.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.HttpHelper;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;

/**
 * Serves the request for /ULBHome.html. Sets the ULB specific organization and default language.

 *
 */
@Controller
@RequestMapping("/ULBHome.html")
public class ULBController {
    private static final Logger logger = Logger.getLogger(ULBController.class);

    /**
     * Session attribute name against which requested or current ULB specific host name is stored in session.
     */
    private static final String CURRENT_ULB_HOST_ATTR_NAME = "CURRENT_ULB_HOST";

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IEmployeeService iEmployeeService;
    /**
     * Serves /ULBHome.html GET request. It sets the default organization and language based on the request attribute
     * 'CURRENT_ULB_HOST'.
     * @param request Request as received by the container
     * @param response Response to be sent
     * @return Redirect URL which needs to be shown to the user
     */
    @RequestMapping(method = RequestMethod.GET)
    public String uLBHome(final HttpServletRequest request, final HttpServletResponse response) {
        final String organisationId = (String) request.getAttribute("orgId");
        logger.info("Inside uLBHome method session data >>  "+organisationId);
        return setOrganisationData(request, response, organisationId);
    }

    @RequestMapping(params = "resetULB", method = RequestMethod.GET)
    public String resetUlb(@RequestParam("orgId") final String orgId, final HttpServletRequest request,
            final HttpServletResponse response) {
        String organisationId = orgId;
        if (StringUtils.isBlank(organisationId)) {
            organisationId = String.valueOf(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        }
        logger.info("resetUlb method  call start >> "+organisationId);
        return setOrganisationData(request, response, organisationId);
    }

    private String setOrganisationData(final HttpServletRequest request, final HttpServletResponse response,
            final String organisationId) {
        if ((organisationId != null) && !organisationId.equalsIgnoreCase(MainetConstants.BLANK)
                && !organisationId.equalsIgnoreCase("-1") && StringUtils.isNumeric(organisationId)) {
            try {
                final UserSession userSession = UserSession.getCurrent();
                userSession.getMapList().clear();
              
                final HttpSession session = request.getSession(false);
                session.setAttribute(CURRENT_ULB_HOST_ATTR_NAME, request.getAttribute(CURRENT_ULB_HOST_ATTR_NAME));
             
                final long orgId = Long.valueOf(organisationId.trim());

                setSessionAttributeValue(request, response, orgId, userSession);
                userSession.setContextName(
                        request.getContextPath());
                logger.info("session data inside setOrganisationData >> "+userSession.getCurrent().getOrganisation());
                if (userSession.getOrganisation() != null) {
                    final String orgCode = userSession.getOrganisation()
                            .getOrgShortNm();
                    final String msgKey = MainetConstants.EIP_WARD + orgCode + ".cordcount";
                    final String wardvalue = ApplicationContextProvider.getApplicationContext().getMessage(msgKey, (Object[]) null, msgKey, LocaleContextHolder.getLocale());
                    if ((wardvalue != null) && !wardvalue.isEmpty()
                            && wardvalue.matches(MainetConstants.NUMBER_REGEX)) {
                        final Long wardCount = Long.valueOf(wardvalue);
                        final Map<String, String> mapCount = new LinkedHashMap<>(
                                0);
                        for (int i = 1; i <= wardCount; i++) {
                            mapCount.put(MainetConstants.EIP_WARD + orgCode + ".coordinate"
                                    + i, MainetConstants.EIP_WARD + orgCode + ".ward" + i);
                        }
                        userSession.setMapList(mapCount);
                    }
                }
            } catch (final Exception exception) {
                logger.error("Exception occured while setting ULB information: ", exception);
            }

            String bypassUrl = request.getParameter("bypassUrl");
            String queryString = request.getQueryString();
            String modifiedQueryStringNew = queryString.replace("bypassUrl=", MainetConstants.BLANK).replace(MainetConstants.operator.AT_THE_RATE, MainetConstants.operator.AMPERSAND);
            logger.info("ByPass Url >>" +modifiedQueryStringNew + "By Pass Act Url "+bypassUrl);
            if(modifiedQueryStringNew.contains(MainetConstants.PRIVACY_POLICY) || modifiedQueryStringNew.contains(MainetConstants.WEBSITE_POLICY)) {
            	return "redirect:"+ modifiedQueryStringNew;
            }
            if (queryString != null) {
                String modifiedQueryString = queryString.replace("bypassUrl=", MainetConstants.BLANK).replace(MainetConstants.operator.AT_THE_RATE, MainetConstants.operator.AMPERSAND);
                if (modifiedQueryString.contains(MainetConstants.Property.PROP_QR_QUERY_STRING)|| modifiedQueryString.contains(MainetConstants.Property.WT_QR_QUERY_STRING)) {
                    return "redirect:/" + modifiedQueryString;
                }
            }
            logger.info("ByPass Url1 >>" +modifiedQueryStringNew + "By Pass Act Url "+bypassUrl);
            if(bypassUrl != null && isBypassUrl(bypassUrl)) {
            	 if(bypassUrl.contains(MainetConstants.operator.AT_THE_RATE)) {
            		 bypassUrl =	bypassUrl.replaceAll(MainetConstants.operator.AT_THE_RATE,MainetConstants.operator.AMPERSAND);
                 } 
            	 if(bypassUrl.contains(MainetConstants.LANG_ID) ) {
            		 String langIdParam= request.getParameter(MainetConstants.LANG_ID);
                 	int langId = 1;
                 	try {
                 	    langId = Integer.valueOf(langIdParam);
                 	}catch (Exception e) {
						langId = Utility.getDefaultLanguageId(UserSession.getCurrent().getOrganisation());
					}
            		Utility.changeLanguage(request, response, langId);
            	}
            	 return "redirect:"+bypassUrl;
            }
            logger.info("ByPass Url 2 >>" +modifiedQueryStringNew + "By Pass Act Url "+bypassUrl); 
            return "redirect:/CitizenHome.html";
        }
        return "redirect:/CitizenHome.html";
    }

    private void setSessionAttributeValue(final HttpServletRequest request, final HttpServletResponse response, final long orgId,
            final UserSession userSession) {
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        userSession.setOrganisation(organisation);
        HttpHelper.setLanguage(request, response, userSession.getOrganisation(), false);
        final String loginName = ApplicationContextProvider.getApplicationContext().getMessage("citizen.noUser.loginName", (Object[]) null, "citizen.noUser.loginName",
                LocaleContextHolder.getLocale());
        userSession.setEmployee(iEmployeeService.getEmployeeByLoginName(loginName, organisation, MainetConstants.IsDeleted.ZERO));
        if(userSession.getEmployee()!=null) {
        	userSession.getEmployee().setEmppiservername(Utility.getClientIpAddress(request));
        }

        logger.info("Organisation data inside setSessionAttributeValue "+userSession.getOrganisation() );
        UserSession.getCurrent().setQuickLinkReg(null);
        UserSession.getCurrent().setQuickLinkEng(null);
        UserSession.getCurrent().setLogoImagesList(null);
        UserSession.getCurrent().setSlidingImgLookUps(null);
    }

    @RequestMapping(params = "ulbDomain", method = RequestMethod.POST)
    public @ResponseBody String getOrgDomain(@RequestParam("orgId") final String orgId, final HttpServletRequest request,
            final HttpServletResponse response) {
        String url = HttpHelper.getDomainURL(orgId);
        if (null == url) {
            url = MainetConstants.BLANK;
        }
        return url;
    }
    
   
    private boolean isBypassUrl(final String url) {
        return  url.contains("/CitizenContactUs.html")
                || url.contains("/CitizenAboutUs.html")
                || url.contains("/CitizenFAQ.html")
                || url.contains("/CitizenHome.html");
    }
               
}