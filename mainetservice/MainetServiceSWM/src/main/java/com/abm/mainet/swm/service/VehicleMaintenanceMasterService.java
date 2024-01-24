package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IVehicleMaintenanceMasterDAO;
import com.abm.mainet.swm.domain.VehicleMaintenanceMaster;
import com.abm.mainet.swm.domain.VehicleMaintenanceMasterHistory;
import com.abm.mainet.swm.dto.VehicleMaintenanceMasterDTO;
import com.abm.mainet.swm.mapper.VehicleMaintenanceMasterMapper;
import com.abm.mainet.swm.repository.VehicleMaintenanceMasterRepository;

/**
 * @author Ajay.Kumar
 *
 */
@Service
public class VehicleMaintenanceMasterService implements IVehicleMaintenanceMasterService {

    /**
     * The Vehicle Maintenance Master Repository
     */
    @Autowired
    private VehicleMaintenanceMasterRepository vehicleMaintenanceMasterRepository;

    /**
     * The IVehicle Maintenance Master DAO
     */
    @Autowired
    private IVehicleMaintenanceMasterDAO vehicleMaintenanceMasterDAO;

    /**
     * The Vehicle Maintenance Master Mapper
     */
    @Autowired
    private VehicleMaintenanceMasterMapper vehicleMaintenanceMasterMapper;

    /**
     * The Audit Service
     */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#updateVehicleMaintenance(com.abm.mainet.swm.dto.
     * VehicleMaintenanceMasterDTO)
     */
    @Override
    @Transactional
    public VehicleMaintenanceMasterDTO updateVehicleMaintenance(
            VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {

        VehicleMaintenanceMaster master = vehicleMaintenanceMasterMapper
                .mapVehicleMaintenanceMasterDTOToVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
        master = vehicleMaintenanceMasterRepository.save(master);
        VehicleMaintenanceMasterHistory masterHistory = new VehicleMaintenanceMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#saveVehicleMaintenanceMaster(com.abm.mainet.swm.dto.
     * VehicleMaintenanceMasterDTO)
     */
    @Override
    @Transactional
    public VehicleMaintenanceMasterDTO saveVehicleMaintenanceMaster(
            VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {
        VehicleMaintenanceMaster master = vehicleMaintenanceMasterMapper
                .mapVehicleMaintenanceMasterDTOToVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
        master = vehicleMaintenanceMasterRepository.save(master);
        VehicleMaintenanceMasterHistory masterHistory = new VehicleMaintenanceMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
        return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#deleteVehicleMaintenanceMaster(java.lang.Long,
     * java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional
    public void deleteVehicleMaintenanceMaster(Long vehicleMaintenanceId, Long empId, String ipMacAdd) {
        VehicleMaintenanceMaster master = vehicleMaintenanceMasterRepository
                .findOne(vehicleMaintenanceId);
        master.setVeMeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        vehicleMaintenanceMasterRepository.save(master);
        VehicleMaintenanceMasterHistory masterHistory = new VehicleMaintenanceMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#getVehicleMaintenanceMaster(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleMaintenanceMasterDTO getVehicleMaintenanceMaster(Long vehicleMaintenanceId) {
        return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(
                vehicleMaintenanceMasterRepository.findOne(vehicleMaintenanceId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#getAllVehicleMaintenance(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleMaintenanceMasterDTO> getAllVehicleMaintenance(Long vehicleType, Long orgId) {
        return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(
                vehicleMaintenanceMasterRepository.findAllByVeVetype(vehicleType, orgId));
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#serchVehicleMaintenanceByveDowntimeAndveDowntimeUnit(java.lang.
     * Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleMaintenanceMasterDTO> serchVehicleMaintenanceByveDowntimeAndveDowntimeUnit(Long vehicleType,
            Long orgId) {
        return vehicleMaintenanceMasterMapper
                .mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(vehicleMaintenanceMasterDAO
                        .serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(MainetConstants.FlagY, vehicleType,
                                null, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#serchVehicleMaintenance(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<VehicleMaintenanceMasterDTO> serchVehicleMaintenance(Long vehicleType,
            Long orgId) {
        return vehicleMaintenanceMasterMapper
                .mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(vehicleMaintenanceMasterDAO
                        .serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(null, vehicleType, null, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#validateVehicleMaintenanceMaster(com.abm.mainet.swm.dto.
     * VehicleMaintenanceMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validateVehicleMaintenanceMaster(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {
        Assert.notNull(vehicleMaintenanceMasterDTO.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(vehicleMaintenanceMasterDTO.getVeVetype(), MainetConstants.VEHICLE_TYPE_NOT_NULL);
        Assert.notNull(vehicleMaintenanceMasterDTO.getVeDowntime(), MainetConstants.VEHICLE_ESTIMATED_DOWNTIME_NULL);
        Assert.notNull(vehicleMaintenanceMasterDTO.getVeMainday(), MainetConstants.VEHICLE_MAINTENANCE_AFTER_NOT_NULL);
        List<VehicleMaintenanceMaster> vehicleMaintenanceList = vehicleMaintenanceMasterDAO
                .serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(null,
                        vehicleMaintenanceMasterDTO.getVeVetype(),
                        vehicleMaintenanceMasterDTO.getVeMeId(),
                        vehicleMaintenanceMasterDTO.getOrgid());
        return vehicleMaintenanceList.isEmpty();

    }

}
