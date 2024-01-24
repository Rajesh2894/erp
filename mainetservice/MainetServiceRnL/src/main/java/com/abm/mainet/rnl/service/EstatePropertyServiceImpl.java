package com.abm.mainet.rnl.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.domain.EstateEntity;
import com.abm.mainet.rnl.domain.EstatePropertyAminity;
import com.abm.mainet.rnl.domain.EstatePropertyDetails;
import com.abm.mainet.rnl.domain.EstatePropertyEntity;
import com.abm.mainet.rnl.domain.EstatePropertyEvent;
import com.abm.mainet.rnl.domain.EstatePropertyShift;
import com.abm.mainet.rnl.dto.EstatePropDtlMaster;
import com.abm.mainet.rnl.dto.EstatePropGrid;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyAmenityDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.EstatePropertyShiftDTO;
import com.abm.mainet.rnl.repository.EstatePropertyRepository;
import com.abm.mainet.rnl.repository.EstateRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author ritesh.patil
 *
 */

@WebService(endpointInterface = "com.abm.mainet.rnl.service.IEstatePropertyService")
@Api(value = "/estateProperty")
@Path("/estateProperty")
@Service("jpaEstatePropertyService")
@Repository
@Transactional(readOnly = true)
public class EstatePropertyServiceImpl implements IEstatePropertyService {

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private IAttachDocsDao iAttachDocsDao;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private EstatePropertyRepository estatePropertyRepository;

    @Autowired
    private EstateRepository estateRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.ITenantService#findAllTenantRecords(java.lang.Long)
     */
    @Override
    public List<EstatePropGrid> findAllRecords(
            final Long orgId) {
        // As discuss with BA
        // final List<Object[]> objList = estatePropertyRepository.findAllRecords(orgId, MainetConstants.RnLCommon.Y);
        final List<Object[]> objList = estatePropertyRepository.findAllRecords(orgId);
        return getRecords(objList);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#findGridFilterRecords(java.lang.Long, java.lang.Long,
     * java.lang.Integer)
     */
    @Override
    public List<EstatePropGrid> findGridFilterRecords(
            final Long orgId, final Long estateId) {
        final List<Object[]> objList = estatePropertyRepository.findAllFilterRecords(orgId, estateId);
        return getRecords(objList);
    }
   
    @Override
	public List<EstatePropGrid> findGridFilterRecordWithActiveStatus(long orgId, Long estatePropGridId) {
    	final List<Object[]> objList = estatePropertyRepository.findAllFilterRecordsWithActiveStatus(orgId, estatePropGridId);
        return getRecords(objList);
	}
    
