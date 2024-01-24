package com.abm.mainet.cms.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IQuickLinkService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author Pranit.Mhatre
 * @since 17 February, 2014
 */
@Component
@Scope("session")
public class SectionEntryModel extends AbstractFormModel {

    private static final long serialVersionUID = -5628156421219698192L;

    private long moduleId;
    private long functionId;

    @Autowired
    private ISectionService iSectionService;

    @Autowired
    private IQuickLinkService iQuickLinkService;

    private final List<LookUp> modules = new ArrayList<>(0);
    private List<LookUp> functions = new ArrayList<>(0);

    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {

        this.makkerchekkerflag = makkerchekkerflag;

    }

    /**
     * To get all section(s) list.
     * @param flag
     * @return {@link List} of {@link SubLinkMaster}.
     */
    public List<SubLinkMaster> getSections(String flag) {
         List<SubLinkMaster> list = iSectionService.findSublinks(flag);
         
         if(list != null) {
        	 list.forEach(i->{
        		  i.setCustOrderMenuEng(iSectionService.levelOrderMenu(i,new StringBuilder(""),MainetConstants.ENGLISH).toString().substring(MainetConstants.MENU_ARROW.length()));
                  i.setCustOrderMenuReg(iSectionService.levelOrderMenu(i,new StringBuilder(""),MainetConstants.MARATHI).toString().substring(MainetConstants.MENU_ARROW.length()));
        	 });
         }
        if ((moduleId == 0) && (functionId == 0)) {
            return list;
        } else {
        	
        	 List<SubLinkMaster> subLinkMas = iSectionService.findSublinks(MainetConstants.IsDeleted.NOT_DELETE, moduleId, functionId, flag);
        	 if(subLinkMas != null) {
        		 subLinkMas.forEach(i->{
            		  i.setCustOrderMenuEng(iSectionService.levelOrderMenu(i,new StringBuilder(""),MainetConstants.ENGLISH).toString().substring(MainetConstants.MENU_ARROW.length()));
                      i.setCustOrderMenuReg(iSectionService.levelOrderMenu(i,new StringBuilder(""),MainetConstants.MARATHI).toString().substring(MainetConstants.MENU_ARROW.length()));
            	 });
             }
          	return subLinkMas;
        }
    }

    @Override
    protected void initializeModel() {
        super.setCommonHelpDocs("SectionEntry.html");
    }

    public List<LookUp> getModules() {
        if (modules.size() == 0) {
            // Get all links details list e.g. Quick Links,Top Bar,Bottom
            // Links,Article,Home Page Central Links
            final List<LookUp> links = getLookUpList("HQS");

            List<LinksMaster> linksMasters = null;

            for (final ListIterator<LookUp> iterator = links.listIterator(); iterator.hasNext();) {
                final LookUp lookUp = iterator.next();

                // 'Q' for QuickLinks
                if (lookUp.getLookUpCode().equals("Q")) {
                    String flag = "Y";
                    linksMasters = iQuickLinkService.getAllLinkMasterByCPDSection(lookUp,
                            UserSession.getCurrent().getOrganisation(), flag);

                    break;
                }

            }

            for (final ListIterator<LinksMaster> iterator = linksMasters.listIterator(); iterator.hasNext();) {
                final LinksMaster linksMaster = iterator.next();
                if (linksMaster.getChekkerflag() != null && linksMaster.getChekkerflag().equalsIgnoreCase("Y")) {
                    final LookUp lookUp = new LookUp();
                    lookUp.setLookUpId(linksMaster.getLinkId());
                    lookUp.setDescLangFirst(linksMaster.getLinkTitleEg());
                    lookUp.setDescLangSecond(linksMaster.getLinkTitleReg());

                    modules.add(lookUp);
                }
            }

        }

        return modules;
    }

    public List<LookUp> getFunctions() {
        functions.clear();

        if (moduleId == 0L) {
            return functions;
        }

        final LinksMaster linksMaster = new LinksMaster();
        linksMaster.setLinkId(moduleId);
        linksMaster.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);

        final List<SubLinkMaster> list = iSectionService.findSublinks(linksMaster);

        for (final ListIterator<SubLinkMaster> iterator = list.listIterator(); iterator.hasNext();) {
            final SubLinkMaster subLinkMaster = iterator.next();

            final LookUp lookUp = new LookUp();

            lookUp.setLookUpId(subLinkMaster.getId());
            lookUp.setDescLangFirst(iSectionService.levelOrderMenu(subLinkMaster,new StringBuilder(),MainetConstants.ENGLISH).toString().substring(MainetConstants.MENU_ARROW.length()));
            lookUp.setDescLangSecond(iSectionService.levelOrderMenu(subLinkMaster,new StringBuilder(),MainetConstants.MARATHI).toString().substring(MainetConstants.MENU_ARROW.length()));
            
            functions.add(lookUp);

        }

        return functions;
    }

    /**
     * @return the functionId
     */
    public long getFunctionId() {
        return functionId;
    }

    /**
     * @param functionId the functionId to set
     */
    public void setFunctionId(final long functionId) {
        this.functionId = functionId;
    }

    /**
     * @param functions the functions to set
     */
    public void setFunctions(final List<LookUp> functions) {
        this.functions = functions;
    }

    /**
     * @return the moduleId
     */
    public long getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(final long moduleId) {
        this.moduleId = moduleId;
    }

}
