package com.abm.mainet.common.utility;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.ui.model.LogOutModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonMasterUtility {

    private static final Logger logger = LoggerFactory
            .getLogger(CommonMasterUtility.class);

    /***
     * Lookup value of that prefix and Code
     *
     * @deprecated Should not be used from service classes. Instead use the overloaded version with organisation
     * @param codeValue
     * @param prefix
     * @return
     */
    @Deprecated
    public static LookUp getValueFromPrefixLookUp(final String codeValue,
            final String prefix) {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final List<LookUp> lookupList = getLookUps(prefix, org);
        LookUp lookUp = null;
        if ((lookupList != null) && !lookupList.isEmpty()) {
            for (final LookUp prefixLookup : lookupList) {
                if (prefixLookup.getLookUpCode().equals(codeValue)) {
                    lookUp = prefixLookup;
                    break;
                }
            }
            if (lookUp == null) {
                logger.error("NonHierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + " and Organization-->" + org.getONlsOrgname()
                        + "but not found for codeValue -->" + codeValue + "]");
                throw new FrameworkException("NonHierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + " and Organization-->" + org.getONlsOrgname()
                        + "but not found for codeValue -->" + codeValue + "]");
            }

        } else {
            logger.error("NonHierarchicalLookUp --> prefix not found for:[prefix -->" + prefix
                    + " and Organization-->" + org.getONlsOrgname() + "]");
            throw new FrameworkException("NonHierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                    + " and Organization-->" + org.getONlsOrgname()
                    + "but not found for codeValue -->" + codeValue + "]");
        }
        return lookUp;
    }

    public static LookUp getValueFromPrefixLookUp(final String codeValue,
            final String prefix, final Organisation organisation) {
        final List<LookUp> lookupList = getLookUps(prefix, organisation);
        LookUp lookUp = null;
        if ((lookupList != null) && !lookupList.isEmpty()) {
            final Iterator<LookUp> lookup = lookupList.iterator();
            while (lookup.hasNext()) {

                final LookUp lookUp2 = lookup.next();

                if (lookUp2.getLookUpCode().equals(codeValue)) {
                    lookUp = lookUp2;
                    break;
                }
            }
            if (lookUp == null) {
                logger.error("NonHierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + " and Organization-->"
                        + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                        + "but not found for codeValue -->" + codeValue + "]");
                throw new FrameworkException("NonHierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                        + "but not found for codeValue -->" + codeValue + "]");
            }

        } else {
            logger.error("NonHierarchicalLookUp --> prefix not found for:[prefix -->" + prefix
                    + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                    + "]");
            throw new FrameworkException("NonHierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                    + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                    + "but not found for codeValue -->" + codeValue + "]");

        }
        return lookUp;
    }

    /**
     * similar to function fn_getcpdvalue function in D2K
     *
     * @param prefix
     * @return
     */
    /*
     * @Deprecated public static String getCPDValue(final String prefix) { String cpdValue = null; final List<LookUp> lookupList =
     * getCurrent().getLevelData(prefix); if ((lookupList != null) && !lookupList.isEmpty()) { cpdValue =
     * lookupList.get(0).getLookUpCode(); } else { logger.error("prefix not found for:[prefix -->" + prefix + "]"); } return
     * cpdValue; }
     */

    /**
     * similar to function fn_getcpdvalue function in D2K
     *
     * @param prefix
     * @return
     */
    public static LookUp getDefaultValue(final String prefix) {
        LookUp lookUpVal = null;
        final List<LookUp> lookupList = getCurrent().getLevelData(prefix);
        if ((lookupList != null) && !lookupList.isEmpty()) {
            for (final LookUp lookUp : lookupList) {
                if (lookUp.getDefaultVal().equals(
                        PrefixConstants.IsLookUp.STATUS.YES)) {
                    lookUpVal = lookUp;
                    break;
                }
            }
            if (lookUpVal == null) {
                logger.error("HierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + "but not found for default value]");
                throw new FrameworkException("HierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + " and ");
            }
        } else {
            logger.error("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefix + "]");
            throw new FrameworkException("HierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                    + " ");
        }

        return lookUpVal;
    }

    public static LookUp getDefaultValue(final String prefix, final Organisation org) {
        LookUp lookUpVal = null;
        final List<LookUp> lookupList = getCurrent().getLevelData(prefix, org);
        if ((lookupList != null) && !lookupList.isEmpty()) {
            for (final LookUp lookUp : lookupList) {
                if (lookUp.getDefaultVal().equals(
                        PrefixConstants.IsLookUp.STATUS.YES)) {
                    lookUpVal = lookUp;
                    break;
                }
            }
            if (lookUpVal == null) {
                logger.error("HierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + "but not found for default value]");
                throw new FrameworkException("HierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + " and Organization-->" + org.getOrgid() + "|"+ org.getONlsOrgname()
                        + "");
            }
        } else {
            logger.error("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefix + "]");
            throw new FrameworkException("HierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                    + " and Organization-->" + org.getOrgid() + "|"+ org.getONlsOrgname()
                    + "");
        }

        return lookUpVal;
    }

    /*
     * public static String getCPDValue(final Organisation organisation, final String prefix) { String cpdValue = null; final
     * List<LookUp> lookupList = ApplicationSession.getInstance() .getHierarchicalLookUp(organisation, prefix).get(prefix); if
     * ((lookupList != null) && !lookupList.isEmpty()) { cpdValue = lookupList.get(0).getLookUpCode(); } else {
     * logger.error("prefix not found for:[prefix -->" + prefix + " and Organization-->" + organisation.getONlsOrgname() + "]"); }
     * return cpdValue; }
     */

    /**
     * similar to fn_getcpddesc function in D2K
     *
     * @param value
     * @param type
     * @return
     */

    public static String getCPDDescription(final Long value, final String type) {
        String result = null;
        final LookUp lookup = getCurrent().getNonHierarchicalLookUpObject(value);
        switch (type) {
            case PrefixConstants.D2KFUNCTION.ENGLISH_DESC: {
                result = lookup.getDescLangFirst();
                break;
            }

            case PrefixConstants.D2KFUNCTION.REG_DESC: {
                result = lookup.getDescLangSecond();
                break;
            }

            case PrefixConstants.D2KFUNCTION.CPD_VALUE: {
                result = lookup.getLookUpCode();
                break;
            }

            case PrefixConstants.D2KFUNCTION.CPD_OTHERVALUE: {
                result = lookup.getOtherField();
                break;
            }

            default: {
                result = lookup.getLookUpDesc();
                break;
            }
        }
        return result;
    }

    /**
     * Get Department LookUp from Department ShortCode
     *
     * @param deptCode
     * @return
     */
    // used only in account. need to remove
    public static DepartmentLookUp getDeptIdFromDeptCode(final String deptCode) {
        DepartmentLookUp departmentLookUp = null;

        DepartmentLookUp department = null;

        final List<DepartmentLookUp> departmentsLookUpList = getCurrent()
                .getDepartmentLookUp();

        if ((departmentsLookUpList != null) && !departmentsLookUpList.isEmpty()) {

            final Iterator<DepartmentLookUp> departmentsLookUp = departmentsLookUpList
                    .iterator();

            while (departmentsLookUp.hasNext()) {
                department = departmentsLookUp.next();

                if (department.getLookUpCode().equals(deptCode)) {
                    departmentLookUp = department;
                }
            }
        }
        return departmentLookUp;
    }

    @Deprecated
    public static Long getIdFromPrefixLookUpDesc(final String valueDesc,
            final String prefix, final int langID) {

        final List<LookUp> lookuplist = getLookUps(prefix, UserSession.getCurrent()
                .getOrganisation());

        String desc = StringUtils.EMPTY;

        Long lookUpID = -1L;

        LookUp lookUp2 = null;

        if ((lookuplist != null) && !lookuplist.isEmpty()) {

            final Iterator<LookUp> lookup = lookuplist.iterator();

            while (lookup.hasNext()) {

                lookUp2 = lookup.next();

                if (langID == MainetConstants.NUMBERS.ONE) {
                    desc = lookUp2.getDescLangFirst();
                } else {
                    desc = lookUp2.getDescLangSecond();
                }

                if ((desc != null) && desc.equalsIgnoreCase(valueDesc)) {
                    lookUpID = lookUp2.getLookUpId();

                    break;
                }

            }
        }
        return lookUpID;
    }

    public static Long getIdFromPrefixLookUpDesc(final String valueDesc, final String prefix, final int langID,
            final Organisation organisation) {
        final List<LookUp> lookuplist = getLookUps(prefix, organisation);
        String desc = StringUtils.EMPTY;
        Long lookUpID = -1L;
        if ((lookuplist != null) && !lookuplist.isEmpty()) {
            final Iterator<LookUp> lookup = lookuplist.iterator();
            while (lookup.hasNext()) {
                final LookUp lookUp2 = lookup.next();
                if (langID == MainetConstants.NUMBERS.ONE) {
                    desc = lookUp2.getDescLangFirst();
                } else {
                    desc = lookUp2.getDescLangSecond();
                }
                if ((desc != null) && desc.equals(valueDesc)) {
                    lookUpID = lookUp2.getLookUpId();
                    break;
                }
            }
        }
        return lookUpID;
    }

    @Deprecated
    public static LookUp getLookUpFromPrefixLookUpDesc(final String valueDesc,
            final String prefix, final int langID) {

        final List<LookUp> lookuplist = getLookUps(prefix, UserSession.getCurrent()
                .getOrganisation());

        String desc = StringUtils.EMPTY;

        LookUp lookUp = new LookUp();

        LookUp lookUp2 = null;
        if ((lookuplist != null) && !lookuplist.isEmpty()) {
            final Iterator<LookUp> lookup = lookuplist.iterator();
            while (lookup.hasNext()) {

                lookUp2 = lookup.next();

                if (langID == MainetConstants.NUMBERS.ONE) {
                    desc = lookUp2.getDescLangFirst();
                } else {
                    desc = lookUp2.getDescLangSecond();
                }

                if ((desc != null) && desc.equals(valueDesc)) {
                    lookUp = lookUp2;

                    break;
                }
            }
        }
        return lookUp;
    }

    // 5 -reference in account
    @Deprecated
    public static LookUp getLookUpFromPrefixLookUpValue(final String cpdValue,
            final String prefix, final int langID) {

        final List<LookUp> lookuplist = getLookUps(prefix, UserSession.getCurrent()
                .getOrganisation());

        /* String value = StringUtils.EMPTY; */

        LookUp lookUp = new LookUp();

        LookUp lookUp2 = null;
        if ((lookuplist != null) && !lookuplist.isEmpty()) {
            final Iterator<LookUp> lookup = lookuplist.iterator();
            while (lookup.hasNext()) {

                lookUp2 = lookup.next();

                /*
                 * if (langID == MainetConstants.NUMBERS.ONE) {
                 * value = lookUp2.getDescLangFirst();
                 * } else {
                 * value = lookUp2.getDescLangSecond();
                 * }
                 */

                if (lookUp2.getLookUpCode() != null && lookUp2.getLookUpCode().equals(cpdValue)) {
                    lookUp = lookUp2;

                    break;
                }
            }
        }
        return lookUp;
    }

    public static LookUp getLookUpFromPrefixLookUpDesc(final String valueDesc,
            final String prefix, final int langID, final Organisation organisation) {
        final List<LookUp> lookuplist = getLookUps(prefix, organisation);

        String desc = StringUtils.EMPTY;

        LookUp lookUp = new LookUp();

        if ((lookuplist != null) && !lookuplist.isEmpty()) {

            final Iterator<LookUp> lookup = lookuplist.iterator();

            while (lookup.hasNext()) {

                final LookUp lookUp2 = lookup.next();

                if (langID == MainetConstants.NUMBERS.ONE) {
                    desc = lookUp2.getDescLangFirst();
                } else {
                    desc = lookUp2.getDescLangSecond();
                }

                if ((desc != null) && desc.equals(valueDesc)) {
                    lookUp = lookUp2;

                    break;
                }
            }
        }
        return lookUp;
    }

    @Deprecated
    public static LookUp getHierarchicalLookUp(final long lookUpId) {
        return ApplicationSession.getInstance().getHierarchicalLookUp(
                UserSession.getCurrent().getOrganisation(), lookUpId);

    }

    public static LookUp getHierarchicalLookUp(final long lookUpId, final long orgID) {
        Organisation org = new Organisation();
        org.setOrgid(orgID);
        return ApplicationSession.getInstance().getHierarchicalLookUp(
                org, lookUpId);

    }

    public static LookUp getHierarchicalLookUp(final long lookUpId,
            final Organisation organisation) {
        return ApplicationSession.getInstance().getHierarchicalLookUp(
                organisation, lookUpId);

    }

    @Deprecated
    public static LookUp getNonHierarchicalLookUpObject(final long lookUpId) {
        return ApplicationSession.getInstance()
                .getNonHierarchicalLookUp(
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        lookUpId);
    }

    public static LookUp getNonHierarchicalLookUpObjectByPrefix(final long lookUpId, final Long orgId,
            String prifixName) {
        return ApplicationSession.getInstance().getNonHierarchicalLookUpByPrefix(orgId, lookUpId, prifixName);
    }

    public static LookUp getNonHierarchicalLookUpObject(final long lookUpId,
            final Organisation organisation) {
        return ApplicationSession.getInstance().getNonHierarchicalLookUp(
                organisation.getOrgid(), lookUpId);
    }

    public static List<LookUp> getLookUps(final String lookUpCode,
            final Organisation organisation) {
        final List<LookUp> lookupList = ApplicationSession.getInstance()
                .getNonHierarchicalLookUp(organisation.getOrgid(), lookUpCode)
                .get(lookUpCode);
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("NonHierarchicalLookUp --> prefix not found for:[prefix -->" + lookUpCode
                    + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                    + "]");
        }
        return lookupList;
    }

    private static AbstractModel getCurrent() {
        return ApplicationContextProvider.getApplicationContext().getBean(LogOutModel.class);
    }

    public static List<LookUp> getLevelData(final String prefixCode, final int level,
            final Organisation organisation) {
        final List<LookUp> lookupList = ApplicationSession.getInstance().getLookUpsByLevel(
                organisation.getOrgid(), prefixCode, level);
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefixCode + "Level--> " + level
                    + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                    + "]");
            throw new FrameworkException("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefixCode + "Level--> " + level
                    + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                    + "]");
        }

        return lookupList;

    }

    public static List<LookUp> getListLookup(final String lookUpCode,
            final Organisation organisation) {
        final List<LookUp> lookupList = ApplicationSession.getInstance()
                .getHierarchicalLookUp(organisation, lookUpCode)
                .get(lookUpCode);

        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("HierarchicalLookUp --> prefix not found for:[prefix -->" + lookUpCode
                    + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname()
                    + "]");
            throw new FrameworkException("HierarchicalLookUp --> prefix not found for:[prefix -->" + lookUpCode
                    + " and Organization-->" + organisation.getOrgid() + "|"+ organisation.getONlsOrgname() + "]");
        }

        return lookupList;

    }

    /**
     * @author umashanker.kanaujiya 04-Mar-2015 TODO List<LookUp> "This for Mobile Application "
     *//*
        * public static List<LookUp> getHierarchicalForSubDetails( final Organisation organisation, final String lookUpCode, final
        * long lookUpId) { return ApplicationSession.getInstance().getHierarchicalForSubDetails( organisation, lookUpCode,
        * lookUpId); }
        */

    /**
     * @author umashanker.kanaujiya 04-Mar-2015 TODO Map<String,List<LookUp>> "This for Mobile Application "
     */
    /*
     * public static Map<String, List<LookUp>> getNonHierarchicalLookUpForWS( final Organisation organisation, final String
     * lookUpCode) { return ApplicationSession.getInstance().getNonHierarchicalLookUp( organisation, lookUpCode); }
     */

    /**
     * @author umashanker.kanaujiya 04-Mar-2015 TODO List<DepartmentLookUp> "This for Mobile Application "
     */
    public static List<DepartmentLookUp> getDepartmentForWS(
            final Organisation organisation) {
        return ApplicationSession.getInstance().getDepartments(organisation);
    }

    public static List<LookUp> getChildLookUpsFromParentId(final long parentId) {

        return ApplicationSession.getInstance().getChildLookUpsFromParentId(
                parentId);
    }

    @Deprecated
    public static List<LookUp> getSecondLevelData(final String prefixCode, final int level) {
        List<LookUp> lookupList = null;
        if (level <= 0) {
            lookupList = new ArrayList<>();
        }

        else {
            lookupList = ApplicationSession.getInstance().getLookUpsByLevel(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    prefixCode, level);
        }
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefixCode
                    + " and level-->" + level
                    + "]");
            throw new FrameworkException("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefixCode  + " and level-->" + level
                    + "]");
        }
        return lookupList;

    }

    /**
     * use this method to get lookUpId description
     * @param prefix
     * @param orgID
     * @param field
     * @return
     */
    public static String findLookUpDesc(final String prefix, final long orgID, final long field) {
        String lookUpDesc = StringUtils.EMPTY;
        final Organisation org = new Organisation();
        org.setOrgid(orgID);
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(prefix, org);
        if ((lookUps == null) || lookUps.isEmpty()) {
            throw new FrameworkException("NonHierarchicalLookUp --> LookUp Object not found for Prefix=" + prefix + " and orgId=" + orgID);
        } else {
            for (final LookUp lookUp : lookUps) {
                if (lookUp.getLookUpId() == field) {
			lookUpDesc = lookUp.getDescLangFirst();
			break;
                }
            }
        }
        return lookUpDesc;
    }

    public static String getAmountInIndianCurrency(final BigDecimal amountStart) {

        BigDecimal amount = amountStart.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        final BigInteger bigint = amount.toBigInteger();
        final DecimalFormat formatter = new DecimalFormat("##,###");
        final String amount11 = amount.toPlainString();
        final String amount1 = amount11.toString();
        final int firstPosition = amount1.indexOf(".");
        String decimalPart = ".00";
        if (firstPosition > 0) {
            decimalPart = amount1.substring(firstPosition, amount1.length());
        }
        if (decimalPart.length() < 3) {
            decimalPart += "0";
        } else if (decimalPart.length() < 2) {
            decimalPart += "00";
        }
        String returnValue = "";
        if (bigint.longValue() > 9999) {
            formatter.applyPattern("#,##");
            returnValue = formatter.format(bigint.longValue() / 1000) + MainetConstants.operator.COMMA;
            formatter.applyPattern("#,###");
            final String amountdiff = formatter.format(bigint.longValue() - ((bigint.longValue() / 1000) * 1000));
            if (amountdiff.length() == 1) {
                returnValue += "00" + amountdiff;
            } else if (amountdiff.length() == 2) {
                returnValue += "0" + amountdiff;
            } else {
                returnValue += amountdiff;
            }
        } else if ((bigint.longValue() >= 1000) && (bigint.longValue() <= 9999)) {
            formatter.applyPattern("#,###");
            returnValue = formatter.format(bigint.longValue());
        } else {
            returnValue += bigint.longValue();
        }
        return returnValue + decimalPart;
    }

    /**
     * use this method incase you need lookUpId by lookUpCode and prefix
     * @param lookUpCode
     * @param prefix
     * @param orgId
     * @return lookUpId
     * @throws NullPointerException : lookUpCode, prefix is mandatory to pass
     * @throws IllegalArgumentException : orgId should pass non zero value
     */
    public static long lookUpIdByLookUpCodeAndPrefix(final String lookUpCode, final String prefix, final long orgId) {

        if ((prefix == null) || prefix.isEmpty()) {
            throw new NullPointerException("prefix cannot be null or empty.");
        }
        if ((lookUpCode == null) || lookUpCode.isEmpty()) {
            throw new NullPointerException("lookUpCode cannot be null or empty.");
        }
        if (orgId == 0l) {
            throw new IllegalArgumentException("orgId cannot be zero.");
        }
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        final List<LookUp> lookUps = getLookUps(prefix, org);
        long lookUpId = 0l;
        if ((lookUps != null) && !lookUps.isEmpty()) {
            for (final LookUp lookUp : lookUps) {
                if (lookUpCode.equals(lookUp.getLookUpCode())) {
                    lookUpId = lookUp.getLookUpId();
                    break;
                }

            }
        }
        return lookUpId;
    }

    /**
     * use this method incase you need lookUp by lookUpId and prefix
     * @param lookUpId
     * @param prefix
     * @param orgId
     * @return lookUp
     * @throws NullPointerException : lookUpId, prefix is mandatory to pass
     * @throws IllegalArgumentException : orgId should pass non zero value
     */

    public static LookUp lookUpByLookUpIdAndPrefix(final Long lookUpId, final String prefix, final long orgId) {
        if ((prefix == null) || prefix.isEmpty()) {
            throw new NullPointerException("prefix cannot be null or empty.");
        }
        if ((lookUpId == null)) {
            throw new NullPointerException("lookUpId cannot be null or empty.");
        }
        if (orgId == 0l) {
            throw new IllegalArgumentException("orgId cannot be zero.");
        }
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        final List<LookUp> lookUps = getLookUps(prefix, org);
        LookUp lookUpNew = null;
        if ((lookUps != null) && !lookUps.isEmpty()) {
            for (final LookUp lookUp : lookUps) {
                if (lookUpId == lookUp.getLookUpId()) {
                    lookUpNew = lookUp;
                    break;
                }
            }
        }
        return lookUpNew;
    }

    public static LookUp getLookUpFromPrefixLookUpValue(final String cpdValue,
            final String prefix, final int langID, final Organisation org) {

        final List<LookUp> lookuplist = getLookUps(prefix, org);

        /* String value = StringUtils.EMPTY; */

        LookUp lookUp = new LookUp();

        LookUp lookUp2 = null;
        if ((lookuplist != null) && !lookuplist.isEmpty()) {
            final Iterator<LookUp> lookup = lookuplist.iterator();
            while (lookup.hasNext()) {

                lookUp2 = lookup.next();

                /*
                 * if (langID == MainetConstants.NUMBERS.ONE) {
                 * value = lookUp2.getDescLangFirst();
                 * } else {
                 * value = lookUp2.getDescLangSecond();
                 * }
                 */

                if (lookUp2.getLookUpCode() != null && lookUp2.getLookUpCode().equals(cpdValue)) {
                    lookUp = lookUp2;

                    break;
                }
            }
        }
        return lookUp;
    }

    /**
     * use this method in order to get List<LookUp>
     *
     * @param prefix : pass prefix
     * @param orgId : pass orgId
     * @return List<LookUp> for provided parameter
     * @throws IllegalArgumentException if Prefix is not found for provided parameter {@code prefix } and {@code orgId}
     */
    public static List<LookUp> lookUpListByPrefix(final String prefix, final long orgId) {
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        final List<LookUp> lookUps = getLookUps(prefix, organisation);
        if ((lookUps == null) || lookUps.isEmpty()) {
            throw new FrameworkException("NonHierarchicalLookUp --> Prefix " + prefix + " not configured for Organization Id=" + orgId);
        }
        return lookUps;
    }

    /**
     * use this method in case you need lookUpId by prefix and otherField
     *
     * @param lookUpCode
     * @param prefix
     * @param orgId
     * @return
     */
    public static long lookUpIdByPrefixAndOtherField(final String prefix, final long otherField, final long orgId) {

        if ((prefix == null) || prefix.isEmpty()) {
            throw new NullPointerException("prefix cannot be null or empty.");
        }
        if (otherField == 0l) {
            throw new NullPointerException("otherField cannot be zero.");
        }
        if (orgId == 0l) {
            throw new IllegalArgumentException("orgId cannot be zero.");
        }
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        final List<LookUp> lookUps = getLookUps(prefix, org);
        long lookUpId = 0l;
        if ((lookUps != null) && !lookUps.isEmpty()) {
            for (final LookUp lookUp : lookUps) {
                if ((lookUp.getOtherField() == null) || lookUp.getOtherField().isEmpty()) {
                    throw new FrameworkException("NonHierarchicalLookUp --> otherField not defined for the prefix:" + prefix);
                } else {
                    if (otherField == Long.parseLong(lookUp.getOtherField())) {
                        lookUpId = lookUp.getLookUpId();
                        break;
                    }

                }

            }
        }
        return lookUpId;
    }

    public static List<LookUp> getNextLevelData(final String prefixCode, final int level, final Long orgid) {
        List<LookUp> lookupList = null;
        if (level <= 0) {
            lookupList = new ArrayList<>();
        } else {
            lookupList = ApplicationSession.getInstance().getLookUpsByLevel(orgid, prefixCode, level);
        }
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefixCode
                    + " and level-->" + level
                    + "]");
            throw new FrameworkException("HierarchicalLookUp --> prefix not found for:[prefix -->" + prefixCode
                    + " and level-->" + level
                    + "]");
        }
        return lookupList;

    }

    /**
     * use this method to cast single dataModel from request received
     * @param requestDTO
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object castRequestToDataModel(final WSRequestDTO requestDTO, final Class<?> clazz) {

        Object dataModel = null;
        try {
            if (requestDTO.getDataModel() != null) {

                Map<String, Object> requestMap = null;
                if (requestDTO.getDataModel() instanceof Map) {
                    requestMap = (LinkedHashMap<String, Object>) requestDTO.getDataModel();
                } else {
                    requestMap = getLinkedHasMapObject(requestDTO.getDataModel());
                }
                final String jsonString = new JSONObject(requestMap).toString();
                dataModel = new ObjectMapper().readValue(jsonString, clazz);
            }
        } catch (Exception ex) {
            throw new FrameworkException("Error while casting model from WSRequestDTO", ex);
        }
        return dataModel;

    }

    /**
     * use this method to get lookUpId Code
     * @param prefix
     * @param orgID
     * @param field
     * @return
     */
    public static String findLookUpCode(final String prefix, final long orgID, final long field) {
        String lookUpCode = StringUtils.EMPTY;
        final Organisation org = new Organisation();
        org.setOrgid(orgID);
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(prefix, org);
        if ((lookUps == null) || lookUps.isEmpty()) {
            throw new FrameworkException("NonHierarchicalLookUp --> LookUp Object not found for Prefix=" + prefix + " and orgId=" + orgID);
        } else {
            for (final LookUp lookUp : lookUps) {
                if (lookUp.getLookUpId() == field) {

                    lookUpCode = lookUp.getLookUpCode();
                    break;
                }
            }
        }
        return lookUpCode;
    }

    /**
     * use this method to get lookUpId OtherField
     * @param prefix
     * @param orgID
     * @param field
     * @return
     */
    /*
     * public static String findLookUpOtherField(final String prefix, final long orgID, final long field) { String otherField =
     * StringUtils.EMPTY; final Organisation org = new Organisation(); org.setOrgid(orgID); final List<LookUp> lookUps =
     * CommonMasterUtility.getLookUps(prefix, org); if ((lookUps == null) || lookUps.isEmpty()) { throw new
     * FrameworkException("LookUp Object not found for Prefix=" + prefix + " and orgId=" + orgID); } else { for (final LookUp
     * lookUp : lookUps) { if (lookUp.getLookUpId() == field) { otherField = lookUp.getOtherField(); break; } } } return
     * otherField; }
     */

    /**
     * use this method to get lookUpCode description
     * @param prefix
     * @param orgID
     * @param field
     * @return
     */
    public static String findLookUpCodeDesc(final String prefix, final long orgID, final String field) {
        String lookUpDesc = StringUtils.EMPTY;
        final Organisation org = new Organisation();
        org.setOrgid(orgID);
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(prefix, org);
        if ((lookUps == null) || lookUps.isEmpty()) {
            throw new FrameworkException("NonHierarchicalLookUp --> LookUp Object not found for Prefix=" + prefix + " and orgId=" + orgID);
        } else {
            for (final LookUp lookUp : lookUps) {
                if (lookUp.getLookUpCode().equals(field)) {

                    lookUpDesc = lookUp.getDescLangFirst();
                    break;
                }
            }
        }
        return lookUpDesc;
    }

    public static List<LookUp> findFinancialYearWiseMonthList(final String prefix, final Organisation organisation) {

        final List<LookUp> lookUps = getLookUps(prefix, organisation);
        final List<LookUp> lookUPList = new ArrayList<>();
        // Add APR to DEC
        for (int i = 3; i < 12; i++) {
            final LookUp lookUp = lookUps.get(i);
            lookUp.setOtherField(Integer.toString(i));
            lookUPList.add(lookUp);
        }
        // Add JAN to MARCH
        for (int i = 0; i < 3; i++) {
            final LookUp lookUp = lookUps.get(i);
            lookUp.setOtherField(Integer.toString(i));
            lookUPList.add(lookUp);
        }

        return lookUPList;
    }

    /**
     * use this method to get lookUp
     * @param lookUpCode
     * @param prefix
     * @param level
     * @param orgId
     * @return
     */
    public static LookUp getHieLookupByLookupCode(final String lookUpCode, final String prefix, final int level,
            final Long orgId) {
        List<LookUp> lookupList = CommonMasterUtility.getNextLevelData(prefix, level, orgId);
        LookUp lookUp = null;
        if ((lookupList != null) && !lookupList.isEmpty()) {
            for (LookUp l : lookupList) {
                if (l.getLookUpCode().equals(lookUpCode)) {
                    lookUp = l;
                    break;
                }
            }
        } else {
            logger.error("HierarchicalLookUp --> prefix not found for: prefix -->" + prefix + " and level-->" + level);
            throw new FrameworkException("HierarchicalLookUp --> prefix not found for: prefix -->" + prefix + " and level-->" + level);
            
        }
        return lookUp;
    }

    public static LookUp getDefaultValueByOrg(final String prefix, final Organisation organisation) {
        LookUp lookUpVal = null;
        final List<LookUp> lookuplist = getLookUps(prefix, organisation);
        if ((lookuplist != null) && !lookuplist.isEmpty()) {
            for (final LookUp lookUp : lookuplist) {
                if (lookUp.getDefaultVal().equals(
                        PrefixConstants.IsLookUp.STATUS.YES)) {
                    lookUpVal = lookUp;
                    break;
                }
            }
            if (lookUpVal == null) {
                logger.error("NonHierarchicalLookUp --> prefix found for:[prefix -->" + prefix
                        + "but not found for default value]");
                throw new FrameworkException("NonHierarchicalLookUp --> prefix not found for: prefix -->" + prefix + " and organisation-->" + organisation.getOrgid() + "|" + organisation.getONlsOrgname());
            }
        } else {
            logger.error("NonHierarchicalLookUp --> prefix not found for:[prefix -->" + prefix + "]");
            throw new FrameworkException("NonHierarchicalLookUp --> prefix not found for: prefix -->" + prefix + " and organisation-->" + organisation.getOrgid() + "|"  + organisation.getONlsOrgname());
        }

        return lookUpVal;
    }

    private static Map<String, Object> getLinkedHasMapObject(Object parameter) {

        Map<String, Object> parameterMap = new LinkedHashMap<>();
        try {
            BeanInfo info = Introspector.getBeanInfo(parameter.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null)
                    parameterMap.put(pd.getName(), reader.invoke(parameter));
            }
            parameterMap.remove("class");
            parameterMap.values().removeIf(Objects::isNull);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException ex) {
            logger.error("Exception while getting getLinkedHasMapObject" + ex);

        }

        return parameterMap;
    }

    /**
     * use this method incase you need lookUpId by lookUpDesc and prefix
     * @param lookUpDesc
     * @param prefix
     * @param orgId
     * @return lookUpId
     * @throws NullPointerException : lookUpDesc, prefix is mandatory to pass
     * @throws IllegalArgumentException : orgId should pass non zero value
     */
    public static long lookUpIdByLookUpDescAndPrefix(final String lookUpDesc, final String prefix, final long orgId) {

        if ((prefix == null) || prefix.isEmpty()) {
            throw new NullPointerException("prefix cannot be null or empty.");
        }
        if ((lookUpDesc == null) || lookUpDesc.isEmpty()) {
            throw new NullPointerException("lookUpDesc cannot be null or empty.");
        }
        if (orgId == 0l) {
            throw new IllegalArgumentException("orgId cannot be zero.");
        }
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        final List<LookUp> lookUps = getLookUps(prefix, org);
        long lookUpId = 0l;
        if ((lookUps != null) && !lookUps.isEmpty()) {
            for (final LookUp lookUp : lookUps) {
                if (lookUpDesc.equals(lookUp.getLookUpDesc())) {
                    lookUpId = lookUp.getLookUpId();
                    break;
                }

            }
        }
        return lookUpId;
    }

    public static String getCPDDescription(final Long value, final String type, final Long orgId) {
        String result = null;
        final LookUp lookup = ApplicationSession.getInstance().getNonHierarchicalLookUp(orgId, value);
        switch (type) {
            case PrefixConstants.D2KFUNCTION.ENGLISH_DESC: {
                result = lookup.getDescLangFirst();
                break;
            }

            case PrefixConstants.D2KFUNCTION.REG_DESC: {
                result = lookup.getDescLangSecond();
                break;
            }

            case PrefixConstants.D2KFUNCTION.CPD_VALUE: {
                result = lookup.getLookUpCode();
                break;
            }

            case PrefixConstants.D2KFUNCTION.CPD_OTHERVALUE: {
                result = lookup.getOtherField();
                break;
            }

            default: {
                result = lookup.getLookUpDesc();
                break;
            }
        }
        return result;
    }
}