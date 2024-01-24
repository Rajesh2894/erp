package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nirmal.mahanta
 *
 */
public class BillingScheduleDto implements Serializable {

	private static final long serialVersionUID = -6864095310636317146L;

	private String cnsMn;
	private Long cnsYearid;
	private Long dependsOnType;
	private Long billFreq;
	private Long codIdWwz1;
	private Long codIdWwz2;
	private Long codIdWwz3;
	private Long codIdWwz4;
	private Long codIdWwz5;
	private Long cnsCcgid1;
	private Long fromMonth;
	private long orgid;
	private Date billFromDate;
	private Date billToDate;
	private String billFromMonth;
	private String billToMonth;

	/**
	 * @return the cnsMn
	 */
	public String getCnsMn() {
		return cnsMn;
	}

	/**
	 * @param cnsMn
	 *            the cnsMn to set
	 */
	public void setCnsMn(final String cnsMn) {
		this.cnsMn = cnsMn;
	}

	/**
	 * @return the cnsYearid
	 */
	public Long getCnsYearid() {
		return cnsYearid;
	}

	/**
	 * @param cnsYearid
	 *            the cnsYearid to set
	 */
	public void setCnsYearid(final Long cnsYearid) {
		this.cnsYearid = cnsYearid;
	}

	/**
	 * @return the dependsOnType
	 */
	public Long getDependsOnType() {
		return dependsOnType;
	}

	/**
	 * @param dependsOnType
	 *            the dependsOnType to set
	 */
	public void setDependsOnType(final Long dependsOnType) {
		this.dependsOnType = dependsOnType;
	}

	/**
	 * @return the billFreq
	 */
	public Long getBillFreq() {
		return billFreq;
	}

	/**
	 * @param billFreq
	 *            the billFreq to set
	 */
	public void setBillFreq(final Long billFreq) {
		this.billFreq = billFreq;
	}

	/**
	 * @return the codIdWwz1
	 */
	public Long getCodIdWwz1() {
		return codIdWwz1;
	}

	/**
	 * @param codIdWwz1
	 *            the codIdWwz1 to set
	 */
	public void setCodIdWwz1(final Long codIdWwz1) {
		this.codIdWwz1 = codIdWwz1;
	}

	/**
	 * @return the codIdWwz2
	 */
	public Long getCodIdWwz2() {
		return codIdWwz2;
	}

	/**
	 * @param codIdWwz2
	 *            the codIdWwz2 to set
	 */
	public void setCodIdWwz2(final Long codIdWwz2) {
		this.codIdWwz2 = codIdWwz2;
	}

	/**
	 * @return the codIdWwz3
	 */
	public Long getCodIdWwz3() {
		return codIdWwz3;
	}

	/**
	 * @param codIdWwz3
	 *            the codIdWwz3 to set
	 */
	public void setCodIdWwz3(final Long codIdWwz3) {
		this.codIdWwz3 = codIdWwz3;
	}

	/**
	 * @return the codIdWwz4
	 */
	public Long getCodIdWwz4() {
		return codIdWwz4;
	}

	/**
	 * @param codIdWwz4
	 *            the codIdWwz4 to set
	 */
	public void setCodIdWwz4(final Long codIdWwz4) {
		this.codIdWwz4 = codIdWwz4;
	}

	/**
	 * @return the codIdWwz5
	 */
	public Long getCodIdWwz5() {
		return codIdWwz5;
	}

	/**
	 * @param codIdWwz5
	 *            the codIdWwz5 to set
	 */
	public void setCodIdWwz5(final Long codIdWwz5) {
		this.codIdWwz5 = codIdWwz5;
	}

	/**
	 * @return the cnsCcgid1
	 */
	public Long getCnsCcgid1() {
		return cnsCcgid1;
	}

	/**
	 * @param cnsCcgid1
	 *            the cnsCcgid1 to set
	 */
	public void setCnsCcgid1(final Long cnsCcgid1) {
		this.cnsCcgid1 = cnsCcgid1;
	}

	/**
	 * @return the fromMonth
	 */
	public Long getFromMonth() {
		return fromMonth;
	}

	/**
	 * @param fromMonth
	 *            the fromMonth to set
	 */
	public void setFromMonth(final Long fromMonth) {
		this.fromMonth = fromMonth;
	}

	public long getOrgid() {
		return orgid;
	}

	public void setOrgid(final long orgid) {
		this.orgid = orgid;
	}

	public Date getBillFromDate() {
		return billFromDate;
	}

	public void setBillFromDate(Date billFromDate) {
		this.billFromDate = billFromDate;
	}

	public Date getBillToDate() {
		return billToDate;
	}

	public void setBillToDate(Date billToDate) {
		this.billToDate = billToDate;
	}

	public String getBillFromMonth() {
		return billFromMonth;
	}

	public void setBillFromMonth(String billFromMonth) {
		this.billFromMonth = billFromMonth;
	}

	public String getBillToMonth() {
		return billToMonth;
	}

	public void setBillToMonth(String billToMonth) {
		this.billToMonth = billToMonth;
	}
}
