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

import com.abm.mainet.swm.domain.EmployeeSchedule;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;

/**
 * The Interface EmployeeScheduleRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Repository
public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, Long> {

    @Query(value = "SELECT b.EMPID,\r\n" +
            "       concat(COALESCE(c.SW_EMPNAME, ' '),' ',\r\n" +
            "              COALESCE(c.SW_EMPMNAME, ' '),' ',\r\n" +
            "              COALESCE(c.SW_EMPLNAME, ' '))\r\n" +
            "          EmployeeName,\r\n" +
            "       c.SW_EMPNAME,\r\n" +
            "       a.EMS_TYPE,\r\n" +
            "       a.EMS_REOCC,\r\n" +
            "       b.MRF_ID,\r\n" +
            "       (SELECT x.MRF_PLATNAME\r\n" +
            "          FROM tb_sw_mrf_mast x\r\n" +
            "         WHERE x.MRF_ID = b.MRF_ID)\r\n" +
            "          CenterName,\r\n" +
            "       LOC_ID,\r\n" +
            "       (SELECT y.LOC_NAME_ENG\r\n" +
            "          FROM tb_location_mas y\r\n" +
            "         WHERE y.loc_id = b.LOC_ID)\r\n" +
            "          LocationName,\r\n" +
            "       VE_ID,\r\n" +
            "       (SELECT z.VE_NO\r\n" +
            "          FROM tb_sw_vehicle_mast z\r\n" +
            "         WHERE z.ve_id = b.VE_ID)\r\n" +
            "          VechicleRegno,\r\n" +
            "       b.COD_WARD1,\r\n" +
            "       (SELECT p.COD_DESC\r\n" +
            "          FROM tb_comparent_det p\r\n" +
            "         WHERE p.cod_id = b.COD_WARD1)\r\n" +
            "          Ward1,\r\n" +
            "       b.COD_WARD2,\r\n" +
            "       (SELECT p.COD_DESC\r\n" +
            "          FROM tb_comparent_det p\r\n" +
            "         WHERE p.cod_id = b.COD_WARD2)\r\n" +
            "          Ward2,\r\n" +
            "       b.COD_WARD3,\r\n" +
            "       (SELECT p.COD_DESC\r\n" +
            "          FROM tb_comparent_det p\r\n" +
            "         WHERE p.cod_id = b.COD_WARD3)\r\n" +
            "          Ward3,\r\n" +
            "       b.COD_WARD4,\r\n" +
            "       (SELECT p.COD_DESC\r\n" +
            "          FROM tb_comparent_det p\r\n" +
            "         WHERE p.cod_id = b.COD_WARD4)\r\n" +
            "          Ward4,\r\n" +
            "       b.COD_WARD5,\r\n" +
            "       (SELECT p.COD_DESC\r\n" +
            "          FROM tb_comparent_det p\r\n" +
            "         WHERE p.cod_id = b.COD_WARD5)\r\n" +
            "          Ward5,\r\n" +
            "       b.EMSD_STARTTIME,\r\n" +
            "       b.EMSD_ENDTIME,\r\n" +
            "       b.ESD_SCHEDULEDATE,\r\n" +
            "       b.ESD_SHIFTID,\r\n" +
            "       b.EMSD_COLL_TYPE\r\n" +
            "  FROM tb_sw_employee_scheduling a, tb_sw_employee_scheddet b, tb_sw_employee c\r\n" +
            " WHERE     a.EMS_ID = b.EMS_ID\r\n" +
            "       AND b.empid = c.SW_EMPID\r\n" +
            "       AND a.orgid =:orgId\r\n" +
            "       AND a.EMS_TYPE =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:scheduleType, 'X') = 'X'\r\n" +
            "                  THEN\r\n" +
            "                     COALESCE(a.EMS_TYPE, 'X')\r\n" +
            "                  ELSE\r\n" +
            "                     COALESCE(:scheduleType, 'X')\r\n" +
            "               END)\r\n" +
            "       AND b.empid =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:empId, 0) = 0 THEN COALESCE(b.empid, 0)\r\n" +
            "                  ELSE COALESCE(:empId, 0)\r\n" +
            "               END)\r\n" +
            "       AND b.ESD_SCHEDULEDATE BETWEEN :fromDate AND :toDate\r\n" +
            "ORDER BY b.ESD_SCHEDULEDATE", nativeQuery = true)
    List<Object[]> employeeScheduleDetails(@Param("orgId") Long orgId, @Param("empId") Long empId,
            @Param("scheduleType") String scheduleType, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("select e1.empid,\r\n" +
            "        e1.emsdStarttime,\r\n" +
            "        e1.emsdEndtime,\r\n" +
            "        e1.esdScheduledate\r\n" +
            "        from EmployeeScheduleDetail e1 where e1.empid=:employeeId and e1.orgid=:orgId")
    List<EmployeeScheduleDTO> getAllScheduledEmp(@Param("employeeId") Long employeeId, @Param("orgId") Long orgId);

    @Query(value = "SELECT count(*)\r\n" +
            "  FROM TB_SW_EMPLOYEE_SCHEDULING e1, TB_SW_EMPLOYEE_SCHEDDET e2\r\n" +
            " WHERE     e1.EMS_ID = e2.EMS_ID\r\n" +
            "       AND e2.EMPID =:empid\r\n" +
            "       AND e2.orgid =:orgid\r\n" +
            "       AND e2.ESD_SCHEDULEDATE IN (:sheduleDate)\r\n" +
            "       AND (:emsdStarttime BETWEEN e2.EMSD_STARTTIME\r\n" +
            "                                         AND e2.EMSD_ENDTIME\r\n" +
            "            OR :emsdEndtime BETWEEN e2.EMSD_STARTTIME\r\n" +
            "                                         AND e2.EMSD_ENDTIME)", nativeQuery = true)
    Long findEmployeSchedule(@Param("empid") Long empid, @Param("orgid") Long orgid, @Param("sheduleDate") List<Date> sheduleDate,
            @Param("emsdStarttime") Date emsdStarttime,
            @Param("emsdEndtime") Date emsdEndtime);

}
