package com.abm.mainet.disastermanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.disastermanagement.domain.ComplainRegister;
import com.abm.mainet.disastermanagement.domain.DisasterOccuranceBookEntity;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.dto.DisasterOccuranceBookDTO;
import com.abm.mainet.disastermanagement.repository.ComplainRegisterRepository;
import com.abm.mainet.disastermanagement.repository.DisasterOccuranceBookRepository;

@Service
public class DisasterOccuranceBookServiceImpl implements IDisasterOccuranceBookService {

	@Autowired
	private DisasterOccuranceBookRepository occuranceBookRepo;

	@Autowired
	private ComplainRegisterRepository callRepository;

	@Override
	// @Transactional
	public DisasterOccuranceBookDTO save(DisasterOccuranceBookDTO occuranceBookDTO) {

		DisasterOccuranceBookEntity OBookEntity = new DisasterOccuranceBookEntity();
		BeanUtils.copyProperties(occuranceBookDTO, OBookEntity);
		OBookEntity.setCmplntNo(occuranceBookDTO.getEnterComplaintNumber());

		OBookEntity = occuranceBookRepo.save(OBookEntity);
		BeanUtils.copyProperties(OBookEntity, occuranceBookDTO);

		return occuranceBookDTO;
	}

	@Override

	@Transactional(readOnly = true)
	public List<ComplainRegisterDTO> searchFireCallRegisterwithDate(Date toDate, Date fromDate, Long orgId, Long callType, Long callSubType) {
		List<ComplainRegisterDTO> listdCallRegisterDTOs = new ArrayList<ComplainRegisterDTO>();
		List<ComplainRegister> list = callRepository.searchFireCallRegisterwithDate(fromDate, toDate, orgId, callType, callSubType, MainetConstants.FlagC);
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);

		list.forEach(entity -> {
			ComplainRegisterDTO dto = new ComplainRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			String complaintType1Desc = CommonMasterUtility
					.getHierarchicalLookUp(entity.getComplaintType1(), organisation).getLookUpDesc();
			dto.setComplaintType1Desc(complaintType1Desc);
			String complaintType2Desc = CommonMasterUtility
					.getHierarchicalLookUp(entity.getComplaintType2(), organisation).getLookUpDesc();
			dto.setComplaintType2Desc(complaintType2Desc);
			listdCallRegisterDTOs.add(dto);

		});
		return listdCallRegisterDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComplainRegisterDTO> disasterSummaryDate(Long orgId) {
		List<ComplainRegisterDTO> listdCallRegisterDTOs = new ArrayList<ComplainRegisterDTO>();
		List<ComplainRegister> list = callRepository.findByOrgidAndComplainStatusOrderByComplainId(orgId,MainetConstants.WorksManagement.OPEN);
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);

		list.forEach(entity -> {
			ComplainRegisterDTO dto = new ComplainRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			String complaintType1Desc = CommonMasterUtility
					.getHierarchicalLookUp(entity.getComplaintType1(), organisation).getLookUpDesc();
			dto.setComplaintType1Desc(complaintType1Desc);
			String complaintType2Desc = CommonMasterUtility
					.getHierarchicalLookUp(entity.getComplaintType2(), organisation).getLookUpDesc();
			dto.setComplaintType2Desc(complaintType2Desc);
			
			listdCallRegisterDTOs.add(dto);

		});
		return listdCallRegisterDTOs;
	}

	@Override
	public String getRecordByTime(Long orgid, String cmplntId) {
		String time = callRepository.validateDateTime(orgid, Long.valueOf(cmplntId));
		return time;
	}
	
}
