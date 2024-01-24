
package com.abm.mainet.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.cms.ui.model.CitizenContactUsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.mobile.dto.EipUserContactUsDTO;
import com.abm.mainet.mobile.dto.OrganisationListResDTO;
import com.abm.mainet.mobile.service.ContactUsService;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/organisationsListController")
public class OrganisationsListController {

	private static final Logger LOG = Logger.getLogger(OrganisationsListController.class);

	@Autowired
	private IOrganisationService iOrganisationService;
	@Autowired
	private IEIPContactUsService iEipContactUsService;
	@Autowired
	private IEmployeeService empService;

	@RequestMapping(value = "/getOrganisationsList", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public OrganisationListResDTO getOrganisationsList(final HttpServletRequest request) {
		final OrganisationListResDTO organisationListResDTO = new OrganisationListResDTO();
		try {
			final List<Organisation> organisationsList = iOrganisationService.findAllActiveOrganization("A");
			if ((organisationsList != null) && !organisationsList.isEmpty()) {
				final List<LookUp> lookUpList = new ArrayList<>();
				LookUp lookup = null;
				for (final Organisation org : organisationsList) {
					lookup = new LookUp();
					lookup.setLookUpId(org.getOrgid());
					lookup.setDescLangFirst(org.getONlsOrgname());
					lookup.setDescLangSecond(org.getONlsOrgnameMar());
					lookup.setDefaultVal(org.getDefaultStatus());
					lookUpList.add(lookup);
				}
				organisationListResDTO.setLookUpList(lookUpList);
				organisationListResDTO.setStatus(MainetConstants.SUCCESS);
			} else {
				organisationListResDTO.setStatus(MainetConstants.FAIL);
			}
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching Organisations List");
		}
		return organisationListResDTO;
	}

	// Adding For getting ContactUs List in Mobile Defect#103914
	@RequestMapping(value = "/getContactUsList/{orgId}/{langId}", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getContactUsList(@PathVariable("orgId") Long orgId, @PathVariable("langId") int langId,
			HttpServletRequest request) {
		List<String> contactOptionList = new ArrayList<String>();
		Organisation org = iOrganisationService.getOrganisationById(orgId);
		List<EIPContactUsHistory> contactUsListByFlag = iEipContactUsService.getContactUslistBy(org);
		for (EIPContactUsHistory eIPContactUsHistory : contactUsListByFlag) {
			// Defect #125497
			if (langId == 2) {
				contactOptionList.add(eIPContactUsHistory.getDesignationReg());
			} else {
				contactOptionList.add(eIPContactUsHistory.getDesignationEn());
			}
		}
		return contactOptionList;

	}

	// Adding For saving ContactUs Details in Mobile Defect#103914
	@RequestMapping(value = "/saveContactUsDet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = {
			MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Boolean saveContactUsDetail(@RequestBody final EipUserContactUsDTO eipDto) throws Exception {

		CitizenContactUsModel model = new CitizenContactUsModel();
		EipUserContactUs eipUser = new EipUserContactUs();
		Employee emp = new Employee();
		if (eipDto != null) {
			eipUser.setDescQuery(eipDto.getDescQuery());
			eipUser.setFirstName(eipDto.getFirstName());
			eipUser.setLastName(eipDto.getLastName());
			eipUser.setEmailId(eipDto.getEmailId());
			eipUser.setPhoneNo(eipDto.getPhoneNo());
			eipUser.setIsDeleted(eipDto.getIsDeleted());
			eipUser.setLangId(eipDto.getLangId());
			eipUser.setLmodDate(eipDto.getLmodDate());
			eipUser.setLgIpMac(eipDto.getLgIpMac());
			eipUser.setUpdatedDate(eipDto.getUpdatedDate());
			emp.setEmpId(eipDto.getEmpId());
			// Defect #135050 start
			Employee emp1 = null;
			Organisation org = null;
			if (eipDto.getOrgId() != null) {
				org = iOrganisationService.getOrganisationById(eipDto.getOrgId());
			} else {
				org = iOrganisationService.getSuperUserOrganisation();

			}
			if (eipDto.getEmpId() != null) {
				emp1 = empService.getEmployeeById(eipDto.getEmpId(), org, MainetConstants.IsDeleted.ZERO);
			} else {
				emp1 = new Employee();
				if (eipDto.getPhoneNo() != null) {
					emp1.setEmpId(Long.valueOf(eipDto.getPhoneNo()));
				} else {
					LOG.info("Mobile no is null for " + eipDto.getFirstName());
				}

			}
			UserSession.getCurrent().setOrganisation(org);
			UserSession.getCurrent().setEmployee(emp1);
			// end
			UserSession.getCurrent().setLanguageId(eipDto.getLangId());

		}
		try {
			model.setEipUserContactUs(eipUser);
			boolean flag2 = true;
			ContactUsService contService = new ContactUsService();
			return contService.saveContactUsDetails(model.getEipUserContactUs());

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	// Adding For getting ContactUs List in Mobile US#140027
	@RequestMapping(value = "/getNodalOfficerContactNo/{orgId}", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, String> getNodalOfficerContactNo(@PathVariable("orgId") Long orgId, HttpServletRequest request) {
		Organisation org = null;
		Map<String, String> mobMap=new HashMap<String, String>();
		if (orgId != null) {
			org = iOrganisationService.getOrganisationById(orgId);
		}

		if (org != null) {
			List<EIPContactUs> contList = iEipContactUsService.getContactListByOrganisation(org);
			if (CollectionUtils.isNotEmpty(contList)) {
				try {
					final LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("NO", "PDL", 1, org);
					if (lookup != null && lookup.getDescLangFirst() != null) {
						List<EIPContactUs> contList1= contList.stream()
								.filter(cont -> cont.getDesignationEn() != null
										&& cont.getDesignationEn().equals(lookup.getDescLangFirst()))
								.collect(Collectors.toList());
						if(CollectionUtils.isNotEmpty(contList1)) {
							mobMap.put("Telephone No", contList1.get(0).getTelephoneNo1En());
							mobMap.put("Mobile No", contList1.get(0).getTelephoneNo2En());
							return mobMap;
						}
					}
				} catch (Exception e) {
					LOG.error("Exception occure during fetching PDL Lookup value");
				}

			}
		}

		return mobMap;

	}
}