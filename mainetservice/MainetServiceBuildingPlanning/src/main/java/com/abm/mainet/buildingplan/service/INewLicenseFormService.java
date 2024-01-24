package com.abm.mainet.buildingplan.service;

import java.util.List;
import java.util.Map;

import org.model.District;
import org.model.Khasra;
import org.model.Must;
import org.model.Owner;
import org.model.Tehsil;
import org.model.Village;

import com.abm.mainet.buildingplan.domain.LicenseApplicationFeesMasterEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;
import com.abm.mainet.buildingplan.dto.LicenseApplicationFeeMasDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandAcquisitionDetailDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandSurroundingsDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationMasterDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

public interface INewLicenseFormService {

	LicenseApplicationMasterDTO saveRegForm(LicenseApplicationMasterDTO licenseApplicationMasterDTO);

	LicenseApplicationMasterDTO getLicenceAppChargesFromBrmsRule(LicenseApplicationMasterDTO masDto);

	List<District> getDistrict() throws Exception;

	List<Tehsil> getTehsil(String DistrictCode) throws Exception;

	List<Village> getVillages(String dCode, String tCode) throws Exception;

	Must getMurabaByNVCODE(String dCode, String tCode, String NVCode) throws Exception;

	List<Khasra> getKhasraListByNVCODE(String dCode, String tCode, String NVCode, String muraba) throws Exception;

	List<Owner> getOwnersbykhewatOnline(String dCode, String tCode, String NVCode, String _Khewat) throws Exception;

	List<LicenseApplicationMasterDTO> getNewLicenseSummaryData(Long orgId, Long userId);

	LicenseApplicationMasterDTO findLicMasById(Long id);

	LicenseApplicationMasterDTO findByApplicationNoAndOrgId(Long applicationNo, Long orgId);

	Map<String, String> getApplicationDetail(Long applicationId, Long orgId);

	List<LicenseApplicationLandAcquisitionDetEntity> saveLandDetailsData(
			List<LicenseApplicationLandAcquisitionDetEntity> listLandDetailsEntity, String scrnFlag);
	
	void saveApplicantCheckList(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId);

	void saveApplicantPurposeCheckList(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId);

	void saveLandScheduleCheckListDoc(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId);

	void saveAppLandDetailsCheckListDoc(LicenseApplicationMasterDTO licenseApplicationMasterDTO, Long serviceId);

	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO);

	List<LicenseApplicationLandAcquisitionDetailDTO> getApplicationNotingDetail(Long applicationId, Long orgId,
			Long level,String scrnFlag);

	List<DocumentDetailsVO> prepareFileUploadForNewLicenseDoc(List<DocumentDetailsVO> docs, Long dCount);
	
	void updateLoaRemark(Long applicationNo, String loaDcRemark);

	String getLaoRemark(Long applicationId, Long orgId);

	List<LicenseApplicationFeeMasDTO> getFeeAndCharges(Long applicationId, Long orgId);

	List<LicenseApplicationFeeMasDTO> saveFeeCharges(List<LicenseApplicationFeeMasDTO> listLandDetailsEntity,
			Long taskId);

	List<LicenseApplicationLandSurroundingsDTO> getSurroundingDetail(Long applicationId, Long orgId);

	List<LicenseApplicationLandSurroundingsDTO> saveSurroundingDetail(
			List<LicenseApplicationLandSurroundingsDTO> landSurroundListDto, Long taskId);
	
	List<LicenseApplicationMaster> doesLicenseApplicationExist(Long khrsDist, Long khrsDevPlan, Long khrsZone, Long khrsSec, String khrsThesil,
			String khrsRevEst, String khrsHadbast, String khrsMustil, String khrsKilla);

}
