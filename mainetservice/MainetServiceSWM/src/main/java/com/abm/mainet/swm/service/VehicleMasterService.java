package com.abm.mainet.swm.service;

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
import javax.ws.rs.QueryParam;

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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dao.IVehicleMasterDAO;
import com.abm.mainet.swm.domain.VehicleMaster;
import com.abm.mainet.swm.domain.VehicleMasterHistory;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.mapper.VehicleMasterMapper;
import com.abm.mainet.swm.repository.VehicleMasterRepository;

/**
 * The Class VehicleMasterServiceImpl.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IVehicleMasterService")
@Path(value = "/vehicleinfoservice")
public class VehicleMasterService implements IVehicleMasterService {

    /** The vehicle master repository. */
    @Autowired
    private VehicleMasterRepository vehicleMasterRepository;

    @Autowired
    private IVehicleMasterDAO vehicleMasterDAO;

    /** The vehicle master mapper. */
    @Autowired
    private VehicleMasterMapper vehicleMasterMapper;

    /**
     * The Seq Gen Function Utility
     */
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    /**
     * The IOrganisation Service
     */
    @Autowired
    private IOrganisationService organisationService;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#serchVehicleByVehicleTypeAndVehicleRegNo(java.lang.Long,
     * java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleMasterDTO> searchVehicleByVehicleTypeAndVehicleRegNo(@QueryParam("vehicleType") Long vehicleType,
            @QueryParam("vehicleRegNo") String vehicleRegNo, @QueryParam("orgId") Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, vehicleType, MainetConstants.FlagY, vehicleRegNo,
                        orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#searchScheduledVehicleByorgId(java.lang.Long)
     */
    @Override
    @POST
    @Path(value = "/search")
    @Transactional
    public List<VehicleMasterDTO> searchScheduledVehicleByorgId(@QueryParam("orgId") Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, null, MainetConstants.FlagY, null,
                        orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#searchVehicle(java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<VehicleMasterDTO> searchVehicle(Long vehicleType,
            String vehicleRegNo, Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, vehicleType, null, vehicleRegNo, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#getVehicleByVehicleId(java.lang.Long)
     */

    @Override
    @GET
    @Path(value = "/get/{id}")
    @Transactional(readOnly = true)
    public VehicleMasterDTO getVehicleByVehicleId(@PathParam("id") Long vehicleId) {
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(vehicleMasterRepository.findOne(vehicleId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#saveVehicle(com.abm.mainet.swm.dto.VehicleMasterDTO)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public VehicleMasterDTO saveVehicle(VehicleMasterDTO vehicleIdDetails) {

        VehicleMaster master = vehicleMasterMapper.mapVehicleMasterDTOToVehicleMaster(vehicleIdDetails);
        VehicleMasterHistory masterHistory = new VehicleMasterHistory();

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

            final Long sequenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.SolidWasteManagement.SHORT_CODE,
                    "TB_SW_VEHICLE_MAST", "VE_ID", vehicleIdDetails.getOrgid(),
                    MainetConstants.FlagC, ULBVendor);

            String vehicleName = VehicleType + MainetConstants.HYPHEN + org.getOrgShortNm() + MainetConstants.HYPHEN
                    + String.format("%02d", vehicleIdDetails.getVmVendorid()) + MainetConstants.HYPHEN
                    + String.format("%03d", sequenceNo);
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
				master.setVeNo(master.getVeRegNo());
			} else {
				master.setVeNo(vehicleName);
			}

            masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
        }
        else {
        	if (Utility.isEnvPrefixAvailable( organisationService.getOrganisationById(vehicleIdDetails.getOrgid()), MainetConstants.ENV_TSCL)) {
				master.setVeNo(master.getVeRegNo());
			}
        }
        if (masterHistory.getStatus() == null) {
            masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        }

        master = vehicleMasterRepository.save(master);
        auditService.createHistory(master, masterHistory);
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#updateVehicle(com.abm.mainet.swm.dto.VehicleMasterDTO)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public VehicleMasterDTO updateVehicle(VehicleMasterDTO vehicleIdDetails) {
        VehicleMaster master = vehicleMasterMapper.mapVehicleMasterDTOToVehicleMaster(vehicleIdDetails);
        master = vehicleMasterRepository.save(master);
        VehicleMasterHistory masterHistory = new VehicleMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#deleteVehicle(java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void deleteVehicle(Long vehicleId, Long empId, String ipMacAdd) {
        VehicleMaster master = vehicleMasterRepository.findOne(vehicleId);
        master.setVeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        vehicleMasterRepository.save(master);
        VehicleMasterHistory masterHistory = new VehicleMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#validateVehiclenMaster(com.abm.mainet.swm.dto.VehicleMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public boolean validateVehiclenMaster(VehicleMasterDTO vehicle) {
        Assert.notNull(vehicle.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(vehicle.getVeVetype(), MainetConstants.VEHICLE_TYPE_NOT_NULL);
        Assert.notNull(vehicle.getVeNo(), MainetConstants.VEHICLE_RegNo_NOT_NULL);

        List<VehicleMaster> vehicleMasterList = vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(vehicle.getVeId(),
                null, null, vehicle.getVeNo().trim(), vehicle.getOrgid());
        return vehicleMasterList.isEmpty();
    }

}
