package com.abm.mainet.common.exception.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exception_log_dtl")
public class ExceptionLogDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "exception_log_id")
	private Long exceptionLogId;

	@Column(name = "exception_class", length = 100, nullable = true)
	private String exceptionClass;

	@Column(name = "URL", length = 100, nullable = true)
	private String url;

	@Column(name = "endpoint", length = 100, nullable = true)
	private String endpoint;

	@Column(name = "file_name", length = 50, nullable = true)
	private String fileName;

	@Column(name = "method_name", length = 50, nullable = true)
	private String methodName;

	@Column(name = "exception_detail", nullable = true)
	@Lob
	private String exceptionDetail;

	@Column(name = "orgId", length = 12, nullable = true)
	private Long orgId;

	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@Column(name = "created_date", nullable = true)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true, updatable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	public String[] getPkValues() {
		return new String[] { "COM", "exception_log_dtl", "exception_log_id" };

	}

	public Long getExceptionLogId() {
		return exceptionLogId;
	}

	public void setExceptionLogId(Long exceptionLogId) {
		this.exceptionLogId = exceptionLogId;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
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

	@Override
	public String toString() {
		return "ExceptionLogDtl [exceptionLogId=" + exceptionLogId + ", exceptionClass=" + exceptionClass + ", url="
				+ url + ", fileName=" + fileName + ", methodName=" + methodName + ", exceptionDetail=" + exceptionDetail
				+ ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ "]";
	}

}
