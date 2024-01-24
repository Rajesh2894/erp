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

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollOption;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IAdminOpinionPollOptionService;
import com.abm.mainet.cms.service.IAdminOpinionPollService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.ui.validator.AdminOpinioMasterValidator;
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
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;


/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminOpinionPollFormModel extends AbstractEntryFormModel<OpinionPoll> {
    private static final long serialVersionUID = 7405567806236977020L;
    private long moduleId;

    private static final Logger LOG = Logger.getLogger(AdminOpinionPollFormModel.class);
    @Autowired
    private IAdminOpinionPollService iAdminOpinionPollService;

    @Autowired
    private IAdminOpinionPollOptionService iAdminOpinionPollOptionService;

    @Autowired
    private IEntitlementService iEntitlementService;
    

    private String isChecker;

    
    private String[] attachList;
    private List<String> attachNameList=new ArrayList<>(0);
    private String[] imgList;
    
    private List<String> imgNameList=new ArrayList<>(0);
    
    private String newAttpath = MainetConstants.BLANK;
    

    private String mode = null;
    String filecount = "";
   

    private OpinionPoll opinionPoll;

    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
    String date = formatter.format(new Date());
    private String publicNoticesPath = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
            + File.separator
            + MainetConstants.DirectoryTree.EIP + File.separator + "PUBLIC NOTICES" + File.separator + Utility.getTimestamp();

    OpinionPoll entity = null;
    
    private List<Option> optionList = new ArrayList<Option>();

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
	  
	  private int optionSize = 2;
	  private int optionMinSize = 2;
	  
	  private String activeOpinionPollList;
    
    @Override
    public void addForm() {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        OpinionPoll opinionPoll = new OpinionPoll();
        
        this.optionList = addDefaultOptions();
        
        setEntity(opinionPoll);
        setMode(MainetConstants.Transaction.Mode.ADD);
        filecount = "Y";
    }

    @Override
    public void editForm(final long rowId) {
    	attachList=null;
    	int cnt = 0;
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
        setEntity(iAdminOpinionPollService.getOpinionPoll(rowId));
        List<Option> options = new ArrayList<Option>();
        List<OpinionPollOption> opinionPollOptionList = iAdminOpinionPollOptionService.getOpinionPollOptionsByOpinionPolltId(rowId);
        if(opinionPollOptionList != null && opinionPollOptionList.size() > 0) {
	        for (OpinionPollOption opinionPollOption : opinionPollOptionList) {
	        	Option  option = new Option();
	            option.setOptionEn(opinionPollOption.getOptionEn());
	            option.setOptionReg(opinionPollOption.getOptionReg());
	            option.setId(opinionPollOption.getpOptionId());
	            options.add(option);
	            cnt ++;
			}
	        String size = ApplicationSession.getInstance().getMessage("option.size");
			try {
				if(size != null && !(size.equalsIgnoreCase("option.size"))) {
					optionSize = Integer.parseInt(size);
				}
			} catch (Exception e) {
				// TODO: handle exception
				optionSize = 2;
			}
			int leftSize= optionSize - cnt;
			if(leftSize>0) {
				for (int i = 0; i < leftSize; i++) {
					Option  option = new Option();
					options.add(option);
				}
			}
	        this.optionList = options;
        }else {
        	this.optionList = addDefaultOptions();
        }
        if (getEntity().getDocPath() != null) {
            filecount = "N";
        } else {

            filecount = "";
        }

        if(null!=attachNameList)
            attachNameList.clear();
            else
            	attachNameList=new ArrayList<>(0);
        
        if (getEntity().getDocPath() != null) {
            attachList = getEntity().getDocPath().toString().split(MainetConstants.operator.COMA);
            
        	for(String fileName:attachList) {
        		attachNameList.add(getAttachName(fileName));
        	}
        }
        
        if(null!=imgNameList)
        	imgNameList.clear();
            else
            	imgNameList=new ArrayList<>(0);
        
        
        if (getEntity().getImgPath() != null) {
        	imgList = getEntity().getImgPath().toString().split(MainetConstants.operator.COMA);
            
        	for(String fileName:imgList) {
        		imgNameList.add(getAttachName(fileName));
        	}
        }
 
    }

    @Override
    public void delete(final long rowId) {
    	iAdminOpinionPollService.delete(rowId);
    }

    @Override
    public boolean saveOrUpdateForm() throws FrameworkException{
        String uploadedFile = MainetConstants.BLANK;
        final OpinionPoll entity = getEntity();
        String profDirectoryTree = MainetConstants.BLANK;
        String directoryTree = MainetConstants.BLANK;
        String iprofDirectoryTree = MainetConstants.BLANK;
        String idirectoryTree = MainetConstants.BLANK;
        entity.setOptionSize(optionSizeValidation());
        entity.setCheckOpinionValidation(false);
        if(entity.getChekkerflag() != null && entity.getChekkerflag().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
	        entity.setCheckOpinionValidation(checkOpinionPollExistOrNot(entity));
	        if(entity.isCheckOpinionValidation()) {
	        	entity.setActivePolls(this.activeOpinionPollList);
	        }
        }
        validateBean(entity, AdminOpinioMasterValidator.class);
        Map<String, String> fileMap = null;

        if (hasValidationErrors()) {

            return false;
        }
        
		/*
		 * if(!optionSizeValidation()) { EntityValidationContext<OpinionPoll>
		 * validationContext = new EntityValidationContext<OpinionPoll>(entity);
		 * //getApplicationSession().getMessage("PublicNotices.date.validate")
		 * validationContext.addOptionConstraint("Please Add Minimum 2 Option"); return
		 * false; }
		 */
        
        try {
            uploadDocAndVerifyDoc(publicNoticesPath);
            
            //iprofDirectoryTree = profDirectoryTree = getStorePath(MainetConstants.DirectoryTree.EIP);
            
            iprofDirectoryTree = idirectoryTree = getStorePathImage(MainetConstants.DirectoryTree.EIP);
            profDirectoryTree = directoryTree = getStorePathPdf(MainetConstants.DirectoryTree.EIP);
            
            if (entity.getDocPath() != null) {
                attachList = entity.getDocPath().toString().split(MainetConstants.operator.COMA);
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
                	
                    setFilePaths(entity, "docPath", directoryTree);

                }
                if (!path.equals(MainetConstants.BLANK)) {
                    directoryTree = path;
                }

            }
        }
        
        if (entity.getImgPath() != null) {
        	imgList = entity.getImgPath().toString().split(MainetConstants.operator.COMA);
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
                    setFilePaths(entity, "imgPath", idirectoryTree);
                }
                if (!path.equals(MainetConstants.BLANK)) {
                    idirectoryTree = path;
                }

            }
        }
        
        
        
        directoryTree = directoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        directoryTree = directoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        directoryTree = directoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        LOG.info("directoryTree :"+directoryTree);
        
        profDirectoryTree = profDirectoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        profDirectoryTree = profDirectoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        profDirectoryTree = profDirectoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        LOG.info("profDirectoryTree :"+profDirectoryTree);
        
        idirectoryTree = idirectoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        idirectoryTree = idirectoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        idirectoryTree = idirectoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        LOG.info("idirectoryTree :"+idirectoryTree);
        
        iprofDirectoryTree = iprofDirectoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
        iprofDirectoryTree = iprofDirectoryTree.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.COMA);
        iprofDirectoryTree = iprofDirectoryTree.replace(MainetConstants.operator.QUAD_BACKWARD_SLACE, MainetConstants.operator.COMA);
        LOG.info("iprofDirectoryTree :"+iprofDirectoryTree);
        
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
        
        iAdminOpinionPollService.saveOrUpdate(entity);
        saveOpinionPollOptions(entity,optionList);
       Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.notice")+" "+(UserSession.getCurrent().getLanguageId()==1?entity.getPollSubEn():entity.getPollSubReg()),entity.getChekkerflag(),id,entity.getUpdatedBy());
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
    
    private String getStorePathPdf(final String pathRoot) {
        String fileNetPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
    	SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date = formatter.format(new Date());
        String path = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date + File.separator + pathRoot;
        path += (File.separator);
        path += ("EIP_OPINION" + File.separator + "HOME_PDF");
        path += (File.separator);
        path += (UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp());
        logger.info("filenet path: "+path);
        return path;
    }
    private String getStorePathImage(final String pathRoot) {
        String fileNetPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
    	SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date = formatter.format(new Date());
        String path = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date + File.separator + pathRoot;
        path += (File.separator);
        path += ("EIP_OPINION" + File.separator + "HOME_IMAGE");
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
                    this.getEntity().setDocPath(directoryTree + File.separator + file.getName());
                }
                if (key == 1L) {
                    this.getEntity().setImgPath(directoryTree + File.separator + file.getName());
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
        	String images = entity.getImgPath();
        	imgList = entity.getImgPath().toString().split(MainetConstants.operator.COMA);
            
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
                        	if(entity.getImgPath().contains(path+",")) {
                        		images = images.replace(path+",", MainetConstants.BLANK);
                    		}else if(entity.getImgPath().contains(","+path)){
                    			images = images.replace(","+path, MainetConstants.BLANK);
                    		}else {
                    			images = images.replace(path, MainetConstants.BLANK);
                    		}
                        }
                    }
                }
                entity.setImgPath(images);
                iAdminOpinionPollService.saveOrUpdate(entity);
            }
        } else if (del.equals("file")) {
        	attachList = entity.getDocPath().toString().split(MainetConstants.operator.COMA);
            newAttpath = entity.getDocPath();
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
                        		if(entity.getDocPath().contains(path+","))
                        		{
                        			newAttpath = newAttpath.replace(path+",", MainetConstants.BLANK);
                        		}else if(entity.getDocPath().contains(","+path)){
                        			newAttpath = newAttpath.replace(","+path, MainetConstants.BLANK);
                        		}else {
                        			newAttpath = newAttpath.replace(path, MainetConstants.BLANK);
                        		}
                        	}
                            entity.setDocPath(newAttpath);
                            iAdminOpinionPollService.saveOrUpdate(entity);
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
    public OpinionPoll getOpinionPoll() {
        return opinionPoll;
    }

    /**
     * @param publicNotices the publicNotices to set
     */
    public void setOpinionPoll(final OpinionPoll opinionPoll) {
        this.opinionPoll = opinionPoll;
    }

	

    

    public String getFilecount() {
        return filecount;
    }

    public void setFilecount(String filecount) {
        this.filecount = filecount;
    }

    

    public String getFilesDetails() {
        if ((getEntity().getDocPath() != null) && !getEntity().getDocPath().isEmpty()) {
            final String FileNames = StringUtility.staticStringBeforeChar(File.separator, getEntity().getDocPath());
            return FileNames;

        } else {
            return null;
        }
    }

    public String getFileName() {
        if ((getEntity().getDocPath() != null) && !getEntity().getDocPath().isEmpty()) {
            final String FileNames = StringUtility.staticStringAfterChar(File.separator, getEntity().getDocPath());
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
        if ((getEntity().getImgPath() != null) && !getEntity().getImgPath().isEmpty()) {
            final String FileNames = outputPath + MainetConstants.FILE_PATH_SEPARATOR + StringUtility.staticStringAfterChar(File.separator, getEntity().getImgPath());
            return FileNames;

        } else {
            return null;
        }
    }

    public String getImageName() {
        if ((getEntity().getImgPath() != null) && !getEntity().getImgPath().isEmpty()) {
            final String FileNames = StringUtility.staticStringAfterChar(File.separator, getEntity().getImgPath());
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
        return this.isChecker;
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

	public List<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}
	
	private List<Option> addDefaultOptions() {
		String size = ApplicationSession.getInstance().getMessage("option.size");
		try {
			if(size != null && !(size.equalsIgnoreCase("option.size"))) {
				optionSize = Integer.parseInt(size);
			}
		} catch (Exception e) {
			// TODO: handle exception
			optionSize = 2;
		}
		
		List<Option> optList = new ArrayList<Option>();
		for (int i = 0; i < optionSize; i++) {
				Option  option = new Option();
				optList.add(option);
		}
		
        return optList;
	}
	private void saveOpinionPollOptions(OpinionPoll opinionPoll,List<Option> optionList) {
		try {
			for (Option option : optionList) {
					OpinionPollOption opinionPollOption = iAdminOpinionPollOptionService.getOpinionPollOption(option.getId());
					if(option.getId() > 0 && !(opinionPollOption !=null && opinionPollOption.getOptionEn().equalsIgnoreCase(option.getOptionEn()) && opinionPollOption.getOptionReg().equalsIgnoreCase(option.getOptionReg()))) {
						opinionPollOption.setOptionEn(option.getOptionEn());
						opinionPollOption.setOptionReg(option.getOptionReg());
						boolean flag = iAdminOpinionPollOptionService.saveOrUpdate(opinionPollOption);
						if(flag) {
							logger.error("updated or renamed  "+option.getId());
						}else {
							logger.error("NOT Updated "+option.getId());
						}
					}
					
					
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("DELETE FAILED ");
		}
		
		
		for (Option option : optionList) {
			OpinionPollOption opinionPollOptionDb = iAdminOpinionPollOptionService.getOpinionPollOption(option.getId());
			if(!(opinionPollOptionDb !=null && opinionPollOptionDb.getOptionEn().equalsIgnoreCase(option.getOptionEn()) && opinionPollOptionDb.getOptionReg().equalsIgnoreCase(option.getOptionReg()))) {
				OpinionPollOption opinionPollOption = null;
				opinionPollOption = new OpinionPollOption();
				opinionPollOption.setOptionEn(option.getOptionEn());
				opinionPollOption.setOptionReg(option.getOptionReg());
				opinionPollOption.setPnId(opinionPoll.getPnId());
				opinionPollOption.setUpdatedBy(opinionPoll.getUpdatedBy());
				opinionPollOption.setUpdatedDate(opinionPoll.getUpdatedDate());
				opinionPollOption.setUserId(opinionPoll.getUserId());
				opinionPollOption.setLgIpMac(opinionPoll.getLgIpMac());
				opinionPollOption.setLgIpMacUpd(opinionPoll.getLgIpMacUpd());
				opinionPollOption.setLmodDate(opinionPoll.getLmodDate());
				if(option.getOptionEn() !=null && !option.getOptionEn().trim().equalsIgnoreCase("")) {
					opinionPollOption.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
					iAdminOpinionPollOptionService.saveOrUpdate(opinionPollOption);
				}
			}
			
		}
		
	}
	
	public boolean checkOpinionPollExistOrNot(final OpinionPoll opinionPoll) {
		
		List<OpinionPoll> listOpinion = iAdminOpinionPollService.getAdminOpinionPollsByValidityAndIssueDate(UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.NOT_DELETE,opinionPoll.getValidityDate(),opinionPoll.getIssueDate());
		if(listOpinion !=null && listOpinion.size() > 0) {
			if(listOpinion.size() == 1 && listOpinion.get(0).getPnId() == opinionPoll.getPnId()) {
				return false;
			}
			for (int i = 0; i < listOpinion.size(); i++) {
				if(listOpinion.get(i).getId() != opinionPoll.getPnId()) {
					this.activeOpinionPollList=listOpinion.get(i).getPollSubEn();
					this.setActiveOpinionPollList(this.activeOpinionPollList);
				}
			}
			
			return true;
		}
		return false;
	}
	
	public int optionSizeValidation() {
		int cnt=0;
		for (Option option : this.optionList) {
			if(option.getOptionEn() != null && !option.getOptionEn().trim().equalsIgnoreCase("")) {
				cnt ++;
			}
		}
		
		return cnt;
	}

	public String getActiveOpinionPollList() {
		return activeOpinionPollList;
	}

	public void setActiveOpinionPollList(String activeOpinionPollList) {
		this.activeOpinionPollList = activeOpinionPollList;
	}
	
	
}

class Option{
	long id;
	String optionEn;
	String optionReg;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOptionEn() {
		return optionEn;
	}
	public void setOptionEn(String optionEn) {
		this.optionEn = optionEn;
	}
	public String getOptionReg() {
		return optionReg;
	}
	public void setOptionReg(String optionReg) {
		this.optionReg = optionReg;
	}
	
	
}
