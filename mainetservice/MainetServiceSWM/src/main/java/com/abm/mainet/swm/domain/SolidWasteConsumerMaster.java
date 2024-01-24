package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the tb_sw_new_registration database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 18-Jun-2018
 */
@Entity
@Table(name = "TB_SW_REGISTRATION")
public class SolidWasteConsumerMaster implements Serializable {

    private static final long serialVersionUID = 2276361172860084920L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "REGISTRATION_ID", unique = true, nullable = false)
    private Long registrationId;

    private Long orgid;

    @Column(name = "SW_ADDRESS", length = 500)
    private String swAddress;

    @Column(name = "SW_LOCATION")
    private Long swLocation;

    @Column(name = "SW_MOBILE")
    private Long swMobile;

    @Column(name = "SW_NAME", length = 100)
    private String swName;

    @Column(name = "SW_NEW_CUST_ID", length = 100)
    private String swNewCustId;

    @Column(name = "SW_OLD_CUST_ID", length = 100)
    private String swOldCustId;

    @Column(name = "SW_OLD_PROP_NO", length = 100)
    private String swOldPropNo;

    @Column(name = "SW_NEW_PROP_NO", length = 100)
    private String swNewPropNo;

    @Column(name = "SW_USER_CATEGORY1", length = 100)
    private String swUserCategory1;

    @Column(name = "SW_USER_CATEGORY2", length = 100)
    private String swUserCategory2;

    @Column(name = "SW_USER_CATEGORY3", length = 100)
    private String swUserCategory3;

    @Column(name = "SW_USER_CATEGORY4", length = 100)
    private String swUserCategory4;

    @Column(name = "SW_USER_CATEGORY5", length = 100)
    private String swUserCategory5;
    
    @Column(name = "SW_USER_CATEGORY1_REG", length = 100)
    private String swUserCategory1Reg;

    @Column(name = "SW_USER_CATEGORY2_REG", length = 100)
    private String swUserCategory2Reg;

    @Column(name = "SW_USER_CATEGORY3_REG", length = 100)
    private String swUserCategory3Reg;

    @Column(name = "SW_USER_CATEGORY4_REG", length = 100)
    private String swUserCategory4Reg;

    @Column(name = "SW_USER_CATEGORY5_REG", length = 100)
    private String swUserCategory5Reg;

    @Column(name = "SW_COUNT1")
    private Long swCount1;

    @Column(name = "SW_COUNT2")
    private Long swCount2;

    @Column(name = "SW_COUNT3")
    private Long swCount3;

    @Column(name = "SW_COUNT4")
    private Long swCount4;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "SW_COD1")	
    private Long swCod1;
    
    @Column(name = "SW_COD2")	
    private Long swCod2;
    
    @Column(name = "SW_COD3")	
    private Long swCod3;
    
    @Column(name = "SW_COD4")	
    private Long swCod4;
    
    @Column(name = "SW_COD5")	
    private Long swCod5;

    // bi-directional many-to-one association to TbSwNewBillMa
    @OneToMany(mappedBy = "tbSwNewRegistration")
    @JsonIgnore
    private List<SolidWasteBillMaster> tbSwNewBillMas;
    
    @OneToMany(mappedBy = "tbSolidWasteConsumerMaster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HomeComposeDetails> tbHomeComposeDetails;

    public List<HomeComposeDetails> getTbHomeComposeDetails() {
        return tbHomeComposeDetails;
    }

    public void setTbHomeComposeDetails(List<HomeComposeDetails> tbHomeComposeDetails) {
        this.tbHomeComposeDetails = tbHomeComposeDetails;
    }

    public SolidWasteConsumerMaster() {
    }

    public Long getRegistrationId() {
        return this.registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public String getSwAddress() {
        return this.swAddress;
    }

    public void setSwAddress(String swAddress) {
        this.swAddress = swAddress;
    }

    public Long getSwLocation() {
        return this.swLocation;
    }

    public void setSwLocation(Long swLocation) {
        this.swLocation = swLocation;
    }

    public Long getSwMobile() {
        return this.swMobile;
    }

    public void setSwMobile(Long swMobile) {
        this.swMobile = swMobile;
    }

    public String getSwName() {
        return this.swName;
    }

    public void setSwName(String swName) {
        this.swName = swName;
    }

    public String getSwNewCustId() {
        return this.swNewCustId;
    }

    public void setSwNewCustId(String swNewCustId) {
        this.swNewCustId = swNewCustId;
    }

    public String getSwOldCustId() {
        return this.swOldCustId;
    }

    public void setSwOldCustId(String swOldCustId) {
        this.swOldCustId = swOldCustId;
    }

    public String getSwOldPropNo() {
        return this.swOldPropNo;
    }

    public void setSwOldPropNo(String swOldPropNo) {
        this.swOldPropNo = swOldPropNo;
    }

    public String getSwUserCategory1() {
        return this.swUserCategory1;
    }

    public void setSwUserCategory1(String swUserCategory1) {
        this.swUserCategory1 = swUserCategory1;
    }

    public String getSwUserCategory2() {
        return this.swUserCategory2;
    }

    public void setSwUserCategory2(String swUserCategory2) {
        this.swUserCategory2 = swUserCategory2;
    }

    public String getSwUserCategory3() {
        return this.swUserCategory3;
    }

    public void setSwUserCategory3(String swUserCategory3) {
        this.swUserCategory3 = swUserCategory3;
    }

    public String getSwUserCategory4() {
        return this.swUserCategory4;
    }

    public void setSwUserCategory4(String swUserCategory4) {
        this.swUserCategory4 = swUserCategory4;
    }

    public String getSwUserCategory5() {
        return this.swUserCategory5;
    }

    public void setSwUserCategory5(String swUserCategory5) {
        this.swUserCategory5 = swUserCategory5;
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

    public List<SolidWasteBillMaster> getTbSwNewBillMas() {
        return this.tbSwNewBillMas;
    }

    public void setTbSwNewBillMas(List<SolidWasteBillMaster> tbSwNewBillMas) {
        this.tbSwNewBillMas = tbSwNewBillMas;
    }

    public SolidWasteBillMaster addTbSwNewBillMa(SolidWasteBillMaster tbSwNewBillMa) {
        getTbSwNewBillMas().add(tbSwNewBillMa);
        tbSwNewBillMa.setTbSwNewRegistration(this);

        return tbSwNewBillMa;
    }

    public SolidWasteBillMaster removeTbSwNewBillMa(SolidWasteBillMaster tbSwNewBillMa) {
        getTbSwNewBillMas().remove(tbSwNewBillMa);
        tbSwNewBillMa.setTbSwNewRegistration(null);

        return tbSwNewBillMa;
    }

    public String getSwNewPropNo() {
        return swNewPropNo;
    }

    public void setSwNewPropNo(String swNewPropNo) {
        this.swNewPropNo = swNewPropNo;
    }

    public Long getSwCount1() {
        return swCount1;
    }

    public void setSwCount1(Long swCount1) {
        this.swCount1 = swCount1;
    }

    public Long getSwCount2() {
        return swCount2;
    }

    public void setSwCount2(Long swCount2) {
        this.swCount2 = swCount2;
    }

    public Long getSwCount3() {
        return swCount3;
    }

    public void setSwCount3(Long swCount3) {
        this.swCount3 = swCount3;
    }

    public Long getSwCount4() {
        return swCount4;
    }

    public void setSwCount4(Long swCount4) {
        this.swCount4 = swCount4;
    }

    public String getSwUserCategory1Reg() {
        return swUserCategory1Reg;
    }

    public void setSwUserCategory1Reg(String swUserCategory1Reg) {
        this.swUserCategory1Reg = swUserCategory1Reg;
    }

    public String getSwUserCategory2Reg() {
        return swUserCategory2Reg;
    }

    public void setSwUserCategory2Reg(String swUserCategory2Reg) {
        this.swUserCategory2Reg = swUserCategory2Reg;
    }

    public String getSwUserCategory3Reg() {
        return swUserCategory3Reg;
    }

    public void setSwUserCategory3Reg(String swUserCategory3Reg) {
        this.swUserCategory3Reg = swUserCategory3Reg;
    }

    public String getSwUserCategory4Reg() {
        return swUserCategory4Reg;
    }

    public void setSwUserCategory4Reg(String swUserCategory4Reg) {
        this.swUserCategory4Reg = swUserCategory4Reg;
    }

    public String getSwUserCategory5Reg() {
        return swUserCategory5Reg;
    }

    public void setSwUserCategory5Reg(String swUserCategory5Reg) {
        this.swUserCategory5Reg = swUserCategory5Reg;
    }

    public Long getSwCod1() {
        return swCod1;
    }

    public void setSwCod1(Long swCod1) {
        this.swCod1 = swCod1;
    }

    public Long getSwCod2() {
        return swCod2;
    }

    public void setSwCod2(Long swCod2) {
        this.swCod2 = swCod2;
    }

    public Long getSwCod3() {
        return swCod3;
    }

    public void setSwCod3(Long swCod3) {
        this.swCod3 = swCod3;
    }

    public Long getSwCod4() {
        return swCod4;
    }

    public void setSwCod4(Long swCod4) {
        this.swCod4 = swCod4;
    }

    public Long getSwCod5() {
        return swCod5;
    }

    public void setSwCod5(Long swCod5) {
        this.swCod5 = swCod5;
    }
    
    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_REGISTRATION", "REGISTRATION_ID" };
    }
}