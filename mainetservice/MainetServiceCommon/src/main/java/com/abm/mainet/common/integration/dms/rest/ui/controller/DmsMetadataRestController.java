package com.abm.mainet.common.integration.dms.rest.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.InvalidRequestException;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.dto.DmsRestDto;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IDmsMetadataService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author Arun Shinde
 *
 */
@RestController
@RequestMapping("/DmsMetadataRestController")
public class DmsMetadataRestController {

	private static final Logger LOGGER = Logger.getLogger(DmsMetadataRestController.class);

	@Autowired
	private IDmsMetadataService dmsService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private IEmployeeService empService;

	@Autowired
	private DepartmentService departmentService;

	// #105966 exposed to save metadata through offline utility
	@RequestMapping(value = "/saveMetadata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> saveMetadata(@RequestBody final DmsRestDto dmsRestDto, final HttpServletRequest request,
			final HttpServletResponse response, final BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		Map<String, String> map = new HashMap<>();
		if (!bindingResult.hasErrors()) {

			try {
				FileUploadDTO fileUploadDto = setDtoFields(dmsRestDto);
				dmsService.saveForm(fileUploadDto); // save data
				if (fileUploadDto.getDmsDocsDto().getSeqNo() != null) {
					map.put(MainetConstants.ResponseCode, MainetConstants.WebServiceStatus.SUCCESS);
					map.put(MainetConstants.ResponseMessage, "Record saved successfully . Your Application no. is : "
							+ fileUploadDto.getDmsDocsDto().getSeqNo());
					responseEntity = ResponseEntity.status(HttpStatus.OK).body(map);
				} else {
					map.put(MainetConstants.ResponseCode, MainetConstants.WebServiceStatus.SUCCESS);
					map.put(MainetConstants.ResponseMessage, "Record saved successfully.");
					responseEntity = ResponseEntity.status(HttpStatus.OK).body(map);
				}
			} catch (Exception e) {
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		} else {
			LOGGER.error("Error in binding request to DTO");
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new InvalidRequestException("Invalid Request", bindingResult));
		}
		return responseEntity;
	}

	public FileUploadDTO setDtoFields(DmsRestDto dmsRestDto) {
		FileUploadDTO fileDto = new FileUploadDTO();
		Organisation orgsn = new Organisation();
		List<Organisation> organisations = orgService.findAllActiveOrganization(MainetConstants.FlagA);
		for (Organisation org : organisations) {
			if (org.getOrgShortNm().equals(dmsRestDto.getOrgCode())) {
				orgsn = org;
				fileDto.setOrgId(org.getOrgid());
			}
		}

		List<EmployeeBean> empList = empService.getAllActiveEmployee();
		empList.forEach(emp -> {
			if (emp.getEmploginname().equals(dmsRestDto.getEmpLoginId())) {
				fileDto.setUserId(emp.getEmpId());
			}
		});
		fileDto.setDepartmentName(dmsRestDto.getDeptCode());

		DmsDocsMetadataDto dmsDocsMetadataDto = new DmsDocsMetadataDto();
		List<DmsDocsMetadataDetDto> dmsDocsMetadataDetList = new ArrayList<DmsDocsMetadataDetDto>();
		List<LookUp> metadataList = null;
		List<LookUp> prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.MTD, MainetConstants.NUMBERS.ONE,
				orgsn);// parent

		Long deptId = null;
		for (LookUp prefix : prefixList) {
			if (prefix.getLookUpCode().equals(dmsRestDto.getDeptCode())) {
				deptId = prefix.getLookUpId();
				metadataList = CommonMasterUtility.getChildLookUpsFromParentId(prefix.getLookUpId());// child
			}
		}

		Map<String, String> dmsServiceMap = new HashMap<String, String>();
		for (LookUp prefix : dmsRestDto.getDmsMetadataList()) {
			DmsDocsMetadataDetDto detDto = new DmsDocsMetadataDetDto();
			for (LookUp metadata : metadataList) {
				if (metadata.getLookUpCode().equals(prefix.getLookUpCode())) {
					detDto.setMtKey(String.valueOf(metadata.getLookUpId()));
					detDto.setMtVal(prefix.getDescLangFirst());
					dmsServiceMap.put(prefix.getLookUpCode(), prefix.getDescLangFirst());
					dmsDocsMetadataDetList.add(detDto);
				}
			}
		}
		dmsDocsMetadataDto.setDmsServiceMap(dmsServiceMap);
		dmsDocsMetadataDto.setDmsDocsMetadataDetList(dmsDocsMetadataDetList);

		String departmentFullName = departmentService.getDepartment(dmsRestDto.getDeptCode(), MainetConstants.FlagA)
				.getDpDeptdesc();
		fileDto.setDepartmentFullName(departmentFullName);
		dmsDocsMetadataDto.setDeptId(deptId.toString());
		dmsDocsMetadataDto.setDmsId(dmsRestDto.getFormId());
		dmsDocsMetadataDto.setStatus(MainetConstants.AssetManagement.STATUS.SUBMITTED);

		List<DocumentDetailsVO> attachments = new ArrayList<>();
		for (LookUp docList : dmsRestDto.getAttachmentList()) {
			DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
			documentDetailsVO.setDocumentName(docList.getDescLangFirst());
			documentDetailsVO.setDescriptionType(CommonMasterUtility
					.getValueFromPrefixLookUp(docList.getLookUpType(), MainetConstants.Dms.DCT, orgsn).getLookUpDesc());
			documentDetailsVO.setDocumentByteCode(docList.getOtherField());
			attachments.add(documentDetailsVO);
		}
		dmsDocsMetadataDto.setAttachments(attachments);
		fileDto.setDmsDocsDto(dmsDocsMetadataDto);
		return fileDto;

	}

}
