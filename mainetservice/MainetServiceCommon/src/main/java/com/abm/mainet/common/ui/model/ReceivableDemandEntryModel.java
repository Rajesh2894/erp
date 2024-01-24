package com.abm.mainet.common.ui.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.ReceivableDemandEntryDTO;
import com.abm.mainet.common.dto.ReceivableDemandEntryDetailsDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IReceivableDemandEntryService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author satish.kadu
 *
 */
@Component
@Scope("session")
public class ReceivableDemandEntryModel extends AbstractFormModel {

    private static final Logger LOGGER = Logger.getLogger(ReceivableDemandEntryModel.class);
    private static final long serialVersionUID = 1L;

    @Autowired
    private IReceivableDemandEntryService receivableDemandEntryService;
    
    @Autowired
    private IFinancialYearService financialYearService;

    ReceivableDemandEntryDTO receivableDemandEntryDto = new ReceivableDemandEntryDTO();
    private List<ReceivableDemandEntryDTO> receivableDemandEntryDtosList = new ArrayList<>();
    private List<ReceivableDemandEntryDetailsDTO> deletedDemandList = new ArrayList<>();
    private List<TbDepartment> deptList = new ArrayList<>();
    private List<TbTaxMas> taxlist = new ArrayList<>();
    Map<Long, String> financeMap;
    Set<Long> taxSubCat = new HashSet<>();
    private String saveMode;

