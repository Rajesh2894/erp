package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IAdminPublicNoticesService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.ui.validator.AdminPublicNoticesLinkValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.DmsService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;


/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminPublicNoticesFormModel extends AbstractEntryFormModel<PublicNotices> {
    private static final long serialVersionUID = 7405567806236977020L;
    private long moduleId;

    private static final Logger LOG = Logger.getLogger(AdminPublicNoticesFormModel.class);
    @Autowired
    private IAdminPublicNoticesService iAdminPublicNoticesService;

    @Autowired
    private DmsService dmsService;

    @Autowired
    private IEntitlementService iEntitlementService;
    

    private String isChecker;
    
    private boolean category = true; 

    private static List<LookUp> publishList = new ArrayList<>(0);
    private static List<LookUp> newOrImpLinkList = new ArrayList<>(0);
    private String[] attachList;
    private List<String> attachNameList=new ArrayList<>(0);
    private String[] imgList;
    
    private List<String> imgNameList=new ArrayList<>(0);
    
    private String newAttpath = MainetConstants.BLANK;
    static {
    	LookUp yesLookup = new LookUp();
    	yesLookup.setLookUpId(1L);
    	yesLookup.setDescLangFirst(ApplicationSession.getInstance().getMessage("eip.pub.notices.yes.eng"));
    	yesLookup.setDescLangSecond(ApplicationSession.getInstance().getMessage("eip.pub.notices.yes.reg"));
        publishList.add(yesLookup);
        LookUp noLookup = new LookUp();
        noLookup.setLookUpId(2L);
        noLookup.setDescLangFirst(ApplicationSession.getInstance().getMessage("eip.pub.notices.no.eng"));
        noLookup.setDescLangSecond(ApplicationSession.getInstance().getMessage("eip.pub.notices.no.reg"));
        publishList.add(noLookup);
//        newOrImpLinkList.add(new LookUp(1L,MainetConstants.NEW_LINK_DESC));
        newOrImpLinkList.add(new LookUp(2L,MainetConstants.IMP_LINK_DESC));
        newOrImpLinkList.add(new LookUp(3L,MainetConstants.QUOTATIONS_DESC));
        newOrImpLinkList.add(new LookUp(4L,MainetConstants.TENDERS_DESC));
        newOrImpLinkList.add(new LookUp(5L,MainetConstants.ON_GOING_PROJECTS_DESC));
    }

    private String mode = null;
    String filecount = "";
   

    private PublicNotices publicNotices;

    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
    String date = formatter.format(new Date());
    private String publicNoticesPath = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
            + File.separator
            + MainetConstants.DirectoryTree.EIP + File.separator + "PUBLIC NOTICES" + File.separator + Utility.getTimestamp();

    PublicNotices entity = null;

    public String[] getAttachList() {
		return attachList;
	}

	public void setAttachList(String[] attachList) {
		this.attachList = attachList;
	}
	
	
	public List<String> getAttachNameList() {
		return attachNameList;
	}

	public void setAttachNameList(List<String> attachNameList) {
		this.attachNameList = attachNameList;
	}
	
	 private final List<LookUp> modules = new ArrayList<>(0);

	  @Autowired
    private ISectionService iSectionService;
    
    @Override
    public void addForm() {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        PublicNotices publicNotice = new PublicNotices();
        if(category) {
        	publicNotice.setPublishFlag("1");
        }
        setEntity(publicNotice);
        setMode(MainetConstants.Transaction.Mode.ADD);
        filecount = "Y";
    }

    @Override
    public void editForm(final long rowId) {
    	attachList=null;
    	
    	//profileImgPath
        // FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        setMode(MainetConstants.Transaction.Mode.UPDATE);

        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }
        setEntity(iAdminPublicNoticesService.getPublicNotices(rowId));
        if (getEntity().getProfileImgPath() != null) {
            filecount = "N";
        } else {

            filecount = "";
        }

        if(null!=attachNameList)
            attachNameList.clear();
            else
            	attachNameList=new ArrayList<>(0);
        
        if (getEntity().getProfileImgPath() != null) {
            attachList = getEntity().getProfileImgPath().toString().split(MainetConstants.operator.COMA);
            
        	for(String fileName:attachList) {
        		attachNameList.add(getAttachName(fileName));
        	}
        }
        
        if(null!=imgNameList)
        	imgNameList.clear();
            else
            	imgNameList=new ArrayList<>(0);
        
        
        if (getEntity().getImagePath() != null) {
        	imgList = getEntity().getImagePath().toString().split(MainetConstants.operator.COMA);
            
        	for(String fileName:imgList) {
        		imgNameList.add(getAttachName(fileName));
        	}
        }
        if(category) {
	        if(getEntity().getIsUsefullLink() != null && getEntity().getIsUsefullLink().equals(MainetConstants.QUOTATIONS_CODE)) {
				getEntity().setNewOrImpLink("3"); 
			}else if(getEntity().getIsUsefullLink() != null && getEntity().getIsUsefullLink().equals(MainetConstants.TENDERS_CODE)) {
				getEntity().setNewOrImpLink("4"); 
			}else if(getEntity().getIsUsefullLink() != null && getEntity().getIsUsefullLink().equals(MainetConstants.ON_GOING_PROJECTS_CODE)) {				
				getEntity().setNewOrImpLink("5"); 
			}else if(getEntity().getIsUsefullLink() != null && getEntity().getIsUsefullLink().equals(MainetConstants.NEW_LINK_CODE)) {
				getEntity().setNewOrImpLink("1"); 
			}else if(getEntity().getIsUsefullLink() != null && getEntity().getIsUsefullLink().equals(MainetConstants.IMP_LINK_CODE)) {
				getEntity().setNewOrImpLink("2"); 
			}else { 		 
				getEntity().setNewOrImpLink("0"); 
			} 	
        }else {
	    	  if(getEntity().getIsUsefullLink() != null && getEntity().getIsUsefullLink().equals(MainetConstants.FLAGY)) {
	    		  getEntity().setNewOrImpLink("1"); 
			  }else if(getEntity().getIsHighlighted() != null && getEntity().getIsHighlighted().equals(MainetConstants.FLAGY)) {
				  getEntity().setNewOrImpLink("2"); }else { getEntity().setNewOrImpLink("0"); 
			  }
        }
 
    }

    @Override
    public void delete(final long rowId) {
        iAdminPublicNoticesService.delete(rowId);
    }

    @Override
    public boolean saveOrUpdateForm() throws FrameworkException{
        String uploadedFile = MainetConstants.BLANK;
        final PublicNotices entity = getEntity();
        String profDirectoryTree = MainetConstants.BLANK;
        String directoryTree = MainetConstants.BLANK;
        String iprofDirectoryTree = MainetConstants.BLANK;
        String idirectoryTree = MainetConstants.BLANK;
        if(category) {
        	entity.setCategoryFlag(true);
        }
        validateBean(entity, AdminPublicNoticesLinkValidator.class);
        Map<String, String> fileMap = null;

        if (hasValidationErrors()) {

            return false;
        }
        Boolean isDMS = false;
        //String attFilesWithPath = "";
        try {
           // uploadDocAndVerifyDoc(publicNoticesPath);
            
            idirectoryTree = iprofDirectoryTree = profDirectoryTree = directoryTree = getStorePath(MainetConstants.DirectoryTree.EIP);
            
            
            if (entity.getProfileImgPath() != null) {
                attachList = entity.getProfileImgPath().toString().split(MainetConstants.operator.COMA);
            }
			/*
			 * for (int i = 0; i < attachList.length; i++) { if(i!=0) {
			 * attFilesWithPath=attFilesWithPath+","; } attFilesWithPath = attFilesWithPath
			 * + directoryTree + "/" +attachList[i]; }
			 */
            
          
        
        if (attachList != null) {
            String path = MainetConstants.BLANK;
            for (final String element : attachList) {
            	if(!element.contains(MainetConstants.operator.DOUBLE_BACKWARD_SLACE)) {
            		if(element.lastIndexOf(MainetConstants.operator.FORWARD_SLACE) == -1) {
            			path = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, element);
            		}else {
	            		path = element.substring(0, element.lastIndexOf(MainetConstants.operator.FORWARD_SLACE));
	            		path = path.replaceAll("/", "\\\\");
            		}
                }else {
                    path = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, element);
                }
                if (path.equals(MainetConstants.BLANK)) {
                	
                    setFilePaths(entity, "profileImgPath", directoryTree);

                }
                if (!path.equals(MainetConstants.BLANK)) {
                    directoryTree = path;
                }

            }
        }
        
        if (entity.getImagePath() != null) {
        	imgList = entity.getImagePath().toString().split(MainetConstants.operator.COMA);
        }
        
        if (imgList != null) {
            String path = MainetConstants.BLANK;
            for (final String element : imgList) {           	
            	if(!element.contains(MainetConstants.operator.DOUBLE_BACKWARD_SLACE)) {
            		if(element.lastIndexOf(MainetConstants.operator.FORWARD_SLACE) == -1) {
            			path = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, element);
            		}else {
	            		path = element.substring(0, element.lastIndexOf(MainetConstants.operator.FORWARD_SLACE));
	            		path = path.replaceAll("/", "\\\\");  
            		}
                }else {
                	path = StringUtility.getStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, element);
                }
                if (path.equals(MainetConstants.BLANK)) {               	
                    setFilePaths(entity, "imagePath", idirectoryTree);
                }
                if (!path.equals(MainetConstants.BLANK)) {
                    idirectoryTree = path;
                }

            }
        }
        
        
        
        directoryTree = directoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        profDirectoryTree = profDirectoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        idirectoryTree = idirectoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        iprofDirectoryTree = iprofDirectoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        
        directoryTree = directoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        profDirectoryTree = profDirectoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        idirectoryTree = idirectoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        iprofDirectoryTree = iprofDirectoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        
        directoryTree = directoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        profDirectoryTree = profDirectoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        idirectoryTree = idirectoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        iprofDirectoryTree = iprofDirectoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        
        final FileStorageCache cache = getEntity().getFileStorageCache();
        
        
        if(entity.getIsDeleted()==null) {
        	entity.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        } 
        List<File> allFiles = cache.getFileList();
        List<File> docFiles = new ArrayList<File>();
        List<File> imageFiles = new ArrayList<File>();
        for (File file : allFiles) {
        	String fileName = file.toString();
        	
        	if(attachList != null && attachList.length > 0 ) {
	        	for(int index = 0; index < attachList.length;index++){
	
	    			if(getAttachName(attachList[index]).equals(getAttachName(fileName))){
	    				docFiles.add(file);
	    			}
	    		}
        	}
        	if(imgList != null && imgList.length > 0 ) {
	        	for(int index = 0; index < imgList.length;index++){
	
	    			if(getAttachName(imgList[index]).equals(getAttachName(fileName))){
	    				imageFiles.add(file);
	    			}
	    		}
        	}
        	
           // int index = fileName.lastIndexOf('.');
			/*
			 * if(index > 0) { String extension = fileName.substring(index + 1);
			 * if(extension.toUpperCase().equalsIgnoreCase("PDF") ||
			 * extension.toUpperCase().equalsIgnoreCase("DOC") ||
			 * extension.toUpperCase().equalsIgnoreCase("DOCX") ||
			 * extension.toUpperCase().equalsIgnoreCase("XLS")) { docFiles.add(file); }else
			 * { imageFiles.add(file); } }
			 */
   	
		}
        if (MainetConstants.IsDeleted.NOT_DELETE.equals(entity.getIsDeleted())) {
            try {
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                	
                	LOG.info("Uploading  file at cache in AdminPublicNoticesFormModel");
                	if(docFiles.size() > 0) {
                		getFileNetClient().uploadFileList(docFiles, Filepaths.getfilepath()+MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER+MainetConstants.operator.FORWARD_SLACE+directoryTree);
                	}
                	if(imageFiles.size() > 0) {
                		getFileNetClient().uploadFileList(imageFiles, Filepaths.getfilepath()+MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER+MainetConstants.operator.FORWARD_SLACE+idirectoryTree);
                	}
                    
                }
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                	
                	LOG.info("Uploading  file at filenet in AdminPublicNoticesFormModel");
                	if(docFiles.size() > 0) {
                		getFileNetClient().uploadFileList(docFiles, directoryTree);
                	}
                	if(imageFiles.size() > 0) {
                		getFileNetClient().uploadFileList(imageFiles, idirectoryTree);
                	}
                    
                }
                if (cache.getProfileImage() != null) {
                	
                	LOG.info("Uploading  profileImage at filenet in AdminPublicNoticesFormModel");
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
            
            
            
            
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        if (!uploadedFile.equals(MainetConstants.BLANK)) {
            uploadedFile = publicNoticesPath + File.separator + uploadedFile;
        }
        String filepath = null;
        if ((entity.getChekkerflag() != null) && entity.getChekkerflag().equals(MainetConstants.YES)) {
            if (MainetConstants.YES.equals(ApplicationSession.getInstance().getMessage(MainetConstants.DMS_CONFIGURE))) {
                isDMS = true;
            }
        }

        if (isDMS) {
            if (uploadedFile.equals(MainetConstants.BLANK)) {
                if ((entity.getProfileImgPath() != null) && !entity.getProfileImgPath().equals(MainetConstants.BLANK)) {
                    filepath = entity.getProfileImgPath();
                }

            } else {
                filepath = uploadedFile;
            }
            if ((filepath != null) && !filepath.equals(MainetConstants.BLANK)) {

                Boolean folderExist = false;
                final String physicalPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
                filepath = physicalPath + File.separator + filepath;
                if (entity.getFolderPath() != null) {
                    folderExist = true;
                    publicNoticesPath = entity.getFolderPath();
                }
                fileMap = dmsService.createDocument(publicNoticesPath, filepath, folderExist);

                if (fileMap != null) {
                    entity.setFolderPath(fileMap.get("FOLDER_PATH"));
                    entity.setDocId(fileMap.get("DOC_ID"));
                    entity.setDocName(fileMap.get("FILE_NAME"));
                    if ((entity.getDocVersion() != null) && !entity.getDocVersion().isEmpty()) {
                        Integer ver = Integer.valueOf(entity.getDocVersion());
                        ver = ver + 1;
                        entity.setDocVersion(String.valueOf(ver));
                    } else {
                        entity.setDocVersion("1");
                    }
                } else {
                    throw new RuntimeException();
                }
            }
        }
        if(category) {
	        	if(entity.getNewOrImpLink().equalsIgnoreCase("3")) {
	        		entity.setIsUsefullLink(MainetConstants.QUOTATIONS_CODE);
	        	}else if(entity.getNewOrImpLink().equalsIgnoreCase("4")) {
	        		entity.setIsUsefullLink(MainetConstants.TENDERS_CODE);
	        	}else if(entity.getNewOrImpLink().equalsIgnoreCase("5")) {
	        		entity.setIsUsefullLink(MainetConstants.ON_GOING_PROJECTS_CODE);        		
	        	}else if(entity.getNewOrImpLink().equalsIgnoreCase("1")) {
					entity.setIsUsefullLink(MainetConstants.NEW_LINK_CODE); 
				}else if(entity.getNewOrImpLink().equalsIgnoreCase("2")) {
					entity.setIsUsefullLink(MainetConstants.IMP_LINK_CODE); 
				}else {
		        	entity.setIsUsefullLink(null);
	        	}
        }
        
	/*
         * if (!uploadedFile.equals(MainetConstants.BLANK)) { entity.setProfileImgPath(uploadedFile); }
         */
        long id=entity.getPnId(); 
        if(MainetConstants.FLAGY.equalsIgnoreCase(entity.getChekkerflag())){
    		final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
	    	if(!(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH))) {
             	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                         UserSession.getCurrent().getOrganisation().getOrgid());
                 if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
                 	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
                 	entity.setChekkerflag(MainetConstants.FLAGN);
                 }
	    	}
         }
        
        iAdminPublicNoticesService.saveOrUpdate(entity);
       Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.notice")+" "+(UserSession.getCurrent().getLanguageId()==1?entity.getNoticeSubEn():entity.getNoticeSubReg()),entity.getChekkerflag(),id,entity.getUpdatedBy());
        return true;
    }

    private String getStorePath(final String pathRoot) {
        String fileNetPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
    	SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date = formatter.format(new Date());
        String path = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date + File.separator + pathRoot;
        path += (File.separator);
        path += ("EIP_PUBLIC_NOTICES" + File.separator + "HOME_PAGE");
        path += (File.separator);
        path += (UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp());
        logger.info("filenet path: "+path);
        return path;
    }
    private void uploadDocAndVerifyDoc(final String directoryTree)
            throws Exception {

        if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            final List<File> list = new ArrayList<>();
            String fileAbsolutePath = null;
            Iterator entries = FileUploadUtility.getCurrent().getFileMap().entrySet().iterator();
            while (entries.hasNext()) {
                Entry thisEntry = (Entry) entries.next();

                Long key = (Long) thisEntry.getKey();
                final Set<File> files = FileUploadUtility.getCurrent().getFileMap().get(key);
                final File file = new ArrayList<>(files).get(0);
                if (key == 0L) {
                    fileAbsolutePath = file.getAbsolutePath();
                    this.getEntity().setProfileImgPath(directoryTree + File.separator + file.getName());
                }
                if (key == 1L) {
                    this.getEntity().setImagePath(directoryTree + File.separator + file.getName());
                }
                list.add(file);
            }

            try {
                getFileNetClient().uploadFileList(list, directoryTree);
            } catch (final Exception e) {
                LOG.error(MainetConstants.ERROR_OCCURED, e);
            }
            FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        }

    }
  
    public void deleteFiles(final String fileName, final String del) {
		entity = getEntity();
    
		if (del.equals("image")) {
        	String images = entity.getImagePath();
        	imgList = entity.getImagePath().toString().split(MainetConstants.operator.COMA);
            
            if (imgList != null) {
                String imgNm = MainetConstants.BLANK;
                String imgdir = MainetConstants.BLANK;
                String path = MainetConstants.BLANK;
                for (int i = 0; i < imgList.length; i++) {
                    if (!imgList[i].isEmpty()) {
                        imgNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, imgList[i]);
                        imgdir = StringUtility.getStringBeforeChar("/", imgList[i]);
                        path = imgdir + MainetConstants.operator.FORWARD_SLACE + imgNm;
                        if (fileName.equals(imgNm)) {
                        	if(entity.getImagePath().contains(path+",")) {
                        		images = images.replace(path+",", MainetConstants.BLANK);
                    		}else if(entity.getImagePath().contains(","+path)){
                    			images = images.replace(","+path, MainetConstants.BLANK);
                    		}else {
                    			images = images.replace(path, MainetConstants.BLANK);
                    		}
                        }
                    }
                }
                entity.setImagePath(images);
                iAdminPublicNoticesService.saveOrUpdate(entity);
            }
        } else if (del.equals("file")) {
        	attachList = entity.getProfileImgPath().toString().split(MainetConstants.operator.COMA);
            newAttpath = entity.getProfileImgPath();
            if (attachList != null) {
                String attNm = MainetConstants.BLANK;
                String attdir = MainetConstants.BLANK;
                String path = MainetConstants.BLANK;
                for (int i = 0; i < attachList.length; i++) {
                    if (!attachList[i].isEmpty()) {
                        attNm = getStringAfterChars(MainetConstants.operator.FORWARD_SLACE, attachList[i]);
                        attdir = StringUtility.getStringBeforeChar("/", attachList[i]);
                        path = attdir + MainetConstants.operator.FORWARD_SLACE + attNm;
                        if (fileName.equals(attNm)) {
                        	if(attachList.length == 0) {
                        		newAttpath = newAttpath.replace(path, MainetConstants.BLANK);
                        	}else {
                        		if(entity.getProfileImgPath().contains(path+","))
                        		{
                        			newAttpath = newAttpath.replace(path+",", MainetConstants.BLANK);
                        		}else if(entity.getProfileImgPath().contains(","+path)){
                        			newAttpath = newAttpath.replace(","+path, MainetConstants.BLANK);
                        		}else {
                        			newAttpath = newAttpath.replace(path, MainetConstants.BLANK);
                        		}
                        	}
                            entity.setProfileImgPath(newAttpath);
                            iAdminPublicNoticesService.saveOrUpdate(entity);
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

    /**
     * @return the publicNotices
     */
    public PublicNotices getPublicNotices() {
        return publicNotices;
    }

    /**
     * @param publicNotices the publicNotices to set
     */
    public void setPublicNotices(final PublicNotices publicNotices) {
        this.publicNotices = publicNotices;
    }

	public List<LookUp> getLinkCatagory() {
		return newOrImpLinkList;
	}
	 
	public boolean getLinkCatagoryFlag() {
		try {
			final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ENVIRNMENT_VARIABLE.GENERAL_PUBLIC_INFORMATION, MainetConstants.ENVIRNMENT_VARIABLE.ENV,
					UserSession.getCurrent().getOrganisation());
			if (lookup != null && StringUtils.isNotBlank(lookup.getOtherField()) && "Y".equalsIgnoreCase(lookup.getOtherField())) {
				this.category = true;
			}else {
				this.category = false;
			}

		} catch (Exception e) {
			this.category = false;
			LOG.error("DGP value not configure in ENV Perfix");
		}
		return this.category;
	}

	public static void setNewOrImpLinkList(List<LookUp> newOrImpLinkList) {
		AdminPublicNoticesFormModel.newOrImpLinkList = newOrImpLinkList;
	}

    /**
     * @return the publishList
     */
    public List<LookUp> getPublish() {
        return publishList;
    }

    public String getFilecount() {
        return filecount;
    }

    public void setFilecount(String filecount) {
        this.filecount = filecount;
    }

    /**
     * @param publishList the publishList to set
     */
    public static void setPublishList(final List<LookUp> publishList) {
        AdminPublicNoticesFormModel.publishList = publishList;
    }

    public String getFilesDetails() {
        if ((getEntity().getProfileImgPath() != null) && !getEntity().getProfileImgPath().isEmpty()) {
            final String FileNames = StringUtility.staticStringBeforeChar(File.separator, getEntity().getProfileImgPath());
            return FileNames;

        } else {
            return null;
        }
    }

    public String getFileName() {
        if ((getEntity().getProfileImgPath() != null) && !getEntity().getProfileImgPath().isEmpty()) {
            final String FileNames = StringUtility.staticStringAfterChar(File.separator, getEntity().getProfileImgPath());
            return FileNames;
        } else {
            return null;
        }
    }

    private final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
            UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
            MainetConstants.NUMBERS.ONE;
            //MainetConstants.HOME_IMAGES;

    public String getImageDetails() {
        if ((getEntity().getImagePath() != null) && !getEntity().getImagePath().isEmpty()) {
            final String FileNames = outputPath + MainetConstants.FILE_PATH_SEPARATOR + StringUtility.staticStringAfterChar(File.separator, getEntity().getImagePath());
            return FileNames;

        } else {
            return null;
        }
    }

    public String getImageName() {
        if ((getEntity().getImagePath() != null) && !getEntity().getImagePath().isEmpty()) {
            final String FileNames = StringUtility.staticStringAfterChar(File.separator, getEntity().getImagePath());
            return FileNames;
        } else {
            return null;
        }
    }

	public List<LookUp> getModules() {
        if (modules.size() == 0) {
            // Get all Sub links
        	  String flag = "Y";
            List<SubLinkMaster> linksMasters = iSectionService.findSublinks(flag);

            for (final ListIterator<SubLinkMaster> iterator = linksMasters.listIterator(); iterator.hasNext();) {
                final SubLinkMaster linksMaster = iterator.next();
                    final LookUp lookUp = new LookUp();
                    lookUp.setLookUpId(linksMaster.getRowId());
                    lookUp.setDescLangFirst(linksMaster.getSubLinkNameEn());
                    lookUp.setDescLangSecond(linksMaster.getSubLinkNameRg());

                modules.add(lookUp);
            }

        }

        return modules;
    }
    public long getModuleId() {
  		return moduleId;
  	}

  	public void setModuleId(long moduleId) {
  		this.moduleId = moduleId;
  	}
    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }

    public String getAttachName(String attachImageURL) {
    	String attachImageNameOnly=null;
        if (attachImageURL != null && attachImageURL != "") {
        	if (attachImageURL.contains("\\")) {
        		attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf("\\") + 1);
            if(attachImageURL.contains("/")) {
            	attachImageNameOnly=attachImageURL.substring(attachImageURL.lastIndexOf("/") + 1);
        	 } else {
        		 attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf("/") + 1);
        		 if(attachImageURL.contains("\\")) {
        			 attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf("\\") + 1);
        		 }
        	 }
        	}else {
        		
        		attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf(MainetConstants.operator.FORWARD_SLACE) + 1,attachImageURL.length());
        	}
        }
        return attachImageNameOnly;
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

	public boolean checkSequence(long sequenceNo) {
		// TODO Auto-generated method stub
		return iAdminPublicNoticesService.checkSequence(sequenceNo,UserSession.getCurrent().getOrganisation().getOrgid());
	}

	public String[] getImgList() {
		return imgList;
	}

	public void setImgList(String[] imgList) {
		this.imgList = imgList;
	}

	public List<String> getImgNameList() {
		return imgNameList;
	}

	public void setImgNameList(List<String> imgNameList) {
		this.imgNameList = imgNameList;
	}
	
	
}
