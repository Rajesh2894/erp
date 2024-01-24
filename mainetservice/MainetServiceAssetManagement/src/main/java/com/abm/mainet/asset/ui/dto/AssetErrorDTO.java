/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

/**
 * @author satish.rathore
 *
 */
public class AssetErrorDTO {

    String assetCode;
    String error;
    Long orgId;
    Long assetClass;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public Long getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(Long assetClass) {
        this.assetClass = assetClass;
    }

}
