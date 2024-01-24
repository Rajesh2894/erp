package com.abm.mainet.bnd.domain;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the tb_bd_cert_copy database table.
 * 
 */
@Entity
@Table(name="tb_bd_cert_copy")
public class TbBdCertCopy implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="BD_ID")
	private Long bdId;

	@Column(name="BR_ID")
	private Long brId;

	@Column(name="CERT_NO", length=24)
	private String certNo;

	@Column(name="COPY_NO")
	private Long copyNo;

	@Column(name="DR_ID")
	private Long drId;

	@Column(name="LANG_ID")
	private int langId;

	@Column(name="LG_IP_MAC", length=100)
	private String lgIpMac;

	@Column(name="LG_IP_MAC_UPD", length=100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lmoddate;

	@Column
	private Long orgid;

	@Column(name="RT_ID")
	private Long rtId;

	@Column(length=2)
	private String status;

	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	@Column(name="USER_ID")
	private Long userId;
	
	
	
	@Column(name="APMApplicationId")
	private Long APMApplicationId;
	
	@Column(name="Nac_brId")
	private Long nacBrId;
	
	@Column(name="Nac_drId")
	private Long nacDrId;

	public TbBdCertCopy() {
	}

	

	public String getCertNo() {
		return this.certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public int getLangId() {
		return this.langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public String getLgIpMac() {
		return this.lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return this.lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Date getLmoddate() {
		return this.lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
	public Long getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Long getBdId() {
		return bdId;
	}



	public void setBdId(Long bdId) {
		this.bdId = bdId;
	}



	public Long getBrId() {
		return brId;
	}



	public void setBrId(Long brId) {
		this.brId = brId;
	}



	public Long getCopyNo() {
		return copyNo;
	}



	public void setCopyNo(Long copyNo) {
		this.copyNo = copyNo;
	}



	public Long getDrId() {
		return drId;
	}



	public void setDrId(Long drId) {
		this.drId = drId;
	}



	public Long getOrgid() {
		return orgid;
	}



	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}



	public Long getRtId() {
		return rtId;
	}



	public void setRtId(Long rtId) {
		this.rtId = rtId;
	}
	
	

	public Long getAPMApplicationId() {
		return APMApplicationId;
	}



	public void setAPMApplicationId(Long aPMApplicationId) {
		APMApplicationId = aPMApplicationId;
	}


	public Long getNacBrId() {
		return nacBrId;
	}


	public void setNacBrId(Long nacBrId) {
		this.nacBrId = nacBrId;
	}



	public Long getNacDrId() {
		return nacDrId;
	}



	public void setNacDrId(Long nacDrId) {
		this.nacDrId = nacDrId;
	}



	public String[] getPkValues() {
		return new String[] { "HD", "tb_bd_cert_copy", "BR_ID" };
	}
	
}