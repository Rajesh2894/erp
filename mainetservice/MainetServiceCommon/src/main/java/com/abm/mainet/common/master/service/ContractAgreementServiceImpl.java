package com.abm.mainet.common.master.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractDetailHistEntity;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractMastHistEntity;
import com.abm.mainet.common.domain.ContractPart1DetailEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.domain.ContractTermsDetailEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dao.IContractAgreementDao;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractInstalmentDetailDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.ContractPart1DetailDTO;
import com.abm.mainet.common.master.dto.ContractPart2DetailDTO;
import com.abm.mainet.common.master.dto.ContractTermsDetailDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author apurva.salgaonkar
 *
 */

@Service
public class ContractAgreementServiceImpl implements IContractAgreementService {

	private static final Logger LOGGER = Logger.getLogger(ContractAgreementServiceImpl.class);

	@Autowired
	private AuditService auditService;

	@Autowired
	private ContractAgreementRepository contractAgreementRepository;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	private IContractAgreementDao iContractAgreementDao;

	@Resource
	private IFileUploadService iFileUploadService;

	@Resource
	private MessageSource messageSource;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private TbTaxMasService tbTaxMasService;

	@Autowired
	IAttachDocsDao iAttachDocsDao;

	@Autowired
	TbFinancialyearService financialyearService;
	
	@Resource
	private TbDepartmentJpaRepository tbDepartmentJpaRepository;
	
	@Autowired
	private TbDepartmentService tbDepartmentService;
	
	@Autowired
    private IOrganisationService orgService;

	private static Logger logger = Logger.getLogger(ContractAgreementServiceImpl.class);

	final Base64 base64 = new Base64();

	private List contractDetailHistEntity;

	@Override
	@Transactional
	public List<Object[]> getDepartmentsMappedWithContractParti1(final Long orgId) {
		List<Object[]> venderNameList = null;
		if (orgId != null) {
			venderNameList = contractAgreementRepository.getDepartmentList(orgId);
		}
		return venderNameList;
	}

	@Override
	@Transactional
	public List<Object[]> getVenderList(final Long orgId, final Long venTypeId, final Long statusId) {
		List<Object[]> venderNameList = null;
		if (orgId != null) {
			if (venTypeId == null && statusId == null) {
				venderNameList = contractAgreementRepository.getVenderList(orgId);
			} else if (statusId == null) {
				venderNameList = contractAgreementRepository.getVenderDetailOnVenderId(orgId, venTypeId);
			} else {
				venderNameList = contractAgreementRepository.getVenderNameOnVenderType(orgId, venTypeId, statusId);
			}
		}
		return venderNameList;
	}

	@Override
	@Transactional(readOnly = true)
	public ContractMastDTO findById(final Long contId, final Long orgId) {

		ContractMastDTO contractMastDTO = null;
		final ContractMastEntity entity = contractAgreementRepository.getContractByContId(contId, orgId);

		final List<ContractPart1DetailDTO> contractPart1DetailDTOList = new ArrayList<>();
		for (final ContractPart1DetailEntity contractPart1DetailEntity : entity.getContractPart1List()) {
			final ContractPart1DetailDTO contractPart1DetailDTO = new ContractPart1DetailDTO();
			BeanUtils.copyProperties(contractPart1DetailEntity, contractPart1DetailDTO);
			contractMastDTO = new ContractMastDTO();
			contractMastDTO.setContId(entity.getContId());
			contractPart1DetailDTO.setContId(contractMastDTO);
			contractPart1DetailDTO.setActive("Y");
			final List<LookUp> contp1NameList = getEmpBasedOnDesgnation(contractPart1DetailEntity.getDsgid(), orgId);
			contractPart1DetailDTO.setContp1NameList(contp1NameList);
			final List<LookUp> desgList = getAllDesgBasedOnDept(contractPart1DetailEntity.getDpDeptid(), orgId);
			if(!desgList.isEmpty() && desgList != null) {
				contractPart1DetailDTO.setDesgList(desgList);
			}
			contractPart1DetailDTOList.add(contractPart1DetailDTO);
		}
		final List<ContractPart2DetailDTO> contractPart2DetailDTOList = new ArrayList<>();
		for (final ContractPart2DetailEntity contractPart2DetailEntity : entity.getContractPart2List()) {
			final ContractPart2DetailDTO contractPar21DetailDTO = new ContractPart2DetailDTO();
			BeanUtils.copyProperties(contractPart2DetailEntity, contractPar21DetailDTO);
			contractPar21DetailDTO.setActive("Y");
			contractMastDTO = new ContractMastDTO();
			contractMastDTO.setContId(entity.getContId());
			contractPar21DetailDTO.setContId(contractMastDTO);
			if (contractPart2DetailEntity.getContp2Type().equals(MainetConstants.CommonConstants.V)) {
				final List<LookUp> vmVendoridList = getVenderNameOnVenderType(
						contractPart2DetailEntity.getContp2vType(), orgId);
				contractPar21DetailDTO.setVmVendoridList(vmVendoridList);
				final String venName = contractAgreementRepository.getVenderNameOnVenderId(orgId,
						contractPart2DetailEntity.getVmVendorid());
				if ((venName != null) && (venName != "")) {
					contractPar21DetailDTO.setVenderName(venName);
				}
			}
			contractPart2DetailDTOList.add(contractPar21DetailDTO);
		}
		final List<ContractDetailDTO> contractDetailDTOList = new ArrayList<>();
		for (final ContractDetailEntity contractDetailEntity : entity.getContractDetailList()) {
			final ContractDetailDTO contractDetailDTO = new ContractDetailDTO();
			BeanUtils.copyProperties(contractDetailEntity, contractDetailDTO);
			contractMastDTO = new ContractMastDTO();
			contractMastDTO.setContId(entity.getContId());
			contractDetailDTO.setContId(contractMastDTO);
			contractDetailDTOList.add(contractDetailDTO);
		}
		final List<ContractTermsDetailDTO> contractTermsDetailList = new ArrayList<>();
		for (final ContractTermsDetailEntity contractTermsDetailEntity : entity.getContractTermsDetailList()) {
			final ContractTermsDetailDTO contractTermsDetailDTO = new ContractTermsDetailDTO();
			BeanUtils.copyProperties(contractTermsDetailEntity, contractTermsDetailDTO);
			contractTermsDetailDTO.setActive("Y");
			contractMastDTO = new ContractMastDTO();
			contractMastDTO.setContId(entity.getContId());
			contractTermsDetailDTO.setContId(contractMastDTO);
			contractTermsDetailList.add(contractTermsDetailDTO);
		}
		final List<ContractInstalmentDetailDTO> contractInstalmentDetailDTOList = new ArrayList<>();
		for (final ContractInstalmentDetailEntity contractInstalmentDetailEntity : entity
				.getContractInstalmentDetailList()) {
			final ContractInstalmentDetailDTO contractInstalmentDetailDTO = new ContractInstalmentDetailDTO();
			BeanUtils.copyProperties(contractInstalmentDetailEntity, contractInstalmentDetailDTO);
			contractInstalmentDetailDTO.setActive("Y");
			contractMastDTO = new ContractMastDTO();
			contractMastDTO.setContId(entity.getContId());
			contractInstalmentDetailDTO.setContId(contractMastDTO);
			contractInstalmentDetailDTOList.add(contractInstalmentDetailDTO);
		}
		entity.getContractPart2List();
		entity.getContractDetailList();
		entity.getContractInstalmentDetailList();
		entity.getContractTermsDetailList();
		contractMastDTO = new ContractMastDTO();
		BeanUtils.copyProperties(entity, contractMastDTO);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			List<LookUp> lookups = CommonMasterUtility.getLookUps("ST",UserSession.getCurrent().getOrganisation());
			for (final LookUp lookUp : lookups) {
                if (contractMastDTO.getContActive().equals(lookUp.getOtherField())) {
                	contractMastDTO.setStatus(lookUp.getLookUpId());
                	break;
                }
            }
			if(entity.getContTerminationDate()!=null) {
				contractMastDTO.setSuspendDate(entity.getContTerminationDate());
			}
			if(entity.getReason()!=null && !entity.getReason().isEmpty() ) {
				contractMastDTO.setReason(entity.getReason());
			}
			
			
		}
		
