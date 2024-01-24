package com.abm.mainet.rnl.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dao.IDemandNoticeDAO;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.dto.DemandNoticeDTO;
import com.abm.mainet.rnl.dto.ReportDTO;
import com.abm.mainet.rnl.repository.RLBillMasterRepository;
import com.abm.mainet.rnl.repository.ReportSummaryRepository;

@Service
public class DemandNoticeServiceImpl implements IDemandNoticeService {

    @Autowired
    ReportSummaryRepository reportSummaryRepository;

    @Autowired
    RLBillMasterRepository rlBillMasterRepository;

    @Autowired
    IRLBILLMasterService iRLBILLMasterService;

    @Autowired
    IDemandNoticeDAO iNoticeBillDAO;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private BRMSRNLService brmsRNLService;

    @Override
    public List<ReportDTO> fetchDemandNoticeReportByCondition(Date date, Long vendorId, Long contractId, Long orgId,
            boolean noticePrintDetails) {
        List<ReportDTO> reportDTOList = new ArrayList<>();
        if (Optional.ofNullable(contractId).orElse(0L) != 0 && Optional.ofNullable(vendorId).orElse(0L) != 0) {
            // check combination of vendorId and contract id present or not in TB_CONTRACT_PART2_DETAIL
            ContractPart2DetailEntity data = reportSummaryRepository.getContractPart2DetailEntity(contractId, vendorId);
            if (data == null)
                return reportDTOList;
        }
        if (Optional.ofNullable(contractId).orElse(0L) != 0) {
            // fetch result based on contractId
            if (noticePrintDetails) {
                // other query
                List<RLBillMaster> dueDetails = rlBillMasterRepository.fetchDueDetails(date, contractId, orgId);
                // get contract No
                ContractMastEntity contMas = reportSummaryRepository.getContractMas(contractId, orgId);
                List<String> vendorNames = reportSummaryRepository.getContractorNames(contractId, orgId);
                for (RLBillMaster rlBillMas : dueDetails) {
                    // out. date and out. AMT
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO
                            .setOutstandingDate(Utility.dateToString(rlBillMas.getDueDate(), MainetConstants.DATE_FORMAT_UPLOAD));
                    reportDTO.setOutstandingAmt(new BigDecimal(rlBillMas.getBalanceAmount()).setScale(2));
                    reportDTO.setContractNo(contMas.getContNo());
                    reportDTO.setTenantName(vendorNames.get(0));
                    reportDTO.setContractDate(Utility.dateToString(contMas.getContDate(), MainetConstants.DATE_FORMAT_UPLOAD));
                    reportDTO.setContractId(contractId);
                    reportDTOList.add(reportDTO);
                }

            } else {
                Object[] results = rlBillMasterRepository.getDueAmountByCond(date, contractId, orgId);
                if (results.length != 0) {
                    Object[] object = (Object[]) results[0];
                    long contId = (long) object[0];
                    ReportDTO reportDTO = new ReportDTO();
                    // get contract No
                    ContractMastEntity contMas = reportSummaryRepository.getContractMas(contId, orgId);
                    reportDTO.setContractNo(contMas.getContNo());
                    reportDTO.setOutstandingAmt(new BigDecimal((Double) object[1]).setScale(2));
                    List<String> vendorNames = reportSummaryRepository.getContractorNames(contId, orgId);
                    String tenantName = "";
                    for (String vendor : vendorNames) {
                        tenantName += vendor + ",";
                    }
                    // remove comma from end side
                    if (tenantName != null) {
                        if (tenantName.endsWith(",")) {
                            tenantName = tenantName.substring(0, tenantName.length() - 1);
                        }
                    }
                    reportDTO.setTenantName(tenantName);
                    reportDTO.setContractId(contractId);
                    reportDTOList.add(reportDTO);
                }
            }
        } else {
            List<Object[]> vendorData = reportSummaryRepository.fetchContractByVendor(vendorId, orgId);
            for (Object[] obj : vendorData) {
                long contId = (long) obj[0];
                String contractNo = (String) obj[1] != null ? (String) obj[1] : "NA";
                String tenantName = (String) obj[2] != null ? (String) obj[2] : "NA";
                Object[] results = rlBillMasterRepository.getDueAmountByCond(date, contId, orgId);
                if (results.length != 0) {
                    Object[] object = (Object[]) results[0];
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setContractNo(contractNo);
                    reportDTO.setOutstandingAmt(new BigDecimal((Double) object[1]).setScale(2));
                    reportDTO.setTenantName(tenantName);
                    reportDTO.setContractId(contId);
                    reportDTOList.add(reportDTO);
                }
            }
        }
        return reportDTOList;
    }

