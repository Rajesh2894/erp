
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Sysmodfunction implements Serializable {

    private static final long serialVersionUID = 1L;

	//-------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long smfid;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    @Size( min = 1, max = 1000 )
    private String smfname;

    @Size( max = 1000 )
    private String smfdescription;

    @Size( max = 1 )
    private String smfflag;

    @Size( max = 200 )
    private String smfaction;

    @Size( max = 1 )
    private String smfcategory;

    @NotNull
    private Long userId;

    @NotNull
    private Date ondate;

    @NotNull
    @Size( min = 1, max = 12 )
    private String ontime;

    @Size( max = 1 )
    private String action;

    @Size( max = 1 )
    private String isdeleted;


    private Long smfsystemid;

    @Size( max = 255 )
    private String smfcode;


    private Long updatedBy;


    private Date updatedDate;


    private Long langId;

    @Size( max = 1000 )
    private String smfnameMar;


    private Long smfsrno;

    @Size( max = 100 )
    private String lgIpMac;

    @Size( max = 100 )
    private String lgIpMacUpd;

    private Long sm_parent_id;
    
    private Long						lang_id;
	private String						lg_ip_mac;
	private String						lg_ip_mac_upd;
	private String						smParam1;
	private String						smParam2;	
	private String						smfname_mar;
	private Long						updated_by;
	private Date						updated_date;
	private Long						hiddenEtId;
	private long						user_id;

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setSmfid( Long smfid ) {
        this.smfid = smfid ;
    }

    public Long getSmfid() {
        return this.smfid;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    
    public String getSmfname_mar() {
		return smfname_mar;
	}

	public void setSmfname_mar(String smfname_mar) {
		this.smfname_mar = smfname_mar;
	}

	public Long getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Long updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	public Long getHiddenEtId() {
		return hiddenEtId;
	}

	public void setHiddenEtId(Long hiddenEtId) {
		this.hiddenEtId = hiddenEtId;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public Long getLang_id() {
		return lang_id;
	}

	public void setLang_id(Long lang_id) {
		this.lang_id = lang_id;
	}

	public String getLg_ip_mac() {
		return lg_ip_mac;
	}

	public void setLg_ip_mac(String lg_ip_mac) {
		this.lg_ip_mac = lg_ip_mac;
	}

	public String getLg_ip_mac_upd() {
		return lg_ip_mac_upd;
	}

	public void setLg_ip_mac_upd(String lg_ip_mac_upd) {
		this.lg_ip_mac_upd = lg_ip_mac_upd;
	}

	public String getSmParam1() {
		return smParam1;
	}

	public void setSmParam1(String smParam1) {
		this.smParam1 = smParam1;
	}

	public String getSmParam2() {
		return smParam2;
	}

	public void setSmParam2(String smParam2) {
		this.smParam2 = smParam2;
	}
	
    public void setSmfname( String smfname ) {
        this.smfname = smfname;
    }
    public String getSmfname() {
        return this.smfname;
    }

    public void setSmfdescription( String smfdescription ) {
        this.smfdescription = smfdescription;
    }
    public String getSmfdescription() {
        return this.smfdescription;
    }

    public void setSmfflag( String smfflag ) {
        this.smfflag = smfflag;
    }
    public String getSmfflag() {
        return this.smfflag;
    }

    public void setSmfaction( String smfaction ) {
        this.smfaction = smfaction;
    }
    public String getSmfaction() {
        return this.smfaction;
    }

    public void setSmfcategory( String smfcategory ) {
        this.smfcategory = smfcategory;
    }
    public String getSmfcategory() {
        return this.smfcategory;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }
    public Long getUserId() {
        return this.userId;
    }

    public void setOndate( Date ondate ) {
        this.ondate = ondate;
    }
    public Date getOndate() {
        return this.ondate;
    }

    public void setOntime( String ontime ) {
        this.ontime = ontime;
    }
    public String getOntime() {
        return this.ontime;
    }

    public void setAction( String action ) {
        this.action = action;
    }
    public String getAction() {
        return this.action;
    }

    public void setIsdeleted( String isdeleted ) {
        this.isdeleted = isdeleted;
    }
    public String getIsdeleted() {
        return this.isdeleted;
    }

    public void setSmfsystemid( Long smfsystemid ) {
        this.smfsystemid = smfsystemid;
    }
    public Long getSmfsystemid() {
        return this.smfsystemid;
    }

    public void setSmfcode( String smfcode ) {
        this.smfcode = smfcode;
    }
    public String getSmfcode() {
        return this.smfcode;
    }

    public void setUpdatedBy( Long updatedBy ) {
        this.updatedBy = updatedBy;
    }
    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedDate( Date updatedDate ) {
        this.updatedDate = updatedDate;
    }
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setLangId( Long langId ) {
        this.langId = langId;
    }
    public Long getLangId() {
        return this.langId;
    }

    public void setSmfnameMar( String smfnameMar ) {
        this.smfnameMar = smfnameMar;
    }
    public String getSmfnameMar() {
        return this.smfnameMar;
    }

    public void setSmfsrno( Long smfsrno ) {
        this.smfsrno = smfsrno;
    }
    public Long getSmfsrno() {
        return this.smfsrno;
    }

    public void setLgIpMac( String lgIpMac ) {
        this.lgIpMac = lgIpMac;
    }
    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMacUpd( String lgIpMacUpd ) {
        this.lgIpMacUpd = lgIpMacUpd;
    }
    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public Long getSm_parent_id() {
		return sm_parent_id;
	}
	public void setSm_parent_id(Long sm_parent_id) {
		this.sm_parent_id = sm_parent_id;
	} 
	
    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(smfid);
        sb.append("|");
        sb.append(smfname);
        sb.append("|");
        sb.append(smfdescription);
        sb.append("|");
        sb.append(smfflag);
        sb.append("|");
        sb.append(smfaction);
        sb.append("|");
        sb.append(smfcategory);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(ondate);
        sb.append("|");
        sb.append(ontime);
        sb.append("|");
        sb.append(action);
        sb.append("|");
        sb.append(isdeleted);
        sb.append("|");
        sb.append(smfsystemid);
        sb.append("|");
        sb.append(smfcode);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(smfnameMar);
        sb.append("|");
        sb.append(smfsrno);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");

        return sb.toString(); 
    } 


}
