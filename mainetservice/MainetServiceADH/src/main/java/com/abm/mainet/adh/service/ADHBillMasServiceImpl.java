package com.abm.mainet.adh.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.adh.domain.ADHBillMasEntity;
import com.abm.mainet.adh.repository.ADHBillMasRepository;
import com.abm.mainet.bill.service.BillDetailsService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Utility;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author cherupelli.srikanth
 * @since 11 November 2019
 */
@Service(value = "ADHBillPayment")
public class ADHBillMasServiceImpl implements IADHBillMasService, BillPaymentService, BillDetailsService {

    private static Logger LOGGER = Logger.getLogger(ADHBillMasServiceImpl.class);
    @Autowired
    private ADHBillMasRepository adhBillMasRepository;

    @Autowired
    private ContractAgreementRepository contractAgreementRepository;
    @Autowired
    private IContractAgreementService iContractAgreementService;

    @Override
    @Transactional
    public void saveADHBillMas(List<ADHBillMasEntity> billMasEntityList) {
        adhBillMasRepository.save(billMasEntityList);
    }

    @Override
    public List<ADHBillMasEntity> finByContractId(final Long contId, final Long orgId, String paidFlag, String bmType) {
        return adhBillMasRepository.findByContractIdAndOrgIdAndPaidFlagAndBillTypeOrderByBillMasNoAsc(contId, orgId,
                paidFlag, bmType);
    }

    @Override
    @Transactional
    public void updateRLBillMas(ADHBillMasEntity adhBillMas) {
        adhBillMas.setPaymentDate(new Date());

        adhBillMas.setRemarks("Bill Payment");

        adhBillMasRepository.save(adhBillMas);
    }

    @Override
    public ContractAgreementSummaryDTO getReceiptAmountDetailsForBillPayment(Long contId,
            ContractAgreementSummaryDTO contractAgreementSummaryDTO, Long orgId,
            List<ADHBillMasEntity> adhBillMasList) {

        AtomicDouble balanceAmount = new AtomicDouble(0);
        AtomicDouble overdueAmount = new AtomicDouble(0);
        AtomicDouble actualAmount = new AtomicDouble(0);

        for (ADHBillMasEntity adhBillMasEntity : adhBillMasList) {
            actualAmount.addAndGet(adhBillMasEntity.getBillAmount());
            if (Utility.compareDate(adhBillMasEntity.getDueDate(), new Date())) {
                overdueAmount.addAndGet(adhBillMasEntity.getBalanceAmount());
                balanceAmount.addAndGet(adhBillMasEntity.getBalanceAmount());
            } else {
                balanceAmount.addAndGet(adhBillMasEntity.getBalanceAmount());
            }
        }

        contractAgreementSummaryDTO.setBalanceAmount(BigDecimal.valueOf(balanceAmount.doubleValue()).setScale(2));
        contractAgreementSummaryDTO.setOverdueAmount(BigDecimal.valueOf(overdueAmount.doubleValue()).setScale(2));

        return contractAgreementSummaryDTO;
    }

