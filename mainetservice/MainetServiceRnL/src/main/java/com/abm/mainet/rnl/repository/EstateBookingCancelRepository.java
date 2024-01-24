package com.abm.mainet.rnl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.domain.EstateBookingCancel;

@Repository
public interface EstateBookingCancelRepository extends JpaRepository<EstateBookingCancel, Long> {
	
	
	/* SQL QUERY */
	
	
	//@Query(value = "SELECT\r\n" + 
			//"\r\n" + 
			//"tb1.RM_RCPTNO,\r\n" + 
	//"tb1.RM_AMOUNT,\r\n" + 
	//"tb1.RM_DATE,\r\n" + 
			
	//"tb1.DP_DEPTID,\r\n" + 
	//"(select dp_deptdesc from tb_department where dp_deptid= tb1.DP_DEPTID) Department,\r\n" + 
	//"\r\n" + 
	//	"tb2.APM_FNAME,\r\n" + 
	//	"tb2.APM_APPLICATION_ID,\r\n" + 
	//	"\r\n" + 
	//	"tb3.EBK_ID,\r\n" + 
	//	"tb3.PROP_ID,\r\n" + 
	//	"tb3.EBK_BOOKING_AMT,\r\n" + 
	//	"tb3.EBK_BOOKING_NO,\r\n" + 
	//	"tb3.EBK_BOOKING_DATE,\r\n" + 
			
	//	"tb3.EBK_BOOK_FROMDT,\r\n" + 
			
	//	"tb3.EBK_BOOK_TODT,\r\n" + 
	//	"tb3.EBK_CANCEL_DATE,\r\n" + 
			
			
	//		"(select Cpd_DESC from tb_comparam_det where CPD_ID=tb3.EBK_BOOK_PURPOSE) Booking_purpose,\r\n" + 
	//		"(select Cpd_DESC from tb_comparam_det where CPD_ID=tb3.EBK_BOOK_SHIFT) Booking_Shift,\r\n" + 
	//		"tb3.EBK_BOOK_PURPOSE,\r\n" + 
	//		"tb3.EBK_BOOK_SHIFT,\r\n" + 
	//		"\r\n" + 
	//		"tb4.PM_PROPNO,\r\n" + 
	//		"tb4.PROP_NAME,\r\n" + 
	//		"\r\n" + 
			//		"tb5.ES_CODE,\r\n" + 
	//		"tb5.COD_ID2_ETY,\r\n" + 
	//	"tb5.COD_ID1_ETY,\r\n" + 
	//	"(select cod_desc from tb_comparent_det where cod_id=tb5.COD_ID1_ETY) Category1,\r\n" + 
	//	"(select cod_desc from tb_comparent_det where cod_id=tb5.COD_ID2_ETY) Category2,\r\n" + 
	//	"\r\n" + 
	//	"(select cpd_desc from\r\n" + 
	//	"tb_receipt_mode a,\r\n" + 
	//	"tb_comparam_det b\r\n" + 
	//	"where a.RM_RCPTID=tb1.RM_RCPTID\r\n" + 
	//	"and a.CPD_FEEMODE=b.cpd_id) PaymentMode,\r\n" + 
	//	"\r\n" + 
	//	
	//	"  (select cpd_desc from\r\n" + 
	//	"tb_receipt_mode a,\r\n" + 
	//	"tb_comparam_det b\r\n" + 
	//	"where a.RM_RCPTID=tb1.RM_RCPTID\r\n" + 
	//	"and a.CPD_FEEMODE=b.cpd_id)  BookingMode,\r\n" + 
	//	"\r\n" + 
	//	"tb6.O_NLS_ORGNAME_MAR,\r\n" + 
	//	"tb6.O_NLS_ORGNAME,\r\n" + 
	//	"\r\n" + 
	//	"tb7.APA_PINCODE,\r\n" + 
	//	"tb7.APA_MOBILNO,\r\n" + 
	//	"tb7.APA_AREANM,\r\n" + 
	//	"tb7.APA_CITY_NAME\r\n" + 
	//	"\r\n" + 
	//	"from\r\n" + 
	//	"tb_receipt_mas as tb1,\r\n" + 
	//	"tb_cfc_application_mst as tb2,\r\n" + 
	//	"tb_rl_estate_booking as tb3,\r\n" + 
	//	"tb_rl_property_mas as tb4,\r\n" + 
	//	"tb_rl_estate_mas as tb5,\r\n" + 
	//	"tb_organisation as tb6,\r\n" + 
	//		"tb_cfc_application_address as tb7\r\n" + 
	//	"\r\n" + 
	//	"where\r\n" + 
	//	"tb1.APM_APPLICATION_ID=tb2.APM_APPLICATION_ID and\r\n" + 
	//	"tb2.APM_APPLICATION_ID=tb3.APM_APPLICATION_ID and\r\n" + 
	//	"tb3.PROP_ID=tb4.PROP_ID and\r\n" + 
	//	"tb4.ES_ID = tb5.ES_ID and\r\n" + 
	//	"tb5.orgid=tb6.ORGID and\r\n" + 
	//	"tb2.APM_APPLICATION_ID=tb7.APM_APPLICATION_ID and\r\n" + 
	//	"tb1.ORGID=:orgId and\r\n" + 
	//	"tb3.EBK_BOOKING_NO=:bookingNo",nativeQuery = true)
	//       List<Object[]> getAllBookedData(@Param("bookingNo") String bookingNo, @Param("orgId") Long orgId);
	
