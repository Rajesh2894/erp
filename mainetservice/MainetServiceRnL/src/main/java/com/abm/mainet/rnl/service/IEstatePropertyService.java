package com.abm.mainet.rnl.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.rnl.dto.EstatePropGrid;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.EstatePropertyShiftDTO;

/**
 * @author ritesh.patil
 *
 */

@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
@WebService
public interface IEstatePropertyService {

    List<EstatePropGrid> findAllRecords(Long orgId);

    List<EstatePropGrid> findGridFilterRecords(Long orgId, Long estateId);

    EstatePropMaster findEstatePropWithDetailsById(Long propId);

    String save(EstatePropMaster estatePropMaster, List<AttachDocs> attachDocs);

    boolean deleteRecord(Long id, Long empId);

    boolean saveEdit(EstatePropMaster estatePropMaster, EstatePropMaster estatePropMasterEdit, List<AttachDocs> attachDocs,
            List<Long> fileIds, List<Long> childDtlIds,
            List<Long> removeAminities, List<Long> removeFacility, List<Long> removeEvent, List<Long> removeShift);

    List<Character> checkProperty(Long esId);

    EstatePropResponseDTO findPropertyForBooking(Long propId, Long orgId);

    EstatePropMaster findLatLong(Long propId, Long orgId);

    List<Object[]> findPropertiesForEstate(Long orgId, Long esId, String subCategoryName, String prefixName);

    EstatePropMaster findByPropDetailsById(Long propId);

    /**
     * get Property Shift Timing By Shift
     * @param propShift
     * @param propId
     * @return
     */
    EstatePropertyShiftDTO getPropertyShiftTimingByShift(Long propShift, Long propId, Long orgId);

    /**
     * get property Event List By propId
     * @param propId
     * @return
     */
    List<EstatePropertyEventDTO> getPropEventListBypropId(Long propId);

    /**
     * find Facility And Amenities
     * @param propId
     * @return
     */
    EstatePropResponseDTO findFacilityAndAmenities(Long propId, Long orgId);

    EstatePropMaster getPropertyDetailsByPropNumber(String propertyNo, Organisation organisation);

    List<Long> fetchEventIds(List<Long> propEventId);

    void softDeletePropertyData(EstatePropMaster estatePropMaster, List<Long> fileIds, List<Long> childIds,
            List<Long> removeAminities,
            List<Long> removeFacility, List<Long> removeEvent, List<Long> removeShift);

	List<EstatePropGrid> findGridFilterRecordWithActiveStatus(long orgId, Long estatePropGridId);

}
