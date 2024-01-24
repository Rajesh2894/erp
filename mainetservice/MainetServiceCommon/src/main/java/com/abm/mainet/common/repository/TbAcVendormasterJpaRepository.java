package com.abm.mainet.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbAcVendormasterEntity;

/**
 * Repository : TbAcVendormaster.
 */
@Repository
public interface TbAcVendormasterJpaRepository extends PagingAndSortingRepository<TbAcVendormasterEntity, Long> {

    @Query("select vem from TbAcVendormasterEntity vem where vem.orgid = :orgid ")
    List<TbAcVendormasterEntity> getVendorList(@Param("orgid") Long orgid);

    @Query("select vem from TbAcVendormasterEntity vem where vem.vmVendorid not in (select sc.vmVendorid from  AccountHeadSecondaryAccountCodeMasterEntity sc where sc.orgid =vem.orgid) and vem.orgid = :orgid ")
    List<TbAcVendormasterEntity> getVendorListsechead(@Param("orgid") Long orgid);

    @Query("select em from TbAcVendormasterEntity em  where em.orgid=:orgid and em.vmPanNumber=:vmPanNumber")
    List<TbAcVendormasterEntity> getVendorvmPanNumber(@Param("vmPanNumber") String vmPanNumber, @Param("orgid") Long orgid);

    @Query("select e from TbAcVendormasterEntity e  where e.orgid=:orgid and lower(e.vmVendorname) LIKE lower(:vmVendorname)")
    List<TbAcVendormasterEntity> getVendorData1(@Param("vmVendorname") String vmVendorname, @Param("orgid") Long orgid);

    @Query("SELECT DISTINCT vm FROM TbAcVendormasterEntity vm, AccountHeadSecondaryAccountCodeMasterEntity sm"
            + " WHERE vm.orgid =:orgId AND vm.vmCpdStatus =:vendorStatus "
            + " AND vm.vmVendorid = sm.vmVendorid AND vm.orgid = sm.orgid")
    List<TbAcVendormasterEntity> getActiveVendors(@Param("orgId") Long orgId, @Param("vendorStatus") Long vendorStatus);

    /**
     * @param orgId
     * @param vendorId
     * @return
     */
    @Query("select v.cpdVendortype,v.cpdVendorSubType,v.vmPanNumber from TbAcVendormasterEntity v where v.orgid =:orgId and v.vmVendorid =:vendorId")
    List<Object[]> getVendorDetailsByVendorId(@Param("orgId") Long orgId, @Param("vendorId") Long vendorId);

    @Query("select v.mobileNo,v.emailId from TbAcVendormasterEntity v where v.vmVendorid =:vendorId and v.orgid =:orgId")
    List<Object[]> getVendorPhoneNoAndEmailId(@Param("vendorId") Long vendorId, @Param("orgId") Long orgId);

    @Query("select v.vmVendorname from TbAcVendormasterEntity v where v.vmVendorid =:vendorId and v.orgid =:orgId")
    String getVendorNameById(@Param("vendorId") Long vendorId, @Param("orgId") Long orgId);

    @Query("select v from TbAcVendormasterEntity v where v.vmPanNumber =:panNo and v.orgid =:orgId")
    List<TbAcVendormasterEntity> getVendorByPanNo(@Param("panNo") String panNo, @Param("orgId") Long orgId);

    @Query("select v from TbAcVendormasterEntity v where v.mobileNo =:mobileNo and v.orgid =:orgId")
    List<TbAcVendormasterEntity> getVendorByMobileNo(@Param("mobileNo") String mobileNo, @Param("orgId") Long orgId);

    @Query("select v from TbAcVendormasterEntity v where v.vmUidNo =:uidNo and v.orgid =:orgId")
    List<TbAcVendormasterEntity> getVendorByUidNo(@Param("uidNo") Long uidNo, @Param("orgId") Long orgId);

    @Query("select v from TbAcVendormasterEntity v where v.tinNumber =:vat and v.orgid =:orgId")
    List<TbAcVendormasterEntity> getVendorByVat(@Param("vat") String tinNumber, @Param("orgId") Long orgId);

    @Query("select v from TbAcVendormasterEntity v where v.vmGstNo =:gst and v.orgid =:orgId")
    List<TbAcVendormasterEntity> getVendorByGst(@Param("gst") String gstNumber, @Param("orgId") Long orgId);

