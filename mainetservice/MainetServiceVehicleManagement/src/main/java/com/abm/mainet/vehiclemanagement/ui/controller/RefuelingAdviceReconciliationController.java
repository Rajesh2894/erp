package com.abm.mainet.vehiclemanagement.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpFuelDetailsDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuelReconciationDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuelReconciationDetDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuellingDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuellingDetailsDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IPumpMasterService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleFuelReconciationService;
import com.abm.mainet.vehiclemanagement.service.IVehicleFuellingService;
import com.abm.mainet.vehiclemanagement.ui.model.RefuelingAdviceReconciliationModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/fuellingAdvice.html")
public class RefuelingAdviceReconciliationController extends AbstractFormController<RefuelingAdviceReconciliationModel> {

    /**
     * IVehicleFuel Reconciation Service
     */
    @Autowired
    private IVehicleFuelReconciationService vehicleFuelReconciationService;

    /**
     * The IVehicleMaster Service
     */
    @Autowired
    private IGenVehicleMasterService vehicleMasterService;

    /**
     * The IPumpMaster Service
     */
    @Autowired
    private IPumpMasterService pumpMasterService;

    @Autowired
    private TbAcVendormasterService vendorService;

    /**
     * The IVehicleFuelling Service
     */
    @Autowired
    private IVehicleFuellingService vehicleFuellingService;

    /**
     * The IFileUpload Service
     */
    @Autowired
    private IFileUploadService fileUpload;

    /**
     * The IAttachDocs Service
     */
    @Autowired
    private IAttachDocsService attachDocsService;

    /**
     * The SecondaryheadMaster Service
     */
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

