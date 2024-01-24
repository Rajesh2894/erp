package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.cms.service.IEIPHomePageImageService;
import com.abm.mainet.cms.ui.validator.EIPHomeImagesValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Component
@Scope("session")
public class EIPLogoImagesModel extends AbstractEntryFormModel<EIPHomeImages> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(EIPLogoImagesModel.class);

    @Autowired
    private IEIPHomePageImageService iEIPHomePageImageService;

    private String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
            UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
            MainetConstants.DirectoryTree.SLIDER_IMAGE;
            
    private FileNetApplicationClient filenet = getFileNetClient();

    private EIPHomeImages homeImages;
    private String mode;

    private long sequenceNo;

    @Autowired
    private IEntitlementService iEntitlementService;

    private String isChecker;
    
    private String makkerchekkerflag;

    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
    String date = formatter.format(new Date());

    private final String eipHomeImagesPath = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
            + File.separator
            + MainetConstants.DirectoryTree.EIP + File.separator + "HOME_IMAGES" + File.separator + "LOGO" + File.separator
            + UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp();

    private String uploadedFile = MainetConstants.BLANK;

    private String flag = MainetConstants.FlagL;

    @Override
    public void addForm() {
        setMode("add");
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        setEntity(new EIPHomeImages());

    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    @Override
    public void editForm(final long rowId) {
        setMode("edit");
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }
        setEntity(iEIPHomePageImageService.getImagesDetails(rowId));
        setSequenceNo(getEntity().getHmImgOrder());
    }

    @Override
    public void viewForm(final long rowId) {
        setMode("view");
        setEntity(iEIPHomePageImageService.getImagesDetails(rowId));
    }

    @Override
    public boolean saveOrUpdateForm() {
        homeImages = getEntity();
        if (homeImages.getImagePath() != null && homeImages.getImagePath().equals("")) {
        	homeImages.setImagePath(null);
        	homeImages.setImageName(null);
		}
        validateBean(getEntity(), EIPHomeImagesValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        if (getSequenceNo() == 0l || getSequenceNo() != getEntity().getHmImgOrder()) {
            /*
             * if ((homeImages.getMakkerchekerflage() != null) &&
             * !homeImages.getMakkerchekerflage().equalsIgnoreCase(MainetConstants.BLANK)) { final boolean result =
             * iEIPHomePageImageService.checkAuthorisedkSequence(getEntity().getHmImgOrder(), getEntity().getModuleType(),
             * UserSession.getCurrent().getOrganisation()); if (!result) { addValidationError(getFieldLabel("checkExistsImage"));
             * return false; } } else {
             */
            // Check For Macker
            final boolean result = iEIPHomePageImageService.checkExistingSequence(getEntity().getHmImgOrder(),
                    getEntity().getModuleType(), UserSession.getCurrent().getOrganisation());

            if (!result) {

                addValidationError(getFieldLabel("checkExistsImage"));

                return false;
            }
        }

        try {
            uploadedFile = uploadDocAndVerifyDoc(getFileNetClient(), eipHomeImagesPath);

        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        if (!uploadedFile.isEmpty() && !uploadedFile.equalsIgnoreCase("")) {
            homeImages.setImageName(uploadedFile);
            uploadedFile = eipHomeImagesPath + File.separator + uploadedFile;
            homeImages.setImagePath(uploadedFile);
        }

        homeImages.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        homeImages.updateAuditFields();
        homeImages.setModuleType(MainetConstants.FlagL);
        
        long id=homeImages.getHmImgId();
        if(MainetConstants.FLAGY.equalsIgnoreCase(homeImages.getMakkerchekerflage())){
    		final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
	    	if(!(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH))) {
             	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                         UserSession.getCurrent().getOrganisation().getOrgid());
                 if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
                 	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
                 	homeImages.setMakkerchekerflage(MainetConstants.FLAGN);
                 }
	    	}
         }
        iEIPHomePageImageService.saveEIPHomeImages(homeImages);
       Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.logo")+" "+homeImages.getHmImgOrder(),homeImages.getMakkerchekerflage(),id,homeImages.getUpdatedBy());

        return true;
    }

    private String uploadDocAndVerifyDoc(final FileNetApplicationClient fileNetApplicationClient, final String directoryTree)
            throws Exception {

        String uploadFileName = StringUtils.EMPTY;

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            final Iterator<File> setFilesItr = entry.getValue().iterator();

            while (setFilesItr.hasNext()) {

                final File file = setFilesItr.next();                

                uploadFileName = uploadFileName + file.getName();
                getEntity().setImageName(uploadFileName);
                final List<File> fileList = new ArrayList<>();

                fileList.add(file);

                fileNetApplicationClient.uploadFileList(fileList, directoryTree);

            }
        }

        return uploadFileName;
    }

    public String getImagesDetail() {    	
        if ((getEntity().getImagePath() != null) && !getEntity().getImagePath().isEmpty()) {
        	final String toCachePath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                    UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR + "LOGO";
            Utility.downloadedFileUrl(getEntity().getImagePath(), toCachePath, getFileNetClient());
            final String FileNames = toCachePath + MainetConstants.FILE_PATH_SEPARATOR + StringUtility.staticStringAfterChar(File.separator, getEntity().getImagePath());
            return FileNames;

        } else {
            return null;
        }
    }

    public String getImage() {
        return getEntity().getImageName();
    }

    public boolean checkMaxRows(final String moduleType) {
        return iEIPHomePageImageService.isAccessToAdd(moduleType, UserSession.getCurrent().getOrganisation());

    }

    private String getFieldLabel(final String field) {
        String value;
        if (getEntity().getHmImgOrder() == 1) {
            value = MainetConstants.LEFT;
        } else {
            value = MainetConstants.RIGHT;
        }
        return ApplicationSession.getInstance().getMessage("EIPHomeImages" + MainetConstants.operator.DOT + field,
                new Object[] { value });
    }

    @Override
    public void delete(final long rowId) {
        final EIPHomeImages eipHomeImages = iEIPHomePageImageService.getEIPHomeImagesDetails(rowId,
                UserSession.getCurrent().getOrganisation());

        if (eipHomeImages != null) {
            iEIPHomePageImageService.deleteHomeImages(eipHomeImages);
        }
    }

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @param outputPath the outputPath to set
     */
    public void setOutputPath(final String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return the filenet
     */
    public FileNetApplicationClient getFilenet() {
        return filenet;
    }

    /**
     * @param filenet the filenet to set
     */
    public void setFilenet(final FileNetApplicationClient filenet) {
        this.filenet = filenet;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }

	public String getMakkerchekkerflag() {
		return makkerchekkerflag;
	}

	public void setMakkerchekkerflag(String makkerchekkerflag) {
		this.makkerchekkerflag = makkerchekkerflag;
	}

}
