package com.abm.mainet.cfc.checklist.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Implementation of ChecklistMstService
 */
@Component
@Transactional
public class ChecklistMstServiceImpl implements ChecklistMstService {

    /*
     * @Resource private TbCfcChecklistMstJpaRepository tbCfcChecklistMstJpaRepository;
     */

    /*
     * @Resource private ChecklistMstJpaRepository checklistMstJpaRepository;
     */

    /*
     * @Resource private TbCfcChecklistMstServiceMapper tbCfcChecklistMstServiceMapper;
     */

    /*
     * @Resource private ChecklistMstServiceMapper checklistMstServiceMapper;
     */

    @Resource
    private CFCAttechmentRepository repository;

    /*
     * @Override public void saveChecklistData(final TbCfcChecklistMstDto checklistMstDto, final List<ChecklistMst>
     * checklistMstList) { ChecklistMstEntity checklistMstEntity = null; final Date curDate = new Date(); for (final ChecklistMst
     * checklistMst : checklistMstList) { checklistMstEntity = new ChecklistMstEntity();
     * checklistMstEntity.setDocGroup(checklistMst.getDocGroup());
     * checklistMstEntity.setSmServiceId(checklistMstDto.getChecklistMst().getHiddenServiceId());
     * checklistMstEntity.setClStatus(MainetConstants.STATUS.ACTIVE);
     * checklistMstEntity.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
     * checklistMstEntity.setLgIpMac(Utility.getMacAddress());
     * checklistMstEntity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
     * checklistMstEntity.setCreatedDate(curDate); checklistMstJpaRepository.save(checklistMstEntity); } }
     * @Override public void updateChecklistData(final TbCfcChecklistMstDto cfcChecklistMstDto, final List<ChecklistMst>
     * checklistMstList, final List<ChecklistMst> checklistMstTempList) { ChecklistMstEntity checklistMstEntity = null; final Date
     * curDate = new Date(); int dbDataFound = 0; final List<ChecklistMst> finalCheckList = new ArrayList<>(); for (final
     * ChecklistMst newListElement : cfcChecklistMstDto.getChecklistList()) { dbDataFound = 0; for (ChecklistMst dbListElement :
     * checklistMstList) { if (newListElement.getClId() != null) { if (dbListElement.getClId().equals(newListElement.getClId())) {
     * dbListElement.setDocGroup(newListElement.getDocGroup()); dbListElement.setUpdatedDate(curDate);
     * dbListElement.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
     * dbListElement.setLgIpMacUpd(Utility.getMacAddress()); finalCheckList.add(dbListElement); dbDataFound++; break; } } else {
     * dbListElement = new ChecklistMst(); dbListElement.setDocGroup(newListElement.getDocGroup());
     * dbListElement.setClStatus(MainetConstants.STATUS.ACTIVE); dbListElement.setCreatedDate(curDate);
     * dbListElement.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
     * dbListElement.setLgIpMac(Utility.getMacAddress()); finalCheckList.add(dbListElement); dbDataFound++; break; } } if
     * (dbDataFound == 0) { finalCheckList.add(newListElement); } } int count = 0; for (final ChecklistMst dbListElement :
     * checklistMstList) { count = 0; for (final ChecklistMst fianlListElement : finalCheckList) { if ((fianlListElement.getClId()
     * != null) && fianlListElement.getClId().equals(dbListElement.getClId())) { count++; } } if (count == 0) {
     * dbListElement.setClStatus(MainetConstants.STATUS.INACTIVE); finalCheckList.add(dbListElement); } } for (final ChecklistMst
     * checklistMst : finalCheckList) { checklistMstEntity = new ChecklistMstEntity();
     * checklistMst.setSmServiceId(cfcChecklistMstDto.getChecklistMst().getSmServiceId()); if (checklistMst.getDocGroup() != null)
     * { checklistMst.setOrgid(checklistMstTempList.get(0).getOrgid());
     * checklistMstServiceMapper.mapChecklistMstToChecklistMstEntity(checklistMst, checklistMstEntity);
     * checklistMstJpaRepository.save(checklistMstEntity); } } }
     * @Override public List<ChecklistMst> findAllDataByServiceIdOrgId(final Long orgId, final Long smServiceId) { final
     * List<ChecklistMstEntity> entities = checklistMstJpaRepository.findAllDataByServiceIdOrgId(orgId.longValue(), smServiceId);
     * final List<ChecklistMst> beans = new ArrayList<>(); ChecklistMst checklistMst = null; for (final ChecklistMstEntity
     * checklistMstEntity : entities) { checklistMst =
     * checklistMstServiceMapper.mapChecklistMstEntityToChecklistMst(checklistMstEntity); beans.add(checklistMst); } return beans;
     * }
     */

