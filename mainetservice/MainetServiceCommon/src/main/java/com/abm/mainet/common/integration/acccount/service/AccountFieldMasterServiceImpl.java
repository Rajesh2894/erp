package com.abm.mainet.common.integration.acccount.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dao.AccountFieldMasterDao;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.hrms.bankaccount.soap.jaxws.client.BankAccount;
import com.abm.mainet.common.integration.acccount.hrms.fieldmaster.soap.jaxws.client.FieldMaster;
import com.abm.mainet.common.integration.acccount.mapper.TbAcFieldMasterServiceMapper;
import com.abm.mainet.common.integration.acccount.repository.TbAcFieldMasterJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * Implementation of TbAcFieldMasterService
 */
@Component
@Transactional
public class AccountFieldMasterServiceImpl implements AccountFieldMasterService {

	@Resource
	private TbAcFieldMasterJpaRepository tbAcFieldMasterJpaRepository;

	@Resource
	private TbAcFieldMasterServiceMapper tbAcFieldMasterServiceMapper;

	@Resource
	private AccountFieldMasterDao accountFieldMasterDao;

	@Resource
	private FiledMasterSoapWSProvisionService filedMasterSoapWSProvisionService;

	private static final String DATE_COVERT_EXCEPTION = "Exception while converting date to XMLGregorianCalendar :";

	@Override
	@Transactional
	public void create(final AccountFieldMasterBean masterBean, final TbAcCodingstructureDetEntity detailEntity,
			final Long orgId, final int langId, final Long empId) throws Exception {

		final List<Object[]> codcofDetIdForAllLvl = accountFieldMasterDao
				.getCodCofigurationDetIdUsingLevel(masterBean.getCodconfigId());
		final Map<Integer, Integer> mapForAllLevels = new HashMap<>();

		if ((codcofDetIdForAllLvl != null) && !codcofDetIdForAllLvl.isEmpty()) {
			for (final Object[] obj : codcofDetIdForAllLvl) {
				mapForAllLevels.put(Integer.valueOf(obj[MainetConstants.ENGLISH].toString()),
						Integer.valueOf(obj[MainetConstants.EMPTY_LIST].toString()));
			}
		}

		final AccountFieldMasterEntity accountPrimayHeadCodeEntity = fillDataForParentLevel(masterBean, orgId, langId,
				empId, mapForAllLevels);

		AccountFieldMasterEntity finalEntity = tbAcFieldMasterJpaRepository.save(accountPrimayHeadCodeEntity);
		insertFiledMasterDataIntoPropertyTaxTableByUsingSoapWS(finalEntity);

	}

