package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.cms.service.IAdminPublicNoticesService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 *
 */
@Component
@Scope("session")
public class CitizenPublicNoticesModel extends AbstractEntryFormModel<PublicNotices> implements Serializable {

    private static final long serialVersionUID = 5933740935650680781L;

    @Autowired
    private IAdminPublicNoticesService iAdminPublicNoticesService;

    private long deptId;

    private List<PublicNoticesHistory> publicNotices = new ArrayList<>(0);

    private List<LookUp> pathLookUps = new ArrayList<>(0);

    /**
     * @return the deptId
     */
    public long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final long deptId) {
        this.deptId = deptId;
    }

    /**
     * @return the publicNotices
     */
    public List<PublicNoticesHistory> getPublicNotices() {
        return publicNotices;
    }

    /**
     * @param publicNotices the publicNotices to set
     */
    public void setPublicNotices(final List<PublicNoticesHistory> publicNotices) {
        this.publicNotices = publicNotices;
    }

    public void getAllNotices() {
        publicNotices = iAdminPublicNoticesService.getGuestCitizenPublicNotices(UserSession.getCurrent().getOrganisation());

        String FileName = null;
        for (final PublicNoticesHistory publicNotice : publicNotices) {
            final String attachmentPath = publicNotice.getProfileImgPath();
            FileName = StringUtility.staticStringAfterChar(File.separator, attachmentPath);
            publicNotice.setAttachmentName(FileName);
        }

        final ListIterator<PublicNoticesHistory> listIterator = publicNotices.listIterator();
        PublicNoticesHistory publicNotices = null;
        String[] pathArr = null;
        String directoryTree = null;
        FileStorageCache cache = null;
        while (listIterator.hasNext()) {
            publicNotices = listIterator.next();

            directoryTree = publicNotices.getProfileImgPath();

            if (directoryTree != null) {
                pathArr = directoryTree.split(MainetConstants.operator.COMA);

                directoryTree = (pathArr.length > 0) ? pathArr[0] : directoryTree;

                directoryTree = StringUtility.staticStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, directoryTree);

                directoryTree = directoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
            }

            cache = publicNotices.getFileStorageCache();

            try {
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                    getFileNetClient().uploadFileList(cache.getFileList(), directoryTree);
                }
            } catch (final Exception ex) {
                throw new FrameworkException(ex.getLocalizedMessage());
            } finally {
                cache.flush();
            }
        }
    }

    @Override
    public String getActiveClass() {
        return "publicNotice";
    }

    /**
     * @return the pathLookUps
     */
    public List<LookUp> getPathLookUps() {
        return pathLookUps;
    }

    /**
     * @param pathLookUps the pathLookUps to set
     */
    public void setPathLookUps(final List<LookUp> pathLookUps) {
        this.pathLookUps = pathLookUps;
    }
}
