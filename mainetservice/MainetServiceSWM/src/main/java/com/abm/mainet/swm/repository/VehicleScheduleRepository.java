
package com.abm.mainet.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.VehicleSchedule;

/**
 * The Interface VehicleScheduleRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Repository
public interface VehicleScheduleRepository extends JpaRepository<VehicleSchedule, Long> {

    /**
     * get Veheicle Schedule
     * @param orgid
     * @param veheicleId
     * @return
     */
    @Query(" SELECT s FROM VehicleSchedule s WHERE s.orgid = :orgid AND s.veId = :veheicleId ")
    List<VehicleSchedule> getVeheicleSchedule(@Param("orgid") Long orgid, @Param("veheicleId") Long veheicleId);

    /**
     * find VehicleScheduling Details
     * @param orgId
     * @param veId
     * @param veNo
     * @param fromdate
     * @param todate
     * @return
     */
    @Query(value = "SELECT c.VE_VETYPE,\r\n" +
            "       (SELECT CPD_DESC\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = c.VE_VETYPE)\r\n" +
            "          VechicleType,\r\n" +
            "       (SELECT CPD_DESC_MAR\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = c.VE_VETYPE)\r\n" +
            "          VechicleTypeMar,\r\n" +
            "       a.VE_ID,\r\n" +
            "       c.VE_NO,\r\n" +
            "       b.BEAT_ID,\r\n" +
            "       d.BEAT_NAME,\r\n" +
            "       d.BEAT_NAME_REG,\r\n" +
            "       (SELECT CPD_DESC\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = b.VES_COLL_TYPE)\r\n" +
            "          CollectionType,\r\n" +
            "       (SELECT CPD_DESC_MAR\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = b.VES_COLL_TYPE)\r\n" +
            "          CollectionTypeMar,\r\n" +
            "       b.VES_STARTIME,\r\n" +
            "       b.VES_ENDTIME,\r\n" +
            "       b.VES_SCHEDULEDT,\r\n" +
            "       b.VES_EMPID\r\n" +
            "  FROM tb_sw_vehicle_scheduling a,\r\n" +
            "       tb_sw_vehicle_scheddet b,\r\n" +
            "       tb_sw_vehicle_mast c,\r\n" +
            "       tb_sw_beat_mast d\r\n" +
            " WHERE     a.ves_id = b.VES_ID\r\n" +
            "       AND a.ve_id = c.VE_ID\r\n" +
            "       AND b.BEAT_ID = d.BEAT_ID\r\n" +
            "       AND c.VE_VETYPE =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:veId, 0) = 0 THEN COALESCE(c.VE_VETYPE, 0)\r\n" +
            "                  ELSE COALESCE(:veId, 0)\r\n" +
            "               END)\r\n" +
            "       AND a.ve_id =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:veNo, 0) = 0 THEN COALESCE(a.ve_id, 0)\r\n" +
            "                  ELSE COALESCE(:veNo, 0)\r\n" +
            "               END)\r\n" +
            "       AND b.VES_SCHEDULEDT BETWEEN :fromdate AND :todate\r\n" +
            "       AND a.orgid =:orgId\r\n" +
            "ORDER BY b.VES_SCHEDULEDT", nativeQuery = true)
    List<Object[]> findVehicleSchedulingDetails(@Param("orgId") long orgId, @Param("veId") Long veId,
            @Param("veNo") Long veNo,
            @Param("fromdate") Date fromdate, @Param("todate") Date todate);

    /**
     * findfuelDetails
     * @param orgId
     * @param veVetype
     * @param veNo
     * @param fromdate
     * @param todate
     * @param pumpId
     * @return
     */
    @Query(value = "SELECT a.VEF_DATE,\r\n" +
            "       (SELECT u.CPD_DESC\r\n" +
            "          FROM tb_sw_vehicle_mast z, tb_comparam_det u\r\n" +
            "         WHERE z.ve_id = a.VE_ID AND u.cpd_id = z.VE_VETYPE)\r\n" +
            "          VechicleType,\r\n" +
            "       (SELECT z.VE_NO\r\n" +
            "          FROM tb_sw_vehicle_mast z\r\n" +
            "         WHERE z.ve_id = a.VE_ID)\r\n" +
            "          VechicleNo,\r\n" +
            "       a.VEF_DRIVERNAME,\r\n" +
            "       a.VEF_DMNO,\r\n" +
            "       a.VEF_DMDATE,\r\n" +
            "       (SELECT cpd_desc\r\n" +
            "          FROM tb_comparam_det x\r\n" +
            "         WHERE c.pu_fuid = x.cpd_id)\r\n" +
            "          ItemNameEng,\r\n" +
            "       (SELECT cpd_desc_mar\r\n" +
            "          FROM tb_comparam_det x\r\n" +
            "         WHERE c.pu_fuid = x.cpd_id)\r\n" +
            "          ItemNameREG,\r\n" +
            "       b.VEFD_QUANTITY,\r\n" +
            "       b.VEFD_COST,\r\n" +
            "       d.PU_PUMPNAME,\r\n" +
            "       b.VEFD_UNIT\r\n" +
            "  FROM tb_sw_vehiclefuel_mast a,\r\n" +
            "       tb_sw_vehiclefuel_det b,\r\n" +
            "       tb_sw_pump_fuldet c,\r\n" +
            "       tb_sw_pump_mast d\r\n" +
            " WHERE     a.VEF_ID = b.VEF_ID\r\n" +
            "       AND c.pfu_id = b.PFU_ID\r\n" +
            "       AND c.PU_ID = d.PU_ID\r\n" +
            "       AND a.ORGID =:orgId\r\n" +
            "       AND d.pu_id =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:pumpId, 0) = 0 THEN COALESCE(d.pu_id, 0)\r\n" +
            "                  ELSE COALESCE(:pumpId, 0)\r\n" +
            "               END)\r\n" +
            "       AND a.VE_ID =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:veVetype, 0) = 0 THEN COALESCE(a.VE_ID, 0)\r\n" +
            "                  ELSE COALESCE(:veVetype, 0)\r\n" +
            "               END)\r\n" +
            "       AND a.VE_VETYPE =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:veNo, 0) = 0 THEN COALESCE(a.VE_VETYPE, 0)\r\n" +
            "                  ELSE COALESCE(:veNo, 0)\r\n" +
            "               END)\r\n" +
            "       AND a.VEF_DMDATE BETWEEN :fromdate AND :todate order by a.VEF_DATE ", nativeQuery = true)
    List<Object[]> findfuelDetails(@Param("orgId") long orgId,
            @Param("veNo") Long veVetype,
            @Param("veVetype") Long veNo, @Param("fromdate") Date fromdate, @Param("todate") Date todate,
            @Param("pumpId") Long pumpId);

    
    /**
     * for PSCL Workshop And Fleet Mgnt
     */
    @Query(value = 
    		"SELECT a.VEF_DATE, (SELECT u.CPD_DESC FROM TB_VEHICLE_MAST z, tb_comparam_det u WHERE z.ve_id = a.VE_ID AND "
    		+ "u.cpd_id = z.VE_VETYPE) VechicleType, (SELECT z.VE_NO FROM TB_VEHICLE_MAST z WHERE z.ve_id = a.VE_ID) VechicleNo, "
    		+ "(SELECT ve.VM_EMPNAME FROM TB_VM_EMPLOYEE ve WHERE ve.VM_EMPID = a.VEF_DRIVERNAME) VEF_DRIVERNAME, "
    		+ " a.VEF_DMNO, a.VEF_DMDATE, (SELECT cpd_desc FROM tb_comparam_det x WHERE c.pu_fuid = x.cpd_id) "
    		+ "ItemNameEng, (SELECT cpd_desc_mar FROM tb_comparam_det x WHERE c.pu_fuid = x.cpd_id) ItemNameREG, b.VEFD_QUANTITY, "
    		+ "b.VEFD_COST, d.PU_PUMPNAME, b.VEFD_UNIT "
    		+ "FROM TB_VM_VEHICLEFUEL_MAST a,   TB_VM_VEHICLEFUEL_DET b,  TB_VM_PUMP_FULDET c, tb_vm_pump_mast d "
    		+ "WHERE a.VEHF_ID = b.VEHF_ID AND c.VFU_ID = b.VFU_ID  AND c.FPM_ID = d.FPM_ID AND a.ORGID =:orgId  " 
    		+ "AND d.FPM_ID = (CASE WHEN COALESCE(:pumpId, 0) = 0 THEN COALESCE(d.FPM_ID, 0) ELSE COALESCE(0, 0) END) "
    		+ "AND a.VE_ID =  (CASE  WHEN COALESCE(:veNo, 0) = 0 THEN COALESCE(a.VE_ID, 0) ELSE COALESCE(0, 0)  END) "
    		+ "AND a.VE_VETYPE = (CASE WHEN COALESCE(:veVetype, 0) = 0 THEN COALESCE(a.VE_VETYPE, 0) ELSE COALESCE(0, 0)  END) "
    		+ "AND a.VEF_DMDATE BETWEEN :fromdate AND :todate order by a.VEF_DATE " , nativeQuery = true)
    List<Object[]> findfuelDetailsForWorkshopAndFleetMgnt(@Param("orgId") long orgId, @Param("veVetype") Long veVetype,
            @Param("veNo") Long veNo, @Param("fromdate") Date fromdate, @Param("todate") Date todate, @Param("pumpId") Long pumpId);
   
    
    /**
     * vehicle Shedule Exist
     * @param orgid
     * @param routeId
     * @param sheduleDate
     * @param vesStartime
     * @param vesEndtime
     * @return
     */
    @Query(value = "SELECT COUNT(*)\r\n" +
            "  FROM tb_sw_vehicle_scheduling e1, tb_sw_vehicle_scheddet e2\r\n" +
            " WHERE     e1.VES_ID = e2.VES_ID\r\n" +
            "       AND e1.ORGID =:orgid\r\n" +
            "       AND e2.BEAT_ID =:routeId\r\n" +
            "       AND e2.VES_SCHEDULEDT IN (:sheduleDate)\r\n" +
            "       AND (:vesStartime BETWEEN e2.VES_STARTIME\r\n" +
            "                                         AND e2.VES_ENDTIME\r\n" +
            "            OR :vesEndtime BETWEEN e2.VES_STARTIME\r\n" +
            "                                         AND e2.VES_ENDTIME)", nativeQuery = true)
    Long vehicleSheduleExist(@Param("orgid") Long orgid, @Param("routeId") Long routeId,
            @Param("sheduleDate") List<Date> sheduleDate,
            @Param("vesStartime") Date vesStartime, @Param("vesEndtime") Date vesEndtime);

    /**
     * find Maintenance Details
     * @param orgId
     * @param veId
     * @param veNo
     * @param fromdate
     * @param todate
     * @param vemMetype
     * @return
     */
    @Query(value = "SELECT m.VEM_DATE,\r\n" +
            "       (SELECT u.CPD_DESC\r\n" +
            "          FROM tb_sw_vehicle_mast z, tb_comparam_det u\r\n" +
            "         WHERE z.ve_id = m.VE_ID AND u.cpd_id = z.VE_VETYPE)\r\n" +
            "          VechicleType,\r\n" +
            "       (SELECT z.VE_NO\r\n" +
            "          FROM tb_sw_vehicle_mast z\r\n" +
            "         WHERE z.ve_id = m.VE_ID)\r\n" +
            "          VechicleNo,\r\n" +
            "       m.VEM_DOWNTIME,\r\n" +
            "       m.VEM_DOWNTIMEUNIT,\r\n" +
            "       m.VEM_READING,\r\n" +
            "       m.VEM_COSTINCURRED\r\n" +
            "  FROM tb_sw_veremen_mast m\r\n" +
            " WHERE     m.ORGID =:orgId\r\n" +
            "       AND m.VEM_METYPE =:vemMetype\r\n" +
            "       AND m.VE_VETYPE =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:veId, 0) = 0 THEN COALESCE(m.VE_VETYPE, 0)\r\n" +
            "                  ELSE COALESCE(:veId, 0)\r\n" +
            "               END)\r\n" +
            "       AND m.VE_ID =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:veNo, 0) = 0 THEN COALESCE(m.VE_ID, 0)\r\n" +
            "                  ELSE COALESCE(:veNo, 0)\r\n" +
            "               END)\r\n" +
            "       AND m.VEM_DATE BETWEEN :fromdate AND :todate order by m.VEM_DATE", nativeQuery = true)

    List<Object[]> findMaintenanceDetails(@Param("orgId") Long orgId, @Param("veId") Long veId, @Param("veNo") Long veNo,
            @Param("fromdate") Date fromdate, @Param("todate") Date todate,
            @Param("vemMetype") Long vemMetype);

    /**
     * for PSCL Workshop And Fleet Mgnt
     */
    @Query(value = "SELECT m.VEM_DATE, (SELECT u.CPD_DESC FROM TB_VEHICLE_MAST z, tb_comparam_det u WHERE z.ve_id = m.VE_ID "
    		+ "AND u.cpd_id = z.VE_VETYPE)  VechicleType, (SELECT z.VE_NO FROM TB_VEHICLE_MAST z  WHERE z.ve_id = m.VE_ID) "
    		+ "VechicleNo,   m.VEM_DOWNTIME, m.VEM_DOWNTIMEUNIT, m.VEM_READING, m.VEM_COSTINCURRED FROM TB_VM_VEREMEN_MAST m "
    		+ "WHERE m.ORGID =:orgId AND m.VEM_METYPE =:vemMetype "
    		+ "AND m.VE_VETYPE = (CASE  WHEN COALESCE(:veId, 0) = 0 THEN COALESCE(m.VE_VETYPE, 0) ELSE COALESCE(:veId, 0) END)  "
    		+ "AND m.VE_ID = (CASE WHEN COALESCE(:veNo, 0) = 0 THEN COALESCE(m.VE_ID, 0) ELSE COALESCE(:veNo, 0) END) "
    		+ "AND m.WF_STATUS=:status AND m.VEM_DATE BETWEEN :fromdate AND :todate order by m.VEM_DATE", nativeQuery = true)
    List<Object[]> findMaintenanceDetailsForWorkshopAndFleetMgnt(@Param("orgId") Long orgId, @Param("veId") Long veId, @Param("veNo") Long veNo,
            @Param("fromdate") Date fromdate, @Param("todate") Date todate, @Param("vemMetype") Long vemMetype, @Param("status") String status);
    
    
    /**
     * find scheduled vehicle Details
     * @param orgId
     * @return
     */
    @Query("select vm.veId,vm.veNo,vs.latitude,vs.longitude from VehicleMaster vm , VehicleSchedule vs where vm.orgid=:orgId AND vm.veId=vs.veId")
    List<Object[]> findscheduledvehicleDetails(@Param("orgId") Long orgId);

    @Query(value = "SELECT a.beat_id,\r\n" +
            "       b.BEAT_NAME,\r\n" +
            "       b.BEAT_NAME_REG,\r\n" +
            "       a.esd_scheduledate,\r\n" +
            "       (SELECT d.SW_EMPNAME\r\n" +
            "          FROM tb_sw_employee d\r\n" +
            "         WHERE d.SW_EMPID = a.EMPID)\r\n" +
            "          EmployeeName,\r\n" +
            "       a.EMSD_STARTTIME,\r\n" +
            "       a.EMSD_ENDTIME,\r\n" +
            "       (SELECT cpd_value\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = a.EMSD_COLL_TYPE)\r\n" +
            "          CollectionType1,\r\n" +
            "       (SELECT cpd_desc\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = a.EMSD_COLL_TYPE)\r\n" +
            "          CollectionType2,\r\n" +
            "       (SELECT cpd_desc_mar\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = a.EMSD_COLL_TYPE)\r\n" +
            "          CollectionRegType3,\r\n" +
            "       (SELECT cpd_desc\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = a.ESD_SHIFTID)\r\n" +
            "          ShiftDesc,\r\n" +
            "       (SELECT cpd_desc_mar\r\n" +
            "          FROM tb_comparam_det\r\n" +
            "         WHERE cpd_id = a.ESD_SHIFTID)\r\n" +
            "          ShiftRegDesc\r\n" +
            "  FROM tb_sw_employee_scheddet a,\r\n" +
            "       tb_sw_beat_mast b,\r\n" +
            "       tb_sw_employee_scheduling c\r\n" +
            " WHERE     a.beat_id = b.beat_id\r\n" +
            "       AND a.ems_id = c.ems_id\r\n" +
            "       AND c.ems_type = 'V'\r\n" +
            "       AND a.orgid =:orgId\r\n" +
            "       AND a.beat_id =:beatId\r\n" +
            "       AND extract(MONTH FROM a.esd_scheduledate) =:monthNo", nativeQuery = true)
    List<Object[]> findSweepingDetails(@Param("orgId") Long orgId, @Param("beatId") Long beatId, @Param("monthNo") Long monthNo);

    @Query(value = "SELECT DISTINCT x.VES_EMPID, y.VE_ID\r\n" +
            "  FROM tb_sw_vehicle_scheddet x JOIN tb_sw_vehicle_scheduling y\r\n" +
            " WHERE x.VES_ID = y.VES_ID\r\n" +
            "       AND :date BETWEEN y.VES_FROMDT AND y.VES_TODT\r\n" +
            "       AND y.VE_ID =:veId \r\n" +
            "       AND y.ORGID =:orgId", nativeQuery = true)
    List<Object[]> findVehicleSchdetailsByVehNo(@Param("orgId") Long orgId, @Param("veId") Long veId, @Param("date") Date date);
}
