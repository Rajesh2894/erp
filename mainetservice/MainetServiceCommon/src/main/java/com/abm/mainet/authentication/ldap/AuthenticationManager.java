package com.abm.mainet.authentication.ldap;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;

public class AuthenticationManager implements IAuthenticationManager {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationManager.class);
    private IAuthenticationModule authModule;
    private String useLdap = "N";

    public String getUseLdap() {
        return useLdap;
    }

    public void setUseLdap(final String useLdap) {
        this.useLdap = useLdap;
    }

    public IAuthenticationModule getAuthModule() {
        return authModule;
    }

    public void setAuthModule(final IAuthenticationModule authModule) {
        this.authModule = authModule;
    }

    @Override
    public boolean authenticateUser(final String userName, final String pwd) {
        boolean isUserAuthenticated = true;
        useLdap =  ApplicationSession.getInstance().getMessage("ldap.enabled");
        if (MainetConstants.Common_Constant.YES.equals(useLdap)) {
            try {
                authModule.authenticateUser(userName, pwd);
            } catch (final Throwable ex) {
                LOGGER.error("Error occurred while authenticating user", ex);
                isUserAuthenticated = false;
            }
        }
        return isUserAuthenticated;
    }

    @Override
    public String getAuthToken(final String userName) {
        return null;
    }

}
