package com.abm.mainet.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DeptOrgMap;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.dto.TemplateLookUp;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IDepartmentService;
import com.abm.mainet.common.service.ILookUpService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.quartz.service.IQuartzSchedulerMasterService;

/**
 * @author Pranit.Mhatre
 * @since 02 September, 2013
 */
@Component
public final class ApplicationSession implements Serializable {
    private static final long serialVersionUID = 4480653666122100519L;

    @Autowired
    private ILookUpService iLookUpService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IQuartzSchedulerMasterService iQuartzSchedulerMasterService;

    private Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap = new LinkedHashMap<>(
            0);

    private Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap = new HashMap<>(
            0);

    private Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap = new HashMap<>(0);

    private List<String> hirachicalPrefix = new ArrayList<>(0);

    private List<String> nonHirachicalPrefix = new ArrayList<>(0);

    private List<String> nonReplicatePrefix = Collections.emptyList();

    private Map<Integer, List<LookUp>> childLookUpsWithParentMap = new HashMap<>(0);

    private List<Department> departmentList = new ArrayList<>();
    private List<DeptOrgMap> deptOrgList = new ArrayList<>();
    private Map<Object, Map<Object, List<DepartmentLookUp>>> departmentMap = new HashMap<>();
    private Map<Long, String> bankList = new HashMap<>();

    private Map<Long, String> customerBanks = new HashMap<>();
    /* private List<BankDetail> banks = new ArrayList<>(); */
    private Map<Long, List<String>> accessLinks = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(ApplicationSession.class);

    public Map<Long, List<String>> getAccessLinks() {
        return accessLinks;
    }

    public void setAccessLinks(final Map<Long, List<String>> accessLinks) {
        this.accessLinks = accessLinks;
    }

    private final List<ViewPrefixDetails> viewPrefixDetails = new ArrayList<>(0);

    private Map<String, Map<String, List<TemplateLookUp>>> templateLookup = new LinkedHashMap<>();

    private int langId;

    /**
     * Organization with default status 'Y'
     */
    private Organisation superUserOrganization;

    public static ApplicationSession getInstance() {
        return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
    }

    @PostConstruct
    public void init() {
        setSuperUserOrganization(iOrganisationService.getSuperUserOrganisation());
        loadPrefixData();

        setDepartmentList(iDepartmentService.getDepartments(MainetConstants.IsLookUp.ACTIVE));
        setDeptOrgList(iDepartmentService.getMappingWithOragnisation(MainetConstants.IsLookUp.ACTIVE));
        startQuartzScheduler();

    }

    @SuppressWarnings("unchecked")
    public void loadPrefixData() {
        {

            Map<String, Object> resultMap = iLookUpService.getHirachicalPrefixDetails();

            hirachicalDetailMap = (Map<Integer, Map<String, Map<Integer, List<LookUp>>>>) resultMap
                    .get(MainetConstants.LookUp.HIRACHICAL_DETAIL_MAP);

            hirachicalLevelMap = (Map<Integer, Map<String, Map<Long, LookUp>>>) resultMap
                    .get(MainetConstants.LookUp.HIRACHICAL_LEVEL_MAP);

            hirachicalPrefix = (List<String>) resultMap.get(MainetConstants.LookUp.HIRACHICAL_LIST);

            resultMap = iLookUpService.getNonHirachicalPrefixDetails();

            nonHirachicalDetailMap = (Map<Integer, Map<String, List<LookUp>>>) resultMap
                    .get(MainetConstants.LookUp.NON_HIRACHICAL_DETAIL_MAP);

            nonHirachicalPrefix = (List<String>) resultMap.get(MainetConstants.LookUp.NON_HIRACHICAL_LIST);

            nonReplicatePrefix = iLookUpService.getNonReplicatePrefix();

            loadChildLookUpsAndWithParent();
        }
    }

    private Map<Integer, List<LookUp>> levelMap = new HashMap<>();

    private List<LookUp> childLookUps = new ArrayList<>(0);

    private List<LookUp> lookUps = new ArrayList<>(0);

    private Map<String, List<LookUp>> subChildLookUpMap = new HashMap<>(0);

    private Map<String, List<LookUp>> nPrefixMap = new HashMap<>(0);

