package com.abm.mainet.workManagement.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.workManagement.domain.MileStone;
import com.abm.mainet.workManagement.domain.MileStoneEntryEntity;
import com.abm.mainet.workManagement.domain.MilestoneDetail;
import com.abm.mainet.workManagement.domain.MilestoneGeoTag;
import com.abm.mainet.workManagement.domain.TbWmsProjectMaster;
import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
import com.abm.mainet.workManagement.dto.MilestoneDetDto;
import com.abm.mainet.workManagement.dto.MilestoneEntryDto;
import com.abm.mainet.workManagement.dto.MilestoneGeoTagDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.repository.MileStoneRepository;
import com.abm.mainet.workManagement.repository.MilestoneDetailRepository;
import com.abm.mainet.workManagement.repository.MilestoneEntryRepository;
import com.abm.mainet.workManagement.repository.MilestoneGeoTagRepository;

/**
 * @author vishwajeet.kumar and SaiPrasad
 * @since 22 March 2018
 */

@Service
public class MileStoneServiceImp implements MileStoneService {

	@Autowired
	private MileStoneRepository mileStoneRepository;

	@Autowired
	private MilestoneEntryRepository mileStoneEntryRepo;

	@Autowired
	private MilestoneDetailRepository detRepository;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private MilestoneGeoTagRepository geoTagRepository;
	
	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private TenderInitiationService tenderInitiationService;

	@Override
	@Transactional
	public void addMilestoneEntry(List<MileStoneDTO> mileStoneDtoList) {

		MileStone mileStoneEntity = null;
		TbWmsProjectMaster projectEntity = null;
		WorkDefinationEntity workDefEntity = null;
		for (MileStoneDTO flowDto : mileStoneDtoList) {

			mileStoneEntity = new MileStone();
			BeanUtils.copyProperties(flowDto, mileStoneEntity);
			mileStoneEntity.setMsStartDate(UtilityService.convertStringDateToDateFormat(flowDto.getMsStartDate()));
			mileStoneEntity.setMsEndDate(UtilityService.convertStringDateToDateFormat(flowDto.getMsEndDate()));

			projectEntity = new TbWmsProjectMaster();
			projectEntity.setProjId(flowDto.getProjId());
			if ((flowDto.getWorkId() != null)) {
				workDefEntity = new WorkDefinationEntity();
				workDefEntity.setWorkId(flowDto.getWorkId());
				mileStoneEntity.setMastDetailsEntity(workDefEntity);
			}

			mileStoneEntity.setProjectMaster(projectEntity);

			mileStoneRepository.save(mileStoneEntity);

		}
	}

