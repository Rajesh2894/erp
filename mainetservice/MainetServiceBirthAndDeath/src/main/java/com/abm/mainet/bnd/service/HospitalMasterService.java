package com.abm.mainet.bnd.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.bnd.domain.HospitalMaster;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.SortByEnglish;
import com.abm.mainet.bnd.dto.SortByMarathi;
import com.abm.mainet.bnd.repository.HospitalMasterRepository;
import com.abm.mainet.common.utility.UserSession;

@Service
public class HospitalMasterService implements IHospitalMasterService {

	@Autowired
	private HospitalMasterRepository hospitalMasterRepository;

	@Override
	public List<HospitalMasterDTO> getAllHospitls(Long orgId) {

		List<HospitalMaster> listOfHospital = hospitalMasterRepository.findByOrgid(orgId);
		List<HospitalMasterDTO> dtoList = new ArrayList<HospitalMasterDTO>();
  
		if(!listOfHospital.isEmpty()) {
		if(String.valueOf(UserSession.getCurrent().getLanguageId()).equals("1")) {
			Collections.sort(listOfHospital, new SortByEnglish());
		}else {
			Collections.sort(listOfHospital, new SortByMarathi());
		}
		
		listOfHospital.forEach(entity -> {
			if(entity.getHiStatus().equals("A")) {
			 if((entity.getHiNameMar()!=null)) {
			HospitalMasterDTO dto = new HospitalMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
			  }
			}
		 });
		}
		return dtoList;
	}

	@Override
	public HospitalMasterDTO getHospitalById(Long hiId) {
		HospitalMaster hospital = hospitalMasterRepository.findOne(hiId);
		HospitalMasterDTO hospitalMasterDTO = new HospitalMasterDTO();
		BeanUtils.copyProperties(hospital, hospitalMasterDTO);

		return hospitalMasterDTO;
	}

	@Override
	public void saveHospital(HospitalMasterDTO hospital) {
		HospitalMaster hospitalMaster = new HospitalMaster();
		BeanUtils.copyProperties(hospital, hospitalMaster);
		hospitalMasterRepository.save(hospitalMaster);

	}

	@Override
	public void deleteHospitalInFoById(Long hiId) {

	}

	@Override
	public List<HospitalMasterDTO> findByHospitalNameAndHospitalType(String hiName, Long cpdTypeId, Long orgId) {

		List<HospitalMasterDTO> listDTO = new ArrayList<HospitalMasterDTO>();
		List<HospitalMaster> list = null;
		if (StringUtils.isNotEmpty(hiName) && null != cpdTypeId && cpdTypeId > 0L) {
			list = hospitalMasterRepository.findByOrgidAndHiNameLikeAndCpdTypeId(orgId, hiName, cpdTypeId);
		} else if (StringUtils.isEmpty(hiName)) {
			list = hospitalMasterRepository.findByOrgidAndCpdTypeId(orgId, cpdTypeId);
		} else if (null == cpdTypeId || cpdTypeId == 0L) {
			list = hospitalMasterRepository.findByOrgidAndHiNameLike(hiName,orgId);
		} else {
			list = hospitalMasterRepository.findByOrgid(orgId);
		}
		list.forEach(obj -> {
			HospitalMasterDTO dto = new HospitalMasterDTO();
			BeanUtils.copyProperties(obj, dto);
			listDTO.add(dto);
		});

		return listDTO;
	}

	@Override
	public List<HospitalMaster> getAllHospitalCode(String hiCode, Long orgId) {
	return hospitalMasterRepository.getHospitalCode(hiCode, orgId);
		
	}
	
	@Override
	public List<HospitalMasterDTO> getAllHospitalByStatus(String Status, Long orgId)
	{

		List<HospitalMaster> listOfHospital = hospitalMasterRepository.getHospitalByStatus(Status, orgId);
		List<HospitalMasterDTO> dtoList = new ArrayList<HospitalMasterDTO>();

		listOfHospital.forEach(entity -> {
			HospitalMasterDTO dto = new HospitalMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
		});

		return dtoList;
		
		
	}

	@Override
	public List<HospitalMasterDTO> getAllHospitalList(Long orgId) {
		List<HospitalMaster> listOfHospital = hospitalMasterRepository.findByOrgid(orgId);
		List<HospitalMasterDTO> dtoList = new ArrayList<HospitalMasterDTO>();
		listOfHospital.forEach(entity -> {
			if(entity.getHiStatus().equals("A")) {
			 if((entity.getHiNameMar()!=null)) {
			HospitalMasterDTO dto = new HospitalMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
			  }
			}
		 });
		return dtoList;
	}
	
	@Override
	public List<HospitalMasterDTO> getAllHospitlsWithAllStatus(Long orgId) {

		List<HospitalMaster> listOfHospital = hospitalMasterRepository.findByOrgid(orgId);
		List<HospitalMasterDTO> dtoList = new ArrayList<HospitalMasterDTO>();
  
		if(!listOfHospital.isEmpty()) {
		/*if(String.valueOf(UserSession.getCurrent().getLanguageId()).equals("1")) {*/
			Collections.sort(listOfHospital, new SortByEnglish());
		/*}else {
			Collections.sort(listOfHospital, new SortByMarathi());
		}*/
		listOfHospital.forEach(entity -> {
			 if((entity.getHiNameMar()!=null)) {
			HospitalMasterDTO dto = new HospitalMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
			  }
		 });
		}
		return dtoList;
	}
	
}
