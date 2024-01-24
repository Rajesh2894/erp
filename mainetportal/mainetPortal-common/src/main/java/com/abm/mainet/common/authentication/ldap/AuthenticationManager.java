package com.abm.mainet.common.authentication.ldap;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;

public class AuthenticationManager implements IAuthenticationManager {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationManager.class);
    private IAuthenticationModule authModule;
    private String useLdap = MainetConstants.IsDeleted.NOT_DELETE;

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
        if (useLdap.equals(MainetConstants.IsDeleted.DELETE)) {
            try {
                authModule.authenticateUser(userName, pwd);
            } catch (final Exception ex) {
                LOGGER.error(MainetConstants.AUTH_USER_ERROR, ex);
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
