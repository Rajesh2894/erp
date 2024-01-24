package com.abm.mainet.sfac.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.DPREntryDetailsEntity;
import com.abm.mainet.sfac.domain.DPREntryMasterEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.IAMasterEntity;
import com.abm.mainet.sfac.dto.DPREntryDetailsDto;
import com.abm.mainet.sfac.dto.DPREntryMasterDto;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.repository.DPREntryDetailsRepository;
import com.abm.mainet.sfac.repository.DPREntryMasterRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.repository.IAMasterRepository;

@Service
public class DPREntryRequestServiceImpl implements DPREntryRequestService{
	
	private static final Logger logger = Logger.getLogger(DPREntryRequestServiceImpl.class);
	
	@Autowired DPREntryMasterRepository dprEntryMasterRepository;
	
	@Autowired DPREntryDetailsRepository dprEntryDetailsRepository;
	
	@Autowired CBBOMasterRepository cbboMasterRepository;
	
	@Autowired IAMasterRepository  iaMasterRepository;
	
	@Autowired FPOMasterRepository  fpoMasterRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Autowired
	private ApplicationService applicationService;

	@Override
	public DPREntryMasterDto getDetailById(Long dprId) {
		// TODO Auto-generated method stub
		DPREntryMasterDto dprEntryMasterDto = new DPREntryMasterDto();

		DPREntryMasterEntity dprEntryMasterEntity =  dprEntryMasterRepository.findOne(dprId);

		BeanUtils.copyProperties(dprEntryMasterEntity, dprEntryMasterDto);
		
		List<AttachDocs> finalAttachList = new ArrayList<>();
		
		List<String> identifer1 = new ArrayList<>();
		identifer1.add("DPR_ID" + MainetConstants.WINDOWS_SLASH + dprEntryMasterEntity.getDprId());

		// get attached document
		final List<AttachDocs> attachDoc = ApplicationContextProvider.getApplicationContext()
				.getBean(IAttachDocsService.class)
				.findByIdfInQuery(dprEntryMasterEntity.getOrgId(), identifer1);
		if (!attachDoc.isEmpty()) {
			for(AttachDocs attachDocs2 : attachDoc) {
				finalAttachList.add(attachDocs2);
			}

		}
		dprEntryMasterDto.setAttachDocsList(attachDoc);
		List<DPREntryDetailsDto> dprEntryDetailsDtos = new ArrayList<>();
		
		for(DPREntryDetailsEntity dprEntryDetailsEntity : dprEntryDetailsRepository.findByDprEntryMasterEntity(dprEntryMasterEntity)){

			DPREntryDetailsDto dprEntryDetailsDto = new DPREntryDetailsDto();
			BeanUtils.copyProperties(dprEntryDetailsEntity, dprEntryDetailsDto);
			
			List<String> identifer = new ArrayList<>();
			identifer.add("DPR_DOC_ID" + MainetConstants.WINDOWS_SLASH + dprEntryDetailsEntity.getDprdId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(dprEntryDetailsEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			dprEntryDetailsDto.setAttachDocsListDet(attachDocs);

			dprEntryDetailsDtos.add(dprEntryDetailsDto);
			
		}
		
		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		dprEntryMasterDto.setDprEntryDetailsDtos(dprEntryDetailsDtos);

		return dprEntryMasterDto;
	}

	@Override
	public List<DPREntryMasterDto> getDPRDetails(Long fpoID, Long iaId) {
		// TODO Auto-generated method stub
		List<DPREntryMasterDto> dprEntryMasterDtos = new ArrayList<>();
		List<DPREntryMasterEntity> dprEntryMasterEntities = new ArrayList<>();
		if(fpoID!=null && fpoID!=0 && iaId!=null && iaId!=0)
			dprEntryMasterEntities = dprEntryMasterRepository.findByFpoIdAndIaId(fpoID , iaId);
		else if(fpoID!=null && fpoID!= 0)
			dprEntryMasterEntities = dprEntryMasterRepository.findByFpoId(fpoID );
		else if(iaId!=null && iaId!=0)
			dprEntryMasterEntities = dprEntryMasterRepository.findByIaId( iaId);
		
		for(DPREntryMasterEntity dprEntryMasterEntity : dprEntryMasterEntities) {
			DPREntryMasterDto dprEntryMasterDto = new DPREntryMasterDto();
			BeanUtils.copyProperties(dprEntryMasterEntity, dprEntryMasterDto);
			dprEntryMasterDtos.add(dprEntryMasterDto);
		}
		
		return dprEntryMasterDtos;
	}
	
	private void saveDocuments(List<DocumentDetailsVO> attach, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attach,
				requestDTO);
	}
	
	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}
	}

	@Override
	public DPREntryMasterDto saveAndUpdateApplication(DPREntryMasterDto mastDto, List<Long> removedIds) {
		try {
			
			if (removedIds != null && !removedIds.isEmpty()) {
				// update with DEACTIVE status on removeIds
				dprEntryDetailsRepository.deActiveBPInfo(removedIds,
						UserSession.getCurrent().getEmployee().getEmpId());

			}

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.Sfac.DPR_ENTRY_BPM_SHORTCODE,UserSession.getCurrent().getOrganisation().getOrgid());


			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			Long applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationNumber(applicationId);


			DPREntryMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = dprEntryMasterRepository.save(masEntity);
			mastDto.setDprId(masEntity.getDprId());
			List<DocumentDetailsVO> attachMast = new ArrayList<>();
			attachMast.add(mastDto.getAttachments().get(0));

			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(masEntity.getOrgId());
			requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId("DPR_ID" + MainetConstants.WINDOWS_SLASH + masEntity.getDprId());
			requestDTO.setUserId(masEntity.getCreatedBy());
			saveDocuments(attachMast, requestDTO);
			
			updateLegacyDocuments(mastDto.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());

			int count = 0;

			for (DPREntryDetailsEntity dprEntryDetailsEntity : masEntity.getDprEntryDetailsEntities()) {
				List<DocumentDetailsVO> attach = new ArrayList<>();
				attach.add(mastDto.getDprEntryDetailsDtos().get(count).getAttachmentsDet().get(count));
				RequestDTO requestDTO1 = new RequestDTO();
				requestDTO1.setOrgId(dprEntryDetailsEntity.getOrgId());
				requestDTO1.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO1.setStatus(MainetConstants.FlagA);
				requestDTO1.setIdfId("DPR_DOC_ID" + MainetConstants.WINDOWS_SLASH + dprEntryDetailsEntity.getDprdId());
				requestDTO1.setUserId(dprEntryDetailsEntity.getCreatedBy());
				saveDocuments(attach, requestDTO1);
				count++;
				// history table update

			}
			
			

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(mastDto.getApplicationNumber());
				applicationData.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				mastDto.getApplicantDetailDto().setUserId(mastDto.getCreatedBy());
				mastDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				mastDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				mastDto.getApplicantDetailDto().setExtIdentifier(mastDto.getIaId());
				
				if (requestDto != null && requestDto.getMobileNo() != null) {
					mastDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				
				for(DPREntryDetailsDto dprEntryDetailsDto :  mastDto.getDprEntryDetailsDtos())
					updateLegacyDocuments(dprEntryDetailsDto.getAttachDocsListDet(), UserSession.getCurrent().getEmployee().getEmpId());
				
				
				commonService.initiateWorkflowfreeService(applicationData, mastDto.getApplicantDetailDto());

			}




		} catch (Exception e) {
			logger.error("error occured while saving DPR Entry Req master  details" + e);
			throw new FrameworkException("error occured while saving DPR Entry Req master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}
	
	private DPREntryMasterEntity mapDtoToEntity(DPREntryMasterDto mastDto) {
		// TODO Auto-generated method stub
		DPREntryMasterEntity dprEntryMasterEntity = new DPREntryMasterEntity();
		List<DPREntryDetailsEntity> detailsList = new ArrayList<>();
		BeanUtils.copyProperties(mastDto, dprEntryMasterEntity);
		CBBOMasterEntity cbboMasterEntity = cbboMasterRepository.findOne(UserSession.getCurrent().getEmployee().getMasId());
		dprEntryMasterEntity.setCbboId(cbboMasterEntity.getCbboId());
		dprEntryMasterEntity.setCbboName(cbboMasterEntity.getCbboName());
		
		FPOMasterEntity fpoMasterEntity = fpoMasterRepository.findOne(mastDto.getFpoId());
		IAMasterEntity iaMasterEntity = iaMasterRepository.findOne(mastDto.getIaId());
		
		dprEntryMasterEntity.setFpoName(fpoMasterEntity.getFpoName());
		dprEntryMasterEntity.setIaName(iaMasterEntity.getIAName());
		
		mastDto.getDprEntryDetailsDtos().forEach(dto -> {
			DPREntryDetailsEntity entity = new DPREntryDetailsEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setDprEntryMasterEntity(dprEntryMasterEntity);
			detailsList.add(entity);
		});


		if (CollectionUtils.isNotEmpty(mastDto.getDprEntryDetailsDtos()))
			dprEntryMasterEntity.setDprEntryDetailsEntities(detailsList);
		
		return dprEntryMasterEntity;
	}

	private RequestDTO setApplicantRequestDto(DPREntryMasterDto mastDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(mastDto.getCreatedBy());

		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		// setting applicant info
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());
		if(UserSession.getCurrent().getEmployee().getEmpemail()!=null)
			requestDto.setEmail(UserSession.getCurrent().getEmployee().getEmpmobno());
		if(UserSession.getCurrent().getEmployee().getEmpmobno()!=null)
			requestDto.setMobileNo(UserSession.getCurrent().getEmployee().getEmpemail());
		if (sm.getSmFeesSchedule() == 0) {
			requestDto.setPayStatus("F");
		} else {
			requestDto.setPayStatus("Y");
		}
		return requestDto;
	}
	
	
	@Override
	public void updateApprovalStatusAndRemark(DPREntryMasterDto oldMasDto, String lastDecision,
			String status) {

		DPREntryMasterEntity dprEntryMasterEntity = dprEntryMasterRepository.findOne(oldMasDto.getDprId());

		if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_REJECTED)) {
			dprEntryMasterEntity.setStatus(lastDecision);


		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)) {
			dprEntryMasterEntity.setStatus(status);

		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED)) {
			dprEntryMasterEntity.setStatus(lastDecision);

		}

		dprEntryMasterEntity.setAuthRemark(oldMasDto.getAuthRemark());

		dprEntryMasterRepository.save(dprEntryMasterEntity);


	}

	@Override
	public DPREntryMasterDto fetchDPREntryReqDetailbyAppId(Long appNumber) {
		// TODO Auto-generated method stub
		DPREntryMasterDto dprEntryMasterDto = new DPREntryMasterDto();

		DPREntryMasterEntity dprEntryMasterEntity =  dprEntryMasterRepository.findByApplicationNumber(appNumber);

		

		BeanUtils.copyProperties(dprEntryMasterEntity, dprEntryMasterDto);
		
		List<AttachDocs> finalAttachList = new ArrayList<>();
		
		List<String> identifer1 = new ArrayList<>();
		identifer1.add("DPR_ID" + MainetConstants.WINDOWS_SLASH + dprEntryMasterEntity.getDprId());

		// get attached document
		final List<AttachDocs> attachDoc = ApplicationContextProvider.getApplicationContext()
				.getBean(IAttachDocsService.class)
				.findByIdfInQuery(dprEntryMasterEntity.getOrgId(), identifer1);
		if (!attachDoc.isEmpty()) {
			for(AttachDocs attachDocs2 : attachDoc) {
				finalAttachList.add(attachDocs2);
			}

		}
		dprEntryMasterDto.setAttachDocsList(attachDoc);
		List<DPREntryDetailsDto> dprEntryDetailsDtos = new ArrayList<>();
		
		for(DPREntryDetailsEntity dprEntryDetailsEntity : dprEntryDetailsRepository.findByDprEntryMasterEntity(dprEntryMasterEntity)){

			DPREntryDetailsDto dprEntryDetailsDto = new DPREntryDetailsDto();
			BeanUtils.copyProperties(dprEntryDetailsEntity, dprEntryDetailsDto);
			
			List<String> identifer = new ArrayList<>();
			identifer.add("DPR_DOC_ID" + MainetConstants.WINDOWS_SLASH + dprEntryDetailsEntity.getDprdId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(dprEntryDetailsEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			dprEntryDetailsDto.setAttachDocsListDet(attachDocs);

			dprEntryDetailsDtos.add(dprEntryDetailsDto);
			
		}
		
		
		
	

		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		dprEntryMasterDto.setDprEntryDetailsDtos(dprEntryDetailsDtos);

		return dprEntryMasterDto;
	}

	private Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient) {
		Set<File> fileList = null;

		Long x = 0L;
		Map<Long, Set<File>> fileMap = new HashMap<>();
		for (AttachDocs doc : attachDocs) {
			fileList = new HashSet<>();
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
			String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
			final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
					existingPath);

			String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
					existingPath);

			directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
			FileOutputStream fos = null;
			File file = null;
			try {
				final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

				Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

				file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

				fos = new FileOutputStream(file);

				fos.write(image);

				fos.close();

			} catch (final Exception e) {
				throw new FrameworkException("Exception in getting getUploadedFileList", e);
			} finally {
				try {

					if (fos != null) {
						fos.close();
					}

				} catch (final IOException e) {
					throw new FrameworkException("Exception in getting getUploadedFileList", e);
				}
			}
			fileList.add(file);
			fileMap.put(x, fileList);
			x++;
		}


		return fileMap;
	}

}
