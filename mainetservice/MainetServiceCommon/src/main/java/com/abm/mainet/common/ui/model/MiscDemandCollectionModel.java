package com.abm.mainet.common.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.ReceivableDemandEntryDTO;
import com.abm.mainet.common.service.IReceivableDemandEntryService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

@Component
@Scope("session")
public class MiscDemandCollectionModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IReceivableDemandEntryService receivableDemandEntryService;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    ReceivableDemandEntryDTO receivableDemandEntryDto = new ReceivableDemandEntryDTO();
    List<ReceivableDemandEntryDTO> receivableDemandEntryDtosList = new ArrayList<>();
    private String saveMode;
    private String processUpdate;
    private boolean isBillAgainstCCN;
    private String billNumbers = "";

    @Override
    public boolean saveForm() {
        boolean status = false;
        if (receivableDemandEntryDto.getReceivedAmount().compareTo(new BigDecimal("0")) == 0) {
            this.addValidationError(getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.BILL_AMOUNT));
            return false;
        } else {
            try {
                CommonChallanDTO offline = getOfflineDTO();
                List<ReceivableDemandEntryDTO> receivableDemandDtosList = new ArrayList<>();

                for (ReceivableDemandEntryDTO rcvDto : receivableDemandEntryDtosList) {
                    if (rcvDto.isCheckBillPay() == true) {
                        billNumbers = billNumbers + rcvDto.getBillNo() + MainetConstants.WHITE_SPACE;
                        rcvDto.setBillPostingDate(new Date());
                        rcvDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        rcvDto.setUpdatedDate(new Date());
                        rcvDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                        rcvDto.getRcvblDemandList().forEach(tr -> {
                            tr.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                            tr.setUpdatedDate(new Date());
                            tr.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                        });
                        receivableDemandDtosList.add(rcvDto);
                    }
                }
                setChallanDToandSaveChallanData(offline);
                ChallanReceiptPrintDTO printDto = receivableDemandEntryService.updateBillAfterPayment(receivableDemandDtosList, getOfflineDTO());
                //setChallanPrintDto(printDto);
                status = true;
                if (MainetConstants.FlagY.equals(getProcessUpdate())) {
                    getWorkflowActionDto().setPaymentMode(MainetConstants.FlagY);
                    getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
                    iWorkflowActionService.updateWorkFlow(getWorkflowActionDto(), UserSession.getCurrent().getEmployee(), UserSession.getCurrent().getOrganisation().getOrgid(), getServiceId());
                }
                setSuccessMessage(getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.MISC_DEMAND_COLLECTION_SUCCESS));
            } catch (Exception e) {
                logger.error("Exception Occured While Misc. Demand Payment", e);
                this.addValidationError(getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.MISC_DEMAND_COLLECTION_EXCEPTION));
            }
        }
        return status;
    }


    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline) {
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setAmountToPay(Double.toString(receivableDemandEntryDto.getReceivedAmount().doubleValue()));
        offline.setAmountToShow(receivableDemandEntryDto.getReceivedAmount().doubleValue());
        offline.setEmailId(receivableDemandEntryDto.getCustomerDetails().getEmail());
        offline.setApplicantName(receivableDemandEntryDto.getCustomerDetails().getfName());
        if (isBillAgainstCCN == true) {
            offline.setUniquePrimaryId(receivableDemandEntryDto.getRefNumber());
        } else {
            offline.setUniquePrimaryId(receivableDemandEntryDto.getApplicationId().toString());
        }
        offline.setApplicantAddress(receivableDemandEntryDto.getCustomerDetails().getHouseComplexName());
        if (StringUtils.isNoneEmpty(receivableDemandEntryDto.getCustomerDetails().getMobileNo())) {
            offline.setMobileNumber(receivableDemandEntryDto.getCustomerDetails().getMobileNo());
        }
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setServiceId(receivableDemandEntryDto.getServiceId());
        offline.setDeptId(receivableDemandEntryDto.getDeptId());
        if (receivableDemandEntryDto.getApplicationId() != null) {
            offline.setApplNo(receivableDemandEntryDto.getApplicationId());
        } else {
            offline.setApplNo(0L);
        }

        Map<Long, Double> details = new HashMap<>(0);
        Map<Long, Long> billDetails = new HashMap<>(0);
        ConcurrentHashMap<Long, Long> supplimentryBillIdMap = new ConcurrentHashMap<>();

        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        offline.setBillDetIds(billDetails);
        offline.setSupplimentryBillIdMap(supplimentryBillIdMap);
        offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
        setOfflineDTO(offline);
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public ReceivableDemandEntryDTO getReceivableDemandEntryDto() {
        return receivableDemandEntryDto;
    }

    public void setReceivableDemandEntryDto(ReceivableDemandEntryDTO receivableDemandEntryDto) {
        this.receivableDemandEntryDto = receivableDemandEntryDto;
    }

    public String getProcessUpdate() {
        return processUpdate;
    }

    public void setProcessUpdate(String processUpdate) {
        this.processUpdate = processUpdate;
    }

    public List<ReceivableDemandEntryDTO> getReceivableDemandEntryDtosList() {
        return receivableDemandEntryDtosList;
    }

    public void setReceivableDemandEntryDtosList(List<ReceivableDemandEntryDTO> receivableDemandEntryDtosList) {
        this.receivableDemandEntryDtosList = receivableDemandEntryDtosList;
    }

    public boolean isBillAgainstCCN() {
        return isBillAgainstCCN;
    }

    public void setBillAgainstCCN(boolean isBillAgainstCCN) {
        this.isBillAgainstCCN = isBillAgainstCCN;
    }


}
