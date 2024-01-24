package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.legal.domain.LegalOpinionEntity;
import com.abm.mainet.legal.dto.LegalOpinionDetailDTO;
import com.abm.mainet.legal.repository.LglOpinionRepository;

@Service
public class LegalOpinionServiceImpl implements ILegalOpinionService {
	@Resource
	private LglOpinionRepository lglOpinionRepository;
	@Resource
	private ApplicationService applicationService;
	@Resource
	private CommonService commonService;
	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private DepartmentService departmentService;

	@Override
	@Transactional
	public LegalOpinionDetailDTO saveLegalOpinionEntry(LegalOpinionDetailDTO legalOpinionDetailDTO)

	{

		legalOpinionDetailDTO.setTitleId(1l);

		final Long applicationNo = applicationService.createApplication(legalOpinionDetailDTO);

		legalOpinionDetailDTO.setApmApplicationId(applicationNo);
		legalOpinionDetailDTO.setApplicationId(applicationNo);
		saveLegalOpinionApplication(legalOpinionDetailDTO);

		RequestDTO requestDTO = new RequestDTO();

		requestDTO.setDeptId(legalOpinionDetailDTO.getDeptId());
		requestDTO.setApplicationId(applicationNo);
		requestDTO.setOrgId(legalOpinionDetailDTO.getOrgId());
		requestDTO.setServiceId(legalOpinionDetailDTO.getSmServiceId());
		requestDTO.setUserId(legalOpinionDetailDTO.getUserId());
		requestDTO.setReferenceId(applicationNo.toString());

		fileUpload.doFileUpload(legalOpinionDetailDTO.getDocList(), requestDTO);

		legalOpinionDetailDTO.setFree(true);

		ApplicationMetadata applicationData = new ApplicationMetadata();

		ApplicantDetailDTO applicantDetailDTO = new ApplicantDetailDTO();

		applicationData.setApplicationId(legalOpinionDetailDTO.getApmApplicationId());
		applicationData.setOrgId(legalOpinionDetailDTO.getOrgId());
		applicationData.setIsCheckListApplicable(false);
		applicantDetailDTO.setUserId(legalOpinionDetailDTO.getUserId());
		applicantDetailDTO.setServiceId(legalOpinionDetailDTO.getSmServiceId());
		applicantDetailDTO.setDepartmentId(legalOpinionDetailDTO.getDeptId().longValue());
		commonService.initiateWorkflowfreeService(applicationData, applicantDetailDTO);
		return legalOpinionDetailDTO;

	}

	@Override
	@Transactional
	public void saveLegalOpinionApplication(LegalOpinionDetailDTO legalOpinionDetailDTO) {

		LegalOpinionEntity legalOpinionEntity = new LegalOpinionEntity();

		BeanUtils.copyProperties(legalOpinionDetailDTO, legalOpinionEntity);

		lglOpinionRepository.save(legalOpinionEntity);
		
		RequestDTO requestDTO = new RequestDTO();
		//#141902
		if (CollectionUtils.isNotEmpty(legalOpinionDetailDTO.getDocumentDetailsList())) {
			requestDTO.setDeptId(departmentService.getDepartmentIdByDeptCode("LGL"));
			requestDTO.setApplicationId(legalOpinionDetailDTO.getId());
			requestDTO.setOrgId(legalOpinionDetailDTO.getOrgId());
			requestDTO.setServiceId(legalOpinionDetailDTO.getSmServiceId());
			requestDTO.setUserId(legalOpinionDetailDTO.getCreatedBy());
			requestDTO.setReferenceId(legalOpinionDetailDTO.getId().toString());
			fileUpload.doFileUpload(legalOpinionDetailDTO.getDocumentDetailsList(), requestDTO);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public LegalOpinionDetailDTO findLegalOpinionApplicationByApmId(Long orgId, Long apmApplicationId) {

		LegalOpinionEntity legalOpinionEntity = lglOpinionRepository.findByOrgIdAndApmApplicationId(orgId,
				apmApplicationId);

		LegalOpinionDetailDTO legalOpinionDetailDTO = new LegalOpinionDetailDTO();

		BeanUtils.copyProperties(legalOpinionEntity, legalOpinionDetailDTO);

		return legalOpinionDetailDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LegalOpinionDetailDTO> findAllByOrgId(Long orgId) {

		List<LegalOpinionEntity> LegalOpinionList = lglOpinionRepository.findByOrgId(orgId);
		List<LegalOpinionDetailDTO> LegalOpinionDtoList = new ArrayList<>();

		for (LegalOpinionEntity legalOpinionEntity : LegalOpinionList) {

			LegalOpinionDetailDTO legalOpinionDetailDTO = new LegalOpinionDetailDTO();

			BeanUtils.copyProperties(legalOpinionEntity, legalOpinionDetailDTO);
			LegalOpinionDtoList.add(legalOpinionDetailDTO);

		}

		return LegalOpinionDtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LegalOpinionDetailDTO> findAllByOrgIdAndDeptId(Long orgId, Long DeptId) {

		List<LegalOpinionEntity> LegalOpinionList = lglOpinionRepository.findByOrgIdAndOpniondeptId(orgId, DeptId);
		List<LegalOpinionDetailDTO> LegalOpinionDtoList = new ArrayList<>();

		for (LegalOpinionEntity legalOpinionEntity : LegalOpinionList) {

			LegalOpinionDetailDTO legalOpinionDetailDTO = new LegalOpinionDetailDTO();

			BeanUtils.copyProperties(legalOpinionEntity, legalOpinionDetailDTO);
			LegalOpinionDtoList.add(legalOpinionDetailDTO);

		}

		return LegalOpinionDtoList;

	}
	//#141902
	@Override
	@Transactional(readOnly = true)
	public LegalOpinionDetailDTO findByIds(Long id, Long orgId) {
		LegalOpinionEntity legalOpinionEntity = lglOpinionRepository.findByIds(id,
				orgId);
		LegalOpinionDetailDTO legalOpinionDetailDTO = new LegalOpinionDetailDTO();
		BeanUtils.copyProperties(legalOpinionEntity, legalOpinionDetailDTO);
		return legalOpinionDetailDTO;
	}
}