	private void insertFiledMasterDataIntoPropertyTaxTableByUsingSoapWS(AccountFieldMasterEntity finalEntity) {
		// TODO Auto-generated method stub
		try {

			FieldMaster fieldMasterParent = new FieldMaster();
			if (finalEntity.getFieldParentId() != null && finalEntity.getFieldParentId().getFieldId() != null) {
				fieldMasterParent.setFIELDPARENTID(finalEntity.getFieldParentId().getFieldId());
			}
			fieldMasterParent.setORGID(finalEntity.getOrgid());
			// fieldMasterParent.setStatus("Status1109329587");
			if (finalEntity.getTbAcCodingstructureDet() != null
					&& (Long) finalEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
				fieldMasterParent.setCODCOFDETID(finalEntity.getTbAcCodingstructureDet().getCodcofdetId());
			}
			// fieldMasterParent.setModifiedIn("ModifiedIn-1855756710");
			fieldMasterParent.setCreatedBy(finalEntity.getUserId().toString());
			fieldMasterParent.setFIELDID(finalEntity.getFieldId());
			// fieldMasterParent.setSheetId("SheetId650794529");
			if (finalEntity.getLmoddate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEntity.getLmoddate());
				fieldMasterParent.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// fieldMasterParent.setMetadata("Metadata1717362157");
			fieldMasterParent.setSheetName(finalEntity.getFieldId().toString());
			if (finalEntity.getUpdatedBy() != null) {
				fieldMasterParent.setModifiedBy(finalEntity.getUpdatedBy().toString());
			}
			// fieldMasterParent.setCaption("Caption-988679453");
			// fieldMasterParent.setAssignedTo("AssignedTo-1747152429");
			fieldMasterParent.setFIELDSTATUS(finalEntity.getFieldStatusCpdId());
			if (finalEntity.getUpdatedDate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEntity.getUpdatedDate());
				fieldMasterParent.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			fieldMasterParent.setFIELDDESC(finalEntity.getFieldDesc());
			// fieldMasterParent.setTenant("Tenant-170017305");
			fieldMasterParent.setFIELDCODE(finalEntity.getFieldCode());
			fieldMasterParent.setFIELDCOMPCODE(finalEntity.getFieldCompcode());
			fieldMasterParent.setULBCode(finalEntity.getOrgid().toString());
			// fieldMasterParent.setProcessInstance("ProcessInstance439577815");
			// fieldMasterParent.setSheetMetadataName("SheetMetadataName-1938542074");
			filedMasterSoapWSProvisionService.createFiledMaster(fieldMasterParent);

			if (finalEntity.getFieldHierarchicalList() != null && !finalEntity.getFieldHierarchicalList().isEmpty()) {
				Set<AccountFieldMasterEntity> childSetEntity = finalEntity.getFieldHierarchicalList();
				for (AccountFieldMasterEntity accountFieldMasterEntity : childSetEntity) {
					FieldMaster fieldMasterChild = new FieldMaster();
					if (accountFieldMasterEntity.getFieldParentId() != null
							&& accountFieldMasterEntity.getFieldParentId().getFieldId() != null) {
						fieldMasterChild.setFIELDPARENTID(accountFieldMasterEntity.getFieldParentId().getFieldId());
					}
					fieldMasterChild.setORGID(accountFieldMasterEntity.getOrgid());
					// fieldMasterChild.setStatus("Status1109329587");
					if (accountFieldMasterEntity.getTbAcCodingstructureDet() != null
							&& (Long) accountFieldMasterEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
						fieldMasterChild
								.setCODCOFDETID(accountFieldMasterEntity.getTbAcCodingstructureDet().getCodcofdetId());
					}
					// fieldMasterChild.setModifiedIn("ModifiedIn-1855756710");
					fieldMasterChild.setCreatedBy(accountFieldMasterEntity.getUserId().toString());
					fieldMasterChild.setFIELDID(accountFieldMasterEntity.getFieldId());
					// fieldMasterChild.setSheetId("SheetId650794529");
					if (accountFieldMasterEntity.getLmoddate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFieldMasterEntity.getLmoddate());
						fieldMasterChild.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					// fieldMasterChild.setMetadata("Metadata1717362157");
					fieldMasterChild.setSheetName(accountFieldMasterEntity.getFieldId().toString());
					if (accountFieldMasterEntity.getUpdatedBy() != null) {
						fieldMasterChild.setModifiedBy(accountFieldMasterEntity.getUpdatedBy().toString());
					}
					// fieldMasterChild.setCaption("Caption-988679453");
					// fieldMasterChild.setAssignedTo("AssignedTo-1747152429");
					fieldMasterChild.setFIELDSTATUS(accountFieldMasterEntity.getFieldStatusCpdId());
					if (accountFieldMasterEntity.getUpdatedDate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFieldMasterEntity.getUpdatedDate());
						fieldMasterChild.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					fieldMasterChild.setFIELDDESC(accountFieldMasterEntity.getFieldDesc());
					// fieldMasterChild.setTenant("Tenant-170017305");
					fieldMasterChild.setFIELDCODE(accountFieldMasterEntity.getFieldCode());
					fieldMasterChild.setFIELDCOMPCODE(accountFieldMasterEntity.getFieldCompcode());
					fieldMasterChild.setULBCode(accountFieldMasterEntity.getOrgid().toString());
					// fieldMasterChild.setProcessInstance("ProcessInstance439577815");
					// fieldMasterChild.setSheetMetadataName("SheetMetadataName-1938542074");
					filedMasterSoapWSProvisionService.createFiledMaster(fieldMasterChild);

				}
			}
		} catch (DatatypeConfigurationException ex) {
			throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
		}
	}

	private AccountFieldMasterEntity fillDataForParentLevel(final AccountFieldMasterBean fieldMasterBean,
			final Long orgId, final int langId, final Long empId, final Map<Integer, Integer> mapForAllLevels) {
		TbAcCodingstructureDetEntity entity;
		final AccountFieldMasterEntity fieldMasterEntity = new AccountFieldMasterEntity();

		fieldMasterEntity.setFieldDesc(fieldMasterBean.getParentDesc());
		fieldMasterEntity.setFieldCode(fieldMasterBean.getParentCode());
		fieldMasterEntity.setFieldCompcode(fieldMasterBean.getParentFinalCode());
		fieldMasterEntity.setFieldParentId(null);
		fieldMasterEntity.setOrgid(orgId);
		fieldMasterEntity.setLgIpMac(fieldMasterBean.getLgIpMac());

		fieldMasterEntity.setUserId(empId);
		fieldMasterEntity.setFieldStatusCpdId(fieldMasterBean.getFieldStatusCpdId());
		entity = new TbAcCodingstructureDetEntity();
		entity.setCodcofdetId(Long.valueOf(mapForAllLevels.get(1)));
		fieldMasterEntity.setTbAcCodingstructureDet(entity);
		fieldMasterEntity.setLmoddate(new Date());
		fieldMasterBean.setParentLevel("1");

		AccountFieldMasterEntity childEntity = null;
		final Set<AccountFieldMasterEntity> set = new HashSet<>();
		AccountFieldMasterEntity parentEntity = null;
		final List<AccountFieldDto> list = new ArrayList<>(fieldMasterBean.getListDto());
		ArrayList<AccountFieldDto> newList = null;
		for (final AccountFieldDto dto : list) {

			newList = new ArrayList<>(fieldMasterBean.getListDto());
			childEntity = new AccountFieldMasterEntity();
			if (dto.getChildParentCode().equals(fieldMasterBean.getParentCode())
					&& fieldMasterBean.getParentLevel().equals(dto.getChildParentLevelCode().toString())) {

				childEntity.setFieldDesc(dto.getChildDesc());
				childEntity.setFieldCode(dto.getChildCode());
				childEntity.setFieldStatusCpdId(dto.getChildFieldStatus());
				childEntity.setFieldCompcode(dto.getChildFinalCode());
				childEntity.setFieldParentId(fieldMasterEntity);
				childEntity.setOrgid(orgId);
				childEntity.setLgIpMac(fieldMasterBean.getLgIpMac());

				childEntity.setUserId(empId);
				entity = new TbAcCodingstructureDetEntity();
				entity.setCodcofdetId(
						Long.valueOf(mapForAllLevels.get(Integer.parseInt(dto.getChildLevelCode().toString()))));
				childEntity.setTbAcCodingstructureDet(entity);
				childEntity.setLmoddate(new Date());
				parentEntity = new AccountFieldMasterEntity();
				parentEntity.setFieldId(childEntity.getFieldId());
				newList = new ArrayList<>(fieldMasterBean.getListDto());
				childEntity.setFieldHierarchicalList(addRecordsRecursively(newList, dto.getChildCode(),
						dto.getChildLevelCode().toString(), dto.getChildFinalCode(), childEntity.getFieldId(), orgId,
						langId, empId, childEntity, mapForAllLevels));
				set.add(childEntity);
			}
		}
		fieldMasterEntity.setFieldHierarchicalList(set);

		return fieldMasterEntity;

	}

