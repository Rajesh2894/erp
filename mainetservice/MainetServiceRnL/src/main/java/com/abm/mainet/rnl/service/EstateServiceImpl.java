package com.abm.mainet.rnl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dao.EstateMasterDao;
import com.abm.mainet.rnl.dao.IReportDAO;
import com.abm.mainet.rnl.domain.EstateEntity;
import com.abm.mainet.rnl.dto.EstateMaster;
import com.abm.mainet.rnl.dto.EstateMasterGrid;
import com.abm.mainet.rnl.repository.EstateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ritesh.patil
 *
 */
@Service("jpaEstateService")
@Repository
@Transactional(readOnly = true)
public class EstateServiceImpl implements IEstateService {
    private static final Logger LOGGER = Logger.getLogger(EstateServiceImpl.class);

    @Autowired
    private EstateRepository estateRepository;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private IAttachDocsDao iAttachDocsDao;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IReportDAO iReportDAO;
    
    @Autowired
    private EstateMasterDao estateMasterDao;

    @Override
    public List<EstateMasterGrid> findGridRecords(final Long orgId) {
        final List<Object[]> objList = estateRepository.findEstateRecords(orgId);
        return getRecords(objList);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#findEstateByLocId(java.lang.Long, java.lang.Long)
     */

    @Override
    public List<EstateMaster> findEstateByLocId(final Long orgId, final Long locId, final int langId) {
        EstateMaster estateMaster = null;
        List<EstateMaster> estateMasters = null;
        final List<Object[]> objList = estateRepository.findEstates(orgId, locId);
        if ((objList != null) && !objList.isEmpty()) {
            estateMasters = new ArrayList<>();
            for (final Object[] obj : objList) {
                estateMaster = new EstateMaster();
                estateMaster.setEsId(Long.valueOf(obj[0].toString()));
                estateMaster.setNameEng(obj[2] == null ? null : String.valueOf(obj[2]));
                if (langId != MainetConstants.ENGLISH) {
                    estateMaster.setNameEng(obj[3] == null ? null : String.valueOf(obj[3]));
                }
                estateMasters.add(estateMaster);
            }
        }
        return estateMasters;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#findGridFilterRecords(java.lang.Long, java.lang.Long,
     * java.lang.Integer)
     */

    @Override
    public List<EstateMasterGrid> findGridFilterRecords(
            final Long orgId, final Long locId, final Long estateId) {
        List<Object[]> objList = null;
        if ((locId != null) && (estateId != null)) {
            objList = estateRepository.findEstateFilterRecordsLocAndEstate(orgId, locId, estateId);
        } else {
            objList = estateRepository.findEstateFilterRecordsLoc(orgId, locId);
        }
        return getRecords(objList);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#save()
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(final EstateMaster estateMaster, final List<AttachDocs> list) {

        final Long javaFq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RnLCommon.RentLease,
                MainetConstants.EstateMaster.TB_RL_ESTATE_MAS, MainetConstants.EstateMaster.ES_CODE, estateMaster.getOrgId(),
                MainetConstants.RnLCommon.Flag_C, null);
        EstateEntity entity = new EstateEntity();
        BeanUtils.copyProperties(estateMaster, entity, MainetConstants.EstateMaster.ExludeCopyArray);
        final LocationMasEntity entity2 = new LocationMasEntity();
        entity2.setLocId(estateMaster.getLocId().longValue());
        entity.setLocationMas(entity2);
        // T#139716
        String estateNo = "E";
        if (estateMaster.getPurpose() != null) {
            // TSCL get location code from location Master
            TbLocationMas locMas = ApplicationContextProvider.getApplicationContext()
                    .getBean(ILocationMasService.class).findById(estateMaster.getLocId());
           
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
            	if (locMas != null && locMas.getLocCode() !=null && !locMas.getLocCode().isEmpty()) {
                     estateNo +=locMas.getLocCode();
                 }
            if (!StringUtils.isEmpty(estateMaster.getAssetNo())) {
            	String no=estateMaster.getAssetNo().substring(estateMaster.getAssetNo().lastIndexOf("/"));
                estateNo += no+"/";
            }
            }
            else {
			if (locMas != null && StringUtils.isNoneBlank(locMas.getLocCode())) {
					estateNo += locMas.getLocCode();
			}
            if (estateMaster.getPurpose() != null) {
                LookUp eprLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(estateMaster.getPurpose(),
                        estateMaster.getOrgId(), "EPR");
                estateNo += eprLookup.getLookUpCode();
            }
            if (estateMaster.getType2() != null) {
                // Estate SubType: Type Of Land
                LookUp subTyLookup = CommonMasterUtility.getHierarchicalLookUp(estateMaster.getType2(), estateMaster.getOrgId());
                estateNo += subTyLookup.getLookUpCode();
            }
            if (estateMaster.getAcqType() != null) {
                LookUp aqmLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(estateMaster.getAcqType(),
                        estateMaster.getOrgId(), "AQM");
                estateNo += aqmLookup.getLookUpCode();
            }
            if (estateMaster.getHoldingType() != null) {
                LookUp aqmLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(estateMaster.getHoldingType(),
                        estateMaster.getOrgId(), "EHT");
                estateNo += aqmLookup.getLookUpCode();
            }
            }
            estateNo += String.format("%02d", javaFq);
            entity.setCode(estateNo);
        } else {
            // normal way
            entity.setCode(MainetConstants.EstateMaster.EST + getNumberBasedOnOrg(entity.getOrgId()) + entity.getOrgId()
                    + getNumberBasedOnFunctionValue(javaFq) + javaFq);
        }

        entity.setIsActive(MainetConstants.RnLCommon.Y);
        entity.setCreatedDate(new Date());
        entity.setLgIpMac(estateMaster.getLgIpMac());
        entity = estateRepository.save(entity);

        if (!list.isEmpty()) {
            for (final AttachDocs attachDocs : list) {
                attachDocs.setIdfId(entity.getCode());
            }
            attachDocsService.saveMasterDocuments(list);
        }
        return entity.getCode();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#deleteRecord(java.lang.Character, java.lang.Long)
     */

    @Override
    @Transactional
    public boolean deleteRecord(final Long id, final Long empId) {
        estateRepository.deleteRecord(MainetConstants.RnLCommon.N, empId, id);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#findById(java.lang.Long)
     */

    @Override
    public EstateMaster findById(final Long esId) {
        final EstateEntity estateEntity = estateRepository.findOne(esId);
        final EstateMaster estateMaster = new EstateMaster();
        BeanUtils.copyProperties(estateEntity, estateMaster);
        estateMaster.setLocId(estateEntity.getLocationMas().getLocId());
        return estateMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#saveEdit(com.abm.mainetservice.rentlease.bean.EstateMaster,
     * java.util.List)
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveEdit(final EstateMaster estateMaster,
            final List<AttachDocs> list, final List<Long> ids) {
        EstateEntity entity = new EstateEntity();
        BeanUtils.copyProperties(estateMaster, entity);
        final LocationMasEntity locationMasEntity = new LocationMasEntity();
        locationMasEntity.setLocId(estateMaster.getLocId().longValue());
        entity.setLocationMas(locationMasEntity);
        entity.setUpdatedDate(new Date());
        entity.setLgIpMacUp(estateMaster.getLgIpMacUp());
        entity = estateRepository.save(entity);
        if ((ids != null) && !ids.isEmpty()) {
            iAttachDocsDao.updateRecord(ids, estateMaster.getUpdatedBy(), MainetConstants.RnLCommon.Flag_D);
        }
        if (!list.isEmpty()) {
            for (final AttachDocs attachDocs : list) {
                attachDocs.setIdfId(entity.getCode());
            }
            attachDocsService.saveMasterDocuments(list);
        }
        return true;
    }

    private List<EstateMasterGrid> getRecords(final List<Object[]> objList) {

        List<EstateMasterGrid> estateMasterGrids = null;
        EstateMasterGrid estateMasterGrid = null;
        if ((objList != null) && !objList.isEmpty()) {
            estateMasterGrids = new ArrayList<>();
            for (final Object[] obj : objList) {
                estateMasterGrid = new EstateMasterGrid();
                estateMasterGrid.setEsId(Long.valueOf(obj[0].toString()));
                estateMasterGrid.setCode(obj[1].toString());
                estateMasterGrid.setNameEng(obj[2] == null ? null : obj[2].toString());
                estateMasterGrid.setNameReg(obj[3] == null ? null : obj[3].toString());
                estateMasterGrid.setLocationEng(obj[4] == null ? null : obj[4].toString());
                estateMasterGrid.setLocationReg(obj[5] == null ? null : obj[5].toString());
                estateMasterGrid.setCat(obj[6] == null ? null : (Character) obj[6]);
                estateMasterGrid.setType(obj[7] == null ? null : Integer.valueOf(obj[7].toString()));
                estateMasterGrid.setSubType(obj[8] == null ? null : Integer.valueOf(obj[8].toString()));
                estateMasterGrid.setPurpose(obj[9] == null ? null : Long.valueOf(obj[9].toString()));
                estateMasterGrid.setAcquisitionType(obj[10] == null ? null : Long.valueOf(obj[10].toString()));;
                estateMasterGrids.add(estateMasterGrid);
            }
        }
        return estateMasterGrids;
    }

    private String getNumberBasedOnFunctionValue(final long no) {
        final int length = (int) Math.log10(no) + 1;
        switch (length) {
        case 1:
            return MainetConstants.RnLCommon.Tripe_Zero;
        case 2:
            return MainetConstants.RnLCommon.Double_Zero;
        case 3:
            return MainetConstants.RnLCommon.Single_Zero;
        }
        return "";
    }

    private String getNumberBasedOnOrg(final long no) {
        final int length = (int) Math.log10(no) + 1;
        switch (length) {
        case 1:
            return MainetConstants.RnLCommon.Double_Zero;
        case 2:
            return MainetConstants.RnLCommon.Single_Zero;
        }
        return "";
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#findEstateRecordsForProperty(java.lang.Long)
     */

    @Override
    public List<Object[]> findEstateRecordsForProperty(
            final Long orgId) {
        return estateRepository.findEstateRecordsForProperty(orgId);
    }

    @Override
    @Transactional
    public EstateMaster getAssetDetailsByAssetCodeAndOrgId(String assetCode, Long orgId) {
        EstateMaster estateMaster = new EstateMaster();
        AssetDetailsDTO assetDetailsDTOReq = new AssetDetailsDTO();
        assetDetailsDTOReq.setOrgId(orgId);
        AssetInformationDTO astinfoDTO = new AssetInformationDTO();
        astinfoDTO.setAstCode(assetCode);
        assetDetailsDTOReq.setAssetInformationDTO(astinfoDTO);
        try {
            ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(assetDetailsDTOReq,
                    ServiceEndpoints.GET_ASSET_DETAILS_BY_ASSETCODE_AND_ORGID);
            if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
                ObjectMapper mapper = new ObjectMapper();
                AssetDetailsDTO assetDetailsDTORes = mapper.convertValue(responseEntity.getBody(), AssetDetailsDTO.class);
                if (assetDetailsDTORes.getAssetInformationDTO() != null) {
                    estateMaster.setNameEng(assetDetailsDTORes.getAssetInformationDTO().getAssetName() != null
                            ? assetDetailsDTORes.getAssetInformationDTO().getAssetName()
                            : "");
                    estateMaster.setNameReg(assetDetailsDTORes.getAssetInformationDTO().getAssetName() != null
                            ? assetDetailsDTORes.getAssetInformationDTO().getAssetName()
                            : "");
                    estateMaster.setRegNo(assetDetailsDTORes.getAssetInformationDTO().getSerialNo() != null
                            ? assetDetailsDTORes.getAssetInformationDTO().getSerialNo()
                            : "");
                    estateMaster.setConstDate(assetDetailsDTORes.getAssetPurchaseInformationDTO().getAstCreationDate() != null
                            ? assetDetailsDTORes.getAssetPurchaseInformationDTO().getAstCreationDate()
                            : null);
                    estateMaster.setRegDate(assetDetailsDTORes.getAssetPurchaseInformationDTO().getDateOfAcquisition() != null
                            ? assetDetailsDTORes.getAssetPurchaseInformationDTO().getDateOfAcquisition()
                            : null);
                    estateMaster.setAcqType(assetDetailsDTORes.getAssetInformationDTO().getAcquisitionMethod()!=null
                    		? assetDetailsDTORes.getAssetInformationDTO().getAcquisitionMethod()
                    		: null);
                   estateMaster.setAddress(assetDetailsDTORes.getAssetClassificationDTO().getAddress()!=null
                           ? assetDetailsDTORes.getAssetClassificationDTO().getAddress()
                           : null );
                   
                   
                    
                    
                    Long noOfFloor = assetDetailsDTORes.getAssetInformationDTO().getAssetSpecificationDTO().getNoOfFloor();
                    estateMaster.setFloors(noOfFloor != null ? noOfFloor.intValue() : null);
                    if (assetDetailsDTORes.getAssetClassificationDTO().getLatitude() != null) {
                        estateMaster.setLatitude(assetDetailsDTORes.getAssetClassificationDTO().getLatitude() != null
                                ? assetDetailsDTORes.getAssetClassificationDTO().getLatitude().toString()
                                : "0");
                        estateMaster.setLongitude(assetDetailsDTORes.getAssetClassificationDTO().getLongitude() != null
                                ? assetDetailsDTORes.getAssetClassificationDTO().getLongitude().toString()
                                : "0");
                        estateMaster.setSurveyNo(assetDetailsDTORes.getAssetClassificationDTO().getSurveyNo() != null
                                ? assetDetailsDTORes.getAssetClassificationDTO().getSurveyNo()
                                : "");
                        estateMaster.setLocId(assetDetailsDTORes.getAssetClassificationDTO().getLocation() != null
                                ? assetDetailsDTORes.getAssetClassificationDTO().getLocation()
                                : null);// location
                       
                        
                        
                    }

                }

            }
        } catch (Exception ex) {
            throw new FrameworkException("Exception occured while calling get asset details :" + assetDetailsDTOReq, ex);
        }
        return estateMaster;
    }

	@Override
    public List<String> fetchAssetCodesByLookupIds(List<Long> lookupIds, Long orgId, String modeType) {
		List<String> assetCodes = new ArrayList<>();
		// List<String> assetInfo = iReportDAO.fetchAssetCodesByAssetClassIds(lookupIds,
		// orgId);
		// check in estate master (tb_rl_estate_mas) asset code is equal to asset id
		// than ignore asset code
		// D#77288

		List<String> assetInfo = estateRepository.fetchAssetCodesByAssetClassIds(lookupIds, orgId);
		if (modeType == null) {
			assetCodes = assetInfo.stream()
					.filter(asset -> asset != null && !estateRepository.checkAssetCodePresent(asset, orgId))
					.collect(Collectors.toList());
		} else {
			assetCodes = assetInfo.stream().filter(asset -> asset != null).collect(Collectors.toList());
		}
		return assetCodes;
    }
	
	@Override
	public List<Object[]> fetchAssetCodesAndAssetNameByAssetClassIds(List<Long> lookupIds, Long orgId, String modeType) {
		List<Object[]> assetCodes = new ArrayList<>();
		List<Object[]> assetInfo = estateRepository.fetchAssetCodesAndAssetNameByAssetClassIds(lookupIds, orgId);
		for (Object[] assest : assetInfo) {
			if (assest[0] != null && assest[1] != null) {
				assetCodes.add(assest);
			}
		}
		return assetCodes;
	}

    @Override
    public Boolean checkEstateRegNoExist(Long esId, String regNo, Long orgId) {
        return iReportDAO.checkEstateRegNoExist(esId, regNo, orgId);
    }
    
	// to find records by purpose
	@Override
    public List<EstateMasterGrid> findGridRecordsByPurpose(
            final Long orgId, final Long purpose) {
        List<Object[]> objList = null;
        if (purpose != null) {
            objList = estateRepository.findEstateFilterRecordsPurpose(orgId, purpose);
        }
        return getRecords(objList);
	}
    
    @Override
    public List<EstateMasterGrid> searchRecordsByParameters(
            Long orgId,Long locationId,Long estateId,Long purpose,Integer type1,Integer type2, Long acqType) {
        List<Object[]> objList = null;
        objList = estateMasterDao.searchData(orgId, locationId, estateId, purpose, type1, type2, acqType);
        return getRecords(objList);
    }
}
