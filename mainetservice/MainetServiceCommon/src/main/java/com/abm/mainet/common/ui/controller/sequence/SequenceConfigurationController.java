package com.abm.mainet.common.ui.controller.sequence;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentDet;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbDeporgMapService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ISequenceConfigMasterService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.service.TbComparentDetService;
import com.abm.mainet.common.service.TbComparentMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.SequenceConfigurationModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author sadik.shaikh
 *
 */
@Controller
@RequestMapping("SequenceConfigrationMaster.html")
public class SequenceConfigurationController extends AbstractFormController<SequenceConfigurationModel> {

	@Autowired
	DepartmentService departmentService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Resource
	private TbComparamMasService tbComparamMasService;

	@Resource
	TbOrganisationService tbOrganisationService;

	@Autowired
	TbServicesMstService serviceMaster;

	@Autowired
	private IOrganisationService iOrganisationService;

	@Resource
	TbComparentDetService tbComparentDetService;

	@Autowired
	private ISequenceConfigMasterService sequenceConfigMasterService;

	@Resource
	TbDeporgMapService tbDeporgMapService;

	@Resource
	TbComparentMasService tbComparentMasService;

	List<TbComparentMas> tbComparentMasList = new ArrayList<>();
	List<TbComparentDet> tbComparentDetList = new ArrayList<>();
	List<TbComparentDet> comparentDetList = new ArrayList<>();
	List<TbComparamMas> listData = new ArrayList<>();

