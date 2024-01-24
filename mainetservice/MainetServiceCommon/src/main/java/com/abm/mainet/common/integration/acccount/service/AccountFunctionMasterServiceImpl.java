package com.abm.mainet.common.integration.acccount.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dao.AccountFunctionMasterDao;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionMasterBean;
import com.abm.mainet.common.integration.acccount.hrms.fieldmaster.soap.jaxws.client.FieldMaster;
import com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.Functionmaster;
import com.abm.mainet.common.integration.acccount.repository.TbAcFunctionMasterJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Implementation of TbAcFunctionMasterService
 */
@Component
@Transactional
public class AccountFunctionMasterServiceImpl implements AccountFunctionMasterService {

	@Resource
	private TbAcFunctionMasterJpaRepository tbAcFunctionMasterJpaRepository;

	@Resource
	private AccountFunctionMasterDao accountFunctionMasterDao;

	@Resource
	private FunctionMasterSoapWSProvisionService functionMasterSoapWSProvisionService;

	private static final String DATE_COVERT_EXCEPTION = "Exception while converting date to XMLGregorianCalendar :";

	public TbAcFunctionMasterJpaRepository getTbAcFunctionMasterJpaRepository() {
		return tbAcFunctionMasterJpaRepository;
	}

	public void setTbAcFunctionMasterJpaRepository(
			final TbAcFunctionMasterJpaRepository tbAcFunctionMasterJpaRepository) {
		this.tbAcFunctionMasterJpaRepository = tbAcFunctionMasterJpaRepository;
	}

	@Override
	@Transactional(readOnly=true)
	public AccountFunctionMasterEntity getFunctionById(final Long functionId) {
		return tbAcFunctionMasterJpaRepository.findOne(functionId);
	}

	@Override
	@Transactional
	public void saveUpdateFunctions(final List<AccountFunctionMasterEntity> entities) {
		tbAcFunctionMasterJpaRepository.save(entities);
	}

	@Override
	@Transactional
	public void saveUpdateFunction(final AccountFunctionMasterEntity function) {
		AccountFunctionMasterEntity finalUpdateEntity = tbAcFunctionMasterJpaRepository.save(function);
		updateFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(finalUpdateEntity);
	}

	private void updateFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(
			AccountFunctionMasterEntity finalUpdateEntity) {
		// TODO Auto-generated method stub
		try {
			Functionmaster functionMaster = new Functionmaster();
			functionMaster.setORGID(finalUpdateEntity.getOrgid());
			// functionMaster.setStatus("Status1202576113");
			if (finalUpdateEntity.getTbAcCodingstructureDet() != null
					&& (Long) finalUpdateEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
				functionMaster.setCODCOFDETID(finalUpdateEntity.getTbAcCodingstructureDet().getCodcofdetId());
			}
			// functionMaster.setModifiedIn("ModifiedIn-357602517");
			functionMaster.setCreatedBy(finalUpdateEntity.getUserId().toString());
			if (finalUpdateEntity.getFunctionParentId() != null
					&& finalUpdateEntity.getFunctionParentId().getFunctionId() != null) {
				functionMaster.setFUNCTIONPARENTID(finalUpdateEntity.getFunctionParentId().getFunctionId());
			}
			// functionMaster.setSheetId("SheetId582446786");
			if (finalUpdateEntity.getLmoddate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalUpdateEntity.getLmoddate());
				functionMaster.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// functionMaster.setMetadata("Metadata-1874294745");
			functionMaster.setFUNCTIONSTATUS(finalUpdateEntity.getFunctionStatusCpdId());
			functionMaster.setSheetName(finalUpdateEntity.getFunctionId().toString());
			if (finalUpdateEntity.getUpdatedBy() != null) {
				functionMaster.setModifiedBy(finalUpdateEntity.getUpdatedBy().toString());
			}
			functionMaster.setFUNCTIONID(finalUpdateEntity.getFunctionId());
			// functionMaster.setCaption("Caption765194629");
			// functionMaster.setAssignedTo("AssignedTo1754909860");
			if (finalUpdateEntity.getUpdatedDate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalUpdateEntity.getUpdatedDate());
				functionMaster.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// functionMaster.setTenant("Tenant1435775442");
			functionMaster.setFUNCTIONCODE(finalUpdateEntity.getFunctionCode());
			functionMaster.setFunctionDesc(finalUpdateEntity.getFunctionDesc());
			functionMaster.setULBCode(finalUpdateEntity.getOrgid().toString());
			// functionMaster.setProcessInstance("ProcessInstance226517866");
			functionMaster.setFunctionCompcode(finalUpdateEntity.getFunctionCompcode());
			// functionMaster.setSheetMetadataName("SheetMetadataName-1606474469");
			functionMasterSoapWSProvisionService.updateFunctionMaster(functionMaster);

		} catch (DatatypeConfigurationException ex) {
			throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
		}
	}

