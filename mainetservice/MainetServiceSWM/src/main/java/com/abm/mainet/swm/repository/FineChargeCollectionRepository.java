package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.FineChargeCollection;

/**
 * The Interface FineChargeCollectionRepositor.
 *
 * @author Lalit.Prusti Created
 * 
 * Date : 29-June-2018
 */
@Repository
public interface FineChargeCollectionRepository extends JpaRepository<FineChargeCollection, Long> {

    List<FineChargeCollection> findByFchMobnoOrRegistrationIdAndOrgid(String fchMobno, Long registrationId, Long orgid);

    @Query(value = "SELECT a.FCH_ENTRYDATE,\r\n" +
            "       b.SW_NAME,\r\n" +
            "       B.SW_MOBILE,\r\n" +
            "       b.SW_ADDRESS,\r\n" +
            "       (SELECT cod_desc\r\n" +
            "          FROM tb_comparent_det x,\r\n" +
            "               tb_location_oper_wardzone y,\r\n" +
            "               tb_department e\r\n" +
            "         WHERE     x.COD_ID = y.COD_ID_OPER_LEVEL1\r\n" +
            "               AND y.LOC_ID = SW_LOCATION\r\n" +
            "               AND y.dp_deptid = e.DP_DEPTID\r\n" +
            "               AND e.DP_DEPTCODE = 'SWM')\r\n" +
            "          Ward_Eng,\r\n" +
            "       (SELECT cod_desc_mar\r\n" +
            "          FROM tb_comparent_det x,\r\n" +
            "               tb_location_oper_wardzone y,\r\n" +
            "               tb_department e\r\n" +
            "         WHERE     x.COD_ID = y.COD_ID_OPER_LEVEL1\r\n" +
            "               AND y.LOC_ID = SW_LOCATION\r\n" +
            "               AND y.dp_deptid = e.DP_DEPTID\r\n" +
            "               AND e.DP_DEPTCODE = 'SWM')\r\n" +
            "          Ward_Reg,\r\n" +
            "       (SELECT cpd_desc\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = a.FCH_TYPE)\r\n" +
            "          Charge_descEng,\r\n" +
            "       (SELECT cpd_desc_mar\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = a.FCH_TYPE)\r\n" +
            "          Charge_descReg,\r\n" +
            "       a.FCH_MANUAL_NO ReceiptNo,\r\n" +
            "       a.fch_amount ChargeAmount\r\n" +
            "  FROM tb_sw_finecharge_col a, tb_sw_registration b\r\n" +
            " WHERE     a.REGISTRATION_ID = b.REGISTRATION_ID\r\n" +
            "       AND month(a.FCH_ENTRYDATE) =:monthNo\r\n" +
            "       AND a.ORGID =:orgId", nativeQuery = true)
    List<Object[]> getAllFineByMonthNo(@Param("orgId") Long orgId, @Param("monthNo") Long monthNo);

