package com.abm.mainet.common.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;

/**
 * @author Vivek.Kumar
 * @since 15-Dec-2015
 */
public interface CommonService {

    /**
     * @param applicationData
     * @param applicantDetailsDto
     */
    void initiateWorkflowfreeService(ApplicationMetadata applicationData, ApplicantDetailDTO applicantDetailsDto);

    /**
     * this method can be used to get Title description by passing titleId
     * @param titleId
     * @param org
     * @return
     */
    String findTitleDesc(long titleId, Organisation org);

    public ApplicantDetailDTO populateApplicantInfo(ApplicantDetailDTO dto, final TbCfcApplicationMstEntity cfcEntity);

    /**
     * @param applicationId
     * @param serviceId
     * @return
     */
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(
            Long applicationId, Long serviceId, Long orgId);

    List<String> findServiceActionUrl(final long applicationId, long orgId);

    Map<Long, String> getAllPgBank(long orgId) throws FrameworkException;

    PaymentTransactionMas getOnlineTransactionMasByTrackId(Long trackId);

    /**
     * @param cbBankName
     * @param orgId
     * @return ViewBankMaster
     */
    PGBankDetail getBankDetailByBankName(final String cbBankName, final Long orgId) ;
    
    /**
     * @param PgId
     * @param orgId
     * @return List<PGBankParameter>
     */
    List<PGBankParameter> getMerchantMasterParamByPgId(Long PgId, long orgId);

	Long getcsIdnByConnectionNo(String connectionNo, Long orgId) throws ClassNotFoundException, LinkageError;
	
	Map<Long, String> getAllPgBankByDeptCode(long orgId, String deptCode) throws FrameworkException;
}