	@Override
	@Transactional
	public void create(final AccountFunctionMasterBean masterBean, final TbAcCodingstructureDetEntity detailEntity,
			final Long orgId, final int langId, final Long empId) throws Exception {

		final List<Object[]> codcofDetIdForAllLvl = accountFunctionMasterDao
				.getCodCofigurationDetIdUsingLevel(masterBean.getCodconfigId());
		final Map<Integer, Integer> mapForAllLevels = new HashMap<>();

		if ((codcofDetIdForAllLvl != null) && !codcofDetIdForAllLvl.isEmpty()) {
			for (final Object[] obj : codcofDetIdForAllLvl) {
				mapForAllLevels.put(Integer.valueOf(obj[MainetConstants.ENGLISH].toString()),
						Integer.valueOf(obj[MainetConstants.EMPTY_LIST].toString()));
			}
		}

		final AccountFunctionMasterEntity accountPrimayHeadCodeEntity = fillDataForParentLevel(masterBean, orgId,
				langId, empId, mapForAllLevels);

		AccountFunctionMasterEntity finalEntity = tbAcFunctionMasterJpaRepository.save(accountPrimayHeadCodeEntity);
		insertFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(finalEntity);

	}

	public void insertFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(AccountFunctionMasterEntity finalEntity) {
		// TODO Auto-generated method stub
		try {
			Functionmaster functionMasterParent = new Functionmaster();
			functionMasterParent.setORGID(finalEntity.getOrgid());
			// functionMasterParent.setStatus("Status1202576113");
			if (finalEntity.getTbAcCodingstructureDet() != null
					&& (Long) finalEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
				functionMasterParent.setCODCOFDETID(finalEntity.getTbAcCodingstructureDet().getCodcofdetId());
			}
			// functionMasterParent.setModifiedIn("ModifiedIn-357602517");
			functionMasterParent.setCreatedBy(finalEntity.getUserId().toString());
			if (finalEntity.getFunctionParentId() != null
					&& finalEntity.getFunctionParentId().getFunctionId() != null) {
				functionMasterParent.setFUNCTIONPARENTID(finalEntity.getFunctionParentId().getFunctionId());
			}
			// functionMasterParent.setSheetId("SheetId582446786");
			if (finalEntity.getLmoddate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEntity.getLmoddate());
				functionMasterParent.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// functionMasterParent.setMetadata("Metadata-1874294745");
			functionMasterParent.setFUNCTIONSTATUS(finalEntity.getFunctionStatusCpdId());
			if(finalEntity.getFunctionId() != null) {
				functionMasterParent.setSheetName(finalEntity.getFunctionId().toString());
			}
			if (finalEntity.getUpdatedBy() != null) {
				functionMasterParent.setModifiedBy(finalEntity.getUpdatedBy().toString());
			}
			if(finalEntity.getFunctionId() != null) {
				functionMasterParent.setFUNCTIONID(finalEntity.getFunctionId());
			}
			// functionMasterParent.setCaption("Caption765194629");
			// functionMasterParent.setAssignedTo("AssignedTo1754909860");
			if (finalEntity.getUpdatedDate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEntity.getUpdatedDate());
				functionMasterParent.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// functionMasterParent.setTenant("Tenant1435775442");
			functionMasterParent.setFUNCTIONCODE(finalEntity.getFunctionCode());
			functionMasterParent.setFunctionDesc(finalEntity.getFunctionDesc());
			functionMasterParent.setULBCode(finalEntity.getOrgid().toString());
			// functionMasterParent.setProcessInstance("ProcessInstance226517866");
			functionMasterParent.setFunctionCompcode(finalEntity.getFunctionCompcode());
			// functionMasterParent.setSheetMetadataName("SheetMetadataName-1606474469");
			functionMasterSoapWSProvisionService.createFunctionMaster(functionMasterParent);
			if (finalEntity.getFunctionHierarchicalList() != null
					&& !finalEntity.getFunctionHierarchicalList().isEmpty()) {
				Set<AccountFunctionMasterEntity> childSetEntity = finalEntity.getFunctionHierarchicalList();
				for (AccountFunctionMasterEntity accountFunctionMasterEntity : childSetEntity) {
					Functionmaster functionMasterChild = new Functionmaster();
					functionMasterChild.setORGID(accountFunctionMasterEntity.getOrgid());
					// functionMasterChild.setStatus("Status1202576113");
					if (accountFunctionMasterEntity.getTbAcCodingstructureDet() != null
							&& (Long) accountFunctionMasterEntity.getTbAcCodingstructureDet()
									.getCodcofdetId() != null) {
						functionMasterChild.setCODCOFDETID(
								accountFunctionMasterEntity.getTbAcCodingstructureDet().getCodcofdetId());
					}
					// functionMasterChild.setModifiedIn("ModifiedIn-357602517");
					functionMasterChild.setCreatedBy(accountFunctionMasterEntity.getUserId().toString());
					if (accountFunctionMasterEntity.getFunctionParentId() != null
							&& accountFunctionMasterEntity.getFunctionParentId().getFunctionId() != null) {
						functionMasterChild
								.setFUNCTIONPARENTID(accountFunctionMasterEntity.getFunctionParentId().getFunctionId());
					}
					// functionMasterChild.setSheetId("SheetId582446786");
					if (accountFunctionMasterEntity.getLmoddate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFunctionMasterEntity.getLmoddate());
						functionMasterChild.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					// functionMasterChild.setMetadata("Metadata-1874294745");
					functionMasterChild.setFUNCTIONSTATUS(accountFunctionMasterEntity.getFunctionStatusCpdId());
					functionMasterChild.setSheetName(accountFunctionMasterEntity.getFunctionId().toString());
					if (accountFunctionMasterEntity.getUpdatedBy() != null) {
						functionMasterChild.setModifiedBy(accountFunctionMasterEntity.getUpdatedBy().toString());
					}
					functionMasterChild.setFUNCTIONID(accountFunctionMasterEntity.getFunctionId());
					// functionMasterChild.setCaption("Caption765194629");
					// functionMasterChild.setAssignedTo("AssignedTo1754909860");
					if (accountFunctionMasterEntity.getUpdatedDate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFunctionMasterEntity.getUpdatedDate());
						functionMasterChild.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					// functionMasterChild.setTenant("Tenant1435775442");
					functionMasterChild.setFUNCTIONCODE(accountFunctionMasterEntity.getFunctionCode());
					functionMasterChild.setFunctionDesc(accountFunctionMasterEntity.getFunctionDesc());
					functionMasterChild.setULBCode(accountFunctionMasterEntity.getOrgid().toString());
					// functionMasterChild.setProcessInstance("ProcessInstance226517866");
					functionMasterChild.setFunctionCompcode(accountFunctionMasterEntity.getFunctionCompcode());
					// functionMasterChild.setSheetMetadataName("SheetMetadataName-1606474469");
					functionMasterSoapWSProvisionService.createFunctionMaster(functionMasterChild);
					
				}
			}
		} catch (DatatypeConfigurationException ex) {
			throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
		}
	}

