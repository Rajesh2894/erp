package com.abm.mainet.cms.ui.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IQuickLinkService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.ui.validator.SectionEntryValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;


/**
 * @author Pranit.Mhatre
 * @since 18 February, 2014
 */
@Component
@Scope("session")
public class SectionEntryFormModel extends AbstractEntryFormModel<SubLinkMaster> {
	
	private static final Logger LOG = Logger.getLogger(SectionEntryFormModel.class);

    private static final long serialVersionUID = 4853380265111403992L;

    @Autowired
    private IQuickLinkService iQuickLinkService;

    @Autowired
    private ISectionService iSectionService;

    private SubLinkFieldMapping subLinkFieldMapping;

    @Autowired
    private IEntitlementService iEntitlementService;
    

    private String isChecker;

    @SuppressWarnings("rawtypes")
    List sectionType2 = new ArrayList(0);
    @SuppressWarnings("rawtypes")
    List fieldType2 = new ArrayList(0);
    
    private final List<LookUp> fieldTypes = new ArrayList<>(0);

    private boolean listMode = true;
    private boolean editMode = false;
    private long editRowId;
    private final List<LookUp> modules = new ArrayList<>(0);
    private final List<LookUp> functions = new ArrayList<>(0);

    public void setSectionType(final List<LookUp> sectionType) {
        this.sectionType = sectionType;
    }

    private List<LookUp> sectionType = new ArrayList<>(0);
    private List<LookUp> eipImageLookUps = new ArrayList<>(0);
    private List<SubLinkMaster> sectionTypeList = new ArrayList<>(0);

    public List<SubLinkMaster> getSectionTypeList() {
        return sectionTypeList;
    }

    public void setSectionTypeList(List<SubLinkMaster> sectionTypeList) {
        this.sectionTypeList = sectionTypeList;
    }

    private long functionCount = 0;

    public List<LookUp> getSectionType() {
        return sectionType;
    }

    @Override
    protected void initializeModel() {

        super.initializeModel();
        fillLookUp();
        sectionTypeList = new ArrayList<>(0);

        sectionTypeList.add(null);
    }

    private void fillLookUp() {
        sectionType = getLevelData(MainetConstants.LookUp.SECTION_TYPE);
        eipImageLookUps = getLevelData(MainetConstants.LookUp.EIP_IMAGE_TYPE);

    }

    /**
     * @return the subLinkFieldMapping
     */
    public SubLinkFieldMapping getSubLinkFieldMapping() {
        return subLinkFieldMapping;
    }

    /**
     * @param subLinkFieldMapping the subLinkFieldMapping to set
     */
    public void setSubLinkFieldMapping(final SubLinkFieldMapping subLinkFieldMapping) {
        this.subLinkFieldMapping = subLinkFieldMapping;
    }

    public List<LookUp> getFieldTypes() {
        return fieldTypes;
    }

