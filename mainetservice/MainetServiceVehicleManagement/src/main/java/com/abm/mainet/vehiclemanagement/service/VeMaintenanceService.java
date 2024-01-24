package com.abm.mainet.vehiclemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dao.IVehicleMaintenanceDAO;
import com.abm.mainet.vehiclemanagement.domain.OEMWarranty;
import com.abm.mainet.vehiclemanagement.domain.OEMWarrantyDetails;
import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceDetails;
import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceDetailsHistory;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;
import com.abm.mainet.vehiclemanagement.mapper.VehicleMaintenanceMap;
import com.abm.mainet.vehiclemanagement.repository.OEMWarrantyServiceRepository;
import com.abm.mainet.vehiclemanagement.repository.VehicleMaintenanceRepo;

/**
 * The Class VehicleMaintenanceServiceImpl.
 * @author Lalit.Prusti Created Date : 22-May-2018
 */
@Service
@Transactional
public class VeMaintenanceService implements IVehicleMaintenanceService {

    /** The vehicle maintenance repository. */
    @Autowired
    private VehicleMaintenanceRepo vehicleMaintenanceRepository;

    @Autowired
    private IVehicleMaintenanceDAO vehicleMaintenanceDAO;

    /** The vehicle maintenance mapper. */
    @Autowired
    private VehicleMaintenanceMap vehicleMaintenanceMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;
    
    @Autowired
    IVehicleMaintenanceDAO iVehicleMaintenanceDAO;
    
    @Autowired
    private ServiceMasterService  serviceMasterService;

	@Autowired
    ISLRMEmployeeMasterService sLRMEmployeeMasterService;
	
	@Autowired
	VehicleMaintenanceRepo vehicleMaintenanceRepo;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	@Autowired
	private OEMWarrantyServiceRepository oemWarrantyServiceRepository;
	
