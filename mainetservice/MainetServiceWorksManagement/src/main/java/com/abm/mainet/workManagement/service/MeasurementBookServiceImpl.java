
package com.abm.mainet.workManagement.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dao.IMeasurementBookDao;
import com.abm.mainet.workManagement.domain.MbCheckListDetail;
import com.abm.mainet.workManagement.domain.MbCheckListDetailHist;
import com.abm.mainet.workManagement.domain.MbCheckListMast;
import com.abm.mainet.workManagement.domain.MbCheckListMastHist;
import com.abm.mainet.workManagement.domain.MbOverHeadDetails;
import com.abm.mainet.workManagement.domain.MeasurementBookDetails;
import com.abm.mainet.workManagement.domain.MeasurementBookDetailsHistory;
import com.abm.mainet.workManagement.domain.MeasurementBookMaster;
import com.abm.mainet.workManagement.domain.MeasurementBookMasterHistory;
import com.abm.mainet.workManagement.domain.WorkEstimOverHeadDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMaster;
import com.abm.mainet.workManagement.domain.WorkOrder;
import com.abm.mainet.workManagement.dto.MbOverHeadDetDto;
import com.abm.mainet.workManagement.dto.MeasurementBookCheckListDetailDto;
import com.abm.mainet.workManagement.dto.MeasurementBookCheckListDto;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;
import com.abm.mainet.workManagement.dto.MeasurementBookLbhDto;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.repository.MbOverHeadRepository;
import com.abm.mainet.workManagement.repository.MeasurementBookCheckListRepository;
import com.abm.mainet.workManagement.repository.MeasurementBookDetailsRepository;
import com.abm.mainet.workManagement.repository.MeasurementBookRepository;
import com.abm.mainet.workManagement.repository.WorkOrderRepository;

/**
 * 
 * @author Jeetendra.Pal
 * @since 21/04/2018
 */

@Service
public class MeasurementBookServiceImpl implements MeasurementBookService {

	@Autowired
	private MeasurementBookRepository mbRepository;

	@Autowired
	private MeasurementBookDetailsRepository mbDetailsRepository;

	@Autowired
	private AuditService auditService;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Autowired
	private MeasurementBookLbhService mbLbhService;

	@Autowired
	private WorkEstimateService estimateService;

	@Autowired
	private IMeasurementBookDao iMeasurementBookDao;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private MeasurementBookCheckListRepository chkRepository;

	@Autowired
	private MbOverHeadRepository mbOverHeadRepository;
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
    private TbAcVendormasterService vendormasterService;

	private static final Logger LOGGER = Logger.getLogger(MeasurementBookServiceImpl.class);

