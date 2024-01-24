package com.abm.mainet.rts.service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.domain.RtsExternalServicesEntity;
import com.abm.mainet.rts.dto.RtsExternalServicesDTO;
import com.abm.mainet.rts.repository.RtsExternalServicesRepository;

import io.swagger.annotations.Api;

@Api(value = "/rtsExternalServices")
@Path("/rtsExternalServices")
@Service
public class RtsExternalServices implements IRtsExternalServices{
	
	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Resource
	private ApplicationService applicationService;
	
	@Autowired
	RtsExternalServicesRepository rtsRepro;
	
	@Resource
	private IFileUploadService fileUploadService;


	@Override
	@POST
	@Path("/saveCertificateData")
	@Transactional
	public RtsExternalServicesDTO saveCertificateData(RtsExternalServicesDTO rtsExternalServicesDTO) {

		RtsExternalServicesEntity external = new RtsExternalServicesEntity();
			final RequestDTO commonRequest = rtsExternalServicesDTO.getRequestDTO();
			ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(rtsExternalServicesDTO.getRequestDTO().getServiceShortCode(), rtsExternalServicesDTO.getOrgId());
			rtsExternalServicesDTO.setServiceId(serviceMas.getSmServiceId());
			if(rtsExternalServicesDTO.getApplicationId()==null) {
			commonRequest.setOrgId(rtsExternalServicesDTO.getOrgId());
			commonRequest.setServiceId(serviceMas.getSmServiceId());
			commonRequest.setApmMode(RtsConstants.FLAGF);
			
			if (serviceMas.getSmFeesSchedule() == 0) {
				commonRequest.setPayStatus(RtsConstants.PAYSTSTUSFREE);
			} else {
				commonRequest.setPayStatus(RtsConstants.PAYSTATUSCHARGE);
			}
			commonRequest.setUserId(rtsExternalServicesDTO.getUserId());

			final Long applicationId = applicationService.createApplication(commonRequest);
			commonRequest.setReferenceId(String.valueOf(applicationId));
			commonRequest.setApplicationId(applicationId);
			if (null == applicationId) {
				throw new FrameworkException("Application Not Generated");
			}
			
			if ((rtsExternalServicesDTO.getUploadDocument() != null) && (!rtsExternalServicesDTO.getUploadDocument().isEmpty())) {
				fileUploadService.doFileUpload(rtsExternalServicesDTO.getUploadDocument(), commonRequest);
			}
			rtsExternalServicesDTO.setApplicationId(applicationId);
			if ((applicationId != null) && (applicationId != 0)) {
				rtsExternalServicesDTO.setApplicationId(applicationId);
			}
			}
		/*
		 * rtsExternalServicesDTO.setApplicantEmail(commonRequest.getEmail());
		 * rtsExternalServicesDTO.setApplicantMobilno(commonRequest.getMobileNo());
		 * rtsExternalServicesDTO.setApplicantName(commonRequest.getfName());
		 * rtsExternalServicesDTO.setApplicantAddress(commonRequest.getCityName());
		 */
		if (rtsExternalServicesDTO.getStatus()== null || rtsExternalServicesDTO.getStatus().equals("")) {
			rtsExternalServicesDTO.setStatus(MainetConstants.TASK_STATUS_DRAFT);
		}
			BeanUtils.copyProperties(rtsExternalServicesDTO, external);
			rtsRepro.save(external);
			
			return rtsExternalServicesDTO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateStatus(Long applicationId,Long orgId, String status) {
		rtsRepro.updateStatus(applicationId, orgId,  status);
	}

}
