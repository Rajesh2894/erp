package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IVehicleMaintenanceDAO;
import com.abm.mainet.swm.domain.VehicleMaintenance;
import com.abm.mainet.swm.domain.VehicleMaintenanceHistory;
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;
import com.abm.mainet.swm.mapper.VehicleMaintenanceMapper;
import com.abm.mainet.swm.repository.VehicleMaintenanceRepository;

/**
 * The Class VehicleMaintenanceServiceImpl.
 * @author Lalit.Prusti Created Date : 22-May-2018
 */
@Service
public class VehicleMaintenanceService implements IVehicleMaintenanceService {

    /** The vehicle maintenance repository. */
    @Autowired
    private VehicleMaintenanceRepository vehicleMaintenanceRepository;

    @Autowired
    private IVehicleMaintenanceDAO vehicleMaintenanceDAO;

    /** The vehicle maintenance mapper. */
    @Autowired
    private VehicleMaintenanceMapper vehicleMaintenanceMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.VehicleMaintenanceService#updateVehicleMaintenance(com.abm.mainet.swm.dto.VehicleMaintenanceDTO)
     */
    @Override
    @Transactional
    public VehicleMaintenanceDTO updateVehicleMaintenance(
            VehicleMaintenanceDTO vehicleMaintenanceDTO) {

        VehicleMaintenance master = vehicleMaintenanceMapper
                .mapVehicleMaintenanceDTOToVehicleMaintenance(vehicleMaintenanceDTO);
        master = vehicleMaintenanceRepository.save(master);
        VehicleMaintenanceHistory masterHistory = new VehicleMaintenanceHistory();
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
        VehicleMaintenance master = vehicleMaintenanceMapper
                .mapVehicleMaintenanceDTOToVehicleMaintenance(vehicleMaintenanceDTO);
        master = vehicleMaintenanceRepository.save(master);
        VehicleMaintenanceHistory masterHistory = new VehicleMaintenanceHistory();
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
        VehicleMaintenance vehicleMaintenance = vehicleMaintenanceRepository
                .findOne(vehicleMaintenanceId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        vehicleMaintenance.setUpdatedBy(empId);
        vehicleMaintenance.setUpdatedDate(new Date());
        vehicleMaintenance.setLgIpMacUpd(ipMacAdd);
        vehicleMaintenanceRepository.save(vehicleMaintenance);
        VehicleMaintenanceHistory masterHistory = new VehicleMaintenanceHistory();
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

}
