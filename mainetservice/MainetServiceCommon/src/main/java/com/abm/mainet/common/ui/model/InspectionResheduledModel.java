package com.abm.mainet.common.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.dto.TbVisitorSchedule;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.InspectionReScheduleService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class InspectionResheduledModel extends AbstractFormModel {
	private static final long serialVersionUID = -3561734332782949346L;
	List<TbVisitorSchedule> tbVisitorList = new ArrayList<TbVisitorSchedule>();
	private TbVisitorSchedule tbVisitor;

	@Autowired
	ICFCApplicationAddressService cfcApplAddress;

	@Autowired
	InspectionReScheduleService inspectionReScheduleService;

	public List<TbVisitorSchedule> getTbVisitorList() {
		return tbVisitorList;
	}

	public void setTbVisitorList(List<TbVisitorSchedule> tbVisitorList) {
		this.tbVisitorList = tbVisitorList;
	}

	public TbVisitorSchedule getTbVisitor() {
		return tbVisitor;
	}

	public void setTbVisitor(TbVisitorSchedule tbVisitor) {
		this.tbVisitor = tbVisitor;
	}

	public String saveOrUpdate() {
		String message = "Something went wrong";
		String appointmentIdResc = this.getTbVisitor().getVisInspNo();
		String splitIds[] = appointmentIdResc.split(MainetConstants.operator.COMMA);
		// make List of memberId long array
		List<Long> appointmentIds = new ArrayList<>();
		for (int i = 0; i < splitIds.length; i++) {
			appointmentIds.add(Long.valueOf(splitIds[i]));
		}
		this.getTbVisitor().setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		inspectionReScheduleService.updateAppointmentResc(this.getTbVisitor(), appointmentIds);

		// send SMS and email
		sendSmsEmail(this, "InspectionResheduleController.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED,
				appointmentIds);
		message = ApplicationSession.getInstance().getMessage("mrm.appoinResc.save");
		return message;
	}

	public void sendSmsEmail(InspectionResheduledModel model, String menuUrl, String msgType,
			List<Long> appointmentIds) {
		// get mobile no and email id
		appointmentIds.stream().forEach(appId -> {

			CFCApplicationAddressEntity cfcAddress = cfcApplAddress.getApplicationAddressByAppId(appId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			smsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			smsDto.setLangId(UserSession.getCurrent().getLanguageId());
			smsDto.setDate(model.getTbVisitor().getVisD1());
			smsDto.setGracePeriod(model.getTbVisitor().getVisTime());

			smsDto.setMobnumber(cfcAddress.getApaMobilno());
			smsDto.setUserId(model.getUserSession().getEmployee().getEmpId());
			if (!StringUtils.isEmpty(cfcAddress.getApaEmail())) {
				smsDto.setEmail(cfcAddress.getApaEmail());
			}
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, menuUrl, msgType, smsDto,
					UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

		});

	}

}