	public Set<AccountFieldMasterEntity> addRecordsRecursively(final ArrayList<AccountFieldDto> list,
			final String parentCode, final String parentLevel, final String parentFinalCode, final Long parentId,
			final Long orgId, final int langId, final Long empId, final AccountFieldMasterEntity parentEntity,
			final Map<Integer, Integer> mapForAllLevels) {
		final Set<AccountFieldMasterEntity> set = new HashSet<>();
		AccountFieldMasterEntity childEntity = null;
		TbAcCodingstructureDetEntity entity = null;
		AccountFieldMasterEntity parentEntity1 = null;
		for (final AccountFieldDto dto : list) {

			childEntity = new AccountFieldMasterEntity();

			if (dto.getChildParentCode().equals(parentCode)
					&& parentLevel.equals(dto.getChildParentLevelCode().toString())) {

				final String[] strArray = dto.getChildFinalCode().split(MainetConstants.HYPHEN);
				String childFinalCode = MainetConstants.BLANK;
				for (int i = 0; i < (strArray.length - 1); i++) {
					childFinalCode += strArray[i] + MainetConstants.HYPHEN;
				}
				childFinalCode.lastIndexOf(MainetConstants.HYPHEN);
				final StringBuffer sb = new StringBuffer(childFinalCode);
				sb.deleteCharAt(childFinalCode.lastIndexOf(MainetConstants.HYPHEN));

				if (parentFinalCode.equals(sb.toString())) {
					childEntity.setFieldDesc(dto.getChildDesc());
					childEntity.setFieldCode(dto.getChildCode());
					childEntity.setFieldCompcode(dto.getChildFinalCode());
					childEntity.setFieldParentId(parentEntity);
					childEntity.setOrgid(orgId);
					childEntity.setUserId(empId);
					entity = new TbAcCodingstructureDetEntity();
					entity.setCodcofdetId(mapForAllLevels.get(Integer.parseInt(dto.getChildLevelCode().toString())));
					childEntity.setTbAcCodingstructureDet(entity);
					childEntity.setLmoddate(new Date());
					parentEntity1 = new AccountFieldMasterEntity();
					parentEntity1.setFieldId(childEntity.getFieldId());
					set.add(childEntity);
					if (!set.isEmpty()) {
						childEntity.setFieldHierarchicalList(addRecordsRecursively(list, dto.getChildCode(),
								dto.getChildLevelCode().toString(), dto.getChildFinalCode(), childEntity.getFieldId(),
								orgId, langId, empId, childEntity, mapForAllLevels));
					}
				}
			}
		}

		return set;
	}

