package com.abm.mainet.water.rest.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.dto.MeterReadingMonthDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.WaterBillGenErrorDTO;
import com.abm.mainet.water.dto.WaterBillPrintingDTO;
import com.abm.mainet.water.repository.TbWtExcessAmtJpaRepository;
import com.abm.mainet.water.rest.dto.WaterBillRequestDTO;
import com.abm.mainet.water.rest.dto.WaterBillResponseDTO;
import com.abm.mainet.water.rest.dto.WaterMeterReadingRequestDTO;
import com.abm.mainet.water.rest.dto.WaterMeterReadingResponseDTO;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbMrdataService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.service.TbWtBillScheduleService;
import com.abm.mainet.water.service.WaterCommonService;

/**
 * @author Rahul.Yadav
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/WaterSpotBillingController")
public class WaterSpotBillingRestController {

    Logger logger = Logger.getLogger(WaterSpotBillingRestController.class);

    @Resource
    private TbMrdataService tbmeterService;

    @Autowired
    private TbWtBillMasService tbWtBillMasService;

    @Autowired
    private BillMasterService billGenerationService;

    @Resource
    private BillMasterCommonService billMasterCommonService;

    @Resource
    NewWaterConnectionService newWaterConnectionService;

    @Autowired
    private TbWtBillScheduleService tbWtBillScheduleService;

    @Resource
    private TbTaxMasService tbTaxMasService;

    @Resource
    private TbWtExcessAmtJpaRepository tbWtExcessAmtJpaRepository;

    @Resource
    private WaterCommonService waterCommonService;

    @RequestMapping(value = "/getMeterDetailSearchData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getMeterDetailSearchData(
            @RequestBody final WaterMeterReadingRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        final WaterMeterReadingResponseDTO response = new WaterMeterReadingResponseDTO();
        try {
            final UserSession session = new UserSession();
            List<MeterReadingDTO> result = null;
            String dependsOnType = null;

            final TbCsmrInfoDTO waterEntity = waterCommonService.fetchConnectionDetailsByConnNo(requestDTO.getCsCcn(),
                    requestDTO.getOrgid(), MainetConstants.Common_Constant.ACTIVE_FLAG);
            if (waterEntity != null) {
                final Organisation organisation = new Organisation();
                organisation.setOrgid(requestDTO.getOrgid());
                final String meteredConn = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(waterEntity.getCsMeteredccn(), organisation).getLookUpCode();
                if (MainetConstants.NewWaterServiceConstants.METER.equals(meteredConn)) {
                    final List<TbWtBillSchedule> billSchedule = tbWtBillScheduleService
                            .getBillScheduleByFinYearId(Long
                                    .valueOf(session.getFinYearId()), requestDTO.getOrgid(),
                                    MainetConstants.NewWaterServiceConstants.METER);
                    final MeterReadingDTO entityDTO = new MeterReadingDTO();
                    entityDTO.setMeterType(MainetConstants.FlagS);
                    entityDTO.setCsCcn(requestDTO.getCsCcn());
                    entityDTO.setUserId(requestDTO.getUserId());
                    entityDTO.setOrgid(requestDTO.getOrgid());
                    entityDTO.setLangId(requestDTO.getLangId());
                    if ((billSchedule != null) && !billSchedule.isEmpty()) {
                        dependsOnType = billSchedule.get(0).getDependsOnType();
                    }
                    result = tbmeterService.findWaterRecords(entityDTO, session.getFinYearId(), billSchedule, dependsOnType,
                            null);
                    if ((result != null) && !result.isEmpty()) {
                        final MeterReadingDTO resultDTO = result.get(0);
                        BeanUtils.copyProperties(resultDTO, response);
                        response.setStatus(MainetConstants.FlagS);
                    } else {
                        response.setStatus(MainetConstants.MENU.F);
                    }
                } else if (MainetConstants.NewWaterServiceConstants.NON_METER.equals(meteredConn)) {
                    response.setCsIdn(waterEntity.getCsIdn());
                    response.setCsCcn(waterEntity.getCsCcn());
                    String name = null;
                    name += waterEntity.getCsName() + MainetConstants.WHITE_SPACE;
                    if (waterEntity.getCsMname() != null) {
                        name += waterEntity.getCsMname() + MainetConstants.WHITE_SPACE;
                    }
                    name += waterEntity.getCsLname();
                    response.setName(name);
                    response.setStatus(MainetConstants.FlagS);
                }
                response.setMeterType(meteredConn);
            } else {
                response.setStatus(MainetConstants.MENU.F);
            }
        } catch (final Exception e) {

            response.setStatus(MainetConstants.MENU.F);
            logger.error("Error while meter reading for spot billing : ", e);
        }
        return response;
    }

    @RequestMapping(value = "/saveMeterReadingData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveMeterReadingRecords(
            @RequestBody final WaterMeterReadingResponseDTO requestDTO,
            final HttpServletRequest httprequest, final BindingResult bindingResult) {
        final List<MeterReadingDTO> request = new ArrayList<>(0);
        final WaterMeterReadingRequestDTO response = new WaterMeterReadingRequestDTO();
        try {
            final MeterReadingDTO dto = new MeterReadingDTO();
            BeanUtils.copyProperties(requestDTO, dto);
            request.add(dto);
            final Organisation organisation = new Organisation();
            organisation.setOrgid(requestDTO.getOrgid());
            boolean result = false;
            int to = 0;
            for (final MeterReadingMonthDTO dtoMonth : requestDTO.getMonth()) {
                if (MainetConstants.Y_FLAG.equals(dtoMonth.getValueCheck())) {
                    to = dtoMonth.getTo();
                }
            }
            result = tbWtBillMasService.validateBillPresentOrNot(requestDTO.getMmMtnid(),
                    requestDTO.getOrgid(), to);
            if (!result) {
                final List<Long> csIdn = new ArrayList<>(0);
                csIdn.add(requestDTO.getCsIdn());
                final boolean status = tbmeterService.saveMeterReadingData(
                        request, requestDTO.getMrdMrdate(),
                        organisation, dto.getMonth(), MainetConstants.Y_FLAG, requestDTO.getUserId(), csIdn);
                if (status) {
                    response.setCsIdn(requestDTO.getCsIdn());
                    response.setCsCcn(requestDTO.getCsCcn());
                    response.setStatus(MainetConstants.FlagS);
                } else {
                    response.setStatus(MainetConstants.MENU.F);
                }
            } else {
                response.setStatus(MainetConstants.MENU.F);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            response.setStatus(MainetConstants.MENU.F);
            logger.error("Error while meter reading saving details for spot billing : ", e);
        }
        FileUploadUtility.getCurrent().getFileMap().clear();
        return response;
    }

    @RequestMapping(value = "/uploadMeterImage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveMeterReadingRecordsForFile(
            @RequestParam(name = "file") final MultipartFile file,
            final HttpServletRequest httprequest) {
        final WaterMeterReadingRequestDTO requestDto = new WaterMeterReadingRequestDTO();
        try {
            if (file != null) {
                final File convFile = new File(file.getOriginalFilename());
                file.transferTo(convFile);
                final Set<File> uplFile = new HashSet<>();
                uplFile.add(convFile);
                final Map<Long, Set<File>> map = new HashMap<>();
                map.put(0L, uplFile);
                FileUploadUtility.getCurrent().setFileMap(map);
            }
            requestDto.setStatus(MainetConstants.FlagS);
        } catch (final Exception e) {

            requestDto.setStatus(MainetConstants.MENU.F);
            logger.error("Error while meter reading saving details for spot billing : ", e);
        }
        return requestDto;
    }

    @RequestMapping(value = "/genearteWaterBill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object genearteWaterBill(
            @RequestBody final WaterMeterReadingRequestDTO requestDTO,
            final HttpServletRequest httprequest, final BindingResult bindingResult) {
        TbCsmrInfoDTO dto = new TbCsmrInfoDTO();
        dto.setUserId(requestDTO.getUserId());
        final List<TbCsmrInfoDTO> list = new ArrayList<>();
        WaterBillResponseDTO response = new WaterBillResponseDTO();
        dto.setLangId(requestDTO.getLangId());
        dto.setOrgId(requestDTO.getOrgid());
        dto.setCsCcn(requestDTO.getCsCcn());
        final List<TbCsmrInfoDTO> entityList = newWaterConnectionService.getwaterRecordsForBill(dto, MainetConstants.FlagS, null,
                null);
        if ((entityList != null) && !entityList.isEmpty()) {
            list.addAll(entityList);
            dto = list.get(0);
            dto.setCsRemark(requestDTO.getCsRemark());
            dto.setPcFlg(MainetConstants.Y_FLAG);
            try {
                final Map<Long, WaterBillGenErrorDTO> errorListMap = new HashMap<>(0);
                final Organisation organisation = new Organisation();
                organisation.setOrgid(requestDTO.getOrgid());
                final List<Long> csIdn = new ArrayList<>(0);
                for (final TbCsmrInfoDTO waterDTO : list) {
                    csIdn.add(waterDTO.getCsIdn());
                }
                Long loggedLocId = UserSession.getCurrent().getLoggedLocId();
                List<Long> bills = tbWtBillMasService.billCalculationAndGeneration(
                        organisation, errorListMap, list, requestDTO.getCsRemark(), requestDTO.getUserId(),
                        requestDTO.getLangId(), csIdn, Utility.getClientIpAddress(httprequest),loggedLocId);
                billMasterCommonService.doVoucherPosting(bills, organisation, MainetConstants.DEPT_SHORT_NAME.WATER,
                        requestDTO.getUserId(), null);
                if ((errorListMap != null) && !errorListMap.isEmpty()) {
                    response.setStatus(MainetConstants.MENU.F);
                } else {
                    WaterBillRequestDTO payrequestDTO = new WaterBillRequestDTO();
                    payrequestDTO.setCcnNumber(requestDTO.getCsCcn());
                    payrequestDTO.setOrgid(requestDTO.getOrgid());
                    response = billGenerationService.fetchBillPaymentData(payrequestDTO);
                }
            } catch (final Exception e) {
                response.setStatus(MainetConstants.MENU.F);
                logger.error("Error while bill generation for spot billing : ", e);
            }
        } else {
            response.setStatus(MainetConstants.MENU.F);
        }
        return response;

    }

    @RequestMapping(value = "/getPrefixDataForMeterReading", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object genearteWaterBill(@RequestBody final WaterMeterReadingRequestDTO requestDTO,
            final HttpServletRequest httprequest) {

        final Map<String, List<LookUp>> lookupMap = new HashMap<>(0);
        final Organisation organisation = new Organisation();
        organisation.setOrgid(requestDTO.getOrgid());

        final List<LookUp> gapcode = CommonMasterUtility.getListLookup(PrefixConstants.NewWaterServiceConstants.GAP_CODE,
                organisation);
        lookupMap.put(PrefixConstants.NewWaterServiceConstants.GAP_CODE, gapcode);
        final List<LookUp> mtrStatus = CommonMasterUtility.getListLookup(PrefixConstants.NewWaterServiceConstants.METER_STATUS,
                organisation);
        lookupMap.put(PrefixConstants.NewWaterServiceConstants.METER_STATUS, mtrStatus);
        return lookupMap;

    }

    @RequestMapping(value = "/printWaterBill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object printWaterBill(
            @RequestBody final WaterMeterReadingRequestDTO requestDTO,
            final HttpServletRequest httprequest, final BindingResult bindingResult) {
        WaterBillPrintingDTO billprintData = null;
        final List<Object[]> data = tbWtBillMasService.fetchConnectionDataAndBillId(requestDTO.getCsIdn(), requestDTO.getOrgid());
        if ((data != null) && !data.isEmpty()) {
            Map<Long, WaterBillPrintingDTO> billprint = new HashMap<>();
            final Object[] result = data.get(0);
            final Organisation org = new Organisation();
            org.setOrgid(requestDTO.getOrgid());
            final List<String> billId = new ArrayList<>(0);
            billId.add(result[1].toString());
            final String meter = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(result[0].toString()), org)
                    .getLookUpCode();
            
            
			final Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE, MainetConstants.STATUS.ACTIVE);
			final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);

			final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
					org);
			final List<TbTaxMas> taxesMaster = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
					chargeApplicableAt.getLookUpId());
			final List<TbTaxMas> taxesMasterBillReceipt = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(),
					deptId, chargeApplicableAtBillReceipt.getLookUpId());
			taxesMaster.addAll(taxesMasterBillReceipt);
			LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("P",
					PrefixConstants.LookUpPrefix.TAC, 1, org.getOrgid());
			LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("SC",
					PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
			Long surchargeTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), org.getOrgid(),
					deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
			
            billprint = tbWtBillMasService.prinBillData(billprint, org, billId, meter, FileNetApplicationClient.getInstance(),taxesMaster,null,null,surchargeTaxId);
            if ((billprint != null) && !billprint.isEmpty()) {
                billprintData = billprint.get(Long.valueOf(result[1].toString()));
            }
        }
        return billprintData;
    }

}
