package com.abm.mainet.materialmgmt.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
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
import com.abm.mainet.materialmgmt.service.IPurchaseOrderService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.Igoodsrecievednoteservice;
import com.abm.mainet.materialmgmt.ui.model.InspectionItemsModel;


@Controller
@RequestMapping("/GoodsReceivedNoteInspection.html")
public class GoodsReceivedNoteInspectionController extends AbstractFormController<InspectionItemsModel>{
	
	@Autowired
	private IStoreMasterService storeMasterservice;
		
	@Resource
	private IEmployeeService employeeService;
	
	@Autowired
	private IPurchaseOrderService purchaseOrderService;
	
	@Autowired
	private Igoodsrecievednoteservice goodsrecievednoteservice;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index(Model model, HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(null, null,
				null, null, null, orgId, MainetConstants.FlagI));
		populateDtails(orgId);
		return new ModelAndView("GRNInspectionList", MainetConstants.FORM_NAME, this.getModel());
	}
	
	private void populateDtails(Long orgId) {
		this.getModel().setCommonHelpDocs("GoodsReceivedNoteInspection.html");
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
	
	
	@RequestMapping(params = "addGrnInspection", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addForm(Model model, final HttpServletRequest request, final HttpServletResponse response) {
		this.sessionCleanup(request);	
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setGrnObjectList(goodsrecievednoteservice.grnIdNoListByStatus(orgId, MainetConstants.FlagA));
		this.getModel().setEmployeesObject(employeeService.findAllEmpIntialInfoByOrg(orgId));
		return new ModelAndView("GoodsReceivedNoteInspection", MainetConstants.FORM_NAME, getModel());
	}
    
	
	@ResponseBody
	@RequestMapping(params = "editGRNInspection", method = RequestMethod.POST)
	public ModelAndView editGRNInspection(Model model, final HttpServletRequest request, @RequestParam("grnid") Long grnid) {
		this.sessionCleanup(request);	
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		getGRNDataBtGrnId(grnid);		
		this.getModel().setEmployeesObject(employeeService.findAllEmpIntialInfoByOrg(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("GoodsReceivedNoteInspection", MainetConstants.FORM_NAME, this.getModel());
	}
		
	@ResponseBody	
	@RequestMapping(params = "viewGRNInspection", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView ViewGRNInspection(Model model, final HttpServletRequest request, @RequestParam("grnid") Long grnid) {
		this.sessionCleanup(request);	
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		getGRNDataBtGrnId(grnid);
		this.getModel().setEmployeesObject(employeeService.findAllEmpIntialInfoByOrg(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("GoodsReceivedNoteInspection", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@ResponseBody
	@RequestMapping(params = "searchGrnData", method = RequestMethod.POST)
	public ModelAndView searchGrnData(final HttpServletRequest request, final Model model) {
		bindModel(request);		
		getGRNDataBtGrnId(this.getModel().getGoodsReceivedNotesDto().getGrnid());
		return new ModelAndView("GoodsReceivedNoteInspection", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/**
	 * common method to get data based on GrnId
	 **/
	private GoodsReceivedNotesDto getGRNDataBtGrnId(Long grnId) {
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
	
	
	@RequestMapping(params = "grnInspectionDetails", method = RequestMethod.POST)
	public ModelAndView viewInspectionItemsForm(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {	
		getModel().bind(httpServletRequest);
		int index = this.getModel().getIndexCount();
		this.getModel().getGrnitemDto().setIsExpiry(this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).getIsExpiry());
		this.getModel().setManagementMethod(this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).getManagement());
		this.getModel().getInspectionItemsDto().setGrnid(this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).getGrnid());
		this.getModel().getInspectionItemsDto().setGrnitemid(this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).getGrnitemid());
		
		this.getModel().getInspectionItemsList().clear();
		List<GrnInspectionItemDetDTO> inspectionItemDetailList = (List<GrnInspectionItemDetDTO>) this.getModel()
				.getInspectionItemsMap().get(this.getModel().getInspectionItemsDto().getGrnitemid());
		if(inspectionItemDetailList != null)
			this.getModel().getInspectionItemsList().addAll(inspectionItemDetailList);

		return new ModelAndView("inspectionItems", MainetConstants.FORM_NAME, this.getModel());
	}


	@ResponseBody
	@RequestMapping(params = "saveInspectionForm", method = RequestMethod.POST)
	public ModelAndView saveInspectionFormData(final Model model, final HttpServletRequest request) {
		bindModel(request);	
		int index = this.getModel().getIndexCount();
		this.getModel().getInspectionItemsList().forEach(grnInspectionItemDetDTO->{
			grnInspectionItemDetDTO.setGrnid(this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).getGrnid());
			grnInspectionItemDetDTO.setGrnitemid(this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).getGrnitemid());			
		});
		
		this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).setAcceptqty(this.getModel().getInspectionItemsDto().getAcceptqty());
		this.getModel().getGoodsReceivedNotesDto().getGoodsreceivedNotesItemList().get(index).setRejectqty(this.getModel().getInspectionItemsDto().getRejectqty());
		
		List<GrnInspectionItemDetDTO> grnInspectionHelperDetDTOs = new ArrayList<GrnInspectionItemDetDTO>();
		grnInspectionHelperDetDTOs.addAll(this.getModel().getInspectionItemsList().stream()
				.filter(item -> null != item.getQuantity()).collect(Collectors.toList()));
		this.getModel().getInspectionItemsMap().put(this.getModel().getInspectionItemsDto().getGrnitemid(), grnInspectionHelperDetDTOs);
		this.getModel().getInspectionItemsList().clear();
		return new ModelAndView("GoodsReceivedNoteInspection", MainetConstants.FORM_NAME, this.getModel());
	}
		
	
	/**To Check Duplicate Item No i.e  Serial No. Or Batch No.*/ 
	@ResponseBody
	@RequestMapping(params = "duplicateChecker", method = RequestMethod.POST)
	public List<String>  duplicateChecker(final Model model, final HttpServletRequest httpServletRequest) {		
		bindModel(httpServletRequest);
		List<String> itemNumberList = new ArrayList<>();
		List<String> modelItemNumberList = new ArrayList<>();		
		List<String> duplicateNoList = new ArrayList<>();
				
		this.getModel().getInspectionItemsList().forEach(grnInspectionItemDetDTO -> {
			if(grnInspectionItemDetDTO.getGrnitemid() == null && grnInspectionItemDetDTO.getItemNo() != null)
				itemNumberList.add(grnInspectionItemDetDTO.getItemNo());
		});
				
		List<GrnInspectionItemDetDTO> grnInspectionItemDetDTOs =  this.getModel().getInspectionItemsMap().entrySet()
		             .stream().flatMap(map -> map.getValue().stream()).collect(Collectors.toList());	
		if(grnInspectionItemDetDTOs != null && !grnInspectionItemDetDTOs.isEmpty()) {
			grnInspectionItemDetDTOs.forEach(detailDto->{
				if(itemNumberList.contains(detailDto.getItemNo()))
					modelItemNumberList.add(detailDto.getItemNo());					
			});
			duplicateNoList.addAll(modelItemNumberList);
		}		
		
		if( !itemNumberList.isEmpty() && itemNumberList.size() != 0 && (modelItemNumberList.isEmpty() || modelItemNumberList.size() == 0) ) {
			List<String>  duplicateNoListFromDB = goodsrecievednoteservice.checkExsistingNumbers(itemNumberList,this.getModel().getGoodsReceivedNotesDto().getStoreid(),
					UserSession.getCurrent().getOrganisation().getOrgid());			
			duplicateNoList.addAll(duplicateNoListFromDB);
		}
		return duplicateNoList;
	}
		
	@RequestMapping(params = "backToMainSearch", method = RequestMethod.POST)
	public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		bindModel(httpServletRequest);
		return new ModelAndView("GoodsReceivedNoteInspection", MainetConstants.FORM_NAME, this.getModel());
	}	
	
	@ResponseBody
	@RequestMapping(params = "searchGRNInspection", method = RequestMethod.POST)
	public ModelAndView searchgoodsInspection(Model model,  final HttpServletRequest request,
			@RequestParam(required = false) Long storeId, @RequestParam(required = false) Long grnid,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate,@RequestParam(required = false) Long poid) {
		this.bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(storeId, grnid,
				fromDate, toDate, poid, orgId, MainetConstants.FlagI));
		populateDtails(orgId);
		this.getModel().getGoodsReceivedNotesDto().setGrnid(grnid);
		this.getModel().getGoodsReceivedNotesDto().setPoid(poid);
		this.getModel().getGoodsReceivedNotesDto().setFromDate(fromDate);
		this.getModel().getGoodsReceivedNotesDto().setToDate(toDate);
		this.getModel().getGoodsReceivedNotesDto().setStoreid(storeId);
		return new ModelAndView("searchGRNInspection", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = { "doItemDetailsDeletion" })
	public @ResponseBody boolean doItemDetailsDeletion(final Model model, final HttpServletRequest request,
			@RequestParam("rowIndex") int rowIndex) {
		if (this.getModel().getInspectionItemsList().size() > rowIndex)
			this.getModel().getInspectionItemsList().remove(rowIndex);
		return true;
	}
	
					
}
