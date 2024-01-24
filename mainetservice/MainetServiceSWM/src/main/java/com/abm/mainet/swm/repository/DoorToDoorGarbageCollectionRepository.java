package com.abm.mainet.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.DoorToDoorGarbageCollection;

/**
 * The Interface DoorToDoorGarbageCollectionRepositor.
 *
 * @author Lalit.Prusti Created
 * 
 * Date : 29-June-2018
 */
@Repository
public interface DoorToDoorGarbageCollectionRepository extends JpaRepository<DoorToDoorGarbageCollection, Long> {

    List<DoorToDoorGarbageCollection> findByOrgidAndDgcDateAfterAndDgcDateBefore(Long orgid, Date fromDate, Date toDate);

    @Query(value = "select a.loc_id,\r\n" +
            "a.LOC_NAME_ENG, \r\n" +
            "a.LOC_NAME_REG,\r\n" +
            "a.LOC_ADDRESS,\r\n" +
            "a.LOC_ADDRESS_REG,\r\n" +
            "(select cod_desc from tb_comparent_det where cod_id=d.cod_ward1) W1_eng,\r\n" +
            "(select cod_desc from tb_comparent_det where cod_id=d.cod_ward2) W2_eng,\r\n" +
            "(select cod_desc from tb_comparent_det where cod_id=d.cod_ward3) W3_eng,\r\n" +
            "(select cod_desc from tb_comparent_det where cod_id=d.cod_ward4) W4_eng,\r\n" +
            "(select COD_DESC_MAR from tb_comparent_det where cod_id=d.cod_ward1) W1_reg,\r\n" +
            "(select COD_DESC_MAR from tb_comparent_det where cod_id=d.cod_ward2) W2_reg,\r\n" +
            "(select COD_DESC_MAR from tb_comparent_det where cod_id=d.cod_ward3) W3_reg,\r\n" +
            "(select COD_DESC_MAR from tb_comparent_det where cod_id=d.cod_ward4) W4_reg,\r\n" +
            "d.su_mobileno,\r\n" +
            "d.su_name,\r\n" +
            "d.su_president,\r\n" +
            "d.su_commiteename,\r\n" +
            "d.su_remark\r\n" +
            "from \r\n" +
            "tb_location_mas a,\r\n" +
            "tb_comparam_mas b,\r\n" +
            "tb_comparam_det c,\r\n" +
            "tb_sw_survey_mast d\r\n" +
            "where a.loc_cat=c.cpd_id and\r\n" +
            "b.cpm_id=c.cpm_id and\r\n" +
            "b.cpm_prefix='LCT' and \r\n" +
            "c.cpd_value=:ptype and\r\n" +
            "a.loc_id=d.loc_id and\r\n" +
            "d.ORGID=:orgId and\r\n" +
            "d.su_date between :fromDate and :toDate", nativeQuery = true)
    List<Object[]> findAllAreaWiseSurveyData(@Param("orgId") long orgId, @Param("ptype") String ptype,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