    private List<EstatePropGrid> getRecords(final List<Object[]> objList) {
        List<EstatePropGrid> list = null;
        EstatePropGrid estatePropGrid = null;
        if ((objList != null) && !objList.isEmpty()) {
            list = new ArrayList<>();
            for (final Object[] obj : objList) {
                estatePropGrid = new EstatePropGrid();
                estatePropGrid.setPropId((Long.valueOf(String.valueOf(obj[0]))));
                if (obj[1] != null) {
                    estatePropGrid.setCode(String.valueOf(obj[1]));
                    }
                estatePropGrid.setName(String.valueOf(obj[2]));
                if (obj[3] != null) {
                    estatePropGrid.setUnitNo(Integer.valueOf(String.valueOf(obj[3])));
                }
                estatePropGrid.setOccupancy((Integer.valueOf(String.valueOf(obj[4]))));
                if (obj[5] != null) {
                estatePropGrid.setUsage((Integer.valueOf(String.valueOf(obj[5]))));
                }
                if (obj[6] != null) {
                    estatePropGrid.setFloor((Integer.valueOf(String.valueOf(obj[6]))));
                }
                if (obj[7] != null) {
                estatePropGrid.setTotalArea((Double.valueOf(String.valueOf(obj[7]))));
                }
                list.add(estatePropGrid);
            }
        }
        return list;
    }
    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#save()
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(final EstatePropMaster estatePropMaster, final List<AttachDocs> list) {
    	 final Long javaFq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RnLCommon.RentLease,
                 MainetConstants.PropMaster.TB_RL_PROPERTY_MAS,MainetConstants.EstateMaster.ES_CODE, estatePropMaster.getOrgId(),
                 MainetConstants.RnLCommon.Flag_C, null);
        EstatePropertyEntity entity = new EstatePropertyEntity();
        BeanUtils.copyProperties(estatePropMaster, entity, MainetConstants.PropMaster.ExludeCopyArrayProp);
        // T#139716
        // first get the record from estate Master
        EstateEntity estateDBEntity = estateRepository.findOne(estatePropMaster.getEstateId());
        if (estateDBEntity != null && estateDBEntity.getPurpose() != null) {
            String propNo = "P";
            // TSCL get location code from location Master
            TbLocationMas locMas = ApplicationContextProvider.getApplicationContext()
                    .getBean(ILocationMasService.class).findById(estateDBEntity.getLocationMas().getLocId());
            
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
            	
                if (locMas != null &&locMas.getLocCode()!=null ) {
                    propNo += locMas.getLocCode();
                }
                
                if (!StringUtils.isEmpty(estateDBEntity.getAssetNo())) {
                	String no=estateDBEntity.getAssetNo().substring(estateDBEntity.getAssetNo().lastIndexOf("/"));
                	 propNo += no;
                }
                
               if(!StringUtils.isEmpty(estateDBEntity.getCode())) {
            	   String no=estateDBEntity.getCode().substring(estateDBEntity.getCode().lastIndexOf("/"));
            	   propNo += no;
               }
                
               if("S".equals(estateDBEntity.getCategory().toString()))
               {
            	   propNo += "/0001";
                }
               else if("G".equals(estateDBEntity.getCategory().toString())){
            	   
    		       long id =estatePropertyRepository.getEstateCount(estateDBEntity.getEsId(), estateDBEntity.getOrgId());
            	      id=id+1;
            	  
    		       propNo += "/" + String.format("%04d", id);
            	   
            }
         
            }else {
            
            if(locMas != null && StringUtils.isNoneBlank(locMas.getLocCode())) {
                propNo += locMas.getLocCode();
            }
            if (estateDBEntity.getPurpose() != null) {
                LookUp eprLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(estateDBEntity.getPurpose(),
                        estateDBEntity.getOrgId(), "EPR");
                propNo += eprLookup.getLookUpCode();
            }
            propNo += "/" + String.format("%03d", javaFq);
            }
            entity.setCode(propNo);
        } 
       
          else {
            entity.setCode(MainetConstants.PropMaster.PropShortType + getESTCode(estatePropMaster.getEstatecode())
                    + getNumberBasedOnFunctionValue(javaFq) + javaFq);
        }

        // entity.setStatus(MainetConstants.RnLCommon.Y);
        entity.setCreatedDate(new Date());
        if (estatePropMaster.getCourtCase() == null) {
            entity.setCourtCase(MainetConstants.RnLCommon.N);
        }
        if (estatePropMaster.getStopBilling() == null) {
            entity.setStopBilling(MainetConstants.RnLCommon.N);
        }
        entity.setLgIpMac(estatePropMaster.getLgIpMac());
        final EstateEntity estateEntity = new EstateEntity();
        estateEntity.setEsId(estatePropMaster.getEstateId());
        entity.setEstateEntity(estateEntity);
        entity.setFlag(MainetConstants.PropMaster.FLAG_O);
        final List<EstatePropDtlMaster> details = estatePropMaster.getDetails();
        final List<EstatePropertyDetails> entityList = new ArrayList<>();
        EstatePropertyDetails dtlEntity;
        for (final EstatePropDtlMaster dtlMaster : details) {
            dtlEntity = new EstatePropertyDetails();
            BeanUtils.copyProperties(dtlMaster, dtlEntity, MainetConstants.TenantMaster.ExludeCopyArrayTenantOwner);
            dtlEntity.setCreatedDate(new Date());
            dtlEntity.setCreatedBy(entity.getCreatedBy());
            dtlEntity.setLgIpMac(entity.getLgIpMac());
            dtlEntity.setLangId(entity.getLangId());
            dtlEntity.setOrgId(entity.getOrgId());
            dtlEntity.setIsActive(MainetConstants.RnLCommon.Y);
            entityList.add(dtlEntity);
            dtlEntity.setEstatePropertyEntity(entity);
        }

        // Set Data For Property Amenities and Facilities Related Information
        createNewPropertyAmenityFacilityData(estatePropMaster, entity);

        // Set Data For Property Event Related Information
        createNewPropertyEventData(estatePropMaster, entity);

        // Set Data For Property Shift Related Information
        createNewPropertyShiftData(estatePropMaster, entity);

        entity.setEstatePropertyDetails(entityList);
        entity = estatePropertyRepository.save(entity);

        if (!list.isEmpty()) {
            for (final AttachDocs attachDocs : list) {
                attachDocs.setIdfId(entity.getCode());
            }
            attachDocsService.saveMasterDocuments(list);
        }
        return entity.getCode();
    }

    /**
     * create New Property AmenityData
     * @author vishwajeet.kumar
     * @param estatePropMaster
     * @param entity
     */
    private void createNewPropertyAmenityFacilityData(EstatePropMaster estatePropMaster, EstatePropertyEntity entity) {
        if ((estatePropMaster.getAminityDTOlist() != null && !estatePropMaster.getAminityDTOlist().isEmpty())
                || (estatePropMaster.getFacilityDTOlist() != null && !estatePropMaster.getFacilityDTOlist().isEmpty())) {
            List<EstatePropertyAminity> amenitiesFacilityList = new ArrayList<>();
            // check amenity and facility has value or not
            if (estatePropMaster.getAminityDTOlist().get(0).getPropAmtFacility() != null) {
                estatePropMaster.getAminityDTOlist().forEach(amenityDto -> {
                    EstatePropertyAminity propertyAmenities = new EstatePropertyAminity();
                    BeanUtils.copyProperties(amenityDto, propertyAmenities);
                    setCommonFields(propertyAmenities, entity);
                    propertyAmenities.setPropType("A");
                    propertyAmenities.setEstatePropertyMasterAmenities(entity);
                    amenitiesFacilityList.add(propertyAmenities);
                });
            }

            if (estatePropMaster.getFacilityDTOlist().get(0).getPropAmtFacility() != null) {
                estatePropMaster.getFacilityDTOlist().forEach(facilityDto -> {
                    EstatePropertyAminity propertyFacilities = new EstatePropertyAminity();
                    BeanUtils.copyProperties(facilityDto, propertyFacilities);
                    setCommonFields(propertyFacilities, entity);
                    propertyFacilities.setPropType("F");
                    propertyFacilities.setEstatePropertyMasterAmenities(entity);
                    amenitiesFacilityList.add(propertyFacilities);
                });
            }
            entity.setEstateAmenities(amenitiesFacilityList);
        }
    }

    /**
     * set Common Fields for Amenity And facility
     * @param aminityFacEntity
     * @param entity
     */
    private void setCommonFields(EstatePropertyAminity aminityFacEntity, EstatePropertyEntity entity) {
        aminityFacEntity.setCreatedDate(new Date());
        aminityFacEntity.setCreatedBy(entity.getCreatedBy());
        aminityFacEntity.setLgIpMac(entity.getLgIpMac());
        aminityFacEntity.setLangId(entity.getLangId());
        aminityFacEntity.setOrgId(entity.getOrgId());
        aminityFacEntity.setAmiFacStatus("Y");
    }

    /**
     * create New Property Event Data
     * @author vishwajeet.kumar
     * @param estatePropMaster
     * @param entity
     */
    private void createNewPropertyEventData(EstatePropMaster estatePropMaster, EstatePropertyEntity entity) {
        if (estatePropMaster.getEventDTOList() != null && !estatePropMaster.getEventDTOList().isEmpty()) {
            List<EstatePropertyEvent> eventList = new ArrayList<>();
            if (estatePropMaster.getEventDTOList().get(0).getPropEvent() != null) {
                estatePropMaster.getEventDTOList().forEach(eventDto -> {
                    EstatePropertyEvent propertyEvent = new EstatePropertyEvent();
                    BeanUtils.copyProperties(eventDto, propertyEvent);
                    propertyEvent.setCreatedDate(new Date());
                    propertyEvent.setCreatedBy(entity.getCreatedBy());
                    propertyEvent.setLgIpMac(entity.getLgIpMac());
                    propertyEvent.setLangId(entity.getLangId());
                    propertyEvent.setOrgId(entity.getOrgId());
                    propertyEvent.setEventStatus("Y");
                    propertyEvent.setEstatePropertyMasterEvent(entity);
                    eventList.add(propertyEvent);
                });
            }
            entity.setEstatePropEvent(eventList);
        }
    }

    /**
     * create New Property Shift Data
     * @author vishwajeet.kumar
     * @param estatePropMaster
     * @param entity
     */
    private void createNewPropertyShiftData(EstatePropMaster estatePropMaster, EstatePropertyEntity entity) {
        if (estatePropMaster.getPropertyShiftDTOList() != null && !estatePropMaster.getPropertyShiftDTOList().isEmpty()) {
            List<EstatePropertyShift> shiftList = new ArrayList<>();
            if (estatePropMaster.getPropertyShiftDTOList().get(0).getPropShift() != null) {
                estatePropMaster.getPropertyShiftDTOList().forEach(shiftDto -> {
                    EstatePropertyShift propertyshift = new EstatePropertyShift();
                    BeanUtils.copyProperties(shiftDto, propertyshift);
                    propertyshift.setCreatedDate(new Date());
                    propertyshift.setCreatedBy(entity.getCreatedBy());
                    propertyshift.setLgIpMac(entity.getLgIpMac());
                    propertyshift.setLangId(entity.getLangId());
                    propertyshift.setOrgId(entity.getOrgId());
                    propertyshift.setShiftStatus("Y");
                    propertyshift.setEstatePropertyMasterShift(entity);
                    propertyshift.setPropFromTime(stringToTimeConvet(shiftDto.getStartTime()));
                    propertyshift.setPropToTime(stringToTimeConvet(shiftDto.getEndTime()));
                    shiftList.add(propertyshift);
                });
            }
            entity.setEstatePropShift(shiftList);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#deleteRecord(java.lang.Character, java.lang.Long)
     */
    @Override
    @Transactional
    public boolean deleteRecord(final Long id, final Long empId) {
        estatePropertyRepository.deleteRecord(MainetConstants.RnLCommon.N, empId, id);
        estatePropertyRepository.deleteDetails(MainetConstants.RnLCommon.N, empId, id);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#findById(java.lang.Long)
     */

    @Override
    public EstatePropMaster findEstatePropWithDetailsById(final Long propId) {
        final EstatePropertyEntity estatePropertyEntity = estatePropertyRepository.findOne(propId);
        final EstatePropMaster estatePropMaster = new EstatePropMaster();
        if (estatePropertyEntity != null) {
            estatePropertyEntity.getEstatePropertyDetails().size();
            EstatePropDtlMaster estatePropDtlMaster;
            BeanUtils.copyProperties(estatePropertyEntity, estatePropMaster);
            estatePropMaster.setEstateId(estatePropertyEntity.getEstateEntity().getEsId());
            for (final EstatePropertyDetails estatePropertyDetails : estatePropertyEntity.getEstatePropertyDetails()) {
                estatePropDtlMaster = new EstatePropDtlMaster();
                BeanUtils.copyProperties(estatePropertyDetails, estatePropDtlMaster);
                estatePropMaster.getDetails().add(estatePropDtlMaster);
            }

            // Modified By Vishwajeet.kumar (Details Of Aminity , Facility,Event and Shift)

            // Get Details of Aminity and Facility
            if (!estatePropertyEntity.getEstateAmenities().isEmpty() && estatePropertyEntity.getEstateAmenities() != null) {
                List<EstatePropertyAmenityDTO> facilityDTOList = new ArrayList<>();
                List<EstatePropertyAmenityDTO> aminityDTOList = new ArrayList<>();
                estatePropertyEntity.getEstateAmenities().forEach(facility -> {
                    EstatePropertyAmenityDTO amenityDTO = new EstatePropertyAmenityDTO();
                    BeanUtils.copyProperties(facility, amenityDTO);
                    if (facility.getPropQuantity() != null && facility.getPropType().equals("F")) {
                        facilityDTOList.add(amenityDTO);
                    } else {
                        aminityDTOList.add(amenityDTO);
                    }
                });
                estatePropMaster.setFacilityDTOlist(facilityDTOList);
                estatePropMaster.setAminityDTOlist(aminityDTOList);
            }
            // Get Details Of Property Event

            if (!estatePropertyEntity.getEstatePropEvent().isEmpty() && estatePropertyEntity.getEstatePropEvent() != null) {
                List<EstatePropertyEventDTO> eventDTOList = new ArrayList<>();
                estatePropertyEntity.getEstatePropEvent().forEach(porpEvent -> {
                    EstatePropertyEventDTO estatePropertyEventDTO = new EstatePropertyEventDTO();
                    BeanUtils.copyProperties(porpEvent, estatePropertyEventDTO);
                    eventDTOList.add(estatePropertyEventDTO);
                });
                estatePropMaster.setEventDTOList(eventDTOList);
            }

            // Get Property Shift Details

            if (!estatePropertyEntity.getEstatePropShift().isEmpty() && estatePropertyEntity.getEstatePropShift() != null) {
                List<EstatePropertyShiftDTO> shiftDTOList = new ArrayList<>();
                estatePropertyEntity.getEstatePropShift().forEach(porpShift -> {
                    EstatePropertyShiftDTO porpShiftDTO = new EstatePropertyShiftDTO();
                    BeanUtils.copyProperties(porpShift, porpShiftDTO);
                    if (porpShift.getPropFromTime() != null) {
                        porpShiftDTO.setStartTime(convertTimeToString(porpShift.getPropFromTime()));
                    }
                    if (porpShift.getPropToTime() != null) {
                        porpShiftDTO.setEndTime(convertTimeToString(porpShift.getPropToTime()));
                    }
                    shiftDTOList.add(porpShiftDTO);
                });
                estatePropMaster.setPropertyShiftDTOList(shiftDTOList);
            }

        }
        return estatePropMaster;
    }

    /**
     * convert Time to string format
     * @author vishwajeet.kumar
     * @param propFromTime
     * @return
     */
    private String convertTimeToString(Date date) {
        String dateString = null;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateString = dateFormat.format(date);
        return dateString;

    }

    /**
     * string To Time Convet
     * @author vishwajeet.kumar
     * @param time
     * @return
     */
    public Date stringToTimeConvet(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date timeValue = null;
        if (time != null)
            try {
                timeValue = new Date(formatter.parse(time).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return timeValue;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#saveEdit(com.abm.mainetservice.rentlease.bean.EstateMaster,
     * java.util.List)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveEdit(final EstatePropMaster estatePropMaster, EstatePropMaster estatePropMasterEdit,
            final List<AttachDocs> list, final List<Long> fileIds, final List<Long> childIds, final List<Long> removeAminities,
            final List<Long> removeFacility, final List<Long> removeEvent, final List<Long> removeShift) {
        EstatePropertyEntity entity = new EstatePropertyEntity();
        BeanUtils.copyProperties(estatePropMaster, entity);
        final List<EstatePropDtlMaster> dtlmasters = estatePropMaster.getDetails();
        final List<EstatePropertyDetails> detailsEntity = new ArrayList<>();
        EstatePropertyDetails propDetailEntity;
        for (final EstatePropDtlMaster estatePropDtlMaster : dtlmasters) {
            if (!childIds.contains(estatePropDtlMaster.getPropDetId())) {
                propDetailEntity = new EstatePropertyDetails();
                BeanUtils.copyProperties(estatePropDtlMaster, propDetailEntity);
                if (null != estatePropDtlMaster.getPropDetId()) {
                    propDetailEntity.setUpdatedDate(new Date());
                    propDetailEntity.setUpdatedBy(estatePropMaster.getUpdatedBy());
                    propDetailEntity.setLgIpMacUp(estatePropMaster.getLgIpMacUp());
                } else {
                    propDetailEntity.setCreatedDate(new Date());
                    propDetailEntity.setCreatedBy(estatePropMaster.getUpdatedBy());
                    propDetailEntity.setLgIpMac(estatePropMaster.getLgIpMac());
                    propDetailEntity.setOrgId(entity.getOrgId());
                    propDetailEntity.setIsActive(MainetConstants.RnLCommon.Y);
                }
                propDetailEntity.setEstatePropertyEntity(entity);
                detailsEntity.add(propDetailEntity);
            }
        }

        // Update Property Facilities
        setPropAmenitiesFacilitiesForUpdate(estatePropMaster, estatePropMasterEdit, entity);

        // Update Property Shift details
        setPropertyShiftForUpdate(estatePropMaster, estatePropMasterEdit, entity);

        // Update Property Event details
        setPropEventForUpdate(estatePropMaster, estatePropMasterEdit, entity);

        final EstateEntity estateEntity = new EstateEntity();
        estateEntity.setEsId(estatePropMaster.getEstateId());
        entity.setEstateEntity(estateEntity);
        entity.setEstatePropertyDetails(detailsEntity);
        entity.setUpdatedDate(new Date());
        if (!list.isEmpty()) {
            for (final AttachDocs attachDocs : list) {
                attachDocs.setIdfId(entity.getCode());
            }
            attachDocsService.saveMasterDocuments(list);
        }
        entity = estatePropertyRepository.save(entity);
        // D#86889
        softDeletePropertyData(estatePropMaster, fileIds, childIds, removeAminities, removeFacility, removeEvent, removeShift);
        return true;
    }

    /**
     * set Properties Facilities For Update
     * @author vishwajeet.kumar
     * @param estatePropMaster
     * @param entity
     */
    private void setPropAmenitiesFacilitiesForUpdate(EstatePropMaster estatePropMaster, EstatePropMaster estatePropMasterEdit,
            EstatePropertyEntity entity) {
        List<EstatePropertyAminity> propertyFacilitiesList = new ArrayList<>();
        if (estatePropMaster.getFacilityDTOlist() != null || estatePropMaster.getAminityDTOlist() != null) {
            if (estatePropMaster.getFacilityDTOlist().get(0).getPropAmtFacility() != null) {
                estatePropMaster.getFacilityDTOlist().forEach(facilityDto -> {

                    EstatePropertyAminity estatePropertyFacility = new EstatePropertyAminity();
                    BeanUtils.copyProperties(facilityDto, estatePropertyFacility);
                    if (facilityDto.getPropAmenityId() != null) {
                        estatePropertyFacility.setUpdatedBy(estatePropMaster.getUpdatedBy());
                        estatePropertyFacility.setUpdatedDate(new Date());
                        estatePropertyFacility.setLgIpMacUp(estatePropMaster.getLgIpMacUp());
                    } else {
                        setCommonFields(estatePropertyFacility, entity);
                        estatePropertyFacility.setPropType("F");
                    }
                    estatePropertyFacility.setEstatePropertyMasterAmenities(entity);
                    // check duplicate IDS present or not
                    if (!containsDuplicateId(propertyFacilitiesList, facilityDto.getPropAmenityId())) {
                        propertyFacilitiesList.add(estatePropertyFacility);
                    }

                });
            }

            for (EstatePropertyAmenityDTO facilityDBDTO : estatePropMasterEdit.getFacilityDTOlist()) {
                if (!propertyFacilitiesList.stream()
                        .anyMatch(pf -> pf.getPropAmenityId() != null
                                && pf.getPropAmenityId().equals(facilityDBDTO.getPropAmenityId()))) {
                    EstatePropertyAminity estatePropertyFacilityDB = new EstatePropertyAminity();
                    BeanUtils.copyProperties(facilityDBDTO, estatePropertyFacilityDB);
                    estatePropertyFacilityDB.setEstatePropertyMasterAmenities(entity);
                    propertyFacilitiesList.add(estatePropertyFacilityDB);
                }
            }

            if (estatePropMaster.getAminityDTOlist().get(0).getPropAmtFacility() != null) {
                estatePropMaster.getAminityDTOlist().forEach(amenityDto -> {
                    EstatePropertyAminity estatePropertyAmentity = new EstatePropertyAminity();
                    BeanUtils.copyProperties(amenityDto, estatePropertyAmentity);
                    if (amenityDto.getPropAmenityId() != null) {
                        estatePropertyAmentity.setUpdatedBy(estatePropMaster.getUpdatedBy());
                        estatePropertyAmentity.setUpdatedDate(new Date());
                        estatePropertyAmentity.setLgIpMacUp(estatePropMaster.getLgIpMacUp());
                    } else {
                        setCommonFields(estatePropertyAmentity, entity);
                        estatePropertyAmentity.setPropType("A");
                    }
                    estatePropertyAmentity.setEstatePropertyMasterAmenities(entity);
                    if (!containsDuplicateId(propertyFacilitiesList, amenityDto.getPropAmenityId())) {
                        propertyFacilitiesList.add(estatePropertyAmentity);
                    }
                });
            }
            for (EstatePropertyAmenityDTO amenityDBDTO : estatePropMasterEdit.getAminityDTOlist()) {
                if (!propertyFacilitiesList.stream()
                        .anyMatch(pa -> pa.getPropAmenityId() != null
                                && pa.getPropAmenityId().equals(amenityDBDTO.getPropAmenityId()))) {
                    EstatePropertyAminity estatePropertyAmenityDB = new EstatePropertyAminity();
                    BeanUtils.copyProperties(amenityDBDTO, estatePropertyAmenityDB);
                    estatePropertyAmenityDB.setEstatePropertyMasterAmenities(entity);
                    propertyFacilitiesList.add(estatePropertyAmenityDB);
                }
            }
        }

        entity.setEstateAmenities(propertyFacilitiesList);
    }

    // D#82996
    public boolean containsDuplicateId(final List<EstatePropertyAminity> list, final Long id) {
        return list.stream().anyMatch(o -> o.getPropAmenityId() != null && o.getPropAmenityId().equals(id));
    }

    /**
     * set Property Event ForUpdate
     * @author vishwajeet.kumar
     * @param estatePropMaster
     * @param entity
     */
    private void setPropEventForUpdate(EstatePropMaster estatePropMaster, EstatePropMaster estatePropMasterEdit,
            EstatePropertyEntity entity) {

        if (estatePropMaster.getEventDTOList() != null) {
            List<EstatePropertyEvent> propertyEventList = new ArrayList<>();
            if (estatePropMaster.getEventDTOList().get(0).getPropEvent() != null) {
                estatePropMaster.getEventDTOList().forEach(eventDto -> {
                    EstatePropertyEvent estatePropertyEvent = new EstatePropertyEvent();
                    BeanUtils.copyProperties(eventDto, estatePropertyEvent);
                    if (eventDto.getPropEventId() != null) {
                        estatePropertyEvent.setUpdatedBy(estatePropMaster.getUpdatedBy());
                        estatePropertyEvent.setUpdatedDate(new Date());
                        estatePropertyEvent.setLgIpMacUp(estatePropMaster.getLgIpMacUp());
                    } else {
                        estatePropertyEvent.setCreatedBy(estatePropMaster.getCreatedBy());
                        estatePropertyEvent.setCreatedDate(estatePropMaster.getCreatedDate());
                        estatePropertyEvent.setLgIpMac(estatePropMaster.getLgIpMac());
                        estatePropertyEvent.setOrgId(estatePropMaster.getOrgId());
                        estatePropertyEvent.setLangId(estatePropMaster.getLangId());
                        estatePropertyEvent.setEventStatus("Y");
                    }
                    estatePropertyEvent.setEstatePropertyMasterEvent(entity);
                    // D#82996
                    if (!propertyEventList.stream().anyMatch(event -> event.getPropEventId() != null
                            && event.getPropEventId().equals(eventDto.getPropEventId()))) {
                        propertyEventList.add(estatePropertyEvent);
                    }

                });
            }
            // compare propertyEventList with DB (estatePropMasterEdit) OBJ list of propEventId
            for (EstatePropertyEventDTO eventDBDTO : estatePropMasterEdit.getEventDTOList()) {
                if (!propertyEventList.stream().anyMatch(event -> event.getPropEventId() != null
                        && event.getPropEventId().equals(eventDBDTO.getPropEventId()))) {

                    EstatePropertyEvent estatePropertyEventDB = new EstatePropertyEvent();
                    BeanUtils.copyProperties(eventDBDTO,
                            estatePropertyEventDB);
                    estatePropertyEventDB.setEstatePropertyMasterEvent(entity);
                    propertyEventList.add(estatePropertyEventDB);

                }
            }
            entity.setEstatePropEvent(propertyEventList);
        }

    }

    /**
     * set Property Shift ForUpdate
     * @author vishwajeet.kumar
     * @param estatePropMaster
     * @param entity
     */
    private void setPropertyShiftForUpdate(EstatePropMaster estatePropMaster, EstatePropMaster estatePropMasterEdit,
            EstatePropertyEntity entity) {
        if (estatePropMaster.getPropertyShiftDTOList() != null) {
            List<EstatePropertyShift> propertyShiftList = new ArrayList<>();
            if (estatePropMaster.getPropertyShiftDTOList().get(0).getPropShift() != null) {
                estatePropMaster.getPropertyShiftDTOList().forEach(shiftDto -> {
                    EstatePropertyShift estatePropertyShift = new EstatePropertyShift();
                    BeanUtils.copyProperties(shiftDto, estatePropertyShift);
                    if (shiftDto.getPropShifId() != null) {
                        estatePropertyShift.setUpdatedBy(estatePropMaster.getUpdatedBy());
                        estatePropertyShift.setUpdatedDate(new Date());
                        estatePropertyShift.setLgIpMacUp(estatePropMaster.getLgIpMacUp());
                        estatePropertyShift.setPropFromTime(stringToTimeConvet(shiftDto.getStartTime()));
                        estatePropertyShift.setPropToTime(stringToTimeConvet(shiftDto.getEndTime()));
                    } else {
                        estatePropertyShift.setCreatedBy(estatePropMaster.getCreatedBy());
                        estatePropertyShift.setCreatedDate(estatePropMaster.getCreatedDate());
                        estatePropertyShift.setLgIpMac(estatePropMaster.getLgIpMac());
                        estatePropertyShift.setOrgId(estatePropMaster.getOrgId());
                        estatePropertyShift.setLangId(estatePropMaster.getLangId());
                        estatePropertyShift.setPropFromTime(stringToTimeConvet(shiftDto.getStartTime()));
                        estatePropertyShift.setPropToTime(stringToTimeConvet(shiftDto.getEndTime()));
                        estatePropertyShift.setShiftStatus("Y");
                    }
                    estatePropertyShift.setEstatePropertyMasterShift(entity);
                    // D#82996
                    if (!propertyShiftList.stream().anyMatch(
                            shift -> shift.getPropShifId() != null && shift.getPropShifId().equals(shiftDto.getPropShifId()))) {
                        propertyShiftList.add(estatePropertyShift);
                    }

                });
            } else if (!estatePropMasterEdit.getPropertyShiftDTOList().isEmpty()) {
                EstatePropertyShift estatePropertyShift = new EstatePropertyShift();
                BeanUtils.copyProperties(estatePropMasterEdit.getPropertyShiftDTOList().get(0), estatePropertyShift);
                estatePropertyShift.setEstatePropertyMasterShift(entity);
                propertyShiftList.add(estatePropertyShift);
            }
            entity.setEstatePropShift(propertyShiftList);
        }
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

    private String getESTCode(final String code) {
        return code.substring(3);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstatePropertyService#checkProperty(java.lang.Long)
     */
    @Override
    public List<Character> checkProperty(final Long esId) {
        return estatePropertyRepository.checkPropertyAgainstEstate(esId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstatePropertyService#findPropertyForBooking(java.lang.Long)
     */
    /*
     * @Consumes("application/json")
     * @ApiOperation(value = "find All Booking Details", notes = "find All Booking Details")
     */

    /*
     * @Produces(MediaType.APPLICATION_JSON)
     * @Consumes({ "application/xml", "application/json" })
     */

    @POST
    @Path("/findPropertyForBooking/{propId}/{orgId}")
    @Transactional
    @Override
    public EstatePropResponseDTO findPropertyForBooking(@PathParam(value = "propId") final Long propId,
            @PathParam(value = "orgId") final Long orgId) {
        final EstatePropertyEntity estatePropertyEntity = estatePropertyRepository.findPropertyForBooking(propId);
        final EstatePropResponseDTO estatePropResponseDTO = new EstatePropResponseDTO();
        if (null != estatePropertyEntity) {
            final Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
            estatePropResponseDTO.setEstateCode(estatePropertyEntity.getEstateEntity().getCode());
            estatePropResponseDTO.setEstateName(estatePropertyEntity.getEstateEntity().getNameEng());
            estatePropResponseDTO.setEstateNameReg(estatePropertyEntity.getEstateEntity().getNameReg());
            estatePropResponseDTO.setPropertyNo(estatePropertyEntity.getCode());
            estatePropResponseDTO.setPropName(estatePropertyEntity.getName());
            estatePropResponseDTO
                    .setUnit(estatePropertyEntity.getUnitNo() == null ? "" : String.valueOf(estatePropertyEntity.getUnitNo()));
            if (estatePropertyEntity.getUsage() != null) {
                estatePropResponseDTO.setUsage(CommonMasterUtility
                        .getHierarchicalLookUp(estatePropertyEntity.getUsage(), organisation).getDescLangFirst());
                estatePropResponseDTO.setUsageForm(CommonMasterUtility
                        .getHierarchicalLookUp(estatePropertyEntity.getUsage(), organisation).getDescLangFirst());
            }
            if (estatePropertyEntity.getOccupancy() != null) {
                estatePropResponseDTO.setOccupancy(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropertyEntity.getOccupancy(), organisation).getDescLangFirst());
                estatePropResponseDTO.setOccupancyForm(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropertyEntity.getOccupancy(), organisation).getDescLangSecond());
            }
            if (estatePropertyEntity.getFloor() != null) {
                estatePropResponseDTO.setFloor(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropertyEntity.getFloor(), organisation).getDescLangFirst());
                estatePropResponseDTO.setFloorForm(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropertyEntity.getFloor(), organisation).getDescLangSecond());
            }
            if (estatePropertyEntity.getEstateEntity().getType1() != null) {
                estatePropResponseDTO.setType(
                        CommonMasterUtility.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType1(), organisation)
                                .getDescLangFirst());
                estatePropResponseDTO.setTypeForm(
                        CommonMasterUtility.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType1(), organisation)
                                .getDescLangSecond());
            }
            if (estatePropertyEntity.getEstateEntity().getType2() != null) {
                estatePropResponseDTO.setSubType(
                        CommonMasterUtility.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType2(), organisation)
                                .getDescLangFirst());
                estatePropResponseDTO.setSubTypeForm(
                        CommonMasterUtility.getHierarchicalLookUp(estatePropertyEntity.getEstateEntity().getType2(), organisation)
                                .getDescLangSecond());
            }
            if (estatePropertyEntity.getRoadType() != null) {
                estatePropResponseDTO.setRoadType(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropertyEntity.getRoadType(), organisation).getDescLangFirst());
                estatePropResponseDTO.setRoadTypeForm(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropertyEntity.getRoadType(), organisation).getDescLangSecond());
            }
            estatePropResponseDTO.setTotalArea(String.valueOf(estatePropertyEntity.getTotalArea()));
            estatePropResponseDTO.setPropId(estatePropertyEntity.getPropId());
            estatePropResponseDTO.setNoOfDaysAllowed(estatePropertyEntity.getPropNoDaysAllow());

            List<EstatePropertyEventDTO> eventDTOSList = new ArrayList<EstatePropertyEventDTO>();
            if (estatePropertyEntity.getEstatePropEvent() != null) {
                estatePropertyEntity.getEstatePropEvent().forEach(eventDtos -> {
                    EstatePropertyEventDTO dto = new EstatePropertyEventDTO();
                    BeanUtils.copyProperties(eventDtos, dto);
                    if (eventDtos.getPropEvent() != null) {
                        organisation.setOrgid(orgId);
                        dto.setPropEventDesc(
                                CommonMasterUtility.getNonHierarchicalLookUpObject(eventDtos.getPropEvent(), organisation)
                                        .getDescLangFirst());
                        dto.setPropId(eventDtos.getEstatePropertyMasterEvent().getPropId());
                    }
                    eventDTOSList.add(dto);
                });
            }

            List<EstatePropertyShiftDTO> shiftDTOSList = new ArrayList<EstatePropertyShiftDTO>();
            if (estatePropertyEntity.getEstatePropShift() != null) {

                estatePropertyEntity.getEstatePropShift().forEach(shiftDtos -> {
                    EstatePropertyShiftDTO dto = new EstatePropertyShiftDTO();
                    BeanUtils.copyProperties(shiftDtos, dto);
                    if (shiftDtos.getPropShift() != null) {
                        organisation.setOrgid(orgId);
                        dto.setPropShiftDesc(
                                CommonMasterUtility.getNonHierarchicalLookUpObject(shiftDtos.getPropShift(), organisation)
                                        .getDescLangFirst());
                    }
                    if (shiftDtos.getPropFromTime() != null) {
                        dto.setStartTime(convertTimeToString(shiftDtos.getPropFromTime()));
                    }
                    if (shiftDtos.getPropToTime() != null) {
                        dto.setEndTime(convertTimeToString(shiftDtos.getPropToTime()));
                    }
                    dto.setPropId(shiftDtos.getEstatePropertyMasterShift().getPropId());
                    shiftDTOSList.add(dto);
                });
            }
            estatePropResponseDTO.setEventDTOList(eventDTOSList);
            estatePropResponseDTO.setShiftDTOsList(shiftDTOSList);
        }
        return estatePropResponseDTO;
    }

    @ApiOperation(value = "findLatLong", notes = "findLatLong", response = Object.class)
    @POST
    @Path("/findLatLong/propId{propId}/orgId{orgId}")
    @Transactional
    @Override
    public EstatePropMaster findLatLong(@PathParam(value = "propId") final Long propId,
            @PathParam(value = "orgId") final Long orgId) {
        final List<String[]> objList = estatePropertyRepository.getLatLang(propId, orgId);
        EstatePropMaster master = null;
        if ((objList != null) && !objList.isEmpty()) {
            for (final Object[] obj : objList) {
                master = new EstatePropMaster();
                master.setName(String.valueOf(obj[0]));
                master.setPropLatitude(String.valueOf(obj[1]));
                master.setPropLongitude(String.valueOf(obj[2]));

            }
        }
        return master;
    }

    @Override
    public List<Object[]> findPropertiesForEstate(final Long orgId, final Long esId, final String subCategoryName,
            final String prefixName) {
        return estatePropertyRepository.findPropertiesForEstate(orgId, esId, subCategoryName, prefixName);

    }

    @Override
    public EstatePropMaster findByPropDetailsById(final Long propId) {
        final EstatePropertyEntity estatePropertyEntity = estatePropertyRepository.findOne(propId);
        final EstatePropMaster estatePropMaster = new EstatePropMaster();
        BeanUtils.copyProperties(estatePropertyEntity, estatePropMaster);
        return estatePropMaster;
    }

    @Override
    @Transactional(readOnly = true)
    public EstatePropertyShiftDTO getPropertyShiftTimingByShift(Long propShift, Long propId, Long orgId) {
        EstatePropertyShiftDTO estatePropShiftDto = null;
        EstatePropertyShift estatePropertyShift = estatePropertyRepository.getPropShiftTiming(propShift, propId, orgId);
        if (estatePropertyShift != null) {
            estatePropShiftDto = new EstatePropertyShiftDTO();
            BeanUtils.copyProperties(estatePropertyShift, estatePropShiftDto);
            if (estatePropertyShift.getPropFromTime() != null) {
                estatePropShiftDto.setStartTime(convertTimeToString(estatePropertyShift.getPropFromTime()));
            }
            if (estatePropertyShift.getPropToTime() != null) {
                estatePropShiftDto.setEndTime(convertTimeToString(estatePropertyShift.getPropToTime()));
            }
        }
        return estatePropShiftDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstatePropertyEventDTO> getPropEventListBypropId(Long propId) {
        List<EstatePropertyEventDTO> eventDTOList = new ArrayList<EstatePropertyEventDTO>();
        List<EstatePropertyEvent> propertyEventEntityList = estatePropertyRepository.getAllPropEventDetails(propId);
        if (propertyEventEntityList != null) {
            Organisation organisation = new Organisation();
            propertyEventEntityList.forEach(evet -> {
                EstatePropertyEventDTO eventDTO = new EstatePropertyEventDTO();
                BeanUtils.copyProperties(evet, eventDTO);
                if (evet.getPropEvent() != null) {
                    organisation.setOrgid(evet.getOrgId());
                    eventDTO.setPropEventDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(evet.getPropEvent(), organisation)
                                    .getDescLangFirst());
                }
                eventDTOList.add(eventDTO);
            });
        }
        return eventDTOList;
    }

    @ApiOperation(value = "findFacilityAndAmenities", notes = "findFacilityAndAmenities", response = Object.class)
    @WebMethod(exclude = true)
    @POST
    @Path("/findFacilityAndAmenities/propId{propId}/orgId{orgId}")
    @Override
    @Transactional(readOnly = true)
    public EstatePropResponseDTO findFacilityAndAmenities(@RequestBody @PathParam(value = "propId") Long propId,
            @PathParam(value = "orgId") Long orgId) {
        EstatePropertyEntity entity = estatePropertyRepository.findAmenitiesAndFacilities(propId, orgId);
        EstatePropResponseDTO responseDTO = new EstatePropResponseDTO();
        // Get Details of Aminity and Facility
        if (!entity.getEstateAmenities().isEmpty() && entity.getEstateAmenities() != null) {
            List<EstatePropertyAmenityDTO> facilityDTOList = new ArrayList<>();
            List<EstatePropertyAmenityDTO> aminityDTOList = new ArrayList<>();
            Organisation organisation = new Organisation();
            organisation.setOrgid(entity.getOrgId());
            entity.getEstateAmenities().forEach(facility -> {
                EstatePropertyAmenityDTO amenityDTO = new EstatePropertyAmenityDTO();
                BeanUtils.copyProperties(facility, amenityDTO);
                if (facility.getPropQuantity() != null && facility.getPropType().equals("F")) {
                    if (facility.getPropAmtFacility() != null) {
                        amenityDTO.setPropAmtFacilityDesc(
                                CommonMasterUtility.getNonHierarchicalLookUpObject(facility.getPropAmtFacility(), organisation)
                                        .getDescLangFirst());
                        amenityDTO.setPropQuantity(facility.getPropQuantity());
                    }
                    facilityDTOList.add(amenityDTO);
                } else {
                    amenityDTO.setPropAmtFacilityDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(facility.getPropAmtFacility(), organisation)
                                    .getDescLangFirst());
                    aminityDTOList.add(amenityDTO);
                }
            });
            responseDTO.setFacilityDtoList(facilityDTOList);
            responseDTO.setAmenityDTOsList(aminityDTOList);
        }
        return responseDTO;
    }

    @Override
    @Transactional
    public EstatePropMaster getPropertyDetailsByPropNumber(String propNo, Organisation organisation) {
        EstatePropMaster property = new EstatePropMaster();
        PropertyInputDto propInputDTO = new PropertyInputDto();
        propInputDTO.setPropertyNo(propNo);
        propInputDTO.setOrgId(organisation.getOrgid());
        ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                ServiceEndpoints.PROP_BY_PROP_ID);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
            PropertyDetailDto detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
            List<LookUp> lookupList = CommonMasterUtility.getLevelData("USA", 1, organisation);
            // get lookup id
            for (int i = 0; i < lookupList.size(); i++) {
                if (detailDTO.getUasge().equalsIgnoreCase(lookupList.get(i).getLookUpDesc())) {
                    property.setUsage((int) lookupList.get(i).getLookUpId());
                    break;
                }
            }
            property.setAssesmentPropId(detailDTO.getPropNo());
            property.setName(detailDTO.getPrimaryOwnerName());
        }
        return property;
    }

    @Override
    public List<Long> fetchEventIds(List<Long> propEventId) {

        return estatePropertyRepository.fetchEventIds(propEventId);
    }

    @Override
    @Transactional
    public void softDeletePropertyData(EstatePropMaster estatePropMaster, List<Long> fileIds, List<Long> childIds,
            List<Long> removeAminities,
            List<Long> removeFacility, List<Long> removeEvent, List<Long> removeShift) {
        if (!childIds.isEmpty()) {
            estatePropertyRepository.deleteRecordDetails(MainetConstants.RnLCommon.N, estatePropMaster.getUpdatedBy(), childIds);
        }
        if ((fileIds != null) && !fileIds.isEmpty()) {
            iAttachDocsDao.updateRecord(fileIds, estatePropMaster.getUpdatedBy(), MainetConstants.RnLCommon.Flag_D);
        }
        if (removeAminities != null && !removeAminities.isEmpty()) {
            estatePropertyRepository.updateAminityRemoveFlag(removeAminities);
        }

        if (removeFacility != null && !removeFacility.isEmpty()) {
            estatePropertyRepository.updateFacilityRemoveFlag(removeFacility);
        }

        if (removeEvent != null && !removeEvent.isEmpty()) {
            estatePropertyRepository.updateEventRemoveFlag(removeEvent);
        }
        if (removeShift != null && !removeShift.isEmpty()) {
            estatePropertyRepository.updateShiftRemoveFlag(removeShift);
        }

    }

	

}
