package com.abm.mainet.common.authentication.ldap;

public interface ILDAPManager {
    String createUser(UserProfile userProfile);

    void assignRole(String uid, String role);

    void createUserRole(String role);

    void updateUser(UserProfile userProfile);
}
