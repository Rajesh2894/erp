package com.abm.mainet.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Kavali.Kiran
 * @since 29 Apr 2015
 */
@Entity
@Table(name = "VW_ORG_DETAILS")
public class ViewOrgDetails implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ORGID", precision = 0, scale = 0, nullable = false)
    private Long orgid;

    @Column(name = "O_NLS_ORGNAME", length = 50, nullable = true)
    private String oNlsOrgname;

    @Column(name = "ORG_SHORT_NM", length = 10, nullable = true)
    private String orgShortNm;

    @Column(name = "O_NLS_ORGNAME_MAR", length = 100, nullable = true)
    private String oNlsOrgnameMar;

    @Column(name = "ORG_CPD_ID_DIS", length = 12, nullable = true)
    private Long deptId;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public String getoNlsOrgname() {
        return oNlsOrgname;
    }

    public void setoNlsOrgname(final String oNlsOrgname) {
        this.oNlsOrgname = oNlsOrgname;
    }

    public String getOrgShortNm() {
        return orgShortNm;
    }

    public void setOrgShortNm(final String orgShortNm) {
        this.orgShortNm = orgShortNm;
    }

    public String getoNlsOrgnameMar() {
        return oNlsOrgnameMar;
    }

    public void setoNlsOrgnameMar(final String oNlsOrgnameMar) {
        this.oNlsOrgnameMar = oNlsOrgnameMar;
    }

    public String getOrgname() {
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            return oNlsOrgname;
        } else {
            return oNlsOrgnameMar;
        }

    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

}