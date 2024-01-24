/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetInformation;

/**
 * @author satish.rathore
 *
 */
public interface AssetInformationRepo extends PagingAndSortingRepository<AssetInformation, Long>, AssetInformationRepoCustom {

    @Query("select ai from AssetInformation ai where ai.orgId=:orgId")
    List<AssetInformation> findAllInformationListByOrgId(@Param("orgId") Long orgId);

    @Query("select ai from AssetInformation ai where ai.assetId=:assetId")
    AssetInformation findInfoByAssetId(@Param("assetId") Long assetId);

    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and lower(ai.assetName)=:assetName")
    List<AssetInformation> checkDuplicateName(@Param("orgId") Long orgId, @Param("assetName") String assetName);

    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and lower(ai.astCode)=:assetCode")
    AssetInformation getAssetByCode(@Param("orgId") Long orgId, @Param("assetCode") String assetCode);

    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and ai.assetId=:assetId")
    AssetInformation getAssetId(@Param("orgId") Long orgId, @Param("assetId") Long assetId);

    @Modifying
    @Query("UPDATE AssetInformation ai SET ai.assetStatus =:assetStatus where ai.orgId=:orgId and ai.assetId=:assetId")
    void updateStatus(@Param("orgId") Long orgId, @Param("assetStatus") Long assetStatus, @Param("assetId") Long assetId);

    @Query("select ai.serialNo from  AssetInformation ai where ai.appovalStatus ='A' and ai.orgId=:orgId order by ai.serialNo asc ")
    Set<String> getReferenceAsset(@Param("orgId") Long orgId);

    @Modifying
    @Query("UPDATE AssetInformation ai SET ai.employeeId =:employeeId where ai.assetId=:assetId")
    void updateEmployee(@Param("assetId") Long assetId, @Param("employeeId") Long employeeId);

    @Modifying
    @Query("UPDATE AssetInformation ai SET ai.location =:location where ai.assetId=:assetId")
    @Deprecated
    void updateLocation(@Param("assetId") Long assetId, @Param("location") Long location);

    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and ai.assetClass2=:assetClass and ai.assetStatus=:assetStatus and ai.appovalStatus =:appovalStatus")
    List<AssetInformation> getAssetByAssetClass(@Param("orgId") Long orgId, @Param("assetClass") Long assetClass,
            @Param("assetStatus") Long assetStatus, @Param("appovalStatus") String appovalStatus);

    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and lower(ai.rfiId)=:rfiId")
    List<AssetInformation> isDuplicateRfIdNo(@Param("orgId") Long orgId, @Param("rfiId") String rfiId);

    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and ai.assetClass1=:assetClass and ai.assetStatus=:assetStatus")
    List<AssetInformation> fetchAssetInfoByIds(@Param("orgId") Long orgId, @Param("assetStatus") Long assetStatus,
            @Param("assetClass") Long assetClass);

    @Modifying
    @Query("UPDATE AssetInformation ai SET ai.urlParam = NULL where ai.assetId=:assetId and ai.orgId =:orgId")
    void updateUrlParamNullById(@Param("assetId") Long assetId, @Param("orgId") Long orgId);
    
    @Modifying
    @Query("UPDATE AssetInformation ai SET ai.groupRefId =:groupRefId,ai.appovalStatus =:appovalStatus where ai.assetId in(:assetIds) and ai.orgId =:orgId")
    void updateGroupRefId(@Param("assetIds") List<Long> assetIds,@Param("groupRefId") String groupRefId,@Param("appovalStatus")String appovalStatus, @Param("orgId") Long orgId);

     
    @Query("select ai from AssetInformation ai where ai.groupRefId =:groupRefId and ai.orgId=:orgId")
    List<AssetInformation> findAllInformationListByGroupRefId(@Param("groupRefId") String groupRefId, @Param("orgId") Long orgId);
    
    @Query("select count(ai) from AssetInformation ai where ai.orgId=:orgId and ai.assetClass2=:assetClass2 and ai.assetStatus=:assetStatus and ai.astAvlstatus is null")
    Long getAssetCount(@Param("orgId") Long orgId, @Param("assetClass2") Long assetClass2,@Param("assetStatus") Long assetStatus);
    
    @Query("select  DISTINCT ai.astCode from AssetInformation ai where ai.orgId=:orgId and ai.astAvlstatus is null and ai.assetClass2=:assetClass2")
    List<String> getAstCode(@Param("orgId") Long orgId,@Param("assetClass2") Long assetClass2);
    
    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and lower(ai.astCode)=:assetCode and ai.astAvlstatus is null")
    List<AssetInformation> getAstSerialNo(@Param("orgId") Long orgId, @Param("assetCode") String assetCode);
    
    @Transactional
    @Modifying
    
