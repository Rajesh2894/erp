package com.abm.mainet.rnl.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.EstateMaster;
import com.abm.mainet.rnl.service.IEstateService;
import com.abm.mainet.rnl.ui.validator.EstateMasterFormValidator;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class EstateMasterModel extends AbstractFormModel

{

    private static final long serialVersionUID = 1L;
    private List<Object[]> locationList;
    private EstateMaster estateMaster;
    private Map<Character, String> categoryType;
    private List<AttachDocs> documentList;
    private List<Long> ids = new ArrayList<>();
    private String modeType;
    private List<String> assetCodeList = new ArrayList<>();
    private String applicableENV = "N";
    private List<Object[]> assetNoAndNameList;
    
    @Autowired
    private IEstateService iEstateService;

    public List<Object[]> getLocationList() {
        return locationList;
    }

    public void setLocationList(
            final List<Object[]> locationList) {
        this.locationList = locationList;
    }

    public Map<Character, String> getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(
            final Map<Character, String> categoryType) {
        this.categoryType = categoryType;
    }

    public EstateMaster getEstateMaster() {
        return estateMaster;
    }

    public void setEstateMaster(final EstateMaster estateMaster) {
        this.estateMaster = estateMaster;
    }

    @Override
    protected void initializeModel() {
        initializeLookupFields(PrefixConstants.RnLPrefix.ETY);
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        return MainetConstants.RnlPrefix_Field.EstateMaster.Type;
    }

    public List<String> getAssetCodeList() {
        return assetCodeList;
    }

    public void setAssetCodeList(List<String> assetCodeList) {
        this.assetCodeList = assetCodeList;
    }

    public String getApplicableENV() {
        return applicableENV;
    }

    public void setApplicableENV(String applicableENV) {
        this.applicableENV = applicableENV;
    }

    @Override
    public boolean saveForm() {

        validateBean(this, EstateMasterFormValidator.class);
        final EstateMaster estateMaster = getEstateMaster();

        // D#77282 check esNo already exist or not
       
        
        
        Boolean regNoExist = false;
        if(StringUtils.isNotBlank(estateMaster.getRegNo())) {
           regNoExist = iEstateService.checkEstateRegNoExist(estateMaster.getEsId(), estateMaster.getRegNo(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        }
        
        if (hasValidationErrors()) {
            return false;
        }

        if (regNoExist) {
            addValidationError(ApplicationSession.getInstance().getMessage("Estate Registration Number already Exist"));
          return false;
        }
        final List<AttachDocs> attachDocsList = uploadFile();

        if (getModeType().equals(MainetConstants.RnLCommon.MODE_CREATE)) {
            estateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            estateMaster.setLangId(UserSession.getCurrent().getLanguageId());
            estateMaster.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            estateMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            if (attachDocsList != null) {
                final String code = iEstateService.save(estateMaster, attachDocsList);
                setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateMasters.ESTATE_LABEL)
                        + " " + code + " " + ApplicationSession.getInstance().getMessage("estate.code.generate.success.msg"));
            }
        } else if (getModeType().equals(MainetConstants.RnLCommon.MODE_EDIT)) {
            estateMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            estateMaster.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
            if (attachDocsList != null) {
                iEstateService.saveEdit(estateMaster, attachDocsList, ids);
                setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateMasters.ESTATE_STATUS));
            }
        }
        return true;
    }

    private String getDirectry() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator
                + MainetConstants.RL_Identifier.NameEstateMaster + File.separator + Utility.getTimestamp();
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
        try {
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
                        FileNetApplicationClient.getInstance().uploadFileList(list, tempDirPath);
                    }
                }
            }
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

	public List<Object[]> getAssetNoAndNameList() {
		return assetNoAndNameList;
	}

	public void setAssetNoAndNameList(List<Object[]> assetNoAndNameList) {
		this.assetNoAndNameList = assetNoAndNameList;
	}

    
}
