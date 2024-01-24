/**
 * 
 */
package com.abm.mainet.asset.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.repository.AssetDetailsReportRepo;
import com.abm.mainet.asset.ui.dto.AssetDetailsReportDto;
import com.abm.mainet.asset.ui.dto.AssetInformationReportDto;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.ReportDetailsListDTO;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;

/**
 * @author satish.rathore
 *
 */
@Service
public class AssetDetailsReportServiceImpl implements IAssetDetailsReportService {

    @Autowired
    private AssetDetailsReportRepo assetDetailsReportRepo;
    @Autowired
    private IOrganisationService iOrganisationService;
    @Resource
    private ILocationMasService iLocationMasService;
    @Resource
    private TbAcVendormasterService vendorMasterService;
    @Autowired
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private IEmployeeService iEmployeeService;
    @Autowired
    private IAssetInformationService infoService;
    @Autowired
    private IAssetValuationService valuationService;

    private static final Logger LOGGER = Logger.getLogger(AssetDetailsReportServiceImpl.class);

    @Override
    @Transactional(readOnly=true)
    public AssetDetailsReportDto findDetailReport(final Long assetGroup, final Long assetType, final Long assetClass1,
            final Long assetClass2, final Long orgId, final Integer langId) {
        List<Object[]> assetList = assetDetailsReportRepo.findAssetInfo(assetGroup, assetType, assetClass1, assetClass2,
                orgId);
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        AssetDetailsReportDto assetDetailsReportDto = null;
        final List<AssetDetailsReportDto> list = new ArrayList<>();
        if (assetList != null && !assetList.isEmpty()) {
            assetDetailsReportDto = new AssetDetailsReportDto();
            // final AssetDetailsReportDto dto;
            // for (final Object[] objects : assetList) {
            assetList.stream().parallel().forEachOrdered(objects -> {
                final AssetDetailsReportDto dto = new AssetDetailsReportDto();
                if (objects[0] != null) {
                    dto.setAssetId(((BigInteger) objects[0]).longValue());
                }
                if (objects[1] != null) {
                    dto.setAstName(objects[1].toString());

                }
                if (objects[2] != null) {
                    final String groupType;
                    if (langId != null && langId == 1) {
                        groupType = CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[2]).longValue(), organisation)
                                .getDescLangFirst();
                    } else {
                        groupType = CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[2]).longValue(), organisation)
                                .getDescLangSecond();

                    }
                    dto.setAstGroupDes(groupType);
                }
                if (objects[3] != null) {
                    final String astType;
                    if (langId != null && langId == 1) {
                        astType = CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[3]).longValue(), organisation)
                                .getDescLangFirst();
                    } else {
                        astType = CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[3]).longValue(), organisation)
                                .getDescLangSecond();
                    }
                    dto.setAstTypeDesc(astType);
                }
                if (objects[4] != null) {
                    if (langId != null && langId == 1) {
                        dto.setAstClassification((CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[4]).longValue(), organisation)
                                .getDescLangFirst()));
                    } else {
                        dto.setAstClassification((CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[4]).longValue(), organisation)
                                .getDescLangSecond()));

                    }
                }
                if (objects[5] != null) {
                    if (langId != null && langId == 1) {
                        dto.setAstClass((CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[5]).longValue(), organisation)
                                .getDescLangFirst()));
                    } else {
                        dto.setAstClass((CommonMasterUtility
                                .getNonHierarchicalLookUpObject(((BigInteger) objects[5]).longValue(), organisation)
                                .getDescLangSecond()));

                    }
                }
                if (objects[6] != null) {

                    dto.setDateOfacquisition((Date) objects[6]);
                }
                if (objects[7] != null) {

                    dto.setLifeYear(((BigDecimal) objects[7]).longValue());
                }
                if (objects[8] != null) {

                    dto.setCurrentvalue(new BigDecimal(objects[8].toString()));
                }
                if (objects[9] != null) {
                    dto.setSerialNo((String) objects[9]);
                }
                if (objects[10] != null) {
                    dto.setAssetCode((String) objects[10]);
                }
                if (objects[11] != null) {
                    dto.setCostOfacqui(new BigDecimal(objects[11].toString()));
                }

                list.add(dto);
            });

            assetDetailsReportDto.setList(list);
            if (assetGroup != null) {
                final String groupType;
                if (langId != null && langId == 1) {
                    groupType = CommonMasterUtility.getNonHierarchicalLookUpObject(assetGroup, organisation)
                            .getDescLangFirst();
                } else {
                    groupType = CommonMasterUtility.getNonHierarchicalLookUpObject(assetGroup, organisation)
                            .getDescLangSecond();
                }
                assetDetailsReportDto.setAstGroupDes(groupType);
            }
            if (assetType != null) {
                final String astType;
                if (langId != null && langId == 1) {
                    astType = CommonMasterUtility.getNonHierarchicalLookUpObject(assetType, organisation)
                            .getDescLangFirst();
                } else {
                    astType = CommonMasterUtility.getNonHierarchicalLookUpObject(assetType, organisation)
                            .getDescLangSecond();
                }
                assetDetailsReportDto.setAstTypeDesc(astType);
            }
            if (assetClass1 != null) {
                final String astClass1;
                if (langId != null && langId == 1) {
                    astClass1 = CommonMasterUtility.getNonHierarchicalLookUpObject(assetClass1, organisation)
                            .getDescLangFirst();
                } else {
                    astClass1 = CommonMasterUtility.getNonHierarchicalLookUpObject(assetClass1, organisation)
                            .getDescLangSecond();
                }
                assetDetailsReportDto.setAstClass(astClass1);
            }
            if (assetClass2 != null) {
                final String astClass2;
                if (langId != null && langId == 1) {
                    astClass2 = CommonMasterUtility.getNonHierarchicalLookUpObject(assetClass2, organisation)
                            .getDescLangFirst();
                } else {
                    astClass2 = CommonMasterUtility.getNonHierarchicalLookUpObject(assetClass2, organisation)
                            .getDescLangSecond();
                }

                assetDetailsReportDto.setAstClassification(astClass2);
            }

        }

        return assetDetailsReportDto;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ReportDetailsListDTO> registerOfMovableReport(Long assetClass1, Long orgId, Long faYearId, Long prefixId) {
        List<Object[]> movRroList = assetDetailsReportRepo.registerOfMovableReport(assetClass1, orgId, faYearId, prefixId);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        List<ReportDetailsListDTO> registerMovList = new ArrayList<>();
        if (movRroList != null && !movRroList.isEmpty()) {

            // for (Object[] movRroLists : movRroList) {
            movRroList.parallelStream().forEach(movRroLists -> {
                final ReportDetailsListDTO reportDetailsListDTO = new ReportDetailsListDTO();
                if (movRroLists[0] != null) {
                    reportDetailsListDTO.setAssetId(convertBigIntTOLong(movRroLists[0]));
                }

                if (movRroLists[1] != null) {
                    reportDetailsListDTO.setSerialNo((String) movRroLists[1]);
                }
                if (movRroLists[2] != null) {
                    reportDetailsListDTO.setAssetLocationDesc((String) movRroLists[2]);
                }
                if (movRroLists[3] != null) {
                    reportDetailsListDTO.setQuantityDisposed(convertBigIntTOLong(movRroLists[3]));
                }
                if (movRroLists[4] != null) {
                    reportDetailsListDTO.setDateOfacquisitions(Utility.dateToString((Date) movRroLists[4]));
                }
                if (movRroLists[28] != null) {
                    reportDetailsListDTO.setModeOfAcquisitionDesc((String) movRroLists[28]);

                }
                if (movRroLists[6] != null) {
                    reportDetailsListDTO.setPayOrderNo((String) movRroLists[6]);
                }
                if (movRroLists[7] != null) {
                    reportDetailsListDTO.setRefCashBook((String) movRroLists[7]);
                }
                if (movRroLists[8] != null) {
                    reportDetailsListDTO.setCostOfAcquisition((BigDecimal) movRroLists[8]);
                }
                if (movRroLists[9] != null) {
                    reportDetailsListDTO.setPaidPersonNameDesc((String) movRroLists[9]);

                }
                if (movRroLists[10] != null) {
                    reportDetailsListDTO.setExpensePurpose((String) movRroLists[10]);
                }
                if (movRroLists[11] != null) {
                    reportDetailsListDTO.setFundSource((String) movRroLists[11]);
                }
                if (movRroLists[12] != null) {
                    reportDetailsListDTO.setOpenWrittenValue((BigDecimal) movRroLists[12]);
                }
                if (movRroLists[13] != null) {

                    reportDetailsListDTO.setDepreciationYearDesc((String) movRroLists[13]);
                }
                if (movRroLists[14] != null && new BigDecimal(movRroLists[14].toString()).signum() == 1) {
                    reportDetailsListDTO.setDepreciation((BigDecimal) movRroLists[14]);
                } else {
                    reportDetailsListDTO.setDepreciation((BigDecimal) movRroLists[26]);
                }
                if (movRroLists[15] != null) {
                    reportDetailsListDTO.setCloseWrittenValue((BigDecimal) movRroLists[15]);
                }
                if (movRroLists[16] != null) {
                    reportDetailsListDTO.setDateOfdisposals(Utility.dateToString((Date) movRroLists[16]));
                }
                if (movRroLists[17] != null) {
                    reportDetailsListDTO.setDisposedPersonNameDesc((String) movRroLists[17]);
                }
                if (movRroLists[18] != null) {
                    reportDetailsListDTO.setDateOfdisposals(Utility.dateToString((Date) movRroLists[18]));
                }
                if (movRroLists[19] != null && !movRroLists[19].equals("NA")) {
                    reportDetailsListDTO.setNoOfDisposalOrder(convertBigIntTOLong(movRroLists[19]));
                }
                if (movRroLists[20] != null) {
                    reportDetailsListDTO.setSaleValueRealised((BigDecimal) movRroLists[20]);
                }
                if (movRroLists[21] != null && !movRroLists[21].equals("NA")) {
                    reportDetailsListDTO.setBalanceQuantity((Long) movRroLists[21]);
                }
                if (movRroLists[22] != null && !movRroLists[22].equals("NA")) {
                    reportDetailsListDTO.setReleasedSecurityDeposit((BigDecimal) movRroLists[22]);
                }
                if (movRroLists[23] != null && !movRroLists[23].equals("NA")) {
                    reportDetailsListDTO.setAutOfficerDesc((String) movRroLists[23]);
                }
                if (movRroLists[25] != null && !movRroLists[25].equals("NA")) {
                    reportDetailsListDTO.setAssetCode((String) movRroLists[25]);
                }
                if (movRroLists[27] != null) {
                    reportDetailsListDTO.setAssetDesc((String) movRroLists[27]);
                }

                registerMovList.add(reportDetailsListDTO);
            });
        }
        return registerMovList;

    }

    private Long convertBigIntTOLong(Object obj) {
        BigInteger bigInt = (BigInteger) obj;
        return bigInt.longValue();
    }

    @Override
    @Transactional(readOnly=true)
    public Long getLandAssetIdByAssetCode(final Long orgId, final Long assetClass, final String assetCode) {
        Object id = assetDetailsReportRepo.getAssetIdByAssetCodeAndClass(orgId, assetClass, assetCode.toLowerCase());
        if (id != null) {
            Long assetId = (Long) id;
            return assetId;
        }
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public Long getPrefixIdByPrefixCode(final Long orgId, final String prefix, final String assetClass) {
        Object id = assetDetailsReportRepo.getPrefixIdByPrefixCode(orgId, prefix, assetClass);
        if (id != null) {
            Long prefixId = (Long) id;
            return prefixId;
        }
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public AssetInformationReportDto getPrimaryDetails(final Long assetId, final Long orgId, final Integer langId) {

        final AssetDetailsDTO detailDTO = infoService.getDetailsByAssetId(assetId);
        final AssetInformationDTO infoDTO = detailDTO.getAssetInformationDTO();
        final AssetClassificationDTO classDTO = detailDTO.getAssetClassificationDTO();
        final AssetServiceInformationDTO serviceDTO = detailDTO.getAstSerInfoDTO();
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        List<TbLocationMas> locList = iLocationMasService.fillAllActiveLocationByOrgId(orgId);
        final AssetInformationReportDto reportDTO = new AssetInformationReportDto();
        reportDTO.setAssetIdentiNo(infoDTO.getAstCode());
        reportDTO.setDescriptionStru(infoDTO.getDetails());
        reportDTO.setAcquisitionMethod(infoDTO.getAcquisitionMethod());
        if (langId != null && langId == 1) {
            reportDTO.setAcquMethodDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(infoDTO.getAcquisitionMethod(), organisation).getDescLangFirst());
        } else {
            reportDTO.setAcquMethodDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(infoDTO.getAcquisitionMethod(), organisation).getDescLangSecond());
        }
        AssetSpecificationDTO specDTO = infoDTO.getAssetSpecificationDTO();
        if (specDTO != null) {
            if (specDTO.getArea() != null) {
                reportDTO.setArea(specDTO.getArea());
                if (specDTO.getAreaValue() != null && specDTO.getAreaValue().intValue() != 0) {
                    reportDTO.setAreaValue(specDTO.getAreaValue());
                    reportDTO.setAreaUnitDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(specDTO.getAreaValue(), organisation).getDescLangFirst());
                }
            }
            if (specDTO.getLength() != null) {
                reportDTO.setLength(specDTO.getLength());
                if (specDTO.getLengthValue() != null && specDTO.getLengthValue().intValue() != 0) {
                    reportDTO.setLengthUnit(specDTO.getLengthValue());
                    reportDTO.setLengthUnitDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(specDTO.getLengthValue(), organisation).getDescLangFirst());
                }
            }
            if (specDTO.getBreadth() != null) {
                reportDTO.setBreadth(specDTO.getBreadth());
                if (specDTO.getBreadthValue() != null && specDTO.getBreadthValue().intValue() != 0) {
                    reportDTO.setBreadthUnit(specDTO.getBreadthValue());
                    reportDTO.setBreadtUnitDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(specDTO.getBreadthValue(), organisation)
                                    .getDescLangFirst());
                }
            }
        }
        if (classDTO != null) {
            if (classDTO.getLocation() != null && classDTO.getLocation().intValue() != 0) {
                reportDTO.setLocation(classDTO.getLocation());
                locList = locList.stream().filter(loc -> loc != null && loc.getLocId().equals(classDTO.getLocation()))
                        .collect(Collectors.toList());
                if (locList != null && !locList.isEmpty()) {
                    if (StringUtils.isNotEmpty(locList.get(0).getLocNameEng())) {
                        reportDTO.setLocationDes(locList.get(0).getLocNameEng());
                    }
                }
            }
        }
        if (serviceDTO != null) {
            if (serviceDTO.getWarrenty() != null && serviceDTO.getWarrenty().intValue() != 0) {
                reportDTO.setWarrenty(serviceDTO.getWarrenty());
            }
        }
        return reportDTO;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ReportDetailsListDTO> registerLandReport(final Long assetId, final Long orgId, final Integer langId,
            final Date startDate, final Date endDate) {

        final AssetDetailsDTO detailDTO = infoService.getDetailsByAssetId(assetId);
        final AssetInformationDTO infoDTO = detailDTO.getAssetInformationDTO();
        final AssetPurchaseInformationDTO purchaseDTO = detailDTO.getAssetPurchaseInformationDTO();
        final List<AssetValuationDetailsDTO> dtoList;
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        if (startDate != null) {
            dtoList = valuationService.findAssetBetweenDates(orgId, assetId, startDate,
                    endDate);
        } else {
            dtoList = valuationService.findAssetTillDate(orgId, assetId,
                    endDate);
        }
        // only show that record which is approved for retire
        List<Object[]> retireList = assetDetailsReportRepo.getRetireValue(assetId, orgId);

        final List<ReportDetailsListDTO> reportList = new ArrayList<>();

        if (dtoList != null && !dtoList.isEmpty()) {
            for (AssetValuationDetailsDTO dto : dtoList) {
                final ReportDetailsListDTO report = new ReportDetailsListDTO();

                report.setSerialNo(infoDTO.getSerialNo());
                report.setLandUsageDesc(infoDTO.getPurpose());
                report.setExpensePurpose(infoDTO.getPurpose());

                if (purchaseDTO != null) {
                    report.setDateOfacquisition(purchaseDTO.getDateOfAcquisition());
                    report.setCostOfAcquisition(purchaseDTO.getCostOfAcquisition());
                    if (purchaseDTO.getPurchaseOrderNo() != null && !purchaseDTO.getPurchaseOrderNo().isEmpty()) {
                        report.setPayOrderNo(purchaseDTO.getPurchaseOrderNo());
                    }
                    // not available is not required thats why i commented that
                    /*
                     * report.setRefCashBook("Not Available"); report.setRefRegisterAsset("Not Available");
                     * report.setFundSource("Not Available"); report.setReceiptVoucherNo("Not Available");
                     * report.setRemarks("Not Available");
                     */

                    final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            AccountConstants.AC.getValue(), PrefixConstants.VSS, (int) langId, organisation);
                    final Long vendorStatus = lookUpVendorStatus.getLookUpId();
                    List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(orgId, vendorStatus);
                    if (vendorList != null && !vendorList.isEmpty()) {
                        vendorList = vendorList.stream()
                                .filter(vendor -> vendor != null
                                        && vendor.getVmVendorid().equals(purchaseDTO.getFromWhomAcquired()))
                                .collect(Collectors.toList());
                        if (vendorList != null && !vendorList.isEmpty()) {
                            vendorList.get(0).getVmVendorname();
                            report.setPaidPersonNameDesc(vendorList.get(0).getVmVendorname());
                        }
                    }

                    if (retireList != null && !retireList.isEmpty()) {
                        for (Object[] retire : retireList) {
                            if (retire[0] != null) {
                                report.setDateOfDisposal((Date) retire[0]);
                            }

                            if (retire[1] != null) {
                                report.setDisposedPersonNameDesc((String) retire[1]);
                            }
                            if (retire[2] != null) {
                                report.setSaleValueRealised((BigDecimal) retire[2]);
                            }
                            if (retire[3] != null) {
                                report.setAutOfficer((Long) retire[3]);
                                EmployeeBean bean = iEmployeeService.findById((Long) retire[3]);
                                report.setAutOfficerDesc(bean.getEmpname());
                            }
                        }
                    }
                }
                reportList.add(report);
                // we need to show only one latest record that is why use break there
                // break;
            }
            return reportList;
        }
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ReportDetailsListDTO> registerImmovableReport(Long assetId, Long orgId, Integer langId,
            final Date startDate, final Date endDate) {
        final AssetDetailsDTO detailDTO = infoService.getDetailsByAssetId(assetId);
        final AssetInformationDTO infoDTO = detailDTO.getAssetInformationDTO();
        final AssetPurchaseInformationDTO purchaseDTO = detailDTO.getAssetPurchaseInformationDTO();
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        TbFinancialyear financiaYear = null;
        List<AssetValuationDetailsDTO> dtoList;
        if (startDate != null) {
            dtoList = valuationService.findAssetBetweenDates(orgId, assetId, startDate,
                    endDate);
        } else {
            dtoList = valuationService.findAssetTillDate(orgId, assetId,
                    endDate);
        }
        List<Object[]> retireList = assetDetailsReportRepo.getRetireValue(assetId, orgId);
        final List<ReportDetailsListDTO> reportList = new ArrayList<>();
        if (dtoList != null && !dtoList.isEmpty()) {
            for (AssetValuationDetailsDTO dto : dtoList) {
                final ReportDetailsListDTO report = new ReportDetailsListDTO();

                report.setSerialNo(infoDTO.getSerialNo());
                report.setLandUsageDesc(infoDTO.getPurpose());
                report.setExpensePurpose(infoDTO.getPurpose());

                if (purchaseDTO != null) {
                    report.setDateOfacquisition(purchaseDTO.getDateOfAcquisition());
                    report.setCostOfAcquisition(purchaseDTO.getCostOfAcquisition());
                    if (purchaseDTO.getPurchaseOrderNo() != null && !purchaseDTO.getPurchaseOrderNo().isEmpty()) {
                        report.setPayOrderNo(purchaseDTO.getPurchaseOrderNo());
                    }
                    report.setOpenWrittenValue(dto.getPreviousBookValue());
                    report.setDepreciationYear(dto.getBookFinYear());
                    if (dto.getDeprValue().signum() == 1) {
                        report.setDepreciation(dto.getDeprValue());
                    } else {
                        report.setDepreciation(dto.getAccumDeprValue());
                    }
                    report.setCloseWrittenValue(dto.getCurrentBookValue());

                    financiaYear = tbFinancialyearService.findYearById(dto.getBookFinYear(), orgId);
                    report.setDepreciationYearDesc(
                            Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate()));

                    final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            AccountConstants.AC.getValue(), PrefixConstants.VSS, (int) langId, organisation);
                    final Long vendorStatus = lookUpVendorStatus.getLookUpId();
                    List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(orgId, vendorStatus);
                    if (vendorList != null && !vendorList.isEmpty()) {
                        vendorList = vendorList.stream()
                                .filter(vendor -> vendor != null
                                        && vendor.getVmVendorid().equals(purchaseDTO.getFromWhomAcquired()))
                                .collect(Collectors.toList());
                        if (vendorList != null && !vendorList.isEmpty()) {
                            vendorList.get(0).getVmVendorname();
                            report.setPaidPersonNameDesc(vendorList.get(0).getVmVendorname());
                        }
                    }

                    if (retireList != null && !retireList.isEmpty()) {
                        for (Object[] retire : retireList) {
                            if (retire[0] != null) {
                                report.setDateOfDisposal((Date) retire[0]);
                            }

                            if (retire[1] != null) {
                                report.setDisposedPersonNameDesc((String) retire[1]);
                            }
                            if (retire[2] != null) {
                                report.setSaleValueRealised((BigDecimal) retire[2]);
                            }
                            if (retire[3] != null) {
                                report.setAutOfficer((Long) retire[3]);
                                EmployeeBean bean = iEmployeeService.findById((Long) retire[3]);
                                report.setAutOfficerDesc(bean.getEmpname());
                            }
                        }
                    }
                }
                reportList.add(report);
                // we need to show only one latest record that is why use break there
                // break;
            }
            return reportList;
        }
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public Long getImmovableAssetIdByAssetCode(Long orgId, Long assetClass, String assetCode) {
        Object id = assetDetailsReportRepo.getAssetIdByAssetCodeAndClass(orgId, assetClass, assetCode.toLowerCase());
        if (id != null) {
            Long assetId = (Long) id;
            return assetId;
        }
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public List<AssetInformationDTO> getAssetCodeByCategory(Long assetClass, Long orgId) {
        List<AssetInformationDTO> astList = new ArrayList<>();
        if (assetClass != null && orgId != null) {
            astList = assetDetailsReportRepo.getAssetCodeByCategory(assetClass, orgId);
        }
        if (astList != null && !astList.isEmpty()) {
            return astList;
        }
        return astList;
    }

}