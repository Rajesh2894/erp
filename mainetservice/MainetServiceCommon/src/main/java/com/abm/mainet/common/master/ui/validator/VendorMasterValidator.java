package com.abm.mainet.common.master.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.dto.VendorMasterUploadDto;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

public class VendorMasterValidator implements Validator {
	@Override
	public boolean supports(final Class<?> aClass) {
		return TbAcVendormaster.class.equals(aClass);
	}

	private TbAcVendormasterService tbAcVendormasterService;
	
	@Autowired
	private DepartmentJpaRepository deptRepo;

	/**
	 * @return the tbAcVendormasterService
	 */
	public TbAcVendormasterService getTbAcVendormasterService() {
		return tbAcVendormasterService;
	}

	/**
	 * @param tbAcVendormasterService
	 *            the tbAcVendormasterService to set
	 */
	public void setTbAcVendormasterService(final TbAcVendormasterService tbAcVendormasterService) {
		this.tbAcVendormasterService = tbAcVendormasterService;
	}

	@Override
	public void validate(final Object object, final Errors errors) {
		final TbAcVendormaster tbAcVendormaster = (TbAcVendormaster) object;
		final ApplicationSession session = ApplicationSession.getInstance();

		if ((tbAcVendormaster.getCpdVendortype() == null)
				|| tbAcVendormaster.getCpdVendortype().equals(MainetConstants.BLANK)) {
			ValidationUtils.rejectIfEmpty(errors, MainetConstants.VENDOR_MASTER.CPD_VENDOR_TYPE,
					MainetConstants.VENDOR_MASTER.ERROR_VENDOR_TYPE,
					session.getMessage("vendormaster.validator.vendortype"));
		}

		if ((tbAcVendormaster.getVmVendorname() == null)
				|| tbAcVendormaster.getVmVendorname().equals(MainetConstants.BLANK)) {
			ValidationUtils.rejectIfEmpty(errors, MainetConstants.VENDOR_MASTER.VM_VENDOR_NAME,
					MainetConstants.VENDOR_MASTER.ERROR_VENDOR_NAME,
					session.getMessage("vendormaster.validator.vendorname"));
		}

		if ((tbAcVendormaster.getVmPanNumber() != null) && !tbAcVendormaster.getVmPanNumber().isEmpty()) {
			final List<TbAcVendormaster> vmpanNumber = tbAcVendormasterService.getVendorvmPanNumber(
					tbAcVendormaster.getVmPanNumber(), UserSession.getCurrent().getOrganisation().getOrgid());
			if (((vmpanNumber != null) && !vmpanNumber.isEmpty())) {
				ValidationUtils.rejectIfEmpty(errors, MainetConstants.VENDOR_MASTER.PAN_NUMBER_TEMP,
						MainetConstants.VENDOR_MASTER.ERROR_PAN_NUMBER,
						session.getMessage("vendormaster.validator.account"));

			}
		}

		if (((tbAcVendormaster.getVmVendoradd() == null)
				|| tbAcVendormaster.getVmVendoradd().equals(MainetConstants.BLANK))&& !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			ValidationUtils.rejectIfEmpty(errors, MainetConstants.VENDOR_MASTER.VM_VENDOR_ADD,
					MainetConstants.VENDOR_MASTER.ERROR_VM_VENDOR_ADD,
					session.getMessage("vendormaster.validator.address"));
		}

		if ((tbAcVendormaster.getCpdVendorSubType() == null)
				|| tbAcVendormaster.getCpdVendorSubType().equals(MainetConstants.BLANK)) {
			ValidationUtils.rejectIfEmpty(errors, MainetConstants.VENDOR_MASTER.VENDOR_SUB_TYPE,
					MainetConstants.VENDOR_MASTER.ERROR_VENDOR_SUB_TYPE,
					session.getMessage("vendormaster.validator.vendorsubtype"));
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	public List<VendorMasterUploadDto> excelValidation(List<VendorMasterUploadDto> vendorMasterUploadDtos,
			BindingResult bindingResult, List<LookUp> venderType, final List<LookUp> vendorSubType,
			final Map<Long, String> functionMasterStatusLastLevels, List<AccountHeadPrimaryAccountCodeMasterBean> venderPrimaryHead,
			final Map<Long, String> bankMap, Long lookUpStatusId, Long cpdVendortype, List<LookUp> vendorClassName) {
		int rowNo = 0;
		final List<VendorMasterUploadDto> vendorMasterUploadDtoList = new ArrayList<>();

		final ApplicationSession session = ApplicationSession.getInstance();
		String deptCode = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
		Set<VendorMasterUploadDto> vendorMasterExport = vendorMasterUploadDtos.stream()
				.filter(dto -> Collections.frequency(vendorMasterUploadDtos, dto) > 1).collect(Collectors.toSet());
		if (vendorMasterExport.isEmpty()) {

			for (VendorMasterUploadDto vendorMasterUploadDto : vendorMasterUploadDtos) {

				VendorMasterUploadDto dto = new VendorMasterUploadDto();
				rowNo++;
				if (vendorMasterUploadDto.getType() == null || vendorMasterUploadDto.getType().isEmpty()) {
				    bindingResult.addError(new org.springframework.validation.FieldError(
						MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
						new String[] { MainetConstants.ERRORS }, null,
						session.getMessage("vendormaster.excel.type") + rowNo));
				                break;
				}

					

				if (vendorMasterUploadDto.getName() == null || vendorMasterUploadDto.getName().isEmpty()) {
				    bindingResult.addError(new org.springframework.validation.FieldError(
						MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
						new String[] { MainetConstants.ERRORS }, null,
						session.getMessage("vendormaster.excel.name") + rowNo));
				                break;
				}

					

				/*
				 * if (vendorMasterUploadDto.getFunction() == null ||
				 * vendorMasterUploadDto.getFunction().isEmpty()) { bindingResult.addError(new
				 * org.springframework.validation.FieldError(
				 * MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK,
				 * null, false, new String[] { MainetConstants.ERRORS }, null,
				 * session.getMessage("vendormaster.excel.function") + rowNo)); break; }
				 */

				/*
				 * if (vendorMasterUploadDto.getAddress() == null ||
				 * vendorMasterUploadDto.getAddress().isEmpty()) { bindingResult.addError(new
				 * org.springframework.validation.FieldError(
				 * MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK,
				 * null, false, new String[] { MainetConstants.ERRORS }, null,
				 * session.getMessage("vendormaster.excel.address") + rowNo)); break; }
				 */

					if (vendorMasterUploadDto.getSubType() == null || vendorMasterUploadDto.getSubType().isEmpty()) {
						bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("vendormaster.excel.subType") + rowNo));
					                break;
					}
					if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)
							|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
							&& deptCode.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.RNL)){
						for (LookUp list : venderType) {
							if (vendorMasterUploadDto.getType().equalsIgnoreCase(list.getDescLangFirst()) || vendorMasterUploadDto.getType().equalsIgnoreCase(list.getDescLangSecond())) {
								if ((vendorMasterUploadDto.getPrimaryAccountHead() == null || vendorMasterUploadDto.getPrimaryAccountHead().isEmpty())
										&& !list.getLookUpCode().equalsIgnoreCase(MainetConstants.TbAcVendormaster.TENANT)) {
									bindingResult.addError(new org.springframework.validation.FieldError(
										MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
										new String[] { MainetConstants.ERRORS }, null,
										session.getMessage("vendormaster.excel.primaryHead") + rowNo));
								                break;

								}
							}

						}
					}else {
						if (vendorMasterUploadDto.getPrimaryAccountHead() == null || vendorMasterUploadDto.getPrimaryAccountHead().isEmpty()) {
							bindingResult.addError(new org.springframework.validation.FieldError(
								MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
								new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("vendormaster.excel.primaryHead") + rowNo));
						                break;

						}
					}
					
					
					int typeExist = 0;
					for (LookUp list : venderType) {
						if (vendorMasterUploadDto.getType().equalsIgnoreCase(list.getDescLangFirst().trim())) {
							dto.setType(String.valueOf(list.getLookUpId()));
							typeExist++;
						}

					}
					if (typeExist == 0) {

						bindingResult.addError(new org.springframework.validation.FieldError(
								MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
								new String[] { MainetConstants.ERRORS }, null,
								rowNo + session.getMessage("vendormaster.excel.typeExist")));
						break;
					}

