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
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.abm.mainet.sfac.domain.MilestoneCBBODetEntity;
import com.abm.mainet.sfac.domain.MilestoneCompletionDocDetailsEntity;
import com.abm.mainet.sfac.domain.MilestoneCompletionMasterEntity;
import com.abm.mainet.sfac.domain.MilestoneDeliverablesEntity;
import com.abm.mainet.sfac.domain.MilestoneMasterEntity;
import com.abm.mainet.sfac.dto.MilestoneCompletionDocDetailsDto;
import com.abm.mainet.sfac.dto.MilestoneCompletionMasterDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;
import com.abm.mainet.sfac.repository.MilestoneCBBODetRepository;
import com.abm.mainet.sfac.repository.MilestoneCompletionDocDetailsRepository;
import com.abm.mainet.sfac.repository.MilestoneCompletionMasterRepository;
import com.abm.mainet.sfac.repository.MilestoneDeliverablesRepository;
import com.abm.mainet.sfac.repository.MilestoneMasterRepository;

@Service
public class MilestoneCompletionServiceImpl implements MilestoneCompletionService{

	private static final Logger logger = Logger.getLogger(MilestoneCompletionServiceImpl.class);

	@Autowired MilestoneMasterRepository milestoneEntryMasterRepository;

	@Autowired MilestoneCBBODetRepository milestoneCBBODetRepository;

	@Autowired MilestoneCompletionMasterRepository milestoneCompletionMasterRepository;

	@Autowired MilestoneCompletionDocDetailsRepository milestoneCompletionDocDetailsRepository;

	@Autowired MilestoneDeliverablesRepository milestoneDeliverablesRepository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Autowired
	private ApplicationService applicationService;



	@Override
	public List<MilestoneMasterDto> getMilestoneDetails(Long cbboId) {
		// TODO Auto-generated method stub

		List<Long> msIdList =	milestoneCompletionMasterRepository.findByCbboId(cbboId);

		List<MilestoneMasterEntity> milestoneMasterEntities = new ArrayList<>();
		List<MilestoneCBBODetEntity> milestoneCBBODetEntities = milestoneCBBODetRepository.findByCbboID(cbboId);

		List<MilestoneMasterDto> milestoneMasterDtos = new ArrayList<MilestoneMasterDto>();
		if(msIdList.size()>0)
			milestoneMasterEntities = milestoneEntryMasterRepository.findByMsIdNotInAndMilestoneCBBODetEntitiesIn(msIdList, milestoneCBBODetEntities);
		else
			milestoneMasterEntities = milestoneEntryMasterRepository.findByMilestoneCBBODetEntitiesIn(milestoneCBBODetEntities);

		for(MilestoneMasterEntity milestoneMasterEntity : milestoneMasterEntities) {
			MilestoneMasterDto milestoneMasterDto = new MilestoneMasterDto();

			BeanUtils.copyProperties(milestoneMasterEntity, milestoneMasterDto);
			milestoneMasterDtos.add(milestoneMasterDto);
		}

		return milestoneMasterDtos;

	}

