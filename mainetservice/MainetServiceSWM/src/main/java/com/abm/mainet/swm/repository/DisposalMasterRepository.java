package com.abm.mainet.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.DisposalMaster;

/**
 * The Interface DisposalMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
@Repository
public interface DisposalMasterRepository extends JpaRepository<DisposalMaster, Long> {

    @Query(value = "SELECT X.TRIP_DATE,\r\n" +
            "       X.de_id,\r\n" +
            "       y.de_name,\r\n" +
            "       y.DE_CAPACITY,\r\n" +
            "       y.DE_CAPACITY_UNIT,\r\n" +
            "       SUM(X.DRY) DRY,\r\n" +
            "       SUM(X.WATE) WATE\r\n" +
            "  FROM (SELECT a.TRIP_DATE,\r\n" +
            "               a.DE_ID,\r\n" +
            "               x.TRIP_VOLUME AS 'DRY',\r\n" +
            "               0 AS 'WATE'\r\n" +
            "          FROM tb_sw_tripsheet a, tb_sw_tripsheet_gdet x, tb_comparent_det e\r\n" +
            "         WHERE     e.cod_value = 'DW'\r\n" +
            "               AND x.wast_type = e.cod_id\r\n" +
            "               AND x.trip_id = a.trip_id\r\n" +
            "               AND a.TRIP_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "               AND a.de_id =\r\n" +
            "                      (CASE\r\n" +
            "                          WHEN COALESCE(:deName, 0) = 0 THEN COALESCE(a.de_id, 0)\r\n" +
            "                          ELSE COALESCE(:deName, 0)\r\n" +
            "                       END)\r\n" +
            "               AND a.ORGID =:orgId\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT a.TRIP_DATE,\r\n" +
            "               a.de_id,\r\n" +
            "               0 AS 'DRY',\r\n" +
            "               x.TRIP_VOLUME AS 'WATE'\r\n" +
            "          FROM tb_sw_tripsheet a, tb_sw_tripsheet_gdet x, tb_comparent_det e\r\n" +
            "         WHERE     e.cod_value = 'WW'\r\n" +
            "               AND x.wast_type = e.cod_id\r\n" +
            "               AND x.trip_id = a.trip_id\r\n" +
            "               AND a.TRIP_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "               AND a.de_id =\r\n" +
            "                      (CASE\r\n" +
            "                          WHEN COALESCE(:deName, 0) = 0 THEN COALESCE(a.de_id, 0)\r\n" +
            "                          ELSE COALESCE(:deName, 0)\r\n" +
            "                       END)\r\n" +
            "               AND a.ORGID =:orgId) X,\r\n" +
            "       tb_sw_disposal_mast Y\r\n" +
            " WHERE x.de_id = y.de_id\r\n" +
            "GROUP BY X.TRIP_DATE, X.de_id, y.de_name", nativeQuery = true)
    List<Object[]> findVehicleSchedulingDetails(@Param("orgId") long orgId, @Param("deName") Long deName,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    /*
     * @Query("select tb.projCode from TbWmsProjectMaster tb where tb.dpDeptId =:deptId and tb.orgId=:orgId") List<String>
     * getAllProjectCode(@Param("orgId") Long orgId, @Param("deptId") Long deptId);
     */

}
