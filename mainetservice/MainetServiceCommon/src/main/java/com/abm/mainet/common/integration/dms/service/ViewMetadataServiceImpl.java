package com.abm.mainet.common.integration.dms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dao.IViewMetadataDao;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.ui.model.ViewMetadataModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Service
public class ViewMetadataServiceImpl implements IViewMetadataService {

	private static final Logger LOGGER = Logger.getLogger(ViewMetadataServiceImpl.class);

	@Autowired
	private IViewMetadataDao dao;

	@Override
	public List<DmsDocsMetadataDto> getMetadataDetails(String deptId, String metadataId, String metadataValue,
			Long roleId, Long orgid, Long zone, Long ward, Long mohalla, String docRefNo, String callType,
			ViewMetadataModel model, Long docType, String complainNo, String status) {
		String newMetadataId = metadataId;
		List<DmsDocsMetadata> entityList = new ArrayList<>();
		if (StringUtils.isEmpty(complainNo)) {
			/*if (StringUtils.isEmpty(metadataValue) || metadataId.equals("0")) {
				List<LookUp> metadataList = CommonMasterUtility.getChildLookUpsFromParentId(Long.valueOf(deptId));
				if (!metadataList.isEmpty()) {
					metadataId = String.valueOf(metadataList.get(0).getLookUpId());
					metadataValue = MainetConstants.CommonConstants.BLANK;
				}
			}*/
			entityList = dao.getMetadataDetails(deptId, metadataId, metadataValue, roleId, orgid, zone, ward, mohalla,
					docRefNo, docType);
		} else {
			entityList = dao.getApprovalMetadataDetails(complainNo, status, orgid);
		}

		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long assignDeptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		List<DmsDocsMetadata> newEntityList = new ArrayList<>();

		entityList.forEach(entity -> {
			if (entity.getStorageType() != null && entity.getStorageType().equals(MainetConstants.Dms.KMS)) {
				List<String> roleIdsList = new ArrayList<>();
				List<String> employeeIdsList = new ArrayList<>();
				List<String> assignDeptIdList = new ArrayList<>();
				if (entity.getUserRoleId() != null) {
					roleIdsList = Arrays.asList(entity.getUserRoleId().split(MainetConstants.operator.COMMA));
				} else if (entity.getAssignedTo() != null) {
					employeeIdsList = Arrays.asList(entity.getAssignedTo().split(MainetConstants.operator.COMMA));
				} else if (entity.getAssignedDept() != null) {
					assignDeptIdList = Arrays.asList(entity.getAssignedDept().split(MainetConstants.operator.COMMA));
				}
				if (complainNo != null) {
					newEntityList.add(entity);
				} else if ((CollectionUtils.isNotEmpty(roleIdsList)
						&& (roleIdsList.contains(roleId.toString()) || roleIdsList.contains("0")))
						|| (CollectionUtils.isNotEmpty(employeeIdsList)
								&& (employeeIdsList.contains(empId.toString()) || employeeIdsList.contains("0")))
						|| (CollectionUtils.isNotEmpty(assignDeptIdList)
								&& (assignDeptIdList.contains(assignDeptId.toString())
										|| assignDeptIdList.contains("0")))) {
					newEntityList.add(entity);
				}
			} else if (entity.getStorageType() != null && entity.getStorageType().equals(MainetConstants.Dms.DMS)) {
				newEntityList.add(entity);
			}
		});
		List<DmsDocsMetadataDto> dtoList = new ArrayList<DmsDocsMetadataDto>();
		newEntityList.forEach(data -> {
			DmsDocsMetadataDto dto = new DmsDocsMetadataDto();
			BeanUtils.copyProperties(data, dto);
			List<DmsDocsMetadataDetDto> detDtoList = new java.util.ArrayList<>();
			data.getDmsDocsMetadataDetList().forEach(childData -> {
				if(null != childData.getMtKey() && childData.getMtKey().matches("\\d+")) {
					LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(childData.getMtKey()),
							dto.getOrgId().getOrgid());
					childData.setMtKey(lookup.getLookUpDesc());
				}
				DmsDocsMetadataDetDto detDto = new DmsDocsMetadataDetDto();
				BeanUtils.copyProperties(childData, detDto);
				detDtoList.add(detDto);
			});
			dto.setDmsDocsMetadataDetList(detDtoList);
			dtoList.add(dto);
		});
		Map<String, Object> argumentsMap = new HashMap<>();
		if (zone != null && !zone.toString().equals("0")) {
			argumentsMap.put("zone", zone);
		}
		if (ward != null && !ward.toString().equals("0")) {
			argumentsMap.put("ward", ward);
		}
		if (mohalla != null && !mohalla.toString().equals("0")) {
			argumentsMap.put("mohalla", mohalla);
		}
		LookUp colValue = new LookUp();
		if (StringUtils.isNotEmpty(newMetadataId)) {
			colValue = CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(newMetadataId), orgid);
		}

		if (CollectionUtils.isEmpty(entityList) && callType != null && callType.equals(MainetConstants.Dms.DMS)) {
			List<DmsDocsMetadataDto> finalMetalDetalList = null;
			try {
				finalMetalDetalList = getDepartmentalMetadeta(colValue.getLookUpCode(), metadataValue, orgid,
						Long.valueOf(deptId), argumentsMap);
			} catch (Exception e) {
				model.setErrorMsg(MainetConstants.FlagY);
				LOGGER.error("Exception occured while fetching data from service", e);
			}
			if (CollectionUtils.isNotEmpty(finalMetalDetalList)) {
				dtoList.addAll(finalMetalDetalList);
			}
		}
		return dtoList;

	}

	private List<DmsDocsMetadataDto> getDepartmentalMetadeta(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {

		LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(deptId), orgId);
		List<LookUp> metadataList = CommonMasterUtility.getChildLookUpsFromParentId(deptId);
		DepartmentalMetadetaService metaDetalList = null;
		if (StringUtils.isNotEmpty(lookup.getOtherField())
				&& lookup.getOtherField().equalsIgnoreCase(MainetConstants.FlagY)) {
			if (lookup.getLookUpCode().equals(MainetConstants.Dms.COMMON_DEPT)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.CARE_METADATA, DepartmentalMetadetaService.class);
			} else if (lookup.getLookUpCode().equals(MainetConstants.Dms.PROPERTY_DEPT)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.PROPERTY_METADATA, DepartmentalMetadetaService.class);

			} else if (lookup.getLookUpCode().equals(MainetConstants.Dms.TRADE_DEPT)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.TRADE_MEADATA, DepartmentalMetadetaService.class);

			} else if (lookup.getLookUpCode().equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.WATER_MEADATA, DepartmentalMetadetaService.class);
			} else if (lookup.getLookUpCode().equals(MainetConstants.AD)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.AUDIT_MEADATA, DepartmentalMetadetaService.class);
			} else if (lookup.getLookUpCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.ASSET_METADATA, DepartmentalMetadetaService.class);
			} else if (lookup.getLookUpCode().equals(MainetConstants.RnLCommon.RentLease)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.RNL_METADATA, DepartmentalMetadetaService.class);
			} else if (lookup.getLookUpCode().equals(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE)) {
				metaDetalList = ApplicationContextProvider.getApplicationContext()
						.getBean(MainetConstants.Dms.ADH_METADATA, DepartmentalMetadetaService.class);
			}
		}
		List<DmsDocsMetadataDto> deptMetadatList = null;
		// List<DmsDocsMetadataDetDto> dmsDocsMetadataDetList = new ArrayList<>();
		if (metaDetalList != null) {
			deptMetadatList = metaDetalList.getDepartmentalMetadeta(colname, Colvalue, orgId, deptId, argumentsMap);
			if (CollectionUtils.isNotEmpty(deptMetadatList)) {
				deptMetadatList.forEach(dto -> {
					List<DmsDocsMetadataDetDto> dmsDocsMetadataDetList = new ArrayList<>();
					dto.getDmsDocsMetadataDetList().forEach(metadatadto -> {
						metadataList.forEach(dtolokup -> {
							DmsDocsMetadataDetDto childDto = new DmsDocsMetadataDetDto();
							if (dtolokup.getLookUpCode().equalsIgnoreCase(metadatadto.getMtKey())) {
								int Landid = UserSession.getCurrent().getLanguageId();
								if (Landid == 1) {
									childDto.setMtKey(dtolokup.getDescLangFirst());

								} else {
									childDto.setMtKey(dtolokup.getDescLangSecond());
								}
								childDto.setMtVal(metadatadto.getMtVal());
								dmsDocsMetadataDetList.add(childDto);
							}
						});
					});
					// dto.getDmsDocsMetadataDetList().clear();
					dto.setDmsDocsMetadataDetList(dmsDocsMetadataDetList);
				});
			}
		}
		return deptMetadatList;
	}
}
