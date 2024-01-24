package com.abm.mainet.cfc.loi.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.ui.model.LoiManualEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/LoiManualEntry.html")
public class LoiManualEntryController extends AbstractFormController<LoiManualEntryModel>{
	
	@Resource
    private TbServicesMstService tbServicesMstService;
	
	@Resource
    private TbDepartmentService tbDepartmentService;
	
	@Resource
    private TbTaxMasService tbTaxMasService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
		sessionCleanup(request);
        bindModel(request);
		Organisation org = new Organisation();
        org = UserSession.getCurrent().getOrganisation();
        
		final List<TbDepartment> deptList = tbDepartmentService.findMappedDepartments(org.getOrgid());
		request.setAttribute(MainetConstants.CommonMasterUi.DEPT_LIST, deptList);
		

        final List<LookUp> txnPrefixData = CommonMasterUtility.getListLookup(MainetConstants.PG_REQUEST_PROPERTY.TXN, org);
        request.setAttribute(MainetConstants.CommonMasterUi.TXN_PREFIX_DATA, txnPrefixData);
     
		request.setAttribute("serviceList", tbServicesMstService.findAllServiceListByOrgId(Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid())));
        return defaultResult();
    }
	
	@RequestMapping(params = "taxList", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getTaxList(@RequestParam("deptId") final Long dpDeptId, final Model model) {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final List<Long> taxDescIdList = tbTaxMasService.findTaxByDeptIdAndOrgId(org.getOrgid(), dpDeptId);
        final List<LookUp> lookUpList = new ArrayList<>();
        if (taxDescIdList.size() != 0) {
            for (final Long descId : taxDescIdList) {
                if (descId != null) {
                    final LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(descId, org);
                    lookUpList.add(lookup);
                }
            }
        }
        return lookUpList;
    }
	
	@RequestMapping(params = MainetConstants.Common_Constant.SERVICES, method = RequestMethod.POST)
	public @ResponseBody Object[] populateServices(@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId) {
		Object[] obj = new Object[] {null};
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long activeStatusId = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.Common_Constant.ACTIVE_FLAG, MainetConstants.Common_Constant.ACN,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation()).getLookUpId();
		obj[0] = serviceMasterService.findAllActiveServicesByDepartment(orgId, deptId, activeStatusId);
		return obj;
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "generateLOI")
    public ModelAndView generateLOI(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final LoiManualEntryModel model = this.getModel();
        if (model.saveLoiData()) {
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
        } else {
            return defaultMyResult();
        }
    }

}
