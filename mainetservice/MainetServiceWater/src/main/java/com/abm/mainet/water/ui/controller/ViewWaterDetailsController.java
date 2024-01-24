package com.abm.mainet.water.ui.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.service.TbComparentMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.repository.BillMasterJpaRepository;
import com.abm.mainet.water.rest.dto.ViewBillMas;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;
import com.abm.mainet.water.service.IViewWaterDetailsService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.ViewWaterDetailsModel;

/**
 * @author cherupelli.srikanth
 * @since 29 July 2020
 */
@Controller
@RequestMapping("/ViewWaterDetails.html")
public class ViewWaterDetailsController extends AbstractFormController<ViewWaterDetailsModel> {

	@Autowired
	IViewWaterDetailsService viewWaterDetailsService;

	@Autowired
	private IDuplicateReceiptService iDuplicateReceiptService;
	
	@Autowired
	private BillMasterJpaRepository billMasterJpaRepository;
	
	@Autowired
	private NewWaterRepository waterRepository;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setShowForm(MainetConstants.FlagN);
		return index();
	}

	@ResponseBody
	@RequestMapping(params = "searchWaterDetails", method = RequestMethod.POST)
	public List<WaterDataEntrySearchDTO> searchWaterDetails(HttpServletRequest request) {
		ViewWaterDetailsModel model = this.getModel();
		model.bind(request);
		model.getEntrySearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		model.getEntrySearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		List<WaterDataEntrySearchDTO> searchConnectionDetails = ApplicationContextProvider.getApplicationContext()
				.getBean(NewWaterConnectionService.class)
				.searchConnectionDetails(model.getEntrySearchDto(), null, null, null);
		return searchConnectionDetails;
	}

	@RequestMapping(params = "getWaterDetail", method = { RequestMethod.POST })
	public ModelAndView getWaterConnectiondetail(HttpServletRequest request,
			@RequestParam(value = "connNo") String connNo) {
		this.getModel().bind(request);
		ModelAndView mv = new ModelAndView("ViewWaterDetailsValidn", MainetConstants.FORM_NAME, this.getModel());
		ViewCsmrConnectionDTO waterViewByConnectionNumber = null;
		Organisation organisation = new Organisation();
		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
			waterViewByConnectionNumber = viewWaterDetailsService.getWaterViewByConnectionNumberForSkdcl(
					connNo, UserSession.getCurrent().getOrganisation().getOrgid(),
					this.getModel().getViewConnectionDto().getCsOldccn());
		}else {
			waterViewByConnectionNumber = viewWaterDetailsService.getWaterViewByConnectionNumber(
					connNo, UserSession.getCurrent().getOrganisation().getOrgid(),
					this.getModel().getViewConnectionDto().getCsOldccn());
		}
		if (waterViewByConnectionNumber == null) {
			this.getModel()
					.addValidationError(getApplicationSession().getMessage("water.enter.valid.connectionNo"));
		} else {
			this.getModel().setViewConnectionDto(waterViewByConnectionNumber);
			Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
			this.getModel()
					.setCollectionDetails(viewWaterDetailsService.getCollectionDetails(
							String.valueOf(this.getModel().getViewConnectionDto().getCsIdn()),
							UserSession.getCurrent().getOrganisation(), deptId));
			this.getModel().setShowForm(MainetConstants.FlagY);
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		return mv;
	}

	@RequestMapping(params = "viewWaterDet", method = { RequestMethod.POST })
	public ModelAndView vieBillAndPropDetails(@RequestParam("bmIdNo") final long bmIdNo,
			final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		ViewWaterDetailsModel model = this.getModel();
		List<ViewBillMas> billMasList = new ArrayList<>(0);
		model.getViewConnectionDto().getViewBillMasList().stream().filter(bill -> bill.getBmIdno() == bmIdNo)
				.forEach(bills -> {
					billMasList.add(bills);
				});
		model.getViewConnectionDto().setViewBillMasWithDetList(billMasList);
		return new ModelAndView("ViewWaterTaxDetails", "command", model);
	}

	@RequestMapping(params = "backToWaterDet", method = RequestMethod.POST)
	public ModelAndView backToPropDet(HttpServletRequest request) {
		ViewWaterDetailsModel model = this.getModel();
		model.bind(request);
		return new ModelAndView("ViewWaterDetailsValidn", "command", model);
	}

	@RequestMapping(params = "receiptDownload", method = { RequestMethod.POST })
	public ModelAndView receiptDownload(@RequestParam("reciptId") Long reciptId,
			@RequestParam("receiptNo") Long receiptNo, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		bindModel(httpServletRequest);
		ViewCsmrConnectionDTO connectionDto = this.getModel().getViewConnectionDto();

		ChallanReceiptPrintDTO receiptDto = iDuplicateReceiptService.getRevenueReceiptDetails(reciptId, receiptNo,
				String.valueOf(this.getModel().getViewConnectionDto().getCsIdn()),UserSession.getCurrent().getOrganisation().getOrgid(),UserSession.getCurrent().getLanguageId());
		final ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class).getServiceByShortName(PrefixConstants.NewWaterServiceConstants.BPW,
        		UserSession.getCurrent().getOrganisation().getOrgid());
        if(UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH){
        	receiptDto.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
 					+ MainetConstants.WHITE_SPACE + service.getSmServiceName());
        }else {
        	receiptDto.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
 					+ MainetConstants.WHITE_SPACE + service.getSmServiceNameMar());
        }

		if (CollectionUtils.isEmpty(receiptDto.getWardZoneList())
				|| StringUtils.isBlank(receiptDto.getUsageType1_L())) {
			String wardZonePrefixValue = null;
			wardZonePrefixValue = "WWZ";

			if (StringUtils.isNotBlank(wardZonePrefixValue)) {
				TbComparamMas comparamMas = ApplicationContextProvider.getApplicationContext()
						.getBean(TbComparamMasService.class).findComparamDetDataByCpmId(wardZonePrefixValue);
				List<TbComparentMas> comparentMasList = ApplicationContextProvider.getApplicationContext()
						.getBean(TbComparentMasService.class).findComparentMasDataById(comparamMas.getCpmId(),
								UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(comparentMasList)) {
					List<String[]> wardZoneList = new ArrayList<String[]>();
					comparentMasList.sort(Comparator.comparing(TbComparentMas::getComLevel));

					comparentMasList.forEach(comparentMas -> {
						Long coddwzId = null;
						String prefixDesc = null;
						if (comparentMas.getComLevel() == 1) {
							prefixDesc = connectionDto.getCodDwzid1();
						} else if (comparentMas.getComLevel() == 2) {
							prefixDesc = connectionDto.getCodDwzid2();
						} else if (comparentMas.getComLevel() == 3) {
							prefixDesc = connectionDto.getCodDwzid3();
						} else if (comparentMas.getComLevel() == 4) {
							prefixDesc = connectionDto.getCodDwzid4();
						} else if (comparentMas.getComLevel() == 5) {
							prefixDesc = connectionDto.getCodDwzid5();
						}
						if (StringUtils.isNotBlank(prefixDesc)) {
							String[] wardZone = new String[2];
							wardZone[0] = comparentMas.getComDesc();
							wardZone[1] = prefixDesc;
							wardZoneList.add(wardZone);
							receiptDto.setWardZoneList(wardZoneList);
						}
					});
				}
			}

			receiptDto.setUsageType1_V(connectionDto.getTrmGroup1());
			receiptDto.setUsageType2_V(connectionDto.getTrmGroup2());
			receiptDto.setUsageType3_V(connectionDto.getTrmGroup3());
			receiptDto.setUsageType4_V(connectionDto.getTrmGroup4());
			receiptDto.setUsageType5_V(connectionDto.getTrmGroup5());

			receiptDto.setPropNo_connNo_estateNo_L("Connection No.");
			receiptDto.setPropNo_connNo_estateN_V(connectionDto.getCsCcn());
			receiptDto.setAddress(connectionDto.getCsAdd());
		}

		getModel().setReceiptDTO(receiptDto);
		if (receiptDto != null) {
			return new ModelAndView("revenueReceiptPrint", MainetConstants.FORM_NAME, getModel());
		}
		return null;

	}

	/* Birt report */
	@RequestMapping(params = "getWaterBillBirtReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("billNo") String billNo,
			final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.ASCL)) {
			TbWtBillMasEntity billDataByBillNo = billMasterJpaRepository.getBillPaymentDataByBmno(Long.valueOf(billNo),
					currentOrgId);
			String connectionNo = waterRepository.getConnectionDetailsById(billDataByBillNo.getCsIdn()).getCsCcn();
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=ViewWaterDownloadBillReport.rptdesign&OrgId="
					+ currentOrgId + "&FinYear=" + billDataByBillNo.getBmYear() + "&BmIdno=" + billNo + "&ConnNo="
					+ connectionNo;
		} else {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=WaterBillReport.rptdesign&__format=pdf&orgid="
					+ currentOrgId + "&bmIdno=" + billNo;
		}

	}
	
}
