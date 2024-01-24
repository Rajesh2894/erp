package com.abm.mainet.swm.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IPopulationMasterDAO;
import com.abm.mainet.swm.domain.PopulationMaster;
import com.abm.mainet.swm.domain.PopulationMasterHistory;
import com.abm.mainet.swm.dto.PopulationMasterDTO;
import com.abm.mainet.swm.mapper.PopulationMasterMapper;
import com.abm.mainet.swm.repository.PopulationMasterRepository;

/**
 * The Class PopulationMasterServiceImpl.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
@Service
public class PopulationMasterService implements IPopulationMasterService {

    /** The population master repository. */
    @Autowired
    private PopulationMasterRepository populationMasterRepository;

    /** The population master DAO. */
    @Autowired
    private IPopulationMasterDAO populationMasterDAO;

    /** The popul ation master mapper. */
    @Autowired
    private PopulationMasterMapper populAtionMasterMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PopulationMasterService#savePopulationMaster(com.abm.mainet.swm.dto.PopulationMasterDTO)
     */
    @Override
    @Transactional
    public PopulationMasterDTO savePopulationMaster(PopulationMasterDTO population) {

        PopulationMaster master = populAtionMasterMapper.mapPopulationMasterDTOToPopulationMaster(population);
        master = populationMasterRepository.save(master);
        PopulationMasterHistory masterHistory = new PopulationMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
        return populAtionMasterMapper.mapPopulationMasterToPopulationMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PopulationMasterService#savePopulationMaster(java.util.List)
     */
    @Override
    @Transactional
    public List<PopulationMasterDTO> savePopulationMaster(List<PopulationMasterDTO> populations) {
        List<PopulationMaster> populationMasterList = populAtionMasterMapper
                .mapPopulationMasterDTOListToPopulationMasterList(populations);
        populationMasterList = populationMasterRepository.save(populationMasterList);
        populationMasterList.forEach(population -> {
            PopulationMasterHistory masterHistory = new PopulationMasterHistory();
            masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
            auditService.createHistory(population, masterHistory);
        });
        return populAtionMasterMapper.mapPopulationMasterListToPopulationMasterDTOList(populationMasterList);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PopulationMasterService#updatePopulationMaster(com.abm.mainet.swm.dto.PopulationMasterDTO)
     */
    @Override
    @Transactional
    public PopulationMasterDTO updatePopulationMaster(PopulationMasterDTO population) {
        PopulationMaster master = populAtionMasterMapper.mapPopulationMasterDTOToPopulationMaster(population);
        master = populationMasterRepository.save(master);
        PopulationMasterHistory masterHistory = new PopulationMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return populAtionMasterMapper.mapPopulationMasterToPopulationMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PopulationMasterService#deletePopulationMaster(java.lang.Long)
     */
    @Override
    @Transactional
    public void deletePopulationMaster(Long populationId, Long empId, String ipMacAdd) {

        Assert.notNull(populationId, MainetConstants.POPULATION_ID_NOT_NULL);

        PopulationMaster master = populationMasterRepository.findOne(populationId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        populationMasterRepository.save(master);
        PopulationMasterHistory masterHistory = new PopulationMasterHistory();
        masterHistory.setStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PopulationMasterService#getAllPopulationMaster(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PopulationMasterDTO> getAllPopulationMaster(Long yerCpdId, Long orgId) {
        Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(yerCpdId, MainetConstants.YEAR_ID_NOT_NULL);

        return populAtionMasterMapper.mapPopulationMasterListToPopulationMasterDTOList(
                populationMasterRepository.findAllByPopYearAndOrgid(yerCpdId, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PopulationMasterService#getPopulationMaster(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public PopulationMasterDTO getPopulationMaster(Long populationId) {
        Assert.notNull(populationId, MainetConstants.POPULATION_ID_NOT_NULL);
        return populAtionMasterMapper
                .mapPopulationMasterToPopulationMasterDTO(populationMasterRepository.findOne(populationId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PopulationMasterService#findPopulationByYearAndArea(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PopulationMasterDTO> findPopulationByYearAndArea(Long yearCpdId, Long ward, Long zone, Long block,
            Long route, Long subRoute, Long orgId) {
        Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
        return populAtionMasterMapper.mapPopulationMasterListToPopulationMasterDTOList(
                populationMasterDAO.searchByYearAndWordZoneBlock(MainetConstants.FlagY, yearCpdId, ward, zone, block, route,
                        subRoute, orgId, null));

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IPopulationMasterService#findPopulation(java.lang.Long, java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<PopulationMasterDTO> findPopulation(Long yearCpdId, Long ward, Long zone, Long block,
            Long route, Long subRoute, Long orgId) {
        Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
        return populAtionMasterMapper.mapPopulationMasterListToPopulationMasterDTOList(
                populationMasterDAO.searchByYearAndWordZoneBlock(MainetConstants.FlagY, yearCpdId, ward, zone, block, route, subRoute, orgId,
                        null));

    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.PopulationMasterService#validatePopulationMaster(com.abm.mainet.swm.dto.PopulationMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validatePopulationMaster(PopulationMasterDTO population) {
        Assert.notNull(population.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(population.getPopYear(), MainetConstants.YEAR_ID_NOT_NULL);
        List<PopulationMaster> populationList = populationMasterDAO.searchByYearAndWordZoneBlock(MainetConstants.FlagY,
                population.getPopYear(), population.getCodDwzid1(), population.getCodDwzid2(),
                population.getCodDwzid3(), population.getCodDwzid4(), population.getCodDwzid5(), population.getOrgid(),
                population.getPopId());
        return populationList.isEmpty();

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IPopulationMasterService#financialyear()
     */
    @Override
    @Transactional(readOnly = true)
    public List<PopulationMasterDTO> financialyear() {
        PopulationMasterDTO populationdto = null;
        List<PopulationMasterDTO> populationList = new ArrayList<>();
        DateFormat formatter;
        formatter = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        String frmdate;
        List<Date> populationFYr = populationMasterRepository.getAllFinincialYear();
        if ((populationFYr != null) && !populationFYr.isEmpty()) {
            for (Date obj : populationFYr) {
                populationdto = new PopulationMasterDTO();
                frmdate = formatter.format(obj);
                populationdto.setPopulationfaYears(frmdate);
                populationList.add(populationdto);
            }
        }
        return populationList;
    }
}
