package com.abm.mainet.authentication.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapName;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.LdapTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

public class LDAPManager implements ILDAPManager {
    private static final Logger LOGGER = Logger.getLogger(LDAPManager.class);
    private String userBaseDN;
    private String userAccountKey;
    private LdapTemplate ldapTemplate;
    private String useLdap = "N";

    public String getUseLdap() {
        return useLdap;
    }

    public void setUseLdap(final String useLdap) {
        this.useLdap = useLdap;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ILDAPManager#createUser(com.abm.mainet.model.UserProfile)
     */
    @Override
    public String createUser( UserProfile userProfile) {
    	String uid = null;
    	useLdap =  ApplicationSession.getInstance().getMessage("ldap.enabled");
        if (MainetConstants.Common_Constant.YES.equals(useLdap)) {
            try {
            	long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
            	userProfile.setuID(userProfile.getuID()+MainetConstants.operator.UNDER_SCORE +orgId);
                final LdapName distinguishedName = new LdapName("cn=" + userProfile.getuID());
                final Attributes attrs = new BasicAttributes();
                Attribute attr = new BasicAttribute("givenName", userProfile.getFirstName() + " " + userProfile.getLastName());
                attrs.put(attr);
                attr = new BasicAttribute("userPassword", userProfile.getPwd());
                attrs.put(attr);
                attr = new BasicAttribute("uid", userProfile.getuID());
                attrs.put(attr);
                attr = new BasicAttribute("sn", userProfile.getLastName());
                attrs.put(attr);
                attr = new BasicAttribute("description", userProfile.getRole());
                attrs.put(attr);
                attr = new BasicAttribute("employeeType", userProfile.getUserType());
                attrs.put(attr);
                attr = new BasicAttribute("objectClass", "person");
                attr.add("organizationalPerson");
                attr.add("inetOrgPerson");
                attrs.put(attr);
                System.out.println(distinguishedName.toString());
                ldapTemplate.bind(distinguishedName, null, attrs);
                uid = userProfile.getuID();
            } catch (final Exception e) {
                LOGGER.error("error occured while ", e);
                throw new RuntimeException("Error while creating user in LDAP", e);
            }
        }
        return uid;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ILDAPManager#assignRoles(java.lang.String, java.util.List)
     */
    @Override
    public void assignRole(String uid, final String role) {
    	useLdap =  ApplicationSession.getInstance().getMessage("ldap.enabled");
        if (MainetConstants.Common_Constant.YES.equals(useLdap)) {
            try {
            	long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
            	uid = uid+MainetConstants.operator.UNDER_SCORE +orgId;
                final LdapName distinguishedName = new LdapName("cn=" + uid);
                final ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("description", role));
                System.out.println(distinguishedName.toString());
                ldapTemplate.modifyAttributes(distinguishedName, new ModificationItem[] { item });
            } catch (final Exception e) {
                LOGGER.error("error occured while ", e);
                throw new RuntimeException("Error while assigning role in LDAP", e);
            }
        }
        return;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ILDAPManager#createUserRole()
     */
    @Override
    public void createUserRole(final String role) {

    }

    /**
     * @return the userBaseDN
     */
    public String getUserBaseDN() {
        return userBaseDN;
    }

    /**
     * @param userBaseDN the userBaseDN to set
     */
    public void setUserBaseDN(final String userBaseDN) {
        this.userBaseDN = userBaseDN;
    }

    /**
     * @return the userAccountKey
     */
    public String getUserAccountKey() {
        return userAccountKey;
    }

    /**
     * @param userAccountKey the userAccountKey to set
     */
    public void setUserAccountKey(final String userAccountKey) {
        this.userAccountKey = userAccountKey;
    }

    /**
     * @return the ldapTemplate
     */
    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    /**
     * @param ldapTemplate the ldapTemplate to set
     */
    public void setLdapTemplate(final LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.auth.ldap.ILDAPManager#updateUser(com.abm.mainet.model.UserProfile)
     */
    @Override
    public void updateUser(UserProfile userProfile) {
    	useLdap =  ApplicationSession.getInstance().getMessage("ldap.enabled");
        if (MainetConstants.Common_Constant.YES.equals(useLdap)) {
            try {
            	long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
            	userProfile.setuID(userProfile.getuID()+MainetConstants.operator.UNDER_SCORE +orgId);
                final LdapName distinguishedName = new LdapName("cn=" + userProfile.getuID());
                final List<ModificationItem> items = new ArrayList<>();
                final ModificationItem[] itemArr = new ModificationItem[] {};
                ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("description", userProfile.getRole()));
                items.add(item);
                item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("givenName", userProfile.getFirstName() + " " + userProfile.getLastName()));
                items.add(item);
                item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("userPassword", userProfile.getPwd()));
                items.add(item);
                item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("uid", userProfile.getuID()));
                items.add(item);
                item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", userProfile.getLastName()));
                items.add(item);
                item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("employeeType", userProfile.getUserType()));
                items.add(item);
                System.out.println(distinguishedName.toString());
                ldapTemplate.modifyAttributes(distinguishedName, items.toArray(itemArr));
            } catch (final Exception e) {
                LOGGER.error("error occured while ", e);
                throw new RuntimeException("Error while assigning role in LDAP", e);
            }
        }
        return;
    }

}