					int SubTypeExist = 0;
					for (LookUp list : vendorSubType) {
						if (vendorMasterUploadDto.getSubType().equalsIgnoreCase(list.getDescLangFirst())) {
							dto.setSubType(String.valueOf(list.getLookUpId()));
							SubTypeExist++;
						}

					}
					if (SubTypeExist == 0) {

						bindingResult.addError(new org.springframework.validation.FieldError(
								MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
								new String[] { MainetConstants.ERRORS }, null,
								rowNo + session.getMessage("vendormaster.excel.subTypeExist")));
						break;
					}

				if(vendorMasterUploadDto.getFunction()!=null) {
				int functionExists = 0;
				for (Map.Entry<Long, String> entry : functionMasterStatusLastLevels.entrySet()) {
					if (vendorMasterUploadDto.getFunction().equals(entry.getValue())) {
						dto.setFunction(entry.getKey().toString());
						functionExists++;
					}
				}
				if (functionExists == 0) {
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("vendormaster.excel.functionExist")));
					break;
				}
				}

					int primaryHeadExists = 0;
					if(cpdVendortype != null && dto.getType() != null) {
						for(AccountHeadPrimaryAccountCodeMasterBean bean : venderPrimaryHead) {
							if (bean.getCpdIdAccountType().equals(cpdVendortype)
									&& bean.getCpdIdPayMode().equals(Long.valueOf(dto.getType()))
									&& bean.getPacStatusCpdId().equals(lookUpStatusId)
									&& bean.getEditedChildStatus().equals(lookUpStatusId)) {
								if(vendorMasterUploadDto.getPrimaryAccountHead()!=null && vendorMasterUploadDto.getPrimaryAccountHead().equalsIgnoreCase(bean.getPrimaryAcCodeHeadCompcode())){
									dto.setPrimaryAccountHead(bean.getPrimaryAcHeadId().toString());
									primaryHeadExists++;
								}
							}
						}
					}
					/*for (Map.Entry<Long, String> entry : venderPrimaryHead.entrySet()) {
						if (vendorMasterUploadDto.getPrimaryAccountHead().equals(entry.getValue())) {
							dto.setPrimaryAccountHead(entry.getKey().toString());
							primaryHeadExists++;
						}
					}*/
					if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)
							|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
							&& deptCode.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.RNL)){
						for (LookUp list : venderType) {
							if (vendorMasterUploadDto.getType().equalsIgnoreCase(list.getDescLangFirst().trim()) || vendorMasterUploadDto.getType().equalsIgnoreCase(list.getDescLangSecond())) {
								if (primaryHeadExists == 0 && !list.getLookUpCode().equalsIgnoreCase(MainetConstants.TbAcVendormaster.TENANT)) {
									bindingResult.addError(new org.springframework.validation.FieldError(
											MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
											new String[] { MainetConstants.ERRORS }, null,
											rowNo + session.getMessage("vendormaster.excel.primaryHeadExist")));
									break;
								}
							}}
					}else {
						if (primaryHeadExists == 0) {
							bindingResult.addError(new org.springframework.validation.FieldError(
									MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
									new String[] { MainetConstants.ERRORS }, null,
									rowNo + session.getMessage("vendormaster.excel.primaryHeadExist")));
							break;
						}
					}
									
					int bankIfscCodeExists = 0;
						if(vendorMasterUploadDto.getBankBranchIfsc() != null && !vendorMasterUploadDto.getBankBranchIfsc().isEmpty()) {
							for (Map.Entry<Long, String> entry : bankMap.entrySet()) {
								if (vendorMasterUploadDto.getBankBranchIfsc().equals(entry.getValue())) {
									dto.setBankBranchIfsc(entry.getValue());
									dto.setBankId(entry.getKey());
									bankIfscCodeExists++;
								}
						}
						if (bankIfscCodeExists == 0) {
							bindingResult.addError(new org.springframework.validation.FieldError(
									MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
									new String[] { MainetConstants.ERRORS }, null,
									rowNo + session.getMessage("vendormaster.excel.bankIFSCCodeExist")));
							break;
						}
					}
						
				/*
				 * int vendorClassNameExist = 0; for (LookUp list : vendorClassName) { if
				 * (list.getDescLangFirst().trim().equalsIgnoreCase(vendorMasterUploadDto.
				 * getVendorClassName())) {
				 * dto.setVendorClassName(String.valueOf(list.getLookUpId()));
				 * vendorClassNameExist++; } } if (vendorClassNameExist == 0) {
				 * 
				 * bindingResult.addError(new org.springframework.validation.FieldError(
				 * MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK,
				 * null, false, new String[] { MainetConstants.ERRORS }, null, rowNo +
				 * session.getMessage("vendormaster.excel.vendorClassExist"))); break; }
				 */
					
					boolean isValidGstNo = false;
					if(StringUtils.isNotBlank(vendorMasterUploadDto.getGstNum())) {
					    isValidGstNo = Pattern.matches(MainetConstants.RegEx.GSTIN_REG_EX, vendorMasterUploadDto.getGstNum());
					    if(isValidGstNo) {
						    dto.setGstNum(vendorMasterUploadDto.getGstNum());
					    }
					    else {
						bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("vendormaster.excel.gstin")));
					        break;
					    }
					}
					boolean isValidMobNo = false;
					if(vendorMasterUploadDto.getMobileNum() != null) {
					    isValidMobNo = Pattern.matches(MainetConstants.MOB_PATTERN, vendorMasterUploadDto.getMobileNum().toString());
					    if(isValidMobNo) {
						    dto.setMobileNum(vendorMasterUploadDto.getMobileNum());
					    }
					    else {
						bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("vendormaster.excel.mobile")));
						break;
					    }
					}
					
					boolean isValidEmail = false;
					if(StringUtils.isNotEmpty(vendorMasterUploadDto.getEmailId())) {
					    isValidEmail = Pattern.matches(MainetConstants.EMAIL_PATTERN, vendorMasterUploadDto.getEmailId());
					    if(isValidEmail) {
						dto.setEmailId(vendorMasterUploadDto.getEmailId());
					    }
					    else {
						bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("vendormaster.excel.email")));
					           break;
					    }
					}
					
					dto.setName(vendorMasterUploadDto.getName());
					dto.setPayTo(vendorMasterUploadDto.getPayTo());
					dto.setUidNum(vendorMasterUploadDto.getUidNum());
					dto.setVatNum(vendorMasterUploadDto.getVatNum());
					dto.setPanNum(vendorMasterUploadDto.getPanNum());
					dto.setBankAcNum(vendorMasterUploadDto.getBankAcNum());
					dto.setAddress(vendorMasterUploadDto.getAddress());
					dto.setAccOldHeadCode(vendorMasterUploadDto.getAccOldHeadCode());
					vendorMasterUploadDtoList.add(dto);
			}
		} else {

			bindingResult.addError(
					new org.springframework.validation.FieldError(MainetConstants.TbAcVendormaster.TB_VENDORMASTER,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
		}
		return vendorMasterUploadDtoList;
	}
}
