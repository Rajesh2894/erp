/*package com.abm.mainet.vehicle.management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.vehicle.management.domain.FmVendorContractMapping;

*//**
 * The Interface VendorContractMappingRepository.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 30-May-2018
 *//*
@Repository
public interface GenVendorContractMappingRepository extends JpaRepository<FmVendorContractMapping, Long> {

    *//**
     * find Contract Dept Wise Not Mapped
     * @param orgId
     * @param deptId
     * @return
     *//*
    @Query("FROM ContractMastEntity c WHERE c.orgId =?1 AND c.contDept = ?2 AND c.contMapFlag IS NULL ")
    List<ContractMastEntity> findContractDeptWiseNotMapped(Long orgId, Long deptId);

    *//**
     * find By ContId And Orgid
     * @param contId
     * @param orgid
     * @return
     *//*
    List<FmVendorContractMapping> findByContIdAndOrgid(Long contId, Long orgid);

    *//**
     * find Contract Dept Wise Mapped
     * @param orgId
     * @param deptId
     * @return
     *//*
    @Query("FROM ContractMastEntity c WHERE c.orgId =?1 AND c.contDept = ?2 AND c.contMapFlag = 'Y' ")
    List<ContractMastEntity> findContractDeptWiseMapped(Long orgId, Long deptId);

    *//**
     * get Count Contract Dept Wise Not Mapped
     * @param orgId
     * @param deptId
     * @return
     *//*
    @Query("SELECT COUNT(c) FROM ContractMastEntity c WHERE c.orgId =?1 AND c.contDept = ?2 AND c.contMapFlag IS NULL ")
    Long getCountContractDeptWiseNotMapped(Long orgId, Long deptId);

    *//**
     * find Contracts Exist
     * @param orgId
     * @param deptId
     * @param contNo
     * @param contdate
     * @return
     *//*
    @Query("from ContractMastEntity c where c.orgId =?1 and c.contDept = ?2 and (c.contNo = ?3 or c.contDate =?4 ) and c.contMapFlag = 'Y' ")
    List<ContractMastEntity> findContractsExist(Long orgId, Long deptId, String contNo, Date contdate);

    *//**
     * find Desig Name ById
     * @param desigId
     * @return
     *//*
    @Query("select d.dsgname from Designation d where d.dsgid=:desigId")
    String findDesigNameById(@Param("desigId") Long desigId);

    *//**
     * find Contract Exit Or Not
     * @param codWard1
     * @param codWard2
     * @param codWard3
     * @param codWard4
     * @param codWard5
     * @param mapTaskId
     * @param mapWastetype
     * @param roId
     * @param orgid
     * @return
     *//*
    @Query(value = "SELECT COUNT(*)\r\n" +
            "  FROM TB_SW_CONTVEND_MAPPING c\r\n" +
            " WHERE     COALESCE(c.COD_WARD1, 0) =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:codWard1, 0) = 0 THEN COALESCE(c.COD_WARD1, 0)\r\n" +
            "                  ELSE COALESCE(:codWard1, 0)\r\n" +
            "               END)\r\n" +
            "       AND COALESCE(c.COD_WARD2, 0) =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:codWard2, 0) = 0 THEN COALESCE(c.COD_WARD2, 0)\r\n" +
            "                  ELSE COALESCE(:codWard2, 0)\r\n" +
            "               END)\r\n" +
            "       AND COALESCE(c.COD_WARD3, 0) =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:codWard3, 0) = 0 THEN COALESCE(c.COD_WARD3, 0)\r\n" +
            "                  ELSE COALESCE(:codWard3, 0)\r\n" +
            "               END)\r\n" +
            "       AND COALESCE(c.COD_WARD4, 0) =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:codWard4, 0) = 0 THEN COALESCE(c.COD_WARD4, 0)\r\n" +
            "                  ELSE COALESCE(:codWard4, 0)\r\n" +
            "               END)\r\n" +
            "       AND COALESCE(c.COD_WARD5, 0) =\r\n" +
            "              (CASE\r\n" +
            "                  WHEN COALESCE(:codWard5, 0) = 0 THEN COALESCE(c.COD_WARD5, 0)\r\n" +
            "                  ELSE COALESCE(:codWard5, 0)\r\n" +
            "               END)\r\n" +
            "       AND c.MAP_TASK_ID =:mapTaskId\r\n" +
            "       AND c.MAP_WASTETYPE =:mapWastetype\r\n" +
            "       AND c.BEAT_ID=:roId\r\n" +
            "       AND c.ORGID =:orgid", nativeQuery = true)
    Long findContractExitOrNot(@Param("codWard1") Long codWard1,
            @Param("codWard2") Long codWard2,
            @Param("codWard3") Long codWard3, @Param("codWard4") Long codWard4,
            @Param("codWard5") Long codWard5,
            @Param("mapTaskId") String mapTaskId,
            @Param("mapWastetype") Long mapWastetype,
            @Param("roId") Long roId,
            @Param("orgid") Long orgid);
}
*/