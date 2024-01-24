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
import com.abm.mainet.sfac.domain.AuditBalanceSheetKeyParameterEntity;
import com.abm.mainet.sfac.domain.AuditBalanceSheetMasterEntity;
import com.abm.mainet.sfac.domain.AuditBalanceSheetSubParameterDetail;
import com.abm.mainet.sfac.domain.AuditBalanceSheetSubParameterEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.dto.AuditBalanceSheetKeyParameterDto;
import com.abm.mainet.sfac.dto.AuditBalanceSheetMasterDto;
import com.abm.mainet.sfac.dto.AuditBalanceSheetSubParameterDetailDto;
import com.abm.mainet.sfac.dto.AuditBalanceSheetSubParameterDto;
import com.abm.mainet.sfac.repository.AuditBalanceSheetKeyParameterRepository;
import com.abm.mainet.sfac.repository.AuditBalanceSheetMasterRepository;
import com.abm.mainet.sfac.repository.AuditBalanceSheetSubParameterDetRepository;
import com.abm.mainet.sfac.repository.AuditBalanceSheetSubParameterRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.ui.model.ABSEntryFormModel;

@Service
public class AuditBalanceSheetEntryServiceImpl implements AuditBalanceSheetEntryService{

	private static final Logger logger = Logger.getLogger(AuditBalanceSheetEntryServiceImpl.class);

	@Autowired FPOMasterRepository fpoMasterRepository;

	@Autowired AuditBalanceSheetMasterRepository auditBalanceSheetMasterRepository;

	@Autowired AuditBalanceSheetKeyParameterRepository keyRepo;

	@Autowired AuditBalanceSheetSubParameterRepository subParamRepo;

