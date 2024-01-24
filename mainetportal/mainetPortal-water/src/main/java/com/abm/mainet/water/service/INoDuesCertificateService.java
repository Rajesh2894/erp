package com.abm.mainet.water.service;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.FinYearDTORespDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;

public interface INoDuesCertificateService {

    NoDuesCertificateReqDTO getConnectionDetail(UserSession usersession, String consumerNo,
            NoDuesCertificateReqDTO noDuesCertificateReqDTO, NoDuesCertificateModel noDuesCertificateModel);

    NoDuesCertificateRespDTO saveForm(NoDuesCertificateReqDTO certificateReqDTO);

    FinYearDTORespDTO getFinancialYear(RequestDTO dto);

    void populateCheckListModel(NoDuesCertificateModel model, CheckListModel checklistModel);

    WaterRateMaster populateChargeModel(NoDuesCertificateModel model, WaterRateMaster chargeModel);

    void setCommonField(Organisation organisation, NoDuesCertificateModel noDuesCertificateModel);

    boolean setPaymentDetail(CommonChallanDTO offline, NoDuesCertificateModel noDuesCertificateModel, UserSession userSession);

    void setPayRequestDTO(PaymentRequestDTO payURequestDTO, NoDuesCertificateModel noDuesCertificateModel,
            UserSession userSession);
    
    NoDueCerticateDTO getNoDuesApplicationData(NoDuesCertificateReqDTO certificateReqDTO);

}
