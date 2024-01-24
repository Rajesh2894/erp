package com.abm.mainet.intranet.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "tb_polls")
public class Poll {
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "id", nullable = false, precision = 10)
	private Long pollid;
	
	@OneToOne(fetch = FetchType.EAGER) //ashish eager LAZY;
	@JoinColumn(name = "poll_det_id",  nullable = true)
	private PollDetails	 id;
	
	/*@Column(name = "poll_det_id",  nullable = true)
	private Long id;*/
	
	@Size(max = 140)
    private String question;

	@OneToMany(
            mappedBy = "poll",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    private List<Choice> choices = new ArrayList<>();

    @Column(name="expiration_Date_Time", nullable = false)
    private Instant expirationDateTime;
    
	@Column(name="ORG_ID", nullable = false, precision = 10)
	private Long orgid;
    
	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "USER_ID", nullable = false, precision = 10)
	private Long userId;
	
	@Column(name = "LANG_ID", nullable = false, precision = 10)
	private int langId;
	
	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lmoddate;

    @CreatedBy
    @Column(name="created_by")
    private Long createdBy;
    
	@Column(name = "POLL_STATUS", nullable = true, length = 1)
	private String pollStatus;
    
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

	public Instant getExpirationDateTime() {
		return expirationDateTime;
	}
	
	public void setExpirationDateTime(Instant expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}

	public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setPoll(this);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setPoll(null);
    }

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
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
	
    public String getPollStatus() {
		return pollStatus;
	}

	public void setPollStatus(String pollStatus) {
		this.pollStatus = pollStatus;
	}
	
	public Long getPollid() {
		return pollid;
	}

	public void setPollid(Long pollid) {
		this.pollid = pollid;
	}

	public PollDetails getId() {
		return id;
	}

	public void setId(PollDetails id) {
		this.id = id;
	}


	public static String[] getPkValues() {
		return new String[] { "HD", "tb_polls", "pollid" };
	}
	
	
    
}
