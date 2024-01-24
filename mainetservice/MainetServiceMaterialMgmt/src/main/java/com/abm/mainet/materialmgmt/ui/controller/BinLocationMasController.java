package com.abm.mainet.materialmgmt.ui.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.ui.model.BinMasterModel;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Controller
@RequestMapping("/BinLocationMas.html")
public class BinLocationMasController  extends AbstractFormController<BinMasterModel>{
	
	private static final Logger log = Logger.getLogger(BinDefinitionMasController.class);

	@Autowired
	private IBinMasService binMasService;
	
	@Resource
	private IStoreMasterService storeMasterService;
	
	
	@RequestMapping(method = { RequestMethod.POST})
    public ModelAndView index(HttpServletRequest request) throws Exception {
		log.info("Bin Location master started to execute");
        this.sessionCleanup(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        BinMasterModel model = this.getModel();
        model.setFormType(MainetMMConstants.BIN_MASTER.BL);
        model.setLocList(binMasService.findAllBinLocation(orgId));
        model.setCommonHelpDocs("BinLocationMas.html");
       
        model.setStoreObjectList(storeMasterService.getStoreIdAndNameList(orgId));
		Map<Long, String> storeMap = new HashMap<>();
		model.getStoreObjectList().forEach(storeIdNameObject -> {
			storeMap.put(Long.valueOf(storeIdNameObject[0].toString()), storeIdNameObject[1].toString());
		});
		model.getLocList().forEach(binLocation -> {
			binLocation.setStoreName(storeMap.get(binLocation.getStoreId()));
		});
		
        return defaultResult();
    }
	
	
	/*
	 * this handler used for opening form as add/view/draft Mode
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
	public ModelAndView marriageRegForm(@RequestParam(value = "binLocId", required = false) Long binLocId,
			@RequestParam(value = MainetConstants.Common_Constant.TYPE) String type, HttpServletRequest request) {
		BinMasterModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setFormType(MainetMMConstants.BIN_MASTER.BL);

		if (null == binLocId) {
			model.setModeType(MainetConstants.MODE_CREATE);
			model.setStoreObjectList(storeMasterService.getActiveStoreObjectListForAdd(orgId));
        } else {
			model.setBinLocMasDto(binMasService.findByBinLocID(binLocId));
			model.setStoreObjectList(storeMasterService.getStoreIdAndNameList(orgId));
			if (MainetMMConstants.BIN_MASTER.V.equals(type))
				model.setModeType(MainetConstants.MODE_VIEW);
			else
				model.setModeType(MainetConstants.MODE_EDIT);
		}
		model.setDefList(binMasService.findAllBinDefintion(orgId));
		return new ModelAndView("BinLocationMasForm", MainetConstants.FORM_NAME, model);
	}
	
	
	@RequestMapping(params = "getStoreDetails", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getStoreDetails(@RequestParam("storeId") Long storeId, final HttpServletRequest request) {
		Map<String, String> storeDetailMap = new HashMap<>();
		Object[] storeOnject=  (Object[]) storeMasterService.getStoreLocationAddress(storeId, UserSession.getCurrent().getOrganisation().getOrgid())[0];
		storeDetailMap.put("location", storeOnject[0].toString());
		storeDetailMap.put("address", storeOnject[1].toString());
		return storeDetailMap;
	}
	
}