    @Query(value = "SELECT x.trip_date,\r\n" +
            "       x.INTIME,\r\n" +
            "       X.OUTTIME,\r\n" +
            "       ((DexitWeight / Dcapacity) * 100) Dry,\r\n" +
            "       DexitWeight,\r\n" +
            "       ((WexitWeight / Wcapacity) * 100) Wate,\r\n" +
            "       WexitWeight,\r\n" +
            "       ((HexitWeight / Hcapacity) * 100) Hazardus,\r\n" +
            "       HexitWeight,\r\n" +
            "       x.WAST_SAGRIGATED\r\n" +
            "  FROM (SELECT a.TRIP_DATE,\r\n" +
            "               a.TRIP_INTIME INTIME,\r\n" +
            "               a.TRIP_OUTTIME OUTTIME,\r\n" +
            "               (SELECT b.ve_capacity\r\n" +
            "                  FROM tb_sw_vehicle_det b, tb_comparent_det c\r\n" +
            "                 WHERE     b.cod_wast1 = c.cod_id\r\n" +
            "                       AND c.COD_VALUE = 'DW'\r\n" +
            "                       AND b.ve_id = a.ve_id)\r\n" +
            "                  Dcapacity,\r\n" +
            "               (SELECT b.TRIP_VOLUME\r\n" +
            "                  FROM tb_sw_tripsheet_gdet b, tb_comparent_det c\r\n" +
            "                 WHERE     b.WAST_TYPE = c.cod_id\r\n" +
            "                       AND c.COD_VALUE = 'DW'\r\n" +
            "                       AND b.TRIP_ID = a.TRIP_ID)\r\n" +
            "                  DexitWeight,\r\n" +
            "               (SELECT b.ve_capacity\r\n" +
            "                  FROM tb_sw_vehicle_det b, tb_comparent_det c\r\n" +
            "                 WHERE     b.cod_wast1 = c.cod_id\r\n" +
            "                       AND c.COD_VALUE = 'WW'\r\n" +
            "                       AND b.ve_id = a.ve_id)\r\n" +
            "                  Wcapacity,\r\n" +
            "               (SELECT b.TRIP_VOLUME\r\n" +
            "                  FROM tb_sw_tripsheet_gdet b, tb_comparent_det c\r\n" +
            "                 WHERE     b.WAST_TYPE = c.cod_id\r\n" +
            "                       AND c.COD_VALUE = 'WW'\r\n" +
            "                       AND b.TRIP_ID = a.TRIP_ID)\r\n" +
            "                  WexitWeight,\r\n" +
            "               (SELECT b.ve_capacity\r\n" +
            "                  FROM tb_sw_vehicle_det b, tb_comparent_det c\r\n" +
            "                 WHERE     b.cod_wast1 = c.cod_id\r\n" +
            "                       AND c.COD_VALUE = 'HZ'\r\n" +
            "                       AND b.ve_id = a.ve_id)\r\n" +
            "                  Hcapacity,\r\n" +
            "               (SELECT b.TRIP_VOLUME\r\n" +
            "                  FROM tb_sw_tripsheet_gdet b, tb_comparent_det c\r\n" +
            "                 WHERE     b.WAST_TYPE = c.cod_id\r\n" +
            "                       AND c.COD_VALUE = 'HZ'\r\n" +
            "                       AND b.TRIP_ID = a.TRIP_ID)\r\n" +
            "                  HexitWeight,\r\n" +
            "               a.WAST_SAGRIGATED\r\n" +
            "          FROM tb_sw_tripsheet a\r\n" +
            "         WHERE month(a.TRIP_DATE) =:monthNo AND a.ve_id =:veId AND a.ORGID =:orgId) x", nativeQuery = true)
    List<Object[]> getAllVehicleLogMainPageReportDataBy(@Param("orgId") Long orgId, @Param("veId") Long veId,
            @Param("monthNo") Long monthNo);

    @Query(value = "select a.VE_ID,\r\n" +
            " b.VE_NO,\r\n" +
            " SUM(case when c.COD_VALUE = 'DW' then a.VE_CAPACITY else 0 end) DRY, \r\n" +
            " SUM(case when c.COD_VALUE = 'WW' then a.VE_CAPACITY else 0 end) WATE,\r\n" +
            " SUM(case when c.COD_VALUE = 'HZ' then a.VE_CAPACITY else 0 end) Hazards\r\n" +
            " from tb_sw_vehicle_det a,\r\n" +
            "tb_sw_vehicle_mast b,\r\n" +
            "tb_comparent_det c\r\n" +
            "where a.ve_id=b.ve_id and\r\n" +
            "a.cod_wast1=c.cod_id and\r\n" +
            "a.ve_id=:veId and a.ORGID=b.ORGID and a.ORGID=:orgId", nativeQuery = true)
    List<Object[]> getAllVehicleLogMainPageReportFirstDataBy(@Param("orgId") Long orgId, @Param("veId") Long veId);

