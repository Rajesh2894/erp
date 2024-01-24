package com.abm.mainet.rnl.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.TenantMaster;
import com.abm.mainet.rnl.service.ITenantService;
import com.abm.mainet.rnl.ui.validator.TenantMasterFormValidator;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class TenantMasterModel extends AbstractFormModel {
    private static final long serialVersionUID = 8044901885461586899L;
    private TenantMaster tenantMaster;
    private List<AttachDocs> documentList;
    private List<Long> ids = new ArrayList<>();
    private String modeType;
    @Autowired
    private ITenantService iTenantService;
    private String removeChildIds;

    public TenantMaster getTenantMaster() {
        return tenantMaster;
    }

    public void setTenantMaster(final TenantMaster tenantMaster) {
        this.tenantMaster = tenantMaster;
    }

    @Override
    public boolean saveForm() {

        validateBean(this, TenantMasterFormValidator.class);
        final TenantMaster tenantMaster = getTenantMaster();
        if (hasValidationErrors()) {
            return false;
        }
        final List<AttachDocs> attachDocsList = uploadFile();

        if (getModeType().equals(MainetConstants.RnLCommon.MODE_CREATE)) {
            tenantMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            tenantMaster.setLangId(UserSession.getCurrent().getLanguageId());
            tenantMaster.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            tenantMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            if (attachDocsList != null) {
                final String code = iTenantService.saveTenantMaster(tenantMaster, attachDocsList);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("estate.label.code") + " " + code + " "
                        + ApplicationSession.getInstance().getMessage("estate.code.generate.success.msg"));
            }
        } else if (getModeType().equals(MainetConstants.RnLCommon.MODE_EDIT)) {
            tenantMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            tenantMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
            final List<Long> removeIds = new ArrayList<>();
            final String ids = getRemoveChildIds();
            if (!ids.isEmpty()) {
                final String array[] = ids.split(",");
                for (final String string : array) {
                    removeIds.add(Long.valueOf(string));
                }
            }
            if (attachDocsList != null) {
                iTenantService.saveEdit(tenantMaster, attachDocsList, this.ids, removeIds);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("estate.code.edit.success.msg"));
            }
        }
        return true;
    }

    private String getDirectry() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator
                + MainetConstants.RL_Identifier.NameTenantMaster + File.separator + Utility.getTimestamp();
    }

    private List<AttachDocs> uploadFile() {

        final List<AttachDocs> attachDocsList = new ArrayList<>(0);
        AttachDocs attachDocs = null;
        final Date date = new Date();
        List<File> list = null;
        File file = null;
        String tempDirPath = "";
        Iterator<File> setFilesItr = null;
        final List<AttachDocs> docs = getDocumentList();
        final List<String> str = new ArrayList<>();
        if (docs != null) {
            for (final AttachDocs attaches : docs) {
                str.add(attaches.getAttFname());
            }
        }
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<>(entry.getValue());
            setFilesItr = entry.getValue().iterator();

            while (setFilesItr.hasNext()) {
                file = setFilesItr.next();
                if (!str.contains(file.getName())) {
                    tempDirPath = MainetConstants.operator.EMPTY;
                    attachDocs = new AttachDocs();
                    attachDocs.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    attachDocs.setLmodate(date);
                    attachDocs.setAttDate(new Date());
                    attachDocs.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                   // attachDocs.setLangId(UserSession.getCurrent().getLanguageId());
                    attachDocs.setStatus(MainetConstants.RnLCommon.Flag_A);
                    attachDocs.setAttFname(file.getName());
                    attachDocs.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                    for (final FileUploadClass fileUploadClass : FileUploadUtility.getCurrent().getFileUploadSet()) {
                        if (entry.getKey().toString().equals(fileUploadClass.getCurrentCount().toString())) {
                            boolean pathStatus = true;
                            if (pathStatus) {
                                tempDirPath = getDirectry() + MainetConstants.FILE_PATH_SEPARATOR
                                        + fileUploadClass.getFolderName();
                                attachDocs.setAttPath(tempDirPath);
                                attachDocs.setSerialNo(Integer.valueOf(fileUploadClass.getFolderName()));
                                pathStatus = false;
                            }
                            attachDocsList.add(attachDocs);
                        }
                    }

                    try {
                        FileNetApplicationClient.getInstance().uploadFileList(list, tempDirPath);
                    } catch (final Exception e) {
                        logger.error("", e);
                        return null;
                    }
                }
            }
        }

        try {
            final String path = FileUploadUtility.getCurrent().getExistingFolderPath();
            if (path != null) {
                final File cacheFolderStructure = new File(FileUploadUtility.getCurrent().getExistingFolderPath());
                FileUtils.deleteDirectory(cacheFolderStructure);
            }
        } catch (final Exception e) {
            logger.error("", e);
        }

        return attachDocsList;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    public List<AttachDocs> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(final List<AttachDocs> documentList) {
        this.documentList = documentList;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(final List<Long> ids) {
        this.ids = ids;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(final String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }
}
