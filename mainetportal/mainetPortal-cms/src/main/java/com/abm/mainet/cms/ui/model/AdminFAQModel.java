package com.abm.mainet.cms.ui.model;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.service.IFrequentlyAskedQuestionsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author rajendra.bhujbal
 *
 */
@Component
@Scope("session")
public class AdminFAQModel extends AbstractEntryFormModel<FrequentlyAskedQuestions> {

    private static final long serialVersionUID = 5878032515021108473L;
    private static final Logger LOG = Logger.getLogger(AdminFAQModel.class);
    private String chekkerflag;
    private String mode;
    private String isChecker;
    @Autowired
    private IFrequentlyAskedQuestionsService iFrequentlyAskedQuestionsService;

    @Autowired
    private IEntitlementService iEntitlementService;

    public IFrequentlyAskedQuestionsService getiFrequentlyAskedQuestionsService() {
        return iFrequentlyAskedQuestionsService;
    }

    public void setiFrequentlyAskedQuestionsService(
            final IFrequentlyAskedQuestionsService iFrequentlyAskedQuestionsService) {
        this.iFrequentlyAskedQuestionsService = iFrequentlyAskedQuestionsService;
    }

    @Override
    public void addForm() {
        setEntity(new FrequentlyAskedQuestions());
        setMode(MainetConstants.Transaction.Mode.ADD);
    }

    @Override
    public void editForm(final long rowId) {
        setMode("Edit");
        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }
        setEntity(iFrequentlyAskedQuestionsService.getFAQById(rowId, UserSession.getCurrent().getOrganisation()));
    }

    @Override
    public boolean saveOrUpdateForm() {
    	long id=getEntity().getFaqId();
    	if(MainetConstants.FLAGY.equalsIgnoreCase(getEntity().getChekkerflag())){
    		final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
	    	if(!(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH))) {
             	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                         UserSession.getCurrent().getOrganisation().getOrgid());
                 if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
                 	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
                 	getEntity().setChekkerflag(MainetConstants.FLAGN);
                 }
	    	}
         }
        iFrequentlyAskedQuestionsService.saveOrUpdateAdminFAQ(getEntity(), this.getChekkerflag());
        Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.FAQs")+" "+(UserSession.getCurrent().getLanguageId()==1?getEntity().getQuestionEn():getEntity().getQuestionReg()),getEntity().getChekkerflag(),id,getEntity().getUpdatedBy());
        
        return true;
    }

    @Override
    public void delete(final long rowId) {
        setEntity(iFrequentlyAskedQuestionsService.getFAQById(rowId, UserSession.getCurrent().getOrganisation()));

        getEntity().setIsDeleted(MainetConstants.IsDeleted.DELETE);

        iFrequentlyAskedQuestionsService.deleteAdminFAQ(getEntity(), "");
    }

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }

}
