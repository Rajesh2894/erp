package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.master.dao.IComParamMasterDAO;
import com.abm.mainet.common.utility.LookUp;
import com.ibm.icu.util.Calendar;

/**
 * @author Pranit.Mhatre
 * @since 26 November, 2013
 */
@Service
public class LookUpService implements ILookUpService {

    private static final Logger LOGGER = Logger.getLogger(LookUpService.class);
    
    private LookUp lookUp = new LookUp();

    private List<LookUp> lookUpList = new ArrayList<>(0);

    private Map<String, List<LookUp>> prefixMap = new ConcurrentHashMap<>(0);

    private Map<Integer, List<LookUp>> lookUpMap = new ConcurrentHashMap<>(0);

    private Map<String, Map<Integer, List<LookUp>>> hPrefixMap = new ConcurrentHashMap<>(0);

    private Map<Long, LookUp> getNonHirachialLookupMap = new ConcurrentHashMap<>(0);

    private Map<Long, LookUp> getHirachialLookupMap = new ConcurrentHashMap<>(0);

    @Autowired
    private IComParamMasterDAO comParamMasterDAO;

    @Override
    @Transactional
    public Map<String, Object> getNonHirachicalPrefixDetails() {
	
	LOGGER.info("LookUpService- > getNonHirachicalPrefixDetails(), Start Time -> " + Calendar.getInstance().getTime());

        final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap = new ConcurrentHashMap<>(0);

        final List<String> nonHirachicalPrefix = Collections.synchronizedList(new ArrayList<>(0));

        final Map<String, Object> resultMap = new ConcurrentHashMap<>();

        loadNonHirachicalPrefix(nonHirachicalDetailMap, nonHirachicalPrefix);

        resultMap.put(PrefixConstants.LookUp.NON_HIRACHICAL_DETAIL_MAP, nonHirachicalDetailMap);

        resultMap.put(PrefixConstants.LookUp.NON_HIRACHICAL_LIST, nonHirachicalPrefix);

        LOGGER.info("LookUpService- > getNonHirachicalPrefixDetails(), End Time -> " + Calendar.getInstance().getTime());
        
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> getHirachicalPrefixDetails() {

	LOGGER.info("LookUpService- > getHirachicalPrefixDetails(), Start Time -> " + Calendar.getInstance().getTime());
	
        final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap = new ConcurrentHashMap<>(
                0);

        final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap = new ConcurrentHashMap<>(
                0);

        final List<String> hirachicalPrefix = Collections.synchronizedList( new ArrayList<>(0));

        final Map<String, Object> resultMap = new ConcurrentHashMap<>();

        loadHirachicalPrefix(hirachicalLevelMap, hirachicalDetailMap, hirachicalPrefix);

        resultMap.put(PrefixConstants.LookUp.HIRACHICAL_LEVEL_MAP, hirachicalLevelMap);

        resultMap.put(PrefixConstants.LookUp.HIRACHICAL_DETAIL_MAP, hirachicalDetailMap);

        resultMap.put(PrefixConstants.LookUp.HIRACHICAL_LIST, hirachicalPrefix);

        LOGGER.info("LookUpService- > getHirachicalPrefixDetails(), End Time -> " + Calendar.getInstance().getTime());
        
        return resultMap;
    }

    /*
     * @Override
     * public Map<String, Object> getAllPrefixDetails() {
     * final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap = new ConcurrentHashMap<>(
     * 0);
     * final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap = new ConcurrentHashMap<>(
     * 0);
     * final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap = new ConcurrentHashMap<>(0);
     * final List<String> hirachicalPrefix = new ArrayList<>(0);
     * final List<String> nonHirachicalPrefix = new ArrayList<>(0);
     * final Map<String, Object> resultMap = new ConcurrentHashMap<>();
     * loadHirachicalPrefix(hirachicalLevelMap, hirachicalDetailMap, hirachicalPrefix);
     * loadNonHirachicalPrefix(nonHirachicalDetailMap, nonHirachicalPrefix);
     * resultMap.put(PrefixConstants.LookUp.HIRACHICAL_LEVEL_MAP, hirachicalLevelMap);
     * resultMap.put(PrefixConstants.LookUp.HIRACHICAL_DETAIL_MAP, hirachicalDetailMap);
     * resultMap.put(PrefixConstants.LookUp.NON_HIRACHICAL_DETAIL_MAP, nonHirachicalDetailMap);
     * resultMap.put(PrefixConstants.LookUp.HIRACHICAL_LIST, hirachicalPrefix);
     * resultMap.put(PrefixConstants.LookUp.NON_HIRACHICAL_LIST, nonHirachicalPrefix);
     * return resultMap;
     * }
     */
    private void loadNonHirachicalPrefix(final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap,
            final List<String> nonHirachicalPrefix) {

	LOGGER.info("LookUpService- > loadNonHirachicalPrefix(), Start Time -> " + Calendar.getInstance().getTime());
        List<ViewPrefixDetails> nPrefixDetails = getViewPrefixDetailsByType(PrefixConstants.LookUp.NON_HIERARCHICAL);

        for (final ViewPrefixDetails viewPrefixDetail : nPrefixDetails) {
            if (!nonHirachicalPrefix.contains(viewPrefixDetail.getCpmPrefix())) {
                nonHirachicalPrefix.add(viewPrefixDetail.getCpmPrefix());
            }

            loadNonHirachicalDetailMap(viewPrefixDetail, nonHirachicalDetailMap);
        }
        
        LOGGER.info("LookUpService- > loadNonHirachicalPrefix(), End Time -> " + Calendar.getInstance().getTime());
    }

    private void loadNonHirachicalDetailMap(final ViewPrefixDetails viewPrefixDetails,
            final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap) {

	LOGGER.info("LookUpService- > loadNonHirachicalDetailMap(), Start Time -> " + Calendar.getInstance().getTime());
	
        if (nonHirachicalDetailMap.containsKey(viewPrefixDetails.getOrgid().intValue())) {
            prefixMap = nonHirachicalDetailMap.get(viewPrefixDetails.getOrgid().intValue());

            if (prefixMap.containsKey(viewPrefixDetails.getCpmPrefix())) {
                lookUpList = prefixMap.get(viewPrefixDetails.getCpmPrefix());

                /*
                 * lookUp = new LookUp();
                 * lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                 * lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                 * lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                 * lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                 * lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                 * if (viewPrefixDetails.getCpdOthers() != null) {
                 * lookUp.setOtherField(viewPrefixDetails.getCpdOthers());
                 * }
                 * if ((viewPrefixDetails.getCodCpdDefault() != null)
                 * && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
                 * lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                 * }
                 * lookUpList.add(lookUp);
                 * prefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpList);
                 * nonHirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
                 */
            } else {
                lookUpList = Collections.synchronizedList(new ArrayList<>(0));

                /*
                 * lookUp = new LookUp();
                 * lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                 * lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                 * lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                 * lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                 * lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                 * if (viewPrefixDetails.getCpdOthers() != null) {
                 * lookUp.setOtherField(viewPrefixDetails.getCpdOthers());
                 * }
                 * if ((viewPrefixDetails.getCodCpdDefault() != null)
                 * && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
                 * lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                 * }
                 * lookUpList.add(lookUp);
                 * prefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpList);
                 * nonHirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
                 */
            }
        } else {
            prefixMap = new ConcurrentHashMap<>(0);

            lookUpList = Collections.synchronizedList(new ArrayList<>(0));
            /*
             * lookUp = new LookUp();
             * lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
             * lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
             * lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
             * lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
             * lookUp.setLookUpType(viewPrefixDetails.getCpmType());
             * if (viewPrefixDetails.getCpdOthers() != null) {
             * lookUp.setOtherField(viewPrefixDetails.getCpdOthers());
             * }
             * if ((viewPrefixDetails.getCodCpdDefault() != null)
             * && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
             * lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
             * }
             * lookUpList.add(lookUp);
             * prefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpList);
             * nonHirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
             */
        }

        lookUp = new LookUp();

        lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
        lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
        lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
        lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
        lookUp.setLookUpType(viewPrefixDetails.getCpmType());
        if (viewPrefixDetails.getCpdOthers() != null) {
            lookUp.setOtherField(viewPrefixDetails.getCpdOthers());
        }

        if ((viewPrefixDetails.getCodCpdDefault() != null)
                && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
            lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
        }
        lookUpList.add(lookUp);

        // rajesh
        if (!getNonHirachialLookupMap.containsKey(viewPrefixDetails.getCodCpdId()))
            getNonHirachialLookupMap.put(viewPrefixDetails.getCodCpdId(), lookUp);

        prefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpList);

        nonHirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
        
        LOGGER.info("LookUpService- > loadNonHirachicalDetailMap(), End Time -> " + Calendar.getInstance().getTime());

    }

