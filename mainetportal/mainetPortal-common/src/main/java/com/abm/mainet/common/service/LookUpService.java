/**
 *
 */
package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IComParamMasterDAO;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.util.LookUp;

/**
 * @author Pranit.Mhatre
 * @since 26 November, 2013
 */
@Service
public class LookUpService implements ILookUpService {

    @Autowired
    private IComParamMasterDAO comParamMasterDAO;

    @Autowired
    private IComparamMasterWebService comparamMasterWebService;

    @Override
    @Transactional
    public Map<String, Object> getNonHirachicalPrefixDetails() {

        final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap = new HashMap<>(0);

        final List<String> nonHirachicalPrefix = new ArrayList<>(0);

        final Map<String, Object> resultMap = new HashMap<>();

        loadNonHirachicalPrefix(nonHirachicalDetailMap, nonHirachicalPrefix);

        resultMap.put(MainetConstants.LookUp.NON_HIRACHICAL_DETAIL_MAP, nonHirachicalDetailMap);

        resultMap.put(MainetConstants.LookUp.NON_HIRACHICAL_LIST, nonHirachicalPrefix);

        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> getHirachicalPrefixDetails() {

        final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap = new LinkedHashMap<>(
                0);

        final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap = new HashMap<>(
                0);

        final List<String> hirachicalPrefix = new ArrayList<>(0);

        final Map<String, Object> resultMap = new HashMap<>();

        loadHirachicalPrefix(hirachicalLevelMap, hirachicalDetailMap, hirachicalPrefix);

        resultMap.put(MainetConstants.LookUp.HIRACHICAL_LEVEL_MAP, hirachicalLevelMap);

        resultMap.put(MainetConstants.LookUp.HIRACHICAL_DETAIL_MAP, hirachicalDetailMap);

        resultMap.put(MainetConstants.LookUp.HIRACHICAL_LIST, hirachicalPrefix);

        return resultMap;
    }

    @Override
    public Map<String, Object> getAllPrefixDetails() {

        final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap = new HashMap<>(
                0);

        final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap = new HashMap<>(
                0);

        final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap = new HashMap<>(0);

        final List<String> hirachicalPrefix = new ArrayList<>(0);

        final List<String> nonHirachicalPrefix = new ArrayList<>(0);

        final Map<String, Object> resultMap = new HashMap<>();

        loadHirachicalPrefix(hirachicalLevelMap, hirachicalDetailMap, hirachicalPrefix);

        loadNonHirachicalPrefix(nonHirachicalDetailMap, nonHirachicalPrefix);

        resultMap.put(MainetConstants.LookUp.HIRACHICAL_LEVEL_MAP, hirachicalLevelMap);

        resultMap.put(MainetConstants.LookUp.HIRACHICAL_DETAIL_MAP, hirachicalDetailMap);

        resultMap.put(MainetConstants.LookUp.NON_HIRACHICAL_DETAIL_MAP, nonHirachicalDetailMap);

        resultMap.put(MainetConstants.LookUp.HIRACHICAL_LIST, hirachicalPrefix);

        resultMap.put(MainetConstants.LookUp.NON_HIRACHICAL_LIST, nonHirachicalPrefix);

        return resultMap;
    }

    private void loadNonHirachicalPrefix(final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap,
            final List<String> nonHirachicalPrefix) {

        List<ViewPrefixDetails> nPrefixDetails = null;

        nPrefixDetails = getViewPrefixDetailsByType(MainetConstants.LookUp.NON_HIERARCHICAL);

        for (final ViewPrefixDetails viewPrefixDetail : nPrefixDetails) {
            if (!nonHirachicalPrefix.contains(viewPrefixDetail.getCpmPrefix())) {
                nonHirachicalPrefix.add(viewPrefixDetail.getCpmPrefix());
            }

            loadNonHirachicalDetailMap(viewPrefixDetail, nonHirachicalDetailMap);
        }
    }

    private void loadNonHirachicalDetailMap(final ViewPrefixDetails viewPrefixDetails,
            final Map<Integer, Map<String, List<LookUp>>> nonHirachicalDetailMap) {
        LookUp lookUp = null;
        List<LookUp> lookUpList = new ArrayList<>(0);
        Map<String, List<LookUp>> prefixMap = new LinkedHashMap<>(0);
        if (nonHirachicalDetailMap.containsKey(viewPrefixDetails.getOrgid().intValue())) {
            prefixMap = nonHirachicalDetailMap.get(viewPrefixDetails.getOrgid().intValue());

            if (prefixMap.containsKey(viewPrefixDetails.getCpmPrefix())) {
                lookUpList = prefixMap.get(viewPrefixDetails.getCpmPrefix());

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
                        && viewPrefixDetails.getCodCpdDefault().equals(MainetConstants.IsLookUp.STATUS.YES)) {
                    lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                }

                lookUpList.add(lookUp);

                prefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpList);

                nonHirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
            } else {
                lookUpList = new ArrayList<>(0);

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
                        && viewPrefixDetails.getCodCpdDefault().equals(MainetConstants.IsLookUp.STATUS.YES)) {
                    lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                }
                lookUpList.add(lookUp);

                prefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpList);

                nonHirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
            }
        } else {
            prefixMap = new HashMap<>(0);

            lookUpList = new ArrayList<>(0);

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
                    && viewPrefixDetails.getCodCpdDefault().equals(MainetConstants.IsLookUp.STATUS.YES)) {
                lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
            }
            lookUpList.add(lookUp);

            prefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpList);

            nonHirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
        }
    }

    private void loadHirachicalPrefix(final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap,
            final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap,
            final List<String> hirachicalPrefix) {

        List<ViewPrefixDetails> hPrefixDetails = null;

        hPrefixDetails = getViewPrefixDetailsByType(MainetConstants.LookUp.HIERARCHICAL);

        for (final ViewPrefixDetails viewPrefixDetails : hPrefixDetails) {

            if (!hirachicalPrefix.contains(viewPrefixDetails.getCpmPrefix())) {
                hirachicalPrefix.add(viewPrefixDetails.getCpmPrefix());
            }

            loadHirachicalLevelMap(viewPrefixDetails, hirachicalLevelMap);

            loadHirachicalDetailMap(viewPrefixDetails, hirachicalDetailMap);
        }
    }

    private void loadHirachicalDetailMap(final ViewPrefixDetails viewPrefixDetails,
            final Map<Integer, Map<String, Map<Integer, List<LookUp>>>> hirachicalDetailMap) {

        LookUp lookUp = null;
        List<LookUp> lookUpList = new ArrayList<>(0);
        Map<Integer, List<LookUp>> lookUpMap = new LinkedHashMap<>(0);
        Map<String, Map<Integer, List<LookUp>>> hPrefixMap = new LinkedHashMap<>(0);

        if (hirachicalDetailMap.containsKey(viewPrefixDetails.getOrgid().intValue())) {
            hPrefixMap = hirachicalDetailMap.get(viewPrefixDetails.getOrgid().intValue());

            if (hPrefixMap.containsKey(viewPrefixDetails.getCpmPrefix())) {
                lookUpMap = hPrefixMap.get(viewPrefixDetails.getCpmPrefix());

                if (lookUpMap.containsKey(viewPrefixDetails.getComLevel())) {
                    lookUpList = lookUpMap.get(viewPrefixDetails.getComLevel());

                    lookUp = new LookUp();

                    lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                    lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                    lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                    lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                    lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                    
                    lookUp.setOtherField(viewPrefixDetails.getCpdOthers());
                    if ((viewPrefixDetails.getCodCpdDefault() != null)
                            && viewPrefixDetails.getCodCpdDefault().equals(MainetConstants.IsLookUp.STATUS.YES)) {
                        lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                    }
                    if (viewPrefixDetails.getCodCpdParentId() != null) {
                        lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
                    }

                    lookUpList.add(lookUp);

                    lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);

                    hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);

                    hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
                } else {
                    lookUpList = new ArrayList<>(0);

                    lookUp = new LookUp();

                    lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                    lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                    lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                    lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                    lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                    lookUp.setOtherField(viewPrefixDetails.getCpdOthers());
                    if ((viewPrefixDetails.getCodCpdDefault() != null)
                            && viewPrefixDetails.getCodCpdDefault().equals(MainetConstants.IsLookUp.STATUS.YES)) {
                        lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                    }

                    if (viewPrefixDetails.getCodCpdParentId() != null) {
                        lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
                    }

                    lookUpList.add(lookUp);

                    lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);

                    hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);

                    hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
                }
            } else {
                lookUpMap = new HashMap<>(0);

                lookUpList = new ArrayList<>(0);

                lookUp = new LookUp();

                lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
                lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
                lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
                lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
                lookUp.setLookUpType(viewPrefixDetails.getCpmType());
                lookUp.setOtherField(viewPrefixDetails.getCpdOthers());

                if ((viewPrefixDetails.getCodCpdDefault() != null)
                        && viewPrefixDetails.getCodCpdDefault().equals(MainetConstants.IsLookUp.STATUS.YES)) {
                    lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
                }

                if (viewPrefixDetails.getCodCpdParentId() != null) {
                    lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
                }

                lookUpList.add(lookUp);

                lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);

                hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);

                hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
            }
        } else {
            hPrefixMap = new HashMap<>(0);

            lookUpMap = new HashMap<>(0);

            lookUpList = new ArrayList<>(0);

            lookUp = new LookUp();

            lookUp.setLookUpId(viewPrefixDetails.getCodCpdId());
            lookUp.setLookUpCode(viewPrefixDetails.getCodCpdValue());
            lookUp.setDescLangFirst(viewPrefixDetails.getCodCpdDesc());
            lookUp.setDescLangSecond(viewPrefixDetails.getCodCpdDescMar());
            lookUp.setLookUpType(viewPrefixDetails.getCpmType());
            lookUp.setOtherField(viewPrefixDetails.getCpdOthers());

            if ((viewPrefixDetails.getCodCpdDefault() != null)
                    && viewPrefixDetails.getCodCpdDefault().equals(MainetConstants.IsLookUp.STATUS.YES)) {
                lookUp.setDefaultVal(viewPrefixDetails.getCodCpdDefault());
            }

            if (viewPrefixDetails.getCodCpdParentId() != null) {
                lookUp.setLookUpParentId(viewPrefixDetails.getCodCpdParentId());
            }

            lookUpList.add(lookUp);

            lookUpMap.put(viewPrefixDetails.getComLevel(), lookUpList);

            hPrefixMap.put(viewPrefixDetails.getCpmPrefix(), lookUpMap);

            hirachicalDetailMap.put(viewPrefixDetails.getOrgid().intValue(), hPrefixMap);
        }
    }

    private void loadHirachicalLevelMap(final ViewPrefixDetails viewPrefixDetails,
            final Map<Integer, Map<String, Map<Long, LookUp>>> hirachicalLevelMap) {

        LookUp levelLookUp = new LookUp();

        Map<Long, LookUp> levelmap = new LinkedHashMap<>(0);

        Map<String, Map<Long, LookUp>> prefixMap = new LinkedHashMap<>(0);

        if (hirachicalLevelMap.containsKey(viewPrefixDetails.getOrgid().intValue())) {
            prefixMap = hirachicalLevelMap.get(viewPrefixDetails.getOrgid().intValue());

            if (prefixMap.containsKey(viewPrefixDetails.getCpmPrefix())) {
                levelmap = prefixMap.get(viewPrefixDetails.getCpmPrefix());

                if (!levelmap.containsKey(viewPrefixDetails.getComLevel().longValue())) {
                    levelLookUp = new LookUp();

                    levelLookUp.setLookUpId(viewPrefixDetails.getComId());
                    levelLookUp.setDescLangFirst(viewPrefixDetails.getComDesc());
                    levelLookUp.setDescLangSecond(viewPrefixDetails.getComDescMar());
                    levelLookUp.setLookUpCode(viewPrefixDetails.getComValue());
                    levelLookUp.setLookUpType(MainetConstants.LookUp.HIERARCHICAL);

                    levelmap.put(viewPrefixDetails.getComLevel().longValue(), levelLookUp);

                    prefixMap.put(viewPrefixDetails.getCpmPrefix(), levelmap);

                    hirachicalLevelMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
                }
            } else {
                levelmap = new LinkedHashMap<>(0);

                levelLookUp = new LookUp();

                levelLookUp.setLookUpId(viewPrefixDetails.getComId());
                levelLookUp.setDescLangFirst(viewPrefixDetails.getComDesc());
                levelLookUp.setDescLangSecond(viewPrefixDetails.getComDescMar());
                levelLookUp.setLookUpCode(viewPrefixDetails.getComValue());
                levelLookUp.setLookUpType(MainetConstants.LookUp.HIERARCHICAL);

                levelmap.put(viewPrefixDetails.getComLevel().longValue(), levelLookUp);

                prefixMap.put(viewPrefixDetails.getCpmPrefix(), levelmap);

                hirachicalLevelMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
            }
        } else {
            prefixMap = new LinkedHashMap<>(0);

            levelmap = new LinkedHashMap<>(0);

            levelLookUp = new LookUp();

            levelLookUp.setLookUpId(viewPrefixDetails.getComId());
            levelLookUp.setDescLangFirst(viewPrefixDetails.getComDesc());
            levelLookUp.setDescLangSecond(viewPrefixDetails.getComDescMar());
            levelLookUp.setLookUpCode(viewPrefixDetails.getComValue());
            levelLookUp.setLookUpType(MainetConstants.LookUp.HIERARCHICAL);

            levelmap.put(viewPrefixDetails.getComLevel().longValue(), levelLookUp);

            prefixMap.put(viewPrefixDetails.getCpmPrefix(), levelmap);

            hirachicalLevelMap.put(viewPrefixDetails.getOrgid().intValue(), prefixMap);
        }
    }

    @Override
    @Transactional
    public List<String> getNonReplicatePrefix() {

        List<String> NonReplicatePrefix = comparamMasterWebService.getNonReplicatePrefix();
        if ((NonReplicatePrefix == null) || NonReplicatePrefix.isEmpty()) {
            NonReplicatePrefix = comParamMasterDAO.getNonReplicatePrefix();
        }
        if (NonReplicatePrefix == null) {
            NonReplicatePrefix = new ArrayList<>();
        }

        return NonReplicatePrefix;
    }

    @Transactional(readOnly = true)
    public List<ViewPrefixDetails> getViewPrefixDetailsByType(final String cpmType) {

        List<ViewPrefixDetails> ViewPrefixDetailsList = comparamMasterWebService.getViewPrefixDetailsByType(cpmType);
        if ((ViewPrefixDetailsList == null) || ViewPrefixDetailsList.isEmpty()) {
            ViewPrefixDetailsList = comParamMasterDAO.getViewPrefixDetailsByType(cpmType);
        }
        if (ViewPrefixDetailsList == null) {
            ViewPrefixDetailsList = new ArrayList<>();
        }

        return ViewPrefixDetailsList;
    }

}
