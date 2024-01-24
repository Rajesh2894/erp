package com.abm.mainet.water.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.WaterConsumerDetailUpdateModel;

@Controller
@RequestMapping("/WaterConsumerDetailUpdate.html")
public class WaterConsumerDetailUpdateController extends AbstractFormController<WaterConsumerDetailUpdateModel> {

	
	
	@Autowired
	private WaterCommonService waterCommonService;
	
	@Resource
	IFileUploadService fileUpload;
	
	@Autowired
	private NewWaterConnectionService newWaterConnectionService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setDetShowFlag(MainetConstants.FlagN);
		return index();
	}
	
    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public ModelAndView search(@RequestParam(value = "csCcn") String csCcn,@RequestParam(value = "csOldccn") String csOldccn,HttpServletRequest request) {
    	WaterConsumerDetailUpdateModel model = this.getModel();
        model.bind(request);
        ModelAndView mv = null;
       Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
        if ((csCcn == null || csCcn.isEmpty()) || (StringUtils.isBlank(csOldccn))) {
	       TbCsmrInfoDTO entity = waterCommonService.fetchConnectionDetailsByConnNoOrOldConnNo(csCcn,csOldccn, orgId, "A");
			if (entity == null || StringUtils.isEmpty(entity.getCsCcn())) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("water.nodata.search.criteria"));
				mv = new ModelAndView("WaterConsumerDetailUpdateValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			} else {
				entity = newWaterConnectionService.getConnectionDetailsById(entity.getCsIdn());				
			}
			this.getModel().setDetShowFlag(MainetConstants.FlagY);
			this.getModel().getSearchDTO().setCsCcn(csCcn);
			this.getModel().getSearchDTO().setCsOldccn(csOldccn);
			model.setCsmrInfo(entity);
			model.getNewConnectionDto().setCsmrInfo(entity);
			
        }
        return new ModelAndView("WaterConsumerDetailUpdateValidn", MainetConstants.FORM_NAME, getModel());
    }


}
