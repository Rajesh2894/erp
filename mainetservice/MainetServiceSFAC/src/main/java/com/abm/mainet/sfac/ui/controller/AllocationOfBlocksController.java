/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.service.AllocationOfBlocksService;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.AllocationOfBlocksModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.ALLOCATION_OF_BLOCK_HTML)
public class AllocationOfBlocksController extends AbstractFormController<AllocationOfBlocksModel> {

	private static final Logger LOG = LoggerFactory.getLogger(AllocationOfBlocksController.class);

	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private AllocationOfBlocksService allocationOfBlocksService;

	@Autowired
	private TbOrganisationService organisationService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private CBBOMasterService cbboMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		populateModel();
		Employee emp = UserSession.getCurrent().getEmployee();
		this.getModel().getBlockAllocationDto().setOrgName(emp.getEmpname());
		this.getModel().getBlockAllocationDto().setOrganizationNameId(emp.getEmpId());
		this.getModel()
				.setBlockAllocationDtoList(allocationOfBlocksService.getAllBlockDetailSummary(
						UserSession.getCurrent().getOrganisation().getOrgid(),
						UserSession.getCurrent().getEmployee().getMasId()));
		return new ModelAndView(MainetConstants.Sfac.ALLOCATION_OF_BLOCK_SUMMARY_FORM, MainetConstants.FORM_NAME,
				getModel());
	}

	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest, Model model) {
		sessionCleanup(httpServletRequest);
		populateModel();
		this.getModel().setSaveMode("A");
		return new ModelAndView(MainetConstants.Sfac.ALLOCATION_OF_BLOCK_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	private void populateModel() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// this.getModel().setDepartmentList(departmentService.findByOrgId(orgId,
		// langId));
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
		List<CommonMasterDto> masList = new ArrayList<>();
		Organisation organisation = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		List<CommonMasterDto> masterDtoList = iaMasterService.getMasterDetail(organisation.getOrgid());
		if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.IA)) {
			masterDtoList.forEach(det -> {
				if (UserSession.getCurrent().getEmployee().getMasId() != null
						&& det.getId().equals(UserSession.getCurrent().getEmployee().getMasId())) {
					masList.add(det);
				}
			});
			this.getModel().setCommonMasterDtoList(masList);
		} else
			this.getModel().setCommonMasterDtoList(masterDtoList);
		this.getModel()
				.setCbboMasterList(cbboMasterService.findCbboById(UserSession.getCurrent().getEmployee().getMasId()));
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.GET_ALLBLOCK_DATA, method = RequestMethod.POST)
	public ModelAndView getAllBlockData(@RequestParam("orgTypeId") Long orgTypeId,
			@RequestParam("organizationNameId") Long organizationNameId,
			@RequestParam("allocationYearId") Long allocationYearId,@RequestParam("sdb1") Long sdb1 ,
			@RequestParam("sdb2") Long sdb2,@RequestParam("sdb3") Long sdb3) {
		List<BlockAllocationDto> blockAllocationDtoList = new ArrayList<BlockAllocationDto>();
		blockAllocationDtoList = allocationOfBlocksService.getBlockDetailsByIds(orgTypeId, organizationNameId,
				allocationYearId, orgTypeId,sdb1,sdb2,sdb3);

		this.getModel().setBlockAllocationDtoList(blockAllocationDtoList);
		this.getModel().getBlockAllocationDto().setOrgTypeId(orgTypeId);
		if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.NPMA) || UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.CBBO))
		this.getModel().getBlockAllocationDto().setOrganizationNameId(organizationNameId);
		this.getModel().getBlockAllocationDto().setAllocationYearId(allocationYearId);
		this.getModel().getBlockAllocationDto().setSdb1(sdb1);
		this.getModel().getBlockAllocationDto().setSdb2(sdb2);
		this.getModel().getBlockAllocationDto().setSdb3(sdb3);
		/*
		 * Map<Object, List<BlockAllocationDto>> grouped =
		 * blockAllocationDtoList.stream() .collect(Collectors.groupingBy(o ->
		 * o.getSdb1()));
		 * 
		 * List<BlockAllocationDto> beanList = new LinkedList<>(); for (Entry<Object,
		 * List<BlockAllocationDto>> entry : grouped.entrySet()) { if
		 * (CollectionUtils.isNotEmpty(entry.getValue())) { List<BlockAllocationDto>
		 * listBean = entry.getValue(); beanList.add(listBean.get(0)); } }
		 * 
		 * final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
		 * UserSession.getCurrent().getOrganisation());
		 * this.getModel().setStateList(stateList);
		 */
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
		return new ModelAndView("AllocationOfBlocksFormValidn", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.GET_ALLBLOCK_DETAILS, method = RequestMethod.POST)
	public List<BlockAllocationDto> getAllBlockDetailsByStateId(@RequestParam("orgTypeId") Long orgTypeId,
			@RequestParam("organizationNameId") Long organizationNameId,
			@RequestParam("allocationYearId") Long allocationYearId, @RequestParam("sdb1") Long sdb1) {
		List<BlockAllocationDto> blockAllocationDtoList = new ArrayList<BlockAllocationDto>();
		blockAllocationDtoList = allocationOfBlocksService.getAllBlockDetailsByStateId(orgTypeId, organizationNameId,
				allocationYearId, orgTypeId);
		this.getModel().setBlockAllocationDtoList(blockAllocationDtoList);
		return blockAllocationDtoList;
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
		BlockAllocationDto mastDto = new BlockAllocationDto();
		if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.CBBO)) {
		Long cbboId = UserSession.getCurrent().getEmployee().getMasId();
		if (cbboId != null)
			mastDto = allocationOfBlocksService.getDetailById(blockId,cbboId);
		}
		else
			mastDto = allocationOfBlocksService.getDetailById(blockId,null);
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

		});

		//if (dto.getAllocationCategory() != null) {
			List<LookUp> alcationList = CommonMasterUtility.getLevelData("ALC", 2,
					UserSession.getCurrent().getOrganisation());
			this.getModel().setAllocationSubCatgList(alcationList);
		//}
		mastDto.getTargetDetDto().forEach(targetDto -> {
			if (targetDto.getAllocationCategory() != null) {
				List<LookUp> alcList = CommonMasterUtility.getLevelData("ALC", 2,
						UserSession.getCurrent().getOrganisation());
				this.getModel().setAllcSubCatgTargetList(alcList);
			}
		});

		List<CommonMasterDto> masterDtoList = iaMasterService.getMasterDetail(mastDto.getOrgTypeId());
		this.getModel().setCommonMasterDtoList(masterDtoList);
		return new ModelAndView(MainetConstants.Sfac.ALLOCATION_OF_BLOCK_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = "getDistrictList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictListByStateId(@RequestParam("stateId") Long stateId, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();
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
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();

		try {
			/*
			 * List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 3,
			 * UserSession.getCurrent().getOrganisation()); lookUpList1 =
			 * lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == distId)
			 * .collect(Collectors.toList());
			 */
			Organisation iaOrg = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
			lookUpList1 = allocationOfBlocksService.getNotAllocatedBlockList(distId, iaOrg.getOrgid());
			return lookUpList1;
		} catch (Exception e) {
			LOG.error("SDB Prefix not found");
			return lookUpList1;
		}
	}

	@ResponseBody
	@RequestMapping(params = "checKDataExist", method = RequestMethod.POST)
	public Boolean checKDataExist(@RequestParam("orgTypeId") Long orgTypeId, @RequestParam("orgNameId") Long orgNameId,
			@RequestParam("allocationYearId") Long allocationYearId) {
		boolean result = allocationOfBlocksService.checKDataExist(orgTypeId, orgNameId, allocationYearId);
		if (result == true) {
			return true;
		}
		return false;
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
