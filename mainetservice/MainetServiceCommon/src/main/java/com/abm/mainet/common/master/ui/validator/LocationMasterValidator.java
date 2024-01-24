package com.abm.mainet.common.master.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.LocationMasterUploadDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

public class LocationMasterValidator implements Validator {

	public static final String LOCATIONMASTER = "TB_LOCATION_MAS";

	@Override
	public void validate(final Object target, final Errors errors) {

		final TbLocationMas tbLocationMas = (TbLocationMas) target;
		final BindingResult bindingResult = (BindingResult) errors;

		if (StringUtils.isEmpty(tbLocationMas.getLocNameEng()) || StringUtils.isEmpty(tbLocationMas.getLocNameReg())) {
			bindingResult.addError(new ObjectError("",
					ApplicationSession.getInstance().getMessage("function.master.selectParentCode")));
		}

		if (StringUtils.isEmpty(tbLocationMas.getLocArea()) || StringUtils.isEmpty(tbLocationMas.getLocAreaReg())) {
			bindingResult.addError(new ObjectError("",
					ApplicationSession.getInstance().getMessage("function.master.selectParentCode")));
		}

		if (StringUtils.isEmpty(tbLocationMas.getLocAddress())
				|| StringUtils.isEmpty(tbLocationMas.getLocAddressReg())) {
			bindingResult.addError(new ObjectError("",
					ApplicationSession.getInstance().getMessage("function.master.selectParentCode")));
		}
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return TbLocationMas.class.equals(clazz);
	}

	@SuppressWarnings("unlikely-arg-type")
	public List<LocationMasterUploadDto> validateExcel(List<LocationMasterUploadDto> locationMasterUploadDtos,
			BindingResult bindingResult, List<LookUp> locCategory, Map<Integer, List<LookUp>> elecLookupsMap,
			Map<Long, String> codIdRevLevel1, List<TbDepartment> deptList, Organisation org) {
		int rowNo = 0;
		final List<LocationMasterUploadDto> vendorMasterUploadDtoList = new ArrayList<>();

		final ApplicationSession session = ApplicationSession.getInstance();

		Set<LocationMasterUploadDto> locationMasterExport = locationMasterUploadDtos.stream()
				.filter(dto -> Collections.frequency(locationMasterUploadDtos, dto) > 1).collect(Collectors.toSet());
		if (locationMasterExport.isEmpty()) {

			for (LocationMasterUploadDto locationMasterUploadDto : locationMasterUploadDtos) {

				LocationMasterUploadDto dto = new LocationMasterUploadDto();
				rowNo++;
				if (isValueNull(locationMasterUploadDto.getLocNameEng())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.locName") + rowNo));
					break;
				}

				if (isValueNull(locationMasterUploadDto.getLocNameReg())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.locNameReg") + rowNo));
					break;
				}

				if (isValueNull(locationMasterUploadDto.getLocArea())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.locArea") + rowNo));
					break;
				}

				if (isValueNull(locationMasterUploadDto.getLocAreaReg())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.locAreaReg") + rowNo));
					break;
				}

				if (isValueNull(locationMasterUploadDto.getLocAddress())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.locAddress") + rowNo));
					break;
				}

				if (isValueNull(locationMasterUploadDto.getLocAddressReg())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.locAddressReg") + rowNo));
					break;
				}

				if (isValueNull(locationMasterUploadDto.getDeptLoc())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.isDeptLoc") + rowNo));
					break;
				}

				if (!locationMasterUploadDto.getDeptLoc().equalsIgnoreCase("Y")
						&& !locationMasterUploadDto.getDeptLoc().equalsIgnoreCase("N")) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.isDeptLoc.notExist") + rowNo));
					break;
				}

				if (isValueNotNull(locationMasterUploadDto.getLocActive())
						&& !locationMasterUploadDto.getLocActive().equalsIgnoreCase("Y")) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.status.notExist") + rowNo));
					break;
				}

				if (isValueNull(locationMasterUploadDto.getLocCategory())) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.category") + rowNo));
					break;
				}

