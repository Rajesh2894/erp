/**
 *
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author vishnu.jagdale
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class ApplicationStatusRequestVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<ApplicationDetail> appDetailList = new ArrayList<>();

    private Long orgId;

    /**
     * @return the appDetailList
     */
    public List<ApplicationDetail> getAppDetailList() {
        return appDetailList;
    }

    /**
     * @param appDetailList the appDetailList to set
     */
    public void setAppDetailList(final List<ApplicationDetail> appDetailList) {
        this.appDetailList = appDetailList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

}
