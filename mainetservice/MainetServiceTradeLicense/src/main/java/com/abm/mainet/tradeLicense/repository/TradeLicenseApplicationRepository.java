package com.abm.mainet.tradeLicense.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Repository
public interface TradeLicenseApplicationRepository extends CrudRepository<TbMlTradeMast, Long> {

	/**
	 * used to get Trade License With All Details By Application Id
	 * 
	 * @param applicationId
	 */
	@Query("select p from TbMlTradeMast p where p.apmApplicationId =:applicationId")
	TbMlTradeMast getTradeLicenseWithAllDetailsByApplicationId(@Param("applicationId") Long applicationId);

	/**
	 * used to get Trade License By OldLiscenseNo
	 * 
	 * @param oldLicenseNo
	 * @param orgId
	 * @return
	 */
	@Query("select p from TbMlTradeMast p where p.trdOldlicno =:oldLicenseNo and p.orgid =:orgId")
	TbMlTradeMast getTradeLicenseByOldLiscenseNo(@Param("oldLicenseNo") String oldLicenseNo,
			@Param("orgId") Long orgId);

	/**
	 * used to get active application id by orgid
	 * 
	 * @param orgid
	 * @return
	 */
//D#125466

	@Query("SELECT p FROM TbMlTradeMast p  where p.orgid = :orgid and p.trdStatus in (:trdStatus,:trdStatus1) or ((select count(t) from TbCfcApplicationMstEntity t where (t.apmApplicationId=p.apmApplicationId and t.rejctionNo is not null) or (t.apmApplicationId=p.apmApplicationId and t.tbServicesMst.smServiceId=:serviceId) ) >0 )")
	List<TbMlTradeMast> getActiveApplicationIdByOrgId(@Param("orgid") long orgid, @Param("trdStatus") long trdStatus,
			@Param("trdStatus1") long trdStatus1, @Param("serviceId") Long serviceId);

	/**
	 * update trdstatus flag and license from and to date
	 * 
	 * @param apmApplicationId
	 * @param orgId
	 * @param flag
	 * @param toDate
	 * @param lgIpMacUpd
	 * @param licenseNo
	 */

	@Modifying
	@Query("UPDATE TbMlTradeMast a SET a.trdStatus =:flag , a.trdLicfromDate = CURRENT_DATE, a.trdLictoDate =:toDate, a.lgIpMacUpd =:lgIpMacUpd, a.trdLicno = :licenseNo  where a.apmApplicationId =:apmApplicationId and a.orgid = :orgId")
	void updateTradeLicenseFlag(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId,
			@Param("flag") Long flag, @Param("toDate") Date toDate, @Param("lgIpMacUpd") String lgIpMacUpd,
			@Param("licenseNo") String licenseNo);

	@Modifying
	@Query("UPDATE TbMlTradeMast a SET a.trdStatus =:flag , a.lgIpMacUpd =:lgIpMacUpd where a.apmApplicationId =:apmApplicationId and a.orgid = :orgId")
	void updateTradeLicenseFlag(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId,
			@Param("flag") Long flag, @Param("lgIpMacUpd") String lgIpMacUpd);

	/**
	 * used to get license details from license no
	 * 
	 * @param trdLicno
	 * @return
	 */
	@Query("select p from TbMlTradeMast p where p.trdLicno =:trdLicno and p.orgid=:orgid")
	TbMlTradeMast getLicenseDetailsByLicenseNo(@Param("trdLicno") String trdLicno, @Param("orgid") Long orgid);

	/**
	 * update trade license from date and to date when renewal license form
	 * submitted
	 * 
	 * @param apmApplicationId
	 * @param treLicfromDate
	 * @param treLictoDate
	 */
	@Modifying
	@Query("UPDATE TbMlTradeMast a SET a.trdLicfromDate =:treLicfromDate, a.trdLictoDate =:treLictoDate where a.trdId =:trdId")
	void updateLicenseDate(@Param("trdId") Long trdId, @Param("treLicfromDate") Date treLicfromDate,
			@Param("treLictoDate") Date treLictoDate);

	/**
	 * get Trade Details By Trade Id
	 * 
	 * @param trdId
	 * @return
	 */

	@Query("select p from TbMlTradeMast p where p.trdId =:trdId and p.orgid=:orgid")
	TbMlTradeMast getTradeDetailsByTrdId(@Param("trdId") Long trdId, @Param("orgid") Long orgid);