	@Override
	public MilestoneCompletionMasterDto findMilestoneDetails(Long msId, Long cbboId) {
		// TODO Auto-generated method stub
		MilestoneCompletionMasterDto milestoneCompletionMasterDto = new MilestoneCompletionMasterDto();
		List<MilestoneCompletionDocDetailsDto> milestoneCompletionDocDetailsDtos = new ArrayList<>(); 
		MilestoneMasterEntity milestoneMasterEntity = milestoneEntryMasterRepository.findOne(msId);
		if(milestoneMasterEntity!=null) {
			MilestoneCBBODetEntity milestoneCBBODetEntity =	milestoneCBBODetRepository.findByMilestoneMasterEntityAndCbboID(milestoneMasterEntity,cbboId);
			milestoneCompletionMasterDto.setMsId(msId);
			milestoneCompletionMasterDto.setMilestoneName(milestoneMasterEntity.getMilestoneId());
			milestoneCompletionMasterDto.setDateOfWorkOrder(milestoneCBBODetEntity.getDateOfWorkOrder());
			milestoneCompletionMasterDto.setTargetDate(DateUtils.addMonths(milestoneCBBODetEntity.getDateOfWorkOrder(), milestoneCBBODetEntity.getTargetAge().intValue()));
			milestoneCompletionMasterDto.setIaId(milestoneMasterEntity.getIaId());
			milestoneCompletionMasterDto.setIaName(milestoneMasterEntity.getIaName());
			milestoneCompletionMasterDto.setCbboId(cbboId);
			milestoneCompletionMasterDto.setAllocationBudget(milestoneCBBODetEntity.getAllocationBudget());

			List<MilestoneDeliverablesEntity> milestoneDeliverablesEntities = milestoneDeliverablesRepository.findByMilestoneMasterEntity(milestoneMasterEntity);
			for(MilestoneDeliverablesEntity milestoneDeliverablesEntity : milestoneDeliverablesEntities) {
				MilestoneCompletionDocDetailsDto milestoneCompletionDocDetailsDto = new MilestoneCompletionDocDetailsDto();
				milestoneCompletionDocDetailsDto.setDocDescription(milestoneDeliverablesEntity.getDeliverables());

				milestoneCompletionDocDetailsDtos.add(milestoneCompletionDocDetailsDto);
			}
			milestoneCompletionMasterDto.setMilestoneCompletionDocDetailsDtos(milestoneCompletionDocDetailsDtos);
		}
		return milestoneCompletionMasterDto;
	}

	@Transactional
	@Override
	public MilestoneCompletionMasterDto saveAndUpdateApplication(MilestoneCompletionMasterDto mastDto) {
		try {

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.Sfac.MILESTONE_COM_BPM_SHORTCODE,UserSession.getCurrent().getOrganisation().getOrgid());


			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			Long applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationNumber(applicationId);


			MilestoneCompletionMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = milestoneCompletionMasterRepository.save(masEntity);
			mastDto.setMscId(masEntity.getMscId());

			List<DocumentDetailsVO> attachMast = new ArrayList<>();
			attachMast.add(mastDto.getAttachments().get(0));

			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(masEntity.getOrgId());
			requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId("MSC_ID" + MainetConstants.WINDOWS_SLASH + masEntity.getMscId());
			requestDTO.setUserId(masEntity.getCreatedBy());
			saveDocuments(attachMast, requestDTO);

			updateLegacyDocuments(mastDto.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());

			int count = 0;

			for (MilestoneCompletionDocDetailsEntity milestoneCompletionDocDetailsEntity : masEntity.getMilestoneCompletionDocDetailsEntities()) {
				List<DocumentDetailsVO> attach = new ArrayList<>();
				attach.add(mastDto.getMilestoneCompletionDocDetailsDtos().get(count).getAttachments().get(count+1));
				requestDTO = new RequestDTO();
				requestDTO.setOrgId(milestoneCompletionDocDetailsEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("MSC_DOC_ID" + MainetConstants.WINDOWS_SLASH + milestoneCompletionDocDetailsEntity.getMscdId());
				requestDTO.setUserId(milestoneCompletionDocDetailsEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);
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

				for(MilestoneCompletionDocDetailsDto milestoneCompletionDocDetailsDto :  mastDto.getMilestoneCompletionDocDetailsDtos())
					updateLegacyDocuments(milestoneCompletionDocDetailsDto.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());

				commonService.initiateWorkflowfreeService(applicationData, mastDto.getApplicantDetailDto());

			}




		} catch (Exception e) {
			logger.error("error occured while saving milestone completion master  details" + e);
			throw new FrameworkException("error occured while saving milestone completion master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
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

	private MilestoneCompletionMasterEntity mapDtoToEntity(MilestoneCompletionMasterDto mastDto) {
		MilestoneCompletionMasterEntity masEntity = new MilestoneCompletionMasterEntity();
		List<MilestoneCompletionDocDetailsEntity> detailsList = new ArrayList<>();


		BeanUtils.copyProperties(mastDto, masEntity);

		mastDto.getMilestoneCompletionDocDetailsDtos().forEach(dto -> {
			MilestoneCompletionDocDetailsEntity entity = new MilestoneCompletionDocDetailsEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMilestoneCompletionMasterEntity(masEntity);
			detailsList.add(entity);
		});


		if (CollectionUtils.isNotEmpty(mastDto.getMilestoneCompletionDocDetailsDtos()))
			masEntity.setMilestoneCompletionDocDetailsEntities(detailsList);


		return masEntity;
	}

	private RequestDTO setApplicantRequestDto(MilestoneCompletionMasterDto mastDto, ServiceMaster sm) {
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
	public void updateApprovalStatusAndRemark(MilestoneCompletionMasterDto oldMasDto, String lastDecision,
			String status) {

		MilestoneCompletionMasterEntity milestoneCompletionMasterEntity = milestoneCompletionMasterRepository.findOne(oldMasDto.getMscId());

		if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_REJECTED)) {
			milestoneCompletionMasterEntity.setStatus(lastDecision);


		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)) {
			milestoneCompletionMasterEntity.setStatus(status);

		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED)) {
			milestoneCompletionMasterEntity.setStatus(lastDecision);

		}

		milestoneCompletionMasterEntity.setAuthRemark(oldMasDto.getAuthRemark());

		milestoneCompletionMasterRepository.save(milestoneCompletionMasterEntity);


	}

