package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAnnouncementLanding;
import com.abm.mainet.cms.service.IEipAnnouncementLandingService;
import com.abm.mainet.cms.ui.validator.EipAnnouncementLandingvalidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * /*@author rajdeep.sinha
 */

@Component
@Scope("session")
public class EipAnnouncementLandingFormModel extends AbstractEntryFormModel<EIPAnnouncementLanding> {

    @Autowired
    private IEipAnnouncementLandingService iEipAnnouncementLandingService;

    private static final long serialVersionUID = -8609225637512888607L;
    private static final Logger LOG = Logger.getLogger(EipAnnouncementLandingFormModel.class);

    private String mode = null;

    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {

        return makkerchekkerflag;

    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;

    }

    @Override
    public void addForm() {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        setEntity(new EIPAnnouncementLanding());
    }

    @Override
    public void editForm(final long rowId) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        setEntity(iEipAnnouncementLandingService.getEipAnnouncementLanding(rowId));
        setMode(MainetConstants.Transaction.Mode.UPDATE);

    }

    @Override
    public void delete(final long rowId) {
        iEipAnnouncementLandingService.delete(rowId);
    }

    @Override
    public boolean saveOrUpdateForm() {
        final EIPAnnouncementLanding entity = getEntity();

        validateBean(entity, EipAnnouncementLandingvalidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        String directoryTree = MainetConstants.operator.EMPTY;
        SimpleDateFormat formatter=new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date=formatter.format(new Date());
        if (entity.getId() == 0) {
            directoryTree = getStorePath(
                    UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.operator.FORWARD_SLACE + date + MainetConstants.operator.FORWARD_SLACE
                            + MainetConstants.DirectoryTree.EIP);
        } else {
            final String file = entity.getAttachment();

            if ((file != null) || !MainetConstants.operator.EMPTY.equals(file)) {
                final String[] files = file.split(MainetConstants.operator.COMA);

                directoryTree = StringUtility.staticStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, files[0]);

                if (MainetConstants.operator.EMPTY.equals(directoryTree)) {
                    directoryTree = getStorePath(
                            UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.operator.FORWARD_SLACE + date + MainetConstants.operator.FORWARD_SLACE
                                    + MainetConstants.DirectoryTree.EIP);
                }
            } else {
                directoryTree = getStorePath(
                        UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.operator.FORWARD_SLACE + date + MainetConstants.operator.FORWARD_SLACE
                                + MainetConstants.DirectoryTree.EIP);
            }
        }

        if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            final Set<File> files = FileUploadUtility.getCurrent().getFileMap().get(0L);
            List<File> list = null;
            for (final File file : files) {
                entity.setAttachment(directoryTree + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
                iEipAnnouncementLandingService.saveOrUpdate(entity);
                try {
                    list = new ArrayList<>();
                    list.add(file);

                    getFileNetClient().uploadFileList(list, directoryTree);
                    FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
                    break;
                } catch (final Exception e) {
                    LOG.error(MainetConstants.ERROR_OCCURED, e);
                    return false;
                }
            }
            return true;
        } else {
            return iEipAnnouncementLandingService.saveOrUpdate(entity);
        }

    }

    private String getStorePath(final String pathRoot) {
        String path = pathRoot;
        path += (MainetConstants.operator.FORWARD_SLACE);
        path += ("EIP_ANNOUNCEMENT/LANDING_PAGE");
        path += (MainetConstants.operator.FORWARD_SLACE);
        path += (UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp());

        return path;
    }

    @Override
    public void cleareUploadFile() {
        getEntity().setAttachment(MainetConstants.operator.EMPTY);
    }

    public String getFilesDetails() {
        if ((getEntity().getAttachment() != null) && !getEntity().getAttachment().isEmpty()) {
            final String FileNames = StringUtility.staticStringBeforeChar(MainetConstants.operator.DOUBLE_BACKWARD_SLACE,
                    getEntity().getAttachment());
            return FileNames;
        } else {
            return null;
        }
    }

    public String getFileName() {
        if ((getEntity().getAttachment() != null) && !getEntity().getAttachment().isEmpty()) {
            final String FileNames = StringUtility.staticStringAfterChar(MainetConstants.operator.DOUBLE_BACKWARD_SLACE,
                    getEntity().getAttachment());
            return FileNames;
        } else {
            return null;
        }
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }
}
