package com.abm.mainet.common.master.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.BankMasterUploadDTO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

public class BankMasterValidator {

	public List<BankMasterUploadDTO> excelValidation(List<BankMasterUploadDTO> bankMasterUploadDtos,
			BindingResult bindingResult) {
		int rowNo = 0;
		final List<BankMasterUploadDTO> bankMasterUploadDtoList = new ArrayList<>();

		final ApplicationSession session = ApplicationSession.getInstance();

		Set<BankMasterUploadDTO> bankMasterExport = bankMasterUploadDtos.stream()
				.filter(dto -> Collections.frequency(bankMasterUploadDtos, dto) > 1)
				.collect(Collectors.toSet());
	if (CollectionUtils.isEmpty(bankMasterExport)) {

	    for (BankMasterUploadDTO bankMasterUploadDto : bankMasterUploadDtos) {

		BankMasterUploadDTO dto = new BankMasterUploadDTO();
		rowNo++;

		
		if (StringUtils.isBlank(bankMasterUploadDto.getBankName()) || !Utility.hasCharacter(bankMasterUploadDto.getBankName())) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.bankName") + rowNo));
		    break;
		} else {
		    dto.setBankName(bankMasterUploadDto.getBankName());
		}

		if (StringUtils.isBlank(bankMasterUploadDto.getBankBranch())) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.bankBranch") + rowNo));
		    break;

		} else {
		    dto.setBankBranch(bankMasterUploadDto.getBankBranch());
		}

		
		boolean isValidIFSC = false;
		if (StringUtils.isNotBlank(bankMasterUploadDto.getIfscCode())) {
		    isValidIFSC = Pattern.matches(MainetConstants.RegEx.BANK_IFSC_REG_EX, bankMasterUploadDto.getIfscCode().trim());
		}

		if (StringUtils.isBlank(bankMasterUploadDto.getIfscCode()) || !isValidIFSC) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.IFSCCode") + rowNo));
		    break;
		} else {
		    dto.setIfscCode(bankMasterUploadDto.getIfscCode());
		}

		boolean isValidMICR = false;
		if (bankMasterUploadDto.getMicrCode() != null) {

		    Pattern pattern = Pattern.compile(MainetConstants.RegEx.BANK_MICR_REG_EX);
		    Matcher matcher = pattern.matcher(bankMasterUploadDto.getMicrCode().toString());
		    isValidMICR = matcher.matches();
		}
		final Organisation org = UserSession.getCurrent().getOrganisation();
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SFAC)){
			if (bankMasterUploadDto.getMicrCode() != null &&  !isValidMICR) 
				 bindingResult
				    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
					    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
					    session.getMessage("account.bankmaster.excel.MICRCodeDigitValid") + rowNo));
			    break;		
			
		}else {
		if (!Optional.ofNullable(bankMasterUploadDto.getMicrCode()).isPresent() || !isValidMICR) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.MICRCode") + rowNo));
		    break;
		} else {
		    dto.setMicrCode(bankMasterUploadDto.getMicrCode());
		}
		}
		if (StringUtils.isBlank(bankMasterUploadDto.getCity()) || !Utility.hasCharacter(bankMasterUploadDto.getCity())) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.city") + rowNo));
		    break;
		} else {
		    dto.setCity(bankMasterUploadDto.getCity());
		}

		if (StringUtils.isBlank(bankMasterUploadDto.getDistrict()) || !Utility.hasCharacter(bankMasterUploadDto.getDistrict())) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.district") + rowNo));
		    break;

		} else {
		    dto.setDistrict(bankMasterUploadDto.getDistrict());
		}

		if (StringUtils.isBlank(bankMasterUploadDto.getState()) || !Utility.hasCharacter(bankMasterUploadDto.getState())) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.state") + rowNo));
		    break;
		} else {
		    dto.setState(bankMasterUploadDto.getState());
		}

		if (StringUtils.isBlank(bankMasterUploadDto.getBranchAddr())) {

		    bindingResult
			    .addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
				    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
				    session.getMessage("account.bankmaster.excel.branchAddr") + rowNo));
		    break;

		} else {
		    dto.setBranchAddr(bankMasterUploadDto.getBranchAddr());
		}
		
		dto.setContDet(bankMasterUploadDto.getContDet());
		bankMasterUploadDtoList.add(dto);
	    }
	} else {
	    bindingResult.addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO,
		    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
		    session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
	}
	return bankMasterUploadDtoList;
    }
	

	
	
}
