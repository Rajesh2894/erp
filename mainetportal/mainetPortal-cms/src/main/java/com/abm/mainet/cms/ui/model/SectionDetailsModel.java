package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.ui.validator.SectionFormDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author Pranit.Mhatre
 * @since 25 February, 2014
 */
@Component
@Scope("session")
public class SectionDetailsModel extends AbstractEntryFormModel<SubLinkFieldDetails> {

    private static final long serialVersionUID = -776877200516078303L;

    private static final Logger LOG = Logger.getLogger(SectionDetailsModel.class);

    @Autowired
    private ISectionService iSectionService;

    private List<SubLinkMaster> subLinkMasters = new ArrayList<>(0);
    private SubLinkMaster subLinkMaster;

    private boolean listMode = true;

    private String mode = "Add";
    
    private String linkName;
    
	private Long secType = 0L;

    private List<LookUp> sectionLookUps = new ArrayList<>(0);

    private String[] imgList;

    private String[] attList;

    private String[] attList2;

    private String[] imgpath;

    private String[] attpath;

    private String[] attpath2;

    private String makkerchekkerflag;

    private String newProfilepath = MainetConstants.BLANK;

    private String newAttpath = MainetConstants.BLANK;

    private String newAttpath2 = MainetConstants.BLANK;

    private List<String> imgName = new ArrayList<>(0);

    private List<String> attName = new ArrayList<>(0);

    private List<String> attName2 = new ArrayList<>(0);

    private List<String> videoName = new ArrayList<>(0);

    private List<String> listGuid = new ArrayList<>(0);

    private FileStorageCache fileStorageCache = new FileStorageCache();

    private List<LookUp> dept = new ArrayList<>(0);
    
    private List<LookUp> financialYear = new ArrayList<>(0);
    
    private List<LookUp> investmentTypeList = new ArrayList<>(0);

    @Autowired
    private IEntitlementService iEntitlementService;
    
    private String isChecker;

    private String isOperator = MainetConstants.IsDeleted.NOT_DELETE;
    private String isCallDelete= MainetConstants.IsDeleted.NOT_DELETE;
    private String errorMsg;
    
 public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

    /**
     * To get all section(s) list.
     * @param flag
     * @return {@link List} of {@link SubLinkMaster}.
     */
    public List<SubLinkMaster> getSections(String flag) {

        subLinkMasters = iSectionService.findSublinks(flag);
        
        return subLinkMasters;

    }

    @Override
    protected void initializeModel() {
        new ArrayList<>(0);
        super.initializeModel();
        super.setCommonHelpDocs("SectionDetails.html");
        if (dept.size() <= 0) {
        	dept=getLookUpList("PDL");
        }
        if (financialYear.size() <= 0) {
        	financialYear=getLookUpList("FIY");
        }
        if (investmentTypeList.size() <= 0) {
        	investmentTypeList=getLookUpList("INV");
        }
        if (sectionLookUps.size() <= 0) {
            sectionLookUps = getLookUpList(MainetConstants.LookUp.SECTION_TYPE);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#editForm(long)
     */
    @Override
    public void editForm(final long rowId) {

        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }
        subLinkMaster = iSectionService.findSublinks(rowId);

    }

    
    public void getRole() {

        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.OPERATOR,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsOperator("Y");
        } else {
            this.setIsOperator("N");
        }
        

    }


    public SubLinkMaster getSection() {
        return subLinkMaster;
    }

    public boolean detailOrderIdDupCheck() {
    		  final SubLinkFieldDetails temp = getEntity();
	        	List<SubLinkFieldDetails> sublinkFieldDetails =    iSectionService.getSubLinkFieldDetails(subLinkMaster.getId());
	        	boolean subLinkFieldDetailOrderExists = false;
	        	if(sublinkFieldDetails != null) {
	        		subLinkFieldDetailOrderExists = sublinkFieldDetails.stream().filter(i -> i.getSubLinkFieldDtlOrd() != null ).anyMatch(t -> t.getSubLinkFieldDtlOrd().equals(temp.getSubLinkFieldDtlOrd()));
	        	}
	        	 
	        	if(subLinkMaster.getEditRowId() == 0 || subLinkFieldDetailOrderExists == false) {
    	            return subLinkFieldDetailOrderExists;
    	     	}else {	
    			if(subLinkFieldDetailOrderExists == true) {
    				return	!sublinkFieldDetails.stream().filter(i -> (i.getSubLinkFieldDtlOrd() != null)).anyMatch(t -> (t.getSubLinkFieldDtlOrd().equals(temp.getSubLinkFieldDtlOrd())) && (t.getId() == subLinkMaster.getEditRowId() ));
    			 
    			}
    			return subLinkFieldDetailOrderExists;
    		}        
    }

