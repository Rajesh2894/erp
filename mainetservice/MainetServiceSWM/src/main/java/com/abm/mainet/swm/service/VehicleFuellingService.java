package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IVehicleFuellingDAO;
import com.abm.mainet.swm.domain.VehicleFuelling;
import com.abm.mainet.swm.domain.VehicleFuellingHistory;
import com.abm.mainet.swm.dto.VehicleFuellingDTO;
import com.abm.mainet.swm.mapper.VehicleFuellingMapper;
import com.abm.mainet.swm.repository.VehicleFuelingRepository;

/**
 * The Class VehicleFuellingServiceImpl.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 25-May-2018
 */
@Service
public class VehicleFuellingService implements IVehicleFuellingService {

    /** The vehicle master repository. */
    @Autowired
    private VehicleFuelingRepository vehicleFuelingRepository;

    @Autowired
    private IVehicleFuellingDAO vehicleFuelingDAO;

    /** The vehicle master mapper. */
    @Autowired
    private VehicleFuellingMapper vehicleFuelingMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelingService#serchVehicleByVehicleTypeAndVehicleRegNo(java.lang.Long,
     * java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleFuellingDTO> searchVehicleFuelling(Long vehicleType, Long pumpId,
            Date fromDate, Date toDate, Long orgId) {
        return vehicleFuelingMapper.mapVehicleFuelingListToVehicleFuelingDTOList(
                vehicleFuelingDAO.searchVehicleFuelling(vehicleType, pumpId, fromDate, toDate, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleFuellingService#getVehicleFuellingByAdviceDateAndPumpId(java.lang.Long,
     * java.util.Date, java.util.Date, java.lang.Long, java.lang.Boolean)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleFuellingDTO> getVehicleFuellingByAdviceDateAndPumpId(Long pumpId,
            Date fromDate, Date toDate, Long orgId, Boolean paid) {
        return vehicleFuelingMapper.mapVehicleFuelingListToVehicleFuelingDTOList(
                vehicleFuelingDAO.getVehicleFuellingByAdviceDateAndPumpId(pumpId, fromDate, toDate, orgId, paid));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelingService#getVehicleByVehicleId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleFuellingDTO getVehicleByVehicleId(Long vehicleFueling) {
        return vehicleFuelingMapper.mapVehicleFuelingToVehicleFuelingDTO(vehicleFuelingRepository.findOne(vehicleFueling));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelingService#saveVehicle(com.abm.mainet.swm.dto.VehicleFuelingDTO)
     */
    @Override
    @Transactional
    public VehicleFuellingDTO saveVehicle(VehicleFuellingDTO vehicleFuelingDetails) {
        VehicleFuelling master = vehicleFuelingMapper.mapVehicleFuelingDTOToVehicleFueling(vehicleFuelingDetails);
        master = vehicleFuelingRepository.save(master);
        VehicleFuellingHistory masterHistory = new VehicleFuellingHistory();
        masterHistory.sethStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
        return vehicleFuelingMapper.mapVehicleFuelingToVehicleFuelingDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelingService#updateVehicle(com.abm.mainet.swm.dto.VehicleFuelingDTO)
     */
    @Override
    @Transactional
    public VehicleFuellingDTO updateVehicle(VehicleFuellingDTO vehicleFuelingDetails) {
        VehicleFuelling master = vehicleFuelingMapper.mapVehicleFuelingDTOToVehicleFueling(vehicleFuelingDetails);
        master = vehicleFuelingRepository.save(master);
        VehicleFuellingHistory masterHistory = new VehicleFuellingHistory();
        masterHistory.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return vehicleFuelingMapper.mapVehicleFuelingToVehicleFuelingDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelingService#deleteVehicle(java.lang.Long)
     */
    @Override
    @Transactional
    public void deleteVehicle(Long vehicleFueling, Long empId, String ipMacAdd) {
        VehicleFuelling master = vehicleFuelingRepository.findOne(vehicleFueling);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        vehicleFuelingRepository.save(master);
        VehicleFuellingHistory masterHistory = new VehicleFuellingHistory();
        masterHistory.sethStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleFuellingService#getLastMeterReading(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Long getLastMeterReading(Long vehicleId, Long orgid) {
        return vehicleFuelingRepository.findLastMeterReading(orgid, vehicleId);
    }

}
