package com.abm.mainet.vehiclemanagement.service;

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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dao.IVehicleMasterDAO;
import com.abm.mainet.vehiclemanagement.domain.VeVehicleMaster;
import com.abm.mainet.vehiclemanagement.domain.VmVehicleMasterHistory;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;
import com.abm.mainet.vehiclemanagement.mapper.GeVehicleMasterMapper;
import com.abm.mainet.vehiclemanagement.repository.GeVehicleMasterRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The Class VehicleMasterServiceImpl.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService")
@Api(value = "/vehicleMgmtService")
@Path(value = "/vehicleMgmtService")
public class GeVehicleMasterService implements IGenVehicleMasterService {
	
	
    /** The vehicle master repository. */
    @Autowired
    private GeVehicleMasterRepository vehicleMasterRepository;

    @Autowired
    private IVehicleMasterDAO vehicleMasterDAO;

    /** The vehicle master mapper. */
    @Autowired
    private GeVehicleMasterMapper vehicleMasterMapper;

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
    public List<GenVehicleMasterDTO> searchVehicleByVehicleTypeAndVehicleRegNo(@QueryParam("vehicleType") Long vehicleType,
            @QueryParam("vehicleRegNo") String vehicleRegNo,@QueryParam("department") Long deptId,@QueryParam("location") Long location, @QueryParam("orgId") Long orgId) {
       List<VeVehicleMaster> list=vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, vehicleType, MainetConstants.FlagY, vehicleRegNo,null, deptId,location,
                orgId);
       List<GenVehicleMasterDTO> genVehicleMasterDTOs =new ArrayList<>();
       GenVehicleMasterDTO dto=null;
       for (VeVehicleMaster veVehicleMaster : list) {
    	 dto=new GenVehicleMasterDTO(); 
		 BeanUtils.copyProperties(veVehicleMaster, dto);
		 genVehicleMasterDTOs.add(dto);
	   }
       return genVehicleMasterDTOs;
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<GenVehicleMasterDTO> searchVehicleByVehTypeAndVehRegNo(@QueryParam("vehicleType") Long vehicleType,
            @QueryParam("vehicleRegNo") String vehicleRegNo,@QueryParam("orgId") Long orgId, @QueryParam("mode") String mode) {
    	if((!mode.equals("")) && mode.equals("C")) {
    		return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(null, vehicleType, MainetConstants.FlagY, vehicleRegNo,null,null,orgId));
    	}
    	if((!mode.equals("")) && mode.equals("ADD")) {
    		return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(null, vehicleType, MainetConstants.FlagY, vehicleRegNo,null,null,orgId));
    	}
    	else{
    		Organisation org=new Organisation();
    		org.setOrgid(orgId);
    		List<GenVehicleMasterDTO> dtosList=new ArrayList<>();
    		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
	    		List<VeVehicleMaster> vehicleMasterList=vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(null, vehicleType, null, vehicleRegNo,null,null,orgId);
	    		GenVehicleMasterDTO dto=null;
	    		for (VeVehicleMaster veVehicleMaster : vehicleMasterList) {
	    			dto=new GenVehicleMasterDTO();
	    			BeanUtils.copyProperties(veVehicleMaster, dto);
	    			dtosList.add(dto);
				}
	    		return dtosList;
    		}else {
    			return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(null, vehicleType, null, vehicleRegNo,null,null,orgId));
    		}
    		
    	}
    }
     
    
    @Override
    @Transactional(readOnly = true)
    public List<GenVehicleMasterDTO> searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(@QueryParam("veId") Long veId,@QueryParam("vehicleType") Long vehicleType,
            @QueryParam("vehicleRegNo") String vehicleRegNo,@QueryParam("veDriverName") Long veDriverName,@QueryParam("toDateFlag") String toDateFlag, @QueryParam("veActiveStatus") String veActiveStatus, @QueryParam("orgId") Long orgId) {
     if(veActiveStatus!=null && veActiveStatus.equals( MainetConstants.FlagN)) {
    	 veActiveStatus = null;//for selecting all the records of status N/Y
     }else {
    	 veActiveStatus = MainetConstants.FlagY; 
     }
     
    	return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(veId, vehicleType, veActiveStatus, vehicleRegNo,veDriverName,toDateFlag,
                        orgId));
    }
    
    
    
    @Override
    @Transactional(readOnly = true)
    public List<GenVehicleMasterDTO> searchFuelByVehRegNo(@QueryParam("vehicleType") Long vehicleType,
            @QueryParam("chasisno") String chasisno, @QueryParam("orgId") Long orgId) {
        //return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(null, vehicleType, MainetConstants.FlagY, vehicleRegNo, orgId));
    	return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.searchFuelTypeByVehId(vehicleType, MainetConstants.FlagY, chasisno,orgId));
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<GenVehicleMasterDTO> searchVehicleTypeByDeptId(@QueryParam("deptId") Long deptId,
            @QueryParam("vehicleRegNo") String vehicleRegNo, @QueryParam("orgId") Long orgId) {
        //return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(null, vehicleType, MainetConstants.FlagY, vehicleRegNo, orgId));
    	return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.searchVehicleTypeByDeptId(deptId, MainetConstants.FlagY, orgId));
    }
    
    
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#searchScheduledVehicleByorgId(java.lang.Long)
     */
    @Override
    @POST
    @Path(value = "/search")
    @Transactional
    public List<GenVehicleMasterDTO> searchScheduledVehicleByorgId(@QueryParam("orgId") Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, null, MainetConstants.FlagY, null,null,null,null,
                        orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMasterService#searchVehicle(java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/searchVehicle")
    public List<GenVehicleMasterDTO> searchVehicle(@QueryParam("vehicleType")Long vehicleType, @QueryParam("vehicleRegNo")String vehicleRegNo,
    		@QueryParam("veChasisSrno") String veChasisSrno, @QueryParam("deptId")Long deptId,@QueryParam("location")Long location, @QueryParam("orgId")Long orgId) {
        return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(null, vehicleType, null, vehicleRegNo,veChasisSrno,deptId,location, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#getVehicleByVehicleId(java.lang.Long)
     */

    @Override
    @GET
    @Path(value = "/get/{id}")
    @Transactional(readOnly = true)
    public GenVehicleMasterDTO getVehicleByVehicleId(@PathParam("id") Long vehicleId) {
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(vehicleMasterRepository.findOne(vehicleId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#saveVehicle(com.abm.mainet.swm.dto.VehicleMasterDTO)
     */
 
    
    @Override
    @Transactional
    @POST
    @Path(value = "/saveVehicleReg")
    public GenVehicleMasterDTO saveVehicle(GenVehicleMasterDTO vehicleIdDetails) {

    	VeVehicleMaster master = vehicleMasterMapper.mapVehicleMasterDTOToVehicleMaster(vehicleIdDetails);
        VmVehicleMasterHistory masterHistory = new VmVehicleMasterHistory();
        master.setDeptId(vehicleIdDetails.getDeptId());
        if (vehicleIdDetails.getVeId() == null) {

            if (vehicleIdDetails.getVmVendorid() == null) {
                vehicleIdDetails.setVmVendorid(00L);
            }

            Long ULBVendor = vehicleIdDetails.getOrgid() + vehicleIdDetails.getVmVendorid();

            String VehicleType;
            
//            if (CommonMasterUtility.getCPDDescription(vehicleIdDetails.getVeVetype(), "").equalsIgnoreCase("Auto Tripper")) {
            if (vehicleIdDetails.getVeVetype()!=null) {

                VehicleType = "VE";//AT
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

            //master.setVeNo(master.getVeRegNo());
            masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
        }
        if (masterHistory.getStatus() == null) {
            masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        }

        master = vehicleMasterRepository.save(master);
       // auditService.createHistory(master, masterHistory);
        return vehicleMasterMapper.mapVehicleMasterToVehicleMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMasterService#updateVehicle(com.abm.mainet.swm.dto.VehicleMasterDTO)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public GenVehicleMasterDTO updateVehicle(GenVehicleMasterDTO vehicleIdDetails) {
    	VeVehicleMaster master = vehicleMasterMapper.mapVehicleMasterDTOToVehicleMaster(vehicleIdDetails);
        master = vehicleMasterRepository.save(master);
        VmVehicleMasterHistory masterHistory = new VmVehicleMasterHistory();
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
    	VeVehicleMaster master = vehicleMasterRepository.findOne(vehicleId);
        master.setVeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        vehicleMasterRepository.save(master);
        VmVehicleMasterHistory masterHistory = new VmVehicleMasterHistory();
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
    public boolean validateVehiclenMaster(GenVehicleMasterDTO vehicle) {
        Assert.notNull(vehicle.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(vehicle.getVeVetype(), MainetConstants.VEHICLE_TYPE_NOT_NULL);
        Assert.notNull(vehicle.getVeNo(), MainetConstants.VEHICLE_RegNo_NOT_NULL);

        List<VeVehicleMaster> vehicleMasterList = vehicleMasterDAO.searchVehicleByVehicleTypeAndVehicleRegNo(vehicle.getVeId(),
                null, null, vehicle.getVeNo().trim(),null,null,null, vehicle.getOrgid());
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
		@GET
		@Path(value = "/getAllVehicles/{orgId}")
		@ApiOperation(value = "Return vehicle data", notes = "Return vehicle data", response = Object.class)
		@Transactional
	 public List<Object[]> getAllVehicles(@PathParam("orgId")Long orgId) {

		List<Object[]> listVehicle = vehicleMasterRepository.vehicleData(orgId);
		return listVehicle;

	}

	@Override
	@Transactional
	public List<GenVehicleMasterDTO> fetchVeNoByVehicleTypeIdAndDeptId(Long vehicleType, Long deptId, Long orgId) {
		//List<GenVehicleMasterDTO> listLogBookDTOs = new ArrayList<GenVehicleMasterDTO>();
		// List<VeVehicleMaster> vehicleMasterList = vehicleMasterDAO.fetchVeNoByVehicleTypeId(vehicleType, deptId, orgId);

		return vehicleMasterDAO.fetchVeNoByVehicleTypeId(vehicleType, deptId, orgId).stream().map(entity -> {
		GenVehicleMasterDTO dto = new GenVehicleMasterDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
		}).collect(Collectors.toList());

		//return listLogBookDTOs;
	}


	@Override
	public String fetchVehicleNoByVeId(Long veId) {
		final String veNoDesc = vehicleMasterRepository.fetchVeNoByVeId(veId);
        return veNoDesc;
	}


	@Override
	public List<Object[]> getVehicleByNumber(Long veid, Long orgid) {
		List<Object[]> listVehicle = vehicleMasterRepository.getVehicleByNumber(veid, orgid);
		return listVehicle;
	}
	
	@Override
	public List<Object[]> getVehicleByNo(Long veNo, Long orgid) {
		List<Object[]> listVehicle = vehicleMasterRepository.getVehicleByNo(veNo, orgid);
		return listVehicle;
	}


	@Override
	public List<VehicleScheduleDTO> getVehicleByNumberVe(Long veid, Long orgid) {
		List<VeVehicleMaster> listVehicle = vehicleMasterDAO.getVehicleByNumberVe(veid, orgid);
		if(listVehicle!=null && (!listVehicle.isEmpty())) {
			VehicleScheduleDTO vehicleScheduledto = new VehicleScheduleDTO();
			BeanUtils.copyProperties(listVehicle, vehicleScheduledto);
		}
		return null;
	}


	@Override
	@Transactional(readOnly = true)
	public boolean validateVehicle(GenVehicleMasterDTO genVehicleMasterDTO) {
        List<VeVehicleMaster> vehMasterList = vehicleMasterDAO.searchVehData(genVehicleMasterDTO.getVeEngSrno(), 
        		genVehicleMasterDTO.getVeChasisSrno(), genVehicleMasterDTO.getVeRegNo(), genVehicleMasterDTO.getOrgid(),genVehicleMasterDTO.getVeActive(),genVehicleMasterDTO.getVeId());

        return vehMasterList.isEmpty();
	}


	@Override
	@GET
	@Path(value = "/getVehicleByDept/{department}/{orgid}")
	@ApiOperation(value = "Return vehicle data", notes = "Return vehicle data", response = Object.class)
	@Transactional
	public List<GenVehicleMasterDTO> searchVehicleNoByDeptId(@PathParam("department") Long department, @PathParam("orgid") Long orgid) {
		return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.fetchVeNoByDeptId(department, orgid));
	}

	@Override
	@Transactional
	public List<GenVehicleMasterDTO> getVehDet(String veId, Date vesFromdt, Date vesTodt) {
		return vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(vehicleMasterDAO.getVehDet(veId, vesFromdt,vesTodt));
	}	
	
	@Override
	@GET
	@Path(value = "/getAllVehiclesWithoutEmp/{orgId}")
	@ApiOperation(value = "Return vehicle data", notes = "Return vehicle data", response = Object.class)
	@Transactional
	public List<Object[]> getAllVehiclesWithoutEmp(@PathParam("orgId") Long orgId) {

		List<Object[]> listVehicle = vehicleMasterRepository.vehicleDataWithoutEmp(orgId);
		return listVehicle;
	}


	@Override
	public String fetchChasisNoByVeIdAndOrgid(Long veId, Long orgId) {
		return vehicleMasterRepository.fetchveChasisSrnoByVeIdAndOrgid(veId, orgId);
	}
	
	@Override
	public List<Object[]> getUlbActiveVehiclesForMaintMasterAdd(Long orgId){
		return vehicleMasterRepository.getUlbActiveVehiclesForMaintMasterAdd(orgId, MainetConstants.FlagY);		
	}
	
	@Override
	public List<Object[]> getAllVehicleIdNumberObjectList(Long orgId){
		return vehicleMasterRepository.getAllVehicleIdNumberObjectList(orgId);		
	}	
	
	
	@Override
	public List<GenVehicleMasterDTO> getActiveVehiclesForMaintenanceAlert(List<Long> activeMaintMasVehicleIdList, Long orgId){
		List<GenVehicleMasterDTO> vehicleMasterDTOList = new ArrayList<>();
		List<VeVehicleMaster> veVehicleMasterList = vehicleMasterRepository.getActiveVehiclesForMaintenanceAlert(activeMaintMasVehicleIdList, orgId, MainetConstants.Y_FLAG);
		veVehicleMasterList.forEach(veVehicleMaster -> {
			GenVehicleMasterDTO vehicleMasterDTO = new GenVehicleMasterDTO();
			BeanUtils.copyProperties(veVehicleMaster, vehicleMasterDTO);
			vehicleMasterDTOList.add(vehicleMasterDTO);
		});
		return vehicleMasterDTOList;
	}

}