	@Override
	@Transactional
	public AccountFieldMasterEntity saveEditedData(final AccountFieldMasterBean fieldMasterBean, final Long orgId,
			final int langId, final Long empId) throws Exception {
		AccountFieldMasterEntity existParentEntity;
		existParentEntity = accountFieldMasterDao.getParentDetailsUsingFieldId(fieldMasterBean);

		final List<Object[]> codcofDetIdForAllLvl = accountFieldMasterDao
				.getCodCofigurationDetIdUsingLevel(fieldMasterBean.getCodconfigId());
		final Map<Integer, Integer> mapForAllLevels = new HashMap<>();

		if ((codcofDetIdForAllLvl != null) && !codcofDetIdForAllLvl.isEmpty()) {
			for (final Object[] obj : codcofDetIdForAllLvl) {
				mapForAllLevels.put(Integer.valueOf(obj[MainetConstants.ENGLISH].toString()),
						Integer.valueOf(obj[MainetConstants.EMPTY_LIST].toString()));
			}
		}
		String[] strArray = null;
		String childParentFinalCode = MainetConstants.BLANK;
		existParentEntity.setFieldDesc(fieldMasterBean.getParentDesc());
		for (final AccountFieldDto dto : fieldMasterBean.getListDto()) {
			strArray = dto.getChildFinalCode().split(MainetConstants.HYPHEN);

			for (int i = 0; i < (strArray.length - 1); i++) {
				childParentFinalCode += strArray[i] + MainetConstants.HYPHEN;
			}
			childParentFinalCode.lastIndexOf(MainetConstants.HYPHEN);
			final StringBuffer stringBuffer = new StringBuffer(childParentFinalCode);
			stringBuffer.deleteCharAt(childParentFinalCode.lastIndexOf(MainetConstants.HYPHEN));

			traverseFirstNode(existParentEntity, fieldMasterBean, stringBuffer.toString(), dto, mapForAllLevels, orgId,
					langId, empId);

		}
		return existParentEntity;
	}

	private void traverseFirstNode(final AccountFieldMasterEntity existParentEntity,
			final AccountFieldMasterBean fieldMasterBean, final String dtocompositeCode, final AccountFieldDto dto,
			final Map<Integer, Integer> mapForAllLevels, final Long orgId, final int langId, final Long empId) {

		TbAcCodingstructureDetEntity codingStructerDetEntity = null;
		final Set<AccountFieldMasterEntity> set = existParentEntity.getFieldHierarchicalList();
		final AccountFieldMasterEntity childEntity = new AccountFieldMasterEntity();
		childEntity.setFieldCode(dto.getChildCode());
		if (!existParentEntity.getFieldHierarchicalList().contains(childEntity)
				&& dto.getChildParentCode().equals(existParentEntity.getFieldCode())) {
			childEntity.setFieldDesc(dto.getChildDesc());
			childEntity.setFieldCode(dto.getChildCode());
			childEntity.setFieldStatusCpdId(dto.getChildFieldStatus());
			childEntity.setFieldCompcode(dto.getChildFinalCode());
			childEntity.setFieldParentId(existParentEntity);
			childEntity.setOrgid(orgId);
			childEntity.setUserId(empId);
			childEntity.setLgIpMac(existParentEntity.getLgIpMac());
			codingStructerDetEntity = new TbAcCodingstructureDetEntity();
			codingStructerDetEntity
					.setCodcofdetId(mapForAllLevels.get(Integer.parseInt(dto.getChildLevelCode().toString())));
			childEntity.setTbAcCodingstructureDet(codingStructerDetEntity);
			childEntity.setLmoddate(new Date());
			set.add(childEntity);
		} else if (existParentEntity.getFieldHierarchicalList().contains(childEntity)) {
			for (final AccountFieldMasterEntity existEntity : existParentEntity.getFieldHierarchicalList()) {
				if (existEntity.getFieldCompcode().equals(dto.getChildFinalCode())) {
					if (dto.getChildDesc() != null) {
						existEntity.setFieldDesc(dto.getChildDesc());
						existEntity.setFieldStatusCpdId(dto.getChildFieldStatus());
					}
				}
			}
		}
		existParentEntity.setFieldHierarchicalList(set);

		if (!existParentEntity.getFieldHierarchicalList().isEmpty()) {
			recursiveMethodCallToTravellTreeNode(existParentEntity.getFieldHierarchicalList(), fieldMasterBean,
					dtocompositeCode, dto, mapForAllLevels, orgId, langId, empId);
		}
	}

	public void recursiveMethodCallToTravellTreeNode(final Set<AccountFieldMasterEntity> listOfExistParentEntity,
			final AccountFieldMasterBean fieldMasterBean, final String dtocompositeCode, final AccountFieldDto dto,
			final Map<Integer, Integer> mapForAllLevels, final Long orgId, final int langId, final Long empId) {
		TbAcCodingstructureDetEntity codingStructerDetEntity = null;
		Set<AccountFieldMasterEntity> set = null;
		for (final AccountFieldMasterEntity entity : listOfExistParentEntity) {
			set = entity.getFieldHierarchicalList();
			final AccountFieldMasterEntity childEntity = new AccountFieldMasterEntity();
			childEntity.setFieldCode(dto.getChildCode());
			if (!entity.getFieldHierarchicalList().contains(childEntity)
					&& dto.getChildParentCode().equals(entity.getFieldCompcode())) {
				childEntity.setFieldDesc(dto.getChildDesc());
				childEntity.setFieldCode(dto.getChildCode());
				childEntity.setFieldStatusCpdId(dto.getChildFieldStatus());
				childEntity.setFieldCompcode(dto.getChildFinalCode());
				childEntity.setFieldParentId(entity);
				childEntity.setOrgid(orgId);
				childEntity.setUserId(empId);
				codingStructerDetEntity = new TbAcCodingstructureDetEntity();
				codingStructerDetEntity.setCodcofdetId(
						Long.valueOf(mapForAllLevels.get(Integer.parseInt(dto.getChildLevelCode().toString()))));
				childEntity.setTbAcCodingstructureDet(codingStructerDetEntity);
				childEntity.setLmoddate(new Date());
				set.add(childEntity);
			} else if (entity.getFieldHierarchicalList().contains(childEntity)) {
				for (final AccountFieldMasterEntity existEntity : entity.getFieldHierarchicalList()) {
					if (existEntity.getFieldCompcode().equals(dto.getChildFinalCode())) {
						if (dto.getChildDesc() != null) {
							existEntity.setFieldDesc(dto.getChildDesc());
							existEntity.setFieldStatusCpdId(dto.getChildFieldStatus());
						}
					}
				}
			}
			entity.setFieldHierarchicalList(set);
			if (!entity.getFieldHierarchicalList().isEmpty()) {
				recursiveMethodCallToTravellTreeNode(entity.getFieldHierarchicalList(), fieldMasterBean,
						dtocompositeCode, dto, mapForAllLevels, orgId, langId, empId);
			}
		}

	}

