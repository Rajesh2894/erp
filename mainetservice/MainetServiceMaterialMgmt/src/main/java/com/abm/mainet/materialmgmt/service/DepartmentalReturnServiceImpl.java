package com.abm.mainet.materialmgmt.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.dao.DepartmentalReturnDao;
import com.abm.mainet.materialmgmt.domain.BinLocMasEntity;
import com.abm.mainet.materialmgmt.domain.CommonTransactionEntity;
import com.abm.mainet.materialmgmt.domain.DeptReturnEntity;
import com.abm.mainet.materialmgmt.domain.DeptReturnEntityDetail;

import com.abm.mainet.materialmgmt.domain.IndentProcessEntity;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.DeptItemDetailsDTO;
import com.abm.mainet.materialmgmt.dto.DeptReturnDTO;

import com.abm.mainet.materialmgmt.dto.IndentProcessDTO;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.repository.BinLocMasRepository;
import com.abm.mainet.materialmgmt.repository.DepartmentalReturnRepository;
import com.abm.mainet.materialmgmt.repository.ICommonTransactionRepository;
import com.abm.mainet.materialmgmt.repository.IndentProcessRepository;

@Service
public class DepartmentalReturnServiceImpl implements DepartmentalReturnService {

	@Autowired
	private DepartmentalReturnRepository departmentalReturnRepository;

	@Resource
	private EmployeeJpaRepository employeeJpaRepository;

	@Autowired
	private CommonService commonService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ItemMasterService itemMasterservice;

	@Autowired
	private IndentProcessRepository indentProcessRepository;

	@Autowired
	private IStoreMasterService storeMasterservice;

	@Autowired
	private DepartmentalReturnDao departmentalReturnDao;

	@Autowired
	private IStoreMasterService iStoreMasterService;

	@Autowired
	private BinLocMasRepository binLocMasRepository;

	@Resource
	private ICommonTransactionRepository transactionRepository;

	@Override
	@Transactional
	public void updateIndentReturnStatus(Long orgId, String dreturnno, Character status, String wfFlag) {
		departmentalReturnRepository.updateIndentReturnStatus(orgId, dreturnno, status, wfFlag);
	}

	@Override
	@Transactional
	public void updateIndentItemReturnStatus(Long orgId, Long dreturnno, Character status) {
		departmentalReturnRepository.updateIndentItemReturnStatus(orgId, dreturnno, status);
	}

	@Override
	@Transactional
	public DeptReturnDTO saveDepartmentalReturn(DeptReturnDTO deptReturnDTO, long levelCheck,
			WorkflowTaskAction workflowTaskAction) {
		DeptReturnEntity deptReturnEntity = new DeptReturnEntity();
		BeanUtils.copyProperties(deptReturnDTO, deptReturnEntity);
		deptReturnDTO.getDeptItemDetailsDTOList().forEach(dto -> {
			DeptReturnEntityDetail detailEntity = new DeptReturnEntityDetail();
			BeanUtils.copyProperties(dto, detailEntity);
			detailEntity.setStoreid(deptReturnDTO.getStoreid());
			detailEntity.setItemid(dto.getItemid());
			detailEntity.setDreturnid(deptReturnEntity);
			if (dto.getDisposalflag() == null)
				detailEntity.setDisposalflag(MainetConstants.CommonConstants.CHAR_N);
			deptReturnEntity.getDeptReturnEntityDetail().add(detailEntity);
		});
		departmentalReturnRepository.save(deptReturnEntity);

		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(
				MainetConstants.MaterialManagement.INDENT_RETURN_CODE, deptReturnDTO.getOrgid());
		if (0L == levelCheck) {
			initializeWorkFlowForFreeServiceIndetReturn(deptReturnDTO, serviceMas);
		} else {
			updateWorkFlowService(workflowTaskAction, serviceMas);
		}

		/** Save Department Return Data To Transaction Table */
		if (2l == levelCheck)
			transactionRepository.save(saveDepartmentReturnDataToTransactionEntity(deptReturnDTO));

		return deptReturnDTO;
	}