	@Autowired
	private ISLRMEmployeeMasterService employeeMasterService ;
    
    
    /**
     * @return
     */
    public List<PumpMasterDTO> getPumpMasterList() {
        List<PumpMasterDTO> pumpList = pumpMasterService.serchPumpMasterByPumpType(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> pumpMap = pumpList.stream()
                .collect(Collectors.toMap(PumpMasterDTO::getPuId, PumpMasterDTO::getPuPumpname));
        // SET PUMP NAME USING PU_ID
        if (CollectionUtils.isNotEmpty(this.getModel().getVehicleFuelReconcilationList())) {
            this.getModel().getVehicleFuelReconcilationList().forEach(master -> {
                master.setPuPumpname(pumpMap.get(master.getPuId()));
            });
        }
        return pumpList;
    }

    /**
     * @return
     */
    private List<GenVehicleMasterDTO> getVehicleMasterList() {
        List<GenVehicleMasterDTO> vehicleMasterList = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,null,null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> locationMap = vehicleMasterList.stream()
                .collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
        // SET VEHEICLE NO USING VE_ID
        if (CollectionUtils.isNotEmpty(this.getModel().getVehicleFuelReconciationDetDTOList())) {
            this.getModel().getVehicleFuelReconciationDetDTOList().forEach(master -> {
                master.setVeNo(locationMap.get(master.getVeId()));
            });
        }
        return vehicleMasterList;
    }

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("RefuelingAdvice.html");
        this.getModel().setVehicleFuelReconcilationList(
                vehicleFuelReconciationService.search(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        httpServletRequest.setAttribute("pumps", getPumpMasterList());
        return index();
    }

    /**
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddRefuelingAdviceReconcilation", method = RequestMethod.POST)
    public ModelAndView addRefuelingAdviceReconcilation(final HttpServletRequest request) {
        fileUpload.sessionCleanUpForFileUpload();
        sessionCleanup(request);
        this.getModel().getVehicleFuelReconciationDTO().setPuId(null);
        this.getModel().getVehicleFuelReconciationDTO().setInrecFromdt(null);
        this.getModel().getVehicleFuelReconciationDTO().setInrecTodt(null);
        ModelAndView mv = new ModelAndView("AddRefuelAdviceReconcilation/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        return mv;
    }

    /**
     * @param request
     * @param inrecId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editRefuelingAdviceReconcilation", method = RequestMethod.POST)
    public ModelAndView editRefuelingAdviceReconcilation(final HttpServletRequest request, @RequestParam Long inrecId) {
        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        VehicleFuelReconciationDTO dto = vehicleFuelReconciationService.getById(inrecId);
        this.getModel().setVehicleFuelReconciationDTO(dto);
        List<VehicleFuellingDTO> vehicleFuellingList = vehicleFuellingService.getVehicleFuellingByAdviceDateAndPumpId(
                dto.getPuId(), dto.getInrecFromdt(),
                dto.getInrecTodt(), UserSession.getCurrent().getOrganisation().getOrgid(), false);
        ModelAndView mv = new ModelAndView("editRefuelAdviceReconcilation/Form", MainetConstants.FORM_NAME, this.getModel());
        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = setUpFuelingReconDet(dto, vehicleFuellingList);
        this.getModel().getVehicleFuelReconciationDTO().setTbSwVehiclefuelInrecDets(vehicleFuelReconcilationList);
        mv.addObject("vehicleFuelReconcilationList",
                this.getModel().getVehicleFuelReconciationDTO().getTbSwVehiclefuelInrecDets());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        // mv.addObject("sum", this.getTotalCostOfFuel());
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                "ADV_RECON/" + inrecId);
        this.getModel().setAttachDocsList(attachDocs);
        return mv;
    }

    /**
     * @param request
     * @param inrecId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteRefuelingAdviceReconcilation", method = RequestMethod.POST)
    public ModelAndView deleteRefuelingAdviceReconcilation(final HttpServletRequest request, @RequestParam Long inrecId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        vehicleFuelReconciationService.delete(inrecId, emp.getEmpId(), emp.getEmppiservername());
        sessionCleanup(request);
        this.getModel().setVehicleFuelReconcilationList(
                vehicleFuelReconciationService.search(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("searchRefuelAdviceReconcilation",
                MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        return mv;
    }

    /**
     * @param request
     * @param puId
     * @param fromDate
     * @param toDate
     * @param orgid
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchRefuelingAdviceReconcilation", method = RequestMethod.POST)
    public ModelAndView searchRefuelingAdviceReconcilation(final HttpServletRequest request,
            @RequestParam(required = false) Long puId, @RequestParam(required = false) Date fromDate,
            @RequestParam(required = false) Date toDate,
            Long orgid) {
        sessionCleanup(request);
        this.getModel().getVehicleFuelReconciationDTO().setPuId(puId);
        this.getModel().getVehicleFuelReconciationDTO().setInrecFromdt(fromDate);
        this.getModel().getVehicleFuelReconciationDTO().setInrecTodt(toDate);
        this.getModel().setVehicleFuelReconcilationList(vehicleFuelReconciationService.search(puId, fromDate, toDate,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("searchRefuelAdviceReconcilation",
                MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        return mv;
    }

    /**
     * @param request
     * @param puId
     * @param fromDate
     * @param toDate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchVehicleFuelingReconcilationDetails", method = RequestMethod.POST)
    public ModelAndView searchVehicleFuelingReconcilationDetails(final HttpServletRequest request,
            @RequestParam Long puId, @RequestParam(required = false) Date fromDate,
            @RequestParam(required = false) Date toDate) {
        sessionCleanup(request);
        Long taxMasLookUpIdded = null;
        final Organisation org = UserSession.getCurrent().getOrganisation();
        List<LookUp> taxDedMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, org);
   /*     for (LookUp lookUp : taxDedMaslookUpList) {
            if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
                taxMasLookUpIdded = lookUp.getLookUpId();
            }
        }
        if (taxMasLookUpIdded != null) {
            Map<Long, String> expenditureHead1 = secondaryheadMasterService.getTaxMasBillDeductionAcHeadDescAllDetails(
                    org.getOrgid(),
                    taxMasLookUpIdded);
            if (expenditureHead1 != null) {
            VehicleFuelReconciationDTO vehicleFuelReconciationDTO = null;
            List<VehicleFuelReconciationDTO> listvehicleFuelReconciationDTO = new ArrayList<>();
            for (Map.Entry<Long, String> entry : expenditureHead1.entrySet()) {
                vehicleFuelReconciationDTO = new VehicleFuelReconciationDTO();
                vehicleFuelReconciationDTO.setDedAcHeadId(entry.getKey());
                vehicleFuelReconciationDTO.setDedAcHead(entry.getValue());
                listvehicleFuelReconciationDTO.add(vehicleFuelReconciationDTO);

            }
            this.getModel().setVehicleFuelReconcilationList(listvehicleFuelReconciationDTO);
            }
        }
        this.getModel().setValueTypeList(CommonMasterUtility.getLookUps("VTY", UserSession.getCurrent().getOrganisation()));
        Long vendorId = vehicleFuelReconciationService.searchVendorIdByPumpId(puId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().getVehicleFuelReconciationDTO().setVendorId(vendorId);
        Long taxMasLookUpId = null;
        List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 2, org);
        for (LookUp lookUp : taxMaslookUpList) {
            if (lookUp.getLookUpCode().equals("FCG")) {
                taxMasLookUpId = lookUp.getLookUpId();
            }
        }
        if (taxMasLookUpId != null) {
            Map<Long, String> expenditureHead = secondaryheadMasterService.getTaxMasBillPaymentsAcHeadAllDetails(
                    org.getOrgid(),
                    taxMasLookUpId);
            for (Map.Entry<Long, String> entry : expenditureHead.entrySet()) {
                this.getModel().getVehicleFuelReconciationDTO().setExpenditureId(entry.getKey());
                this.getModel().getVehicleFuelReconciationDTO().setExpenditureHead(entry.getValue());
            }
        }*/

        List<VehicleFuellingDTO> vehicleFuellingList = vehicleFuellingService.getVehicleFuellingByAdviceDateAndPumpId(puId,
                fromDate, toDate, UserSession.getCurrent().getOrganisation().getOrgid(), true);
        this.getModel().getVehicleFuelReconciationDTO().setPuId(puId);
        this.getModel().getVehicleFuelReconciationDTO().setInrecFromdt(fromDate);
        this.getModel().getVehicleFuelReconciationDTO().setInrecTodt(toDate);
        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = loadVehicleFuelReconcilationList(vehicleFuellingList);
        this.getModel().setVehicleFuelReconciationDetDTOList(vehicleFuelReconcilationList);
        ModelAndView mv = new ModelAndView("searchVehicleFuelReconcilationDetails", MainetConstants.FORM_NAME,
                this.getModel());
        mv.addObject("vehicleFuelReconcilationList", vehicleFuelReconcilationList);
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        return mv;
    }

    /**
     * @param request
     * @param inrecId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewRefuelingAdviceReconcilation", method = RequestMethod.POST)
    public ModelAndView viewRefuelingAdviceReconcilation(final HttpServletRequest request, @RequestParam Long inrecId) {
        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        VehicleFuelReconciationDTO dto = vehicleFuelReconciationService.getById(inrecId);
        this.getModel().setVehicleFuelReconciationDTO(dto);
        List<VehicleFuellingDTO> vehicleFuellingList = vehicleFuellingService.getVehicleFuellingByAdviceDateAndPumpId(
                dto.getPuId(), dto.getInrecFromdt(), dto.getInrecTodt(), UserSession.getCurrent().getOrganisation().getOrgid(),
                false);
        ModelAndView mv = new ModelAndView("viewRefuelAdviceReconcilation/Form", MainetConstants.FORM_NAME, this.getModel());
        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = setUpFuelingReconDet(dto, vehicleFuellingList);
        this.getModel().getVehicleFuelReconciationDTO().setTbSwVehiclefuelInrecDets(vehicleFuelReconcilationList);
        mv.addObject("vehicleFuelReconcilationList",
                this.getModel().getVehicleFuelReconciationDTO().getTbSwVehiclefuelInrecDets());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                "ADV_RECON/" + inrecId);
        this.getModel().setAttachDocsList(attachDocs);
        return mv;
    }

    /**
     * @param model
     * @param request
     * @param inrecId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "formForPrint", method = RequestMethod.POST)
    public ModelAndView formForPrint(final Model model, final HttpServletRequest request,
            @RequestParam final Long inrecId) {

    	VehicleFuelReconciationDTO vehicleFuelReconciationDTO = vehicleFuelReconciationService.getById(inrecId);
        Map<Long, String> vendorMap = this.getModel().getVendorList().stream()
                .collect(Collectors.toMap(TbAcVendormaster::getVmVendorid, TbAcVendormaster::getVmVendorname));
        PumpMasterDTO pumpMasterDTO = pumpMasterService.getPumpByPumpId(vehicleFuelReconciationDTO.getPuId());
        vehicleFuelReconciationDTO.setPuPumpname(pumpMasterDTO.getPuPumpname());
        Long vendorId = pumpMasterDTO.getVendorId();
        vehicleFuelReconciationDTO.setVendorId(vendorId);
        vehicleFuelReconciationDTO.setPuVendorname(vendorMap.get(vendorId));
        
        List<Long> vefuelingIdList = new ArrayList<>();
        vehicleFuelReconciationDTO.getTbSwVehiclefuelInrecDets().forEach(detDto->{
        	vefuelingIdList.add(detDto.getVefId());
        });
        
        List<VehicleFuellingDTO> vehicleFuellingList = vehicleFuellingService.getVehicleFuellingByVefIdsAndPumpId(vefuelingIdList,
        		vehicleFuelReconciationDTO.getPuId(), UserSession.getCurrent().getOrganisation().getOrgid());
        
		/*List<VehicleFuellingDTO> vehicleFuellingList = vehicleFuellingService.getVehicleFuellingByAdviceDateAndPumpId(
		        vehicleFuelReconciationDTO.getPuId(), vehicleFuelReconciationDTO.getInrecFromdt(),
		        vehicleFuelReconciationDTO.getInrecTodt(), UserSession.getCurrent().getOrganisation().getOrgid(), false);*/        
        
        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = setUpFuelingReconDetPrint(vehicleFuelReconciationDTO,
        		vehicleFuellingList, pumpMasterDTO);

        this.getModel().getVehicleFuelReconciationDTO().setTbSwVehiclefuelInrecDets(vehicleFuelReconcilationList);
        ModelAndView mv = new ModelAndView("VehicleAdviceReconcilPrint/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicleFuelReconciationDTO", vehicleFuelReconciationDTO);
        mv.addObject("vehicleFuelReconcilationList", this.getModel().getVehicleFuelReconciationDTO().getTbSwVehiclefuelInrecDets());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        return mv;
    }

