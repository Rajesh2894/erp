package com.abm.mainet.common.utility;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.CommonDao;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigDetDTO;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ISequenceConfigMasterService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.service.TbComparentMasService;

@Component
public class SeqGenFunctionUtility {

    private static final Logger LOGGER = Logger.getLogger(SeqGenFunctionUtility.class);

    @Autowired
    private CommonDao commonDao;

    @Resource
    TbComparentMasService tbComparentMasService;

    @Resource
    private TbComparamMasService tbComparamMasService;

    List<TbComparentMas> tbComparentMasList = new ArrayList<>();

    @Autowired
    private ISequenceConfigMasterService sequenceConfigMasterService;

    /**
     * use this method in order to generate Unique Sequence no:
     * 
     * @param deptShortCode : pass department short code
     * @param tableName : pass your table name
     * @param columnName: pass table primary key column name
     * @param orgId : pass orgId
     * @param flag: pass flag to reset Type
     * @param resetId : pass null to resetId like F-for financial year wise D-Day wise Y-Calendar Year wise
     * @return a unique Sequence No.(without using the UserSession for getting Orgid in - WebService Mobile Area)
     */
    public Long generateSequenceNo(final String deptShortCode, final String tableName, final String columnName,
            final Long orgId, final String resetflag, final Long resetId) {

        Long seqNum = null;
        final Object[] ipValues = new Object[] { deptShortCode, tableName, columnName, orgId, resetflag, resetId };

        final int[] opTypes = new int[] { Types.NUMERIC };
        final List<Object> list = commonDao.getSequenceProc(ipValues, opTypes);
        if (null != list) {
            seqNum = Long.parseLong(list.get(0).toString());
        }
        return seqNum;
    }

    public Long generateJavaSequenceNo(final String deptShortCode, final String tableName, final String columnName,
            final String resetflag, final Long resetId) {

        Long seqNum = null;
        final Object[] ipValues = new Object[] { deptShortCode, tableName, columnName, resetflag, resetId };

        final int[] opTypes = new int[] { Types.NUMERIC };
        final List<Object> list = commonDao.getJavaSequenceProc(ipValues, opTypes);
        if (null != list) {
            seqNum = Long.parseLong(list.get(0).toString());
        }
        return seqNum;
    }

    // @returns the numeric sequence number by calling the procedure
    public Long getNumericSeqNo(final String ctrlId, final String tableName, final String columnName, final Long orgId,
            final String resetflag, final String deptID, final Long startNum, final Long lastNum) {
        // D#127658
        LOGGER.info("parameter pass for generate seq no" + ctrlId + tableName + columnName + orgId + resetflag + deptID + startNum
                + lastNum);
        Long seqNum = null;
        final Object[] ipValues = new Object[] { ctrlId, tableName, columnName, orgId, resetflag, deptID, startNum,
                lastNum };

        final int[] opTypes = new int[] { Types.NUMERIC };
        final List<Object> list = commonDao.getCustSequenceProc(ipValues, opTypes);
        if (null != list) {
            seqNum = Long.parseLong(list.get(0).toString());
        }
        return seqNum;
    }

