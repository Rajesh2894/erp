package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.ChequeBookUtilizationDto;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.service.TbAcChequebookleafMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author tejas.kotekar
 *
 */
@Controller()
@RequestMapping("/ChequeBookUtilization.html")
public class ChequeBookUtilizationController extends AbstractController {

    private static final String JSP_LIST = "ChequeBookUtilization/list";
    private final static String CHEQUEBOOK_UTILIZATION_DTO = "chequeBookUtilizationDto";
    private static final String BANK_AC_MAP = "bankAccountMap";
    private static final String NO_CHEQUE_DETAILS_FOUND = "No cheque details found for cheque book id";

    @Resource
    private TbBankmasterService banksMasterService;
    @Resource
    private TbAcChequebookleafMasService chequeBookLeafService;
    @Resource
    private PaymentEntrySrevice paymentEntrySerivce;
    @Resource
    private AccountContraVoucherEntryService contraVoucherService;

    private List<ChequeBookUtilizationDto> masterDtoList = null;

    /**
     * @param controllerClass
     * @param entityName
     */
    public ChequeBookUtilizationController() {
        super(ChequeBookUtilizationController.class, CHEQUEBOOK_UTILIZATION_DTO);
    }

    @RequestMapping()
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {

        masterDtoList = new ArrayList<>();
        final ChequeBookUtilizationDto chequeBookUtilizationDto = new ChequeBookUtilizationDto();
        populateModel(model, chequeBookUtilizationDto);
        masterDtoList.clear();
        return JSP_LIST;
    }

    public void populateModel(final Model model, final ChequeBookUtilizationDto chequeBookUtilizationDto) {
        model.addAttribute(CHEQUEBOOK_UTILIZATION_DTO, chequeBookUtilizationDto);
        populateListOfBanks(model);
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody JQGridResponse<ChequeBookUtilizationDto> gridData(final HttpServletRequest request, final Model model) {

        final int page = Integer.parseInt(request.getParameter(AccountConstants.PAGE.getValue()));
        final JQGridResponse<ChequeBookUtilizationDto> response = new JQGridResponse<>();
        response.setRows(masterDtoList);
        response.setTotal(masterDtoList.size());
        response.setRecords(masterDtoList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, masterDtoList);
        return response;
    }

    @RequestMapping(params = "searchChequeUtilization")
    public @ResponseBody List<ChequeBookUtilizationDto> searchChequeUtilization(
            @RequestParam("chequeBookId") final Long chequeBookId) {

        masterDtoList = new ArrayList<>();
        masterDtoList.clear();
        ChequeBookUtilizationDto masterDto = null;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        if (chequeBookId != null) {
            final List<Object[]> chequeDetailList = chequeBookLeafService.getChequeUtilizationDetails(chequeBookId, orgId);
            if ((chequeDetailList != null) && !chequeDetailList.isEmpty()) {
                for (final Object[] cheque : chequeDetailList) {
                    masterDto = new ChequeBookUtilizationDto();
                    masterDto.setChequeNo((String) cheque[0]);
                    masterDto.setChequeStatus((Long) cheque[1]);
                    masterDto.setPaymentId((Long) cheque[2]);
                    masterDto.setPaymentType((String) cheque[3]);
                    final String chequeStatusDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.CLR.toString(), orgId,
                            (Long) cheque[1]);
                    masterDto.setChequeStatusDesc(chequeStatusDesc);
                    
                    LookUp chequeStatusLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject((Long) cheque[1], UserSession.getCurrent().getOrganisation());
                    
                    if(!StringUtils.equals(chequeStatusLookUp.getLookUpCode(), "NSD")) {
                    if (cheque[3] != null) {
                        if (cheque[3].toString().equals(MainetConstants.AccountContraVoucherEntry.PE)
                                || cheque[3].toString().equals(MainetConstants.AccountContraVoucherEntry.DE)
                                || cheque[3].toString().equals(MainetConstants.AccountContraVoucherEntry.TF)
                                || cheque[3].toString().equals(MainetConstants.AccountContraVoucherEntry.WD)) {
                            final List<Object[]> paymentDetails = paymentEntrySerivce.getDetailsForChequeUtilization(
                                    (Long) cheque[2],
                                    orgId);
                            for (final Object[] paymentObj : paymentDetails) {
                                masterDto.setTransactionNo(paymentObj[0].toString());
                                masterDto.setTransactionDate(UtilityService.convertDateToDDMMYYYY((Date) paymentObj[1]));
                                final String payAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency((BigDecimal) paymentObj[2]);
                                masterDto.setAmountDesc(payAmount);
                                if (cheque[4] != null) {
                                    Date clearanceDate = paymentEntrySerivce.checkClearanceDateExists(
                                            Long.valueOf(cheque[4].toString()),
                                            orgId);
                                    if (clearanceDate != null) {
                                        masterDto.setClearanceDate(UtilityService.convertDateToDDMMYYYY(clearanceDate));
                                    }
                                }

                            }
                        }

                    }
                    }
                    masterDtoList.add(masterDto);
                }
            } else {
                log(NO_CHEQUE_DETAILS_FOUND + chequeBookId);
            }
        }
        return masterDtoList;
    }

    // Returns the cheque status of not issued cheques
    @RequestMapping(params = "getChequeStatus", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getChequeStatus() {

        final Map<Long, String> statusMap = new HashMap<>();
        final LookUp lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.NOT_ISSUED.getValue(),
                AccountPrefix.CLR.toString(),
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        statusMap.put(lkpStatus.getLookUpId(), lkpStatus.getLookUpDesc());
        return statusMap;
    }

    // Populates the list of banks
    public void populateListOfBanks(final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> bankAccountList = new ArrayList<>();
        final Map<Long, String> bankAccountMap = new HashMap<>();
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.U.toString(),
                PrefixConstants.BAS,
                orgId);
        bankAccountList = banksMasterService.getActiveBankAccountList(orgId, statusId);
        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object[] bankAc : bankAccountList) {
                bankAccountMap.put((Long) bankAc[0],
                        bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
            }
        }
        model.addAttribute(BANK_AC_MAP, bankAccountMap);
    }

    // Returns the cheque numbers range for a particular bank account
    @RequestMapping(params = "getChequeNumberRange", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getChequeNumberRange(@RequestParam("bankAccountId") final Long bankAccountId,
            final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> chequeRangeMap = new HashMap<>();
        chequeRangeMap = chequeBookLeafService.getChequeRangeByBankAccountId(bankAccountId, orgId);
        return chequeRangeMap;
    }

}
