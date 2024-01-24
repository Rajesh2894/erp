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

import com.abm.mainet.swm.domain.SanitationStaffTarget;
import com.abm.mainet.swm.domain.SanitationStaffTargetDet;

/**
 * The Interface SanitationStaffTargetRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
@Repository
public interface SanitationStaffTargetRepository extends JpaRepository<SanitationStaffTarget, Long> {

    /**
     * find Vehicle Target Datils By VeId
     * @param orgId
     * @param veId
     * @param fromDate
     * @param toDate
     * @return
     */
    @Query(value = "SELECT x.VEHICLE_ID,\r\n" +
            "       (SELECT z.VE_NO\r\n" +
            "          FROM tb_sw_vehicle_mast z\r\n" +
            "         WHERE z.ve_id = x.VEHICLE_ID)\r\n" +
            "          VechicleRegno,\r\n" +
            "       x.TragetVolume,\r\n" +
            "       y.ActualCollection\r\n" +
            "  FROM (SELECT b.VEHICLE_ID, sum(b.SAND_VOLUME) TragetVolume\r\n" +
            "          FROM tb_sw_vehicle_tg a, tb_sw_vehicle_tgdet b\r\n" +
            "         WHERE     a.SAN_ID = b.SAN_ID\r\n" +
            "               AND (a.san_tgfromdt BETWEEN :fromDate AND :toDate)\r\n" +
            "               AND (a.SAN_TGTODT BETWEEN :fromDate AND :toDate)\r\n" +
            "               AND b.VEHICLE_ID =\r\n" +
            "                      (CASE\r\n" +
            "                          WHEN COALESCE(:veId, 0) = 0\r\n" +
            "                          THEN\r\n" +
            "                             COALESCE(b.VEHICLE_ID, 0)\r\n" +
            "                          ELSE\r\n" +
            "                             COALESCE(:veId, 0)\r\n" +
            "                       END)\r\n" +
            "               AND a.ORGID =:orgId\r\n" +
            "        GROUP BY b.VEHICLE_ID) X\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT c.ve_id, sum(c.TRIP_TOTALGARBAGE) ActualCollection\r\n" +
            "          FROM tb_sw_tripsheet c\r\n" +
            "         WHERE     c.TRIP_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "               AND c.ve_id =\r\n" +
            "                      (CASE\r\n" +
            "                          WHEN COALESCE(:veId, 0) = 0 THEN COALESCE(c.ve_id, 0)\r\n" +
            "                          ELSE COALESCE(:veId, 0)\r\n" +
            "                       END)\r\n" +
            "               AND c.ORGID =:orgId\r\n" +
            "        GROUP BY c.ve_id) y\r\n" +
            "          ON x.VEHICLE_ID = y.ve_id", nativeQuery = true)
    List<Object[]> findVehicleTargetDatilsByVeId(@Param("orgId") Long orgId, @Param("veId") Long veId,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    /**
     * get child by Id
     * @param sandId
     * @return
     */
    @Query("select wst from SanitationStaffTargetDet wst where wst.sandId =:sandId")
    SanitationStaffTargetDet getchildbyId(@Param("sandId") Long sandId);

    /**
     * validate Vehicle Target
     * @param vehicleId
     * @param roId
     * @param sandId
     * @param orgid
     * @param sanTgfromdt
     * @param sanTgtodt
     * @return
     */
    @Query(value = "\r\n" +
            "SELECT Count(1)\r\n" +
            "  FROM tb_sw_vehicle_tg e1,  tb_sw_vehicle_tgdet e2\r\n" +
            "	WHERE   e1.SAN_ID=e2.SAN_ID AND e2.VEHICLE_ID =:vehicleId AND e2.BEAT_ID =:roId \r\n" +
            "       AND e1.ORGID =:orgid AND e2.SAND_ID !=:sandId \r\n" +
            "       AND ((:sanTgfromdt BETWEEN e1.SAN_TGFROMDT AND e1.SAN_TGTODT) OR (:sanTgtodt BETWEEN e1.SAN_TGFROMDT AND e1.SAN_TGTODT))", nativeQuery = true)
    int valindateVehicleTarget(@Param("vehicleId") Long vehicleId, @Param("roId") Long roId, @Param("sandId") Long sandId,
            @Param("orgid") Long orgid, @Param("sanTgfromdt") Date sanTgfromdt, @Param("sanTgtodt") Date sanTgtodt);

}
