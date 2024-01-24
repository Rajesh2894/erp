package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.util.LookUp;

public class SelfAssessmentDeaultValueDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1076953691760731099L;

    private List<LookUp> location = null;

    private Map<Long, String> financialYearMap = new HashMap<>(0);

    private Map<Date, Long> financialYearDate = new HashMap<>(0);

    private List<LookUp> schedule = null;

    private ProvisionalAssesmentMstDto provisionalMas = null;

    private Date leastFinYear;

    private Long orgId;

    private Long deptId;

    private Date uiDate;

    private Long givenfinYearId;

    private Long scheduleDetId;

    private BillDisplayDto earlyPayRebate = new BillDisplayDto();

    Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>

    List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto = null;

    List<DemandLevelRebateDTO> demandLevelRebateList = null;

    private List<Long> finYearList = null;

    private CommonChallanDTO commonChallanDTO = new CommonChallanDTO();

    private String displayValidMsg;

    private String ownerFullName;

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public Map<Long, String> getFinancialYearMap() {
        return financialYearMap;
    }

    public void setFinancialYearMap(Map<Long, String> financialYearMap) {
        this.financialYearMap = financialYearMap;
    }

    public Date getLeastFinYear() {
        return leastFinYear;
    }

    public void setLeastFinYear(Date leastFinYear) {
        this.leastFinYear = leastFinYear;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public List<LookUp> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LookUp> schedule) {
        this.schedule = schedule;
    }

    public ProvisionalAssesmentMstDto getProvisionalMas() {
        return provisionalMas;
    }

    public void setProvisionalMas(ProvisionalAssesmentMstDto provisionalMas) {
        this.provisionalMas = provisionalMas;
    }

    public Map<Date, Long> getFinancialYearDate() {
        return financialYearDate;
    }

    public void setFinancialYearDate(Map<Date, Long> financialYearDate) {
        this.financialYearDate = financialYearDate;
    }

    public Date getUiDate() {
        return uiDate;
    }

    public void setUiDate(Date uiDate) {
        this.uiDate = uiDate;
    }

    public Long getGivenfinYearId() {
        return givenfinYearId;
    }

    public void setGivenfinYearId(Long givenfinYearId) {
        this.givenfinYearId = givenfinYearId;
    }

    public Long getScheduleDetId() {
        return scheduleDetId;
    }

    public void setScheduleDetId(Long scheduleDetId) {
        this.scheduleDetId = scheduleDetId;
    }

    public List<ProvisionalAssesmentFactorDtlDto> getProvAsseFactDtlDto() {
        return provAsseFactDtlDto;
    }

    public void setProvAsseFactDtlDto(List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto) {
        this.provAsseFactDtlDto = provAsseFactDtlDto;
    }

    public BillDisplayDto getEarlyPayRebate() {
        return earlyPayRebate;
    }

    public void setEarlyPayRebate(BillDisplayDto earlyPayRebate) {
        this.earlyPayRebate = earlyPayRebate;
    }

    public Map<Long, BillDisplayDto> getTaxWiseRebate() {
        return taxWiseRebate;
    }

    public void setTaxWiseRebate(Map<Long, BillDisplayDto> taxWiseRebate) {
        this.taxWiseRebate = taxWiseRebate;
    }

    public List<DemandLevelRebateDTO> getDemandLevelRebateList() {
        return demandLevelRebateList;
    }

    public void setDemandLevelRebateList(List<DemandLevelRebateDTO> demandLevelRebateList) {
        this.demandLevelRebateList = demandLevelRebateList;
    }

    public List<Long> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<Long> finYearList) {
        this.finYearList = finYearList;
    }

    public CommonChallanDTO getCommonChallanDTO() {
        return commonChallanDTO;
    }

    public void setCommonChallanDTO(CommonChallanDTO commonChallanDTO) {
        this.commonChallanDTO = commonChallanDTO;
    }

    public String getDisplayValidMsg() {
        return displayValidMsg;
    }

    public void setDisplayValidMsg(String displayValidMsg) {
        this.displayValidMsg = displayValidMsg;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

}
