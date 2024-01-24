package com.abm.mainet.account.quartz.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.domain.VoucherTemplateDetailEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.repository.FindVoucherDataRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountReceiptEntry;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;
import com.abm.mainet.common.integration.acccount.dto.SrcptFeesDetDTO;
import com.abm.mainet.common.integration.acccount.dto.SrcptModesDetDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillDedDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.quartz.domain.ViewAccountVoucherEntryDetailsEntity;
import com.abm.mainet.quartz.domain.ViewAccountVoucherEntryEntity;
import com.abm.mainet.quartz.domain.ViewBillDeductionEntity;
import com.abm.mainet.quartz.domain.ViewBillExpenditureEntity;
import com.abm.mainet.quartz.domain.ViewSalaryBillMasterEntity;
import com.abm.mainet.quartz.domain.ViewServiceReceiptMasEntity;
import com.abm.mainet.quartz.domain.ViewSrcptFeesDetEntity;
import com.abm.mainet.quartz.domain.ViewSrcptModesDetEntity;

public class AccountVoucherPostingForQuartzImpl implements AccountVoucherPostingForQuartz {

    private static final Logger LOGGER = Logger.getLogger(AccountVoucherPostingForQuartzImpl.class);

    @Autowired
    FindVoucherDataRepository findVoucherDataRepository;
    @Autowired
    private ILocationMasService locMasService;
    @Autowired
    private VoucherTemplateRepository voucherTemplateRepository;
    @Autowired
    private TbFinancialyearService tbFinancialyearService;

    private static final String FI_YEAR_DATE_MAP = "Financial year Status is missing in the given financial year Date : ";
    private static final String ORGID_IS = " and orgid is : ";
    private static final String FI_YEAR_STATUS_CLOSED = "This Financial year status is already closed";
    private static final String CANNOT_BE_NULL_EMPTY_ZERO = "} cannot be null, empty or zero.";
    private static final String VOUCHER_DATE = "voucherDate, ";
    private static final String VOUCHER_SUB_TYPE_ID = "voucherSubTypeId, ";
    private static final String DEPARTMENT_ID = "departmentId, ";
    private static final String NARRATION = "narration, ";
    private static final String FIELD_ID = "fieldId, ";
    private static final String ORGID = "orgId, ";
    private static final String CREATED_BY = "createdBy, ";
    private static final String LG_IP_MAC = "lgIpMac, ";
    private static final String ENTRY_TYPE = "entryType, ";
    private static final String CANNOT_BE_NULL = " } cannot be null, empty or zero.";

