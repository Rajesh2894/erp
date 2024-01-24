package com.abm.mainet.authentication.ldap;

public class MockLDAPManager implements ILDAPManager {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ILDAPManager#createUser(com.abm.mainet.model.UserProfile)
     */
    @Override
    public String createUser(final UserProfile userProfile) {
        return userProfile.getuID();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ILDAPManager#assignRoles(java.lang.String, java.util.List)
     */
    @Override
    public void assignRole(final String uid, final String role) {

        return;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ILDAPManager#createUserRole()
     */
    @Override
    public void createUserRole(final String role) {

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ldap.ILDAPManager#updateUser(com.abm.mainet.model.UserProfile)
     */
    @Override
    public void updateUser(final UserProfile userProfile) {

        return;
    }

}
