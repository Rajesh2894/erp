package com.abm.mainet.water.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * @author pushkar.dike
 */
@Entity
@Table(name = "TB_WT_CONNECTION_SIZE")
public class TbWtConnectionSize {

    /**
     * 
     */
    private static final long serialVersionUID = -5467469633580515588L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CNS_ID", precision = 12, scale = 0, nullable = false)
    private long cnsId;

    @Column(name = "CNS_FROM", precision = 3, scale = 0, nullable = true)
    private Double cnsFrom;

    @Column(name = "CNS_TO", precision = 3, scale = 0, nullable = true)
    private Double cnsTo;

    @Column(name = "CNS_VALUE", precision = 3, scale = 2, nullable = true)
    private Double cnsValue;

    @Column(name = "ORGID", precision = 3, scale = 2, nullable = true)
    private Long orgId;

    @Column(name = "USER_ID", precision = 3, scale = 2, nullable = true)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private Long langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    
    
    public long getCnsId() {
		return cnsId;
	}



	public void setCnsId(long cnsId) {
		this.cnsId = cnsId;
	}



	public Double getCnsFrom() {
		return cnsFrom;
	}



	public void setCnsFrom(Double cnsFrom) {
		this.cnsFrom = cnsFrom;
	}



	public Double getCnsTo() {
		return cnsTo;
	}



	public void setCnsTo(Double cnsTo) {
		this.cnsTo = cnsTo;
	}



	public Double getCnsValue() {
		return cnsValue;
	}



	public void setCnsValue(Double cnsValue) {
		this.cnsValue = cnsValue;
	}



	public Long getOrgId() {
		return orgId;
	}



	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Long getLangId() {
		return langId;
	}



	public void setLangId(Long langId) {
		this.langId = langId;
	}



	public Date getLmodDate() {
		return lmodDate;
	}



	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_CONNECTION_SIZE", "CNS_ID" };
    }

}
