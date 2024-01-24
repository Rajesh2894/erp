package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.TbBillMas;

public class AssessmentChargeCalDTO implements Serializable {

    private static final long serialVersionUID = 6654172644523100795L;

    private List<DocumentDetailsVO> checkList = null;

    private List<BillDisplayDto> displayDto = null;

    private List<TbBillMas> billMasList = null;

    private List<Long> taxCatList = null;

    private ProvisionalAssesmentMstDto provisionalMas = null;

    private double billTotalAmt;

    Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>

    private BillDisplayDto earlyPayRebate = null;

    List<DemandLevelRebateDTO> demandLevelRebateList = null;

    private Long deptId;

    BillDisplayDto advanceAmt = null;

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public List<TbBillMas> getBillMasList() {
        return billMasList;
    }

    public void setBillMasList(List<TbBillMas> billMasList) {
        this.billMasList = billMasList;
    }

    public double getBillTotalAmt() {
        return billTotalAmt;
    }

    public void setBillTotalAmt(double billTotalAmt) {
        this.billTotalAmt = billTotalAmt;
    }

    public List<BillDisplayDto> getDisplayDto() {
        return displayDto;
    }

    public void setDisplayDto(List<BillDisplayDto> displayDto) {
        this.displayDto = displayDto;
    }

    public List<Long> getTaxCatList() {
        return taxCatList;
    }

    public void setTaxCatList(List<Long> taxCatList) {
        this.taxCatList = taxCatList;
    }

    public ProvisionalAssesmentMstDto getProvisionalMas() {
        return provisionalMas;
    }

    public void setProvisionalMas(ProvisionalAssesmentMstDto provisionalMas) {
        this.provisionalMas = provisionalMas;
    }

    public Map<Long, BillDisplayDto> getTaxWiseRebate() {
        return taxWiseRebate;
    }

    public void setTaxWiseRebate(Map<Long, BillDisplayDto> taxWiseRebate) {
        this.taxWiseRebate = taxWiseRebate;
    }

    public BillDisplayDto getEarlyPayRebate() {
        return earlyPayRebate;
    }

    public void setEarlyPayRebate(BillDisplayDto earlyPayRebate) {
        this.earlyPayRebate = earlyPayRebate;
    }

    public List<DemandLevelRebateDTO> getDemandLevelRebateList() {
        return demandLevelRebateList;
    }

    public void setDemandLevelRebateList(List<DemandLevelRebateDTO> demandLevelRebateList) {
        this.demandLevelRebateList = demandLevelRebateList;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public BillDisplayDto getAdvanceAmt() {
        return advanceAmt;
    }

    public void setAdvanceAmt(BillDisplayDto advanceAmt) {
        this.advanceAmt = advanceAmt;
    }

}
