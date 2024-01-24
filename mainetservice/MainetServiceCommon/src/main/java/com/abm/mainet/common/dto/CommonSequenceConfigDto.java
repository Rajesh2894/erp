package com.abm.mainet.common.dto;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.FinancialYear;

/**
 * @author sadik.shaikh
 *
 */
public class CommonSequenceConfigDto implements Serializable {

	private static final long serialVersionUID = -47162186990242090L;

	private Long level1Id;

	private Long level2Id;

	private Long level3Id;

	private Long level4Id;

	private Long level5Id;

	private String level1Desc;

	private String level2Desc;

	private String level3Desc;

	private String level4Desc;

	private String level5Desc;

	private String level1value;

	private String level2value;

	private String level3value;

	private String level4value;

	private String level5value;

	private String serviceCode;

	private String usageCode1;

	private String usageCode2;

	private String usageCode3;

	private String usageCode4;

	private String usageCode5;

	private Long usageId1;

	private Long usageId2;

	private Long usageId3;

	private Long usageId4;

	private Long usageId5;

	private String financialYear;

	private Long usageCtrlId;

	private String usageCtrlCode;
//
	private List<Long> usageIds;

	private FinancialYear financYear;

	private String designation;

	private Long tradeCategory;
	
	private String customField;

	public Long getTradeCategory() {
		return tradeCategory;
	}

	public void setTradeCategory(Long tradeCategory) {
		this.tradeCategory = tradeCategory;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public FinancialYear getFinancYear() {
		return financYear;
	}

	public void setFinancYear(FinancialYear financYear) {
		this.financYear = financYear;
	}

	public List<Long> getUsageIds() {
		return usageIds;
	}

	public void setUsageIds(List<Long> usageIds) {
		this.usageIds = usageIds;
	}

	public String getUsageCode1() {
		return usageCode1;
	}

	public void setUsageCode1(String usageCode1) {
		this.usageCode1 = usageCode1;
	}

	public String getUsageCode2() {
		return usageCode2;
	}

	public void setUsageCode2(String usageCode2) {
		this.usageCode2 = usageCode2;
	}

	public String getUsageCode3() {
		return usageCode3;
	}

	public void setUsageCode3(String usageCode3) {
		this.usageCode3 = usageCode3;
	}

	public String getUsageCode4() {
		return usageCode4;
	}

	public void setUsageCode4(String usageCode4) {
		this.usageCode4 = usageCode4;
	}

	public String getUsageCode5() {
		return usageCode5;
	}

	public void setUsageCode5(String usageCode5) {
		this.usageCode5 = usageCode5;
	}

	public Long getUsageId1() {
		return usageId1;
	}

	public void setUsageId1(Long usageId1) {
		this.usageId1 = usageId1;
	}

	public Long getUsageId2() {
		return usageId2;
	}

	public void setUsageId2(Long usageId2) {
		this.usageId2 = usageId2;
	}

	public Long getUsageId3() {
		return usageId3;
	}

	public void setUsageId3(Long usageId3) {
		this.usageId3 = usageId3;
	}

	public Long getUsageId4() {
		return usageId4;
	}

	public void setUsageId4(Long usageId4) {
		this.usageId4 = usageId4;
	}

	public Long getUsageId5() {
		return usageId5;
	}

	public void setUsageId5(Long usageId5) {
		this.usageId5 = usageId5;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public Long getUsageCtrlId() {
		return usageCtrlId;
	}

	public void setUsageCtrlId(Long usageCtrlId) {
		this.usageCtrlId = usageCtrlId;
	}

	public String getUsageCtrlCode() {
		return usageCtrlCode;
	}

	public void setUsageCtrlCode(String usageCtrlCode) {
		this.usageCtrlCode = usageCtrlCode;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getLevel1Id() {
		return level1Id;
	}

	public void setLevel1Id(Long level1Id) {
		this.level1Id = level1Id;
	}

	public Long getLevel2Id() {
		return level2Id;
	}

	public void setLevel2Id(Long level2Id) {
		this.level2Id = level2Id;
	}

	public Long getLevel3Id() {
		return level3Id;
	}

	public void setLevel3Id(Long level3Id) {
		this.level3Id = level3Id;
	}

	public Long getLevel4Id() {
		return level4Id;
	}

	public void setLevel4Id(Long level4Id) {
		this.level4Id = level4Id;
	}

	public Long getLevel5Id() {
		return level5Id;
	}

	public void setLevel5Id(Long level5Id) {
		this.level5Id = level5Id;
	}

	public String getLevel1Desc() {
		return level1Desc;
	}

	public void setLevel1Desc(String level1Desc) {
		this.level1Desc = level1Desc;
	}

	public String getLevel2Desc() {
		return level2Desc;
	}

	public void setLevel2Desc(String level2Desc) {
		this.level2Desc = level2Desc;
	}

	public String getLevel3Desc() {
		return level3Desc;
	}

	public void setLevel3Desc(String level3Desc) {
		this.level3Desc = level3Desc;
	}

	public String getLevel4Desc() {
		return level4Desc;
	}

	public void setLevel4Desc(String level4Desc) {
		this.level4Desc = level4Desc;
	}

	public String getLevel5Desc() {
		return level5Desc;
	}

	public void setLevel5Desc(String level5Desc) {
		this.level5Desc = level5Desc;
	}

	public String getLevel1value() {
		return level1value;
	}

	public void setLevel1value(String level1value) {
		this.level1value = level1value;
	}

	public String getLevel2value() {
		return level2value;
	}

	public void setLevel2value(String level2value) {
		this.level2value = level2value;
	}

	public String getLevel3value() {
		return level3value;
	}

	public void setLevel3value(String level3value) {
		this.level3value = level3value;
	}

	public String getLevel4value() {
		return level4value;
	}

	public void setLevel4value(String level4value) {
		this.level4value = level4value;
	}

	public String getLevel5value() {
		return level5value;
	}

	public void setLevel5value(String level5value) {
		this.level5value = level5value;
	}
	
	public String getCustomField() {
		return customField;
	}

	public void setCustomField(String customField) {
		this.customField = customField;
	}

	@Override
	public String toString() {
		return "CommonSequenceConfigDto [level1Id=" + level1Id + ", level2Id=" + level2Id + ", level3Id=" + level3Id
				+ ", level4Id=" + level4Id + ", level5Id=" + level5Id + ", level1Desc=" + level1Desc + ", level2Desc="
				+ level2Desc + ", level3Desc=" + level3Desc + ", level4Desc=" + level4Desc + ", level5Desc="
				+ level5Desc + ", level1value=" + level1value + ", level2value=" + level2value + ", level3value="
				+ level3value + ", level4value=" + level4value + ", level5value=" + level5value + ", serviceCode="
				+ serviceCode + ", usageCode1=" + usageCode1 + ", usageCode2=" + usageCode2 + ", usageCode3="
				+ usageCode3 + ", usageCode4=" + usageCode4 + ", usageCode5=" + usageCode5 + ", usageId1=" + usageId1
				+ ", usageId2=" + usageId2 + ", usageId3=" + usageId3 + ", usageId4=" + usageId4 + ", usageId5="
				+ usageId5 + ", financialYear=" + financialYear + ", usageCtrlId=" + usageCtrlId + ", usageCtrlCode="
				+ usageCtrlCode + "]";
	}

}