	@Modifying
	@Query("update TbMlTradeMast a set a.trdBusnm =:trdNewBusnm  where a.trdLicno =:trdLicno and a.orgid=:orgid")
	void updateTradeBusinessName(@Param("trdNewBusnm") String trdBusnm, @Param("trdLicno") String trdLicno,
			@Param("orgid") Long orgid);

	/**
	 * Update TRI_STATUS
	 * 
	 * @param triId
	 * @param orgid
	 */

	@Modifying
	@Query("update TbMlItemDetail a set a.triStatus = 'N'  where a.triId =:triId and a.orgid=:orgid")
	void updateDeletedFlag(@Param("triId") Long triId, @Param("orgid") Long orgid);
  //Defect #128419 changes due to modify item flag changes as M
	@Query("select a from TbMlItemDetail a "
			+ "where (a.triStatus = 'A' or a.triStatus = 'Y' or a.triStatus = 'M') and a.masterTradeId.trdId in ( "
			+ "select  b.trdId from TbMlTradeMast b where b.trdId= :trdId and " + "b.orgid=:orgid )")
	List<TbMlItemDetail> getItemDetailsByTriStatus(@Param("trdId") Long trdId, @Param("orgid") Long orgid);

	@Modifying
	@Query("UPDATE TbMlTradeMast a SET a.trdStatus =:flag, a.lgIpMacUpd =:lgIpMacUpd where a.trdLicno =:trdLicno and a.orgid = :orgId")
	void updateCancellationTrdFlag(@Param("trdLicno") String trdLicno, @Param("orgId") Long orgId,
			@Param("flag") Long flag, @Param("lgIpMacUpd") String lgIpMacUpd);

	@Query("SELECT count(1) from TbMlOwnerDetail i where i.troAdhno=:troAdhno and i.orgid=:orgid")
	int getCountbyAdharNumber(@Param("troAdhno") Long troAdhno, @Param("orgid") Long orgid);

	@Query("select p from TbMlTradeMast p where p.apmApplicationId =:applicationId and p.orgid =:orgId")
	TbMlTradeMast getLicenseDetailsByAppIdAndOrgId(@Param("applicationId") Long applicationId,
			@Param("orgId") Long orgId);

	@Query("Select t.apmApplicationId from TbMlTradeMast t where t.trdLicno =:licenseNo and t.orgid =:orgId")
	Long getApplicationNumberByRefNo(@Param("licenseNo") String licenseNo, @Param("orgId") Long orgId);

	@Query("select case when count(tm)>0 THEN true ELSE false END from TbMlTradeMast tm where tm.trdLicno =:referenceNo AND tm.orgid =:orgId")
	Boolean checkRefNoValidOrNot(@Param("referenceNo") String referenceNo, @Param("orgId") Long orgId);

