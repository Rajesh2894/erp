package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IVehicleFuelReconciationDAO;
import com.abm.mainet.swm.domain.VehicleFuelReconciation;
import com.abm.mainet.swm.domain.VehicleFuelReconciationHistory;
import com.abm.mainet.swm.dto.VehicleFuelReconciationDTO;
import com.abm.mainet.swm.mapper.VehicleFuelReconciationMapper;
import com.abm.mainet.swm.repository.VehicleFuelReconciationRepository;

/**
 * The Class VehicleFuelReconciationServiceImpl.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 19-Jun-2018
 */
@Service
public class VehicleFuelReconciationService implements IVehicleFuelReconciationService {

    /** The Vehicle fuel reconciation repository. */
    @Autowired
    private VehicleFuelReconciationRepository vehicleFuelReconciationRepository;

    /** The Vehicle fuel reconciation DAO. */
    @Autowired
    private IVehicleFuelReconciationDAO VehicleFuelReconciationDAO;

    /** The Vehicle fuel reconciation mapper. */
    @Autowired
    private VehicleFuelReconciationMapper VehicleFuelReconciationMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelReconciationService#delete(java.lang.Long)
     */
    @Override
    @Transactional
    public void delete(Long inrecId, Long empId, String ipMacAdd) {
        VehicleFuelReconciation master = vehicleFuelReconciationRepository.findOne(inrecId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        vehicleFuelReconciationRepository.save(master);
        saveVehicleFuelReconciationHistory(master, MainetConstants.Transaction.Mode.DELETE);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelReconciationService#getById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleFuelReconciationDTO getById(Long inrecId) {
        return VehicleFuelReconciationMapper
                .mapVehicleFuelReconciationToVehicleFuelReconciationDTO(
                        vehicleFuelReconciationRepository.findOne(inrecId));
    }

    /**
     * Mapped.
     *
     * @param VehicleFuelReconciationDetails the vehicle fuel reconciation details
     * @return the vehicle fuel reconciation
     */
    private VehicleFuelReconciation mapped(VehicleFuelReconciationDTO VehicleFuelReconciationDetails) {
        VehicleFuelReconciation master = VehicleFuelReconciationMapper
                .mapVehicleFuelReconciationDTOToVehicleFuelReconciation(VehicleFuelReconciationDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelReconciationService#save(com.abm.mainet.swm.dto.VehicleFuelReconciationDTO)
     */
    @Override
    @Transactional
    public VehicleFuelReconciationDTO save(VehicleFuelReconciationDTO VehicleFuelReconciationDetails) {
        VehicleFuelReconciation master = mapped(VehicleFuelReconciationDetails);
        master = vehicleFuelReconciationRepository.save(master);
        saveVehicleFuelReconciationHistory(master, MainetConstants.Transaction.Mode.ADD);
        return VehicleFuelReconciationMapper.mapVehicleFuelReconciationToVehicleFuelReconciationDTO(master);
    }

    /**
     * Save vehicle fuel reconciation history.
     *
     * @param master the master
     * @param status the status
     */
    private void saveVehicleFuelReconciationHistory(VehicleFuelReconciation master, String status) {
        VehicleFuelReconciationHistory masterHistory = new VehicleFuelReconciationHistory();
        masterHistory.setHStatus(status);
        auditService.createHistory(master, masterHistory);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelReconciationService#update(com.abm.mainet.swm.dto.VehicleFuelReconciationDTO)
     */
    @Override
    @Transactional
    public VehicleFuelReconciationDTO update(VehicleFuelReconciationDTO VehicleFuelReconciationDetails) {
        VehicleFuelReconciation master = mapped(VehicleFuelReconciationDetails);
        master = vehicleFuelReconciationRepository.save(master);
        saveVehicleFuelReconciationHistory(master, MainetConstants.Transaction.Mode.UPDATE);
        return VehicleFuelReconciationMapper.mapVehicleFuelReconciationToVehicleFuelReconciationDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.VehicleFuelReconciationService#search(java.lang.Long, java.util.Date, java.util.Date,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleFuelReconciationDTO> search(Long veId, Date fromDate, Date toDate, Long orgId) {
        return VehicleFuelReconciationMapper.mapVehicleFuelReconciationListToVehicleFuelReconciationDTOList(
                VehicleFuelReconciationDAO.searchVehicleFuelReconciation(veId, fromDate, toDate, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleFuelReconciationService#searchVendorIdByPumpId(java.lang.Long, long)
     */
    @Override
    @Transactional(readOnly = true)
    public Long searchVendorIdByPumpId(Long puId, long orgid) {
        // TODO Auto-generated method stub
        return vehicleFuelReconciationRepository.getVendorIdByPumpId(puId, orgid);
    }

}