	@Override
	@Transactional(readOnly=true)
	public AccountFieldMasterBean getDetailsUsingFieldId(final AccountFieldMasterBean tbAcFieldMaster) {

		final AccountFieldMasterBean fieldBean = new AccountFieldMasterBean();
		final AccountFieldMasterEntity parentEntity = accountFieldMasterDao
				.getParentDetailsUsingFieldId(tbAcFieldMaster);
		final List<AccountFieldDto> listOfDto = new ArrayList<>();
		fieldBean.setParentCode(parentEntity.getFieldCode());
		fieldBean.setParentDesc(parentEntity.getFieldDesc());
		fieldBean.setParentFinalCode(parentEntity.getFieldCompcode());
		fieldBean.setParentLevel(parentEntity.getTbAcCodingstructureDet().getCodDescription());
		fieldBean.setFieldId(parentEntity.getFieldId());
		if ((parentEntity.getFieldHierarchicalList() != null) && !parentEntity.getFieldHierarchicalList().isEmpty()) {
			addField(listOfDto, parentEntity.getFieldHierarchicalList(), parentEntity.getFieldCode());
		}
		fieldBean.setListDto(listOfDto);
		return fieldBean;
	}

	@Override
	@Transactional
	public void update(final AccountFieldMasterBean tbAcFieldMaster) {
		final AccountFieldMasterEntity tbAcFieldMasterEntity = tbAcFieldMasterJpaRepository
				.findOne(tbAcFieldMaster.getFieldId());
		tbAcFieldMasterServiceMapper.mapTbAcFieldMasterToTbAcFieldMasterEntity(tbAcFieldMaster, tbAcFieldMasterEntity);
		final AccountFieldMasterEntity tbAcFieldMasterEntitySaved = tbAcFieldMasterJpaRepository
				.save(tbAcFieldMasterEntity);
		tbAcFieldMasterServiceMapper.mapTbAcFieldMasterEntityToTbAcFieldMaster(tbAcFieldMasterEntitySaved, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountFieldMasterService#
	 * getParentFieldCodeList(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public Boolean isParentExists(final String fieldCode, final Long orgId) {

		return accountFieldMasterDao.isParentExists(fieldCode, orgId);
	}

	private void addField(final List<AccountFieldDto> fields, final Set<AccountFieldMasterEntity> fieldHierarchicalList,
			final String pCode) {
		if ((fieldHierarchicalList != null) && !fieldHierarchicalList.isEmpty()) {

			AccountFieldDto dto = null;
			Long codLevel = null;
			Long codingLevel = null;
			for (final AccountFieldMasterEntity entry : fieldHierarchicalList) {
				dto = new AccountFieldDto();
				dto.setChildCode(entry.getFieldCode());
				dto.setChildFieldId(entry.getFieldId());
				dto.setChildParentCode(pCode);
				dto.setChildDesc(entry.getFieldDesc());
				dto.setChildFieldStatus(entry.getFieldStatusCpdId());
				dto.setChildFinalCode(entry.getFieldCompcode());

				if (entry.getTbAcCodingstructureDet() != null) {
					dto.setChildLevelCode(entry.getTbAcCodingstructureDet().getCodLevel());
					codLevel = entry.getTbAcCodingstructureDet().getCodLevel();
					if ((codLevel != null) && (codLevel > 1l)) {
						codingLevel = codLevel - 1;
						dto.setChildParentLevelCode(codingLevel);
					}
				}
				fields.add(dto);
				if ((entry.getFieldHierarchicalList() != null) && !entry.getFieldHierarchicalList().isEmpty()) {
					addField(fields, entry.getFieldHierarchicalList(), entry.getFieldCode());

				}

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountFieldMasterService#getLastLevels
	 * (java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Map<Long, String> getFieldMasterLastLevels(final Long orgId) {

		List<AccountFieldMasterEntity> fieldList = null;
		final LookUp lookup = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.ACCOUNT_MASTERS.FIELD_CPD_VALUE, MainetConstants.CMD);
		if (lookup != null) {
			fieldList = accountFieldMasterDao.getLastLevels(orgId, lookup.getLookUpId());
		}
		final Map<Long, String> map = new LinkedHashMap<>();
		if ((fieldList != null) && !fieldList.isEmpty()) {
			for (final AccountFieldMasterEntity field : fieldList) {
				map.put(field.getFieldId(),
						field.getFieldCompcode() + MainetConstants.SEPARATOR + field.getFieldDesc());
			}
		}

		return map;
	}

	public Long getActiveStatusId() {
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.LookUp.ACN, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		return activeStatusId;
	}

	@Override
	@Transactional(readOnly=true)
	public AccountFieldMasterBean findById(final Long fieldId) {
		final AccountFieldMasterEntity tbAcFieldMasterEntity = tbAcFieldMasterJpaRepository.findOne(fieldId);
		return tbAcFieldMasterServiceMapper.mapTbAcFieldMasterEntityToTbAcFieldMaster(tbAcFieldMasterEntity, false);
	}

	@Override
	@Transactional(readOnly=true)
	public List<AccountFieldMasterBean> findAll(final Long orgId) {
		final Iterable<AccountFieldMasterEntity> entities = accountFieldMasterDao.findAllParentFields(orgId);
		final List<AccountFieldMasterBean> beans = new ArrayList<>();
		if (entities != null) {
			for (final AccountFieldMasterEntity tbAcFieldMasterEntity : entities) {
				beans.add(tbAcFieldMasterServiceMapper.mapTbAcFieldMasterEntityToTbAcFieldMaster(tbAcFieldMasterEntity,
						true));
			}
		}
		return beans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountFieldMasterService#
	 * getFieldMasterLastLevelsForLocation(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Map<Long, String> getFieldMasterLastLevelsForLocation(final Long orgId) {

		final Map<Long, String> map = new TreeMap<>();
		final List<AccountFieldMasterEntity> fieldList = accountFieldMasterDao.getLastLevels(orgId);

		if ((fieldList != null) && !fieldList.isEmpty()) {
			for (final AccountFieldMasterEntity field : fieldList) {
				map.put(field.getFieldId(),
						field.getFieldCompcode() + MainetConstants.SEPARATOR + field.getFieldDesc());
			}
		}

		return map;
	}

	@Override
	@Transactional(readOnly=true)
	public String getFieldCode(final Long fieldId) {

		final String fieldMasterCode = tbAcFieldMasterJpaRepository.getFieldCode(fieldId);

		return fieldMasterCode;

	}
	@Override
	@Transactional(readOnly=true)
	public String getFieldDesc(final Long fieldId) {

		final String fieldMasterCode = tbAcFieldMasterJpaRepository.getFieldDesc(fieldId);

		return fieldMasterCode;

	}
	@Override
	@Transactional
	public Map<Long, String> getFieldMasterStatusLastLevels(final Long orgId, final Organisation organisation,
			final int langId) {

		List<AccountFieldMasterEntity> fieldList = null;
		final LookUp lookup = CommonMasterUtility
				.getValueFromPrefixLookUp(PrefixConstants.ACCOUNT_MASTERS.FIELD_CPD_VALUE, MainetConstants.CMD);
		if (lookup != null) {
			fieldList = accountFieldMasterDao.getLastLevels(orgId, lookup.getLookUpId());
		}
		final Map<Long, String> map = new LinkedHashMap<>();
		if ((fieldList != null) && !fieldList.isEmpty()) {
			for (final AccountFieldMasterEntity field : fieldList) {

				final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
						PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
						PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
				final Long lookUpStatusId = statusLookup.getLookUpId();
				if (lookUpStatusId.equals(field.getFieldStatusCpdId())) {
					map.put(field.getFieldId(),
							field.getFieldCompcode() + MainetConstants.SEPARATOR + field.getFieldDesc());
				}
			}
		}
		return map;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isChildFieldCompositeCodeExists(final String compositeCode, final Long defaultOrgId) {
		return accountFieldMasterDao.isChildFieldCompositeCodeExists(compositeCode, defaultOrgId);
	}

	@Override
	@Transactional
	public void insertEditedFieldMasterDataIntoPropertyTaxTableByUsingSoapWS(
			AccountFieldMasterEntity finalEditedEntity) {
		try {

			FieldMaster fieldMasterParent = new FieldMaster();
			if (finalEditedEntity.getFieldParentId() != null && finalEditedEntity.getFieldParentId().getFieldId() != null) {
				fieldMasterParent.setFIELDPARENTID(finalEditedEntity.getFieldParentId().getFieldId());
			}
			fieldMasterParent.setORGID(finalEditedEntity.getOrgid());
			// fieldMasterParent.setStatus("Status1109329587");
			if (finalEditedEntity.getTbAcCodingstructureDet() != null
					&& (Long) finalEditedEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
				fieldMasterParent.setCODCOFDETID(finalEditedEntity.getTbAcCodingstructureDet().getCodcofdetId());
			}
			// fieldMasterParent.setModifiedIn("ModifiedIn-1855756710");
			fieldMasterParent.setCreatedBy(finalEditedEntity.getUserId().toString());
			fieldMasterParent.setFIELDID(finalEditedEntity.getFieldId());
			// fieldMasterParent.setSheetId("SheetId650794529");
			if (finalEditedEntity.getLmoddate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEditedEntity.getLmoddate());
				fieldMasterParent.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			// fieldMasterParent.setMetadata("Metadata1717362157");
			fieldMasterParent.setSheetName(finalEditedEntity.getFieldId().toString());
			if (finalEditedEntity.getUpdatedBy() != null) {
				fieldMasterParent.setModifiedBy(finalEditedEntity.getUpdatedBy().toString());
			}
			// fieldMasterParent.setCaption("Caption-988679453");
			// fieldMasterParent.setAssignedTo("AssignedTo-1747152429");
			fieldMasterParent.setFIELDSTATUS(finalEditedEntity.getFieldStatusCpdId());
			if (finalEditedEntity.getUpdatedDate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(finalEditedEntity.getUpdatedDate());
				fieldMasterParent.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			fieldMasterParent.setFIELDDESC(finalEditedEntity.getFieldDesc());
			// fieldMasterParent.setTenant("Tenant-170017305");
			fieldMasterParent.setFIELDCODE(finalEditedEntity.getFieldCode());
			fieldMasterParent.setFIELDCOMPCODE(finalEditedEntity.getFieldCompcode());
			fieldMasterParent.setULBCode(finalEditedEntity.getOrgid().toString());
			// fieldMasterParent.setProcessInstance("ProcessInstance439577815");
			// fieldMasterParent.setSheetMetadataName("SheetMetadataName-1938542074");
			filedMasterSoapWSProvisionService.updateFiledMaster(fieldMasterParent);

			if (finalEditedEntity.getFieldHierarchicalList() != null && !finalEditedEntity.getFieldHierarchicalList().isEmpty()) {
				Set<AccountFieldMasterEntity> childSetEntity = finalEditedEntity.getFieldHierarchicalList();
				for (AccountFieldMasterEntity accountFieldMasterEntity : childSetEntity) {
					FieldMaster fieldMasterChild = new FieldMaster();
					if (accountFieldMasterEntity.getFieldParentId() != null
							&& accountFieldMasterEntity.getFieldParentId().getFieldId() != null) {
						fieldMasterChild.setFIELDPARENTID(accountFieldMasterEntity.getFieldParentId().getFieldId());
					}
					fieldMasterChild.setORGID(accountFieldMasterEntity.getOrgid());
					// fieldMasterChild.setStatus("Status1109329587");
					if (accountFieldMasterEntity.getTbAcCodingstructureDet() != null
							&& (Long) accountFieldMasterEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
						fieldMasterChild
								.setCODCOFDETID(accountFieldMasterEntity.getTbAcCodingstructureDet().getCodcofdetId());
					}
					// fieldMasterChild.setModifiedIn("ModifiedIn-1855756710");
					fieldMasterChild.setCreatedBy(accountFieldMasterEntity.getUserId().toString());
					fieldMasterChild.setFIELDID(accountFieldMasterEntity.getFieldId());
					// fieldMasterChild.setSheetId("SheetId650794529");
					if (accountFieldMasterEntity.getLmoddate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFieldMasterEntity.getLmoddate());
						fieldMasterChild.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					// fieldMasterChild.setMetadata("Metadata1717362157");
					fieldMasterChild.setSheetName(accountFieldMasterEntity.getFieldId().toString());
					if (accountFieldMasterEntity.getUpdatedBy() != null) {
						fieldMasterChild.setModifiedBy(accountFieldMasterEntity.getUpdatedBy().toString());
					}
					// fieldMasterChild.setCaption("Caption-988679453");
					// fieldMasterChild.setAssignedTo("AssignedTo-1747152429");
					fieldMasterChild.setFIELDSTATUS(accountFieldMasterEntity.getFieldStatusCpdId());
					if (accountFieldMasterEntity.getUpdatedDate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(accountFieldMasterEntity.getUpdatedDate());
						fieldMasterChild.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
					}
					fieldMasterChild.setFIELDDESC(accountFieldMasterEntity.getFieldDesc());
					// fieldMasterChild.setTenant("Tenant-170017305");
					fieldMasterChild.setFIELDCODE(accountFieldMasterEntity.getFieldCode());
					fieldMasterChild.setFIELDCOMPCODE(accountFieldMasterEntity.getFieldCompcode());
					fieldMasterChild.setULBCode(accountFieldMasterEntity.getOrgid().toString());
					// fieldMasterChild.setProcessInstance("ProcessInstance439577815");
					// fieldMasterChild.setSheetMetadataName("SheetMetadataName-1938542074");
					filedMasterSoapWSProvisionService.createFiledMaster(fieldMasterChild);

					if (accountFieldMasterEntity.getFieldHierarchicalList() != null && !accountFieldMasterEntity.getFieldHierarchicalList().isEmpty()) {
						Set<AccountFieldMasterEntity> childSubSetEntity = accountFieldMasterEntity.getFieldHierarchicalList();
						for (AccountFieldMasterEntity accountSubFieldMasterEntity : childSubSetEntity) {
							FieldMaster fieldMasterSubChild = new FieldMaster();
							if (accountSubFieldMasterEntity.getFieldParentId() != null
									&& accountSubFieldMasterEntity.getFieldParentId().getFieldId() != null) {
								fieldMasterSubChild.setFIELDPARENTID(accountSubFieldMasterEntity.getFieldParentId().getFieldId());
							}
							fieldMasterSubChild.setORGID(accountSubFieldMasterEntity.getOrgid());
							// fieldMasterSubChild.setStatus("Status1109329587");
							if (accountSubFieldMasterEntity.getTbAcCodingstructureDet() != null
									&& (Long) accountSubFieldMasterEntity.getTbAcCodingstructureDet().getCodcofdetId() != null) {
								fieldMasterSubChild
										.setCODCOFDETID(accountSubFieldMasterEntity.getTbAcCodingstructureDet().getCodcofdetId());
							}
							// fieldMasterSubChild.setModifiedIn("ModifiedIn-1855756710");
							fieldMasterSubChild.setCreatedBy(accountSubFieldMasterEntity.getUserId().toString());
							fieldMasterSubChild.setFIELDID(accountSubFieldMasterEntity.getFieldId());
							// fieldMasterSubChild.setSheetId("SheetId650794529");
							if (accountSubFieldMasterEntity.getLmoddate() != null) {
								GregorianCalendar cal = new GregorianCalendar();
								cal.setTime(accountSubFieldMasterEntity.getLmoddate());
								fieldMasterSubChild.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
							}
							// fieldMasterSubChild.setMetadata("Metadata1717362157");
							fieldMasterSubChild.setSheetName(accountSubFieldMasterEntity.getFieldId().toString());
							if (accountSubFieldMasterEntity.getUpdatedBy() != null) {
								fieldMasterSubChild.setModifiedBy(accountSubFieldMasterEntity.getUpdatedBy().toString());
							}
							// fieldMasterSubChild.setCaption("Caption-988679453");
							// fieldMasterSubChild.setAssignedTo("AssignedTo-1747152429");
							fieldMasterSubChild.setFIELDSTATUS(accountSubFieldMasterEntity.getFieldStatusCpdId());
							if (accountSubFieldMasterEntity.getUpdatedDate() != null) {
								GregorianCalendar cal = new GregorianCalendar();
								cal.setTime(accountSubFieldMasterEntity.getUpdatedDate());
								fieldMasterSubChild.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
							}
							fieldMasterSubChild.setFIELDDESC(accountSubFieldMasterEntity.getFieldDesc());
							// fieldMasterSubChild.setTenant("Tenant-170017305");
							fieldMasterSubChild.setFIELDCODE(accountSubFieldMasterEntity.getFieldCode());
							fieldMasterSubChild.setFIELDCOMPCODE(accountSubFieldMasterEntity.getFieldCompcode());
							fieldMasterSubChild.setULBCode(accountSubFieldMasterEntity.getOrgid().toString());
							// fieldMasterSubChild.setProcessInstance("ProcessInstance439577815");
							// fieldMasterSubChild.setSheetMetadataName("SheetMetadataName-1938542074");
							filedMasterSoapWSProvisionService.createFiledMaster(fieldMasterSubChild);

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
	public Map<Long, String> getFieldMasterLastLevelsCompositeCodeByOrgId(final Long orgId) {

		List<AccountFieldMasterEntity> fieldList = tbAcFieldMasterJpaRepository.getLastLevelsCompositeCodeByOrgId(orgId);
		final Map<Long, String> map = new LinkedHashMap<>();
		if ((fieldList != null) && !fieldList.isEmpty()) {
			for (final AccountFieldMasterEntity field : fieldList) {
				map.put(field.getFieldId(),
						field.getFieldCompcode());
			}
		}
		return map;
	}
	

	@Override
	public Long getFieldIdByFieldCompositCode(String compositeCode, Long orgId) {
		return tbAcFieldMasterJpaRepository.getFieldId(compositeCode, orgId);
	}

	@Override
	public List<AccountFieldMasterBean> findAllByOrgId(Long orgId) {
		List<AccountFieldMasterEntity> filedList = tbAcFieldMasterJpaRepository.getFieldMasterByOrgid(orgId);
	    List<AccountFieldMasterBean> beans = null;
		if (filedList != null) {
			beans=filedList.stream().map(dto->{
				AccountFieldMasterBean fdto=new AccountFieldMasterBean();
				BeanUtils.copyProperties(dto, fdto);
				return fdto;
			}).collect(Collectors.toList());;
		}
		return beans;
	}
	
}
