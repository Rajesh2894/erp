package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author pabitra.raulo
 *
 */
@Entity
@Table(name = "VW_EIP_QUICK_LINK_MENU")
public class VIEWQuickLink implements Serializable {

    private static final long serialVersionUID = -1078908242707290750L;

    @Id
    @Column(name = "ROW_NUM")
    private long Id;

    @Column(name = "ORGID")
    private long orgid;

    @Column(name = "PARENTID")
    private String parent_id;

    @Column(name = "LINKID")
    private int linkid;

    @Column(name = "LINK_ID")
    private String link_id;

    @Column(name = "MENU_NM_EN")
    private String menu_name_eng;

    @Column(name = "MENU_NM_REG")
    private String menu_name_reg;

    @Column(name = "LINK_TYPE")
    private String link_type;

    @Column(name = "SORT_ORDER")
    private long sort_order;

    @Column(name = "HAS_SUB_LINK")
    private String haSubLink;

    @Column(name = "LINK_ORDER")
    private Double linkOrder;

    @Column(name = "PAGE_URL")
    private String page_url;

    @Column(name = "IS_LINK_MODIFY")
    private String isLinkModify;

    @Column(name = "Cheker")
    private String checker;

    @Transient
    private CopyOnWriteArrayList<VIEWQuickLink> child;

    @Transient
    private VIEWQuickLink parent;

    
    public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getOrgid() {
        return orgid;
    }

    public void setOrgid(final long orgid) {
        this.orgid = orgid;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(final String parent_id) {
        this.parent_id = parent_id;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(final String link_id) {
        this.link_id = link_id;
    }

    public String getMenu_name_eng() {
        return menu_name_eng;
    }

    public void setMenu_name_eng(final String menu_name_eng) {
        this.menu_name_eng = menu_name_eng;
    }

    public String getMenu_name_reg() {
        return menu_name_reg;
    }

    public void setMenu_name_reg(final String menu_name_reg) {
        this.menu_name_reg = menu_name_reg;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(final String link_type) {
        this.link_type = link_type;
    }

    public long getSort_order() {
        return sort_order;
    }

    public void setSort_order(final long sort_order) {
        this.sort_order = sort_order;
    }

    public CopyOnWriteArrayList<VIEWQuickLink> getChild() {
        return child;
    }

    public void setChild(final CopyOnWriteArrayList<VIEWQuickLink> child) {
        this.child = child;
    }

    public VIEWQuickLink getParent() {
        return parent;
    }

    public void setParent(final VIEWQuickLink parent) {
        this.parent = parent;
    }

    public int getLinkid() {
        return linkid;
    }

    public void setLinkid(final int linkid) {
        this.linkid = linkid;
    }

    public String getHaSubLink() {
        return haSubLink;
    }

    public void setHaSubLink(final String haSubLink) {
        this.haSubLink = haSubLink;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(final String page_url) {
        this.page_url = page_url;
    }

    public String getIsLinkModify() {
        return isLinkModify;
    }

    public void setIsLinkModify(final String isLinkModify) {
        this.isLinkModify = isLinkModify;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(final String checker) {
        this.checker = checker;
    }

	public Double getLinkOrder() {
		return linkOrder;
	}

	public void setLinkOrder(Double linkOrder) {
		this.linkOrder = linkOrder;
	}

}
