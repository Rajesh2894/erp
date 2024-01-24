package com.abm.mainet.firemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dao.IFireCallRegisterDAO;
import com.abm.mainet.firemanagement.domain.FireCallRegister;
import com.abm.mainet.firemanagement.domain.OccuranceBookEntity;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.dto.OccuranceBookDTO;
import com.abm.mainet.firemanagement.repository.FireCallRegisterRepository;
import com.abm.mainet.firemanagement.repository.OccuranceBookRepository;

/**
 * 
 * @author Niraj kumar
 *
 */
@Service
public class OccuranceBookServiceImpl implements IOccuranceBookService {

	@Autowired
	private OccuranceBookRepository occuranceBookRepo;

	@Autowired
	private FireCallRegisterRepository fireCallRepository;

	@Autowired
	private IFireCallRegisterDAO fireCallRegisterDAO;

	@Override
	// @Transactional
	public OccuranceBookDTO save(OccuranceBookDTO occuranceBookDTO) {

		OccuranceBookEntity OBookEntity = new OccuranceBookEntity();
		BeanUtils.copyProperties(occuranceBookDTO, OBookEntity);
		OBookEntity.setCmplntNo(occuranceBookDTO.getEnterComplaintNumber());

		OBookEntity = occuranceBookRepo.save(OBookEntity);
		BeanUtils.copyProperties(OBookEntity, occuranceBookDTO);

		return occuranceBookDTO;
	}

	@Override
	public List<FireCallRegisterDTO> getAllOccLogBook(Long orgId, String status) {

		List<FireCallRegister> listFire = fireCallRegisterDAO.searchFireData(orgId, status);
		List<FireCallRegisterDTO> dtoList = new ArrayList<FireCallRegisterDTO>();

		listFire.forEach(entity -> {
			FireCallRegisterDTO dto = new FireCallRegisterDTO();
			BeanUtils.copyProperties(entity, dto);

			String fireStations = entity.getCpdFireStation();
			String joinedString = Constants.FIRE_CALL_BLANK_STATION;
			if (fireStations != null) {
				String[] splitIds = fireStations.split(",");
				List<String> fireStationList = new ArrayList<>();
				for (int i = Constants.FIRE_ZERO; i < splitIds.length; i++) {
					String lookuDesc = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
							Long.parseLong(splitIds[i]), orgId, Constants.FIRE_CALL_PREFIX).getLookUpDesc();
					fireStationList.add(lookuDesc);
				}

				if (fireStationList.size() > Constants.FIRE_ZERO) {
					joinedString = String.join(",", fireStationList);
				}
				dto.setFsDesc(joinedString);
			} else {
				dto.setFsDesc(joinedString);
			}
			dtoList.add(dto);
		});

		return dtoList;

	}

	@Override
	public FireCallRegisterDTO getBookOccId(Long cmplntId) {

		OccuranceBookEntity occBookEntity = occuranceBookRepo.findOne(cmplntId);
		FireCallRegisterDTO occBookDTO = new FireCallRegisterDTO();
		BeanUtils.copyProperties(occBookEntity, occBookDTO);
		return occBookDTO;

	}

	@Override
	@Transactional(readOnly = true)
	public List<FireCallRegisterDTO> searchFireCallRegisterwithDate(Date toDate, Date fromDate, String fireStation,
			Long orgId) {
		List<FireCallRegisterDTO> listdCallRegisterDTOs = new ArrayList<FireCallRegisterDTO>();
		List<FireCallRegister> list = fireCallRegisterDAO.searchFireCallRegisterwithDate(fromDate, toDate, fireStation,
				orgId);

		list.forEach(entity -> {
			FireCallRegisterDTO dto = new FireCallRegisterDTO();
			BeanUtils.copyProperties(entity, dto);

			String cpdFireStation = entity.getCpdFireStation();
			String joinedString = Constants.FIRE_CALL_BLANK_STATION;
			if (cpdFireStation != null) {
				String[] splitIds = cpdFireStation.split(",");
				List<String> fireStationList = new ArrayList<>();
				for (int i = Constants.FIRE_ZERO; i < splitIds.length; i++) {
					String lookuDesc = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
							Long.parseLong(splitIds[i]), orgId, Constants.FIRE_CALL_PREFIX).getLookUpDesc();
					fireStationList.add(lookuDesc);
				}

				if (fireStationList.size() > Constants.FIRE_ZERO) {
					joinedString = String.join(",", fireStationList);
				}
				dto.setFsDesc(joinedString);
			} else {
				dto.setFsDesc(joinedString);
			}
			listdCallRegisterDTOs.add(dto);

		});
		return listdCallRegisterDTOs;
	}

	@Override
	public String getRecordByDate(Long orgid, Date inputDate, String cmplntId) {

		Long count = fireCallRepository.ValidateDate(orgid, inputDate, Long.valueOf(cmplntId));
		String msg = null;
		if (count != null && count == Constants.FIRE_ZERO) {
			msg = "Please Enter the Valid Date";
		} else {
			msg = "true";
		}
		return msg;
	}

	@Override
	public String getRecordByTime(Long orgid, String cmplntId) {
		String time = fireCallRepository.validateDateTime(orgid, Long.valueOf(cmplntId));
		return time;
	}
}

	/*
	 * @Override public String getRecordByTime(Long orgid, String cmplntId) { String
	 * time =fireCallRepository.validateDateTime(orgid,cmplntId); return time; }
	 */
	
	
	/*
	 * @Override public String getRecordByTime(Long orgid, String inputTime, String
	 * cmplntId) {
	 * 
	 * Long count = fireCallRepository.validateTime(orgid, inputTime,
	 * Long.valueOf(cmplntId)); String msg = null; if (count != null && count ==
	 * Constants.FIRE_ZERO) { msg = "Please Enter the Valid Time"; } else { msg =
	 * "true"; } return msg; }
	 */

	


