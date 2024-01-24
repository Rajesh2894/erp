package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.dto.VendorMasterUploadDto;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorDTO;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.utility.LookUp;

/**
 * Business Service Interface for entity TbAcVendormaster.
 */
public interface TbAcVendormasterService {

    /**
     * Loads an entity from the database using its Primary Key
     * @param vmVendorid
     * @param orgid
     * @return entity
     */
    TbAcVendormaster findById(Long vmVendorid, Long orgid);

    /**
     * Loads all entities.
     * @return all entities
     */
    List<TbAcVendormaster> findAll(Long orgid);

    List<TbAcVendormaster> findAllSechead(Long orgid);

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    TbAcVendormaster update(TbAcVendormaster entity, Organisation orgId, int langId);

    /**
     * Creates the given entity in the database
     * @param entity
     * @return
     */
    TbAcVendormaster create(TbAcVendormaster entity, List<LookUp> venderType, Long secndVendorType, Organisation defaultOrg,
            Organisation org, int langId);

    /**
     * Deletes an entity using its Primary Key
     * @param vmVendorid
     * @param orgid
     */
    void delete(Long vmVendorid, Long orgid);

    /**
     * @param vmpanNumber
     * @param orgId
     * @return
     */

    /**
     * @param vmpanNumber
     * @param orgId
     * @param vmVendorid
     * @return
     */
    List<TbAcVendormaster> getVendorvmPanNumber(String vmpanNumber, Long orgId);

    /**
     * @param cpdVendortype
     * @param cpdVendorSubType
     * @param vendor_vmvendorcode
     * @param vmvendorname
     * @param vmCpdStatus
     * @param orgid
     * @return
     */
    List<TbAcVendormaster> getVendorData(Long cpdVendortype, Long cpdVendorSubType, String vendor_vmvendorcode, Long vmCpdStatus,
            Long orgid);

    /**
     * @param vmpanNumber
     * @param orgId
     * @return
     */

    List<TbAcVendormaster> getActiveVendors(Long orgid, Long vendorStatus);

    /**
     * @param orgId
     * @param vendorId
     * @return
     */
    List<Object[]> getVendorDetails(Long orgId, Long vendorId);

    /**
     * @param vmVendorid
     * @param orgId
     * @return
     */
    String getVendorNameById(Long vmVendorid, Long orgId);

    public String getCpdMode();

    List<TbAcVendormasterEntity> getVendorByPanNo(String vmPanNumber, Long orgId);

    List<TbAcVendormasterEntity> getVendorByMobileNo(String vmMobileNumber, Long orgId);

    List<TbAcVendormasterEntity> getVendorByUidNo(Long uidNo, Long orgId);

    List<TbAcVendormasterEntity> getVendorByVat(String tinNumber, Long orgId);

    List<TbAcVendormasterEntity> getVendorByGst(String gstNo, Long orgId);
    
    List<TbAcVendormaster> getVendorMasterData(Long orgId);

    List<TbAcVendormaster> getActiveStatusVendorsAndSacAcHead(Long orgid, Long vendorStatus, Long sacStatus);

    /**
     * @param orgId
     * @param vendorId
     * @return
     */
    List<TbAcVendormasterEntity> getVendorCodeByVendorId(Long orgId, Long vendorId);

    VendorBillApprovalDTO getVendorNames(final Long orgId, final Long languageId);

    List<VendorDTO> addVendorListData(List<TbAcVendormaster> list);

    List<Object[]> getVendorPhoneNoAndEmailId(Long vendorId, Long orgId);

    /**
     * get All Active vendors which are mapped and non mapped with secondary head code by Organization Id and active status id of
     * vender status prefix 'VSS'
     * @param orgid
     * @param vendorStatus
     * @return List<TbAcVendormaster>
     */
    List<TbAcVendormaster> getAllActiveVendors(Long orgid, Long vendorStatus);

	void saveVendorMasterExcelData(VendorMasterUploadDto vendorMasterUploadDto, Organisation defaultOrg, int langId, List<LookUp> venderType, Long subTypeLookUpId);

	void updateUploadedFileDeleteRecords(List<Long> removeFileById, Long updatedBy);

	void documentUpload(List<DocumentDetailsVO> attach, FileUploadDTO fileUploadDTO, long empId, long orgid);

}