    @Query("select v from TbAcVendormasterEntity v where v.orgid =:orgId")
    List<TbAcVendormasterEntity> getVendorMasterData(@Param("orgId") Long orgId);
    
    
    
    @Query("SELECT DISTINCT vm FROM TbAcVendormasterEntity vm, AccountHeadSecondaryAccountCodeMasterEntity sm"
            + " WHERE vm.orgid =:orgId AND vm.vmCpdStatus =:vendorStatus "
            + " AND vm.vmVendorid = sm.vmVendorid AND vm.orgid = sm.orgid AND sm.sacStatusCpdId =:sacStatus")
    List<TbAcVendormasterEntity> getActiveStatusVendorsAndSacAcHead(@Param("orgId") Long orgId,
            @Param("vendorStatus") Long vendorStatus, @Param("sacStatus") Long sacStatus);

    /**
     * @param orgId
     * @param vendorId
     * @return
     */
    @Query("select v from TbAcVendormasterEntity v where v.orgid =:orgId and v.vmVendorid =:vendorId")
    List<TbAcVendormasterEntity> getVendorCodeByVendorId(@Param("orgId") Long orgId, @Param("vendorId") Long vendorId);

    @Query(value = "SELECT \n" +
            "  DISTINCT(VM.VM_VENDORID),\n" +
            "    BM.VM_VENDORNAME,\n" +
            "    BD.SAC_HEAD_ID,\n" +
            "    BD.DEDUCTION_BALAMT,\n" +
            "    BD.BDH_ID,\n" +
            "    BD.BM_ID, \n" +
            "	 BM.BM_BILLNO,\n" +
            "    BM.BM_ENTRYDATE,  \n" +
            "    BM.BM_BILLTYPE_CPD_ID \n" +
            "FROM\n" +
            "    TB_VENDORMASTER VM,\n" +
            "    TB_AC_BILL_MAS BM,\n" +
            "    TB_AC_BILL_EXP_DETAIL BE,\n" +
            "    TB_AC_BILL_DEDUCTION_DETAIL BD,\n" +
            "    TB_AC_PAYMENT_MAS PM,\n" +
            "    TB_AC_PAYMENT_DET PD,\n" +
            "    TB_AC_BANK_TDS_DETAILS TD\n" +
            "WHERE VM.VM_VENDORID=BM.VM_VENDORID\n" +
            "        AND BM.BM_ID=BE.BM_ID\n" +
            "        AND BM.BM_ID=BD.BM_ID\n" +
            "        AND BD.BM_ID=PD.BM_ID \n" +
            "        AND PM.PAYMENT_ID = PD.PAYMENT_ID        \n" +
            "        AND BD.SAC_HEAD_ID = TD.SAC_HEAD_ID\n" +
            "        AND PM.ORGID = PD.ORGID\n" +
            "        AND VM.ORGID = BD.ORGID\n" +
            "        AND BD.ORGID = BM.ORGID\n" +
            "        AND BD.SAC_HEAD_ID =:sacHeadid\n" +
            "        AND PM.ORGID =:orgId\n" +
            "        AND PM.PAYMENT_DATE BETWEEN :fromDate AND :toDate\n" +
            "        AND PM.PAYMENT_DEL_FLAG IS NULL \n" +
            "        AND BD.DEDUCTION_BALAMT > 0 \n" +
            "        ORDER BY BM.BM_ENTRYDATE", nativeQuery = true)
    List<Object[]> getActiveVendorsDetails(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("sacHeadid") Long sacHeadid);

