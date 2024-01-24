/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dao.FarmerMasterDao;
import com.abm.mainet.sfac.domain.FarmerMasterEntity;
import com.abm.mainet.sfac.dto.FarmerMasterDto;
import com.abm.mainet.sfac.repository.FarmerMasterRepository;
import com.abm.mainet.sfac.ui.model.FarmerMasterModel;

/**
 * @author pooja.maske
 *
 */
@Service
public class FarmerMasterServiceImpl implements FarmerMasterService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FarmerMasterService#saveFarmerDetails(com.abm.
	 * mainet.sfac.dto.FarmerMasterDto,
	 * com.abm.mainet.sfac.ui.model.FarmerMasterModel)
	 */

	private static final Logger LOGGER = Logger.getLogger(FarmerMasterServiceImpl.class);

	@Autowired
	private FarmerMasterRepository farmerMasterRepository;

	@Autowired
	private FarmerMasterDao farmerMasterDao;

	@Autowired
	private FPOMasterService fpoMasterService;
	
	@Autowired
	private  IFileUploadService fileUpload;

	@Override
	@Transactional
	public FarmerMasterDto saveFarmerDetails(FarmerMasterDto farmerMasterDto, FarmerMasterModel farmerMasterModel,List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO) {
		FarmerMasterEntity entity = new FarmerMasterEntity();
		try {
				BeanUtils.copyProperties(farmerMasterDto, entity);
				entity.setFrmVoterCardNo(farmerMasterDto.getFrmVoterCardNo().toUpperCase());
				entity = farmerMasterRepository.save(entity);
				requestDTO.setIdfId("FRM"+ MainetConstants.WINDOWS_SLASH + entity.getFrmId());
				saveImages(attachments, requestDTO);
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Saving Farmer Master Details " + e);
			throw new FrameworkException("Exception Occured While Saving Farmer Master Details ", e);
		}
		return farmerMasterDto;
	}

	@Override

	public boolean checkWomenCentric(Long femaleId, Long maleId, Long userId) {
		Boolean result;
		Long femaleCount = farmerMasterRepository.getFemaleCount(femaleId, userId);
		Long maleCount = farmerMasterRepository.getMaleCount(maleId, userId);
		if (femaleCount != null && maleCount != null && femaleCount >= maleCount)
			result = true;
		else
			result = false;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FarmerMasterService#getDetailById(java.lang.Long)
	 */
	@Override
	public FarmerMasterDto getDetailById(Long frmId) {
		FarmerMasterDto dto = new FarmerMasterDto();
		try {
			LOGGER.info("getDetailById Started");
			FarmerMasterEntity entity = farmerMasterRepository.getDetailById(frmId);
			BeanUtils.copyProperties(entity, dto);
		} catch (Exception e) {
			LOGGER.error("Error occured while fetching farmer details getDetailById in service" + e);
		}
		LOGGER.info("getDetailById Ended");
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FarmerMasterService#getFarmerDetailsByIds(java.
	 * lang.Long, java.lang.String)
	 */
	@Override
	public List<FarmerMasterDto> getFarmerDetailsByIds(Long frmId, String frmFPORegNo) {
		List<FarmerMasterDto> dtoList = new ArrayList<>();
		try {
			LOGGER.info("getFarmerDetailsByIds Started");
			List<FarmerMasterEntity> entityList = farmerMasterDao.getFarmerDetailsByIds(frmId, frmFPORegNo);
			for (FarmerMasterEntity entity : entityList) {
				FarmerMasterDto dto = new FarmerMasterDto();
				String fpoName = fpoMasterService.getFpoName(entity.getFrmFPORegNo());
				if (StringUtils.isNotEmpty(fpoName))
				dto.setFpoName(fpoName);
				BeanUtils.copyProperties(entity, dto);
				dtoList.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while fetching farmer details in getFarmerDetailsByIds() service" + e);
		}
		LOGGER.info("getFarmerDetailsByIds Ended");
		return dtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.FarmerMasterService#getAllDetailsById(java.lang.
	 * Long)
	 */
	@Override
	public List<FarmerMasterDto> getAllDetailsById(Long frmId) {
		List<FarmerMasterDto> dtoList = new ArrayList<>();
		try {
			LOGGER.info("getAllDetailsById Started");
			List<FarmerMasterEntity> entityList = farmerMasterRepository.getAllDetailsById(frmId);
			for (FarmerMasterEntity entity : entityList) {
				FarmerMasterDto dto = new FarmerMasterDto();
				BeanUtils.copyProperties(entity, dto);
				dtoList.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while fetching farmer details in service getAllDetailsById () " + e);
		}
		LOGGER.info("getAllDetailsById Ended");
		return dtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.FarmerMasterService#findAll()
	 */
	@Override
	public List<FarmerMasterDto> findAll() {
		List<FarmerMasterDto> dtoList = new ArrayList<>();
		try {
			LOGGER.info("findAll Started");
			List<FarmerMasterEntity> entityList = farmerMasterRepository.findAllFarmerDet();
			for (FarmerMasterEntity entity : entityList) {
				FarmerMasterDto dto = new FarmerMasterDto();
				BeanUtils.copyProperties(entity, dto);
				dtoList.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while fetching farmer details in service findAll() " + e);
		}
		LOGGER.info("findAll Ended");
		return dtoList;
	}
	
	
	public void saveImages(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		fileUpload.doMasterFileUpload(attachments, requestDTO);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient) {
		Set<File> fileList = null;
		Long x = 0L;
		Map<Long, Set<File>> fileMap = new HashMap<>();
		Map<Integer, List<AttachDocs>> map = new HashMap<>();
		for (AttachDocs attachDoc : attachDocs) {
			Integer serNo = attachDoc.getSerialNo();
			if (map.containsKey(serNo)) {
				map.get(serNo).add(attachDoc);
			} else {
				List<AttachDocs> list = new ArrayList<>();
				list.add(attachDoc);
				map.put(serNo, list);
			}
		}

		for (final Map.Entry<Integer, List<AttachDocs>> entry : map.entrySet()) {

			fileList = new HashSet<>();
			for (AttachDocs doc : entry.getValue()) {
				final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
						+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
				String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
				final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
						existingPath);

				String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
						existingPath);

				directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
						MainetConstants.operator.COMMA);
				FileOutputStream fos = null;
				File file = null;
				try {
					final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

					Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

					file = new File(
							Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

					fos = new FileOutputStream(file);

					fos.write(image);

					fos.close();

				} catch (final Exception e) {
					throw new FrameworkException(e);
				} finally {
					try {

						if (fos != null) {
							fos.close();
						}

					} catch (final IOException e) {
						throw new FrameworkException(e);
					}
				}
				fileList.add(file);
			}
			fileMap.put(x, fileList);
			x++;

		}

		return fileMap;
	}
}
