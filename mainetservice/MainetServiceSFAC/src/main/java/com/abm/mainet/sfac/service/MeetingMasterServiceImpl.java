/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dao.MeetingMasterDao;
import com.abm.mainet.sfac.domain.CommitteeMemberMasterEntity;
import com.abm.mainet.sfac.domain.MOMDetEntity;
import com.abm.mainet.sfac.domain.MeetingAgendaDetEntity;
import com.abm.mainet.sfac.domain.MeetingDetailEntity;
import com.abm.mainet.sfac.domain.MeetingMasterEntity;
import com.abm.mainet.sfac.dto.MeetingAgendaDto;
import com.abm.mainet.sfac.dto.MeetingDetailDto;
import com.abm.mainet.sfac.dto.MeetingMOMDto;
import com.abm.mainet.sfac.dto.MeetingMasterDto;
import com.abm.mainet.sfac.repository.CommitteeMemberRepository;
import com.abm.mainet.sfac.repository.MOMDetailRepository;
import com.abm.mainet.sfac.repository.MeetingAgendaRepository;
import com.abm.mainet.sfac.repository.MeetingDetailRepository;
import com.abm.mainet.sfac.repository.MeetingMasterRepository;
import com.abm.mainet.sfac.ui.model.MeetingMasterFormModel;

/**
 * @author pooja.maske
 *
 */
@Service
public class MeetingMasterServiceImpl implements MeetingMasterService {

	private static final Logger logger = Logger.getLogger(MeetingMasterServiceImpl.class);

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private MeetingMasterRepository masterRepository;

	@Autowired
	private MeetingMasterDao MeetingMasterDao;

	@Autowired
	private MOMDetailRepository momRepository;

	@Autowired
	private MeetingDetailRepository meetingDetRepository;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private CommitteeMemberRepository comMemRepo;
	
