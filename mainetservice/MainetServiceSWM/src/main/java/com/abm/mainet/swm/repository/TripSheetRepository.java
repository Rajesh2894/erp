/*
 *
 */
package com.abm.mainet.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.TripSheet;

/**
 * The Interface TripSheetRepository.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Repository
public interface TripSheetRepository extends JpaRepository<TripSheet, Long> {

    /**
     * find Trip SheetBy
     * @param orgId
     * @param veId
     * @param veRentFromdate
     * @param veRentTodate
     * @param veVetype
     * @return
     */
    @Query(value = "\r\n" + 
    		"SELECT a.TRIP_DATE,\r\n" + 
    		"                   a.ve_id,\r\n" + 
    		"                   (SELECT z.VE_NO\r\n" + 
    		"                      FROM tb_sw_vehicle_mast z\r\n" + 
    		"                     WHERE z.ve_id = a.ve_id)\r\n" + 
    		"                      VechicleNo,\r\n" + 
    		"                   a.VechicleType,\r\n" + 
    		"                   count(1) 'No. of Trip',\r\n" + 
    		"                   SUM(a.DRY) DRY,\r\n" + 
    		"                   SUM(a.WATE) WATE,\r\n" + 
    		"                   SUM(a.Hazardous) Hazardous\r\n" + 
    		"              FROM (SELECT x.TRIP_ID,    X.TRIP_DATE,\r\n" + 
    		"                           X.ve_id,    y.VE_REG_NO,\r\n" + 
    		"                           (SELECT CPD_DESC   FROM tb_comparam_det\r\n" + 
    		"                             WHERE cpd_id = Y.VE_VETYPE) VechicleType,\r\n" + 
    		"                           SUM(X.DRY) DRY,\r\n" + 
    		"                           SUM(X.WATE) WATE,\r\n" + 
    		"                           SUM(X.Hazardous) Hazardous\r\n" + 
    		"                      FROM (SELECT a.TRIP_ID,        a.TRIP_DATE,\r\n" + 
    		"                                   a.ve_id,\r\n" + 
    		"                                   (CASE    WHEN e.COD_VALUE = 'DW' THEN x.TRIP_VOLUME\r\n" + 
    		"                                       ELSE 0    END)  AS DRY,\r\n" + 
    		"                                   (CASE  WHEN e.COD_VALUE = 'WW' THEN x.TRIP_VOLUME\r\n" + 
    		"                                       ELSE 0   END)   AS WATE,\r\n" + 
    		"                                   (CASE  WHEN e.COD_VALUE = 'HZ' THEN x.TRIP_VOLUME\r\n" + 
    		"                                       ELSE 0   END)   AS Hazardous\r\n" + 
    		"                              FROM tb_sw_tripsheet a,\r\n" + 
    		"                                   tb_sw_tripsheet_gdet x,\r\n" + 
    		"                                   tb_comparent_det e\r\n" + 
    		"                             WHERE     x.wast_type = e.cod_id\r\n" + 
    		"                                   AND x.trip_id = a.trip_id\r\n" + 
    		"                                    AND a.TRIP_DATE BETWEEN :veRentFromdate AND :veRentTodate\r\n" + 
    		"                                  AND a.ve_id =\r\n" + 
    		"                                          (CASE  WHEN COALESCE(:veId, 0) = 0\r\n" + 
    		"                                              THEN   COALESCE(a.ve_id, 0)\r\n" + 
    		"                                              ELSE   COALESCE(:veId, 0) END) \r\n" + 
    		"                                   AND a.ORGID =:orgId) X\r\n" + 
    		"                                   INNER JOIN   tb_sw_vehicle_mast Y ON x.ve_id = y.ve_id\r\n" + 
    		"                                   LEFT JOIN tb_vendormaster V ON V.VM_VENDORID = Y.VM_VENDORID and V.VM_VENDORNAME=:vendorName\r\n" + 
    		"                                   LEFT JOIN tb_contract_mast C ON C.CONT_ID = Y.CONT_ID and C.CONT_NO=:contractNo\r\n" + 
    		"                                   LEFT JOIN tb_sw_contvend_mapping MP ON MP.CONT_ID = Y.CONT_ID\r\n" + 
    		"                     WHERE  Y.Orgid=:orgId  AND \r\n" + 
    		"                            y.VE_VETYPE in \r\n" + 
    		"                                  (CASE WHEN COALESCE(:veVetype, -1) = -1\r\n" + 
    		"                                      THEN   COALESCE(y.VE_VETYPE, -1)\r\n" + 
    		"                                      ELSE   COALESCE(:veVetype, -1)     END) \r\n" + 
    		"                    GROUP BY x.TRIP_ID,\r\n" + 
    		"                             X.TRIP_DATE,\r\n" + 
    		"                             Y.VE_VETYPE,\r\n" + 
    		"                             X.ve_id,\r\n" + 
    		"                             y.VE_REG_NO) a\r\n" + 
    		"            GROUP BY a.TRIP_DATE,\r\n" + 
    		"                     a.VechicleType,\r\n" + 
    		"                     a.ve_id,\r\n" + 
    		"                     a.VE_REG_NO", nativeQuery = true)
    List<Object[]> findTripSheetBy(@Param("orgId") Long orgId, @Param("veId") Long veId,
            @Param("veRentFromdate") Date veRentFromdate, @Param("veRentTodate") Date veRentTodate,
            @Param("veVetype") Long veVetype,@Param("vendorName")String vendorName, @Param("contractNo")String contractNo);

    /**
     * find By DeId And Orgid And Trip Date Equals
     * @param deId
     * @param orgid
     * @param tripDate
     * @return
     */
    @Query("SELECT SUM(tgd.tripVolume) FROM TripSheet t, TripSheetGarbageDet tgd WHERE tgd.tbSwTripsheet.tripId = t.tripId AND t.tripDate = :tripDate AND t.deId = :deId AND t.orgid = :orgId")
    Long findByDeIdAndOrgidAndTripDateEquals(@Param("deId") Long deId, @Param("orgId") Long orgid,
            @Param("tripDate") Date tripDate);

    /**
     * find Trip Sheet Details
     * @param orgId
     * @param veId
     * @param veRentFromdate
     * @param veRentTodate
     * @param veVetype
     * @return
     */
    @Query(value = "SELECT TRIP_DATE,  \r\n" + 
    		"    		       TRIP_INTIME,  \r\n" + 
    		"    		       TRIP_OUTTIME,  \r\n" + 
    		"    		       TRIP_WESLIPNO,  \r\n" + 
    		"    		       VE_ID,  \r\n" + 
    		"    		       VECHICLENO,  \r\n" + 
    		"    		       VECHICLETYPE,  \r\n" + 
    		"    		       MRF_ID,  \r\n" + 
    		"    		       MRFCENTERNAMEENG,  \r\n" + 
    		"    		       SUM(DRY) DRY,  \r\n" + 
    		"    		       SUM(WATE) WATE,  \r\n" + 
    		"    		       SUM(HAZARDOUS) HAZARDOUS  \r\n" + 
    		"    		  FROM (SELECT X.TRIP_DATE,  \r\n" + 
    		"    		               X.TRIP_INTIME,  \r\n" + 
    		"    		               X.TRIP_OUTTIME,  \r\n" + 
    		"    		               X.TRIP_WESLIPNO,  \r\n" + 
    		"    		               X.VE_ID,  \r\n" + 
    		"    		               (SELECT Z.VE_NO  \r\n" + 
    		"    		                  FROM TB_SW_VEHICLE_MAST Z  \r\n" + 
    		"    		                 WHERE Z.VE_ID = X.VE_ID)  \r\n" + 
    		"    		                  VECHICLENO,  \r\n" + 
    		"    		               (SELECT CPD_DESC  \r\n" + 
    		"    		                  FROM TB_COMPARAM_DET  \r\n" + 
    		"    		                 WHERE CPD_ID = Y.VE_VETYPE)  \r\n" + 
    		"    		                  VECHICLETYPE,  \r\n" + 
    		"    		               X.MRF_ID,  \r\n" + 
    		"    		               Z.MRF_PLATNAME AS MRFCENTERNAMEENG,  \r\n" + 
    		"    		               X.DRY DRY,  \r\n" + 
    		"    		               X.WATE WATE,  \r\n" + 
    		"    		               X.HAZARDOUS  \r\n" + 
    		"    		          FROM (SELECT A.TRIP_DATE,  \r\n" + 
    		"    		                       A.TRIP_INTIME,  \r\n" + 
    		"    		                       A.TRIP_OUTTIME,  \r\n" + 
    		"    		                       A.TRIP_WESLIPNO,  \r\n" + 
    		"    		                       A.VE_ID,  \r\n" + 
    		"    		                       A.MRF_ID,  \r\n" + 
    		"    		                       (CASE  \r\n" + 
    		"    		                           WHEN E.COD_VALUE = 'DW' THEN X.TRIP_VOLUME  \r\n" + 
    		"    		                           ELSE 0  \r\n" + 
    		"    		                        END)  \r\n" + 
    		"    		                          AS 'DRY',  \r\n" + 
    		"    		                       (CASE  \r\n" + 
    		"    		                           WHEN E.COD_VALUE = 'WW' THEN X.TRIP_VOLUME  \r\n" + 
    		"    		                           ELSE 0  \r\n" + 
    		"    		                        END)  \r\n" + 
    		"    		                          AS 'WATE',  \r\n" + 
    		"    		                       (CASE  \r\n" + 
    		"    		                           WHEN E.COD_VALUE = 'HZ' THEN X.TRIP_VOLUME  \r\n" + 
    		"    		                           ELSE 0  \r\n" + 
    		"    		                        END)  \r\n" + 
    		"    		                          AS 'HAZARDOUS'  \r\n" + 
    		"    		                  FROM TB_SW_TRIPSHEET A,  \r\n" + 
    		"    		                       TB_SW_TRIPSHEET_GDET X,  \r\n" + 
    		"    		                       TB_COMPARENT_DET E  \r\n" + 
    		"    		                 WHERE     X.WAST_TYPE = E.COD_ID  \r\n" + 
    		"    		                       AND X.TRIP_ID = A.TRIP_ID  \r\n" + 
    		"    		                       AND A.TRIP_DATE BETWEEN :veRentFromdate AND :veRentTodate \r\n" + 
    		"    		                       AND A.VE_ID =  \r\n" + 
    		"    		                              (CASE  \r\n" + 
    		"    		                                  WHEN COALESCE(:veId, 0) = 0  \r\n" + 
    		"    		                                  THEN  \r\n" + 
    		"    		                                     COALESCE(A.VE_ID, 0)  \r\n" + 
    		"    		                                  ELSE  \r\n" + 
    		"    		                                     COALESCE(:veId, 0)  \r\n" + 
    		"    		                               END)  \r\n" + 
    		"    		                       AND A.ORGID =:orgId) X  \r\n" + 
    		"    		               INNER JOIN TB_SW_VEHICLE_MAST Y ON X.VE_ID = Y.VE_ID  \r\n" + 
    		"    		               INNER JOIN TB_SW_MRF_MAST Z ON  X.MRF_ID = Z.MRF_ID\r\n" + 
    		"                           LEFT JOIN tb_vendormaster V ON V.VM_VENDORID = Y.VM_VENDORID and V.VM_VENDORNAME=:vendorName\r\n" + 
    		"						   LEFT JOIN tb_contract_mast C ON C.CONT_ID = Y.CONT_ID and C.CONT_NO=:contractNo\r\n" + 
    		"                           LEFT JOIN tb_sw_contvend_mapping MP ON MP.CONT_ID = Y.CONT_ID\r\n" + 
    		"                     WHERE    Y.Orgid=:orgId   \r\n" + 
    		"    		               AND Y.VE_VETYPE =  \r\n" + 
    		"    		                      (CASE  \r\n" + 
    		"    		                          WHEN COALESCE(:veVetype, -1) = -1  \r\n" + 
    		"    		                          THEN  \r\n" + 
    		"    		                             COALESCE(Y.VE_VETYPE, -1)  \r\n" + 
    		"    		                          ELSE  \r\n" + 
    		"    		                             COALESCE(:veVetype, -1)  \r\n" + 
    		"    		                       END)) V  \r\n" + 
    		"    		GROUP BY VE_ID,  \r\n" + 
    		"    		         TRIP_DATE,  \r\n" + 
    		"    		         TRIP_INTIME,  \r\n" + 
    		"    		         TRIP_OUTTIME,  \r\n" + 
    		"    		         TRIP_WESLIPNO,  \r\n" + 
    		"    		         VECHICLENO,  \r\n" + 
    		"    		         VECHICLETYPE,  \r\n" + 
    		"    		         MRF_ID,  \r\n" + 
    		"    		         MRFCENTERNAMEENG", nativeQuery = true)
	List<Object[]> findTripSheetDetails(@Param("orgId") Long orgId, @Param("veId") Long veId,
			@Param("veRentFromdate") Date veRentFromdate, @Param("veRentTodate") Date veRentTodate,
			 @Param("veVetype") Long veVetype,@Param("vendorName")String vendorName, @Param("contractNo")String contractNo);

    @Query(value = "select distinct \r\n" +
            "           (select x.VE_NO  from tb_sw_vehicle_mast x where ve_id=a.ve_id) ve_no,\r\n" +
            "           (select y.cpd_desc \r\n" +
            "           from tb_sw_vehicle_mast x,\r\n" +
            "           tb_comparam_det y\r\n" +
            "           where ve_id=a.ve_id and\r\n" +
            "           y.cpd_id=x.VE_VETYPE) Vechicle_typeEng,\r\n" +
            "           (select y.cpd_desc_mar \r\n" +
            "           from tb_sw_vehicle_mast x,\r\n" +
            "           tb_comparam_det y\r\n" +
            "           where ve_id=a.ve_id and\r\n" +
            "           y.cpd_id=x.VE_VETYPE) Vechicle_typeReg\r\n" +
            "                  from tb_sw_tripsheet a where a.MRF_ID=:mrfId and a.TRIP_DATE=:date and a.ORGID=:orgId", nativeQuery = true)
    List<Object[]> getVehicleDetOfMRFCenter(@Param("mrfId") Long mrfId, @Param("date") Date date, @Param("orgId") Long orgId);

    @Query(value = "select distinct\r\n" +
            "count(distinct a.VE_ID) ve_cnt,\r\n" +
            "count(distinct a.BEAT_ID) be_cnt\r\n" +
            "from tb_sw_tripsheet a where a.MRF_ID=:mrfId and a.TRIP_DATE=:date and a.ORGID=:orgId", nativeQuery = true)
    List<Object[]> getVehicleCountandBeatCountOfAssMRFCenter(@Param("mrfId") Long mrfId, @Param("date") Date date,
            @Param("orgId") Long orgId);

    @Query(value = "select b.BEAT_ID, \r\n" +
            "             b.BEAT_COMMERTIAL, \r\n" +
            "             b.BEAT_INDUSTRIAL, \r\n" +
            "             b.BEAT_RESIDENTIAL, \r\n" +
            "             (select COD_DESC  \r\n" +
            "             from tb_location_oper_wardzone c, \r\n" +
            "             tb_comparent_det d,\r\n" +
            "             tb_department e\r\n" +
            "             where C.loc_id=b.BEAT_START_POINT and \r\n" +
            "             c.COD_ID_OPER_LEVEL1=d.COD_ID and\r\n" +
            "             C.dp_deptid=e.DP_DEPTID and\r\n" +
            "             e.DP_DEPTCODE='SWM') SWard, \r\n" +
            "             (select COD_DESC  \r\n" +
            "             from tb_location_oper_wardzone c, \r\n" +
            "             tb_comparent_det d,\r\n" +
            "             tb_department e \r\n" +
            "             where C.loc_id=b.beat_END_point and \r\n" +
            "             c.COD_ID_OPER_LEVEL1=d.COD_ID and\r\n" +
            "             C.dp_deptid=e.DP_DEPTID and\r\n" +
            "             e.DP_DEPTCODE='SWM') EWard,\r\n" +
            "             a.MRF_ID \r\n" +
            "from tb_sw_tripsheet a,\r\n" +
            "tb_sw_beat_mast b\r\n" +
            "where a.mrf_id=:mrfId and \r\n" +
            "a.beat_id=b.beat_id and a.TRIP_DATE=:date and a.ORGID=:orgId", nativeQuery = true)
    List<Object[]> getMrfwiseDetails(@Param("mrfId") Long mrfId, @Param("date") Date date, @Param("orgId") Long orgId);

    @Query(value = "select sum(SWard+EWard)\r\n" +
            "from\r\n" +
            "(select\r\n" +
            "(select count(1) \r\n" +
            "from tb_location_oper_wardzone c \r\n" +
            "where loc_id=b.beat_start_point) SWard,\r\n" +
            "(select count(1) \r\n" +
            "from tb_location_oper_wardzone c \r\n" +
            "where loc_id=b.beat_end_point) EWard,\r\n" +
            "a.MRF_ID\r\n" +
            "from tb_sw_tripsheet a,\r\n" +
            "tb_sw_beat_mast b\r\n" +
            "where a.mrf_id=:mrfId  and a.TRIP_DATE=:date and a.ORGID=:orgId and\r\n" +
            "a.beat_id=b.beat_id) x\r\n" +
            "group by x.mrf_id", nativeQuery = true)
    List<Object[]> getwardCount(@Param("mrfId") Long mrfId, @Param("date") Date date, @Param("orgId") Long orgId);

}