	private AccountFunctionMasterEntity fillDataForParentLevel(final AccountFunctionMasterBean functionMasterBean,
			final Long orgId, final int langId, final Long empId, final Map<Integer, Integer> mapForAllLevels) {
		TbAcCodingstructureDetEntity entity;
		final AccountFunctionMasterEntity functionMasterEntity = new AccountFunctionMasterEntity();

		functionMasterEntity.setFunctionDesc(functionMasterBean.getParentDesc());
		functionMasterEntity.setFunctionCode(functionMasterBean.getParentCode());
		functionMasterEntity.setFunctionCompcode(functionMasterBean.getParentFinalCode());
		functionMasterEntity.setFunctionParentId(null);
		functionMasterEntity.setOrgid(orgId);
		functionMasterEntity.setLgIpMac(functionMasterBean.getLgIpMac());
		functionMasterEntity.setFunctionStatusCpdId(functionMasterBean.getFunctionStatusCpdId());
		functionMasterEntity.setUserId(empId);
		functionMasterEntity.setFunctionLevel("1");
		entity = new TbAcCodingstructureDetEntity();
		entity.setCodcofdetId(Long.valueOf(mapForAllLevels.get(1)));
		functionMasterEntity.setTbAcCodingstructureDet(entity);
		functionMasterEntity.setLmoddate(new Date());
		functionMasterBean.setParentLevel("1");

		final Set<AccountFunctionMasterEntity> set = new HashSet<>();

		ArrayList<AccountFunctionDto> newList = null;

		newList = new ArrayList<>(functionMasterBean.getListDto());
		newList = new ArrayList<>(functionMasterBean.getListDto());

		final List<AccountFunctionMasterEntity> addedElementList = new ArrayList<>();
		addedElementList.add(functionMasterEntity);
		functionMasterEntity
				.setFunctionHierarchicalList(addRecordsRecursively(newList, functionMasterBean.getParentCode(),
						functionMasterBean.getParentLevel(), functionMasterBean.getParentFinalCode(), null, orgId,
						langId, empId, functionMasterEntity, mapForAllLevels, set, addedElementList));

		return functionMasterEntity;

	}