	// To get Milestone Records for edit
	@Override
	@Transactional(readOnly = true)
	public List<MileStoneDTO> editMilestone(Long projId, Long workId, String mileStoneType, Long orgId) {
		List<MileStone> entityList;
		if (workId != null) {
			entityList = mileStoneRepository.milestoneByProjIdworkId(projId, workId, mileStoneType, orgId);
		} else {
			entityList = mileStoneRepository.milestoneByProjId(projId, mileStoneType, orgId);

		}

		List<MileStoneDTO> dtoList = new ArrayList<>();
		if (!entityList.isEmpty()) {
			entityList.forEach(entity -> {
				MileStoneDTO flowDto = new MileStoneDTO();
				BeanUtils.copyProperties(entity, flowDto);
				flowDto.setMsStartDate(
						(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getMsStartDate())));
				flowDto.setMsEndDate((new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getMsEndDate())));

				dtoList.add(flowDto);
			});
		}

		return dtoList;
	}

	// used to update Milestone form details
	@Override
	@Transactional
	public void saveAndUpdateMilestone(List<MileStoneDTO> MileStoneDtos, String ids) {
		MileStone mileStoneEntity;
		TbWmsProjectMaster projectEntity = null;
		WorkDefinationEntity workDefEntity = null;
		List<Long> removeIds = null;
		for (MileStoneDTO flowDto : MileStoneDtos) {

			mileStoneEntity = new MileStone();
			BeanUtils.copyProperties(flowDto, mileStoneEntity);
			mileStoneEntity.setMileStoneType(flowDto.getMileStoneType());
			mileStoneEntity.setMsStartDate(UtilityService.convertStringDateToDateFormat(flowDto.getMsStartDate()));
			mileStoneEntity.setMsEndDate(UtilityService.convertStringDateToDateFormat(flowDto.getMsEndDate()));

			projectEntity = new TbWmsProjectMaster();
			projectEntity.setProjId(flowDto.getProjId());

			if (flowDto.getWorkId() != null && flowDto.getWorkId() != 0) {
				workDefEntity = new WorkDefinationEntity();
				workDefEntity.setWorkId(flowDto.getWorkId());
				mileStoneEntity.setMastDetailsEntity(workDefEntity);
			}

			mileStoneEntity.setProjectMaster(projectEntity);
			mileStoneRepository.save(mileStoneEntity);
		}

		if (ids != null && !ids.isEmpty()) {
			removeIds = new ArrayList<>();
			String array[] = ids.split(MainetConstants.operator.COMMA);
			for (String id : array) {
				removeIds.add(Long.valueOf(id));
			}
		}
		if (removeIds != null && !removeIds.isEmpty()) {
			mileStoneRepository.deleteEntityRecords(removeIds);

		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<WmsProjectMasterDto> findALLMileStoneList(Long projId, Long orgId, String milestoneFlag) {

		List<MileStone> mileStoneEntity = mileStoneRepository.searchAllMileStoneProjectId(projId, orgId, milestoneFlag);
		List<WmsProjectMasterDto> projectMasterDto = new ArrayList<>();
		WmsProjectMasterDto dto = null;

		for (MileStone mileStone : mileStoneEntity) {

			if (mileStone.getProjectMaster() != null
					&& mileStone.getProjectMaster().getProjId().longValue() == projId.longValue()
					&& mileStone.getMastDetailsEntity() != null) {
				dto = new WmsProjectMasterDto();
				dto.setProjNameEng(mileStone.getProjectMaster().getProjNameEng());
				dto.setProjCode(mileStone.getMastDetailsEntity().getWorkcode());
				if(mileStone.getMastDetailsEntity().getWorkStartDate() != null) {
				dto.setStartDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(mileStone.getMastDetailsEntity().getWorkStartDate()));
				}
				if (mileStone.getMastDetailsEntity().getWorkEndDate() != null) {
					dto.setEndDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
							.format(mileStone.getMastDetailsEntity().getWorkEndDate()));
				}
				if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
						MainetConstants.ENV_PSCL))) {
					TenderWorkDto orderDto = tenderInitiationService.getTenderByWorkId(projId);
					dto.setProjActualCost(orderDto.getTenderAmount());
				} else {
					dto.setProjActualCost(mileStone.getMastDetailsEntity().getWorkEstAmt());
				}
				dto.setProjId(mileStone.getProjectMaster().getProjId());
				dto.setWorkName(mileStone.getMastDetailsEntity().getWorkName());
				dto.setWorkId(mileStone.getMastDetailsEntity().getWorkId());
				dto.setMileId(mileStone.getMileId());
				dto.setMileStoneType(mileStone.getMileStoneType());
				dto.setMileStoneDesc(mileStone.getMileStoneDesc());
				projectMasterDto.add(dto);
			} else if (mileStone.getProjectMaster() != null
					&& mileStone.getProjectMaster().getProjId().longValue() == projId.longValue()) {
				dto = new WmsProjectMasterDto();
				dto.setProjNameEng(mileStone.getProjectMaster().getProjNameEng());
				dto.setProjCode(mileStone.getProjectMaster().getProjCode());
				dto.setStartDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(mileStone.getProjectMaster().getProjStartDate()));
				dto.setEndDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(mileStone.getProjectMaster().getProjEndDate()));
				dto.setProjActualCost(mileStone.getProjectMaster().getProjActualCost());
				dto.setProjId(mileStone.getProjectMaster().getProjId());
				dto.setMileId(mileStone.getMileId());
				dto.setMileStoneType(mileStone.getMileStoneType());
				dto.setMileStoneDesc(mileStone.getMileStoneDesc());
				projectMasterDto.add(dto);
			}
		}
		return projectMasterDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WmsProjectMasterDto> findALLMileStoneListwithWorkId(Long projId, Long workId, Long orgId,
			String milestoneFlag) {
		List<MileStone> mileStoneEntity = mileStoneRepository.searchAllMileStoneWorkId(orgId, workId, milestoneFlag);
		List<WmsProjectMasterDto> projectMasterDto = new ArrayList<>();

		WmsProjectMasterDto dto = new WmsProjectMasterDto();
		if (mileStoneEntity.get(0).getMsStartDate() != null) {
			dto.setStartDate(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(mileStoneEntity.get(0).getMsStartDate()));
		}
		for (MileStone mileStone : mileStoneEntity) {
			if (mileStone.getProjectMaster().getProjId().longValue() == projId.longValue()
					&& mileStone.getMastDetailsEntity() != null
					&& mileStone.getMastDetailsEntity().getWorkId().longValue() == workId.longValue()) {

				dto.setProjCode(mileStone.getMastDetailsEntity().getWorkcode());
				dto.setProjNameEng(mileStone.getProjectMaster().getProjNameEng());
				dto.setProjNameReg(mileStone.getProjectMaster().getProjNameReg());

				if (mileStone.getMsEndDate() != null) {
					dto.setEndDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(mileStone.getMsEndDate()));
				}
				if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
						MainetConstants.ENV_PSCL))) {
					TenderWorkDto orderDto = tenderInitiationService.getTenderByWorkId(workId);
					dto.setProjActualCost(orderDto.getTenderAmount());
				} else {
					dto.setProjActualCost(mileStone.getMastDetailsEntity().getWorkEstAmt());
				}
				
				dto.setProjId(mileStone.getProjectMaster().getProjId());
				dto.setWorkName(mileStone.getMastDetailsEntity().getWorkName());
				dto.setWorkId(mileStone.getMastDetailsEntity().getWorkId());
				dto.setMileId(mileStone.getMileId());
				dto.setMileStoneType(mileStone.getMileStoneType());
				dto.setMileStoneDesc(mileStone.getMileStoneDesc());
				projectMasterDto.add(dto);
			}
		}
		return projectMasterDto;
	}

	@Override
	public boolean checkMilestone(Long projId, String mileStoneType, Long orgId) {
		boolean valid = false;

		List<MileStone> entityList = mileStoneRepository.milestoneByProjId(projId, mileStoneType, orgId);
		if (entityList != null && !entityList.isEmpty()) {
			valid = true;
		}
		return valid;
	}

	@Override
	@Transactional(readOnly = true)
	public MileStoneDTO getMileStoneDetail(Long mileId) {
		MileStoneDTO dto = new MileStoneDTO();
		MileStone entity = mileStoneRepository.findOne(mileId);
		if (entity != null)
			BeanUtils.copyProperties(entity, dto);
		dto.setMsStartDate(UtilityService.convertDateToDDMMYYYY(entity.getMsStartDate()));
		dto.setMsEndDate(UtilityService.convertDateToDDMMYYYY(entity.getMsEndDate()));
		dto.setProjId(entity.getProjectMaster().getProjId());
		if (entity.getMastDetailsEntity() != null)
			dto.setWorkId(entity.getMastDetailsEntity().getWorkId());
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MileStoneDTO> milestoneDtosByProjId(Long projId, Long orgId, String flag) {
		List<MileStoneDTO> dtos = new ArrayList<>();
		MileStoneDTO flowDto;
		List<MileStone> mileStoneEntity = mileStoneRepository.searchAllMileStoneProjectId(projId, orgId, flag);
		for (MileStone mileStone : mileStoneEntity) {
			flowDto = new MileStoneDTO();
			BeanUtils.copyProperties(mileStone, flowDto);
			flowDto.setMsStartDate(UtilityService.convertDateToDDMMYYYY(mileStone.getMsStartDate()));
			flowDto.setMsEndDate(UtilityService.convertDateToDDMMYYYY(mileStone.getMsEndDate()));
			dtos.add(flowDto);
		}
		return dtos;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MileStoneDTO> milestoneDtosWithWorkId(Long projId, Long workId, Long orgId, String flag) {
		List<MileStoneDTO> dtos = new ArrayList<>();
		MileStoneDTO flowDto;
		List<MileStone> mileStoneEntity = mileStoneRepository.searchAllMileStoneWorkId(orgId, workId, flag);
		for (MileStone mileStone : mileStoneEntity) {
			flowDto = new MileStoneDTO();
			BeanUtils.copyProperties(mileStone, flowDto);
			flowDto.setMsStartDate(UtilityService.convertDateToDDMMYYYY(mileStone.getMsStartDate()));
			flowDto.setMsEndDate(UtilityService.convertDateToDDMMYYYY(mileStone.getMsEndDate()));
			dtos.add(flowDto);
		}
		return dtos;
	}

	@Override
	@Transactional
	public void saveAndUpdateMilestoneProgress(List<MilestoneDetDto> detDtoList, List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO, List<AttachDocs> savedDocs, Long orgId) {
		MilestoneDetail detEntity = null;
		MileStone masEntity = null;
		List<MilestoneDetail> detEntityList = new ArrayList<>();

		Long mileId = detDtoList.get(0).getMileId();
		final List<AttachDocs> attachDocs;
		List<AttachDocs> ids = new ArrayList<>();
		for (MilestoneDetDto flowDetDto : detDtoList) {
			detEntity = new MilestoneDetail();
			BeanUtils.copyProperties(flowDetDto, detEntity);
			detEntity.setProUpdateDate(UtilityService.convertStringDateToDateFormat(flowDetDto.getProUpdateDate()));
			masEntity = new MileStone();
			detEntity.setMilestoneEntity(masEntity);
			detEntity.getMilestoneEntity().setMileId(mileId);
			detEntity.setMiledActive(MainetConstants.Y_FLAG);
			MilestoneDetail saveEntity = detRepository.save(detEntity);
			detEntityList.add(saveEntity);
			//D91022
			mileStoneRepository.updateMilePer(saveEntity.getPhyPercent(), mileId);
		}

		for (AttachDocs savedDoc : savedDocs) {
			boolean flag = false;
			if (!attachments.isEmpty()) {
				for (DocumentDetailsVO vo : attachments) {
					if (vo.getDocumentSerialNo().intValue() == savedDoc.getSerialNo().intValue()
							&& vo.getDocumentName().equals(savedDoc.getAttFname())) {
						flag = true;
					}
				}
				if (flag) {
					ids.add(savedDoc);
				}
			} else {
				ids.add(savedDoc);
			}

		}
		if (!ids.isEmpty()) {
			DeleteProgressDocuments(ids);
		}

		saveImages(attachments, requestDTO);
		MileStoneDTO masDto = getMileStoneDetail(mileId);

		if (masDto.getWorkId() != null) {
			attachDocs = attachDocsService.findByCode(orgId,
					ApplicationContextProvider.getApplicationContext().getBean(WorkDefinitionService.class)
							.findAllWorkDefinitionById(masDto.getWorkId()).getWorkcode()
							+ MainetConstants.operator.DOUBLE_BACKWARD_SLACE + mileId);
		} else {
			attachDocs = attachDocsService.findByCode(orgId,
					ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
							.getProjectMasterByProjId(masDto.getProjId()).getProjCode()
							+ MainetConstants.operator.DOUBLE_BACKWARD_SLACE + mileId);
		}
		if (attachDocs != null) {
			for (AttachDocs doc : attachDocs) {
				final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
						+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
				String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
				final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
						existingPath);
				Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);
				doc.setAttFromPath(
						Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);
			}
		}
		saveUpdateMilestoneGeoTag(attachDocs, detEntityList);
	}

	@Transactional
	public void saveUpdateMilestoneGeoTag(List<AttachDocs> attachDocs, List<MilestoneDetail> detEntityList) {

		MilestoneGeoTagDto geoTagDto = null;
		MilestoneGeoTag geoEntity = null;
		MilestoneDetail detEntity = null;
		Map<Integer, List<AttachDocs>> map = new HashMap<>();
		for (AttachDocs attachDoc : attachDocs) {
			Integer serNo = attachDoc.getSerialNo();
			if (map.containsKey(serNo)) {
				map.get(serNo).add(attachDoc);
			} else {
				List<AttachDocs> list = new ArrayList<>();
				list.add(attachDoc);
				map.put(serNo, list);
			}
		}
		for (final Map.Entry<Integer, List<AttachDocs>> entry : map.entrySet()) {
			for (MilestoneDetail milestoneDetail : detEntityList) {
				if (entry.getKey() == milestoneDetail.getPhyPercent().intValueExact()) {
					for (AttachDocs doc : entry.getValue()) {
						detEntity = new MilestoneDetail();
						geoTagDto = new MilestoneGeoTagDto();
						geoEntity = new MilestoneGeoTag();
						geoTagDto.setAtdId(doc.getAttId());
						javaxt.io.Image image = new javaxt.io.Image(doc.getAttFromPath());
						double[] coord = image.getGPSCoordinate();
						if (coord != null) {
							geoTagDto.setLongitude(BigDecimal.valueOf(coord[0]));
							geoTagDto.setLatitude(BigDecimal.valueOf(coord[1]));
						}

						BeanUtils.copyProperties(geoTagDto, geoEntity);
						geoEntity.setStatus(MainetConstants.FlagY);
						geoEntity.setOrgId(milestoneDetail.getOrgId());
						geoEntity.setMilestoneDetEntity(detEntity);
						geoEntity.getMilestoneDetEntity().setMiledId(milestoneDetail.getMiledId());
						geoEntity.setCreatedBy(milestoneDetail.getCreatedBy());
						geoEntity.setCreatedDate(milestoneDetail.getCreatedDate());
						geoEntity.setLgIpMac(milestoneDetail.getLgIpMac());
						geoTagRepository.save(geoEntity);
					}
				}
			}
		}
	}

	public void saveImages(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {

		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.doMasterFileUpload(attachments, requestDTO);
	}

	@Override
	@Transactional
	public void updateMilestoneDetailStatus(Long mileDetId, Long orgId) {
		detRepository.updateMilestoneDetailStatus(mileDetId, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MilestoneDetDto> getMilestoneDetListByMilestoneId(Long mileId, Long orgId) {
		List<MilestoneDetDto> detDtoList = new ArrayList<>();
		List<MilestoneDetail> detEntityList = detRepository.getAllMilestoneDetByMilestoneId(mileId, orgId);
		if (!detEntityList.isEmpty()) {
			detEntityList.forEach(entity -> {
				MilestoneDetDto detDto = new MilestoneDetDto();
				BeanUtils.copyProperties(entity, detDto);
				detDto.setProUpdateDate(
						(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getProUpdateDate())));
				detDtoList.add(detDto);
			});
		}
		return detDtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs, List<MilestoneDetDto> progressList,
			FileNetApplicationClient fileNetApplicationClient) {
		Set<File> fileList = null;
		Long x = 0L;
		Map<Long, Set<File>> fileMap = new HashMap<>();
		Map<Integer, List<AttachDocs>> map = new HashMap<>();
		for (AttachDocs attachDoc : attachDocs) {
			Integer serNo = attachDoc.getSerialNo();
			if (map.containsKey(serNo)) {
				map.get(serNo).add(attachDoc);
			} else {
				List<AttachDocs> list = new ArrayList<>();
				list.add(attachDoc);
				map.put(serNo, list);
			}
		}
		for (MilestoneDetDto detDto : progressList) {
			for (final Map.Entry<Integer, List<AttachDocs>> entry : map.entrySet()) {
				if (entry.getKey() == detDto.getPhyPercent().intValueExact()) {
					fileList = new HashSet<>();
					for (AttachDocs doc : entry.getValue()) {
						final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
								+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
						String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR
								+ doc.getAttFname();
						final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
								existingPath);

						String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
								existingPath);

						directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
								MainetConstants.operator.COMMA);
						FileOutputStream fos = null;
						File file = null;
						try {
							final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

							Utility.createDirectory(
									Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

							file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR
									+ fileName);

							fos = new FileOutputStream(file);

							fos.write(image);

							fos.close();

						} catch (final Exception e) {
							throw new FrameworkException(e);
						} finally {
							try {

								if (fos != null) {
									fos.close();
								}

							} catch (final IOException e) {
								throw new FrameworkException(e);
							}
						}
						fileList.add(file);
					}
					fileMap.put(x, fileList);
					x++;
				}
			}
		}
		return fileMap;
	}

	@Override
	@Transactional
	public void DeleteProgressDocuments(List<AttachDocs> attachDocs) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.updateMasterDocumentStatus(savedDoc.getIdfId(), MainetConstants.FlagD);
				geoTagRepository.updateGeoTagImageStatus(savedDoc.getAttId(), MainetConstants.FlagN);
			}
		}

	}

	@Override
	@Transactional
	public void saveMilestoneEntry(MilestoneEntryDto milestoneEntryDto) {
		MileStoneEntryEntity entryEntity = new MileStoneEntryEntity();
		BeanUtils.copyProperties(milestoneEntryDto, entryEntity);
		mileStoneEntryRepo.save(entryEntity);

	}
	
	@Override
	@Transactional
	public void addMilestoneEntryData(List<MilestoneEntryDto> mileStoneDtoList) {

		MileStoneEntryEntity mileStoneEntity = null;
		for (MilestoneEntryDto flowDto : mileStoneDtoList) {

			mileStoneEntity = new MileStoneEntryEntity();
			BeanUtils.copyProperties(flowDto, mileStoneEntity);
			mileStoneEntryRepo.save(mileStoneEntity);
		}

	}

	@Override
	@Transactional
	public List<MilestoneEntryDto> getMilestoneInfo(Long projId, Long workId, Long orgId) {
		List<MileStoneEntryEntity> entryEntities = mileStoneEntryRepo.getAllMilestoneEntries(projId, workId, orgId);
		List<MilestoneEntryDto> milestoneEntryDtos = new ArrayList<MilestoneEntryDto>();
		entryEntities.forEach(entity -> {
			MilestoneEntryDto dto = new MilestoneEntryDto();
			BeanUtils.copyProperties(entity, dto);
			milestoneEntryDtos.add(dto);
		});
		return milestoneEntryDtos;
	}

	@Override
	public List<MileStoneDTO> getMilestoneByMileNm(Long projId, Long workId, Long mileStoneName, Long orgid) {
		List<MileStone> entityList;

		entityList = mileStoneRepository.getMileStoneDtobyMileId(projId, workId, mileStoneName, orgid);

		List<MileStoneDTO> dtoList = new ArrayList<>();
		if (!entityList.isEmpty()) {
			entityList.forEach(entity -> {
				MileStoneDTO flowDto = new MileStoneDTO();
				BeanUtils.copyProperties(entity, flowDto);
				flowDto.setMsStartDate(
						(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getMsStartDate())));
				flowDto.setMsEndDate((new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getMsEndDate())));

				dtoList.add(flowDto);
			});
		}
		return dtoList;

	}

	@Override
	@Transactional
	public BigDecimal getMileStonePer(Long projId, Long workId, Long orgId) {
		BigDecimal totalPer = mileStoneRepository.getMilestonePer(projId, workId, orgId);
		return totalPer;
	}
	
	@Override
	@Transactional
	public boolean checkMilestoneEntry(Long projId, Long workId,Long orgId,String milestoneName) {
		List<MileStoneEntryEntity> entryEntities = mileStoneEntryRepo.checkMilestone(projId, workId, orgId, milestoneName);
		
		if(!entryEntities.isEmpty())
			return true;
		return false;
	}


}