    private void loadHirachicalPrefix(final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap,
            final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap,
            final List<String> hirachicalPrefix) {
	LOGGER.info("LookUpService- > loadHirachicalPrefix(), Start Time -> " + Calendar.getInstance().getTime());
	
        List<ViewPrefixDetails> hPrefixDetails = getViewPrefixDetailsByType(PrefixConstants.LookUp.HIERARCHICAL);

        for (final ViewPrefixDetails viewPrefixDetails : hPrefixDetails) {

            if (!hirachicalPrefix.contains(viewPrefixDetails.getCpmPrefix())) {
                hirachicalPrefix.add(viewPrefixDetails.getCpmPrefix());
            }

            
            loadHirachicalLevelMap(viewPrefixDetails, hirachicalLevelMap);
            

            loadHirachicalDetailMap(viewPrefixDetails, hirachicalDetailMap);
        }
        
        LOGGER.info("LookUpService- > loadHirachicalPrefix(), End Time -> " + Calendar.getInstance().getTime());
    }

    private void loadHirachicalDetailMap(final ViewPrefixDetails viewPrefixDetails,
            final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap) {

	LOGGER.info("LookUpService- > loadHirachicalDetailMap(), Start Time -> " + Calendar.getInstance().getTime());
	
        if (hirachicalDetailMap.containsKey(viewPrefixDetails.getOrgid().intValue())) {
            hPrefixMap = hirachicalDetailMap.get(viewPrefixDetails.getOrgid().intValue());

            if (hPrefixMap.containsKey(viewPrefixDetails.getCpmPrefix())) {
                lookUpMap = hPrefixMap.get(viewPrefixDetails.getCpmPrefix());

                if (lookUpMap.containsKey(viewPrefixDetails.getComLevel())) {
                    lookUpList = lookUpMap.get(viewPrefixDetails.getComLevel());

                    /*
                     * lookUp = new LookUp();
                     * lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                     * lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                     * lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                     * lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                     * lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                     * if ((viewPrefixDetails.getCodCpdDefault() != null)
                     * && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
                     * lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                     * }
                     * if (viewPrefixDetails.getCodCpdParentId() != null) {
                     * lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
                     * }
                     * lookUpList.add(lookUp);
                     * lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);
                     * hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);
                     * hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
                     */
                } else {
                    lookUpList = Collections.synchronizedList(new ArrayList<>(0));

                    /*
                     * lookUp = new LookUp();
                     * lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                     * lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                     * lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                     * lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                     * lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                     * if ((viewPrefixDetails.getCodCpdDefault() != null)
                     * && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
                     * lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                     * }
                     * if (viewPrefixDetails.getCodCpdParentId() != null) {
                     * lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
                     * }
                     * lookUpList.add(lookUp);
                     * lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);
                     * hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);
                     * hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
                     */
                }
            } else {
                lookUpMap = new ConcurrentHashMap<>(0);

                lookUpList = Collections.synchronizedList(new ArrayList<>(0));

                /*
                 * lookUp = new LookUp();
                 * lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                 * lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                 * lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                 * lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                 * lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                 * if ((viewPrefixDetails.getCodCpdDefault() != null)
                 * && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
                 * lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                 * }
                 * if (viewPrefixDetails.getCodCpdParentId() != null) {
                 * lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
                 * }
                 * lookUpList.add(lookUp);
                 * lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);
                 * hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);
                 * hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
                 */
            }
        } else {
            hPrefixMap = new ConcurrentHashMap<>(0);

            lookUpMap = new ConcurrentHashMap<>(0);

            lookUpList = Collections.synchronizedList(new ArrayList<>(0));

            /*
             * lookUp = new LookUp();
             * lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
             * lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
             * lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
             * lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
             * lookUp.setLookUpType(viewPrefixDetails.getCpmType());
             * if ((viewPrefixDetails.getCodCpdDefault() != null)
             * && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
             * lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
             * }
             * if (viewPrefixDetails.getCodCpdParentId() != null) {
             * lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
             * }
             * lookUpList.add(lookUp);
             * lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);
             * hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);
             * hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
             */
        }

        lookUp = new LookUp();

        lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
        lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
        lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
        lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
        lookUp.setLookUpType(viewPrefixDetails.getCpmType());
        
        lookUp.setOtherField(viewPrefixDetails.getCpdOthers());
        // rajesh
        if (!getHirachialLookupMap.containsKey(viewPrefixDetails.getCodCpdId()))
            getHirachialLookupMap.put(viewPrefixDetails.getCodCpdId(), lookUp);

        if ((viewPrefixDetails.getCodCpdDefault() != null)
                && viewPrefixDetails.getCodCpdDefault().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
            lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
        }

        if (viewPrefixDetails.getCodCpdParentId() != null) {
            lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
        }

        lookUpList.add(lookUp);

        lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);

        hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);

        hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
        
        LOGGER.info("LookUpService- > loadHirachicalDetailMap(), End Time -> " + Calendar.getInstance().getTime());

    }

    private void loadHirachicalLevelMap(final ViewPrefixDetails viewPrefixDetails,
            final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap) {

	LOGGER.info("LookUpService- > loadHirachicalLevelMap(), Start Time -> " + Calendar.getInstance().getTime());
	
        LookUp levelLookUp = new LookUp();

        Map<Long, LookUp> levelmap = new ConcurrentHashMap<>(0);

        Map<String, Map<Long, LookUp>> prefixMap = new ConcurrentHashMap<>(0);

        if (hirachicalLevelMap.containsKey(viewPrefixDetails.getOrgid().intValue())) {
            prefixMap = hirachicalLevelMap.get(viewPrefixDetails.getOrgid().intValue());

            if (prefixMap.containsKey(viewPrefixDetails.getCpmPrefix())) {
                levelmap = prefixMap.get(viewPrefixDetails.getCpmPrefix());

                if (!levelmap.containsKey(viewPrefixDetails.getComLevel().longValue())) {
                    /*
                     * levelLookUp = new LookUp();
                     * levelLookUp.setLookUpId(viewPrefixDetails.getComId());
                     * levelLookUp.setDescLangFirst(viewPrefixDetails.getComDesc());
                     * levelLookUp.setDescLangSecond(viewPrefixDetails.getComDescMar());
                     * levelLookUp.setLookUpCode(viewPrefixDetails.getComValue());
                     * levelLookUp.setLookUpType(PrefixConstants.LookUp.HIERARCHICAL);
                     * levelmap.put(viewPrefixDetails.getComLevel().longValue(), levelLookUp);
                     * prefixMap.put(viewPrefixDetails.getCpmPrefix(), levelmap);
                     * hirachicalLevelMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
                     */
                }
            } else {
                levelmap = new ConcurrentHashMap<>(0);

                /*
                 * levelLookUp = new LookUp();
                 * levelLookUp.setLookUpId(viewPrefixDetails.getComId());
                 * levelLookUp.setDescLangFirst(viewPrefixDetails.getComDesc());
                 * levelLookUp.setDescLangSecond(viewPrefixDetails.getComDescMar());
                 * levelLookUp.setLookUpCode(viewPrefixDetails.getComValue());
                 * levelLookUp.setLookUpType(PrefixConstants.LookUp.HIERARCHICAL);
                 * levelmap.put(viewPrefixDetails.getComLevel().longValue(), levelLookUp);
                 * prefixMap.put(viewPrefixDetails.getCpmPrefix(), levelmap);
                 * hirachicalLevelMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
                 */
            }
        } else {
            prefixMap = new ConcurrentHashMap<>(0);

            levelmap = new ConcurrentHashMap<>(0);

            /*
             * levelLookUp = new LookUp();
             * levelLookUp.setLookUpId(viewPrefixDetails.getComId());
             * levelLookUp.setDescLangFirst(viewPrefixDetails.getComDesc());
             * levelLookUp.setDescLangSecond(viewPrefixDetails.getComDescMar());
             * levelLookUp.setLookUpCode(viewPrefixDetails.getComValue());
             * levelLookUp.setLookUpType(PrefixConstants.LookUp.HIERARCHICAL);
             * levelmap.put(viewPrefixDetails.getComLevel().longValue(), levelLookUp);
             * prefixMap.put(viewPrefixDetails.getCpmPrefix(), levelmap);
             * hirachicalLevelMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
             */
        }

        levelLookUp = new LookUp();

        levelLookUp.setLookUpId(viewPrefixDetails.getComId());
        levelLookUp.setDescLangFirst(viewPrefixDetails.getComDesc());
        levelLookUp.setDescLangSecond(viewPrefixDetails.getComDescMar());
        levelLookUp.setLookUpCode(viewPrefixDetails.getComValue());
        levelLookUp.setLookUpType(PrefixConstants.LookUp.HIERARCHICAL);
        levelLookUp.setOtherField(viewPrefixDetails.getCpdOthers());
        levelmap.put(viewPrefixDetails.getComLevel().longValue(), levelLookUp);

        prefixMap.put(viewPrefixDetails.getCpmPrefix(), levelmap);

        hirachicalLevelMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);

        LOGGER.info("LookUpService- > loadHirachicalLevelMap(), End Time -> " + Calendar.getInstance().getTime());
    }

    // Rajesh
    @Override
    public Map<Long, LookUp> getNonHirachicalPrefixDetailsByLookupID() {

        return getNonHirachialLookupMap;

    }

    // Rajesh
    @Override
    public Map<Long, LookUp> getHirachicalPrefixDetailsByLookupID() {

        return getHirachialLookupMap;

    }

    @Transactional(readOnly = true)
    public List<String> getAllStartupPrefix() {

        return comParamMasterDAO.getAllStartupPrefix();
    }

    @Transactional(readOnly = true)
    public List<ViewPrefixDetails> getViewPrefixDetailsByType(final String cpmType) {
        return comParamMasterDAO.getViewPrefixDetailsByType(cpmType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getNonReplicatePrefix() {
        return comParamMasterDAO.getNonReplicatePrefix();
    }
}
