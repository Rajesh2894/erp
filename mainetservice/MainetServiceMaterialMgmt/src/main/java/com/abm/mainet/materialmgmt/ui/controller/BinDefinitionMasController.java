package com.abm.mainet.materialmgmt.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dto.BinDefMasDto;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.ui.model.BinMasterModel;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Controller
@RequestMapping("/BinDefMaster.html")
public class BinDefinitionMasController extends AbstractFormController<BinMasterModel> {

	private static final Logger log = Logger.getLogger(BinDefinitionMasController.class);

	@Autowired
	private IBinMasService iBinMasService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		log.info("Bin Definition master started to execute");
		this.sessionCleanup(request);
		BinMasterModel model = this.getModel();
		model.setFormType(MainetMMConstants.BIN_MASTER.BD);
		List<BinDefMasDto> defList = iBinMasService.findAllBinDefintion(UserSession.getCurrent().getOrganisation().getOrgid());
		model.setDefList(defList);
		model.setCommonHelpDocs("BinDefMaster.html");
		return defaultResult();
	}

	/*
	 * this handler used for opening form as add/view/draft Mode
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
	public ModelAndView marriageRegForm(@RequestParam(value = "binDefId", required = false) Long binDefId,
			@RequestParam(value = MainetConstants.Common_Constant.TYPE) String type, HttpServletRequest request) {
		BinMasterModel model = this.getModel();
		model.setFormType(MainetMMConstants.BIN_MASTER.BD);

		if (null == binDefId) {
			model.setModeType(MainetConstants.MODE_CREATE);
        } else {

        	if (MainetMMConstants.BIN_MASTER.V.equals(type)) {
				model.setModeType(MainetConstants.MODE_VIEW);
			} else {
				model.setModeType(MainetConstants.MODE_EDIT);
			}
			model.setBinDefMasDto(iBinMasService.findByBinDefID(binDefId));

		}
		return new ModelAndView("BinDefinitionMasForm", MainetConstants.FORM_NAME, model);
	}

}
