package com.abm.mainet.common.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import com.abm.mainet.bill.dto.PropertyBillGenerationMap;
import com.abm.mainet.bill.dto.WaterBillGenerationMap;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DeptOrgMap;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILookUpService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IResourceService;
import com.abm.mainet.quartz.service.IQuartzSchedulerMasterService;
import com.ibm.icu.util.Calendar;

/**
 * @author Pranit.Mhatre
 * @since 02 September, 2013
 */
@Component
public final class ApplicationSession implements Serializable {
    private static final long serialVersionUID = 4480653666122100519L;

    private static final Logger LOGGER = Logger.getLogger(ApplicationSession.class);

    @Autowired
    private ILookUpService iLookUpService;
    
    @Autowired
    private IResourceService iResourceService;


    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private BankMasterService bankMasterService;

    @Autowired
    private IQuartzSchedulerMasterService iQuartzSchedulerMasterService;

    private static Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap = new ConcurrentHashMap<>(
            0);

    private static Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap = new ConcurrentHashMap<>(
            0);

    private static Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap = new ConcurrentHashMap<>(0);
    
    private static Map<String, Map<String, Resource>> resourceDetailsMap;

    private List<String> hirachicalPrefix = Collections.synchronizedList(new ArrayList<>(0));

    private List<String> nonHirachicalPrefix = Collections.synchronizedList(new ArrayList<>(0));

    private List<String> nonReplicatePrefix = Collections.synchronizedList(new ArrayList<>(0));

    private Map<Integer, List<LookUp>> childLookUpsWithParentMap = new ConcurrentHashMap<>(0);

    private List<Department> departmentList = new ArrayList<>();
    private List<DeptOrgMap> deptOrgList = new ArrayList<>();
    private Map<Object, Map<Object, List<DepartmentLookUp>>> departmentMap = new ConcurrentHashMap<>();
    private Map<Long, String> bankList = new ConcurrentHashMap<>();

    private Map<Long, String> customerBanks = new ConcurrentHashMap<>();
    private List<BankMasterEntity> banks = new ArrayList<>();
    private Map<Long, List<String>> accessLinks = new ConcurrentHashMap<>();
    private Map<Long, LookUp> nonHirachicalPrefixDetails = new ConcurrentHashMap<>();
    private Map<Long, LookUp> hirachicalPrefixDetails = new ConcurrentHashMap<>();

    private PropertyBillGenerationMap propertyBillGenerationMap = new PropertyBillGenerationMap();
    
	private Map<Long, PropertyBillGenerationMap> propBillGenerationMapOrgId = new HashMap<Long, PropertyBillGenerationMap>();
    
    private Map<Long, WaterBillGenerationMap> waterBillGenerationMapOrgId = new HashMap<Long, WaterBillGenerationMap>();
    
    private WaterBillGenerationMap waterBillGenerationMap = new WaterBillGenerationMap();

    public Map<Long, List<String>> getAccessLinks() {
        return accessLinks;
    }

    public void setAccessLinks(final Map<Long, List<String>> accessLinks) {
        this.accessLinks = accessLinks;
    }

    private final List<ViewPrefixDetails> viewPrefixDetails = new ArrayList<>(0);

    private Map<String, Map<String, List<TemplateLookUp>>> templateLookup = new ConcurrentHashMap<>();

    private int langId;

    /**
     * Organization with default status 'Y'
     */
    private Organisation superUserOrganization;
    
    private String uniqueKeyId;

    public static ApplicationSession getInstance() {
        return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
    }

