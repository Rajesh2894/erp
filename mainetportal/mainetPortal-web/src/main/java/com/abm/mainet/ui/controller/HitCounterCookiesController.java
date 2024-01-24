package com.abm.mainet.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.cms.ui.listener.SessionListener;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEmployeeService;
import org.apache.log4j.Logger;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
/*import com.abm.mainet.ao2.smart.dao.util.Utility;
import com.abm.mainet.constants.MainetConstants;*/
import com.abm.mainet.domain.PageMaster;
import com.abm.mainet.eip.service.IHitCounterService;

@Controller
@RequestMapping("/HitCounterCookies.html")
public class HitCounterCookiesController {
    @Autowired 
    private IHitCounterService iHitCounterService;

    @Autowired
    private IEmployeeService iEmployeeService;
    private static final Logger LOGGER = Logger.getLogger(HitCounterCookiesController.class);
    @RequestMapping(method = RequestMethod.GET)
	/*
	 * public void index(final HttpServletRequest request, final HttpServletResponse
	 * response, @RequestParam final Long pageId) {
	 */
       
    	public void index(final HttpServletRequest request, final HttpServletResponse response) {
    	LOGGER.info("HitCounter Method Start Here");
    	final Long pageId = UserSession.getCurrent().getOrganisation().getOrgid();
        final PageMaster master = iHitCounterService.getPageMaster(pageId);
        boolean isNewCookie = true;
        final Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            for (final Cookie cookie : cookies) {
                if (null != cookie.getName()) {
                    if (cookie.getName().equalsIgnoreCase(pageId.toString())
                            && cookie.getValue().equalsIgnoreCase(
                                    pageId.toString())) {
                        isNewCookie = false;
                        break;
                    }
                }
            }
        }
        
        
       
        if ((master != null) && isNewCookie) {
            iHitCounterService.updateCount(pageId, master);
            createCookie(request, response, pageId, master);
        }
        if (master == null) {
            /* synchronized (this) { */
            PageMaster pageCount = new PageMaster();
            pageCount.setOrg(pageId);
            pageCount.setPageName("CitizenHome.html");

            iHitCounterService.updateCount(pageId, pageCount);

            createCookie(request, response, pageId, master);
            // }
        }

        final long count = iHitCounterService.getFinalCountOfHits(pageId);
        Cookie cookie = new Cookie("countuser", count + MainetConstants.BLANK);
        cookie.setHttpOnly(false);
       
        request.getSession().setAttribute("countuser", count + MainetConstants.BLANK);// df# 121961
        response.addCookie(cookie);
     
        request.getSession().setAttribute("countuser2", count);// df# 121961

    }

    @RequestMapping(params = "userActivity")
    public @ResponseBody Map<String, Long> getUserActivityCount(Model model) {

        Map<String, Long> resultMap = new ConcurrentHashMap<String, Long>();

        resultMap.put("totalRegisUser", iEmployeeService.findCountOfRegisteredEmployee());
        resultMap.put("loggedInUser", iEmployeeService.findCountOfLoggedInUser());
        resultMap.put("activeuser", Long.valueOf(SessionListener.getActiveSessions()));
        resultMap.put("countuser", iHitCounterService.getFinalCountOfHits(UserSession.getCurrent().getOrganisation().getOrgid()));
		/*
		 * if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)){
		 * resultMap.put("MarathiCount",
		 * iHitCounterService.getMyMarathiCount(UserSession.getCurrent().getOrganisation
		 * ().getOrgid())); }
		 */
        
        for(Map.Entry<String, Long> result: resultMap.entrySet()) {
            LOGGER.info("Map from resultMap Key: " + result.getKey()+ "Value: " +result.getValue());
        }
        

        
        return resultMap;

    }

    private void createCookie(final HttpServletRequest request, final HttpServletResponse response, final Long pageId,
            final PageMaster master) {
        final Cookie coKey = new Cookie(pageId.toString(), pageId.toString());
        coKey.setPath(request.getRequestURI());
       response.addCookie(coKey); 
       LOGGER.info("Hit Counter Method Ends Here");
    }
    
    
	/*
	 * @RequestMapping(params = "updateMyMarathiCount") public @ResponseBody boolean
	 * updateMyMarathiCount() { return
	 * iHitCounterService.updateMyMarathiCount(UserSession.getCurrent().
	 * getOrganisation().getOrgid());
	 * 
	 * }
	 */
}
