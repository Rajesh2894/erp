package com.abm.mainet.swm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.domain.WasteRateMaster;
import com.abm.mainet.swm.domain.WasteRateMasterHistory;
import com.abm.mainet.swm.dto.WasteRateMasterDTO;
import com.abm.mainet.swm.repository.WasteRateMasterRepository;

/**
 * @author Ajay.Kumar
 *
 */
@Service
public class WasteRateMasterService implements IWasteRateMasterService {

    private static Logger log = Logger.getLogger(WasteRateMasterService.class);

    /**
     * The WasteRate Master Repository
     */
    @Autowired
    WasteRateMasterRepository wasteRateMasterRepository;

    /**
     * The Audit Service
     */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteRateMasterService#save(com.abm.mainet.swm.dto.WasteRateMasterDTO)
     */
    @Override
    @Transactional
    public void save(WasteRateMasterDTO wasteRateMasterdto) {
        WasteRateMaster wasteRateMaster = new WasteRateMaster();
        BeanUtils.copyProperties(wasteRateMasterdto, wasteRateMaster);
        wasteRateMasterRepository.save(wasteRateMaster);

        WasteRateMasterHistory wasteRateMasterHistory = new WasteRateMasterHistory();
        wasteRateMasterHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());

        try {
            auditService.createHistory(wasteRateMaster, wasteRateMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + wasteRateMaster, e);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteRateMasterService#update(com.abm.mainet.swm.dto.WasteRateMasterDTO)
     */
    @Override
    @Transactional
    public void update(WasteRateMasterDTO wasteRateMasterdto) {

        WasteRateMaster wasteRateMaster = new WasteRateMaster();
        BeanUtils.copyProperties(wasteRateMasterdto, wasteRateMaster);
        wasteRateMaster = wasteRateMasterRepository.save(wasteRateMaster);

        WasteRateMasterHistory wasteRateMasterHistory = new WasteRateMasterHistory();
        wasteRateMasterHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        try {
            auditService.createHistory(wasteRateMaster, wasteRateMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + wasteRateMaster, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteRateMasterService#getAllwasteRate()
     */
    @Override
    @Transactional(readOnly = true)
    public List<WasteRateMasterDTO> getAllwasteRate() {
        List<WasteRateMasterDTO> wasteRateList = wasteRateMasterRepository.findAll().stream().map(entity -> {
            WasteRateMasterDTO dto = new WasteRateMasterDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());
        return wasteRateList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteRateMasterService#validateEntryWasteType(com.abm.mainet.swm.dto.WasteRateMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validateEntryWasteType(WasteRateMasterDTO wasteRateMasterdto) {

        WasteRateMaster wasteRateMaster = (WasteRateMaster) wasteRateMasterRepository
                .validateWasteRate(wasteRateMasterdto.getCodWast(), wasteRateMasterdto.getOrgid());

        return wasteRateMaster == null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteRateMasterService#getWasteRateById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public WasteRateMasterDTO getWasteRateById(Long id) {
        WasteRateMaster wasteRateMaster = wasteRateMasterRepository.findOne(id);
        WasteRateMasterDTO dto = new WasteRateMasterDTO();
        BeanUtils.copyProperties(wasteRateMaster, dto);
        return dto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteRateMasterService#getAllRate(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<WasteRateMasterDTO> getAllRate(Long orgid) {
        List<WasteRateMasterDTO> wasteRateList = wasteRateMasterRepository.getAllWasteRate(orgid).stream()
                .map(entity -> {
                    WasteRateMasterDTO dto = new WasteRateMasterDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
        return wasteRateList;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getPrefixLevel(String prefix, Long orgId) {
        return wasteRateMasterRepository.getPrefixLevelCount(prefix, orgId);
    }

}
