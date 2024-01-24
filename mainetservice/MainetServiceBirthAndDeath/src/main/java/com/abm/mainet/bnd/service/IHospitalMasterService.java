package com.abm.mainet.bnd.service;

import java.util.List;

import com.abm.mainet.bnd.domain.HospitalMaster;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;

public interface IHospitalMasterService {

	public List<HospitalMasterDTO> getAllHospitls(Long orgId);

	public void saveHospital(HospitalMasterDTO hospital);

	public HospitalMasterDTO getHospitalById(Long hiId);

	public void deleteHospitalInFoById(Long hiId);


	List<HospitalMasterDTO> findByHospitalNameAndHospitalType(String hiName, Long cpdTypeId, Long orgId);

	List<HospitalMaster> getAllHospitalCode(String hiCode, Long orgId);
	
   public List<HospitalMasterDTO> getAllHospitalByStatus(String Status, Long orgId);
   
   public List<HospitalMasterDTO> getAllHospitalList(Long orgId);

   public List<HospitalMasterDTO> getAllHospitlsWithAllStatus(Long orgId);

}
