package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.dto.LinkMasterDTO;
import com.abm.mainet.cms.service.IQuickLinkService;
import com.abm.mainet.cms.ui.validator.AdminQuickLinkValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author swapnil.shirke
 *
 */
@Component
@Scope("session")
public class AdminQuickLinkFormModel extends AbstractEntryFormModel<LinksMaster> implements Serializable {

    private static final long serialVersionUID = -8795983120511983563L;
    private static final Logger LOG = Logger.getLogger(AdminQuickLinkFormModel.class);

    @Autowired
    private IQuickLinkService iAdminQuickLinkService;

    private LinksMaster linksMaster;

    private LinkMasterDTO linkMasterDTO;

    private String mode;

    @Autowired
    private IEntitlementService iEntitlementService;

    private String isChecker;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#addForm() Open form for Adding records
     */
    @Override
    public void addForm() {

        final LookUp lookUpObj = getQuickLinkSection("HQS");
        final LinksMaster linksMasterObj = new LinksMaster();
        final LinkMasterDTO linkMasterDTOObj = new LinkMasterDTO();

        linkMasterDTOObj.setTemp("local");

        linksMasterObj.setCpdSection(lookUpObj.getLookUpId());
        linksMasterObj.setLinkOrder(0D);
        setMode(MainetConstants.Transaction.Mode.ADD);
        setLinksMaster(linksMasterObj);

        setLinkMasterDTO(linkMasterDTOObj);
        setEntity(linksMasterObj);
        setMode(MainetConstants.Transaction.Mode.ADD);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#editForm(long) Open form for editing records
     */
    @Override
    public void editForm(final long rowId) {
        setMode(MainetConstants.Transaction.Mode.UPDATE);
        final LinkMasterDTO linkMasterDTO = new LinkMasterDTO();

        setMode(MainetConstants.Transaction.Mode.UPDATE);
        
        
        
	        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
	                UserSession.getCurrent().getOrganisation().getOrgid());
	        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
	            this.setIsChecker("Y");
	        } else {
	            this.setIsChecker("N");
	        }
        
        final LinksMaster linksMaster = iAdminQuickLinkService.getLinkMaster(rowId);
        String alink = linksMaster.getLinkPath();
        final String[] linkarr = alink.split(MainetConstants.operator.COLON);
        String exlink = MainetConstants.operator.EMPTY;
        if (linkarr.length > 1) {
            exlink = linkarr[0] + "://";
            alink = alink.replaceAll(exlink, MainetConstants.operator.EMPTY);
            linksMaster.setLinkPath(alink);
            linksMaster.setExLink(exlink);
        }
        if ((exlink == MainetConstants.operator.EMPTY) || (exlink == null)) {

            linkMasterDTO.setTemp("local");
        } else {
            linkMasterDTO.setTemp("external");
        }

        linkMasterDTO.setExLink(exlink);
        setLinkMasterDTO(linkMasterDTO);
        setLinksMaster(linksMaster);
        setEntity(linksMaster);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#delete(long) Delete Grid data
     */
    @Override
    public void delete(final long rowId) {
        iAdminQuickLinkService.delete(rowId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#saveOrUpdateForm() To save
     */
    @Override
    public boolean saveOrUpdateForm() {
        validateBean(getEntity(), AdminQuickLinkValidator.class);
        if (hasValidationErrors()) {
            String exlink = linkMasterDTO.getExLink();
            if ((exlink == MainetConstants.operator.EMPTY) || (exlink == null)) {
                linkMasterDTO.setTemp("local");
            } else {
                linkMasterDTO.setTemp("external");
            }
            return false;
        }

        long id=getEntity().getLinkId();
        if(MainetConstants.FLAGY.equalsIgnoreCase(getEntity().getChekkerflag())){
    		final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
	    	if(!(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH))) {
             	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER, UserSession.getCurrent().getOrganisation().getOrgid());
                 if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
                 	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
                 	getEntity().setChekkerflag(MainetConstants.FLAGN);
                 }
	    	}
         }
        if (getMode().equals(MainetConstants.Transaction.Mode.ADD)) {
            if (saveQuickLinks()) {
            	Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.quicklink")+" "+(UserSession.getCurrent().getLanguageId()==1?getEntity().getLinkTitleEg():getEntity().getLinkTitleReg()),getEntity().getChekkerflag(),id,getEntity().getUpdatedBy());
            	return true;
                  
            } else {
                return false;
            }
        } else {
            if (updateQuickLinks()) {
            	Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.quicklink")+" "+(UserSession.getCurrent().getLanguageId()==1?getEntity().getLinkTitleEg():getEntity().getLinkTitleReg()),getEntity().getChekkerflag(),id,getEntity().getUpdatedBy());
                return true;
                
            } else {
                return false;
            }
        }

    }

    /**
     * @return
     * @throws FrameworkException Before saving set all the values using this method
     */
    private boolean saveQuickLinks() throws FrameworkException {

        final LinksMaster linksMasterObj = getEntity();
        if (linksMasterObj.getExLink() != null) {
            final String fullLink = linksMasterObj.getExLink() + linksMasterObj.getLinkPath();
            linksMasterObj.setLinkPath(fullLink);
        }
        return iAdminQuickLinkService.saveOrUpdate(linksMasterObj);

    }

    /**
     * @return
     * @throws FrameworkException Before Updating set all the values using this method
     */
    private boolean updateQuickLinks() throws FrameworkException {
        try {
            final LinksMaster linksMasterObj = getLinksMaster();
            if (linksMasterObj.getExLink() != null) {
                final String fullLink = linksMasterObj.getExLink() + linksMasterObj.getLinkPath();
                linksMasterObj.setLinkPath(fullLink);
            }
            return iAdminQuickLinkService.saveOrUpdate(linksMasterObj);
        } catch (final FrameworkException ex) {
            throw ex;
        }

    }

    public List<LinksMaster> getAllLinkMaster() {
        return null;
    }

    /**
     * @param prefix
     * @return object of entity {@link: LookUp}
     */
    private LookUp getQuickLinkSection(final String prefix) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals("Q")) {
                quickLink = lookUp;
                break;
            }

        }
        return quickLink;

    }

    /**
     * @return the linksMaster
     */
    public LinksMaster getLinksMaster() {
        return linksMaster;
    }

    /**
     * @param linksMaster the linksMaster to set
     */
    public void setLinksMaster(final LinksMaster linksMaster) {
        this.linksMaster = linksMaster;
    }

    /**
     * @return the linkMasterDTO
     */
    public LinkMasterDTO getLinkMasterDTO() {
        return linkMasterDTO;
    }

    /**
     * @param linkMasterDTO the linkMasterDTO to set
     */
    public void setLinkMasterDTO(final LinkMasterDTO linkMasterDTO) {
        this.linkMasterDTO = linkMasterDTO;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final String mode) {
        this.mode = mode;
    }

    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }

}
