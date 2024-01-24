package com.abm.mainet.adh.dto;

import java.io.Serializable;

/**
 * @author vishwajeet.kumar
 * @since 16 October 2019
 */
public class ADHCommonReqDto implements Serializable {

    private static final long serialVersionUID = -2083969221922495770L;

    private Long orgId;
    private Long advertiserCatId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getAdvertiserCatId() {
        return advertiserCatId;
    }

    public void setAdvertiserCatId(Long advertiserCatId) {
        this.advertiserCatId = advertiserCatId;
    }

}
