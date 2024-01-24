/**
 * 
 */
package com.abm.mainet.asset.repository;

import com.abm.mainet.asset.domain.AssetRegisterUploadError;

/**
 * @author satish.rathore
 *
 */
public interface AssetRegisterUploadRepoCustom {

    public void deleteErrorLog(Long orgId, String AstType);

    public void saveErrorDetails(AssetRegisterUploadError entity);

}