	public Set<AccountFunctionMasterEntity> addRecordsRecursively(final ArrayList<AccountFunctionDto> list,
			final String parentCode, final String parentLevel, final String parentFinalCode, final Long parentId,
			final Long orgId, final int langId, final Long empId, final AccountFunctionMasterEntity parentEntity,
			final Map<Integer, Integer> mapForAllLevels, final Set<AccountFunctionMasterEntity> set,
			final List<AccountFunctionMasterEntity> addedElementList) {

		AccountFunctionMasterEntity childEntity = null;
		TbAcCodingstructureDetEntity entity = null;
		AccountFunctionMasterEntity parentEntity1 = null;
		String childParentFinalCode = null;
		String[] strArray = null;

		final Iterator itr = list.iterator();
		while (itr.hasNext()) {
			final AccountFunctionDto dto = (AccountFunctionDto) itr.next();

			childEntity = new AccountFunctionMasterEntity();

			AccountFunctionMasterEntity pEntity = null;
			final AccountFunctionMasterEntity funEntity = new AccountFunctionMasterEntity();
			funEntity.setFunctionCode(dto.getChildParentCode());
			funEntity.setOrgid(orgId);
			funEntity.setFunctionLevel(dto.getChildParentLevel());
			if (addedElementList.contains(funEntity)) {
				pEntity = addedElementList.get(addedElementList.indexOf(funEntity));
			}

			if (pEntity != null) {

				strArray = dto.getChildFinalCode().split(MainetConstants.HYPHEN);
				for (int i = 0; i < (strArray.length - 1); i++) {
					if (childParentFinalCode == null) {
						childParentFinalCode = strArray[i];
					} else {
						childParentFinalCode += MainetConstants.HYPHEN + strArray[i];
					}
				}

				if (pEntity.getFunctionCompcode().equals(childParentFinalCode)) {
					childEntity.setFunctionDesc(dto.getChildDesc());
					childEntity.setFunctionCode(dto.getChildCode());
					childEntity.setFunctionCompcode(dto.getChildFinalCode());
					childEntity.setFunctionStatusCpdId(getActiveStatusId());
					childEntity.setFunctionParentId(pEntity);
					childEntity.setLgIpMac(parentEntity.getLgIpMac());
					childEntity.setOrgid(orgId);

					childEntity.setUserId(empId);
					entity = new TbAcCodingstructureDetEntity();
					entity.setCodcofdetId(mapForAllLevels.get(Integer.parseInt(dto.getChildLevel())));
					childEntity.setTbAcCodingstructureDet(entity);
					childEntity.setLmoddate(new Date());
					childEntity.setFunctionLevel(dto.getChildLevel());
					parentEntity1 = new AccountFunctionMasterEntity();
					parentEntity1.setFunctionId(childEntity.getFunctionId());
					set.add(childEntity);
					if (!set.isEmpty()) {
						addedElementList.add(childEntity);
						itr.remove();
						childEntity.setFunctionHierarchicalList(addRecordsRecursively(list, dto.getChildCode(),
								dto.getChildLevel(), dto.getChildFinalCode(), childEntity.getFunctionId(), orgId,
								langId, empId, childEntity, mapForAllLevels, set, addedElementList));
					}
				}

			}

		}

		return set;
	}

	public Long getActiveStatusId() {
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.LookUp.ACN, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		return activeStatusId;
	}

	@Override
	@Transactional
	public AccountFunctionMasterEntity saveEditedData(final AccountFunctionMasterBean functionMasterBean, final Long orgId, final int langId,
			final Long empId) throws Exception {
		AccountFunctionMasterEntity existParentEntity;
		existParentEntity = accountFunctionMasterDao.getParentDetailsUsingFunctionId(functionMasterBean);

		final List<Object[]> codcofDetIdForAllLvl = accountFunctionMasterDao
				.getCodCofigurationDetIdUsingLevel(functionMasterBean.getCodconfigId());
		final Map<Integer, Integer> mapForAllLevels = new HashMap<>();

		if ((codcofDetIdForAllLvl != null) && !codcofDetIdForAllLvl.isEmpty()) {
			for (final Object[] obj : codcofDetIdForAllLvl) {
				mapForAllLevels.put(Integer.valueOf(obj[MainetConstants.ENGLISH].toString()),
						Integer.valueOf(obj[MainetConstants.EMPTY_LIST].toString()));
			}
		}
		String[] strArray = null;
		String childParentFinalCode = "";
		existParentEntity.setFunctionDesc(functionMasterBean.getParentDesc());
		for (final AccountFunctionDto dto : functionMasterBean.getListDto()) {

			strArray = dto.getChildFinalCode().split(MainetConstants.HYPHEN);
			for (int i = 0; i < (strArray.length - 1); i++) {
				if (childParentFinalCode == null) {
					childParentFinalCode = strArray[i];
				} else {
					childParentFinalCode += MainetConstants.HYPHEN + strArray[i];
				}
			}

			traverseFirstNode(existParentEntity, functionMasterBean, childParentFinalCode, dto, mapForAllLevels, orgId,
					langId, empId);

		}

		/*for (AccountFunctionMasterEntity functionSecondEntity : existParentEntity.getFunctionHierarchicalList()) {
			if (functionSecondEntity.getFunctionId() != null) {
				updateFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(functionSecondEntity);
			} else {
				insertFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(functionSecondEntity);
			}
			for (AccountFunctionMasterEntity functionThirdEntity : functionSecondEntity.getFunctionHierarchicalList()) {
				if (functionThirdEntity.getFunctionId() != null) {
					updateFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(functionThirdEntity);
				} else {
					insertFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(functionThirdEntity);
				}
			}
		}
		updateFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(existParentEntity);*/
		return existParentEntity;
	}

