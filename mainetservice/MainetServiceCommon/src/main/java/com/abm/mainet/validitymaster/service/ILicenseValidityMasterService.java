package com.abm.mainet.validitymaster.service;

import java.util.List;

import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;

/**
 * @author cherupelli.srikanth
 * @since 17 september 2019
 */

public interface ILicenseValidityMasterService {

	/**
	 * This method is used to save the License Validity Data
	 * 
	 * @param masterDto
	 */
	void saveLicenseValidityData(LicenseValidityMasterDto masterDto);

	/**
	 * This method is used to update the License Validity Data
	 * 
	 * @param masterDto
	 */
	void updateLicenseValidityData(LicenseValidityMasterDto masterDto);

	/**
	 * This method is used to fetch the list of license validity data by either
	 * orgId or deptId or serviceId
	 * 
	 * @param orgId
	 * @param deptId
	 * @param serviceId
	 * @param triCod1 
	 * @return LicenseValidityMasterDto list
	 */
	List<LicenseValidityMasterDto> searchLicenseValidityData(Long orgId, Long deptId, Long serviceId, Long triCod1,Long licType);

	/**
	 * This method is used to fetch the license validity data by licId and orgId
	 * 
	 * @param licId
	 * @param orgId
	 * @return list of LicenseValidityMasterDto
	 */
	LicenseValidityMasterDto searchLicenseValidityByLicIdAndOrgId(Long licId, Long orgId);

	/**
	 * This method is used to fetch the license validity data by licId and orgId
	 * 
	 * @param orgId
	 * @param deptId
	 * @param serviceId
	 * @return LicenseValidityMasterDto
	 */
	LicenseValidityMasterDto getLicValDataByDeptIdAndServiceId(Long orgId, Long deptId, Long serviceId,
			Long licenceTypeId);

	/**
	 * This method is used to fetch the license validity data by License catagory
	 * and subCategory
	 * 
	 * @param orgId
	 * @param deptId
	 * @param serviceId
	 * @param licCatgry
	 * @param licSubCatgry
	 * @return LicenseValidityMasterDto
	 */
	LicenseValidityMasterDto getLicValDataByCategoryAndSubCategory(Long orgId, Long deptId, Long serviceId,
			Long licenseTypeId, Long licCatgry, Long licSubCatgry);
}
