/**
 *
 */
package com.abm.mainet.cfc.loi.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.cfc.loi.dto.LoiPrintDTO;
import com.abm.mainet.cfc.loi.dto.LoiPrintingDTO;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.loi.ui.model.LoiPaymentModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author umashanker.kanaujiya
 *
 */
@Controller
@RequestMapping(value = "/LoiPrintingController.html")
public class LoiPrintingController extends AbstractFormController<LoiPaymentModel> {

    private final static String LIST_NAME = "loiPrintingData";
    private final static String LOI_PRINTING_DETAIL = "LoiPrintingDetail";
    
    
	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbLoiMasService itbLoiMasService;
	
	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasService;
	
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        LoiPaymentModel model = this.getModel();
        model.setOrgId(orgId);
        model.setDeptList(tbDepartmentService.findAllDepartmentByOrganization(orgId, MainetConstants.MENU.A));
        model.setLoiDetailList(itbLoiMasService.getLoiDataByOrgId(orgId));
        model.setApplNameList(cfcApplicationMasService.getCFCApplData(orgId));
        return new ModelAndView("LoiPrintData", MainetConstants.CommonConstants.COMMAND, getModel());
    }

       //code updated for D#121724

    @RequestMapping(params = "getLoiPrintData", method = RequestMethod.POST)
    public @ResponseBody LoiPrintingDTO gridData(HttpServletRequest request, final Model model) {
    	this.sessionCleanup(request);
        final LoiPrintingDTO response = new LoiPrintingDTO();
        final UserSession userSession = UserSession.getCurrent();
        final LoiPaymentModel mode = getModel();
        Long deptId = Long.valueOf(request.getParameter("deptIdHidden"));
		Long serviceId = Long.valueOf(request.getParameter("serviceIdHidden"));
		Long appId = Long.valueOf(request.getParameter("appIdHidden"));
		String loiNo = String.valueOf(request.getParameter("loiNoHidden"));
		String applicantName = String.valueOf(request.getParameter("applicantNameHidden"));
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        if (userSession != null) {
            final List<LoiPrintDTO> list = itbLoiMasService.getLoiPrintIngData(userSession.getOrganisation().getOrgid(),deptId,serviceId,appId,loiNo,applicantName);
            if (CollectionUtils.isNotEmpty(list))

            if (list != null) {
                response.setRows(list);
                response.setTotal(list.size());
                response.setRecords(list.size());
                response.setPage(page);
            }
            model.addAttribute(LIST_NAME, list);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showDetails")
    public ModelAndView searchLOIRecords(final HttpServletRequest httpServletRequest,
            @RequestParam(value = "appId") final Long appId,
            @RequestParam(value = "serviceId") final Long serviceId) throws ClassNotFoundException, LinkageError {
        bindModel(httpServletRequest);
        final LoiPaymentModel model = getModel();
        final LoiPaymentSearchDTO searchdata = model.getSearchDto();
        searchdata.setApplicationId(appId);
        searchdata.setServiceId(serviceId);

        final boolean result = model.getLoiData(MainetConstants.PAY_STATUS.NOT_PAID);
        if (!result) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("no.record.found"));
        }
        return new ModelAndView(LOI_PRINTING_DETAIL, MainetConstants.CommonConstants.COMMAND, getModel());
    }

    
	@RequestMapping(params = MainetConstants.Common_Constant.SERVICES, method = RequestMethod.POST)
	public @ResponseBody Object[] populateServicesAndComplaint(
			@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId) {
		Object[] obj = new Object[] { null, null };
		final Long activeStatusId = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.Common_Constant.ACTIVE_FLAG, MainetConstants.Common_Constant.ACN,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation()).getLookUpId();
		obj[0] = serviceMasterService.findAllActiveServicesByDepartment(orgId, deptId, activeStatusId);		
		return obj;
	}
	
}
