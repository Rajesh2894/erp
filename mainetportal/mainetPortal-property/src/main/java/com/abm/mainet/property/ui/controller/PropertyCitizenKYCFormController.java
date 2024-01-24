
package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.IViewPropertyDetailsService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author cherupelli.srikanth
 * @since 14 Dec 2023
 */
@Controller
@RequestMapping("/PropertyCitizenKYCForm.html")
public class PropertyCitizenKYCFormController extends AbstractFormController<SelfAssesmentNewModel> {

	@Autowired
	private SelfAssessmentService selfAssessmentService;

	@Autowired
	private IViewPropertyDetailsService viewPropertyDetailsService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	FileUploadServiceValidator fileUploadServiceValidator;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		fileUploadServiceValidator.sessionCleanUpForFileUpload();
		getModel().setShowForm(MainetConstants.FlagN);
		return index();
	}

	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
		getModel().bind(request);
		List<String> flatList = selfAssessmentService.fetchFlstList(propNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isEmpty(flatList)) {
			return null;
		}
		return flatList;
	}

	@RequestMapping(params = "getPropertyDetails", method = { RequestMethod.POST })
	public ModelAndView getpropertyDetails(HttpServletRequest request) {
		getModel().bind(request);
		SelfAssesmentNewModel model = getModel();
		model.getProvisionalAssesmentMstDto().setBillingMethod(model.getBillingMethod());
		ProperySearchDto searchDto = new ProperySearchDto();
		searchDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		String[] split = model.getProvisionalAssesmentMstDto().getAssNo().split(",");
		model.getProvisionalAssesmentMstDto().setAssNo(split[0]);
		searchDto.setProertyNo(model.getProvisionalAssesmentMstDto().getAssNo());
		searchDto.setFlatNo(model.getProvisionalAssesmentMstDto().getFlatNo());
		ProvisionalAssesmentMstDto assesmentMstDto = viewPropertyDetailsService.fetchPropertyByPropNo(searchDto);
		model.setProvisionalAssesmentMstDto(assesmentMstDto);
		model.setOwnerName(assesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoOwnerName());
		model.setEmailId(assesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).geteMail());
		model.setMobileNo(assesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
		model.setOccupierName(assesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getOccupierName());
		model.setDisplayCaptcha(getCaptcha());
		getModel().setShowForm("Y");

		addDocumentListToUpload(model);

		return new ModelAndView("PropertyCitizenKYCFormValidn", MainetConstants.FORM_NAME, model);
	}
	
	@RequestMapping(params = "getpropertyDetailsFromSearchGrid", method = { RequestMethod.POST })
	public ModelAndView getpropertyDetailsFromSearchGrid(@RequestParam("propNo") String propNo , @RequestParam("flatNo") String flatNo, HttpServletRequest request) {
		getModel().bind(request);
		SelfAssesmentNewModel model = getModel();
		model.getProvisionalAssesmentMstDto().setBillingMethod(model.getBillingMethod());
		ProperySearchDto searchDto = new ProperySearchDto();
		List<String> flatList = selfAssessmentService.fetchFlstList(propNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(flatList)) {
			model.getProvisionalAssesmentMstDto().setBillingMethod(MainetConstants.FLAGI);
			searchDto.setFlatNo(flatNo);
			model.setBillingMethod(MainetConstants.FLAGI);
		}
		searchDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		model.getProvisionalAssesmentMstDto().setAssNo(propNo);
		searchDto.setProertyNo(propNo);
		
		ProvisionalAssesmentMstDto assesmentMstDto = viewPropertyDetailsService.fetchPropertyByPropNo(searchDto);
		model.setProvisionalAssesmentMstDto(assesmentMstDto);
		model.setOwnerName(assesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoOwnerName());
		model.setEmailId(assesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).geteMail());
		model.setMobileNo(assesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
		model.setOccupierName(assesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getOccupierName());
		model.setDisplayCaptcha(getCaptcha());
		getModel().setShowForm("Y");

		addDocumentListToUpload(model);
		model.setSearchFlag(MainetConstants.FlagN);

		return new ModelAndView("PropertyCitizenKYCFormValidn", MainetConstants.FORM_NAME, model);
	}

	private void addDocumentListToUpload(SelfAssesmentNewModel model) {
		List<DocumentDetailsVO> checkList = new ArrayList<DocumentDetailsVO>();

		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		document.setCheckkMANDATORY(MainetConstants.FlagY);
		document.setDoc_DESC_ENGL("Property Bill");

		DocumentDetailsVO document1 = new DocumentDetailsVO();
		document1.setDocumentSerialNo(2L);
		document1.setCheckkMANDATORY(MainetConstants.FlagN);
		document1.setDoc_DESC_ENGL("Electric Bill");
		checkList.add(document);
		checkList.add(document1);
		model.setCheckList(checkList);
	}

	@ResponseBody
	@RequestMapping(params = "generateOtp", method = { RequestMethod.POST })
	public String generateOtp(HttpServletRequest request) {
		getModel().bind(request);
		SelfAssesmentNewModel model = getModel();
		String otp = null;
		otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
		model.setOtp(otp);
		sendOTPEmailAndSMS(model, model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().get(0),
				otp);
		return otp;
	}

	private void sendOTPEmailAndSMS(SelfAssesmentNewModel model, ProvisionalAssesmentOwnerDtlDto ownDtlDto,
			final String otp) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(model.getProvisionalAssesmentMstDto().getAssEmail());
		dto.setMobnumber(model.getMobileNo());
		dto.setAppName(ownDtlDto.getAssoOwnerName());
		dto.setOneTimePassword(otp);
		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		} else {
			dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
		}
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, "PropertyCitizenKYCForm.html",
				MainetConstants.SMS_EMAIL.OTP_MSG, dto, UserSession.getCurrent().getOrganisation(),
				UserSession.getCurrent().getLanguageId());
	}

	@ResponseBody
	@RequestMapping(params = "savePropertyData", method = RequestMethod.POST)
	public Map<String, Object> savePropertyData(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		if (getModel().savePropertyEditForm()) {
			object.put("applicationId", getModel().getProvisionalAssesmentMstDto().getApmApplicationId());
		} else {
			object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		}

		return object;

	}

	public String getCaptcha() {
		char data[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		char index[] = new char[7];

		Random r = new Random();
		int i = 0;
		for (i = 0; i < (index.length); i++) {
			int ran = r.nextInt(data.length);
			index[i] = data[ran];
		}
		return new String(index);
	}

	@RequestMapping(params = "searchPropertyNo", method = { RequestMethod.POST })
	public ModelAndView searchPropertyNo(HttpServletRequest request) {
		getModel().bind(request);
		SelfAssesmentNewModel model = getModel();
		model.getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		List<ProperySearchDto> searchPropertyDetails = viewPropertyDetailsService.searchPropertyDetails(model.getSearchDto());
		model.setSearchDtoResult(searchPropertyDetails);
		return new ModelAndView("PropertyCitizenKYCFormValidn", MainetConstants.FORM_NAME, model);
	}
}
