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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.domain.CircularNotificationMasterEntity;
import com.abm.mainet.sfac.domain.CircularNotiicationDetEntity;
import com.abm.mainet.sfac.dto.CircularNotificationMasterDto;
import com.abm.mainet.sfac.dto.CircularNotiicationDetDto;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.repository.CircularNotificationDetRepository;
import com.abm.mainet.sfac.repository.CircularNotificationMasterRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.repository.IAMasterRepository;
import com.abm.mainet.smsemail.service.IEmailService;

@Service
public class CircularNotificationServiceImpl implements CircularNotificationService{

	@Value("${upload.physicalPath}")
	private String filenetPath;

	private static final Logger logger = Logger.getLogger(CircularNotificationServiceImpl.class);

	@Autowired CircularNotificationMasterRepository circularNotificationMasterRepository;

	@Autowired CircularNotificationDetRepository circularNotificationDetRepository;

	@Autowired private AttachDocsRepository attachDocsRepository;

	@Autowired IEmailService emailService;

	@Autowired CBBOMasterRepository cbboMasterRepository;

	@Autowired FPOMasterRepository fpoMasterRepository;

	@Autowired IAMasterRepository iaMasterrepository;

	@Autowired EmployeeJpaRepository employeeJpaRepo;

	@Autowired IOrganisationService orgService;

	CircularNotificationBackgroundService  circularNotificationBackgroundService = new CircularNotificationBackgroundService();




	@Override
	public CircularNotificationMasterDto saveAndUpdateApplication(CircularNotificationMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			CircularNotificationMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = circularNotificationMasterRepository.save(masEntity);

			List<DocumentDetailsVO> attach = new ArrayList<>();


			mastDto.setCnId(masEntity.getCnId());
			RequestDTO requestDTO = new RequestDTO();
			if(mastDto.getAttachments().size()>0) {
				attach.add(mastDto.getAttachments().get(0));


				requestDTO.setOrgId(masEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("CN_DOC_ID" + MainetConstants.WINDOWS_SLASH + masEntity.getCnId());
				requestDTO.setUserId(masEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);
			}

			updateLegacyDocuments(mastDto.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());


			final ExecutorService executorService = Executors.newSingleThreadExecutor();

			circularNotificationBackgroundService.setAttach(attach);
			circularNotificationBackgroundService.setFilenetPath(filenetPath);
			circularNotificationBackgroundService.setDto(mastDto);
			circularNotificationBackgroundService.setRequestDTO(requestDTO);
			circularNotificationBackgroundService.setCbboMasterRepository(cbboMasterRepository);
			circularNotificationBackgroundService.setFpoMasterRepository(fpoMasterRepository);
			circularNotificationBackgroundService.setIaMasterrepository(iaMasterrepository);
			circularNotificationBackgroundService.setOrgService(orgService);
			circularNotificationBackgroundService.setCircularNotificationMasterRepository(circularNotificationMasterRepository);
			circularNotificationBackgroundService.setEmployeeJpaRepo(employeeJpaRepo);
			circularNotificationBackgroundService.getDto().setCircularNotiicationDetDtos(mastDto.getCircularNotiicationDetDtos());
			circularNotificationBackgroundService.setEmailService(emailService);
			executorService.execute(circularNotificationBackgroundService);
			executorService.shutdown();


		} catch (Exception e) {
			logger.error("error occured while saving circular notification master  details" + e);
			throw new FrameworkException("error occured while saving circular notification master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}




	private CircularNotificationMasterEntity mapDtoToEntity(CircularNotificationMasterDto mastDto) {
		// TODO Auto-generated method stub

		CircularNotificationMasterEntity circularNotificationMasterEntity = new CircularNotificationMasterEntity();
		List<CircularNotiicationDetEntity> circularNotiicationDetEntities = new ArrayList<>();
		BeanUtils.copyProperties(mastDto, circularNotificationMasterEntity);

		mastDto.getCircularNotiicationDetDtos().forEach(dto -> {

			CircularNotiicationDetEntity entity = new CircularNotiicationDetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMasterEntity(circularNotificationMasterEntity);
			circularNotiicationDetEntities.add(entity);

		});

		if(circularNotiicationDetEntities.size()>0)
			circularNotificationMasterEntity.setCircularNotiicationDetEntities(circularNotiicationDetEntities);


		return circularNotificationMasterEntity;
	}

	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
				requestDTO);
	}

	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}
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
	public CircularNotificationMasterDto getDetailById(Long cnId) {
		// TODO Auto-generated method stub
		CircularNotificationMasterDto circularNotificationMasterDto = new CircularNotificationMasterDto();

		CircularNotificationMasterEntity circularNotificationMasterEntity =  circularNotificationMasterRepository.findOne(cnId);



		BeanUtils.copyProperties(circularNotificationMasterEntity, circularNotificationMasterDto);
		List<CircularNotiicationDetDto> circularNotiicationDetDtos = new ArrayList<>();
		List<AttachDocs> finalAttachList = new ArrayList<>();

		List<String> identifer1 = new ArrayList<>();
		identifer1.add("CN_DOC_ID" + MainetConstants.WINDOWS_SLASH + circularNotificationMasterEntity.getCnId());

		// get attached document
		final List<AttachDocs> attachDoc = ApplicationContextProvider.getApplicationContext()
				.getBean(IAttachDocsService.class)
				.findByIdfInQuery(circularNotificationMasterEntity.getOrgId(), identifer1);
		if (!attachDoc.isEmpty()) {
			for(AttachDocs attachDocs2 : attachDoc) {
				finalAttachList.add(attachDocs2);
			}
		}
		circularNotificationMasterDto.setAttachDocsList(attachDoc);
		for(CircularNotiicationDetEntity circularNotiicationDetEntity : circularNotificationDetRepository.findByMasterEntity(circularNotificationMasterEntity)){

			CircularNotiicationDetDto circularNotiicationDetDto = new CircularNotiicationDetDto();
			BeanUtils.copyProperties(circularNotiicationDetEntity, circularNotiicationDetDto);
			circularNotiicationDetDtos.add(circularNotiicationDetDto);
		}

		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		circularNotificationMasterDto.setCircularNotiicationDetDtos(circularNotiicationDetDtos);


		return circularNotificationMasterDto;
	}



	@Override
	public List<CircularNotificationMasterDto> getCircularNotification(String circularTitle, String circularNo) {

		List<CircularNotificationMasterDto> circularNotificationMasterDtos = new ArrayList<>();
		List<CircularNotificationMasterEntity> circularNotificationMasterEntities = new ArrayList<>();

		if(circularTitle!=null && circularNo !=null  && circularNo!=""  && circularTitle!="")
			circularNotificationMasterEntities = circularNotificationMasterRepository.getDetails(circularTitle, circularNo);
		else if(circularTitle!=null && circularTitle != "")
			circularNotificationMasterEntities = circularNotificationMasterRepository.getDetailsCircularTitle(circularTitle);
		else if(circularNo!=null && circularNo!="")
			circularNotificationMasterEntities = circularNotificationMasterRepository.getDetailsCircularNo(circularNo);

		if(circularNotificationMasterEntities.size()>0) {
			for(CircularNotificationMasterEntity circularNotificationMasterEntity : circularNotificationMasterEntities) {
				CircularNotificationMasterDto circularNotificationMasterDto = new CircularNotificationMasterDto();
				BeanUtils.copyProperties(circularNotificationMasterEntity, circularNotificationMasterDto);
				circularNotificationMasterDtos.add(circularNotificationMasterDto);
			}
		}

		return circularNotificationMasterDtos;
	}



}