    @Query(value = "SELECT E.VM_VENDORID1,\n" +
            "       E.VM_VENDORNAME2,\n" +
            "       E.BILL_PAYMENT_AMT,\n" +
            "       E.DEDUCTION_AMT,\n" +
            "       F.DEDUCTION_PAYMENT_AMT,\n" +
            "       F.PAYMENT_NO,\n" +
            "       F.PAYMENT_DATE,    \n" +
            "       E.BM_ENTRYDATE,       \n" +
            "       E.BCH_CHARGES_AMT, \n" +
            "       E.SAC_HEAD_ID,\n" +
            "       E.BM_ID1\n" +
            "  FROM (SELECT C.VM_VENDORID1,\n" +
            "               C.BM_ENTRYDATE,\n" +
            "               C.BM_ID1,\n" +
            "               C.BCH_CHARGES_AMT,\n" +
            "               C.DEDUCTION_AMT,\n" +
            "               C.BDH_ID,\n" +
            "               C.BILL_PAYMENT_AMT,\n" +
            "               D.VM_VENDORNAME2,\n" +
            "               C.SAC_HEAD_ID\n" +
            "          FROM (SELECT A.*, B.*\n" +
            "                  FROM (SELECT BM.VM_VENDORID AS VM_VENDORID1,\n" +
            "                               BM.VM_VENDORNAME VM_VENDORNAME1,\n" +
            "                               BM.BM_ID AS BM_ID1,\n" +
            "                               BM.BM_ENTRYDATE,\n" +
            "                               SUM(BE.BCH_CHARGES_AMT) AS BCH_CHARGES_AMT,\n" +
            "                               SUM(BD.DEDUCTION_AMT) AS DEDUCTION_AMT,\n" +
            "                               BDH_ID,\n" +
            "                               BM.ORGID AS ORGID1,\n" +
            "                               BD.SAC_HEAD_ID\n" +
            "                          FROM TB_AC_BILL_MAS              BM,\n" +
            "                               TB_AC_BILL_EXP_DETAIL       BE,\n" +
            "                               TB_AC_BILL_DEDUCTION_DETAIL BD\n" +
            "                         WHERE BM.BM_ID = BE.BM_ID\n" +
            "                           AND BM.BM_ID = BD.BM_ID\n" +
            "                           AND BM.BM_ENTRYDATE BETWEEN :fromDate AND :toDate\n" +
            "                               AND BM.ORGID=:orgId\n" +
            "                               AND BD.SAC_HEAD_ID=:sacHeadid\n" +
            "                           AND BM.BM_DEL_FLAG IS NULL \n" +
            "                         GROUP BY BM.VM_VENDORID,\n" +
            "                                  BM.BM_ID,\n" +
            "                                  BM.BM_ENTRYDATE,\n" +
            "                                  BM.VM_VENDORNAME,\n" +
            "                                  BM.ORGID,\n" +
            "                                  BDH_ID,\n" +
            "                                  BD.SAC_HEAD_ID\n" +
            "                         ORDER BY BM.BM_ENTRYDATE) A\n" +
            "                  LEFT JOIN (SELECT BM_ID,\n" +
            "                                   PD.ORGID AS ORGID,\n" +
            "                                   SUM(PAYMENT_AMT) AS BILL_PAYMENT_AMT\n" +
            "                              FROM TB_AC_PAYMENT_MAS PM, TB_AC_PAYMENT_DET PD\n" +
            "                             WHERE PM.PAYMENT_DEL_FLAG IS NULL\n" +
            "                               AND PM.PAYMENT_ID = PD.PAYMENT_ID\n" +
            "                             GROUP BY BM_ID, PD.ORGID) B\n" +
            "                    ON A.BM_ID1 = B.BM_ID\n" +
            "                   AND A.ORGID1 = B.ORGID) C\n" +
            "          LEFT JOIN (SELECT VM_VENDORID,\n" +
            "                           VM_VENDORNAME AS VM_VENDORNAME2,\n" +
            "                           ORGID\n" +
            "                      FROM TB_VENDORMASTER) D\n" +
            "            ON C.VM_VENDORID1 = D.VM_VENDORID) E\n" +
            "  LEFT JOIN (SELECT PAYMENT_NO,\n" +
            "  PAYMENT_DATE,\n" +
            "                    BDH_ID,\n" +
            "                    SUM(PAYMENT_AMT) AS DEDUCTION_PAYMENT_AMT\n" +
            "               FROM TB_AC_PAYMENT_MAS PM, TB_AC_PAYMENT_DET PD\n" +
            "              WHERE PM.PAYMENT_DEL_FLAG IS NULL\n" +
            "                AND PM.PAYMENT_ID = PD.PAYMENT_ID\n" +
            "                AND BDH_ID IS NOT NULL\n" +
            "              GROUP BY PAYMENT_NO,PAYMENT_DATE, BDH_ID) F\n" +
            "    ON E.BDH_ID = F.BDH_ID ", nativeQuery = true)
    List<Object[]> queryForDeductionRegisterData(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("sacHeadid") Long sacHeadid);

    @Query("SELECT vm FROM TbAcVendormasterEntity vm WHERE vm.orgid =:orgId AND vm.vmCpdStatus =:vendorStatus")
    List<TbAcVendormasterEntity> getAllActiveVendors(@Param("orgId") Long orgId, @Param("vendorStatus") Long vendorStatus);

}
