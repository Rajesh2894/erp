/**
 * 
 */
package com.abm.mainet.rti.rest.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.repository.RtiRepository;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.RtiApplicationFormModel;

/**
 * @author Anwarul.Hassan
 * @since 24-Jun-2020
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/brmsrtirestservice")
public class RtiApplicationDetailRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RtiApplicationDetailRestController.class);
	private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";

	@Autowired
	private TbDepartmentJpaRepository departmentJpaRepository;

	@Autowired
	RtiRepository rtiRepository;

	@Autowired
	private IRtiApplicationDetailService rtiApplicationDetailService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IOrganisationService iOrganisationService;

	private final ModelMapper modelMapper;

	public RtiApplicationDetailRestController() {
		this.modelMapper = new ModelMapper();
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	@RequestMapping(value = "/getDepartmentByOrg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<LookUp> getActiveDepartment(@RequestBody Long orgId) {
		List<Department> dept = departmentJpaRepository.findAllMappedDepartments(orgId);
		List<LookUp> lookupList = new ArrayList<>();
		for (Department d : dept) {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(d.getDpDeptdesc());
			detData.setDescLangSecond(d.getDpNameMar());
			detData.setLookUpId(d.getDpDeptid());
			lookupList.add(detData);
		}
		return lookupList;
	}

	@RequestMapping(value = "/dependentparams", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO) {
		WSResponseDTO responseDTO = new WSResponseDTO();
		LOGGER.info("brms RTI getApplicableTaxes execution start..");
		responseDTO = rtiApplicationDetailService.getApplicableTaxes(requestDTO);
		return responseDTO;
	}

	@RequestMapping(value = "/servicecharge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO requestDTO) {
		LOGGER.info("brms RTI getApplicableCharges execution start..");
		WSResponseDTO responseDTO = null;
		try {
			responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.RTI_SERVICE_CHARGE_URI);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)
					|| responseDTO.equals(MainetConstants.Common_Constant.ACTIVE)) {
				responseDTO = setServiceChargeDTO(responseDTO);
			} else {
				throw new FrameworkException(
						UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE + responseDTO.getErrorMessage());
			}
		} catch (Exception ex) {
			throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
		}
		LOGGER.info("brms RTI getApplicableCharges execution End..");
		return responseDTO;
	}

//	@RequestMapping(value = "/getCurentTime", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@RequestMapping(value = { "/getCurentTime" }, method = { RequestMethod.POST }, produces = {
			"application/json" }, consumes = { "*/*" })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public RtiApplicationFormDetailsReqDTO getCurentTime(@RequestBody RtiApplicationFormDetailsReqDTO saveDto) {

		RtiApplicationFormDetailsReqDTO rtiApplicationFormDetailsReqDTO = new RtiApplicationFormDetailsReqDTO();
		rtiApplicationFormDetailsReqDTO.setTime(rtiRepository.getCurrentTime());
		return rtiApplicationFormDetailsReqDTO;

	}

	@RequestMapping(value = "/saveRTIApplication", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public RtiApplicationFormDetailsReqDTO getCurentTime1(
			@RequestBody(required = true) RtiApplicationFormDetailsReqDTO requestDTO) {
		return rtiApplicationDetailService.saveApplication(requestDTO);
	}

	private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setServiceChargeDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, RtiRateMaster.class);
		final List<RtiRateMaster> finalRateMaster = new ArrayList<>();
		for (final Object rate : charges) {
			final RtiRateMaster masterRate = (RtiRateMaster) rate;
			finalRateMaster.add(masterRate);
		}
		final List<MediaChargeAmountDTO> detailDTOs = new ArrayList<>();
		for (final RtiRateMaster rateCharge : finalRateMaster) {
			final MediaChargeAmountDTO chargedto = new MediaChargeAmountDTO();
			chargedto.setChargeAmount(rateCharge.getFlatRate());
			chargedto.setFreeCopy(rateCharge.getFreeCopy());
			chargedto.setQuantity(rateCharge.getQuantity());
			chargedto.setMediaType(rateCharge.getMediaType());
			chargedto.setTaxId(rateCharge.getTaxId());
			detailDTOs.add(chargedto);
		}
		responseDTO.setResponseObj(detailDTOs);
		LOGGER.info("setServiceChargeDTO execution end..");
		return responseDTO;
	}

	@RequestMapping(value = "getServiceIdByShortName/{orgId}/{serviceCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Long getServiceIdByShortName(@PathVariable("orgId") Long orgId,
			@PathVariable("serviceCode") String serviceCode) {
		LOGGER.info("Service Master fetch ServiceId  execution start..");

		Long serviceId = serviceMasterService.getServiceIdByShortName(orgId, serviceCode);

		LOGGER.info("Service Master fetch ServiceId  execution end..");
		return serviceId;

	}

	@RequestMapping(value = "getRtiOrganisations/{districtId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<LookUp> rtiOrganisations(@PathVariable Long districtId,
			HttpServletRequest httpServletRequest) {
		RtiApplicationFormModel model = new RtiApplicationFormModel();

		model.setOrg(iOrganisationService.getAllMunicipalOrganisation(districtId));
		if (model.getOrg() != null) {
			for (Organisation org : model.getOrg()) {
				LookUp lookup = new LookUp();
				lookup.setLookUpId(org.getOrgid());
				lookup.setLookUpCode(org.getOrgShortNm());
				lookup.setDescLangFirst(org.getONlsOrgname());
				lookup.setDescLangSecond(org.getONlsOrgnameMar());
				lookup.setLookUpDesc(lookup.getLookUpDesc());
				model.getOrgLookups().add(lookup);
			}
		}
		/*
		 * return new ModelAndView(MainetConstants.GrievanceConstants.ORGANISATION,
		 * MainetConstants.FORM_NAME, getModel());
		 */
		return model.getOrgLookups();
	}

}
