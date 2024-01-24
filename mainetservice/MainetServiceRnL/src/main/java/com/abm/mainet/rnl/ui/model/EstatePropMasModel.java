package com.abm.mainet.rnl.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.ui.validator.PropMasterFormValidator;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class EstatePropMasModel extends AbstractFormModel {

    @Autowired
    private IEstatePropertyService estatePropertyService;

    @Autowired
    private IEstateBookingService bookingService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    private static final long serialVersionUID = 1L;
    private EstatePropMaster estatePropMaster=new  EstatePropMaster();
    private List<AttachDocs> documentList;
    private List<Long> ids = new ArrayList<>();
    private String modeType;
    private List<Object[]> estateMasters = Collections.emptyList();
    private Map<Long, String> codeMap = new HashMap<>();
    private String removeChildIds;
    private Long estatePropGridId;
    private boolean floorCheck;
    private String removeEventIds;
    private String removeShiftIds;
    private String removeFacilityIds;
    private String removeAminities;
    List<LookUp> usageType = new ArrayList<LookUp>();
    private String saveMode;

    @Override
    public boolean saveForm() {
        estatePropMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        validateBean(this, PropMasterFormValidator.class);
        final EstatePropMaster estatePropMaster = getEstatePropMaster();
        if (hasValidationErrors()) {
            return false;
        }
        final List<AttachDocs> attachDocsList = uploadFile();

        if (getModeType().equals(MainetConstants.RnLCommon.MODE_CREATE)) {
            estatePropMaster.setLangId(UserSession.getCurrent().getLanguageId());
            estatePropMaster.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            estatePropMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            estatePropMaster.setEstatecode(getCodeMap().get(estatePropMaster.getEstateId()));
            if (!isFloorCheck()) {
                estatePropMaster.setFloor(null);
            }
            if (attachDocsList != null) {
                final String code = estatePropertyService.save(estatePropMaster, attachDocsList);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("property.label.code") + " " + code + " "
                        + ApplicationSession.getInstance().getMessage("estate.code.generate.success.msg"));
            }
        } else if (getModeType().equals(MainetConstants.RnLCommon.MODE_EDIT)) {
            estatePropMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            estatePropMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
            final List<Long> removeIds = new ArrayList<>();
            final String ids = getRemoveChildIds();
            if (!ids.isEmpty()) {
                final String array[] = ids.split(",");
                for (final String string : array) {
                    removeIds.add(Long.valueOf(string));
                }
            }
            List<Long> removeAminity = getRemovePropertyAminities();
            List<Long> removeFacility = getRemovePropertyFacility();
            List<Long> removeEvent = getRemovePropertyEvent();
            List<Long> removeShift = getRemovePropertyShift();

            // D#82996
            // check event is map with estate booking using property id and eventId
            // fetch eventId using removeEvent

            if (removeEvent != null) {
                List<Long> eventIds = iEstatePropertyService.fetchEventIds(removeEvent);
                Boolean propertyBooked = false;
                if (!eventIds.isEmpty()) {
                    propertyBooked = bookingService.checkPropertyBookedByEventId(estatePropMaster.getPropId(), eventIds,
                            UserSession.getCurrent().getOrganisation().getOrgid());
                }
                if (propertyBooked) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("Cannot delete this event beacuse it is already in use"));
                    return false;
                }
            }

            if (attachDocsList != null) {
                // D#92517 below OBJ use at the time of edit of any details item delete completely from UI
                final EstatePropMaster estatePropMasterEdit = iEstatePropertyService
                        .findEstatePropWithDetailsById(estatePropMaster.getPropId());
                estatePropMasterEdit.setEstatecode(getCodeMap().get(estatePropMaster.getEstateId()));
                estatePropMasterEdit.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                estatePropMasterEdit.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                estatePropMasterEdit.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
                estatePropertyService.saveEdit(estatePropMaster, estatePropMasterEdit, attachDocsList, this.ids, removeIds,
                        removeAminity,
                        removeFacility, removeEvent, removeShift);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("property.code.edit.success"));
            }
        }
        return true;
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

    private List<Long> getRemovePropertyEvent() {
        String eventIds = getRemoveEventIds();
        List<Long> removeEvntIdList = null;
        if (eventIds != null && !eventIds.isEmpty()) {
            removeEvntIdList = new ArrayList<>();
            String eventId[] = eventIds.split(MainetConstants.operator.COMMA);
            for (String ids : eventId) {
                removeEvntIdList.add(Long.valueOf(ids));
            }
        }
        return removeEvntIdList;
    }

    private List<Long> getRemovePropertyShift() {
        List<Long> shiftList = null;
        String shiftIds = getRemoveShiftIds();
        if (shiftIds != null && !shiftIds.isEmpty()) {
            shiftList = new ArrayList<Long>();
            String propShiftId[] = shiftIds.split(MainetConstants.operator.COMMA);
            for (String id : propShiftId) {
                shiftList.add(Long.valueOf(id));
            }
        }
        return shiftList;
    }

    private List<Long> getRemovePropertyFacility() {
        List<Long> facilityList = null;
        String facilityIds = getRemoveFacilityIds();
        if (facilityIds != null && !facilityIds.isEmpty()) {
            facilityList = new ArrayList<Long>();
            String facilityId[] = facilityIds.split(MainetConstants.operator.COMMA);
            for (String ids : facilityId) {
                facilityList.add(Long.valueOf(ids));
            }
        }
        return facilityList;
    }

    private List<Long> getRemovePropertyAminities() {
        List<Long> aminitiesList = null;
        String aminitiesIds = getRemoveAminities();
        if (aminitiesIds != null && !aminitiesIds.isEmpty()) {
            aminitiesList = new ArrayList<Long>();
            String aminityId[] = aminitiesIds.split(MainetConstants.operator.COMMA);
            for (String ids : aminityId) {
                aminitiesList.add(Long.valueOf(ids));
            }
        }
        return aminitiesList;
    }

    public Long getEstatePropGridId() {
        return estatePropGridId;
    }

    public void setEstatePropGridId(final Long estatePropGridId) {
        this.estatePropGridId = estatePropGridId;
    }

    public Map<Long, String> getCodeMap() {
        return codeMap;
    }

    public void setCodeMap(final Map<Long, String> codeMap) {
        this.codeMap = codeMap;
    }

    public EstatePropMaster getEstatePropMaster() {
        return estatePropMaster;
    }

    public void setEstatePropMaster(
            final EstatePropMaster estatePropMaster) {
        this.estatePropMaster = estatePropMaster;
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

    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    public List<Object[]> getEstateMasters() {
        return estateMasters;
    }

    public void setEstateMasters(final List<Object[]> estateMasters) {
        this.estateMasters = estateMasters;
    }

    private String getDirectry() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator
                + MainetConstants.RL_Identifier.NamePropMaster + File.separator + Utility.getTimestamp();
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(final String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public boolean isFloorCheck() {
        return floorCheck;
    }

    public void setFloorCheck(final boolean floorCheck) {
        this.floorCheck = floorCheck;
    }

    public String getRemoveEventIds() {
        return removeEventIds;
    }

    public void setRemoveEventIds(String removeEventIds) {
        this.removeEventIds = removeEventIds;
    }

    public String getRemoveShiftIds() {
        return removeShiftIds;
    }

    public void setRemoveShiftIds(String removeShiftIds) {
        this.removeShiftIds = removeShiftIds;
    }

    public String getRemoveFacilityIds() {
        return removeFacilityIds;
    }

    public void setRemoveFacilityIds(String removeFacilityIds) {
        this.removeFacilityIds = removeFacilityIds;
    }

    public String getRemoveAminities() {
        return removeAminities;
    }

    public void setRemoveAminities(String removeAminities) {
        this.removeAminities = removeAminities;
    }

    public List<LookUp> getUsageType() {
        return usageType;
    }

    public void setUsageType(List<LookUp> usageType) {
        this.usageType = usageType;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

}
