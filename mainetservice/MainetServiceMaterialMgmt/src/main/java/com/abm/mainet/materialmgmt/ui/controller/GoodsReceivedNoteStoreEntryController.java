package com.abm.mainet.materialmgmt.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.GrnInspectionItemDetDTO;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.service.IPurchaseOrderService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.Igoodsrecievednoteservice;
import com.abm.mainet.materialmgmt.ui.model.InspectionItemsModel;

@Controller
@RequestMapping("/GoodsReceivedNoteStoreEntry.html")
public class GoodsReceivedNoteStoreEntryController extends AbstractFormController<InspectionItemsModel>{
		
	@Autowired
	private IStoreMasterService storeMasterservice;
	
	@Autowired
    private  IEmployeeService employeeService;
	
	@Autowired
	private Igoodsrecievednoteservice goodsrecievednoteservice;
	
	@Autowired
	private IPurchaseOrderService purchaseOrderService;
	
	@Autowired
	private IBinMasService binMasService;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index(  final Model model ,HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(null, null,
				null, null, null, orgId, MainetConstants.FlagS));
		populateDtails(orgId);
		return new ModelAndView("GoodsNotesStoreEntry", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	private void populateDtails(Long orgId) {
		this.getModel().setCommonHelpDocs("GoodsReceivedNoteStoreEntry.html");

		this.getModel().setStoreIdAndNameList(storeMasterservice.getStoreIdAndNameList(orgId));
		Map<Long, String> storeMap = new HashMap<>();
		this.getModel().getStoreIdAndNameList().forEach(object -> storeMap.put(Long.valueOf(object[0].toString()), object[1].toString()));
		this.getModel().setPurchaseOrderObjects(purchaseOrderService.getPurcahseOrderIdAndNumbers(orgId));
		Map<Long, String> poMap = new HashMap<>();
		this.getModel().getPurchaseOrderObjects().forEach(object -> poMap.put(Long.valueOf(object[0].toString()), object[1].toString()));
		this.getModel().getGoodsReceivedNotesDtoList().forEach( goodsReceivedNote -> {
			goodsReceivedNote.setStoreName(storeMap.get(goodsReceivedNote.getStoreid()));
			goodsReceivedNote.setPoNumber(poMap.get(goodsReceivedNote.getPoid()));
		});
	}
	
	@RequestMapping(params = "addGrnStoreEntry", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addForm(Model model, final HttpServletRequest request, final HttpServletResponse response) {
		this.sessionCleanup(request);	
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setGrnObjectList(goodsrecievednoteservice.grnIdNoListByStatus(orgId, MainetConstants.FlagI));
		this.getModel().setEmployeesObject(employeeService.findAllEmpIntialInfoByOrg(orgId));		
		return new ModelAndView("GoodsReceivedNoteStoreEntry", MainetConstants.FORM_NAME, getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "editGRNStoreEntry", method = RequestMethod.POST)
	public ModelAndView editGRNStoreEntry(Model model, final HttpServletRequest request, @RequestParam("grnid") Long grnid) {
		this.sessionCleanup(request);	
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		getGRNDataByGrnId(grnid);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEmployeesObject(employeeService.findAllEmpIntialInfoByOrg(orgId));
		this.getModel().setBinLocObjectList(binMasService.getbinLocIdAndNameListByStore(this.getModel().getGoodsReceivedNotesDto().getStoreid(), orgId));
		return new ModelAndView("GoodsReceivedNoteStoreEntry", MainetConstants.FORM_NAME, this.getModel());
	}
		
	@ResponseBody	
	@RequestMapping(params = "viewGRNStoreEntry", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewGRNStoreEntry(Model model, final HttpServletRequest request, @RequestParam("grnid") Long grnid) {
		this.sessionCleanup(request);	
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		getGRNDataByGrnId( grnid);		
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEmployeesObject(employeeService.findAllEmpIntialInfoByOrg(orgId));
		this.getModel().setBinLocObjectList(binMasService.getbinLocIdAndNameListByStore(this.getModel().getGoodsReceivedNotesDto().getStoreid(), orgId));
		return new ModelAndView("GoodsReceivedNoteStoreEntry", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchGrnStoreEntryData", method = RequestMethod.POST)
	public ModelAndView searchGrnData(final HttpServletRequest request, final Model model) {
		bindModel(request);		
		getGRNDataByGrnId(this.getModel().getGoodsReceivedNotesDto().getGrnid());
		this.getModel().setBinLocObjectList(binMasService.getbinLocIdAndNameListByStore(this.getModel().getGoodsReceivedNotesDto().getStoreid(), UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("GoodsReceivedNoteStoreEntry", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/**
	 * common method to get data based on GrnId
	 **/
	private GoodsReceivedNotesDto getGRNDataByGrnId(Long grnId) {
		this.getModel().setGoodsReceivedNotesDto(goodsrecievednoteservice.getDetailsByGrnNo(grnId, UserSession.getCurrent().getOrganisation().getOrgid()));		
		Map<Long, List<GrnInspectionItemDetDTO>> map = new LinkedHashMap<Long, List<GrnInspectionItemDetDTO>>();
		this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().forEach(goodsreceivedNotesItemList->{			
			List<GrnInspectionItemDetDTO> grnInspectionDetDTOs = new ArrayList<GrnInspectionItemDetDTO>();
			grnInspectionDetDTOs.addAll(goodsreceivedNotesItemList.getIspectionItemsList());
			map.put(goodsreceivedNotesItemList.getGrnitemid(), grnInspectionDetDTOs);	
		});
		this.getModel().getInspectionItemsMap().putAll(map);
		return this.getModel().getGoodsReceivedNotesDto();
	}

	@RequestMapping(params = "grnStoreEntryDetails", method = RequestMethod.POST)
	public ModelAndView viewStoreEntryItemsForm(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,@RequestParam(required = false) Integer count, @RequestParam(required = false) Long grnid,
			@RequestParam(required = false) Long grnitemid, @RequestParam(required = false) String isExpiry, @RequestParam(required = false) Long itemid,
			@RequestParam(required = false) Double receivedqty, @RequestParam(required = false) String managementCode) {
		getModel().bind(httpServletRequest);
		this.getModel().getGrnitemDto().setIsExpiry(isExpiry);
		this.getModel().setManagementMethod(managementCode);
		this.getModel().setIndexCount(count);
		this.getModel().getInspectionItemsDto().setGrnid(grnid);
		this.getModel().getInspectionItemsDto().setGrnitemid(grnitemid);

		this.getModel().getInspectionItemsList().clear();
		List<GrnInspectionItemDetDTO> inspectionItemDetailList=this.getModel().getInspectionItemsMap().get(grnitemid);
		if(inspectionItemDetailList != null) {
			this.getModel().getInspectionItemsList().addAll(inspectionItemDetailList);
		}
		return new ModelAndView("inspectionItemsStory", MainetConstants.FORM_NAME,this.getModel());
	}
	 

	@ResponseBody
	@RequestMapping(params = "saveStoreEntryForm", method = RequestMethod.POST)
	public ModelAndView saveStoreEntryFormData( final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getInspectionItemsList().forEach(grnInspectionItemDetDTO->{
			grnInspectionItemDetDTO.setGrnid(this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(this.getModel().getIndexCount()).getGrnid());
			grnInspectionItemDetDTO.setGrnitemid(this.getModel().getInspectionItemsDto().getGrnitemid());
		});	
		this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(this.getModel().getIndexCount()).setAcceptqty(this.getModel().getInspectionItemsDto().getAcceptqty());
		this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(this.getModel().getIndexCount()).setRejectqty(this.getModel().getInspectionItemsDto().getRejectqty());

		List<GrnInspectionItemDetDTO> grnInspectionHelperDetDTOs = new ArrayList<GrnInspectionItemDetDTO>();
		grnInspectionHelperDetDTOs.addAll(this.getModel().getInspectionItemsList());
		this.getModel().getInspectionItemsMap().put(this.getModel().getInspectionItemsDto().getGrnitemid(), grnInspectionHelperDetDTOs);
		this.getModel().getInspectionItemsList().clear();
		return new ModelAndView("GoodsReceivedNoteStoreEntryValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@RequestMapping(params = "backToMainSearch", method = RequestMethod.POST)
	public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		bindModel(httpServletRequest);
		return new ModelAndView("GoodsReceivedNoteStoreEntry", MainetConstants.FORM_NAME, this.getModel());
	}	

	
	@ResponseBody
	@RequestMapping(params = "searchGoodsStoreEnrty", method = RequestMethod.POST)
	public ModelAndView searchGoodsStoreEnrty(Model model,final HttpServletRequest request,
			@RequestParam(required = false) Long storeId, @RequestParam(required = false) Long grnid,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate,@RequestParam(required = false) Long poid) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(storeId, grnid,
				fromDate, toDate, poid, orgId, MainetConstants.FlagS));
		this.getModel().getGoodsReceivedNotesDto().setGrnid(grnid);
		this.getModel().getGoodsReceivedNotesDto().setPoid(poid);
		this.getModel().getGoodsReceivedNotesDto().setFromDate(fromDate);
		this.getModel().getGoodsReceivedNotesDto().setToDate(toDate);
		this.getModel().getGoodsReceivedNotesDto().setStoreid(storeId);
		populateDtails(orgId);
		return new ModelAndView("searchGoodsNotesStoreEntry", MainetConstants.FORM_NAME, this.getModel());
	}

}
