package com.abm.mainet.tradeLicense.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.CommonConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMtlInspectionReg;
import com.abm.mainet.tradeLicense.domain.TbMtlNoticeMas;
import com.abm.mainet.tradeLicense.dto.InspectionDetailDto;
import com.abm.mainet.tradeLicense.dto.NoticeDetailDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.InspectionDetailRepository;
import com.abm.mainet.tradeLicense.repository.NoticeDetailRepository;
import com.abm.mainet.tradeLicense.ui.model.InspectionDetailFormModel;

@Service
public class InspectionDetailServiceImpl implements InspectionDetailService {

	private static final Logger LOGGER = Logger.getLogger(InspectionDetailServiceImpl.class);

	@Autowired
	private ITradeLicenseApplicationService iTradeLicApplService;

	@Autowired
	private InspectionDetailRepository inspectionRepo;

	@Autowired
	private NoticeDetailRepository noticeDetailRepo;

	@Autowired
	private IOrganisationService organisationService;

	@Autowired
	private DepartmentService departmentService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Override
	@Transactional
	public InspectionDetailDto saveInspection(InspectionDetailDto inspectionDto, InspectionDetailFormModel model) {
		TbMtlInspectionReg entity = mapDtoToEntity(inspectionDto);
		try {
			BeanUtils.copyProperties(inspectionDto, entity);
			inspectionRepo.save(entity);
		} catch (Exception e) {
			LOGGER.error("Exception Occured While saving the InspectionEntry Data", e);
			throw new FrameworkException("Exception acured while  saving the InspectionEntry Data", e);
		}
		return inspectionDto;

	}

	private TbMtlInspectionReg mapDtoToEntity(InspectionDetailDto inspectionDto) {
		TbMtlInspectionReg entity = new TbMtlInspectionReg();
		TbMlTradeMast tradeMastEntiy = null;
		BeanUtils.copyProperties(inspectionDto, entity);
		TradeMasterDetailDTO dto = iTradeLicApplService.getLicenseDetailsByLicenseNo(inspectionDto.getLicNo(),
				inspectionDto.getOrgId());
		if (dto != null) {
			tradeMastEntiy = new TbMlTradeMast();
			tradeMastEntiy.setTrdId(dto.getTrdId());
			BeanUtils.copyProperties(dto, tradeMastEntiy);
		}
		entity.setMasterTradeId(tradeMastEntiy);
		return entity;
	}

	@Override
	@Transactional
	public void saveNoticeDetails(List<NoticeDetailDto> noticeDetDtoList, InspectionDetailFormModel model) {
		TradeMasterDetailDTO dto = iTradeLicApplService.getLicenseDetailsByLicenseNo(
				model.getInspectionDetailDto().getLicNo(), noticeDetDtoList.get(0).getOrgId());
		for (NoticeDetailDto tbMtlNoticeMas : noticeDetDtoList) {
			NoticeDetailDto dtos = new NoticeDetailDto();
			TbMtlNoticeMas entity = new TbMtlNoticeMas();
			if (dto != null) {
				dtos.setTrdId(dto.getTrdId());
			}
			dtos.setCreatedBy(tbMtlNoticeMas.getCreatedBy());
			dtos.setCreatedDate(tbMtlNoticeMas.getCreatedDate());
			dtos.setOrgId(tbMtlNoticeMas.getOrgId());
			dtos.setLgIpMac(tbMtlNoticeMas.getLgIpMac());
			dtos.setNoticeDate(tbMtlNoticeMas.getNoticeDate());
			dtos.setReason(tbMtlNoticeMas.getReason());
			dtos.setRemark(tbMtlNoticeMas.getRemark());
			dtos.setNoticeNo(tbMtlNoticeMas.getNoticeNo());
			dtos.setNoticeTypeId(tbMtlNoticeMas.getNoticeTypeId());
			BeanUtils.copyProperties(dtos, entity);
			noticeDetailRepo.save(entity);
		}
	}

	@Override
	public Long generateNoticeNo(Long orgId) {
		Organisation org = organisationService.getOrganisationById(orgId);
		Long number = null;
		SequenceConfigMasterDTO configMasterDTO = null;

		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		configMasterDTO = seqGenFunctionUtility.loadSequenceData(org.getOrgid(), deptId,
				MainetConstants.TradeLicense.TB_MTL_NOTICE_MAS, MainetConstants.TradeLicense.NT_NO);

		if (configMasterDTO.getSeqConfigId() == null) {
			number = seqGenFunctionUtility.generateSequenceNo(CommonConstants.COM,
					MainetConstants.TradeLicense.TB_MTL_NOTICE_MAS, MainetConstants.TradeLicense.NT_NO, org.getOrgid(),
					MainetConstants.FlagF, null);

		} else {
			CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
			String no = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
			number = Long.valueOf(no);
		}
		return number;
	}

