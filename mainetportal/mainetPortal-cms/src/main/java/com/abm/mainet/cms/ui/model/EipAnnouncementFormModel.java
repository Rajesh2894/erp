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

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IEipAnnouncementService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.ui.validator.EIPAnnouncementValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.DmsService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Component
@Scope("session")
public class EipAnnouncementFormModel extends AbstractEntryFormModel<EIPAnnouncement> {

    private static final long serialVersionUID = 7572501478320462620L;
    private static final Logger LOG = Logger.getLogger(EipAnnouncementFormModel.class);
    private String[] imgList;
    private String[] attachList;
    private List<String> imgNameList=new ArrayList<>(0);
    private String newProfilepath = MainetConstants.BLANK;
    private String newAttpath = MainetConstants.BLANK;
    public List<String> attachNameList=new ArrayList<>(0);
    private FileStorageCache fileStorageCache;
    //private String[] imgpath;
    final String physicalPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
    EIPAnnouncement entity =null;

    @Autowired
    private IEipAnnouncementService iEipAnnouncementService;
    

    @Autowired
    private DmsService dmsService;

    private EIPAnnouncement EIPAnnouncement;
    private String mode = null;

    @Autowired
    private IEntitlementService iEntitlementService;

    private String isChecker;
    
    private long moduleId;
    
    private final List<LookUp> modules = new ArrayList<>(0);
    
    @Autowired
    private ISectionService iSectionService;

    public EIPAnnouncement getEIPAnnouncement() {
        return EIPAnnouncement;
    }

    public void setEIPAnnouncement(final EIPAnnouncement eIPAnnouncement) {
        EIPAnnouncement = eIPAnnouncement;
    }

    @Override
    public void addForm() {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        setMode(MainetConstants.Transaction.Mode.ADD);
        setEntity(new EIPAnnouncement());
    }

