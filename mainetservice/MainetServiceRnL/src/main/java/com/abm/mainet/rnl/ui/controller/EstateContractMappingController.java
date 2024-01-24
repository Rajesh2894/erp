package com.abm.mainet.rnl.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractInstalmentDetailDTO;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.ui.model.ContractAgreementModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.service.IEstateContractMappingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.service.IEstateService;
import com.abm.mainet.rnl.service.IRLBILLMasterService;
import com.abm.mainet.rnl.ui.model.EstateContractMappingModel;

@Controller
@RequestMapping("/EstateContractMapping.html")
public class EstateContractMappingController extends AbstractFormController<EstateContractMappingModel> {

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private IContractAgreementService iContractAgreementService;

    @Autowired
    private IEstateService iEstateService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @Autowired
    private IEstateContractMappingService iEstateContractMappingService;
    
    @Autowired
    private IRLBILLMasterService iRLBILLMasterService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public String index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A,
                MainetConstants.RnLCommon.RentLease);
        final List<ContractMappingDTO> list = iEstateContractMappingService.findContractDeptWise(orgId, tbDepartment,
                MainetConstants.CommonConstants.E);
        if(list !=null ) {
        uiModel.addAttribute(MainetConstants.EstateContract.CONTRACT_lIST, list);
        }
        getModel().setTbDepartment(tbDepartment);
        return MainetConstants.EstateContract.ESTATE_CONTRACT_MAP_HOME;
    }

    // @RequestParam(name="contractNo",required=false) Long contractNo,@RequestParam(name="date",required=false) Date contractDate
    @ResponseBody
    @RequestMapping(params = "filterListData", method = RequestMethod.POST)
    public List<ContractMappingDTO> getFilterdList(final HttpServletRequest httpServletRequest,
            @RequestParam(name = "contractNo", required = false) String contractNo,
            @RequestParam(name = "contDate", required = false) Date contDate) {
        final List<ContractMappingDTO> list = iEstateContractMappingService.findContract(
                UserSession.getCurrent().getOrganisation().getOrgid(), contractNo,
                contDate, getModel().getTbDepartment());
        return list;
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbEstateMaster
     */
    private void populateModel(final Long contId, final EstateContractMappingModel estateContractMappingModel,
            final String modeType) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<ContractMappingDTO> contractList = null;
        if (contId == null) {
            estateContractMappingModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
            contractList = iEstateContractMappingService.findContractDeptWise(orgId, estateContractMappingModel.getTbDepartment(),
                    MainetConstants.CommonConstants.N);
        } else {
            estateContractMappingModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
            contractList = iEstateContractMappingService.findContractsByContractId(orgId, contId,
                    estateContractMappingModel.getTbDepartment());
            final EstateContMappingDTO estateContMappingDTO = iEstateContractMappingService.findByContractId(contId);
            getModel().setEstateContMappingDTO(estateContMappingDTO);
        }
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)
        		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	  List<ContractMappingDTO> filterContracts = new ArrayList<ContractMappingDTO>();
              filterContracts = contractList.stream()
                      .filter(contract -> contract.getContractNo() != null && contract.getFromDate() != null)
                      .collect(Collectors.toList());

              estateContractMappingModel.setContractList(filterContracts);
        }else {
        // D#90462/D#88167 filter the list with exist from date and to date between current date
        List<ContractMappingDTO> filterContracts = new ArrayList<ContractMappingDTO>();
        filterContracts = contractList.stream()
                .filter(contract -> contract.getContractNo() != null && contract.getFromDate() != null
                        && checkDate(Utility.stringToDate(contract.getFromDate()),
                                Utility.stringToDate(contract.getToDate()), new Date()))
                .collect(Collectors.toList());

        estateContractMappingModel.setContractList(filterContracts);
        }
    }

    /**
     * Shows a form page in order to create a new Property
     * @param model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "contId", required = false) final Long contId,
            @RequestParam(value = "type", required = false) final String modeType) {

        final EstateContractMappingModel estateContractMappingModel = getModel();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        populateModel(contId, estateContractMappingModel, modeType);
        final List<Object[]> list = iEstateService.findEstateRecordsForProperty(orgId);
        estateContractMappingModel.setEstateMasters(list);
        String viewName;
        if (contId == null) {
            viewName = MainetConstants.EstateContract.ESTATE_CONTRACT_MAPIN;
        } else {
            viewName = MainetConstants.EstateContract.CONTRACT_MAPVIEW;
        }
        return new ModelAndView(viewName, MainetConstants.FORM_NAME, estateContractMappingModel);
    }

    @ResponseBody
    @RequestMapping(params = "propList", method = RequestMethod.POST)
    public List<Object[]> getPropertyList(@RequestParam("esId") final Long esId, @RequestParam("contId") final Long contId) {
        List<Object[]> finalList = new ArrayList<>();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> list = iEstatePropertyService.findPropertiesForEstate(orgId, esId, PrefixConstants.CPD_VALUE_LEASE, PrefixConstants.CATEGORY_PREFIX_NAME);
        
        // Contract Details for fromDate and toDate
        ContractDetailDTO contractDetails = ApplicationContextProvider.getApplicationContext()
                .getBean(IContractAgreementService.class).getContractDetail(contId);
        Date fromDate1 = contractDetails.getContFromDate();
        Date toDate1 = contractDetails.getContToDate();
        Date currentDate = new Date();
        // below code is for filter the property list
        // D-#30154
        for (final Object[] obj : list) {
            long propId = (long) obj[0];
            char status;
            boolean present = false;
            // check this property already mapped with another contract within fromDate and toDate in tb_rl_est_contract_mapping
            List<EstateContMappingDTO> contractMaps = iEstateContractMappingService.findByPropertyId(propId);
            if (!contractMaps.isEmpty()) {
                for (EstateContMappingDTO contractMap : contractMaps) {
					/*ContractDetailDTO contractDet = ApplicationContextProvider.getApplicationContext()
					        .getBean(IContractAgreementService.class).getContractDetail(contractMap.getContractId());*/
                	ContractMastDTO contractDet = iContractAgreementService.findById(contractMap.getContractId(), orgId);
                    Date fromDate2 = contractDet.getContractDetailList().get(0).getContFromDate();
                    Date toDate2 = contractDet.getContractDetailList().get(0).getContToDate();
                    Long toPeriod = contractDet.getContractDetailList().get(0).getContToPeriod();
                    Long periodUnit = contractDet.getContractDetailList().get(0).getContToPeriodUnit();
                    status = MainetConstants.CommonConstants.CHAR_Y;
                    
                    if(toDate2.before(currentDate)) {
                    	status = MainetConstants.CommonConstants.CHAR_N;
                    	iEstateContractMappingService.updatePropertyMappingStatus(status, propId);
                    }
                    // D#77975 check date in between or not
                    /*
                     * if (!checkDate(fromDate1, toDate1, toDate1)) { } else if (!checkDate(fromDate1, toDate1, toDate2)) { } else
                     * if (!checkDate(fromDate2, toDate2, toDate1)) { } else if (!checkDate(fromDate2, toDate2, toDate2)) { } else
                     * { finalList.add(obj); }
              finalList       */
					/* if (checkDate(fromDate2, toDate2, fromDate1)) {
					    present = true;
					    break;
					} else if (checkDate(fromDate2, toDate2, toDate1)) {
					    present = true;
					    break;
					}*/
                    
                    if(toDate2.before(currentDate) && status != 'Y') {
                    	finalList.add(obj);
                    }
                 
                }
                
			}  else {
				  finalList.add(obj);
			}
        }
        getModel().setProps(finalList);
        return finalList;
    }

    public Boolean checkDate(Date startDate, Date endDate, Date checkDate) {
        return startDate.compareTo(checkDate) * checkDate.compareTo(endDate) >= 0;
    }

    @ResponseBody
    @RequestMapping(params = "propDetails", method = RequestMethod.POST)
    public EstatePropResponseDTO getPropertyDetails(@RequestParam("propId") final Long propId) {
        final EstatePropMaster data = iEstatePropertyService.findByPropDetailsById(propId);
        final EstatePropResponseDTO estatePropResponseDTO = new EstatePropResponseDTO();
        estatePropResponseDTO.setPropId(data.getPropId());
        estatePropResponseDTO.setPropertyNo(data.getCode());
        Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        // property floor is not mandatory
        if (data.getFloor() != null) {
            estatePropResponseDTO
                    .setFloor(CommonMasterUtility.getNonHierarchicalLookUpObject(data.getFloor(), organisation).getLookUpDesc());
        }
        // D#77620
        estatePropResponseDTO.setUsage(CommonMasterUtility.getHierarchicalLookUp(data.getUsage(), organisation)
                .getLookUpDesc());
        estatePropResponseDTO.setUnit(data.getUnitNo() != null ? data.getUnitNo().toString() : "");
        estatePropResponseDTO.setTotalArea(data.getTotalArea().toString());
        return estatePropResponseDTO;
    }

    @RequestMapping(params = "propDetailsForm", method = RequestMethod.POST)
    public ModelAndView getPropertyDetailsForm() {
        final EstateContractMappingModel estateContractMappingModel = getModel();
        return new ModelAndView(MainetConstants.EstateContract.ESTATE_CONT_MAP, MainetConstants.FORM_NAME,
                estateContractMappingModel);
    }

    @RequestMapping(params = "printContract", method = RequestMethod.POST)
    public String printContractForm(final Model uiModel, @RequestParam("contId") final Long contId) {
        uiModel.addAttribute(MainetConstants.CommonConstants.COMMAND, iEstateContractMappingService
                .findContractPrintValues(UserSession.getCurrent().getOrganisation().getOrgid(), contId));
        return MainetConstants.EstateContract.ESTATE_CONT_PRINT;
    }

    @ResponseBody
    @RequestMapping(params = "contractCount", method = RequestMethod.POST)
    public boolean countNotExistContract() {
        return iEstateContractMappingService.getCountContractDeptWiseNotExist(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                getModel().getTbDepartment().getDpDeptid());
    }

    @RequestMapping(method = RequestMethod.POST, params = "IsPropAssociated")
    public @ResponseBody boolean hasPropertyInContractOrBooking(@RequestParam final Long propId) {
        final boolean count = iEstateContractMappingService.findCountForProperty(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                propId);
        return count;
    }
    
    @RequestMapping(params = "createAdjustment", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView createAdjustment(final HttpServletRequest request,
			@RequestParam(value = "contId") final Long contId) {
		bindModel(request);
		EstateContractMappingModel model = getModel();
		model.setContractMastDTO(new ContractMastDTO());
		model.getContractMastDTO().setContId(contId);
		final List<ContractInstalmentDetailDTO> AdjustmentDetailList = iRLBILLMasterService
                .findAdjRecords(contId, MainetConstants.RnLCommon.Y_FLAG,"A");
		model.getContractMastDTO().setContractInstalmentDetailList(AdjustmentDetailList);
		return new ModelAndView("AdjustmentEntryForm", MainetConstants.FORM_NAME,
				model);
	}
    
    @RequestMapping(params = "saveAdjustment", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView saveAdjustment(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		EstateContractMappingModel model = this.getModel();
		if (model.saveAdjustment()) {
			return jsonResult(
					JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("Save SuccessFully")));
		}
		return customDefaultMyResult("AdjustmentEntryForm");
	}
}
