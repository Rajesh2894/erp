/**
 * 
 */
package com.abm.mainet.account.ui.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.domain.TbAcChequebookleafDetHistoryEntity;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.StopPaymemtReqDto;
import com.abm.mainet.account.dto.TbAcChequebookleafDetDto;
import com.abm.mainet.account.repository.TbAcChequebookleafDetJpaRepository;
import com.abm.mainet.account.service.IStopPaymentService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Anwarul.Hassan
 * @since 10-Dec-2019
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class StopPaymentModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IStopPaymentService stopPaymentService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private TbAcChequebookleafDetJpaRepository chequebookleafDetJpaRepository;
    private StopPaymemtReqDto stopPaymemtReqDto = new StopPaymemtReqDto();
    private PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
    private LookUp cpdIdStatusLookUp = new LookUp();
    private TbAcChequebookleafDetDto chequebookleafDetDto = new TbAcChequebookleafDetDto();

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.ui.model.AbstractFormModel#saveForm()
     */
    @Override
    public boolean saveForm() {
        boolean status = false;
        TbAcChequebookleafDetEntity entity = null;
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Date date = new Date();
        String clientIpAddress = getClientIpAddress();
        TbAcChequebookleafDetDto detDto = getChequebookleafDetDto();
        getStopPaymemtReqDto().getPaymentEntryDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        detDto.setCpdIdstatus(getCpdIdStatusLookUp().getLookUpId());
        detDto.setChequebookDetid(stopPaymemtReqDto.getPaymentEntryDto().getInstrumentNumber());
        detDto.setLgIpMac(stopPaymemtReqDto.getPaymentEntryDto().getLgIpMacAddress());

        if (detDto.getChequebookDetid() != null) {
            detDto.setUpdatedBy(empId);
            detDto.setUpdatedDate(date);
            detDto.setLgIpMacUpd(clientIpAddress);
            stopPaymentService.updateStopPaymentEntry(detDto.getChequebookDetid(), detDto.getCpdIdstatus(),
                    detDto.getStopPayRemark(), detDto.getStoppayDate(), detDto.getUpdatedBy(), detDto.getUpdatedDate(),
                    detDto.getLgIpMacUpd(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            entity = new TbAcChequebookleafDetEntity();
            entity = chequebookleafDetJpaRepository.findById(detDto.getChequebookDetid());
            TbAcChequebookleafDetHistoryEntity history = new TbAcChequebookleafDetHistoryEntity();
            history.sethSataus(MainetConstants.FlagA);
            auditService.createHistory(entity, history);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("Stop payment entry updated successfully"));
            return true;

        }
        return status;

    }

    public StopPaymemtReqDto getStopPaymemtReqDto() {
        return stopPaymemtReqDto;
    }

    public void setStopPaymemtReqDto(StopPaymemtReqDto stopPaymemtReqDto) {
        this.stopPaymemtReqDto = stopPaymemtReqDto;
    }

    public PaymentEntryDto getPaymentEntryDto() {
        return paymentEntryDto;
    }

    public void setPaymentEntryDto(PaymentEntryDto paymentEntryDto) {
        this.paymentEntryDto = paymentEntryDto;
    }

    public LookUp getCpdIdStatusLookUp() {
        return cpdIdStatusLookUp;
    }

    public void setCpdIdStatusLookUp(LookUp cpdIdStatusLookUp) {
        this.cpdIdStatusLookUp = cpdIdStatusLookUp;
    }

    public TbAcChequebookleafDetDto getChequebookleafDetDto() {
        return chequebookleafDetDto;
    }

    public void setChequebookleafDetDto(TbAcChequebookleafDetDto chequebookleafDetDto) {
        this.chequebookleafDetDto = chequebookleafDetDto;
    }

}
