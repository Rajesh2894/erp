package com.abm.mainet.cfc.scrutiny.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_SCRUTINY_VALUES")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbScrutinyLabelValueEntity.countAll", query = "SELECT COUNT(x) FROM TbScrutinyLabelValueEntity x")
})
public class TbScrutinyLabelValueEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2075391565589812858L;

    // ENTITY PRIMARY KEY ( EMBEDDED IN AN EXTERNAL CLASS )
    // ----------------------------------------------------------------------
    @EmbeddedId
    private TbScrutinyLabelValueEntityKey labelValueKey;

    @Column(name = "SV_VALUE", length = 400, nullable = true)
    private String svValue;
    
    @Column(name = "REMARK",  nullable = true)
    private String resolutionComments;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "levels", precision = 7, scale = 0, nullable = false)
    private Long levels;
    
    @Column(name = "TASK_ID")
	private Long taskId;

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

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
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

    public TbScrutinyLabelValueEntityKey getLabelValueKey() {
        return labelValueKey;
    }

    public void setLabelValueKey(final TbScrutinyLabelValueEntityKey labelValueKey) {
        this.labelValueKey = labelValueKey;
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

    public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	// ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbScrutinyLabelValueEntity() {
        super();
        labelValueKey = new TbScrutinyLabelValueEntityKey();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE COMPOSITE KEY
    // ----------------------------------------------------------------------
    public void setSlLabelId(final Long slLabelId) {
        labelValueKey.setSlLabelId(slLabelId);
    }

    public Long getSlLabelId() {
        return labelValueKey.getSlLabelId();
    }

    public Long getSaApplicationId() {
        return labelValueKey.getSaApplicationId();
    }

    public void setSaApplicationId(final Long saApplicationId) {
        labelValueKey.setSaApplicationId(saApplicationId);
        ;
    }

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}
}