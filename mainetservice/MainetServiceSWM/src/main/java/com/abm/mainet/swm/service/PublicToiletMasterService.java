/**
 *
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.swm.dao.ISanitationMasterDAO;
import com.abm.mainet.swm.domain.SanitationMaster;
import com.abm.mainet.swm.domain.SanitationMasterHistory;
import com.abm.mainet.swm.dto.SanitationMasterDTO;
import com.abm.mainet.swm.mapper.SanitationMasterMapper;
import com.abm.mainet.swm.repository.SanitationMasterRepository;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 17-May-2018
 */

@Service
public class PublicToiletMasterService implements IPublicToiletMasterService {

    /**
     * The SanitationMaster Repository
     */
    @Autowired
    private SanitationMasterRepository sanitationRepository;

    /**
     * The Sanitation Master Repository
     */
    @Autowired
    private ISanitationMasterDAO sanitationDAO;

    /**
     * The Sanitation Master Mapper
     */
    @Autowired
    private SanitationMasterMapper mapper;

    /**
     * The Audit Service
     */
    @Autowired
    private AuditService auditService;

    /**
     * The Seq Gen Function Utility
     */
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    /**
     * The IOrganisation Service
     */
    @Autowired
    private IOrganisationService organisationService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PublicToiletMasterService#searchToiletLocation( java.lang.Long, java.lang.Long,
     * java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SanitationMasterDTO> searchToiletLocation(Long number, Long type, String name, Long ward, Long zone,
            Long block, Long route, Long subRoute, Long orgId) {

        return mapper.mapSanitationMasterListToSanitationMasterDTOList(sanitationDAO.searchToiletLocation(
                MainetConstants.FlagY, number, type, name, ward, zone, block, route, subRoute, orgId));

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IPublicToiletMasterService#searchToilet(java.lang.Long, java.lang.Long, java.lang.String,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<SanitationMasterDTO> searchToilet(Long number, Long type, String name, Long ward, Long zone, Long block,
            Long route, Long subRoute, Long orgId) {

        return mapper.mapSanitationMasterListToSanitationMasterDTOList(sanitationDAO.searchToiletLocation(null, number,
                type, name, ward, zone, block, route, subRoute, orgId));

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PublicToiletMasterService# getPublicToiletByPublicToiletId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public SanitationMasterDTO getPublicToiletByPublicToiletId(Long toiletId) {
        Assert.notNull(toiletId, " id should not be null");
        return mapper.mapSanitationMasterToSanitationMasterDTO(sanitationRepository.findOne(toiletId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PublicToiletMasterService#savePublicToilet(com.abm .mainet.swm.dto.SanitationMasterDTO)
     */
    @Override
    @Transactional
    public SanitationMasterDTO savePublicToilet(SanitationMasterDTO toiletDetails) {

        SanitationMaster master = mapper.mapSanitationMasterDTOToSanitationMaster(toiletDetails);
        SanitationMasterHistory masterHistory = new SanitationMasterHistory();

        if (toiletDetails.getSanId() == null) {
            Organisation org = organisationService.getOrganisationById(toiletDetails.getOrgid());
            final LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(toiletDetails.getSanType(), org);
            String sanType = lookup.getLookUpCode();

            Long lastLavelWardId = getLastLevelWardId("codWard", toiletDetails);// toiletDetails.getCodWard2(); remove static ward last level
            String ward = CommonMasterUtility.getHierarchicalLookUp(lastLavelWardId, org).getDescLangFirst();

            String wardNo = ward.replaceAll("[^0-9]", "");
            if (wardNo.length() == 1) {
                wardNo = "W0" + wardNo;
            } else {
                wardNo = "W" + wardNo;
            }

            Long resetId = toiletDetails.getOrgid() + lastLavelWardId + toiletDetails.getSanType();

            final Long sequenceNo = seqGenFunctionUtility.generateSequenceNo(
                    MainetConstants.SolidWasteManagement.SHORT_CODE, "TB_SW_SANITATION_MAST", "SAN_ID",
                    toiletDetails.getOrgid(), MainetConstants.FlagC, resetId);

            String sanName = org.getOrgShortNm() + MainetConstants.WINDOWS_SLASH + wardNo + MainetConstants.WINDOWS_SLASH
                    + sanType + MainetConstants.WINDOWS_SLASH + String.format("%03d", sequenceNo);
            master.setSanName(sanName);
            masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
        }
        if (masterHistory.getHStatus() == null) {
            masterHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
        }
        master = sanitationRepository.save(master);
        auditService.createHistory(master, masterHistory);
        return mapper.mapSanitationMasterToSanitationMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PublicToiletMasterService#updatePublicToilet(com. abm.mainet.swm.dto.SanitationMasterDTO)
     */
    @Override
    @Transactional
    public SanitationMasterDTO updatePublicToilet(SanitationMasterDTO toiletDetails) {
        SanitationMaster master = mapper.mapSanitationMasterDTOToSanitationMaster(toiletDetails);
        master = sanitationRepository.save(master);
        SanitationMasterHistory masterHistory = new SanitationMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        return mapper.mapSanitationMasterToSanitationMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.PublicToiletMasterService#deletePublicToilet(java. lang.Long)
     */
    @Override
    @Transactional
    public void deletePublicToilet(Long toiletId, Long empId, String ipMacAdd) {
        Assert.notNull(toiletId, "id should not be null");

        SanitationMaster master = sanitationRepository.findOne(toiletId);
        master.setSanActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        sanitationRepository.save(master);
        SanitationMasterHistory masterHistory = new SanitationMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IPublicToiletMasterService#validatePublicToilet(com.abm.mainet.swm.dto.SanitationMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validatePublicToilet(SanitationMasterDTO sanitationMasterDTO) {

        Assert.notNull(sanitationMasterDTO.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(sanitationMasterDTO.getSanType(),
                MainetConstants.SANITATION_MASTER_VALIDATION.SANITATION_TYPE_NOT_NULL);
        Assert.notNull(sanitationMasterDTO.getSanName(),
                MainetConstants.SANITATION_MASTER_VALIDATION.SANITATION_NAME_NOT_NULL);
        Assert.notNull(sanitationMasterDTO.getCodWard1(),
                MainetConstants.SANITATION_MASTER_VALIDATION.SANITATION_WARD_NOT_NULL);
        Assert.notNull(sanitationMasterDTO.getSanSeatCnt(),
                MainetConstants.SANITATION_MASTER_VALIDATION.SANITATION_COUNT_NOT_NULL);

        List<SanitationMaster> sanitationMasterList = sanitationDAO.searchToiletLocation(null,
                sanitationMasterDTO.getSanId(), sanitationMasterDTO.getSanType(), sanitationMasterDTO.getSanName(),
                sanitationMasterDTO.getCodWard1(), sanitationMasterDTO.getCodWard2(), sanitationMasterDTO.getCodWard3(),
                sanitationMasterDTO.getCodWard4(), sanitationMasterDTO.getCodWard5(), sanitationMasterDTO.getOrgid());

        return sanitationMasterList.isEmpty();

    }
    
    private final Long getLastLevelWardId(final String propertyPrefix, SanitationMasterDTO toiletDetails) {
        Long lastLevel = null;

        int level = 1;

        final BeanWrapper wrapper = new BeanWrapperImpl(toiletDetails);
        boolean foundProperty = false;

        do {
            final String propertyName = propertyPrefix + String.valueOf(level);
            foundProperty = wrapper.isReadableProperty(propertyName);

            if (foundProperty) {
                final Object object = wrapper.getPropertyValue(propertyName);

                if (object != null) {
                    lastLevel = Long.valueOf(object.toString());
                }

                ++level;
            }
        } while (foundProperty);

        return lastLevel;
    }

}
