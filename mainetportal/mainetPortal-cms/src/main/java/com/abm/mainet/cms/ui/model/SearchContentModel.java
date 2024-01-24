package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IQuickLinkService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.dto.PropertyFiles;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;

@Component
@Scope(value = "session")
public class SearchContentModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -8131310246907338937L;

    @Autowired
    private final ISectionService iSectionService = null;

    @Autowired
    private final IQuickLinkService iQuickLinkService = null;

    private String accessPropertyFileName = MainetConstants.BLANK;

    private List<PropertyFiles> propList = new ArrayList<>(0);

    List<SystemModuleFunction> details = new ArrayList<>(0);

    private List<LinksMaster> linksMasterList = null;
    private List<SubLinkMaster> subLinkMasterList = null;
    private List<SubLinkMaster> subLinkFieldDetailsCKeditorList = null;
    
    private List<SubLinkMaster> finalSubLinkMasterList = null;
   private String keyword;

   

	public String getDirectoryPath() {

        String directoryPath = getAppSession().getMessage("lablpath");
        final StringTokenizer tokenizer = new StringTokenizer(directoryPath, MainetConstants.operator.COMA);
        directoryPath = MainetConstants.BLANK;
        while (tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextElement().toString();
            directoryPath += token + File.separator;
        }

        return directoryPath;
    }

    public Boolean setPropertFileName(final Long gmId) {

        if (gmId == null) {
            if (UserSession.getCurrent().getLanguageId() == 1) {
                accessPropertyFileName = MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_NOUSER_ENG;
            } else {
                accessPropertyFileName = MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_NOUSER_REG;
            }
        } else {

            if (UserSession.getCurrent().getLanguageId() == 1) {
                accessPropertyFileName = MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_USER_ENG;
            } else {
                accessPropertyFileName = MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_USER_REG;
            }
        }
        return true;
    }

    public void setSearchListFromDB(final String searchText) {
        final IEntitlementService iEntitlementService = (IEntitlementService) ApplicationContextProvider.getApplicationContext()
                .getBean("entitlementService");
        Long gmid = null;
        if(null == UserSession.getCurrent().getEmployee().getEmplType()) {
        	gmid = UserSession.getCurrent().getEmployee().getGmid();
        	
        }else {
        	gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.DEFAULT_CITIZEN,
        			 UserSession.getCurrent().getOrganisation().getOrgid());
        }
        
        
      
		setDetails(iEntitlementService.getSearchdata(gmid, searchText,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    public void setLinkMasterListFromDB(final String searchText) {
        setLinksMasterList(iQuickLinkService.getSerchContentList(searchText, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId()));
    }

    public void setSubLinkMasterListFromDB(final String searchText) {
        setSubLinkMasterList(iSectionService.getSerchContentList(searchText));
    }
    
    public void setSubLinkdetailCKeditorFromDB(final String searchText) {
    	setSubLinkFieldDetailsCKeditorList(iSectionService.getSerchContentCKeditorList(searchText));
    }

    public String getAccessPropertyFileName() {
        return accessPropertyFileName;
    }

    public void setAccessPropertyFileName(final String accessPropertyFileName) {
        this.accessPropertyFileName = accessPropertyFileName;
    }

    @Override
    public String getActiveClass() {
        return MainetConstants.BLANK;
    }

    public List<PropertyFiles> getPropList() {
        return propList;
    }

    public void setPropList(final List<PropertyFiles> propList) {
        this.propList = propList;
    }

    public List<SystemModuleFunction> getDetails() {
        return details;
    }

    public void setDetails(final List<SystemModuleFunction> details) {
        this.details = details;
    }

    public List<LinksMaster> getLinksMasterList() {
        return linksMasterList;
    }

    public void setLinksMasterList(final List<LinksMaster> linksMasterList) {
        this.linksMasterList = linksMasterList;
    }

    public List<SubLinkMaster> getSubLinkMasterList() {
        return subLinkMasterList;
    }

    public void setSubLinkMasterList(final List<SubLinkMaster> subLinkMasterList) {
        this.subLinkMasterList = subLinkMasterList;
    }
    public List<SubLinkMaster> getSubLinkFieldDetailsCKeditorList() {
		return subLinkFieldDetailsCKeditorList;
	}

	public void setSubLinkFieldDetailsCKeditorList(List<SubLinkMaster> list) {
		this.subLinkFieldDetailsCKeditorList = list;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<SubLinkMaster> getFinalSubLinkMasterList() {
		Set<SubLinkMaster> setTemp = new HashSet<SubLinkMaster>();
		for (SubLinkMaster subLinkMaster : this.subLinkMasterList) {
			setTemp.add(subLinkMaster);
		}
		for (SubLinkMaster subLinkMaster : this.subLinkFieldDetailsCKeditorList) {
			setTemp.add(subLinkMaster);
		}
		List<SubLinkMaster> temp = new ArrayList<SubLinkMaster>(setTemp);
		
		this.finalSubLinkMasterList = temp;
		return finalSubLinkMasterList;
	}

	public void setFinalSubLinkMasterList(List<SubLinkMaster> finalSubLinkMasterList) {
		
		Set<SubLinkMaster> setTemp = new HashSet<SubLinkMaster>();
		for (SubLinkMaster subLinkMaster : this.subLinkMasterList) {
			setTemp.add(subLinkMaster);
		}
		for (SubLinkMaster subLinkMaster : this.subLinkFieldDetailsCKeditorList) {
			setTemp.add(subLinkMaster);
		}
		List<SubLinkMaster> temp = new ArrayList<SubLinkMaster>(setTemp);
		
		this.finalSubLinkMasterList = temp;
	}

	

}
