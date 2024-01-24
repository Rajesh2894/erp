package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBillDistributionService;
import com.abm.mainet.property.ui.model.PropertyBillDistributionModel;
import com.abm.mainet.property.ui.model.PropertyBillGenerationModel;

/**
 * @author cherupelli.srikanth
 * @since 07 May 2021
 */
@Controller
@RequestMapping("/PropertyBillDistribution.html")
public class PropertyBillDistributionController extends AbstractFormController<PropertyBillDistributionModel> {

	@Autowired
	private PrimaryPropertyService primaryPropertyService;

	@Autowired
	private PropertyBillDistributionService propertyBillDistributionService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		getModel().bind(request);
		getModel().getSpecialNotGenSearchDto().setSpecNotSearchType("S");
		getModel().getSpecialNotGenSearchDto().setAction(MainetConstants.FlagA);
		return index();
	}

	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
		this.getModel().bind(request);
		PropertyBillDistributionModel model = this.getModel();
		List<String> flatNoList = null;
		String billingMethod = null;
		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp billingMethodLookUp = null;
		try {
			billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId,
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (billingMethodLookUp != null) {
			billingMethod = billingMethodLookUp.getLookUpCode();
		}
		if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
			flatNoList = new ArrayList<String>();
			flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo,
					UserSession.getCurrent().getOrganisation().getOrgid());
		}
		model.setFlatNoList(flatNoList);
		return flatNoList;
	}

	@RequestMapping(method = RequestMethod.POST, params = "serachPropForBillDistribution")
	public ModelAndView searchForSkdcl(HttpServletRequest request) {
		getModel().bind(request);
		PropertyBillDistributionModel model = this.getModel();
		model.getSpecialNotGenSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		model.setNotGenSearchDtoList(null);
		if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("M")
				&& (model.getSpecialNotGenSearchDto().getAssWard1() == null
						|| model.getSpecialNotGenSearchDto().getAssWard1() <= 0)) {
			model.addValidationError("Please select ward");
		}
		if (!model.hasValidationErrors()) {
			List<NoticeGenSearchDto> notGenShowList = null;
			if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("S") && (model.getSpecialNotGenSearchDto().getPropertyNo() == null
					|| model.getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
					&& (model.getSpecialNotGenSearchDto().getOldPropertyNo() == null
							|| model.getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())) {
				model.addValidationError("Please enter valid property number or Old property number.");
			} else {
				notGenShowList = propertyBillDistributionService
						.getAllBillsForDistributionDateUpdation(model.getSpecialNotGenSearchDto());
				model.setNotGenSearchDtoList(notGenShowList);
				if (notGenShowList == null || notGenShowList.isEmpty()) {
					model.addValidationError("No record found");
				}else if(CollectionUtils.isNotEmpty(notGenShowList) && StringUtils.equals(notGenShowList.get(0).getCheckStatus(), MainetConstants.FlagY)) {
                	model.addValidationError("Distribution Date is already updated");
                	notGenShowList.clear();
                }
			}
		}
		return defaultResult();
	}
}