    @PostConstruct
    public void init() {
	
	LOGGER.info("ApplicationSession- > init(), Start Time -> " + Calendar.getInstance().getTime());
        setSuperUserOrganization(iOrganisationService.getSuperUserOrganisation());
        loadPrefixData();
        setDepartmentList(departmentService.getDepartments(PrefixConstants.IsLookUp.ACTIVE));
        setDeptOrgList(departmentService.getMappingWithOragnisation(PrefixConstants.IsLookUp.ACTIVE));
        setBanks(bankMasterService.getBankList());
        setCustomerBanks(getBanks());
        loadResourceData();

	LOGGER.info("ApplicationSession- > init(), End Time -> " + Calendar.getInstance().getTime());
        startQuartzScheduler();
	LOGGER.info("ApplicationSession- > init(), End Time startQuartzScheduler -> " + Calendar.getInstance().getTime());

    }

    @SuppressWarnings("unchecked")
    private void loadPrefixData() {

	LOGGER.info("ApplicationSession- > loadPrefixData(), Start Time -> " + Calendar.getInstance().getTime());
		
        Map<String, Object> resultMap = iLookUpService.getHirachicalPrefixDetails();

        hirachicalDetailMap = (Map<Integer, Map<String, Map<Integer, List<LookUp>>>>) resultMap
                .get(PrefixConstants.LookUp.HIRACHICAL_DETAIL_MAP);

        hirachicalLevelMap = (Map<Integer, Map<String, Map<Long, LookUp>>>) resultMap
                .get(PrefixConstants.LookUp.HIRACHICAL_LEVEL_MAP);

        hirachicalPrefix = (List<String>) resultMap.get(PrefixConstants.LookUp.HIRACHICAL_LIST);

        resultMap = iLookUpService.getNonHirachicalPrefixDetails();

        nonHirachicalDetailMap = (Map<Integer, Map<String, List<LookUp>>>) resultMap
                .get(PrefixConstants.LookUp.NON_HIRACHICAL_DETAIL_MAP);

        nonHirachicalPrefix = (List<String>) resultMap.get(PrefixConstants.LookUp.NON_HIRACHICAL_LIST);

        nonReplicatePrefix = iLookUpService.getNonReplicatePrefix();

        /*
         * // Rajesh nonHirachicalPrefixDetails = iLookUpService.getNonHirachicalPrefixDetailsByLookupID(); // Rajesh
         * hirachicalPrefixDetails = iLookUpService.getHirachicalPrefixDetailsByLookupID();
         */

        LOGGER.info("ApplicationSession- > loadPrefixData(), End Time -> " + Calendar.getInstance().getTime());
        loadChildLookUpsAndWithParent();
    }
	//#35090
    @SuppressWarnings("unchecked")
    private void loadResourceData() {
    	LOGGER.info("ApplicationSession- > loadResourceData(), Start Time -> " + Calendar.getInstance().getTime());
    	resourceDetailsMap = iResourceService.getAllResourceDtls();
        LOGGER.info("ApplicationSession- > loadResourceData(), End Time -> " + Calendar.getInstance().getTime());

    }