    /**
     * @param dto
     * @param vehicleFuellingList
     * @return
     */
    private List<VehicleFuelReconciationDetDTO> setUpFuelingReconDet(VehicleFuelReconciationDTO dto,
            List<VehicleFuellingDTO> vehicleFuellingList) {
        Map<Long, VehicleFuelReconciationDetDTO> statusMap = dto.getTbSwVehiclefuelInrecDets().stream().collect(
                Collectors.toMap(VehicleFuelReconciationDetDTO::getVefId, det -> det));
        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = loadVehicleFuelReconcilationList(vehicleFuellingList);
        vehicleFuelReconcilationList.forEach(reconc -> {
            if (null != reconc.getVefId() && null != statusMap.get(reconc.getVefId())) {
                reconc.setInrecdId(statusMap.get(reconc.getVefId()).getInrecdId());
                reconc.setInrecdActive(statusMap.get(reconc.getVefId()).getInrecdActive());
                reconc.setOrgid(statusMap.get(reconc.getVefId()).getOrgid());
                reconc.setCreatedBy(statusMap.get(reconc.getVefId()).getCreatedBy());
                reconc.setCreatedDate(statusMap.get(reconc.getVefId()).getCreatedDate());
                reconc.setLgIpMac(statusMap.get(reconc.getVefId()).getLgIpMac());

            }
        });
        this.getModel().setVehicleFuelReconciationDetDTOList(vehicleFuelReconcilationList);
        return vehicleFuelReconcilationList;
    }

