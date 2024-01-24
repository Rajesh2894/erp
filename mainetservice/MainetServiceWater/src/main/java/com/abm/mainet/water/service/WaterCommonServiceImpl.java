package com.abm.mainet.water.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WaterServiceShortCode;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.AbstractService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.dao.WaterDisconnectionRepository;
import com.abm.mainet.water.domain.CsmrInfoHistEntity;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.repository.ChangeOfUsageRepository;
import com.abm.mainet.water.repository.PlumberMasterRepository;
import com.abm.mainet.water.repository.TbCsmrInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WaterCommonServiceImpl extends AbstractService implements WaterCommonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterCommonServiceImpl.class);
	@Resource
	private ChangeOfUsageRepository changeOfUsesRepository;

	@Resource
	private WaterServiceMapper waterServiceMapper;

	@Resource
	private NewWaterRepository waterRepository;

	@Resource
	private AuditService auditService;

	@Resource
	private WaterDisconnectionRepository disconnectionRepository;

	@Resource
	private ChangeOfUsageRepository changeOfUsageRepository;

	@Resource
	private PlumberMasterRepository plumberMasterRepository;
	
	@Resource
	private TbCsmrInfoRepository tbCsmrInfoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public TbKCsmrInfoMH getConnectionDetail(final long orgId, final String connectionNumber) {
		return changeOfUsesRepository.getConnectionDetails(orgId, connectionNumber);

	}

	@Override
	@Transactional(readOnly = true)
	public CustomerInfoDTO getConnectionDetailDto(final long orgId, final String connectionNumber) {
		return waterServiceMapper
				.mapTbKCsmrInfoToCustomerInfoDTO(changeOfUsesRepository.getConnectionDetails(orgId, connectionNumber));

	}

	@Override
	@Transactional(readOnly = true)
	public TbCsmrInfoDTO getApplicantInformationById(final long applicationId, final long orgId) {

		final TbKCsmrInfoMH master = waterRepository.getApplicantInformationById(applicationId, orgId);
		final TbCsmrInfoDTO csmrDTO = waterServiceMapper.maptbKCsmrInfoMHToTbKCsmrInfoDTO(master);
		return csmrDTO;

	}

	@Override
	@Transactional
	public void updateCsmrInfo(final TbKCsmrInfoMH master, final ScrutinyLableValueDTO lableValueDTO) {
		if (lableValueDTO != null) {
			saveScrutinyValue(lableValueDTO);
		}
		waterRepository.updateCsmrInfo(master);
		final CsmrInfoHistEntity hist = new CsmrInfoHistEntity();
		CsmrInfoHistEntity csmrHistory = waterRepository.getCsmrHistByCsIdAndOrgId(master.getCsIdn(), master.getOrgId());
		if(csmrHistory!=null) {
			TbKCsmrInfoMH currentPersistingEntity = waterRepository.getApplicantInformationById(master.getApplicationNo(), master.getOrgId());
			waterRepository.updateCsmrInfoHistory(currentPersistingEntity);
		}else {
			auditService.createHistory(master, hist);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TbCsmrInfoDTO getApplicantInformationByAppId(long applicationId, long orgId, String serviceShortCode) {
		TbKCsmrInfoMH master = null;
		switch (serviceShortCode) {
		case MainetConstants.NewWaterServiceConstants.WNC:
			master = waterRepository.getApplicantInformationById(applicationId, orgId);
			break;
		case WaterServiceShortCode.WATER_DISCONNECTION:
			master = disconnectionRepository.getApplicantDetails(applicationId, orgId);
			//master.getRoadList().clear();
			break;
		case WaterServiceShortCode.CHANGE_OF_USAGE:
			master = changeOfUsageRepository.getConnectionDetails(orgId,
					changeOfUsageRepository.getChangeOfUsaes(orgId, applicationId).getCsIdn());
			Organisation org = new Organisation();
			org.setOrgid(orgId);
			if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {

			}
			else {
				master.getRoadList().clear();
			}
			break;
		case "WIL":
			master = waterRepository.getApplicantInformationById(applicationId, orgId);
			break;
		}

		final TbCsmrInfoDTO csmrDTO = waterServiceMapper.maptbKCsmrInfoMHToTbKCsmrInfoDTO(master);
		return csmrDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public TbCsmrInfoDTO fetchConnectionDetailsByConnNo(String csCcn, Long orgid, String active) {
		final TbKCsmrInfoMH entityData = waterRepository.fetchConnectionDetails(csCcn, orgid, active);
		TbCsmrInfoDTO dto = null;
		if (entityData != null) {
			dto = new TbCsmrInfoDTO();
			/*dto.setCsIdn(entityData.getCsIdn());
			dto.setCsCcn(entityData.getCsCcn());
			dto.setCsMeteredccn(entityData.getCsMeteredccn());
			dto.setCsName(entityData.getCsName());
			dto.setCsMname(entityData.getCsMname());
			dto.setCsLname(entityData.getCsLname());
			dto.setCsCcnsize(entityData.getCsCcnsize());*/
			BeanUtils.copyProperties(entityData, dto);
			Long meternoByConNo = waterRepository.getMeternoByConNo(csCcn, orgid, active);
			if(meternoByConNo != null)
				dto.setMeterNo(meternoByConNo);
		}
		
		return dto;
	}

	
	/* @Override
	    public int findGenFlag(Long csIdn, Long orgId) {
	        return pensionDetailMasterRepository.findGenFlag(csIdn, orgId);
	    }*/
	@Override
	@Transactional(readOnly = true)
	public TbKCsmrInfoMH fetchConnectionDetailsByConnNo(String csCcn, Long orgid) {
		final TbKCsmrInfoMH entityData = waterRepository.fetchConnectionDetailsByConnNo(csCcn, orgid);
		return entityData;
	}

	@Override
	@Transactional(readOnly = true)
	public TbKCsmrInfoMH getWaterConnectionDetailsById(Long csIdn, Long orgId) {
		return waterRepository.getWaterConnectionDetailsById(csIdn, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public PlumberMaster getPlumberDetailsById(final Long pluberId) {
		return plumberMasterRepository.findOne(pluberId);
	}

	@Override
	@Transactional(readOnly = true)
	public String getPlumberLicenceNumber(final Long plumbId) {

		return plumberMasterRepository.getPlumberLicenceNumber(plumbId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> validateAndFetchProperty(final String propertyNo, final Long orgId) {
		final Map<String, String> request = new HashMap<>(0);
		final Map<String, String> response = new HashMap<>(0);
		request.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.REQUEST_PROPNO, propertyNo);
		request.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.REQUEST_ORGID, orgId.toString());
		try {
			final ResponseEntity<?> responseValidate = RestClient.callRestTemplateClient(request,
					ApplicationSession.getInstance().getMessage("PROPERTY_BILL_PAYMENT"));
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) responseValidate.getBody();
			final String jsonString = new JSONObject(responseVo).toString();
			final Map<String, Object> result = new ObjectMapper().readValue(jsonString, Map.class);
			if (result != null && !result.isEmpty()) {
				response.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_AMOUNT, result
						.get(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_AMOUNT).toString());
				response.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_PROPNO, result
						.get(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_PROPNO).toString());
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching property details in water connection for property No:" + propertyNo
					+ " and orgID:" + orgId, e);
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean fetchConnectionFromOldCcnNumber(String csOldccn, long orgid) {
		final TbKCsmrInfoMH entityData = waterRepository.fetchConnectionFromOldCcnNumber(csOldccn, orgid);
		if (entityData == null) {
			return false;
		}
		return true;
	}

	@Override
	public List<PlumberMaster> listofplumber(final Long orgid) {
		return plumberMasterRepository.getAllPlumber(orgid);
	}

	@Override
	@Transactional(readOnly = true)
	public String getPlumberLicenceName(final Long plumbId) {
		String plumberName = null;
		List<Object[]> plumberLicenceNameList = plumberMasterRepository.getPlumberLicenceName(plumbId);
		if(plumberLicenceNameList != null) {
			for (Object[] plumObject : plumberLicenceNameList) {
				 plumberName = plumObject[0]+ MainetConstants.WHITE_SPACE;
					plumberName += plumObject[1] == null ? MainetConstants.BLANK
			                : plumObject[1] + MainetConstants.WHITE_SPACE;
			        
					plumberName += plumObject[2];
			}
		}
		return plumberName;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<Long,Date> getReceiptDet(Long apmId,Long orgId){

		return waterRepository.getReceiptDet(apmId,orgId);
		
	}
      // #95890->Required search option with old connection number for water bill deletion
	@Override
	public TbCsmrInfoDTO fetchConnectionDetailsByConnNoOrOldConnNo(String csCcn, String csOldccn, Long orgid,
			String active) {
		final TbKCsmrInfoMH entityData = waterRepository.fetchConnectionDet(csCcn,csOldccn, orgid, active);
		TbCsmrInfoDTO dto = null;
		if (entityData != null) {
			dto = new TbCsmrInfoDTO();
			BeanUtils.copyProperties(entityData, dto);
			Long meternoByConNo = waterRepository.getMeternoByConNo(entityData.getCsCcn(), orgid, active);
			if(meternoByConNo != null)
				dto.setMeterNo(meternoByConNo);
		}
		return dto;
	
	}
	
	@Override
	@Transactional(readOnly = true)
	public TbCsmrInfoDTO getCsmrInfoByAppIdAndOrgId(final long applicationId, final long orgId) {

		final TbKCsmrInfoMH master = waterRepository.getCsmrInfoByAppIdAndOrgId(applicationId, orgId);
		final TbCsmrInfoDTO csmrDTO = waterServiceMapper.maptbKCsmrInfoMHToTbKCsmrInfoDTO(master);
		return csmrDTO;

	}
	
	@Transactional
    @Override
    @WebMethod(exclude = true)
    public boolean updateConnectionAppNoByCsIdn(Long csIdn,Long applicationId) {
		tbCsmrInfoRepository.updateConnectionAppNoByCsIdn(csIdn, applicationId);
        return true;
    }
	
	
	@Override
	@Transactional
	public List<Object[]> fetchCountMeterTypeCcn(Long orgId, String date1) {
		List<Object[]> master=tbCsmrInfoRepository.fetchCountMeterTypeCcn(orgId, date1);
		return master;
	}

	@Override
	@Transactional
	public List<Object[]> fetchCountUsageTypeCcn(Long orgId, String date1) {	 
		List<Object[]> master=tbCsmrInfoRepository.fetchCountUsageTypeCcn(orgId, date1);
		return master;
	}

	@Override
	@Transactional
	public List<Object[]> totalPendingApplications(Long orgId, String date1) {
		List<Object[]> master= tbCsmrInfoRepository.totalPendingApplications(orgId, date1);
		return master;
	}

	@Override
	@Transactional
	public List<Object[]> fetchCountCcnType(Long orgId, String dateN) {
		List<Object[]> master= tbCsmrInfoRepository.fetchCountCcnType(orgId, dateN);
		return master;
	}
	
}
