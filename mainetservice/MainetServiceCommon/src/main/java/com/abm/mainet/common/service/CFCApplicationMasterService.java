package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.dao.ICFCApplicationStatusDao;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicationStatusDTO;
import com.abm.mainet.common.dto.CFCApplicationStatusDto;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

/**
 * @author Rajendra.Bhujbal
 *
 */
@Service

public class CFCApplicationMasterService implements ICFCApplicationMasterService {

	private static final Logger logger = Logger.getLogger(CFCApplicationMasterService.class);

	@Autowired
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private IWorkflowActionService workflowActionService;

	@Autowired
	private TbCfcApplicationMstJpaRepository cfcApplicationMstJpaRepository;

	@Autowired
	private ICFCApplicationStatusDao cfcAppllicationStatusDao;

	@Autowired
	private IWorkflowRequestService workflowRequestService;

	@Override
	@Transactional
	public long getServiceIdByApplicationId(final long apmApplicationId, final long orgId) {
		return cfcApplicationMasterDAO.getServiceIdByApplicationId(apmApplicationId, orgId);
	}

	@Override
	@Transactional
	public CFCApplicationAddressEntity getApplicantsDetails(final long apmApplicationId) {
		return cfcApplicationMasterDAO.getApplicantsDetailsDao(apmApplicationId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.service.ICFCApplicationMasterService#
	 * getCFCApplicationByApplicationId(long, long)
	 */
	@Override
	@Transactional
	public TbCfcApplicationMstEntity getCFCApplicationByApplicationId(final long applicationId, final long orgid) {
		final TbCfcApplicationMstEntity cfcMaster = cfcApplicationMasterDAO
				.getCFCApplicationByApplicationId(applicationId, orgid);
		if (cfcMaster != null) {
			cfcMaster.getTbServicesMst().getSmServiceId();
			cfcMaster.getTbOrganisation().getOrgid();
		}
		return cfcMaster;
	}

	@Override
	@Transactional
	public void updateCFCApplicationStatus(final String status, final long applicationId) {
		cfcApplicationMasterDAO.updateCFCApplicationStatus(status, applicationId);
	}

	@Override
	public TbCfcApplicationMstEntity getCFCApplicationByRefNoOrAppNo(String refNumber, Long applicationId, Long orgid) {
		TbCfcApplicationMstEntity entity = new TbCfcApplicationMstEntity();
		try {
			entity = cfcApplicationMasterDAO.getCFCApplicationByRefNoOrAppNo(refNumber, applicationId, orgid);
		} catch (Exception e) {
			logger.error("Exception Occur while fetching Data of Cfc Application Master :" + e);
		}
		return entity;
	}

	@Override
	public List<TbCfcApplicationMstEntity> fetchCfcApplicationByServiceIdandMobileNo(List<Long> serviceIds, Long orgId,
			String mobileNo) {
		return cfcApplicationMstJpaRepository.fetchCfcApplicationsByIds(serviceIds, orgId, mobileNo);
	}

	@Override
	public List<TbCfcApplicationMstEntity> fetchCfcApplicationsByServiceIds(List<Long> serviceIds, Long orgId) {
		return cfcApplicationMstJpaRepository.fetchCfcApplicationsByServiceIds(serviceIds, orgId);
	}

	@Override
	@Transactional
	public List<ApplicationStatusDTO> getApplicationStatusDtos(CFCApplicationStatusDto cfcApplicationStatusDto,int langId) {
		
		List<ApplicationStatusDTO> applicationStatusDTOs = new ArrayList<ApplicationStatusDTO>();
		
		List<TbCfcApplicationMstEntity> result = cfcAppllicationStatusDao
				.getCFCApplicationEtites(cfcApplicationStatusDto);

		for (TbCfcApplicationMstEntity application : result) {
			ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(); //
			if (application.getTbServicesMst() != null) {
				if(langId == MainetConstants.DEFAULT_LANGUAGE_ID) 
					applicationStatusDTO.setServiceName(application.getTbServicesMst().getSmServiceName());
				else 
				    applicationStatusDTO.setServiceNameReg(application.getTbServicesMst().getSmServiceNameMar());
			//D#127888	7
			if(langId == MainetConstants.DEFAULT_LANGUAGE_ID) 
					applicationStatusDTO.setDeptName(application.getTbServicesMst().getTbDepartment().getDpDeptdesc());
				else
					applicationStatusDTO.setDeptName(application.getTbServicesMst().getTbDepartment().getDpNameMar());
			}
			if(langId == MainetConstants.DEFAULT_LANGUAGE_ID) 
				applicationStatusDTO.setOrganizationName(application.getTbOrganisation().getONlsOrgname());
			else
		    	applicationStatusDTO.setOrganizationNameReg(application.getTbOrganisation().getONlsOrgnameMar());
			//applicationStatusDTO.setDate(application.getApmApplicationDate());
			applicationStatusDTO.setApplicationId(application.getApmApplicationId());
			applicationStatusDTO.setFormattedDate(
					Utility.dateToString(application.getApmApplicationDate(), MainetConstants.DATE_FORMAT));
		  if(StringUtils.isNotEmpty(application.getApmFname()) && StringUtils.isNotEmpty(application.getApmLname()))
			applicationStatusDTO.setEmpName(application.getApmFname().concat(" ").concat(application.getApmLname()));
		  else if(StringUtils.isNotEmpty(application.getApmFname()))
		  {
			  applicationStatusDTO.setEmpName(application.getApmFname());
		  }
	 
		  try {
				WorkflowRequest workflowRequest = workflowRequestService
						.findByApplicationId(application.getApmApplicationId());
				if (workflowRequest != null) {
					//120225
					if(langId == MainetConstants.DEFAULT_LANGUAGE_ID)
						applicationStatusDTO.setStatus(workflowRequest.getStatus());
					else
						if(workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.PENDING))					
							applicationStatusDTO.setStatus(ApplicationSession.getInstance().getMessage("CFC.app.status.pending"));
						else 
						    applicationStatusDTO.setStatus(ApplicationSession.getInstance().getMessage("CFC.app.status.closed"));
					     //#151629	
					 	if (workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.PENDING)) {
					 		if (application.getTbServicesMst() != null && application.getTbServicesMst().getSmServiceDuration() != null) {
							  Date dueDate = Utility.getAddedDateBy2(application.getApmApplicationDate(), application.getTbServicesMst().getSmServiceDuration().intValue());
							  applicationStatusDTO.setDueDate(Utility.dateToString(dueDate, MainetConstants.DATE_FORMAT));
					 		  }else
					 			 applicationStatusDTO.setDueDate(Utility.dateToString(application.getApmApplicationDate(), MainetConstants.DATE_FORMAT));  
							  }
					 	else if(workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) && workflowRequest.getLastDateOfAction() != null ) {
					 		applicationStatusDTO.setDueDate(Utility.dateToString(workflowRequest.getLastDateOfAction(), MainetConstants.DATE_FORMAT));
					 	}
					 	
			    	//applicationStatusDTO.setStatus(workflowRequest.getStatus());
					applicationStatusDTO.setLastDecision(workflowRequest.getLastDecision());

				} else {
					if (application.getApmApplClosedFlag() != null
							&& application.getApmApplClosedFlag().equals(MainetConstants.Common_Constant.YES))
						if(langId == MainetConstants.DEFAULT_LANGUAGE_ID)
							applicationStatusDTO.setStatus(MainetConstants.WorkFlow.Status.CLOSED);
						else
							applicationStatusDTO.setStatus(ApplicationSession.getInstance().getMessage("CFC.app.status.closed"));
					else
						if(langId == MainetConstants.DEFAULT_LANGUAGE_ID)
							applicationStatusDTO.setStatus(MainetConstants.WorkFlow.Status.PENDING);
						else
							applicationStatusDTO.setStatus(ApplicationSession.getInstance().getMessage("CFC.app.status.pending"));
					if (application.getTbServicesMst() != null && application.getTbServicesMst().getSmServiceDuration() != null) {
						  Date dueDate = Utility.getAddedDateBy2(application.getApmApplicationDate(), application.getTbServicesMst().getSmServiceDuration().intValue());
						  applicationStatusDTO.setDueDate(Utility.dateToString(dueDate, MainetConstants.DATE_FORMAT));
						  }else 
							  applicationStatusDTO.setDueDate(Utility.dateToString(application.getApmApplicationDate(), MainetConstants.DATE_FORMAT));
						  
				}
			} catch (Exception e) {
				logger.info("Exception occure while calling method getApplicationStatusDtos() "+e);
			}
			applicationStatusDTOs.add(applicationStatusDTO);
		}
		return applicationStatusDTOs;
	}

	@Override
	@Transactional
	public boolean isDataExist(CFCApplicationStatusDto cfcApplicationStatusDto) {
		
		List<TbCfcApplicationMstEntity> result = cfcAppllicationStatusDao
				.getCFCApplicationEtites(cfcApplicationStatusDto);
		
		if(result.isEmpty())
			return true;
		return false;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.service.ICFCApplicationMasterService#
	 * getCFCApplicationOnlyByApplicationId(long)
	 */
	@Override
	@Transactional
	public TbCfcApplicationMstEntity getCFCApplicationOnlyByApplicationId(final long applicationId) {
		final TbCfcApplicationMstEntity cfcMaster = cfcApplicationMasterDAO
				.getCFCApplicationByOnlyApplicationId(applicationId);
		
		return cfcMaster;
	}
	
	@Override
	@Transactional
	public List<TbCfcApplicationMstEntity> getCFCApplData(Long orgid) {
	    List<TbCfcApplicationMstEntity> cfcMaster = new ArrayList<>();
		cfcMaster = cfcApplicationMasterDAO.getApplicantList(orgid);
		return cfcMaster;
	}
	
	@Transactional
	public Long getTransctionsData(Long orgid, Long deptId, Date daten) {
	    Long cfcMaster;
		cfcMaster = cfcApplicationMasterDAO.getTransactionsList(orgid, deptId, daten);
		return cfcMaster;
	}
	
	@Transactional
	public Long getCountClosedApplications(Long orgid, Long deptId, String dateSet) {
	    Long cfcMaster;
		cfcMaster = cfcApplicationMasterDAO.getCountClosedApplications(orgid, deptId, dateSet);
		return cfcMaster;
	}
	
	public Long getTodaysApprovedApplications(Long orgId, Long deptId, String dateSet) {
		 Long cfcMaster;
			cfcMaster = cfcApplicationMasterDAO.getTodaysApprovedApplications(orgId, deptId, dateSet);
			return cfcMaster;
		
	}
	
	public List<Object[]> getPropTodaysMovedAppl(Long orgId, Long deptId, String dateSet) {
		 List<Object[]> cfcMaster = new ArrayList<>();
		 cfcMaster = cfcApplicationMasterDAO.getPropTodaysMovedAppl(orgId, deptId, dateSet);
		return cfcMaster;
		
	}
}