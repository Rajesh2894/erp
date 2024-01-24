package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IDataArchivalService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author Pranit.Mhatre
 * @since 01 March, 2014
 */
@Component
@Scope("session")
public class SectionInformationModel extends AbstractEntryFormModel<SubLinkMaster> implements Serializable {
	
    private static final String PREFIX_NOT_FOUND = "prefix not found for:[prefix -->";
	
    private static final long serialVersionUID = 8687361034333459289L;

    private static final Logger LOG = Logger.getLogger(SectionInformationModel.class);
    @Autowired
    private ISectionService iSectionService;
    
    @Autowired
    private IDataArchivalService dataArchivalService;

    private List<SubLinkMaster> subLinkMasters = new ArrayList<>(0);

    private boolean viewMode = false;
    
    private boolean showOnlyHeader = false;

    private List<LookUp> sectionLookUps = new ArrayList<>(0);

    private List<SubLinkMaster> subLinkMasterList = null;
    private List<SubLinkMaster> subLinkMasterListForMap = null;
    private String mapFlag = MainetConstants.IsDeleted.NOT_DELETE;

    private String[] imgpath;

    private String idformap;
    
    private String linkName;

    private String defaultStatus;
    
    private boolean notFound;
    
    public String IS_HIGHLIGHTED = "IS_HIGHLIGHTED";
    /**
     * To get all section(s) list.
     * @return {@link List} of {@link SubLinkMaster}.
     */
    /*
     * public List<SubLinkMaster> getSections() { subLinkMasters = iSectionService.findSublinks(); return subLinkMasters; }
     */

    @Override
    protected void initializeModel() {

        super.initializeModel();
        if (sectionLookUps.size() <= 0) {
            sectionLookUps = getLookUpList(MainetConstants.LookUp.SECTION_TYPE);
        }

    }
    
    public List<String> getAllhtml(final String CK_Editer3) {
        return iSectionService.getAllhtml(CK_Editer3);
    }
    
    public long getLookUpId(final String lookUpCode) {
        final ListIterator<LookUp> iterator = sectionLookUps.listIterator();

        while (iterator.hasNext()) {
            final LookUp lookUp = iterator.next();

            if (lookUp.getLookUpCode().equals(lookUpCode)) {
                return lookUp.getLookUpId();
            }
        }

        return 0;
    }

    /**
     * @return the viewMode
     */
    public boolean isViewMode() {
        return viewMode;
    }

    /**
     * @param viewMode the viewMode to set
     */
    public void setViewMode(final boolean viewMode) {
        this.viewMode = viewMode;
    }

