package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.service.INoDuesCertificateService;
import com.abm.mainet.water.ui.validator.WaterNoDuesCertificateValidator;

@Component
@Scope("session")
public class NoDuesCertificateModel extends AbstractFormModel {

    private static final long serialVersionUID = -1112115377545505440L;

    @Resource
    private INoDuesCertificateService noDuesCertificateService;

    private NoDuesCertificateReqDTO reqDTO = new NoDuesCertificateReqDTO();
    private NoDuesCertificateRespDTO responseDTO = new NoDuesCertificateRespDTO();
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    Map<Long, Double> chargesMap = new HashMap<>();
    private Double charges = 0.0d;
    private String free =MainetConstants.PAYMENT.FREE;
    private Long orgId;
    private Long deptId;
    private Long langId;
    private boolean isDocumentSubmitted;
    private List<FinYearDTO> finYear;
    private String otp;
    private String userOtp;
    private NoDueCerticateDTO nodueCertiDTO = new NoDueCerticateDTO();
    private String checkListApplFlag;
    private Long connectionSize;
    private String checkListFlag;
    
    
    
	/**
	 * @return the checkListFlag
	 */
	public String getCheckListFlag() {
		return checkListFlag;
	}

	/**
	 * @param checkListFlag the checkListFlag to set
	 */
	public void setCheckListFlag(String checkListFlag) {
		this.checkListFlag = checkListFlag;
	}

	/**
	 * @return the connectionSize
	 */
	public Long getConnectionSize() {
		return connectionSize;
	}

	/**
	 * @param connectionSize the connectionSize to set
	 */
	public void setConnectionSize(Long connectionSize) {
		this.connectionSize = connectionSize;
	}

	/**
	 * @return the checkListApplFlag
	 */
	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	/**
	 * @param checkListApplFlag the checkListApplFlag to set
	 */
	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public NoDueCerticateDTO getNodueCertiDTO() {
		return nodueCertiDTO;
	}

	public void setNodueCertiDTO(NoDueCerticateDTO nodueCertiDTO) {
		this.nodueCertiDTO = nodueCertiDTO;
	}

	public String getUserOtp() {
		return userOtp;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
     * @return the chargesMap
     */
    public Map<Long, Double> getChargesMap() {

        return chargesMap;
    }

    /**
     * @param chargesMap the chargesMap to set
     */
    public void setChargesMap(final Map<Long, Double> chargesMap) {

        this.chargesMap = chargesMap;
    }

    public List<FinYearDTO> getFinYear() {

        return finYear;
    }

    /**
     * @param finYear the finYear to set
     */
    public void setFinYear(final List<FinYearDTO> finYear) {

        this.finYear = finYear;
    }

    /**
     * @return the reqDTO
     */
    public NoDuesCertificateReqDTO getReqDTO() {

        return reqDTO;
    }

    /**
     * @param reqDTO the reqDTO to set
     */
    public void setReqDTO(final NoDuesCertificateReqDTO reqDTO) {

        this.reqDTO = reqDTO;
    }

    /**
     * @return the responseDTO
     */
    public NoDuesCertificateRespDTO getResponseDTO() {

        return responseDTO;
    }

    /**
     * @param responseDTO the responseDTO to set
     */
    public void setResponseDTO(final NoDuesCertificateRespDTO responseDTO) {

        this.responseDTO = responseDTO;
    }

    /**
     * @return the checkList
     */
    public List<DocumentDetailsVO> getCheckList() {

        return checkList;
    }

    /**
     * @param checkList the checkList to set
     */
    public void setCheckList(final List<DocumentDetailsVO> checkList) {

        this.checkList = checkList;
    }

    /**
     * @return the charges
     */
    public Double getCharges() {

        return charges;
    }

    /**
     * @param charges the charges to set
     */
    public void setCharges(final Double charges) {

        this.charges = charges;
    }

    /**
     * @return the isDocumentSubmitted
     */
    public boolean isDocumentSubmitted() {

        return isDocumentSubmitted;
    }

    /**
     * @param isDocumentSubmitted the isDocumentSubmitted to set
     */
    public void setDocumentSubmitted(final boolean isDocumentSubmitted) {

        this.isDocumentSubmitted = isDocumentSubmitted;
    }

    /**
     * @return the free
     */
    public String getFree() {

        return free;
    }

    /**
     * @param free the free to set
     */
    public void setFree(final String free) {

        this.free = free;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {

        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {

        this.orgId = orgId;
    }

    /**
     * @return the deptId
     */
    public Long getDeptId() {

        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final Long deptId) {

        this.deptId = deptId;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {

        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {

        this.langId = langId;
    }

    public boolean validateInputs() {

        validateBean(this, WaterNoDuesCertificateValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    public boolean setPaymentDeatil(final CommonChallanDTO offline) {

        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        final boolean setFlag = noDuesCertificateService.setPaymentDetail(offline, this, UserSession.getCurrent());
        return setFlag;
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {

        switch (parentCode) {

        case MainetConstants.NewWaterServiceConstants.WWZ:
            return MainetConstants.NewWaterServiceConstants.WR_ZONE;

        default:
            return null;

        }
    }

    @Override
    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
        noDuesCertificateService.setPayRequestDTO(payURequestDTO, this, UserSession.getCurrent());
    }
}
