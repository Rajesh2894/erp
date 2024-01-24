/**
 *
 */
package com.abm.mainet.vehiclemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.vehiclemanagement.dao.IPumpMasterDAO;
import com.abm.mainet.vehiclemanagement.domain.FuelPumpMaster;
import com.abm.mainet.vehiclemanagement.domain.FuelPumpMasterHistory;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.mapper.FuelPumpMasterMapper;
import com.abm.mainet.vehiclemanagement.repository.FuelPumpMasterRepository;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 04-May-2018
 */
@Service
public class FuelPumpMasterService implements IPumpMasterService {

    /**
     * The Pump Master Repository
     */
    @Autowired
    private FuelPumpMasterRepository pumpMasterRepository;

    /**
     * The IPumpMaster DAO
     */
    @Autowired
    private IPumpMasterDAO pumpMasterDAO;

    /**
     * The Pump Master Mapper
     */
    @Autowired
    private FuelPumpMasterMapper pumpMasterMapper;

    /**
     * The Audit Service
     */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PumpMasterService#getPumpByPumpId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public PumpMasterDTO getPumpByPumpId(Long pumpId) {
        return pumpMasterMapper.mapPumpMasterToPumpMasterDTO(pumpMasterRepository.findOne(pumpId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PumpMasterService#savePumpMaster(com.abm.mainet.swm.dto.PumpMasterDTO)
     */
    @Override
    @Transactional
    public PumpMasterDTO savePumpMaster(PumpMasterDTO pumpIdDetails, List<Long> removeIds) {
    	FuelPumpMaster master = pumpMasterMapper.mapPumpMasterDTOToPumpMaster(pumpIdDetails);
        master = pumpMasterRepository.save(master);
        FuelPumpMasterHistory masterHistory = new FuelPumpMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
		if (removeIds != null && removeIds.size() != 0) {
        	pumpMasterRepository.removeItemsById(removeIds);
        }
        return pumpMasterMapper.mapPumpMasterToPumpMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PumpMasterService#updatePumpMaster(com.abm.mainet.swm.dto.PumpMasterDTO)
     */
    @Override
    @Transactional
    public PumpMasterDTO updatePumpMaster(PumpMasterDTO pumpIdDetails) {
    	FuelPumpMaster master = pumpMasterMapper.mapPumpMasterDTOToPumpMaster(pumpIdDetails);
        master = pumpMasterRepository.save(master);
        FuelPumpMasterHistory masterHistory = new FuelPumpMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return pumpMasterMapper.mapPumpMasterToPumpMasterDTO(master);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PumpMasterService#deletePumpMaster(java.lang.Long)
     */
    @Override
    @Transactional
    public void deletePumpMaster(Long pumpId, Long empId, String ipMacAdd) {
    	FuelPumpMaster master = pumpMasterRepository.findOne(pumpId);
        master.setPuActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        pumpMasterRepository.save(master);
        FuelPumpMasterHistory masterHistory = new FuelPumpMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PumpMasterService#serchPumpMasterByPumpTypeAndPumpName(java.lang.Long, java.lang.String,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PumpMasterDTO> serchPumpMasterByPumpType(Long pumpType, String puPumpname, Long orgId) {
        return pumpMasterMapper
                .mapPumpMasterListToPumpMasterDTOList(
                        pumpMasterDAO.searchPumpByPumpType(MainetConstants.FlagY, pumpType, null, puPumpname, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IPumpMasterService#serchPumpMaster(java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PumpMasterDTO> serchPumpMaster(Long pumpType, String puPumpname, Long orgId) {
        return pumpMasterMapper
                .mapPumpMasterListToPumpMasterDTOList(
                        pumpMasterDAO.searchPumpByPumpType(null, pumpType, null, puPumpname, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IPumpMasterService#getAllPumpMaster(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PumpMasterDTO> getAllPumpMaster(Long pumpType, Long orgId) {
        return pumpMasterMapper.mapPumpMasterListToPumpMasterDTOList(pumpMasterRepository.findAllBypuPutype(pumpType, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IPumpMasterService#validatePumpMaster(com.abm.mainet.swm.dto.PumpMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validatePumpMaster(PumpMasterDTO pumpMasterDTO) {

        Assert.notNull(pumpMasterDTO.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(pumpMasterDTO.getPuPutype(), MainetConstants.PUMP_MASTER_VALIDATION.PUMP_TYPE_NOT_NULL);
        Assert.notNull(pumpMasterDTO.getPuPumpname(), MainetConstants.PUMP_MASTER_VALIDATION.PUMP_NAME_NOT_NULL);
        Assert.notNull(pumpMasterDTO.getPuAddress(), MainetConstants.PUMP_MASTER_VALIDATION.PUMP_ADDRESS_NOT_NULL);
        Assert.notNull(pumpMasterDTO.getVendorId(), MainetConstants.PUMP_MASTER_VALIDATION.PUMP_VENDOR_NAME_NOT_NULL);

        List<FuelPumpMaster> pumpMasterList = pumpMasterDAO.searchPumpByPumpType(null, pumpMasterDTO.getPuPutype(),
                pumpMasterDTO.getPuId(),
                pumpMasterDTO.getPuPumpname(), pumpMasterDTO.getOrgid());

        return pumpMasterList.isEmpty();

    }

	@Override
	@Transactional(readOnly = true)
	public List<PumpMasterDTO> getPumpIdAndNamesListByVeID(Long vehId, Long orgId) {
		List<Object[]> entitiesList = pumpMasterRepository.getPumpIdAndNameByVeId(vehId, orgId);
		List<PumpMasterDTO> pumpMasterDTOList = new ArrayList<>();
		PumpMasterDTO pumpMasterDTO;
		for (Object[] object : entitiesList) {
			pumpMasterDTO = new PumpMasterDTO();
			pumpMasterDTO.setPuId(Long.valueOf(object[0].toString()));
			pumpMasterDTO.setPuPumpname(object[1].toString());
			pumpMasterDTOList.add(pumpMasterDTO);
		}
		return pumpMasterDTOList;
	}
    
    
}