	@Override
	public List<NoticeDetailDto> getNoticeDataById(String licNo,Long noticeNo,Long orgId) {
		List<NoticeDetailDto> dtoList = new ArrayList<>();
		TradeMasterDetailDTO trdMastDet = iTradeLicApplService.getLicenseDetailsByLicenseNo(licNo, orgId);
		List<TbMtlNoticeMas> entity = noticeDetailRepo.getNoticeDataByNoticeNo(noticeNo, orgId);
		if (CollectionUtils.isNotEmpty(entity)) {
			for (TbMtlNoticeMas tbMtlNMas : entity) {
				NoticeDetailDto dto = new NoticeDetailDto();
				dto.setReason(tbMtlNMas.getReason());
				dto.setTrdId(tbMtlNMas.getTrdId());
				dto.setCreatedDate(tbMtlNMas.getCreatedDate());
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public NoticeDetailDto getDetailsToPrintLetter(String licNo, Long inspNo, Long noticeNo ,Long orgId) {
		final ApplicationSession appSession = ApplicationSession.getInstance();
		NoticeDetailDto dto = new NoticeDetailDto();
		TradeMasterDetailDTO trdMastDet = iTradeLicApplService.getLicenseDetailsByLicenseNo(licNo, orgId);
		List<TbMtlNoticeMas> entity = noticeDetailRepo.getNoticeDataByNoticeNo(noticeNo, orgId);
		dto.setNoticeNo(entity.get(0).getNoticeNo());
		StringBuilder ownName = new StringBuilder();
		StringBuilder ownAdd = new StringBuilder();
		String fName = null;
		String add = null;
		trdMastDet.getTradeLicenseOwnerdetailDTO().forEach(d -> {
			if (d.getTroName() != null) {
				ownName.append(d.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (d.getTroAddress() != null) {
				ownAdd.append(d.getTroAddress() + " " + MainetConstants.operator.COMMA);
			}
		});
		if(ownName != null)
		fName = ownName.deleteCharAt(ownName.length() - 1).toString();
		if(ownAdd != null)
		add = ownAdd.deleteCharAt(ownAdd.length() - 1).toString();
		if (fName != null) {
			dto.setApplicantName(fName);
		}
		if (StringUtils.isNotBlank(add)) {
			dto.setAppAdd(add);
		}

		final Date date = new Date();
		final DateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
		dto.setnTDate(dateFormat.format(date).toString());
		dto.setLicDate(dateFormat.format(trdMastDet.getCreatedDate()));
		String ref = appSession.getMessage("inscpection.license.no") + " " + licNo.toString() + " "
				+ appSession.getMessage("license.dated.on") + " " + dto.getLicDate();
		dto.setRef(ref);
		dto.setDeptName(departmentService.getDepartmentDescByDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE));
		TbMtlInspectionReg inspEntity = inspectionRepo.getInspectionDetByInspNo(inspNo, orgId);
		final DateFormat df = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		String inspDate = df.format(inspEntity.getInspDate());
		String bodyContent = appSession.getMessage("trd.notice.b1") + " " + trdMastDet.getTrdBusnm() + " "
				+ appSession.getMessage("trd.notice.b2") + " " + inspDate + "  "
				+ appSession.getMessage("trd.notice.b3") + " " + inspEntity.getInspectorName() + "  "
				+ appSession.getMessage("trd.notice.b4");
		dto.setContent(bodyContent);
		return dto;

	}

	@Override
	public Long generateInspectionNo(Long orgId) {
		Organisation org = organisationService.getOrganisationById(orgId);
		Long number = null;
		SequenceConfigMasterDTO configMasterDTO = null;

		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		configMasterDTO = seqGenFunctionUtility.loadSequenceData(org.getOrgid(), deptId,
				MainetConstants.TradeLicense.TB_MTL_INSPECTION_REG, MainetConstants.TradeLicense.IN_NO);

		if (configMasterDTO.getSeqConfigId() == null) {
			number = seqGenFunctionUtility.generateSequenceNo(CommonConstants.COM,
					MainetConstants.TradeLicense.TB_MTL_INSPECTION_REG, MainetConstants.TradeLicense.IN_NO, org.getOrgid(),
					MainetConstants.FlagF, null);

		} else {
			CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
			String no = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
			number = Long.valueOf(no);
		}
		return number;
	

	}

	@Override
	public void saveRenewalReminderNotice(NoticeDetailDto noticeDetailDto) {
		try {
			TbMtlNoticeMas entity = new TbMtlNoticeMas();
			BeanUtils.copyProperties(noticeDetailDto, entity);
			noticeDetailRepo.save(entity);
			
		}catch (Exception exception) {
		    throw new FrameworkException("Exception occured while saving the reminder notice data", exception);
		}
	}

	@Override
	public void updateRenewalReminderNotice(NoticeDetailDto noticeDetailDto) {
    try {
		TbMtlNoticeMas entity = new TbMtlNoticeMas();
		BeanUtils.copyProperties(noticeDetailDto, entity);
		noticeDetailRepo.save(entity);		
		}catch (Exception exception) {
		    throw new FrameworkException("Exception occured while updating the reminder notice data", exception);
		}
	}


	@Override
	public NoticeDetailDto getNoticeDetailsByTrdIdAndOrgId(TradeMasterDetailDTO trdMastDto, Long orgId) {
		NoticeDetailDto dto = new NoticeDetailDto();
		List<TbMtlNoticeMas> entity = noticeDetailRepo.getNoticeDataById(trdMastDto.getTrdId(), orgId);
		dto.setNoticeNo(entity.get(0).getNoticeNo());				
		StringBuilder ownName = new StringBuilder();
		StringBuilder ownAdd = new StringBuilder();
		String fName = null;
		String add = null;
		trdMastDto.getTradeLicenseOwnerdetailDTO().forEach(d -> {			
			if (d.getTroName() != null) {
				ownName.append(d.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (d.getTroAddress() != null) {
				ownAdd.append(d.getTroAddress() + " " + MainetConstants.operator.COMMA);
			}
		});
		fName = ownName.deleteCharAt(ownName.length() - 1).toString();
		add = ownAdd.deleteCharAt(ownAdd.length() - 1).toString();
		if (fName != null) {
			dto.setApplicantName(fName);
		}
		if (StringUtils.isNotBlank(add)) {
			dto.setAppAdd(add);
		}
		dto.setFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(trdMastDto.getTrdLicfromDate()));
		dto.setToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(trdMastDto.getTrdLictoDate()));
		dto.setLicNo(trdMastDto.getTrdLicno());
		dto.setBusinessName(trdMastDto.getTrdBusnm());
		dto.setnTDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date()));
		return dto;
	}
	

}