				int locCategoryExist = 0;
				for (LookUp list : locCategory) {
					if (locationMasterUploadDto.getLocCategory().equalsIgnoreCase(list.getDescLangFirst().trim())) {
						dto.setLocCategory(String.valueOf(list.getLookUpId()));
						locCategoryExist++;
						break;
					}
				}
				if (locCategoryExist == 0) {
					bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("locationMas.excel.category.notExist") + rowNo));
					break;
				}

				if (isValueNotNull(locationMasterUploadDto.getIsAreaMappingElectoral())) {
					if (locationMasterUploadDto.getIsAreaMappingElectoral().equalsIgnoreCase("Y")) {
						int size = elecLookupsMap.size();
						if (size == 0) {
							// Means no hierarchical prefix configured for electoral ward dept for this org
							// Check if cod values are not present. They should not be present
							if ((isValueNotNull(locationMasterUploadDto.getCodIdElecLevel1()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel2()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel3()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel4()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel5()))) {
								bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
										MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
										null,
										session.getMessage("locationMas.excel.electoral.prefixNotExist") + rowNo));
								break;
							}
						} else {
							List<LookUp> elecLevel1List = elecLookupsMap.get(1);
							List<LookUp> elecLevel2List = elecLookupsMap.get(2);
							List<LookUp> elecLevel3List = elecLookupsMap.get(3);
							List<LookUp> elecLevel4List = elecLookupsMap.get(4);
							List<LookUp> elecLevel5List = elecLookupsMap.get(5);
							// Check if cod values are present. They should be present.
							// If present, check each value in respective level's lookup list inside map.
							if (size == 1) {
								if (isValueNull(locationMasterUploadDto.getCodIdElecLevel1())) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.electoralWard") + rowNo));
									break;
								}
							}
							if (size == 2) {
								if ((isValueNull(locationMasterUploadDto.getCodIdElecLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel2()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.electoralWard") + rowNo));
									break;
								}
							}
							if (size == 3) {
								if ((isValueNull(locationMasterUploadDto.getCodIdElecLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel2()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel3()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.electoralWard") + rowNo));
									break;
								}
							}
							if (size == 4) {
								if ((isValueNull(locationMasterUploadDto.getCodIdElecLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel2()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel3()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel4()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.electoralWard") + rowNo));
									break;
								}
							}
							if (size == 5) {
								if ((isValueNull(locationMasterUploadDto.getCodIdElecLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel2()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel3()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel4()))
										|| (isValueNull(locationMasterUploadDto.getCodIdElecLevel5()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.electoralWard") + rowNo));
									break;
								}
							}

							int electoralExist = 0;
							long parentId = 0L;
							if (elecLevel1List != null) {
								for (LookUp list : elecLevel1List) {
									if (locationMasterUploadDto.getCodIdElecLevel1()
											.equalsIgnoreCase(list.getDescLangFirst().trim())) {
										dto.setCodIdElecLevel1(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										electoralExist++;
										break;
									}
								}
								if (electoralExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null,
											session.getMessage("locationMas.excel.electoralWard.notExist") + rowNo));
									break;
								}
							}
							electoralExist = 0;
							if (elecLevel2List != null) {
								for (LookUp list : elecLevel2List) {
									if (locationMasterUploadDto.getCodIdElecLevel2().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdElecLevel2(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										electoralExist++;
										break;
									}
								}
								if (electoralExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null,
											session.getMessage("locationMas.excel.electoralWard.notExist") + rowNo));
									break;
								}
							}
							electoralExist = 0;
							if (elecLevel3List != null) {
								for (LookUp list : elecLevel3List) {
									if (locationMasterUploadDto.getCodIdElecLevel3().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdElecLevel3(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										electoralExist++;
										break;
									}
								}
								if (electoralExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null,
											session.getMessage("locationMas.excel.electoralWard.notExist") + rowNo));
									break;
								}
							}
							electoralExist = 0;
							if (elecLevel4List != null) {
								for (LookUp list : elecLevel4List) {
									if (locationMasterUploadDto.getCodIdElecLevel4().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdElecLevel4(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										electoralExist++;
										break;
									}
								}
								if (electoralExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null,
											session.getMessage("locationMas.excel.electoralWard.notExist") + rowNo));
									break;
								}
							}
							electoralExist = 0;
							if (elecLevel5List != null) {
								for (LookUp list : elecLevel5List) {
									if (locationMasterUploadDto.getCodIdElecLevel5().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdElecLevel5(String.valueOf(list.getLookUpId()));
										electoralExist++;
										break;
									}
								}
								if (electoralExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null,
											session.getMessage("locationMas.excel.electoralWard.notExist") + rowNo));
									break;
								}
							}
						}

					} else if (!locationMasterUploadDto.getIsAreaMappingElectoral().equalsIgnoreCase("N")) {
						bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("locationMas.excel.areaMappingElectoral.notExist") + rowNo));
						break;
					}
				} else {
					if ((isValueNotNull(locationMasterUploadDto.getCodIdElecLevel1()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel2()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel3()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel4()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdElecLevel5()))) {
						bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("locationMas.excel.electoral.notRequired") + rowNo));
						break;
					}
				}

				if (isValueNotNull(locationMasterUploadDto.getIsAreaMappingRevenue())) {
					if (locationMasterUploadDto.getIsAreaMappingRevenue().equalsIgnoreCase("Y")) {

						if (isValueNull(locationMasterUploadDto.getCodIdRevLevel1())) {
							bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
									MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
									session.getMessage("locationMas.excel.revenueFieldMapping") + rowNo));
							break;
						}
						int revenueMappingExist = 0;
						for (Map.Entry<Long, String> revMapEntry : codIdRevLevel1.entrySet()) {
							if (locationMasterUploadDto.getCodIdRevLevel1()
									.equalsIgnoreCase(revMapEntry.getValue().trim())) {
								dto.setCodIdRevLevel1(revMapEntry.getKey().toString());
								revenueMappingExist++;
								break;
							}
						}
						if (revenueMappingExist == 0) {
							bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
									MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
									session.getMessage("locationMas.excel.revenueFieldMapping.notExist") + rowNo));
							break;
						}

					} else if (!locationMasterUploadDto.getIsAreaMappingRevenue().equalsIgnoreCase("N")) {
						bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("locationMas.excel.areaMappingRevenue.notExist") + rowNo));
						break;
					}
				} else {
					if (isValueNotNull(locationMasterUploadDto.getCodIdRevLevel1())) {
						bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("locationMas.excel.revenue.notRequired") + rowNo));
						break;
					}
				}

				if (isValueNotNull(locationMasterUploadDto.getIsAreaMappingOperational())) {
					if (locationMasterUploadDto.getIsAreaMappingOperational().equalsIgnoreCase("Y")) {
						Long deptId = null;
						if (isValueNull(locationMasterUploadDto.getDpDeptDesc())) {
							bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
									MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
									session.getMessage("locationMas.excel.operWardDept") + rowNo));
							break;
						}
						int operDeptExist = 0;
						for (TbDepartment dept : deptList) {
							if (locationMasterUploadDto.getDpDeptDesc().equalsIgnoreCase(dept.getDpDeptdesc().trim())) {
								dto.setDpDeptId(dept.getDpDeptid());
								dto.setDpDeptDesc(dept.getDpDeptdesc());
								deptId = dept.getDpDeptid();
								operDeptExist++;
								break;
							}
						}
						if (operDeptExist == 0) {
							bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
									MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
									session.getMessage("locationMas.excel.operWardDept.notExist") + rowNo));
							break;
						}

						String prefixName = null;
						try {
							prefixName = ApplicationContextProvider.getApplicationContext()
									.getBean(TbDepartmentService.class).findDepartmentPrefixName(deptId,
											UserSession.getCurrent().getOrganisation().getOrgid());
						} catch (Exception e1) {
							bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
									MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
									session.getMessage("locationMas.excel.operDept.prefixFail") + rowNo));
							break;
						}

						if (prefixName == null || prefixName.equals("")) {
							bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
									MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
									session.getMessage("locationMas.excel.operDept.prefixNotExist") + rowNo));
							break;
						}

						Map<Integer, List<LookUp>> operLookupsMap = new HashMap<>();
						for (int i = 1; i <= 5; i++) {
							try {
								List<LookUp> lookUps = CommonMasterUtility.getLevelData(prefixName, i, org);
								operLookupsMap.put(i, lookUps);
							} catch (Exception e) {

							}
						}

						int size = operLookupsMap.size();
						if (size == 0) {
							// Means no hierarchical prefix configured for operationL ward dept for this org
							// Check if cod values are not present. They should not be present
							if ((isValueNotNull(locationMasterUploadDto.getCodIdOperLevel1()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel2()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel3()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel4()))
									|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel5()))) {
								bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
										MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
										null, session.getMessage("locationMas.excel.operDept.prefixNotExist") + rowNo));
								break;
							}
						} else {
							List<LookUp> operLevel1List = operLookupsMap.get(1);
							List<LookUp> operLevel2List = operLookupsMap.get(2);
							List<LookUp> operLevel3List = operLookupsMap.get(3);
							List<LookUp> operLevel4List = operLookupsMap.get(4);
							List<LookUp> operLevel5List = operLookupsMap.get(5);
							// Check if cod values are present. They should be present.
							// If present, check each value in respective level's lookup list inside map.
							if (size == 1) {
								if (isValueNull(locationMasterUploadDto.getCodIdOperLevel1())) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard") + rowNo));
									break;
								}
							}
							if (size == 2) {
								if ((isValueNull(locationMasterUploadDto.getCodIdOperLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel2()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard") + rowNo));
									break;
								}
							}
							if (size == 3) {
								if ((isValueNull(locationMasterUploadDto.getCodIdOperLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel2()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel3()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard") + rowNo));
									break;
								}
							}
							if (size == 4) {
								if ((isValueNull(locationMasterUploadDto.getCodIdOperLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel2()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel3()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel4()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard") + rowNo));
									break;
								}
							}
							if (size == 5) {
								if ((isValueNull(locationMasterUploadDto.getCodIdOperLevel1()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel2()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel3()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel4()))
										|| (isValueNull(locationMasterUploadDto.getCodIdOperLevel5()))) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard") + rowNo));
									break;
								}
							}

							int operationalExist = 0;
							long parentId = 0L;
							if (operLevel1List != null) {
								for (LookUp list : operLevel1List) {
									if (locationMasterUploadDto.getCodIdOperLevel1()
											.equalsIgnoreCase(list.getDescLangFirst().trim())) {
										dto.setCodIdOperLevel1(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										operationalExist++;
										break;
									}
								}
								if (operationalExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard.notExist") + rowNo));
									break;
								}
							}
							operationalExist = 0;
							if (operLevel2List != null) {
								for (LookUp list : operLevel2List) {
									if (locationMasterUploadDto.getCodIdOperLevel2().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdOperLevel2(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										operationalExist++;
										break;
									}
								}
								if (operationalExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard.notExist") + rowNo));
									break;
								}
							}
							operationalExist = 0;
							if (operLevel3List != null) {
								for (LookUp list : operLevel3List) {
									if (locationMasterUploadDto.getCodIdOperLevel3().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdOperLevel3(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										operationalExist++;
										break;
									}
								}
								if (operationalExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard.notExist") + rowNo));
									break;
								}
							}
							operationalExist = 0;
							if (operLevel4List != null) {
								for (LookUp list : operLevel4List) {
									if (locationMasterUploadDto.getCodIdOperLevel4().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdOperLevel4(String.valueOf(list.getLookUpId()));
										parentId = list.getLookUpId();
										operationalExist++;
										break;
									}
								}
								if (operationalExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard.notExist") + rowNo));
									break;
								}
							}
							operationalExist = 0;
							if (operLevel5List != null) {
								for (LookUp list : operLevel5List) {
									if (locationMasterUploadDto.getCodIdOperLevel5().equalsIgnoreCase(
											list.getDescLangFirst().trim()) && list.getLookUpParentId() == parentId) {
										dto.setCodIdOperLevel5(String.valueOf(list.getLookUpId()));
										operationalExist++;
										break;
									}
								}
								if (operationalExist == 0) {
									bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
											MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
											null, session.getMessage("locationMas.excel.operWard.notExist") + rowNo));
									break;
								}
							}
						}

					} else if (!locationMasterUploadDto.getIsAreaMappingOperational().equalsIgnoreCase("N")) {
						bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("locationMas.excel.areaMappingOperational.notExist") + rowNo));
						break;
					}
				} else {
					if ((isValueNotNull(locationMasterUploadDto.getDpDeptDesc()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel1()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel2()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel3()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel4()))
							|| (isValueNotNull(locationMasterUploadDto.getCodIdOperLevel5()))) {
						bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("locationMas.excel.operational.notRequired") + rowNo));
						break;
					}
				}

				boolean isValidPincode = false;
				if (locationMasterUploadDto.getPincode() != null) {
					isValidPincode = Pattern.matches(MainetConstants.PIN_CODE,
							locationMasterUploadDto.getPincode().toString());
					if (isValidPincode) {
						dto.setPincode(locationMasterUploadDto.getPincode());
					} else {
						bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER,
								MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
								rowNo + session.getMessage("locationMas.excel.pincode")));
						break;
					}
				}

				dto.setLocNameEng(locationMasterUploadDto.getLocNameEng());
				dto.setLocNameReg(locationMasterUploadDto.getLocNameReg());
				dto.setLocArea(locationMasterUploadDto.getLocArea());
				dto.setLocAreaReg(locationMasterUploadDto.getLocAreaReg());
				dto.setLocAddress(locationMasterUploadDto.getLocAddress());
				dto.setLocAddressReg(locationMasterUploadDto.getLocAddressReg());
				dto.setLandmark(locationMasterUploadDto.getLandmark());
				dto.setDeptLoc(locationMasterUploadDto.getDeptLoc());
				dto.setLocActive(locationMasterUploadDto.getLocActive());
				dto.setGISNo(locationMasterUploadDto.getGISNo());
				dto.setLatitude(locationMasterUploadDto.getLatitude());
				dto.setLongitude(locationMasterUploadDto.getLongitude());
				dto.setIsAreaMappingElectoral(locationMasterUploadDto.getIsAreaMappingElectoral());
				dto.setIsAreaMappingRevenue(locationMasterUploadDto.getIsAreaMappingRevenue());
				dto.setIsAreaMappingOperational(locationMasterUploadDto.getIsAreaMappingOperational());
				if (dto.getIsAreaMappingElectoral() != null && dto.getIsAreaMappingElectoral().equalsIgnoreCase("Y"))
					dto.setElectoralChkBox(true);
				if (dto.getIsAreaMappingRevenue() != null && dto.getIsAreaMappingRevenue().equalsIgnoreCase("Y"))
					dto.setRevenueChkBox(true);
				if (dto.getIsAreaMappingOperational() != null
						&& dto.getIsAreaMappingOperational().equalsIgnoreCase("Y"))
					dto.setOpertionalChkBox(true);
				vendorMasterUploadDtoList.add(dto);
			}
		} else {

			bindingResult.addError(new org.springframework.validation.FieldError(LOCATIONMASTER, MainetConstants.BLANK,
					null, false, new String[] { MainetConstants.ERRORS }, null,
					session.getMessage("locationMas.empty.excel.duplicate")));
		}
		return vendorMasterUploadDtoList;
	}

	private boolean isValueNull(String val) {
		if (val == null || val.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValueNotNull(String val) {
		if (val != null && !val.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
