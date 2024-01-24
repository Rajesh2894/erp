package com.abm.mainet.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.MRFMaster;

/**
 * The Interface MRFMasterRepository
 * @author Ajay.Kumar
 *
 */
@Repository
public interface MRFMasterRepository extends JpaRepository<MRFMaster, Long> {

    @Query(value = "SELECT X.TRIP_DATE,\r\n" +
            "       X.MRF_ID,\r\n" +
            "       y.MRF_PLATNAME,\r\n" +
            "       y.MRF_PLANTCAP,\r\n" +
            "       SUM(X.DRY) DRY,\r\n" +
            "       SUM(X.WATE) WATE,\r\n" +
            "       SUM(X.Hazardous) Hazardous\r\n" +
            "  FROM (SELECT a.MRF_ID,\r\n" +
            "               a.TRIP_DATE AS TRIP_DATE,\r\n" +
            "               (CASE WHEN e.COD_VALUE = 'DW' THEN x.TRIP_VOLUME ELSE 0 END)\r\n" +
            "                  AS DRY,\r\n" +
            "               (CASE WHEN e.COD_VALUE = 'WW' THEN x.TRIP_VOLUME ELSE 0 END)\r\n" +
            "                  AS WATE,\r\n" +
            "               (CASE WHEN e.COD_VALUE = 'HZ' THEN x.TRIP_VOLUME ELSE 0 END)\r\n" +
            "                  AS Hazardous\r\n" +
            "          FROM tb_sw_tripsheet a, tb_sw_tripsheet_gdet x, tb_comparent_det e\r\n" +
            "         WHERE     x.WAST_TYPE = e.COD_ID\r\n" +
            "               AND x.TRIP_ID = a.TRIP_ID\r\n" +
            "               AND a.TRIP_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "               AND a.MRF_ID =\r\n" +
            "                      (CASE\r\n" +
            "                          WHEN COALESCE(:mrfId, 0) = 0 THEN COALESCE(a.MRF_ID, 0)\r\n" +
            "                          ELSE COALESCE(:mrfId, 0)\r\n" +
            "                       END)\r\n" +
            "               AND a.ORGID =:orgId) X,\r\n" +
            "       tb_sw_mrf_mast Y\r\n" +
            " WHERE x.MRF_ID = y.MRF_ID\r\n" +
            "GROUP BY X.TRIP_DATE, X.MRF_ID, y.MRF_PLATNAME", nativeQuery = true)
    List<Object[]> findVehicleSchedulingDetails(@Param("orgId") Long orgId, @Param("mrfId") Long mrfId,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
