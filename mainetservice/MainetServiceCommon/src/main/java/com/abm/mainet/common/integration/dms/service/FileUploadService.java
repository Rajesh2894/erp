/**
 *
 */
package com.abm.mainet.common.integration.dms.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.dao.IFileUploadRepository;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.DMSRequestDTO;
import com.abm.mainet.common.integration.dms.dto.DocField;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.mapper.FileUploadValidator;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.integration.dms.repository.IFileUploadCrudRepository;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;

/**
 * @author vishnu.jagdale
 *
 */
@Service
public class FileUploadService implements IFileUploadService {

	@Resource
	private IFileUploadRepository fileUploadRepository;

	@Resource
	private IFileUploadCrudRepository crudRepository;
	@Resource
	private CFCAttechmentRepository repository;

	@Autowired
	private IDmsService dmsService;

	@Value("${upload.physicalPath}")
	private String filenetPath;
	
	@Autowired
	private IOrganisationDAO iOrganisationDAO;
	
	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);

	/**
	 * common method to save uploaded documents
	 */
	@Override
	@Transactional
	public boolean saveFileToPath(final List<DocumentDetailsVO> docList, final FileUploadDTO fileUploadDTO) {
		String directoryPath = null;
		Map<String, String> fileMap = null;
		boolean status = false;
		boolean isDMS = false;
		boolean isFolderExist = false;
		if (MainetConstants.Common_Constant.YES
				.equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
			isDMS = true;
		}
		final List<CFCAttachment> savepathObjectList = new ArrayList<>();
		if ((docList != null) && !docList.isEmpty()) {
			for (final DocumentDetailsVO docdetailVO : docList) {
				String fileName = docdetailVO.getDocumentName();
				if (fileName != null) {
					if (fileName.contains(MainetConstants.operator.FORWARD_SLACE)) {
						fileName = fileName.replace(MainetConstants.operator.FORWARD_SLACE,
								MainetConstants.operator.UNDER_SCORE);
					}
					if (docdetailVO.getDoc_DESC_Mar() != null) {
						docdetailVO.setDoc_DESC_Mar(docdetailVO.getDoc_DESC_Mar());
					}

					fileUploadDTO.setFileName(fileName);
					CFCAttachment saveFilePath = null;
					if (!isDMS) {
						final boolean fileSavedSucessfully = convertAndSaveFile(docdetailVO,
								fileUploadDTO.getFileNetPath(), fileUploadDTO.getDirPath(), fileName);

						if (fileSavedSucessfully) {
							saveFilePath = doSetSaveFilePathEntityObject(fileUploadDTO, docdetailVO);
							if (saveFilePath != null) {
								savepathObjectList.add(saveFilePath);
							}
							//Doon_DMS specific code 
							if (fileUploadDTO.getOrgId() != null) {
								Organisation org = iOrganisationDAO.getOrganisationById(fileUploadDTO.getOrgId(),
										MainetConstants.STATUS.ACTIVE);
								LookUp lookUp = null;
								try {
									lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Dms.DOON_DMS,
											MainetConstants.ENV, org);
								} catch (Exception exception) {
									LOGGER.error("DOON_DMS prefix not found in Env");
								}
								try {
									if (lookUp != null && StringUtils.isNotBlank(lookUp.getOtherField())
											&& StringUtils.equals(lookUp.getOtherField(), MainetConstants.FlagY)) {
										doonDmsAPICall(fileName, fileUploadDTO,docdetailVO,org);
									}
								} catch (Exception exception) {
									LOGGER.error("Exception occur while calling Doon DMS API integration" + exception);
								}
							}
						}
							
					} else {
						if (directoryPath == null) {
							directoryPath = fileUploadDTO.getDirPath();
						}

						fileMap = dmsService.createDocument(directoryPath, docdetailVO.getDocumentByteCode(), fileName,
								isFolderExist,fileUploadDTO,docdetailVO);
						if (fileMap != null) {
							isFolderExist = true;
							saveFilePath = doSetSaveFilePathEntityObject(fileUploadDTO, docdetailVO);
							if (saveFilePath != null) {
								savepathObjectList.add(saveFilePath);
							}
							directoryPath = fileMap.get("FOLDER_PATH");
							saveFilePath.setDmsFolderPath(fileMap.get("FOLDER_PATH"));
							saveFilePath.setDmsDocId(fileMap.get("DOC_ID"));
							saveFilePath.setDmsDocName(fileMap.get("FILE_NAME"));
							if ((saveFilePath.getDmsDocVersion() != null)
									&& !saveFilePath.getDmsDocVersion().isEmpty()) {
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
			if (!savepathObjectList.isEmpty()) {
				status = fileUploadRepository.saveFilePathToDB(savepathObjectList);
			}
		}
		return status;
	}

	private CFCAttachment doSetSaveFilePathEntityObject(final FileUploadDTO fileUploadDTO,
			final DocumentDetailsVO docdetailVO) {
		final CFCAttachment saveFilePath = new CFCAttachment();

		saveFilePath.setAttId(docdetailVO.getAttachmentId());
		saveFilePath.setAttDate(new Date());
		saveFilePath.setAttPath(fileUploadDTO.getDirPath());
		saveFilePath.setAttFname(fileUploadDTO.getFileName());
		saveFilePath.setDept(fileUploadDTO.getDeptId());
		saveFilePath.setOrgid(fileUploadDTO.getOrgId());
		saveFilePath.setUserId(fileUploadDTO.getUserId());
		saveFilePath.setApplicationId(fileUploadDTO.getApplicationId());
		saveFilePath.setServiceId(fileUploadDTO.getServiceId());
		saveFilePath.setClmId(docdetailVO.getDocumentId());
		saveFilePath.setClmDesc(docdetailVO.getDoc_DESC_Mar());
		saveFilePath.setClmDescEngl(docdetailVO.getDoc_DESC_ENGL());
		saveFilePath.setClmSrNo(docdetailVO.getDocumentSerialNo());
		saveFilePath.setMandatory(docdetailVO.getCheckkMANDATORY());
		saveFilePath.setReferenceId(fileUploadDTO.getReferenceId());
		saveFilePath.setDocDescription(docdetailVO.getDocDescription());
		return saveFilePath;
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
					LOGGER.info("sub directories created successfully");
				} else {
					LOGGER.error("failed to create sub directories");
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
			LOGGER.error("Error Occurred while saving checklist document", e1);
			return false;
		} catch (final IOException e) {
			LOGGER.error("Error Occurred while decoding File(I/O operation):", e);
			return false;
		} finally {
			IOUtils.closeQuietly(fileOutputStream);
		}
	}

	/**
	 * convert File to byte code and encode converted bytes as String
	 *
	 * @param filePath
	 *            : filePath where File reside
	 * @return encoded File as String
	 */
	@Override
	public String encodeFile(final String filePath) throws IOException {

		String encodedFile = null;
		BufferedInputStream reader = null;
		try {

			final File file = new File(filenetPath + MainetConstants.DOUBLE_BACK_SLACE + filePath);
			final int length = (int) file.length();
			reader = new BufferedInputStream(new FileInputStream(file));
			final byte[] bytes = new byte[length];
			reader.read(bytes, 0, length);
			encodedFile = Base64.encodeBase64String(bytes);
		} finally {
			closeQuetly(reader);
		}

		return encodedFile;
	}

	/**
	 * closing EntityManager safely
	 *
	 * @param entityManager
	 */
	private void closeQuetly(final BufferedInputStream reader) {
		if (reader != null) {
			IOUtils.closeQuietly(reader);
		}
	}

	/**
	 * common method to upload documents, use this method if file upload is required
	 *
	 * @param requestDTO
	 *            : pass this dto by setting its required field
	 * @param documentList
	 *            : pass document list
	 * @return
	 */
	@Override
	public boolean doFileUpload(final List<DocumentDetailsVO> documentList, final RequestDTO requestDTO) {

		final FileUploadDTO uploadDTO = new FileUploadDTO();

		String dirPath = null;
		if (requestDTO.getApplicationId() != null) {
			dirPath = dirStructure(requestDTO.getOrgId(), requestDTO.getServiceId(), requestDTO.getApplicationId());
		} else {
			dirPath = dirStructureByReferenceId(requestDTO.getOrgId(), requestDTO.getServiceId(),
					requestDTO.getReferenceId());
		}
		uploadDTO.setApplicationId(requestDTO.getApplicationId());
		uploadDTO.setDeptId(requestDTO.getDeptId());
		uploadDTO.setDirPath(dirPath);
		uploadDTO.setFileNetPath(filenetPath);
		uploadDTO.setLangId(requestDTO.getLangId());
		uploadDTO.setOrgId(requestDTO.getOrgId());
		uploadDTO.setServiceId(requestDTO.getServiceId());
		uploadDTO.setUserId(requestDTO.getUserId());
		uploadDTO.setReferenceId(requestDTO.getReferenceId());
		return saveFileToPath(documentList, uploadDTO);

	}

	/**
	 * creating directory structure for File Upload
	 *
	 * @param orgId
	 * @param serviceId
	 * @param applicationNo
	 * @return
	 */
	private String dirStructure(final Long orgId, final Long serviceId, final Long applicationNo) {
		final StringBuilder builder = new StringBuilder();
		builder.append(orgId).append(File.separator)
				.append(Utility.dateToString(new Date(), MainetConstants.DATE_FORMAT_UPLOAD)).append(File.separator)
				.append(serviceId).append(File.separator).append(applicationNo);

		return builder.toString();
	}

	/**
	 * creating directory structure for File Upload using IdFid-ReferenceId
	 * 
	 * @param orgId
	 * @param serviceId
	 * @param referenceId
	 * @return
	 */
	private String dirStructureByReferenceId(final Long orgId, final Long serviceId, final String referenceId) {

		final StringBuilder builder = new StringBuilder();
		builder.append(orgId).append(File.separator)
				.append(Utility.dateToString(new Date(), MainetConstants.DATE_FORMAT_UPLOAD)).append(File.separator)
				.append(serviceId).append(File.separator).append(referenceId);

		return builder.toString();
	}

	@Override
	public void validateUpload(final BindingResult bindingResult) {
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
	public void sessionCleanUpForFileUpload() {
		FileUploadUtility.getCurrent().getFileMap().clear();
		FileUploadUtility.getCurrent().getFileUploadSet().clear();
		FileUploadUtility.getCurrent().setFolderCreated(false);
	}

	@Override
	public void downLoadFilesFromServer(final CFCAttachment cfcAttachment, final String guidRanDNum,
			final FileNetApplicationClient fileNetApplicationClient) {
		final Map<Long, Set<File>> fileMap = FileUploadUtility.getCurrent().getFileMap();

		if (!FileUploadUtility.getCurrent().isFolderCreated()) {
			final String uidPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + guidRanDNum + MainetConstants.FILE_PATH_SEPARATOR;

			FileUploadUtility.getCurrent().setExistingFolderPath(uidPath);

		}

		final String folder = getFolderName(cfcAttachment.getAttPath());

		final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath() + folder;

		Utility.downloadedFileUrl(
				cfcAttachment.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + cfcAttachment.getAttFname(),
				folderPath, fileNetApplicationClient);

		final String path = Filepaths.getfilepath() + FileUploadUtility.getCurrent().getExistingFolderPath() + folder
				+ MainetConstants.FILE_PATH_SEPARATOR + cfcAttachment.getAttFname();

		final String val = FileUploadValidator.getPrefixSuffixFromString(cfcAttachment.getAttPath(),
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

	public static FileUploadService getCurrent() {
		return ApplicationContextProvider.getApplicationContext().getBean(FileUploadService.class);
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
						LOGGER.error("Exception has been occurred in file byte to string conversions", e);
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

	@Override
	public List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {

		Base64 base64 = null;
		List<File> list = null;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			list = new ArrayList<>(entry.getValue());
			for (final File file : list) {
				try {
					base64 = new Base64();
					final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));

					DocumentDetailsVO d = new DocumentDetailsVO();
					d.setDocumentName(file.getName());
					d.setDocumentByteCode(bytestring);
					d.setUploadedDocumentPath(file.getPath());
					docs.add(d);

				} catch (final IOException e) {
					LOGGER.error("Exception has been occurred in file byte to string conversions", e);
				}
			}
		}

		return docs;
	}

	/* File uploaded Added for Master */

	/**
	 * creating save Master for File Upload
	 *
	 * @param DocumentDetailsVO
	 * @param FileUploadDTO
	 * @return
	 */
	@Override
	@Transactional
	public boolean saveMasterFileToPath(List<DocumentDetailsVO> docdetailsList, FileUploadDTO fileUploadDTOs) {

		String directoryPath = null;
		Map<String, String> fileMaps = null;
		StringBuilder dmsDocIdStr = new StringBuilder() ;
		StringBuilder dmsDocNameStr = new StringBuilder() ;		
		boolean status = false;
		boolean isDMS = false;
		boolean isFolderExist = false;

		if (MainetConstants.Common_Constant.YES
				.equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
			isDMS = true;
		}

		final List<AttachDocs> saveMasterpathObjectList = new ArrayList<>();

		if ((docdetailsList != null) && !docdetailsList.isEmpty()) {

			for (final DocumentDetailsVO docdetailVO : docdetailsList) {
				String fileName = docdetailVO.getDocumentName();
				if (fileName != null) {
					if (fileName.contains(MainetConstants.operator.FORWARD_SLACE)) {
						fileName = fileName.replace(MainetConstants.operator.FORWARD_SLACE,
								MainetConstants.operator.UNDER_SCORE);
					}
					if (docdetailVO.getDoc_DESC_Mar() != null) {
						docdetailVO.setDoc_DESC_Mar(docdetailVO.getDoc_DESC_Mar());
					}
					fileUploadDTOs.setFileName(fileName);
					AttachDocs saveFilePath = null;

					if (!isDMS) {
						final boolean fileSavedSucessfully = convertAndSaveFile(docdetailVO,
								fileUploadDTOs.getFileNetPath(), fileUploadDTOs.getDirPath(), fileName);

						if (fileSavedSucessfully) {
							saveFilePath = doSetSaveMasterFilePathEntityObject(fileUploadDTOs, docdetailVO);
							if (saveFilePath != null) {
								saveMasterpathObjectList.add(saveFilePath);
							}
							
							//Doon_DMS specific code 
							if (fileUploadDTOs.getOrgId() != null) {
								Organisation org = iOrganisationDAO.getOrganisationById(fileUploadDTOs.getOrgId(),
										MainetConstants.STATUS.ACTIVE);
								LookUp lookUp = null;
								try {
									lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Dms.DOON_DMS,
											MainetConstants.ENV, org);
								} catch (Exception exception) {
									LOGGER.error("DOON_DMS prefix not found in Env");
								}
								try {
									if (lookUp != null && StringUtils.isNotBlank(lookUp.getOtherField())
											&& StringUtils.equals(lookUp.getOtherField(), MainetConstants.FlagY)) {
										doonDmsAPICall(fileName, fileUploadDTOs,docdetailVO,org);
									}
								} catch (Exception exception) {
									LOGGER.error("Exception occur while calling Doon DMS API integration" + exception);
								}
							}
						}
						
					} else {
						if (directoryPath == null) {
							directoryPath = fileUploadDTOs.getDirPath();
						}

						//#106603  By Arun
						fileMaps = dmsService.createDocument(directoryPath, docdetailVO.getDocumentByteCode(), fileName,
								isFolderExist,fileUploadDTOs,docdetailVO);
						if (fileMaps != null) {
							if(StringUtils.isNotEmpty(fileMaps.get(MainetConstants.Dms.ERROR_MSG))) {
								throw new FrameworkException(fileMaps.get(MainetConstants.Dms.ERROR_MSG));
							}											
							isFolderExist = true;
							saveFilePath = doSetSaveMasterFilePathEntityObject(fileUploadDTOs, docdetailVO);
							if (saveFilePath != null) {
								saveMasterpathObjectList.add(saveFilePath);
							}

							directoryPath = fileMaps.get("FOLDER_PATH");
							saveFilePath.setDmsFolderPath(fileMaps.get("FOLDER_PATH"));
							saveFilePath.setDmsDocId(fileMaps.get("DOC_ID"));
							saveFilePath.setDmsDocName(fileMaps.get("FILE_NAME"));
							dmsDocIdStr.append(saveFilePath.getDmsDocId()+MainetConstants.operator.COMMA);
							dmsDocNameStr.append(fileName+MainetConstants.operator.COMMA);						
							if ((saveFilePath.getDmsDocVersion() != null)
									&& !saveFilePath.getDmsDocVersion().isEmpty()) {
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
				status = fileUploadRepository.masterSaveFilePathToDB(saveMasterpathObjectList);

			}
			if(!dmsDocIdStr.toString().isEmpty() && !dmsDocNameStr.toString().isEmpty() && fileUploadDTOs.getDmsDocsDto()!=null) {
				dmsDocIdStr=dmsDocIdStr.deleteCharAt((dmsDocIdStr.length()-1));
				dmsDocNameStr=dmsDocNameStr.deleteCharAt((dmsDocNameStr.length()-1));
				fileUploadRepository.saveInMetadata(dmsDocIdStr.toString(), dmsDocNameStr.toString(), fileUploadDTOs.getDmsDocsDto());
			}
		}
		return status;
	}

	private AttachDocs doSetSaveMasterFilePathEntityObject(final FileUploadDTO fileUploadDTO,
			final DocumentDetailsVO docdetailVO) {
		final AttachDocs attachDocs = new AttachDocs();

		attachDocs.setAttId(docdetailVO.getAttachmentId());
		attachDocs.setAttDate(new Date());
		attachDocs.setLmodate(new Date());
		attachDocs.setAttPath(fileUploadDTO.getDirPath());
		attachDocs.setAttFname(fileUploadDTO.getFileName());
		attachDocs.setAttBy("");
		attachDocs.setIdfId(fileUploadDTO.getIdfId());
		attachDocs.setDept(fileUploadDTO.getDeptId());
		attachDocs.setOrgid(fileUploadDTO.getOrgId());
		attachDocs.setUserId(fileUploadDTO.getUserId());
		attachDocs.setStatus(fileUploadDTO.getStatus());
		attachDocs.setDmsDocName(docdetailVO.getDoc_DESC_ENGL());
		attachDocs.setDocDesc(docdetailVO.getDoc_DESC_ENGL());//Defect #109222
		if (docdetailVO.getDocumentSerialNo() != null) {
			attachDocs.setSerialNo(docdetailVO.getDocumentSerialNo().intValue());
		}
		return attachDocs;
	}

	/**
	 * common method to upload Master documents, use this method if file upload is
	 * required
	 *
	 * @param requestDTO
	 *            : pass this dto by setting its required field
	 * @param documentList
	 *            : pass document list
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
	 * @param identifier
	 *            Id
	 * @return
	 */
	private String masterDirStructure(final Long orgId, final String idfId, final String departmentName) {
		final StringBuilder builder = new StringBuilder();

		builder.append(orgId).append(File.separator).append(departmentName).append(File.separator)
				.append(Utility.dateToString(new Date(), MainetConstants.DATE_FORMAT_UPLOAD)).append(File.separator)
				.append(idfId);

		return builder.toString();
	}

	@Override
	public boolean doMasterFileUpload(List<DocumentDetailsVO> documentList, FileUploadDTO fileUploadDTO) {

		final String masterDirPath = masterDirStructure(fileUploadDTO.getOrgId(), fileUploadDTO.getIdfId(),
				fileUploadDTO.getDepartmentName());

		fileUploadDTO.setDirPath(masterDirPath);
		fileUploadDTO.setFileNetPath(filenetPath);

		return saveMasterFileToPath(documentList, fileUploadDTO);

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
						int index = file.getAbsolutePath().lastIndexOf(File.separator);
						String path = file.getAbsolutePath().substring(0, index);
						c.setUploadedDocumentPath(path);
						c.setDocumentName(file.getName());
						docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey() + 1))
								.forEach(doc -> {
									c.setDoc_DESC_ENGL(doc.getDoc_DESC_ENGL());
									c.setDoc_DESC_Mar(doc.getDoc_DESC_Mar());

								});
						docsument.add(c);
					} catch (final Exception e) {
						LOGGER.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}
		return docsument;

	}
	private void doonDmsAPICall(String fileName,FileUploadDTO fileUploadDTO,DocumentDetailsVO docdetailVO,Organisation org) {
		Long referenceId = null;
		if(StringUtils.isNotBlank(fileUploadDTO.getReferenceId())) {
			referenceId = Long.valueOf(fileUploadDTO.getReferenceId());
		}else {
			referenceId = fileUploadDTO.getApplicationId();
		}
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByServiceId(fileUploadDTO.getServiceId(), org.getOrgid());
		int lastIndex = fileName.lastIndexOf(".");
		String documentExtension = fileName.substring(lastIndex+1);
		String documentName = fileName.substring(0, lastIndex);
		DMSRequestDTO dto = new DMSRequestDTO();
		dto.setDocumentExtension(documentExtension);
		dto.setDocumentName(documentName);
		DocField docField1 = new DocField();
		docField1.setFieldName(MainetConstants.Dms.APPLICATION_ID);
		docField1.setFieldValue(String.valueOf(referenceId));
		DocField docField2 = new DocField();
		docField2.setFieldName(MainetConstants.Dms.SERVICE_NAME);
		docField2.setFieldValue(serviceMas.getSmServiceName());
		DocField docField3 = new DocField();
		docField3.setFieldName(MainetConstants.Dms.SUBMISSION_DATE);
		docField3.setFieldValue(Utility.dateToString(new Date(), MainetConstants.DATE_FORMATS));
		/*
		 * DocField docField4 = new DocField();
		 * docField4.setFieldName(MainetConstants.Dms.DEPT_NAME);
		 * docField4.setFieldValue(serviceMas.getTbDepartment().getDpDeptdesc());
		 */
		dto.getDocFields().add(docField1);
		dto.getDocFields().add(docField2);
		dto.getDocFields().add(docField3);
		dto.setParentFolderName(String.valueOf(referenceId));
		dto.setPathToRoot(dirStructureForDoon(fileUploadDTO, docdetailVO,org,serviceMas));
		
		try {
			String dmsDtoString = Utility.getMapper().writeValueAsString(dto);
			LOGGER.info("dmsDtoToString:- "+dmsDtoString);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			String inputXmlValue="<DMSAddDocument_Input>" + 
					"<documentName>"+dto.getDocumentName()+"</documentName>" + 
					"<documentExtension>"+dto.getDocumentExtension()+"</documentExtension>" + 
					"<parentFolderName>"+dto.getParentFolderName()+"</parentFolderName>" + 
					"<pathToRoot>"+dto.getPathToRoot()+"</pathToRoot>" + 
					"<dataDefName>"+serviceMas.getTbDepartment().getDpDeptcode()+"</dataDefName>" + 
					"<docFields>" + 
					"<docField><fieldName>"+docField1.getFieldName()+"</fieldName>" + 
					"<fieldValue>"+docField1.getFieldValue()+"</fieldValue></docField>" + 
					"<docField><fieldName>"+docField2.getFieldName()+"</fieldName>" + 
					"<fieldValue>"+docField2.getFieldValue()+"</fieldValue></docField>" +
					"<docField><fieldName>"+docField3.getFieldName()+"</fieldName>" + 
					"<fieldValue>"+docField3.getFieldValue()+"</fieldValue></docField>" +
					//"<docField><fieldName>"+docField4.getFieldName()+"</fieldName>" + 
					//"<fieldValue>"+docField4.getFieldValue()+"</fieldValue></docField>" +
					"</docFields>" + 
					"</DMSAddDocument_Input>";
			LOGGER.info("inputXmlValue :- "+inputXmlValue);
			//LOGGER.info("encoded file bytecode :- "+docdetailVO.getDocumentByteCode());
			LOGGER.info("File name  :- "+fileUploadDTO.getFileName());
			LOGGER.info("Document Byte Code :- "+docdetailVO.getDocumentByteCode() + "  getDocumentByteCode   "+fileUploadDTO.getDocumentByteCode());
	
			String filesName = fileUploadDTO.getFileNetPath()+fileUploadDTO.getDirPath()+"/"+fileUploadDTO.getFileName();
			upload_file(filesName, inputXmlValue);
			
		   // body.add("file", fbody);

			///body.add(MainetConstants.Dms.INPUTXML, inputXmlValue);
		
			//ResponseEntity<?> response = RestClient.callRestTemplateClientForMultipart(body, ServiceEndpoints.DOON_DMS_URL, HttpMethod.POST,headers);
			//LOGGER.info("Response from Doon DMS server "+response.getBody());
		}catch(Exception ex) {
			LOGGER.error("Exception occures while calling Doon dms API",ex);
			ex.printStackTrace();
		}
	}
	
	private String dirStructureForDoon(FileUploadDTO fileUploadDTO,DocumentDetailsVO docdetailVO,Organisation org,ServiceMaster serviceMas) {
		LOGGER.info("Start dirStructureForDoon in FileUploadService");
		
		final StringBuilder builder = new StringBuilder();
		builder.append(MainetConstants.Dms.DMS_ROOT_PATH).append(File.separator)
		       .append(org.getONlsOrgname()).append(File.separator)
			   .append(serviceMas.getTbDepartment().getDpDeptdesc()).append(File.separator)
			   .append(serviceMas.getSmServiceName()).append(File.separator);
		LOGGER.info(builder.toString());
		return builder.toString();
	}

	public static String upload_file(String filepath,String xmlFile) {

		
		try {
			// fis = new FileInputStream(inFile);
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

			// server back-end URL
			HttpPost httppost = new HttpPost(ServiceEndpoints.DOON_DMS_URL);
			MultipartEntity entity = new MultipartEntity();

			// set the file input stream and file name as arguments
			FileBody fileBody = new FileBody(new File(filepath));
			// entity.addPart("file", new InputStreamBody(fis, inFile.getName()));
			entity.addPart("file", fileBody);
			entity.addPart("inputXml", new StringBody(xmlFile));
			httppost.setEntity(entity);

			// execute the request
			HttpResponse response = httpclient.execute(httppost);
			System.out.println("Response: " + response);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String responseString = EntityUtils.toString(responseEntity, "UTF-8");

			System.out.println("[" + statusCode + "] " + responseString);
			LOGGER.info("Response from Doon DMS server "+responseString);
			return responseString;

		} catch (ClientProtocolException e) {
			System.err.println("Unable to make connection");
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			System.err.println("Unable to read file");
			e.printStackTrace();
			return "";
		}
	}
}
