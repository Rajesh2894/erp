package com.abm.mainet.mrm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.service.IAppointmentService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AppointmentModel extends AbstractFormModel {

    private static final long serialVersionUID = -3561734337829409346L;

    AppointmentDTO appointmentDTO = new AppointmentDTO();
    private MarriageDTO marriageDTO = new MarriageDTO();

    List<AppointmentDTO> appointmentList = new ArrayList<>();

    public AppointmentDTO getAppointmentDTO() {
        return appointmentDTO;
    }

    public void setAppointmentDTO(AppointmentDTO appointmentDTO) {
        this.appointmentDTO = appointmentDTO;
    }

    public MarriageDTO getMarriageDTO() {
        return marriageDTO;
    }

    public void setMarriageDTO(MarriageDTO marriageDTO) {
        this.marriageDTO = marriageDTO;
    }

    public List<AppointmentDTO> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<AppointmentDTO> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @Autowired
    IAppointmentService appointmentService;

    @Autowired
    ICFCApplicationAddressService cfcApplAddress;

    public String updateAppointmentResc(MarriageDTO marriageDTO) {
        // update the appointmentDate and appointment Time
        String message = "Something went wrong";
        String appointmentIdResc = this.appointmentDTO.getAppointmentIdResc();
        String splitIds[] = appointmentIdResc.split(MainetConstants.operator.COMMA);
        // make List of memberId long array
        List<Long> appointmentIds = new ArrayList<>();
        for (int i = 0; i < splitIds.length; i++) {
            appointmentIds.add(Long.valueOf(splitIds[i]));
        }

        appointmentService.updateAppointmentResc(marriageDTO, appointmentIds);

        // send SMS and email
        sendSmsEmail(this, "AppointmentReschedule.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, appointmentIds);
        message = ApplicationSession.getInstance().getMessage("mrm.appoinResc.save");
        return message;
    }

    public void sendSmsEmail(AppointmentModel model, String menuUrl, String msgType, List<Long> appointmentIds) {
        // get mobile no and email id
        appointmentIds.stream().forEach(appId -> {
            AppointmentDTO appDTO = appointmentService.getAppointmentData(appId);
            if (appDTO != null) {
                CFCApplicationAddressEntity cfcAddress = cfcApplAddress
                        .getApplicationAddressByAppId(appDTO.getMarId().getApplicationId(), appDTO.getOrgId());
                SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
                smsDto.setOrgId(model.getOrgId());
                smsDto.setLangId(UserSession.getCurrent().getLanguageId());
                smsDto.setDate(model.getMarriageDTO().getAppointmentDTO().getAppointmentDate());
                smsDto.setGracePeriod(model.getMarriageDTO().getAppointmentDTO().getAppointmentTime());
                smsDto.setServName("MARS");

                smsDto.setMobnumber(cfcAddress.getApaMobilno());
                smsDto.setUserId(model.getUserSession().getEmployee().getEmpId());
                if (StringUtils.isNotBlank(cfcAddress.getApaEmail())) {
                    smsDto.setEmail(cfcAddress.getApaEmail());
                }
                ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                        MainetConstants.MRM.MRM_DEPT_CODE, menuUrl, msgType, smsDto,
                        UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
            }
        });

    }

}