    @Override
    public boolean uploadForChecklistVerification(final String directoryTree, final Long deptid, final long serviceId,
            final FileNetApplicationClient fileNetApplicationClient, final Long appid, final String[] listOfChkboxStatus) {

        final List<CFCAttachment> attachments = new ArrayList<>();
        List<File> list = null;
        final Date date = new Date();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<>(entry.getValue());

            final Iterator<File> setFilesItr = entry.getValue().iterator();
            CFCAttachment cfcAttachment = null;
            while (setFilesItr.hasNext()) {
                String tempDirPath = MainetConstants.operator.EMPTY;

                final File file = setFilesItr.next();

                cfcAttachment = new CFCAttachment();

                cfcAttachment.setAttBy(UserSession.getCurrent().getEmployee().getEmpname());

                cfcAttachment.setAttDate(date);

                cfcAttachment.setApplicationId(appid);

                cfcAttachment.setServiceId(serviceId);

                cfcAttachment.setDept(deptid);

                cfcAttachment.setAttFromPath(MainetConstants.FROM_SYS);

                cfcAttachment.setAttFname(file.getName());

                for (final FileUploadClass fileUploadClass : FileUploadUtility.getCurrent().getFileUploadSet()) {
                    if (entry.getKey().toString().equals(fileUploadClass.getCurrentCount().toString())) {
                        boolean pathStatus = true;

                        if (isChecked(entry.getKey().toString(), listOfChkboxStatus)) {
                            cfcAttachment.setClmAprStatus(MainetConstants.Common_Constant.YES);
                        }

                        cfcAttachment.setClmId(fileUploadClass.getCheckListId());
                        cfcAttachment.setClmDesc(fileUploadClass.getCheckListDesc());
                        cfcAttachment.setClmDescEngl(fileUploadClass.getCheckListDesc());
                        cfcAttachment.setClmSrNo(fileUploadClass.getCheckListSrNo());
                        cfcAttachment.setClmStatus(fileUploadClass.getCheckListMStatus());
                        cfcAttachment.setChkStatus(fileUploadClass.getCheckListSStatus());
                        cfcAttachment.setMandatory(fileUploadClass.getCheckListMandatoryDoc());
                        cfcAttachment.setDocDescription(fileUploadClass.getCheckListDocDesc());

                        if (pathStatus) {
                            tempDirPath = directoryTree + MainetConstants.FILE_PATH_SEPARATOR
                                    + fileUploadClass.getFolderName();

                            cfcAttachment.setAttPath(tempDirPath);

                            pathStatus = false;
                        }
                        cfcAttachment = updateAuditFields(cfcAttachment);
                        attachments.add(cfcAttachment);

                        repository.save(cfcAttachment);
                    }
                }

                try {
                    fileNetApplicationClient.uploadFileList(list, tempDirPath);
                } catch (final Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        flushServerFolder();
        return true;
    }

    private void flushServerFolder() {

        try {
            final String path = FileUploadUtility.getCurrent().getExistingFolderPath();
            if (path != null) {
                final File cacheFolderStructure = new File(FileUploadUtility.getCurrent().getExistingFolderPath());

                FileUtils.deleteDirectory(cacheFolderStructure);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isChecked(final String index, final String[] listOfChkboxStatus1) {
        for (final String string : listOfChkboxStatus1) {
            if (!(string == null) && string.equals(index + MainetConstants.BLANK)) {
                return true;
            }

        }

        return false;
    }

    private CFCAttachment updateAuditFields(final CFCAttachment cfcAttachment) {
        cfcAttachment.setLmodate(new Date());
        cfcAttachment.setLgIpMac(Utility.getMacAddress());
        final UserSession currentSession = UserSession.getCurrent();
        cfcAttachment.setOrgid(currentSession.getOrganisation().getOrgid());
        cfcAttachment.setUserId(currentSession.getEmployee().getEmpId());
        return cfcAttachment;

    }

}