    /**
     * @param dto
     * @param vehicleFuellingList
     * @return
     */
    private List<VehicleFuelReconciationDetDTO> setUpFuelingReconDetPrint(VehicleFuelReconciationDTO dto,
            List<VehicleFuellingDTO> vehicleFuellingList, PumpMasterDTO pumpMasterDTO) {
        Map<Long, VehicleFuelReconciationDetDTO> statusMap = dto.getTbSwVehiclefuelInrecDets().stream().collect(
                Collectors.toMap(VehicleFuelReconciationDetDTO::getVefId, det -> det));
        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = loadVehicleFuelReconcilationPrintList(
                vehicleFuellingList, pumpMasterDTO);
        vehicleFuelReconcilationList.forEach(reconc -> {
            if (null != reconc.getVefId() && null != statusMap.get(reconc.getVefId())) {
                reconc.setInrecdId(statusMap.get(reconc.getVefId()).getInrecdId());
                reconc.setInrecdActive(statusMap.get(reconc.getVefId()).getInrecdActive());
                reconc.setOrgid(statusMap.get(reconc.getVefId()).getOrgid());
                reconc.setCreatedBy(statusMap.get(reconc.getVefId()).getCreatedBy());
                reconc.setCreatedDate(statusMap.get(reconc.getVefId()).getCreatedDate());
                reconc.setLgIpMac(statusMap.get(reconc.getVefId()).getLgIpMac());

            }
        });
        this.getModel().setVehicleFuelReconciationDetDTOList(vehicleFuelReconcilationList);
        return vehicleFuelReconcilationList;
    }

