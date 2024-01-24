package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;

public class SelfAssessmentSaveDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6413600370675866969L;

    private List<TbBillMas> billMasList = null;

    private ProvisionalAssesmentMstDto provisionalMas = null;

    private Long deptId;

    private Long empId;

    private int languageId;

    private List<Long> finYearList = null;

    private Long finYear = null;

    List<DemandLevelRebateDTO> demandLevelRebateList = null;

    private List<ProvisionalAssesmentFactorDtlDto> factorDtlDtoList = new ArrayList<>(0);

    BillDisplayDto advanceAmt = null;

    CommonChallanDTO offline = null;

    public List<TbBillMas> getBillMasList() {
        return billMasList;
    }

    public void setBillMasList(List<TbBillMas> billMasList) {
        this.billMasList = billMasList;
    }

    public ProvisionalAssesmentMstDto getProvisionalMas() {
        return provisionalMas;
    }

    public void setProvisionalMas(ProvisionalAssesmentMstDto provisionalMas) {
        this.provisionalMas = provisionalMas;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public List<Long> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<Long> finYearList) {
        this.finYearList = finYearList;
    }

    public List<DemandLevelRebateDTO> getDemandLevelRebateList() {
        return demandLevelRebateList;
    }

    public void setDemandLevelRebateList(List<DemandLevelRebateDTO> demandLevelRebateList) {
        this.demandLevelRebateList = demandLevelRebateList;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getFinYear() {
        return finYear;
    }

    public void setFinYear(Long finYear) {
        this.finYear = finYear;
    }

    public List<ProvisionalAssesmentFactorDtlDto> getFactorDtlDtoList() {
        return factorDtlDtoList;
    }

    public void setFactorDtlDtoList(List<ProvisionalAssesmentFactorDtlDto> factorDtlDtoList) {
        this.factorDtlDtoList = factorDtlDtoList;
    }

    public BillDisplayDto getAdvanceAmt() {
        return advanceAmt;
    }

    public void setAdvanceAmt(BillDisplayDto advanceAmt) {
        this.advanceAmt = advanceAmt;
    }

    public CommonChallanDTO getOffline() {
        return offline;
    }

    public void setOffline(CommonChallanDTO offline) {
        this.offline = offline;
    }

    /*
     * public List<BillReceiptPostingDTO> getDemandLevelRebateList() { return demandLevelRebateList; } public void
     * setDemandLevelRebateList(List<BillReceiptPostingDTO> demandLevelRebateList) { this.demandLevelRebateList =
     * demandLevelRebateList; }
     */

}
