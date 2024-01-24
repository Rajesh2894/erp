/*package com.abm.mainet.vehicle.management.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.vehicle.management.constant.Constants;
import com.abm.mainet.vehicle.management.dao.IVehicleMasterDAO;
import com.abm.mainet.vehicle.management.domain.FmVehicleMaster;
import com.abm.mainet.vehicle.management.domain.FmVehicleMasterHistory;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehicle.management.mapper.GenVehicleMasterMapper;
import com.abm.mainet.vehicle.management.repository.GenVehicleMasterRepository;

import io.swagger.annotations.ApiParam;

*//**
 * The Class VehicleMasterServiceImpl.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 *//*
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.vehicle.management.service.IGenVehicleMasterService")
@Path(value = "/vehicleinfoservice")
public class GenVehicleMasterService implements IGenVehicleMasterService {
	
	
	
    *//** The vehicle master repository. *//*
    @Autowired
    private GenVehicleMasterRepository vehicleMasterRepository;

    @Autowired
    private IVehicleMasterDAO vehicleMasterDAO;

    *//** The vehicle master mapper. *//*
    @Autowired
    private GenVehicleMasterMapper vehicleMasterMapper;

    *//**
     * The Seq Gen Function Utility
     *//*
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    *//**
     * The IOrganisation Service
     *//*
    @Autowired
    private IOrganisationService organisationService;

    *//** The audit service. *//*
    @Autowired
    private AuditService auditService;

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#serchVehicleByVehicleTypeAndVehicleRegNo(java.lang.Long,
     * java.lang.String, java.lang.Long)
     
    @Override
    @Transactional(readOnly = true)
    public List<GenVehicleMasterDTO> searchVehicleByVehicleTypeAndVehicleRegNo(@QueryParam("vehicleType") Long vehicleType,
            @QueryParam("vehicleRegNo") String vehicleRegNo,@QueryParam("department") Long deptId,@QueryParam("location") Long location, @QueryParam("orgId") Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, vehicleType, MainetConstants.FlagY, vehicleRegNo,deptId,location,
                        orgId));
    }

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#searchScheduledVehicleByorgId(java.lang.Long)
     
    @Override
    @POST
    @Path(value = "/search")
    @Transactional
    public List<GenVehicleMasterDTO> searchScheduledVehicleByorgId(@QueryParam("orgId") Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, null, MainetConstants.FlagY, null,null,null,
                        orgId));
    }

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#searchVehicle(java.lang.Long, java.lang.String, java.lang.Long)
     
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<GenVehicleMasterDTO> searchVehicle(Long vehicleType,
            String vehicleRegNo,Long deptId,Long location, Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, vehicleType, null, vehicleRegNo,deptId,location, orgId));
    }

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#getVehicleByVehicleId(java.lang.Long)
     

    @Override
    @GET
    @Path(value = "/get/{id}")
    @Transactional(readOnly = true)
    public GenVehicleMasterDTO getVehicleByVehicleId(@PathParam("id") Long vehicleId) {
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(vehicleMasterRepository.findOne(vehicleId));
    }

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#saveVehicle(com.abm.mainet.swm.dto.VehicleMasterDTO)
     
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public GenVehicleMasterDTO saveVehicle(GenVehicleMasterDTO vehicleIdDetails) {

        FmVehicleMaster master = vehicleMasterMapper.mapVehicleMasterDTOToVehicleMaster(vehicleIdDetails);
        FmVehicleMasterHistory masterHistory = new FmVehicleMasterHistory();
        master.setDeptId(vehicleIdDetails.getDeptId());
        if (vehicleIdDetails.getVeId() == null) {

            if (vehicleIdDetails.getVmVendorid() == null) {
                vehicleIdDetails.setVmVendorid(00L);
            }

            Long ULBVendor = vehicleIdDetails.getOrgid() + vehicleIdDetails.getVmVendorid();

            String VehicleType;

            if (CommonMasterUtility.getCPDDescription(vehicleIdDetails.getVeVetype(), "").equalsIgnoreCase("Auto Tripper")) {
                VehicleType = "AT";
            } else if (CommonMasterUtility.getCPDDescription(vehicleIdDetails.getVeVetype(), "").equalsIgnoreCase("TriCycle")) {
                VehicleType = "TC";
            } else {
                VehicleType = "VE";
            }
            Organisation org = organisationService.getOrganisationById(vehicleIdDetails.getOrgid());

            final Long sequenceNo = seqGenFunctionUtility.generateSequenceNo(Constants.SHORT_CODE,
                    "TB_VEHICLE_MAST", "VE_ID", vehicleIdDetails.getOrgid(),
                    MainetConstants.FlagC, ULBVendor);

            String vehicleName = VehicleType + MainetConstants.HYPHEN + org.getOrgShortNm() + MainetConstants.HYPHEN
                    + String.format("%02d", vehicleIdDetails.getVmVendorid()) + MainetConstants.HYPHEN
                    + String.format("%03d", sequenceNo);

            master.setVeNo(vehicleName);
            masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
        }
        if (masterHistory.getStatus() == null) {
            masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        }

        master = vehicleMasterRepository.save(master);
        auditService.createHistory(master, masterHistory);
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(master);
    }

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#updateVehicle(com.abm.mainet.swm.dto.VehicleMasterDTO)
     
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public GenVehicleMasterDTO updateVehicle(GenVehicleMasterDTO vehicleIdDetails) {
        FmVehicleMaster master = vehicleMasterMapper.mapVehicleMasterDTOToVehicleMaster(vehicleIdDetails);
        master = vehicleMasterRepository.save(master);
        FmVehicleMasterHistory masterHistory = new FmVehicleMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(master);
    }

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#deleteVehicle(java.lang.Long)
     
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void deleteVehicle(Long vehicleId, Long empId, String ipMacAdd) {
        FmVehicleMaster master = vehicleMasterRepository.findOne(vehicleId);
        master.setVeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        vehicleMasterRepository.save(master);
        FmVehicleMasterHistory masterHistory = new FmVehicleMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#validateVehiclenMaster(com.abm.mainet.swm.dto.VehicleMasterDTO)
     
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public boolean validateVehiclenMaster(GenVehicleMasterDTO vehicle) {
        Assert.notNull(vehicle.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(vehicle.getVeVetype(), MainetConstants.VEHICLE_TYPE_NOT_NULL);
        Assert.notNull(vehicle.getVeNo(), MainetConstants.VEHICLE_RegNo_NOT_NULL);

        List<FmVehicleMaster> vehicleMasterList = vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(vehicle.getVeId(),
                null, null, vehicle.getVeNo().trim(),null,null, vehicle.getOrgid());
        return vehicleMasterList.isEmpty();
    }

  

	@Override
	public List<GenVehicleMasterDTO> findAll(
		@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid){
		return vehicleMasterRepository.findByOrgid(orgid).stream().map(entity -> {
			GenVehicleMasterDTO dto = new GenVehicleMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}).collect(Collectors.toList());
	}
    
    
	@Override
	public List<Object[]> getAllVehicles(Long orgId) {

//		List<FireCallRegister> listOccuranceBookEntities = fireCallRepository.findByOrgid(orgId);
		List<Object[]> listVehicle = vehicleMasterRepository.vehicleData(orgId);
		return listVehicle;

	}

	@Override
	@Transactional
	public List<GenVehicleMasterDTO> fetchVeNoByVehicleTypeIdAndDeptId(Long vehicleType, Long deptId, Long orgId) {
		List<GenVehicleMasterDTO> listLogBookDTOs = new ArrayList<GenVehicleMasterDTO>();
		 List<FmVehicleMaster> vehicleMasterList = vehicleMasterDAO.fetchVeNoByVehicleTypeId(vehicleType, deptId, orgId);
		
		return listLogBookDTOs;
	}

	
	 * @Override public List<Object[]> getVehicleDetails(Long orgid) { //
	 * List<FireCallRegister> listOccuranceBookEntities =
	 * fireCallRepository.findByOrgid(orgId); List<Object[]> vehiclDetails =
	 * vehicleMasterRepository.vehicleDetail(orgid);
	 * 
	 * return vehiclDetails;
	 * 
	 * 
	 * }
	 
	
	
}


























*/