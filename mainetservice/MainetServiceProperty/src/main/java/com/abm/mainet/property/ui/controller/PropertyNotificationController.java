package com.abm.mainet.property.ui.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.model.PropertyStatusUpdateModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Arun Shinde
 *
 */
@Controller
@RequestMapping("/PropertyNotification.html")
public class PropertyNotificationController extends AbstractFormController<PropertyStatusUpdateModel> {

	private static final Logger LOGGER = Logger.getLogger(PropertyNotificationController.class);

	@Autowired
	private ViewPropertyDetailsService viewDetailService;

	@Autowired
	private ISMSAndEmailService smsAndEmailService;

	@Autowired
	private PropertyMainBillService mainBillService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		return new ModelAndView("PropertyNotification", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "sendNotification", method = RequestMethod.POST )
	public ModelAndView sendNotification(final HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " sendNotification() method");
		getModel().bind(request);
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		boolean success = true;
		ProperySearchDto searchDto = getModel().getSearchDto();
		searchDto.setOrgId(org.getOrgid());
		String orgLink = this.getModel().getAppSession().getMessage("property.orgLink");
		try {
			List<ProperySearchDto> results = viewDetailService.searchPropertyDetailsForAll(searchDto, null, null, null,
					null);
			LOGGER.info(" <----------------------- Details fetched from Property tables -------------------->");

		if (CollectionUtils.isNotEmpty(results)) {
			// To calculate rebate percentage slab wise in case of ASCL
			String percentage = "";
			List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.RDS, org);
			int month = Utility.getMonthByDate(new Date()) + 1;
			int monthForEndDate = 0;
			
            //In prefix RDS , months should be define with '-' in between Ex- 04-07
			for (LookUp lookUp : lookUps) {
				String[] str = lookUp.getLookUpCode().split(MainetConstants.HYPHEN);
				if ((Integer.parseInt(str[0]) <= month) && (month <= Integer.parseInt(str[1]))) {
					percentage = lookUp.getOtherField() + MainetConstants.operator.PERCENTILE; // To set rebate percentage
					monthForEndDate = Integer.parseInt(str[1]);
					break;
				}
			}

			String financialYear = "";
			financialYear = Utility.getFinancialYearFromDate(new Date());
			Date[] dates = Utility.getFromAndToDate(financialYear, monthForEndDate); // Will give month start and end
																						// date
			LocalDate date = dates[1].toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainetConstants.COMMON_DATE_FORMAT);
			String dueDateStr = date.format(formatter); // To set due date
			
            
				for (ProperySearchDto result : results) {
					SMSAndEmailDTO dto = new SMSAndEmailDTO();
					dto.setMobnumber(result.getMobileno());
					dto.setEmail(result.geteMail());
					dto.setUserId(empId);
					dto.setPropertyNo(
							StringUtils.isNotBlank(result.getOldPid()) ? result.getOldPid() : MainetConstants.HYPHEN);
					dto.setRegNo(
							StringUtils.isNotBlank(result.getHouseNo()) ? result.getHouseNo() : MainetConstants.HYPHEN);

					LOGGER.info(" <----------------------- Getting bill details -------------------->");
					List<TbBillMas> mainBillList = mainBillService.fetchAllBillByPropNo(result.getProertyNo(),
							org.getOrgid());
					Double amount = 0.0;
					String billNo = "";
					if (CollectionUtils.isNotEmpty(mainBillList)) {
						TbBillMas mainBill = mainBillList.get(mainBillList.size() - 1);
						for (TbBillDet det : mainBill.getTbWtBillDet()) {
							amount += det.getBdCurBalTaxamt() + det.getBdPrvBalArramt();
						}
						billNo = mainBill.getBmNo();
					}
					dto.setBillNo(billNo);
					dto.setAmount(amount);
					dto.setDueDt(dueDateStr);
					dto.setAppAmount(percentage);
					dto.setOrgName(orgLink);
					dto.setServiceUrl(MainetConstants.Property.PROP_NOT_URL);
					LOGGER.info(" Sending SMS to property no : " + result.getProertyNo());
					smsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
							"PropertyNotification.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org,
							UserSession.getCurrent().getLanguageId());
				}
			} else {
				success = false;
				this.getModel()
						.addValidationError(getApplicationSession().getMessage("property.noRecordFoundForSelected"));
			}
		} catch (Exception e) {
			success = false;
			this.getModel()
					.addValidationError(getApplicationSession().getMessage("property.problemOccurredWhileSearch"));
		}
		if (success) {
			LOGGER.info("End --> " + this.getClass().getSimpleName() + " sendNotification() method");
			return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("property.notificationSent")));
		} else {
			ModelAndView mv = new ModelAndView("PropertyNotificationValidn", MainetConstants.FORM_NAME,
					this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
	}

	@RequestMapping(params = "propertyNotificationReset", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView propertyNotificationReset(final HttpServletRequest request) {
		this.sessionCleanup(request);
		return new ModelAndView("PropertyNotificationValidn", MainetConstants.FORM_NAME, this.getModel());
	}
}