	private void traverseFirstNode(final AccountFunctionMasterEntity existParentEntity,
			final AccountFunctionMasterBean functionMasterBean, final String dtocompositeCode,
			final AccountFunctionDto dto, final Map<Integer, Integer> mapForAllLevels, final Long orgId,
			final int langId, final Long empId) {

		TbAcCodingstructureDetEntity codingStructerDetEntity = null;
		final Set<AccountFunctionMasterEntity> set = existParentEntity.getFunctionHierarchicalList();
		final AccountFunctionMasterEntity childEntity = new AccountFunctionMasterEntity();
		childEntity.setFunctionCode(dto.getChildCode());
		if (!existParentEntity.getFunctionHierarchicalList().contains(childEntity)
				&& dto.getChildParentCode().equals(existParentEntity.getFunctionCode())) {
			childEntity.setFunctionDesc(dto.getChildDesc());
			childEntity.setFunctionCode(dto.getChildCode());
			childEntity.setFunctionCompcode(dto.getChildFinalCode());
			childEntity.setFunctionStatusCpdId(getActiveStatusId());
			childEntity.setFunctionParentId(existParentEntity);
			childEntity.setOrgid(orgId);

			childEntity.setUserId(empId);
			childEntity.setLgIpMac(existParentEntity.getLgIpMac());
			codingStructerDetEntity = new TbAcCodingstructureDetEntity();
			codingStructerDetEntity
					.setCodcofdetId(Long.valueOf(mapForAllLevels.get(Integer.parseInt(dto.getChildLevel()))));
			childEntity.setTbAcCodingstructureDet(codingStructerDetEntity);
			childEntity.setLmoddate(new Date());
			set.add(childEntity);
		} else if (existParentEntity.getFunctionHierarchicalList().contains(childEntity)) {
			for (final AccountFunctionMasterEntity existEntity : existParentEntity.getFunctionHierarchicalList()) {
				if (existEntity.getFunctionCompcode().equals(dto.getChildFinalCode())) {
					if (dto.getChildDesc() != null) {
						existEntity.setFunctionDesc(dto.getChildDesc());
						existEntity.setFunctionStatusCpdId(dto.getChildFunctionStatus());
					}
				}
			}
		}
		existParentEntity.setFunctionHierarchicalList(set);

		if (!existParentEntity.getFunctionHierarchicalList().isEmpty()) {
			recursiveMethodCallToTravellTreeNode(existParentEntity.getFunctionHierarchicalList(), functionMasterBean,
					dtocompositeCode, dto, mapForAllLevels, orgId, langId, empId);
		}
	}

	public void recursiveMethodCallToTravellTreeNode(final Set<AccountFunctionMasterEntity> listOfExistParentEntity,
			final AccountFunctionMasterBean functionMasterBean, final String dtocompositeCode,
			final AccountFunctionDto dto, final Map<Integer, Integer> mapForAllLevels, final Long orgId,
			final int langId, final Long empId) {
		TbAcCodingstructureDetEntity codingStructerDetEntity = null;
		Set<AccountFunctionMasterEntity> set = null;
		for (final AccountFunctionMasterEntity entity : listOfExistParentEntity) {
			set = entity.getFunctionHierarchicalList();
			final AccountFunctionMasterEntity childEntity = new AccountFunctionMasterEntity();
			childEntity.setFunctionCode(dto.getChildCode());
			if (!entity.getFunctionHierarchicalList().contains(childEntity)
					&& dto.getChildParentCode().equals(entity.getFunctionCompcode())) {
				childEntity.setFunctionDesc(dto.getChildDesc());
				childEntity.setFunctionCode(dto.getChildCode());
				childEntity.setFunctionStatusCpdId(getActiveStatusId());
				childEntity.setFunctionCompcode(dto.getChildFinalCode());
				childEntity.setFunctionParentId(entity);
				childEntity.setOrgid(orgId);

				childEntity.setUserId(empId);
				childEntity.setLgIpMac(functionMasterBean.getLgIpMac());
				codingStructerDetEntity = new TbAcCodingstructureDetEntity();
				codingStructerDetEntity
						.setCodcofdetId(Long.valueOf(mapForAllLevels.get(Integer.parseInt(dto.getChildLevel()))));
				childEntity.setTbAcCodingstructureDet(codingStructerDetEntity);
				childEntity.setLmoddate(new Date());
				set.add(childEntity);
			} else if (entity.getFunctionHierarchicalList().contains(childEntity)) {
				for (final AccountFunctionMasterEntity existEntity : entity.getFunctionHierarchicalList()) {
					if (existEntity.getFunctionCompcode().equals(dto.getChildFinalCode())) {
						if (dto.getChildDesc() != null) {
							existEntity.setFunctionDesc(dto.getChildDesc());
							existEntity.setFunctionStatusCpdId(dto.getChildFunctionStatus());
						}
					}
				}
			}
			entity.setFunctionHierarchicalList(set);
			if (!entity.getFunctionHierarchicalList().isEmpty()) {
				recursiveMethodCallToTravellTreeNode(entity.getFunctionHierarchicalList(), functionMasterBean,
						dtocompositeCode, dto, mapForAllLevels, orgId, langId, empId);
			}
		}

	}

	@Override
	@Transactional(readOnly=true)
	public AccountFunctionMasterBean getDetailsUsingFunctionId(final AccountFunctionMasterBean tbAcFunctionMaster)
			throws Exception {
		System.out.println("In service");

		final AccountFunctionMasterBean fieldBean = new AccountFunctionMasterBean();
		final AccountFunctionMasterEntity parentEntity = accountFunctionMasterDao
				.getParentDetailsUsingFunctionId(tbAcFunctionMaster);
		final List<AccountFunctionDto> listOfDto = new ArrayList<>();
		fieldBean.setParentCode(parentEntity.getFunctionCode());
		fieldBean.setParentDesc(parentEntity.getFunctionDesc());
		fieldBean.setParentFinalCode(parentEntity.getFunctionCompcode());
		fieldBean.setParentLevel(parentEntity.getTbAcCodingstructureDet().getCodDescription());
		fieldBean.setFunctionId(parentEntity.getFunctionId());
		if ((parentEntity.getFunctionHierarchicalList() != null)
				&& !parentEntity.getFunctionHierarchicalList().isEmpty()) {
			addFunction(listOfDto, parentEntity.getFunctionHierarchicalList(), parentEntity.getFunctionCode());
		}

		fieldBean.setListDto(listOfDto);
		return fieldBean;
	}

