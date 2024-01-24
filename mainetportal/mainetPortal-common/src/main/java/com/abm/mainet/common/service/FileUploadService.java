package com.abm.mainet.common.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AttachDocsRepository;
import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.FileUploadDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.service.DmsService;
import com.abm.mainet.dms.service.FileUploadClass;
import com.abm.mainet.dms.ui.validator.FileUploadValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author vishnu.jagdale
 *
 */
@Service
public class FileUploadService implements IFileUploadService {

    @Autowired
    private DmsService dmsService;

    @Value("${upload.physicalPath}")
    private String filenetPath;

    @Autowired
    private AttachDocsRepository attachDocsRepository;

    private static final Logger LOG = Logger.getLogger(FileUploadService.class);

    /*
     * creating save Master for File Upload
     * @param DocumentDetailsVO
     * @param FileUploadDTO
     * @return
     */
    @Override
    @Transactional
    public boolean saveMasterFileToPath(List<DocumentDetailsVO> docdetailsList, FileUploadDTO fileUploadDTOs) {
        String directoryPath = null;
        Map<String, String> fileMaps = null;
        boolean status = false;
        boolean isDMS = false;
        boolean isFolderExist = false;
        if (MainetConstants.Common_Constant.YES
                .equals(ApplicationSession.getInstance().getMessage(MainetConstants.DMS_CONFIGURE))) {
            isDMS = true;
        }
        final List<AttachDocs> saveMasterpathObjectList = new ArrayList<>();
        if ((docdetailsList != null) &&
                !docdetailsList.isEmpty()) {
            for (final DocumentDetailsVO docdetailVO : docdetailsList) {
                String fileName = docdetailVO.getDocumentName();
                if (fileName != null) {
                    if (fileName.contains(MainetConstants.operator.FORWARD_SLACE)) {
                        fileName = fileName.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.UNDER_SCORE);
                    }
                    if (docdetailVO.getDoc_DESC_Mar() != null) {
                        docdetailVO.setDoc_DESC_Mar(docdetailVO.getDoc_DESC_Mar());
                    }
                    fileUploadDTOs.setFileName(fileName);
                    AttachDocs saveFilePath = null;
                    if (!isDMS) {
                        final boolean fileSavedSucessfully = convertAndSaveFile(docdetailVO, fileUploadDTOs.getFileNetPath(),
                                fileUploadDTOs.getDirPath(),
                                fileName);
                        if (fileSavedSucessfully) {
                            saveFilePath = doSetSaveMasterFilePathEntityObject(fileUploadDTOs,
                                    docdetailVO);
                            if (saveFilePath != null) {
                                saveMasterpathObjectList.add(saveFilePath);
                            }
                        }
                    } else {
                        if (directoryPath == null) {
                            directoryPath = fileUploadDTOs.getDirPath();
                        }
                        fileMaps = dmsService.createDocument(directoryPath,
                                docdetailVO.getDocumentByteCode(), isFolderExist);
                        if (fileMaps != null) {
                            isFolderExist = true;
                            saveFilePath = doSetSaveMasterFilePathEntityObject(fileUploadDTOs, docdetailVO);
                            if (saveFilePath != null) {
                                saveMasterpathObjectList.add(saveFilePath);
                            }
                            directoryPath = fileMaps.get("FOLDER_PATH");
                            saveFilePath.setDmsFolderPath(fileMaps.get("FOLDER_PATH"));
                            saveFilePath.setDmsDocId(fileMaps.get("DOC_ID"));
                            saveFilePath.setDmsDocName(fileMaps.get("FILE_NAME"));
                            if ((saveFilePath.getDmsDocVersion() != null) &&
                                    !saveFilePath.getDmsDocVersion().isEmpty()) {
                                Integer ver = Integer.valueOf(saveFilePath.getDmsDocVersion());
                                ver = ver + 1;
                                saveFilePath.setDmsDocVersion(String.valueOf(ver));
                            } else {
                                saveFilePath.setDmsDocVersion("1");
                            }
                        } else {
                            throw new RuntimeException();
                        }
                    }
                } else {
                    status = true;
                }
            }
            if (!saveMasterpathObjectList.isEmpty()) {
                try {
                	//not required at portal by MAYANK
                    //attachDocsRepository.save(saveMasterpathObjectList);
                    status = true;
                } catch (Exception e) {
                    LOG.error(MainetConstants.ERROR_OCCURED, e);
                    status = true;
                }
            }
        }
        return status;
    }

    private AttachDocs doSetSaveMasterFilePathEntityObject(final FileUploadDTO fileUploadDTO,
            final DocumentDetailsVO docdetailVO) {
        final AttachDocs attachDocs = new AttachDocs();
        attachDocs.setAttId(docdetailVO.getAttachmentId());
        attachDocs.setAttDate(new Date());
        attachDocs.setLmodDate(new Date());
        attachDocs.setAttPath(fileUploadDTO.getDirPath());
        attachDocs.setAttFname(fileUploadDTO.getFileName());
        attachDocs.setAttBy("");
        attachDocs.setIdfId(fileUploadDTO.getIdfId());
        attachDocs.setDept(fileUploadDTO.getDeptId());
        attachDocs.setOrgid(fileUploadDTO.getOrgId());
        attachDocs.setUserid(fileUploadDTO.getUserId());
        attachDocs.setStatus(fileUploadDTO.getStatus());
        attachDocs.setDmsDocName(docdetailVO.getDoc_DESC_ENGL());
        return attachDocs;
    }

    /**
     * common method to upload Master documents, use this method if file upload is required
     *
     * @param requestDTO : pass this dto by setting its required field
     * @param documentList : pass document list
     * @return
     */

    @Override
    public boolean doMasterFileUpload(List<DocumentDetailsVO> documentList, RequestDTO requestDTO) {

        final FileUploadDTO uploadDTO = new FileUploadDTO();

        final String masterDirPath = masterDirStructure(requestDTO.getOrgId(), requestDTO.getIdfId(),
                requestDTO.getDepartmentName());

        uploadDTO.setDeptId(requestDTO.getDeptId());
        uploadDTO.setDirPath(masterDirPath);
        uploadDTO.setFileNetPath(filenetPath);
        uploadDTO.setOrgId(requestDTO.getOrgId());
        uploadDTO.setIdfId(requestDTO.getIdfId());
        uploadDTO.setStatus(requestDTO.getStatus());
        uploadDTO.setUserId(requestDTO.getUserId());
        uploadDTO.setDepartmentName(requestDTO.getDepartmentName());

        return saveMasterFileToPath(documentList, uploadDTO);

    }

    /**
     * creating directory structure for master File Upload
     *
     * @param orgId
     * @param departmentname
     * @param identifier Id
     * @return
     */
    private String masterDirStructure(final Long orgId, final String idfId, final String departmentName) {
        final StringBuilder builder = new StringBuilder();

        builder.append(orgId).append(File.separator)
                .append(Utility.dateToString(new Date(), MainetConstants.DATEFORMAT_DDMMYYYY)).append(File.separator)
                .append(departmentName).append(File.separator).append(idfId);

        return builder.toString();
    }

    @Override
    public List<DocumentDetailsVO> setFileUploadMethod(List<DocumentDetailsVO> docs) {
        Base64 base64 = null;
        ArrayList<?> list = null;
        Iterator<?> arg4 = FileUploadUtility.getCurrent().getFileMap().entrySet().iterator();

        while (arg4.hasNext()) {
            Entry<?, ?> entry = (Entry<?, ?>) arg4.next();
            list = new ArrayList<Object>((Collection<?>) entry.getValue());
            Iterator<?> arg6 = list.iterator();

            while (arg6.hasNext()) {
                File file = (File) arg6.next();

                try {
                    base64 = new Base64();
                    String e = base64.encodeToString(FileUtils.readFileToByteArray(file));
                    DocumentDetailsVO d = new DocumentDetailsVO();
                    d.setDocumentName(file.getName());
                    d.setDocumentByteCode(e);
                    docs.add(d);
                } catch (IOException arg9) {
                    LOG.error("Exception has been occurred in file byte to string conversions", arg9);
                }
            }
        }

        return docs;
    }

    @Override
    public boolean convertAndSaveFile(final DocumentDetailsVO ddVO, final String fileNetPath, final String dirPath,
            final String fileName) {
        final Base64 base64 = new Base64();

        final String completeDirpath = fileNetPath + MainetConstants.FILE_PATH_SEPARATOR + dirPath;
        final String completPath = completeDirpath + MainetConstants.FILE_PATH_SEPARATOR + fileName;
        FileOutputStream fileOutputStream = null;
        try {
            final File files = new File(completeDirpath);
            if (!files.exists()) {
                if (files.mkdirs()) {
                    LOG.info("sub directories created successfully");
                } else {
                    LOG.error("failed to create sub directories");
                }
            }
            fileOutputStream = new FileOutputStream(completPath);
            if (ddVO.getDocumentByteCode() != null) {
                fileOutputStream.write(base64.decode(ddVO.getDocumentByteCode()));
                fileOutputStream.flush();

                return true;
            }

            return false;
        } catch (final FileNotFoundException e1) {
            LOG.error("Error Occurred while saving checklist document", e1);
            return false;
        } catch (final IOException e) {
            LOG.error("Error Occurred while decoding File(I/O operation):", e);
            return false;
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    @Override
    public void downLoadFilesFromServer(final AttachDocs attachDocs, final String guidRanDNum,
            final FileNetApplicationClient fileNetApplicationClient) {
        final Map<Long, Set<File>> fileMap = FileUploadUtility.getCurrent().getFileMap();

        if (!FileUploadUtility.getCurrent().isFolderCreated()) {
            final String uidPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                    + MainetConstants.FILE_PATH_SEPARATOR + guidRanDNum + MainetConstants.FILE_PATH_SEPARATOR;

            FileUploadUtility.getCurrent().setExistingFolderPath(uidPath);

        }

        final String folder = getFolderName(attachDocs.getAttPath());

        final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath() + folder;

        Utility.downloadedFileUrl(
                attachDocs.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + attachDocs.getAttFname(), folderPath,
                fileNetApplicationClient);

        final String path = Filepaths.getfilepath() + FileUploadUtility.getCurrent().getExistingFolderPath() + folder
                + MainetConstants.FILE_PATH_SEPARATOR + attachDocs.getAttFname();

        final String val = FileUploadValidator.getPrefixSuffixFromString(attachDocs.getAttPath(),
                MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.OperationMode.SUFFIX,
                MainetConstants.OperationMode.END);

        final File file = new File(path);

        boolean flag = true;

        for (final Map.Entry<Long, Set<File>> entry : fileMap.entrySet()) {

            if (entry.getKey().toString().equals(val)) {
                final Set<File> set = entry.getValue();

                set.add(file);

                fileMap.put(Long.valueOf(val), set);

                flag = false;

            }
        }

        if (flag) {
            final Set<File> fileDetails = new LinkedHashSet<>();

            fileDetails.add(file);

            if (folder.equals(MainetConstants.operator.EMPTY)) {
                fileMap.put(Long.valueOf(val), fileDetails);
            } else {
                fileMap.put(Long.valueOf(folder), fileDetails);
            }

        }

        FileUploadUtility.getCurrent().setFolderCreated(true);
    }

    private String getFolderName(final String path) {
        final String val = FileUploadValidator.getPrefixSuffixFromString(path, MainetConstants.FILE_PATH_SEPARATOR,
                MainetConstants.OperationMode.SUFFIX, MainetConstants.OperationMode.END);

        if (val.length() > 2) {
            return MainetConstants.operator.EMPTY;
        }

        return val;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.core.service.IAttachDocsService#findByEstateCode(java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttachDocs> findByCode(final Long orgId,
            final String identifier) {
        LOG.info("findByCode(Long orgId,String estateCode) execution starts");
        return attachDocsRepository.findByCode(orgId, identifier, MainetConstants.STATUS.ACTIVE);
    }

    @Override
    @Transactional
    public int updateMasterDocumentStatus(String uniqueId, String status) {
        return attachDocsRepository.updateMasterDocumentStatus(uniqueId, status);
    }

    @Override
    @Transactional
    public int updateAllDocumentStatus(String status, Long empId, List<Long> attList) {
        return attachDocsRepository.updateRecord(attList, empId, status);
    }

    @Override
    public List<DocumentDetailsVO> getUploadedDocForPreview(List<DocumentDetailsVO> docs) {

        List<DocumentDetailsVO> docsument = null;
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            docsument = new ArrayList<>();
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        DocumentDetailsVO c = new DocumentDetailsVO();
                        c.setUploadedDocumentPath(file.getPath());
                        c.setDocumentName(file.getName());
                        docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey() + 1)).forEach(doc -> {
                            c.setDoc_DESC_ENGL(doc.getDoc_DESC_ENGL());
                            c.setDoc_DESC_Mar(doc.getDoc_DESC_Mar());

                        });
                        docsument.add(c);
                    } catch (final Exception e) {
                        LOG.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }
        return docsument;
    }

    /*
     * Method added for converting (non-Javadoc)
     * @see com.abm.mainet.common.service.IFileUploadService#setFileUploadMethod(java.util.List)
     */
    @Override
    public List<DocumentDetailsVO> convertFileToByteString(
            final List<DocumentDetailsVO> docs) {
        final Map<Long, String> listOfString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();

        if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            Base64 base64 = null;
            List<File> list = null;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                        fileName.put(entry.getKey(), file.getName());
                        listOfString.put(entry.getKey(), bytestring);
                    } catch (final IOException e) {
                        LOG.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }
        if (!docs.isEmpty() && !listOfString.isEmpty()) {
            for (final DocumentDetailsVO d : docs) {
                final long count = d.getDocumentSerialNo() - 1;
                if (listOfString.containsKey(count) && fileName.containsKey(count)) {
                    d.setDocumentByteCode(listOfString.get(count));
                    d.setDocumentName(fileName.get(count));
                }
            }
        }
        return docs;
    }

    @Override
    public void validateUpload(BindingResult bindingResult) {
        // TODO Auto-generated method stub
        final String manditoryValue = MainetConstants.Common_Constant.YES;
        final Iterator<FileUploadClass> fileUploadItr = FileUploadUtility.getCurrent().getFileUploadSet().iterator();
        ObjectError objectError = null;
        FileUploadClass fileUploadClass = null;
        while (fileUploadItr.hasNext()) {
                fileUploadClass = fileUploadItr.next();

                if (fileUploadClass.getCheckListMandatoryDoc().equals(manditoryValue)) {
                        boolean check = false;

                        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

                                if (entry.getKey().toString().equals(fileUploadClass.getCurrentCount().toString())) {
                                        check = true;
                                        break;
                                }

                        }

                        if (!check) {
                                objectError = new ObjectError(MainetConstants.operator.EMPTY,
                                                FileUploadValidator
                                                                .getFieldLabel(MainetConstants.ValidationMessageCode.FILE_UPLOAD_VALIDATION_MSG)
                                                                + fileUploadClass.getCheckListDesc());
                                bindingResult.addError(objectError);
                        }
                }
        }  
    }

    @Override
    public List<DocumentDetailsVO> prepareFileUpload(final List<DocumentDetailsVO> docs) {
        final Map<Long, String> listOfString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                        && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                        final List<File> list = new ArrayList<>(entry.getValue());
                        for (final File file : list) {
                                try {
                                        final Base64 base64 = new Base64();
                                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                                        fileName.put(entry.getKey(), file.getName());
                                        listOfString.put(entry.getKey(), bytestring);
                                } catch (final IOException e) {
                                        LOG.error("Exception has been occurred in file byte to string conversions", e);
                                }
                        }
                }
        }
        if (docs != null && !docs.isEmpty() && !listOfString.isEmpty()) {
                long count = 0;
                for (final DocumentDetailsVO d : docs) {
                        if (d.getDocumentSerialNo() != null) {
                                count = d.getDocumentSerialNo() - 1;// writing this code because every time we did not get
                                                                                                        // documentSerialNo
                        }
                        if (listOfString.containsKey(count) && fileName.containsKey(count)) {
                                d.setDocumentByteCode(listOfString.get(count));
                                d.setDocumentName(fileName.get(count));
                        }
                        count++;
                }
        }
        return docs;
        }
}
