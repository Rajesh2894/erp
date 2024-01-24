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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.entitlement.domain.RoleEntitlement;

@Entity
@Table(name = "ENTITLEMENT")// Entitlement class rename by SysmodFunction
public class Entitlement implements Serializable {

    private static final long serialVersionUID = -4547724734277641492L;
    private Long etId;
    private String nameEng;
    private String nameReg;
    private String description;
    private String flag;
    private String action;
    private String isActive;
    private Integer depth;
    private Long createdBy;
    private Date createdDate;
    private Entitlement entitlement;
    private Set<Entitlement> menuHierarchicalList = new LinkedHashSet<>();
    private Set<RoleEntitlement> entitlements = new LinkedHashSet<>();
    private Long hiddenEtId;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ET_ID", precision = 0, scale = 0, nullable = false)
    public Long getEtId() {
        return etId;
    }

    public void setEtId(final Long etId) {
        this.etId = etId;
    }

    @Column(name = "NAME_ENG")
    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(final String nameEng) {
        this.nameEng = nameEng;
    }

    @Column(name = "NAME_REG")
    public String getNameReg() {
        return nameReg;
    }

    public void setNameReg(final String nameReg) {
        this.nameReg = nameReg;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Column(name = "LEVEL_CHECK_FLAG")
    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    @Column(name = "ACTION")
    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    @Column(name = "IS_ACTIVE")
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(final String isActive) {
        this.isActive = isActive;
    }

    @Column(name = "DEPTH")
    public Integer getDepth() {
        return depth;
    }

    public void setDepth(final Integer depth) {
        this.depth = depth;
    }

    @Column(name = "CREATED_BY")
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ET_PARENT_ID")
    public Entitlement getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(final Entitlement entitlement) {
        this.entitlement = entitlement;
    }

    @OneToMany(mappedBy = "entitlement", fetch = FetchType.EAGER)
    @OrderBy(value = "nameEng")
    public Set<Entitlement> getMenuHierarchicalList() {
        return menuHierarchicalList;
    }

    public void setMenuHierarchicalList(final Set<Entitlement> menuHierarchicalList) {
        this.menuHierarchicalList = menuHierarchicalList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entitle")
    public Set<RoleEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(final Set<RoleEntitlement> entitlements) {
        this.entitlements = entitlements;
    }

    @Transient
    public Long getHiddenEtId() {
        return hiddenEtId;
    }

    public void setHiddenEtId(final Long hiddenEtId) {
        this.hiddenEtId = hiddenEtId;
    }

    @Transient
    public String[] getPkValues() {
        return new String[] { "AUT", "ENTITLEMENT", "ET_ID" };
    }
}