    @Override
    public boolean saveForm() {
        boolean status = false;
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        try {
            ReceivableDemandEntryDTO recvblDto = new ReceivableDemandEntryDTO();
            if (getSaveMode().equals(MainetConstants.MODE_EDIT)) {
                receivableDemandEntryDto.setUpdatedBy(empId);
                receivableDemandEntryDto.setUpdatedDate(new Date());
                receivableDemandEntryDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                receivableDemandEntryDto.getCustomerDetails().setUserId(empId);
                receivableDemandEntryDto.getRcvblDemandList().forEach(tr -> {
                    if (tr.getBillDetId() != null) {
                        tr.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        tr.setUpdatedDate(new Date());
                        tr.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                        tr.setBillDetailsAmount(BigDecimal.valueOf(Math.abs(Math.round(tr.getBillDetailsAmount().doubleValue()))).setScale(2, RoundingMode.HALF_UP));                        
                    } else {

                        Long applicableAtId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL,
                                PrefixConstants.LookUp.CHARGE_MASTER_CAA, UserSession.getCurrent().getOrganisation()).getLookUpId();
                        Long taxId = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class).getTaxId(applicableAtId,
                                UserSession.getCurrent().getOrganisation().getOrgid(), receivableDemandEntryDto.getDeptId(), 
                                tr.getTaxCategory1(), tr.getTaxCategory2());
                        tr.setTaxId(taxId);
                        tr.setBillDetailsAmount(BigDecimal.valueOf(Math.abs(Math.round(tr.getBillDetailsAmount().doubleValue()))).setScale(2, RoundingMode.HALF_UP));
                        tr.setPaidBillDetAmount(new BigDecimal("0"));
                        tr.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                        tr.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        tr.setCreatedDate(new Date());
                        tr.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                        tr.setIsDeleted(MainetConstants.FlagN);
                    }
                });
                receivableDemandEntryDto.getRcvblDemandList().addAll(getDeletedDemandList());
                recvblDto = receivableDemandEntryService.updateReceivableDemandEntry(receivableDemandEntryDto);
                recvblDto.setCustomerDetails(receivableDemandEntryDto.getCustomerDetails());
                setReceivableDemandEntryDto(recvblDto);
                setSuccessMessage(getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.RECEIVABELE_DEMAND_ENTRY_EDIT_SUCCESS) + receivableDemandEntryDto.getCustomerDetails().getCcnNumber());
                status = true;
            } else {    
                Long faYearId = financialYearService.getFinanceYearId(new Date());
                receivableDemandEntryDto.setFaYearId(faYearId);
                receivableDemandEntryDto.setPaidBillAmount(new BigDecimal("0"));
                receivableDemandEntryDto.setOrgid(orgId);
                receivableDemandEntryDto.setCreatedBy(empId);
                receivableDemandEntryDto.setCreatedDate(new Date());
                receivableDemandEntryDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                receivableDemandEntryDto.setBilligDate(new Date());
                receivableDemandEntryDto.getCustomerDetails().setLangId((long) UserSession.getCurrent().getLanguageId());
                receivableDemandEntryDto.getCustomerDetails().setUserId(empId);
                receivableDemandEntryDto.getCustomerDetails().setOrgId(orgId);
                receivableDemandEntryDto.getCustomerDetails().setLocId(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId());
                receivableDemandEntryDto.getCustomerDetails().setServiceId(receivableDemandEntryDto.getServiceId());
                receivableDemandEntryDto.getRcvblDemandList().forEach(tr -> {

                    Long applicableAtId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL,
                            PrefixConstants.LookUp.CHARGE_MASTER_CAA, UserSession.getCurrent().getOrganisation()).getLookUpId();
                    Long taxId = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class).getTaxId(applicableAtId,
                            UserSession.getCurrent().getOrganisation().getOrgid(), receivableDemandEntryDto.getDeptId(), tr.getTaxCategory1(), tr.getTaxCategory2());
                    tr.setTaxId(taxId);
                    tr.setBillDetailsAmount(BigDecimal.valueOf(Math.abs(Math.round(tr.getBillDetailsAmount().doubleValue()))).setScale(2, RoundingMode.HALF_UP));
                    tr.setPaidBillDetAmount(new BigDecimal("0"));
                    tr.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    tr.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    tr.setCreatedDate(new Date());
                    tr.setUpdatedDate(new Date());
                    tr.setIsDeleted(MainetConstants.FlagN);
                });

                recvblDto = receivableDemandEntryService.saveReceivableDemandEntry(receivableDemandEntryDto);
                setReceivableDemandEntryDto(recvblDto);
                setSuccessMessage(getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.RECEIVABELE_DEMAND_ENTRY_SAVE_SUCCESS) + recvblDto.getRefNumber() +MainetConstants.WHITE_SPACE+
                        getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.BILL_NUMBER) + recvblDto.getBillNo());
                status = true;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occured while Saving Supplimentry Bill :" , e);
            addValidationError(getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.EXCEPTION_OCCURED));
        }
        return status;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbDepartment> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<TbDepartment> deptList) {
        this.deptList = deptList;
    }

    public List<TbTaxMas> getTaxlist() {
        return taxlist;
    }

    public void setTaxlist(List<TbTaxMas> taxlist) {
        this.taxlist = taxlist;
    }

    public Map<Long, String> getFinanceMap() {
        return financeMap;
    }

    public void setFinanceMap(Map<Long, String> financeMap) {
        this.financeMap = financeMap;
    }

    public ReceivableDemandEntryDTO getReceivableDemandEntryDto() {
        return receivableDemandEntryDto;
    }

    public void setReceivableDemandEntryDto(ReceivableDemandEntryDTO receivableDemandEntryDto) {
        this.receivableDemandEntryDto = receivableDemandEntryDto;
    }

    public Set<Long> getTaxSubCat() {
        return taxSubCat;
    }

    public void setTaxSubCat(Set<Long> taxSubCat) {
        this.taxSubCat = taxSubCat;
    }

    public List<ReceivableDemandEntryDTO> getReceivableDemandEntryDtosList() {
        return receivableDemandEntryDtosList;
    }

    public void setReceivableDemandEntryDtosList(List<ReceivableDemandEntryDTO> receivableDemandEntryDtosList) {
        this.receivableDemandEntryDtosList = receivableDemandEntryDtosList;
    }

    public List<ReceivableDemandEntryDetailsDTO> getDeletedDemandList() {
        return deletedDemandList;
    }

    public void setDeletedDemandList(List<ReceivableDemandEntryDetailsDTO> deletedDemandList) {
        this.deletedDemandList = deletedDemandList;
    }

    
}
