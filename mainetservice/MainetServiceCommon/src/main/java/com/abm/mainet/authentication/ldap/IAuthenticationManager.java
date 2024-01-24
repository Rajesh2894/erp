package com.abm.mainet.authentication.ldap;

/**
 * Interface for authentication manager.
 */

public interface IAuthenticationManager {
    /**
     *
     * @param userName
     * @param pwd
     * @throws Exception
     */
    boolean authenticateUser(String userName, String pwd);

    /**
     *
     * @param userName
     * @return
     */
    String getAuthToken(String userName);
}
