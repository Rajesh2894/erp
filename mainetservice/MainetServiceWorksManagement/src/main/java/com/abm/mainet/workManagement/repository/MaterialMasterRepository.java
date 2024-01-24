package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.TbWmsError;
import com.abm.mainet.workManagement.domain.TbWmsMaterialMaster;

@Repository
public interface MaterialMasterRepository extends CrudRepository<TbWmsMaterialMaster, Long> {

    /**
     * used to get Material List By SorId
     * 
     * @param sorId
     * @return
     */
    @Query("SELECT mm FROM TbWmsMaterialMaster mm  where mm.sorId.sorId = :sorId and maActive= 'Y' ")
    List<TbWmsMaterialMaster> getMaterialListBySorId(@Param("sorId") Long sorId);

    /**
     * used to update Deleted Flag for rate master
     * 
     * @param deletedMaterialId
     */
    @Modifying
    @Query("update TbWmsMaterialMaster set maActive = 'N'  where maId in :deletedMaterialId")
    void updateDeletedFlag(@Param("deletedMaterialId") List<Long> deletedMaterialId);

    /**
     * used to delete Material By SOR id
     * 
     * @param sorId
     */
    @Modifying
    @Query("update TbWmsMaterialMaster set maActive = 'N'  where sorId.sorId =:sorId")
    void deleteMaterialById(@Param("sorId") Long sorId);

    /**
     * used to find All Active Material By SOR Master
     * 
     * @param orgId
     * @return
     */
    @Query("SELECT DISTINCT mm FROM TbWmsMaterialMaster mm  where  maActive= 'Y' and  orgId= :orgId")
    List<TbWmsMaterialMaster> findAllActiveMaterialBySorMas(@Param("orgId") long orgId);

    /**
     * used to check Duplicate Excel Data
     * 
     * @param maTypeId
     * @param maItemNo
     * @param sorId
     * @param orgId
     * @return
     */
    @Query("SELECT DISTINCT mm FROM TbWmsMaterialMaster mm  where  maActive= 'Y' and sorId.sorId= :sorId and orgId= :orgId and maTypeId=:maTypeId and maItemNo=:maItemNo")
    List<TbWmsMaterialMaster> checkDuplicateExcelData(@Param("maTypeId") Long maTypeId,
            @Param("maItemNo") String maItemNo, @Param("sorId") Long sorId, @Param("orgId") long orgId);

    /**
     * used to delete Error Log
     * 
     * @param orgId
     * @param masterType
     */
    @Modifying
    @Query("DELETE FROM TbWmsError d WHERE d.errFlag= :errFlag and d.orgId= :orgId")
    void deleteErrorLog(@Param("orgId") Long orgId, @Param("errFlag") String masterType);

    /**
     * used to get Error Log
     * 
     * @param orgId
     * @param masterType
     * @return
     */
    @Query("SELECT mm FROM TbWmsError mm  where mm.orgId = :orgId and mm.errFlag= :errFlag")
    List<TbWmsError> getErrorLog(@Param("orgId") Long orgId, @Param("errFlag") String masterType);

}