    // @returns the composite sequence number by adding the selected factors
    public String generateNewSequenceNo(SequenceConfigMasterDTO sequenceConfigurationMaster,
            CommonSequenceConfigDto commonSeqDto) {
        String seqNo = null;
        String ctrlId = new String();
        Long orgId = sequenceConfigurationMaster.getOrgId();
        String resetFlag, deptId, colName, catName, code, type, prefix, tabName;
        Long len, sequence, startNo;
        String usageCtrlId = new String();
        Long lastNum;
        int level = 0;

        // To set the Financial year base on current date
        if (commonSeqDto.getFinancialYear() == null) {
            FinancialYear faYear = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());

            String finacialYear = Utility.getFinancialYear(faYear.getFaFromDate(), faYear.getFaToDate());
            commonSeqDto.setFinancialYear(finacialYear);
        }
        // derive the category description
        LookUp seqCategoryCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                sequenceConfigurationMaster.getCatId(), sequenceConfigurationMaster.getOrgId(),
                MainetConstants.SEQ_PREFIXES.SEC);
        catName = seqCategoryCode.getLookUpCode();

        // Derive Column name and table name
        LookUp seqNameCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                sequenceConfigurationMaster.getSeqName(), sequenceConfigurationMaster.getOrgId(),
                MainetConstants.SEQ_PREFIXES.SQN);

        List<TbComparamMas> prefixList = new ArrayList<>();
        // derive the department short code
        code = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                .getDeptCode(sequenceConfigurationMaster.getDeptId());
        prefixList = tbComparamMasService.findAllByDepartment(code, "");
        type = new String();
        prefix = null;
        // get the selected prefix under department from the detaildto
        for (SequenceConfigDetDTO configDetDTO : sequenceConfigurationMaster.getConfigDetDTOs()) {
            if (configDetDTO.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.DEPARTMENT_PREFIX)) {
                prefix = configDetDTO.getPrefixId();
                break;
            }

        }
        // to get the type of the selected prefix H/N
        if (prefix != null && !prefix.equals("0")) {
            for (TbComparamMas comparamMas : prefixList) {
                if (comparamMas.getCpmPrefix().equals(prefix)) {
                    type = comparamMas.getCpmType();
                    break;
                }
            }
        }
        if (catName.equals(MainetConstants.CAT_ID.PREFIX_BASE)) {
            // To get the level of the selected hierarchical prefix

            if (type.equals(MainetConstants.FlagH)) {
                // This is used to set the level of the selected prefix data
                for (SequenceConfigDetDTO configDetDTO : sequenceConfigurationMaster.getConfigDetDTOs()) {
                    if (configDetDTO.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.DEPARTMENT_PREFIX)) {
                        List<ViewPrefixDetails> refixList = tbComparamMasService
                                .findPrefixData(configDetDTO.getPrefixId(), orgId);
                        for (SequenceConfigDetDTO configDetDTO1 : sequenceConfigurationMaster.getConfigDetDTOs()) {
                            if (configDetDTO1.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.LEVEL)) {
                                for (ViewPrefixDetails details : refixList) {
                                    if (details.getComValue().equals(configDetDTO1.getPrefixId())) {
                                        level = details.getComLevel();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                // This will set the level code if the selected data is hierarchical
                ctrlId = getLevelCtrlId(commonSeqDto, ctrlId, orgId, level);

            }
            // This will set the level code if the selected data is non hierarchical
            else if (type.equals(MainetConstants.FlagN)) {
                for (SequenceConfigDetDTO configDetDTO : sequenceConfigurationMaster.getConfigDetDTOs()) {
                    if (configDetDTO.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.LEVEL)) {
                        ctrlId = configDetDTO.getPrefixId();
                        break;
                    }
                }
            }

        }
        // set the value for level code in normal base
        else if (catName.equals(MainetConstants.CAT_ID.NORMAL_BASE)) {
            ctrlId = "0";
        }

        // derive column name base on selected sequence name
        colName = seqNameCode.getLookUpCode();

        // derive table name base on selected sequence name
        tabName = seqNameCode.getLookUpDesc();

        // Derive Sequence Type
        LookUp seqTypeCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                sequenceConfigurationMaster.getSeqType(), sequenceConfigurationMaster.getOrgId(),
                MainetConstants.SEQ_PREFIXES.SQT);
        resetFlag = seqTypeCode.getLookUpCode();
        startNo = sequenceConfigurationMaster.getSeqFrmNo();
        if (startNo == null) {
            startNo = 1L;
        }
        len = sequenceConfigurationMaster.getSeqLength();
        // Initialize the last number
        StringBuilder lastnumber = new StringBuilder();
        for (int i = 0; i < len; i++) {
            lastnumber.append("9");
        }

        lastNum = Long.parseLong(lastnumber.toString());
        deptId = sequenceConfigurationMaster.getDeptId().toString();

        // Get the sequence number by passing the parameters to the method
        sequence = getNumericSeqNo(ctrlId, tabName, colName, orgId, resetFlag, deptId, startNo, lastNum);
        LOGGER.info("Sequence Numeric number reti=urn by calling procedure" + sequence);

        // format the generated sequence number base on category
        if (catName.equals(MainetConstants.CAT_ID.NORMAL_BASE)) {
            seqNo = formatNormalBaseSequenceNumber(sequenceConfigurationMaster, sequence);

        } else if (catName.equals(MainetConstants.CAT_ID.PREFIX_BASE)) {
            LOGGER.info("Enter into the Prefix base condition" + catName);
            seqNo = formatPrefixBaseSequenceNumber(sequenceConfigurationMaster, sequence, commonSeqDto, level,
                    usageCtrlId);
        }
        LOGGER.info("Composite Sequence No is" + seqNo);
        return seqNo;
    }

    // Returns the id of the selected hierarchical prefix data
    private String getLevelCtrlId(CommonSequenceConfigDto seqCommonDto, String ctrlId, Long orgId, int level) {

        Long levelCtrlId = 0L;

        if (level != 0) {
            if (seqCommonDto.getLevel1Id() != null && level == 1) {
                levelCtrlId = seqCommonDto.getLevel1Id();
            } else if (seqCommonDto.getLevel2Id() != null && level == 2) {
                levelCtrlId = seqCommonDto.getLevel2Id();
            } else if (seqCommonDto.getLevel3Id() != null && level == 3) {
                levelCtrlId = seqCommonDto.getLevel3Id();
            } else if (seqCommonDto.getLevel4Id() != null && level == 4) {
                levelCtrlId = seqCommonDto.getLevel4Id();
            } else if (seqCommonDto.getLevel5Id() != null && level == 5) {
                levelCtrlId = seqCommonDto.getLevel5Id();
            }

        }
        return levelCtrlId.toString();
    }

    // To get the SequenceConfigMasterDto from the database base on passed
    // parameters
    public SequenceConfigMasterDTO loadSequenceData(Long orgId, Long deptId, String tbName, String colName) {

        SequenceConfigMasterDTO configMasterDTO;

        configMasterDTO = sequenceConfigMasterService.loadSequenceData(orgId, deptId, tbName, colName);

        return configMasterDTO;
    }

    // Format the sequence number for normal base configuration
    public String formatNormalBaseSequenceNumber(SequenceConfigMasterDTO sequenceConfigurationMaster, Long sequenceNo) {

        Long seqLen = sequenceConfigurationMaster.getSeqLength();

        String format = "%0" + seqLen + "d";
        String seq = String.format(format, Integer.parseInt(sequenceNo.toString()));

        return seq;
    }

    // Format sequence number for prefix base configuration and return the composite
    // key
    public String formatPrefixBaseSequenceNumber(SequenceConfigMasterDTO sequenceConfigurationMaster, Long sequenceNo,
            CommonSequenceConfigDto seqCommonDto, int level, String usageCtrlId) {

        List<SequenceConfigDetDTO> sequenceConfigDetDTOs = reorderAllPrefixes(sequenceConfigurationMaster);
        LOGGER.info("Inside format prefix base sequence number" + sequenceConfigDetDTOs.size());
        Long seqLen = sequenceConfigurationMaster.getSeqLength();

        String format = "%0" + seqLen + "d";
        String seqNo = String.format(format, Integer.parseInt(sequenceNo.toString()));
        Long seqSepId = sequenceConfigurationMaster.getSeqSep();
        String seqSep = new String();
        StringBuilder prefixes = new StringBuilder();

        // Derive Sequence separator code
        if (seqSepId != null && seqSepId != 0) {
            LookUp seqSepCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(seqSepId,
                    sequenceConfigurationMaster.getOrgId(), MainetConstants.SEQ_PREFIXES.SEP);
            seqSep = seqSepCode.getLookUpDesc();
            LOGGER.info("Sequence Separator is" + seqSep);
        }
        Long orgId = sequenceConfigurationMaster.getOrgId();
        for (SequenceConfigDetDTO sequenceConfigDetDTO : sequenceConfigDetDTOs) {
            LOGGER.info("Entered into composing the sequence");
            String prefixId = sequenceConfigDetDTO.getPrefixId();

            if (!prefixId.equals(MainetConstants.FlagN) && !prefixId.equals(MainetConstants.FlagY)
                    && !sequenceConfigDetDTO.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.DEPARTMENT_PREFIX)
                    && !prefixId.equals("0") && !prefixId.isEmpty()
                    && !sequenceConfigDetDTO.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.LEVEL)) {
                prefixes.append(prefixId);
                prefixes.append(seqSep);
            } else if (prefixId.equals(MainetConstants.FlagY)
                    || sequenceConfigDetDTO.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.LEVEL)) {

                switch (sequenceConfigDetDTO.getSeqFactId()) {
                case MainetConstants.SQ_FACT_ID.ORG_ID:
                    Organisation org = UserSession.getCurrent().getOrganisation();
                    prefixes.append(org.getOrgShortNm());
                    prefixes.append(seqSep);
                    LOGGER.info("Composite key after adding org name" + prefixes);
                    break;
                case MainetConstants.SQ_FACT_ID.SERVICE_CODE:
                    if (seqCommonDto.getServiceCode() != null) {
                        prefixes.append(seqCommonDto.getServiceCode());
                        prefixes.append(seqSep);
                    }
                    break;
                case MainetConstants.SQ_FACT_ID.BUSINESS_UNIT_CODE:
                    prefixes.append(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocAddress());
                    prefixes.append(seqSep);
                    break;
                case MainetConstants.SQ_FACT_ID.DEPARTMENT_CODE:
                    prefixes.append(ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                            .getDeptCode(sequenceConfigurationMaster.getDeptId()));
                    prefixes.append(seqSep);
                    LOGGER.info("Composite key after adding department code" + prefixes);
                    break;
                case MainetConstants.SQ_FACT_ID.LEVEL:
                    getLevelPrefixes(sequenceConfigurationMaster, seqCommonDto, level, seqSep, prefixes, orgId);
                    break;
                case MainetConstants.SQ_FACT_ID.USAGE_TYPE:
                    getUsagePrefixes(seqCommonDto, usageCtrlId, seqSep, prefixes, orgId);
                    break;
                case MainetConstants.SQ_FACT_ID.FINANCIAL_YEAR_WISE:
                    if (seqCommonDto.getFinancialYear() != null) {
                        prefixes.append(seqCommonDto.getFinancialYear());
                        prefixes.append(seqSep);
                    }
                    break;
                case MainetConstants.SQ_FACT_ID.DESIGNATION:
                    prefixes.append(UserSession.getCurrent().getEmployee().getDesignation().getDsgshortname());
                    prefixes.append(seqSep);
                    LOGGER.info("Composite key after adding designation" + prefixes);
                    break;
                case MainetConstants.SQ_FACT_ID.TRADE_CAT:
                    if (seqCommonDto.getTradeCategory() != null) {
                        prefixes.append(CommonMasterUtility
                                .getHierarchicalLookUp(seqCommonDto.getTradeCategory(), orgId).getLookUpCode());
                        prefixes.append(seqSep);
                    }
                    break;
                case MainetConstants.SQ_FACT_ID.Calendar_Year_wise:
                    Integer year = Calendar.getInstance().get(Calendar.YEAR);
                    prefixes.append(year.toString());
                    prefixes.append(seqSep);
                    break;
                case MainetConstants.SQ_FACT_ID.FIELD:
                    if (seqCommonDto.getCustomField() != null) {
                        prefixes.append(seqCommonDto.getCustomField());
                        prefixes.append(seqSep);
                    }
                    break;
                }
            }
        }

        return prefixes.append(seqNo).toString();
    }

    // append the usage type in the sequence number
    private void getUsagePrefixes(CommonSequenceConfigDto seqCommonDto, String usageCtrlId, String seqSep,
            StringBuilder prefixes, Long orgId) {
        if (seqCommonDto.getUsageCtrlId() != 1 && seqCommonDto.getUsageCtrlId() != null) {
            seqCommonDto.setUsageCtrlId(getUsageType(seqCommonDto.getUsageIds()));
        }
        if (seqCommonDto.getUsageCtrlId() == 1 && seqCommonDto.getUsageCtrlId() != 0
                && seqCommonDto.getUsageCtrlId() != null) {
            if (seqCommonDto.getUsageId1() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId1(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            } else if (seqCommonDto.getUsageId2() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId1(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId2(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            } else if (seqCommonDto.getUsageId3() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId1(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId2(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId3(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            } else if (seqCommonDto.getUsageId4() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId1(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId2(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId3(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId4(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            } else if (seqCommonDto.getUsageId5() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId1(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId2(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId3(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId4(), orgId).getLookUpCode());
                prefixes.append(seqSep);
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getUsageId5(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            }
        } // Execute if selected usage types are not same
        else if (seqCommonDto.getUsageCtrlId() == 0) {
            prefixes.append(MainetConstants.SQ_FACT_ID.MIX_USAGE_CODE);
            prefixes.append(seqSep);
        }
    }

    // Append the ward-zone hierarchy base on selected level
    private void getLevelPrefixes(SequenceConfigMasterDTO sequenceConfigurationMaster,
            CommonSequenceConfigDto seqCommonDto, int level, String seqSep, StringBuilder prefixes, Long orgId) {
        if (level != 0) {
            if (seqCommonDto.getLevel1Id() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getLevel1Id(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            }
            if (seqCommonDto.getLevel2Id() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getLevel2Id(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            }
            if (seqCommonDto.getLevel3Id() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getLevel3Id(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            }
            if (seqCommonDto.getLevel4Id() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getLevel4Id(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            }
            if (seqCommonDto.getLevel5Id() != null) {
                prefixes.append(
                        CommonMasterUtility.getHierarchicalLookUp(seqCommonDto.getLevel5Id(), orgId).getLookUpCode());
                prefixes.append(seqSep);
            }
        } else if (level == 0) {
            for (SequenceConfigDetDTO configDetDTO : sequenceConfigurationMaster.getConfigDetDTOs()) {
                if (configDetDTO.getSeqFactId().equals(MainetConstants.SQ_FACT_ID.LEVEL)) {
                    prefixes.append(configDetDTO.getPrefixId());
                    prefixes.append(seqSep);
                    break;
                }
            }
        }
    }

    // Retrieve the detail data base in the order
    private List<SequenceConfigDetDTO> reorderAllPrefixes(SequenceConfigMasterDTO sequenceConfigurationMaster) {

        List<SequenceConfigDetDTO> listt = sequenceConfigurationMaster.getConfigDetDTOs().stream()
                .filter(c -> c.getSeqOrder() != null)
                .sorted((c1,
                        c2) -> (c1.getSeqOrder() != null && c2.getSeqOrder() != null
                                && (c1.getSeqOrder().compareTo(c2.getSeqOrder()) > 0)) ? 1 : -1)
                .collect(Collectors.toList());
        return listt;

    }

    // It will return the usage type value base on entered usage types are mixed or
    // same

    public Long getUsageType(List<Long> list) {
        Long flag = 0L;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) {
                    flag = 1L;
                } else {
                    flag = 0L;
                }
            }
        }

        return flag;
    }

}
