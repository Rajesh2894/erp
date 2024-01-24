package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.SolidWasteBillMaster;

/**
 * The Interface BillMasterRepository.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 19-Jun-2018
 */
@Repository
public interface SolidWasteBillMasterRepository extends JpaRepository<SolidWasteBillMaster, Long> {

    /**
     * update Account Posting Flag
     * @param bmIdNo
     * @param flag
     */
    @Modifying
    @Query("UPDATE SolidWasteBillMaster m set m.billPaidFlg=:flag where m.swBmIdno in (:swBmIdno) ")
    void updateAccountPostingFlag(@Param("swBmIdno") List<Long> bmIdNo, @Param("flag") String flag);

    /**
     * fetch Unpaid Bill
     * @param bmIdNo
     * @param orgid
     * @return
     */
    @Query("FROM SolidWasteBillMaster m WHERE m.orgid=:orgId AND m.billPaidFlg IS NULL and m.swBmIdno in (:swBmIdno) ")
    List<SolidWasteBillMaster> fetchUnpaidBill(@Param("swBmIdno") List<Long> bmIdNo, @Param("orgId") Long orgid);

    /**
     * last Payment Details
     * @param customerNo
     * @param orgid
     * @return
     */
    @Query(" FROM SolidWasteBillMaster m WHERE m.tbSwNewRegistration.registrationId=:customerNo AND  m.billPaidFlg='Y' AND m.orgid=:orgId AND  m.swBmIdno = ( SELECT MAX( c.swBmIdno ) FROM SolidWasteBillMaster c WHERE c.tbSwNewRegistration.registrationId=:customerNo AND c.orgid=:orgId AND c.billPaidFlg='Y' ) ")
    SolidWasteBillMaster lastPaymentDetails(@Param("customerNo") Long customerNo, @Param("orgId") Long orgid);
    
    @Query(value="select  \r\n" + 
            "a.BILL_TO_DATE,\r\n" + 
            "b.sw_name,\r\n" + 
            "b.SW_MOBILE,\r\n" + 
            "b.SW_ADDRESS,\r\n" + 
            "b.SW_USER_CATEGORY1,\r\n" + 
            "b.SW_USER_CATEGORY1_REG,\r\n" + 
            "(select y.COD_DESC \r\n" + 
            "from tb_location_oper_wardzone x,\r\n" + 
            "tb_comparent_det y\r\n" + 
            "where x.loc_id=b.SW_LOCATION and\r\n" + 
            "x.COD_ID_OPER_LEVEL1=y.COD_ID) Ward_Eng,\r\n" + 
            "(select y.COD_DESC_MAR\r\n" + 
            "from tb_location_oper_wardzone x,\r\n" + 
            "tb_comparent_det y\r\n" + 
            "where x.loc_id=b.SW_LOCATION and\r\n" + 
            "x.COD_ID_OPER_LEVEL1=y.COD_ID) Ward_Reg,\r\n" + 
            "a.MANUAL_RECEIPT_NO,\r\n" + 
            "a.MONTHLY_CHARGES,\r\n" + 
            "(COALESCE(a.BILL_AMOUNT,0)+COALESCE(a.ADVANCE_AMOUNT,0)) TotalPayment,\r\n" + 
            "( Select m.RM_RCPTNO from tb_receipt_mas m where m.ADDITIONAL_REF_NO = a.SW_BM_IDNO and m.ORGID=a.ORGID  ) Receipt_NO, \r\n"+
            "a.MANUAL_BOOKNO\r\n" + 
            "from tb_sw_bill_mas a,\r\n" + 
            "tb_sw_registration b\r\n" + 
            "where a.REGISTRATION_ID=b.REGISTRATION_ID \r\n" + 
            "and a.orgid=b.orgid and month(a.BILL_TO_DATE)=:monthNo\r\n" + 
            "and a.orgid=:orgId order by a.BILL_TO_DATE", nativeQuery = true)
    List<Object[]> getUserChargeCollectionData(@Param("monthNo") Long monthNo,@Param("orgId") Long orgId);
    
    

}
