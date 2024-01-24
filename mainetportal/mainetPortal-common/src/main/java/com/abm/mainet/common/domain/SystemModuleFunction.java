package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * @author ritesh.patil
 *
 */
@Entity
@Table(name = "TB_SYSMODFUNCTION")
public class SystemModuleFunction implements Serializable {

    private static final long serialVersionUID = -1984306460097764417L;
    private Long smfid;
    private String smfname;
    private String smfname_mar;
    private String smfdescription;
    private String smfflag;
    private String smfaction;
    private Long updated_by;
    private Date updated_date;
    private String isdeleted;
    private Long hiddenEtId;
    private long user_id;
    private Date ondate;
    private String ontime;
    private Long lang_id;
    private String lg_ip_mac;
    private String lg_ip_mac_upd;
    private String smParam1;
    private String smParam2;
    private String smfcategory;
    private String smShortDesc;
    private SystemModuleFunction moduleFunction;
    private Set<SystemModuleFunction> menuHierarchicalList = new LinkedHashSet<>();
    private Set<RoleEntitlement> entitlements = new LinkedHashSet<>();
    private String smfname_eng;

    

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SMFID", nullable = false)
    public Long getSmfid() {
        return smfid;
    }

    public void setSmfid(final Long smfid) {
        this.smfid = smfid;
    }

    @Column(name = "SMFNAME", nullable = false)
    public String getSmfname() {
        return smfname;
    }

    public void setSmfname(final String smfname) {
        this.smfname = smfname;
    }

    @Column(name = "SMFDESCRIPTION")
    public String getSmfdescription() {
        return smfdescription;
    }

    public void setSmfdescription(final String smfdescription) {
        this.smfdescription = smfdescription;
    }

    @Column(name = "SMFFLAG", length = 2)
    public String getSmfflag() {
        return smfflag;
    }

    public void setSmfflag(final String smfflag) {
        this.smfflag = smfflag;
    }

    @Column(name = "SMFACTION")
    public String getSmfaction() {
        return smfaction;
    }

    public void setSmfaction(final String smfaction) {
        this.smfaction = smfaction;
    }

    @Column(name = "ISDELETED", length = 1)
    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

    @Column(name = "UPDATED_BY")
    public Long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(final Long updated_by) {
        this.updated_by = updated_by;
    }

    @Column(name = "UPDATED_DATE")
    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(final Date updated_date) {
        this.updated_date = updated_date;
    }

    @Column(name = "SMFNAME_MAR")
    public String getSmfname_mar() {
        return smfname_mar;
    }

    public void setSmfname_mar(final String smfname_mar) {
        this.smfname_mar = smfname_mar;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SM_PARENT_ID")
    public SystemModuleFunction getModuleFunction() {
        return moduleFunction;
    }

    public void setModuleFunction(
            final SystemModuleFunction moduleFunction) {
        this.moduleFunction = moduleFunction;
    }

    @OneToMany(mappedBy = "moduleFunction", fetch = FetchType.EAGER)
    // @OrderBy(value="smfid")
    @Where(clause = "isdeleted = '0'")
    public Set<SystemModuleFunction> getMenuHierarchicalList() {
        return menuHierarchicalList;
    }

    public void setMenuHierarchicalList(
            final Set<SystemModuleFunction> menuHierarchicalList) {
        this.menuHierarchicalList = menuHierarchicalList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entitle"/*
                                                            * , cascade= CascadeType.ALL
                                                            */)
    public Set<RoleEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(
            final Set<RoleEntitlement> entitlements) {
        this.entitlements = entitlements;
    }

    @Transient
    public Long getHiddenEtId() {
        return hiddenEtId;
    }

    public void setHiddenEtId(final Long hiddenEtId) {
        this.hiddenEtId = hiddenEtId;
    }

    @Column(name = "USER_ID", nullable = false)
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(final long user_id) {
        this.user_id = user_id;
    }

    @Column(name = "ONDATE", nullable = false)
    public Date getOndate() {
        return ondate;
    }

    public void setOndate(final Date ondate) {
        this.ondate = ondate;
    }

    @Column(name = "ONTIME", nullable = false)
    public String getOntime() {
        return ontime;
    }

    public void setOntime(final String ontime) {
        this.ontime = ontime;
    }

    @Column(name = "LANG_ID")
    public Long getLang_id() {
        return lang_id;
    }

    public void setLang_id(final Long lang_id) {
        this.lang_id = lang_id;
    }

    @Column(name = "LG_IP_MAC")
    public String getLg_ip_mac() {
        return lg_ip_mac;
    }

    public void setLg_ip_mac(final String lg_ip_mac) {
        this.lg_ip_mac = lg_ip_mac;
    }

    @Column(name = "LG_IP_MAC_UPD")
    public String getLg_ip_mac_upd() {
        return lg_ip_mac_upd;
    }

    public void setLg_ip_mac_upd(final String lg_ip_mac_upd) {
        this.lg_ip_mac_upd = lg_ip_mac_upd;
    }

    @Column(name = "SM_PARAM1")
    public String getSmParam1() {
        return smParam1;
    }

    public void setSmParam1(final String smParam1) {
        this.smParam1 = smParam1;
    }

    @Column(name = "SM_PARAM2")
    public String getSmParam2() {
        return smParam2;
    }

    public void setSmParam2(final String smParam2) {
        this.smParam2 = smParam2;
    }

    /**
     * @return the smfcategory
     */
    @Column(name = "SMFCATEGORY")
    public String getSmfcategory() {
        return smfcategory;
    }

    /**
     * @param smfcategory the smfcategory to set
     */
    public void setSmfcategory(final String smfcategory) {
        this.smfcategory = smfcategory;
    }

    @Column(name = "SM_SHORTDESC")
    public String getSmShortDesc() {
        return smShortDesc;
    }

    public void setSmShortDesc(String smShortDesc) {
        this.smShortDesc = smShortDesc;
    }

    @Transient
    public String[] getPkValues() {
        return new String[] { "AUT", "TB_SYSMODFUNCTION",
                "SMFID" };
    }
    @Transient
    public String getSmfname_eng() {
		return smfname_eng;
	}

	public void setSmfname_eng(String smfname_eng) {
		this.smfname_eng = smfname_eng;
	}
}