	@Override
	public MilestoneCompletionMasterDto fetchMilestoneCompletionbyAppId(Long appNumber) {
		// TODO Auto-generated method stub
		MilestoneCompletionMasterDto milestoneCompletionMasterDto = new MilestoneCompletionMasterDto();

		MilestoneCompletionMasterEntity milestoneCompletionMasterEntity =  milestoneCompletionMasterRepository.findByApplicationNumber(appNumber);



		BeanUtils.copyProperties(milestoneCompletionMasterEntity, milestoneCompletionMasterDto);
		List<MilestoneCompletionDocDetailsDto> milestoneCompletionDocDetailsDtos = new ArrayList<>();
		Long count = 0L;
		List<AttachDocs> finalAttachList = new ArrayList<>();

		List<String> identifer1 = new ArrayList<>();
		identifer1.add("MSC_ID" + MainetConstants.WINDOWS_SLASH + milestoneCompletionMasterEntity.getMscId());

		// get attached document
		final List<AttachDocs> attachDoc = ApplicationContextProvider.getApplicationContext()
				.getBean(IAttachDocsService.class)
				.findByIdfInQuery(milestoneCompletionMasterEntity.getOrgId(), identifer1);
		if (!attachDoc.isEmpty()) {
			for(AttachDocs attachDocs2 : attachDoc) {
				finalAttachList.add(attachDocs2);
			}

		}
		milestoneCompletionMasterDto.setAttachDocsList(attachDoc);



		for(MilestoneCompletionDocDetailsEntity milestoneCompletionDocDetailsEntity : milestoneCompletionDocDetailsRepository.findByMilestoneCompletionMasterEntity(milestoneCompletionMasterEntity)){

			MilestoneCompletionDocDetailsDto milestoneCompletionDocDetailsDto = new MilestoneCompletionDocDetailsDto();
			BeanUtils.copyProperties(milestoneCompletionDocDetailsEntity, milestoneCompletionDocDetailsDto);

			List<String> identifer = new ArrayList<>();
			identifer.add("MSC_DOC_ID" + MainetConstants.WINDOWS_SLASH + milestoneCompletionDocDetailsEntity.getMscdId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(milestoneCompletionDocDetailsEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			milestoneCompletionDocDetailsDto.setAttachDocsList(attachDocs);

			milestoneCompletionDocDetailsDtos.add(milestoneCompletionDocDetailsDto);
			count++;
		}



		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		milestoneCompletionMasterDto.setMilestoneCompletionDocDetailsDtos(milestoneCompletionDocDetailsDtos);


		return milestoneCompletionMasterDto;
	}

	public Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
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

	@Override
	public MilestoneCompletionMasterDto getDetailById(Long mscId) {
		// TODO Auto-generated method stub
		MilestoneCompletionMasterDto milestoneCompletionMasterDto = new MilestoneCompletionMasterDto();

		MilestoneCompletionMasterEntity milestoneCompletionMasterEntity =  milestoneCompletionMasterRepository.findOne(mscId);



		BeanUtils.copyProperties(milestoneCompletionMasterEntity, milestoneCompletionMasterDto);
		List<MilestoneCompletionDocDetailsDto> milestoneCompletionDocDetailsDtos = new ArrayList<>();
		Long count = 0L;
		List<AttachDocs> finalAttachList = new ArrayList<>();

		List<String> identifer1 = new ArrayList<>();
		identifer1.add("MSC_ID" + MainetConstants.WINDOWS_SLASH + milestoneCompletionMasterEntity.getMscId());

		// get attached document
		final List<AttachDocs> attachDoc = ApplicationContextProvider.getApplicationContext()
				.getBean(IAttachDocsService.class)
				.findByIdfInQuery(milestoneCompletionMasterEntity.getOrgId(), identifer1);
		if (!attachDoc.isEmpty()) {
			for(AttachDocs attachDocs2 : attachDoc) {
				finalAttachList.add(attachDocs2);
			}

		}
		milestoneCompletionMasterDto.setAttachDocsList(attachDoc);



		for(MilestoneCompletionDocDetailsEntity milestoneCompletionDocDetailsEntity : milestoneCompletionDocDetailsRepository.findByMilestoneCompletionMasterEntity(milestoneCompletionMasterEntity)){

			MilestoneCompletionDocDetailsDto milestoneCompletionDocDetailsDto = new MilestoneCompletionDocDetailsDto();
			BeanUtils.copyProperties(milestoneCompletionDocDetailsEntity, milestoneCompletionDocDetailsDto);

			List<String> identifer = new ArrayList<>();
			identifer.add("MSC_DOC_ID" + MainetConstants.WINDOWS_SLASH + milestoneCompletionDocDetailsEntity.getMscdId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(milestoneCompletionDocDetailsEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			milestoneCompletionDocDetailsDto.setAttachDocsList(attachDocs);

			milestoneCompletionDocDetailsDtos.add(milestoneCompletionDocDetailsDto);
			count++;
		}



		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		milestoneCompletionMasterDto.setMilestoneCompletionDocDetailsDtos(milestoneCompletionDocDetailsDtos);


		return milestoneCompletionMasterDto;
	}

	@Override
	public List<MilestoneCompletionMasterDto> getMilestoneDetails(Long iaId, Long cbboId, String status) {
		// TODO Auto-generated method stub
		List<MilestoneCompletionMasterEntity> milestoneCompletionMasterEntities = new ArrayList<>();
		List<MilestoneCompletionMasterDto>  milestoneCompletionMasterDtos = new ArrayList<>();
		if((status!=null && !status.isEmpty()) && (cbboId!=null && cbboId!= 0) && (iaId!=null && iaId != 0) ) {
			milestoneCompletionMasterEntities = milestoneCompletionMasterRepository.findByCbboIdAndIaIdAndStatus(cbboId, iaId, status);
		}
		else {
			milestoneCompletionMasterEntities =	milestoneCompletionMasterRepository.findByCbboIdAndIaId(cbboId, iaId);
		}
		for(MilestoneCompletionMasterEntity milestoneCompletionMasterEntity : milestoneCompletionMasterEntities) {
			MilestoneCompletionMasterDto milestoneCompletionMasterDto = new MilestoneCompletionMasterDto();
			BeanUtils.copyProperties(milestoneCompletionMasterEntity, milestoneCompletionMasterDto);
			milestoneCompletionMasterDtos.add(milestoneCompletionMasterDto);
		}
		return milestoneCompletionMasterDtos;
	}

	@Override
	public List<MilestoneMasterDto> getMilestoneDetailsByID(Long msId) {
		// TODO Auto-generated method stub



		List<MilestoneMasterDto> milestoneMasterDtos = new ArrayList<MilestoneMasterDto>();

		MilestoneMasterEntity milestoneMasterEntity = milestoneEntryMasterRepository.findOne(msId);

		MilestoneMasterDto milestoneMasterDto = new MilestoneMasterDto();

		BeanUtils.copyProperties(milestoneMasterEntity, milestoneMasterDto);
		milestoneMasterDtos.add(milestoneMasterDto);


		return milestoneMasterDtos;

	}

}