	@Autowired AuditBalanceSheetSubParameterDetRepository subDetRepo;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Override
	public AuditBalanceSheetMasterDto findFPODetails(Long masId) {

		AuditBalanceSheetMasterDto auditBalanceSheetMasterDto = new AuditBalanceSheetMasterDto();
		FPOMasterEntity fpoMasterEntity =   fpoMasterRepository.findOne(masId);
		auditBalanceSheetMasterDto.setFpoId(fpoMasterEntity.getFpoId());
		auditBalanceSheetMasterDto.setFpoName(fpoMasterEntity.getFpoName());
		auditBalanceSheetMasterDto.setCbboId(fpoMasterEntity.getCbboId());
		auditBalanceSheetMasterDto.setCbboName(fpoMasterEntity.getCbboName());
		auditBalanceSheetMasterDto.setFpoAddress(fpoMasterEntity.getFpoOffAddr());

		return auditBalanceSheetMasterDto;
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
	public AuditBalanceSheetMasterDto saveDetails(AuditBalanceSheetMasterDto masDto, ABSEntryFormModel absEntryFormModel) {

		Long applicationId = null;
		try {
			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.ABS_ENTRY_BPM_SHORTCODE,
					masDto.getOrgId());
			RequestDTO requestDto = setApplicantRequestDto(masDto, sm);
			applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				masDto.setApplicationId(applicationId);
			// to generate assessment no



			logger.info("saveDetails Started");
			AuditBalanceSheetMasterEntity	masEntity=mapDtoToEntity(masDto);
			logger.info("saveDetails Ended");

			List<DocumentDetailsVO> attachMast = new ArrayList<>();
			attachMast.add(masDto.getAttachments().get(0));

			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(masEntity.getOrgId());
			requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId("ABS_ID" + MainetConstants.WINDOWS_SLASH + masEntity.getAbsId());
			requestDTO.setUserId(masEntity.getCreatedBy());
			saveDocuments(attachMast, requestDTO);

			updateLegacyDocuments(masDto.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(masDto.getApplicationId());
				applicationData.setOrgId(masDto.getOrgId());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				masDto.getApplicantDetailDto().setUserId(masDto.getCreatedBy());
				masDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				masDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				if (requestDto != null && requestDto.getMobileNo() != null) {
					masDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				masDto.getApplicantDetailDto().setExtIdentifier(masDto.getCbboId());
				commonService.initiateWorkflowfreeService(applicationData, masDto.getApplicantDetailDto());
			}
		} catch (Exception e) {
			logger.error("Error occured While saving Audit Balance Sheet Entry details" + e);
			throw new FrameworkException("Error occured While saving Audit Balance Sheet Entry details", e);
		}
		logger.info("saveDetails ended");
		return masDto;
	}

	private AuditBalanceSheetMasterEntity mapDtoToEntity(AuditBalanceSheetMasterDto masDto) {
		AuditBalanceSheetMasterEntity masEntity = new AuditBalanceSheetMasterEntity();

		BeanUtils.copyProperties(masDto, masEntity);
		masEntity = auditBalanceSheetMasterRepository.save(masEntity);

		for (AuditBalanceSheetKeyParameterDto key : masDto.getAuditBalanceSheetKeyParameterDtos()) {
			AuditBalanceSheetKeyParameterEntity keyEntity = new AuditBalanceSheetKeyParameterEntity();
			BeanUtils.copyProperties(key, keyEntity);
			keyEntity.setMasterEntity(masEntity);
			keyEntity = keyRepo.save(keyEntity);

			for (AuditBalanceSheetSubParameterDto subDto : key.getAuditBalanceSheetSubParameterDtos()) {
				AuditBalanceSheetSubParameterEntity subEntity = new AuditBalanceSheetSubParameterEntity();
				BeanUtils.copyProperties(subDto, subEntity);
				subEntity.setKeyMasterEntity(keyEntity);
				subEntity = subParamRepo.save(subEntity);

				for (AuditBalanceSheetSubParameterDetailDto det : subDto.getAuditBalanceSheetSubParameterDetailDtos()) {
					AuditBalanceSheetSubParameterDetail subDetEntity = new AuditBalanceSheetSubParameterDetail();
					BeanUtils.copyProperties(det, subDetEntity);
					subDetEntity.setAbsSubParamEntity(subEntity);
					subDetEntity = subDetRepo.save(subDetEntity);
				}
			}
		}


		return masEntity;
	}

	private RequestDTO setApplicantRequestDto(AuditBalanceSheetMasterDto masDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masDto.getCreatedBy());
		requestDto.setOrgId(masDto.getOrgId());
		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
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
	public AuditBalanceSheetMasterDto fetchABSDetails(Long absId) {
		AuditBalanceSheetMasterDto dto = new AuditBalanceSheetMasterDto();
		AuditBalanceSheetMasterEntity entity = new AuditBalanceSheetMasterEntity();
		List<AuditBalanceSheetKeyParameterDto> keyDtoList = new ArrayList<>();
		try {
			entity = auditBalanceSheetMasterRepository.findOne(absId);
			if (entity != null) {
				BeanUtils.copyProperties(entity, dto);

				int keyCount =0;
				List<AuditBalanceSheetKeyParameterEntity> keyList = keyRepo.findByMasterEntity(entity);
				for (AuditBalanceSheetKeyParameterEntity key : keyList) {
					AuditBalanceSheetKeyParameterDto keyDto = new AuditBalanceSheetKeyParameterDto();
					BeanUtils.copyProperties(key, keyDto);
					keyDtoList.add(keyDto);

					int subCount = 0;
					List<AuditBalanceSheetSubParameterDto> subDtoList = new ArrayList<>();
					List<AuditBalanceSheetSubParameterEntity> subList = subParamRepo.findByKeyMasterEntity(key);
					for (AuditBalanceSheetSubParameterEntity sub : subList) {
						AuditBalanceSheetSubParameterDto subDto = new AuditBalanceSheetSubParameterDto();
						BeanUtils.copyProperties(sub, subDto);
						subDtoList.add(subDto);
						keyDtoList.get(keyCount).setAuditBalanceSheetSubParameterDtos(subDtoList);

						List<AuditBalanceSheetSubParameterDetailDto> subDetDtoList = new ArrayList<>();
						List<AuditBalanceSheetSubParameterDetail> subDetList = subDetRepo.findByAbsSubParamEntity(sub);
						for (AuditBalanceSheetSubParameterDetail subDet : subDetList) {
							AuditBalanceSheetSubParameterDetailDto detDto = new AuditBalanceSheetSubParameterDetailDto();
							BeanUtils.copyProperties(subDet,detDto);
							;
							subDetDtoList.add(detDto);
							subDtoList.get(subCount).setAuditBalanceSheetSubParameterDetailDtos(subDetDtoList);
						}
						subCount++;
					}
					keyCount++;
				}
				dto.setAuditBalanceSheetKeyParameterDtos(keyDtoList);

			}
			List<AttachDocs> finalAttachList = new ArrayList<>();
			
			List<String> identifer1 = new ArrayList<>();
			identifer1.add("ABS_ID" + MainetConstants.WINDOWS_SLASH + entity.getAbsId());

			// get attached document
			final List<AttachDocs> attachDoc = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(entity.getOrgId(), identifer1);
			if (!attachDoc.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDoc) {
					finalAttachList.add(attachDocs2);
				}

			}
			dto.setAttachDocsList(attachDoc);
			
			FileUploadUtility.getCurrent().setFileMap(
					getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));

		} catch (Exception e) {
			logger.error("Error Occured while fetching ABS Entry details" + e);
		}

