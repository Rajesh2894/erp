package com.abm.mainet.socialsecurity.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.socialsecurity.domain.BeneficiaryPaymentDetailEntity;

@Repository
public interface IBeneficiaryPaymentOrderRepository extends PagingAndSortingRepository<BeneficiaryPaymentDetailEntity, Long> {

    
      
    
    
    
    
    /*
     * @Query(value = "SELECT a.SAPI_NAME," + "a.SAPI_ID," + "a.SAPI_BANKID," + "a.SAPI_ACCOUNTID," + "c.SDSCHE_AMT," +
     * "b.SDSCH_SER_ID," + "c.SDSCHE_PAYSCH,\r\n" + "a.APM_APPLICATION_ID,\r\n" + "a.BENEFICIARY_NUMBER \r \n" +
     * "FROM TB_SWD_SCHEME_APPLICATION a, \r\n" + "tb_swd_scheme_mast b, \r \n" + "TB_SWD_SCHEME_ElIGIBILITY c \r \n" +
     * "WHERE  a.SDSCH_SER_ID = b.SDSCH_SER_ID and\r\n" + "b.SDSCH_ID = c.SDSCH_ID and\r\n" + "a.SDSCH_SER_ID =:serviceId and\r\n"
     * + "c.SDSCHE_PAYSCH =:paymentscheId and\r\n" + "a.ORGID=:orgId and\r\n" + "a.SAPI_STATUS = 'A'\r\n" +
     * "and a.APM_APPLICATION_ID NOT IN \r\n" + " (SELECT j.APM_APPLICATION_ID from tb_swd_rtgs_payment j)",nativeQuery = true)
     * List<Object[]>filterSearchDatas(@Param("serviceId") Long serviceId, @Param("paymentscheId") Long
     * paymentscheId,@Param("orgId") Long orgId);
     */
     
     
	 @Query(value ="SELECT a.SAPI_NAME,\r\n" + 
     		"       a.SAPI_ID,\r\n" + 
     		"       a.SAPI_BANKID,\r\n" + 
     		"       a.SAPI_ACCOUNTID,\r\n" + 
     		"       x.SDSCHE_AMT,\r\n" + 
     		"       b.SDSCH_SER_ID,\r\n" + 
     		"       x.SDSCHE_PAYSCH,\r\n" + 
     		"       a.APM_APPLICATION_ID,\r\n" + 
     		"       a.BENEFICIARY_NUMBER,\r\n" + 
     		"       a.SAPI_LAST_LIFECERTDATE\r\n" + 
     		"  FROM TB_SWD_SCHEME_APPLICATION a,\r\n" + 
     		"       tb_swd_scheme_mast b,\r\n" + 
     		"       (SELECT   DISTINCT SDSCHE_GRID ,c.SDSCHE_AMT, c.SDSCHE_PAYSCH, c.SDSCH_ID\r\n" + 
     		"          FROM TB_SWD_SCHEME_ElIGIBILITY c, TB_SWD_SCHEME_APPLICATION z\r\n" + 
     		"         WHERE     c.SDSCHE_PAYSCH IS NOT NULL\r\n" + 
     		"               AND c.SDSCHE_AMT IS NOT NULL and c.SDSCHE_GRID = z.SAPI_BRANCH_ID) x\r\n" + 
     		" WHERE     a.SDSCH_SER_ID = b.SDSCH_SER_ID\r\n" + 
     		"       AND x.SDSCH_ID = b.SDSCH_ID\r\n" + 
     		"       AND a.SDSCH_SER_ID =\r\n" + 
     		"              (CASE\r\n" + 
     		"                  WHEN COALESCE(:serviceId, 0) = 0 THEN COALESCE(a.SDSCH_SER_ID, 0)\r\n" + 
     		"                  ELSE COALESCE(:serviceId, 0)\r\n" + 
     		"               END)\r\n" + 
     		"       AND a.ORGID =:orgId\r\n" + 
     		"       AND a.SAPI_STATUS = 'A'\r\n" + 
     		"       AND COALESCE(a.APM_APPLICATION_ID, 0) NOT IN\r\n" + 
     		"              (SELECT j.APM_APPLICATION_ID\r\n" + 
     		"                 FROM tb_swd_rtgs_payment j)",nativeQuery = true) 
            List<Object[]>filterSearchDatas(@Param("serviceId") Long serviceId, @Param("orgId") Long orgId);
            