    /**
     * @param vehicleFuellingList
     * @return
     */
    public List<VehicleFuelReconciationDetDTO> loadVehicleFuelReconcilationList(List<VehicleFuellingDTO> vehicleFuellingList) {
        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = new ArrayList<>();
        for (VehicleFuellingDTO vehicleFuellingDTO : vehicleFuellingList) {
            VehicleFuelReconciationDetDTO detailDto = new VehicleFuelReconciationDetDTO();
            detailDto.setVeVetype(vehicleFuellingDTO.getVeVetype());
            detailDto.setVeId(vehicleFuellingDTO.getVeId());
            detailDto.setAdviceDate(vehicleFuellingDTO.getVefDmdate());
            detailDto.setAdviceNumber(vehicleFuellingDTO.getVefDmno());
            detailDto.setVefRmamount(vehicleFuellingDTO.getVefRmamount());
            detailDto.setVefId(vehicleFuellingDTO.getVefId());
            detailDto.setDriverName(vehicleFuellingDTO.getDriverName());
            // detailDto.setVefdCost(vehicleFuellingDTO.get);
            // detailDto.setVefdQuantity(vefdQuantity);
            vehicleFuelReconcilationList.add(detailDto);
        }
        return vehicleFuelReconcilationList;
    }

    /**
     * @param vehicleFuellingList
     * @return
     */
    public List<VehicleFuelReconciationDetDTO> loadVehicleFuelReconcilationPrintList(
            List<VehicleFuellingDTO> vehicleFuellingList, PumpMasterDTO pumpMasterDTO) {

        List<VehicleFuelReconciationDetDTO> vehicleFuelReconcilationList = new ArrayList<>();
        for (VehicleFuellingDTO vehicleFuellingDTO : vehicleFuellingList) {
            List<VehicleFuellingDetailsDTO> vehicleFuellingDetailsDTOs = vehicleFuellingDTO.getTbSwVehiclefuelDets();
            for (VehicleFuellingDetailsDTO vehicleFuellingDetailsDTO : vehicleFuellingDetailsDTOs) {
            	if( vehicleFuellingDetailsDTO.getVefdCost() != null ) {
            		  BigDecimal SumOfAmout = new BigDecimal(0.00);
                      VehicleFuelReconciationDetDTO detailDto = new VehicleFuelReconciationDetDTO();
                      detailDto.setVeVetype(vehicleFuellingDTO.getVeVetype());
                      detailDto.setVeId(vehicleFuellingDTO.getVeId());
                      detailDto.setAdviceDate(vehicleFuellingDTO.getVefDmdate());
                      detailDto.setAdviceNumber(vehicleFuellingDTO.getVefDmno());
                      detailDto.setVefRmamount(vehicleFuellingDTO.getVefRmamount());
                      detailDto.setVefId(vehicleFuellingDTO.getVefId());
                      detailDto.setPfuId(vehicleFuellingDetailsDTO.getPfuId());                      
                      detailDto.setDriverName(employeeMasterService.getDriverFullNameById(Long.valueOf(vehicleFuellingDTO.getDriverName())));

                      for (PumpFuelDetailsDTO pumpMasterDTO1 : pumpMasterDTO.getTbSwPumpFuldets()) {
                          if (pumpMasterDTO1.getPfuId() == vehicleFuellingDetailsDTO.getPfuId()) {
                              detailDto.setPuFuid(pumpMasterDTO1.getPuFuid());
                          }
                      }

      				if (vehicleFuellingDetailsDTO.getVefdCost() != null) {
      				    detailDto.setVefdCost(
      				            new BigDecimal(vehicleFuellingDetailsDTO.getVefdCost().toString()).setScale(2,
      				                    RoundingMode.HALF_EVEN));
      				}
      				if (vehicleFuellingDetailsDTO.getVefdQuantity() != null) {
      				    detailDto.setVefdQuantity(new BigDecimal(vehicleFuellingDetailsDTO.getVefdQuantity().toString()).setScale(2,
      				            RoundingMode.HALF_EVEN));
      				}
      				if (vehicleFuellingDetailsDTO.getVefdQuantity() != null) {
      				    SumOfAmout = SumOfAmout.add(new BigDecimal(vehicleFuellingDetailsDTO.getVefdCost().toString())
      				            .multiply(new BigDecimal(vehicleFuellingDetailsDTO.getVefdQuantity().toString())));
      				
      				    detailDto.setSumOfAmount(new BigDecimal(SumOfAmout.toString()).setScale(2,
      				            RoundingMode.HALF_EVEN));
      				} else if (vehicleFuellingDetailsDTO.getVefdCost() != null) {
      				    detailDto.setSumOfAmount(new BigDecimal(vehicleFuellingDetailsDTO.getVefdCost().toString()).setScale(2,
      				            RoundingMode.HALF_EVEN));
      				}
                      vehicleFuelReconcilationList.add(detailDto);
            	}
            	
            }
        }
        return vehicleFuelReconcilationList;
    }

    @InitBinder
    public void loadVendor() {
        final Long vendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        this.getModel().setVendorList(
                vendorService.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
    }
    
    
    @ResponseBody
    @RequestMapping(params = "duplicateChecker", method = RequestMethod.POST)
    public Boolean duplicateChecker(final HttpServletRequest request, @RequestParam(required = false) Long inrecdInvno ) {
    	return vehicleFuelReconciationService.isExistingInvoice(inrecdInvno, UserSession.getCurrent().getOrganisation().getOrgid());
    }
    
    

}
