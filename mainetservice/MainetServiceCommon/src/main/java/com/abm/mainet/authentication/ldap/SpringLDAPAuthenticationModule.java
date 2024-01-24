package com.abm.mainet.authentication.ldap;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.LdapTemplate;

public class SpringLDAPAuthenticationModule implements IAuthenticationModule {
    private static final Logger LOGGER = Logger.getLogger(SpringLDAPAuthenticationModule.class);
    private String userBaseDN;
    private String userAccountKey;
    private LdapTemplate ldapTemplate;

    @Override
    public void authenticateUser(final String userName, final String pwd) {
        boolean isUserAuthenticated = false;
        try {
            isUserAuthenticated = ldapTemplate.authenticate("", "(" + getUserAccountKey() + "=" + userName + ")", pwd);
        } catch (final Exception ex) {
            LOGGER.error("Exception occured while authenticating user against LDAP", ex);
        }
        if (!isUserAuthenticated) {
            throw new RuntimeException("Could not authenticate user: " + userName);
        }
    }

    public String getUserBaseDN() {
        return userBaseDN;
    }

    public void setUserBaseDN(final String userBaseDN) {
        this.userBaseDN = userBaseDN;
    }

    public String getUserAccountKey() {
        return userAccountKey;
    }

    public void setUserAccountKey(final String userAccountKey) {
        this.userAccountKey = userAccountKey;
    }

    /**
     * @param ldapTemplate the ldapTemplate to set
     */
    public void setLdapTemplate(final LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

}