            @Query(value ="SELECT a.SAPI_NAME,\r\n" + 
             		"       a.SAPI_ID,\r\n" + 
             		"       a.SAPI_BANKID,\r\n" + 
             		"       a.SAPI_ACCOUNTID,\r\n" + 
             		"       x.SDSCHE_AMT,\r\n" + 
             		"       b.SDSCH_SER_ID,\r\n" + 
             		"       x.SDSCHE_PAYSCH,\r\n" + 
             		"       a.APM_APPLICATION_ID,\r\n" + 
             		"       a.BENEFICIARY_NUMBER,\r\n" + 
             		"       a.SAPI_LAST_LIFECERTDATE\r\n" + 
             		"  FROM TB_SWD_SCHEME_APPLICATION a,\r\n" + 
             		"       tb_swd_scheme_mast b,\r\n" + 
             		"       (SELECT   DISTINCT SDSCHE_GRID ,c.SDSCHE_AMT, c.SDSCHE_PAYSCH, c.SDSCH_ID\r\n" + 
             		"          FROM TB_SWD_SCHEME_ElIGIBILITY c, TB_SWD_SCHEME_APPLICATION z\r\n" + 
             		"         WHERE     c.SDSCHE_PAYSCH IS NOT NULL\r\n" + 
             		"               AND c.SDSCHE_AMT IS NOT NULL and c.SDSCHE_GRID = z.SAPI_BRANCH_ID) x\r\n" + 
             		" WHERE     a.SDSCH_SER_ID = b.SDSCH_SER_ID\r\n" + 
             		"       AND x.SDSCH_ID = b.SDSCH_ID\r\n" + 
             		"       AND a.SDSCH_SER_ID =\r\n" + 
             		"              (CASE\r\n" + 
             		"                  WHEN COALESCE(:serviceId, 0) = 0 THEN COALESCE(a.SDSCH_SER_ID, 0)\r\n" + 
             		"                  ELSE COALESCE(:serviceId, 0)\r\n" + 
             		"               END)\r\n" + 
             		"       AND coalesce(a.SWD_WARD1,0) =\r\n" + 
             		"              (CASE\r\n" + 
             		"                  WHEN COALESCE(:swdward1, 0) = 0 THEN COALESCE(a.SWD_WARD1, 0)\r\n" + 
             		"                  ELSE COALESCE(:swdward1, 0)\r\n" + 
             		"               END)\r\n" + 
             		"       AND coalesce(a.SWD_WARD2,0) =\r\n" + 
             		"              (CASE\r\n" + 
             		"                  WHEN COALESCE(:swdward2, 0) = 0 THEN COALESCE(a.SWD_WARD2, 0)\r\n" + 
             		"                  ELSE COALESCE(:swdward2, 0)\r\n" + 
             		"               END)\r\n" +
             		"       AND coalesce(a.SDSCH_SUB_SER_ID,0) =\r\n" + 
             		"              (CASE\r\n" + 
             		"                  WHEN COALESCE(:subServiceId, 0) = 0 THEN COALESCE(a.SDSCH_SUB_SER_ID, 0)\r\n" + 
             		"                  ELSE COALESCE(:subServiceId, 0)\r\n" + 
             		"               END)\r\n" +
             		"       AND a.ORGID =:orgId\r\n" + 
             		"       AND a.SAPI_STATUS = 'A'\r\n" + 
             		"       AND COALESCE(a.APM_APPLICATION_ID, 0) NOT IN\r\n" + 
             		"              (SELECT j.APM_APPLICATION_ID\r\n" + 
             		"                 FROM tb_swd_rtgs_payment j)",nativeQuery = true) 
                    List<Object[]>filterSearchData(@Param("serviceId") Long serviceId, @Param("orgId") Long orgId,@Param("swdward1") String swdward1,@Param("swdward2") String swdward2,@Param("subServiceId") Long subServiceId);
            
            
            @Query("from BeneficiaryPaymentDetailEntity bpe where bpe.orgId=:orgId and bpe.workOrderNumber=:applicationId")
            List<BeneficiaryPaymentDetailEntity> getViewDataFromRtgsPayment(@Param("orgId") Long orgId,@Param("applicationId") String applicationId);

            @Modifying
            @Query("update BeneficiaryPaymentDetailEntity bpe set bpe.rtgsStatus=:status,bpe.updatedDate=:date where bpe.workOrderNumber=:applicationId and bpe.orgId=:orgId")
            void updateApprovalFlag(@Param("applicationId") String applicationId, @Param("orgId") Long orgId, @Param("status") String status,@Param("date") Date date);      
            
            

    @Modifying
    @Query("update BeneficiaryPaymentDetailEntity bpe set bpe.rtgsPostst=:accStatus,bpe.rtgsBillno=:billNo  where bpe.rtgsTransId=:rtgsTransId and bpe.orgId=:orgId")
    void updateAccountStatusAndBillNumber(@Param("billNo") String billNo, @Param("orgId") Long orgId,
            @Param("rtgsTransId") Long rtgsTransId, @Param("accStatus") String accStatus);

    
    
    @Query("Select ss.lastDateofLifeCerti FROM  SocialSecurityApplicationForm ss   where ss.beneficiarynumber=:beneficiaryNo and ss.orgId=:orgId")
    Object getCertificateDate(@Param("beneficiaryNo") String beneficiaryNo, @Param("orgId") Long orgId);
    
    @Query("FROM BeneficiaryPaymentDetailEntity bpe where bpe.orgId=:orgId AND bpe.beneficiaryNumber=:beneficiaryNo")
    BeneficiaryPaymentDetailEntity getRtgsData(@Param("beneficiaryNo") String beneficiaryNo, @Param("orgId") Long orgId);
    
    
    @Query("SELECT MAX(e.workOrdrerDate), e.paymentScheduleId\r\n" + 
    		"  FROM BeneficiaryPaymentDetailEntity e\r\n" + 
    		" WHERE e.orgId=:orgId AND e.beneficiaryNumber=:beneficiaryNo" + 
    		" GROUP BY e.paymentScheduleId")
    List<Object[]> getRtgsDatagetRtgsData(@Param("beneficiaryNo") String beneficiaryNo, @Param("orgId") Long orgId);
    
    
    
    @Query("SELECT COUNT(e.beneficiaryNumber) from BeneficiaryPaymentDetailEntity e where e.beneficiaryNumber=:beneficiaryNo AND e.orgId=:orgId AND MONTH(e.workOrdrerDate)=:month")
    Object getBiMonthlyCount(@Param("beneficiaryNo") String beneficiaryNo, @Param("orgId") Long orgId, @Param("month") int month);
    
    
}
