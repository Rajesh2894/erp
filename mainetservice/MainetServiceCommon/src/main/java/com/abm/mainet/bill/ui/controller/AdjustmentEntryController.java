package com.abm.mainet.bill.ui.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.AdjustmentEntryService;
import com.abm.mainet.bill.ui.model.AdjustmentEntryModel;
import com.abm.mainet.cfc.challan.dto.AdjustmentMasterDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.CustomerInfoDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/AdjustmentEntry.html")
public class AdjustmentEntryController extends AbstractFormController<AdjustmentEntryModel> {

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Resource
    private AdjustmentEntryService adjustmentEntryService;

    @Resource
    private TbTaxMasService tbTaxMasService;
    
    @Resource
    IFileUploadService fileUpload;
    
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("AdjustmentEntry.html");
        getModel().setDepartment(tbDepartmentService.findAllActive(UserSession.getCurrent().getOrganisation().getOrgid()));
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "searchAdjustmentData")
    public ModelAndView serachAdjustmentData(final HttpServletRequest request, @RequestParam("deptId") final Long deptId,
            @RequestParam("ccnOrPropNo")  String ccnNoOrPropNo, @RequestParam("oldNo")  String oldNo) {
        bindModel(request);
        final AdjustmentEntryModel model = getModel();
        model.setBill(null);
        model.setBillList(null);
        model.getAdjustmentDto().setDpDeptId(deptId);
        final Organisation orgId = UserSession.getCurrent().getOrganisation();
        String deptCode = tbDepartmentService.findDepartmentShortCodeByDeptId(deptId, orgId.getOrgid());
        model.setDepartmentCode(deptCode);
        if(StringUtils.equals(deptCode, "AS") && StringUtils.isBlank(ccnNoOrPropNo) && StringUtils.isNotBlank(oldNo)) {
        	String propNo = adjustmentEntryService.getPropNoByOldPropNo(oldNo, orgId.getOrgid(), deptId);
        	ccnNoOrPropNo = propNo;
        }
        model.getAdjustmentDto().setAdjRefNo(ccnNoOrPropNo);
        if (model.validateData()) {
            model.setBillList(adjustmentEntryService.fetchModuleWiseBills(deptId, ccnNoOrPropNo,
                    orgId.getOrgid()));
			if (CollectionUtils.isEmpty(model.getBillList())) {
                model.addValidationError(ApplicationSession.getInstance().getMessage("adjustment.entry.invalid"));
            }else {
            	if(deptCode.equals(MainetConstants.WATER_DEPT)) {
            		CustomerInfoDTO csmrInfoDetails = adjustmentEntryService.getCsmrInfoDetails(ccnNoOrPropNo, orgId.getOrgid());
            		if(csmrInfoDetails!=null) {
                		model.setBmCcnOwner(csmrInfoDetails.getName());
                		model.setAddress(csmrInfoDetails.getAddress());
            		}
            	}
            	
            	if(deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) {
            		PropertyDetailDto propertyDetails = adjustmentEntryService.getPropertyDetails(ccnNoOrPropNo, orgId.getOrgid());
            		if(propertyDetails!=null) {
                		model.setBmCcnOwner(propertyDetails.getFullOwnerName());
                		model.setAddress(propertyDetails.getAddress());
            		}
            	}
            	
            	if ((model.getBillList().get(0).getBmIdno() <= 0) && (model.getBillList().size() == 1)) {
                    final List<TbTaxMas> taxes = adjustmentEntryService.fetchAllModulewiseTaxes(deptId, orgId);
                    List<TbBillDet> billdet = null;
                    TbBillDet det = null;
                    if ((taxes != null) && !taxes.isEmpty()) {
                        billdet = new ArrayList<>();
                        for (final TbTaxMas tax : taxes) {
                            det = new TbBillDet();
                            det.setTaxDesc(tax.getTaxDesc());
                            det.setTaxId(tax.getTaxId());
                            billdet.add(det);
                        }
                        model.setBill(new TbBillMas());
                        model.getBill().setTbWtBillDet(billdet);
                    }
                } else {
                	model.setBill(model.getBillList().get(model.getBillList().size() - 1));
                    if ((model.getBill().getTbWtBillDet() != null) && !model.getBill().getTbWtBillDet().isEmpty()) {
                        for (final TbBillDet det : model.getBill().getTbWtBillDet()) {
                            det.setTaxDesc(tbTaxMasService.findTaxDescByTaxIdAndOrgId(det.getTaxId(), orgId.getOrgid()));
                            det.setDisplaySeq(tbTaxMasService.getDisplayIdByTaxId(det.getTaxId()));
                        }
                    }
                    model.getBillList().get(model.getBillList().size() - 1).getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getDisplaySeq));
                }
			}
        }
        return defaultMyResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "viewHistoryDetails")
    public ModelAndView serachAdjustmentData(final HttpServletRequest request) {
        bindModel(request);
        final AdjustmentEntryModel model = getModel();
        model.setHistoryMsg(null);
        final List<AdjustmentMasterDTO> adjustmentHistory;
        if(StringUtils.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY, model.getDepartmentCode())) {
        	adjustmentHistory = adjustmentEntryService.fetchHistory(
        			model.getAdjustmentDto().getAdjRefNo(),
                    model.getAdjustmentDto().getDpDeptId());
        }else {
        	adjustmentHistory = adjustmentEntryService.fetchHistory(
                    model.getBill().getCsIdn().toString(),
                    model.getAdjustmentDto().getDpDeptId());
        }
        model.setAdjustmentHistoryDto(adjustmentHistory);
        if ((adjustmentHistory == null) || adjustmentHistory.isEmpty()) {
            model.setHistoryMsg(ApplicationSession.getInstance().getMessage("adjustment.history.invalid"));
        }
        return new ModelAndView("AdjustmentHistoryView", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "getDeptCode", method = {RequestMethod.POST})
    public @ResponseBody String getDeptCode(@RequestParam("deptId") final Long deptId, HttpServletRequest request) {
    	String deptCode = "";
    	try {
    		final Organisation orgId = UserSession.getCurrent().getOrganisation();
            deptCode = tbDepartmentService.findDepartmentShortCodeByDeptId(deptId, orgId.getOrgid());
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return deptCode;
    }
    
    @ResponseBody
    @RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
    public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, @RequestParam("deptId") final Long deptId,
    		@RequestParam("oldNo")  String oldNo, HttpServletRequest request) {
    	this.getModel().bind(request);
    	AdjustmentEntryModel model = this.getModel();
    	List<String> flatNoList = null;
    	String billingMethod = null;
    	final Organisation orgId = UserSession.getCurrent().getOrganisation();
        String deptCode = tbDepartmentService.findDepartmentShortCodeByDeptId(deptId, orgId.getOrgid());
        model.setDepartmentCode(deptCode);
        if(StringUtils.equals(deptCode, "AS") && StringUtils.isBlank(propNo) && StringUtils.isNotBlank(oldNo)) {
        	propNo = adjustmentEntryService.getPropNoByOldPropNo(oldNo, orgId.getOrgid(), deptId);
        }
    	Long billingMethodId = adjustmentEntryService.getBillMethodIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid(), deptCode);
    	LookUp billingMethodLookUp  = null;
    	try {
    		 billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, UserSession.getCurrent().getOrganisation());
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	if(billingMethodLookUp != null) {
    		billingMethod = billingMethodLookUp.getLookUpCode();
    	}
    	this.getModel().setBillingMethod(billingMethod);
    	if(StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
    		flatNoList = new ArrayList<String>();
    		flatNoList = adjustmentEntryService.getFlatNoIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid(), deptCode);
    	}
    	model.setFlatNoList(flatNoList);
    	return flatNoList;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "serachAdjustmentDataIndividualBillMethod")
    public ModelAndView serachAdjustmentDataIndividualBillMethod(final HttpServletRequest request, @RequestParam("deptId") final Long deptId,
            @RequestParam("ccnOrPropNo")  String ccnNoOrPropNo, @RequestParam("oldNo")  String oldNo
            , @RequestParam("billingMeth")  String billingMeth , @RequestParam("flatNo")  String flatNo) {
        bindModel(request);
        final AdjustmentEntryModel model = getModel();
        model.setBill(null);
        model.setBillList(null);
        model.getAdjustmentDto().setDpDeptId(deptId);
        final Organisation orgId = UserSession.getCurrent().getOrganisation();
        String deptCode = tbDepartmentService.findDepartmentShortCodeByDeptId(deptId, orgId.getOrgid());
        model.setDepartmentCode(deptCode);
        if(StringUtils.equals(deptCode, "AS") && StringUtils.isBlank(ccnNoOrPropNo) && StringUtils.isNotBlank(oldNo)) {
        	String propNo = adjustmentEntryService.getPropNoByOldPropNo(oldNo, orgId.getOrgid(), deptId);
        	ccnNoOrPropNo = propNo;
        }
        model.getAdjustmentDto().setAdjRefNo(ccnNoOrPropNo);
        if (model.validateData()) {
        	if(StringUtils.isNotBlank(billingMeth) && StringUtils.equals(billingMeth, MainetConstants.FlagI)) {
        		model.getAdjustmentDto().setFlatNo(flatNo);
        		model.setBillList(adjustmentEntryService.fetchAllBillByPropNoAndFlatNo(deptId, ccnNoOrPropNo, flatNo, orgId.getOrgid()));
        	} else {
        		model.setBillList(adjustmentEntryService.fetchModuleWiseBills(deptId, ccnNoOrPropNo,
                        orgId.getOrgid()));
        	}
            
			if (CollectionUtils.isEmpty(model.getBillList())) {
                model.addValidationError(ApplicationSession.getInstance().getMessage("adjustment.entry.invalid"));
            } else if ((model.getBillList().get(0).getBmIdno() <= 0) && (model.getBillList().size() == 1)) {
                final List<TbTaxMas> taxes = adjustmentEntryService.fetchAllModulewiseTaxes(deptId, orgId);
                List<TbBillDet> billdet = null;
                TbBillDet det = null;
                if ((taxes != null) && !taxes.isEmpty()) {
                    billdet = new ArrayList<>();
                    for (final TbTaxMas tax : taxes) {
                        det = new TbBillDet();
                        det.setTaxDesc(tax.getTaxDesc());
                        det.setTaxId(tax.getTaxId());
                        billdet.add(det);
                    }
                    model.setBill(new TbBillMas());
                    model.getBill().setTbWtBillDet(billdet);
                }
            } else {
            	model.setBill(model.getBillList().get(model.getBillList().size() - 1));
                if ((model.getBill().getTbWtBillDet() != null) && !model.getBill().getTbWtBillDet().isEmpty()) {
                    for (final TbBillDet det : model.getBill().getTbWtBillDet()) {
                        det.setTaxDesc(tbTaxMasService.findTaxDescByTaxIdAndOrgId(det.getTaxId(), orgId.getOrgid()));
                    }
                }
            }
        }
        return defaultMyResult();
    }
}
