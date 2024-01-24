package com.abm.mainet.cms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.abm.mainet.cms.domain.VIEWQuickLink;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class VIEWQuickLinkDTO implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private long Id;

    private long orgid;

    private String parent_id;

    private int linkid;
    
    @JsonIgnore
    private CopyOnWriteArrayList<VIEWQuickLink> child;

    private String link_id;

    private String menu_name_eng;

    private String menu_name_reg;

    private String link_type;

    private long sort_order;

    private String haSubLink;

    private Double linkOrder;

    private String page_url;

    private String isLinkModify;

    private String checker;

    @JsonIgnore
    private VIEWQuickLinkDTO parent;
    
    private ArrayList<VIEWQuickLinkDTO> childList;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getOrgid() {
		return orgid;
	}

	public void setOrgid(long orgid) {
		this.orgid = orgid;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public int getLinkid() {
		return linkid;
	}

	public void setLinkid(int linkid) {
		this.linkid = linkid;
	}

	public String getLink_id() {
		return link_id;
	}

	public void setLink_id(String link_id) {
		this.link_id = link_id;
	}

	public String getMenu_name_eng() {
		return menu_name_eng;
	}

	public void setMenu_name_eng(String menu_name_eng) {
		this.menu_name_eng = menu_name_eng;
	}

	public String getMenu_name_reg() {
		return menu_name_reg;
	}

	public void setMenu_name_reg(String menu_name_reg) {
		this.menu_name_reg = menu_name_reg;
	}

	public String getLink_type() {
		return link_type;
	}

	public void setLink_type(String link_type) {
		this.link_type = link_type;
	}

	public long getSort_order() {
		return sort_order;
	}

	public void setSort_order(long sort_order) {
		this.sort_order = sort_order;
	}

	public String getHaSubLink() {
		return haSubLink;
	}

	public void setHaSubLink(String haSubLink) {
		this.haSubLink = haSubLink;
	}

	public Double getLinkOrder() {
		return linkOrder;
	}

	public void setLinkOrder(Double linkOrder) {
		this.linkOrder = linkOrder;
	}

	public String getPage_url() {
		return page_url;
	}

	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}

	public String getIsLinkModify() {
		return isLinkModify;
	}

	public void setIsLinkModify(String isLinkModify) {
		this.isLinkModify = isLinkModify;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public CopyOnWriteArrayList<VIEWQuickLink> getChild() {
		return child;
	}

	public void setChild(CopyOnWriteArrayList<VIEWQuickLink> child) {
		this.child = child;
	}

	public VIEWQuickLinkDTO getParent() {
		return parent;
	}

	public void setParent(VIEWQuickLinkDTO parent) {
		this.parent = parent;
	}

	public ArrayList<VIEWQuickLinkDTO> getChildList() {
		return childList;
	}

	public void setChildList(ArrayList<VIEWQuickLinkDTO> childList) {
		this.childList = childList;
	}	
}
