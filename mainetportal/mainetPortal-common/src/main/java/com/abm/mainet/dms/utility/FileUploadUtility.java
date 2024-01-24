package com.abm.mainet.dms.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.FileNetPathService;
import com.abm.mainet.dms.service.FileUploadClass;
import com.abm.mainet.dms.ui.validator.FileUploadValidator;

/**
 * @author Vikrant.Thakur
 * @since 4 October 2014.
 * 
 * This class is Develop to call File Upload Purpose . All the File Upload Operation are Perform here. i.e, Add file , Delete FIle
 * and List of file after Server Side Validation
 * 
 * In this Class JsonViewObject is Used Every Where. JsonViewObject Parameters are. status = true-when you want to display data in
 * html, i.e,Div tag. False when you want to display data in message Box. message = Contaion list of file information which is
 * render for that file upload tag url = contaion the message which is read from Properties file. and Validation error message.
 * hiddenOtherVal = contain the div tag id information in which list of file get display.
 */
@Component
@Scope(value = "session")
public class FileUploadUtility implements Serializable {

    private static final String EXCEPTION_NO_MULTI_PATH_OBJECT_FOUND = "Exception no MultiPath Object Found";
    private static final Logger LOG = Logger.getLogger(FileUploadUtility.class);
    FileNetPathService utilityService = new FileNetPathService();

    /**
     * Use of variable Declare at Global Level.
     * 
     * 1. fileUploadSet = This Set Contain information of number of file upload tag present in current form. It contain all
     * information. i.e, all attribute Present in FileUploadClass class.
     * 
     * 2. fileMap = This Map Contain information of currently uploaded file. key contain current position of file Upload tag.
     * Value conation number of file uploaded in that position.
     * 
     * 3. fileUploadClass = Contain Current Request of that file Upload.
     * 
     * 4. existingFolderPath = Existing path in which file to be Upload.
     * 
     * 5. folderCreated = Use to Check that current folder Created or not.
     * 
     */

    private static final long serialVersionUID = -4013308807888768101L;

    private Set<FileUploadClass> fileUploadSet = new LinkedHashSet<>();

    private Map<Long, Set<File>> fileMap = new LinkedHashMap<>();

    // private FileUploadClass fileUploadClass = new FileUploadClass();

    private String existingFolderPath;

    private boolean folderCreated;

    @Autowired
    private IDocumentDetector iDocumentDetector;

    /**
     * 
     * This Method is call when you upload any file. the ajax-call Call this method. In which first Valdtion are get call. if all
     * validation pass then file is added to server cache. and information get added to fileMap.
     * @param request : it's a MultipartHttpServletRequest which contaion all Multipath File Information
     * @param fileCode : Current file upload Code Information
     * @return JsonViewObject Object which contaion Json Information
     */
    public JsonViewObject doFileUpload(final MultipartHttpServletRequest request, final String fileCode,
            final String browserType) {

        final JsonViewObject jsonViewObject = JsonViewObject.successResult();

        jsonViewObject.setStatus(true);

        MultipartFile multipartFile = null;

        final Iterator<String> iterator = request.getFileNames();

        /*final Long currentCount = FileUploadValidator.getFileIDFromCode(fileCode);

        if (browserType.equals("Other") || fileCode.equals("file_0")) {
            multipartFile = request.getFile(iterator.next());

        } else {

            if (browserType.equals("IE")) {

                String itr = null;
                while (iterator.hasNext()) {
                    itr = iterator.next();

                    if (itr.contains(currentCount + MainetConstants.BLANK)) {
                        multipartFile = request.getFile(itr);

                        break;
                    }
                }

            }
        }*/
        
        	// Added below code for U#90800
     		String fileName = null;
     		if(iterator != null) {
     			int size = 0;
     			while(iterator.hasNext()) {
     			    size++;
     			    fileName = iterator.next();
             }
     			if(size > 1) {
     				return doMultipleFileUpload(request, fileCode, browserType);
     			}
     		}

     		final Long currentCount = FileUploadValidator.getFileIDFromCode(fileCode);
     		if (browserType.equals("Other") || fileCode.equals("file_0")) {
     			multipartFile = request.getFile(fileName);
     		} else {
     			if (browserType.equals("IE")) {
     				if (fileName != null && fileName.contains(currentCount + MainetConstants.BLANK)) {
     					multipartFile = request.getFile(fileName);
     				}
     			}
     		}
     		// End of code added for U#90800

        if (multipartFile == null) {
            LOG.error(EXCEPTION_NO_MULTI_PATH_OBJECT_FOUND);
        }

        jsonViewObject.setHiddenOtherVal(MainetConstants.FileParameters.FILE_LIST_1 + currentCount);

        String responseString = MainetConstants.operator.EMPTY;

        FileUploadClass fileUploadClass = getCurrentFileUploadClassDetails(getFileUploadSet(), fileCode);

        try {
            boolean check = true;

            Set<File> fileDetails = new LinkedHashSet<>();

            // setFileUploadClass(getCurrentFileUploadClassDetails(getFileUploadSet(), fileCode));

            /**
             * Here the Map is Iterate to check validation Function.
             */

            for (final Map.Entry<Long, Set<File>> entry : getFileMap().entrySet()) {
                if (entry.getKey().toString().equals(currentCount.toString())) {
                    fileDetails = entry.getValue();

                    final boolean resultFileExis = FileUploadValidator.applyFileExistsValidation(fileDetails,
                            multipartFile.getOriginalFilename());
                    if (!resultFileExis) {
                        jsonViewObject.setStatus(false);
                        jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
                        jsonViewObject.setUrl(FileUploadValidator
                                .getFieldLabel(MainetConstants.ValidationMessageCode.FILE_NAME_EXISTS));
                        return jsonViewObject;
                    }

                    final Field field = MainetConstants.MAX_FILEUPLOAD_COUNT.class
                            .getField(fileUploadClass.getMaxFileCount());
                    final int maxFileCount = (int) field.get(MainetConstants.MAX_FILEUPLOAD_COUNT.class);

                    final boolean resultFileMaxCont = FileUploadValidator.applyFileMaxCountValidation(fileDetails,
                            maxFileCount);
                    if (!resultFileMaxCont) {
                        jsonViewObject.setStatus(false);
                        jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
                        jsonViewObject.setUrl(
                                FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.MAX_FILE_ERROR,
                                        maxFileCount + MainetConstants.operator.EMPTY));
                        return jsonViewObject;
                    }

                    check = false;
                }
            }
            
          //D#130573 for checklist duplicate file validation
            for (final Map.Entry<Long, Set<File>> entry : getFileMap().entrySet()) {
                    final boolean resultFileExis = FileUploadValidator.applyFileExistsValidation(entry.getValue(),
                            multipartFile.getOriginalFilename());
                    if (!resultFileExis) {
                        jsonViewObject.setStatus(false);
                        jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
                        jsonViewObject.setUrl(FileUploadValidator
                                .getFieldLabel(MainetConstants.ValidationMessageCode.FILE_NAME_EXISTS));
                        return jsonViewObject;
                    }
            }
            
            if (check) {

                if (!isFolderCreated()) {
                    final String newPath = Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                            + MainetConstants.FILE_PATH_SEPARATOR + Utility.getGUIDNumber()
                            + MainetConstants.FILE_PATH_SEPARATOR;

                    setExistingFolderPath(newPath);

                    Utility.createDirectory(newPath);
                }

                setFolderCreated(true);
            }

            final boolean resultFileExt = FileUploadValidator.applyFileExtensionValidation(
                    multipartFile.getOriginalFilename(), fileUploadClass.getValidnfn());
            if (!resultFileExt) {
                jsonViewObject.setStatus(false);
                jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));

