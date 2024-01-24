/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.socialsecurity.service.CancellationOfPensionService;
import com.abm.mainet.socialsecurity.ui.dto.CancelPensionDto;

/**
 * @author priti.singh
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CancelPensionModel extends AbstractFormModel {
  
    private static final long serialVersionUID = 1387449222271618470L;

    @Autowired
    private CancellationOfPensionService cancellationOfPensionService;
    
    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    private List<Object[]> serviceList = new ArrayList<>();

    private List<CancelPensionDto> cancelPensionDtoList = new ArrayList<>();

    private CancelPensionDto cancelPensionDto = new CancelPensionDto();
    private ServiceMaster serviceMaster = new ServiceMaster();

    /**
     * @return the serviceList
     */
    public List<Object[]> getServiceList() {
        return serviceList;
    }

    /**
     * @param serviceList the serviceList to set
     */
    public void setServiceList(List<Object[]> serviceList) {
        this.serviceList = serviceList;
    }

    /**
     * @return the cancellationOfPensionService
     */
    public CancellationOfPensionService getCancellationOfPensionService() {
        return cancellationOfPensionService;
    }

    /**
     * @param cancellationOfPensionService the cancellationOfPensionService to set
     */
    public void setCancellationOfPensionService(CancellationOfPensionService cancellationOfPensionService) {
        this.cancellationOfPensionService = cancellationOfPensionService;
    }

    /**
     * @return the cancelPensionDtoList
     */
    public List<CancelPensionDto> getCancelPensionDtoList() {
        return cancelPensionDtoList;
    }

    /**
     * @param cancelPensionDtoList the cancelPensionDtoList to set
     */
    public void setCancelPensionDtoList(List<CancelPensionDto> cancelPensionDtoList) {
        this.cancelPensionDtoList = cancelPensionDtoList;
    }

    /**
     * @return the cancelPensionDto
     */
    public CancelPensionDto getCancelPensionDto() {
        return cancelPensionDto;
    }

    /**
     * @param cancelPensionDto the cancelPensionDto to set
     */
    public void setCancelPensionDto(CancelPensionDto cancelPensionDto) {
        this.cancelPensionDto = cancelPensionDto;
    }

    /**
     * @return the serviceMaster
     */
    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    /**
     * @param serviceMaster the serviceMaster to set
     */
    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    @Override
    public boolean saveForm() {
        CancelPensionDto statusDto = getCancelPensionDto();

        try {
            statusDto = cancellationOfPensionService.saveCancelPension(statusDto.getPensionCancelReason(),
                    statusDto.getPensionCancelDate(), statusDto.getBeneficiaryno());

            // sms and email on cancel of pension

            final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
            Organisation org = UserSession.getCurrent().getOrganisation();
            smsDto.setMobnumber(getCancelPensionDto().getMobNum().toString());
          
            if(getCancelPensionDto().getApplicationId()!=null) {
            smsDto.setAppNo(getCancelPensionDto().getApplicationId().toString());
            }

            ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(MainetConstants.SocialSecurity.CANCELLATION_OF_PENSION_SERVICE_CODE,
                            UserSession.getCurrent().getOrganisation().getOrgid());
            setServiceMaster(sm);
            smsDto.setServName(sm.getSmServiceName());
            String url = "CancellationofPension.html";
            org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            int langId = UserSession.getCurrent().getLanguageId();
            ismsAndEmailService.sendEmailSMS("SWD", url,
                    MainetConstants.SocialSecurity.REJECTED, smsDto, org, langId);
        } catch (FrameworkException exp) {
            
            statusDto.setStatusFlag(false);
            throw new FrameworkException(exp);
        }
        return true;
    }

}