	@Query(value = "SELECT  distinct TM.TRD_LICNO,\r\n"
			+ "(Select D1.CPD_DESC  from tb_comparam_det D1 Where D1.CPD_ID = TM.TRD_LICTYPE),"
			+ "(Select D1.CPD_DESC_MAR from tb_comparam_det D1 Where D1.CPD_ID = TM.TRD_LICTYPE),"
			+ "(SELECT REPLACE(GROUP_CONCAT(CONCAT(IFNULL(TRO_NAME,' '),' ',IFNULL(TRO_MNAME,' '))),',','\n')"
			+ " FROM tb_ml_owner_detail OD WHERE OD.TRD_ID = TM.TRD_ID\r\n "
			+ "AND OD.TRO_PR in ('D','A')) AS BusinessOwnerName,\r\n"
			+ "(Select C1.COD_DESC from tb_comparent_det C1 Where C1.COD_ID = ID.TRI_COD1) AS LICENSE_CATAGORY,\r\n"
			+ "(Select C1.COD_DESC_MAR from tb_comparent_det C1 Where C1.COD_ID = ID.TRI_COD1) AS LICENSE_CATAGORY1,\r\n"
			+ "(select C2.COD_DESC from tb_comparent_det C2 where C2.COD_ID = ID.TRI_COD2) AS LICENSE_SUB_CATAGORY,\r\n"
			+ "(select C2.COD_DESC_MAR from tb_comparent_det C2 where C2.COD_ID = ID.TRI_COD2) AS LICENSE_SUB_CATAGORY1,\r\n"
			+ " TM.TRD_LICFROM_DATE AS LICFROMDATE,\r\n" + " TM.TRD_LICTO_DATE AS LICTODATE,\r\n"
			+ "TIMESTAMPDIFF(MONTH, TM.TRD_LICTO_DATE,now()) AS DuePendingMonth\r\n" + "FROM tb_ml_trade_mast TM,\r\n"
			+ "tb_ml_item_detail ID,\r\n" + " TB_COMPARAM_det x,\r\n" + " tb_comparam_mas y\r\n"
			+ "WHERE TM.TRD_ID = ID.TRD_ID\r\n"
			+ " AND x.cpm_id=y.cpm_id and cpm_prefix='LIS' and CPD_VALUE in ('I','T')\r\n" + "AND TM.ORGID =:orgId\r\n"
			+ "AND COALESCE(ID.TRI_COD1 ,0)=(case when COALESCE(:triCod1,0)=0 then COALESCE(ID.TRI_COD1,0) else COALESCE(:triCod1,0) end)\r\n"
			+ "AND COALESCE(ID.TRI_COD2 ,0)=(case when COALESCE(:triCod2,0)=0 then COALESCE(ID.TRI_COD2,0) else COALESCE(:triCod2,0) end)\r\n"
			+ " AND DATE_ADD(TRD_LICTO_DATE, INTERVAL -1 MONTH) BETWEEN :fromDate AND :toDate\r\n"
			+ " AND TM.TRD_LICTO_DATE < DATE_ADD(NOW(), INTERVAL +1 MONTH)\r\n" + "AND TM.TRD_LICNO IS NOT NULL\r\n"
			+ "AND TM.TRD_STATUS=x.CPD_ID\r\n" + "AND ID.TRI_STATUS !='I'", nativeQuery = true)
	List<Object[]> getLicenseDetByCatAndDate(@Param("triCod1") Long triCod1, @Param("triCod2") Long triCod2,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("orgId") Long orgId);

	@Query(value = "select TRO_NAME,TRD_LICNO from TB_ML_TRADE_MAST m , TB_ML_OWNER_DETAIL d  where m.TRD_ID=d.TRD_ID  and APM_APPLICATION_ID=:applicationId and m.ORGID=:orgId", nativeQuery = true)
	List<Object[]> getApplicantName(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

	@Query(value = "select distinct m.* from tb_ml_trade_mast m , tb_ml_owner_detail o ,tb_comparam_det cpd where cpd.CPD_ID=m.trd_status and cpd.cpd_value='I' AND  m.ORGID=:orgId AND m.TRD_ID = o.TRD_ID AND m.TRD_LICTO_DATE < DATE_ADD(NOW(), INTERVAL +1 MONTH) and o.TRO_MOBILENO in (select owd.TRO_MOBILENO from tb_ml_owner_detail owd where owd.trd_id=m.trd_id and owd.ORGID=:orgId and owd.TRO_MOBILENO=:mobileNo)", nativeQuery = true)
	List<TbMlTradeMast> getLicenseDetailsBySourceAndOrgId(@Param("orgId") Long orgId,@Param("mobileNo") String mobileNo);

	/**
	 * used to get license details from license no
	 * 
	 * @param trdLicno
	 * @return
	 */
	@Query("select p from TbMlTradeMast p where p.trdLicno =:trdLicno")
	TbMlTradeMast getLicenseDetailsOnlyByLicenseNo(@Param("trdLicno") String trdLicno);
	
	@Query("select a from TbMlItemDetail a where a.masterTradeId.trdId=:trdId and a.orgid=:orgid")
	List<TbMlItemDetail> getItemDetailsByTrdId(@Param("trdId") Long trdId,@Param("orgid")  Long orgid);
	
	@Modifying
	@Transactional
	@Query("UPDATE TbMlTradeMast a SET a.trdStatus =:flag , a.updatedDate=:updatedDate ,a.updatedBy=:updatedBy where a.apmApplicationId =:apmApplicationId and a.orgid = :orgId")
	void updateStatusFlagByRefId(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId,
			@Param("flag") Long flag,@Param("updatedDate") Date updatedDate,@Param("updatedBy")Long updatedBy);

}
