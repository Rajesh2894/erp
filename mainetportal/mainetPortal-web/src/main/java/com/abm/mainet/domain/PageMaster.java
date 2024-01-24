package com.abm.mainet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author deepika.pimpale
 * @since 26 Feb 2016
 */
@Entity
@Table(name = "TB_PAGE_MASTER")
public class PageMaster {
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PAGE_ID", precision = 12, scale = 0, nullable = false)
    private long pageId;

    @Column(name = "PAGE_NAME", length = 200, nullable = false)
    private String pageName;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long org;

    @Column(name = "TOTAL_COUNT", precision = 20, scale = 0, nullable = true)
    private Long totalCount;

    public long getPageId() {
        return pageId;
    }

    public void setPageId(final long pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(final String pageName) {
        this.pageName = pageName;
    }

    public Long getOrg() {
        return org;
    }

    public void setOrg(final Long org) {
        this.org = org;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final Long totalCount) {
        this.totalCount = totalCount;
    }

    public String[] getPkValues() {

        return new String[] { "AUT", "TB_PAGE_MASTER", "PAGE_ID" };
    }

}