    @Override
    // @Transactional
    public void invokeQtzForPosting(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        LOGGER.info(":: In voucher Posting ::");

        final List<ViewAccountVoucherEntryEntity> entity = findVoucherDataRepository
                .getVoucherEntryData(runtimeBean.getOrgId().getOrgid());

        if (entity != null) {

            for (ViewAccountVoucherEntryEntity tempMasVoucherEntityData : entity) {

                VoucherPostDTO masVoucherData = new VoucherPostDTO();

                List<AccountVoucherEntryEntity> existVouEntryList = findVoucherDataRepository
                        .checkVoucherEntryDataExists(String.valueOf(tempMasVoucherEntityData.getVouId()),
                                tempMasVoucherEntityData.getOrg());

                if (existVouEntryList == null || existVouEntryList.isEmpty()) {

                    masVoucherData.setVoucherSubTypeId(tempMasVoucherEntityData.getVouSubtypeCpdId());
                    masVoucherData.setVoucherDate(tempMasVoucherEntityData.getVouDate());
                    masVoucherData.setBillVouPostingDate(tempMasVoucherEntityData.getVouPostingDate());
                    masVoucherData.setDepartmentId(tempMasVoucherEntityData.getDpDeptid());
                    masVoucherData.setVoucherReferenceNo(String.valueOf(tempMasVoucherEntityData.getVouId()));
                    masVoucherData.setVoucherReferenceDate(tempMasVoucherEntityData.getVouReferenceNoDate());
                    masVoucherData.setNarration(tempMasVoucherEntityData.getNarration());
                    masVoucherData.setPayerOrPayee(tempMasVoucherEntityData.getPayerPayee());
                    masVoucherData.setFieldId(tempMasVoucherEntityData.getFieldId());
                    masVoucherData.setOrgId(tempMasVoucherEntityData.getOrg());
                    masVoucherData.setCreatedBy(tempMasVoucherEntityData.getCreatedBy());
                    masVoucherData.setCreatedDate(tempMasVoucherEntityData.getLmodDate());
                    masVoucherData.setLgIpMac(tempMasVoucherEntityData.getLgIpMac());
                    masVoucherData.setEntryType(tempMasVoucherEntityData.getEntryType());
                    // masVoucherData.setPropertyIntFlag(MainetConstants.Y_FLAG);

                    List<VoucherPostDetailDTO> dtoList = new ArrayList<>();
                    List<ViewAccountVoucherEntryDetailsEntity> listDetDetails = findVoucherDataRepository
                            .getVoucherDetEntryData(tempMasVoucherEntityData.getVouId());
                    BigDecimal sumOfModeAmount = BigDecimal.ZERO;
                    if (listDetDetails != null) {
                        for (ViewAccountVoucherEntryDetailsEntity tempDetVoucherData : listDetDetails) {
                            VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
                            dtoListFee.setSacHeadId(tempDetVoucherData.getSacHeadId());
                            dtoListFee.setVoucherAmount(tempDetVoucherData.getVoudetAmt());
                            sumOfModeAmount = sumOfModeAmount.add(tempDetVoucherData.getVoudetAmt());
                            dtoList.add(dtoListFee);
                        }
                    }

                    VoucherPostDetailDTO dtoListMode = new VoucherPostDetailDTO();
                    dtoListMode.setPayModeId(tempMasVoucherEntityData.getFeeMode());
                    dtoListMode.setVoucherAmount(sumOfModeAmount);
                    dtoList.add(dtoListMode);
                    masVoucherData.setVoucherDetails(dtoList);

                    final String validationError = validateVoucherEntryInput(masVoucherData);
                    if (validationError.isEmpty() || validationError == null) {

                        final ResponseEntity<?> response = RestClient.callRestTemplateClient(masVoucherData,
                                ServiceEndpoints.ACCOUNT_POSTING);
                        if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                            LOGGER.info(":: Account Voucher Posting from View is done successfully ::");
                        } else {
                            LOGGER.error(":: Account Voucher Posting from View failed due to :"
                                    + (response != null ? response.getBody() : MainetConstants.BLANK));

                            throw new FrameworkException(
                                    "Check account integration for tax master: NO itegration found for "
                                            + tempMasVoucherEntityData.getNarration());
                        }
                    } else {
                        LOGGER.error(":: Property Tax Voucher Posting Data failed due to :" + (masVoucherData));
                    }
                }
            }
        } else {
            throw new NullPointerException(
                    "Data is not available for this given orgid is : " + runtimeBean.getOrgId().getOrgid());
        }
    }

    public String validateVoucherEntryInput(VoucherPostDTO dto) {

        final StringBuilder builder = new StringBuilder();
        if (dto.getVoucherDate() == null) {
            builder.append(VOUCHER_DATE);
        }
        if ((dto.getEntryType() == null) || dto.getEntryType().isEmpty()) {
            builder.append(ENTRY_TYPE);
        }
        if ((dto.getVoucherSubTypeId() == null) || (dto.getVoucherSubTypeId() == 0l)) {
            builder.append(VOUCHER_SUB_TYPE_ID);
        }
        if ((dto.getDepartmentId() == null) || (dto.getDepartmentId() == 0l)) {
            builder.append(DEPARTMENT_ID);
        }
        if ((dto.getNarration() == null) || dto.getNarration().isEmpty()) {
            builder.append(NARRATION);
        }
        if ((dto.getFieldId() == null) || (dto.getFieldId() == 0l)) {
            builder.append(FIELD_ID);
        }
        if ((dto.getOrgId() == null) || (dto.getOrgId() == 0l)) {
            builder.append(ORGID);
        }
        if ((dto.getCreatedBy() == null) || (dto.getCreatedBy() == 0l)) {
            builder.append(CREATED_BY);
        }
        if ((dto.getLgIpMac() == null) || dto.getLgIpMac().isEmpty()) {
            builder.append(LG_IP_MAC);
        }
        if (!builder.toString().isEmpty()) {
            builder.append(CANNOT_BE_NULL);
        }

        boolean fiYearHeadClosed = false;
        if (dto.getVoucherDate() != null) {
            Date hardClosedFiYearDate = dto.getVoucherDate();
            Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate,
                    dto.getOrgId());
            if (finYeadStatus == null) {
                builder.append(FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + dto.getOrgId());
            } else {
                Long finYeadMonthStatus = tbFinancialyearService.checkSoftClosedFinYearDateExists(hardClosedFiYearDate,
                        dto.getOrgId());
                Organisation org = new Organisation();
                org.setOrgid(dto.getOrgId());
                String fiYearStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus, org)
                        .getLookUpCode();
                String fiYearMonthStatusCode = "";
                if (finYeadMonthStatus != null) {
                    fiYearMonthStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadMonthStatus, org)
                            .getLookUpCode();
                }
                if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode == null || fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN)) {
                        fiYearHeadClosed = true;
                    }
                }
            }
        }
        if (fiYearHeadClosed == false) {
            builder.append(FI_YEAR_STATUS_CLOSED);
        }

        if (!builder.toString().isEmpty()) {
            builder.append(CANNOT_BE_NULL_EMPTY_ZERO);
        }

        return builder.toString();
    }

    @Override
    public void propertyTexReceiptPosting(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final List<ViewServiceReceiptMasEntity> entity = findVoucherDataRepository
                .getPropertyTexReceiptPosting(runtimeBean.getOrgId().getOrgid());

        if (entity != null) {
            AccountReceiptDTO accountReceiptDTO = null;
            List<SrcptFeesDetDTO> srcptFeesDetDTOList = null;
            SrcptFeesDetDTO srcptFeesDetDTO = null;
            List<ViewSrcptFeesDetEntity> receiptFeeDetail = null;
            ViewSrcptModesDetEntity receiptModeDetail = null;
            SrcptModesDetDTO srcptModesDetDTO = null;

            Long fieldId = 0L;
            fieldId = findVoucherDataRepository.getFiledId(Long.valueOf(runtimeBean.getOrgId().getOrgid()));

            for (ViewServiceReceiptMasEntity viewServiceReceiptMasEntity : entity) {

                accountReceiptDTO = new AccountReceiptDTO();

                List<TbServiceReceiptMasEntity> existRecEntryList = findVoucherDataRepository
                        .checkReceiptEntryDataExists(viewServiceReceiptMasEntity.getRmRcptid(),
                                viewServiceReceiptMasEntity.getOrgId());

                if (existRecEntryList == null || existRecEntryList.isEmpty()) {

                    accountReceiptDTO.setFieldId(fieldId);
                    if (viewServiceReceiptMasEntity.getCreatedBy() != null) {
                        accountReceiptDTO
                                .setLangId(Integer.valueOf(viewServiceReceiptMasEntity.getCreatedBy().toString()));
                    }
                    // accountReceiptDTO.setReceiptId(viewServiceReceiptMasEntity.getRmRcptid());
                    accountReceiptDTO.setOrgId(viewServiceReceiptMasEntity.getOrgId());
                    accountReceiptDTO.setCreatedBy(viewServiceReceiptMasEntity.getCreatedBy());
                    accountReceiptDTO.setCreatedDate(viewServiceReceiptMasEntity.getCreatedDate());
                    accountReceiptDTO.setDpDeptId(viewServiceReceiptMasEntity.getDpDeptId());
                    final String macAddress = Utility.getMacAddress();
                    accountReceiptDTO.setLgIpMac(macAddress);
                    // viewServiceReceiptMasEntity.getReceiptDelOrderNo();
                    accountReceiptDTO.setRmReceiptAmt(viewServiceReceiptMasEntity.getRmAmount());
                    accountReceiptDTO.setReceiptAmount(viewServiceReceiptMasEntity.getRmAmount().toString());
                    accountReceiptDTO.setReceiptDate(Utility.dateToString(viewServiceReceiptMasEntity.getRmDate()));
                    accountReceiptDTO.setRmNarration(viewServiceReceiptMasEntity.getRmNarration());
                    accountReceiptDTO.setRecPropertytaxRefId(viewServiceReceiptMasEntity.getRmRcptid());
                    accountReceiptDTO.setReceiptNumber(viewServiceReceiptMasEntity.getRmRcptno().toString());
                    accountReceiptDTO.setReceiptPayeeName(viewServiceReceiptMasEntity.getRmReceivedfrom());
                    accountReceiptDTO.setRecProperyTaxFlag(MainetConstants.Y_FLAG);

                    Long voucherSubTypeIdC = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DMD",
                            PrefixConstants.REV_TYPE_CPD_VALUE, runtimeBean.getOrgId().getOrgid());
                    Long voucherSubTypeIdP = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DMP",
                            PrefixConstants.REV_TYPE_CPD_VALUE, runtimeBean.getOrgId().getOrgid());
                    final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                            MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(),
                            runtimeBean.getOrgId().getOrgid());
                    Long dpDeptId = viewServiceReceiptMasEntity.getDpDeptId();

                    int countC = 0;
                    int countP = 0;
                    receiptFeeDetail = viewServiceReceiptMasEntity.getReceiptFeeDetail();
                    srcptFeesDetDTOList = new ArrayList<>();
                    if (receiptFeeDetail != null) {
                        for (ViewSrcptFeesDetEntity receiptFeeDetails : receiptFeeDetail) {
                            srcptFeesDetDTO = new SrcptFeesDetDTO();
                            // receiptFeeDetails.getBilldetId();
                            BigDecimal sumOfRecDetAmount = null;
                            Long sacHeadId = null;
                            if (receiptFeeDetails.getBillType() != null && !receiptFeeDetails.getBillType().isEmpty()) {
                                if (receiptFeeDetails.getBillType().equals("C") && (countC == 0)) {
                                    sumOfRecDetAmount = findVoucherDataRepository.getSumOfRecDetAmount(
                                            viewServiceReceiptMasEntity.getRmRcptid(), receiptFeeDetails.getBillType());
                                    srcptFeesDetDTO.setRfFeeamount(sumOfRecDetAmount);
                                    VoucherTemplateMasterEntity template = voucherTemplateRepository
                                            .queryDefinedTemplate(voucherSubTypeIdC, dpDeptId,
                                                    viewServiceReceiptMasEntity.getOrgId(), status, null);
                                    List<VoucherTemplateDetailEntity> templateDetList = null;
                                    if (template != null) {
                                        templateDetList = voucherTemplateRepository
                                                .queryDefinedTemplateDet((long) template.getTemplateId(),
                                                        viewServiceReceiptMasEntity.getOrgId());
                                    }
                                    for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : templateDetList) {
                                        if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                                            sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
                                            break;
                                        }
                                    }
                                    srcptFeesDetDTO.setBudgetCodeid(sacHeadId);
                                    srcptFeesDetDTOList.add(srcptFeesDetDTO);
                                    countC++;
                                } else if (receiptFeeDetails.getBillType().equals("P") && (countP == 0)) {
                                    sumOfRecDetAmount = findVoucherDataRepository.getSumOfRecDetAmount(
                                            viewServiceReceiptMasEntity.getRmRcptid(), receiptFeeDetails.getBillType());
                                    srcptFeesDetDTO.setRfFeeamount(sumOfRecDetAmount);
                                    VoucherTemplateMasterEntity template = voucherTemplateRepository
                                            .queryDefinedTemplate(voucherSubTypeIdP, dpDeptId,
                                                    viewServiceReceiptMasEntity.getOrgId(), status, null);
                                    List<VoucherTemplateDetailEntity> templateDetList = null;
                                    if (template != null) {
                                        templateDetList = voucherTemplateRepository
                                                .queryDefinedTemplateDet((long) template.getTemplateId(),
                                                        viewServiceReceiptMasEntity.getOrgId());
                                    }
                                    for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : templateDetList) {
                                        if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                                            sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
                                            break;
                                        }
                                    }
                                    srcptFeesDetDTO.setBudgetCodeid(sacHeadId);
                                    srcptFeesDetDTOList.add(srcptFeesDetDTO);
                                    countP++;
                                } else if (receiptFeeDetails.getBillType().equals("A")) {
                                    srcptFeesDetDTO.setRfFeeamount(receiptFeeDetails.getRfFeeamount());
                                    srcptFeesDetDTO.setBudgetCodeid(receiptFeeDetails.getTaxId());
                                    srcptFeesDetDTOList.add(srcptFeesDetDTO);
                                }
                            }
                            accountReceiptDTO.setReceiptFeeDetail(srcptFeesDetDTOList);
                        }
                    }
                    receiptModeDetail = viewServiceReceiptMasEntity.getReceiptModeDetail();
                    srcptModesDetDTO = new SrcptModesDetDTO();
                    if (receiptModeDetail.getCpdFeemode() != null) {
                        srcptModesDetDTO.setCpdFeemode(receiptModeDetail.getCpdFeemode());
                    } else {
                        throw new NullPointerException(
                                "paymode id is null this given orgid wise : " + viewServiceReceiptMasEntity.getOrgId());
                    }
                    String cpdFeeModeCode = CommonMasterUtility.findLookUpCode(PrefixConstants.PAY_PREFIX.PREFIX_VALUE,
                            viewServiceReceiptMasEntity.getOrgId(), receiptModeDetail.getCpdFeemode());
                    if (cpdFeeModeCode != null && !cpdFeeModeCode.isEmpty()) {
                        srcptModesDetDTO.setCpdFeemodeCode(cpdFeeModeCode);
                    }
                    if (!cpdFeeModeCode.isEmpty() && cpdFeeModeCode != null) {
                        if (cpdFeeModeCode.equals(MainetConstants.Complaint.MODE_CREATE)) {
                        } else {
                            srcptModesDetDTO.setCbBankid(receiptModeDetail.getCbBankid());
                            srcptModesDetDTO.setBaAccountid(receiptModeDetail.getCbBankid());
                            srcptModesDetDTO.setTranRefNumber(receiptModeDetail.getRdChequeddno());
                            srcptModesDetDTO
                                    .setTranRefDatetemp(Utility.dateToString(receiptModeDetail.getRdChequedddate()));
                            srcptModesDetDTO.setRdChequedddate(receiptModeDetail.getRdChequedddate());
                            srcptModesDetDTO.setRdDrawnon(receiptModeDetail.getRdDrawnon());
                        }
                    }
                    srcptModesDetDTO.setRdAmount(receiptModeDetail.getRdAmount());
                    accountReceiptDTO.setReceiptModeDetail(srcptModesDetDTO);
                    final String validationError = validateReceiptInput(accountReceiptDTO);
                    if (validationError.isEmpty() || validationError == null) {
                        final ResponseEntity<?> response = RestClient.callRestTemplateClient(accountReceiptDTO,
                                ServiceEndpoints.RECEIPT_POSTING);
                        if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                            LOGGER.info(":: Property Tex Receipt Posting from View is done successfully ::");
                        } else {
                            LOGGER.error(":: Property Tex Receipt Posting from View failed due to :"
                                    + (response != null ? response.getBody() : MainetConstants.BLANK));

                            throw new FrameworkException(
                                    "Check account integration for tax master: NO itegration found for "
                                            + viewServiceReceiptMasEntity.getRmNarration());
                        }
                    } else {
                        LOGGER.error(":: Property Tax Receipt Posting Data failed due to :" + (accountReceiptDTO));
                    }
                }
            }

        } else {
            throw new NullPointerException(
                    "Data is not available for this given orgid is : " + runtimeBean.getOrgId().getOrgid());
        }

    }

    private String validateReceiptInput(final AccountReceiptDTO dto) {

        final StringBuilder builder = new StringBuilder();
        if ((dto.getReceiptPayeeName() == null) || dto.getReceiptPayeeName().isEmpty()) {
            builder.append(AccountReceiptEntry.PAYEE_NAME);
        }
        if ((dto.getRmNarration() == null) || dto.getRmNarration().isEmpty()) {
            builder.append(MainetConstants.AccountBillEntry.NARRATION);
        }
        if ((dto.getOrgId() == null) || (dto.getOrgId() == 0l)) {
            builder.append(MainetConstants.AccountBillEntry.ORG);
        }
        if ((dto.getCreatedBy() == null) || (dto.getCreatedBy() == 0l)) {
            builder.append(AccountReceiptEntry.CREATED_BY);
        }
        if (dto.getLangId() == 0) {
            builder.append(AccountReceiptEntry.LANG_ID);
        }
        if ((dto.getLgIpMac() == null) || dto.getLgIpMac().isEmpty()) {
            builder.append(AccountReceiptEntry.LG_IP_MAC);
        }

        // validating AccountRecieptDetailsDTO
        if ((dto.getReceiptFeeDetail() == null) || dto.getReceiptFeeDetail().isEmpty()) {
            builder.append(AccountReceiptEntry.RECEIPT_COLLECTION_DETAILS);
        } else {
            int count = 0;
            for (final SrcptFeesDetDTO detail : dto.getReceiptFeeDetail()) {
                if ((detail.getBudgetCodeid() == null) || (detail.getBudgetCodeid() == 0l)) {
                    builder.append(AccountReceiptEntry.RECEIPTR_DETAILS + count)
                            .append(AccountReceiptEntry.RECEIPT_HEAD);
                }
                if ((detail.getRfFeeamount() == null)) {
                    builder.append(AccountReceiptEntry.VOUCHER_DETAILS + count).append(AccountReceiptEntry.FEEAMOUNT);
                }
                count++;
            }
        }
        // validating AccountRecieptModeDTO
        if (dto.getReceiptModeDetail() == null) {
            builder.append(AccountReceiptEntry.RECEIPT_COLLECTION_MODE_DETAILS);
        } else {
            if (dto.getReceiptModeDetail().getCpdFeemodeCode() == null) {
                builder.append(AccountReceiptEntry.MODE);
            } else {
                if (!dto.getReceiptModeDetail().getCpdFeemodeCode().equalsIgnoreCase(MainetConstants.MODE_CREATE)) {

                    if (dto.getReceiptModeDetail().getBaAccountid() == null) {
                        builder.append(AccountReceiptEntry.BANKNAME);
                    }
                    if (dto.getReceiptModeDetail().getTranRefNumber() == null
                            || dto.getReceiptModeDetail().getTranRefNumber().isEmpty()
                            || dto.getReceiptModeDetail().getTranRefNumber() == "") {
                        builder.append(AccountReceiptEntry.TRN_NO);
                    }
                    if (dto.getReceiptModeDetail().getTranRefDatetemp() == null
                            || dto.getReceiptModeDetail().getTranRefDatetemp().isEmpty()
                            || dto.getReceiptModeDetail().getTranRefDatetemp() == "") {
                        builder.append(AccountReceiptEntry.TRN_DATE);
                    }
                }
                if (dto.getReceiptModeDetail().getCpdFeemodeCode().equals(PrefixConstants.WATERMODULEPREFIX.RT)
                        || dto.getReceiptModeDetail().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
                        || dto.getReceiptModeDetail().getCpdFeemodeCode().equals(PrefixConstants.PaymentMode.BANK)) {
                    if (dto.getReceiptModeDetail().getBaAccountid() != null) {
                        boolean bankIdExist = false;
                        Long count = findVoucherDataRepository
                                .checkBankIdExistsOrNot(dto.getReceiptModeDetail().getBaAccountid(), dto.getOrgId());
                        if (count != 0) {
                            bankIdExist = false;
                        } else {
                            bankIdExist = true;
                        }
                        if (bankIdExist) {
                            builder.append(AccountReceiptEntry.RM_BANK_ID_NOT_EXIST);
                        }
                    }
                }
            }
        }

        boolean fiYearHeadClosed = false;
        if (dto.getReceiptDate() != null) {
            Date hardClosedFiYearDate = Utility.stringToDate(dto.getReceiptDate());
            Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate,
                    dto.getOrgId());
            if (finYeadStatus == null) {
                builder.append(FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + dto.getOrgId());
            } else {
                Long finYeadMonthStatus = tbFinancialyearService.checkSoftClosedFinYearDateExists(hardClosedFiYearDate,
                        dto.getOrgId());
                Organisation org = new Organisation();
                org.setOrgid(dto.getOrgId());
                String fiYearStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus, org)
                        .getLookUpCode();
                String fiYearMonthStatusCode = "";
                if (finYeadMonthStatus != null) {
                    fiYearMonthStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadMonthStatus, org)
                            .getLookUpCode();
                }
                if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode == null || fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN)) {
                        fiYearHeadClosed = true;
                    }
                }
            }
        }
        if (fiYearHeadClosed == false) {
            builder.append(FI_YEAR_STATUS_CLOSED);
        }

        if (!builder.toString().isEmpty()) {
            builder.append(ApplicationSession.getInstance().getMessage("accounts.receipt.receiptmode.null"));
        }
        return builder.toString();
    }

    @Override
    public void SalaryBillUpload(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final List<ViewSalaryBillMasterEntity> entity = findVoucherDataRepository
                .getSalaryRecordsForUpload(Long.valueOf(runtimeBean.getOrgId().getOrgid()));

        if (entity != null) {
            VendorBillApprovalDTO vendorBillApprovalDTO = null;
            List<ViewBillExpenditureEntity> viewBillExpenditureEntityList = null;
            List<ViewBillDeductionEntity> viewDeductionDetailList = null;
            List<VendorBillDedDetailDTO> dedDetListDto = null;
            List<VendorBillExpDetailDTO> expDetListDto = null;
            VendorBillExpDetailDTO vendorBillExpDetailDTO = null;
            for (ViewSalaryBillMasterEntity viewSalaryBillMasterEntity : entity) {
                vendorBillApprovalDTO = new VendorBillApprovalDTO();

                List<AccountBillEntryMasterEnitity> existSalBillEntryList = findVoucherDataRepository
                        .checkSalBillEntryDataExists(viewSalaryBillMasterEntity.getBmId(),
                                viewSalaryBillMasterEntity.getOrgId());

                if (existSalBillEntryList == null || existSalBillEntryList.isEmpty()) {

                    // vendorBillApprovalDTO.setId(viewSalaryBillMasterEntity.getBmId());
                    vendorBillApprovalDTO.setBillNo(viewSalaryBillMasterEntity.getBmBillno());
                    vendorBillApprovalDTO.setBillTypeId(viewSalaryBillMasterEntity.getBmBillTypeCpdId());
                    vendorBillApprovalDTO
                            .setBillEntryDate(Utility.dateToString(viewSalaryBillMasterEntity.getBmEntryDate()));
                    vendorBillApprovalDTO.setDepartmentId(viewSalaryBillMasterEntity.getDpDepId());
                    vendorBillApprovalDTO.setVendorId(viewSalaryBillMasterEntity.getVmVenderId());
                    vendorBillApprovalDTO.setInvoiceNumber(viewSalaryBillMasterEntity.getBmInvoiceNumber());
                    vendorBillApprovalDTO.setInvoiceAmount(viewSalaryBillMasterEntity.getBmInvoiceValue());
                    vendorBillApprovalDTO.setNarration(viewSalaryBillMasterEntity.getBmNarration());
                    vendorBillApprovalDTO.setOrgId(viewSalaryBillMasterEntity.getOrgId());
                    vendorBillApprovalDTO.setCreatedBy(viewSalaryBillMasterEntity.getCreatedBy());
                    vendorBillApprovalDTO
                            .setCreatedDate(Utility.dateToString(viewSalaryBillMasterEntity.getCreatedDate()));
                    vendorBillApprovalDTO.setFieldId(viewSalaryBillMasterEntity.getFieldId());
                    vendorBillApprovalDTO.setLgIpMacAddress(viewSalaryBillMasterEntity.getLgIpMac());
                    vendorBillApprovalDTO.setBillIntRefId(viewSalaryBillMasterEntity.getBmId());

                    viewBillExpenditureEntityList = viewSalaryBillMasterEntity.getViewExpenditureDetailList();
                    expDetListDto = new ArrayList<>();
                    if (viewBillExpenditureEntityList != null) {
                        for (ViewBillExpenditureEntity viewBillExpenditureEntityLists : viewBillExpenditureEntityList) {
                            vendorBillExpDetailDTO = new VendorBillExpDetailDTO();

                            if (viewBillExpenditureEntityLists.getBchChargesAmt() != null) {
                                vendorBillExpDetailDTO
                                        .setBalanceAmount(viewBillExpenditureEntityLists.getBchChargesAmt().toString());
                            }

                            vendorBillExpDetailDTO.setBudgetCodeId(viewBillExpenditureEntityLists.getSacHeadId());
                            vendorBillExpDetailDTO
                                    .setSanctionedAmount(viewBillExpenditureEntityLists.getBchChargesAmt());
                            vendorBillExpDetailDTO.setAmount(viewBillExpenditureEntityLists.getBchChargesAmt());
                            expDetListDto.add(vendorBillExpDetailDTO);
                        }
                    }
                    vendorBillApprovalDTO.setExpDetListDto(expDetListDto);

                    viewDeductionDetailList = viewSalaryBillMasterEntity.getViewDeductionDetailList();
                    dedDetListDto = new ArrayList<>();
                    if (viewDeductionDetailList != null) {
                        for (ViewBillDeductionEntity viewDeductionDetailLists : viewDeductionDetailList) {
                            VendorBillDedDetailDTO vendorBillDedDetailDTO = new VendorBillDedDetailDTO();

                            vendorBillDedDetailDTO.setBchId(viewDeductionDetailLists.getBchId());

                            vendorBillDedDetailDTO.setDeductionAmount(viewDeductionDetailLists.getDeductionAmount());
                            vendorBillDedDetailDTO.setBudgetCodeId(viewDeductionDetailLists.getSacHeadId());

                            dedDetListDto.add(vendorBillDedDetailDTO);
                        }
                    }
                    vendorBillApprovalDTO.setDedDetListDto(dedDetListDto);

                    final String validationError = validateSalaryBillInput(vendorBillApprovalDTO);
                    if (validationError.isEmpty() || validationError == null) {

                        final ResponseEntity<?> response = RestClient.callRestTemplateClient(vendorBillApprovalDTO,
                                ServiceEndpoints.SALARY_POSTING);
                        if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                            LOGGER.info(":: Salary Bill Upload Posting from View is done successfully ::");
                        } else {
                            LOGGER.error(":: Salary Bill Upload Posting from View failed due to :"
                                    + (response != null ? response.getBody() : MainetConstants.BLANK));

                            throw new FrameworkException(
                                    "Check account integration for Bill master: NO itegration found for "
                                            + vendorBillApprovalDTO.getNarration());
                        }
                    } else {
                        LOGGER.error(
                                ":: Property Tax Salary Bill Posting Data failed due to :" + (vendorBillApprovalDTO));
                    }
                }
            }

        } else {
            throw new NullPointerException(
                    "Data is not available for this given orgid is : " + runtimeBean.getOrgId().getOrgid());
        }

    }

    public String validateSalaryBillInput(final VendorBillApprovalDTO dto) {

        final StringBuilder builder = new StringBuilder();
        if (dto.getOrgId() == null) {
            builder.append(MainetConstants.AccountBillEntry.ORG);
        }
        if (dto.getBillTypeId() == null) {
            builder.append(MainetConstants.AccountBillEntry.BILL_TYPE_ID);
        }
        if (dto.getVendorId() == null) {
            builder.append(MainetConstants.AccountBillEntry.VENDOR_ID);
        }
        if (dto.getInvoiceAmount() == null) {
            builder.append(MainetConstants.AccountBillEntry.INVOICE_AMT);
        }
        if ((dto.getNarration() == null) || dto.getNarration().isEmpty()) {
            builder.append(MainetConstants.AccountBillEntry.NARRATION);
        }

        // Validating expenditure details
        if (dto.getExpDetListDto() == null) {
            builder.append(MainetConstants.AccountBillEntry.EXP_LIST_DTO);
        } else {
            int count = 0;
            for (final VendorBillExpDetailDTO detail : dto.getExpDetListDto()) {
                if (detail.getBudgetCodeId() == null) {
                    builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
                            .append(MainetConstants.AccountBillEntry.BUDGET_CODE_ID);
                }
                if (detail.getAmount() == null) {
                    builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
                            .append(MainetConstants.AccountBillEntry.AMOUNT);
                }
                if (detail.getSanctionedAmount() == null) {
                    builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
                            .append(MainetConstants.AccountBillEntry.SANCTION_AMT);
                }
                if (detail.getDisallowedAmount() != null) {
                    if ((detail.getDisallowedRemark() == null) && detail.getDisallowedRemark().isEmpty()) {
                        builder.append(MainetConstants.EXP_DET_LIST_DTO + count)
                                .append(MainetConstants.AccountBillEntry.DIS_ALLOWED_REMARK);
                    }
                }
                count++;
            }
        }
        // Validating deduction details
        if (dto.getDedDetListDto() != null) {

            int count = 0;
            for (final VendorBillDedDetailDTO detail : dto.getDedDetListDto()) {
                if (detail.getBudgetCodeId() == null) {
                    builder.append(MainetConstants.AccountBillEntry.DED_DET_LIST + count)
                            .append(MainetConstants.AccountBillEntry.BUDGET_CODE_ID);
                }
                if (detail.getBchId() == null) {
                    builder.append(MainetConstants.AccountBillEntry.DED_DET_LIST + count)
                            .append(MainetConstants.AccountBillEntry.BCH_ID);
                }
                if (detail.getDeductionAmount() == null) {
                    builder.append(MainetConstants.AccountBillEntry.DED_DET_LIST + count)
                            .append(MainetConstants.AccountBillEntry.SANCTION_AMT);
                }
                count++;
            }
        }

        boolean fiYearHeadClosed = false;
        if (dto.getBillEntryDate() != null) {
            Date hardClosedFiYearDate = Utility.stringToDate(dto.getBillEntryDate());
            Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate,
                    dto.getOrgId());
            if (finYeadStatus == null) {
                builder.append(FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + dto.getOrgId());
            } else {
                Long finYeadMonthStatus = tbFinancialyearService.checkSoftClosedFinYearDateExists(hardClosedFiYearDate,
                        dto.getOrgId());
                Organisation org = new Organisation();
                org.setOrgid(dto.getOrgId());
                String fiYearStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus, org)
                        .getLookUpCode();
                String fiYearMonthStatusCode = "";
                if (finYeadMonthStatus != null) {
                    fiYearMonthStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadMonthStatus, org)
                            .getLookUpCode();
                }
                if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode == null || fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN)) {
                        fiYearHeadClosed = true;
                    }
                }
            }
        }
        if (fiYearHeadClosed == false) {
            builder.append(FI_YEAR_STATUS_CLOSED);
        }

        if (!builder.toString().isEmpty()) {
            builder.append(CANNOT_BE_NULL_EMPTY_ZERO);
        }
        return builder.toString();
    }

}