	@Autowired
	private CommonService commonService;
	
    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.VehicleMaintenanceService#updateVehicleMaintenance(com.abm.mainet.swm.dto.VehicleMaintenanceDTO)
     */
    @Override
    @Transactional
    public VehicleMaintenanceDTO updateVehicleMaintenance(
            VehicleMaintenanceDTO vehicleMaintenanceDTO) {

    	VehicleMaintenanceDetails master = vehicleMaintenanceMapper
                .mapVehicleMaintenanceDTOToVehicleMaintenance(vehicleMaintenanceDTO);
        master = vehicleMaintenanceRepository.save(master);
        VehicleMaintenanceDetailsHistory masterHistory = new VehicleMaintenanceDetailsHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return vehicleMaintenanceMapper.mapVehicleMaintenanceToVehicleMaintenanceDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.VehicleMaintenanceService#saveVehicleMaintenance(com.abm.mainet.swm.dto.VehicleMaintenanceDTO)
     */
    @Override
    @Transactional
    public VehicleMaintenanceDTO saveVehicleMaintenance(
            VehicleMaintenanceDTO vehicleMaintenanceDTO) {
    	VehicleMaintenanceDetails master = vehicleMaintenanceMapper
                .mapVehicleMaintenanceDTOToVehicleMaintenance(vehicleMaintenanceDTO);
        master = vehicleMaintenanceRepository.save(master);
        VehicleMaintenanceDetailsHistory masterHistory = new VehicleMaintenanceDetailsHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
        return vehicleMaintenanceMapper.mapVehicleMaintenanceToVehicleMaintenanceDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMaintenanceService#deleteVehicleMaintenance(java.lang.Long)
     */
    @Override
    @Transactional
    public void deleteVehicleMaintenance(Long vehicleMaintenanceId, Long empId, String ipMacAdd) {
    	VehicleMaintenanceDetails vehicleMaintenance = vehicleMaintenanceRepository
                .findOne(vehicleMaintenanceId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        vehicleMaintenance.setUpdatedBy(empId);
        vehicleMaintenance.setUpdatedDate(new Date());
        vehicleMaintenance.setLgIpMacUpd(ipMacAdd);
        vehicleMaintenanceRepository.save(vehicleMaintenance);
        VehicleMaintenanceDetailsHistory masterHistory = new VehicleMaintenanceDetailsHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(vehicleMaintenance, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMaintenanceService#getVehicleMaintenance(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleMaintenanceDTO getVehicleMaintenance(Long vehicleMaintenanceId) {
        return vehicleMaintenanceMapper.mapVehicleMaintenanceToVehicleMaintenanceDTO(
                vehicleMaintenanceRepository.findOne(vehicleMaintenanceId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleMaintenanceService#searchVehicleMaintenance(java.lang.Long, java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleMaintenanceDTO> searchVehicleMaintenance(Long vehicleType, Long maintenanceType, Date fromDate,
            Date toDate, Long orgId) {
        return vehicleMaintenanceMapper.mapVehicleMaintenanceListToVehicleMaintenanceDTOList(
                vehicleMaintenanceDAO.searchVehicleMaintenance(vehicleType, maintenanceType, fromDate, toDate, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceService#validateVehicleMaintenance(com.abm.mainet.swm.dto.
     * VehicleMaintenanceDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validateVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
        Assert.notNull(vehicleMaintenanceDTO.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(vehicleMaintenanceDTO.getVemMetype(), MainetConstants.PUMP_MASTER_VALIDATION.PUMP_TYPE_NOT_NULL);
        Assert.notNull(vehicleMaintenanceDTO.getVeVetype(), MainetConstants.PUMP_MASTER_VALIDATION.PUMP_NAME_NOT_NULL);
        Assert.notNull(vehicleMaintenanceDTO.getVemDate(), MainetConstants.PUMP_MASTER_VALIDATION.PUMP_ADDRESS_NOT_NULL);
        Assert.notNull(vehicleMaintenanceDTO.getVemEstDowntime(),
                MainetConstants.PUMP_MASTER_VALIDATION.PUMP_VENDOR_NAME_NOT_NULL);
        Assert.notNull(vehicleMaintenanceDTO.getVemDowntimeunit(),
                MainetConstants.PUMP_MASTER_VALIDATION.PUMP_VENDOR_NAME_NOT_NULL);
        Assert.notNull(vehicleMaintenanceDTO.getVemCostincurred(),
                MainetConstants.PUMP_MASTER_VALIDATION.PUMP_VENDOR_NAME_NOT_NULL);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceService#getLastMeterReading(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Long getLastMeterReading(Long vehicleId, Long orgid) {
        return vehicleMaintenanceRepository.findLastMeterReading(orgid, vehicleId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VehicleMaintenanceDTO> searchVehicleMaintenanceData(Long vehicleId, Long orgid) {
    	List<VehicleMaintenanceDetails>  DetailEntity= vehicleMaintenanceRepository.findData(orgid, vehicleId);
    	List<VehicleMaintenanceDTO> dto1=new ArrayList<VehicleMaintenanceDTO>();
		if (DetailEntity != null) {
			DetailEntity.forEach(entity -> {
				VehicleMaintenanceDTO dto = new VehicleMaintenanceDTO();
				BeanUtils.copyProperties(entity, dto);
				dto1.add(dto);
			});

		}	
      return dto1;
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleMaintenanceDTO getDetailByVehNo(String requestNo, Long orgid ) {
    	VehicleMaintenanceDetails list = vehicleMaintenanceRepository.getdData(requestNo, orgid);
		VehicleMaintenanceDTO dto = new VehicleMaintenanceDTO();
		dto.setVemReading(list.getVemReading());
		BeanUtils.copyProperties(list, dto);
			
		return dto;

    }
    
    @Override
	@Transactional
	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction,VehicleMaintenanceDTO vehicleMaintenanceDTO) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		ServiceMaster serviceMas = serviceMasterService.getServiceMasterByShortCode(Constants.MOV, vehicleMaintenanceDTO.getOrgid());
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),vehicleMaintenanceDTO.getOrgid());
		workflowProcessParameter.setProcessName(processName);
		
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	@Transactional
	public VehicleMaintenanceDTO saveVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO,
			OEMWarrantyDTO oemWarrantyDto,long levelCheck,WorkflowTaskAction workflowTaskAction,ApplicantDetailDTO applicantDto) {
		VehicleMaintenanceDetails master = vehicleMaintenanceMapper
                .mapVehicleMaintenanceDTOToVehicleMaintenance(vehicleMaintenanceDTO);
        master = vehicleMaintenanceRepository.save(master);
        VehicleMaintenanceDetailsHistory masterHistory = new VehicleMaintenanceDetailsHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
        String maintCatLookupCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(vehicleMaintenanceDTO.getMaintCategory(),vehicleMaintenanceDTO.getOrgid(), Constants.VEHICLE_MAINT_CATEGORY).getLookUpCode();
        if(3 == levelCheck && maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_AMC) || 5 == levelCheck && maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_WORKSHOP) ) {
	        List<OEMWarrantyDetails> parentEntityList = new ArrayList<OEMWarrantyDetails>();
			OEMWarranty oemWarranty = new OEMWarranty();
			BeanUtils.copyProperties(oemWarrantyDto, oemWarranty);
			oemWarrantyDto.getTbvmoemwarrantydetails().forEach(data -> {
				OEMWarrantyDetails oemDet = new OEMWarrantyDetails();
				BeanUtils.copyProperties(data, oemDet);
				oemDet.setTboemwarranty(oemWarranty);
				parentEntityList.add(oemDet);
			});
			oemWarranty.setTbvmoemwrrantydetails(parentEntityList);
			oemWarrantyServiceRepository.save(oemWarranty);
        }
        if(0L == levelCheck) {
        	applicantDto.setExtIdentifier(vehicleMaintenanceDTO.getMaintCategory());
        	initializeWorkFlowForService(vehicleMaintenanceDTO,applicantDto);
        }else {
			ApplicationContextProvider.getApplicationContext().getBean(IVehicleMaintenanceService.class)
					.updateWorkFlowService(workflowTaskAction, vehicleMaintenanceDTO);
        }
        return vehicleMaintenanceMapper.mapVehicleMaintenanceToVehicleMaintenanceDTO(master);
	}
	
	
	private void initializeWorkFlowForService(VehicleMaintenanceDTO vehicleMaintenanceDTO,ApplicantDetailDTO applicantDto) {
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicationMetaData.setReferenceId(vehicleMaintenanceDTO.getRequestNo());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(vehicleMaintenanceDTO.getOrgid());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	@Transactional
	public void updateWorkFlowStatus(Long vemId, Long orgId, String wfStatus) {
		vehicleMaintenanceRepository.updateWorkFlowStatus(vemId, orgId, wfStatus);
	}

	@Override
	public boolean isForVehicleWorkFlowInProgress(Long veNo, long orgid) {
		long count =vehicleMaintenanceRepo.countByVeIdAndOrgidAndWfStatus(veNo,orgid,"PENDING");
		if(count >= 1L) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
    public String getDecisionByAppId(String requestNo, Long orgId) {
    	return vehicleMaintenanceRepo.findWorkflowTaskDecisionByAppId(requestNo, orgId, Constants.MAINT_INSP_APPR_URL);
     }
	

	@Override
    public List<VehicleMaintenanceDTO> getMaintenanceDataForMaintenanceAlert(List<Long> activeMaintMasVehicleIdList, Long orgId) {
        return vehicleMaintenanceMapper.mapVehicleMaintenanceListToVehicleMaintenanceDTOList(
        		vehicleMaintenanceRepo.getMaintenanceDataForMaintenanceAlert(activeMaintMasVehicleIdList, orgId));
    }
	

}