    @Query("UPDATE AssetInformation ai SET ai.employeeId =:employeeId,ai.updatedDate =:updatedDate,ai.astAvlstatus=:depCode,ai.location =:location where ai.astCode=:astCode and ai.serialNo=:serialNo and ai.orgId =:orgId  ")
    void updateEmpDep(@Param("employeeId") Long employeeId,@Param("updatedDate") Date updatedDate,@Param("depCode") String depCode, @Param("location") Long location,  @Param("astCode") String astCode,@Param("serialNo") String serialNo,@Param("orgId") Long orgId );
    
    @Query("select count(ai) from AssetInformation ai where ai.orgId=:orgId and ai.assetClass2=:assetClass2 and ai.assetStatus=:assetStatus")
    Long getAllAssetCount(@Param("orgId") Long orgId, @Param("assetClass2") Long assetClass2,@Param("assetStatus") Long assetStatus);
    
    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and ai.astCode=:assetCode and ai.serialNo=:serialNo")
    AssetInformation getAssetByCodeAndSerialNo(@Param("orgId") Long orgId, @Param("assetCode") String assetCode,@Param("serialNo") String serialNo);

    @Transactional
    @Modifying
    @Query("UPDATE AssetInformation ai SET ai.employeeId =:employeeId,ai.updatedDate =:updatedDate,ai.astAvlstatus=:depCode,ai.location =:location where ai.astCode=:astCode and ai.assetModelIdentifier=:serialNo and ai.orgId =:orgId  ")
    void updateAsssetData(@Param("employeeId") Long employeeId,@Param("updatedDate") Date updatedDate,@Param("depCode") String depCode, @Param("location") Long location,  @Param("astCode") String astCode,@Param("serialNo") String serialNo,@Param("orgId") Long orgId );
    
    
    @Modifying
    @Query("UPDATE AssetInformation ac SET ac.astAvlstatus =:astAvlstatus where ac.assetId.assetId =:assetId")
	void updateDepartment(@Param("assetId") Long assetId, @Param("astAvlstatus") String astAvlstatus);
    
    @Modifying
    @Query("UPDATE AssetInformation ac SET ac.location =:location where ac.assetId.assetId=:assetId")
    void updateLocationId(@Param("assetId") Long assetId, @Param("location") Long location);
    
    
   
    
    @Modifying
    @Query("UPDATE InformationHistory ai SET ai.assetCode =:assetCode where ai.assetId=:assetId  and ai.orgId =:orgId  ")
    void updateAssetCode(@Param("assetCode") String assetCode, @Param("assetId") Long assetId,@Param("orgId") Long orgId );
    
	
    @Modifying
    @Query("UPDATE InformationHistory ai SET ai.employeeId =:employeeId,ai.location =:location where ai.assetCode=:astCode and ai.assetModelIdentifier=:serialNo and ai.orgId =:orgId  ")
    void updateAsssetDatahis(@Param("employeeId") Long employeeId,@Param("location") Long location,@Param("astCode") String astCode,@Param("serialNo") String serialNo,@Param("orgId") Long orgId );
	 
    @Modifying
    @Query("UPDATE InformationHistory ai SET ai.employeeId =:employeeId,ai.location =:location where ai.assetCode=:astCode and ai.serialNo=:serialNo and ai.orgId =:orgId  ")
    void updateEmpDephi(@Param("employeeId") Long employeeId,@Param("location") Long location, @Param("astCode") String astCode,@Param("serialNo") String serialNo,@Param("orgId") Long orgId );
	 
	
    @Modifying
    @Query("UPDATE InformationHistory ai SET ai.employeeId =:employeeId where  ai.assetId=:assetId  and ai.orgId =:orgId")
    void updateAssseHistory(@Param("employeeId") Long employeeId,@Param("assetId") Long assetId,@Param("orgId") Long orgId );

	
   
    @Query("select ai from AssetInformation ai where ai.orgId=:orgId and ai.astCode=:assetCode and ai.assetModelIdentifier=:serialNo ")
    AssetInformation getAssetCodeIdentifier(@Param("orgId") Long orgId, @Param("assetCode") String assetCode,@Param("serialNo") String serialNo);
    
    

    @Modifying
    @Query("UPDATE AssetRequisition ai SET  ai.assetId=:assetId where ai.assetRequisitionId=:assetRequisitionId  and ai.orgId =:orgId  ")
    void updateAssetIdbySerialNo( @Param("assetRequisitionId") Long assetRequisitionId,@Param("orgId") Long orgId,@Param("assetId") String assetId );
    
    @Query("SELECT ti.groupRefId FROM AssetInformation ti WHERE ti.astAppNo = :astAppNo ORDER BY ti.groupRefId DESC")
    String findGroupRefIdByAssetAppNo(@Param("astAppNo") String astAppNo);
    
    
}