	public void addFunction(final List<AccountFunctionDto> fields,
			final Set<AccountFunctionMasterEntity> fieldHierarchicalList, final String pCode) {
		if ((fieldHierarchicalList != null) && !fieldHierarchicalList.isEmpty()) {
			AccountFunctionDto dto = null;
			for (final AccountFunctionMasterEntity entry : fieldHierarchicalList) {
				dto = new AccountFunctionDto();
				dto.setChildCode(entry.getFunctionCode());
				dto.setChildFunId(entry.getFunctionId());
				dto.setChildParentCode(pCode);
				dto.setChildFunctionStatus(entry.getFunctionStatusCpdId());
				dto.setChildDesc(entry.getFunctionDesc());
				dto.setChildFinalCode(entry.getFunctionCompcode());

				if (entry.getTbAcCodingstructureDet() != null) {
					dto.setChildLevel(entry.getTbAcCodingstructureDet().getCodLevel().toString());
					final Long codLevel = entry.getTbAcCodingstructureDet().getCodLevel();
					if ((codLevel != null) && (codLevel > 1l)) {
						final Long k = codLevel - 1;
						dto.setChildParentLevel(k.toString());
					}
				}
				fields.add(dto);
				if ((entry.getFunctionHierarchicalList() != null) && !entry.getFunctionHierarchicalList().isEmpty()) {
					addFunction(fields, entry.getFunctionHierarchicalList(), entry.getFunctionCode());

				}

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountFunctionMasterService#
	 * getAllParentLevelCodes()
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Integer> getAllParentLevelCodes(final Long orgId) {
		return accountFunctionMasterDao.getAllParentLevelCodes(orgId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountFunctionMasterService#
	 * findAllWithOrgId(long)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<AccountFunctionMasterBean> findAllWithOrgId(final long orgid) {
		final List<AccountFunctionMasterEntity> entities = accountFunctionMasterDao.findAllParentFunctions(orgid);
		final List<AccountFunctionMasterBean> beans = new ArrayList<>();
		for (final AccountFunctionMasterEntity tbAcFunctionMasterEntity : entities) {
			final AccountFunctionMasterBean bean = new AccountFunctionMasterBean();
			bean.setFunctionId(tbAcFunctionMasterEntity.getFunctionId());
			bean.setFunctionCode(tbAcFunctionMasterEntity.getFunctionCode());
			bean.setFunctionDesc(tbAcFunctionMasterEntity.getFunctionDesc());
			bean.setUpdatedDate(tbAcFunctionMasterEntity.getLmoddate());
			beans.add(bean);

		}
		return beans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountFunctionMasterService#
	 * getLastLevels(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Map<Long, String> getFunctionMasterLastLevels(final Long orgId) throws Exception {

		List<AccountFunctionMasterEntity> functionList = null;
		final LookUp lookup = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.ACCOUNT_MASTERS.FUNCTION_CPD_VALUE, MainetConstants.CMD);
		if (lookup != null) {
			functionList = accountFunctionMasterDao.getLastLevels(orgId, lookup.getLookUpId());
		}

		final Map<Long, String> map = new LinkedHashMap<>();
		if ((functionList != null) && !functionList.isEmpty()) {
			for (final AccountFunctionMasterEntity function : functionList) {
				map.put(function.getFunctionId(), function.getFunctionCompcode() + " - " + function.getFunctionDesc());
			}
		}

		return map;
	}

	@Override
	@Transactional(readOnly=true)
	public String getFunctionCode(final Long functionId) {

		final String functionMasterCode = tbAcFunctionMasterJpaRepository.getFunctionCode(functionId);

		return functionMasterCode;

	}

	@Override
	@Transactional(readOnly=true)
	public List<String> getAllCompositeCodeByOrgId(final Long orgId) {
		final List<String> functionMasterCodeList = tbAcFunctionMasterJpaRepository.getAllComposteCode(orgId);
		return functionMasterCodeList;
	}

	@Override
	@Transactional
	public Map<Long, String> getFunctionMasterStatusLastLevels(final Long orgId, final Organisation organisation,
			final int langId) throws Exception {

		List<AccountFunctionMasterEntity> functionList = null;
		final LookUp lookup = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.ACCOUNT_MASTERS.FUNCTION_CPD_VALUE, MainetConstants.CMD);
		if (lookup != null) {
			functionList = accountFunctionMasterDao.getLastLevels(orgId, lookup.getLookUpId());
		}

		final Map<Long, String> map = new LinkedHashMap<>();
		if ((functionList != null) && !functionList.isEmpty()) {
			for (final AccountFunctionMasterEntity function : functionList) {

				final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
						PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
				final Long lookUpStatusId = statusLookup.getLookUpId();
				if (lookUpStatusId.equals(function.getFunctionStatusCpdId())) {
					map.put(function.getFunctionId(),
							function.getFunctionCompcode() + " - " + function.getFunctionDesc());
				}
			}
		}
		return map;
	}
	
	@Override
	public void insertEditedFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(AccountFunctionMasterEntity finalEntity) {
		// TODO Auto-generated method stub
		try {
			Functionmaster functionMasterParent = new Functionmaster();
			functionMasterParent.setORGID(finalEntity.getOrgid());
			// functionMasterParent.setStatus("Status1202576113");
			if (finalEntity.getTbAcCodingstructureDet() != null
					&& (Long) finalEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
				functionMasterParent.setCODCOFDETID(finalEntity.getTbAcCodingstructureDet().getCodcofdetId());
			}
			// functionMasterParent.setModifiedIn("ModifiedIn-357602517");
			functionMasterParent.setCreatedBy(finalEntity.getUserId().toString());
			if (finalEntity.getFunctionParentId() != null
					&& finalEntity.getFunctionParentId().getFunctionId() != null) {
				functionMasterParent.setFUNCTIONPARENTID(finalEntity.getFunctionParentId().getFunctionId());
			}
			// functionMasterParent.setSheetId("SheetId582446786");
			if (finalEntity.getLmoddate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEntity.getLmoddate());
				functionMasterParent.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// functionMasterParent.setMetadata("Metadata-1874294745");
			functionMasterParent.setFUNCTIONSTATUS(finalEntity.getFunctionStatusCpdId());
			if(finalEntity.getFunctionId() != null) {
				functionMasterParent.setSheetName(finalEntity.getFunctionId().toString());
			}
			if (finalEntity.getUpdatedBy() != null) {
				functionMasterParent.setModifiedBy(finalEntity.getUpdatedBy().toString());
			}
			if(finalEntity.getFunctionId() != null) {
				functionMasterParent.setFUNCTIONID(finalEntity.getFunctionId());
			}
			// functionMasterParent.setCaption("Caption765194629");
			// functionMasterParent.setAssignedTo("AssignedTo1754909860");
			if (finalEntity.getUpdatedDate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEntity.getUpdatedDate());
				functionMasterParent.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// functionMasterParent.setTenant("Tenant1435775442");
			functionMasterParent.setFUNCTIONCODE(finalEntity.getFunctionCode());
			functionMasterParent.setFunctionDesc(finalEntity.getFunctionDesc());
			functionMasterParent.setULBCode(finalEntity.getOrgid().toString());
			// functionMasterParent.setProcessInstance("ProcessInstance226517866");
			functionMasterParent.setFunctionCompcode(finalEntity.getFunctionCompcode());
			// functionMasterParent.setSheetMetadataName("SheetMetadataName-1606474469");
			functionMasterSoapWSProvisionService.updateFunctionMaster(functionMasterParent);
			
			if (finalEntity.getFunctionHierarchicalList() != null
					&& !finalEntity.getFunctionHierarchicalList().isEmpty()) {
				Set<AccountFunctionMasterEntity> childSetEntity = finalEntity.getFunctionHierarchicalList();
				for (AccountFunctionMasterEntity accountFunctionMasterEntity : childSetEntity) {
					Functionmaster functionMasterChild = new Functionmaster();
					functionMasterChild.setORGID(accountFunctionMasterEntity.getOrgid());
					// functionMasterChild.setStatus("Status1202576113");
					if (accountFunctionMasterEntity.getTbAcCodingstructureDet() != null
							&& (Long) accountFunctionMasterEntity.getTbAcCodingstructureDet()
									.getCodcofdetId() != null) {
						functionMasterChild.setCODCOFDETID(
								accountFunctionMasterEntity.getTbAcCodingstructureDet().getCodcofdetId());
					}
					// functionMasterChild.setModifiedIn("ModifiedIn-357602517");
					functionMasterChild.setCreatedBy(accountFunctionMasterEntity.getUserId().toString());
					if (accountFunctionMasterEntity.getFunctionParentId() != null
							&& accountFunctionMasterEntity.getFunctionParentId().getFunctionId() != null) {
						functionMasterChild
								.setFUNCTIONPARENTID(accountFunctionMasterEntity.getFunctionParentId().getFunctionId());
					}
					// functionMasterChild.setSheetId("SheetId582446786");
					if (accountFunctionMasterEntity.getLmoddate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFunctionMasterEntity.getLmoddate());
						functionMasterChild.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					// functionMasterChild.setMetadata("Metadata-1874294745");
					functionMasterChild.setFUNCTIONSTATUS(accountFunctionMasterEntity.getFunctionStatusCpdId());
					functionMasterChild.setSheetName(accountFunctionMasterEntity.getFunctionId().toString());
					if (accountFunctionMasterEntity.getUpdatedBy() != null) {
						functionMasterChild.setModifiedBy(accountFunctionMasterEntity.getUpdatedBy().toString());
					}
					functionMasterChild.setFUNCTIONID(accountFunctionMasterEntity.getFunctionId());
					// functionMasterChild.setCaption("Caption765194629");
					// functionMasterChild.setAssignedTo("AssignedTo1754909860");
					if (accountFunctionMasterEntity.getUpdatedDate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFunctionMasterEntity.getUpdatedDate());
						functionMasterChild.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					// functionMasterChild.setTenant("Tenant1435775442");
					functionMasterChild.setFUNCTIONCODE(accountFunctionMasterEntity.getFunctionCode());
					functionMasterChild.setFunctionDesc(accountFunctionMasterEntity.getFunctionDesc());
					functionMasterChild.setULBCode(accountFunctionMasterEntity.getOrgid().toString());
					// functionMasterChild.setProcessInstance("ProcessInstance226517866");
					functionMasterChild.setFunctionCompcode(accountFunctionMasterEntity.getFunctionCompcode());
					// functionMasterChild.setSheetMetadataName("SheetMetadataName-1606474469");
					functionMasterSoapWSProvisionService.createFunctionMaster(functionMasterChild);

					if (accountFunctionMasterEntity.getFunctionHierarchicalList() != null
							&& !accountFunctionMasterEntity.getFunctionHierarchicalList().isEmpty()) {
						Set<AccountFunctionMasterEntity> childSetEntity2 = accountFunctionMasterEntity.getFunctionHierarchicalList();
						for (AccountFunctionMasterEntity accountFunctionMasterEntity2 : childSetEntity2) {
							Functionmaster functionMasterChild2 = new Functionmaster();
							functionMasterChild2.setORGID(accountFunctionMasterEntity2.getOrgid());
							// functionMasterChild2.setStatus("Status1202576113");
							if (accountFunctionMasterEntity2.getTbAcCodingstructureDet() != null
									&& (Long) accountFunctionMasterEntity2.getTbAcCodingstructureDet()
											.getCodcofdetId() != null) {
								functionMasterChild2.setCODCOFDETID(
										accountFunctionMasterEntity2.getTbAcCodingstructureDet().getCodcofdetId());
							}
							// functionMasterChild2.setModifiedIn("ModifiedIn-357602517");
							functionMasterChild2.setCreatedBy(accountFunctionMasterEntity2.getUserId().toString());
							if (accountFunctionMasterEntity2.getFunctionParentId() != null
									&& accountFunctionMasterEntity2.getFunctionParentId().getFunctionId() != null) {
								functionMasterChild2
										.setFUNCTIONPARENTID(accountFunctionMasterEntity2.getFunctionParentId().getFunctionId());
							}
							// functionMasterChild2.setSheetId("SheetId582446786");
							if (accountFunctionMasterEntity2.getLmoddate() != null) {
								GregorianCalendar cal = new GregorianCalendar();
								cal.setTime(accountFunctionMasterEntity2.getLmoddate());
								functionMasterChild2.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
							}
							// functionMasterChild2.setMetadata("Metadata-1874294745");
							functionMasterChild2.setFUNCTIONSTATUS(accountFunctionMasterEntity2.getFunctionStatusCpdId());
							functionMasterChild2.setSheetName(accountFunctionMasterEntity2.getFunctionId().toString());
							if (accountFunctionMasterEntity2.getUpdatedBy() != null) {
								functionMasterChild2.setModifiedBy(accountFunctionMasterEntity2.getUpdatedBy().toString());
							}
							functionMasterChild2.setFUNCTIONID(accountFunctionMasterEntity2.getFunctionId());
							// functionMasterChild2.setCaption("Caption765194629");
							// functionMasterChild2.setAssignedTo("AssignedTo1754909860");
							if (accountFunctionMasterEntity2.getUpdatedDate() != null) {
								GregorianCalendar cal = new GregorianCalendar();
								cal.setTime(accountFunctionMasterEntity2.getUpdatedDate());
								functionMasterChild2.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
							}
							// functionMasterChild2.setTenant("Tenant1435775442");
							functionMasterChild2.setFUNCTIONCODE(accountFunctionMasterEntity2.getFunctionCode());
							functionMasterChild2.setFunctionDesc(accountFunctionMasterEntity2.getFunctionDesc());
							functionMasterChild2.setULBCode(accountFunctionMasterEntity2.getOrgid().toString());
							// functionMasterChild2.setProcessInstance("ProcessInstance226517866");
							functionMasterChild2.setFunctionCompcode(accountFunctionMasterEntity2.getFunctionCompcode());
							// functionMasterChild2.setSheetMetadataName("SheetMetadataName-1606474469");
							functionMasterSoapWSProvisionService.createFunctionMaster(functionMasterChild2);

						}
					}
				}
			}
		} catch (DatatypeConfigurationException ex) {
			throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Map<Long, String> getFunctionCompositeCode(Organisation organisation, int langId) {
		List<AccountFunctionMasterEntity> functionList = null;
		final LookUp lookup = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.ACCOUNT_MASTERS.FUNCTION_CPD_VALUE, MainetConstants.CMD);
		if (lookup != null) {
			functionList = accountFunctionMasterDao.getLastLevels(organisation.getOrgid(), lookup.getLookUpId());
		}

		final Map<Long, String> map = new LinkedHashMap<>();
		if ((functionList != null) && !functionList.isEmpty()) {
			for (final AccountFunctionMasterEntity function : functionList) {

				final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
						PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
				final Long lookUpStatusId = statusLookup.getLookUpId();
				if (lookUpStatusId.equals(function.getFunctionStatusCpdId())) {
					map.put(function.getFunctionId(),
							function.getFunctionCompcode());
				}
			}
		}
		return map;
	}

	}


