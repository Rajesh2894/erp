/**
 * 
 */
package com.abm.mainet.asset.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.asset.ui.dto.AssetAccountPostDTO;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class DepreciationCalculationFrequencyServiceImpl implements IDepreciationCalculationFrequencyService {

    @Autowired
    private IChartOfDepreciationMasterService chartService;

    @Autowired
    private IDepreciationCalculationDetailsService detailService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IMaintenanceService maintainService;

    private static final Logger LOGGER = Logger.getLogger(DepreciationCalculationFrequencyServiceImpl.class);

    private static final String FREQUENCY = "PRF";
    private static final String DAILY = "D";
    private static final String WEEKLY = "W";
    private static final String MONTHLY = "M";
    private static final String QUARTERLY = "Q";
    private static final String HALFYEARLY = "HY";
    private static final String YEARLY = "Y";
    private static final String DEPRECIATION = "DPK";
    private static final String METHOD = "STLM";
    private static final String STATUS = "AST";
    private static final String ACTIVE = "A";

    /**
     * Used to calculate depreciation on daily basis
     * 
     * @param orgId
     * @param frequency
     */
    @Override
    public void calculateDaily(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(DAILY, FREQUENCY, organisation);
        Long frequency = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(METHOD, DEPRECIATION, organisation);
        Long method = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(ACTIVE, STATUS, organisation);
        final Long assetstatus = lookup.getLookUpId();

        List<ChartOfDepreciationMasterDTO> cdmDTO = chartService
                .getAssetClassByfrequency(runtimeBean.getOrgId().getOrgid(), frequency, method);
        if (cdmDTO != null && !cdmDTO.isEmpty()) {
            try {
                for (ChartOfDepreciationMasterDTO dto : cdmDTO) {
                    final List<AssetValuationDetailsDTO> depAssetDTO = detailService
                            .getAssetWithDepreciation(runtimeBean.getOrgId().getOrgid(), dto, assetstatus);
                    LOGGER.info("Depreciation DTO after calculating depreciation" + depAssetDTO);
                }
            } catch (Exception exp) {
                throw new FrameworkException("error while scheduling quartz for daily basis", exp);
            }
        } else {
            LOGGER.error("No Asset Classes are available under daily frequency for Straight Line Method");
        }
    }

    /**
     * Used to calculate depreciation on Weekly basis
     * 
     * @param orgId
     * @param frequency
     */
    @Override
    public void calculateWeekly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(WEEKLY, FREQUENCY, organisation);
        Long frequency = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(METHOD, DEPRECIATION, organisation);
        Long method = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(ACTIVE, STATUS, organisation);
        final Long assetstatus = lookup.getLookUpId();

        List<ChartOfDepreciationMasterDTO> cdmDTO = chartService
                .getAssetClassByfrequency(runtimeBean.getOrgId().getOrgid(), frequency, method);
        if (cdmDTO != null && !cdmDTO.isEmpty()) {
            try {
                for (ChartOfDepreciationMasterDTO dto : cdmDTO) {
                    final List<AssetValuationDetailsDTO> depAssetDTO = detailService
                            .getAssetWithDepreciation(runtimeBean.getOrgId().getOrgid(), dto, assetstatus);
                    LOGGER.info("Depreciation DTO after calculating depreciation" + depAssetDTO);
                }
            } catch (Exception exp) {
                throw new FrameworkException("error while scheduling quartz for Weekly basis", exp);
            }
        } else {
            LOGGER.error("No Asset Classes are available under Weekly frequency for Straight Line Method");
        }
    }

    /**
     * Used to calculate depreciation on Monthly basis
     * 
     * @param orgId
     * @param frequency
     */
    @Override
    public void calculateMonthly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MONTHLY, FREQUENCY, organisation);
        Long frequency = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(METHOD, DEPRECIATION, organisation);
        Long method = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(ACTIVE, STATUS, organisation);
        final Long assetstatus = lookup.getLookUpId();

        List<ChartOfDepreciationMasterDTO> cdmDTO = chartService
                .getAssetClassByfrequency(runtimeBean.getOrgId().getOrgid(), frequency, method);

        if (cdmDTO != null && !cdmDTO.isEmpty()) {
            try {
                for (ChartOfDepreciationMasterDTO dto : cdmDTO) {
                    final List<AssetValuationDetailsDTO> depAssetDTO = detailService
                            .getAssetWithDepreciation(runtimeBean.getOrgId().getOrgid(), dto, assetstatus);
                    LOGGER.info("Depreciation DTO after calculating depreciation" + depAssetDTO);
                }
            } catch (Exception exp) {
                throw new FrameworkException("error while scheduling quartz for Monthly basis", exp);
            }
        } else {
            LOGGER.error("No Asset Classes are available under Monthly frequency for Straight Line Method");
        }
    }

    /**
     * Used to calculate depreciation on Quarterly basis
     * 
     * @param orgId
     * @param frequency
     */
    @Override
    public void calculateQuarterly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(QUARTERLY, FREQUENCY, organisation);
        Long frequency = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(METHOD, DEPRECIATION, organisation);
        Long method = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(ACTIVE, STATUS, organisation);
        final Long assetstatus = lookup.getLookUpId();

        List<ChartOfDepreciationMasterDTO> cdmDTO = chartService
                .getAssetClassByfrequency(runtimeBean.getOrgId().getOrgid(), frequency, method);

        if (cdmDTO != null && !cdmDTO.isEmpty()) {
            try {
                for (ChartOfDepreciationMasterDTO dto : cdmDTO) {
                    final List<AssetValuationDetailsDTO> depAssetDTO = detailService
                            .getAssetWithDepreciation(runtimeBean.getOrgId().getOrgid(), dto, assetstatus);
                    LOGGER.info("Depreciation DTO after calculating depreciation" + depAssetDTO);
                }
            } catch (Exception exp) {
                throw new FrameworkException("error while scheduling quartz for Quarterly basis", exp);
            }
        } else {
            LOGGER.error("No Asset Classes are available under Quarterly frequency for Straight Line Method");
        }
    }

    /**
     * Used to calculate depreciation on Half Yearly basis
     * 
     * @param orgId
     * @param frequency
     */
    @Override
    public void calculateHalfYearly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(HALFYEARLY, FREQUENCY, organisation);
        Long frequency = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(METHOD, DEPRECIATION, organisation);
        Long method = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(ACTIVE, STATUS, organisation);
        final Long assetstatus = lookup.getLookUpId();

        List<ChartOfDepreciationMasterDTO> cdmDTO = chartService
                .getAssetClassByfrequency(runtimeBean.getOrgId().getOrgid(), frequency, method);

        if (cdmDTO != null && !cdmDTO.isEmpty()) {
            try {
                for (ChartOfDepreciationMasterDTO dto : cdmDTO) {
                    final List<AssetValuationDetailsDTO> depAssetDTO = detailService
                            .getAssetWithDepreciation(runtimeBean.getOrgId().getOrgid(), dto, assetstatus);
                    LOGGER.info("Depreciation DTO after calculating depreciation" + depAssetDTO);
                }
            } catch (Exception exp) {
                throw new FrameworkException("error while scheduling quartz for Half Yearly basis", exp);
            }
        } else {
            LOGGER.error("No Asset Classes are available under Half Yearly frequency for Straight Line Method");
        }
    }

    /**
     * Used to calculate depreciation on Yearly basis
     * 
     * @param orgId
     * @param frequency
     */
    @Override
    public void calculateYearly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(YEARLY, FREQUENCY, organisation);
        final Long frequency = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(METHOD, DEPRECIATION, organisation);
        final Long method = lookup.getLookUpId();
        lookup = CommonMasterUtility.getValueFromPrefixLookUp(ACTIVE, STATUS, organisation);
        final Long assetstatus = lookup.getLookUpId();

        final List<ChartOfDepreciationMasterDTO> cdmDTO = chartService
                .getAssetClassByfrequency(runtimeBean.getOrgId().getOrgid(), frequency, method);

        if (cdmDTO != null && !cdmDTO.isEmpty()) {
            try {
                for (ChartOfDepreciationMasterDTO dto : cdmDTO) {
                    final List<AssetValuationDetailsDTO> depAssetDTO = detailService
                            .getAssetWithDepreciation(runtimeBean.getOrgId().getOrgid(), dto, assetstatus);
                    LOGGER.info("Depreciation DTO after calculating depreciation" + depAssetDTO);

                    accountVoucherPostingAfterDepreciation(organisation, dto, depAssetDTO);
                }
            } catch (Exception exp) {
                throw new FrameworkException("error while scheduling quartz for Yearly basis", exp);
            }
        } else {
            LOGGER.error("No Asset Classes are available under Yearly frequency for Straight Line Method");
        }
    }

    private void accountVoucherPostingAfterDepreciation(final Organisation organisation,
            ChartOfDepreciationMasterDTO dto, final List<AssetValuationDetailsDTO> depAssetDTO) {

        final Date curDate = new Date();
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI, organisation);
        String assetClassDesc = CommonMasterUtility.getHierarchicalLookUp(dto.getAssetClass(), organisation)
                .getDescLangFirst();
        List<VoucherPostDetailDTO> voucherList = new ArrayList<>(0);

        if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.FlagL)) {
            VoucherPostDetailDTO voucher;
            BigDecimal totalDepreciation = BigDecimal.ZERO;
            for (AssetValuationDetailsDTO valDTO : depAssetDTO) {
                if (valDTO.getTotDeprValueInYear() != null) {
                    totalDepreciation = totalDepreciation.add(valDTO.getTotDeprValueInYear());
                }
            }

            if (totalDepreciation.compareTo(BigDecimal.ZERO) > 0) {

                LookUp voucherId = CommonMasterUtility.getValueFromPrefixLookUp(
                        MainetConstants.AssetManagement.DEPR_ACCT_TMPLT_PRF,
                        MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, organisation);
                Long voucherSubTypeId = null;
                if (voucherId != null) {
                    voucherSubTypeId = voucherId.getLookUpId();
                }

                Long payModeId = CommonMasterUtility
                        .getValueFromPrefixLookUp(MainetConstants.AssetManagement.PAY_MODE_TRANSFER,
                                MainetConstants.AssetManagement.PAY_MODE_PRF, organisation)
                        .getLookUpId();

                final AssetAccountPostDTO postDTO = new AssetAccountPostDTO();
                // postDTO.setAccountHead(dto.getAccountCode());
                postDTO.setAccountHeadAmount(totalDepreciation);
                postDTO.setVoucherSubTypeId(voucherSubTypeId);
                postDTO.setPayModeId(payModeId);
                postDTO.setPayModeIdAmount(totalDepreciation);
                postDTO.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
                postDTO.setPayerPayee("System for depreciation");
                postDTO.setBillVouPostingDate(curDate);
                postDTO.setCreatedDate(curDate);
                postDTO.setVoucherDate(curDate);
                postDTO.setVoucherSubTypeName("ADE");
                postDTO.setLgIpMac(Utility.getMacAddress());

                voucher = new VoucherPostDetailDTO();
                voucher.setVoucherAmount(postDTO.getAccountHeadAmount());
                voucher.setSacHeadId(postDTO.getAccountHead());
                voucherList.add(voucher);

                voucher = new VoucherPostDetailDTO();
                voucher.setVoucherAmount(postDTO.getPayModeIdAmount());
                voucher.setPayModeId(postDTO.getPayModeId());
                voucherList.add(voucher);

                final String narration = "Asset Class " + assetClassDesc
                        + " after Depreciation Calculation from System";
                try {
                    maintainService.postDepreciationVoucher(postDTO, "Y", organisation, narration, voucherList,
                            dto.getDeptCode());
                    LOGGER.info("Account posting service called successfully for Asset Class " + assetClassDesc
                            + " and details " + dto);
                } catch (Exception exc) {
                    throw new FrameworkException("Exception occured while posting depreciation voucher to accounts module: ",
                            exc);
                }

            } else {
                LOGGER.info("Account posting service will not be called for " + assetClassDesc
                        + " as total depreciation amount is ZERO");
            }

        } else {
            LOGGER.info("Default Lookup value could not be found for prefix " + PrefixConstants.SLI
                    + ", Hence Account Posting will not be called");
        }
    }
}
