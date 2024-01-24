package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.account.dto.ReceiptRegisterdto;
import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.account.service.BankAccountService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.RecieptRegisterService;
import com.abm.mainet.account.ui.model.ReceiptRegisterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/RecieptRegisterController.html")

public class RecieptRegisterController extends AbstractFormController<ReceiptRegisterModel> {

    @Autowired
    private RecieptRegisterService oRecieptRegisterService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;

    @Resource
    private AccountReceiptEntryService accountReceiptEntryService;

    @Resource
    private BankAccountService bankAccountService;

    @Resource
    private BankMasterService bankMasterService;

    @Resource
    private TbComparamDetService tbComparamDetService;

    @Autowired
    private IEmployeeService employeeService;
    
    @Resource
	private DepartmentService deptService;

    private static final String MAIN_ENTITY_NAME = "acRecieptRegister";
    private static final String JSP_LIST = "acRecieptRegister/list";
    private static final String Reciept_List = "challanRecieptReoprt/list";
    protected static final String MODE_VIEW = "view";
    protected static final String MODE = "mode";

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final Map<Long, String> employeeList = oRecieptRegisterService
                .getRecieptRegisterEmployeeDetails(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MainetConstants.RecieptRegister.EMP_MAP, employeeList);
        final ReceiptRegisterdto dto = new ReceiptRegisterdto();
        populateModel(model, dto, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        model.addAttribute(MainetConstants.RecieptRegister.EMP_LIST, employeeList);
        result = JSP_LIST;
        return result;
    }

