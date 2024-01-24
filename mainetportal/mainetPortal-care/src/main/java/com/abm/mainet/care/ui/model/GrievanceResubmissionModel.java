package com.abm.mainet.care.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.ComplaintRegistrationAcknowledgementDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.ActionDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@SuppressWarnings("serial")
@Component
@Scope("session")
public class GrievanceResubmissionModel extends AbstractFormModel implements Serializable {

    private ComplaintRegistrationAcknowledgementDTO complaintAcknowledgementModel;
    private ActionDTO action = new ActionDTO();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    public ComplaintRegistrationAcknowledgementDTO getComplaintAcknowledgementModel() {
        return complaintAcknowledgementModel;
    }

    public void setComplaintAcknowledgementModel(ComplaintRegistrationAcknowledgementDTO complaintAcknowledgementModel) {
        this.complaintAcknowledgementModel = complaintAcknowledgementModel;
    }

    public ActionDTO getAction() {
        return action;
    }

    public void setAction(ActionDTO action) {
        this.action = action;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public long getEmplType() {
        long emplType = 0;
        Employee emp = UserSession.getCurrent().getEmployee();
        if (emp != null)
            if (emp.getEmploginname().equalsIgnoreCase("NOUSER")) {

                List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.NEC,
                        UserSession.getCurrent().getOrganisation());
                Optional<LookUp> lookup = lookUps.stream().filter(l -> l.getLookUpCode().equals(MainetConstants.NEC.CITIZEN))
                        .findFirst();
                if (lookup.isPresent())
                    emplType = lookup.get().getLookUpId();

            } else {
                emplType = emp.getEmplType();
            }

        return emplType;
    }

}
