package com.abm.mainet.rti.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RtiApplicationUserDetailServiceImpl implements IRtiApplicationDetailService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private IDepartmentDAO iDepartmentDAO;

	public WSResponseDTO getApplicableTaxes(RtiRateMaster rtiRateMaster, WSRequestDTO wsRequestDto,
			String chargeApplicablePrefixCode) {
		final WSResponseDTO dto = new WSResponseDTO();
		rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
		rtiRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
				.getValueFromPrefixLookUp(chargeApplicablePrefixCode, MainetConstants.NewWaterServiceConstants.CAA)
				.getLookUpId()));
		wsRequestDto.setDataModel(rtiRateMaster);
		Object response = JersyCall.callRestTemplateClient(wsRequestDto,
				ServiceEndpoints.BRMS_RTI_URL.RTI_DEPENDENT_PARAM_URL);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) response;
		final String jsonString = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(jsonString, WSResponseDTO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/* Method to get charges on applicable taxes */
	@Override
	public List<MediaChargeAmountDTO> getApplicableCharges(List<RtiRateMaster> requiredCharges) {

		final WSRequestDTO wsRequestDto = new WSRequestDTO();
		wsRequestDto.setDataModel(requiredCharges);
		Object responseObj = JersyCall.callRestTemplateClient(wsRequestDto,
				ServiceEndpoints.BRMS_RTI_URL.RTI_SERVICE_CHARGE_URL);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) responseObj;
		final String jsonString = new JSONObject(responseVo).toString();
		WSResponseDTO response;
		try {
			final List<MediaChargeAmountDTO> detailDTOs = new ArrayList<>();
			response = new ObjectMapper().readValue(jsonString, WSResponseDTO.class);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final ArrayList<LinkedHashMap<Long, Object>> responseobj = (ArrayList<LinkedHashMap<Long, Object>>) response
						.getResponseObj();
				for (LinkedHashMap<Long, Object> obj : responseobj) {
					final String d = new JSONObject(obj).toString();
					MediaChargeAmountDTO mediaChargeAmountDTO = new ObjectMapper().readValue(d,
							MediaChargeAmountDTO.class);
					detailDTOs.add(mediaChargeAmountDTO);
				}

				return detailDTOs;
			} else {
				throw new RuntimeException("Rule not Match : " + response.getErrorMessage());
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/* end */

	@SuppressWarnings("unchecked")
	public Set<LookUp> getDeptLocation(Long orgid, Long deptId) {
		Set<LookUp> lookup = new HashSet<>();
		final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(null,
						ServiceEndpoints.CommonPrefixUri.LOCATION_OPERATION_WZ_MAPPING + MainetConstants.WINDOWS_SLASH
								+ "orgId" + MainetConstants.WINDOWS_SLASH + orgid + MainetConstants.WINDOWS_SLASH
								+ "deptId" + MainetConstants.WINDOWS_SLASH + deptId);
		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				LookUp l = new ObjectMapper().readValue(d, LookUp.class);
				lookup.add(l);
			}
			return lookup;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public RtiApplicationFormDetailsReqDTO saveorUpdateRtiApplication(RtiApplicationFormDetailsReqDTO requestDTO) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestDTO, ServiceEndpoints.BRMS_RTI_URL.RTI_SERVICE_SAVE_URL);

		final String d = new JSONObject(responseVo).toString();
		try {
			/*
			 * if (requestDTO.getApplicationId() != null) {
			 * requestDTO.setStatus(MainetConstants.COMMON_STATUS.SUCCESS); }
			 * for(LinkedHashMap<Long, Object> obj:responseVo) { final String d = new
			 * JSONObject(obj).toString(); LookUp l= new ObjectMapper().readValue(d,
			 * LookUp.class); lookup.add(l);
			 */

			return new ObjectMapper().readValue(d, RtiApplicationFormDetailsReqDTO.class);

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/* Method to initiate Free Service Workflow */

	@SuppressWarnings("unchecked")
	@Override
	public RtiApplicationFormDetailsReqDTO initiateFreeServiceWorkFlow(RtiApplicationFormDetailsReqDTO requestDTO) {
		LinkedHashMap<Long, Object> responseVo = null;
		try {
			// responseVo = (LinkedHashMap<Long, Object>)
			// JersyCall.callRestTemplateClientForJBPM(requestDTO,
			// ServiceEndpoints.RTIBRMSMAPPINGURI.RTI_FREE_SERVICE_WORKFLOW);
			final String d = new JSONObject(responseVo).toString();
			requestDTO = new ObjectMapper().readValue(d, RtiApplicationFormDetailsReqDTO.class);
		} catch (Exception e) {
			throw new FrameworkException("Error while casting response to RtiApplicationFormDetailsReqDTO", e);
		}
		return requestDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<LookUp> getRtiWorkflowMasterDefinedDepartmentListByOrgId(Long orgid) {
		List<LinkedHashMap<Long, Object>> requestList = null;
		requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(orgid,
				ServiceEndpoints.BRMS_RTI_URL.RTI_BRMS_MAPPING_URI);
		Set<LookUp> department = new HashSet<>();
		requestList.forEach(obj -> {
			final String d = new JSONObject(obj).toString();
			try {
				final LookUp dept = new ObjectMapper().readValue(d, LookUp.class);
				if (dept != null) {
					department.add(dept);
				}
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		});
		return department;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RtiApplicationFormDetailsReqDTO fetchRtiApplicationInformationById(Long applicationId, Long orgId) {

		RtiApplicationFormDetailsReqDTO rtiDto = new RtiApplicationFormDetailsReqDTO();
		rtiDto.setApplicationId(applicationId);
		rtiDto.setOrgId(orgId);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				rtiDto, ServiceEndpoints.BRMS_RTI_URL.FETCH_RTI_DETAILS + MainetConstants.WINDOWS_SLASH
						+ rtiDto.getApplicationId() + MainetConstants.WINDOWS_SLASH + rtiDto.getOrgId());

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, RtiApplicationFormDetailsReqDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	@Override
	public Boolean resolveServiceWorkflowType(RtiApplicationFormDetailsReqDTO tradeDTO) {
		// TODO Auto-generated method stub
		final Boolean responseVo = (Boolean) JersyCall.callRestTemplateClient(tradeDTO,
				ServiceEndpoints.BRMS_RTI_URL.CHECK_RTI_WORKFLOW_EXIST_OR_NOT);

		try {
			return responseVo;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

}
