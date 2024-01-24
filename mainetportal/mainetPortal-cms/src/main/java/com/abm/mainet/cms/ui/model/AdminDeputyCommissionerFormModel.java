package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.Serializable;
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

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.service.IProfileMasterService;
import com.abm.mainet.cms.ui.validator.AdminProfileMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.service.DmsService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Component
@Scope("session")
public class AdminDeputyCommissionerFormModel extends AbstractEntryFormModel<ProfileMaster> implements Serializable {

    private static final long serialVersionUID = -3772636431764660190L;
    private static final Logger LOG = Logger.getLogger(AdminDeputyCommissionerFormModel.class);
    @Autowired
    private IProfileMasterService iProfileMasterService;

    @Autowired
    private DmsService dmsService;

    @Autowired
    private IEntitlementService iEntitlementService;

    private String isChecker;

    private ProfileMaster profileMaster;

    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    private String mode;

    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
    String date = formatter.format(new Date());

    private String eipHomeImagesPath = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
            + File.separator
            + MainetConstants.DirectoryTree.EIP + File.separator + "HOME_IMAGES" + File.separator + "DEPUTY_COMMISSIONER"
            + File.separator
            + UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp();

    private String uploadedFile = MainetConstants.BLANK;

    private final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
            UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
            MainetConstants.HOME_IMAGES;

    @Override
    public void addForm() {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        final LookUp lookUpObj = getProfileMasterSection(PrefixConstants.Prefix.PMS);
        final ProfileMaster profileMaster = new ProfileMaster();
        profileMaster.setCpdSection(lookUpObj.getLookUpId());
        setMode(MainetConstants.Transaction.Mode.ADD);
        setProfileMaster(profileMaster);
        setEntity(profileMaster);
    }

    @Override
    public void editForm(final long rowId) {
        setMode(MainetConstants.Transaction.Mode.UPDATE);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }

        final ProfileMaster profileMaster = iProfileMasterService.getProfileMasterById(rowId);
        setProfileMaster(profileMaster);
        setEntity(profileMaster);
    }

    @Override
    public boolean saveForm() throws FrameworkException {

        final ProfileMaster entity = getProfileMaster();
        entity.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);

        try {
            uploadedFile = uploadDocAndVerifyDoc(getFileNetClient(), eipHomeImagesPath);

        } catch (final Exception e) {
            LOG.error("Error occured during saveForm ", e);
        }

        if ((uploadedFile != null) && !uploadedFile.equals(MainetConstants.BLANK)) {
            entity.setImageName(uploadedFile);
            uploadedFile = eipHomeImagesPath + File.separator + uploadedFile;
            entity.setImagePath(uploadedFile);
        }

        iProfileMasterService.saveOrUpdate(entity, entity.getChekkerflag1());
        return true;

    }

    @Override
    public void deleteFile(final String filename) {

        getEntity().setImageName(MainetConstants.BLANK);

    }

    private String uploadDocAndVerifyDoc(final FileNetApplicationClient fileNetApplicationClient, final String directoryTree)
            throws Exception {

        String uploadFileName = StringUtils.EMPTY;

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            final Iterator<File> setFilesItr = entry.getValue().iterator();

            while (setFilesItr.hasNext()) {

                final File file = setFilesItr.next();

                uploadFileName = uploadFileName + file.getName();
                final List<File> fileList = new ArrayList<>();

                fileList.add(file);

                fileNetApplicationClient.uploadFileList(fileList, directoryTree);

            }
        }

        return uploadFileName;
    }

    @Override
    public boolean saveOrUpdateForm() {
        validateBean(getEntity(), AdminProfileMasterValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        Boolean isDMS = false;
        final ProfileMaster entity = getProfileMaster();
        entity.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        Map<String, String> fileMap = null;

        try {
            uploadedFile = uploadDocAndVerifyDoc(getFileNetClient(), eipHomeImagesPath);

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        if ((uploadedFile != null) && !uploadedFile.equals(MainetConstants.BLANK)) {
            entity.setImageName(uploadedFile);
            uploadedFile = eipHomeImagesPath + File.separator + uploadedFile;
            entity.setImagePath(uploadedFile);
        }

        String filepath = null;
        if ((entity.getChekkerflag() != null) && entity.getChekkerflag().equals(MainetConstants.YES)) {
            if (MainetConstants.YES.equals(ApplicationSession.getInstance().getMessage(MainetConstants.DMS_CONFIGURE))) {
                isDMS = true;
            }
        }

        if (isDMS) {
            if (uploadedFile.equals(MainetConstants.BLANK)) {
                if ((entity.getImageName() != null) && !entity.getImageName().equals(MainetConstants.BLANK)) {
                    filepath = entity.getImageName();
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
                    eipHomeImagesPath = entity.getFolderPath();
                }
                fileMap = dmsService.createDocument(eipHomeImagesPath, filepath, folderExist);

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

        iProfileMasterService.saveOrUpdate(entity, entity.getChekkerflag1());

        return true;

    }

    @Override
    public void delete(final long rowId) {
        iProfileMasterService.delete(rowId);

    }

    private LookUp getProfileMasterSection(final String prefix) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals(MainetConstants.DEPT_SHORT_NAME.DEATH_CERTIFICATE)) {
                quickLink = lookUp;
                break;
            }

        }
        return quickLink;

    }

    /**
     * @return the profileMaster
     */
    public ProfileMaster getProfileMaster() {
        return profileMaster;
    }

    /**
     * @param profileMaster the profileMaster to set
     */
    public void setProfileMaster(final ProfileMaster profileMaster) {
        this.profileMaster = profileMaster;
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

    public String getImagesDetail() {
        if ((getEntity().getImagePath() != null) && !getEntity().getImagePath().isEmpty()) {
        	final String toCachePath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                    UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR + "EIP_HOME";
            Utility.downloadedFileUrl(getEntity().getImagePath(), toCachePath, getFileNetClient());
            final String FileNames = toCachePath + MainetConstants.FILE_PATH_SEPARATOR + StringUtility.staticStringAfterChar(File.separator, getEntity().getImagePath());
            return FileNames;
        } else {
            return null;
        }
    }

    public void setImage(final String image) {
    }

    public String getImage() {
        return getEntity().getImageName();
    }

    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }

}
