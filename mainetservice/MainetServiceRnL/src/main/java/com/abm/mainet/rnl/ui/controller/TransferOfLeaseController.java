/**
 * 
 */
package com.abm.mainet.rnl.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.service.ITransferOfLeaseService;
import com.abm.mainet.rnl.ui.model.TransferOfLeaseModel;

/**
 * @author divya.marshettiwar
 *
 */
@Controller
@RequestMapping("/TransferOfLease.html")
public class TransferOfLeaseController extends AbstractFormController<TransferOfLeaseModel>{
	
	@Autowired
	private ITransferOfLeaseService transferOfLeaseService;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index(final Model uiModel, HttpServletRequest request) {
		sessionCleanup(request);
		
		// fetch vendor list from vendor master
		final Long vendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        List<TbAcVendormaster> vendorList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbAcVendormasterService.class)
                .getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
        this.getModel().setVendorList(vendorList);
		return index();
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "getContractDetail")
	public ModelAndView searchContractRecords(@RequestParam("contNo") final String contNo,
			final HttpServletRequest request) {
		sessionCleanup(request);
		getModel().bind(request);
		ModelAndView mv = null;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		TransferOfLeaseModel model = this.getModel();
		
		// fetch vendor list from vendor master
		final Long vendorStatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
						UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
				.getLookUpId();
		List<TbAcVendormaster> vendorList = ApplicationContextProvider.getApplicationContext()
				.getBean(TbAcVendormasterService.class)
				.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
		this.getModel().setVendorList(vendorList);
		
		ContractMastDTO seacrhData = transferOfLeaseService.searchContractDetails(contNo, orgid);
		
		if(seacrhData.getContNo() != null) {
			model.setContractMasterDto(seacrhData);
			
			mv = new ModelAndView("TransferOfLeaseValidn", MainetConstants.FORM_NAME, model);
		}else {
			mv = new ModelAndView("TransferOfLeaseValidn", MainetConstants.FORM_NAME, this.getModel());
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("rnl.seacrch.valid.nofound"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		}	
		
		return mv;
	}
	
	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_CURRENT_FORM, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView showCurrentForm(HttpServletRequest httpServletRequest, final Model models) {
		getModel().bind(httpServletRequest);
		TransferOfLeaseModel model = this.getModel();
		final Long vendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        List<TbAcVendormaster> vendorList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbAcVendormasterService.class)
                .getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
        this.getModel().setVendorList(vendorList);
		
		return new ModelAndView("TransferOfLeaseValidn", MainetConstants.FORM_NAME, this.getModel());
	}

}
