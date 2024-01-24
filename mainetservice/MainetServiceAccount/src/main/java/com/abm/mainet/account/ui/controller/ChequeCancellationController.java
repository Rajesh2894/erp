package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.ChequeCancellationDto;
import com.abm.mainet.account.service.ChequeCancellationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author tejas.kotekar
 *
 */
@Controller
@RequestMapping("/ChequeCancellationAndReIssue.html")
public class ChequeCancellationController extends AbstractController {

    private static final String JSP_FORM = "ChequeCancellation/form";
    private static final String JSP_FORM_SAVED = "ChequeCancellation/formSaved";
    private final static String CHEQUE_CANCELLATION_DTO = "chequeCancellationDto";
    private static final String BANK_AC_MAP = "bankAccountMap";

    @Resource
    private TbBankmasterService banksMasterService;
    @Resource
    private ChequeCancellationService chequeCancellationService;

    public ChequeCancellationController() {
        super(ChequeCancellationController.class, CHEQUE_CANCELLATION_DTO);
    }

    public void populateModel(final Model model, final ChequeCancellationDto chequeCancellationDto) {
        model.addAttribute(CHEQUE_CANCELLATION_DTO, chequeCancellationDto);
        populateListOfBanks(model);
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

    @RequestMapping()
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {

        final ChequeCancellationDto chequeCancellationDto = new ChequeCancellationDto();
        populateModel(model, chequeCancellationDto);
        return JSP_FORM;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String createChequeCancellation(final ChequeCancellationDto chequeCancellationDto, final Model model) {
        chequeCancellationDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        chequeCancellationDto.setLanguageId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        chequeCancellationService.chequeCancellation(chequeCancellationDto);
        chequeCancellationDto.setSuccessfulFlag(MainetConstants.MASTER.Y);
        populateModel(model, chequeCancellationDto);
        return JSP_FORM_SAVED;
    }

    @RequestMapping(params = "getDateByInstrumentNo")
    public @ResponseBody String getDateByInstrumentNo(@RequestParam("issuedChequeNo") final Long issuedChequeNo,
            final Model model) {
        String date = "";
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        date = chequeCancellationService.getDateByInstrumentNo(issuedChequeNo, orgId);
        return date;
    }

}
