package com.abm.mainet.asset.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.asset.domain.AssetDepreciationChart;
import com.abm.mainet.asset.domain.AssetFunctionalLocation;
import com.abm.mainet.asset.repository.AssetDepreciationChartRepo;
import com.abm.mainet.asset.repository.SearchRepo;
import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.asset.ui.dto.SummaryDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.BarcodeGenerator;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

@Service
public class SearchServiceImpl implements ISearchService {

    private static final Logger LOGGER = Logger.getLogger(SearchServiceImpl.class);

    @Resource
    private SearchRepo searchRepo;

    @Resource
    private TbDepartmentService iTbDepartmentService;

    @Autowired
    private IAssetFunctionalLocationMasterService assetFuncLocMasterService;

    @Autowired
    private AssetDepreciationChartRepo assetDepreciationChart;

    private static String barcodeLimit = ApplicationSession.getInstance().getMessage("barcode.generation.limit");

    private BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

    /**
     * It is used to search assets
     * 
     * @return list of Object as join is applied on it
     */
    @Override
    public List<SummaryDTO> search(SearchDTO searchDTO) {

        List<Object[]> objList = searchRepo.search(searchDTO);
        List<SummaryDTO> summaryDTOList = new ArrayList<SummaryDTO>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(searchDTO.getOrgId());
        final List<TbDepartment> depList = iTbDepartmentService.findAllActive(searchDTO.getOrgId());
        final List<AssetFunctionalLocation> funlocList = assetFuncLocMasterService
                .retriveFunctionLocationListByOrgId(searchDTO.getOrgId());
        if ((objList != null) && !objList.isEmpty()) {
            objList.parallelStream().forEachOrdered(array -> {
                SummaryDTO summaryDTO = new SummaryDTO();
                summaryDTO.setAstId(convertBigIntTOLong(array[0]));
                summaryDTO.setSerialNo((String) array[2]);
                if (array[1] != null) {
                    summaryDTO.setAssetName((String) array[1]);
                }
                if (array[7] != null) {
                    summaryDTO.setBarcode(convertBigIntTOLong(array[7]));
                }
                
                if (array[18] != null) {
                    summaryDTO.setAssetModelIdentifier(array[18].toString());
                }
                LookUp astLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        Long.valueOf(convertBigIntTOLong(array[3])),
                        organisation);
                if (searchDTO.getLangId() == 1) {
                    summaryDTO.setAssetStatusDesc(astLookup.getDescLangFirst());
                } else {
                    summaryDTO.setAssetStatusDesc(astLookup.getDescLangSecond());
                }
                LookUp astClassf1 = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        Long.valueOf(convertBigIntTOLong(array[4])),
                        organisation);
                if (searchDTO.getLangId() == 1) {
                    summaryDTO.setAssetClass1Desc(astClassf1.getDescLangFirst());
                } else {
                    summaryDTO.setAssetClass1Desc(astClassf1.getDescLangSecond());
                }
                summaryDTO.setAssetClass1(astClassf1.getLookUpId());
                summaryDTO.setAssetClass1Code(astClassf1.getLookUpCode());
                LookUp astClassf2 = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        Long.valueOf(convertBigIntTOLong(array[5])),
                        organisation);
                if (searchDTO.getLangId() == 1) {
                    summaryDTO.setAssetClass2Desc(astClassf2.getDescLangFirst());
                } else {
                    summaryDTO.setAssetClass2Desc(astClassf2.getDescLangSecond());
                }
                summaryDTO.setDetails((String) array[6]);
                summaryDTO.setAssetStatus(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(convertBigIntTOLong(array[3]), organisation)
                                .getLookUpCode());
                if (array[8] != null) {
                    summaryDTO.setAppovalStatus((String) array[8]);
                }
                if (array[15] != null) {
                    summaryDTO.setAstCode((String) array[15]);
                } else {
                    summaryDTO.setAstCode("");
                }
                if (array[16] != null) {
                    summaryDTO.setAstAppNo((String) array[16]);
                } else {
                    summaryDTO.setAstAppNo("");
                }
                if (array[12] != null) {
                    summaryDTO.setCostCenterDesc((String) array[12]);
                }
                // #39875
                /*
                 * if (array[11] != null) { List<AssetFunctionalLocation> funlocLists = funlocList.stream().parallel()
                 * .filter(func -> func.getFuncLocationId().equals(convertBigIntTOLong(array[11]))) .collect(Collectors.toList());
                 * if (funlocLists != null && !funlocLists.isEmpty()) {
                 * summaryDTO.setLocation(funlocLists.get(0).getFuncLocationCode()); } }
                 */
                if (array[11] != null) {
                    LocationMasEntity locEntity = ApplicationContextProvider.getApplicationContext()
                            .getBean(ILocationMasService.class)
                            .findbyLocationId(convertBigIntTOLong(array[11]));
                    summaryDTO.setLocation(locEntity != null ? locEntity.getLocNameEng() : "NA");
                }

                if (array[13] != null) {
                    List<TbDepartment> depLists = depList.stream().parallel()
                            .filter(dep -> dep.getDpDeptid().equals(convertBigIntTOLong(array[13])))
                            .collect(Collectors.toList());
                    if (depLists != null && !depLists.isEmpty()) {
                        if (searchDTO.getLangId() == 1) {
                            summaryDTO.setDeptName(depLists.get(0).getDpDeptdesc());
                        } else {
                            summaryDTO.setDeptName(depLists.get(0).getDpNameMar());
                        }
                    }
                }

                // get data from TB_AST_CHART_OF_DEPRETN using asset id
                // D#79446
                List<AssetDepreciationChart> dprCharts = assetDepreciationChart.findDeprtChartByAssetId(summaryDTO.getAstId());
                if (!dprCharts.isEmpty()) {
                    summaryDTO.setDepriChecked(
                            dprCharts.get(0).getDeprApplicable() != null ? dprCharts.get(0).getDeprApplicable() : "N");
                }

                summaryDTOList.add(summaryDTO);
            });
        }

        /*
         * for (int i = 0; i < objList.size(); i++) { Object[] objArry = objList.get(i); AssetInformation information =
         * (AssetInformation) objArry[0]; AssetClassification classification = (AssetClassification) objArry[1]; if (information
         * != null && classification != null) { SummaryDTO summaryDTO = convertEntityToDTO(information, classification,
         * searchDTO); if (summaryDTO != null) summaryDTOList.add(summaryDTO); } }
         */
        // D#34059 order by approval status draft wise
        List<SummaryDTO> draftSummaryList = summaryDTOList.stream()
                .filter(summary -> summary.getAppovalStatus() != null
                        && summary.getAppovalStatus().equals(MainetConstants.AssetManagement.APPROVAL_STATUS_DRAFT))
                .collect(Collectors.toList());

        if (!draftSummaryList.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<SummaryDTO> remainList = new ArrayList<SummaryDTO>(CollectionUtils.subtract(summaryDTOList, draftSummaryList));
            List<SummaryDTO> mergeSummaryDTOList = new ArrayList<SummaryDTO>();
            mergeSummaryDTOList.addAll(remainList);
            mergeSummaryDTOList.addAll(draftSummaryList);
            return mergeSummaryDTOList;
        }

        return summaryDTOList;
    }

    @Override
    public List<SummaryDTO> barcodeGenerate(List<SummaryDTO> barcodeList) {
        List<SummaryDTO> imageList = new ArrayList<>();
        if (barcodeList != null && !barcodeList.isEmpty()) {
            if (barcodeLimit == null) {
                barcodeLimit = "100";
            }
            // D#91147
            /*
             * Integer limit = Integer.parseInt(barcodeLimit.toString()); if (limit >= barcodeList.size()) { } else { throw new
             * FrameworkException(MainetConstants.AssetManagement.ASSET_LIMIT_ERROR_CODE +
             * ": Exception occurs because limit is less then  the given list "); }
             */
            for (SummaryDTO dto : barcodeList) {

                if (dto.getBarcode() != null && StringUtils.isNotEmpty(dto.getAstCode())) {
                    try {
                        // dto.setBufferImage(barcodeGenerator.generateBarcode(String.valueOf(dto.getBarcode())));
                        dto.setBufferImage(barcodeGenerator.generateBarcode(dto.getAstCode()));
                    } catch (Exception e) {
                        throw new FrameworkException("error occurs while generating barcode", e);
                    }
                }
                if (dto.getBufferImage() != null) {
                    imageList.add(dto);
                }
            }
        }
        return imageList;

    }

    private Long convertBigIntTOLong(Object obj) {
        BigInteger bigInt = (BigInteger) obj;
        return bigInt.longValue();
    }
}