	/* SQL QUERY */
	
	
	@Query("SELECT\r\n" + 
			"\r\n" + 
			"tb1.rmRcptno,\r\n" + 
			"tb1.rmAmount,\r\n" + 
			"tb1.rmDate,\r\n" + 
			"tb1.dpDeptId,\r\n" + 
			"(select d1.dpDeptdesc from Department d1 where d1.dpDeptid=tb1.dpDeptId),\r\n" + 
			"\r\n" + 
			"tb2.apmFname,\r\n" + 
			"tb2.apmApplicationId,\r\n" + 
			"\r\n" + 
			"tb3.id,\r\n" + 
			"tb3.estatePropertyEntity.propId,\r\n" + 
			"tb3.amount,\r\n" + 
			"tb3.bookingNo,\r\n" + 
			"tb3.bookingDate,\r\n" + 
			"tb3.fromDate,\r\n" + 
			"tb3.toDate,\r\n" + 
			"tb3.cancelDate,\r\n" + 
			"(select tc1.cpdDesc from TbComparamDetEntity tc1 where tc1.cpdId=tb3.purpose),\r\n" + 
			"(select tc2.cpdDesc from TbComparamDetEntity tc2 where tc2.cpdId=tb3.shiftId),\r\n" + 
			"tb3.purpose,\r\n" + 
			"tb3.shiftId,\r\n" + 
			"\r\n" + 
			"tb4.code,\r\n" + 
			"tb4.name,\r\n" + 
			"\r\n" + 
			"tb5.code,\r\n" + 
			"tb5.type2,\r\n" + 
			"tb5.type1,\r\n" + 
			"(select td.codDesc from TbComparentDetEntity td where td.codId=tb5.type1),\r\n" + 
			"(select te.codDesc from TbComparentDetEntity te where te.codId=tb5.type2),\r\n" + 
			"\r\n" + 
			"(select b.cpdDesc from\r\n" + 
			"TbSrcptModesDetEntity a,\r\n" + 
			"TbComparamDetEntity b \r\n" + 
			" where a.rmRcptid.rmRcptid=tb1.rmRcptid\r\n" + 
			" and a.cpdFeemode=b.cpdId),\r\n" + 
			
			" (select b.cpdDesc from\r\n" + 
			"TbSrcptModesDetEntity a,\r\n" + 
			"TbComparamDetEntity b \r\n" + 
			" where a.rmRcptid.rmRcptid=tb1.rmRcptid\r\n" + 
			" and b.cpdDesc=b.cpdId),\r\n" + 
			"\r\n" + 
			"tb6.oNlsOrgnameMar,\r\n" + 
			"tb6.ONlsOrgname,\r\n" + 
			"\r\n" + 
			"tb7.apaPincode,\r\n" + 
			"tb7.apaMobilno,\r\n" + 
			"tb7.apaAreanm,\r\n" + 
			"tb7.apaCityName\r\n" + 
			"\r\n" + 
			"from\r\n" + 
			"TbServiceReceiptMasEntity as tb1,\r\n" + 
			"TbCfcApplicationMstEntity  as tb2,\r\n" + 
			"EstateBooking as tb3,\r\n" + 
			"EstatePropertyEntity as tb4,\r\n" + 
			"EstateEntity as tb5,\r\n" + 
			"Organisation as tb6,\r\n" + 
			"CFCApplicationAddressEntity as tb7\r\n" + 
			"\r\n" + 
			"where\r\n" + 
			"tb1.apmApplicationId=tb2.apmApplicationId and\r\n" + 
			"tb2.apmApplicationId=tb3.applicationId and\r\n" + 
			"tb3.estatePropertyEntity.propId=tb4.propId and\r\n" + 
			"tb4.estateEntity.esId = tb5.esId and\r\n" + 
			"tb5.orgId=tb6.orgid and\r\n" + 
			"tb2.apmApplicationId=tb7.apmApplicationId and\r\n" + 
			"tb1.orgId=:orgId and \r\n" + 
			"tb3.bookingNo=:bookingNo")
	         List<Object[]> getAllBookedData(@Param("bookingNo") String bookingNo, @Param("orgId") Long orgId);
	         
	         
	         @Query("from EstateBooking r where r.createdBy=:userId and r.orgId=:orgId and r.bookingStatus!='C' and r.bookingStatus!='U' and r.bookingStatus!='F'")
	         List<EstateBooking> getBookedDatabyUseridandOrgid(@Param("userId") Long userId,@Param("orgId") Long orgId);
	         
	         
	         @Query("from EstateBooking r where r.orgId=:orgId and r.bookingStatus!='C' and r.bookingStatus!='U' and r.bookingStatus!='F'")
	         List<EstateBooking> getAllBookedPropertiesByOrgId(@Param("orgId") Long orgId);

}