    @Override
    public boolean saveOrUpdateForm() {
        final SubLinkFieldDetails temp = getEntity();
        final FileStorageCache cache = fileStorageCache;
        temp.setSubLinkMaster(subLinkMaster);
        subLinkMaster.getSubLinkFieldDetails().clear();
        if("Y".equalsIgnoreCase(subLinkMaster.getIsArchive())){
        	List<SubLinkFieldMapping> listSubLinkFieldMapping = subLinkMaster.getSubLinkFieldMappings();
        	for (SubLinkFieldMapping subLinkFieldMapping : listSubLinkFieldMapping) {
        		if(MainetConstants.LAST_DATE.equalsIgnoreCase(subLinkFieldMapping.getFieldNameEn())) {
        			if("DATE_01".equalsIgnoreCase(subLinkFieldMapping.getFiledNameMap())){
        				temp.setDate_01(temp.getValidityDate());
        			}else if("DATE_02".equalsIgnoreCase(subLinkFieldMapping.getFiledNameMap())){
        				temp.setDate_02(temp.getValidityDate());
        			}else if("DATE_03".equalsIgnoreCase(subLinkFieldMapping.getFiledNameMap())){
        				temp.setDate_03(temp.getValidityDate());
        			}else if("DATE_04".equalsIgnoreCase(subLinkFieldMapping.getFiledNameMap())){
        				temp.setDate_04(temp.getValidityDate());
        			}
        		}
			}
        }
        
        if(MainetConstants.FLAGY.equalsIgnoreCase(temp.getChekkerflag())){
    		final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
	    	if(!(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH))) {
             	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                         UserSession.getCurrent().getOrganisation().getOrgid());
                 if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
                 	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
                 	temp.setChekkerflag(MainetConstants.FLAGN);
                 }
	    	}
         }
        
        subLinkMaster.getSubLinkFieldDetails().add(temp);
        validateBean(this, SectionFormDetailsValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        String directoryTree = MainetConstants.BLANK;

        String profDirectoryTree = MainetConstants.BLANK;

        if (temp.getTempId() <= 0) {
        	if(this.getIsCallDelete().equals(MainetConstants.IsDeleted.NOT_DELETE)) {
            temp.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        	}
			subLinkMaster.getSubLinkFieldDetails().add(temp);
            directoryTree = getStorePath(MainetConstants.DirectoryTree.EIP);
            profDirectoryTree = directoryTree;
            if (temp.getProfile_img_path() != null) {
                imgList = temp.getProfile_img_path().toString().split(MainetConstants.operator.COMA);
            }

        } else {
            replaceEntity(subLinkMaster.getSubLinkFieldDetails(), temp);

            if (temp.getAtt_01() != null) {
            	if (temp.getAtt_path() != null) {
                    final String temppath = temp.getAtt_01() + MainetConstants.operator.COMA + temp.getAtt_path();
                    temp.setAtt_01(temppath);
                    temp.setAtt_path(MainetConstants.BLANK);
                }
                for (final String tempFile : temp.getAtt_01().split(MainetConstants.operator.COMA)) {
                    directoryTree = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, tempFile);
                    break;
                }
            }

            if (temp.getAtt_02() != null) {
                if (temp.getAtt_path() != null) {
                    final String temppath = temp.getAtt_02() + MainetConstants.operator.COMA + temp.getAtt_path();
                    temp.setAtt_02(temppath);
                    temp.setAtt_path(MainetConstants.BLANK);
                }
                for (final String tempFile : temp.getAtt_02().split(MainetConstants.operator.COMA)) {
                    directoryTree = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, tempFile);
                    break;
                }
            }

            if (temp.getAtt_video_path() != null) {
                if (temp.getVideo_path() != null) {
                    final String temppath = temp.getAtt_video_path() + MainetConstants.operator.COMA + temp.getVideo_path();
                    temp.setAtt_video_path(temppath);
                    temp.setVideo_path(MainetConstants.BLANK);
                }
                for (final String tempFile : temp.getAtt_video_path().split(MainetConstants.operator.COMA)) {
                    directoryTree = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, tempFile);
                    break;
                }
            }

            if (temp.getProfile_img_path() != null) {
                if (temp.getImg_path() != null) {
                    final String temppath = temp.getProfile_img_path() + MainetConstants.operator.COMA + temp.getImg_path();
                    temp.setProfile_img_path(temppath);
                    temp.setImg_path(MainetConstants.BLANK);
                }
                imgList = temp.getProfile_img_path().toString().split(MainetConstants.operator.COMA);
                profDirectoryTree = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE,
                        temp.getProfile_img_path());
            }

            if (temp.getProfile_img_path() == null) {
                if (temp.getImg_path() != null) {
                    temp.setProfile_img_path(temp.getImg_path());
                    temp.setImg_path(MainetConstants.BLANK);
                    imgList = temp.getProfile_img_path().toString().split(MainetConstants.operator.COMA);
                    profDirectoryTree = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE,
                            temp.getProfile_img_path());
                }

            }

            if (MainetConstants.BLANK.equals(profDirectoryTree)) {
                if (MainetConstants.BLANK.equals(directoryTree)) {
                    directoryTree = getStorePath(MainetConstants.DirectoryTree.EIP);
                }
                profDirectoryTree = directoryTree;

            } else {
                if (MainetConstants.BLANK.equals(directoryTree)) {
                    directoryTree = profDirectoryTree;
                }

            }
        }

        temp.updateAuditFields();

        if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
        setFilePaths(temp, "att_01", directoryTree);
        setFilePaths(temp, "att_02", directoryTree);
        setFilePaths(temp, "att_video_path", directoryTree);
        }

        

       

       
        if (imgList != null) {
            String path = MainetConstants.BLANK;
            for (final String element : imgList) {
                path = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, element);

                if (path.equals(MainetConstants.BLANK)) {
                    setFilePaths(temp, "profile_img_path", directoryTree);

                }
                if (!path.equals(MainetConstants.BLANK)) {
                    directoryTree = path;
                }

            }
        }

        directoryTree = directoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        profDirectoryTree = profDirectoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);

        /*
         * for (final SubLinkFieldMapping sb1 : subLinkMaster.getSubLinkFieldMappings()) { if (sb1.getFieldType() == 6) { for
         * (final SubLinkFieldDetails sbD : subLinkMaster.getSubLinkFieldDetails()) { if (sbD.getAtt_video_path() == null) {
         * sbD.setAtt_video_path(profDirectoryTree + MainetConstants.operator.FORWARD_SLACE + temp.getVideo_path()); } } } }
         */

        try {
        	
            iSectionService.saveSection(subLinkMaster);
            Utility.sendSmsAndEmail("Sub Link"+" "+(UserSession.getCurrent().getLanguageId()==1?subLinkMaster.getSubLinkNameEn():subLinkMaster.getSubLinkNameRg()),getEntity().getChekkerflag(),subLinkMaster.getId(),getEntity().getUpdatedBy());
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        setImgList(null);

        if (MainetConstants.IsDeleted.NOT_DELETE.equals(temp.getIsDeleted())) {

            try {
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)
                        && (temp.getTxta_03_ren_blob() != null)) {

                    /*
                     * getUploadPath(Filepaths.getfilepath()) :: Upload physical path for CKEDITER UPLOAD Content when its
                     * uploaded
                     */
                    getFileNetClient().uploadFileList(cache.getFileList(), getUploadPath(Filepaths.getfilepath()));
                }
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                	String genericDirectoryTree = directoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.FORWARD_SLACE);
                    getFileNetClient().uploadFileList(cache.getFileList(), genericDirectoryTree);
                }

                if (cache.getProfileImage() != null) {
                    final List<File> profileList = new ArrayList<>(0);
                    profileList.add(cache.getProfileImage());
                    getFileNetClient().uploadFileList(profileList, profDirectoryTree);
                }
            } catch (final Exception ex) {
                throw new FrameworkException(ex);
            } finally {
                cache.flush();
            }

        }

        return true;
    }

    /**
     * 
     * @return Upload path for CKEDITER UPLOAD Content
     */
    public String getUploadPath() {
        return getUploadPath(MainetConstants.WINDOWS_SLASH);
    }

    /**
     * 
     * @return Upload physical path for CKEDITER UPLOAD Content when its uploaded
     */
    public String getUploadPath(String filePath) {
        StringBuilder realpath = new StringBuilder(filePath);

        realpath.append(MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER)
                .append(MainetConstants.FILE_PATH_SEPARATOR)
                .append(UserSession.getCurrent().getOrganisation().getOrgid()).append(MainetConstants.FILE_PATH_SEPARATOR)
                .append(subLinkMaster.getLinksMaster().getLinkTitleEg().trim()).append(MainetConstants.FILE_PATH_SEPARATOR)
                .append(subLinkMaster.getSubLinkNameEn().trim());
        Utility.createDirectory(realpath.toString());
        return realpath.toString();
    }

    /**
     * @return
     */
    public List<List<LookUp>> getTableDetails() {
        final List<List<LookUp>> objects = new LinkedList<>();

        final List<LookUp> headerList = new LinkedList<>();

        final List<LookUp> detailsList = new LinkedList<>();

        final List<String> fields = new ArrayList<>(0);
        LookUp lookUp = null;
        LookUp lookUp2 = null;
        LookUp lookUp3 = null;
       // #122444
        List<SubLinkFieldDetails> fieldDtlList = iSectionService.getSubLinkFieldDetails(subLinkMaster.getId());
       boolean isCkEditor = false;
       List<SubLinkFieldMapping>  subLinkFieldMappings = subLinkMaster.getSubLinkFieldMappings();
       if(subLinkFieldMappings != null && !subLinkFieldMappings.isEmpty()) {
    	   for (final SubLinkFieldMapping isCkCheck : subLinkFieldMappings) {
               	if(isCkCheck.getFieldType() == MainetConstants.TEXT_AREA_HTML) {
               		isCkEditor = true;
               }
       }
       }
      
        for (final SubLinkFieldMapping temp : subLinkFieldMappings) {
            if (MainetConstants.IsDeleted.DELETE.equals(temp.getIsUsed())
                    && MainetConstants.IsDeleted.NOT_DELETE.equals(temp.getIsDeleted())) {
                lookUp = new LookUp(temp.getId(), temp.getFieldNameEn());
                 //changes for Photo/Attachment----> starts
                
             if( (!( (temp.getFieldType()  ==   MainetConstants.PROFILE_IMG && isCkEditor) || (temp.getFieldType()  ==   MainetConstants.ATTACHMENT_FIELD && isCkEditor) ))) {
                headerList.add(lookUp);
               	}
					
                //changes for Photo/Attachment-----> ends

                if ((temp.getFieldType() == 1) || (temp.getFieldType() == 2) || (temp.getFieldType() == 3)) {
                    lookUp2 = new LookUp();
                    lookUp2.setLookUpId(temp.getId());
                    lookUp2.setDescLangFirst(temp.getFieldNameRg());
                    lookUp2.setDescLangSecond(temp.getFieldNameRg());
                    lookUp2.setLookUpType("rg");
                    headerList.add(lookUp2);
                    fields.add(temp.getFiledNameMap().toLowerCase() + "_en");
                    fields.add(temp.getFiledNameMap().toLowerCase() + "_rg");
                } else {
                    //changes for  Photo/Attachment----> starts
                	 if( (!( (temp.getFieldType()  ==   MainetConstants.PROFILE_IMG && isCkEditor) || (temp.getFieldType()  ==   MainetConstants.ATTACHMENT_FIELD && isCkEditor) ))) {
                           fields.add(temp.getFiledNameMap().toLowerCase());
               }
                    //changes for  Photo/Attachment-----> ends

            }
        }
        }

        if(fieldDtlList!=null) {
	        for (final SubLinkFieldDetails details : fieldDtlList) {
	            if (MainetConstants.IsDeleted.NOT_DELETE.equals(details.getIsDeleted()) && details.getTxta_03_ren_blob() == null) {
                for (final String field : fields) {
                    String docName = MainetConstants.BLANK;
                    lookUp3 = new LookUp();
                    lookUp3.setLookUpId(details.getId());
                    final String fieldVal = getPropertyVal(field, details);
	                    if (fieldVal != null && !field.contains("date") && !field.contains("txt") && !field.contains("txta") ) {
                        docName = fieldVal.substring(fieldVal.lastIndexOf('/') + 1);
                    }
	                    if (fieldVal != null && field.contains("date") || fieldVal != null && field.contains("txt") || fieldVal != null && field.contains("txta")) {
                        docName = fieldVal;
                    }
                    if (!docName.equals("null")) {
                        lookUp3.setDescLangFirst(docName);
                        lookUp3.setDescLangSecond(docName);
                    }
					/*
					 * if(details.getChekkerflag()!=null && details.getChekkerflag().equals("Y")) {
					 * lookUp3.setOtherField("Y"); }
					 */
                   
                    lookUp3.setLookUpType(field);
	                    lookUp3.setExtraStringField1(details.getChekkerflag());
						lookUp3.setOtherField(details.getIsDeleted()); 
                    detailsList.add(lookUp3);
                }
            }

            if (MainetConstants.IsDeleted.NOT_DELETE.equals(details.getIsDeleted()) && (details.getTxta_03_ren_blob() != null)) {
                for (final String field : fields) {
                    String str = null;
                    str = new String(details.getTxta_03_ren_blob());
                    lookUp3 = new LookUp();
                    lookUp3.setLookUpId(details.getId());
                    lookUp3.setDescLangFirst(str);
                    lookUp3.setDescLangSecond(details.getTxta_03_en_nnclob());
                    lookUp3.setLookUpType(field);
                    lookUp3.setExtraStringField1(details.getChekkerflag());
                    detailsList.add(lookUp3);
                }
            }

        }
        }
        objects.add(headerList);
        objects.add(detailsList);

        return objects;
    }

    /**
     * @param propertyName
     * @param clazz
     * @return
     */
    private String getPropertyVal(final String propertyName, final Object clazz) {
        final BeanWrapper wrapper = new BeanWrapperImpl(clazz);

        if (wrapper.isReadableProperty(propertyName)) {
            final Object val = wrapper.getPropertyValue(propertyName);

            if (val != null) {
                if (val instanceof Date) {
                    return Utility.dateToString(Utility.converObjectToDate(val));
                }
                return val.toString();
            }
        }

        return null;
    }

    /**
     * To get detail object using row id and set as current entity.
     * @param rowId the long literal containing identifier value.
     */
    public boolean editDetailObject(final long rowId) {
        this.getSubLinkMaster().setEditRowId(rowId);
        setLinkName(getSection().getLinksMaster().getLinkTitleEg());
        secType = getSection().getSectionType0();
        setMode("Edit");
        imgName.clear();
        attName.clear();
        attName2.clear();
        videoName.clear();
        boolean flag = true;
        try {
        setEntity(findEntity(getSection().getSubLinkFieldDetails(), rowId));
		} catch (Exception e) {
			setEntity(iSectionService.getSubLinkFieldDtlObj(rowId));
			flag = false;
		}

        if (getEntity().getProfile_img_path() != null) {
            imgpath = getEntity().getProfile_img_path().toString().split(MainetConstants.operator.COMA);
            if (imgpath != null) {
                String imgNm = MainetConstants.BLANK;
                for (int i = 0; i < imgpath.length; i++) {
                    if (!imgpath[i].isEmpty()) {
                        imgNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, imgpath[i]);
                        imgName.add(imgNm);
                    }
                }
            }
        }
        try {
       	List<SubLinkFieldDetailsHistory> historys =	iSectionService.getAllDetailHistorysByDetailId(rowId, null, UserSession.getCurrent().getOrganisation());
        	if(historys!=null && !historys.isEmpty()) {
        		getEntity().setLmodDate(historys.get(0).getLmodDate());
        		if(historys.get(0).getUserId() !=null) {
            		getEntity().setCreatedBy(historys.get(0).getUserId().getEmpname());
        		}
        		getEntity().setUpdatedDate(historys.get(0).getUpdatedDate());
        		if(historys.get(0).getUpdatedBy() !=null) {
            		getEntity().setModifiedBy(historys.get(0).getUpdatedBy().getEmpname());
        		}
        		 for (SubLinkFieldDetailsHistory hist :historys) {
        			if(hist.getChekkerflag()!=null ) {
        				if(hist.getUpdatedBy() !=null) {
        				getEntity().setLastApprovedBy(hist.getUpdatedBy().getEmpname());
        				}
        				getEntity().setLastApprovedDate(hist.getUpdatedDate());
        				break;
        			}
        		}  
        } else {
        	SubLinkFieldDetails fieldDetails = iSectionService.getSubLinkFieldDetail(rowId);
			if (fieldDetails != null  && fieldDetails.getUserId()!=null) {
        		getEntity().setCreatedBy(fieldDetails.getUserId().getEmpname());
        	}
        }
         
        }catch (Exception e) {
        	LOG.error("Unable to fetch history details"+e);
		}
        if (getEntity().getAtt_01() != null) {
            attpath = getEntity().getAtt_01().toString().split(MainetConstants.operator.COMA);
            if (attpath != null) {
                String attNm = MainetConstants.BLANK;
                for (int i = 0; i < attpath.length; i++) {

                    if (!attpath[i].isEmpty()) {
                        attNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, attpath[i]);
                        attName.add(attNm);
                    }
                }
            }
        }
        if (getEntity().getAtt_02() != null) {
            attpath2 = getEntity().getAtt_02().toString().split(MainetConstants.operator.COMA);
            if (attpath2 != null) {
                String attNm2 = MainetConstants.BLANK;
                for (int i = 0; i < attpath2.length; i++) {

                    if (!attpath2[i].isEmpty()) {
                        attNm2 = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, attpath2[i]);
                        attName2.add(attNm2);
                    }
                }
            }
        }
        if (getEntity().getAtt_video_path() != null) {
            attpath = getEntity().getAtt_video_path().toString().split(MainetConstants.operator.COMA);
            if (attpath != null) {
                String attNm = MainetConstants.BLANK;
                for (int i = 0; i < attpath.length; i++) {

                    if (!attpath[i].isEmpty()) {
                        attNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, attpath[i]);
                        videoName.add(attNm);
                    }
                }
            }
        }
        return flag;
    }

    /**
     * To delete selected detail object.
     * @param rowId the long literal containing identifier value.
     */
    public void deleteDetailObject(final long rowId) {
        editDetailObject(rowId);
        getEntity().setIsDeleted(MainetConstants.IsDeleted.DELETE);
        this.setIsCallDelete(MainetConstants.IsDeleted.DELETE);
        saveOrUpdateForm();
    }

    public boolean hideDetailObject(final long rowId, String hideFlag) {
    	boolean flag=editDetailObject(rowId);
        getEntity().setIsDeleted(hideFlag);
        this.setIsCallDelete(hideFlag);
        saveOrUpdateForm();
        
        return true;
    }


    private final void setFilePaths(final Object beanClass, final String propertyName, final String directoryTree)
            throws FrameworkException {

        if (beanClass == null) {
            throw new FrameworkException("Wrapping 'beanClass' cannot be null.");
        }

        final BeanWrapper wrapper = new BeanWrapperImpl(beanClass);

        final boolean foundProperty = wrapper.isReadableProperty(propertyName);

        if (foundProperty) {
            final Object value = wrapper.getPropertyValue(propertyName);

            if (value != null) {
                final String[] tempArr = value.toString().split(MainetConstants.operator.COMA);

                wrapper.setPropertyValue(propertyName, MainetConstants.BLANK);

                for (int i = 0; i < tempArr.length; i++) {
                    final String[] splitTempArrBySlash = tempArr[i].split(MainetConstants.operator.FORWARD_SLACE);

                    String path = MainetConstants.BLANK;
                    if (!splitTempArrBySlash[0].equals(MainetConstants.BLANK)) {
                        if (splitTempArrBySlash.length == 1) {
                            path = directoryTree + MainetConstants.operator.FORWARD_SLACE + tempArr[i];
                        }

                        else {

                            path = directoryTree + MainetConstants.operator.FORWARD_SLACE
                                    + splitTempArrBySlash[splitTempArrBySlash.length - 1];
                        }

                        if (splitTempArrBySlash.length == 0) {
                            path = directoryTree + MainetConstants.operator.FORWARD_SLACE + tempArr[i];
                        }

                        if (i < (tempArr.length - 1)) {
                            path += MainetConstants.operator.COMA;
                        }

                        wrapper.setPropertyValue(propertyName, wrapper.getPropertyValue(propertyName) + path);

                    }
                }

            }

        } else {
            throw new FrameworkException(
                    "Unable to found property " + propertyName + " of class " + beanClass.getClass().getSimpleName());
        }
    }

    private String getStorePath(final String pathRoot) {
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date = formatter.format(new Date());
        String path = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date + File.separator + pathRoot
                + "QuickLink";
        path += (File.separator);
        path += (subLinkMaster.getSubLinkNameEn());
        path += (File.separator);
        path += (Utility.getTimestamp());

        return path;
    }

    /**
     * Added by Deepika Pimpale To delete files and images which are selected.
     * @param fileName containing filename and @param del contains type of filename ie.image or file.
     */
    public void deleteFiles(final String fileName, final String del, String elementName) {
    	SubLinkFieldDetails entity = getEntity();
        if (del.equals("image")) {
            imgpath = getEntity().getProfile_img_path().toString().split(MainetConstants.operator.COMA);
            newProfilepath = getEntity().getProfile_img_path();
            if (imgpath != null) {
                String imgNm = MainetConstants.BLANK;
                String imgdir = MainetConstants.BLANK;
                String path = MainetConstants.BLANK;
                for (int i = 0; i < imgpath.length; i++) {
                    if (!imgpath[i].isEmpty()) {
                        imgNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, imgpath[i]);
                        imgdir = StringUtility.getStringBeforeChar("/", imgpath[i]);
                        path = imgdir + MainetConstants.operator.FORWARD_SLACE + imgNm;
                        if (fileName.equals(imgNm)) {
                            newProfilepath = newProfilepath.replace(path, MainetConstants.BLANK);
                            getEntity().setProfile_img_path(newProfilepath);
                            subLinkMaster.getSubLinkFieldDetails().clear();
                            subLinkMaster.getSubLinkFieldDetails().add(getEntity());
                            iSectionService.saveSection(subLinkMaster);
                        }
                    }
                }
            }
        } else if (del.equals("file")) {
        	
        	if (elementName.equalsIgnoreCase(MainetConstants.FORM_ELEMENT.ATTACHMENT_1)) {
            attpath = getEntity().getAtt_01().toString().split(MainetConstants.operator.COMA);
            newAttpath = getEntity().getAtt_01();
            if (attpath != null) {
                String attNm = MainetConstants.BLANK;
                String attdir = MainetConstants.BLANK;
                String path = MainetConstants.BLANK;
                for (int i = 0; i < attpath.length; i++) {
                    if (!attpath[i].isEmpty()) {
                        attNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, attpath[i]);
                        attdir = StringUtility.getStringBeforeChar("/", attpath[i]);
                        path = attdir + MainetConstants.operator.FORWARD_SLACE + attNm;
                        if (fileName.equals(attNm)) {
                        	if(attpath.length == 0) {
                            newAttpath = newAttpath.replace(path, MainetConstants.BLANK);
                        	}else {
                        		if(getEntity().getAtt_01().contains(path+",")) {
                        			newAttpath = newAttpath.replace(path+",", MainetConstants.BLANK);
                        		}else if(getEntity().getAtt_01().contains(","+path)){
                        			newAttpath = newAttpath.replace(","+path, MainetConstants.BLANK);
                        		}else {
                        			newAttpath = newAttpath.replace(path, MainetConstants.BLANK);
                        		}
                        	}
                            getEntity().setAtt_01(newAttpath);
                            //this.saveOrUpdateForm();
                            subLinkMaster.getSubLinkFieldDetails().clear();
                            subLinkMaster.getSubLinkFieldDetails().add(getEntity());
                            iSectionService.saveSection(subLinkMaster); 
                        }
                    }
                }
            }
         }
        	
        else if (elementName.equalsIgnoreCase(MainetConstants.FORM_ELEMENT.ATTACHMENT_2)) {
            attpath2 = getEntity().getAtt_02().toString().split(MainetConstants.operator.COMA);
            newAttpath2 = getEntity().getAtt_02();
            if (attpath2 != null) {
                String attNm2 = MainetConstants.BLANK;
                String attdir2 = MainetConstants.BLANK;
                String path2 = MainetConstants.BLANK;
                for (int i = 0; i < attpath2.length; i++) {
                    if (!attpath2[i].isEmpty()) {
                        attNm2 = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, attpath2[i]);
                        attdir2 = StringUtility.getStringBeforeChar("/", attpath2[i]);
                        path2 = attdir2 + MainetConstants.operator.FORWARD_SLACE + attNm2;
                        if (fileName.equals(attNm2)) {
                            newAttpath2 = newAttpath2.replace(path2, MainetConstants.BLANK);
                            if(attpath2.length == 0) {
                        		newAttpath2 = newAttpath2.replace(path2, MainetConstants.BLANK);
                        	}else {
                        		if(getEntity().getAtt_02().contains(path2+",")) {
                        			newAttpath2 = newAttpath2.replace(path2+",", MainetConstants.BLANK);
                        		}else if(getEntity().getAtt_02().contains(","+path2)){
                        			newAttpath2 = newAttpath2.replace(","+path2, MainetConstants.BLANK);
                        		}else {
                        			newAttpath2 = newAttpath2.replace(path2, MainetConstants.BLANK);
                        		}
                        	}
                            getEntity().setAtt_02(newAttpath2);
                            //this.saveOrUpdateForm();
                            subLinkMaster.getSubLinkFieldDetails().clear();
                            subLinkMaster.getSubLinkFieldDetails().add(getEntity());
                            iSectionService.saveSection(subLinkMaster);
                        }
                    }
                }
            }
         }	

        } else {
            attpath = getEntity().getAtt_video_path().toString().split(MainetConstants.operator.COMA);
            newAttpath = getEntity().getAtt_video_path();
            if (attpath != null) {
                String attNm = MainetConstants.BLANK;
                String attdir = MainetConstants.BLANK;
                String path = MainetConstants.BLANK;
                for (int i = 0; i < attpath.length; i++) {
                    if (!attpath[i].isEmpty()) {
                        attNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, attpath[i]);
                        attdir = StringUtility.getStringBeforeChar("/", attpath[i]);
                        path = attdir + MainetConstants.operator.FORWARD_SLACE + attNm;
                        if (fileName.equals(attNm)) {
                            newAttpath = newAttpath.replace(path, MainetConstants.BLANK);
                            getEntity().setAtt_video_path(newAttpath);
                            subLinkMaster.getSubLinkFieldDetails().clear();
                            subLinkMaster.getSubLinkFieldDetails().add(getEntity());
                            iSectionService.saveSection(subLinkMaster);
                        }
                    }
                }
            }

        }
    }

    private String getStringAfterChars(final String afterChar, final String sourceString) {
        final int lastIndex = sourceString.lastIndexOf(afterChar);

        return sourceString.substring(lastIndex + 1, sourceString.length());
    }

    public String[] getImgList() {
        return imgList;
    }

    public void setImgList(final String[] imgList) {
        this.imgList = imgList;
    }

    public String[] getImgpath() {
        return imgpath;
    }

    public void setImgpath(final String[] imgpath) {
        this.imgpath = imgpath;
    }

    public List<String> getImgName() {
        return imgName;
    }

    public void setImgName(final List<String> imgName) {
        this.imgName = imgName;
    }

    public String getNewProfilepath() {
        return newProfilepath;
    }

    public void setNewProfilepath(final String newProfilepath) {
        this.newProfilepath = newProfilepath;
    }

    public SubLinkMaster getSubLinkMaster() {
        return subLinkMaster;
    }

    public void setSubLinkMaster(final SubLinkMaster subLinkMaster) {
        this.subLinkMaster = subLinkMaster;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
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

    @Override
    public String doUploading(final List<MultipartFile> multipartFiles, final String propertyName, String removeFileName,
            final String randno, final String count, final boolean isSingleFileUploadBox)
            throws IOException, FileSizeLimitExceededException {
    	if(propertyName!=null && propertyName.equals("profile_img_path")) {
    		imgName.clear();
    	}

        if (!isSingleFileUploadBox) {
            listGuid.set(Integer.parseInt(count), randno);
        }

        boolean isProfileImage = false;

        for (final String excPath : MainetConstants.excludePath) {
            isProfileImage = excPath.equals(propertyName);
            if (isProfileImage) {
                break;
            }
        }

        String propertyValue = MainetConstants.BLANK;

        String propValue = MainetConstants.BLANK;

        String existingFilePath = MainetConstants.BLANK;

        final BeanWrapper wrapper = new BeanWrapperImpl(getEntity());

        final boolean foundProperty = wrapper.isReadableProperty(propertyName);

        if (foundProperty) {
            propertyValue = wrapper.getPropertyValue(propertyName) != null ? wrapper.getPropertyValue(propertyName).toString()
                    : MainetConstants.BLANK;
        }
      
        boolean isNotNullOrNotEmpty = ((propertyValue != null) && !MainetConstants.BLANK.equals(propertyValue));
        
        //check if property is already contain same file or not
        for (MultipartFile multipartFile : multipartFiles) {
        	String filename = multipartFile.getOriginalFilename();
        	if (removeFileName.equals(MainetConstants.BLANK)) {
        		if(propertyValue.contains(filename)) {
        			String tt = MainetConstants.BLANK;
        			 for (final String tempFile : propertyValue.split(MainetConstants.operator.COMA)) {
        				 String tt1 = StringUtility.staticStringAfterChar(
        	                        MainetConstants.operator.FORWARD_SLACE, tempFile);
        				 if(tt1.equals(MainetConstants.BLANK)) {
        					 tt = tt+ tempFile + MainetConstants.operator.COMA;
        				 }else {
        					 tt = tt+ tt1 + MainetConstants.operator.COMA;
        				 }
        				 
        				 
        			 }
        			 tt = StringUtility.staticRemoveLastChar(MainetConstants.operator.COMA,
        	                    tt);
        			return tt;
        		}
        	}
		}
        
        // Check if property is not null and already has some value in database
        if (isNotNullOrNotEmpty) {
            // Check whether have more than one or more uploaded file(s) path in database.

            String tt = MainetConstants.BLANK;

            // If then split it and take first file path.
            for (final String tempFile : propertyValue.split(MainetConstants.operator.COMA)) {
                tt = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, tempFile);

                if (existingFilePath.equals(MainetConstants.BLANK)) {
                    // Get file path of already store file in database.
                    existingFilePath = (MainetConstants.BLANK.equals(tt)) ? tt : tt + MainetConstants.operator.FORWARD_SLACE;
                }

                tt = StringUtility.staticStringAfterChar(MainetConstants.operator.FORWARD_SLACE, tempFile);

                propValue += ((MainetConstants.BLANK.equals(tt)) ? tempFile : tt) + MainetConstants.operator.COMA;

                tt = MainetConstants.BLANK;
            }

            // Get actual file(s) name which are stored in database.
            tt = StringUtility.staticRemoveLastChar(MainetConstants.operator.COMA, propValue);

            propertyValue = (MainetConstants.BLANK.equals(tt)) ? propValue : tt;

        }

        // Get the file storage cache object from current entity.
        final FileStorageCache cache = getFileStorageCache();

        final ListIterator<MultipartFile> listIterator = multipartFiles.listIterator();

        MultipartFile file = null;
        File file2 = null;
        Iterator<Map.Entry<Long, String>> iter = null;
        Map.Entry<Long, String> entry = null;
        while (listIterator.hasNext()) {
            file = listIterator.next();

            if (removeFileName.equals(MainetConstants.BLANK)) {
                cache.getMultipleFiles().addRemove(file, MainetConstants.Transaction.Mode.ADD);
            }
            if (isNotNullOrNotEmpty) {
                propertyValue += MainetConstants.operator.COMA;

                if (!removeFileName.equals(MainetConstants.BLANK)) {
                    try {
                        final int s3 = removeFileName.lastIndexOf(MainetConstants.operator.COMA);
                        removeFileName = removeFileName.substring(s3);
                        removeFileName = removeFileName.substring(1);

                        file2 = new File(removeFileName);
                        cache.getMultipleFiles().addRemove(file2, MainetConstants.Transaction.Mode.DELETE);

                        iter = getFileListMap().entrySet().iterator();
                        while (iter.hasNext()) {
                            entry = iter.next();

                            if (count.equalsIgnoreCase(entry.getKey().toString())) {
                                iter.remove();
                            }

                        }

                        propertyValue = propertyValue.replace(removeFileName + MainetConstants.operator.COMA,
                                MainetConstants.BLANK);
                        if(!propertyValue.equals(MainetConstants.BLANK)) {
                        propertyValue = propertyValue.substring(0, propertyValue.length() - 1);
                        }
                    } catch (final Exception e) {
                        throw new FrameworkException("Remove first file", e);
                    }
                }
            }
            if (removeFileName.equals(MainetConstants.BLANK)) {
                propertyValue += file.getOriginalFilename();
            }

            isNotNullOrNotEmpty = true;

        }

        // Make empty property value before updating.
        wrapper.setPropertyValue(propertyName, MainetConstants.BLANK);

        // Split the string and append with existing file path stored in database.
       
       if(!propertyValue.isEmpty() && propertyValue!=MainetConstants.BLANK ) {
    	   if(propertyValue.charAt(0) == ',') {
    		   propertyValue = propertyValue.substring(1,propertyValue.length());
    	   }
       }

        final String[] splitArr = propertyValue.split(MainetConstants.operator.COMA);
        propValue = MainetConstants.BLANK;
        for (int i = 0; i < splitArr.length; i++) {
        	if(!splitArr[i].isEmpty()) {
        		 propValue = wrapper.getPropertyValue(propertyName) + (existingFilePath + splitArr[i]);

                 if ((i + 1) < splitArr.length) {
                     propValue += MainetConstants.operator.COMA;
                 }

                 wrapper.setPropertyValue(propertyName, propValue);
        	}
           
        }

        return propertyValue;
    }

    
    
    private FileStorageCache getFileCache() {

        if (fileStorageCache == null) {
            fileStorageCache = FileStorageCache.getInstance();
        }

        return fileStorageCache;
    }

    public FileStorageCache getFileStorageCache() {
        return getFileCache();
    }

    @Override
    public List<String> getListGuid() {
        return listGuid;
    }

    /**
     * @param listGuid the listGuid to set
     */
    @Override
    public void setListGuid(final List<String> listGuid) {
        this.listGuid = listGuid;
    }

    public boolean getListMode() {
        return listMode;
    }

    public void setListMode(final boolean listMode) {
        this.listMode = listMode;
    }

    public Long getSecType() {
        return secType;
    }

    public void setSecType(final Long secType) {
        this.secType = secType;
    }

    public String[] getAttList() {
        return attList;
    }

    public void setAttList(final String[] attList) {
        this.attList = attList;
    }

    public List<String> getAttName() {
        return attName;
    }

    public void setAttName(final List<String> attName) {
        this.attName = attName;
    }

    

    public String[] getAttpath2() {
		return attpath2;
	}

	public void setAttpath2(String[] attpath2) {
		this.attpath2 = attpath2;
	}

	public List<String> getAttName2() {
		return attName2;
	}

	public void setAttName2(List<String> attName2) {
		this.attName2 = attName2;
	}

    public String[] getAttpath() {
        return attpath;
    }

    public void setAttpath(final String[] attpath) {
        this.attpath = attpath;
    }

    

    public String[] getAttList2() {
		return attList2;
	}

	public void setAttList2(String[] attList2) {
		this.attList2 = attList2;
	}

    public String getNewAttpath() {
        return newAttpath;
    }

    public void setNewAttpath(final String newAttpath) {
        this.newAttpath = newAttpath;
    }

    
    public String getNewAttpath2() {
		return newAttpath2;
	}

	public void setNewAttpath2(String newAttpath2) {
		this.newAttpath2 = newAttpath2;
	}

    public List<String> getVideoName() {
        return videoName;
    }

    public void setVideoName(final List<String> videoName) {
        this.videoName = videoName;
    }

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    public String getIsChecker() {
        return isChecker;
    }

	public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }
    
    public void getMaxOrderDetail() {
    	Long id=subLinkMaster.getId();
		Double details=iSectionService.getSubLinkFieldMaxOrder(id,UserSession.getCurrent().getOrganisation().getOrgid());
		if(details != null) {
		details=details+1;
		}else {
			details=1.0;
		}
		
		getEntity().setSubLinkFieldDtlOrd(details);
		setLinkName(getSection().getLinksMaster().getLinkTitleEg());
    }

	public List<LookUp> getDept() {
		return dept;
	}
	    	    
	public void setDept(List<LookUp> dept) {
		this.dept = dept;
	}
		
	public List<LookUp> getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(List<LookUp> financialYear) {
		this.financialYear = financialYear;
	}
	
	public List<LookUp> getInvestmentTypeList() {
		return investmentTypeList;
	}

	public void setInvestmentTypeList(List<LookUp> investmentTypeList) {
		this.investmentTypeList = investmentTypeList;
}

	public String getIsOperator() {
		return isOperator;
	}

	public void setIsOperator(String isOperator) {
		this.isOperator = isOperator;
	}

	public String getIsCallDelete() {
		return isCallDelete;
	}

	public void setIsCallDelete(String isCallDelete) {
		this.isCallDelete = isCallDelete;
	}
    
	
    public void setLinkName(String linkName) {
			this.linkName = linkName;
		}
	    
	    public String getLinkName() {
	    	return linkName;
	    }
}