    @Override
    public void editForm(final long rowId) {
    	FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        setMode(MainetConstants.Transaction.Mode.UPDATE);
        String imgName="";
        imgList=null;
        attachList=null;
        if(null!=imgNameList)
        imgNameList.clear();
        else
        	imgNameList=new ArrayList<>(0);
        if(null!=attachNameList)
        attachNameList.clear();
        else
        	attachNameList=new ArrayList<>(0);
        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }
        entity=iEipAnnouncementService.getEIPAnnouncement(rowId);
        if (entity.getAttachImage() != null) {
        	imgList = entity.getAttachImage().toString().split(MainetConstants.operator.COMA);

        	for(String img:imgList) {
        		imgName=getAttachImageName(img);
        		imgNameList.add(imgName);
        	}

        }
        if (entity.getAttach() != null && !entity.getAttach().equals(MainetConstants.BLANK)) {
            attachList = entity.getAttach().toString().split(MainetConstants.operator.COMA);
            
        	for(String fileName:attachList) {
        		attachNameList.add(getAttachName(fileName));
        	}
        }
        setEntity(entity);
    }

    @Override
    public void delete(final long rowId) {
        iEipAnnouncementService.delete(rowId);
    }

    @Override
    public boolean saveOrUpdateForm() {
    	logger.info("Inside saveOrUpadateForm of EIPAnnouncementFormModel");
        entity = getEntity();
        String profDirectoryTree = MainetConstants.BLANK;
        Map<String, String> fileMap = null;
        String fileAbsolutePath = null;
        Boolean isDMS = false;
      
        validateBean(entity, EIPAnnouncementValidator.class);
        if (hasValidationErrors()) {
        	if(entity.getAttach() == null || entity.getAttach().isEmpty()) {//on UI that div is not getting removed
        		this.attachNameList.clear();
        	}
        	
            return false;
        }
        String directoryTree = MainetConstants.BLANK;
        String iprofDirectoryTree = MainetConstants.BLANK;
        String idirectoryTree = MainetConstants.BLANK;
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date = formatter.format(new Date());
        
        if(entity.getIsDeleted()==null) {
        	entity.setIsDeleted("N");
        }
        
        boolean folderExist = false;
        if ((entity.getChekkerflag() != null) && entity.getChekkerflag().equals(MainetConstants.YES)) {
            if (MainetConstants.YES.equals(ApplicationSession.getInstance().getMessage(MainetConstants.DMS_CONFIGURE))) {
                isDMS = true;
            }
        }
        if (isDMS) {
            if (entity.getFolderPath() != null) {
                folderExist = true;
                idirectoryTree = directoryTree = entity.getFolderPath();
            }
        }

        idirectoryTree = directoryTree = getStorePath(MainetConstants.DirectoryTree.EIP);
            profDirectoryTree = directoryTree;
            iprofDirectoryTree = idirectoryTree;
            if (entity.getAttachImage() != null) {
                imgList = entity.getAttachImage().toString().split(MainetConstants.operator.COMA);
            }
            if (entity.getAttach() != null) {
                attachList = entity.getAttach().toString().split(MainetConstants.operator.COMA);
            }
         

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
                    setFilePaths(entity, "attach", directoryTree);

                }
                if (!path.equals(MainetConstants.BLANK)) {
                    directoryTree = path;
                }

            }
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
                    setFilePaths(entity, "attachImage", idirectoryTree);

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
                	LOG.info("Uploading  file at cache in EipAnnouncementFormModel");	
                	if(docFiles.size() > 0) {
                		getFileNetClient().uploadFileList(docFiles, Filepaths.getfilepath()+MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER+MainetConstants.operator.FORWARD_SLACE+directoryTree);
                	}
                	if(imageFiles.size() > 0) {
                		getFileNetClient().uploadFileList(imageFiles, Filepaths.getfilepath()+MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER+MainetConstants.operator.FORWARD_SLACE+idirectoryTree);
                	}
                    
                }
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                	LOG.info("Uploading  file at filenet in EipAnnouncementFormModel");
                	if(docFiles.size() > 0) {
                		getFileNetClient().uploadFileList(docFiles, directoryTree);
                	}
                	if(imageFiles.size() > 0) {
                		getFileNetClient().uploadFileList(imageFiles, idirectoryTree);
                	}
                    
                }
                if (cache.getProfileImage() != null) {
                	LOG.info("Uploading  profileImage at filenet in EipAnnouncementFormModel");
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
        
        
//DMS Code        
        
        if (isDMS) {
            
            if (fileAbsolutePath == null) {
                fileAbsolutePath = physicalPath + File.separator + entity.getAttach();
            }

            fileMap = dmsService.createDocument(directoryTree, fileAbsolutePath, folderExist);
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
        long id=entity.getAnnounceId();
        
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
        
        boolean news=  iEipAnnouncementService.saveOrUpdate(entity);
	    Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.news")+" "+(UserSession.getCurrent().getLanguageId()==1?entity.getAnnounceDescEng():entity.getAnnounceDescReg()),entity.getChekkerflag(),id,entity.getUpdatedBy());
	    return news;

    }

    private String getStorePath(final String pathRoot) {
        String fileNetPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
    	SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date = formatter.format(new Date());
        String path = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date + File.separator + pathRoot;
        path += (File.separator);
        path += ("EIP_ANNOUNCEMENT" + File.separator + "HOME_PAGE");
        path += (File.separator);
        path += (UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp());
        logger.info("filenet path: "+path);
        return path;
    }

    @Override
    public void cleareUploadFile() {
        getEntity().setAttach(MainetConstants.BLANK);
    }

    public String getFilesDetails() {
        if ((getEntity().getAttach() != null) && !getEntity().getAttach().isEmpty()) {
            return StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
                    getEntity().getAttach());
        } else {
            return null;
        }
    }

    public String getImageDetails() {

        if ((getEntity().getAttachImage() != null) && !getEntity().getAttachImage().isEmpty()) {
            return StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
                    getEntity().getAttachImage());
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
    
    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public String getFileName() {
        if ((getEntity().getAttach() != null) && !getEntity().getAttach().isEmpty()) {
            return StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
                    getEntity().getAttach());
        } else {
            return null;
        }
    }

    public String getImageFileName() {
        if ((getEntity().getAttach() != null) && !getEntity().getAttach().isEmpty()) {
            return StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
                    getEntity().getAttachImage());
        } else {
            return null;
        }
    }

    public void setFileName(final String fileName) {
    }

    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }
    
    

    public String[] getImgList() {
		return imgList;
	}

	public void setImgList(String[] imgList) {
		this.imgList = imgList;
	}

	public String[] getAttachList() {
		return attachList;
}

	public void setAttachList(String[] attachList) {
		this.attachList = attachList;
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

    
/*    public String getUploadPath(String filePath) {
        StringBuilder realpath = new StringBuilder(MainetConstants.WINDOWS_SLASH);

        realpath.append(MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER)
                .append(MainetConstants.WINDOWS_SLASH)
                .append(filePath);
        Utility.createDirectory(realpath.toString());
        return realpath.toString();
    }
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
        String date=new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY).format(new Date());
        realpath.append(MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER)
                .append(MainetConstants.WINDOWS_SLASH)
                .append(UserSession.getCurrent().getOrganisation().getOrgid()).append(MainetConstants.WINDOWS_SLASH)
                .append(entity.getAnnounceDescEng().trim()).append(MainetConstants.WINDOWS_SLASH);
        		Utility.createDirectory(realpath.toString());
                logger.info("getUploadPath: "+realpath.toString());
        return realpath.toString();

    }

    public String getAttachImageName(String attachImageURL) {
    	String attachImageNameOnly=null;
        if (attachImageURL != null && attachImageURL != "") {
        	if (attachImageURL.contains("\\")) {
        		attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf("\\") + 1);
            if(attachImageURL.contains("/")) {
            	attachImageNameOnly=attachImageURL.substring(attachImageURL.lastIndexOf("/") + 1);
            }
        	 } else {
        		 attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf("/") + 1);
        		 if(attachImageURL.contains("\\")) {
        			 attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf("\\") + 1);
                 }
        	 }
        	
        }else {
    		
    		attachImageNameOnly = attachImageURL.substring(attachImageURL.lastIndexOf(MainetConstants.operator.FORWARD_SLACE) + 1,attachImageURL.length());
    	}
        return attachImageNameOnly;
    }
    
    public String getAttachName(String attachURL) {
    	String attachNameOnly=null;
        if (attachURL != null && attachURL != "") {
        	if (attachURL.contains("\\")) {
        		attachNameOnly = attachURL.substring(attachURL.lastIndexOf("\\") + 1);
            if(attachURL.contains("/")) {
            	attachNameOnly=attachURL.substring(attachURL.lastIndexOf("/") + 1);
            }
        	 } else {
        		 attachNameOnly = attachURL.substring(attachURL.lastIndexOf("/") + 1);
        		 if(attachURL.contains("\\")) {
        			 attachNameOnly = attachURL.substring(attachURL.lastIndexOf("\\") + 1);
                 }
        	 }
        	
        }
        return attachNameOnly;
    }

	public List<String> getImgNameList() {
		return imgNameList;
	}

	public void setImgNameList(List<String> imgNameList) {
		this.imgNameList = imgNameList;
	}

	public List<String> getAttachNameList() {
		return attachNameList;
	}

	public void setAttachNameList(List<String> attachNameList) {
		this.attachNameList = attachNameList;
	}
    
	public static String toCommaSeparatedString(String[] array) {
		String result = "";
		if (array.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : array) {
				sb.append(s).append(",");
			}
			result = sb.deleteCharAt(sb.length() - 1).toString();
		}
		return result;
	}

	
	public void deleteFiles(final String fileName, final String del) {
		entity = getEntity();
        if (del.equals("image")) {
        	String images = entity.getAttachImage();
        	imgList = entity.getAttachImage().toString().split(MainetConstants.operator.COMA);
            newProfilepath = entity.getAttachImage();
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
                        	if(entity.getAttachImage().contains(path+",")) {
                        		images = images.replace(path+",", MainetConstants.BLANK);
                    		}else if(entity.getAttachImage().contains(","+path)){
                    			images = images.replace(","+path, MainetConstants.BLANK);
                    		}else {
                    			images = images.replace(path, MainetConstants.BLANK);
                    		}
                        }
                    }
                }
               // String imageUrls=toCommaSeparatedString(imgList);
                entity.setAttachImage(images);
                iEipAnnouncementService.saveOrUpdate(entity);
            }
        } else if (del.equals("file")) {
        	attachList = entity.getAttach().toString().split(MainetConstants.operator.COMA);
            newAttpath = entity.getAttach();
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
                        		if(entity.getAttach().contains(path+",")) {
                        			newAttpath = newAttpath.replace(path+",", MainetConstants.BLANK);
                        		}else if(entity.getAttach().contains(","+path)){
                        			newAttpath = newAttpath.replace(","+path, MainetConstants.BLANK);
                        		}else {
                        			newAttpath = newAttpath.replace(path, MainetConstants.BLANK);
                        		}
                        	}
                            
                            entity.setAttach(newAttpath);
                            iEipAnnouncementService.saveOrUpdate(entity);
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

	public long getModuleId() {
  		return moduleId;
  	}

  	public void setModuleId(long moduleId) {
  		this.moduleId = moduleId;
  	}
}
