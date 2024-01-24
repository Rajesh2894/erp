/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.List;
import java.util.stream.Stream;

import com.abm.mainet.asset.ui.dto.AssetRegisterUploadDto;
import com.abm.mainet.asset.ui.dto.AssetUploadErrorDetailDto;
import com.abm.mainet.asset.ui.dto.ITAssetRegisterUploadDto;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;

/**
 * @author satish.rathore
 *
 */
public interface IAssetRegisterUploadService {

    public void saveAndDeleteErrorDetails(List<AssetUploadErrorDetailDto> errorDetails, Long orgId, String AssetType);

    // public boolean saveUploadData(List<AssetRegisterUploadDto> dtos, Long orgId, String ipMacAddress, Long empId);

    public String assetCodeForExcel(Long orgId, String astCode, String ulbName,
            AssetPurchaseInformationDTO assetPurchaseInformationDTO, String moduleDeptCode);

    public boolean saveUploadData(Stream<AssetRegisterUploadDto> stream, Long orgId, String ipMacAddress, Long empId,
            String ulbName, String moduleDeptCode);

	boolean saveUploadITAsssetData(Stream<ITAssetRegisterUploadDto> dtos, Long orgId, String ipMacAddress, Long empId,
			String ulbName, String moduleDeptCode);

}