    private void loadChildLookUpsAndWithParent() {

        childLookUpsWithParentMap = new HashMap<>(0);

        levelMap = new HashMap<>();

        childLookUps = new ArrayList<>(0);

        for (final Integer orgid : hirachicalDetailMap.keySet()) {
            for (final String prefix : hirachicalPrefix) {
                levelMap = hirachicalDetailMap.get(orgid).get(prefix);

                if ((levelMap != null) && (levelMap.size() > 0)) {
                    for (final Integer level : levelMap.keySet()) {
                        if (level != 1) {
                            for (final LookUp lookUp : levelMap.get(level)) {
                                if (childLookUpsWithParentMap.containsKey((int) lookUp.getLookUpParentId())) {
                                    childLookUps = childLookUpsWithParentMap.get((int) lookUp.getLookUpParentId());
                                } else {
                                    childLookUps = new ArrayList<>(0);
                                }

                                childLookUps.add(lookUp);

                                childLookUpsWithParentMap.put((int) lookUp.getLookUpParentId(), childLookUps);
                            }
                        }
                    }
                }
            }
        }
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * This method prepare map for given look up code for Non-Hierarchical.
     * @param organisation the {@link Organisation} object containing current oraganization
     * @param lookUpCode the {@link String} literal containing look up code.
     * @return {@link Map} containing map key and {@link List} of {@link LookUp} objects.
     */
    public Map<String, List<LookUp>> getNonHierarchicalLookUp(Organisation organisation, final String lookUpCode) {

        if ((organisation == null) || (lookUpCode == null)) {
            return subChildLookUpMap;
        }

        if (nonReplicatePrefix.contains(lookUpCode)) {
            organisation = superUserOrganization;
        }

        subChildLookUpMap = new HashMap<>(0);// this.hasAlreadyExistsInMap(organisation, lookUpCode);

        if (nonHirachicalDetailMap.containsKey(organisation.getOrgid().intValue())
                && nonHirachicalDetailMap.get(organisation.getOrgid().intValue()).containsKey(lookUpCode)) {
            subChildLookUpMap.put(lookUpCode, nonHirachicalDetailMap.get(organisation.getOrgid().intValue()).get(lookUpCode));
        }

        return subChildLookUpMap;
    }

    public Map<String, List<LookUp>> getHierarchicalLookUp(Organisation organisation, final String lookUpCode) {

        if ((organisation == null) || (lookUpCode == null)) {
        	LOGGER.error("Returning subChildLookUpMap");
            return subChildLookUpMap;
        }

        if (nonReplicatePrefix.contains(lookUpCode)) {
            organisation = superUserOrganization;
        }

        if (nonHirachicalPrefix.contains(lookUpCode)) {
            return this.getNonHierarchicalLookUp(organisation, lookUpCode);
        }

        subChildLookUpMap = new HashMap<>(0);

        lookUps = new ArrayList<>();
        Integer orgId = Integer.valueOf(organisation.getOrgid().toString());
        LOGGER.info("Final orgId : "+orgId);
        if (hirachicalLevelMap.containsKey(orgId)
                && hirachicalLevelMap.get(orgId).containsKey(lookUpCode)) {
            for (final Long key : hirachicalLevelMap.get(orgId).get(lookUpCode).keySet()) {
                if (hirachicalLevelMap.get(orgId).get(lookUpCode).containsKey(key)) {
                    lookUps.add(hirachicalLevelMap.get(orgId).get(lookUpCode).get(key));
                }
            }

            subChildLookUpMap.put(lookUpCode, lookUps);
        }

        return subChildLookUpMap;
    }

    public Map<String, List<LookUp>> getHierarchicalForDetails(Organisation organisation, final String parentCode,
            final String childCode) {
        subChildLookUpMap = new HashMap<>(0);

        if ((organisation == null) || (parentCode == null) || (childCode == null)) {
            return subChildLookUpMap;
        }

        if (nonReplicatePrefix.contains(parentCode)) {
            organisation = superUserOrganization;
        }

        lookUps = new ArrayList<>(0);

        if (hirachicalLevelMap.containsKey(organisation.getOrgid())
                && hirachicalLevelMap.get(organisation.getOrgid()).containsKey(parentCode)) {
            for (final Long key : hirachicalLevelMap.get(organisation.getOrgid()).get(parentCode).keySet()) {
                if (hirachicalLevelMap.get(organisation.getOrgid()).get(parentCode).containsKey(key)
                        && hirachicalLevelMap.get(organisation.getOrgid()).get(parentCode).get(key).getLookUpCode()
                                .equals(childCode)) {
                    subChildLookUpMap.put(childCode,
                            hirachicalDetailMap.get(organisation.getOrgid()).get(parentCode).get(key.intValue()));
                }
            }
        }

        return subChildLookUpMap;
    }

    public List<LookUp> getHierarchicalForSubDetails(final Organisation organisation, final String parentCode,
            final long parentId) {
        return getChildLookUpsFromParentId(parentId);
    }

    public int getHierarchicalForSubDetails(final Organisation organisation, Long parentId) {
        lookUps = new ArrayList<>(0);

        if (childLookUpsWithParentMap.containsKey(parentId.intValue())) {
            lookUps = childLookUpsWithParentMap.get(parentId.intValue());

            if ((lookUps != null) && (lookUps.size() > 0)) {
                return lookUps.size();
            }
        }

        return 0;
    }

    public LookUp getNonHierarchicalLookUp(final long orgId, final Long lookUpId) {
        nPrefixMap = new HashMap<>(0);

        lookUps = new ArrayList<>(0);

        for (final Integer orgid : nonHirachicalDetailMap.keySet()) {
            nPrefixMap = nonHirachicalDetailMap.get(orgid);

            if ((nPrefixMap != null) && (nPrefixMap.size() > 0)) {
                for (final String prefix : nPrefixMap.keySet()) {
                    if (nPrefixMap.containsKey(prefix)) {
                        lookUps = nPrefixMap.get(prefix);
                        try {
                        for (final LookUp lookUp : lookUps) {
                            if (lookUp.getLookUpId() == lookUpId) {
                                return lookUp;
                            }
                         }
                        }catch(Exception e) {
                        	LOGGER.error("Prefix not found for Prefix Code= " +prefix,e);
                        }
                    }
                }
            }
        }

        return new LookUp();
    }

    public LookUp getHierarchicalLookUp(final Organisation organisation, final Long lookUpId) {
        levelMap = new HashMap<>();

        lookUps = new ArrayList<>(0);

        for (final Integer orgid : hirachicalDetailMap.keySet()) {
            for (final String prefix : hirachicalDetailMap.get(orgid).keySet()) {
                levelMap = hirachicalDetailMap.get(orgid).get(prefix);

                if ((levelMap != null) && (levelMap.size() > 0)) {
                    for (final Integer level : levelMap.keySet()) {
                        lookUps = levelMap.get(level);

                        if ((lookUps != null) && (lookUps.size() > 0)) {
                            for (final LookUp lookUp : lookUps) {

                                if (lookUp.getLookUpId() == lookUpId) {
                                    return lookUp;
                                }
                            }
                        }
                    }
                }
            }
        }

        return new LookUp();
    }

    public String getMessage(final String code) {
        return getMessage(code, code);
    }

    public String getMessage(final String code, final Object... args) {
        return getMessage(code, code, args);
    }

    public String getMessage(final String code, final String defaultMessage) {
        return getMessage(code, defaultMessage, (Object[]) null);
    }

    public String getMessage(final String code, final String defaultMessage, final Object... args) {
        return getMessage(code, defaultMessage, LocaleContextHolder.getLocale(), args);
    }

    public String getMessage(final String code, final String defaultMessage, final Locale locale, final Object... args) {
        return ApplicationContextProvider.getApplicationContext().getMessage(code, args, defaultMessage, locale);
    }

    /**
     * @return the superUserOrganization
     */
    public Organisation getSuperUserOrganization() {
        return superUserOrganization;
    }

    /**
     * @param superUserOrganization the superUserOrganization to set
     */
    public void setSuperUserOrganization(final Organisation superUserOrganization) {
        this.superUserOrganization = superUserOrganization;
    }

    /**
     * @return the departmentList
     */
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    /**
     * @param departmentList the departmentList to set
     */
    public void setDepartmentList(final List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    /**
     * @return the deptOrgList
     */
    public List<DeptOrgMap> getDeptOrgList() {
        return deptOrgList;
    }

    /**
     * @param deptOrgList the deptOrgList to set
     */
    public void setDeptOrgList(final List<DeptOrgMap> deptOrgList) {
        this.deptOrgList = deptOrgList;
    }

    /**
     * @return the departmentMap
     */
    public Map<Object, Map<Object, List<DepartmentLookUp>>> getDepartmentMap() {
        return departmentMap;
    }

    /**
     * @param departmentMap the departmentMap to set
     */
    public void setDepartmentMap(final Map<Object, Map<Object, List<DepartmentLookUp>>> departmentMap) {
        this.departmentMap = departmentMap;
    }

    /**
     * To get the list of {@link DepartmentLookUp} object for the given {@link Organisation}.
     * @param organisation the {@link Organisation} object.
     * @return {@link List} of {@link DepartmentLookUp} object.
     */
    public List<DepartmentLookUp> getDepartments(final Organisation organisation) {
        Map<Object, List<DepartmentLookUp>> map = getDepartmentMap().get(organisation);

        List<DepartmentLookUp> lookUps = isDepartmentCached(organisation);

        if ((lookUps != null) && (lookUps.size() > 0)) {
            return lookUps;
        }

        lookUps = new ArrayList<>();

        final ListIterator<Department> mapListIterator = getDepartmentList().listIterator();

        Department department = null;
        DepartmentLookUp lookUp = null;
        while (mapListIterator.hasNext()) {
            department = mapListIterator.next();
            try {

                lookUp = new DepartmentLookUp();
                lookUp.setLookUpId(department.getDpDeptid());
                lookUp.setLookUpCode(department.getDpDeptcode());
                lookUp.setDescLangSecond(department.getDpNameMar());
                lookUp.setDescLangFirst(department.getDpDeptdesc());

                lookUps.add(lookUp);

            } catch (final FrameworkException ex) {
            }

        }

        if (map == null) {
            map = new HashMap<>();
        }

        map.put(MainetConstants.MENU.ALL, lookUps);

        getDepartmentMap().put(organisation, map);

        return lookUps;
    }

    /**
     * To get list of {@link DepartmentLookUp} object for given module name and {@link Organisation}.
     * @param moduleName the {@link String} literal containing module name.
     * @param organisation the {@link Organisation} object.
     * @return {@link List} of {@link DepartmentLookUp} object.
     */
    public List<DepartmentLookUp> getDepartments(final String moduleName, final Organisation organisation) {

        final Map<Object, List<DepartmentLookUp>> map = getDepartmentMap().get(moduleName);

        List<DepartmentLookUp> lookUps = isDepartmentCached(moduleName, organisation);

        if ((lookUps != null) && (lookUps.size() > 0)) {
            return lookUps;
        } else {
            lookUps = new ArrayList<>();

            final ListIterator<DeptOrgMap> mapListIterator = getDeptOrgList().listIterator();

            DeptOrgMap deptOrgMap = null;

            Department department = null;

            DepartmentLookUp lookUp = null;

            while (mapListIterator.hasNext()) {
                deptOrgMap = mapListIterator.next();

                if (deptOrgMap.getOrgid() == organisation.getOrgid()) {
                    try {
                        department = findDepartment(deptOrgMap.getDepartment().getDpDeptid());

                        lookUp = new DepartmentLookUp();

                        lookUp.setLookUpId(department.getDpDeptid());
                        lookUp.setLookUpCode(department.getDpDeptcode());
                        lookUp.setDescLangSecond(department.getDpNameMar());
                        lookUp.setDescLangFirst(department.getDpDeptdesc());
                        lookUps.add(lookUp);

                    } catch (final FrameworkException ex) {
                        throw ex;
                    }

                }

            }

            map.put(moduleName, lookUps);

            getDepartmentMap().put(organisation, map);

            return lookUps;
        }
    }

    /**
     * To get {@link Department} object for given department id.
     * @param deptId the long containing department id.
     * @param organisation the {@link Organisation} object.
     * @return {@link DepartmentLookUp} object containing department data.
     */
    public DepartmentLookUp getDepartment(final long deptId, final Organisation organisation) {
        final List<DepartmentLookUp> lookUps = getDepartments(organisation);

        final ListIterator<DepartmentLookUp> listIterator = lookUps.listIterator();
        DepartmentLookUp departmentLookUp = null;
        while (listIterator.hasNext()) {
            departmentLookUp = listIterator.next();

            if (departmentLookUp.getLookUpId() == deptId) {
                return departmentLookUp;
            }

        }

        return new DepartmentLookUp();

    }

    /**
     * To get {@link Department} object from list of {@link Department} object.
     * @param deptId the long literal containing department identifier.
     * @return {@link Department} object for given identifier otherwise <code>null</code>
     * @throws FrameworkException if no record found.
     */
    private Department findDepartment(final long deptId) throws FrameworkException {
        final ListIterator<Department> listIterator = getDepartmentList().listIterator();

        Department department = null;
        Department clone = null;
        while (listIterator.hasNext()) {
            department = listIterator.next();

            if (department.getDpDeptid() == deptId) {
                try {
                    clone = (Department) department.clone();

                    if (clone == null) {
                        continue;
                    }

                    return clone;
                } catch (final CloneNotSupportedException ex) {

                    throw new FrameworkException(ex);
                }
            }

        }

        throw new FrameworkException("No department available with dept id :" + deptId);
    }

    /**
     * To check whether for given {@link Organisation} {@link DepartmentLookUp} already stored in cache or not.
     * @param organisation the {@link Organisation} object.
     * @return {@link List} of {@link DepartmentLookUp} if cached otherwise <code>null</code>.
     */
    private List<DepartmentLookUp> isDepartmentCached(final Organisation organisation) {
        final Map<Object, List<DepartmentLookUp>> map = getDepartmentMap().get(organisation);

        if (map != null) {
            if (map != null) {
                if (map.containsKey(MainetConstants.MENU.ALL)) {
                    return map.get(MainetConstants.MENU.ALL);
                }
            }
        }

        return null;
    }

    /**
     * To check whether for given {@link Organisation} and modeul name {@link DepartmentLookUp} * already stored in cache or not.
     * @param moduleName the {@link String} containing module name.
     * @param organisation the {@link Organisation} object.
     * @return {@link List} of {@link DepartmentLookUp} if cached otherwise <code>null</code>.
     */
    private List<DepartmentLookUp> isDepartmentCached(final String moduleName, final Organisation organisation) {
        final Map<Object, List<DepartmentLookUp>> map = getDepartmentMap().get(organisation);

        if (map != null) {
            if (map != null) {
                if (map.containsKey(moduleName)) {
                    return map.get(moduleName);
                }
            }
        }

        return null;
    }

    public Map<Long, String> getBankList() {
        return bankList;
    }

    public void setBankList(final Map<Long, String> bankList) {
        this.bankList = bankList;
    }

    public Map<Long, String> getCustomerBanks() {
        return customerBanks;
    }

    /*
     * public void setCustomerBanks(final List<BankDetail> customerBanks) { final Map<Long, String> banksMap = new HashMap<>();
     * for (final BankDetail bankDetail : customerBanks) { final String bankAndBranchName = bankDetail.getCbbankname() + " :: " +
     * bankDetail.getCbbranchname(); banksMap.put(bankDetail.getCbbankid(), bankAndBranchName); } this.customerBanks = banksMap; }
     */

    public Map<String, Map<String, List<TemplateLookUp>>> getTemplateLookup() {
        return templateLookup;
    }

    public void setTemplateLookup(
            final Map<String, Map<String, List<TemplateLookUp>>> templateLookup) {
        this.templateLookup = templateLookup;
    }

    /*
     * public List<BankDetail> getBanks() { return banks; } public void setBanks(final List<BankDetail> banks) { this.banks =
     * banks; }
     */
    public List<LookUp> getChildLookUpsFromParentId(final long parentId) {
        if (childLookUpsWithParentMap.containsKey((int) parentId)) {
            return childLookUpsWithParentMap.get((int) parentId);
        }

        return new ArrayList<>(0);
    }

    public List<ViewPrefixDetails> getViewPrefixDetails() {
        return viewPrefixDetails;
    }

    public Map<Integer, Map<String, Map<Long, LookUp>>> getHirachicalLevelMap() {
        return hirachicalLevelMap;
    }

    public Map<Integer, Map<String, Map<Integer, List<LookUp>>>> getHirachicalDetailMap() {
        return hirachicalDetailMap;
    }

    public Map<Integer, Map<String, List<LookUp>>> getNonHirachicalDetailMap() {
        return nonHirachicalDetailMap;
    }

    public List<String> getHirachicalPrefix() {
        return hirachicalPrefix;
    }

    public List<String> getNonHirachicalPrefix() {
        return nonHirachicalPrefix;
    }

    public List<LookUp> getLookUpsByLevel(long orgid, final String prefixCode, final int level) {

        if (nonReplicatePrefix.contains(prefixCode)) {
            orgid = superUserOrganization.getOrgid();
        }

        if (hirachicalDetailMap.containsKey((int) orgid)) {
            if (hirachicalDetailMap.get((int) orgid).containsKey(prefixCode)) {
                if (hirachicalDetailMap.get((int) orgid).get(prefixCode).containsKey(level)) {
                    return hirachicalDetailMap.get((int) orgid).get(prefixCode).get(level);
                }
            }
        }

        return new ArrayList<>(0);
    }

    /**
     * Quartz Scheduler being start here.
     */
    private void startQuartzScheduler() {

        try {
            iQuartzSchedulerMasterService.invokeQuartzShceduler();
        } catch (ClassNotFoundException | RuntimeException | SchedulerException
                | LinkageError e) {

            LOGGER.error("*** Error Occured while Quartz Scheduler execution: ", e);
        }
    }
    
}
