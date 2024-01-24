package com.abm.mainet.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Vikrant.Thakur
 * @since 29 Dec 2014
 * @Comment this class is develop to call D2K Function from lookUp
 */
public class CommonMasterUtility {

    private static final String PREFIX_FOUND_FOR_PREFIX = "prefix found for:[prefix -->";
    private static final Logger logger = Logger.getLogger(CommonMasterUtility.class);

    /***
     * Lookup value of that prefix and Code
     * @param codeValue
     * @param prefix
     * @return
     */

    public static LookUp getValueFromPrefixLookUp(final String codeValue, final String prefix) {
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
                logger.error(PREFIX_FOUND_FOR_PREFIX + prefix
                        + " and Organization-->" + org.getONlsOrgname()
                        + "but not found for codeValue -->" + codeValue + MainetConstants.operator.LEFT_SQUARE_BRACKET);
            }

        } else {
            logger.error("prefix not found for:[prefix -->" + prefix
                    + " and Organization-->" + org.getONlsOrgname() + MainetConstants.operator.LEFT_SQUARE_BRACKET);
        }
        return lookUp;
    }

    public static LookUp getValueFromPrefixLookUp(final String codeValue, final String prefix, final Organisation organisation) {
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
                logger.error(PREFIX_FOUND_FOR_PREFIX + prefix
                        + " and Organization-->"
                        + organisation.getONlsOrgname()
                        + "but not found for codeValue -->" + codeValue + MainetConstants.operator.LEFT_SQUARE_BRACKET);
            }

        } else {
            logger.error("prefix not found for:[prefix -->" + prefix
                    + " and Organization-->" + organisation.getONlsOrgname()
                    + MainetConstants.operator.LEFT_SQUARE_BRACKET);

        }
        return lookUp;
    }

   /* public static String getCPDValue(final Organisation organisation, final String prefix) {
        String cpdValue = null;
        final List<LookUp> lookupList = ApplicationSession.getInstance()
                .getHierarchicalLookUp(organisation, prefix).get(prefix);

        if ((lookupList != null) && !lookupList.isEmpty()) {
            cpdValue = lookupList.get(0).getLookUpCode();
        } else {
            logger.error("prefix not found for:[prefix -->" + prefix
                    + " and Organization-->" + organisation.getONlsOrgname()
                    + MainetConstants.operator.LEFT_SQUARE_BRACKET);
        }
        return cpdValue;
    }*/

    /**
     * similar to fn_getcpddesc function in D2K
     * @param value
     * @param type
     * @return
     */

    public static String getCPDDescription(final Long value, final String type) {
        String result = null;

        final LookUp lookup = ApplicationSession.getInstance()
                .getNonHierarchicalLookUp(UserSession.getCurrent().getOrganisation().getOrgid(), value);

        switch (type) {
        case MainetConstants.D2KFUNCTION.ENGLISH_DESC: {
            result = lookup.getDescLangFirst();
            break;
        }

        case MainetConstants.D2KFUNCTION.REG_DESC: {
            result = lookup.getDescLangSecond();
            break;
        }

        case MainetConstants.D2KFUNCTION.CPD_VALUE: {
            result = lookup.getLookUpCode();
            break;
        }

        case MainetConstants.D2KFUNCTION.CPD_OTHERVALUE: {
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

  /*  public static List<DepartmentLookUp> getDepartmentLookUp() {
        final List<DepartmentLookUp> departmentllokup = ApplicationSession.getInstance()
                .getDepartments(UserSession.getCurrent().getOrganisation());
        if ((departmentllokup != null) && !departmentllokup.isEmpty()) {
            Collections.sort(departmentllokup);
        }
        return departmentllokup;

    }*/

    /**
     * Get Department LookUp from Department ShortCode
     * @param deptCode
     * @return
     */
   /* public static DepartmentLookUp getDeptIdFromDeptCode(final String deptCode) {
        DepartmentLookUp departmentLookUp = null;

        DepartmentLookUp department = null;

        final List<DepartmentLookUp> departmentsLookUpList = getDepartmentLookUp();

        if ((departmentsLookUpList != null) && !departmentsLookUpList.isEmpty()) {

            final Iterator<DepartmentLookUp> departmentsLookUp = departmentsLookUpList.iterator();

            while (departmentsLookUp.hasNext()) {
                department = departmentsLookUp.next();

                if (department.getLookUpCode().equals(deptCode)) {
                    departmentLookUp = department;
                }
            }
        }
        return departmentLookUp;
    }*/

  /*  public static Long getIdFromPrefixLookUpDesc(final String valueDesc, final String prefix, final int langID) {

        final List<LookUp> lookuplist = getLookUps(prefix, UserSession.getCurrent().getOrganisation());

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
    }*/

   /* public static Long getIdFromPrefixLookUpDesc(final String valueDesc, final String prefix, final int langID,
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
*/
   /* public static LookUp getLookUpFromPrefixLookUpDesc(final String valueDesc, final String prefix, final int langID) {

        final List<LookUp> lookuplist = getLookUps(prefix, UserSession.getCurrent().getOrganisation());

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
    }*/

    public static LookUp getLookUpFromPrefixLookUpDesc(final String valueDesc, final String prefix, final int langID,
            final Organisation organisation) {
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

    public static LookUp getHierarchicalLookUp(final long lookUpId) {
        return ApplicationSession.getInstance().getHierarchicalLookUp(UserSession.getCurrent().getOrganisation(), lookUpId);

    }
    public static LookUp getHierarchicalLookUp(final long lookUpId, final Organisation org) {       
        return ApplicationSession.getInstance().getHierarchicalLookUp(
                org, lookUpId);
    }

   /* public static LookUp getHierarchicalLookUp(final long lookUpId, final Organisation organisation) {
        return ApplicationSession.getInstance().getHierarchicalLookUp(organisation, lookUpId);

    }*/

    public static LookUp getNonHierarchicalLookUpObject(final long lookUpId) {
        return ApplicationSession.getInstance().getNonHierarchicalLookUp(UserSession.getCurrent().getOrganisation().getOrgid(),
                lookUpId);
    }

    public static LookUp getNonHierarchicalLookUpObject(final long lookUpId, final Organisation organisation) {
        return ApplicationSession.getInstance().getNonHierarchicalLookUp(organisation.getOrgid(), lookUpId);
    }

    public static List<LookUp> getLookUps(final String lookUpCode, final Organisation organisation) {
        final List<LookUp> lookupList = ApplicationSession.getInstance().getNonHierarchicalLookUp(organisation, lookUpCode)
                .get(lookUpCode);
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("prefix not found for:[prefix -->" + lookUpCode
                    + " and Organization-->" + organisation.getONlsOrgname()
                    + MainetConstants.operator.LEFT_SQUARE_BRACKET);
        }
        return lookupList;
    }

    public static List<LookUp> getLevelData(final String prefixCode, final int level, final Organisation organisation) {
        final List<LookUp> lookupList = ApplicationSession.getInstance().getLookUpsByLevel(
                organisation.getOrgid(), prefixCode, level);
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("prefix not found for:[prefix -->" + prefixCode + "Level--> " + level
                    + " and Organization-->" + organisation.getONlsOrgname()
                    + MainetConstants.operator.LEFT_SQUARE_BRACKET);
        }

        return lookupList;
    }

    public static List<LookUp> getListLookup(final String lookUpCode, final Organisation organisation) {
        final List<LookUp> lookupList = ApplicationSession.getInstance()
                .getHierarchicalLookUp(organisation, lookUpCode)
                .get(lookUpCode);

        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("prefix not found for:[prefix -->" + lookUpCode
                    + " and Organization-->" + organisation.getONlsOrgname()
                    + MainetConstants.operator.LEFT_SQUARE_BRACKET);
        }

        return lookupList;

    }

    /**
     * @author umashanker.kanaujiya 04-Mar-2015
     */
  /*  public static List<LookUp> getHierarchicalForSubDetails(final Organisation organisation, final String lookUpCode,
            final long lookUpId) {
        return ApplicationSession.getInstance().getHierarchicalForSubDetails(organisation, lookUpCode, lookUpId);
    }

    *//**
     * @author umashanker.kanaujiya 04-Mar-2015
     *//*
    public static Map<String, List<LookUp>> getNonHierarchicalLookUpForWS(final Organisation organisation,
            final String lookUpCode) {

        return ApplicationSession.getInstance().getNonHierarchicalLookUp(organisation, lookUpCode);
    }

    *//**
     * @author umashanker.kanaujiya 04-Mar-2015
     *//*
    public static List<DepartmentLookUp> getDepartmentForWS(final Organisation organisation) {
        return ApplicationSession.getInstance().getDepartments(organisation);
    }
    
*/
    /*public static List<LookUp> getChildLookUpsFromParentId(final long parentId) {

        return ApplicationSession.getInstance().getChildLookUpsFromParentId(parentId);
    }*/
    
    public static List<LookUp> getSecondLevelData(final String prefixCode, final int level) {
        List<LookUp> lookupList = null;
        if (level <= 0) {
            lookupList = new ArrayList<>();
        }

        else {
            lookupList = ApplicationSession.getInstance().getLookUpsByLevel(
            		Utility.getOrgId(),
                    prefixCode, level);
        }
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("prefix not found for:[prefix -->" + prefixCode
                    + " and level-->" + level
                    + MainetConstants.operator.LEFT_SQUARE_BRACKET);
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
            throw new FrameworkException("LookUp Object not found for Prefix=" + prefix + " and orgId=" + orgID);
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

    public static List<LookUp> getLookUpList(final String lookUpCode) {
        final List<LookUp> lookupList = ApplicationSession.getInstance()
                .getHierarchicalLookUp(UserSession.getCurrent().getOrganisation(), lookUpCode).get(lookUpCode);
        if ((lookupList == null) || lookupList.isEmpty()) {
            logger.error("prefix not found for:[prefix -->" + lookUpCode + MainetConstants.operator.LEFT_SQUARE_BRACKET);
        }
        return lookupList;
    }

    public static List<LookUp> getLookupLabel(final String prefixCode) {
        final List<LookUp> lookUps = getLookUpList(prefixCode);
        if ((lookUps == null) || lookUps.isEmpty()) {
            logger.error("prefix not found for:[prefix -->" + prefixCode + MainetConstants.operator.LEFT_SQUARE_BRACKET);
        }
        return lookUps;
    }
    
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
            throw new FrameworkException("LookUp Object not found for Prefix=" + prefix + " and orgId=" + orgID);
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

    /**
     * use this method to cast single dataModel from request received
     * @param requestDTO
     * @param clazz
     * @return
     */
   /* @SuppressWarnings("unchecked")
    public static Object castRequestToDataModel(WSRequestDTO requestDTO, Class<?> clazz) {

        Object dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();

            String jsonString = new JSONObject(requestMap).toString();
            try {
                dataModel = new ObjectMapper().readValue(jsonString, clazz);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }

        }

        return dataModel;

    }
*/

public static LookUp getDefaultValueByOrg(final String prefix, final Organisation org) {
        LookUp lookUpVal = null;
        final List<LookUp> lookupList = getLookUps(prefix, org);
        if ((lookupList != null) && !lookupList.isEmpty()) {
            for (final LookUp lookUp : lookupList) {
                if (lookUp.getDefaultVal().equals(
                        MainetConstants.IsLookUp.STATUS.YES)) {
                    lookUpVal = lookUp;
                    break;
                }
            }
            if (lookUpVal == null) {
                logger.error("prefix found for:[prefix -->" + prefix
                        + "but not found for default value]");
            }
        } else {
            logger.error("prefix not found for:[prefix -->" + prefix + "]");
        }

        return lookUpVal;
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
            throw new FrameworkException("LookUp Object not found for Prefix=" + prefix + " and orgId=" + orgID);
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
    
    public static LookUp getLookUpFromPrefixLookUpValue(final String cpdValue,
            final String prefix, final int langID, final Organisation org) {

        final List<LookUp> lookuplist = getLookUps(prefix, org);


        LookUp lookUp = new LookUp();

        LookUp lookUp2 = null;
        if ((lookuplist != null) && !lookuplist.isEmpty()) {
            final Iterator<LookUp> lookup = lookuplist.iterator();
            while (lookup.hasNext()) {

                lookUp2 = lookup.next();

                if (lookUp2.getLookUpCode() != null && lookUp2.getLookUpCode().equals(cpdValue)) {
                    lookUp = lookUp2;

                    break;
                }
            }
        }
        return lookUp;
    }

	public static List<LookUp> lookUpListByPrefix(final String prefix, final Organisation organisation) {
        final List<LookUp> lookUps = getLookUps(prefix, organisation);
        if ((lookUps == null) || lookUps.isEmpty()) {
            throw new FrameworkException("NonHierarchicalLookUp --> Prefix " + prefix + " not configured for Organization Id=" + organisation.getOrgid());
        }
        return lookUps;
    }
}