	@Override
	@Transactional
	public void saveAndUpdateMB(MeasurementBookMasterDto masterDto, List<Long> deletedMbId) {
		MeasurementBookMaster measurementBookMaster = new MeasurementBookMaster();
		MeasurementBookMasterHistory measurementBookMasterHistory = new MeasurementBookMasterHistory();
		List<MeasurementBookDetails> bookDetailsList = new ArrayList<>();
		MeasurementBookDetails bookDetails = null;
		WorkEstimateMaster estimateMaster = null;
		WorkOrder workOrder = null;

		try {

			BeanUtils.copyProperties(masterDto, measurementBookMaster);
			workOrder = new WorkOrder();
			workOrder.setWorkId(masterDto.getWorkOrId());
			measurementBookMaster.setWorkOrder(workOrder);
			for (MeasurementBookDetailsDto measurementBookDetailsDto : masterDto.getMbDetails()) {
				bookDetails = new MeasurementBookDetails();
				estimateMaster = new WorkEstimateMaster();
				BeanUtils.copyProperties(measurementBookDetailsDto, bookDetails);
				bookDetails.setMbMaster(measurementBookMaster);
				estimateMaster.setWorkEstemateId(measurementBookDetailsDto.getWorkEstimateMaster().getWorkEstemateId());
				bookDetails.setWorkEstimateType(measurementBookDetailsDto.getWorkEstimateMaster().getEstimateType());
				bookDetails.setWorkFlag(measurementBookDetailsDto.getWorkEstimateMaster().getWorkEstimFlag());
				bookDetails.setWorkEstimateMaster(estimateMaster);
				bookDetailsList.add(bookDetails);

			}
			measurementBookMaster.setBookDetailsList(bookDetailsList);
			measurementBookMaster = mbRepository.save(measurementBookMaster);
			if (masterDto.getMbMultiSelect() != null && !masterDto.getMbMultiSelect().isEmpty()) {
				String assigneeName = String.join(MainetConstants.operator.COMMA, masterDto.getMbMultiSelect());
				workOrder.setWorkAssignee(assigneeName);
				mbRepository.updateWorkAssigneeByworkOrderId(masterDto.getWorkOrId(), workOrder.getWorkAssignee());
			}
			if (deletedMbId != null && !deletedMbId.isEmpty()) {
				mbRepository.deleteMBDetailsById(deletedMbId);
				mbLbhService.deleteMbLbhByMbDetailsId(deletedMbId);
			}

			try {
				measurementBookMasterHistory.setWorkOrId(measurementBookMaster.getWorkOrder().getWorkId());
				auditService.createHistory(measurementBookMaster, measurementBookMasterHistory);

				List<Object> mbDetailsList = new ArrayList<>();
				for (MeasurementBookDetails mbDetails : measurementBookMaster.getBookDetailsList()) {
					MeasurementBookDetailsHistory measurementBookDetailsHistory = new MeasurementBookDetailsHistory();
					measurementBookDetailsHistory.setMbId(measurementBookMaster.getWorkMbId());
					measurementBookDetailsHistory.setMbdId(mbDetails.getMbdId());
					mbDetailsList.add(measurementBookDetailsHistory);
				}
				auditService.createHistoryForListObj(mbDetailsList);

			} catch (Exception exception) {
				LOGGER.error("Exception ocours to create History of MB  " + exception);
			}

			List<Object[]> cumulativedetails = getCummulativeDetailsByWorkOrderId(masterDto.getWorkOrId(),
					measurementBookMaster.getWorkMbId());

			List<Long> estimatedId = new ArrayList<>();
			for (Object[] obj : cumulativedetails) {
				estimatedId.add((Long) obj[0]);
			}

			if (!estimatedId.isEmpty()) {
				List<MeasurementBookDetails> details = mbDetailsRepository
						.getPreviousMbForCummulativeUpdate(estimatedId, (Long) cumulativedetails.get(0)[3]);

				for (MeasurementBookDetails mbdetails : measurementBookMaster.getBookDetailsList()) {
					for (MeasurementBookDetails object : details) {
						if (object.getWorkEstimateMaster().getWorkEstemateId().longValue() == mbdetails
								.getWorkEstimateMaster().getWorkEstemateId().longValue()) {
							if (object.getWorkActualQty() != null && object.getWorkUtlQty() != null) {
								BigDecimal actualQuanity = object.getWorkActualQty();
								BigDecimal utlQuanity = object.getWorkUtlQty();
								mbdetails.setWorkUtlQty(actualQuanity.add(utlQuanity));
							} else if (object.getWorkActualQty() != null) {
								mbdetails.setWorkUtlQty(object.getWorkActualQty());
							}
						}
					}
				}

				mbDetailsRepository.save(measurementBookMaster.getBookDetailsList());
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method saveAndUpdateMB ", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MeasurementBookMasterDto getMBById(Long mbId) {
		MeasurementBookMasterDto bookMasterDto = new MeasurementBookMasterDto();
		List<MeasurementBookDetailsDto> bookDetailsList = new ArrayList<>();
		MeasurementBookDetailsDto bookDetails = null;
		WorkEstimateMasterDto workEstimateMasterDto = null;
		try {
			MeasurementBookMaster bookMaster = mbRepository.getMBById(mbId);
			BeanUtils.copyProperties(bookMaster, bookMasterDto);
			bookMasterDto.setWorkOrId(bookMaster.getWorkOrder().getWorkId());
			for (MeasurementBookDetails detailsEntity : bookMaster.getBookDetailsList()) {
				bookDetails = new MeasurementBookDetailsDto();
				workEstimateMasterDto = new WorkEstimateMasterDto();
				BeanUtils.copyProperties(detailsEntity, bookDetails);
				BeanUtils.copyProperties(detailsEntity.getWorkEstimateMaster(), workEstimateMasterDto);
				bookDetails.setWorkEstimateMaster(workEstimateMasterDto);
				bookDetailsList.add(bookDetails);
			}
			bookMasterDto.setMbDetails(bookDetailsList);
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getMBById " + e);
		}
		return bookMasterDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookMasterDto> getAllMBListByOrgId(Long orgId) {
		List<MeasurementBookMasterDto> bookMasterDto = new ArrayList<>();
		MeasurementBookMasterDto mb = null;
		try {
			List<MeasurementBookMaster> bookMaster = mbRepository.getAllMBListByOrgId(orgId);
			for (MeasurementBookMaster measurementBookMaster : bookMaster) {
				mb = new MeasurementBookMasterDto();
				BeanUtils.copyProperties(measurementBookMaster, mb);
				bookMasterDto.add(mb);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getAllMBListByOrgId " + e);
		}
		return bookMasterDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookDetailsDto> getMBDetailsListByMBId(Long mbId) {
		List<MeasurementBookDetailsDto> bookMasterDto = new ArrayList<>();
		MeasurementBookDetailsDto mb = null;
		try {
			List<MeasurementBookDetails> bookMaster = mbRepository.getMBDetailsListByMBId(mbId);
			for (MeasurementBookDetails entity : bookMaster) {
				mb = new MeasurementBookDetailsDto();
				BeanUtils.copyProperties(entity, mb);
				bookMasterDto.add(mb);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getMBDetailsListByMBId " + e);
		}
		return bookMasterDto;
	}

	@Override
	@Transactional(readOnly = true)
	public MeasurementBookDetailsDto getMBDetailsByDetailsId(Long mbDId) {
		MeasurementBookDetailsDto mbDetails = new MeasurementBookDetailsDto();
		try {
			MeasurementBookDetails bookMaster = mbRepository.getMBDetailsByDetailsId(mbDId);
			if (bookMaster != null) {
				BeanUtils.copyProperties(bookMaster, mbDetails);
				mbDetails.setMbId(bookMaster.getMbMaster().getWorkMbId());
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getMBDetailsByDetailsId " + e);
		}
		return mbDetails;
	}

	@Override
	@Transactional
	public void saveMbRateDetails(List<MeasurementBookDetailsDto> bookDetailsDto, List<Long> deletedRatedId) {
		MeasurementBookDetails bookDetails = null;
		List<MeasurementBookDetails> bookDetailsList = new ArrayList<>();
		MeasurementBookDetailsHistory bookDetailsHistory = null;
		WorkEstimateMaster workEstimateMaster = null;
		List<WorkEstimateMasterDto> workEstimateMasterUpdateList = new ArrayList<>();
		MeasurementBookMaster mbMaster = null;
		List<Object> objectsList = new ArrayList<>();
		try {
			for (MeasurementBookDetailsDto measurementBookDetailsDto : bookDetailsDto) {
				bookDetails = new MeasurementBookDetails();
				bookDetailsHistory = new MeasurementBookDetailsHistory();
				workEstimateMaster = new WorkEstimateMaster();
				mbMaster = new MeasurementBookMaster();
				BeanUtils.copyProperties(measurementBookDetailsDto, bookDetails);
				mbMaster.setWorkMbId(measurementBookDetailsDto.getMbId());
				workEstimateMaster.setWorkEstemateId(measurementBookDetailsDto.getWorkEstemateId());
				bookDetails.setWorkEstimateMaster(workEstimateMaster);
				bookDetails.setMbMaster(mbMaster);
				WorkEstimateMasterDto estimationMaster = estimateService.findById(measurementBookDetailsDto.getOrgId(),
						bookDetails.getWorkEstimateMaster().getWorkEstemateId());
				estimationMaster.setWorkEstimQuantityUtl(bookDetails.getWorkActualQty());
				estimationMaster.setWorkEstimAmountUtl(bookDetails.getWorkActualAmt());
				workEstimateMasterUpdateList.add(estimationMaster);
			}

			estimateService.updateRateValues(workEstimateMasterUpdateList);

			bookDetailsList.add(bookDetails);
			mbDetailsRepository.save(bookDetailsList);

			try {
				for (MeasurementBookDetails object : bookDetailsList) {
					BeanUtils.copyProperties(object, bookDetailsHistory);
					bookDetailsHistory.setMbId(bookDetails.getMbMaster().getWorkMbId());
					bookDetailsHistory.setMbdId(bookDetails.getMbdId());
					bookDetailsHistory.setWorkEId(bookDetails.getWorkEstimateMaster().getWorkEstemateId());
					objectsList.add(bookDetailsHistory);
				}
				auditService.createHistoryForListObj(objectsList);
			} catch (Exception exception) {
				LOGGER.error("Exception ocours to create History of MB  " + exception);
			}

			if (deletedRatedId != null && !deletedRatedId.isEmpty()) {
				mbRepository.deleteMBDetailsByDetpId(deletedRatedId);
			}
			if (bookDetailsDto != null && !bookDetailsDto.isEmpty()) {
				BigDecimal amt = mbDetailsRepository.getAllRateByMbId(bookDetailsDto.get(0).getMbDetPId());
				BigDecimal quantity = mbDetailsRepository.findOne(bookDetailsDto.get(0).getMbDetPId())
						.getWorkActualQty();

				if (amt != null && quantity != null) {
					BigDecimal totalQuantity = quantity.multiply(bookDetailsDto.get(0).getSorRate());
					BigDecimal totalAmount = amt.add(totalQuantity);
					mbDetailsRepository.updateAmount(bookDetailsDto.get(0).getMbDetPId(), totalAmount);
				}
				if (amt != null && quantity == null) {
					mbDetailsRepository.updateAmount(bookDetailsDto.get(0).getMbDetPId(), amt);
				}
				BigDecimal actualAmt = mbDetailsRepository.getAllRateAmount(bookDetailsDto.get(0).getMbId());
				//D87850
				BigDecimal mbovdAmt=mbOverHeadRepository.getOverheadAmount(bookDetailsDto.get(0).getMbId());
				if(mbovdAmt!=null) {
		        	actualAmt=actualAmt.add(mbovdAmt);
		        }
				mbRepository.updateTotalMbAmountByMbId(actualAmt, bookDetailsDto.get(0).getMbId());
			}

		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getMBDetailsByDetailsId " + e);
		}
	}

	@Override
	@Transactional
	public String generateAndUpdateMbNumber(Long workOrderNo, Long orgId, Long deptId) {

		Date workOrderDate = ApplicationContextProvider.getApplicationContext().getBean(WorkOrderService.class)
				.getWorkOredrByOrderId(workOrderNo).getStartDate();

		// get financial by date
		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).getFinanciaYearByDate(workOrderDate);

		// get financial year formate
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

		// gerenerate sequence
		final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
						MainetConstants.WorksManagement.TB_WMS_MEASUREMENTBOOK_MAST,
						MainetConstants.WorksManagement.WORK_MB_NO, orgId, MainetConstants.FlagC,
						financiaYear.getFaYear());
		String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDeptCode(deptId);

		String mbNumber = MainetConstants.WorksManagement.MB + MainetConstants.WINDOWS_SLASH + deptCode
				+ MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
				+ String.format(MainetConstants.CommonMasterUi.PADDING_SIX, sequence);

		return mbNumber;
	}

	@Override
	@Transactional
	public void deleteEnclosureFileById(List<Long> enclosureRemoveById, Long empId) {

		ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class)
				.updateRecord(enclosureRemoveById, empId, MainetConstants.FlagD);
	}

	@Override
	@Transactional(readOnly = true)
	public MeasurementBookMasterDto getMBByWorkOrderId(Long workId, String flag) {
		MeasurementBookMasterDto bookMasterDto = new MeasurementBookMasterDto();
		List<MeasurementBookDetailsDto> bookDetailsList = new ArrayList<>();
		MeasurementBookDetailsDto bookDetails = null;
		WorkEstimateMasterDto workEstimateMasterDto = null;
		try {
			MeasurementBookMaster bookMaster = null;
			if (flag == null) {
				bookMaster = mbRepository.getMBByWorkOrderId(workId);
			} else {
				bookMaster = mbRepository.getMBStatusByWorkOrderId(workId);
			}
			if(null!=bookMaster){
				if(null!=bookMaster.getWorkOrder()){
					String ids = bookMaster.getWorkOrder().getWorkAssignee();
					if (ids != null && !ids.isEmpty()) {
						String array[] = ids.split(MainetConstants.operator.COMMA);
						for (String id : array) {
							bookMasterDto.getMbMultiSelect().add(id);
						}
					}
					BeanUtils.copyProperties(bookMaster, bookMasterDto);
					
					if(null!=bookMaster.getWorkOrder().getWorkId())
						bookMasterDto.setWorkOrId(bookMaster.getWorkOrder().getWorkId());
				}

				List<Object[]> cummulativeQuantityDetails = getCummulativeDetailsByWorkOrderId(workId,
						bookMaster.getWorkMbId());

				if(null!=bookMaster.getBookDetailsList() && !bookMaster.getBookDetailsList().isEmpty()){
					for (MeasurementBookDetails detailsEntity : bookMaster.getBookDetailsList()) {
						bookDetails = new MeasurementBookDetailsDto();
						workEstimateMasterDto = new WorkEstimateMasterDto();
						BeanUtils.copyProperties(detailsEntity, bookDetails);
						BeanUtils.copyProperties(detailsEntity.getWorkEstimateMaster(), workEstimateMasterDto);
						bookDetails.setWorkEstimateMaster(workEstimateMasterDto);
						List<MeasurementBookLbhDto> mbLbhDto = mbLbhService
								.getAllLbhDetailsByMeasurementId(bookDetails.getMbdId());
						List<MeasurementBookDetailsDto> mbDetailsDto = getMBByMbdetParentId(bookDetails.getMbdId());
						if (mbLbhDto.isEmpty() && mbDetailsDto.isEmpty()) {
							bookDetails.getWorkEstimateMaster().setMbDetails(true);
						}
						for (Object[] obj : cummulativeQuantityDetails) {
							if ((Long) obj[0] == detailsEntity.getWorkEstimateMaster().getWorkEstemateId().longValue()) {
								if (obj[2] != null)
									bookDetails.setWorkUtlQty(new BigDecimal(obj[2].toString()));
							}
						}

						bookDetailsList.add(bookDetails);
					}
				}
				
				//bookMasterDto.setMbOverHeadDetDtos(overHeadDetDtos);
				bookMasterDto.setMbDetails(bookDetailsList);
			}
			
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getMBByWorkOrderId " + e);
		}
		return bookMasterDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookDetailsDto> getMBByMbdetParentId(Long mbPId) {
		List<MeasurementBookDetailsDto> bookDetDto = new ArrayList<>();
		MeasurementBookDetailsDto mb = null;
		try {
			List<MeasurementBookDetails> bookMaster = mbRepository.getMBByMbdetParentId(mbPId);
			for (MeasurementBookDetails entity : bookMaster) {
				mb = new MeasurementBookDetailsDto();
				BeanUtils.copyProperties(entity, mb);
				mb.setWorkEstemateId(entity.getWorkEstimateMaster().getWorkEstemateId());
				bookDetDto.add(mb);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getMBDetailsListByMBId " + e);
		}
		return bookDetDto;
	}

	@Override
	@Transactional(readOnly = true)
	public MeasurementBookMasterDto checkMBByWorkOrderId(Long workId) {
		MeasurementBookMasterDto bookMasterDto = null;
		try {
			MeasurementBookMaster bookMaster = mbRepository.getMBByWorkOrderId(workId);
			if (bookMaster != null) {
				bookMasterDto = new MeasurementBookMasterDto();
				BeanUtils.copyProperties(bookMaster, bookMasterDto);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while  calling method checkMBByWorkOrderId " + e);
		}
		return bookMasterDto;
	}

	@Override
	@Transactional(readOnly = true)
	public MeasurementBookDetailsDto getMBDetailsByEstimateId(Long estimateId, Long mbDetId, String flag) {
		MeasurementBookDetailsDto mbDetails = new MeasurementBookDetailsDto();
		MeasurementBookDetails bookMaster = null;
		try {
			if (flag != null) {
				bookMaster = mbRepository.getMBNonSorDetailsByEstimateId(estimateId, mbDetId, flag);
			} else {
				bookMaster = mbRepository.getMBDetailsByEstimateId(estimateId, mbDetId);
			}
			if (bookMaster != null) {
				BeanUtils.copyProperties(bookMaster, mbDetails);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getMBDetailsByEstimateId " + e);
		}
		return mbDetails;
	}

	@Override
	@Transactional(readOnly = true)
	public MeasurementBookMasterDto getWorkMbByMBCode(String workMBCode, Long currentOrgId) {
		MeasurementBookMasterDto bookMasterDto = null;
		try {
			MeasurementBookMaster bookMaster = mbRepository.getWorkMbByMBCode(workMBCode, currentOrgId);
			if (bookMaster != null) {
				bookMasterDto = new MeasurementBookMasterDto();
				BeanUtils.copyProperties(bookMaster, bookMasterDto);
				bookMasterDto.setWorkOrId(bookMaster.getWorkOrder().getWorkId());
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while  calling method getWorkMbByMBCode " + e);
		}
		return bookMasterDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookMasterDto> findAbstractSheetReport(Long workMbId) {

		List<MeasurementBookMasterDto> measurementBookList = new ArrayList<>();

		List<Object[]> mbMasterEntity = mbRepository.findAbstractReportSheet(workMbId);

		for (Object[] masterEntity : mbMasterEntity) {

			MeasurementBookMasterDto bookMasterDto = new MeasurementBookMasterDto();
			BigDecimal actualAmount = null;
			if (masterEntity[0] != null) {
				bookMasterDto.setWorkId((Long) masterEntity[0]);
			}
			bookMasterDto.setSordCategory((Long) masterEntity[1]);
			bookMasterDto.setSordSubCategory((String) masterEntity[2]);
			bookMasterDto.setSorDIteamNo((String) masterEntity[3]);
			bookMasterDto.setSorDDescription(
					bookMasterDto.getSorDIteamNo() + MainetConstants.WHITE_SPACE + (String) masterEntity[4]);
			if (masterEntity[5] != null) {
				bookMasterDto.setSorIteamUnitDesc(
						CommonMasterUtility.getCPDDescription((Long) masterEntity[5], MainetConstants.FlagV));
			}
			bookMasterDto.setWorkEstimQuantity((BigDecimal) masterEntity[6]);
			bookMasterDto.setWorkEstimQuantityUtl((BigDecimal) masterEntity[7]);
			bookMasterDto.setWorkActualAmt((BigDecimal) masterEntity[8]);
			bookMasterDto.setRate((BigDecimal) masterEntity[9]);
			bookMasterDto.setActualRate((BigDecimal) masterEntity[10]);

			bookMasterDto.setWorkName((String) masterEntity[11]);
			bookMasterDto.setProjName((String) masterEntity[12]);

			if (masterEntity[13] != null)
				bookMasterDto.setCummulativeAmt((BigDecimal) masterEntity[13]);

			if (masterEntity[14] != null)
				bookMasterDto.setRemark((String) masterEntity[14]);

			if (bookMasterDto.getCummulativeAmt() != null && bookMasterDto.getActualRate() != null) {
				bookMasterDto.setTotalSubHeadAmount(bookMasterDto.getCummulativeAmt()
						.multiply(bookMasterDto.getActualRate()).setScale(2, BigDecimal.ROUND_FLOOR));
			}

			/*
			 * if (bookMasterDto.getWorkEstimQuantityUtl() != null &&
			 * bookMasterDto.getActualRate() != null) { actualAmount =
			 * bookMasterDto.getWorkEstimQuantityUtl().multiply(bookMasterDto.getActualRate(
			 * )); } if (actualAmount != null) {
			 * bookMasterDto.setWorkActualAmt(actualAmount.setScale(2,
			 * BigDecimal.ROUND_FLOOR)); }
			 */

			measurementBookList.add(bookMasterDto);
		}
		return measurementBookList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookDetailsDto> getAuditLogDetails(Long mbParentId) {
		List<MeasurementBookDetailsDto> bookDetDto = new ArrayList<>();
		MeasurementBookDetailsDto mb = null;
		try {
			List<MeasurementBookDetailsHistory> bookMasterHistory = mbRepository.getAuditLogDetails(mbParentId);
			for (MeasurementBookDetailsHistory entity : bookMasterHistory) {
				mb = new MeasurementBookDetailsDto();
				BeanUtils.copyProperties(entity, mb);
				bookDetDto.add(mb);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getAuditLogDetails " + e);
		}
		return bookDetDto;
	}

	@Override
	@Transactional
	public void updateLbhQuantityByMbId(BigDecimal quantity, Long workMbDId) {
		mbRepository.updateLbhQuantityByMbId(quantity, workMbDId);
	}

	@Override
	@Transactional
	public void updateTotalMbAmountByMbId(BigDecimal amount, Long workMbId) {
		mbRepository.updateTotalMbAmountByMbId(amount, workMbId);
	}
	
	@Override
	@Transactional
	public void updateTotalAmountByMbId(BigDecimal workActualAmt, Long mbDetId) {
		mbRepository.updateAmountByMbId(workActualAmt, mbDetId);
	}
	
	@Override
	@Transactional
	public void updateTotalMbAmountByMbIdDet(BigDecimal amount, Long workMbId) {
		mbRepository.updateTotalMbAmountByMbIdDet(amount, workMbId);
	}

	@Override
	@Transactional
	public void updateBillNumberByMbId(String amount, Long workMbId) {
		mbRepository.updateBillNumberByMbId(amount, workMbId);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getWorkIdWithMbNumber(String workMbNo, Long orgId) {

		return mbRepository.getWorkIdWithMbNumber(workMbNo, orgId);
	}

	@Override
	@Transactional
	public void updateMeasureMentMode(Long workMbId, String flag) {
		mbRepository.updateMeasureMentMode(workMbId, flag);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookMasterDto> filterMeasurementBookData(Long workOrderId, String flag, Long orgId) {
		List<MeasurementBookMasterDto> bookMasDto = new ArrayList<>();
		MeasurementBookMasterDto mb = null;
		MeasurementBookDetailsDto detDto = null;
		try {
			List<MeasurementBookMaster> bookMaster = iMeasurementBookDao.filterMeasurementBookData(workOrderId, flag,
					orgId);
			if (!bookMaster.isEmpty()) {
				for (MeasurementBookMaster entity : bookMaster) {
					mb = new MeasurementBookMasterDto();
					BeanUtils.copyProperties(entity, mb);
					for (MeasurementBookDetails mbDet : entity.getBookDetailsList()) {
						detDto = new MeasurementBookDetailsDto();
						BeanUtils.copyProperties(mbDet, detDto);
						detDto.setWorkEstemateId(mbDet.getWorkEstimateMaster().getWorkEstemateId());
						mb.getMbDetails().add(detDto);
					}
					mb.setWorkOrId(entity.getWorkOrder().getWorkId());
					mb.setMbTakenDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getWorkMbTakenDate()));
					bookMasDto.add(mb);
				}
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method filterMeasurementBookData " + e);
		}
		return bookMasDto;
	}

	@Override
	@Transactional
	public void saveMbNonSorDetails(List<MeasurementBookDetailsDto> bookDetailsDto) {
		MeasurementBookDetails detailsEntity = null;
		List<MeasurementBookDetails> detailsEntityList = new ArrayList<>();
		MeasurementBookMaster mbMaster = null;
		WorkEstimateMaster workEstimateMaster = null;
		if (!bookDetailsDto.isEmpty()) {
			for (MeasurementBookDetailsDto measurementBookDetailsDto : bookDetailsDto) {
				detailsEntity = new MeasurementBookDetails();
				mbMaster = new MeasurementBookMaster();
				workEstimateMaster = new WorkEstimateMaster();
				BeanUtils.copyProperties(measurementBookDetailsDto, detailsEntity);
				mbMaster.setWorkMbId(measurementBookDetailsDto.getMbId());
				detailsEntity.setMbMaster(mbMaster);
				workEstimateMaster.setWorkEstemateId(measurementBookDetailsDto.getWorkEstemateId());
				detailsEntity.setWorkEstimateMaster(workEstimateMaster);
				detailsEntityList.add(detailsEntity);
			}
			mbDetailsRepository.save(detailsEntityList);

		}
		

	}
	
	@Override
	@Transactional
	public void updateAmountNonSor(List<MeasurementBookDetailsDto> bookDetailsDto,List<Long> mbdId) {
		BigDecimal actualAmt = mbDetailsRepository.getAllRateAmount(bookDetailsDto.get(0).getMbId());
		//D87850
		BigDecimal mbovdAmt=mbOverHeadRepository.getOverheadAmount(bookDetailsDto.get(0).getMbId());
		if(mbovdAmt!=null) {
        	actualAmt=actualAmt.add(mbovdAmt);
        }
		mbRepository.updateTotalMbAmountByMbId(actualAmt, bookDetailsDto.get(0).getMbId());
		 if (mbdId != null && !mbdId.isEmpty()) {
		mbRepository.deleteNonSorMbDetailsId(mbdId);
		 }
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeBean> getAssignedEmpDetailByEmpIdList(List<Long> empId, Long orgId) {
		List<EmployeeBean> empList = new ArrayList<>();
		EmployeeBean dto = null;
		List<Employee> employeeList = employeeService.getEmpDetailListByEmpIdList(empId, orgId);
		if (!employeeList.isEmpty()) {
			for (Employee employee : employeeList) {
				dto = new EmployeeBean();
				BeanUtils.copyProperties(employee, dto);
				dto.setDesignName(employee.getDesignation().getDsgname());
				empList.add(dto);
			}
		}
		return empList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getCummulativeDetailsByWorkOrderId(Long workOrderId, Long runningMbId) {
		List<Object[]> cumulativeQuantityDetails = mbRepository.getCummulativeDetailsByWorkOrderId(workOrderId,
				runningMbId);
		return cumulativeQuantityDetails;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookMasterDto> getAllMbDeatilsByListOfMBId(List<Long> workMbId, Long orgId) {

		List<MeasurementBookMasterDto> listBookMasterDtos = new ArrayList<>();
		MeasurementBookMasterDto bookMasterDto = null;
		List<MeasurementBookMaster> listBookMaster = mbRepository.getAllMbDetailsByListOfWorkMbId(workMbId, orgId);
		if (!listBookMaster.isEmpty()) {
			for (MeasurementBookMaster bookMaster : listBookMaster) {
				bookMasterDto = new MeasurementBookMasterDto();
				BeanUtils.copyProperties(bookMaster, bookMasterDto);
				bookMasterDto.setWorkOrId(bookMaster.getWorkOrder().getWorkId());
				bookMasterDto.setMbTakenDate(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(bookMaster.getWorkMbTakenDate()));
				bookMasterDto.setAgreeFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(bookMaster.getWorkOrder().getContractFromDate()));
				bookMasterDto.setAgreeToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						.format(bookMaster.getWorkOrder().getContractToDate()));
				List<Long> listOfIds = new ArrayList<>();
				String id = bookMaster.getWorkOrder().getWorkAssignee();
				if (id != null && !id.isEmpty()) {
					String array[] = id.split(MainetConstants.operator.COMMA);
					for (String ids : array) {
						listOfIds.add(Long.parseLong(ids));
					}
				}

				if (!listOfIds.isEmpty() && listOfIds != null) {
					String employee = MainetConstants.BLANK;
					List<EmployeeBean> empList = getAssignedEmpDetailByEmpIdList(listOfIds, orgId);
					for (EmployeeBean employeeBean : empList) {
						employee += employeeBean.getEmpname() + MainetConstants.WHITE_SPACE + employeeBean.getEmpmname()
								+ MainetConstants.WHITE_SPACE + employeeBean.getEmplname()
								+ MainetConstants.operator.LEFT_BRACKET + employeeBean.getDesignName()
								+ MainetConstants.operator.RIGHT_BRACKET + MainetConstants.BLANK
								+ MainetConstants.BLANK_WITH_SPACE;
					}

					bookMasterDto.setWorkAssigneeName(employee);
				}

				listBookMasterDtos.add(bookMasterDto);
			}
		}
		return listBookMasterDtos;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, Long> getPreviousMbUtilzedQuantity(Long mbId, Long workOrderId) {

		List<Object[]> quantityList = mbRepository.getPreviousMbUtilzedQuantity(mbId, workOrderId);
		Map<Long, Long> quantityMap = null;
		if (!quantityList.isEmpty()) {
			quantityMap = new HashMap<>();
			for (Object object[] : quantityList) {
				Long measureId = (Long) object[0];
				Long utilQuantity = (Long) object[1];
				quantityMap.put(measureId, utilQuantity);
			}
		}

		return quantityMap;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, BigDecimal> getPreviousNonSorUtilzedQuantity(Long mbId, Long workOrderId) {

		List<Object[]> quantityList = mbRepository.getPreviousNonSorUtilzedQuantity(mbId, workOrderId);
		Map<Long, BigDecimal> quantityMap = null;
		if (!quantityList.isEmpty()) {
			quantityMap = new HashMap<>();
			for (Object object[] : quantityList) {
				Long measureId = (Long) object[0];
				BigDecimal utilQuantity = (BigDecimal) object[1];
				quantityMap.put(measureId, utilQuantity);
			}
		}

		return quantityMap;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, Long> getPreviousDirectUtilzedQuantity(Long mbId, Long workOrderId) {

		List<Object[]> quantityList = mbRepository.getPreviousDirectUtilzedQuantity(mbId, workOrderId);
		Map<Long, Long> quantityMap = null;
		if (!quantityList.isEmpty()) {
			quantityMap = new HashMap<>();
			for (Object object[] : quantityList) {
				Long measureId = (Long) object[0];
				Long utilQuantity = (Long) object[1];
				quantityMap.put(measureId, utilQuantity);
			}
		}

		return quantityMap;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<File> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient) {

		Set<File> fileList = new HashSet<>();
		for (AttachDocs doc : attachDocs) {
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
		return fileList;
	}

	@Override
	@Transactional
	public void deleteMbDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookMasterDto> getAllMbDeatilsBySearch(Long workOrderId, String flag, String mbNo,
			Long workId, Long vendorId, Long orgId) {

		List<MeasurementBookMasterDto> bookMasDto = new ArrayList<>();
		MeasurementBookMasterDto mb = null;
		MeasurementBookDetailsDto detDto = null;
		try {
			List<MeasurementBookMaster> bookMaster = iMeasurementBookDao.getAllMbDeatilsBySearch(workOrderId, flag,
					mbNo, workId, vendorId, orgId);
			if (!bookMaster.isEmpty()) {
				for (MeasurementBookMaster entity : bookMaster) {
					mb = new MeasurementBookMasterDto();
					BeanUtils.copyProperties(entity, mb);
					for (MeasurementBookDetails mbDet : entity.getBookDetailsList()) {
						detDto = new MeasurementBookDetailsDto();
						BeanUtils.copyProperties(mbDet, detDto);
						detDto.setWorkEstemateId(mbDet.getWorkEstimateMaster().getWorkEstemateId());
						mb.getMbDetails().add(detDto);
					}
					mb.setWorkOrId(entity.getWorkOrder().getWorkId());
					mb.setMbTakenDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getWorkMbTakenDate()));
					bookMasDto.add(mb);
				}
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getAllMbDeatilsBySearch " + e);
		}
		return bookMasDto;

	}

	@Override
	@Transactional
	public void saveAndUpdateMBCheckList(MeasurementBookCheckListDto masterDto, List<Long> deletedMbcdId) {

		MbCheckListMast mbChkMast = new MbCheckListMast();
		List<MbCheckListDetail> mbCheckListDetail = new ArrayList<>();
		MbCheckListDetail chkDetails = null;
		MeasurementBookMaster mb = null;
		try {

			BeanUtils.copyProperties(masterDto, mbChkMast);
			mb = new MeasurementBookMaster();
			mb.setWorkMbId(masterDto.getMbId());
			mbChkMast.setMbMaster(mb);
			for (MeasurementBookCheckListDetailDto mbChkDet : masterDto.getMbChkListDetails()) {
				chkDetails = new MbCheckListDetail();
				BeanUtils.copyProperties(mbChkDet, chkDetails);
				chkDetails.setMbChkListMaster(mbChkMast);
				mbCheckListDetail.add(chkDetails);
			}
			mbChkMast.setMbCheckListDetail(mbCheckListDetail);
			mbChkMast = chkRepository.save(mbChkMast);
			try {
				if (mbChkMast.getUpdatedBy() != null) {
					List<Object> detHistList = new ArrayList<>();
					MbCheckListMastHist mbChkMasHist = new MbCheckListMastHist();
					mbChkMasHist.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
					auditService.createHistory(mbChkMast, mbChkMasHist);
					mbChkMast.getMbCheckListDetail().forEach(savedDetEntity -> {
						MbCheckListDetailHist detHistory = new MbCheckListDetailHist();
						BeanUtils.copyProperties(savedDetEntity, detHistory);
						detHistory.setMbcId(savedDetEntity.getMbChkListMaster().getMbcId());
						detHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
						detHistList.add(detHistory);
					});
					auditService.createHistoryForListObj(detHistList);
				} else {
					List<Object> detHistList = new ArrayList<>();
					MbCheckListMastHist mbChkMasHist = new MbCheckListMastHist();
					mbChkMasHist.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
					auditService.createHistory(mbChkMast, mbChkMasHist);
					mbChkMast.getMbCheckListDetail().forEach(savedDetEntity -> {
						MbCheckListDetailHist detHistory = new MbCheckListDetailHist();
						BeanUtils.copyProperties(savedDetEntity, detHistory);
						detHistory.setMbcId(savedDetEntity.getMbChkListMaster().getMbcId());
						detHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
						detHistList.add(detHistory);
					});
					auditService.createHistoryForListObj(detHistList);
				}
			} catch (Exception exception) {
				LOGGER.error("Could not make audit entry for" + mbChkMast, exception);
			}
			if (deletedMbcdId != null && !deletedMbcdId.isEmpty()) {
				chkRepository.deleteMBChkListDetailsById(deletedMbcdId);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method saveAndUpdateMBCheckList " + e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MeasurementBookCheckListDto getMBCheckListByMBAndOrgId(Long mbId, Long orgId) {
		MeasurementBookCheckListDto flowDto = null;
		List<MeasurementBookCheckListDetailDto> chkListDetList = new ArrayList<>();
		MeasurementBookCheckListDetailDto dto = null;
		MbCheckListMast chkList = chkRepository.getMBCheckListByMBAndOrgId(mbId, orgId);
		if (chkList != null) {
			flowDto = new MeasurementBookCheckListDto();
			BeanUtils.copyProperties(chkList, flowDto);
			flowDto.setMbId(chkList.getMbMaster().getWorkMbId());
			for (MbCheckListDetail det : chkList.getMbCheckListDetail()) {
				dto = new MeasurementBookCheckListDetailDto();
				BeanUtils.copyProperties(det, dto);
				dto.setMbcId(chkList.getMbcId());
				chkListDetList.add(dto);
			}
			flowDto.setMbChkListDetails(chkListDetList);
		}
		return flowDto;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getTotalMbAmountByWorkId(Long workId, Long orgId) {
		BigDecimal totAmount = mbRepository.getTotalMbAmountByWorkId(workId, orgId);
		return totAmount;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getTotalMbAmtByWorkOrderId(Long workId, Long orgId) {
		BigDecimal totAmount = mbRepository.getTotalMbAmountByWorkOrderId(workId, orgId);
		return totAmount;
	}

	@Override
	@Transactional
	public void saveLegacyData(List<MeasurementBookMasterDto> bookMas, List<DocumentDetailsVO> attachments) {
		List<MeasurementBookMaster> entityList = new ArrayList<>();
		try {
			if (!CollectionUtils.isEmpty(bookMas)) {
				bookMas.forEach(dto -> {
					MeasurementBookMaster entity = new MeasurementBookMaster();
					WorkOrder workOrder = new WorkOrder();
					BeanUtils.copyProperties(dto, entity);
					workOrder.setWorkId(dto.getWorkOrId());
					entity.setWorkOrder(workOrder);
					entityList.add(entity);
				});
				mbRepository.save(entityList);
				int count = 0;
				for (MeasurementBookMaster mb : entityList) {
					List<DocumentDetailsVO> attach = new ArrayList<>();
					attach.add(attachments.get(count));
					RequestDTO requestDTO = new RequestDTO();
					requestDTO.setOrgId(mb.getOrgId());
					requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.WATER);
					requestDTO.setStatus(MainetConstants.FlagA);
					requestDTO.setIdfId(mb.getWorkMbNo());
					requestDTO.setUserId(mb.getCreatedBy());
					saveDocuments(attach, requestDTO);
					count++;
				}
			}
		} catch (Exception exception) {
			LOGGER.error("Exception ocours to saveUpdateLegacyData()" + "  " + exception);
			throw new FrameworkException("Exception occours to saveAndUpdateSellEntry method" + exception);
		}

	}

	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {

		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.doMasterFileUpload(attachments, requestDTO);
	}

	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}

	}

	@Override
	@Transactional
	public void updateLegacyData(List<MeasurementBookMasterDto> bookMas, List<DocumentDetailsVO> attachments,
			List<AttachDocs> attachDocs) {
		List<MeasurementBookMaster> entityList = new ArrayList<>();
		try {
			if (!CollectionUtils.isEmpty(bookMas)) {
				bookMas.forEach(dto -> {
					MeasurementBookMaster entity = new MeasurementBookMaster();
					WorkOrder workOrder = new WorkOrder();
					BeanUtils.copyProperties(dto, entity);
					workOrder.setWorkId(dto.getWorkOrId());
					entity.setWorkOrder(workOrder);
					entityList.add(entity);
				});
				mbRepository.save(entityList);
				int count = 0;
				for (MeasurementBookMaster mb : entityList) {
					List<DocumentDetailsVO> attach = new ArrayList<>();
					attach.add(attachments.get(count));
					RequestDTO requestDTO = new RequestDTO();
					requestDTO.setOrgId(mb.getOrgId());
					requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.WATER);
					requestDTO.setStatus(MainetConstants.FlagA);
					requestDTO.setIdfId(mb.getWorkMbNo());
					requestDTO.setUserId(mb.getCreatedBy());
					saveDocuments(attach, requestDTO);
					count++;
				}
			}
			updateLegacyDocuments(attachDocs, bookMas.get(0).getUpdatedBy());

		} catch (Exception exception) {
			LOGGER.error("Exception ocours to updateLegacyData()" + "  " + exception);
			throw new FrameworkException("Exception occours to updateLegacyData method" + exception);
		}

	}

	@Transactional
	public void saveOverHeadList(List<MbOverHeadDetDto> mbeOverHeadDetDtoList, List<Long> overHeadRemoveById,BigDecimal totalMbAount,Long mbId) {
		List<MbOverHeadDetails> overHeadList = new ArrayList<>();

		mbeOverHeadDetDtoList.forEach(dto -> {
			WorkEstimOverHeadDetails workOverhead = new WorkEstimOverHeadDetails();
			MeasurementBookMaster measurementBookMaster=new MeasurementBookMaster();
			measurementBookMaster.setWorkMbId(dto.getMbMasterId());
			MbOverHeadDetails entity = new MbOverHeadDetails();
			BeanUtils.copyProperties(dto, workOverhead);
			BeanUtils.copyProperties(dto, entity);
			entity.setMbOvhId(dto.getMbOvhId());
			entity.setOverHeadId(workOverhead);
			entity.setMbMaster(measurementBookMaster);

			if (dto.getOverHeadCode() != null && !dto.getOverHeadCode().isEmpty())
				overHeadList.add(entity);
		});
		mbOverHeadRepository.save(overHeadList);

		/*
		 * if (overHeadRemoveById != null && !overHeadRemoveById.isEmpty())
		 * mbOverHeadRepository.updateDeletedFlagForOverHeads(overHeadRemoveById);
		 */

		if (mbeOverHeadDetDtoList != null && !mbeOverHeadDetDtoList.isEmpty()) {
			
			/*
			 * BigDecimal overheadAmount = mbRepository.getTotalMbAmountByWorkOrderId(
			 * mbeOverHeadDetDtoList.get(0).getWorkId(),
			 * mbeOverHeadDetDtoList.get(0).getOrgId());
			 */
			
			updateTotalEstimateAmount(totalMbAount,mbId);

		}
	}

	private void updateTotalEstimateAmount(BigDecimal mbAmount,Long mbId) {
		
		updateTotalMbAmountByMbId(mbAmount, mbId);
	}
	
   @Transactional
   public void updateTotalEstimateAmounts(BigDecimal mbAmount,Long mbId) {
		
		updateTotalMbAmountByMbId(mbAmount, mbId);
		//updateTotalMbAmountByMbIdDet(mbAmount, mbId);
	}

	@Override
	public BigDecimal getTotalMbAmtByMbId(Long mbId, Long orgId) {
		BigDecimal amount=mbRepository.getTotalMbAmountByWorkMbId(mbId,orgId);
		if(amount==null) {
			amount=new BigDecimal(0.00);
		}
		return amount;
	}

	@Override
	public List<MbOverHeadDetDto> getmbOverDetByMbMasId(Long mbId, Long orgId) {
		List<MbOverHeadDetDto> overHeadDetDtos=new ArrayList<MbOverHeadDetDto>();
		List<MbOverHeadDetails> mbOverHeadDetails=mbOverHeadRepository.getMbOverHeadDetailsByWorkOrgId(mbId, orgId);
		if(mbOverHeadDetails!=null && !mbOverHeadDetails.isEmpty()) {
			mbOverHeadDetails.forEach(entity->{
				MbOverHeadDetDto detDto=new MbOverHeadDetDto();
				BeanUtils.copyProperties(entity, detDto);
				detDto.setOverHeadId(entity.getOverHeadId().getOverHeadId());
				overHeadDetDtos.add(detDto);
			});
		}
		return overHeadDetDtos;
	}
	//Getting the MbOverHeadDetDtos except the draft mode MbOverHeadDetDtos
	@Override
	public List<MbOverHeadDetDto> getmbOverDetByEstimationId(Long ovId, Long orgId) {
		List<MbOverHeadDetDto> overHeadDetDtos=new ArrayList<MbOverHeadDetDto>();
		List<MbOverHeadDetails> mbOverHeadDetails=mbOverHeadRepository.getMbOverHeadDetailsByWorkOvId(ovId, orgId);
		if(mbOverHeadDetails!=null && !mbOverHeadDetails.isEmpty()) {
			mbOverHeadDetails.forEach(entity->{
				MbOverHeadDetDto detDto=new MbOverHeadDetDto();
				BeanUtils.copyProperties(entity, detDto);
				MeasurementBookMasterDto bookMasterDto=new MeasurementBookMasterDto();
				BeanUtils.copyProperties(entity.getMbMaster(), bookMasterDto);
				detDto.setMeasurementBookMasterDto(bookMasterDto);
				detDto.setOverHeadId(entity.getOverHeadId().getOverHeadId());
				if(!detDto.getMeasurementBookMasterDto().getMbStatus().equals(MainetConstants.FlagD))
					overHeadDetDtos.add(detDto);
			});
		}
		return overHeadDetDtos;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookMasterDto> getAllMBListSummaryByOrgId(Long orgId) {
		List<MeasurementBookMasterDto> bookMasterDto = new ArrayList<>();
		MeasurementBookMasterDto mb = null;
		try {
			List<MeasurementBookMaster> bookMaster = mbRepository.getAllMBListByOrgId(orgId);
			for (MeasurementBookMaster measurementBookMaster : bookMaster) {
				mb = new MeasurementBookMasterDto();
				BeanUtils.copyProperties(measurementBookMaster, mb);
				
				
				mb.setWorkId(measurementBookMaster.getWorkOrder().getWorkId());
				mb.setWorkOrderNo(measurementBookMaster.getWorkOrder().getWorkOrderNo());
				
				if(measurementBookMaster.getMbStatus() !=null) {
					if (measurementBookMaster.getMbStatus().equals(MainetConstants.FlagP)) {
						mb.setWorkOrderStatus(MainetConstants.TASK_STATUS_PENDING);
					} else if (measurementBookMaster.getMbStatus().equals(MainetConstants.FlagD)) {
						mb.setWorkOrderStatus(MainetConstants.TASK_STATUS_DRAFT);
					} else if (measurementBookMaster.getMbStatus().equals(MainetConstants.FlagA)) {
						mb.setWorkOrderStatus(MainetConstants.TASK_STATUS_APPROVED);
					} else if (measurementBookMaster.getMbStatus().equals(MainetConstants.FlagR)) {
						mb.setWorkOrderStatus(MainetConstants.TASK_STATUS_REJECTED);
					} else if (measurementBookMaster.getMbStatus().equals(MainetConstants.WorksManagement.RATE_TYPE)) {
						mb.setWorkOrderStatus(MainetConstants.WorksManagement.RABILL);
					} else if (measurementBookMaster.getMbStatus().equals(MainetConstants.WorksManagement.Send_for_RA_Bill)) {
						mb.setWorkOrderStatus(MainetConstants.WorksManagement.SendRAbill);
					} else if (measurementBookMaster.getMbStatus().equals(MainetConstants.WorksManagement.send_for_approval)) {
						mb.setWorkOrderStatus(MainetConstants.WorksManagement.SendForApproval);
					}
				}

				
				mb.setContDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(measurementBookMaster.getWorkOrder().getContractMastEntity().getContDate()));
				mb.setContNo(measurementBookMaster.getWorkOrder().getContractMastEntity().getContNo());
				mb.setWorkAssigneeDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(measurementBookMaster.getWorkOrder().getContractFromDate()));
				
				if(measurementBookMaster.getMbTotalAmt() == null) {
					mb.setMbTotalAmt(BigDecimal.valueOf(0.00));
				}
				WorkOrder workOrderEntity = workOrderRepository.findOne(mb.getWorkId());
				if(workOrderEntity.getContractMastEntity().getContractPart2List()!=null &&  workOrderEntity.getContractMastEntity().getContractPart2List().get(0).getVmVendorid() != null) {
					//#154873
					String vendorName = vendormasterService.getVendorNameById(workOrderEntity.getContractMastEntity().getContractPart2List().get(0).getVmVendorid(), workOrderEntity.getContractMastEntity().getContractPart2List().get(0).getOrgId());
					mb.setVendorName(vendorName);
				}
				

				
				bookMasterDto.add(mb);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception acured while  calling method getAllMBListByOrgId " + e);
		}
		return bookMasterDto;
	}

}
