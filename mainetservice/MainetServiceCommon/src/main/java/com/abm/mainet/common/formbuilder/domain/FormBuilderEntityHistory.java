package com.abm.mainet.common.formbuilder.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_scrutiny_labels_hist database table.
 * 
 */
@Entity
@Table(name = "TB_FORMBUILDER_LABELS_HIST")
public class FormBuilderEntityHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FORM_LABEL_ID_H")
    private Long hSlLabelid;

    @Column(name = "FORM_LABEL_ID")
    private Long slLabelId;

    @Column(name = "GM_ID")
    private Long gmId;

    @Column(name = "SM_SHORTDESC")
    private String smShortDesc;
    
    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "FORM_LEVELS")
    private Long levels;

    @Column(name = "FORM_ACCESS_DSGID")
    private BigDecimal slAccessDsgid;

    @Column(name = "FORM_ACTIVE_STATUS")
    private String slActiveStatus;

    @Column(name = "FORM_AUTHORISING")
    private String slAuthorising;

    @Column(name = "FORM_DATATYPE")
    private String slDatatype;

    @Column(name = "FORM_DISPLAY_FLAG")
    private String slDisplayFlag;

    @Column(name = "FORM_FORM_MODE")
    private String slFormMode;

    @Column(name = "FORM_FORM_NAME")
    private String slFormName;

    @Column(name = "FORM_FORMULA")
    private String slFormula;

    @Column(name = "FORM_LABEL")
    private String slLabel;

    @Column(name = "FORM_LABEL_MAR")
    private String slLabelMar;

    @Column(name = "FORM_POSITION")
    private BigDecimal slPosition;

    @Column(name = "FORM_PRE_VALIDATION")
    private String slPreValidation;

    @Column(name = "FORM_PROCEDURE")
    private String slProcedure;

    @Column(name = "FORM_TABLE_COLUMN")
    private String slTableColumn;

    @Column(name = "FORM_TABULAR_DATA")
    private String slTabularData;

    @Column(name = "FORM_VALIDATION_TEXT")
    private String slValidationText;

    @Column(name = "FORM_WHERE_CLAUSE")
    private String slWhereClause;

    @Column(name = "ORGID")
    private Long orgid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date lmoddate;

    @Column(name = "CREATED_BY")
    private Long userId;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public FormBuilderEntityHistory() {
    }

    public Long gethSlLabelid() {
        return hSlLabelid;
    }

    public void sethSlLabelid(Long hSlLabelid) {
        this.hSlLabelid = hSlLabelid;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public Long getGmId() {
        return this.gmId;
    }

    public void setGmId(Long gmId) {
        this.gmId = gmId;
    }

    public Long getLevels() {
        return this.levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public BigDecimal getSlAccessDsgid() {
        return this.slAccessDsgid;
    }

    public void setSlAccessDsgid(BigDecimal slAccessDsgid) {
        this.slAccessDsgid = slAccessDsgid;
    }

    public String getSlActiveStatus() {
        return this.slActiveStatus;
    }

    public void setSlActiveStatus(String slActiveStatus) {
        this.slActiveStatus = slActiveStatus;
    }

    public String getSlAuthorising() {
        return this.slAuthorising;
    }

    public void setSlAuthorising(String slAuthorising) {
        this.slAuthorising = slAuthorising;
    }

    public String getSlDatatype() {
        return this.slDatatype;
    }

    public void setSlDatatype(String slDatatype) {
        this.slDatatype = slDatatype;
    }

    public String getSlDisplayFlag() {
        return this.slDisplayFlag;
    }

    public void setSlDisplayFlag(String slDisplayFlag) {
        this.slDisplayFlag = slDisplayFlag;
    }

    public String getSlFormMode() {
        return this.slFormMode;
    }

    public void setSlFormMode(String slFormMode) {
        this.slFormMode = slFormMode;
    }

    public String getSlFormName() {
        return this.slFormName;
    }

    public void setSlFormName(String slFormName) {
        this.slFormName = slFormName;
    }

    public String getSlFormula() {
        return this.slFormula;
    }

    public void setSlFormula(String slFormula) {
        this.slFormula = slFormula;
    }

    public String getSlLabel() {
        return this.slLabel;
    }

    public void setSlLabel(String slLabel) {
        this.slLabel = slLabel;
    }

    public Long getSlLabelId() {
        return this.slLabelId;
    }

    public void setSlLabelId(Long slLabelId) {
        this.slLabelId = slLabelId;
    }

    public String getSlLabelMar() {
        return this.slLabelMar;
    }

    public void setSlLabelMar(String slLabelMar) {
        this.slLabelMar = slLabelMar;
    }

    public BigDecimal getSlPosition() {
        return this.slPosition;
    }

    public void setSlPosition(BigDecimal slPosition) {
        this.slPosition = slPosition;
    }

    public String getSlPreValidation() {
        return this.slPreValidation;
    }

    public void setSlPreValidation(String slPreValidation) {
        this.slPreValidation = slPreValidation;
    }

    public String getSlProcedure() {
        return this.slProcedure;
    }

    public void setSlProcedure(String slProcedure) {
        this.slProcedure = slProcedure;
    }

    public String getSlTableColumn() {
        return this.slTableColumn;
    }

    public void setSlTableColumn(String slTableColumn) {
        this.slTableColumn = slTableColumn;
    }

    public String getSlTabularData() {
        return this.slTabularData;
    }

    public void setSlTabularData(String slTabularData) {
        this.slTabularData = slTabularData;
    }

    public String getSlValidationText() {
        return this.slValidationText;
    }

    public void setSlValidationText(String slValidationText) {
        this.slValidationText = slValidationText;
    }

    public String getSlWhereClause() {
        return this.slWhereClause;
    }

    public void setSlWhereClause(String slWhereClause) {
        this.slWhereClause = slWhereClause;
    }


    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    public String getSmShortDesc() {
		return smShortDesc;
	}

	public void setSmShortDesc(String smShortDesc) {
		this.smShortDesc = smShortDesc;
	}

	public String[] getPkValues() {
        return new String[] { "COM", "TB_FORMBUILDER_LABELS_HIST", "FORM_LABEL_ID_H" };
    }
}