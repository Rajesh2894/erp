/**
 *
 */
package com.abm.mainet.swm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.swm.domain.DoorToDoorGarbageCollection;
import com.abm.mainet.swm.dto.AreaWiseDto;
import com.abm.mainet.swm.dto.DoorToDoorGarbageCollectionDTO;
import com.abm.mainet.swm.mapper.DoorToDoorGarbageCollectionMapper;
import com.abm.mainet.swm.repository.DoorToDoorGarbageCollectionRepository;

/**
 * The Class DoorToDoorGarbageCollectionServiceImpl.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IDoorToDoorGarbageCollectionService")
@Path(value = "/garbageCollectionService")
public class DoorToDoorGarbageCollectionService implements IDoorToDoorGarbageCollectionService {

    /**
     * Door To Door Garbage Collection Repository
     */
    @Autowired
    private DoorToDoorGarbageCollectionRepository collectionRepository;

    /**
     * Door To Door Garbage Collection Mapper
     */
    @Autowired
    private DoorToDoorGarbageCollectionMapper collectionMapper;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDoorToDoorGarbageCollectionService#delete(java.lang.Long, java.lang.Long,
     * java.lang.String)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void delete(Long collectionId, Long empId, String ipMacAdd) {
        DoorToDoorGarbageCollection master = collectionRepository.findOne(collectionId);
        // TODO: DELETE FLAG NOT AVAILABLE :: MainetConstants.IsDeleted.DELETE
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        collectionRepository.save(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDoorToDoorGarbageCollectionService#getById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/get/{id}")
    public DoorToDoorGarbageCollectionDTO getById(@PathParam("id") Long collection) {
        return collectionMapper
                .mapDoorToDoorGarbageCollectionToDoorToDoorGarbageCollectionDTO(
                        collectionRepository.findOne(collection));
    }

    private DoorToDoorGarbageCollection mapped(DoorToDoorGarbageCollectionDTO collectionDetails) {
        DoorToDoorGarbageCollection master = collectionMapper
                .mapDoorToDoorGarbageCollectionDTOToDoorToDoorGarbageCollection(collectionDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.IDoorToDoorGarbageCollectionService#save(com.abm.mainet.swm.dto.DoorToDoorGarbageCollectionDTO)
     */
    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public DoorToDoorGarbageCollectionDTO save(@RequestBody DoorToDoorGarbageCollectionDTO collectionDetails) {
        DoorToDoorGarbageCollection master = mapped(collectionDetails);
        master = collectionRepository.save(master);
        return collectionMapper.mapDoorToDoorGarbageCollectionToDoorToDoorGarbageCollectionDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDoorToDoorGarbageCollectionService#update(com.abm.mainet.swm.dto.
     * DoorToDoorGarbageCollectionDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public DoorToDoorGarbageCollectionDTO update(DoorToDoorGarbageCollectionDTO collectionDetails) {
        DoorToDoorGarbageCollection master = mapped(collectionDetails);
        master = collectionRepository.save(master);
        return collectionMapper.mapDoorToDoorGarbageCollectionToDoorToDoorGarbageCollectionDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDoorToDoorGarbageCollectionService#getAllAreaWiseSurveyData(long, java.lang.String,
     * java.util.Date, java.util.Date)
     */

    @Override
    @Transactional(readOnly = true)
    public AreaWiseDto getAllAreaWiseSurveyData(long orgId, String ptype, Date fromDate, Date toDate) {
        List<Object[]> areaWiseSurveyDetails = collectionRepository.findAllAreaWiseSurveyData(orgId, ptype, fromDate, toDate);
        AreaWiseDto areaWiseDto = null;
        List<AreaWiseDto> areaWiseDtoList = new ArrayList<>();
        if (areaWiseSurveyDetails != null && !areaWiseSurveyDetails.isEmpty()) {
            for (Object[] surveyDto : areaWiseSurveyDetails) {
                areaWiseDto = new AreaWiseDto();
                if (surveyDto[1] != null) {
                    areaWiseDto.setLocName(surveyDto[1].toString());
                }
                if (surveyDto[3] != null) {
                    areaWiseDto.setLocAdress(surveyDto[3].toString());
                }
                if (surveyDto[5] != null && surveyDto[6]!= null) {
                    areaWiseDto.setWardName(surveyDto[6].toString());
                }else {
                	areaWiseDto.setWardName(surveyDto[5].toString());
                }
                if (surveyDto[6] != null) {
                    areaWiseDto.setZoneName(surveyDto[5].toString());
                }
                if (surveyDto[13] != null) {
                    areaWiseDto.setMobNo(surveyDto[13].toString());
                }
                if (surveyDto[14] != null) {
                    areaWiseDto.setChiefName(surveyDto[14].toString());
                }
                if (surveyDto[17] != null) {
                    areaWiseDto.setRemarks(surveyDto[17].toString());
                }
                areaWiseDtoList.add(areaWiseDto);
            }
            areaWiseDto.setAreaWiseDtoList(areaWiseDtoList);
        }
        return areaWiseDto;
    }

}