		if (contractMastDTO.getContMode().equals(MainetConstants.CommonConstants.C) && CommonMasterUtility
				.findLookUpCode(PrefixConstants.CONTRACT_TYPE, orgId, contractMastDTO.getContType())
				.equals(MainetConstants.CL)) {
			final List<LookUp> taxCodeList = tbTaxMasService.getAllTaxesBasedOnDept(orgId, entity.getContDept());
			contractMastDTO.setTaxId(
					contractInstalmentDetailDTOList.isEmpty() ? 0 : contractInstalmentDetailDTOList.get(0).getTaxId());
			contractMastDTO.setTaxCodeList(taxCodeList);
		}
		contractMastDTO.setContractPart1List(contractPart1DetailDTOList);
		contractMastDTO.setContractPart2List(contractPart2DetailDTOList);
		contractMastDTO.setContractInstalmentDetailList(contractInstalmentDetailDTOList);
		contractMastDTO.setContractTermsDetailList(contractTermsDetailList);
		contractMastDTO.setContractDetailList(contractDetailDTOList);

		return contractMastDTO;
	}

	@Override
	@Transactional
	public List<ContractAgreementSummaryDTO> getContractAgreementSummaryData(final Long orgId, final String contractNo,
			final String contractDate, final Long deptId, final Long venderId, final String viewClosedCon,
			final String renewal) throws Exception {
		List<ContractAgreementSummaryDTO> contractAgreementSummaryDTOList = new ArrayList<>();
		List<Object[]> venderNameList = null;
		try {
			Date contractFormDate = null;
			if (contractDate != null && !contractDate.isEmpty()) {
				contractFormDate = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE).parse(contractDate);
			}
			if (orgId != null) {
				venderNameList = iContractAgreementDao.getContractAgreementSummary(orgId, contractNo, contractFormDate,
						deptId, venderId, viewClosedCon, renewal);
			}
			if (venderNameList != null && !venderNameList.isEmpty()) {
				ContractAgreementSummaryDTO contractAgreementSummaryDTO = null;

				for (final Object venObj[] : venderNameList) {
					contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
					contractAgreementSummaryDTO.setContId(Long.valueOf(venObj[0].toString()));
					if (venObj[1] != null) {
						contractAgreementSummaryDTO.setContNo(venObj[1].toString());
					}
					contractAgreementSummaryDTO.setContDept(venObj[2].toString());
					contractAgreementSummaryDTO.setContDate(CommonUtility.dateToString((Date) venObj[3]));
					contractAgreementSummaryDTO.setContFromDate(CommonUtility.dateToString((Date) venObj[4]));
					contractAgreementSummaryDTO.setContToDate(CommonUtility.dateToString((Date) venObj[5]));
					contractAgreementSummaryDTO.setContp1Name(venObj[6].toString());
					contractAgreementSummaryDTO.setContp2Name(venObj[7].toString());
					if (venObj[8] != null) {
						contractAgreementSummaryDTO.setContTndNo(venObj[8].toString());
					}
					if (venObj[9] != null) {
						contractAgreementSummaryDTO.setContLoaNo(venObj[9].toString());
					}

					if (venObj[10] != null) {
						contractAgreementSummaryDTO.setContAmount(new BigDecimal(venObj[10].toString()));
					}
					contractAgreementSummaryDTOList.add(contractAgreementSummaryDTO);
				}
			}
		} catch (final Exception e) {
			logger.info(
					"getContractAgreementSummaryData(Long orgId,String contractNo,String contractDate, Long deptId, Long venderId, String viewClosedCon) end");
			throw new Exception("Problem in getContractAgreementSummaryData" + e);

		}
		return contractAgreementSummaryDTOList;
	}

	@Override
	@Transactional
	public ContractMastDTO saveContractAgreement(final ContractMastDTO contractMastDTO, final Long orgId,
			final int langId, final Long empId, final String modeType, final Map<String, File> UploadMap) {
		final String ipAddress = Utility.getMacAddress();
		final ContractMastEntity entity = new ContractMastEntity();
		EmployeeBean employeeBean = null;
		String fileNetPath = null;
		String dirPath = null;
		List<Object[]> venderDeatil = null;
		if(contractMastDTO.getContActive()==null)
		{
			contractMastDTO.setContActive(MainetConstants.FlagY);
		}
		contractMastDTO.setContCloseFlag(MainetConstants.FlagN);
		contractMastDTO.setOrgId(orgId);
		contractMastDTO.setLangId(langId);
		if (modeType.equals(MainetConstants.MODE_CREATE)) {
			contractMastDTO.setCreatedBy(empId);
			contractMastDTO.setLgIpMac(ipAddress);
			contractMastDTO.setLmodDate(new Date());
		} else if (modeType.equals(MainetConstants.MODE_EDIT)) {
			contractMastDTO.setUpdatedBy(empId);
			contractMastDTO.setLgIpMacUpd(ipAddress);
			contractMastDTO.setUpdatedDate(new Date());
		}
		
		BeanUtils.copyProperties(contractMastDTO, entity);
		if (entity.getContActive().equals(MainetConstants.FlagS)) {
			contractAgreementRepository.updateMappingPropertyFlag(contractMastDTO.getContId());
		}		
		
		

		// Contract Detail
		final List<ContractDetailEntity> contractDetailEntityList = new ArrayList<>();
		ContractDetailEntity contractDetailEntity = null;
		for (final ContractDetailDTO contractDetailDTO : contractMastDTO.getContractDetailList()) {
			contractDetailEntity = new ContractDetailEntity();
			BeanUtils.copyProperties(contractDetailDTO, contractDetailEntity);
			contractDetailEntity.setContId(entity);
			contractDetailEntity.setOrgId(orgId);
			contractDetailEntity.setContEntryType(MainetConstants.WorksManagement.OPEN);
			contractDetailEntity.setContdActive(MainetConstants.FlagY);
			if (contractDetailEntity.getContdId() == 0L) {
				contractDetailEntity.setCreatedBy(empId);
				contractDetailEntity.setLgIpMac(ipAddress);
				contractDetailEntity.setLmodDate(new Date());
			} else {
				contractDetailEntity.setUpdatedBy(empId);
				contractDetailEntity.setLgIpMacUpd(ipAddress);
				contractDetailEntity.setUpdatedDate(new Date());
			}
			contractDetailEntityList.add(contractDetailEntity);
		}
		entity.setContractDetailList(contractDetailEntityList);

		if (!contractMastDTO.getContractPart1List().isEmpty()) {
			venderDeatil = getVenderList(orgId, contractMastDTO.getContractPart2List().get(0).getVmVendorid(), null);
			employeeBean = iEmployeeService.findById(contractMastDTO.getContractPart1List().get(0).getEmpid());

			fileNetPath = messageSource.getMessage("upload.physicalPath", new Object[] {}, StringUtils.EMPTY,
					Locale.ENGLISH);
			final StringBuilder builder = new StringBuilder();
			builder.append(orgId).append(MainetConstants.DOUBLE_BACK_SLACE).append(MainetConstants.CONTRACT)
					.append(MainetConstants.DOUBLE_BACK_SLACE).append(Utility.getTimestamp());
			dirPath = builder.toString();

			if (contractMastDTO.getContNo() == null) {
				final String contractNo = getContractNo(orgId,
						contractMastDTO.getContractPart1List().get(0).getDpDeptid(), contractMastDTO.getContDate());
				entity.setContNo(contractNo);
			}
		}
		// Party 1
		final List<ContractPart1DetailEntity> contractPart1DetailEntityList = new ArrayList<>();
		ContractPart1DetailEntity contractPart1DetailEntity = null;
		Long photoId = 1l;
		Long thumbId = 1l;
		for (final ContractPart1DetailDTO contractPart1DetailDTO : contractMastDTO.getContractPart1List()) {
			if ((contractPart1DetailDTO.getActive() != null)
					&& contractPart1DetailDTO.getActive().equals(MainetConstants.FlagY)) {
				contractPart1DetailEntity = new ContractPart1DetailEntity();
				BeanUtils.copyProperties(contractPart1DetailDTO, contractPart1DetailEntity);
				contractPart1DetailEntity.setContId(entity);
				contractPart1DetailEntity.setOrgId(orgId);
				contractPart1DetailEntity.setContp1Active(MainetConstants.FlagY);
				if(contractPart1DetailDTO.getDpDeptid() != null){
					contractPart1DetailEntity.setDpDeptid(contractPart1DetailDTO.getDpDeptid());
				}
				
				if (contractPart1DetailEntity.getContp1Id() == 0L) {
					contractPart1DetailEntity.setCreatedBy(empId);
					contractPart1DetailEntity.setLgIpMac(ipAddress);
					contractPart1DetailEntity.setLmodDate(new Date());
				} else {
					contractPart1DetailEntity.setUpdatedBy(empId);
					contractPart1DetailEntity.setLgIpMacUpd(ipAddress);
					contractPart1DetailEntity.setUpdatedDate(new Date());
					contractPart1DetailEntity.setContp1PhotoFileName(null);
					contractPart1DetailEntity.setContp1PhotoFilePathName(null);
				}
				// if Row ULB
				if (contractPart1DetailEntity.getContp1Type().equals(MainetConstants.FlagU)) {
					if ((UploadMap != null) && !UploadMap.isEmpty()) {
						final File photoFile = UploadMap.get("0U");
						final File thumbFile = UploadMap.get("1U");
						if (photoFile != null) {
							final String photoFileName = uploadImage(photoFile, fileNetPath, dirPath);
							contractPart1DetailEntity.setContp1PhotoFileName(photoFileName);
							contractPart1DetailEntity.setContp1PhotoFilePathName(dirPath);
						}
						if (thumbFile != null) {
							final String thumbFileName = uploadImage(thumbFile, fileNetPath, dirPath);
							contractPart1DetailEntity.setContp1ThumbFileName(thumbFileName);
							contractPart1DetailEntity.setContp1ThumbFilePathName(dirPath);
						}
					}
					if (employeeBean != null) {
						if ((employeeBean.getEmpAddress() != null) && !employeeBean.getEmpAddress().isEmpty()) {
							contractPart1DetailEntity.setContp1Address(employeeBean.getEmpAddress());
						}
						if ((employeeBean.getPanNo() != null) && !employeeBean.getPanNo().isEmpty()) {
							contractPart1DetailEntity.setContp1ProofIdNo(employeeBean.getPanNo());
						}
					}
				}
				// if Row ULB Witness
				if (contractPart1DetailEntity.getContp1Type().equals(MainetConstants.FlagW)) {
					if ((UploadMap != null) && !UploadMap.isEmpty()) {
						final String photoCount = photoId.toString() + "0" + "UW";
						final String thumbCount = thumbId.toString() + "1" + "UW";
						final File photoFile = UploadMap.get(photoCount);
						final File thumbFile = UploadMap.get(thumbCount);
						if (photoFile != null) {
							final String photoFileName = uploadImage(photoFile, fileNetPath, dirPath);
							contractPart1DetailEntity.setContp1PhotoFileName(photoFileName);
							contractPart1DetailEntity.setContp1PhotoFilePathName(dirPath);
						}
						if (thumbFile != null) {
							final String thumbFileName = uploadImage(thumbFile, fileNetPath, dirPath);
							contractPart1DetailEntity.setContp1ThumbFileName(thumbFileName);
							contractPart1DetailEntity.setContp1ThumbFilePathName(dirPath);
						}
						photoId++;
						thumbId++;
					}
				}
				contractPart1DetailEntityList.add(contractPart1DetailEntity);
			} else {

				contractAgreementRepository.deletePart1Row(contractPart1DetailDTO.getContp1Id());
			}
		}
		entity.setContractPart1List(contractPart1DetailEntityList);

		// Party 2
		final List<ContractPart2DetailEntity> contractPart2DetailEntityList = new ArrayList<>();
		ContractPart2DetailEntity contractPart2DetailEntity = null;
		Long photoIdVender = 11l;
		Long thumbIdvender = 11l;
		Long photoIdVenderwit = 111l;
		Long thumbIdvenderWit = 111l;
		for (final ContractPart2DetailDTO contractPart2DetailDTO : contractMastDTO.getContractPart2List()) {	
		if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL )) || (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL))) {
			final ContractMastEntity contarct = contractAgreementRepository.getContractByContId(entity.getContId(), orgId);
			String dept= tbDepartmentJpaRepository.findDepartmentShortCode(contarct.getContDept(),UserSession.getCurrent().getOrganisation().getOrgid());
			if(dept.equalsIgnoreCase(MainetConstants.WorksManagement.WORKS_MANAGEMENT) || dept.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.STORE)
					||dept.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.WORKFLEETMGMT)) {
			String[] active=contractPart2DetailDTO.getActive().split(",");
			if(active[1]!=null) {
			contractPart2DetailDTO.setActive(active[1]);
			}
			else {
				contractPart2DetailDTO.setActive(active[0]);
			}
			
			}
		}
			
			
			if ((contractPart2DetailDTO.getActive() != null)
					&& contractPart2DetailDTO.getActive().equals(MainetConstants.FlagY)) {
				contractPart2DetailEntity = new ContractPart2DetailEntity();
				BeanUtils.copyProperties(contractPart2DetailDTO, contractPart2DetailEntity);
				contractPart2DetailEntity.setContId(entity);
				contractPart2DetailEntity.setOrgId(orgId);
				contractPart2DetailEntity.setContvActive(MainetConstants.FlagY);
				if (contractPart2DetailEntity.getContp2Id() == 0L) {
					contractPart2DetailEntity.setCreatedBy(empId);
					contractPart2DetailEntity.setLgIpMac(ipAddress);
					contractPart2DetailEntity.setLmodDate(new Date());
				} else {
					contractPart2DetailEntity.setUpdatedBy(empId);
					contractPart2DetailEntity.setLgIpMacUpd(ipAddress);
					contractPart2DetailEntity.setUpdatedDate(new Date());
					contractPart2DetailEntity.setContp2PhotoFileName(null);
					contractPart2DetailEntity.setContp2PhotoFilePathName(null);
				}
			if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) || (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL))) {
				final ContractMastEntity contarct = contractAgreementRepository.getContractByContId(entity.getContId(), orgId);
				String dept= tbDepartmentJpaRepository.findDepartmentShortCode(contarct.getContDept(),UserSession.getCurrent().getOrganisation().getOrgid());
				if(dept.equalsIgnoreCase(MainetConstants.WorksManagement.WORKS_MANAGEMENT) ||dept.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.STORE) 
						||dept.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.WORKFLEETMGMT)) {
				String[]  conType=contractPart2DetailDTO.getContp2Type().split(",");
				if(conType[1]!=null) {
				contractPart2DetailEntity.setContp2Type(conType[1]);
				}
				else {
					contractPart2DetailEntity.setContp2Type(conType[0]);
				}
			}
				
				if (contractPart2DetailEntity.getContp2Type().equals(MainetConstants.FlagV)) {
					if ((UploadMap != null) && !UploadMap.isEmpty()) {
						final String photoCount = photoIdVender.toString() + "0" + MainetConstants.FlagV;
						final String thumbCount = thumbIdvender.toString() + "1" + MainetConstants.FlagV;
						final File photoFile = UploadMap.get(photoCount);
						final File thumbFile = UploadMap.get(thumbCount);
						if (photoFile != null) {
							final String photoFileName = uploadImage(photoFile, fileNetPath, dirPath);
							contractPart2DetailEntity.setContp2PhotoFileName(photoFileName);
							contractPart2DetailEntity.setContp2PhotoFilePathName(dirPath);
						}
						if (thumbFile != null) {
							final String thumbFileName = uploadImage(thumbFile, fileNetPath, dirPath);
							contractPart2DetailEntity.setContp2ThumbFileName(thumbFileName);
							contractPart2DetailEntity.setContp2ThumbFilePathName(dirPath);
						}
						photoIdVender++;
						thumbIdvender++;
					}
					
					if (!(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) ||
						      Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL))) {
					if ((venderDeatil != null) && !venderDeatil.isEmpty()) {
						for (final Object[] obj : venderDeatil) {
							if (obj[0] != null) {
								contractPart2DetailEntity.setContp2Address(obj[0].toString());
							}
							if (obj[1] != null) {
								contractPart2DetailEntity.setContp2ProofIdNo(obj[1].toString());
							}
						}
					}
			}
				}
				
			}
				if (contractPart2DetailEntity.getContp2Type().equals(MainetConstants.FlagW)) {
					if ((UploadMap != null) && !UploadMap.isEmpty()) {
						final String photoCount = photoIdVenderwit.toString() + "0" + "VW";
						final String thumbCount = thumbIdvenderWit.toString() + "1" + "VW";
						final File photoFile = UploadMap.get(photoCount);
						final File thumbFile = UploadMap.get(thumbCount);
						if (photoFile != null) {
							final String photoFileName = uploadImage(photoFile, fileNetPath, dirPath);
							contractPart2DetailEntity.setContp2PhotoFileName(photoFileName);
							contractPart2DetailEntity.setContp2PhotoFilePathName(dirPath);
						}
						if (thumbFile != null) {
							final String thumbFileName = uploadImage(thumbFile, fileNetPath, dirPath);
							contractPart2DetailEntity.setContp2ThumbFileName(thumbFileName);
							contractPart2DetailEntity.setContp2ThumbFilePathName(dirPath);
						}
						photoIdVenderwit++;
						thumbIdvenderWit++;
					}
				}
				contractPart2DetailEntityList.add(contractPart2DetailEntity);
			} else {
				contractAgreementRepository.deletePart2Row(contractPart2DetailDTO.getContp2Id());
			}
		}
		entity.setContractPart2List(contractPart2DetailEntityList);

		// contract TermsDetail
		final List<ContractTermsDetailEntity> contractTermsDetailEntityList = new ArrayList<>();
		ContractTermsDetailEntity contractTermsDetailEntity = null;
		for (final ContractTermsDetailDTO contractTermsDetailDTO : contractMastDTO.getContractTermsDetailList()) {
			if ((contractTermsDetailDTO.getActive() != null)
					&& contractTermsDetailDTO.getActive().equals(MainetConstants.FlagY)) {
				contractTermsDetailEntity = new ContractTermsDetailEntity();
				BeanUtils.copyProperties(contractTermsDetailDTO, contractTermsDetailEntity);
				contractTermsDetailEntity.setContId(entity);
				contractTermsDetailEntity.setOrgId(orgId);
				contractTermsDetailEntity.setConttActive(MainetConstants.FlagY);
				if (contractTermsDetailEntity.getConttId() == 0L) {
					contractTermsDetailEntity.setCreatedBy(empId);
					contractTermsDetailEntity.setLgIpMac(ipAddress);
					contractTermsDetailEntity.setLmodDate(new Date());
				} else {
					contractTermsDetailEntity.setUpdatedBy(empId);
					contractTermsDetailEntity.setLgIpMacUpd(ipAddress);
					contractTermsDetailEntity.setUpdatedDate(new Date());
				}
				contractTermsDetailEntityList.add(contractTermsDetailEntity);
			} else {
				contractAgreementRepository.deleteTermsRow(contractTermsDetailDTO.getConttId());
			}
		}
		entity.setContractTermsDetailList(contractTermsDetailEntityList);

		// contract Instalment
		if (contractMastDTO.getContMode().equals(MainetConstants.MODE_CREATE) && ((CommonMasterUtility
				.findLookUpCode(PrefixConstants.CONTRACT_TYPE, orgId, contractMastDTO.getContType())
				.equals(MainetConstants.CL))
				|| (CommonMasterUtility
						.findLookUpCode(PrefixConstants.CONTRACT_TYPE, orgId, contractMastDTO.getContType())
						.equals("CAH")))) {
			final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntityList = new ArrayList<>();
			ContractInstalmentDetailEntity contractInstalmentDetailEntity = null;
			Long count = 0L;
			Date startDate = null;
			final Calendar cal = Calendar.getInstance();

			for (final ContractInstalmentDetailDTO contractInstalmentDetailDTO : contractMastDTO
					.getContractInstalmentDetailList()) {
				if ((contractInstalmentDetailDTO.getActive() != null)
						&& contractInstalmentDetailDTO.getActive().equals(MainetConstants.FlagY)) {
					contractInstalmentDetailEntity = new ContractInstalmentDetailEntity();
					BeanUtils.copyProperties(contractInstalmentDetailDTO, contractInstalmentDetailEntity);

					final LookUp perLook = CommonMasterUtility.getValueFromPrefixLookUp("PER", "VTY");
					final LookUp amtLook = CommonMasterUtility.getValueFromPrefixLookUp("AMT", "VTY");
					if ((perLook != null) && (contractInstalmentDetailDTO.getConitAmtType() == perLook.getLookUpId())) {
						final Double contAmt = ((contractInstalmentDetailDTO.getConitValue()
								* contractMastDTO.getContractDetailList().get(0).getContAmount()) / 100);
						contractInstalmentDetailEntity.setConitAmount(contAmt);
					} else if ((amtLook != null)
							&& (contractInstalmentDetailDTO.getConitAmtType() == amtLook.getLookUpId())) {
						contractInstalmentDetailEntity.setConitAmount(contractInstalmentDetailDTO.getConitValue());
					}
					contractInstalmentDetailEntity.setTaxId(contractMastDTO.getTaxId());
					contractInstalmentDetailEntity.setContId(entity);
					contractInstalmentDetailEntity.setOrgId(orgId);
					contractInstalmentDetailEntity.setConttActive(MainetConstants.FlagY);
					if (contractInstalmentDetailEntity.getConitId() == 0L) {
						contractInstalmentDetailEntity.setCreatedBy(empId);
						contractInstalmentDetailEntity.setLgIpMac(ipAddress);
						contractInstalmentDetailEntity.setLmodDate(new Date());
					} else {
						contractInstalmentDetailEntity.setUpdatedBy(empId);
						contractInstalmentDetailEntity.setLgIpMacUpd(ipAddress);
						contractInstalmentDetailEntity.setUpdatedDate(new Date());
					}
					if (count == 0) {
						contractInstalmentDetailEntity
								.setConitStartDate(contractMastDTO.getContractDetailList().get(0).getContFromDate());
					} else {

						cal.setTime(startDate);
						cal.add(Calendar.DATE, 1);
						contractInstalmentDetailEntity.setConitStartDate(cal.getTime());
					}
					startDate = contractInstalmentDetailDTO.getConitDueDate();
					count++;
					contractInstalmentDetailEntityList.add(contractInstalmentDetailEntity);
				} else {
					contractAgreementRepository.deleteInstalmentRow(contractInstalmentDetailDTO.getConitId());
				}
			}
			entity.setContractInstalmentDetailList(contractInstalmentDetailEntityList);
		}
		
		//Defect #182114
		String departmentCode = tbDepartmentService.findDepartmentShortCodeByDeptId(contractMastDTO.getContDept(), orgId);
		Organisation org = orgService.getOrganisationById(contractMastDTO.getOrgId());
		String orgName = org.getOrgShortNm();
		if(orgName.equals(MainetConstants.ENV_TSCL) && modeType.equals(MainetConstants.CommonConstants.E) &&
				departmentCode.equals(MainetConstants.RnLCommon.RentLease)) {
			if(contractMastDTO.getStatus() != 0l) {
				LookUp lookups = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(contractMastDTO.getStatus(),orgId,"ST");
				entity.setContActive(lookups.getOtherField());
			}
			if(contractMastDTO.getSuspendDate() != null) {
				entity.setContTerminationDate(contractMastDTO.getSuspendDate());
			}
			
			if (contractMastDTO.getReason() != null && !contractMastDTO.getReason().isEmpty()) {
			    entity.setReason(contractMastDTO.getReason());
			}

			
			//contractAgreementRepository.updateSuspendDate(contractMastDTO.getContId(),contractMastDTO.getSuspendDate(), empId, orgId);
			try {
				Class<?> clazz = null;
				String serviceClassName = null;
				String deptCode = departmentService.getDeptCode(entity.getContDept());
				Object dynamicServiceInstance = null;
				try {
					serviceClassName = messageSource.getMessage(
							ApplicationSession.getInstance().getMessage("objection.lbl") + deptCode, new Object[] {},
							StringUtils.EMPTY, Locale.ENGLISH);
					if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
						clazz = ClassUtils.forName(serviceClassName,
								ApplicationContextProvider.getApplicationContext().getClassLoader());
						dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
								.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
						final Method method = ReflectionUtils.findMethod(clazz, "updateRlBillMaster",
								new Class[] { Long.class,String.class,Date.class, Long.class, Long.class });
						ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
								new Object[] { entity.getContId(),entity.getContActive().toString(),
										entity.getContTerminationDate(), empId, orgId });
					}
				} catch (Exception e) {
					throw new FrameworkException("Exception in Objection for finding reference Number  :" + e);
				}

			} catch (Exception e) {

				LOGGER.error("Error While updating flags for contract: " + e.getMessage(), e);
			}
		}
		
		ContractMastEntity masEntity = contractAgreementRepository.save(entity);

		// D80049
		if (masEntity.getContNo() != null && !masEntity.getContNo().isEmpty() && masEntity.getLoaNo() != null
				&& !masEntity.getLoaNo().isEmpty()) {

			try {
				// iContractAgreementDao.updateContractFlag(orgId, masEntity.getLoaNo(), empId,
				// masEntity.getContId());

				Class<?> clazz = null;
				String serviceClassName = null;
				String deptCode = departmentService.getDeptCode(masEntity.getContDept());
				Object dynamicServiceInstance = null;
				try {
					serviceClassName = messageSource.getMessage(
							ApplicationSession.getInstance().getMessage("objection.lbl") + deptCode, new Object[] {},
							StringUtils.EMPTY, Locale.ENGLISH);
					if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
						clazz = ClassUtils.forName(serviceClassName,
								ApplicationContextProvider.getApplicationContext().getClassLoader());
						dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
								.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
						final Method method = ReflectionUtils.findMethod(clazz, "updateContractFlag",
								new Class[] { Long.class, String.class, Long.class, Long.class });
						ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
								new Object[] { orgId, masEntity.getLoaNo(), empId, masEntity.getContId() });
					}
				} catch (Exception e) {
					throw new FrameworkException("Exception in Objection for finding reference Number  :" + e);
				}

			} catch (Exception e) {

				LOGGER.error("Error While updating flags for contract: " + e.getMessage(), e);
			}

		}

		// 34300
		// To save the history of master and detail table
		try {
			ContractMastHistEntity contractMastHistEntity = new ContractMastHistEntity();
			contractMastHistEntity.setStatus(contractMastDTO.getHistFlag());
			auditService.createHistory(masEntity, contractMastHistEntity);
			List<Object> schemeHistoryList = new ArrayList<>();
			masEntity.getContractDetailList().forEach(masDet -> {
				ContractDetailHistEntity detailsHistory = new ContractDetailHistEntity();
				BeanUtils.copyProperties(masDet, detailsHistory);
				detailsHistory.setContId(masEntity.getContId());
				detailsHistory.setStatus(contractMastDTO.getHistFlag());
				schemeHistoryList.add(detailsHistory);
			});

			auditService.createHistoryForListObj(schemeHistoryList);
		} catch (Exception exception) {
			LOGGER.error("Exception occured when calling audit service  ", exception);
		}

		return prepareContractMasterDto(masEntity, contractMastDTO);
	}

	/**
	 * @param orgId
	 * @param dpDeptid
	 */
	private String getContractNo(final Long orgId, final Long dpDeptid, Date contractDate) {
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(contractDate);
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("COM", "TB_CONTRACT_MAST", "CONT_NO", orgId,
				MainetConstants.FlagC, financiaYear.getFaYear());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

		final String year[] = finacialYear.split("-");
		final String finYear = year[0].substring(2) + year[1];
		final String contractNo = finYear + MainetConstants.WINDOWS_SLASH + String.format("%04d", sequence);
		return contractNo;
	}

	private List<LookUp> getEmpBasedOnDesgnation(final Long desId, final Long orgId) {
		List<Object[]> list = null;

		List<LookUp> emplookupList = null;
		list = iEmployeeService.getAllEmpByDesignation(desId, orgId);
		emplookupList = new ArrayList<>();
		LookUp look = null;
		for (final Object[] oneEmp : list) {
			look = new LookUp();
			String name = null;
			look.setLookUpId(Long.parseLong((oneEmp[0].toString())));
			if (oneEmp[1] != null) {
				name = oneEmp[1].toString();
			}
			if (oneEmp[3] != null) {
				name = name + " " + oneEmp[3].toString();
			}
			look.setDescLangFirst(name);
			emplookupList.add(look);
		}
		return emplookupList;
	}

	public List<LookUp> getVenderNameOnVenderType(final Long venTypeId, final Long orgId) {
		List<Object[]> list = null;
		List<LookUp> venderlookupList = null;
		final Long statusId = CommonMasterUtility.getIdFromPrefixLookUpDesc("Active", "VSS", 1);
		list = getVenderList(orgId, venTypeId, statusId);
		venderlookupList = new ArrayList<>();
		LookUp look = null;
		for (final Object[] oneVender : list) {
			look = new LookUp();
			look.setLookUpId(Long.parseLong((oneVender[0].toString())));
			look.setDescLangFirst(oneVender[1].toString());
			venderlookupList.add(look);
		}
		return venderlookupList;
	}

	@Override
	@Transactional(readOnly = true)
	public String findContractMapedOrNot(final Long orgId, final Long contId) {

		return contractAgreementRepository.findContractMapedOrNot(orgId, contId);
	}

	private String uploadImage(final File uploadfile, final String fileNetPath, final String dirPath) {
		String bytestring = null;
		try {
			bytestring = base64.encodeToString(FileUtils.readFileToByteArray(uploadfile));
		} catch (final IOException e) {
			logger.error("Could not convert from base64 encoding: " + uploadfile, e);
		}
		String photoFileName = uploadfile.getName();
		if ((photoFileName != null) && photoFileName.contains("/")) {
			photoFileName = photoFileName.replace("/", "_");
		}
		final DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
		documentDetailsVO.setDocumentByteCode(bytestring);
		iFileUploadService.convertAndSaveFile(documentDetailsVO, fileNetPath, dirPath, photoFileName);
		return photoFileName;
	}

	@Override
	@Transactional(readOnly = true)
	public ContractAgreementSummaryDTO findByContractNo(final Long orgId, final String contNo) {

		final List<Object[]> venderNameList = iContractAgreementDao.findByContractNo(orgId, contNo);
		ContractAgreementSummaryDTO contractAgreementSummaryDTO = null;
		if (!CollectionUtils.isEmpty(venderNameList)) {
			for (final Object venObj[] : venderNameList) {
				contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
				contractAgreementSummaryDTO.setContId(Long.valueOf(venObj[0].toString()));
				contractAgreementSummaryDTO.setContNo(venObj[1].toString());
				contractAgreementSummaryDTO.setContDept(venObj[2].toString());
				contractAgreementSummaryDTO.setContDate(CommonUtility.dateToString((Date) venObj[3]));
				contractAgreementSummaryDTO.setContFromDate(CommonUtility.dateToString((Date) venObj[4]));
				contractAgreementSummaryDTO.setContToDate(CommonUtility.dateToString((Date) venObj[5]));
				contractAgreementSummaryDTO.setContp1Name(venObj[12].toString());
				contractAgreementSummaryDTO.setContp2Name(venObj[7].toString());
				contractAgreementSummaryDTO.setAddress(venObj[8] != null ? String.valueOf(venObj[8]) : null);
				contractAgreementSummaryDTO.setEmailId(venObj[9] != null ? String.valueOf(venObj[9]) : null);
				contractAgreementSummaryDTO.setMobileNo(String.valueOf(venObj[10]));
				contractAgreementSummaryDTO.setContDeptSc(venObj[13].toString());

				if (venObj[11] != null) {
					contractAgreementSummaryDTO
							.setContAmount(BigDecimal.valueOf((Double) venObj[11]).setScale(2, BigDecimal.ROUND_UP));
				}
				break;
			}
		}
		return contractAgreementSummaryDTO;

	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getBillDetailbyContAndMobile(String contNo, String mobileNo,
			Long orgId) {
		List<Object[]> venderNameList = new ArrayList<>();
		venderNameList = iContractAgreementDao.getBillDetailbyContAndMobile(contNo, mobileNo, orgId);
		return venderNameList;
	}

	@Override
	@Transactional
	public int updateContractMapFlag(Long contId, Long empId) {
		return contractAgreementRepository.updateContractMapFlag(contId, empId);

	}

	@Override
	public ContractMastDTO getLoaDetailsByLoaNumber(Long orgId, String laoNumber, String deptShortCode) {

		String deptRestCallKey = ApplicationSession.getInstance().getMessage(deptShortCode + "_CONTRACT_DETAILS");
		ContractMastDTO contractMastDTO = null;
		ResponseEntity<?> responseEntity = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("referenceId", laoNumber);
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(deptRestCallKey, requestParam);
		try {
			responseEntity = RestClient.callRestTemplateClient(contractMastDTO, uri.toString());
			HttpStatus statusCode = responseEntity.getStatusCode();
			if (statusCode == HttpStatus.OK) {
				contractMastDTO = (ContractMastDTO) RestClient.castResponse(responseEntity, ContractMastDTO.class);
			}
		} catch (Exception ex) {
			logger.error("Exception occured while fetching contract details : " + ex);
			return contractMastDTO;
		}
		return contractMastDTO;
	}

	@Override
	public ContractDetailDTO getContractDetail(Long contId) {
		ContractDetailDTO contractDetailDTO = null;
		ContractDetailEntity contractDetEntity = contractAgreementRepository.findContractDetail(contId,
				MainetConstants.FlagY);
		if (contractDetEntity != null) {
			contractDetailDTO = new ContractDetailDTO();
			BeanUtils.copyProperties(contractDetEntity, contractDetailDTO);
		}
		return contractDetailDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public ContractMastDTO getContractByLoaNo(String loaNo, Long orgId) {
		ContractMastDTO masDto = null;
		ContractMastEntity masEntity = contractAgreementRepository.getContractByLoaNo(loaNo.toString(), orgId);
		if (masEntity != null) {
			masDto = new ContractMastDTO();
			BeanUtils.copyProperties(masEntity, masDto);
		}
		return masDto;
	}

	@Override
	@Transactional
	public void deleteContractDocFileById(List<Long> enclosureRemoveById, Long empId) {
		iAttachDocsDao.updateRecord(enclosureRemoveById, empId, MainetConstants.RnLCommon.Flag_D);
	}

	@Override
	public List<ContractAgreementSummaryDTO> getContractAgreementFilterData(Long orgId, String contractNo,
			String contractDate, String viewClosedCon) {
		List<ContractAgreementSummaryDTO> contractAgreementSummaryDTOList = new ArrayList<>();
		List<Object[]> contractAgreementList = null;
		try {
			Date contractFormDate = null;
			if (contractDate != null && !contractDate.isEmpty()) {
				contractFormDate = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE).parse(contractDate);
			}
			if (orgId != null) {
			}
			contractAgreementList = iContractAgreementDao.getContractFilterData(orgId, contractNo, contractFormDate,
					viewClosedCon);
			if (contractAgreementList != null && !contractAgreementList.isEmpty()) {
				ContractAgreementSummaryDTO contractAgreementSummaryDTO = null;

				for (final Object venObj[] : contractAgreementList) {
					contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
					contractAgreementSummaryDTO.setContId(Long.valueOf(venObj[0].toString()));

					contractAgreementSummaryDTO.setContTndNo(venObj[1].toString());
					if (venObj[2] != null) {
						contractAgreementSummaryDTO.setContLoaNo(venObj[2].toString());
					}
					contractAgreementSummaryDTO.setContDate(CommonUtility.dateToString((Date) venObj[3]));
					contractAgreementSummaryDTO.setContFromDate(CommonUtility.dateToString((Date) venObj[4]));
					contractAgreementSummaryDTO.setContToDate(CommonUtility.dateToString((Date) venObj[5]));

					contractAgreementSummaryDTOList.add(contractAgreementSummaryDTO);
				}
			}
		} catch (final Exception e) {
			logger.info(
					"getContractAgreementFilterData(Long orgId,String contractNo,String contractDate, Long deptId, Long venderId, String viewClosedCon) end");
			throw new FrameworkException("Problem in getContractAgreementFilterData" + e);

		}
		return contractAgreementSummaryDTOList;
	}

	private ContractMastDTO prepareContractMasterDto(ContractMastEntity masEntity, ContractMastDTO mastDTO) {
		int count = 0;
		int reCount = 0;
		mastDTO.setContId(masEntity.getContId());
		BeanUtils.copyProperties(masEntity.getContractDetailList().get(0), mastDTO.getContractDetailList().get(0));

		for (ContractTermsDetailEntity termsEntity : masEntity.getContractTermsDetailList()) {
			BeanUtils.copyProperties(termsEntity, mastDTO.getContractTermsDetailList().get(count));
			mastDTO.getContractTermsDetailList().get(count).setConttId(termsEntity.getConttId());
			count++;
		}
		if (masEntity.getContractInstalmentDetailList() != null) {
			for (ContractInstalmentDetailEntity instlEntity : masEntity.getContractInstalmentDetailList()) {
				BeanUtils.copyProperties(instlEntity, mastDTO.getContractInstalmentDetailList().get(reCount));
				mastDTO.getContractInstalmentDetailList().get(reCount).setConitId(instlEntity.getConitId());
				reCount++;
			}
		}
		if (masEntity.getContNo() != null) {
			mastDTO.setContNo(masEntity.getContNo());
		}
		return mastDTO;
	}

	@Override
	@Transactional
	public TbAcVendormaster getVenderTypeIdByVenderId(Long orgId, Long venderId) {
		List<TbAcVendormasterEntity> venderMasterEntity = contractAgreementRepository.getVenderTypeOnVenderId(orgId,
				venderId);
		TbAcVendormaster tenderDto = new TbAcVendormaster();
		venderMasterEntity.forEach(list -> {
			tenderDto.setCpdVendortype(list.getCpdVendortype());
			tenderDto.setVmVendorid(list.getVmVendorid());
			tenderDto.setVmVendorname(list.getVmVendorname());
			tenderDto.setCpdVendorSubType(list.getCpdVendorSubType());
		});
		return tenderDto;
	}

	@Override
	public List<Object[]> getEstateList(Long orgId) {
		List<Object[]> list = contractAgreementRepository.getEstateList(orgId,MainetConstants.NUMBERS.ZERO);
		return list;

	}
	
	@Override
	public List<Object[]> getPropListByEstateId(Long orgId,Long estateId) {
		List<Object[]> list = contractAgreementRepository.getPropListByEstateId(orgId,estateId);
		return list;
	}
	
	@Override
	@Transactional
	public List<ContractAgreementSummaryDTO> getRLContractAgreementSummaryData(final Long orgId, final String contractNo,
			final String contractDate, final Long deptId, final String viewClosedCon,final String renewal,final Long estateId, final Long propId, final int langId) throws Exception {
		List<ContractAgreementSummaryDTO> contractAgreementSummaryDTOList = new ArrayList<>();
		List<Object[]> venderNameList = null;
		try {
			Date contractFormDate = null;
			if (contractDate != null && !contractDate.isEmpty()) {
				contractFormDate = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE).parse(contractDate);
			}
			if (orgId != null) {
				venderNameList = iContractAgreementDao.getRLContractAgreementSummaryData(orgId, contractNo, contractFormDate,
						deptId, viewClosedCon, renewal,estateId,propId);
			}
			if (venderNameList != null && !venderNameList.isEmpty()) {
				ContractAgreementSummaryDTO contractAgreementSummaryDTO = null;

				for (final Object venObj[] : venderNameList) {
					contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
					contractAgreementSummaryDTO.setContId(Long.valueOf(venObj[0].toString()));
					if (venObj[1] != null) {
						contractAgreementSummaryDTO.setContNo(venObj[1].toString());
					}
					contractAgreementSummaryDTO.setContDept(venObj[2].toString());
					contractAgreementSummaryDTO.setContDate(CommonUtility.dateToString((Date) venObj[3]));
					contractAgreementSummaryDTO.setContFromDate(CommonUtility.dateToString((Date) venObj[4]));
					contractAgreementSummaryDTO.setContToDate(CommonUtility.dateToString((Date) venObj[5]));
					contractAgreementSummaryDTO.setContp1Name(venObj[6].toString());
					contractAgreementSummaryDTO.setContp2Name(venObj[7].toString());
					if (venObj[8] != null) {
						contractAgreementSummaryDTO.setContTndNo(venObj[8].toString());
					}
					if (venObj[9] != null) {
						contractAgreementSummaryDTO.setContLoaNo(venObj[9].toString());
					}

					if (venObj[10] != null) {
						contractAgreementSummaryDTO.setContAmount(new BigDecimal(venObj[10].toString()));
					}
					if (langId > 0 && langId == MainetConstants.DEFAULT_LANGUAGE_ID) {
						if (venObj[12] != null) {
							contractAgreementSummaryDTO.setEstateName(venObj[12].toString());
						}
					}else {
						if (venObj[13] != null) {
							contractAgreementSummaryDTO.setEstateName(venObj[13].toString());
						}
					}
					if (venObj[16] != null) {
						contractAgreementSummaryDTO.setPropName(venObj[16].toString());
					}
					contractAgreementSummaryDTOList.add(contractAgreementSummaryDTO);
				}
			}
		} catch (final Exception e) {
			logger.info(
					"getContractAgreementSummaryData(Long orgId,String contractNo,String contractDate, Long deptId, Long venderId, String viewClosedCon) end");
			throw new Exception("Problem in getContractAgreementSummaryData" + e);

		}
		return contractAgreementSummaryDTOList;
	}

	@Override
	public List<LookUp> getAllDesgBasedOnDept(Long deptId, Long orgId) {
		List<Object[]> list = null;
		List<LookUp> dsglookupList = null;
		list = contractAgreementRepository.getAllDesgBasedOnDept(deptId,orgId);
		dsglookupList = new ArrayList<>();
		LookUp look = null;
		for (final Object[] oneDesg : list) {
			look = new LookUp();
			look.setLookUpId(Long.parseLong(oneDesg[0].toString()));
			if (oneDesg[1] != null) {
				look.setDescLangFirst(oneDesg[1].toString());
			}
			if (oneDesg[2] != null) {
				look.setDescLangSecond(oneDesg[2].toString());
			}
			dsglookupList.add(look);
		}
		return dsglookupList;
	}
	
	@Override
	@Transactional
	public List<Object[]> getBillDetailbyContAndMobileWorkFlow(String contNo, String wfRefno, Long orgId) {
		List<Object[]> venderNameList = new ArrayList<>();
		venderNameList = contractAgreementRepository.getBillDetailbyContAndMobileWorkFlow(contNo, Long.parseLong(wfRefno), orgId);
		return venderNameList;
	}

}