	@Autowired
	private MeetingAgendaRepository meetingAgendaRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.MeetingMasterService#searchMeetingMasterData(java
	 * .lang.Long, java.lang.String, java.util.Date, java.util.Date, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<MeetingMasterDto> searchMeetingMasterData(Long meetingTypeId, String meetingNumber, Long orgId,
			String orderBy,Long meetingId) {
		List<MeetingMasterDto> meetingMasterDtos = new ArrayList<MeetingMasterDto>();
		List<MeetingMasterEntity> meetingMasterEntities = MeetingMasterDao.searchMeetingMasterData(meetingTypeId,
				meetingNumber, orgId, orderBy,meetingId);
		Organisation organisation = new Organisation();
		
		meetingMasterEntities.forEach(mastEntity -> {
			organisation.setOrgid(mastEntity.getOrgId());
			MeetingMasterDto dto = new MeetingMasterDto();
			BeanUtils.copyProperties(mastEntity, dto);
			// set meeting date and meeting time
			Date meetingDateTime = mastEntity.getMeetingDate();
			String meetingDateDesc = Utility.dateToString(meetingDateTime);
			String meetingTime = new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(meetingDateTime);
			dto.setMeetingTypeName(CommonMasterUtility
					.getNonHierarchicalLookUpObject(mastEntity.getMeetingTypeId(), organisation).getLookUpDesc());
			dto.setMeetingDateDesc(meetingDateDesc);
			dto.setMeetingTime(meetingTime);
			meetingMasterDtos.add(dto);
		});
		return meetingMasterDtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.MeetingMasterService#saveMeetingDetails(com.abm.
	 * mainet.sfac.dto.MeetingMasterDto, java.util.List,
	 * com.abm.mainet.common.integration.dms.dto.FileUploadDTO, java.lang.Object,
	 * java.util.List)
	 */
	@Override
	@Transactional
	public MeetingMasterDto saveMeetingDetails(MeetingMasterDto masterDto, List<DocumentDetailsVO> attachmentList,
			FileUploadDTO fileUploadDTO, Long deleteFileId, List<Long> removeYearIdList) {
		try {
			logger.info("saveMeetingDetails started");

			MeetingMasterEntity masterEntity = mapDtoToEntity(masterDto);
			masterEntity = masterRepository.save(masterEntity);
			MeetingMasterFormModel.setPrintMeetingId(masterEntity.getMeetingId());
			// file upload code set here
			if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId == null) {
				fileUploadDTO.setIdfId(MainetConstants.Sfac.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
						+ masterEntity.getMeetingId());
				fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
			} else if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId != null) {
				fileUploadDTO.setIdfId(MainetConstants.Sfac.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
						+ masterEntity.getMeetingId());
				fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
				List<Long> deletedDocFiles = new ArrayList<>();
				deletedDocFiles.add(deleteFileId);
				ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class)
						.updateRecord(deletedDocFiles, masterDto.getUpdatedBy(), MainetConstants.FlagD);
			}
		} catch (Exception e) {
			logger.error("error occured while saving meeting details" + e);
		}
		logger.info("saveMeetingDetails ended");
		return masterDto;
	}

	/**
	 * @param masterDto
	 * @return
	 */
	private MeetingMasterEntity mapDtoToEntity(MeetingMasterDto masterDto) {
		MeetingMasterEntity masterEntity = new MeetingMasterEntity();
		List<MeetingDetailEntity> meetingDetailEntityList = new ArrayList<>();
		List<MOMDetEntity> momEntityList = new ArrayList<>();
		List<MeetingAgendaDetEntity> agenEntityList = new ArrayList<>();

		if (masterDto.getMeetingId() == null) {
			// 1st get year and month name using meetingDate
			int year = Utility.getYearByDate(masterDto.getMeetingDate());
			String monthName = new SimpleDateFormat("MMM").format(new Date()).toUpperCase();
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class).generateSequenceNo(MainetConstants.Sfac.SFAC,
							MainetConstants.Sfac.TB_SFAC_MEETING_MASTER, MainetConstants.Sfac.MEETING_NO,
							masterDto.getOrgId(), MainetConstants.FlagC, masterDto.getOrgId());
			String meetingNo = year + MainetConstants.WINDOWS_SLASH + monthName + MainetConstants.WINDOWS_SLASH
					+ sequence;
			masterEntity.setMeetingNo(meetingNo);
			masterDto.setMeetingNo(meetingNo);
		}
		BeanUtils.copyProperties(masterDto, masterEntity);
		masterDto.getMeetingMOMDto().forEach(dto -> {
			MOMDetEntity momEntity = new MOMDetEntity();
			BeanUtils.copyProperties(dto, momEntity);
			momEntity.setMeetingMasterEntity(masterEntity);
			momEntityList.add(momEntity);
		});
		masterDto.getMeetingDetailDto().forEach(dto -> {
			MeetingDetailEntity meetingEntity = new MeetingDetailEntity();
			BeanUtils.copyProperties(dto, meetingEntity);
			meetingEntity.setMeetingMasterEntity(masterEntity);
			meetingDetailEntityList.add(meetingEntity);
		});
		
		masterDto.getMeetinAgendaDto().forEach(dto -> {
			MeetingAgendaDetEntity meetingAgenEntity = new MeetingAgendaDetEntity();
			BeanUtils.copyProperties(dto, meetingAgenEntity);
			meetingAgenEntity.setMeetingMasterEntity(masterEntity);
			agenEntityList.add(meetingAgenEntity);
		});

		masterEntity.setMeetingDetail(meetingDetailEntityList);
		masterEntity.setMomDetEntity(momEntityList);
		masterEntity.setAgendaDetEntity(agenEntityList);
		return masterEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.MeetingMasterService#findById(java.lang.Long)
	 */
	@Override
	public MeetingMasterDto findById(Long meetingId) {
		logger.info("findById started");
		MeetingMasterDto dto = new MeetingMasterDto();
		List<MeetingDetailDto> detList = new ArrayList<MeetingDetailDto>();
		List<MeetingMOMDto> momList = new ArrayList<MeetingMOMDto>();
		List<MeetingAgendaDto> agendaList = new ArrayList<MeetingAgendaDto>();

		try {
			MeetingMasterEntity entity = masterRepository.findOne(meetingId);
			BeanUtils.copyProperties(entity, dto);
			Organisation organisation = new Organisation();
			organisation.setOrgid(dto.getOrgId());
			String pattern = "dd/MM/yyyy HH:mm:ss";
			DateFormat df = new SimpleDateFormat(pattern);
			String todayAsString = df.format(entity.getMeetingDate());
			dto.setMeetingDateDesc(todayAsString);
			String meetingTime = new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(entity.getMeetingDate());
			dto.setMeetingTime(meetingTime);
			dto.setMeetingTypeName(CommonMasterUtility
					.getNonHierarchicalLookUpObject(entity.getMeetingTypeId(), organisation).getLookUpDesc());

			entity.getMeetingDetail().forEach(detDto -> {
				MeetingDetailDto meetingDetDto = new MeetingDetailDto();
				BeanUtils.copyProperties(detDto, meetingDetDto);
				if (meetingDetDto != null && (meetingDetDto.getDsgId() != null && meetingDetDto.getDsgId() !=0)) {
				 DesignationBean designationBean = designationService.findById(meetingDetDto.getDsgId());
				if (designationBean != null)
					meetingDetDto.setDesignation(designationBean.getDsgname());
				}
				if (StringUtils.isNotEmpty(detDto.getStatus()) && detDto.getStatus().equals(MainetConstants.FlagA))
					detList.add(meetingDetDto);
			});

			List<MOMDetEntity> momdetEntity = momRepository.getMomDet(entity);
			momdetEntity.forEach(momEnt -> {
				MeetingMOMDto momDto = new MeetingMOMDto();
				BeanUtils.copyProperties(momEnt, momDto);
				momDto.setActionable(CommonMasterUtility
						.getNonHierarchicalLookUpObject(momEnt.getActionowner(), organisation).getLookUpDesc());
				if (StringUtils.isNotEmpty(momEnt.getStatus()) && momEnt.getStatus().equals(MainetConstants.FlagA))
					momList.add(momDto);

			});
			
			List<MeetingAgendaDetEntity> agendaDetEntity = meetingAgendaRepo.getAgendaDet(entity);
			agendaDetEntity.forEach(agenEnt -> {
				MeetingAgendaDto agenDto = new MeetingAgendaDto();
				BeanUtils.copyProperties(agenEnt, agenDto);
				if (StringUtils.isNotEmpty(agenEnt.getStatus()) && agenEnt.getStatus().equals(MainetConstants.FlagA))
					agendaList.add(agenDto);

			});
			dto.setMeetingDetailDto(detList);
			dto.setMeetingMOMDto(momList);
			dto.setMeetinAgendaDto(agendaList);
		} catch (Exception e) {
			logger.error("Error occured while fetching meeting details" + e);
			throw new FrameworkException("Error occured while fetching meeting details" + e);
		}
		logger.info("findById started");

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.MeetingMasterService#inactiveRemovedMemDetails(
	 * com.abm.mainet.sfac.dto.MeetingMasterDto, java.util.List)
	 */
	@Override
	@Transactional
	public void inactiveRemovedMemDetails(MeetingMasterDto masterDto, List<Long> removedMemtDetIdsList) {
		if (removedMemtDetIdsList != null && !removedMemtDetIdsList.isEmpty()) {
			meetingDetRepository.inActiveMemDetByIds(removedMemtDetIdsList, masterDto.getUpdatedBy());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.MeetingMasterService#inactiveRemovedMomDetails(
	 * com.abm.mainet.sfac.dto.MeetingMasterDto, java.util.List)
	 */
	@Override
	@Transactional
	public void inactiveRemovedMomDetails(MeetingMasterDto masterDto, List<Long> removedMomDetIdsList) {
		if (removedMomDetIdsList != null && !removedMomDetIdsList.isEmpty()) {
			momRepository.inActiveMomDetByIds(removedMomDetIdsList, masterDto.getUpdatedBy());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.MeetingMasterService#getCommitteeMemDetById(java.
	 * lang.Long)
	 */
	@Override
	public List<MeetingDetailDto> getCommitteeMemDetById(Long meetingTypeId) {
		List<MeetingDetailDto> meetingDetDtoList = new ArrayList<>();
		try {
			List<CommitteeMemberMasterEntity> comEntityList = comMemRepo.getCommitteeMemDetByComTypeId(meetingTypeId);
			for (CommitteeMemberMasterEntity entity : comEntityList) {
				MeetingDetailDto dto = new MeetingDetailDto();
				dto.setMemberName(entity.getMemberName());
				dto.setDesignation(entity.getDesignation());
				dto.setComMemberId(entity.getComMemberId());
				dto.setOrganization(entity.getOrganization());
				String status = CommonMasterUtility.findLookUpCode("CMS", entity.getOrgId(), entity.getStatus());
				if (status.equals("ACE"))
				meetingDetDtoList.add(dto);
			}
		} catch (Exception e) {
			logger.error("Error occured while fetching commitee details in getCommitteeMemDetById" + e);
		}
		return meetingDetDtoList;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.MeetingMasterService#inactiveRemovedAgendaDetails(com.abm.mainet.sfac.dto.MeetingMasterDto, java.util.List)
	 */
	@Override
	public void inactiveRemovedAgendaDetails(MeetingMasterDto masterDto, List<Long> removedAgendaDetIdsList) {
		if (removedAgendaDetIdsList != null && !removedAgendaDetIdsList.isEmpty()) {
			meetingAgendaRepo.inActiveAgendaDetByIds(removedAgendaDetIdsList, masterDto.getUpdatedBy());
		}
	}

}