	int level;
	long tempSelectedId;
	String replicateFlag = MainetConstants.Common_Constant.NO;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);

		this.getModel().setDepartmentList(
				ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());

		UserSession.getCurrent().getEmployee().getTbLocationMas();
		List<SequenceConfigMasterDTO> configMasterDTOs = sequenceConfigMasterService.searchSequenceData(
				UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null, null);

		if (CollectionUtils.isNotEmpty(configMasterDTOs)) {
			List<SequenceConfigMasterDTO> masterDtoList = new ArrayList<>();

			configMasterDTOs.forEach(masterDto -> {

				masterDto.setDeptName(ApplicationContextProvider.getApplicationContext()
						.getBean(DepartmentService.class).fetchDepartmentDescById(masterDto.getDeptId()));

				Long orgId = masterDto.getOrgId();
				if (masterDto.getSeqType() != null) {
					LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(masterDto.getSeqType(),
							orgId, MainetConstants.SEQ_PREFIXES.SQT);

					masterDto.setSeqTypeName(lookUp.getLookUpDesc());
				}

				if (masterDto.getSeqName() != null) {
					LookUp lookUp2 = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(masterDto.getSeqName(),
							orgId, MainetConstants.SEQ_PREFIXES.SQN);

					masterDto.setSeqtbName(lookUp2.getLookUpCode());
				}

				LookUp lookUp1 = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(masterDto.getCatId(), orgId,
						MainetConstants.SEQ_PREFIXES.SEC);

				masterDto.setCatName(lookUp1.getLookUpDesc());

				masterDtoList.add(masterDto);
			});

			this.getModel().setConfigMasterDTOs(masterDtoList);
		}

		/* This flag is used in Model class */
		this.getModel().getConfigMasterDTO().setEditFlag("S");
		this.getModel().setCommonHelpDocs("SequenceConfigrationMaster.html");
		return index();
	}

	@RequestMapping(params = "addSequenceMaster", method = RequestMethod.POST)
	public ModelAndView addSequenceMaster(HttpServletRequest request) {

		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);

		this.getModel().setDepartmentList(
				ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());

		return new ModelAndView("SequenceConfiguration/form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView sequenceMasterSearch(final HttpServletRequest request, @RequestParam Long deptId,
			@RequestParam Long catId, @RequestParam Long seqName, @RequestParam String status) {

		sessionCleanup(request);

		seqName = seqName.equals("0") || seqName.equals("0") ? null : seqName;
		status = status.isEmpty() || status.equals("0") ? null : status;

		List<SequenceConfigMasterDTO> configMasterDTOs = sequenceConfigMasterService.searchSequenceData(
				UserSession.getCurrent().getOrganisation().getOrgid(), seqName, deptId, null, catId, status);

		this.getModel().setDepartmentList(
				ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());

		if (CollectionUtils.isNotEmpty(configMasterDTOs)) {
			List<SequenceConfigMasterDTO> masterDtoList = new ArrayList<>();

			configMasterDTOs.forEach(masterDto -> {

				if (masterDto.getDeptId() != null) {
					masterDto.setDeptName(ApplicationContextProvider.getApplicationContext()
							.getBean(DepartmentService.class).fetchDepartmentDescById(masterDto.getDeptId()));
				}
				Long orgId = masterDto.getOrgId();

				if (masterDto.getSeqType() != null) {
					LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(masterDto.getSeqType(),
							orgId, MainetConstants.SEQ_PREFIXES.SQT);
					masterDto.setSeqTypeName(lookUp.getLookUpDesc());

				}
				if (masterDto.getCatId() != null) {
					LookUp lookUp1 = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(masterDto.getCatId(),
							orgId, MainetConstants.SEQ_PREFIXES.SEC);

					masterDto.setCatName(lookUp1.getLookUpDesc());
				}

				if (masterDto.getSeqName() != null) {
					LookUp lookUp2 = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(masterDto.getSeqName(),
							orgId, MainetConstants.SEQ_PREFIXES.SQN);

					masterDto.setSeqtbName(lookUp2.getLookUpCode());
				}

				masterDtoList.add(masterDto);
			});

			this.getModel().setConfigMasterDTOs(masterDtoList);
		}
		SequenceConfigMasterDTO configMasterDTO = new SequenceConfigMasterDTO();
		configMasterDTO.setDeptId(deptId);
		configMasterDTO.setSeqName(seqName);
		configMasterDTO.setCatId(catId);
		configMasterDTO.setSeqStatus(status);
		this.getModel().setConfigMasterDTO(configMasterDTO);
		return new ModelAndView("SequenceConfiguration/search", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "viewSequenceMaster", method = RequestMethod.POST)
	public ModelAndView viewSequenceMaster(final HttpServletRequest request, @RequestParam Long seqConfigId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);

		this.getModel().setDepartmentList(
				ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		SequenceConfigMasterDTO configMasterDTO = sequenceConfigMasterService.searchSequenceById(seqConfigId, orgId);

		this.getModel().setConfigMasterDTO(configMasterDTO);

		return new ModelAndView("SequenceConfiguration/form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "editSequenceMaster", method = RequestMethod.POST)
	public ModelAndView editSequenceMaster(final HttpServletRequest request, @RequestParam Long seqConfigId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_EDIT);

		this.getModel().setDepartmentList(
				ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		SequenceConfigMasterDTO configMasterDTO = sequenceConfigMasterService.searchSequenceById(seqConfigId, orgId);

		this.getModel().setConfigMasterDTO(configMasterDTO);
		this.getModel().getConfigMasterDTO().setConfigDetDTOs(configMasterDTO.getConfigDetDTOs());

		/* Flag used in model class */
		this.getModel().getConfigMasterDTO().setEditFlag("E");
		return new ModelAndView("SequenceConfiguration/form", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "searchPrefixData", method = RequestMethod.POST)
	public @ResponseBody List<TbComparamMas> searchPrefixData(
			@RequestParam("department") final String departmentShortCode) {
		List<TbComparamMas> prefixList = new ArrayList<>();

		if (!departmentShortCode.isEmpty()) {
			prefixList = tbComparamMasService.findAllByDepartment(departmentShortCode, "");
		}
		return prefixList;
	}

	@RequestMapping(params = "searchPrefixDataById", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> searchPrefixDataById(@RequestParam("prefix") final String prefix,
			@RequestParam("type") final String type) {

		List<LookUp> lookupList = new ArrayList<>();

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = iOrganisationService.getOrganisationById(orgId);

		if (type.equals(MainetConstants.FlagN)) {
			lookupList = CommonMasterUtility.getLookUps(prefix, org);
		}

		else if (type.equals(MainetConstants.FlagH)) {
			lookupList = CommonMasterUtility.getListLookup(prefix, org);

		}

		return lookupList;
	}

}
