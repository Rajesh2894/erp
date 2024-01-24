package com.abm.mainet.materialmgmt.ui.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;

import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.service.IPurchaseOrderService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.Igoodsrecievednoteservice;
import com.abm.mainet.materialmgmt.ui.model.GoodsReceivedNotesItemModel;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/GoodsReceivedNotesItem.html")
public class GoodsReceivedNotesItemController extends AbstractFormController<GoodsReceivedNotesItemModel> {

	@Autowired
	private IStoreMasterService storeMasterservice;
	
	@Autowired
	private Igoodsrecievednoteservice goodsrecievednoteservice;
	
	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private IAttachDocsService attachDocsService;
	
	@Autowired
	private IPurchaseOrderService purchaseOrderService;


	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(null,
				null, null, null, null, orgId, MainetConstants.FlagY));
		populateLists(orgId);
		return new ModelAndView("GoodsReceivedNotesList", MainetConstants.FORM_NAME, this.getModel());
	}

	
	private void populateLists(Long orgId) {
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

	
	@RequestMapping(params = "getPoItems", method = RequestMethod.POST)
	public ModelAndView searchPurchaseData(final Model model, HttpServletRequest request) {
		this.getModel().bind(request);
		this.getModel().setGoodsNotesDto(goodsrecievednoteservice.getPurchaseOrderData(this.getModel().getGoodsNotesDto(), 
				UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("GoodsReceivedNotesItemValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@ResponseBody
	@RequestMapping(params = "searchGoodsReceivedNote", method = RequestMethod.POST)
	public ModelAndView searchPurchaseData(final HttpServletRequest request, @RequestParam(required = false) Long storeId, 
			@RequestParam(required = false) Long grnid, @RequestParam(required = false) Date fromDate,
			@RequestParam(required = false) Date toDate, @RequestParam(required = false) Long poid) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().getGoodsNotesDto().setGrnid(grnid);
		this.getModel().getGoodsNotesDto().setPoid(poid);
		this.getModel().getGoodsNotesDto().setFromDate(fromDate);
		this.getModel().getGoodsNotesDto().setToDate(toDate);
		this.getModel().getGoodsNotesDto().setStoreid(storeId);
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(storeId,
				grnid, fromDate, toDate, poid, orgId, MainetConstants.FlagY));
		populateLists(orgId);
		return new ModelAndView("searchGoodsReceivedNote", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@RequestMapping(params = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addForm(Model model, final HttpServletRequest request, final HttpServletResponse response) {
		this.sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseOrderObjects(goodsrecievednoteservice.getPurchaseOrderListForGRN(orgId));		
		return new ModelAndView("GoodsReceivedNotesItemValidn", MainetConstants.FORM_NAME, getModel());
	}
		
	
	@RequestMapping(params = "ViewGoodsReceivedNotes", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewGoodsReceivedNotes(Model model, @RequestParam("grnid") Long grnid, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setGoodsNotesDto(goodsrecievednoteservice.goodsReceivedNoteEditAndView(grnid, UserSession.getCurrent().getOrganisation().getOrgid()));
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "MMM" + MainetConstants.WINDOWS_SLASH + 
				this.getModel().getGoodsNotesDto().getGrnno() + MainetConstants.WINDOWS_SLASH + this.getModel().getGoodsNotesDto().getGrnno());
		this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("GoodsReceivedNotesItemValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(params = "checkFileUpload", method = RequestMethod.POST)
    public @ResponseBody Boolean checkFileUpload(HttpServletRequest httpServletRequest,  HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        boolean flag = false;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            flag = true;
            break;
        }
        return flag;
    }

}