    @Query(value = "select \r\n" +
            "sum(a.BEAT_POPULATION) Population,\r\n" +
            "sum(a.BEAT_RESIDENTIAL) house,\r\n" +
            "sum(a.BEAT_ESTDECOM) Decompost,\r\n" +
            "sum(a.BEAT_ANIMAL_CNT) AnimalCnt,\r\n" +
            "sum(a.BEAT_INDUSTRIAL) estCnt,\r\n" +
            "b.ve_id\r\n" +
            "from tb_sw_beat_mast a,\r\n" +
            "tb_sw_tripsheet b\r\n" +
            "where a.BEAT_ID=b.BEAT_ID and\r\n" +
            "b.ve_id=:veId\r\n" +
            "and b.ORGID=:orgId\r\n" +
            "group by b.ve_id", nativeQuery = true)
    List<Object[]> getAllVehicleLogMainPageReportSecondDataBy(@Param("orgId") Long orgId, @Param("veId") Long veId);

    @Query(value = "SELECT a.COG_COLL_DATE,\r\n" +
            "                   (SELECT b.LOC_NAME_ENG\r\n" +
            "                      FROM tb_location_mas b\r\n" +
            "                     WHERE b.loc_id = a.loc_id)\r\n" +
            "                      LocationName,\r\n" +
            "                   (SELECT b.LOC_NAME_ENG\r\n" +
            "                      FROM tb_location_mas b\r\n" +
            "                     WHERE b.loc_id = a.loc_id)\r\n" +
            "                      LocationRegName,\r\n" +
            "                   (SELECT cpd_desc\r\n" +
            "                      FROM tb_comparam_det\r\n" +
            "                     WHERE cpd_id = a.VE_VETYPE)\r\n" +
            "                      VechicleType,\r\n" +
            "                   (SELECT cpd_desc_mar\r\n" +
            "                      FROM tb_comparam_det\r\n" +
            "                     WHERE cpd_id = a.VE_VETYPE)\r\n" +
            "                      VechicleType1,\r\n" +
            "                   (select ve_no from tb_sw_vehicle_mast where ve_id=a.VE_NO)VE_NO,\r\n" +
            "                   (a.VE_CAPACITY)TOTAL_COLLECTION_WASTE ,\r\n" +
            "                   a.COG_COLL_AMT,\r\n" +
            "                   a.COG_BUILDING_PERMISSIONNO,\r\n" +
            "                   a.COG_NIDDAN_COMPLAIN_NO,\r\n" +
            "                   a.MRF_ID,\r\n" +
            "                   b.MRF_PLATNAME,\r\n" +
            "                   (SELECT cod_desc\r\n" +
            "                      FROM tb_comparent_det x, tb_location_oper_wardzone y\r\n" +
            "                     WHERE y.COD_ID_OPER_LEVEL1 = x.cod_id AND y.LOC_ID = b.LOC_ID)\r\n" +
            "                      Ward_desc,\r\n" +
            "                   (SELECT cod_desc\r\n" +
            "                      FROM tb_comparent_det x, tb_location_oper_wardzone y\r\n" +
            "                     WHERE y.COD_ID_OPER_LEVEL1 = x.cod_id AND y.LOC_ID = b.LOC_ID)\r\n" +
            "                      Ward_descEng,\r\n" +
            "                   (SELECT a.SM_SERVICE_NAME\r\n" +
            "                      FROM tb_services_mst a\r\n" +
            "                     WHERE SM_SERVICE_ID =\r\n" +
            "                              (SELECT b.SM_SERVICE_ID\r\n" +
            "                                 FROM tb_cfc_application_mst b\r\n" +
            "                                WHERE APM_APPLICATION_ID = a.APM_APPLICATION_ID))\r\n" +
            "                      AS SERVICE_NAME,\r\n" +
            "                      (select max(VE_CAPACITY) from tb_sw_vehicle_det where ve_id=a.VE_NO)VEHICLE_CAP\r\n" +
            "              FROM tb_sw_constdemo_garbagecoll a, tb_sw_mrf_mast b" +
            " WHERE     month(a.COG_COLL_DATE) =:monthNo\r\n" +
            "       AND a.mrf_id = b.mrf_id\r\n" +
            "       AND a.MRF_ID =:mrfId\r\n" +
            "       AND a.ORGID =:orgId", nativeQuery = true)
    List<Object[]> getAllCAndDWasteCenterInputReportDataBy(@Param("orgId") Long orgId, @Param("mrfId") Long mrfId,
            @Param("monthNo") Long monthNo);

}