    @Override
    public List<BillReceiptPostingDTO> updateBillMasterAmountPaid(String uniqueId, Double amount, Long orgid, Long userId,
            String ipAddress, Date manualReceptDate, String flatNo) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " updateBillMasterAmountPaid method");
        List<BillReceiptPostingDTO> result = new ArrayList<>();
        ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService
                .findByContractNo(orgid, uniqueId);
        if (contractAgreementSummaryDTO != null) {
            Long contractExist = ApplicationContextProvider.getApplicationContext()
                    .getBean(IAdvertisementContractMappingService.class)
                    .findContractByContIdAndOrgId(contractAgreementSummaryDTO.getContId(), orgid);
            if (contractExist != null && contractExist > 0) {
                List<ADHBillMasEntity> adhBillMasList = finByContractId(
                        contractAgreementSummaryDTO.getContId(), orgid,
                        MainetConstants.FlagN, MainetConstants.FlagB);

                if (CollectionUtils.isNotEmpty(adhBillMasList)) {
                    contractAgreementSummaryDTO = getReceiptAmountDetailsForBillPayment(
                            contractAgreementSummaryDTO.getContId(),
                            contractAgreementSummaryDTO, orgid, adhBillMasList);
                    if (amount.equals(adhBillMasList.get(0).getBalanceAmount())) {
                        ADHBillMasEntity adhBillMas = adhBillMasList.get(0);
                        adhBillMas.setPaidAmount(amount);
                        adhBillMas.setBalanceAmount(0d);
                        adhBillMas.setUpdatedBy(userId);
                        adhBillMas.setUpdatedDate(new Date());
                        adhBillMas.setLgIpMacUpd(ipAddress);
                        adhBillMas.setPaidFlag(MainetConstants.Common_Constant.YES);
                        adhBillMas.setBillType(MainetConstants.STATUS.INACTIVE);
                        updateRLBillMas(adhBillMas);
                    } else {

                        Double actualPayAmount = amount;
                        for (int i = 0; i < adhBillMasList.size(); i++) {
                            if (actualPayAmount > 0) {
                                ADHBillMasEntity adhBillMas = adhBillMasList.get(i);
                                if (actualPayAmount > adhBillMas.getBalanceAmount()
                                        || actualPayAmount.equals(adhBillMas.getBalanceAmount())) {
                                    actualPayAmount = actualPayAmount - adhBillMas.getBalanceAmount();
                                    adhBillMas.setPaidAmount(adhBillMas.getBalanceAmount());
                                    adhBillMas.setBalanceAmount(0d);
                                    adhBillMas.setUpdatedBy(userId);
                                    adhBillMas.setUpdatedDate(new Date());
                                    adhBillMas.setLgIpMacUpd(ipAddress);
                                    adhBillMas.setPaidFlag(MainetConstants.Common_Constant.YES);
                                    adhBillMas.setBillType(MainetConstants.STATUS.INACTIVE);

                                    updateRLBillMas(adhBillMas);

                                } else {
                                    adhBillMas.setPaidAmount(actualPayAmount);
                                    adhBillMas.setBalanceAmount(adhBillMas.getBalanceAmount() - actualPayAmount);
                                    actualPayAmount = 0.0;
                                    adhBillMas.setUpdatedBy(userId);
                                    adhBillMas.setUpdatedDate(new Date());
                                    adhBillMas.setLgIpMacUpd(ipAddress);
                                    adhBillMas.setPaidFlag(MainetConstants.FlagN);
                                    updateRLBillMas(adhBillMas);

                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " updateBillMasterAmountPaid method");
        return result;
    }

    @Override
    public boolean saveAdvancePayment(Long orgId, Double amount, String uniqueId, Long userId, Long receiptId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getApplicantUserNameModuleWise(long orgId, String uniqueKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VoucherPostDTO reverseBill(TbServiceReceiptMasBean feedetailDto, Long orgId, Long userId, String ipAddress) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster, Organisation orgnisation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonChallanDTO getBillDetails(CommonChallanDTO offline) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " getBillDetails method");
        ContractAgreementSummaryDTO summaryDTO = iContractAgreementService
                .findByContractNo(offline.getOrgId(), offline.getUniquePrimaryId());

        if (summaryDTO != null) {

            Long contractExist = ApplicationContextProvider.getApplicationContext()
                    .getBean(IAdvertisementContractMappingService.class)
                    .findContractByContIdAndOrgId(summaryDTO.getContId(), offline.getOrgId());
            if (contractExist != null && contractExist > 0) {
                List<ADHBillMasEntity> adhBillMasList = finByContractId(summaryDTO.getContId(), offline.getOrgId(),
                        MainetConstants.FlagN, MainetConstants.FlagB);
                if (CollectionUtils.isNotEmpty(adhBillMasList)) {
                    summaryDTO = getReceiptAmountDetailsForBillPayment(summaryDTO.getContId(), summaryDTO,
                            offline.getOrgId(), adhBillMasList);
                }
            }
            offline.setAmountToPay(summaryDTO.getBalanceAmount().toString());
            offline.setUserId(MainetConstants.Property.UserId);
            offline.setOrgId(summaryDTO.getOrgId());
            offline.setLangId(1);
            offline.setFeeIds(new HashMap<>(0));
            offline.setBillDetIds(new HashMap<>(0));
            offline.setApplicantName(summaryDTO.getContp1Name());
            offline.setApplNo(summaryDTO.getContId());
            offline.setApplicantAddress(summaryDTO.getAddress());
            offline.setUniquePrimaryId(summaryDTO.getContNo());
            offline.setMobileNumber(summaryDTO.getMobileNo());
            offline.setServiceId(offline.getServiceId());
            // offline.setDeptId(billPayDto.getDeptId());
            offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
            offline.setDocumentUploaded(false);
            // offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
            // offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
            // if (billPayDto.getWard3() != null)
            // offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
            // offline.setPlotNo(billPayDto.getPlotNo());
            // offline.setApplicantFullName(billPayDto.getOwnerFullName());
            // offline.setPdRv(billPayDto.getPdRv());
            // offline.setPropNoConnNoEstateNoV(billPayDto.getPropNo());
            // offline.setPropNoConnNoEstateNoL(ApplicationSession.getInstance().getMessage("propertydetails.PropertyNo."));
            offline.setEmpType(null);
            // offline.setUsageType(billPayDto.getUsageType1());
            offline.setReferenceNo(summaryDTO.getContNo());
            // offline.setApplicantFullName(billPayDto.getOwnerFullName());
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " getBillDetails method");
        return offline;

    }

}
