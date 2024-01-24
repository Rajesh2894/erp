package com.abm.mainet.common.integration.dms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.service.IViewMetadataService;
import com.abm.mainet.common.integration.dms.ui.model.ViewMetadataModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "KmsViewDocument.html")
public class KmsViewDocumentController extends AbstractFormController<ViewMetadataModel> {

	@Autowired
	private IViewMetadataService viewMetadataService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		List<LookUp> prefixListNewDoc = new ArrayList<LookUp>();
		Long dept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		String prefixList1 = CommonMasterUtility.getHierarchicalLookUp(dept).getLookUpCode();
		List<LookUp> atta = CommonMasterUtility.getListLookup(MainetConstants.Dms.KDT,
				UserSession.getCurrent().getOrganisation());
		atta.forEach(prefix -> {
			prefixListNewDoc.add(prefix);
		});
		getModel().setDocTypeList(prefixListNewDoc);
		return new ModelAndView("KmsViewDocument", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "searchDetails", method = RequestMethod.POST)
	public ModelAndView searchMetadataDetails(@RequestParam("level1") Long deptId,
			@RequestParam("level2") Long metadataId, @RequestParam("metadataValue") String metadataValue,@RequestParam("docType") Long docType) {
		String callType = MainetConstants.Dms.KMS;
		List<DmsDocsMetadataDto> metadataList = viewMetadataService.getMetadataDetails(String.valueOf(deptId),
				String.valueOf(metadataId), metadataValue, UserSession.getCurrent().getEmployee().getGmid(),
				UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null, callType, null,docType, null, null);
		getModel().setDmsDocsMetadataDto(metadataList);
		getModel().getMetadatDto().setLevel1(deptId);
		getModel().getMetadatDto().setLevel2(metadataId);
		getModel().setMetadataValue(metadataValue);
		return new ModelAndView("KmsViewDocumentValidn", MainetConstants.FORM_NAME, getModel());

	}

}
