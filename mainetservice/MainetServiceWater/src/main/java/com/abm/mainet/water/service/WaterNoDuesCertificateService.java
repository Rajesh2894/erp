package com.abm.mainet.water.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.dto.WaterNoDueDto;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.WaterNoDuesEntity;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface WaterNoDuesCertificateService {

    /**
     * @param apmApplicationId
     * @param orgid
     * @return NoDuesCertificateRespDTO
     */
    NoDueCerticateDTO getNoDuesApplicationData(long apmApplicationId, long orgid);

    /**
     * @param requestDTO
     * @return NoDuesCertificateRespDTO
     */
    NoDuesCertificateRespDTO populateNoDuesCertificateResp(NoDuesCertificateReqDTO requestDTO);

    /**
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return WardZoneBlockDTO
     */
    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

    /**
     * @param requestDTO
     * @return
     */
    NoDuesCertificateRespDTO saveForm(NoDuesCertificateReqDTO requestDTO);

    /**
     * @param requestDTO
     * @param formData
     * 
     * @return List<WebServiceResponseDTO>
     */
    List<WebServiceResponseDTO> validation(NoDuesCertificateReqDTO requestDTO, String formData);

    /**
     * @param requestDTO
     * @param formData
     * @return
     */
    List<WebServiceResponseDTO> validationFromData(NoDuesCertificateReqDTO requestDTO, String formData);

    /**
     * @param nodueCertiDTO
     * @return
     */
    Boolean saveExeFormData(NoDueCerticateDTO nodueCertiDTO);

    /**
     * @param usersession
     * @param consumerNo
     * @param noDuesCertificateReqDTO
     * @param noDuesCertificateModel
     * @return NoDuesCertificateReqDTO
     */
    NoDuesCertificateReqDTO getConnectionDetailForNoDuesCer(UserSession usersession, String consumerNo,
            NoDuesCertificateReqDTO noDuesCertificateReqDTO, NoDuesCertificateModel noDuesCertificateModel);

    /**
     * @param organisation
     * @param noDuesCertificateModel
     */
    void setCommonField(Organisation organisation, NoDuesCertificateModel noDuesCertificateModel);

    /**
     * @param offline
     * @param noDuesCertificateModel
     * @param userSession
     * @return
     */
    boolean populateNoDuesPaymentDetails(CommonChallanDTO offline, NoDuesCertificateModel noDuesCertificateModel,
            UserSession userSession);

    void initiateWorkflowForFreeService(NoDuesCertificateReqDTO reqDTO);

    /**
     * @param docs
     * @return List<DocumentDetailsVO>
     */
    int updateOTPServiceForWater(String mobileNo, String connectionNo, String otp, Date updatedDate);

    RequestDTO doOTPVerificationServiceForWater(String mobileNo, String connectionNo);

	WaterNoDuesEntity getNoDuesDetailsByApplId(Long applicationId, Long orgId);

	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	WaterNoDueDto getNoDuesApplicationDataForScrutiny(Long apmApplicationId, Long orgid);

	NoDuesCertificateRespDTO getWaterDuesByPropNoNConnNo(NoDuesCertificateReqDTO requestDTO);

}