    private void loadChildLookUpsAndWithParent() {
	LOGGER.info("ApplicationSession- > loadChildLookUpsAndWithParent(), Start Time -> " + Calendar.getInstance().getTime());
        childLookUpsWithParentMap = new ConcurrentHashMap<>(0);

        for (final Integer orgid : hirachicalDetailMap.keySet()) {
            for (final String prefix : hirachicalPrefix) {
                Map<Integer, List<LookUp>> levelMap = hirachicalDetailMap.get(orgid).get(prefix);

                if ((levelMap != null) && (levelMap.size() > 0)) {
                    for (final Integer level : levelMap.keySet()) {
                        if (level != 1) {
                            for (final LookUp lookUp : levelMap.get(level)) {
                        	List<LookUp> childLookUps = Collections.synchronizedList(new ArrayList<>(0));
                                if (childLookUpsWithParentMap.containsKey((int) lookUp.getLookUpParentId())) {
                                    childLookUps = childLookUpsWithParentMap.get((int) lookUp.getLookUpParentId());
                                } 
                                

                                childLookUps.add(lookUp);

                                childLookUpsWithParentMap.put((int) lookUp.getLookUpParentId(), childLookUps);
                            }
                        }
                    }
                }
            }
        }
        
        LOGGER.info("ApplicationSession- > loadChildLookUpsAndWithParent(), End Time -> " + Calendar.getInstance().getTime());
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
    /*
     * public Map<String, List<LookUp>> getNonHierarchicalLookUp(Organisation organisation, final String lookUpCode) { if
     * ((organisation == null) || (lookUpCode == null)) { return subChildLookUpMap; } if (nonReplicatePrefix.contains(lookUpCode))
     * { organisation = superUserOrganization; } subChildLookUpMap = new ConcurrentHashMap<>(0); if
     * (nonHirachicalDetailMap.containsKey((int) organisation.getOrgid()) && nonHirachicalDetailMap.get((int)
     * organisation.getOrgid()).containsKey(lookUpCode)) { subChildLookUpMap.put(lookUpCode, nonHirachicalDetailMap.get((int)
     * organisation.getOrgid()).get(lookUpCode)); } else { LOGGER.info("No Prefix Configure in Prefix Master for Prifix =" +
     * lookUpCode); } return subChildLookUpMap; }
     */

    public Map<String, List<LookUp>> getNonHierarchicalLookUp(Long orgId, final String lookUpCode) {
	Map<String, List<LookUp>> subChildLookUpMap = new ConcurrentHashMap<>(0);
        if ((orgId == null) || (lookUpCode == null)) {
            return subChildLookUpMap;
        }

        if (nonReplicatePrefix.contains(lookUpCode)) {
            orgId = superUserOrganization.getOrgid();
        }

        subChildLookUpMap = new ConcurrentHashMap<>(0);

        if (nonHirachicalDetailMap.containsKey(orgId.intValue())
                && nonHirachicalDetailMap.get(orgId.intValue()).containsKey(lookUpCode)) {
            subChildLookUpMap.put(lookUpCode, nonHirachicalDetailMap.get(orgId.intValue()).get(lookUpCode));
        }else {

        	LOGGER.error("getNonHierarchicalLookUp - >No Prefix Configure in Prefix Master for Prifix =" + lookUpCode);
            throw new FrameworkException("getNonHierarchicalLookUp- > No Prefix Configure in Prefix Master for Prifix =" + lookUpCode);

        }

        return subChildLookUpMap;
    }

    public Map<String, List<LookUp>> getHierarchicalLookUp(Organisation organisation, final String lookUpCode) {
	Map<String, List<LookUp>> subChildLookUpMap = new ConcurrentHashMap<>(0);
        if ((organisation == null) || (lookUpCode == null)) {
            return subChildLookUpMap;
        }

        if (nonReplicatePrefix.contains(lookUpCode)) {
            organisation = superUserOrganization;
        }

        if (nonHirachicalPrefix.contains(lookUpCode)) {
            return this.getNonHierarchicalLookUp(organisation.getOrgid(), lookUpCode);
        }

        subChildLookUpMap = new ConcurrentHashMap<>(0);

        List<LookUp> lookUps = Collections.synchronizedList(new ArrayList<>(0));

        if (hirachicalLevelMap.containsKey((int) organisation.getOrgid())
                && hirachicalLevelMap.get((int) organisation.getOrgid()).containsKey(lookUpCode)) {
            for (final Long key : hirachicalLevelMap.get((int) organisation.getOrgid()).get(lookUpCode).keySet()) {
                if (hirachicalLevelMap.get((int) organisation.getOrgid()).get(lookUpCode).containsKey(key)) {
                    lookUps.add(hirachicalLevelMap.get((int) organisation.getOrgid()).get(lookUpCode).get(key));
                } else {
                    LOGGER.warn("No Prefix Configure in Prefix Master for Prifix =" + lookUpCode);
                }
            }

            subChildLookUpMap.put(lookUpCode, lookUps);
        }

        return subChildLookUpMap;
    }

    public Map<String, List<LookUp>> getHierarchicalForDetails(Organisation organisation, final String parentCode,
            final String childCode) {
	Map<String, List<LookUp>> subChildLookUpMap = new ConcurrentHashMap<>(0);

        if ((organisation == null) || (parentCode == null) || (childCode == null)) {
            return subChildLookUpMap;
        }

        if (nonReplicatePrefix.contains(parentCode)) {
            organisation = superUserOrganization;
        }

        if (hirachicalLevelMap.containsKey((int) organisation.getOrgid())
                && hirachicalLevelMap.get((int) organisation.getOrgid()).containsKey(parentCode)) {
            for (final Long key : hirachicalLevelMap.get((int) organisation.getOrgid()).get(parentCode).keySet()) {
                if (hirachicalLevelMap.get((int) organisation.getOrgid()).get(parentCode).containsKey(key)
                        && hirachicalLevelMap.get((int) organisation.getOrgid()).get(parentCode).get(key).getLookUpCode()
                                .equals(childCode)) {
                    subChildLookUpMap.put(childCode,
                            hirachicalDetailMap.get((int) organisation.getOrgid()).get(parentCode).get(key.intValue()));
                }
            }
        }

        return subChildLookUpMap;
    }

    public List<LookUp> getHierarchicalForSubDetails(final Organisation organisation, final String parentCode,
            final long parentId) {
        return getChildLookUpsFromParentId(parentId);
    }

    public List<LookUp> getChildsByOrgPrefixTopParentLevel(int orgId, String prefix, long top, long level) {
        // get level map for organization and prefix code
        Map<Integer, List<LookUp>> levelMap = hirachicalDetailMap.get(orgId).get(prefix);
        List<LookUp> retVal, topLevel;
        retVal = new ArrayList<LookUp>();
        LookUp topLookUp = null;
        // get top level of the given prefix
        topLevel = levelMap.get(1);

        // validate whether top is at top level
        for (LookUp lookUp : topLevel) {
            if (lookUp.getLookUpId() == top) {
                topLookUp = lookUp;
                break;
            }
        }
        // if top not at top level throw error
        if (topLookUp == null) {
            LOGGER.warn("prefix not found for:[prefix -->" + prefix
                    + "level not found for:[level -->" + level
                    + " and Organization-->" + orgId
                    + "]");
        }
        retVal.add(topLookUp);
        return getImmediateChilds(orgId, prefix, retVal, Long.valueOf(level).intValue(), 1);
    }

    private List<LookUp> getImmediateChilds(int orgId, String prefix, List<LookUp> siblings, int finalLevel, int curLevel) {
        System.out.println("getImmediateChilds - curLevel - " + curLevel);
        System.out.println("getImmediateChilds - finalLevel - " + finalLevel);
        Map<Integer, List<LookUp>> levelMap = hirachicalDetailMap.get(orgId).get(prefix);
        List<LookUp> retVal = new ArrayList<LookUp>(), topLevel, searchLevel;
        // for(int i=curLevel+1;i<=finalLevel;i++) {
        // get top level of the given prefix
        topLevel = levelMap.get(curLevel + 1);
        if (topLevel == null) {
            return siblings;
        }
        for (LookUp lookUp : topLevel) {
            for (LookUp childLookUp : siblings) {
                if (lookUp.getLookUpParentId() == childLookUp.getLookUpId()) {
                    retVal.add(lookUp);
                }
            }
        }
        if (curLevel + 1 > finalLevel) {
            return retVal;
        }
        retVal = getImmediateChilds(orgId, prefix, retVal, finalLevel, curLevel + 1);
        // }
        return retVal;
    }

    public int getHierarchicalForSubDetails(final Organisation organisation, final long parentId) {
	List<LookUp> lookUps =null;

        if (childLookUpsWithParentMap.containsKey(parentId)) {
            lookUps = childLookUpsWithParentMap.get(parentId);

            if ((lookUps != null) && (lookUps.size() > 0)) {
                return lookUps.size();
            }
        }

        return 0;
    }

    public LookUp getNonHierarchicalLookUp(final long orgId, final Long lookUpId) {
	Map<String, List<LookUp>> nPrefixMap = new ConcurrentHashMap<>(0);
        LookUp result = null;
        List<LookUp> lookUps = null;

        for (final Integer orgid : nonHirachicalDetailMap.keySet()) {
            nPrefixMap = nonHirachicalDetailMap.get(orgid);

            if ((nPrefixMap != null) && (nPrefixMap.size() > 0)) {
                for (final String prefix : nPrefixMap.keySet()) {
                    if (nPrefixMap.containsKey(prefix)) {
                        lookUps = nPrefixMap.get(prefix);

                        for (final LookUp lookUp : lookUps) {

                            if (lookUp.getLookUpId() == lookUpId) {
                                result = lookUp;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (result == null) {
            LOGGER.warn("prefix not found for:[prefix -->" + lookUpId
                    + " and Organization-->" + orgId
                    + "]");
            result = new LookUp();
        }
        return result;
    }

    public LookUp getNonHierarchicalLookUpByPrefix(final long orgId, final Long lookUpId, final String prefixName) {

        final List<LookUp> lookuplist = ApplicationSession.getInstance()
                .getNonHierarchicalLookUp(orgId, prefixName)
                .get(prefixName);
        LookUp lookUp = null;
        if ((lookuplist != null) && !lookuplist.isEmpty()) {
            final Iterator<LookUp> lookup = lookuplist.iterator();
            while (lookup.hasNext()) {
                final LookUp lookUp2 = lookup.next();
                if (lookUpId == lookUp2.getLookUpId()) {
                    lookUp = lookUp2;
                    break;
                }
            }
        }
        if (lookUp == null) {
            LOGGER.warn("prefix not found for:[prefix -->" + prefixName
                    + "lookUpId not found for:[lookUpId -->" + lookUpId
                    + " and Organization-->" + orgId
                    + "]");

        }
        return lookUp;
    }

    public LookUp getHierarchicalLookUp(final Organisation organisation, final Long lookUpId) {
        
        for (final Integer orgid : hirachicalDetailMap.keySet()) {
            for (final String prefix : hirachicalDetailMap.get(orgid).keySet()) {
                Map<Integer, List<LookUp>>  levelMap = hirachicalDetailMap.get(orgid).get(prefix);

                if ((levelMap != null) && (levelMap.size() > 0)) {
                    for (final Integer level : levelMap.keySet()) {
                        List<LookUp> lookUps = levelMap.get(level);

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
        LOGGER.warn("prefix not found for:[prefix -->" + lookUpId
                + " and Organization-->" + organisation.getOrgid()
                + "]");
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

        lookUps = Collections.synchronizedList(new ArrayList<>(0));
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
            map = new ConcurrentHashMap<>();
        }

        map.put(MainetConstants.ALL, lookUps);

        getDepartmentMap().put(organisation, map);

        return lookUps;
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
     * To check whether for given {@link Organisation} {@link DepartmentLookUp} already stored in cache or not.
     * @param organisation the {@link Organisation} object.
     * @return {@link List} of {@link DepartmentLookUp} if cached otherwise <code>null</code>.
     */
    private List<DepartmentLookUp> isDepartmentCached(final Organisation organisation) {
        final Map<Object, List<DepartmentLookUp>> map = getDepartmentMap().get(organisation);

        if (map != null) {
            if (map != null) {
                if (map.containsKey(MainetConstants.ALL)) {
                    return map.get(MainetConstants.ALL);
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

    public void setCustomerBanks(final List<BankMasterEntity> customerBanks) {

        final Map<Long, String> banksMap = new ConcurrentHashMap<>();

        for (final BankMasterEntity bankDetail : customerBanks) {

            final String bankAndBranchName = bankDetail.getBank() + " :: " + bankDetail.getBranch();
            banksMap.put(bankDetail.getBankId(), bankAndBranchName);

        }

        this.customerBanks = banksMap;
    }

    public Map<String, Map<String, List<TemplateLookUp>>> getTemplateLookup() {
        return templateLookup;
    }

    public void setTemplateLookup(
            final Map<String, Map<String, List<TemplateLookUp>>> templateLookup) {
        this.templateLookup = templateLookup;
    }

    public TemplateLookUp getTemplateLookUp(final String deptCode, final String menuURL) {
        final TemplateLookUp lookup = null;
        getTemplateLookup();
        if ((deptCode != null) && !deptCode.isEmpty()) {

        }

        return lookup;

    }

    public List<BankMasterEntity> getBanks() {
        return banks;
    }

    public void setBanks(final List<BankMasterEntity> banks) {
        this.banks = banks;
    }

    public List<LookUp> getChildLookUpsFromParentId(final long parentId) {
        if (childLookUpsWithParentMap.containsKey((int) parentId)) {
            return childLookUpsWithParentMap.get((int) parentId);
        }

        return Collections.synchronizedList(new ArrayList<>(0));
    }

    public List<LookUp> getChildLookUpsFromParentIdForLevel(final long parentId, final long level1) {
        int parentLevel = 0;

        Map<Integer, List<LookUp>> levelMap = new ConcurrentHashMap<>();

        List<LookUp> lookUps = null;

        for (final Integer orgid : hirachicalDetailMap.keySet()) {
            for (final String prefix : hirachicalDetailMap.get(orgid).keySet()) {
                levelMap = hirachicalDetailMap.get(orgid).get(prefix);

                if ((levelMap != null) && (levelMap.size() > 0)) {
                    for (final Integer level : levelMap.keySet()) {
                        lookUps = levelMap.get(level);

                        if ((lookUps != null) && (lookUps.size() > 0)) {
                            for (final LookUp lookUp : lookUps) {

                                if (lookUp.getLookUpId() == parentId) {
                                    parentLevel = level;
                                }
                            }
                        }
                    }
                }
            }
        }

        List<LookUp> list = null;
        final List<LookUp> list1 = Collections.synchronizedList(new ArrayList<>(0));

        int i = parentLevel + 1;

        if ((parentLevel != 0) && (parentLevel < level1)) {
            if (childLookUpsWithParentMap.containsKey((int) parentId)) {
                while ((parentLevel + 1) <= level1) {
                    if (i == (parentLevel + 1)) {
                        list = childLookUpsWithParentMap.get((int) parentId);
                    } else {
                        list = list1;
                    }

                    if ((list != null) && (list.size() > 0)) {
                        for (final LookUp lookUp : list) {

                            list1.addAll(childLookUpsWithParentMap.get((int) lookUp.getLookUpId()));

                        }
                        i++;
                    }

                    if (i == level1) {
                        return list1;
                    }
                }
            }
        }

        return Collections.synchronizedList(new ArrayList<>(0));
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

        return Collections.synchronizedList(new ArrayList<>(0));
    }

    /**
     * Quartz Scheduler being start here.
     */
    private void startQuartzScheduler() {

        try {
            iQuartzSchedulerMasterService.invokeQuartzShceduler();
        } catch (ClassNotFoundException | RuntimeException | SchedulerException | LinkageError e) {

            LOGGER.error("*** Error Occured while Quartz Scheduler execution: ", e);
        }

    }

    /**
     * This method is for checking whether replicate flag is 'Y' or 'N' for given prefix
     * @param prefix
     * @return
     */
    public boolean checkForReplicateFlag(final String prefix) {
        boolean flag = false;

        if (nonReplicatePrefix.contains(prefix)) {
            flag = true;
        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    public synchronized void updatePrefixCache(String prefixType) {
	LOGGER.info("ApplicationSession- > updatePrefixCache(),prefixType->"+prefixType+", Start Time -> " + Calendar.getInstance().getTime());


        Map<String, Object> resultMap = null;

        if (prefixType.equals("H")) {
            /*Defect #30784
             * hirachicalDetailMap.clear();
            hirachicalLevelMap.clear();
            hirachicalPrefix.clear();
            childLookUpsWithParentMap.clear();*/
            resultMap = iLookUpService.getHirachicalPrefixDetails();
            hirachicalDetailMap = (Map<Integer, Map<String, Map<Integer, List<LookUp>>>>) resultMap
                    .get(PrefixConstants.LookUp.HIRACHICAL_DETAIL_MAP);
            hirachicalLevelMap = (Map<Integer, Map<String, Map<Long, LookUp>>>) resultMap
                    .get(PrefixConstants.LookUp.HIRACHICAL_LEVEL_MAP);
            hirachicalPrefix = (List<String>) resultMap.get(PrefixConstants.LookUp.HIRACHICAL_LIST);
            loadChildLookUpsAndWithParent();
        } else {

           /* Defect #30784
            * nonHirachicalPrefix.clear();
            nonHirachicalDetailMap.clear();*/
            resultMap = iLookUpService.getNonHirachicalPrefixDetails();
            nonHirachicalDetailMap = (Map<Integer, Map<String, List<LookUp>>>) resultMap
                    .get(PrefixConstants.LookUp.NON_HIRACHICAL_DETAIL_MAP);
            nonHirachicalPrefix = (List<String>) resultMap.get(PrefixConstants.LookUp.NON_HIRACHICAL_LIST);

        }
        /* Defect #30784
         * nonReplicatePrefix.clear();
         */
        nonReplicatePrefix = iLookUpService.getNonReplicatePrefix();
        LOGGER.info("ApplicationSession- > updatePrefixCache(),prefixType->"+prefixType+", End Time -> " + Calendar.getInstance().getTime());
    }

    /**
     * Returns the all lookups at the last level for each parent in case of hierarchical prefix
     * @param cpmPrefix hierarchical prefix
     * @param orgId organisation
     * @return List of LookUp else null
     */
    public List<LookUp> getLastLevelChildsForHierarchical(final String cpmPrefix, final Long orgId) {
        List<LookUp> childLookUps = Collections.synchronizedList(new ArrayList<>(0));
        Long tempOrgId = orgId;
        if (nonReplicatePrefix.contains(cpmPrefix)) {
            tempOrgId = getSuperUserOrganization().getOrgid();
        }
        Map<String, Map<Integer, List<LookUp>>> prefixMap = hirachicalDetailMap.get(tempOrgId.intValue());
        if (prefixMap != null) {
            Map<Integer, List<LookUp>> levelMap = prefixMap.get(cpmPrefix);
            if (levelMap != null) {
                int highestLevel = 0;
                for (Entry<Integer, List<LookUp>> levelEntry : levelMap.entrySet()) {
                    if (highestLevel < levelEntry.getKey().intValue()) {
                        highestLevel = levelEntry.getKey().intValue();
                    }
                }
                childLookUps.addAll(levelMap.get(highestLevel));
            }
        }
        if (childLookUps.isEmpty()) {
            childLookUps = null;
        }
        return childLookUps;
    }

    // Rajesh
    public Map<Long, LookUp> getNonHirachicalPrefixDetails() {
        return nonHirachicalPrefixDetails;
    }

    // Rajesh
    public void setNonHirachicalPrefixDetails(Map<Long, LookUp> nonHirachicalPrefixDetails) {
        this.nonHirachicalPrefixDetails = nonHirachicalPrefixDetails;
    }

    public Map<Long, LookUp> getHirachicalPrefixDetails() {
        return hirachicalPrefixDetails;
    }

    public void setHirachicalPrefixDetails(Map<Long, LookUp> hirachicalPrefixDetails) {
        this.hirachicalPrefixDetails = hirachicalPrefixDetails;
    }

    public List<String> getNonReplicatePrefix() {
        return nonReplicatePrefix;
    }
	public PropertyBillGenerationMap getPropertyBillGenerationMap() {
        return propertyBillGenerationMap;}
	public void setPropertyBillGenerationMap(PropertyBillGenerationMap propertyBillGenerationMap) {
        this.propertyBillGenerationMap = propertyBillGenerationMap;}
   
	//#35090
	public boolean isResAttrsOverridden(String pageId,String labelCode){
    	if(pageId!=null && labelCode!=null && !pageId.isEmpty() && !labelCode.isEmpty()) {
    		Map<String, Resource> pageDtls = resourceDetailsMap.get(pageId);
    		if(pageDtls!=null && ! pageDtls.isEmpty()) {
    			Resource fieldDtls = pageDtls.get(labelCode);
    			if(fieldDtls!=null) {
    				return true;
    			}
    		}
    	}
    		
		return false;	
    }
	//#35090
    public boolean isMandatory(String pageId,String labelCode){
    	if(pageId!=null && labelCode!=null && !pageId.isEmpty() && !labelCode.isEmpty()) {
    		Map<String, Resource> pageDtls = resourceDetailsMap.get(pageId);
    		if(pageDtls!=null && ! pageDtls.isEmpty()) {
    			Resource fieldDtls = pageDtls.get(labelCode);
    			if(fieldDtls!=null) {
    				return fieldDtls.isMandatory();
    			}
    		}
    	}
    		
		return false;	
    }
	//#35090
    public boolean isVisible(String pageId,String labelCode){
    	if(pageId!=null && labelCode!=null && !pageId.isEmpty() && !labelCode.isEmpty()) {
    		Map<String, Resource> pageDtls = resourceDetailsMap.get(pageId);
    		if(pageDtls!=null && ! pageDtls.isEmpty()) {
    			Resource fieldDtls = pageDtls.get(labelCode);
    			if(fieldDtls!=null) {
    				return fieldDtls.isVisible();
    			}
    		}
    	}
    		
		return false;	
    }
	//#35090
    @SuppressWarnings("unchecked")
    public synchronized void updateResourceCache(String pageId) {
    	LOGGER.info("ApplicationSession- > loadResourceData(), Start Time -> " + Calendar.getInstance().getTime());
    	resourceDetailsMap.put(pageId,iResourceService.getSingleResourceDtls(pageId).get(pageId));
        LOGGER.info("ApplicationSession- > loadResourceData(), End Time -> " + Calendar.getInstance().getTime());
    	
    }
	public Map<Long, WaterBillGenerationMap> getWaterBillGenerationMapOrgId() {
		return waterBillGenerationMapOrgId;
	}

	public void setWaterBillGenerationMapOrgId(Map<Long, WaterBillGenerationMap> waterBillGenerationMapOrgId) {
		this.waterBillGenerationMapOrgId = waterBillGenerationMapOrgId;
	}

	public WaterBillGenerationMap getWaterBillGenerationMap() {
		return waterBillGenerationMap;
	}

	public void setWaterBillGenerationMap(WaterBillGenerationMap waterBillGenerationMap) {
		this.waterBillGenerationMap = waterBillGenerationMap;
	}

	public Map<Long, PropertyBillGenerationMap> getPropBillGenerationMapOrgId() {
		return propBillGenerationMapOrgId;
	}

	public void setPropBillGenerationMapOrgId(Map<Long, PropertyBillGenerationMap> propBillGenerationMapOrgId) {
		this.propBillGenerationMapOrgId = propBillGenerationMapOrgId;
	}

	public String getUniqueKeyId() {
		return uniqueKeyId;
	}

	public void setUniqueKeyId(String uniqueKeyId) {
		this.uniqueKeyId = uniqueKeyId;
	}
	
	
	
	
}
