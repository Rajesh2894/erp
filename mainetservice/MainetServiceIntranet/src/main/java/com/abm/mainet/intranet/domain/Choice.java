package com.abm.mainet.intranet.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_choices")
public class Choice implements Serializable {
	
	private static final long serialVersionUID = 4904163124181302322L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "id", nullable = false, precision = 10)
	private Long id;
	
    @Size(max = 40)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;
    
	@Column(name="ORG_ID", nullable = false, precision = 10)
	private Long orgid;
	
    @Column(name="Expire_Date_Time", nullable = false)
    private Instant expirationDateTime;
	
	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "USER_ID", nullable = false, precision = 10)
	private Long userId;
	
	@Column(name = "LANG_ID", nullable = false, precision = 10)
	private Long langId;
	
	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lmoddate;

    public Choice() {

    }

    public Choice(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return Objects.equals(id, choice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Instant getExpirationDateTime() {
		return expirationDateTime;
	}

	public void setExpirationDateTime(Instant expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public static String[] getPkValues() {
		return new String[] { "HD", "tb_choices", "id" };
	}
	
	
}
