
package com.abm.mainet.vehiclemanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleData;



/**
 * The Interface VehicleScheduleRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Repository
public interface VehicleSchedulingRepository extends JpaRepository<VehicleScheduleData, Long> {

    /**
     * get Veheicle Schedule
     * @param orgid
     * @param veheicleId
     * @return
     */
    @Query(" SELECT s FROM VehicleScheduleData s WHERE s.orgid = :orgid AND s.veId = :veheicleId ")
    List<VehicleScheduleData> getVeheicleSchedule(@Param("orgid") Long orgid, @Param("veheicleId") Long veheicleId);

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
	/*
	 * @Query(value = "SELECT a.VEF_DATE,\r\n" + "       (SELECT u.CPD_DESC\r\n" +
	 * "          FROM tb_sw_vehicle_mast z, tb_comparam_det u\r\n" +
	 * "         WHERE z.ve_id = a.VE_ID AND u.cpd_id = z.VE_VETYPE)\r\n" +
	 * "          VechicleType,\r\n" + "       (SELECT z.VE_NO\r\n" +
	 * "          FROM tb_sw_vehicle_mast z\r\n" +
	 * "         WHERE z.ve_id = a.VE_ID)\r\n" + "          VechicleNo,\r\n" +
	 * "       a.VEF_DRIVERNAME,\r\n" + "       a.VEF_DMNO,\r\n" +
	 * "       a.VEF_DMDATE,\r\n" + "       (SELECT cpd_desc\r\n" +
	 * "          FROM tb_comparam_det x\r\n" +
	 * "         WHERE c.pu_fuid = x.cpd_id)\r\n" + "          ItemNameEng,\r\n" +
	 * "       (SELECT cpd_desc_mar\r\n" + "          FROM tb_comparam_det x\r\n" +
	 * "         WHERE c.pu_fuid = x.cpd_id)\r\n" + "          ItemNameREG,\r\n" +
	 * "       b.VEFD_QUANTITY,\r\n" + "       b.VEFD_COST,\r\n" +
	 * "       d.PU_PUMPNAME,\r\n" + "       b.VEFD_UNIT\r\n" +
	 * "  FROM tb_sw_vehiclefuel_mast a,\r\n" +
	 * "       tb_sw_vehiclefuel_det b,\r\n" + "       tb_sw_pump_fuldet c,\r\n" +
	 * "       tb_sw_pump_mast d\r\n" + " WHERE     a.VEF_ID = b.VEF_ID\r\n" +
	 * "       AND c.pfu_id = b.PFU_ID\r\n" + "       AND c.PU_ID = d.PU_ID\r\n" +
	 * "       AND a.ORGID =:orgId\r\n" + "       AND d.pu_id =\r\n" +
	 * "              (CASE\r\n" +
	 * "                  WHEN COALESCE(:pumpId, 0) = 0 THEN COALESCE(d.pu_id, 0)\r\n"
	 * + "                  ELSE COALESCE(:pumpId, 0)\r\n" +
	 * "               END)\r\n" + "       AND a.VE_ID =\r\n" +
	 * "              (CASE\r\n" +
	 * "                  WHEN COALESCE(:veVetype, 0) = 0 THEN COALESCE(a.VE_ID, 0)\r\n"
	 * + "                  ELSE COALESCE(:veVetype, 0)\r\n" +
	 * "               END)\r\n" + "       AND a.VE_VETYPE =\r\n" +
	 * "              (CASE\r\n" +
	 * "                  WHEN COALESCE(:veNo, 0) = 0 THEN COALESCE(a.VE_VETYPE, 0)\r\n"
	 * + "                  ELSE COALESCE(:veNo, 0)\r\n" + "               END)\r\n"
	 * +
	 * "       AND a.VEF_DMDATE BETWEEN :fromdate AND :todate order by a.VEF_DATE ",
	 * nativeQuery = true) List<Object[]> findfuelDetails(@Param("orgId") long
	 * orgId,
	 * 
	 * @Param("veNo") Long veVetype,
	 * 
	 * @Param("veVetype") Long veNo, @Param("fromdate") Date
	 * fromdate, @Param("todate") Date todate,
	 * 
	 * @Param("pumpId") Long pumpId);
	 */

    /**
     * vehicle Shedule Exist
     * @param orgid
     * @param veId
     * @param sheduleDate
     * @param vesStartime
     * @param vesEndtime
     * @return
     */
    @Query(value = "SELECT COUNT(*)\r\n" +
            "  FROM TB_VEHICLE_MAST vm,TB_VM_VEHICLE_SCHEDULING vs,TB_VM_VEHICLE_SCHEDDET vsd \r\n" +
            "  WHERE vm.ve_id = vs.ve_id and vs.vehs_id = vsd.vehs_id and vm.ve_id = :veId \r\n" +
            "       AND vm.ve_no = (select vehm.ve_no from TB_VEHICLE_MAST vehm where vehm.ve_id = :veId) \r\n" +
            "       AND vm.orgid = :orgid \r\n" +
            "       AND vsd.vesd_id <> :vesdId \r\n"+ //this is for self checking while edit
            "       AND vsd.VES_SCHEDULEDT IN (:sheduleDate)\r\n" +
            "       AND (:vesStartime BETWEEN vsd.VES_STARTIME\r\n" +
            "                                         AND vsd.VES_ENDTIME\r\n" +
            "            OR :vesEndtime BETWEEN vsd.VES_STARTIME\r\n" +
            "                                         AND vsd.VES_ENDTIME)", nativeQuery = true)
	Long vehicleSheduleExist(@Param("orgid") Long orgid,
			 @Param("veId")  Long veId,
            @Param("sheduleDate") List<Date> sheduleDate,
            @Param("vesStartime") Date vesStartime, @Param("vesEndtime") Date vesEndtime,@Param("vesdId")Long vesdId);

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
     * find scheduled vehicle Details
     * @param orgId
     * @return
     */
    @Query("select vm.veId,vm.veNo,vs.latitude,vs.longitude from VeVehicleMaster vm , VehicleScheduleData vs where vm.orgid=:orgId AND vm.veId=vs.veId")
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

    @Query(value = "SELECT DISTINCT x.EMPID, x.VE_ID\r\n" +
            "  FROM tb_sw_employee_scheddet x JOIN tb_sw_employee_scheduling y\r\n" +
            " WHERE x.EMS_ID = y.EMS_ID\r\n" +
            "       AND :date BETWEEN y.EMS_FROMDATE AND y.EMS_TODATE\r\n" +
            "       AND x.VE_ID =:veId \r\n" +
            "       AND x.ORGID =:orgId", nativeQuery = true)
    List<Object[]> findVehicleSchdetailsByVehNo(@Param("orgId") Long orgId, @Param("veId") Long veId, @Param("date") Date date);
    
    /*@Query(" SELECT vm.veId, vm.veNo FROM VehicleScheduleData s, VeVehicleMaster vm WHERE s.veId = vm.veId AND s.orgid = :orgid AND s.veId = :veid ")
	List<Object[]> getVehicleByNumberVe(@Param("veid") Long veid, @Param("orgid") Long orgid);
*/

	
}
