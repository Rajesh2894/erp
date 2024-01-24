package com.abm.mainet.common.formbuilder.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_FORMBUILDER_VALUES")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "FormBuilderValueEntity.countAll", query = "SELECT COUNT(x) FROM FormBuilderValueEntity x")
})
public class FormBuilderValueEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2075391565589812858L;
    
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FORM_ID", nullable = false)
    private Long formId;

    @Column(name = "FORM_VID", nullable = false)
    private Long saApplicationId;
    
    @Column(name = "FORM_LABEL_ID", nullable = false)
    private Long slLabelId;

    @Column(name = "FORM_VALUE", length = 200, nullable = true)
    private String svValue;

    @Column(name = "FORM_LEVELS")
    private Long levels;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public String getSvValue() {
        return svValue;
    }

    public void setSvValue(final String svValue) {
        this.svValue = svValue;
    }

    public Organisation getOrgId() {
        return orgId;
    }

    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public Employee getUserId() {
        return userId;
    }

    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }


    /**
     * @return the levels
     */
    public Long getLevels() {
        return levels;
    }

    /**
     * @param levels the levels to set
     */
    public void setLevels(final Long levels) {
        this.levels = levels;
    }


    
    public Long getSaApplicationId() {
		return saApplicationId;
	}

	public void setSaApplicationId(Long saApplicationId) {
		this.saApplicationId = saApplicationId;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }
    
    
    public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String[] getPkValues() {
        return new String[] { "COM", "TB_FORMBUILDER_VALUES", "FORM_ID" };
    }

}