                final Field field = MainetConstants.Validation_Constant.class.getField(fileUploadClass.getValidnfn());

                final String[] fieldValue = (String[]) field.get(MainetConstants.Validation_Constant.class);

                jsonViewObject.setUrl(FileUploadValidator.getFieldLabel(
                        MainetConstants.ValidationMessageCode.EXTENSION_VALIDN, Arrays.toString(fieldValue)));

                return jsonViewObject;

            }

            
            int fileSize = 0;
			Set<File> existingFileSet = new LinkedHashSet<>();
			for (final Map.Entry<Long, Set<File>> entry : getFileMap().entrySet()) {
				if (entry.getKey().toString().equals(currentCount.toString())) {
					existingFileSet = entry.getValue();
					final Iterator<File> itr = existingFileSet.iterator();

			        while (itr.hasNext()) {
			            final File file = itr.next();
			            fileSize = fileSize + Long.valueOf(file.length()).intValue();
			        }
				}
			}

			//D#123049
            final int maxFileSize;
            if(StringUtils.isNotBlank(fileUploadClass.getFileSize())) {
            	final Field field = MainetConstants.CheckList_Size.class.getField(fileUploadClass.getFileSize());
                maxFileSize = (int) field.get(MainetConstants.CheckList_Size.class);
            }else {
                maxFileSize=Integer.parseInt( fileUploadClass.getChecklistDocSize());
            }

            final boolean resultFileSize = FileUploadValidator.applyFileSizeValidation(fileSize + multipartFile.getBytes().length,
                    maxFileSize);
            
            
            
            if (!resultFileSize) {
                jsonViewObject.setStatus(false);
                jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));

                final DecimalFormat twoDForm = new DecimalFormat(MainetConstants.COMMON_DECIMAL_FORMAT);

                final Double size = (maxFileSize / 1024.0) / 1024.0;		// convert bytes to MB.

                jsonViewObject.setUrl(FileUploadValidator
                        .getFieldLabel(MainetConstants.ValidationMessageCode.FILE_SIZE_VALIDN, twoDForm.format(size)));
                return jsonViewObject;
            }

            final File file = getFolderPath(multipartFile.getOriginalFilename(), fileUploadClass);
            //boolean isSafe = isFileSafe(multipartFile, file);
            boolean isSafe = true;
            if (!isSafe) {
                jsonViewObject.setStatus(false);
                jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
                jsonViewObject
                        .setUrl(FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.INVALID_FILE_ERROR));
                return jsonViewObject;
            }
            fileDetails.add(file);

            getFileMap().put(currentCount, fileDetails);

            final boolean uploadResult = uploadFileToServerCache(file, multipartFile);

            if (!uploadResult) {

                LOG.error("Fail To Upload FIle ");

                jsonViewObject.setStatus(false);

                jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
                jsonViewObject
                        .setUrl(FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.SERVER_ERROR));

                getFileMap().clear();

                return jsonViewObject;
            }
            responseString = generateOutputString(currentCount, fileUploadClass);

            jsonViewObject.setMessage(responseString);
        }

        catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);

            jsonViewObject.setStatus(false);
            jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
            jsonViewObject
                    .setUrl(FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.SERVER_ERROR));

            getFileMap().clear();

            return jsonViewObject;
        }

        return jsonViewObject;
    }
    
    // Added below method for U#90800

  	/**
  	 *
  	 * This method is called when you upload multiple files. The ajax
  	 * request calls this method. In this, first validations are checked. If all the
  	 * validations pass then file gets added to the server cache and Information
  	 * gets added to the fileMap.
  	 *
  	 * @param request     : it's a MultipartHttpServletRequest which contains all
  	 *                    the Information about multipart file request
  	 * @param fileCode    : Current file upload code Information
  	 * @param browserType : Type of browser
  	 * @return JsonViewObject Object which contains Json Information
  	 */
  	public JsonViewObject doMultipleFileUpload(MultipartHttpServletRequest request, String fileCode,
  			String browserType) {

  		final JsonViewObject jsonViewObject = JsonViewObject.successResult();

  		jsonViewObject.setStatus(true);

  		MultipartFile multipartFile = null;

  		final Iterator<String> iterator = request.getFileNames();

  		final Long currentCount = FileUploadValidator.getFileIDFromCode(fileCode);

  		Map<String, MultipartFile> multipartFileMap = new LinkedHashMap<>();

  		if (browserType.equals("Other") || fileCode.equals("file_0")) {
  			multipartFileMap = request.getFileMap();
  		} else {
  			if (browserType.equals("IE")) {

  				String itr = null;
  				while (iterator.hasNext()) {
  					itr = iterator.next();

  					if (itr.contains(currentCount + MainetConstants.BLANK)) {
  						multipartFile = request.getFile(itr);
  						multipartFileMap.put(itr, multipartFile);
  					}
  				}

  			}
  		}

  		int requestFileCount = multipartFileMap.size();
  		if (multipartFileMap.isEmpty()) {
  			LOG.error(EXCEPTION_NO_MULTI_PATH_OBJECT_FOUND);
  		}

  		jsonViewObject.setHiddenOtherVal(MainetConstants.FileParameters.FILE_LIST_1 + currentCount);

  		String responseString = MainetConstants.operator.EMPTY;

  		FileUploadClass fileUploadClass = getCurrentFileUploadClassDetails(getFileUploadSet(), fileCode);

  		try {

  			boolean check = true;

  			Set<File> fileDetails = new LinkedHashSet<>();

  			/**
  			 * Here the Map is Iterate to check validation Function.
  			 */
  			
  			// If map is empty or map.get(currenCount) is empty, check if uploaded files within max limit 			
  			if (getFileMap().isEmpty() || getFileMap().get(currentCount) == null) {
  				final Field field = MainetConstants.MAX_FILEUPLOAD_COUNT.class
 							.getField(fileUploadClass.getMaxFileCount());
 				int maxFileCount = (int) field.get(MainetConstants.MAX_FILEUPLOAD_COUNT.class);
 				final boolean resultFileMaxCont = FileUploadValidator
 						.applyMultipleFileMaxCountValidation(fileDetails, requestFileCount, maxFileCount);
 				if (!resultFileMaxCont) {
 					jsonViewObject.setStatus(false);
 					jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
 					jsonViewObject.setUrl(
 							FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.MAX_FILE_ERROR,
 									maxFileCount + MainetConstants.operator.EMPTY));
 					return jsonViewObject;
 				}
  			}

  			for (final Map.Entry<Long, Set<File>> entry : getFileMap().entrySet()) {
  				if (entry.getKey().toString().equals(currentCount.toString())) {
  					fileDetails = entry.getValue();

  					for (Map.Entry<String, MultipartFile> fileEntry : multipartFileMap.entrySet()) {
  						multipartFile = fileEntry.getValue();
  						final boolean resultFileExis = FileUploadValidator.applyFileExistsValidation(fileDetails,
  								multipartFile.getOriginalFilename());
  						if (!resultFileExis) {
  							jsonViewObject.setStatus(false);
  							jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
  							jsonViewObject.setUrl(FileUploadValidator
  									.getFieldLabel(MainetConstants.ValidationMessageCode.FILE_NAME_EXISTS));
  							return jsonViewObject;
  						}
  					}

  					final Field field = MainetConstants.MAX_FILEUPLOAD_COUNT.class
  							.getField(fileUploadClass.getMaxFileCount());
  					int maxFileCount = (int) field.get(MainetConstants.MAX_FILEUPLOAD_COUNT.class);

  					final boolean resultFileMaxCont = FileUploadValidator
  							.applyMultipleFileMaxCountValidation(fileDetails, requestFileCount, maxFileCount);
  					if (!resultFileMaxCont) {
  						jsonViewObject.setStatus(false);
  						jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
  						jsonViewObject.setUrl(
  								FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.MAX_FILE_ERROR,
  										maxFileCount + MainetConstants.operator.EMPTY));
  						return jsonViewObject;
  					}

  					check = false;
  				}
  			}
  			if (check) {

  				if (!isFolderCreated()) {
  					final String newPath = Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
  							+ MainetConstants.FILE_PATH_SEPARATOR + Utility.getGUIDNumber()
  							+ MainetConstants.FILE_PATH_SEPARATOR;

  					setExistingFolderPath(newPath);

  					Utility.createDirectory(newPath);
  				}

  				setFolderCreated(true);
  			}
  			
  			int fileSize = 0;
 			Set<File> existingFileSet = new LinkedHashSet<>();
 			for (final Map.Entry<Long, Set<File>> entry : getFileMap().entrySet()) {
 				if (entry.getKey().toString().equals(currentCount.toString())) {
 					existingFileSet = entry.getValue();
 					final Iterator<File> itr = existingFileSet.iterator();

 			        while (itr.hasNext()) {
 			            final File file = itr.next();
 			            fileSize = fileSize + Long.valueOf(file.length()).intValue();
 			        }
 				}
 			}
 			
 			for (Map.Entry<String, MultipartFile> fileEntry : multipartFileMap.entrySet()) {
 				multipartFile = fileEntry.getValue();
 				fileSize = fileSize + multipartFile.getBytes().length;
 			}
 			
 			final Field maxField = MainetConstants.CheckList_Size.class.getField(fileUploadClass.getFileSize());
 			final int maxFileSize = (int) maxField.get(MainetConstants.CheckList_Size.class);
 			
 			final boolean resultFileSize = FileUploadValidator
 					.applyFileSizeValidation(fileSize, maxFileSize);
 			if (!resultFileSize) {
 				jsonViewObject.setStatus(false);
 				jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));

 				final DecimalFormat twoDForm = new DecimalFormat(MainetConstants.COMMON_DECIMAL_FORMAT);

 				final Double size = (maxFileSize / 1024.0) / 1024.0; // convert bytes to MB.

 				jsonViewObject.setUrl(FileUploadValidator.getFieldLabel(
 						MainetConstants.ValidationMessageCode.FILE_SIZE_VALIDN, twoDForm.format(size)));
 				return jsonViewObject;
 			}

  			for (Map.Entry<String, MultipartFile> fileEntry : multipartFileMap.entrySet()) {
  				multipartFile = fileEntry.getValue();
  				final boolean resultFileExt = FileUploadValidator.applyFileExtensionValidation(
  						multipartFile.getOriginalFilename(), fileUploadClass.getValidnfn());
  				if (!resultFileExt) {
  					jsonViewObject.setStatus(false);
  					jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));

  					final Field field = MainetConstants.Validation_Constant.class
  							.getField(fileUploadClass.getValidnfn());

  					final String[] fieldValue = (String[]) field.get(MainetConstants.Validation_Constant.class);

  					jsonViewObject.setUrl(FileUploadValidator.getFieldLabel(
  							MainetConstants.ValidationMessageCode.EXTENSION_VALIDN, Arrays.toString(fieldValue)));

  					return jsonViewObject;

  				}

  				final File file = getFolderPath(multipartFile.getOriginalFilename(), fileUploadClass);
  				//boolean isSafe = isFileSafe(multipartFile, file);
  	            boolean isSafe = true;
  				if (!isSafe) {
  					jsonViewObject.setStatus(false);
  					jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
  					jsonViewObject.setUrl(FileUploadValidator
  							.getFieldLabel(MainetConstants.ValidationMessageCode.INVALID_FILE_ERROR));
  					return jsonViewObject;
  				}
  			}

  			for (Map.Entry<String, MultipartFile> validFile : multipartFileMap.entrySet()) {
  				multipartFile = validFile.getValue();
  				final File file = getFolderPath(multipartFile.getOriginalFilename(), fileUploadClass);
  				fileDetails.add(file);

  				getFileMap().put(currentCount, fileDetails);

  				final boolean uploadResult = uploadFileToServerCache(file, multipartFile);

  				if (!uploadResult) {

  					LOG.error("Fail To Upload FIle ");

  					jsonViewObject.setStatus(false);

  					jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
  					jsonViewObject.setUrl(
  							FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.SERVER_ERROR));

  					getFileMap().clear();

  					return jsonViewObject;
  				}
  			}
  			responseString = generateOutputString(currentCount, fileUploadClass);

  			jsonViewObject.setMessage(responseString);
  		}

  		catch (final Exception e) {
  			LOG.error(MainetConstants.ERROR_OCCURED, e);

  			jsonViewObject.setStatus(false);
  			jsonViewObject.setMessage(generateOutputString(currentCount, fileUploadClass));
  			jsonViewObject
  					.setUrl(FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.SERVER_ERROR));

  			getFileMap().clear();

  			return jsonViewObject;
  		}

  		return jsonViewObject;
  	}

    /*
     * this method will check whether uploaded file is safe or not
     */
    private boolean isFileSafe(MultipartFile multipartFile, final File file) {
        boolean isSafe = true;
        if (MainetConstants.FileContentType.FILE_PDF.equals(multipartFile.getContentType())) {
            isSafe = iDocumentDetector.isPdfSafe(multipartFile);

        } else if (MainetConstants.FileContentType.FILE_DOC_XLS.equals(multipartFile.getContentType())) {
            isSafe = iDocumentDetector.isWordDocumentSafe(file, multipartFile);

        } else if (MainetConstants.FileContentType.FILE_JPEG.equals(multipartFile.getContentType())
                || MainetConstants.FileContentType.FILE_PNG.equals(multipartFile.getContentType()) ||
                MainetConstants.FileContentType.FILE_BMP.equals(multipartFile.getContentType())
                || MainetConstants.FileContentType.FILE_GIF.equals(multipartFile.getContentType())) {
            isSafe = iDocumentDetector.isImageSafe(file, multipartFile);
        }
        return isSafe;
    }

    /**
     * This Method is call when you delete any file from file Upload Tag.
     * @param delCode : Contaion code of that file which is to be delete
     * @return JsonViewObject Object which contaion Json Information
     */
    public JsonViewObject deleteFile(final String delCode) {

        /**** FOR tESTING **/

        for (final Map.Entry<Long, Set<File>> entry : getFileMap().entrySet()) {
            for (final File file : entry.getValue()) {
                LOG.error("for Position : " + entry.getKey() + " File are  " + file.getName());
            }
        }

        /*** enD **/

        final JsonViewObject jsonViewObject = JsonViewObject.successResult();

        try {

            final Long value = Long
                    .valueOf(FileUploadValidator.getPrefixSuffixFromString(delCode, MainetConstants.operator.UNDER_SCORE,
                            MainetConstants.OperationMode.SUFFIX, MainetConstants.OperationMode.END));

            final Long delValue = Long
                    .valueOf(FileUploadValidator.getPrefixSuffixFromString(delCode, MainetConstants.operator.UNDER_SCORE,
                            MainetConstants.OperationMode.PREFIX, MainetConstants.OperationMode.START));

            /*
             * setFileUploadClass(getCurrentFileUploadClassDetails(getFileUploadSet(), MainetConstants.FileParameters.FILE_LIST_3
             * + value));
             */

            FileUploadClass fileUploadClass = getCurrentFileUploadClassDetails(getFileUploadSet(),
                    MainetConstants.FileParameters.FILE_LIST_3 + value);

            final Iterator<File> fileItr = getFileMap().get(value).iterator();

            Long currentCount = 0l;

            while (fileItr.hasNext()) {
                final File file = fileItr.next();

                if (currentCount == delValue) {
                    file.delete();
                    fileItr.remove();

                    if (getFileMap().get(value).size() == 0) {
                        getFileMap().remove(value);
                    }

                }
                currentCount++;
            }

            jsonViewObject.setHiddenOtherVal(MainetConstants.FileParameters.FILE_LIST_1 + value);

            jsonViewObject.setMessage(generateOutputString(value, fileUploadClass));

            jsonViewObject.setStatus(true);

            return jsonViewObject;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            jsonViewObject.setStatus(false);

            jsonViewObject.setMessage(FileUploadValidator.getFieldLabel(MainetConstants.ValidationMessageCode.SERVER_ERROR));

        }

        return jsonViewObject;
    }

    /**
     * 
     * @param tempFile : file which is upload on server cache.
     * @param multipartFile : MultipartFile object used to transfer file from client side to server side cache.
     * @return true-When file get successfully pass, false-When some error occur.
     */
    private boolean uploadFileToServerCache(final File tempFile, final MultipartFile multipartFile) {
        try {
            multipartFile.transferTo(tempFile);

            return true;

        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);

            return false;
        }
    }

    /**
     * This method is used to generated String of OutPut File to be. in html Format i.e,
     * <ul>
     * </li> format.
     * 
     * @param currentCount : Current Count of that file. of which output string to be generated
     * @return String which contaion current file Upload file list.
     * 
     */
    private String generateOutputString(final Long currentCount, final FileUploadClass fileUploadClass) {

        if (!((getFileMap().size() == 0) || (getFileMap().get(currentCount) == null))) {
            int currentFileCount = 0;
            String content = MainetConstants.BLANK;
            if (UserSession.getCurrent().getBrowserType().equals("Other")) {

                content = MainetConstants.HTML_CHARACTER.UL_START;

                if (!fileUploadClass.isAllowClassicStyle()) {
 					// D#129070 point 4 start
                	if(getFileMap().get(currentCount).size()>1) {
                		content += MainetConstants.HTML_CHARACTER.LI_START + MainetConstants.HTML_CHARACTER.BOLD_START
                                + getFileMap().get(currentCount).size() +" "
                                + ApplicationSession.getInstance().getMessage("file.upload.validation.msg")
                                + MainetConstants.HTML_CHARACTER.BOLD_END + MainetConstants.HTML_CHARACTER.LI_END;	
                	}else {
                		content += MainetConstants.HTML_CHARACTER.LI_START + MainetConstants.HTML_CHARACTER.BOLD_START
                                + ApplicationSession.getInstance().getMessage("file.single.upload.validation.msg")
                                + MainetConstants.HTML_CHARACTER.BOLD_END + MainetConstants.HTML_CHARACTER.LI_END;
                	}
					// D#129070 point 4 end
                    
                }

                for (final File fileListName : getFileMap().get(currentCount)) {

                    final String id = currentFileCount + MainetConstants.FileParameters.FILE_LIST_2 + currentCount;

                    if (fileUploadClass.isRemoveDeleteOption()) {
                        content = content + MainetConstants.HTML_CHARACTER.LI_START
                                + generateValidString(fileListName.getName()) + MainetConstants.HTML_CHARACTER.LI_END;
                    } else {
                        content = content + MainetConstants.HTML_CHARACTER.LI_START
                                + generateValidString(fileListName.getName()) + MainetConstants.HTML_CHARACTER.NBSP
                                + MainetConstants.HTML_CHARACTER.IMAGE_TAG_1 + id
                                + MainetConstants.HTML_CHARACTER.IMAGE_TAG_2 + MainetConstants.HTML_CHARACTER.LI_END;

                    }
                    currentFileCount++;

                }

                content = content + MainetConstants.HTML_CHARACTER.UL_END;
            } else {

                if (!fileUploadClass.isAllowClassicStyle()) {
                	// D#129070 point 4 start
                	if(getFileMap().get(currentCount).size()>1) {
                		content += getFileMap().get(currentCount).size() + " "+  ApplicationSession.getInstance().getMessage("file.upload.validation.msg");
                	}else {
                		content += ApplicationSession.getInstance().getMessage("file.single.upload.validation.msg");	
                	}
					// D#129070 point 4 end
                	
                    
                }

                for (final File fileListName : getFileMap().get(currentCount)) {

                    final String id = currentFileCount + MainetConstants.FileParameters.FILE_LIST_2 + currentCount;
                    content += MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK;
                    if (fileUploadClass.isRemoveDeleteOption()) {
                        content = content + generateValidString(fileListName.getName())
                                + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK;
                    } else {
                        content = content + generateValidString(fileListName.getName())
                                + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK + id;

                    }
                    currentFileCount++;

                }

            }
            return content;
        } else {
            return MainetConstants.operator.EMPTY;
        }
    }

    /**
     * This Method is Call When Serverside Error Occur. to regenerate file Upload list. of all file upload tag
     * @return List<JsonViewObject> = return list of json object which contain all file upload list information.
     */
    public List<JsonViewObject> getFileUploadList() {
        final List<JsonViewObject> jsonViewObjectsList = new ArrayList<>();

        Set<FileUploadClass> fileSet = getFileUploadSet();
        // for (Long count = 0l; count < fileSet.size(); count++) {
        // Long count = 0l;
        JsonViewObject jsonViewObject;
        Long fileId;
        String result;
        for (FileUploadClass fileUploadClass : fileSet) {
            jsonViewObject = JsonViewObject.successResult();
            fileId = FileUploadValidator.getFileIDFromCode(fileUploadClass.getCode());
            result = generateOutputString(fileId, fileUploadClass);

            jsonViewObject.setHiddenOtherVal(MainetConstants.FileParameters.FILE_LIST_1 + fileId);

            jsonViewObject.setMessage(result);

            jsonViewObjectsList.add(jsonViewObject);

        }

        return jsonViewObjectsList;
    }

    /**
     * This method is used to generate Short String, String(File Name) length is decide form
     * FileUploadConstant.MAX_FILE_CHARACTER_TO_BE_DISPLAY constant. if length exceed. the file name is shown by (...) Seperated
     * by file extension.
     * 
     * @param fileName : Name of file.
     * @return Short name of that file.
     * 
     */
    public String generateValidString(final String fileName) {

        final int maxLength = MainetConstants.MAX_FILE_CHARACTER_TO_BE_DISPLAY;

        if (fileName.length() > maxLength) {
            String name = FileUploadValidator.getPrefixSuffixFromString(fileName, MainetConstants.operator.DOT,
                    MainetConstants.OperationMode.PREFIX, MainetConstants.OperationMode.END);

            final String fileExtn = FileUploadValidator.getPrefixSuffixFromString(fileName, MainetConstants.operator.DOT,
                    MainetConstants.OperationMode.SUFFIX, MainetConstants.OperationMode.END);

            final int subString = maxLength - fileExtn.length();

            name = name.substring(0, subString);

            name = name + MainetConstants.operator.DOT + MainetConstants.operator.DOT + MainetConstants.operator.DOT;

            return name + fileExtn;
        } else {
            return fileName;
        }
    }

    /**
     * While deleting or adding file we need to recoginse from which file upload tag that request come. This method will give us
     * Current FileUploadClass Information form that Set<FileUploadClass>, from that tag Code.
     * 
     * @param fileUploadSet : Contaion all tag information
     * @param fileCode : Contain Current File code information
     * @return FileUploadClass which current current file Information.
     */
    private FileUploadClass getCurrentFileUploadClassDetails(final Set<FileUploadClass> fileUploadSet, final String fileCode) {

        for (final FileUploadClass fileUploadClass : fileUploadSet) {
            if (fileUploadClass.getCode().equals(fileCode)) {

                return fileUploadClass;
            }
        }
        return null;
    }

    /**
     * This Method give that folder Name. if folder name is give. else all the file will store in current.
     * 
     * @param fileName - current fileName
     * @return File class which contaion file information.
     * 
     */
    private File getFolderPath(final String fileName, final FileUploadClass fileUploadClass) {
        String path = getExistingFolderPath();

        if (fileUploadClass.getFolderName().equals(MainetConstants.operator.EMPTY)) {
            path = path + fileName;
        } else {
            final File filePath = new File(path + fileUploadClass.getFolderName());

            filePath.mkdir();
            String rfileName = fileName.replaceAll(" ", "-");
            path = path + fileUploadClass.getFolderName() + MainetConstants.WINDOWS_SLASH + rfileName;
        }

        final File filePath = new File(path);
        
        return filePath;
    }

    /**
     * Create from FileUploadUtility bean.
     * @return
     */
    public static FileUploadUtility getCurrent() {
        return ApplicationContextProvider.getApplicationContext().getBean(FileUploadUtility.class);
    }

    public Set<FileUploadClass> getFileUploadSet() {
        return fileUploadSet;
    }

    public void setFileUploadSet(final Set<FileUploadClass> fileUploadSet) {
        this.fileUploadSet = fileUploadSet;
    }

    public Map<Long, Set<File>> getFileMap() {
        return fileMap;
    }

    public void setFileMap(final Map<Long, Set<File>> fileMap) {
        this.fileMap = fileMap;
    }

    public String getExistingFolderPath() {
        return existingFolderPath;
    }

    public void setExistingFolderPath(final String existingFolderPath) {
        this.existingFolderPath = existingFolderPath;
    }

    public boolean isFolderCreated() {
        return folderCreated;
    }

    public void setFolderCreated(final boolean folderCreated) {
        this.folderCreated = folderCreated;
    }

    /*
     * public FileUploadClass getFileUploadClass() { return fileUploadClass; }
     */

    /*
     * public void setFileUploadClass(final FileUploadClass fileUploadClass) { this.fileUploadClass = fileUploadClass; }
     */

    /**
     * This Method is Call When Serverside Error Occur. to regenerate file Upload list. of all file upload tag
     *
     * @return List<JsonViewObject> = return list of json object which contain all file upload list information.
     */
    public List<JsonViewObject> getFileUploadListWithUniqueId(final Map<Long, String> fileNames) {
        final List<JsonViewObject> jsonViewObjectsList = new ArrayList<>();
        Set<FileUploadClass> fileSet = getFileUploadSet();
        // for (Long count = 0l; count < fileSet.size(); count++) {
        Long count = 0l;
        for (FileUploadClass fileUploadClass : fileSet) {
            final JsonViewObject jsonViewObject = JsonViewObject.successResult();

            final String result = generateOutputStringWithUniqueId(count, fileNames, fileUploadClass);

            jsonViewObject.setHiddenOtherVal(MainetConstants.FileParameters.FILE_LIST_1 + count);

            jsonViewObject.setMessage(result);

            jsonViewObjectsList.add(jsonViewObject);
            count++;
        }

        return jsonViewObjectsList;
    }

    /**
     * This method is used to generated String of OutPut File to be. in html Format i.e,
     * <ul>
     * </li> format.
     *
     * @param currentCount : Current Count of that file. of which output string to be generated
     * @return String which contaion current file Upload file list.
     *
     */
    private String generateOutputStringWithUniqueId(final Long currentCount, final Map<Long, String> fileNames,
            final FileUploadClass fileUploadClass) {

        if (!((getFileMap().size() == 0) || (getFileMap().get(currentCount) == null))) {
            int currentFileCount = 0;
            String content = MainetConstants.BLANK;
            if (UserSession.getCurrent().getBrowserType().equals("Other")) {

                content = MainetConstants.HTML_CHARACTER.UL_START;

                if (!fileUploadClass.isAllowClassicStyle()) {
                	// D#129070 point 4 start
                	if(getFileMap().get(currentCount).size()>1) {
                		content += MainetConstants.HTML_CHARACTER.LI_START + MainetConstants.HTML_CHARACTER.BOLD_START
                                + getFileMap().get(currentCount).size() +" " + ApplicationSession.getInstance().getMessage("file.upload.validation.msg")
                                + MainetConstants.HTML_CHARACTER.BOLD_END + MainetConstants.HTML_CHARACTER.LI_END;	
                	}else {
                		content += MainetConstants.HTML_CHARACTER.LI_START + MainetConstants.HTML_CHARACTER.BOLD_START
                                + ApplicationSession.getInstance().getMessage("file.single.upload.validation.msg")
                                + MainetConstants.HTML_CHARACTER.BOLD_END + MainetConstants.HTML_CHARACTER.LI_END;
                	}
                	// D#129070 point 4 end
                    
                }

                for (final File fileListName : getFileMap().get(currentCount)) {

                    final String id = currentFileCount + MainetConstants.FileParameters.FILE_LIST_2 + currentCount;

                    if (fileUploadClass.isRemoveDeleteOption()) {
                        content = content + MainetConstants.HTML_CHARACTER.LI_START
                                + generateValidString(fileListName.getName()) + MainetConstants.HTML_CHARACTER.LI_END;
                    } else {

                        final Set<Entry<Long, String>> entries = fileNames.entrySet();
                        for (final Entry<Long, String> entry : entries) {
                            if (entry.getValue().equals(fileListName.getName())) {
                                content = content + MainetConstants.HTML_CHARACTER.LI_START
                                        + generateValidString(fileListName.getName())
                                        + MainetConstants.HTML_CHARACTER.NBSP
                                        + MainetConstants.HTML_CHARACTER.IMAGE_TAG_1 + id
                                        + MainetConstants.HTML_CHARACTER.IMAGE_TAG_3 + entry.getKey()
                                        + MainetConstants.HTML_CHARACTER.IMAGE_TAG_2;
                                content = content + "<input type='hidden' value='" + entry.getKey() + "' >"
                                        + MainetConstants.HTML_CHARACTER.LI_END;
                            }
                        }

                    }
                    currentFileCount++;

                }

                content = content + MainetConstants.HTML_CHARACTER.UL_END;
            } else {

                if (!fileUploadClass.isAllowClassicStyle()) {
                	// D#129070 point 4 start
                	if(getFileMap().get(currentCount).size()>1) {
                		content += getFileMap().get(currentCount).size() + " "+ ApplicationSession.getInstance().getMessage("file.upload.validation.msg");
                	}else {
                		content += ApplicationSession.getInstance().getMessage("file.single.upload.validation.msg");	
                	}
                    // D#129070 point 4 end
                }

                for (final File fileListName : getFileMap().get(currentCount)) {

                    final String id = currentFileCount + MainetConstants.FileParameters.FILE_LIST_2 + currentCount;
                    content += MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK;
                    ;
                    if (fileUploadClass.isRemoveDeleteOption()) {
                        content = content + generateValidString(fileListName.getName())
                                + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK;
                    } else {

                        final Set<Entry<Long, String>> entries = fileNames.entrySet();
                        for (final Entry<Long, String> entry : entries) {
                            if (entry.getValue().equals(fileListName.getName())) {
                                content = content + generateValidString(fileListName.getName())
                                        + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK + id
                                        + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK
                                        + entry.getKey();
                            }
                        }

                    }
                    currentFileCount++;

                }

            }
            return content;
        } else {
            return MainetConstants.operator.EMPTY;
        }
    }

    public String uploadFile(final MultipartFile file, final String dirTree) {

        final String folderTree = utilityService.getDirectoryTree(dirTree);

        String readFileFrom = MainetConstants.BLANK;
        readFileFrom = folderTree + file.getOriginalFilename();
        final String uploadFileAt = utilityService.getRootDirectory() + readFileFrom;

        final File uploadFile = new File(uploadFileAt);
        try {
            FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
        } catch (final IOException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return readFileFrom;
    }

    public String uploadFile(final File file, final String dirTree) {

        String uploadFileAt = null;
        String readFileFrom = MainetConstants.BLANK;
        LOG.info(" dirTree Path Inside uploadFile of FileUploadUtility Class :"+dirTree);        
        if ((dirTree != null) && !dirTree.contains(MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER)) {
            final String folderTree = utilityService.getDirectoryTree(dirTree);

            readFileFrom = folderTree + file.getName();
            uploadFileAt = utilityService.getRootDirectory() + folderTree;
            LOG.info("Filenet Upload File From : "+readFileFrom);
            LOG.info("Filenet Upload File To : "+uploadFileAt);
        }

        else {
            readFileFrom = utilityService.getDirectoryTree(dirTree) + file.getName();
            uploadFileAt = utilityService.getDirectoryTree(dirTree);
            LOG.info("cache Upload File From : "+readFileFrom);
            LOG.info("cache Upload File To : "+uploadFileAt);
        }
        try {
            FileUtils.copyFileToDirectory(file, new File(uploadFileAt));
        } catch (final IOException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return readFileFrom;
    }

    public byte[] downloadFile(final String fileName, final String dirTree) throws FileNotFoundException {

        FileInputStream inputStream = null;
        try {

            final String folderTree = utilityService.getDirectoryTree(dirTree);

            // get absolute path of the application
            final String appPath = utilityService.getRootDirectory();

            // construct the complete absolute path of the file
            final String fullPath = appPath + folderTree + fileName;
            final File downloadFile = new File(fullPath);

            if (downloadFile.exists()) {
                inputStream = new FileInputStream(downloadFile);

                final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                final byte[] buf = new byte[1024];
                try {
                    for (int readNum; (readNum = inputStream.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum); // no doubt here is 0
                    }
                } catch (final IOException ex) {
                    LOG.error(MainetConstants.ERROR_OCCURED, ex);
                }
                return bos.toByteArray();

            } else {
            	LOG.error("File not found at path :"+fullPath);
                throw new FileNotFoundException("File Does Not Exist.");
            }
        } catch (final FileNotFoundException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    LOG.error(MainetConstants.ERROR_OCCURED, e);
                }
            }
        }
    }

}
