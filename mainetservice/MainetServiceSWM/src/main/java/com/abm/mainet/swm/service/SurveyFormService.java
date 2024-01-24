package com.abm.mainet.swm.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.master.dao.IOperationWZMappingDAO;
import com.abm.mainet.common.master.dao.OperationWZMappingDAO;
import com.abm.mainet.swm.domain.SurveyFormMaster;
import com.abm.mainet.swm.domain.SurveyFormMasterHistory;
import com.abm.mainet.swm.dto.SurveyReportDTO;
import com.abm.mainet.swm.repository.SurveyEntryFormRepository;

@Service
public class SurveyFormService implements ISurveyFormService {
    
    private static Logger log = Logger.getLogger(SurveyFormService.class);
    
    @Autowired
    SurveyEntryFormRepository surveyEntryFormRepository;
    
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private IOperationWZMappingDAO operationWZMappingDAO;

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISurveyFormService#saveSurveyForm(com.abm.mainet.swm.dto.SurveyReportDTO)
     */
    @Override
    @Transactional
    public void saveSurveyForm(SurveyReportDTO surveyDetails) {
        SurveyFormMaster surveyFormMaster= new SurveyFormMaster();
        BeanUtils.copyProperties(surveyDetails, surveyFormMaster);
        surveyEntryFormRepository.save(surveyFormMaster);

        SurveyFormMasterHistory surveyFormMasterHistory = new SurveyFormMasterHistory();
        surveyFormMasterHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());

        try {
            auditService.createHistory(surveyFormMaster, surveyFormMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + surveyFormMaster, e);
        }
      
    
    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISurveyFormService#searchSurveyDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public SurveyReportDTO searchSurveyDetails(Long locId, Long orgId) {
        SurveyReportDTO surveyReportDTO = new SurveyReportDTO();
        SurveyFormMaster surveyFormMaster =surveyEntryFormRepository.getSurveyDetails(orgId, locId);
        if(surveyFormMaster != null) {
        BeanUtils.copyProperties(surveyFormMaster, surveyReportDTO);
        }
        if(surveyReportDTO.getCodWard1() == null) {
        	addWardZoneDetails(surveyReportDTO,locId,orgId);
        }
        return surveyReportDTO;
    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISurveyFormService#updateSurveyForm(com.abm.mainet.swm.dto.SurveyReportDTO)
     */
    @Override
    @Transactional
    public void updateSurveyForm(SurveyReportDTO surveyDetails) {
        SurveyFormMaster surveyFormMaster= new SurveyFormMaster();
        BeanUtils.copyProperties(surveyDetails, surveyFormMaster);
        surveyEntryFormRepository.save(surveyFormMaster);

        SurveyFormMasterHistory surveyFormMasterHistory = new SurveyFormMasterHistory();
        surveyFormMasterHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());

        try {
            auditService.createHistory(surveyFormMaster, surveyFormMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + surveyFormMaster, e);
        }
      
    
        
    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISurveyFormService#getDetailsbyOrgIdandLocId(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Long[]> getDetailsbyOrgIdandLocId(Long locId, Long orgId) {      
         return surveyEntryFormRepository.getLocationMappingDetails(orgId,locId);       
    }
    
    /*Defect #10227*/
    @Override
	public SurveyReportDTO addWardZoneDetails(SurveyReportDTO surveyReportDTO, Long locId, Long orgId) {
		if (surveyReportDTO != null) {
			final List<LocationOperationWZMapping> locationOperationWZMapping = operationWZMappingDAO
					.getLocationOperationWZMappingByLocId(locId,orgId);
			
			if (locationOperationWZMapping != null && !locationOperationWZMapping.isEmpty()) {
				LocationOperationWZMapping locationOperationWard = locationOperationWZMapping.get(0);
				if (locationOperationWard.getCodIdOperLevel1() != null) {
					surveyReportDTO.setCodWard1(locationOperationWard.getCodIdOperLevel1());
				}
				if (locationOperationWard.getCodIdOperLevel2() != null) {
					surveyReportDTO.setCodWard2(locationOperationWard.getCodIdOperLevel2());
				}
				if (locationOperationWard.getCodIdOperLevel3() != null) {
					surveyReportDTO.setCodWard3(locationOperationWard.getCodIdOperLevel3());
				}
				if (locationOperationWard.getCodIdOperLevel4() != null) {
					surveyReportDTO.setCodWard4(locationOperationWard.getCodIdOperLevel4());
				}
				if (locationOperationWard.getCodIdOperLevel5() != null) {
					surveyReportDTO.setCodWard5(locationOperationWard.getCodIdOperLevel5());
				}
			}
		}
		return surveyReportDTO;
	}
}