    public String[] levelOrderMenu(SubLinkMaster mas) {
    String op =	iSectionService.levelOrderMenu(mas,new StringBuilder(""),UserSession.getCurrent().getLanguageId()).toString().substring(MainetConstants.MENU_ARROW.length());
    String[] datas =op.split(MainetConstants.MENU_ARROW) ;
    return datas;
    }
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#editForm(long)
     */
    @Override
    public void editForm(final long rowId) {
    	this.showOnlyHeader = false;
        this.sectionLookUps = getLookUpList(MainetConstants.LookUp.SECTION_TYPE);
        SubLinkMaster master = new SubLinkMaster();
        if ((getIdformap() != null) && !getIdformap().equals(MainetConstants.BLANK)) {
            master = iSectionService.findSublinks(rowId, getIdformap());
        } else {
            master = iSectionService.findSublinks(rowId);

        }        
              
        if (master != null) {
        	setLinkName(master.getSubLinkNameEn());  
            final SubLinkMaster cloneLinkMaster = (SubLinkMaster) master.clone();
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                UserSession.getCurrent().setKeywords(UserSession.getCurrent().getKeywords() + master.getSubLinkNameEn() + ", ");
            } else {
                UserSession.getCurrent().setKeywords(UserSession.getCurrent().getKeywords() + master.getSubLinkNameRg() + ", ");
            }
            final ListIterator<SubLinkFieldMapping> listIterator = cloneLinkMaster.getSubLinkFieldMappings().listIterator();
            while (listIterator.hasNext()) {
                listIterator.next();
            }

            final ListIterator<SubLinkFieldDetailsHistory> listIterator2 = cloneLinkMaster.getDetailsHistories().listIterator();
            while (listIterator2.hasNext()) {
                final SubLinkFieldDetailsHistory links = listIterator2.next();
                final String temp = links.getTxta_01_en();
                if (temp != null) {
                    if (temp.contains("$")) {
                        final StringTemplate template = new StringTemplate(temp);
                        final String cityname = UserSession.getCurrent().getOrganisation().getONlsOrgname();
                        template.setAttribute("orgname", cityname);
                        links.setTxta_01_en(template.toString());
                        links.setTxta_01_rg(template.toString());
                    }
                }
            }

            setEntity(cloneLinkMaster);
            Long sectionType2 = null;
            Long sectionType3 = null;
            for (final SubLinkFieldMapping subLinkFieldMapping2 : cloneLinkMaster.getSubLinkFieldMappings()) {
                sectionType2 = subLinkFieldMapping2.getSectionType();
                if(subLinkFieldMapping2.getFieldType()==MainetConstants.TEXT_AREA_HTML) {
                	getEntity().getSectiontypevalue().clear();
                	getEntity().getSectiontypevalue().add(subLinkFieldMapping2);
                	break;
                }
                if (getEntity().getSectionType1() == null) {
                    if ((sectionType2 != null) && !sectionType2.equals(sectionType3)) {
                        getEntity().getSectiontypevalue().add(subLinkFieldMapping2);
                        sectionType3 = sectionType2;
                    }
                } else {
                    getEntity().getSectiontypevalue().add(subLinkFieldMapping2);
                }
            	
            	}
           setViewMode(true);setNotFound(false);
        }else {
        	setNotFound(true);
        }
    }

    public List<List<LookUp>> getSectionInformation() {
        try {
        	if(getEntity()!=null) { 
        	if(UserSession.getCurrent().getOrganisation().getDefaultStatus().equals(MainetConstants.Organisation.SUPER_ORG_STATUS)) {			
    			setDefaultStatus(MainetConstants.FlagY.toUpperCase());		
    		}else {
    			setDefaultStatus(MainetConstants.FlagN.toUpperCase());
    		}
        	
        	
        	
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                    UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
                    getEntity().getLinksMaster().getLinkTitleEg().trim() + MainetConstants.FILE_PATH_SEPARATOR +
                    getEntity().getSubLinkNameEn().trim();

            final List<List<LookUp>> dataList = new ArrayList<>();

            String fieldLabel;

            final List<KeyValue> fields = new ArrayList<>(0);

            List<SubLinkFieldMapping> list =  getEntity().getSubLinkFieldMappings();
            list.sort(Comparator.comparingDouble(SubLinkFieldMapping::getOrderNo));
            
        	
            for (final SubLinkFieldMapping temp : list) {
                if (MainetConstants.IsDeleted.DELETE.equals(temp.getIsUsed())
                        && MainetConstants.IsDeleted.NOT_DELETE.equals(temp.getIsDeleted())) {

                    if (getUserSession().getLanguageId() == MainetConstants.ENGLISH) {

                        fieldLabel = temp.getFieldNameEn();
                    } else {
                        fieldLabel = temp.getFieldNameRg();
                    }
                    fields.add(this.new KeyValue(temp.getFieldType(), temp.getFiledNameMap().toLowerCase(), fieldLabel,
                            temp.getSectionType()));
                }
            }

            // Check whether profile image has been uploaded or not.
            final boolean hasPhoto = hasProfileImage(fields);
            // Check whether documents has been uploaded or not.
            final boolean hasDocs = hasDownloadDocs(fields);
            int count = 0;
            List<LookUp> lookUps = null;
           if(CollectionUtils.isNotEmpty( getEntity().getDetailsHistories())) {
            for (final SubLinkFieldDetailsHistory details : getEntity().getDetailsHistories()) {
                if (MainetConstants.IsDeleted.NOT_DELETE.equals(details.getIsDeleted())) {
                    lookUps = new ArrayList<>();
                    LookUp lookUp = null;
                    // To add details of text_filed,text_area and date picker only.
                    if(getEntity().getSectiontypevalue()!=null 
                            && !getEntity().getSectiontypevalue().isEmpty() 
                            && getEntity().getSectiontypevalue().get(0).getSectionType() == getLookUpId(MainetConstants.TABLE) //if we want for section,map,etc then remove condition now implemented only for tablegrid
                            && getEntity().getSectiontypevalue().get(0).getFieldType() != MainetConstants.TEXT_AREA_HTML) {
                            	 lookUp = new LookUp();
                            	 lookUp.setLookUpType(IS_HIGHLIGHTED);
                            	 lookUp.setLookUpCode(((details.getIsHighlighted() ==null) ?MainetConstants.FlagN:details.getIsHighlighted()) );
                                 lookUps.add(lookUp);                    	
                            }
                           
                    for (final KeyValue keyValue : fields) {
                        String filepath1 = null;
                        if ((keyValue.getKey() == MainetConstants.TEXT_FIELD)
                                || (keyValue.getKey() == MainetConstants.TEXT_AREA) 
                                || (keyValue.getKey() == MainetConstants.DROP_DOWN_BOX) ) {
                            lookUp = new LookUp();

                            lookUp.setDescLangFirst(String.valueOf(getPropertyVal(keyValue.getValue() + "_en", details)));
                            lookUp.setDescLangSecond(String.valueOf(getPropertyVal(keyValue.getValue() + "_rg", details)));
                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongTwo(count);
                            lookUps.add(lookUp);

                        }
                        if ((keyValue.getKey() == MainetConstants.LINK_FIELD)) {
                            lookUp = new LookUp();

                            lookUp.setDescLangFirst(String.valueOf(getPropertyVal(keyValue.getValue() , details)));
                            lookUp.setDescLangSecond(String.valueOf(getPropertyVal(keyValue.getValue(), details)));
                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongTwo(count);
                            lookUps.add(lookUp);

                        }

                        if (keyValue.getKey() == MainetConstants.TEXT_AREA_HTML) {
                            lookUp = new LookUp();
                            String str = null;
                            str = new String(details.getTxta_03_ren_blob());

                            final String s1 = MainetConstants.PARENT_PATH;
                            final String s3 = MainetConstants.CURRENT_PATH;
                            if (str.contains(s1)) {
                            }
                            final String str2 = str.replace(s1, MainetConstants.CHILD_PATH);
                            final String str3 = str2.replace(s3, MainetConstants.BLANK);

                            lookUp.setDescLangFirst(str3);
                            // Setting hindi data
                            if (null != details.getTxta_03_en_nnclob() && !details.getTxta_03_en_nnclob().isEmpty()) {
                                String hindiData = details.getTxta_03_en_nnclob();
                                hindiData.replace(s1, MainetConstants.CHILD_PATH);
                                hindiData.replace(s3, MainetConstants.BLANK);
                                lookUp.setDescLangSecond(hindiData);
                            }

                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongTwo(count);
                            lookUps.add(lookUp);
                        }
                        if (keyValue.getKey() == MainetConstants.DATE_PICKER) {
                            lookUp = new LookUp(details.getId(), String.valueOf(getPropertyVal(keyValue.getValue(), details)));
                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUps.add(lookUp);
                        }
                        if (hasPhoto) {
                            count = 0;
                            File file = null;
                            FileOutputStream fos = null;
                            FileOutputStream fos1=null;
                            if ((keyValue.getKey() == MainetConstants.PROFILE_IMG)
                                    || (keyValue.getKey() == MainetConstants.VIDEO)) {
                                final String filePath = String.valueOf(getPropertyVal(keyValue.getValue(), details));
                                imgpath = filePath.split(MainetConstants.operator.COMA);
                                String finString = MainetConstants.BLANK;
                                String subtitle=null;
                                if (imgpath != null) {
                                    for (int i = 0; i < imgpath.length; i++) {
                                        lookUp = new LookUp();
                                        
                                        lookUp.setDefaultVal(((details.getIsHighlighted() ==null || details.getIsHighlighted().equals(MainetConstants.FlagN)) ?MainetConstants.FlagN:IS_HIGHLIGHTED) );
                                        
                                        if (!imgpath[i].isEmpty()) {
                                            final String fileName = StringUtility
                                                    .staticStringAfterChar(MainetConstants.WINDOWS_SLASH, imgpath[i]);

                                            if ((fileName != null) && (fileName.length() > 0)) {
                                                String directoryPath = StringUtility
                                                        .staticStringBeforeChar(MainetConstants.WINDOWS_SLASH, imgpath[i]);
                                               /*Set filepath as filenet path & removed Cache Path*/
                                                filepath1 = directoryPath + MainetConstants.FILE_PATH_SEPARATOR + fileName;
                                                directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                        MainetConstants.operator.COMA);

                                                final byte[] image = getFileNetClient().getFileByte(fileName, directoryPath);

                                               // filepath1 = outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName;
                                                Utility.createDirectory(Filepaths.getfilepath() + outputPath
                                                        + MainetConstants.FILE_PATH_SEPARATOR);
                                                file = new File(Filepaths.getfilepath() + outputPath
                                                        + MainetConstants.FILE_PATH_SEPARATOR + fileName);
                                                
                                                /*Attachment for video subtitle*/
                                                byte[] image1=null;
                                                File file1=null;
                                                String att_01="att_01";
                                                final String filePath2 = String.valueOf(getPropertyVal(att_01, details));
                                                if((filePath2 !=null) && (filePath2.length()>0) ) {
                                               String[] imgpath2 = filePath2.split(MainetConstants.operator.COMA);
                                                
                                                final String fileName1 = StringUtility
                                                        .staticStringAfterChar(MainetConstants.WINDOWS_SLASH, imgpath2[0]);
                                                image1 = getFileNetClient().getFileByte(fileName1, directoryPath);
                                                file1=new File(Filepaths.getfilepath()+outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName1);
                                                subtitle= outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName1;
                                                }
                                                if (finString.length() == 0) {
                                                    finString = file.getName();
                                                } else {
                                                    finString += MainetConstants.operator.ORR + file.getName();
                                                }

                                                if (image != null) {
                                                    try {
                                                        fos = new FileOutputStream(file);
                                                        fos.write(image);
                                                        fos.close();
                                                    } catch (final Exception ex) {
                                                        LOG.error("Error while reading value from response: ", ex);
                                                    }
                                                }
                                                if (image1 != null) {
                                                    try {
                                                        fos1=new FileOutputStream(file1);
                                                        fos1.write(image1);
                                                        fos1.close();
                                                    } catch (final Exception ex) {
                                                        LOG.error("Error while reading value from response: ", ex);
                                                    }
                                                }

                                            }
                                           
                                            if (filepath1 != null) {
                                                /*
                                                lookUp.setLookUpDesc(filepath1.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                        MainetConstants.WINDOWS_SLASH));
                                                lookUp.setDescLangFirst(filepath1.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                        MainetConstants.WINDOWS_SLASH));
                                                lookUp.setDescLangSecond(filepath1.replace(MainetConstants.FILE_PATH_SEPARATOR,MainetConstants.WINDOWS_SLASH));
                                                */
												/* modified for cache path below*/
                                            	//filepath1 =  outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName;
                                            	lookUp.setLookUpDesc(filepath1);
                                                lookUp.setDescLangFirst(filepath1);
                                                lookUp.setDescLangSecond(filepath1);									            
                                                lookUp.setExtraStringField1((file.length() / 1024) + " KB");
                                            }
                                            lookUp.setLookUpId(details.getId());
                                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                                            lookUp.setLookUpExtraLongTwo(count);

                                            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                                                lookUp.setOtherField(details.getTxt_01_en() != null ? details.getTxt_01_en()
                                                        : MainetConstants.BLANK);
                                            } else {
                                                lookUp.setOtherField(details.getTxt_01_rg() != null ? details.getTxt_01_rg()
                                                        : MainetConstants.BLANK);
                                            }
                                            if(subtitle != null) {
                                            lookUp.setExtraStringField2(subtitle.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                    MainetConstants.WINDOWS_SLASH));
                                            }
                                            count++;

                                            lookUps.add(lookUp);

                                        }else {
                                            lookUp.setLookUpId(details.getId());
                                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                                            lookUp.setLookUpExtraLongTwo(count);
                                            lookUps.add(lookUp);
                                        }
                                    }
                                }

                                // lookUp.setD

                            }
                        }
                        if (hasDocs) {
                            if (keyValue.getKey() == MainetConstants.ATTACHMENT_FIELD) {
                                lookUp = new LookUp(details.getId(),
                                        String.valueOf(getPropertyVal(keyValue.getValue(), details)));
                                lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                lookUp.setLookUpCode(keyValue.getFieldLabel());
                                lookUps.add(lookUp);
                            }
                        }
                        count++;
                    }

                    // Add details of Profile Image.

                    dataList.add(lookUps);
                }

            }
            }else {
            	this.showOnlyHeader = true;
            	lookUps = new ArrayList<>();
				for (KeyValue val : fields) {
						LookUp lookUp = new LookUp();
						/*lookUp.setLookUpCode(val.getFieldLabel());*/
						lookUp.setDescLangFirst(val.getFieldLabel());
						lookUp.setDescLangSecond(val.getFieldLabel());
						lookUps.add(lookUp);
						 
				}
				dataList.add(lookUps);

			}
            // below method is called for setting the label according to language Id.
            getSubLinkLabel();
            return dataList;
        	}
        } catch (final NullPointerException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return null;
    }

    private boolean hasProfileImage(final List<KeyValue> fields) {
        for (final KeyValue keyValue : fields) {
            if (keyValue.getKey() == MainetConstants.PROFILE_IMG) {
                return true;
            }

            if (keyValue.getKey() == MainetConstants.VIDEO) {
                return true;
            }
        }

        return false;
    }

    private boolean hasDownloadDocs(final List<KeyValue> fields) {
        for (final KeyValue keyValue : fields) {
            if (keyValue.getKey() == MainetConstants.ATTACHMENT_FIELD) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param propertyName
     * @param clazz
     * @return
     */
    private Object getPropertyVal(final String propertyName, final Object clazz) {
        final BeanWrapper wrapper = new BeanWrapperImpl(clazz);

        if (wrapper.isReadableProperty(propertyName)) {
            final Object val = wrapper.getPropertyValue(propertyName);

            if (val != null) {
                if (val instanceof Date) {
                    return Utility.dateToString(Utility.converObjectToDate(val));
                } else if (val instanceof byte[]) {
                    final byte[] bytes = (byte[]) val;

                    return bytes;
                }
                return val.toString();
            } else {
                return MainetConstants.BLANK;
            }
        }

        return null;
    }

    private class KeyValue implements Serializable {

        private static final long serialVersionUID = -4413589667550488856L;
        private final int key;
        private final String value;
        private final String fieldLabel;
        private final long sectiontype;

        public long getSectiontype() {
            return sectiontype;
        }

        public KeyValue(final int key, final String value, final String fieldLabel, final long sectiontype) {
            this.key = key;
            this.value = value;
            this.fieldLabel = fieldLabel;
            this.sectiontype = sectiontype;
        }

        /**
         * @return the key
         */
        public int getKey() {
            return key;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        public String getFieldLabel() {
            return fieldLabel;
        }
    }

    public Long getCommitteInfo(final String Prefixvalue) {
        return iSectionService.findCommiteeInfo(Prefixvalue);

    }

    public String getSubLinkLabel() {
        try {
        	if(getEntity()!=null) {
            String sublinkName = MainetConstants.BLANK;
            if (UserSession.getCurrent().getLanguageId() == 1) {
                sublinkName = getEntity().getSubLinkNameEn();
            } else {
                sublinkName = getEntity().getSubLinkNameRg();
            }
            return sublinkName;
        	}
        } catch (final NullPointerException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return "Page Not Found";
    }

    public String getSubLinkParentLabel() {
        try {
        	if(getEntity()!=null) {
            String sublinkName = MainetConstants.BLANK;
            final LinksMaster linkMaster = getEntity().getLinksMaster();
            if (UserSession.getCurrent().getLanguageId() == 1) {
                sublinkName = linkMaster.getLinkTitleEg();
            } else {
                sublinkName = linkMaster.getLinkTitleReg();
            }
            return sublinkName;
        	}
        } catch (final NullPointerException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return null;
    }
    /**
     * @return section is archived or not
     */
    public Boolean getArchiveSection() {
    	
    	List<LookUp> arSections=dataArchivalService.getArchiveSectionName(UserSession.getCurrent().getOrganisation(),UserSession.getCurrent().getLanguageId());
    	List<String> archiveSections= new ArrayList<String>();
    	for (LookUp lookUp : arSections) {
    		archiveSections.add(lookUp.getLookUpCode());
		}
    	if(archiveSections.contains(getSubLinkLabel())) {
    		long id=getEntity().getId();
    		List<Long> valiadteId=dataArchivalService.getArchiveFielsId(id);
    		if(valiadteId.size()>0) {
    			return true;
    		}
    		return false;
    	}else {
    		return false;
    	}
    	
    }
    /**
     * @return the sectionLookUps
     */
    public List<LookUp> getSectionLookUps() {
        return sectionLookUps;
    }

    public List<SubLinkMaster> getSubLinkMasterList() {
        return subLinkMasterList;
    }

    public void setSubLinkMasterList(final List<SubLinkMaster> subLinkMasterList) {
        this.subLinkMasterList = subLinkMasterList;
    }

    public String getIdformap() {
        return idformap;
    }

    public void setIdformap(final String idformap) {
        this.idformap = idformap;
    }

    public List<SubLinkMaster> getSubLinkMasterListForMap() {
        return subLinkMasterListForMap;
    }

    public void setSubLinkMasterListForMap(
            final List<SubLinkMaster> subLinkMasterListForMap) {
        this.subLinkMasterListForMap = subLinkMasterListForMap;
    }

    public String getMapFlag() {
        return mapFlag;
    }

    public void setMapFlag(final String mapFlag) {
        this.mapFlag = mapFlag;
    }

	public boolean isShowOnlyHeader() {
		return showOnlyHeader;
	}

	public void setShowOnlyHeader(boolean showOnlyHeader) {
		this.showOnlyHeader = showOnlyHeader;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}    
	
	public void setDefaultStatus(String defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
	
	public String getDefaultStatus() {
		return defaultStatus;
	}

	public boolean isNotFound() {
		return notFound;
	}

	public void setNotFound(boolean notFound) {
		this.notFound = notFound;
	}
}
