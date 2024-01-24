package com.abm.mainet.additionalservices.rest.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.additionalservices.dto.EChallanItemDetailsDto;
import com.abm.mainet.additionalservices.dto.EChallanMasterDto;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 *
 */

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/echallanEntry")
public class EChallanEntryRestController {

	@Autowired
	private com.abm.mainet.additionalservices.service.EChallanEntryService echallanEntryService;
	
	@RequestMapping(value = "/searchRaidDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public List<EChallanMasterDto> searchRaidDetailsList(@RequestBody EChallanMasterDto challanDto,
			final HttpServletRequest request) {
		String challanType = MainetConstants.EncroachmentChallan.AGAINST_SEIZED_ITEMS;
		List<EChallanMasterDto> searchRaidDetailsList = echallanEntryService.searchRaidDetailsList(
				challanDto.getRaidNo(), challanDto.getOffenderName(), challanDto.getChallanFromDate(),
				challanDto.getChallanToDate(), challanDto.getOffenderMobNo(),challanType,
				challanDto.getOrgid());
		return searchRaidDetailsList;
	}
	
	@RequestMapping(value = "/getEChallanMasterByOrgidAndChallanId/{challanId}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public EChallanMasterDto getEChallanMasterByOrgidAndChallanId(@PathVariable("challanId") Long challanId , @PathVariable("orgId") Long orgId) {
		EChallanMasterDto masterDto = echallanEntryService.getEChallanMasterByOrgidAndChallanId(orgId, challanId);
		return masterDto;
	}
	
	@RequestMapping(value = "/getDuplicateReceiptDetail/{challanId}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public ChallanReceiptPrintDTO getDuplicateReceiptDetail(@PathVariable("challanId") Long challanId, @PathVariable("orgId") Long orgId) {
		ChallanReceiptPrintDTO duplicateReceiptDetail = echallanEntryService.getDuplicateReceiptDetail(challanId, orgId);
		return duplicateReceiptDetail;
	}
	
	@RequestMapping(value = "/saveEChallanEntry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public  EChallanMasterDto saveEChallanEntry(@RequestBody EChallanMasterDto challanMasterDto) {
		try {
			echallanEntryService.saveEChallanEntry(challanMasterDto, challanMasterDto.getEchallanItemDetDto(), null);
			challanMasterDto.setStatus("S");
		}catch (Exception e) {
			challanMasterDto.setStatus("F");
		}
		return challanMasterDto;
	}
	
	
	@RequestMapping(value = "/getDocumentUploadedByRefNoAndDeptId/{raidNo}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public  List<DocumentDetailsVO> getDocumentUploadedByRefNoAndDeptId(@PathVariable("raidNo") String raidNo, @PathVariable("orgId") Long orgId) {
		List<CFCAttachment> checklist = new ArrayList<>();
		List<DocumentDetailsVO> document = new ArrayList<>();
		try {
			
			Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class).getDepartmentIdByDeptCode("ENC");
			checklist = ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class).getDocumentUploadedByRefNoAndDeptId(raidNo,
					deptId, orgId);
			
			if (checklist != null && !checklist.isEmpty()) {
				checklist.forEach(doc -> {
	                DocumentDetailsVO d = new DocumentDetailsVO();
	                d.setDocumentName(doc.getClmDescEngl());
	                d.setUploadedDocumentPath(doc.getAttPath());
	                d.setDocumentType(doc.getDmsDocId());
	                d.setDescriptionType(doc.getAttFname());
	                document.add(d);
	            });
	        }
		}catch (Exception e) {
			
		}
		return document;
	}
}
