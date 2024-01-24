package com.abm.mainet.account.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindingResult;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountSecondaryHeadMasterExportDto;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

public class SecondaryHeadMasterExcelValidator {

	public int count = 0;

	public List<AccountSecondaryHeadMasterExportDto> excelValidation(
			final List<AccountSecondaryHeadMasterExportDto> secondaryHeadMasterExportDtos,
			final BindingResult bindingResult, final Map<Long, String> primaryHead,
			final Map<Long, String> functionHead, List<LookUp> lookUpList) {
		int rowNo = 0;
		final List<AccountSecondaryHeadMasterExportDto> secHeadMasList = new ArrayList<>();
		final ApplicationSession session = ApplicationSession.getInstance();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		Set<AccountSecondaryHeadMasterExportDto> secondaryHeadMasterExport = secondaryHeadMasterExportDtos.stream()
				.filter(dto -> Collections.frequency(secondaryHeadMasterExportDtos, dto) > 1)
				.collect(Collectors.toSet());
		StringBuilder builder = new StringBuilder();
		if (CollectionUtils.isNotEmpty(secondaryHeadMasterExport)) {
			secondaryHeadMasterExport.forEach(dto -> {
				builder.append(dto.getDescription() + "&");
			});
		}

		if (secondaryHeadMasterExport.isEmpty()) {

			for (AccountSecondaryHeadMasterExportDto accountSecondaryHeadMasterExportDto : secondaryHeadMasterExportDtos) {

				AccountSecondaryHeadMasterExportDto dto = new AccountSecondaryHeadMasterExportDto();
				rowNo++;
				int countPrimaryHead = 0;
				if (accountSecondaryHeadMasterExportDto.getPrimaryHead() == null
						|| accountSecondaryHeadMasterExportDto.getPrimaryHead().isEmpty()) {
					countPrimaryHead++;
				}
				if (countPrimaryHead != 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("accounts.Secondaryhead.empty.excel.primaryhead") + rowNo));
					break;
				}

				int countFunctionHead = 0;
				if (accountSecondaryHeadMasterExportDto.getFunction() == null
						|| accountSecondaryHeadMasterExportDto.getFunction().isEmpty()) {
					countFunctionHead++;
				}
				if (countFunctionHead != 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("accounts.Secondaryhead.empty.excel.function") + rowNo));
					break;
				}

				int countLedgerType = 0;
				if (accountSecondaryHeadMasterExportDto.getLedgerType() == null
						|| accountSecondaryHeadMasterExportDto.getLedgerType().isEmpty()) {
					countLedgerType++;
				}
				if (countLedgerType != 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("accounts.Secondaryhead.empty.excel.LedgerType") + rowNo));
					break;
				}

				int countDesc = 0;
				if ((accountSecondaryHeadMasterExportDto.getDescription() == null
						|| accountSecondaryHeadMasterExportDto.getDescription().isEmpty())) {
					countDesc++;
				} else {
					dto.setDescription(accountSecondaryHeadMasterExportDto.getDescription());
				}
				if (countDesc != 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("accounts.Secondaryhead.empty.excel.secondaryHeadDesc") + rowNo));
					break;
				}

				int primaryHeadExist = 0;
				for (Map.Entry<Long, String> entry : primaryHead.entrySet()) {
					if (accountSecondaryHeadMasterExportDto.getPrimaryHead().trim()
							.equals(entry.getValue().toString())) {
						dto.setPrimaryHead(entry.getKey().toString());
						primaryHeadExist++;
					}
				}

				if (primaryHeadExist == 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("accounts.Secondaryhead.db.primaryheadcode")));
					break;
				}

				int functionHeadExist = 0;
				for (Map.Entry<Long, String> entry : functionHead.entrySet()) {
					if (accountSecondaryHeadMasterExportDto.getFunction().trim().equals(entry.getValue())) {
						dto.setFunction(entry.getKey().toString());
						functionHeadExist++;
					}
				}
				if (functionHeadExist == 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("accounts.Secondaryhead.db.functioncode")));
					break;
				}
				int ledgerTypeExist = 0;
				int ledgerCodeExist = 0;
				for (LookUp lookUp : lookUpList) {
					if (accountSecondaryHeadMasterExportDto.getLedgerType()
							.equalsIgnoreCase((lookUp.getLookUpDesc()))) {
						dto.setLedgerType((String.valueOf(lookUp.getLookUpId())));
						ledgerTypeExist++;
					}
				}

				String vendorDesc = CommonMasterUtility.findLookUpCodeDesc(PrefixConstants.SECONDARY_LOOKUPCODE, orgid,
						"VD");
				String bankDesc = CommonMasterUtility.findLookUpCodeDesc(PrefixConstants.SECONDARY_LOOKUPCODE, orgid,
						"BK");
				if (vendorDesc.equalsIgnoreCase(accountSecondaryHeadMasterExportDto.getLedgerType())) {
					ledgerCodeExist++;
				}
				if (bankDesc.equalsIgnoreCase(accountSecondaryHeadMasterExportDto.getLedgerType())) {
					ledgerCodeExist++;
				}

				if (ledgerTypeExist == 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("accounts.Secondaryhead.db.ledgertype")));
					break;
				}
				if (ledgerCodeExist != 0) {
					count++;
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							rowNo + session.getMessage("accounts.Secondaryhead.db.ledgertype")));
					break;
				}

				if (accountSecondaryHeadMasterExportDto.getOldSachHeadCode() != null) {
					dto.setOldSachHeadCode(accountSecondaryHeadMasterExportDto.getOldSachHeadCode());
				}

				secHeadMasList.add(dto);
			}
		} else {
			count++;
			bindingResult.addError(
					new org.springframework.validation.FieldError(MainetConstants.SecondaryheadMaster.SECONDARY_HEAD,
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
						  session.getMessage("accounts.Secondaryhead.empty.excel.duplicate.in.record")));
			String[] erros = builder.toString().split("&");
			int i = 1;
			if (erros != null && !erros.toString().isEmpty()) {
				for (String msg : erros) {
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null, i + ") " + msg));
					i++;
				}
			}
		}
		return secHeadMasList;
	}

	public void primaryFuncLedgTypeDbCombinationExistsValid(
			List<AccountSecondaryHeadMasterExportDto> secondaryHeadMasterExportList, final BindingResult bindingResult,
			List<AccountHeadSecondaryAccountCodeMasterEntity> accountHeadList) {
		final ApplicationSession session = ApplicationSession.getInstance();
		int rowNo = 0;
		for (AccountSecondaryHeadMasterExportDto accountSecondaryHeadMasterExportDto : secondaryHeadMasterExportList) {
			rowNo++;
			int prifunExists = 0;
			for (AccountHeadSecondaryAccountCodeMasterEntity list : accountHeadList) {
				Long pacHeadId = list.getTbAcPrimaryheadMaster().getPrimaryAcHeadId();
				Long functionId = list.getTbAcFunctionMaster().getFunctionId();
				Long ledgerTypeId = list.getSacLeddgerTypeCpdId();
				String secHeadDes = list.getSacHeadDesc();
				if (pacHeadId.equals(Long.valueOf(accountSecondaryHeadMasterExportDto.getPrimaryHead()))
						&& (functionId.equals(Long.valueOf(accountSecondaryHeadMasterExportDto.getFunction())))
						&& (ledgerTypeId.equals(Long.valueOf(accountSecondaryHeadMasterExportDto.getLedgerType()))
								&& secHeadDes.equals(accountSecondaryHeadMasterExportDto.getDescription()))) {
					prifunExists++;
				}
			}
			if (prifunExists != 0) {
				bindingResult.addError(new org.springframework.validation.FieldError(
						MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
						new String[] { MainetConstants.ERRORS }, null,
						rowNo + " " + session.getMessage("accounts.Secondaryhead.db.duplicate")));
				break;
			}
		}
	}
}
