/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.service.AllocationOfBlocksService;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.ChangeofBlockModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.CHANGE_OF_BLOCK_HTML)
public class ChangeofBlockController extends AbstractFormController<ChangeofBlockModel> {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeofBlockController.class);

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private AllocationOfBlocksService allocationOfBlocksService;


	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private TbOrganisationService organisationService;

	@Autowired
	private CBBOMasterService cbboMasterService;

	@Autowired
	private IOrganisationService orgService;
	
	 @Autowired
     private IAttachDocsService attachDocsService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setShowHideFlag(MainetConstants.FlagN);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long masId=UserSession.getCurrent().getEmployee().getMasId();
		populateModel();
		LOG.info("masId"+masId +"orgid"+ orgId);
		this.getModel()
				.setBlockAllocationDtoList(allocationOfBlocksService.getAllBlockDetailSummary(orgId,masId));
		return new ModelAndView(MainetConstants.Sfac.CHANGE_OF_SUMMARY_BLOCK, MainetConstants.FORM_NAME, getModel());
	}

	private void populateModel() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// get organization list for sfac project
		List<TbOrganisation> orgList = organisationService.findAll();
		this.getModel().setOrgList(orgList);
		Organisation org = orgService.getOrganisationById(orgId);
		this.getModel().setOrgShortNm(org.getOrgShortNm());

		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
		final List<LookUp> allcCatgList = CommonMasterUtility.getLevelData("ALC", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setAllocationCatgList(allcCatgList);
		boolean result = allocationOfBlocksService.checkMasIdPresent(UserSession.getCurrent().getEmployee().getMasId());
		if (result == true)
			this.getModel().setShowEdit(MainetConstants.FlagY);
		Organisation organisation = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		List<CommonMasterDto> masterDtoList = iaMasterService.getMasterDetail(organisation.getOrgid());
		this.getModel().setCommonMasterDtoList(masterDtoList);
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
	}


	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.GET_ALLBLOCK_DATA, method = RequestMethod.POST)
	public ModelAndView getAllBlockData(HttpServletRequest request, @RequestParam("orgTypeId") Long orgTypeId,
			@RequestParam("organizationNameId") Long organizationNameId,
			@RequestParam("allocationYearId") Long allocationYearId,@RequestParam("sdb1") Long sdb1 ,
			@RequestParam("sdb2") Long sdb2,@RequestParam("sdb3") Long sdb3) {
		this.bindModel(request);
		ChangeofBlockModel model = this.getModel();
		List<BlockAllocationDto> blockAllocationDtoList = new ArrayList<BlockAllocationDto>();
		blockAllocationDtoList = allocationOfBlocksService.getBlockDetailsByIds(orgTypeId, organizationNameId,
				allocationYearId, orgTypeId,sdb1,sdb2,sdb3);
		/*Map<Object, List<BlockAllocationDto>> grouped = blockAllocationDtoList.stream()
				.collect(Collectors.groupingBy(o -> o.getSdb1()));

		List<BlockAllocationDto> beanList = new LinkedList<>();
		for (Entry<Object, List<BlockAllocationDto>> entry : grouped.entrySet()) {
			if (CollectionUtils.isNotEmpty(entry.getValue())) {
				List<BlockAllocationDto> listBean = entry.getValue();
				beanList.add(listBean.get(0));
			}
		}
	*/
		if (CollectionUtils.isNotEmpty(blockAllocationDtoList))
			this.getModel().setShowHideFlag(MainetConstants.FlagY);
		else
			this.getModel().setShowHideFlag(MainetConstants.FlagM);
		this.getModel().setBlockAllocationDtoList(blockAllocationDtoList);
		this.getModel().getBlockAllocationDto().setOrgTypeId(orgTypeId);
		this.getModel().getBlockAllocationDto().setOrganizationNameId(organizationNameId);
		this.getModel().getBlockAllocationDto().setSdb1(sdb1);
		this.getModel().getBlockAllocationDto().setSdb2(sdb2);
		this.getModel().getBlockAllocationDto().setSdb3(sdb3);
		if (CollectionUtils.isNotEmpty(blockAllocationDtoList)
				&& blockAllocationDtoList.get(0).getAllocationYearId() != null)
			this.getModel().getBlockAllocationDto()
					.setAllocationYearId(blockAllocationDtoList.get(0).getAllocationYearId());
		else
			this.getModel().getBlockAllocationDto().setAllocationYearId(allocationYearId);
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
		
		if (null !=sdb1 && sdb1 !=0) {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			List<LookUp> lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == sdb1)
					.collect(Collectors.toList());
			this.getModel().setDistrictList(lookUpList1);
		}
		
		if (null !=sdb2 && sdb2 !=0) {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 3,
					UserSession.getCurrent().getOrganisation());
			List<LookUp> lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == sdb2)
					.collect(Collectors.toList());
			this.getModel().setBlockList(lookUpList1);
		}
		// this.getModel().setVacantBlockList(allocationOfBlocksService.getNotAllocatedBlockList(orgId));
		/*
		 * ServiceMaster sm =
		 * serviceMasterService.getServiceMasterByShortCode(MainetConstants.Sfac.CBR,
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 * 
		 * LookUp checkListApplLookUp =
		 * CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify(),
		 * UserSession.getCurrent().getOrganisation()); if
		 * (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA) &&
		 * this.getModel().getShowHideFlag().equals(MainetConstants.FlagY)) {
		 * model.getCheckListFromBrms(); model.setChecklistCheck(MainetConstants.FlagY);
		 * } else { model.setChecklistCheck(MainetConstants.FlagN); }
		 */
		// 
		return new  ModelAndView("ChangeofBlockSummaryValidn",MainetConstants.FORM_NAME,getModel());
		//return defaultMyResult();
	}

	@RequestMapping(params = "getDistrictList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictListByStateId(@RequestParam("stateId") Long stateId, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == stateId)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			LOG.error("SDB Prefix not found");
			return lookUpList1;
		}
	}

	@RequestMapping(params = "getBlockList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getBlockListByDistrictId(@RequestParam("distId") Long distId,
			@RequestParam("orgTypeId") Long orgTypeId, HttpServletRequest request) {
		List<LookUp> notAllocatedBlockList = new java.util.ArrayList<LookUp>();
		// List<LookUp> notAllocatedBlockList =
		// allocationOfBlocksService.getNotAllocatedBlockList(sdbId2,UserSession.getCurrent().getOrganisation().getOrgid());
		try {
			notAllocatedBlockList = allocationOfBlocksService.getNotAllocatedBlockList(distId, orgTypeId);

			/*
			 * List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB",
			 * 3,UserSession.getCurrent().getOrganisation()); for (LookUp lookUp2 :
			 * notAllocatedBlockList) { lookUpList1 = lookUpList.stream().filter(lookUp ->
			 * lookUp.getLookUpParentId() == lookUp2.getLookUpId())
			 * .collect(Collectors.toList()); }
			 */
			return notAllocatedBlockList;
		} catch (Exception e) {
			LOG.error("SDB Prefix not found");
			return notAllocatedBlockList;
		}
	}

	/**
	 * generate Payment mode and save duplicate license
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = MainetConstants.Sfac.SAVE_ALLOCATION_OF_BLOCK_FORM, method = RequestMethod.POST)
	public ModelAndView saveAllocationOfBlockForm(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ChangeofBlockModel model = this.getModel();
		List<DocumentDetailsVO> docs = model.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}

		model.getBlockAllocationDto().setDocumentList(docs);
		fileUpload.validateUpload(model.getBindingResult());
		/* if (model.validateInputs()) { */
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else
			return defaultMyResult();
		// }
		// return defaultMyResult();
	}

	@RequestMapping(params = "getMasterDetail", method = { RequestMethod.POST })
	public @ResponseBody List<CommonMasterDto> getMasterDetail(final HttpServletRequest request, final Model model,
			@RequestParam("orgid") final Long orgid) {
		List<CommonMasterDto> masterDtoList = null;
		try {
			masterDtoList = iaMasterService.getMasterDetail(orgid);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		this.getModel().setCommonMasterDtoList(masterDtoList);
		return masterDtoList;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long blockId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setSaveMode(mode);
		populateModel();
		List<BlockAllocationDetailDto>  inactiveDtoList = new ArrayList<BlockAllocationDetailDto>();
		List<BlockAllocationDetailDto>  changedDtoList = new ArrayList<BlockAllocationDetailDto>();
		Long cbboId = UserSession.getCurrent().getEmployee().getMasId();
		BlockAllocationDto mastDto = allocationOfBlocksService.getDetailById(blockId,cbboId);
		this.getModel().setBlockAllocationDto(mastDto);
		
		List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
				UserSession.getCurrent().getOrganisation());
		List<LookUp> blckList = CommonMasterUtility.getLevelData("SDB", 3,
				UserSession.getCurrent().getOrganisation());
		
		mastDto.getBlockDetailDto().forEach(dto -> {
			List<LookUp> distList = new ArrayList<LookUp>();
			List<LookUp> blockList = new ArrayList<LookUp>();
			if (dto.getStateId() != null) {
				distList = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == dto.getStateId())
						.collect(Collectors.toList());
				dto.setDistrictList(distList);
			}
			if (dto.getDistId() != null) {
				blockList = blckList.stream().filter(lookUp -> lookUp.getLookUpParentId() == dto.getDistId())
						.collect(Collectors.toList());
				dto.setBlockList(blockList);
			}
			if (dto.getStatus() != null && (dto.getStatus().equals(MainetConstants.FlagI) || dto.getStatus().equals(MainetConstants.FlagA)))
				inactiveDtoList.add(dto);
			if (dto.getStatus() != null && dto.getStatus().equals(MainetConstants.FlagC))
				changedDtoList.add(dto);
		});

		CBBOMasterDto cbbodto = cbboMasterService.findById(cbboId);
		BlockAllocationDetailDto d = new BlockAllocationDetailDto();
		d.setCbboName(cbbodto.getCbboName());
		d.setCbboId(cbbodto.getCbboId());
		changedDtoList.add(d);
		
		//if (dto.getAllocationCategory() != null) {
			List<LookUp> alcList = CommonMasterUtility.getLevelData("ALC", 2,
					UserSession.getCurrent().getOrganisation());
			this.getModel().setAllocationSubCatgList(alcList);
		//}
		List<LookUp> allcationList = CommonMasterUtility.getLevelData("ALC", 2, UserSession.getCurrent().getOrganisation());
		this.getModel().setAllcSubCatgTargetList(allcationList);
		this.getModel().getNewBlockAllocationDto().setBlockDetailDto(changedDtoList);
		this.getModel().getBlockAllocationDto().setBlockDetailDto(inactiveDtoList);
	
	
		List<CommonMasterDto> masterDtoList = iaMasterService.getMasterDetail(mastDto.getOrgTypeId());
		this.getModel().setCommonMasterDtoList(masterDtoList);
	    this.getModel()
        .setAttachDocsList(attachDocsService.findByCode(mastDto.getOrgId(),MainetConstants.Sfac.CBR + MainetConstants.FILE_PATH_SEPARATOR + mastDto.getApplicationId()));
        logger.info("doclist"+ this.getModel().getAttachDocsList());
		return new ModelAndView(MainetConstants.Sfac.CHANGE_OF_BLOCK, MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getAlcSubCatList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getalcSubCatList(@RequestParam("allocationCategory") Long allocationCategory,
			HttpServletRequest request) {
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ALC", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == allocationCategory)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			LOG.error("ALC Prefix not found");
			return lookUpList1;
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<DocumentDetailsVO> attachments = new ArrayList<>();
		for (int i = 0; i <= this.getModel().getLength(); i++) {
			attachments.add(new DocumentDetailsVO());
		}
		this.getModel().setAttachments(attachments);
		Long count1 = 0l;
		Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()) {
				fileMap1.put(count1, entry.getValue());
				count1++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);
		return new ModelAndView(MainetConstants.Sfac.CHANGE_OF_BLOCK, MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ENTRY_DELETE)
	public void doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getNewBlockAllocationDto().getBlockDetailDto().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			List<Long> enclosureRemoveById = new ArrayList<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getNewBlockAllocationDto().getBlockDetailDto();
					enclosureRemoveById.add(entry.getKey());
					this.getModel().getAttachments().remove(id);
				}
			}
			Long count1 = 0l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				if (!list.isEmpty()) {
					fileMap1.put(count1, entry.getValue());
					count1++;
				}
			}
			FileUploadUtility.getCurrent().setFileMap(fileMap1);
		}

	}

	
	@RequestMapping(params = "getDistrictData", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictData(@RequestParam("sdb1") Long sdb1, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == sdb1)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			LOG.error("SDB Prefix not found"+e);
			return lookUpList1;
		}
	}

	@RequestMapping(params = "getBlockData", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getBlockData(@RequestParam("sdb2") Long sdb2,
			HttpServletRequest request) {
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();

		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 3,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == sdb2)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			LOG.error("SDB Prefix not found"+e);
			return lookUpList1;
		}
	}
}