    public List<LookUp> getFieldTypes(final String sectiontype) {
        fieldTypes.clear();
        if (sectiontype.equalsIgnoreCase("PHOTO") || sectiontype.equalsIgnoreCase("MAP")) {
            fieldTypes.clear();
            fieldTypes.add(new LookUp(MainetConstants.PROFILE_IMG, MainetConstants.PROFILE_FIELD_FACE));
            fieldTypes.add(new LookUp(MainetConstants.TEXT_FIELD, MainetConstants.TEXT_FIELD_FACE));
        }
        if (sectiontype.equalsIgnoreCase("VD")) {
            fieldTypes.clear();
            fieldTypes.add(new LookUp(MainetConstants.VIDEO, MainetConstants.VIDEO_FACE));
            fieldTypes.add(new LookUp(MainetConstants.TEXT_FIELD, MainetConstants.TEXT_FIELD_FACE));
            fieldTypes.add(new LookUp(MainetConstants.ATTACHMENT_FIELD, MainetConstants.ATTACHMENT_FIELD_FACE));            
            fieldTypes.add(new LookUp(MainetConstants.DATE_PICKER, MainetConstants.DATE_PICKER_FACE));
        }

        if (sectiontype.equalsIgnoreCase("TABLE") || sectiontype.equalsIgnoreCase("SEC")
                || sectiontype.equalsIgnoreCase("PORTRAIT")) {
            fieldTypes.clear();
            fieldTypes.add(new LookUp(MainetConstants.PROFILE_IMG, MainetConstants.PROFILE_FIELD_FACE));
            fieldTypes.add(new LookUp(MainetConstants.VIDEO, MainetConstants.VIDEO_FACE));
            fieldTypes.add(new LookUp(MainetConstants.TEXT_FIELD, MainetConstants.TEXT_FIELD_FACE));
            fieldTypes.add(new LookUp(MainetConstants.TEXT_AREA, MainetConstants.TEXT_AREA_FACE));
            fieldTypes.add(new LookUp(MainetConstants.TEXT_AREA_HTML, MainetConstants.TEXT_AREA_HTML_FACE));
            fieldTypes.add(new LookUp(MainetConstants.ATTACHMENT_FIELD, MainetConstants.ATTACHMENT_FIELD_FACE));
            fieldTypes.add(new LookUp(MainetConstants.DATE_PICKER, MainetConstants.DATE_PICKER_FACE));
	        if (sectiontype.equalsIgnoreCase("TABLE") || sectiontype.equalsIgnoreCase("SEC")) {
            fieldTypes.add(new LookUp(MainetConstants.DROP_DOWN_BOX, MainetConstants.DROP_DOWN_BOX_FACE));
	        }
			if (sectiontype.equalsIgnoreCase("TABLE")) {
				fieldTypes.add(new LookUp(MainetConstants.LINK_FIELD, MainetConstants.LINK_FIELD_FACE));
			}
	        
        }


        if (sectiontype.equalsIgnoreCase("LBL")) {
            fieldTypes.clear();
            fieldTypes.add(new LookUp(MainetConstants.TEXT_FIELD, MainetConstants.TEXT_FIELD_FACE));
            fieldTypes.add(new LookUp(MainetConstants.TEXT_AREA, MainetConstants.TEXT_AREA_FACE));
            fieldTypes.add(new LookUp(MainetConstants.TEXT_AREA_HTML, MainetConstants.TEXT_AREA_HTML_FACE));
        }

        return fieldTypes;
    }

    public boolean isListMode() {
        return listMode;
    }

    public void setListMode(final boolean listMode) {
        this.listMode = listMode;
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
                    linksMasters = iQuickLinkService.getAllLinkMasterByCPDSection(lookUp,
                            UserSession.getCurrent().getOrganisation(), "Y");

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

        functionCount = 0;
        int Count = 0;
        if (getEntity().getLinksMaster().getLinkId() == 0) {
            return functions;
        }
        
        final List<SubLinkMaster> list = iSectionService.findSublinks(getEntity().getLinksMaster());
        for (final ListIterator<SubLinkMaster> iterator = list.listIterator(); iterator.hasNext();) {
            final SubLinkMaster subLinkMaster = iterator.next();

            if (subLinkMaster.getSubLinkMaster() == null) {
                functionCount++;
            }
            if (subLinkMaster.getId() != getEntity().getId()) {
                final LookUp lookUp = new LookUp();     
        		lookUp.setLookUpId(subLinkMaster.getId());
                lookUp.setDescLangFirst(iSectionService.levelOrderMenu(subLinkMaster,new StringBuilder(),MainetConstants.ENGLISH).toString().substring(MainetConstants.MENU_ARROW.length()));
                lookUp.setDescLangSecond(iSectionService.levelOrderMenu(subLinkMaster,new StringBuilder(),MainetConstants.MARATHI).toString().substring(MainetConstants.MENU_ARROW.length()));
                functions.add(lookUp);
            }
            if (Count == 0) {
                sectionTypeList.clear();

                if (getEntity().getSectionType0() != 0) {
                    sectionTypeList.add(subLinkMaster);
                }
                if (getEntity().getSectionType1() != null) {
                    subLinkMaster.setSectionType1(getEntity().getSectionType1());
                    sectionTypeList.add(subLinkMaster);
                }
                if (getEntity().getSectionType2() != null) {
                    subLinkMaster.setSectionType2(getEntity().getSectionType2());
                    sectionTypeList.add(subLinkMaster);
                }
                if ((getEntity().getSectionType3() != null) && (getEntity().getSectionType3() != 0)) {
                    subLinkMaster.setSectionType3(getEntity().getSectionType3());
                    sectionTypeList.add(subLinkMaster);
                }
                if ((getEntity().getSectionType4() != null) && (getEntity().getSectionType4() != 0)) {
                    subLinkMaster.setSectionType3(getEntity().getSectionType3());
                    sectionTypeList.add(subLinkMaster);
                }
                Count++;
            }
        }
        if ((getEntity().getSubLinkFieldMappings() != null) && (getSubLinkFieldMapping() != null)) {
            final List<SubLinkFieldDetails> SubLinkFieldDetails2 = getSubLinkFieldMapping().getSubLinkFieldlist();
            if (getEntity().getSubLinkFieldMappings().size() > getSubLinkFieldMapping().getSubLinkFieldlist().size()) {
                SubLinkFieldDetails2.clear();
            }
            int count = 0;
            for (final SubLinkFieldDetails subLinkFieldDetails3 : getSubLinkFieldMapping().getSubLinkFieldlist()) {
                if (subLinkFieldDetails3.getFieldNameEn() != null) {
                    getSubLinkFieldMapping().getSubLinkFieldlist().get(count)
                            .setFieldNameEn(subLinkFieldDetails3.getFieldNameEn());
                    getSubLinkFieldMapping().getSubLinkFieldlist().get(count)
                            .setFieldNameRg(subLinkFieldDetails3.getFieldNameRg());
                    getSubLinkFieldMapping().getSubLinkFieldlist().get(count).setFieldType(subLinkFieldDetails3.getFieldType());

                    count++;
                }
            }
        }
        return functions;
    }
    
    
    public void setEditData(final long rowId) {
        final SubLinkFieldMapping temp = (SubLinkFieldMapping) findEntity(getEntity().getSubLinkFieldMappings(), rowId).clone();
        setSubLinkFieldMapping(temp);
        setListMode(false);
    }

