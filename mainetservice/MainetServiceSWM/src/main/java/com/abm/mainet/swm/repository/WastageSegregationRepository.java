package com.abm.mainet.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.WastageSegregation;

/**
 * The Interface WastageSegregationRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@Repository
public interface WastageSegregationRepository extends JpaRepository<WastageSegregation, Long> {

    /**
     * find Wastage Segregation ReportBy
     * @param OrgId
     * @param deId
     * @param codWast1
     * @param codWast2
     * @param codWast3
     * @param fromDate
     * @param toDate
     * @return
     */
    @Query(value = "SELECT c.MRF_PLATNAME,\r\n" +
            "       a.gr_date,\r\n" +
            "       (SELECT COD_DESC\r\n" +
            "          FROM tb_comparent_det\r\n" +
            "         WHERE cod_id = COD_WAST1)\r\n" +
            "          Waste,\r\n" +
            "       (SELECT COD_DESC\r\n" +
            "          FROM tb_comparent_det\r\n" +
            "         WHERE cod_id = COD_WAST2)\r\n" +
            "          SubTypeWaste,\r\n" +
            "       (SELECT COD_DESC\r\n" +
            "          FROM tb_comparent_det\r\n" +
            "         WHERE cod_id = COD_WAST3)\r\n" +
            "          AS SubTypeWaste1,\r\n" +
            "       (SELECT COD_DESC\r\n" +
            "          FROM tb_comparent_det\r\n" +
            "         WHERE cod_id = COD_WAST4)\r\n" +
            "          AS SubTypeWaste2,\r\n" +
            "       (SELECT COD_DESC\r\n" +
            "          FROM tb_comparent_det\r\n" +
            "         WHERE cod_id = COD_WAST5)\r\n" +
            "          AS SubTypeWaste3,\r\n" +
            "       VOLUME\r\n" +
            "  FROM (SELECT a.MRF_ID,\r\n" +
            "               a.gr_date,\r\n" +
            "               b.COD_WAST1,\r\n" +
            "               b.COD_WAST2,\r\n" +
            "               b.COD_WAST3,\r\n" +
            "               COD_WAST4,\r\n" +
            "               COD_WAST5,\r\n" +
            "               sum(b.TRIP_VOLUME) Volume\r\n" +
            "          FROM TB_SW_WASTESEG a, tb_sw_wasteseg_det b\r\n" +
            "         WHERE     a.gr_id = b.GR_ID\r\n" +
            "               AND a.MRF_ID =\r\n" +
            "                      (CASE\r\n" +
            "                          WHEN COALESCE(:deId, 0) = 0 THEN COALESCE(a.MRF_ID, 0)\r\n" +
            "                          ELSE COALESCE(:deId, 0)\r\n" +
            "                       END)\r\n" +
            "               AND (    (COALESCE(b.cod_WAST1, 0) =\r\n" +
            "                            (CASE\r\n" +
            "                                WHEN COALESCE(:codWast1, 0) = 0\r\n" +
            "                                THEN\r\n" +
            "                                   COALESCE(b.cod_WAST1, 0)\r\n" +
            "                                ELSE\r\n" +
            "                                   COALESCE(:codWast1, 0)\r\n" +
            "                             END))\r\n" +
            "                    AND (COALESCE(b.cod_WAST2, 0) =\r\n" +
            "                            (CASE\r\n" +
            "                                WHEN COALESCE(:codWast2, 0) = 0\r\n" +
            "                                THEN\r\n" +
            "                                   COALESCE(b.cod_WAST2, 0)\r\n" +
            "                                ELSE\r\n" +
            "                                   COALESCE(:codWast2, 0)\r\n" +
            "                             END))\r\n" +
            "                    AND (COALESCE(b.cod_WAST3, 0) =\r\n" +
            "                            (CASE\r\n" +
            "                                WHEN COALESCE(:codWast3, 0) = 0\r\n" +
            "                                THEN\r\n" +
            "                                   COALESCE(b.cod_WAST3, 0)\r\n" +
            "                                ELSE\r\n" +
            "                                   COALESCE(:codWast3, 0)\r\n" +
            "                             END))\r\n" +
            "                    AND (COALESCE(b.cod_WAST4, 0) =\r\n" +
            "                            (CASE\r\n" +
            "                                WHEN COALESCE(NULL, 0) = 0\r\n" +
            "                                THEN\r\n" +
            "                                   COALESCE(b.cod_WAST4, 0)\r\n" +
            "                                ELSE\r\n" +
            "                                   COALESCE(NULL, 0)\r\n" +
            "                             END))\r\n" +
            "                    AND (COALESCE(b.cod_WAST5, 0) =\r\n" +
            "                            (CASE\r\n" +
            "                                WHEN COALESCE(NULL, 0) = 0\r\n" +
            "                                THEN\r\n" +
            "                                   COALESCE(b.cod_WAST5, 0)\r\n" +
            "                                ELSE\r\n" +
            "                                   COALESCE(NULL, 0)\r\n" +
            "                             END)))\r\n" +
            "               AND a.gr_date BETWEEN :fromDate AND :toDate\r\n" +
            "               AND a.ORGID =:OrgId\r\n" +
            "        GROUP BY a.MRF_ID,\r\n" +
            "                 a.gr_date,\r\n" +
            "                 b.COD_WAST1,\r\n" +
            "                 b.COD_WAST2,\r\n" +
            "                 b.COD_WAST3,\r\n" +
            "                 COD_WAST4,\r\n" +
            "                 COD_WAST5) A,\r\n" +
            "       tb_sw_mrf_mast c\r\n" +
            " WHERE a.MRF_ID = c.MRF_ID", nativeQuery = true)
    List<Object[]> findWastageSegregationReportBy(@Param("OrgId") Long OrgId, @Param("deId") Long deId,
            @Param("codWast1") Long codWast1, @Param("codWast2") Long codWast2, @Param("codWast3") Long codWast3,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value = "SELECT DISTINCT a.MRF_ID,\r\n" +
            "                b.MRF_PLATNAME,\r\n" +
            "                day(GR_DATE),\r\n" +
            "                month(GR_DATE),\r\n" +
            "                year(gr_date),\r\n" +
            "                (SELECT cod_desc\r\n" +
            "                   FROM tb_comparent_det\r\n" +
            "                  WHERE cod_id = c.COD_WAST3)\r\n" +
            "                   WasteType,\r\n" +
            "                (CASE\r\n" +
            "                    WHEN (SELECT cod_value\r\n" +
            "                            FROM tb_comparent_det b\r\n" +
            "                           WHERE cod_id = c.COD_WAST1) = 'DW'\r\n" +
            "                    THEN\r\n" +
            "                       c.TRIP_VOLUME\r\n" +
            "                    ELSE\r\n" +
            "                       0\r\n" +
            "                 END)\r\n" +
            "                   Dry,\r\n" +
            "                (CASE\r\n" +
            "                    WHEN (SELECT cod_value\r\n" +
            "                            FROM tb_comparent_det b\r\n" +
            "                           WHERE cod_id = c.COD_WAST1) = 'WW'\r\n" +
            "                    THEN\r\n" +
            "                       c.TRIP_VOLUME\r\n" +
            "                    ELSE\r\n" +
            "                       0\r\n" +
            "                 END)\r\n" +
            "                   Wate,\r\n" +
            "                (CASE\r\n" +
            "                    WHEN (SELECT cod_value\r\n" +
            "                            FROM tb_comparent_det b\r\n" +
            "                           WHERE cod_id = c.COD_WAST1) = 'HZ'\r\n" +
            "                    THEN\r\n" +
            "                       c.TRIP_VOLUME\r\n" +
            "                    ELSE\r\n" +
            "                       0\r\n" +
            "                 END)\r\n" +
            "                   hz\r\n" +
            "  FROM tb_sw_wasteseg a, tb_sw_mrf_mast b, tb_sw_wasteseg_det c\r\n" +
            " WHERE     a.MRF_ID = :mrfId\r\n" +
            "       AND a.MRF_ID = b.mrf_id\r\n" +
            "       AND a.gr_id = c.gr_id\r\n" +
            "       AND month(GR_DATE) =:monthNo\r\n" +
            "       AND a.ORGID =:orgId", nativeQuery = true)
    List<Object[]> findSlrmWastageSegregationReportBy(@Param("orgId") Long orgId, @Param("mrfId") Long mrfId,
            @Param("monthNo") Long monthNo);

}
