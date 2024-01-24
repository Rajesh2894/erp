package com.abm.mainet.common.authentication.ldap;

/**
 * Interface for authentication module. This would act facade between authentication module and portal
 */

public interface IAuthenticationModule {
    void authenticateUser(String user, String pwd);
}