    private void addElement() {
        attachParentEntity(getSubLinkFieldMapping());
        final List<SubLinkFieldMapping> SubLinkFieldMapping2 = getEntity().getSubLinkFieldMappings();
        int count = 0;
        if (SubLinkFieldMapping2.size() > 0) {
            if ((SubLinkFieldMapping2.size() != 1) && (getEntity().getSectionType1() != null)) {
                SubLinkFieldMapping2.clear();
            } else {
                if (getEntity() != null) {
                    count = SubLinkFieldMapping2.size();
                }
            }
        }
        getSubLinkFieldMapping().setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        for (final SubLinkFieldDetails subLinkFieldDetails3 : getSubLinkFieldMapping().getSubLinkFieldlist()) {
            SubLinkFieldMapping2.add(new SubLinkFieldMapping());
            SubLinkFieldMapping2.get(count).setFieldNameEn(subLinkFieldDetails3.getFieldNameEn());
            SubLinkFieldMapping2.get(count).setFieldNameRg(subLinkFieldDetails3.getFieldNameRg());
            SubLinkFieldMapping2.get(count).setFieldType(subLinkFieldDetails3.getFieldType());
            SubLinkFieldMapping2.get(count).setIsMandatory(getSubLinkFieldMapping().getIsMandatory());
            SubLinkFieldMapping2.get(count).setDropDownOptionEn(getSubLinkFieldMapping().getDropDownOptionEn());
            SubLinkFieldMapping2.get(count).setDropDownOptionReg(getSubLinkFieldMapping().getDropDownOptionReg());
            SubLinkFieldMapping2.get(count).setOrderNo(getSubLinkFieldMapping().getOrderNo());
            count++;

        }
    }

