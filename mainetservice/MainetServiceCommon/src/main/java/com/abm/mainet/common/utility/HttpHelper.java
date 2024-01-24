 package com.abm.mainet.common.utility;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.Common_Constant;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.WebProperties.PROPERTIES_CATEGORY;

public class HttpHelper {
    public static boolean isAjaxRequest(final HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static int getSessionLanguageId(final HttpServletRequest request) {
        final UserSession userSession = UserSession.getCurrent();
        int langId = 0;
        final HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute(MainetConstants.PRE_LOGIN_LANGUAGE_ID) != null) {
                langId = ((Integer) session.getAttribute(MainetConstants.PRE_LOGIN_LANGUAGE_ID)).intValue();
            } else {
                langId = userSession.getLanguageId();
            }
        }
        return langId;
    }

    public static void setLanguage(final HttpServletRequest request, final HttpServletResponse response, final Organisation org,
            final boolean isPostLogin) {
        setLanguage(request, response, Utility.getDefaultLanguageId(org), isPostLogin);
    }

    public static void setLanguage(final HttpServletRequest request, final HttpServletResponse response,
            final String localeString,
            final boolean isPostLogin) {
        setLanguage(request, response, Utility.getLanguageId(localeString), localeString, isPostLogin);
    }

    public static void setLanguage(final HttpServletRequest request, final HttpServletResponse response, final int langId,
            final boolean isPostLogin) {
        setLanguage(request, response, langId, Utility.getLocaleString(langId), isPostLogin);
    }

    private static void setLanguage(final HttpServletRequest request, final HttpServletResponse response, final int langId,
            final String localeString,
            final boolean isPostLogin) {
        final UserSession userSession = UserSession.getCurrent();
        // Update the UserSession language Id
        userSession.setLanguageId(langId);
        if (!isPostLogin) {
            // Set language id in session
            request.getSession(false).setAttribute(MainetConstants.PRE_LOGIN_LANGUAGE_ID, Integer.valueOf(langId));
        }
        // Set locale for Spring components
        final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(localeString));
    }

    public static boolean isUserLoggedIn(final HttpServletRequest request, final Long userId) {
        if (null != userId) {
            final ServletContext context = request.getServletContext();
            @SuppressWarnings("unchecked")
            final Map<Long, String> loggedInUserMap = (Map<Long, String>) context.getAttribute(MainetConstants.LOGGED_IN_USER_MAP);
            final String sessionId = loggedInUserMap.get(userId);
            if (null == sessionId) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static void addLoggedInUser(final HttpServletRequest request, final Long userId) {
        if (null != userId) {
            final ServletContext context = request.getServletContext();
            final String sessionId = request.getSession(false).getId();
            @SuppressWarnings("unchecked")
            final Map<Long, String> loggedInUserMap = (Map<Long, String>) context.getAttribute(MainetConstants.LOGGED_IN_USER_MAP);
            loggedInUserMap.put(userId, sessionId);
            @SuppressWarnings("unchecked")
            final Map<String, Long> loggedInSessionMap = (Map<String, Long>) context.getAttribute(MainetConstants.LOGGED_IN_SESSION_ID_MAP);
            loggedInSessionMap.put(sessionId, userId);
        }
    }

    public static void removeLoggedInUser(final HttpServletRequest request, final Long userId) {
        final ServletContext context = request.getServletContext();
        final String sessionId = request.getSession(false).getId();
        @SuppressWarnings("unchecked")
        final Map<String, Long> loggedInSessionMap = (Map<String, Long>) context.getAttribute(MainetConstants.LOGGED_IN_SESSION_ID_MAP);
        loggedInSessionMap.remove(sessionId);
        if (null != userId) {
            @SuppressWarnings("unchecked")
            final Map<Long, String> loggedInUserMap = (Map<Long, String>) context.getAttribute(MainetConstants.LOGGED_IN_USER_MAP);
            loggedInUserMap.remove(userId);
        }
    }

    public static void removeLoggedInUser(final HttpSession session) {
        final ServletContext context = session.getServletContext();
        final String sessionId = session.getId();
        @SuppressWarnings("unchecked")
        final Map<String, Long> loggedInSessionMap = (Map<String, Long>) context.getAttribute(MainetConstants.LOGGED_IN_SESSION_ID_MAP);
        final Long userId = loggedInSessionMap.get(sessionId);
        if (null != userId) {
            loggedInSessionMap.remove(sessionId);
            @SuppressWarnings("unchecked")
            final Map<Long, String> loggedInUserMap = (Map<Long, String>) context.getAttribute(MainetConstants.LOGGED_IN_USER_MAP);
            loggedInUserMap.remove(userId);
        }
    }

    public static Long getLoggedInEmpId(final HttpSession session) {
        final ServletContext context = session.getServletContext();
        final String sessionId = session.getId();
        @SuppressWarnings("unchecked")
        final Map<String, Long> loggedInSessionMap = (Map<String, Long>) context.getAttribute(MainetConstants.LOGGED_IN_SESSION_ID_MAP);
        return loggedInSessionMap.get(sessionId);
    }

    /**
     * Returns the domain name configured for the organisation Id passed in the request. It gets the domain name from
     * uLBDomain.properties against property &lt;orgId&gt;.domain.name
     * @param orgId Organization ID for which domain name is to be retrieved
     * @return domain name
     */
    public static String getDomainURL(final String orgId) {
        final WebProperties uLBDomainProperties = ApplicationContextProvider.getApplicationContext().getBean(MainetConstants.WEB_PROPERTIES,
                WebProperties.class);
        return uLBDomainProperties.getProperty(WebProperties.PROPERTIES_CATEGORY.ULB_DOMAIN, orgId + MainetConstants.DOMAIN_URL);
    }

    /**
     * Returns the domain name configured for the organisation Id passed in the request. It gets the domain name from
     * uLBDomain.properties against property &lt;orgId&gt;.domain.name. If returnDefault is true then, if domain for orgId is not
     * found it returns the default domain URL configured in uLBDomain.properties.
     * @param orgId Organization ID for which domain name is to be retrieved
     * @param returnDefault if set to true, this method will return default domainURL configured in uLBDomain.properties
     * @return domain name
     */
    public static String getDomainURL(final String orgId, final boolean returnDefault) {
        final WebProperties uLBDomainProperties = ApplicationContextProvider.getApplicationContext().getBean(MainetConstants.WEB_PROPERTIES,
                WebProperties.class);
        String domainURL = uLBDomainProperties.getProperty(WebProperties.PROPERTIES_CATEGORY.ULB_DOMAIN, orgId + MainetConstants.DOMAIN_URL);
        if (((null == domainURL) || domainURL.isEmpty()) && returnDefault) {
            domainURL = uLBDomainProperties.getProperty(WebProperties.PROPERTIES_CATEGORY.ULB_DOMAIN, MainetConstants.DEFAULT_DOMAIN_URL);
        }
        return domainURL;
    }

    public static String getOrgIdBasedOnDomain(final HttpServletRequest request) {
        final WebProperties uLBDomainProperties = ApplicationContextProvider.getApplicationContext().getBean(MainetConstants.WEB_PROPERTIES,
                WebProperties.class);
        return uLBDomainProperties.getProperty(WebProperties.PROPERTIES_CATEGORY.ULB_DOMAIN, request.getServerName() + Common_Constant.ORG_ID);
    }

    /**
     * Returns the domain name configured based on the server name passed in the request. It gets the domain name from
     * uLBDomain.properties against property &lt;orgId&gt;.domain.url
     * @param orgId Organization ID for which domain name is to be retrieved
     * @param request Servlet request
     * @return domain name
     */
    public static String getDomainURL(final HttpServletRequest request) {
        // could have used getDomainURL() method but since WebProperties will
        // be required used raw version
        final WebProperties uLBDomainProperties = ApplicationContextProvider.getApplicationContext().getBean(MainetConstants.WEB_PROPERTIES,
                WebProperties.class);
        final String orgIdStr = uLBDomainProperties.getProperty(PROPERTIES_CATEGORY.ULB_DOMAIN,
                request.getServerName() + Common_Constant.ORG_ID);
        String defaultDomainURL = uLBDomainProperties.getProperty(WebProperties.PROPERTIES_CATEGORY.ULB_DOMAIN,
                orgIdStr + MainetConstants.DOMAIN_URL);
        if ((null == defaultDomainURL) || defaultDomainURL.isEmpty()) {
            defaultDomainURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/";
        }
        return defaultDomainURL;
    }

    /**
     * Returns the complete URL along with the server name. It returns null if domain URL is not configured for organization Id in
     * uLBDomain.properties
     * @param orgId Organization for which URL is required
     * @param resourcePath path of resource for which URL is required
     * @return null if domain is not configured or resourcePath is null else the complete URL
     */
    public static String getCompleteURL(final String orgId, final String resourcePath) {
        final String domainUrl = getDomainURL(orgId);
        String retVal = null;
        String tempResourcePath = null;
        if (null != resourcePath) {
            final StringBuffer tempBuf = new StringBuffer(resourcePath.trim());
            tempResourcePath = tempBuf.toString();
            if (tempBuf.charAt(0) == '/') {
                tempResourcePath = tempBuf.substring(1, tempBuf.length());
            }
        }
        if ((null != domainUrl) && !domainUrl.isEmpty() && (null != tempResourcePath)) {
            if (domainUrl.endsWith("/")) {
                retVal = domainUrl.substring(0, domainUrl.lastIndexOf('/'));
            }
            retVal = retVal + "/" + tempResourcePath;
        }
        return retVal;
    }

    /**
     * Returns the request URL based on the domain configured for organization if configured, else tries to build the domain URL
     * based on server name in HTTP request. If none is configured it returns the output of request.getRequestURL.
     * @param orgId Organization Id
     * @param request HTTP request
     * @return Returns the request URL based on the domain configured for organization if configured, else returns the output of
     * request.getRequestURL.
     */
    public static String getRequestURL(final Long orgId, final HttpServletRequest request) {
        final StringBuffer origURL = request.getRequestURL();
        final String servletPath = request.getServletPath();
        StringBuffer finalURL = new StringBuffer(origURL);
        if ((null != orgId) && (orgId.longValue() != 0)) {
            String domainURL = getDomainURL(orgId.toString());
            // if Organization specific domain URL is not configured, get domain
            // URL based on server name from HTTP request
            if ((null == domainURL) || domainURL.isEmpty()) {
                domainURL = getDomainURL(request);
            }
            // if domain name is not configured at all then use
            // request.getServletURL() as default
            if ((null != domainURL) && !domainURL.isEmpty()) {
                if (servletPath.isEmpty() || (origURL.indexOf(servletPath) == -1)) {
                    finalURL = new StringBuffer(domainURL);
                } else {
                    finalURL = origURL.replace(0, origURL.indexOf(servletPath) + 1, domainURL);
                }
            }
        }
        return finalURL.toString();
    }
}
