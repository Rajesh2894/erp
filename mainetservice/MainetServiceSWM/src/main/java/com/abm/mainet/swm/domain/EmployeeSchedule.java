package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_employee_scheduling database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Entity
@Table(name = "TB_SW_EMPLOYEE_SCHEDULING")
public class EmployeeSchedule implements Serializable {

    private static final long serialVersionUID = -8809779600639286997L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EMS_ID", unique = true, nullable = false)
    private Long emsId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMS_FROMDATE", nullable = false)
    private Date emsFromdate;

    @Column(name = "EMS_REOCC", nullable = false, length = 1)
    private String emsReocc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMS_TODATE", nullable = false)
    private Date emsTodate;

    @Column(name = "EMS_TYPE", nullable = false)
    private String emsType;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    // bi-directional many-to-one association to TbSwEmployeeScheddet
    @OneToMany(mappedBy = "tbSwEmployeeScheduling", cascade = CascadeType.ALL)
    private List<EmployeeScheduleDetail> tbSwEmployeeScheddets;

    public EmployeeSchedule() {
    }

    public Long getEmsId() {
        return this.emsId;
    }

    public void setEmsId(Long emsId) {
        this.emsId = emsId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getEmsFromdate() {
        return this.emsFromdate;
    }

    public void setEmsFromdate(Date emsFromdate) {
        this.emsFromdate = emsFromdate;
    }

    public String getEmsReocc() {
        return this.emsReocc;
    }

    public void setEmsReocc(String emsReocc) {
        this.emsReocc = emsReocc;
    }

    public Date getEmsTodate() {
        return this.emsTodate;
    }

    public void setEmsTodate(Date emsTodate) {
        this.emsTodate = emsTodate;
    }

    public String getEmsType() {
        return emsType;
    }

    public void setEmsType(String emsType) {
        this.emsType = emsType;
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

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<EmployeeScheduleDetail> getTbSwEmployeeScheddets() {
        return this.tbSwEmployeeScheddets;
    }

    public void setTbSwEmployeeScheddets(List<EmployeeScheduleDetail> tbSwEmployeeScheddets) {
        this.tbSwEmployeeScheddets = tbSwEmployeeScheddets;
    }

    public EmployeeScheduleDetail addTbSwEmployeeScheddet(EmployeeScheduleDetail tbSwEmployeeScheddet) {
        getTbSwEmployeeScheddets().add(tbSwEmployeeScheddet);
        tbSwEmployeeScheddet.setTbSwEmployeeScheduling(this);

        return tbSwEmployeeScheddet;
    }

    public EmployeeScheduleDetail removeTbSwEmployeeScheddet(EmployeeScheduleDetail tbSwEmployeeScheddet) {
        getTbSwEmployeeScheddets().remove(tbSwEmployeeScheddet);
        tbSwEmployeeScheddet.setTbSwEmployeeScheduling(null);

        return tbSwEmployeeScheddet;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_EMPLOYEE_SCHEDULING", "EMS_ID" };
    }
}