    @Override
    public List<DemandNoticeDTO> fetchDemandNoticeBillData(Long contractId, Date previousFinancialEndDate, Long orgId) {
        List<DemandNoticeDTO> demandNoticeDTOList = new ArrayList<>();

        Date dateAfterCurrentFinance = Utility.getAddedDateBy2(previousFinancialEndDate, 1);
        List<RLBillMaster> billMasters = iRLBILLMasterService.finByContractId(contractId, orgId,
                MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
        // get tax name using tax id and
        TbTaxMas tax = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                .findTaxByTaxIdAndOrgId(billMasters.get(0).getTaxId(), orgId);
        // String taxName = tax.getTaxDesc();
        // get arrears and current AMT
        StringBuilder arrearsAmt = new StringBuilder("dueDate <= :date");// financialEndDate
        StringBuilder currentAmt = new StringBuilder("dueDate >= :date");// dateAfterCurrentFinance
        // call DAO to get AMT
        Object[] objArrears = iNoticeBillDAO.getDemandNoticeDataByCond(contractId, orgId, previousFinancialEndDate, arrearsAmt);
        Object[] objCurrent = iNoticeBillDAO.getDemandNoticeDataByCond(contractId, orgId, dateAfterCurrentFinance, currentAmt);
        // 1st tax charges value set like TAXDESC and arrears and current
        DemandNoticeDTO noticeBill = new DemandNoticeDTO();
        BigDecimal totalAmt = new BigDecimal(0.00);
        noticeBill.setTaxName(tax.getTaxDesc());
        if (objArrears != null) {
            noticeBill.setArrearsAmt(new BigDecimal((Double) objArrears[1]).setScale(2));
            totalAmt = new BigDecimal((Double) objArrears[1]).setScale(2);
        } else {
            noticeBill.setArrearsAmt(new BigDecimal(0).setScale(2));
        }
        if (objCurrent != null) {
            noticeBill.setCurrentAmt(new BigDecimal((Double) objCurrent[1]).setScale(2));
            totalAmt = totalAmt.add(new BigDecimal((Double) objCurrent[1]).setScale(2));
        } else {
            noticeBill.setCurrentAmt(new BigDecimal(0).setScale(2));
        }
        noticeBill.setTotalAmt(totalAmt);

        // property name
        Object[] data = reportSummaryRepository.getContractPropertyData(contractId, orgId);// property name[0], contract No[1]
        Object[] object = (Object[]) data[0];
        noticeBill.setPropertyNameAndAddress((String) object[0] + "/" + object[2]);
        noticeBill.setContractNo((String) object[1]);
        // 1st object add based on arrears
        demandNoticeDTOList.add(noticeBill);

        // get GST percentage from BRMS
        // fetch assign taxes to CBP
        // 2nd object add based on arrears

        // 3rd object add based on current
        makeGSTDemandCharges(demandNoticeDTOList, orgId);
        return demandNoticeDTOList;
    }

    void makeGSTDemandCharges(List<DemandNoticeDTO> demandNoticeDTOList, Long orgId) {
        // START BRMS call initialize model
        final WSRequestDTO initReqDTO = new WSRequestDTO();
        initReqDTO.setModelName(MainetConstants.RnLCommon.CHECKLIST_RNLRATEMASTER);
        final WSResponseDTO response = brmsCommonService.initializeModel(initReqDTO);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 1);
            final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
            // setting default parameter rnl rate master parameter
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            rnlRateMaster.setOrgId(orgId);
            rnlRateMaster.setServiceCode(MainetConstants.EstateContract.CBP);
            rnlRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
                    .getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL, PrefixConstants.LookUpPrefix.CAA)
                    .getLookUpId()));
            taxReqDTO.setDataModel(rnlRateMaster);
            // fetch taxes assign to CBP service
            final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);
            if (taxResponseDTO.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {
                if (!taxResponseDTO.isFree()) {
                    final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                    final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        final RNLRateMaster master1 = (RNLRateMaster) rate;
                        master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
                        master1.setRateStartDate(new Date().getTime());
                        requiredCHarges.add(master1);
                    }
                    WSRequestDTO chargeReqDTO = new WSRequestDTO();
                    chargeReqDTO.setDataModel(requiredCHarges);
                    WSResponseDTO applicableCharges = brmsRNLService.getApplicableCharges(chargeReqDTO);
                    final List<ChargeDetailDTO> output = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                    if (CollectionUtils.isEmpty(output)) {
                        // throw new FrameworkException("Charges not Found in brms Sheet");
                    } else {
                        for (ChargeDetailDTO charge : output) {
                            BigDecimal percentageRate = BigDecimal.valueOf(charge.getPercentageRate());
                            DemandNoticeDTO demandNotice = new DemandNoticeDTO();
                            demandNotice.setTaxName(charge.getChargeDescEng() + "(" + percentageRate + " %)");
                            BigDecimal taxArrears = (demandNoticeDTOList.get(0).getArrearsAmt().multiply(percentageRate))
                                    .divide(new BigDecimal(100));
                            demandNotice.setArrearsAmt(taxArrears.setScale(2));
                            BigDecimal taxCurrent = (demandNoticeDTOList.get(0).getCurrentAmt().multiply(percentageRate))
                                    .divide(new BigDecimal(100));
                            demandNotice.setCurrentAmt(taxCurrent.setScale(2));
                            demandNotice.setTotalAmt(taxArrears.add(taxCurrent).setScale(2));
                            demandNoticeDTOList.add(demandNotice);
                        }
                    }
                }
            }
        }
    }

}