    public boolean saveElement() {
        
        final SubLinkFieldMapping temp = getSubLinkFieldMapping();
        for (final SubLinkFieldDetails tempField : subLinkFieldMapping.getSubLinkFieldlist()) {           
        if ((tempField.getTempId() == 0) && !validateFieldAvailability(tempField.getFieldType(), tempField)) {
        	
                addValidationError("Cannot add more element of " + findFieldType(tempField.getFieldType()) + " type.");

                return false;
            }
        }
        validateBean(this, SectionEntryValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        if (temp.getTempId() == 0) {
            temp.getRowId();
            addElement();
        } else {
            replaceEntity(getChildEntityCollection(), temp);
        }

        return true;
    }

    public void deleteElement(final long rowId) {
        final List<SubLinkFieldMapping> childCollection = getChildEntityCollection();

        for (int i = 0; i < childCollection.size(); ++i) {
            final SubLinkFieldMapping entity = childCollection.get(i);

            if (entity.getRowId() == rowId) {
                if (rowId < 0) {
                    childCollection.remove(i);
                } else {
                    entity.setIsDeleted(MainetConstants.IsDeleted.DELETE);
                    entity.setIsUsed(MainetConstants.IsDeleted.NOT_DELETE);
                    entity.setFieldSequence(0);

                    final String fieldName = entity.getFiledNameMap().toLowerCase();
                    String fieldNameEn = MainetConstants.BLANK;
                    String fieldNameRg = MainetConstants.BLANK;

                    final ListIterator<SubLinkFieldDetails> listIterator = getEntity().getSubLinkFieldDetails().listIterator();
                    while (listIterator.hasNext()) {
                        final SubLinkFieldDetails details = listIterator.next();

                        final BeanWrapper wrapper = new BeanWrapperImpl(details);

                        if ((entity.getFieldType() == MainetConstants.TEXT_FIELD)
                                || (entity.getFieldType() == MainetConstants.TEXT_AREA) || (entity.getFieldType() == MainetConstants.DROP_DOWN_BOX)) {
                            fieldNameEn = fieldName + "_en";
                            fieldNameRg = fieldName + "_rg";

                            if (wrapper.isWritableProperty(fieldNameEn) && wrapper.isWritableProperty(fieldNameRg)) {
                                wrapper.setPropertyValue(fieldNameEn, null);
                                wrapper.setPropertyValue(fieldNameRg, null);
                            } else if (wrapper.isWritableProperty(fieldName)) {
                                wrapper.setPropertyValue(fieldName, null);
                            }
                        }

                    }

                }

                break;
            }
        }
    }

    public List<SubLinkFieldMapping> getChildEntityCollection() {
        return getEntity().getSubLinkFieldMappings();
    }

    private void attachParentEntity(final SubLinkFieldMapping child) {
        child.setSubLinkMaster(getEntity());
    }

    public boolean saveSection() {
        validateBean(this, SectionEntryValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean saveOrUpdateForm() {
        SubLinkMaster parent = getEntity().getSubLinkMaster();

        if ((parent != null) && (parent.getId() != 0)) {
            parent = iSectionService.findSublinks(parent.getId());
        }

        else {
            parent = null;
        }

        final List<SubLinkMaster> linkMasters = iSectionService.isSubLinkIsExist(getEntity());

        if ((linkMasters != null) && (linkMasters.size() > 0)) {
            addValidationError("Sub Link Order Already Exists.Please Enter Different Order Number");

            return false;
        }

        getEntity().setSubLinkMaster(parent);

        if ((getEntity().getHasSubLink() != null) && MainetConstants.IsDeleted.NOT_DELETE.equals(getEntity().getHasSubLink())) {
            getEntity().setPageUrl(MainetConstants.EIPSection.DEFAULT_PAGE_URL);
        }

        long id=getEntity().getId();
        
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
        iSectionService.saveSection(getEntity());
        Utility.sendSmsAndEmail("Sub Link"+" "+(UserSession.getCurrent().getLanguageId()==1?getEntity().getSubLinkNameEn():getEntity().getSubLinkNameRg()),getEntity().getChekkerflag(),id,getEntity().getUpdatedBy());
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#editForm(long)
     */
    @Override
    public void editForm(final long rowId) {
        if (rowId != 0) {
        	SubLinkMaster sublinklist = iSectionService.findSublinks(rowId);
        	List<SubLinkFieldMapping> sortedLinkField = sublinklist.getSubLinkFieldMappings().stream().sorted(Comparator.comparing(SubLinkFieldMapping::getOrderNo)).collect(Collectors.toList());
        	sublinklist.setSubLinkFieldMappings(sortedLinkField);
        	//setEntity(iSectionService.findSublinks(rowId));
        	setEntity(sublinklist);
        }
        setEditMode(true);
        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }
        getEntity().setEditRowId(rowId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#delete(long)
     */
    @Override
    public void delete(final long rowId) {
        final SubLinkMaster master = iSectionService.findSublinks(rowId);
        master.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        iSectionService.saveSection(master);
    }

    /**
     * To check whether for newly added element has availability in database.
     * @param fieldType the in literal containing field type.
     * @param tempField
     * @return <code>true</code> if has availability otherwise <code>false</code> .
     */
    private boolean validateFieldAvailability(final int fieldType, final SubLinkFieldDetails tempField) {
        int fieldAvailabilityCount = 0;
        final ListIterator<SubLinkFieldMapping> listIterator = getEntity().getSubLinkFieldMappings().listIterator();
        if (getEntity().getSubLinkFieldMappings().contains(tempField)) {
            return true;
        }

        while (listIterator.hasNext()) {
            final SubLinkFieldMapping mapping = listIterator.next();

            if (mapping.getFieldType() == fieldType) {
                if (MainetConstants.IsDeleted.NOT_DELETE.equals(mapping.getIsDeleted())
                        && MainetConstants.IsDeleted.DELETE.equals(mapping.getIsUsed())) {
                    ++fieldAvailabilityCount;
                }
            }
        }
      
        if (fieldType == MainetConstants.TEXT_FIELD) {
            if (fieldAvailabilityCount >= MainetConstants.TEXT_FIELD_COUNT) {
                return false;
            }
        } else if (fieldType == MainetConstants.TEXT_AREA) {
            if (fieldAvailabilityCount >= MainetConstants.TEXT_AREA) {
                return false;
            }
        } else if (fieldType == MainetConstants.DROP_DOWN_BOX) {
            if (fieldAvailabilityCount >= MainetConstants.DROP_DOWN_BOX_COUNT) {
                return false;
            }
        } else if (fieldType == MainetConstants.DATE_PICKER) {
            if (fieldAvailabilityCount >= MainetConstants.DATE_PICKER_COUNT) {
                return false;
            }
        } else if (fieldType == MainetConstants.ATTACHMENT_FIELD) {
            if (fieldAvailabilityCount >= MainetConstants.ATTACHMENT_FIELD_COUNT) {
                return false;
            }
        } else if (fieldType == MainetConstants.PROFILE_IMG) {
            if (fieldAvailabilityCount >= MainetConstants.PROFILE_FIELD_COUNT) {
                return false;
            }
        } else if (fieldType == MainetConstants.VIDEO) {
            if (fieldAvailabilityCount >= MainetConstants.PROFILE_FIELD_COUNT) {
                return false;
            }
        }else if(fieldType == MainetConstants.LINK_FIELD) {
        	if (fieldAvailabilityCount >= MainetConstants.LINK_FIELD_COUNT) {
                return false;
            }
        }
        

        return true;
    }

    /**
     * Get type of field by field type.
     * @param fieldType the in literal containing type of field.
     * @return {@link String} containing type of field.
     */
    public String findFieldType(final int fieldType) {
        switch (fieldType) {
        case MainetConstants.TEXT_FIELD:
            return MainetConstants.TEXT_FIELD_FACE;
        case MainetConstants.TEXT_AREA:
            return MainetConstants.TEXT_AREA_FACE;
        case MainetConstants.DROP_DOWN_BOX:
            return MainetConstants.DROP_DOWN_BOX_FACE;
        case MainetConstants.DATE_PICKER:
            return MainetConstants.DATE_PICKER_FACE;
        case MainetConstants.ATTACHMENT_FIELD:
            return MainetConstants.ATTACHMENT_FIELD_FACE;
        case MainetConstants.PROFILE_IMG:
            return MainetConstants.PROFILE_FIELD_FACE;
        case MainetConstants.VIDEO:
            return MainetConstants.VIDEO_FACE;
        case MainetConstants.LINK_FIELD:
        	return MainetConstants.LINK_FIELD_FACE;
        default:
            return null;

        }
    }

    /**
     * @return the eipImageLookUps
     */
    public List<LookUp> getEipImageLookUps() {
        return eipImageLookUps;
    }

    /**
     * @return the functionCount
     */
    public long getFunctionCount() {
        return functionCount;
    }

    public Double getFunctionCount(final long linkId) {
        return iSectionService.getMaxLinkOrderCount(linkId);
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(final boolean editMode) {
        this.editMode = editMode;
    }

    public long getEditRowId() {
        return editRowId;
    }

    public void setEditRowId(final long editRowId) {
        this.editRowId = editRowId;
    }

    public List getSectionType2() {
        return sectionType2;
    }

    public void setSectionType2(final List sectionType2) {
        this.sectionType2 = sectionType2;
    }
    
    public List getFieldType2() {
        return fieldType2;
    }

    public void setFieldType2(final List fieldType2) {
        this.fieldType2 = fieldType2;
    }

    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }

}
