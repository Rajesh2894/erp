/**
 * 
 */
package com.abm.mainet.asset.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.ui.dto.AssetErrorDTO;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.AstSchedulerMasterDTO;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.asset.ui.dto.DeprAprFactorsDTO;
import com.abm.mainet.asset.ui.dto.DeprAprScheduleDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class DepreciationCalculationDetailsImpl implements IDepreciationCalculationDetailsService {

    @Autowired
    private IAssetInformationService infoService;

    @Autowired
    private IDeprAprCalculationService calculateService;

    @Autowired
    private IAssetValuationService valuationService;

    @Autowired
    private TbFinancialyearService financialyearService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IInformationService informationService;

    @Autowired
    private IChartOfDepreciationMasterService chartService;

    @Autowired
    private IOrganisationService organisationService;

    @Resource
    private SecondaryheadMasterService shmService;

    @Autowired
    private IDepreciationService depreciationService;

    @Autowired
    IMaintenanceService maintenanceService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    private static final Logger LOGGER = Logger.getLogger(DepreciationCalculationDetailsImpl.class);

    /**
     * Used to get list of asset by asset class and orgId
     * 
     * @param assetId
     * @return DepreciationAssetDTO
     */
    @Override
    public List<AssetValuationDetailsDTO> getAssetWithDepreciation(final Long orgId,
            final ChartOfDepreciationMasterDTO cdmDTO, final Long assetstatus) {

        final List<AssetValuationDetailsDTO> depDTOList = new ArrayList<>();
        try {
            List<AssetInformationDTO> assetList = infoService.getAssetByAssetClass(orgId, cdmDTO.getAssetClass(),
                    assetstatus, MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED);
            if (assetList != null && !assetList.isEmpty()) {

                for (AssetInformationDTO asset : assetList) {
                    AstSchedulerMasterDTO dto = prepareDepreciation(orgId, asset, cdmDTO, null, new Date());
                    depDTOList.add(dto.getValuationDto());

                }
            } else {
                final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
                String assetClassDesc = CommonMasterUtility.getHierarchicalLookUp(cdmDTO.getAssetClass(), organisation)
                        .getDescLangFirst();
                LOGGER.error("No Asset found for given Asset Class " + assetClassDesc);
            }
        } catch (FrameworkException exp) {
            throw new FrameworkException("Problem occur while fetching asset by asset class", exp);
        }
        LOGGER.info("Depreciation DTO after getting all the assets" + depDTOList);
        return depDTOList;
    }

    private AssetValuationDetailsDTO populateCalcuationData(final AssetInformationDTO asset,
            AssetValuationDetailsDTO dto, Set<AssetErrorDTO> errorSet, final Date assetdDateField) {
        FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(assetdDateField);
        if (financiaYear == null) {
            LOGGER.error("Financial year " + Utility.getYearByDate(assetdDateField)
                    + " is missing. And it should not calculate Accumulated Depreciation amount for that year and for new years too.");
            AssetErrorDTO errorDto = new AssetErrorDTO();
            errorDto.setAssetCode(asset.getAstCode());
            errorDto.setOrgId(asset.getOrgId());
            errorDto.setAssetClass(asset.getAssetClass2());
            errorDto.setError("Financial year " + Utility.getYearByDate(assetdDateField)
                    + " is missing. And it should not calculate Accumulated Depreciation amount for that year and for new years too.");
            errorSet.add(errorDto);
            dto = null;
            return dto;
        } else {

            List<AssetValuationDetailsDTO> depAssetDTOList = valuationService.findAssetInFinYear(dto.getOrgId(),
                    dto.getAssetId(), financiaYear.getFaYear(),
                    MainetConstants.AssetManagement.ValuationType.DPR.getValue());

            if (depAssetDTOList != null && !depAssetDTOList.isEmpty()) {
                LOGGER.error("Depreciation is already calculated for asset id " + asset.getAssetId()
                        + " for current financial year, so depreciation calculation will be not be done");
                AssetErrorDTO errorDto = new AssetErrorDTO();
                errorDto.setAssetCode(asset.getAstCode());
                errorDto.setOrgId(asset.getOrgId());
                errorDto.setAssetClass(asset.getAssetClass2());
                errorDto.setError("Depreciation is already calculated for asset id " + asset.getAssetId()
                        + " for current financial year, so depreciation calculation will be not be done");
                errorSet.add(errorDto);
                dto = null;
                /*
                 * throw new FrameworkException( "Depreciation is already calculated for asset id " + asset.getAssetId() +
                 * " for current financial year, so depreciation calculation will be not be done");
                 */

            } else {
                // D#75397 here 1st check depreciation is in sequential basis or not using assetId
                AssetDetailsDTO astDetails = infoService.getDetailsByAssetId(asset.getAssetId());
                Date purchaseDate = astDetails.getAssetPurchaseInformationDTO().getDateOfAcquisition();
                FinancialYear purchaseYear = financialyearService.getFinanciaYearByDate(purchaseDate);
                List<AssetValuationDetailsDTO> purchaseYearDepAsset = valuationService.findAssetInFinYear(dto.getOrgId(),
                        dto.getAssetId(), purchaseYear.getFaYear(),
                        MainetConstants.AssetManagement.ValuationType.DPR.getValue());
                if (purchaseYearDepAsset == null && financiaYear.getFaYear() != purchaseYear.getFaYear()) {
                    // error MSG calculate depreciation sequentially
                    LOGGER.error("Financial year " + Utility.getYearByDate(purchaseDate)
                            + " is missing. please calculate depreciation sequentially");
                    AssetErrorDTO errorDto = new AssetErrorDTO();
                    errorDto.setAssetCode(asset.getAstCode());
                    errorDto.setOrgId(asset.getOrgId());
                    errorDto.setAssetClass(asset.getAssetClass2());
                    errorDto.setError("Financial year " + Utility.getYearByDate(purchaseDate)
                            + " is missing. please calculate depreciation sequentially");
                    errorSet.add(errorDto);
                    dto = null;
                    return dto;
                } else {
                    long period = (long) Math.ceil(astDetails.getAstDepreChartDTO().getOriUseYear().doubleValue());
                    for (int i = 0; i <= period; i++) {
                        // i+ 1 year in purchaseDate to check every record exist or not
                        Date sequenceDate = Utility.getAddedYearsBy(purchaseDate, i + 1);
                        FinancialYear sequenceYear = financialyearService.getFinanciaYearByDate(sequenceDate);
                        // D#39183 check year and selected date is same than break loop
                        if (sequenceYear != null && financiaYear.getFaYear() == sequenceYear.getFaYear()) {
                            break;
                        }
                        if (sequenceYear != null) {
                            List<AssetValuationDetailsDTO> sequenceYearDepAsset = valuationService.findAssetInFinYear(
                                    dto.getOrgId(),
                                    dto.getAssetId(), sequenceYear.getFaYear(),
                                    MainetConstants.AssetManagement.ValuationType.DPR.getValue());
                            if (sequenceYearDepAsset == null && financiaYear.getFaYear() != purchaseYear.getFaYear()) {
                                // error MSG calculate depreciation sequentially
                                LOGGER.error("Financial year " + Utility.getYearByDate(sequenceDate)
                                        + " is missing. please calculate depreciation sequentially");
                                AssetErrorDTO errorDto = new AssetErrorDTO();
                                errorDto.setAssetCode(asset.getAstCode());
                                errorDto.setOrgId(asset.getOrgId());
                                errorDto.setAssetClass(asset.getAssetClass2());
                                errorDto.setError("Financial year " + Utility.getYearByDate(sequenceDate)
                                        + " is missing. please calculate depreciation sequentially");
                                errorSet.add(errorDto);
                                dto = null;
                                return dto;
                            }
                        }

                    }

                }

                AssetValuationDetailsDTO depAssetDTO = valuationService.findLatestAssetId(dto.getOrgId(), dto.getAssetId());
                final AssetPurchaseInformationDTO purchaseDTO = infoService.getPurchaseInfo(dto.getAssetId());

                if (purchaseDTO != null) {
                    if (purchaseDTO.getCostOfAcquisition() != null) {
                        dto.setInitialBookValue(purchaseDTO.getCostOfAcquisition());
                    } else {
                        if (purchaseDTO.getInitialBookValue() != null) {
                            dto.setInitialBookValue(purchaseDTO.getInitialBookValue());
                        }
                    }
                    if (purchaseDTO.getDateOfAcquisition() != null) {
                        dto.setInitialBookDate(purchaseDTO.getDateOfAcquisition());
                    }
                    if (depAssetDTO != null) {
                        dto.setPreviousBookDate(depAssetDTO.getCurrentBookDate());
                        dto.setPreviousBookValue(depAssetDTO.getCurrentBookValue());
                        dto.setAccumDeprValue(depAssetDTO.getAccumDeprValue());
                    } else {
                        dto.setPreviousBookValue(purchaseDTO.getInitialBookValue());
                        dto.setPreviousBookDate(purchaseDTO.getDateOfAcquisition());
                    }

                    final AssetDepreciationChartDTO depDTO = infoService.getDepreciationInfo(dto.getAssetId());
                    if (depDTO != null && depDTO.getOriUseYear() != null
                            && (StringUtils.isNotBlank(depDTO.getAccumuDepAc()))) {
                        dto.setLife(depDTO.getOriUseYear());
                        // D#39107
                        dto.setSalvageValue(depDTO.getSalvageValue() != null ? depDTO.getSalvageValue() : null);
                        calculateDepreciation(dto, null, financiaYear);
                    } else {
                        // #D33177
                        /*
                         * LOGGER.error(
                         * "useful life,accumulated deprecation account head is not available as value of depreciation tab is null for asset id "
                         * + asset.getAssetId() + ", so depreciation calculation will be not be done"); AssetErrorDTO errorDto =
                         * new AssetErrorDTO(); errorDto.setAssetCode(asset.getAstCode()); errorDto.setOrgId(asset.getOrgId());
                         * errorDto.setAssetClass(asset.getAssetClass2()); errorDto.setError(
                         * "useful life,accumulated deprecation account head is not available as value of depreciation tab is null for asset id "
                         * + asset.getAssetId() + ", so depreciation calculation will be not be done"); errorSet.add(errorDto);
                         */
                        LOGGER.error(
                                "useful life,accumulated deprecation account head is not available as value of depreciation tab is null for asset id "
                                        + asset.getAssetId() + ", so depreciation calculation will be not be done");
                        dto = null;

                    }
                } else {
                    LOGGER.error("Acquisition Details are not available for asset id " + asset.getAssetId()
                            + ", so depreciation calculation will be not be done");
                    AssetErrorDTO errorDto = new AssetErrorDTO();
                    errorDto.setAssetCode(asset.getAstCode());
                    errorDto.setOrgId(asset.getOrgId());
                    errorDto.setAssetClass(asset.getAssetClass2());
                    errorDto.setError(
                            "Acquisition Details are not available for asset id " + asset.getAssetId()
                                    + ", so depreciation calculation will be not be done");
                    errorSet.add(errorDto);
                    dto = null;

                }
            }
        }
        return dto;

    }

    /**
     * Used to calculate depreciation for each asset
     * 
     * @param AssetValuationDetailsDTO
     */
    @Override
    public void calculateDepreciation(final AssetValuationDetailsDTO dto, Integer months, FinancialYear financialYear) {

        try {
            final DeprAprFactorsDTO factorDTO = new DeprAprFactorsDTO();

            factorDTO.setRate(dto.getRate());
            factorDTO.setLife(dto.getLife());
            factorDTO.setInitialCost(dto.getInitialBookValue());
            factorDTO.setSalvageValue(dto.getSalvageValue());

            if (months == null) {
                months = getMonthsForCalculation(dto);
                LOGGER.info("Month is null for asset id " + dto.getAssetId()
                        + " so calculating months with asset acquisition date" + dto);
            }

            if (dto.getPreviousBookValue() != null && dto.getPreviousBookValue().compareTo(BigDecimal.ONE) > 0) {
                final DeprAprScheduleDTO scheduleDTO = calculateService.calculateForPeriod(factorDTO, months);
                if (scheduleDTO.getDeprExpense() == null) {
                    scheduleDTO.setDeprExpense(BigDecimal.ZERO);
                }
                if (dto.getAccumDeprValue() == null) {
                    dto.setAccumDeprValue(BigDecimal.ZERO);
                }
                BigDecimal newBookValue = BigDecimal.ONE;
                if (dto.getPreviousBookValue().compareTo(scheduleDTO.getDeprExpense()) > 0) {
                    newBookValue = dto.getPreviousBookValue().subtract(scheduleDTO.getDeprExpense());
                } else {
                    scheduleDTO.setDeprExpense(scheduleDTO.getDeprExpense().subtract(dto.getPreviousBookValue()));
                }

                final BigDecimal accumDeprValue = scheduleDTO.getDeprExpense().add(dto.getAccumDeprValue());
                // D#39922
                // FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
                dto.setBookFinYear(financialYear.getFaYear());
                dto.setCurrentBookDate(financialYear.getFaFromDate());
                dto.setCurrentBookValue(newBookValue);
                dto.setAccumDeprValue(accumDeprValue);
                dto.setDeprValue(scheduleDTO.getDeprExpense());
                dto.setChangetype(MainetConstants.AssetManagement.ValuationType.DPR.getValue());
                LOGGER.info("Depreciation DTO after calculating depreciation for an asset" + dto);
                dto.setCreationDate(new Date());

                Long id = valuationService.addEntry(dto);
                dto.setTotDeprValueInYear(dto.getDeprValue());
                LOGGER.info(
                        "asset is added successfully in asset valuation table with id " + id + " and details " + dto);

            } else {
                LOGGER.info("Current book Value of asset id " + dto.getAssetId() + " is " + dto.getCurrentBookValue()
                        + ", Hence depreciation would not be calculated " + dto);
            }
        } catch (Exception exp) {
            LOGGER.info("exception occures while calculating depreciation " + dto.getAssetId()
                    + " during calculation of depreciation", exp);
            throw new FrameworkException("exception occures while calculating depreciation", exp);
        }
    }

    private Integer getMonthsForCalculation(final AssetValuationDetailsDTO dto) {
        // D#39107 month not calculate proper change 7 to 6
        int months = 6;
        Date dtoDate = dto.getPreviousBookDate();
        FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(dtoDate);
        Date halfYearDate = Utility.getAddedMonthBy(Utility.dateToString(financiaYear.getFaFromDate()), months);
        // months will be 12 if previous book date is in 1st half of financial year
        // else it will be 6
        if (Utility.compareDate(dtoDate, halfYearDate)) {
            months = 12;
        } else {
            months = 6;
        }
        return new Integer(months);
    }
    @Transactional
    @Override
    /* this method is for asset type wise calculation */
    public AstSchedulerMasterDTO getAssetWithDepreciation(final Long orgId, final String assetCode,
            final AuditDetailsDTO auditDTO, Date assetDateField) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        AssetErrorDTO errordto;

        AstSchedulerMasterDTO astmstDto = new AstSchedulerMasterDTO();
        final Set<AssetErrorDTO> errorSet = new HashSet<>();
        final AssetInformationDTO infoDto = informationService.getInfoByCode(orgId, assetCode);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED,
                MainetConstants.AssetManagement.ASSET_MANAGEMENT, org);
        final Long assetstatus = lookup.getLookUpId();
        if (infoDto == null || !infoDto.getAssetStatus().equals(assetstatus)) {
            errordto = new AssetErrorDTO();
            errordto.setOrgId(orgId);
            errordto.setAssetCode(assetCode);

            errordto.setError("This asset is not present or it is not active");
            errorSet.add(errordto);
            astmstDto.setErrorSet(errorSet);
            return astmstDto;
        }
        ChartOfDepreciationMasterDTO cdmDTO = null;
        if (infoDto != null) {
            cdmDTO = chartService.getAllByAssetClass(orgId, infoDto.getAssetClass2());
            if (cdmDTO == null) {
                errordto = new AssetErrorDTO();
                errordto.setOrgId(orgId);
                errordto.setAssetCode(assetCode);
                errordto.setError("depreciation master configuration missing for Asset class");
                errorSet.add(errordto);
                astmstDto.setErrorSet(errorSet);
                return astmstDto;
            }
        }
        astmstDto = prepareDepreciation(orgId, infoDto, cdmDTO, auditDTO, assetDateField);
        return astmstDto;

    }

    /* this method is for asset type wise calculation */
    private AstSchedulerMasterDTO prepareDepreciation(final Long orgId, final AssetInformationDTO asset,
            final ChartOfDepreciationMasterDTO cdmDTO, final AuditDetailsDTO auditDTO, final Date assetDateField) {
        final AstSchedulerMasterDTO astQuartzDto = new AstSchedulerMasterDTO();
        final AssetValuationDetailsDTO dto = new AssetValuationDetailsDTO();
        final Set<AssetErrorDTO> errorSet = new HashSet<>();
        final List<AssetValuationDetailsDTO> valudtoList = new ArrayList<AssetValuationDetailsDTO>(100);
        dto.setAssetId(asset.getAssetId());
        dto.setOrgId(orgId);
        dto.setAssetClass(cdmDTO.getAssetClass());
        dto.setMethod(cdmDTO.getDepreciationKey());
        dto.setRate(cdmDTO.getRate());
        dto.setAccountCode(cdmDTO.getAccountCode());
        dto.setGroupId(cdmDTO.getGroupId());
        dto.setCreatedBy(auditDTO.getEmpId());
        dto.setLgIpMac(auditDTO.getEmpIpMac());
        try {
            populateCalcuationData(asset, dto, errorSet, assetDateField);
            dto.setAssetCode(asset.getAstCode());
            astQuartzDto.setValuationDto(dto);
            valudtoList.add(dto);
            if (errorSet.size() == 0) {
                /* check account module is live or not */
                if (maintenanceService.checkAccountActiveOrNot()) {
                    VoucherPostExternalDTO vouPostDto = accountVoucherPostingByAsset(orgId, cdmDTO, valudtoList, assetDateField);
                    maintenanceService.externalVoucherPostingInAccount(orgId, vouPostDto);
                }
                Set<AssetValuationDetailsDTO> valudto = new HashSet<>(valudtoList);
                astQuartzDto.setValuationSet(valudto);
            }
            astQuartzDto.setErrorSet(errorSet);
        } catch (Exception exception) {
            LOGGER.error("Problem occur while populating data for asset id " + asset.getAssetId()
                    + " during calculation ", exception);
            if (exception.getMessage() != null) {
                AssetErrorDTO errdto = new AssetErrorDTO();
                errdto.setError(exception.getMessage());
                errorSet.add(errdto);
            }

            astQuartzDto.setErrorSet(errorSet);
        }
        return astQuartzDto;

    }

    @Override
    /* this method is for asset type wise calculation */
    public VoucherPostExternalDTO accountVoucherPostingByAsset(Long orgId, ChartOfDepreciationMasterDTO dto,
            List<AssetValuationDetailsDTO> valuationDtoList, final Date assetDateField) throws FrameworkException {
        Organisation org = organisationService.getOrganisationById(orgId);
        List<VoucherPostDetailExternalDTO> detailExtenalDtoList = new ArrayList<>();
        final VoucherPostExternalDTO vouPostDto = new VoucherPostExternalDTO();
        vouPostDto.setVoucherDate(Utility.dateToString(assetDateField));
        vouPostDto.setVousubTypeCode(MainetConstants.AssetManagement.DEPR_ACCT_TMPLT_PRF);
        vouPostDto.setDepartmentCode(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
        vouPostDto.setUlbCode(org.getOrgShortNm());
        vouPostDto.setVoucherReferenceNo(valuationDtoList.get(0).getAssetCode());
        vouPostDto.setVoucherReferenceDate(Utility.dateToString(assetDateField));
        vouPostDto.setNarration("asset depreciation for " + valuationDtoList.get(0).getAssetCode());
        vouPostDto.setLocationDescription("1-1");
        vouPostDto.setPayerOrPayee(MainetConstants.AssetManagement.SYSTEM);
        vouPostDto.setCreatedBy(valuationDtoList.get(0).getCreatedBy().toString());
        vouPostDto.setEntryCode(MainetConstants.AssetManagement.ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL);
        vouPostDto.setPayModeCode(MainetConstants.AssetManagement.PAY_MODE_TRANSFER);
        vouPostDto.setEntryFlag(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);
        valuationDtoList.parallelStream().forEach(dtos -> {
            VoucherPostDetailExternalDTO vouPostDetExteDto = new VoucherPostDetailExternalDTO();
            vouPostDetExteDto.setVoucherAmount(dtos.getDeprValue());
            vouPostDetExteDto.setAcHeadCode(dto.getAccountCode());
            vouPostDetExteDto.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
            VoucherPostDetailExternalDTO vouPostDetExteDto1 = new VoucherPostDetailExternalDTO();
            vouPostDetExteDto1.setVoucherAmount(dtos.getDeprValue());
            vouPostDetExteDto1.setAcHeadCode(depreciationService.findDeprtChartByAssetId(dtos.getAssetId()).getAccumuDepAc());
            vouPostDetExteDto1.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);
            detailExtenalDtoList.add(vouPostDetExteDto);
            detailExtenalDtoList.add(vouPostDetExteDto1);
        });
        vouPostDto.setVoucherExtDetails(detailExtenalDtoList);
        return vouPostDto;
    }

    @Override
    /* this is for asset class wise calculation */
    public AstSchedulerMasterDTO getAssetDepreciationByClass(Long orgId, Long assetClass, final AuditDetailsDTO auditDTO,
            Date assetDateField) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        AssetErrorDTO errordto;
        final Set<AssetErrorDTO> errorSet = new HashSet<>();
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED,
                MainetConstants.AssetManagement.ASSET_MANAGEMENT, org);
        final Long assetstatus = lookup.getLookUpId();
        AstSchedulerMasterDTO astmstDto = new AstSchedulerMasterDTO();
        List<AssetInformationDTO> assetList = infoService.getAssetByAssetClass(orgId, assetClass,
                assetstatus, MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED);
        if (assetList == null || assetList.isEmpty()) {
            errordto = new AssetErrorDTO();
            errordto.setOrgId(orgId);
            errordto.setAssetClass(assetClass);
            errordto.setError("This asset is not present or it is not active");
            astmstDto.setAsetErrorDto(errordto);
            errorSet.add(errordto);
            astmstDto.setErrorSet(errorSet);
            return astmstDto;
        }
        ChartOfDepreciationMasterDTO cdmDTO = null;
        if (assetList != null && !assetList.isEmpty()) {
            cdmDTO = chartService.getAllByAssetClass(orgId, assetClass);
            if (cdmDTO == null) {
                errordto = new AssetErrorDTO();
                errordto.setOrgId(orgId);
                errordto.setAssetClass(assetClass);
                errordto.setError("depreciation master configuration missing for Asset class");
                astmstDto.setAsetErrorDto(errordto);
                errorSet.add(errordto);
                astmstDto.setErrorSet(errorSet);
                return astmstDto;
            }
        }
        astmstDto = prepareDepreciationForAssetClass(orgId, assetList, cdmDTO, auditDTO, assetDateField);
        return astmstDto;
    }

    /* this is for asset class wise calculation */
    private AstSchedulerMasterDTO prepareDepreciationForAssetClass(final Long orgId, final List<AssetInformationDTO> assetList,
            final ChartOfDepreciationMasterDTO cdmDTO, final AuditDetailsDTO auditDTO, final Date assetDateField) {
        final AstSchedulerMasterDTO astQuartzDto = new AstSchedulerMasterDTO();
        final List<AssetValuationDetailsDTO> valudtoList = new ArrayList<AssetValuationDetailsDTO>(100);
        final Set<AssetErrorDTO> errorSet = new HashSet<>();
        Organisation org = organisationService.getOrganisationById(orgId);
        String batchNo = sequenceGenerate(cdmDTO.getAssetClass(), org);
        try {
            assetList.parallelStream().forEachOrdered(astDto -> {
                AssetValuationDetailsDTO dto = new AssetValuationDetailsDTO();
                dto.setAssetId(astDto.getAssetId());
                dto.setOrgId(orgId);
                dto.setAssetClass(cdmDTO.getAssetClass());
                dto.setMethod(cdmDTO.getDepreciationKey());
                dto.setRate(cdmDTO.getRate());
                dto.setAccountCode(cdmDTO.getAccountCode());
                dto.setGroupId(cdmDTO.getGroupId());
                dto.setCreatedBy(auditDTO.getEmpId());
                dto.setLgIpMac(auditDTO.getEmpIpMac());
                dto.setAssetCode(astDto.getAstCode());
                dto.setBatchNo(batchNo);
                dto = populateCalcuationData(astDto, dto, errorSet, assetDateField);
                if (dto != null) {
                    astQuartzDto.setValuationDto(dto);
                    valudtoList.add(dto);
                }
                astQuartzDto.setErrorSet(errorSet);
            });
            if (valudtoList.size() > 0) {
                /* check account module is live or not */
                if (maintenanceService.checkAccountActiveOrNot()) {
                    VoucherPostExternalDTO vouPostDto = accountVoucherPostingByassetClass(orgId, cdmDTO, valudtoList,
                            assetDateField);
                    maintenanceService.externalVoucherPostingInAccount(orgId, vouPostDto);
                }
                Set<AssetValuationDetailsDTO> valudto = new HashSet<>(valudtoList);
                astQuartzDto.setValuationSet(valudto);
            }
        } catch (Exception exception) {
            LOGGER.error("Problem occur while populating data for asset id "
                    + " during calculation ", exception);
            astQuartzDto.setErrorSet(errorSet);
        }
        return astQuartzDto;
    }

    /*
     * this is for asset class wise calculation
     */
    private VoucherPostExternalDTO accountVoucherPostingByassetClass(Long orgId, ChartOfDepreciationMasterDTO cdmDTO,
            List<AssetValuationDetailsDTO> valudtoList, final Date assetDateField) throws FrameworkException {
        Organisation org = organisationService.getOrganisationById(orgId);
        BigDecimal amount = BigDecimal.ZERO;
        List<VoucherPostDetailExternalDTO> detailExtenalDtoList = new ArrayList<>();
        VoucherPostExternalDTO vouPostDto = new VoucherPostExternalDTO();
        vouPostDto.setVoucherDate(Utility.dateToString(assetDateField));
        vouPostDto.setVousubTypeCode(MainetConstants.AssetManagement.DEPR_ACCT_TMPLT_PRF);
        vouPostDto.setDepartmentCode(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
        vouPostDto.setUlbCode(org.getOrgShortNm());
        vouPostDto.setVoucherReferenceNo((valudtoList.get(0).getBatchNo()));
        vouPostDto.setVoucherReferenceDate(Utility.dateToString(assetDateField));
        vouPostDto.setNarration("asset depreciation for " + valudtoList.get(0).getBatchNo());
        vouPostDto.setLocationDescription("1-1");
        vouPostDto.setPayerOrPayee(MainetConstants.AssetManagement.SYSTEM);
        vouPostDto.setCreatedBy(valudtoList.get(0).getCreatedBy().toString());
        vouPostDto.setEntryCode(MainetConstants.AssetManagement.ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL);
        vouPostDto.setPayModeCode(MainetConstants.AssetManagement.PAY_MODE_TRANSFER);
        vouPostDto.setEntryFlag(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);
        for (AssetValuationDetailsDTO dtos : valudtoList) {
            VoucherPostDetailExternalDTO vouPostDetExteDto1 = new VoucherPostDetailExternalDTO();
            vouPostDetExteDto1.setVoucherAmount(dtos.getDeprValue());
            vouPostDetExteDto1.setAcHeadCode(depreciationService.findDeprtChartByAssetId(dtos.getAssetId()).getAccumuDepAc());
            vouPostDetExteDto1.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);
            detailExtenalDtoList.add(vouPostDetExteDto1);
            amount = amount.add(dtos.getDeprValue());
        }
        VoucherPostDetailExternalDTO vouPostDetExteDto = new VoucherPostDetailExternalDTO();
        vouPostDetExteDto.setVoucherAmount(amount);
        vouPostDetExteDto.setAcHeadCode(cdmDTO.getAccountCode());
        vouPostDetExteDto.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
        detailExtenalDtoList.add(vouPostDetExteDto);
        vouPostDto.setVoucherExtDetails(detailExtenalDtoList);
        return vouPostDto;
    }

    /*
     * @param assetClass
     * @param org this is for asset class sequence generate using batch number and the maximum size of the sequence is size of
     * your variable for which you are generating sequence
     * @return
     */
    private String sequenceGenerate(Long assetClass, Organisation org) {
        Long squenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.AssetManagement.ASSETCODE,
                "TB_AST_VALUATION_DET", "BATCH_NO", org.getOrgid(), MainetConstants.FlagC, null);
        LookUp lookUpObj = CommonMasterUtility.getNonHierarchicalLookUpObject(assetClass, org);
        String batchNo = "DEPR-" + lookUpObj.getLookUpCode() + "-" + squenceNo;
        return batchNo;
    }

}