		return dto;
	}

	@Override
	public void updateApprovalStatusAndRemark(AuditBalanceSheetMasterDto oldMasDto, 
			String lastDecision, String status) {

		AuditBalanceSheetMasterEntity auditBalanceSheetMasterEntity = auditBalanceSheetMasterRepository.findOne(oldMasDto.getAbsId());

		if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_REJECTED)) {
			auditBalanceSheetMasterEntity.setAbsStatus(lastDecision);


		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)) {
			auditBalanceSheetMasterEntity.setAbsStatus(status);

		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED)) {
			auditBalanceSheetMasterEntity.setAbsStatus(lastDecision);

		}

		auditBalanceSheetMasterEntity.setRemark(oldMasDto.getRemark());

		auditBalanceSheetMasterRepository.save(auditBalanceSheetMasterEntity);


	}

	@Override
	public AuditBalanceSheetMasterDto fetchABSEntryReqDetailbyAppId(Long applicationId) {
		AuditBalanceSheetMasterDto dto = new AuditBalanceSheetMasterDto();
		AuditBalanceSheetMasterEntity entity = new AuditBalanceSheetMasterEntity();
		List<AuditBalanceSheetKeyParameterDto> keyDtoList = new ArrayList<>();
		try {
			entity = auditBalanceSheetMasterRepository.findByApplicationId(applicationId);
			if (entity != null) {
				BeanUtils.copyProperties(entity, dto);

				int keyCount =0;
				List<AuditBalanceSheetKeyParameterEntity> keyList = keyRepo.findByMasterEntity(entity);
				for (AuditBalanceSheetKeyParameterEntity key : keyList) {
					AuditBalanceSheetKeyParameterDto keyDto = new AuditBalanceSheetKeyParameterDto();
					BeanUtils.copyProperties(key, keyDto);
					keyDtoList.add(keyDto);

					int subCount = 0;
					List<AuditBalanceSheetSubParameterDto> subDtoList = new ArrayList<>();
					List<AuditBalanceSheetSubParameterEntity> subList = subParamRepo.findByKeyMasterEntity(key);
					for (AuditBalanceSheetSubParameterEntity sub : subList) {
						AuditBalanceSheetSubParameterDto subDto = new AuditBalanceSheetSubParameterDto();
						BeanUtils.copyProperties(sub, subDto);
						subDtoList.add(subDto);
						keyDtoList.get(keyCount).setAuditBalanceSheetSubParameterDtos(subDtoList);

						List<AuditBalanceSheetSubParameterDetailDto> subDetDtoList = new ArrayList<>();
						List<AuditBalanceSheetSubParameterDetail> subDetList = subDetRepo.findByAbsSubParamEntity(sub);
						for (AuditBalanceSheetSubParameterDetail subDet : subDetList) {
							AuditBalanceSheetSubParameterDetailDto detDto = new AuditBalanceSheetSubParameterDetailDto();
							BeanUtils.copyProperties(subDet,detDto);
							;
							subDetDtoList.add(detDto);
							subDtoList.get(subCount).setAuditBalanceSheetSubParameterDetailDtos(subDetDtoList);
						}
						subCount++;
					}
					keyCount++;
				}
				dto.setAuditBalanceSheetKeyParameterDtos(keyDtoList);
			}

			List<AttachDocs> finalAttachList = new ArrayList<>();

			List<String> identifer1 = new ArrayList<>();
			identifer1.add("ABS_ID" + MainetConstants.WINDOWS_SLASH + entity.getAbsId());

			// get attached document
			final List<AttachDocs> attachDoc = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(entity.getOrgId(), identifer1);
			if (!attachDoc.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDoc) {
					finalAttachList.add(attachDocs2);
				}

			}
			dto.setAttachDocsList(attachDoc);





			FileUploadUtility.getCurrent().setFileMap(
					getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));



		} catch (Exception e) {
			logger.error("Error Occured while fetching ABS Entry details" + e);
		}

		return dto;
	}

	private Map<Long, Set<File>> getUploadedFileList(List<AttachDocs>  attachDocs,
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