	/** Transaction Type : Purchase-P, Indent-I, Store Indent Outward-M, Store Indent Inward-S,
  		Disposal-D, Adjustment-A, Transfer-T, Expired-E, Opening Balance-O, Returned-R  */
	private List<CommonTransactionEntity> saveDepartmentReturnDataToTransactionEntity(DeptReturnDTO deptReturnDTO) {
		List<CommonTransactionEntity> transactionEntityList = new ArrayList<>();
		deptReturnDTO.getDeptItemDetailsDTOList().forEach(itemDetail -> {
			CommonTransactionEntity transactionEntity = new CommonTransactionEntity();
			transactionEntity.setTransactionDate(itemDetail.getUpdatedDate());
			transactionEntity.setTransactionType(MainetConstants.FlagR);
			transactionEntity.setReferenceNo(deptReturnDTO.getDreturnno());
			transactionEntity.setStoreId(deptReturnDTO.getStoreid());
			transactionEntity.setItemId(itemDetail.getItemid());
			transactionEntity.setItemNo(itemDetail.getItemno());
			transactionEntity.setStatus(MainetConstants.FlagY);

			if (MainetConstants.CommonConstants.CHAR_Y == itemDetail.getDisposalflag()) {
				transactionEntity.setDebitQuantity(new BigDecimal(itemDetail.getReturnqty()));
				transactionEntity.setDisposalStatus(MainetConstants.FlagP); /** For Marked for Disposal Item */
			} else {
				transactionEntity.setBinLocation(itemDetail.getBinlocation());
				transactionEntity.setCreditQuantity(new BigDecimal(itemDetail.getReturnqty()));
				transactionEntity.setDisposalStatus(MainetConstants.FlagN);
			}
			transactionEntity.setOrgId(itemDetail.getOrgid());
			transactionEntity.setUserId(itemDetail.getUpdatedBy());
			transactionEntity.setLangId(itemDetail.getLangId());
			transactionEntity.setCreatedDate(itemDetail.getUpdatedDate());
			transactionEntity.setLgIpMacUpd(itemDetail.getLgIpMacUpd());
			transactionEntityList.add(transactionEntity);
		});
		return transactionEntityList;
	}

	
	@Override
	@Transactional
	public void updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ServiceMaster serviceMas) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(
				serviceMasterService.getProcessName(serviceMas.getSmServiceId(), serviceMas.getOrgid()));
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
	}

	@Override
	public List<IndentProcessDTO> findIndentByEmpId(final Long indenter, final Long orgId, String status) {
		IndentProcessDTO indentProcessDTO = null;
		List<IndentProcessDTO> indentProcessDTOs = null;
		final List<Object[]> objList = departmentalReturnRepository.findIndentByEmpId(indenter, orgId, status);
		if ((objList != null) && !objList.isEmpty()) {
			indentProcessDTOs = new ArrayList<>();
			for (final Object[] obj : objList) {
				indentProcessDTO = new IndentProcessDTO();
				indentProcessDTO.setIndentid(Long.valueOf(obj[0].toString()));
				indentProcessDTO.setIndentno(obj[1] == null ? null : String.valueOf(obj[1]));

				indentProcessDTOs.add(indentProcessDTO);
			}
		}
		return indentProcessDTOs;
	}

	@Transactional
	public void initializeWorkFlowForFreeServiceIndetReturn(DeptReturnDTO deptReturnDTO, ServiceMaster serviceMas) {
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(employeeService.getEmpFullNameById(deptReturnDTO.getUserid()));
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(deptReturnDTO.getUserid());
		applicationMetaData.setReferenceId(deptReturnDTO.getDreturnno());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(deptReturnDTO.getOrgid());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	@Transactional
	public DeptReturnDTO getIndentReturnDataById(String indentNo, Long orgId) {
		DeptReturnEntity entity = departmentalReturnRepository.findByIndentReturnNo(indentNo, orgId);
		return getIndentReturnDto(entity, orgId);
	}

	private DeptReturnDTO getIndentReturnDto(DeptReturnEntity entity, Long orgId) {
		DeptReturnDTO dto = new DeptReturnDTO();
		BeanUtils.copyProperties(entity, dto);
		final Object[] data = departmentalReturnRepository.getStoreDetailsByIndentId(entity.getIndentid(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		Object[] dataobject = (Object[]) data[0];
		dto.setStoreName(dataobject[2].toString());
		dto.setLocationName(dataobject[0].toString());

		final Object[] empObject = employeeJpaRepository.getEmployeDetailsById(dto.getIndenter(), orgId);
		Object[] employee = (Object[]) empObject[0];
		dto.setDeptName(employee[1].toString());
		dto.setDesgName(employee[3].toString());
		if (null != employee[5] && null != employee[6])
			dto.setReportingMgrName(employee[5].toString() + MainetConstants.WHITE_SPACE + employee[6].toString());

		List<DeptItemDetailsDTO> itemList = new ArrayList<>();

		entity.getDeptReturnEntityDetail().forEach(returnDetailEntity -> {
			DeptItemDetailsDTO deptItemDto = new DeptItemDetailsDTO();
			BeanUtils.copyProperties(returnDetailEntity, deptItemDto);
			Object[] itemObj = (Object[]) itemMasterservice.getItemDetailObjectByItemId(deptItemDto.getItemid())[0];
			deptItemDto.setItemDesc(itemObj[1].toString());
			deptItemDto.setUomDesc(CommonMasterUtility
					.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(itemObj[2].toString()), orgId, "UOM")
					.getLookUpDesc());

			itemList.add(deptItemDto);

		});

		dto.setDeptItemDetailsDTOList(itemList);
		return dto;
	}

	@Override
	@Transactional
	public List<IndentProcessDTO> findindentBystatus(String status, Long orgId) {
		List<IndentProcessEntity> entityList = indentProcessRepository.findindentBystatus(status, orgId);
		List<IndentProcessDTO> indentProcessDTOList = new ArrayList<>();
		entityList.forEach(entity -> {
			IndentProcessDTO indentProcessDTO = new IndentProcessDTO();
			if (entity != null) {
				BeanUtils.copyProperties(entity, indentProcessDTO);
				if (indentProcessDTO.getStoreid() != null && indentProcessDTO.getIndenter() != null) {
					indentProcessDTO.setStoreDesc(
							storeMasterservice.getStoreMasterByStoreId(entity.getStoreid()).getStoreName());
					indentProcessDTO
							.setIndenterName(employeeService.findEmployeeById(entity.getIndenter()).getEmpname());
				}
			}
			indentProcessDTOList.add(indentProcessDTO);
		});
		return indentProcessDTOList;
	}

	@Override

	@Transactional
	public DeptReturnDTO findItemInfoByIndentIdORG(DeptReturnDTO deptReturnDTO, Long ORGID) {
		List<Object[]> entitiesList = departmentalReturnRepository.getIndentReturnDataByIndentId(
				MainetConstants.CommonConstants.CHAR_Y, ORGID, deptReturnDTO.getIndentid());
		deptReturnDTO.getDeptItemDetailsDTOList().clear();

		for (Object[] obj : entitiesList) {
			if (null != obj[1]) {
				deptReturnDTO.setIndentid(Long.valueOf(obj[1].toString()).longValue());
			}
			DeptItemDetailsDTO deptItemDetailsDTO = new DeptItemDetailsDTO();
			if (null != obj[0]) {
				deptItemDetailsDTO.setPrimId(Long.valueOf(obj[0].toString()).longValue());
			}
			if (null != obj[2]) {
				deptItemDetailsDTO.setItemid(Long.valueOf(obj[2].toString()).longValue());
			}
			if (null != obj[3]) {
				deptItemDetailsDTO.setItemno(obj[3].toString());
			}
			if (null != obj[4]) {
				deptItemDetailsDTO.setIssueqty((long) (Double.valueOf(obj[4].toString()).doubleValue()));
			}
			if (null != obj[5]) {
				deptItemDetailsDTO.setItemDesc(obj[5].toString());
			}
			if (null != obj[6]) {
				deptItemDetailsDTO.setUomDesc(CommonMasterUtility
						.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(obj[6].toString()), ORGID, "UOM")
						.getLookUpDesc());

			}
			if (null != obj[7]) {
				deptItemDetailsDTO.setPreReceivedQty((long) (Double.valueOf(obj[7].toString()).doubleValue()));
			}

			deptReturnDTO.getDeptItemDetailsDTOList().add(deptItemDetailsDTO);
		}

		return deptReturnDTO;

	}

	@Override
	public List<DeptReturnDTO> fetchindentReturnSummaryData(Long orgid) {
		List<DeptReturnEntity> entities = departmentalReturnRepository.findByOrgIdForIndentReturnSummary(orgid);
		final List<DeptReturnDTO> bean = new ArrayList<>();
		for (final DeptReturnEntity indentReturnEntity : entities) {
			DeptReturnDTO deptReturnDTO = new DeptReturnDTO();
			if (indentReturnEntity.getStoreid() != null) {
				StoreMasterDTO storeDto = iStoreMasterService.getStoreMasterByStoreId(indentReturnEntity.getStoreid());
				deptReturnDTO.setStoreName(storeDto.getStoreName());
			}

			if (indentReturnEntity.getIndentid() != null) {
				IndentProcessEntity indententity = indentProcessRepository
						.findByIndentidAndOrgid(indentReturnEntity.getIndentid(), orgid);
				deptReturnDTO.setIndentid(indententity.getIndentid());
				deptReturnDTO.setIndentNo(indententity.getIndentno());
			}

			if (indentReturnEntity.getDreturnno() != null) {
				deptReturnDTO.setDreturnno(indentReturnEntity.getDreturnno());
				deptReturnDTO.setDreturnid(indentReturnEntity.getDreturnid());
			}
			if (indentReturnEntity.getDreturndate() != null) {
				deptReturnDTO.setDreturndate(indentReturnEntity.getDreturndate());
			}

			if (indentReturnEntity.getStatus() != null) {
				deptReturnDTO.setStatus(indentReturnEntity.getStatus());
			}

			bean.add(deptReturnDTO);
		}
		return bean;
	}

	@Override
	@Transactional
	public List<DeptReturnDTO> searchStoresReturnData(String dreturnno, Long indentid, Date fromDate, Date toDate,
			Long storeid, Character status, Long orgid) {

		List<DeptReturnEntity> entities = departmentalReturnDao.searchindentReturnData(dreturnno, indentid, fromDate,
				toDate, storeid, status, orgid);
		List<DeptReturnDTO> listDto = new ArrayList<>();

		if (entities != null) {
			entities.forEach(entity -> {

				DeptReturnDTO dto = new DeptReturnDTO();
				if (entity.getStoreid() != null) {
					StoreMasterDTO storeDto = iStoreMasterService.getStoreMasterByStoreId(entity.getStoreid());
					dto.setStoreName(storeDto.getStoreName());
				}
				if (entity.getDreturnno() != null) {
					dto.setDreturnno(entity.getDreturnno());
					dto.setDreturnid(entity.getDreturnid());
				}
				if (entity.getIndentid() != null) {
					IndentProcessEntity indententity = indentProcessRepository
							.findByIndentidAndOrgid(entity.getIndentid(), orgid);
					dto.setIndentNo(indententity.getIndentno());

				}

				if (entity.getStatus() != null) {
					dto.setStatus(entity.getStatus());
				}

				if (entity.getDreturndate() != null) {
					dto.setDreturndate(entity.getDreturndate());
				}

				listDto.add(dto);
			});
		}
		return listDto;

	}

	@Override
	@Transactional
	public List<BinLocMasDto> getBinlocationList(Long orgid) {
		List<BinLocMasEntity> binLocMasEntity = binLocMasRepository.getbinLocInfoListByOrgId(orgid);
		List<BinLocMasDto> binDtoList = new ArrayList<>();

		for (BinLocMasEntity entity : binLocMasEntity) {
			BinLocMasDto dto = new BinLocMasDto();
			BeanUtils.copyProperties(entity, dto);
			binDtoList.add(dto);
		}
		return binDtoList;

	}

}