    private void populateModel(final Model model, final ReceiptRegisterdto bean, final FormMode formMode) {
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.UPDATE);
        }
    }

    @RequestMapping(params = "PrintChallanReceiptReport", method = RequestMethod.POST)
    public String getRecieptForm(final ReceiptRegisterdto receiptRegisterdto, final HttpServletRequest request,
            final Model model, @RequestParam("fromDate") final String fromDate, @RequestParam("toDate") String toDate,
            @RequestParam("EmployeeListid") final Long EmployeeListid) {

        Map<String, List<ReceiptRegisterdto>> listOfTbReceiptRegister = new HashMap<>();
        final List<ReceiptRegisterdto> list1 = new ArrayList<>();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Department> deptList = deptService.getDepartments(MainetConstants.STATUS.ACTIVE);
        List<Object[]> RecieptList = null;
        List<Object[]> challanSummary = null;
        if (EmployeeListid != 0L) {
            BigDecimal finalAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
            final List<ReceiptRegisterdto> list = new ArrayList<>();
            String empName = "";
            final EmployeeBean bean = employeeService.findById(EmployeeListid);
            if ((bean.getEmpmname() != null) && !bean.getEmpmname().isEmpty()) {
                empName = bean.getEmpname() + " " + bean.getEmpmname() + " " + bean.getEmplname();
            } else {
                if (bean.getEmplname() != null && !bean.getEmplname().isEmpty()) {
                    empName = bean.getEmpname() + " " + bean.getEmplname();
                } else {
                    empName = bean.getEmpname();
                }
            }
            RecieptList = oRecieptRegisterService.getRecieptChallanDeatiles(EmployeeListid, fromDate, toDate, orgid);
            challanSummary = oRecieptRegisterService.findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntity(fromDate,
                    toDate, orgid, EmployeeListid);
            
        	
            if (RecieptList != null && !RecieptList.isEmpty()) {

                BigDecimal sumRmAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                BigDecimal sumBankAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                BigDecimal sumChequeDDAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                BigDecimal sumCashAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                for (final Object obj[] : RecieptList) {

                    Long cpdIdMode = oRecieptRegisterService
                            .findByRmRcptidTbSrcptModesDetEntity(Long.valueOf(obj[0].toString()));
                    String collectionMode = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(), orgid,
                            cpdIdMode);

                    final ReceiptRegisterdto dto = new ReceiptRegisterdto();

                    if (StringUtils.isNotEmpty(collectionMode)) {
                        dto.setColletionMode(collectionMode);
                    }

                    if (obj[1] != null) {
                        BigDecimal bd = new BigDecimal(obj[1].toString());
                        dto.setRmRcptno(bd.longValue());
                        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
    						List<Department> deptFilterList = deptList.stream().filter(f->f.getDpDeptid()==Long.valueOf(obj[13].toString())).collect(Collectors.toList());
    						dto.setRmReceiptNo(deptFilterList.get(0).getDpDeptcode().concat(obj[1].toString()));
                        }
                    }
                    if (obj[2] != null) {
                        dto.setRmDate(Utility.dateToString((Date) obj[2]));
                    }
                    if (obj[3] != null) {
                        dto.setManualReceiptNo((String) obj[3]);
                    }
                    if (obj[4] != null) {
                        dto.setCashAmountIndianCurrency(
                                CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[4]));
                        dto.setRmAmountIndianCurrency(
                                CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[4]));
                        sumRmAmount = sumRmAmount.add((BigDecimal) obj[4]);
                        sumCashAmount = sumCashAmount.add((BigDecimal) obj[4]);

                    }
                    if (obj[5] != null) {
                        dto.setBankAmountIndianCurrency(
                                CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[5]));
                        dto.setRmAmountIndianCurrency(
                                CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[5]));
                        sumRmAmount = sumRmAmount.add((BigDecimal) obj[5]);
                        sumBankAmount = sumBankAmount.add((BigDecimal) obj[5]);
                    }
                    if (obj[6] != null) {
                        dto.setChequeAmountIndianCurrency(
                                CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[6]));
                        dto.setRmAmountIndianCurrency(
                                CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[6]));
                        sumRmAmount = sumRmAmount.add((BigDecimal) obj[6]);
                        sumChequeDDAmount = sumChequeDDAmount.add((BigDecimal) obj[6]);
                    }
                    if (obj[7] != null) {
                        BigDecimal bd = new BigDecimal(obj[7].toString());
                        dto.setCheqno(bd.longValue());
                    }
                    if (obj[8] != null) {
                        BigDecimal bd = new BigDecimal(obj[8].toString());
                        dto.setCbBankidDesc(bd.longValue());
                    }
                    if (obj[9] != null) {
                        dto.setBankName((String) obj[9]);
                    }
                    if (obj[10] != null) {
                        dto.setRmReceivedfrom((String) obj[10]);
                    }
                    if (obj[12] != null) {
                        dto.setReceiptHead((String) obj[12]);
                    }
                    finalAmount = finalAmount.add(new BigDecimal(dto.getRmAmountIndianCurrency().replace(",", "")));
                    list.add(dto);
                }

                for (final Object obj[] : challanSummary) {
                    final ReceiptRegisterdto dto1 = new ReceiptRegisterdto();

                    if (obj[0] != null) {
                        BigDecimal bd = new BigDecimal(obj[0].toString());
                        dto1.setCashAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(bd));
                        if (obj[1] != null && obj[2] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[1]).add((BigDecimal) obj[2]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));

                        } else if (obj[2] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[2]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else if (obj[1] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[1]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else {
                            dto1.setRmAmount(bd);
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        }

                    }
                    if (obj[1] != null) {
                        BigDecimal bd = new BigDecimal(obj[1].toString());
                        dto1.setBankAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(bd));
                        if (obj[2] != null && obj[0] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[0]).add((BigDecimal) obj[2]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else if (obj[2] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[2]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else if (obj[0] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[0]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else {
                            dto1.setRmAmount(bd);
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        }
                    }
                    if (obj[2] != null) {
                        BigDecimal bd = new BigDecimal(obj[2].toString());
                        dto1.setChequeAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(bd));
                        if (obj[1] != null && obj[0] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[0]).add((BigDecimal) obj[1]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else if (obj[1] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[1]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else if (obj[0] != null) {
                            dto1.setRmAmount(bd.add((BigDecimal) obj[0]));
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        } else {
                            dto1.setRmAmount(bd);
                            dto1.setRmAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                        }

                    }
                    if (obj[4] != null) {
                        dto1.setReceiptHead((String) obj[4]);
                    }
                    list1.add(dto1);
                }

                if (sumChequeDDAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))

                {
                    receiptRegisterdto.setSumChequeDDAmount(null);
                } else {
                    receiptRegisterdto.setSumOfChequeAmountIndianCurrency(
                            CommonMasterUtility.getAmountInIndianCurrency(sumChequeDDAmount));
                }

                if (sumCashAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                    receiptRegisterdto.setSumCashAmount(null);
                } else {
                    receiptRegisterdto.setSumOfCashAmountIndianCurrency(
                            CommonMasterUtility.getAmountInIndianCurrency(sumCashAmount));
                }

                if (sumBankAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                    receiptRegisterdto.setSumBankAmount(null);
                } else {
                    receiptRegisterdto.setSumOfBankAmountIndianCurrency(
                            CommonMasterUtility.getAmountInIndianCurrency(sumBankAmount));
                }
                receiptRegisterdto
                        .setSumOfRmAmountindianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumRmAmount));
                receiptRegisterdto.setFromDate(fromDate);
                receiptRegisterdto.setToDate(toDate);
                // receiptRegisterdto.setUsername(empName);
                receiptRegisterdto.setListofSumHead(list1);
            }
            receiptRegisterdto.getAmountMap().put(empName, CommonMasterUtility.getAmountInIndianCurrency(finalAmount));
            listOfTbReceiptRegister.put(empName, list);
        } else {
            final Map<Long, String> employeeList = oRecieptRegisterService
                    .getRecieptRegisterEmployeeAllDetails(UserSession.getCurrent().getOrganisation().getOrgid());
            int count = 0;
            if (employeeList != null) {
                BigDecimal sumRmAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                BigDecimal sumBankAmount = new BigDecimal(
                        MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                BigDecimal sumChequeDDAmount = new BigDecimal(
                        MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                BigDecimal sumCashAmount = new BigDecimal(
                        MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                for (Entry<Long, String> entry : employeeList.entrySet()) {

                    BigDecimal finalAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                    final List<ReceiptRegisterdto> list = new ArrayList<>();
                    String empName = "";
                    Long employeeId = entry.getKey();
                    final EmployeeBean bean = employeeService.findById(employeeId);
                    if ((bean.getEmpmname() != null) && !bean.getEmpmname().isEmpty()) {
                        empName = bean.getEmpname() + " " + bean.getEmpmname() + " " + bean.getEmplname();
                    } else {
                        if (bean.getEmplname() != null && !bean.getEmplname().isEmpty()) {
                            empName = bean.getEmpname() + " " + bean.getEmplname();
                        } else {
                            empName = bean.getEmpname();
                        }
                    }
                    RecieptList = oRecieptRegisterService.getRecieptChallanDeatiles(employeeId, fromDate, toDate,
                            orgid);
                    challanSummary = oRecieptRegisterService
                            .findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntityAll(fromDate, toDate, orgid);
                    if (RecieptList != null && !RecieptList.isEmpty()) {

                        for (final Object obj[] : RecieptList) {

                            Long cpdIdMode = oRecieptRegisterService
                                    .findByRmRcptidTbSrcptModesDetEntity(Long.valueOf(obj[0].toString()));
                            String collectionMode = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(), orgid,
                                    cpdIdMode);

                            final ReceiptRegisterdto dto = new ReceiptRegisterdto();

                            if (StringUtils.isNotEmpty(collectionMode)) {
                                dto.setColletionMode(collectionMode);
                            }

                            if (obj[1] != null) {
                                BigDecimal bd = new BigDecimal(obj[1].toString());
                                dto.setRmRcptno(bd.longValue());
                                if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
            						List<Department> deptFilterList = deptList.stream().filter(f->f.getDpDeptid()==Long.valueOf(obj[13].toString())).collect(Collectors.toList());
            						dto.setRmReceiptNo(deptFilterList.get(0).getDpDeptcode().concat(obj[1].toString()));
                                }
                            }
                            if (obj[2] != null) {
                                dto.setRmDate(Utility.dateToString((Date) obj[2]));
                            }
                            if (obj[3] != null) {
                                dto.setManualReceiptNo((String) obj[3]);
                            }
                            if (obj[4] != null) {
                                dto.setCashAmountIndianCurrency(
                                        CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[4]));
                                dto.setRmAmountIndianCurrency(
                                        CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[4]));
                                sumRmAmount = sumRmAmount.add((BigDecimal) obj[4]);
                                sumCashAmount = sumCashAmount.add((BigDecimal) obj[4]);

                            }
                            if (obj[5] != null) {
                                dto.setBankAmountIndianCurrency(
                                        CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[5]));
                                dto.setRmAmountIndianCurrency(
                                        CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[5]));
                                sumRmAmount = sumRmAmount.add((BigDecimal) obj[5]);
                                sumBankAmount = sumBankAmount.add((BigDecimal) obj[5]);
                            }
                            if (obj[6] != null) {
                                dto.setChequeAmountIndianCurrency(
                                        CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[6]));
                                dto.setRmAmountIndianCurrency(
                                        CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[6]));
                                sumRmAmount = sumRmAmount.add((BigDecimal) obj[6]);
                                sumChequeDDAmount = sumChequeDDAmount.add((BigDecimal) obj[6]);
                            }
                            if (obj[7] != null) {
                                BigDecimal bd = new BigDecimal(obj[7].toString());
                                dto.setCheqno(bd.longValue());
                            }
                            if (obj[8] != null) {
                                BigDecimal bd = new BigDecimal(obj[8].toString());
                                dto.setCbBankidDesc(bd.longValue());
                            }
                            if (obj[9] != null) {
                                dto.setBankName((String) obj[9]);
                            }
                            if (obj[10] != null) {
                                dto.setRmReceivedfrom((String) obj[10]);
                            }
                            if (obj[12] != null) {
                                dto.setReceiptHead((String) obj[12]);
                            }
                            finalAmount = finalAmount.add(new BigDecimal(dto.getRmAmountIndianCurrency().replace(",", "")));
                            list.add(dto);
                        }

                        if (count == 0) {
                            for (final Object obj[] : challanSummary) {
                                final ReceiptRegisterdto dto1 = new ReceiptRegisterdto();

                                if (obj[0] != null) {
                                    BigDecimal bd = new BigDecimal(obj[0].toString());
                                    dto1.setCashAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(bd));
                                    if (obj[1] != null && obj[2] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[1]).add((BigDecimal) obj[2]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));

                                    } else if (obj[2] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[2]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else if (obj[1] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[1]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else {
                                        dto1.setRmAmount(bd);
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    }

                                }
                                if (obj[1] != null) {
                                    BigDecimal bd = new BigDecimal(obj[1].toString());
                                    dto1.setBankAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(bd));
                                    if (obj[2] != null && obj[0] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[0]).add((BigDecimal) obj[2]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else if (obj[2] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[2]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else if (obj[0] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[0]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else {
                                        dto1.setRmAmount(bd);
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    }
                                }
                                if (obj[2] != null) {
                                    BigDecimal bd = new BigDecimal(obj[2].toString());
                                    dto1.setChequeAmountIndianCurrency(
                                            CommonMasterUtility.getAmountInIndianCurrency(bd));
                                    if (obj[1] != null && obj[0] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[0]).add((BigDecimal) obj[1]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else if (obj[1] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[1]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else if (obj[0] != null) {
                                        dto1.setRmAmount(bd.add((BigDecimal) obj[0]));
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    } else {
                                        dto1.setRmAmount(bd);
                                        dto1.setRmAmountIndianCurrency(
                                                CommonMasterUtility.getAmountInIndianCurrency(dto1.getRmAmount()));
                                    }

                                }
                                if (obj[4] != null) {
                                    dto1.setReceiptHead((String) obj[4]);
                                }
                                list1.add(dto1);
                                count++;
                            }
                        }

                        if (sumChequeDDAmount
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))

                        {
                            receiptRegisterdto.setSumChequeDDAmount(null);
                        } else {
                            receiptRegisterdto.setSumOfChequeAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(sumChequeDDAmount));
                        }

                        if (sumCashAmount
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                            receiptRegisterdto.setSumCashAmount(null);
                        } else {
                            receiptRegisterdto.setSumOfCashAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(sumCashAmount));
                        }

                        if (sumBankAmount
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                            receiptRegisterdto.setSumBankAmount(null);
                        } else {
                            receiptRegisterdto.setSumOfBankAmountIndianCurrency(
                                    CommonMasterUtility.getAmountInIndianCurrency(sumBankAmount));
                        }
                        receiptRegisterdto.setSumOfRmAmountindianCurrency(
                                CommonMasterUtility.getAmountInIndianCurrency(sumRmAmount));
                        receiptRegisterdto.setFromDate(fromDate);
                        receiptRegisterdto.setToDate(toDate);
                        // receiptRegisterdto.setUsername(empName);
                        receiptRegisterdto.setListofSumHead(list1);
                    }
                    receiptRegisterdto.getAmountMap().put(empName, CommonMasterUtility.getAmountInIndianCurrency(finalAmount));
                    listOfTbReceiptRegister.put(empName, list);
                }
            }
        }
        receiptRegisterdto.setListOfTbReceiptRegister(listOfTbReceiptRegister);
        populateModel(model, receiptRegisterdto, FormMode.CREATE);

        model.addAttribute(MAIN_ENTITY_NAME, receiptRegisterdto);

        return Reciept_List;
    }

}
