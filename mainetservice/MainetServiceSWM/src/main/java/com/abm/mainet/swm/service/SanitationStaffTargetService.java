package com.abm.mainet.swm.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.ISanitationStaffTargetDAO;
import com.abm.mainet.swm.domain.SanitationStaffTarget;
import com.abm.mainet.swm.domain.SanitationStaffTargetDet;
import com.abm.mainet.swm.domain.SanitationStaffTargetHistory;
import com.abm.mainet.swm.dto.SanitationStaffTargetDTO;
import com.abm.mainet.swm.dto.SanitationStaffTargetDetDTO;
import com.abm.mainet.swm.mapper.SanitationStaffTargetMapper;
import com.abm.mainet.swm.repository.SanitationStaffTargetRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class SanitationStaffTargetServiceImpl.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
@Service
public class SanitationStaffTargetService implements ISanitationStaffTargetService {

    /** The sanitation staff target repository. */
    @Autowired
    private SanitationStaffTargetRepository sanitationStaffTargetRepository;

    /** The sanitation staff target DAO. */
    @Autowired
    private ISanitationStaffTargetDAO sanitationStaffTargetDAO;

    /** The sanitation staff target mapper. */
    @Autowired
    private SanitationStaffTargetMapper sanitationStaffTargetMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.SanitationStaffTargetService#delete(java.lang.Long)
     */
    @Override
    @Transactional
    public void delete(Long sanitationStaffTargetId, Long empId, String ipMacAdd) {
        SanitationStaffTarget master = sanitationStaffTargetRepository.findOne(sanitationStaffTargetId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        sanitationStaffTargetRepository.save(master);
        saveSanitationStaffTargetHistory(master, MainetConstants.Transaction.Mode.DELETE);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.SanitationStaffTargetService#getById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public SanitationStaffTargetDTO getById(Long sanitationStaffTarget) {
        return sanitationStaffTargetMapper
                .mapSanitationStaffTargetToSanitationStaffTargetDTO(
                        sanitationStaffTargetRepository.findOne(sanitationStaffTarget));
    }

    /**
     * Mapped.
     *
     * @param sanitationStaffTargetDetails the sanitation staff target details
     * @return the sanitation staff target
     */
    private SanitationStaffTarget mapped(SanitationStaffTargetDTO sanitationStaffTargetDetails) {
        SanitationStaffTarget master = sanitationStaffTargetMapper
                .mapSanitationStaffTargetDTOToSanitationStaffTarget(sanitationStaffTargetDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.SanitationStaffTargetService#save(com.abm.mainet.swm.dto.SanitationStaffTargetDTO)
     */
    @Override
    @Transactional
    public SanitationStaffTargetDTO save(SanitationStaffTargetDTO sanitationStaffTargetDetails) {
        SanitationStaffTarget master = mapped(sanitationStaffTargetDetails);
        master = sanitationStaffTargetRepository.save(master);
        saveSanitationStaffTargetHistory(master, MainetConstants.Transaction.Mode.ADD);
        return sanitationStaffTargetMapper.mapSanitationStaffTargetToSanitationStaffTargetDTO(master);
    }

    /**
     * Save sanitation staff target history.
     *
     * @param master the master
     * @param status the status
     */
    private void saveSanitationStaffTargetHistory(SanitationStaffTarget master, String status) {
        SanitationStaffTargetHistory masterHistory = new SanitationStaffTargetHistory();
        masterHistory.setHStatus(status);
        auditService.createHistory(master, masterHistory);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.SanitationStaffTargetService#update(com.abm.mainet.swm.dto.SanitationStaffTargetDTO)
     */
    @Override
    @Transactional
    public SanitationStaffTargetDTO update(SanitationStaffTargetDTO sanitationStaffTargetDetails) {
        SanitationStaffTarget master = mapped(sanitationStaffTargetDetails);
        master = sanitationStaffTargetRepository.save(master);
        saveSanitationStaffTargetHistory(master, MainetConstants.Transaction.Mode.UPDATE);
        return sanitationStaffTargetMapper.mapSanitationStaffTargetToSanitationStaffTargetDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISanitationStaffTargetService#search(java.lang.Long, java.util.Date, java.util.Date,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SanitationStaffTargetDTO> search(Long sanType, Date fromDate, Date toDate, Long orgId) {
        return sanitationStaffTargetMapper.mapSanitationStaffTargetListToSanitationStaffTargetDTOList(
                sanitationStaffTargetDAO.searchSanitationStaffTarget(sanType, fromDate, toDate, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISanitationStaffTargetService#getchildById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public SanitationStaffTargetDetDTO getchildById(Long sandId) {
        SanitationStaffTargetDet sanitationStaffTargetDet = sanitationStaffTargetRepository.getchildbyId(sandId);
        SanitationStaffTargetDetDTO sanitationStaffTargetDetDTO = new SanitationStaffTargetDetDTO();
        BeanUtils.copyProperties(sanitationStaffTargetDet, sanitationStaffTargetDetDTO);
        sanitationStaffTargetDetDTO.setSanId(sanitationStaffTargetDet.getSanitationStaffTarget().getSanId());
        return sanitationStaffTargetDetDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISanitationStaffTargetService#validateVehicleTarget(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.util.Date, java.util.Date, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validateVehicleTarget(Long vehicleId, Long roId, Long sandId, Date fromDate, Date toDate, Long orgId) {
        boolean status = false;
        int count = sanitationStaffTargetRepository.valindateVehicleTarget(vehicleId, roId, sandId, orgId, fromDate, toDate);
        if (count == 0) {
            status = true;
        }
        return status;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISanitationStaffTargetService#findVehicleTargetDatils(java.lang.Long, java.lang.Long,
     * java.util.Date, java.util.Date)
     */
    @Override
    @Transactional(readOnly = true)
    public SanitationStaffTargetDTO findVehicleTargetDatils(Long OrgId, Long veId, Date fromDate, Date toDate) {
        // TODO Auto-generated method stub
        List<Object[]> vehicleTargetdet = sanitationStaffTargetRepository.findVehicleTargetDatilsByVeId(OrgId, veId,
                fromDate, toDate);
        SanitationStaffTargetDTO vehicleTargetDTO = null;
        SanitationStaffTargetDTO vehicleCollectionTargetDTO = null;
        List<SanitationStaffTargetDTO> listVehicleTargetDTO = new ArrayList<SanitationStaffTargetDTO>();
        BigDecimal totalTarget = new BigDecimal(0.00);
        BigDecimal totalCollection = new BigDecimal(0.00);
        if (vehicleTargetdet != null && !vehicleTargetdet.isEmpty()) {
            vehicleCollectionTargetDTO = new SanitationStaffTargetDTO();
            for (Object[] vehicleTargetdetails : vehicleTargetdet) {
                vehicleTargetDTO = new SanitationStaffTargetDTO();
                vehicleTargetDTO.setVehicleRegNo(vehicleTargetdetails[1].toString());
                if (vehicleTargetdetails[2] != null) {
                    vehicleTargetDTO.setCollectionTarget(
                            new BigDecimal(vehicleTargetdetails[2].toString()).setScale(3, RoundingMode.HALF_EVEN));
                } else {
                    vehicleTargetDTO.setCollectionTarget(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (vehicleTargetdetails[3] != null) {
                    vehicleTargetDTO.setActualCollection(
                            new BigDecimal(vehicleTargetdetails[3].toString()).setScale(3, RoundingMode.HALF_EVEN));
                } else {
                    vehicleTargetDTO.setActualCollection(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_EVEN));
                }
                totalTarget = totalTarget.add(vehicleTargetDTO.getCollectionTarget());
                totalCollection = totalCollection.add(vehicleTargetDTO.getActualCollection());
                listVehicleTargetDTO.add(vehicleTargetDTO);
            }
            vehicleCollectionTargetDTO.setTotalTarget(totalTarget);
            vehicleCollectionTargetDTO.setTotalCollection(totalCollection);
            vehicleCollectionTargetDTO.setTargetDto(listVehicleTargetDTO);
        }
        return vehicleCollectionTargetDTO;
    }
}
