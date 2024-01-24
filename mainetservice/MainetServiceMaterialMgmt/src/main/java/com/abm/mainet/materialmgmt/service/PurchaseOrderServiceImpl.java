package com.abm.mainet.materialmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dao.PurchaseOrderDao;
import com.abm.mainet.materialmgmt.domain.PurchaseOrderEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseorderAttachmentEntity;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDetDto;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderOverheadsDto;
import com.abm.mainet.materialmgmt.dto.PurchaseorderAttachmentDto;
import com.abm.mainet.materialmgmt.dto.PurchaseorderTncDto;
import com.abm.mainet.materialmgmt.mapper.PurchaseOrderMapper;
import com.abm.mainet.materialmgmt.repository.PurchaseOrderRepository;
import com.abm.mainet.materialmgmt.repository.PurchaseRequistionRepository;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private PurchaseOrderMapper purchaseOrderMapper;

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Autowired
	private ItemMasterService itemMasterService;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Autowired
	private PurchaseRequistionRepository purchaseRequistionRepository;

	@Override
	@Transactional
	public PurchaseOrderDto savePurchaseOrder(PurchaseOrderDto purchaseOrderDto, List<Long> removeOverheadIdList,
			List<Long> removeTncIdList, List<Long> removeEncIdList) {
		PurchaseOrderEntity purchaseOrderEntity = purchaseOrderMapper
				.mapPurchaseOrderDTOToPurchaseOrderEntity(purchaseOrderDto);
		purchaseOrderEntity = purchaseOrderRepository.save(purchaseOrderEntity);

		if (removeOverheadIdList != null && removeOverheadIdList.size() != 0)
			purchaseOrderRepository.removeOverheadsById(purchaseOrderEntity.getUserId(), removeOverheadIdList);
		if (removeTncIdList != null && removeTncIdList.size() != 0)
			purchaseOrderRepository.removeTNCsById(purchaseOrderEntity.getUserId(), removeTncIdList);
		if (removeEncIdList != null && removeEncIdList.size() != 0)
			purchaseOrderRepository.removeEncsById(purchaseOrderEntity.getUserId(), removeEncIdList);

		int count = 0;
		for (PurchaseorderAttachmentEntity attachmentEntity : purchaseOrderEntity.getPurchaseorderAttachmentEntity()) {
			List<DocumentDetailsVO> attach = new ArrayList<>();
			attach.add(purchaseOrderDto.getPurchaseorderAttachmentDto().get(count).getAttachments().get(count));
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(attachmentEntity.getOrgId());
			requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId("MMM" + MainetConstants.WINDOWS_SLASH + attachmentEntity.getPodocId());
			requestDTO.setUserId(attachmentEntity.getCreatedBy());
			saveDocuments(attach, requestDTO);
			count++;
		}

		for (PurchaseorderAttachmentDto attachmentDto : purchaseOrderDto.getPurchaseorderAttachmentDto())
			updateLegacyDocuments(attachmentDto.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());

		return purchaseOrderDto;
	}

	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.doMasterFileUpload(attachments, requestDTO);
	}

	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs)
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
		}
	}

	@Override
	@Transactional
	public PurchaseOrderDto getPurchaseRequisitionByPrID(PurchaseOrderDto purchaseOrderDto, Long orgId) {
		purchaseOrderDto.getPurchaseOrderDetDto().clear();
		PurchaseRequistionEntity requisitionEntity = purchaseRequistionRepository
				.findOne(purchaseOrderDto.getPrIdGetData());
		purchaseOrderDto.setStoreId(requisitionEntity.getStoreId());
		Object[] requistStoreVenderObject = purchaseRequistionRepository
				.getRequisitionStoreVenderDetail(requisitionEntity.getPrId(), orgId);
		if (null != requistStoreVenderObject && requistStoreVenderObject.length != 0) {
			requistStoreVenderObject = (Object[]) requistStoreVenderObject[0];
			purchaseOrderDto.setStoreName(requistStoreVenderObject[0].toString());
			purchaseOrderDto.setVendorId(Long.valueOf(requistStoreVenderObject[1].toString()));
			purchaseOrderDto.setVendorName(requistStoreVenderObject[2].toString());
		}

		requisitionEntity.getPurchaseRequistionDetEntity().forEach(requistionDetEntity -> {
			PurchaseOrderDetDto purchaseOrderDetDto = new PurchaseOrderDetDto();
			purchaseOrderDetDto.setPrId(requisitionEntity.getPrId());
			purchaseOrderDetDto.setPrNo(requisitionEntity.getPrNo());
			purchaseOrderDetDto.setPrDate(requisitionEntity.getPrDate());
			purchaseOrderDetDto.setItemId(requistionDetEntity.getItemId());
			Object[] itemObj = (Object[]) itemMasterService
					.getItemDetailObjectByItemId(purchaseOrderDetDto.getItemId())[0];
			purchaseOrderDetDto.setItemName(itemObj[1].toString());
			purchaseOrderDetDto.setUonName(CommonMasterUtility
					.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(itemObj[2].toString()), orgId, "UOM")
					.getLookUpDesc());
			purchaseOrderDetDto.setQuantity(requistionDetEntity.getQuantity());
			purchaseOrderDto.getPurchaseOrderDetDto().add(purchaseOrderDetDto);
		});
		return purchaseOrderDto;
	}

	@Override
	@Transactional
	public List<PurchaseOrderDto> purchaseSearchForSummaryData(Long orgId) {
		List<PurchaseOrderEntity> entities = purchaseOrderRepository.findByOrgIdAndStatusOrder(orgId, 'Y');
		List<PurchaseOrderDto> listDto = new ArrayList<>();
		PurchaseOrderDto dto;
		for (final PurchaseOrderEntity purchaseOrderEntity : entities) {
			dto = new PurchaseOrderDto();
			BeanUtils.copyProperties(purchaseOrderEntity, dto);
			listDto.add(dto);
		}
		return listDto;
	}
	
	@Override
	public List<Object[]> getPurcahseOrderIdAndNumbers(Long orgId) {
		return purchaseOrderRepository.getPurcahseOrderIdAndNumbers(orgId);
	}

	@Override
	@Transactional
	public List<PurchaseOrderDto> searchPurchaseOrderData(Long storeId, Long vendorId, Date fromDate, Date toDate,
			Long orgId) {
		List<Object[]> purchaseOrderObjcets = purchaseOrderDao.purchaseOrderSearchSummaryData(storeId,
				vendorId, fromDate, toDate, orgId);
		List<PurchaseOrderDto> purchaseOrderDtoList = new ArrayList<>();
		PurchaseOrderDto purchaseOrderDto;
		String[] poDate;
		for(Object[] purchaseOrder : purchaseOrderObjcets) {
			purchaseOrderDto = new PurchaseOrderDto();
			purchaseOrderDto.setPoId(Long.parseLong(purchaseOrder[0].toString()));
			purchaseOrderDto.setPoNo(purchaseOrder[1].toString());
			poDate = purchaseOrder[2].toString().split(MainetConstants.WHITE_SPACE);
			purchaseOrderDto.setPoDate(Utility.stringToDate(
					LocalDate.parse(poDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			purchaseOrderDto.setStoreId(Long.parseLong(purchaseOrder[3].toString()));
			purchaseOrderDto.setVendorId(Long.parseLong(purchaseOrder[4].toString()));
			purchaseOrderDto.setStatus(purchaseOrder[5].toString().charAt(0));
			purchaseOrderDtoList.add(purchaseOrderDto);
		}
		return purchaseOrderDtoList;
	}

	@Override
	@Transactional
	public PurchaseOrderDto purchaseOrderEditAndView(Long poId, Long orgId) {
		PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(poId);
		PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
		BeanUtils.copyProperties(purchaseOrderEntity, purchaseOrderDto);

		Long prId = purchaseOrderEntity.getPurchaseOrderDetEntity().get(0).getPrId();
		Object[] purRequisitionObject = (Object[]) purchaseRequistionRepository.getRequisitionStoreVender(prId,
				purchaseOrderEntity.getVendorId(), orgId)[0];
		purchaseOrderDto.setStoreName(purRequisitionObject[2].toString());
		purchaseOrderDto.setVendorName(purRequisitionObject[3].toString());

		purchaseOrderEntity.getPurchaseOrderDetEntity().forEach(poDetEntity -> {
			PurchaseOrderDetDto orderDetDto = new PurchaseOrderDetDto();
			BeanUtils.copyProperties(poDetEntity, orderDetDto);
			orderDetDto.setPrNo(purRequisitionObject[0].toString());
			String[] prDate = purRequisitionObject[1].toString().split(MainetConstants.WHITE_SPACE);
			orderDetDto.setPrDate(Utility.stringToDate(LocalDate.parse(prDate[0]).format(DateTimeFormatter
					.ofPattern(MainetConstants.DATE_FORMAT))));
			
			Object[] itemObj = (Object[]) itemMasterService.getItemDetailObjectByItemId(orderDetDto.getItemId())[0];
			orderDetDto.setItemName(itemObj[1].toString());
			orderDetDto.setUonName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(itemObj[2].toString()),
							orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());
			purchaseOrderDto.getPurchaseOrderDetDto().add(orderDetDto);
		});

		purchaseOrderEntity.getPurchaseOrderOverheadsEntity().forEach(overheadsEntity -> {
			PurchaseOrderOverheadsDto overheadsDto = new PurchaseOrderOverheadsDto();
			BeanUtils.copyProperties(overheadsEntity, overheadsDto);
			purchaseOrderDto.getPurchaseOrderOverheadsDto().add(overheadsDto);
		});

		purchaseOrderEntity.getPurchaseorderTncEntity().forEach(tncEntity -> {
			PurchaseorderTncDto tncDto = new PurchaseorderTncDto();
			BeanUtils.copyProperties(tncEntity, tncDto);
			purchaseOrderDto.getPurchaseorderTncDto().add(tncDto);
		});

		Long count = 0L;
		List<AttachDocs> finalAttachList = new ArrayList<>();
		for (PurchaseorderAttachmentEntity attachmentEntity : purchaseOrderEntity.getPurchaseorderAttachmentEntity()) {
			PurchaseorderAttachmentDto attachmentDto = new PurchaseorderAttachmentDto();
			BeanUtils.copyProperties(attachmentEntity, attachmentDto);

			List<String> identifer = new ArrayList<>();
			identifer.add("MMM" + MainetConstants.WINDOWS_SLASH + attachmentEntity.getPodocId());

			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class).findByIdfInQuery(attachmentEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for (AttachDocs attachDocs2 : attachDocs)
					finalAttachList.add(attachDocs2);
			}
			attachmentDto.setAttachDocsList(attachDocs);
			count++;

			purchaseOrderDto.getPurchaseorderAttachmentDto().add(attachmentDto);
		}
		FileUploadUtility.getCurrent()
				.setFileMap(getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));

		return purchaseOrderDto;
	}

	@Override
	@Transactional
	public void purchaseOrderFordelete(Long poId, Long orgId) {
		purchaseOrderRepository.deletePurchaseOrder(poId, orgId, MainetConstants.CommonConstants.CHAR_N);
	}

	public Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient) {
		Set<File> fileList = null;
		Long x = 0L;
		Map<Long, Set<File>> fileMap = new HashMap<>();
		for (AttachDocs doc : attachDocs) {
			fileList = new HashSet<>();
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
			String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
			final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
					existingPath);
			String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
					existingPath);
			directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
			FileOutputStream fos = null;
			File file = null;
			try {
				final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);
				Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);
				file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);
				fos = new FileOutputStream(file);
				fos.write(image);
				fos.close();
			} catch (final Exception e) {
				throw new FrameworkException("Exception in getting getUploadedFileList", e);
			} finally {
				try {
					if (fos != null)
						fos.close();
				} catch (final IOException e) {
					throw new FrameworkException("Exception in getting getUploadedFileList", e);
				}
			}
			fileList.add(file);
			fileMap.put(x, fileList);
			x++;
		}
		return fileMap;
	}

}
