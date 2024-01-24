package com.abm.mainet.adh.ui.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.IRenewalRemainderNoticeService;
import com.abm.mainet.adh.ui.model.RenewalRemainderNoticeModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author cherupelli.srikanth
 * @since 27 September 2019
 */
@Controller
@RequestMapping("/RenewalRemainderNotice.html")
public class RenewalRemainderNoticeController extends AbstractFormController<RenewalRemainderNoticeModel> {

    @Autowired
    IAdvertiserMasterService advertiserMasterService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
	sessionCleanup(request);

	LookUp noticeTypelookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.RRN,
		PrefixConstants.LookUpPrefix.NTP, UserSession.getCurrent().getOrganisation());

	if (noticeTypelookUp != null) {
	    this.getModel().setNoticeTypeLookUp(noticeTypelookUp);
	}

	this.getModel().setAdvertiserDisplayFlag(MainetConstants.FlagN);
	return index();
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_ADVERTISER_BY_ADVERTISER_TYPE, method = {
	    RequestMethod.POST })
    public List<AdvertiserMasterDto> getAdvertiserByAdvertiserType(
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.ADVERTISER_TYPE) Long advertiserType,
	    HttpServletRequest request) {

	this.getModel().bind(request);
	RenewalRemainderNoticeModel model = this.getModel();

	List<AdvertiserMasterDto> advertiserMasterDtoList = advertiserMasterService
		.getAgencyDetailsByAgencyCategoryAndOrgId(advertiserType,
			UserSession.getCurrent().getOrganisation().getOrgid());
	if (CollectionUtils.isNotEmpty(advertiserMasterDtoList)) {

	    for (AdvertiserMasterDto advertiserDto : advertiserMasterDtoList) {
		if (advertiserDto.getAgencyLicToDate() != null) {
		    LocalDate renRemDate = advertiserDto.getAgencyLicToDate().toInstant().atZone(ZoneId.systemDefault())
			    .toLocalDate();
		    LocalDate applicableDate = renRemDate
			    .minusDays(Long.valueOf(model.getNoticeTypeLookUp().getOtherField()));
		    if ((Utility.compareDate(Date.from(applicableDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
			    new Date())
			    || Utility.comapreDates(new Date(),
				    Date.from(applicableDate.atStartOfDay(ZoneId.systemDefault()).toInstant())))
			    && StringUtils.equals(advertiserDto.getAgencyStatus(), MainetConstants.FlagA)) {
			model.getAdvertiserDtoList().add(advertiserDto);
		    }
		}
	    }
	}
	return model.getAdvertiserDtoList();
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_AGENCY_BY_LICNO, method = {
	    RequestMethod.POST })
    public ModelAndView searchAgencyByLicNo(
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.AGENCY_ID) Long agencyId, HttpServletRequest request) {

	this.getModel().bind(request);
	RenewalRemainderNoticeModel model = this.getModel();
	for (AdvertiserMasterDto advertsierDto : model.getAdvertiserDtoList()) {
	    if (agencyId.equals(advertsierDto.getAgencyId())) {
		advertsierDto.setAgencyLicenseToDate(
			new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(advertsierDto.getAgencyLicToDate()));
		advertsierDto.setAgencyLicenseFromDate(
			new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(advertsierDto.getAgencyLicFromDate()));
		model.setAdvertiserDto(advertsierDto);
		break;
	    }
	}

	model.setAdvertiserDisplayFlag(MainetConstants.FlagY);
	model.setRemainNoticeExistFlag(MainetConstants.FlagN);

	List<Object[]> renRemDateAndNoticeNoList = ApplicationContextProvider.getApplicationContext()
		.getBean(IRenewalRemainderNoticeService.class).getNoticeCreatedDateByAgencyIdAndOrgId(
			model.getAdvertiserDto().getAgencyId(), UserSession.getCurrent().getOrganisation().getOrgid());

	if (CollectionUtils.isNotEmpty(renRemDateAndNoticeNoList)) {
	    Object[] obj = renRemDateAndNoticeNoList.get(renRemDateAndNoticeNoList.size() - 1);
	    Date converObjectToDate = Utility.converObjectToDate(obj[0]);
	    if ((converObjectToDate.after(model.getAdvertiserDto().getAgencyLicFromDate())
		    || converObjectToDate.compareTo(model.getAdvertiserDto().getAgencyLicFromDate()) == 0)
		    && (converObjectToDate.before(model.getAdvertiserDto().getAgencyLicToDate())
			    || converObjectToDate.compareTo(model.getAdvertiserDto().getAgencyLicToDate()) == 0)) {
		model.getRemainderNoticeDto().setNoticeNo(String.valueOf(obj[1]));
		model.getRemainderNoticeDto().setRemarks(String.valueOf(obj[2]));
		model.setRemainNoticeExistFlag(MainetConstants.FlagY);
	    } else {
		model.setRemainNoticeExistFlag(MainetConstants.FlagN);
		model.getRemainderNoticeDto().setRemarks("");
	    }
	}

	return new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_REMAINDER_NOTICE_VALIDN,
		MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.PRINT_RENEWAL_REMAINDER_NOTICE, method = {
	    RequestMethod.POST })
    public ModelAndView printRenewalremainderNotice(HttpServletRequest request) {

	this.getModel().bind(request);
	RenewalRemainderNoticeModel model = this.getModel();
	ModelAndView mv = null;
	mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_REMAINDER_NOTICE_PRINT,
		MainetConstants.FORM_NAME, this.getModel());
	model.getAdvertiserDto().setAgencyLicenseFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
		.format(model.getAdvertiserDto().getAgencyLicFromDate()));

	if (StringUtils.equals(model.getRemainNoticeExistFlag(), MainetConstants.FlagN)) {
	    if (!model.saveNoticeForm()) {
		model.addValidationError(getApplicationSession().getMessage("renewal.save.validation.message"));
	    } else {
		model.sendSmsEmail(model.getAdvertiserDto(), "RenewalRemainderNotice.html",
			PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG);
	    }
	}

	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	return mv;
    }
}
