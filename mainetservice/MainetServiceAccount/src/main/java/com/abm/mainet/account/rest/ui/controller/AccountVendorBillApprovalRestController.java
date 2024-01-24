package com.abm.mainet.account.rest.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.account.rest.dto.ResponseDto;
import com.abm.mainet.account.rest.dto.VendorBillApprovalExtDTO;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.master.service.ICommonEncryptionAndDecryption;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author tejas.kotekar
 * @since 1-Apr-2017
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/VendorBills")
public class AccountVendorBillApprovalRestController {

    private static final Logger LOGGER = Logger.getLogger(AccountVendorBillApprovalRestController.class);
    @Resource
    private AccountBillEntryService billEntryService;

    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Resource
    private AccountBudgetProjectedExpenditureService projectedExpenditureService;

    @Resource
    private TbFinancialyearService tbFinancialyearService;

    @Resource
    private BudgetCodeService budgetCodeService;
    
    
    @Resource
    private ICommonEncryptionAndDecryption commonEncryptionAndDecryption;
    
  

    private static final String CANNOT_BE_NULL_EMPTY = " cannot be null or empty.";

    /**
     * consume this service by using service {@code URI=/VendorBills/getBillRecords }
     * @param requestDTO : dto, which {@code dataModel} field can hold {@code VendorBillApprovalDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether records fetched or not.
     *
     */
    @RequestMapping(value = "/getBillRecords", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAvailableRecordsOnSearch(@RequestBody final VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                List<VendorBillApprovalDTO> billDataList = null;
                final String validationError = billEntryService.validateBillSearch(vendorApprovalDto);
                if (validationError.isEmpty()) {
                    billDataList = billEntryService.getBillEntryDetailsForSearch(vendorApprovalDto);
                    if ((billDataList != null) && !billDataList.isEmpty()) {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(billDataList);
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record.search"));
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.bill.search") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/recordForView", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getRecordForView(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateForViewAndEdit(vendorApprovalDto.getOrgId(),
                        vendorApprovalDto.getId());
                if (validationError.isEmpty()) {
                    vendorApprovalDto = billEntryService.getRecordForView(vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getId());
                    if (vendorApprovalDto != null) {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record.nofound"));
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.record.view") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/recordForEdit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getRecordForEdit(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateForViewAndEdit(vendorApprovalDto.getOrgId(),
                        vendorApprovalDto.getId());
                if (validationError.isEmpty()) {
                    vendorApprovalDto = billEntryService.getRecordForEdit(vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getId());
                    responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.record.edit") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/doSaveBillApproval", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> saveBillApproval(@RequestBody final VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        VendorBillApprovalDTO approvalDto = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateInputBeforeSave(vendorApprovalDto);
                if (validationError.isEmpty()) {
                    approvalDto = billEntryService.saveBillApproval(vendorApprovalDto);
                    if (approvalDto.getSalaryBillExitFlag().equals(MainetConstants.Y_FLAG)) {
                        responseEntity = ResponseEntity.status(HttpStatus.OK)
                                .body("Salary Bill Saved Successfully  With Bill No :"+approvalDto.getBillNo());
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.bill.save") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getVendorNames", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getVendorNames(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateOrgIdAndLangId(vendorApprovalDto.getOrgId(),
                        vendorApprovalDto.getLanguageId());
                if (validationError.isEmpty()) {
                    vendorApprovalDto = tbAcVendormasterService.getVendorNames(vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getLanguageId());
                    if (vendorApprovalDto.getLookUpList().isEmpty()) {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.vendor.name") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getDepartments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getDepartments(@RequestBody final VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateGetDepartments(vendorApprovalDto.getOrgId());
                if (validationError.isEmpty()) {
                    final Organisation organisation = new Organisation();
                    organisation.setOrgid(vendorApprovalDto.getOrgId());
                    final List<DepartmentLookUp> deptList = CommonMasterUtility.getDepartmentForWS(organisation);
                    if ((deptList != null) && !deptList.isEmpty()) {
                        vendorApprovalDto.setDepartmentList(deptList);
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.dept.name") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getDepartmentId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getDepartmentIdByBudgetCode(@RequestBody final VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final FinancialYear financialYear = tbFinancialyearService.getFinanciaYearByDate(
                        UtilityService.convertStringDateToDateFormat(vendorApprovalDto.getBillEntryDate()));
                String validationError = null;
                Long finYearId = null;
                if (financialYear != null) {
                    finYearId = financialYear.getFaYear();
                    validationError = validateGetDepartmentId(vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getBudgetCodeId(), finYearId);
                }
                if (validationError.isEmpty()) {
                    final Long departmentId = projectedExpenditureService.getDepartmentFromBudgetProjectedExpenditureByBudgetCode(
                            vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getBudgetCodeId(), finYearId);
                    vendorApprovalDto.setDepartmentId(departmentId);
                    if (departmentId != null) {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.dept.name") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getExpenditureDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getExpenditureDetails(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateOrgIdAndLangId(vendorApprovalDto.getOrgId(),
                        vendorApprovalDto.getLanguageId());
                if (validationError.isEmpty()) {
                    vendorApprovalDto = projectedExpenditureService.getExpenditureDetails(vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getLanguageId());
                    if (vendorApprovalDto.getLookUpList().isEmpty()) {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.expenditure.detail") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getDeductionDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> geDeductionDetails(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateOrgIdAndSuperOrgId(vendorApprovalDto.getOrgId(),
                        vendorApprovalDto.getSuperOrgId());
                if (validationError.isEmpty()) {
                    vendorApprovalDto = budgetCodeService.geDeductionDetails(vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getSuperOrgId());
                    if (vendorApprovalDto.getLookUpList().isEmpty()) {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.deduct.detail") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/viewExpenditureDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> viewExpenditureDetails(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateViewDetails(vendorApprovalDto.getOrgId(),
                        vendorApprovalDto.getBudgetCodeId(), vendorApprovalDto.getSanctionedAmount());
                if (validationError.isEmpty()) {
                    vendorApprovalDto = projectedExpenditureService.viewExpenditureDetails(vendorApprovalDto.getOrgId(),
                            vendorApprovalDto.getBudgetCodeId(), vendorApprovalDto.getSanctionedAmount());
                    if (vendorApprovalDto == null) {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.expenditure.view") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/viewSalaryBillBudgetDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> viewInvoiceSalaryBillBudgetDetails(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                final String validationError = billEntryService.validateViewBudgetInputDetails(vendorApprovalDto);
                if (validationError.isEmpty()) {
                    vendorApprovalDto = projectedExpenditureService.viewInvoiceSalaryBillBudgetDetails(vendorApprovalDto);
                    if (vendorApprovalDto == null) {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.expenditure.detail") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    private String validateGetDepartmentId(final Long orgId, final Long budgetCodeId, final Long finYearId) {

        final StringBuilder builder = new StringBuilder();
        if (orgId == null) {
            builder.append(MainetConstants.AccountBillEntry.APPEND_ORG_ID);
        }
        if (budgetCodeId == null) {
            builder.append(MainetConstants.AccountBillEntry.BUGDET_CODE);
        }
        if (finYearId == null) {
            builder.append(MainetConstants.AccountBillEntry.FIN_YEAR_ID);
        }
        if (!builder.toString().isEmpty()) {
            builder.append(CANNOT_BE_NULL_EMPTY);
        }
        return builder.toString();
    }

    
    @RequestMapping(value = "/doExtSaveBillApproval", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> saveExtBillApproval(@RequestBody final String inputRequest,HttpServletRequest request) {
		final ApplicationSession session = ApplicationSession.getInstance();
		ResponseEntity<?> responseEntity = null;
		VendorBillApprovalDTO approvalDto = null;
		try {
			Object decryptExternalReceiptDto = commonEncryptionAndDecryption.commonDecryption(inputRequest);
			ObjectMapper mapper = new ObjectMapper();
			VendorBillApprovalExtDTO vendorApprovalDtoExt = mapper.convertValue(decryptExternalReceiptDto,
					VendorBillApprovalExtDTO.class);
			StringBuilder createCheckSum = new StringBuilder();
			createCheckSum.append(vendorApprovalDtoExt.getCreatedBy());
			createCheckSum.append(MainetConstants.operator.ORR);
			createCheckSum.append(vendorApprovalDtoExt.getUlbCode());
			String internalChecksum = commonEncryptionAndDecryption.commonCheckSum(createCheckSum.toString());

			if (StringUtils.equals(internalChecksum, vendorApprovalDtoExt.getCheckSum())) {
				List<String> validationError = billEntryService.ValidateExternalRequest(vendorApprovalDtoExt);
				if (validationError.isEmpty()) {
					List<String> ValidateData = new ArrayList<>();
					vendorApprovalDtoExt.setlIpMac(Utility.getClientIpAddress(request));
					VendorBillApprovalDTO intDto = billEntryService.convertExternaDtoToInternaDto(vendorApprovalDtoExt,
							ValidateData);
					if (ValidateData.isEmpty()) {
						approvalDto = billEntryService.saveBillApproval(intDto);
						if (approvalDto.getSalaryBillExitFlag().equals(MainetConstants.Y_FLAG)) {
							responseEntity = ResponseEntity.status(HttpStatus.OK).body(ResponseDto.getResponse(
									approvalDto.getBillNo(), "Salary Bill Saved Successfully ", null, HttpStatus.OK));
						}
					} else {
						responseEntity = ResponseEntity.status(HttpStatus.OK)
								.body(ResponseDto.getResponse(null, "Fail", ValidateData, HttpStatus.OK));

					}
				} else {
					responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(ResponseDto.getResponse(null, "Fail", validationError, HttpStatus.OK));

				}
			} else {
				LOGGER.error(session.getMessage("Salary Bill Entry Failed Due To:")
						+ session.getMessage("improper input parameter for ReceiptExternalDto, these fields {")
						+ ResponseEntity.status(HttpStatus.BAD_REQUEST).body(""));
				return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
						.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.getResponse(null, "Fail",
								Arrays.asList("Invalid check sum"), HttpStatus.OK)));
			}
		} catch (final Exception ex) {
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseDto.getResponse(null, "fail", null, HttpStatus.INTERNAL_SERVER_ERROR));
			LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(),
					ex);
		}
		return responseEntity;
	}
    
    
	@RequestMapping(value = "/getBudget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	public Map<Long, String> getBudget(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
	
		Map<Long, String> responseEntity = new HashMap<>();
		try {
			List<Object[]> dto = null;
			if (vendorApprovalDto.getDepartmentId() != null) {
				dto = budgetCodeService.getSecondaryHeadcodesDeptField(vendorApprovalDto.getOrgId(),
						vendorApprovalDto.getDepartmentId(), vendorApprovalDto.getFieldId());
				if ((dto != null) && !dto.isEmpty()) {
	                 for (Object[] obj : dto) {
	                	 responseEntity.put(Long.valueOf(obj[0].toString()), (String) obj[1]);
	                 }
	             }
			} else {
				dto = budgetCodeService.getSecondaryHeadcodesWithField(vendorApprovalDto.getOrgId(),
						vendorApprovalDto.getFieldId());
				if ((dto != null) && !dto.isEmpty()) {
	                 for (Object[] obj : dto) {
	                	 responseEntity.put(Long.valueOf(obj[0].toString()), (String) obj[1]);
	                 }
	             }
			}

		} catch (final Exception ex) {
			
		}
		return responseEntity;
	}
	
	
	@RequestMapping(value = "/viewCouncilBudgetDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> viewCouncilBudgetDetails(@RequestBody VendorBillApprovalDTO vendorApprovalDto) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
            if (billSalaryValidation.isEmpty()) {
                    vendorApprovalDto = projectedExpenditureService.viewCouncilBillBudgetDetails(vendorApprovalDto);
                    if (vendorApprovalDto == null) {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.vendor.bill.approval.rest.record"));
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorApprovalDto);
                    }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.vendor.bill.approval.rest.expenditure.detail") + ex.getMessage());
            LOGGER.error(session.getMessage("account.vendor.bill.approval.rest.bill.approve.error") + ex.getMessage(), ex);
        }
        return responseEntity;
    }
}
