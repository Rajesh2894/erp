/**
 *
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IPumpMasterDAO;
import com.abm.mainet.swm.domain.PumpMaster;
import com.abm.mainet.swm.domain.PumpMasterHistory;
import com.abm.mainet.swm.dto.PumpMasterDTO;
import com.abm.mainet.swm.mapper.PumpMasterMapper;
import com.abm.mainet.swm.repository.PumpMasterRepository;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 04-May-2018
 */
@Service
public class PumpMasterService implements IPumpMasterService {

    /**
     * The Pump Master Repository
     */
    @Autowired
    private PumpMasterRepository pumpMasterRepository;

    /**
     * The IPumpMaster DAO
     */
    @Autowired
    private IPumpMasterDAO pumpMasterDAO;

    /**
     * The Pump Master Mapper
     */
    @Autowired
    private PumpMasterMapper pumpMasterMapper;

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
    public PumpMasterDTO savePumpMaster(PumpMasterDTO pumpIdDetails) {
        PumpMaster master = pumpMasterMapper.mapPumpMasterDTOToPumpMaster(pumpIdDetails);
        master = pumpMasterRepository.save(master);
        PumpMasterHistory masterHistory = new PumpMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
        return pumpMasterMapper.mapPumpMasterToPumpMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PumpMasterService#updatePumpMaster(com.abm.mainet.swm.dto.PumpMasterDTO)
     */
    @Override
    @Transactional
    public PumpMasterDTO updatePumpMaster(PumpMasterDTO pumpIdDetails) {
        PumpMaster master = pumpMasterMapper.mapPumpMasterDTOToPumpMaster(pumpIdDetails);
        master = pumpMasterRepository.save(master);
        PumpMasterHistory masterHistory = new PumpMasterHistory();
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
        PumpMaster master = pumpMasterRepository.findOne(pumpId);
        master.setPuActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        pumpMasterRepository.save(master);
        PumpMasterHistory masterHistory = new PumpMasterHistory();
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

        List<PumpMaster> pumpMasterList = pumpMasterDAO.searchPumpByPumpType(null, pumpMasterDTO.getPuPutype(),
                pumpMasterDTO.getPuId(),
                pumpMasterDTO.getPuPumpname(), pumpMasterDTO.getOrgid());

        return pumpMasterList.isEmpty();

    }
}
