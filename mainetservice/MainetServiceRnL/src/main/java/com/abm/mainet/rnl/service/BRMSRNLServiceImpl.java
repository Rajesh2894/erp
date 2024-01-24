package com.abm.mainet.rnl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxDetMasEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.abm.mainet.rnl.rest.ui.controller.EstateBookingRestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author hiren.poriya
 * @Since 09-Jun-2018
 */
@WebService(endpointInterface = "com.abm.mainet.rnl.service.BRMSRNLService")
@Api(value = "/brmsrnlservice")
@Path("/brmsrnlservice")
@Service
public class BRMSRNLServiceImpl implements BRMSRNLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRMSRNLServiceImpl.class);
    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
    private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
    private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
    private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";

    @Resource
    private ServiceMasterService serviceMasterService;

    @Resource
    private TbTaxMasService taxMasService;

    @Autowired
    private IEstateBookingService iEstateBookingService;
    
    private static Map<Long, String> dependsOnFactorMap = null;

    @POST
    @Path("/dependentparams")
    @ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableTaxes(@ApiParam(value = "get dependent params", required = true) WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        LOGGER.info("brms RNL getApplicableTaxes execution start..");
        LOGGER.info("RNL DEPENDENT PARMAS Request ::  INPUT = {}", requestDTO);
        try {
            if (requestDTO.getDataModel() == null) {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            } else {
                RNLRateMaster rnlRateMaster = (RNLRateMaster) CommonMasterUtility.castRequestToDataModel(requestDTO,
                        RNLRateMaster.class);
                LOGGER.info("RNL DEPENDENT PARMAS Request  After Casting ::  INPUT = {}", requestDTO);
                validateDataModel(rnlRateMaster, responseDTO);
                if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                    responseDTO = populateOtherFieldsForServiceCharge(rnlRateMaster, responseDTO);
                    LOGGER.info("RNL DEPENDENT PARMAS Response  ::  OUTPUT = {}", responseDTO);
                }
            }
        } catch (CloneNotSupportedException | FrameworkException ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
        }
        LOGGER.info("brms RNL getApplicableTaxes execution end..");
        return responseDTO;
    }

    @POST
    @Path("/servicecharge")
    @ApiOperation(value = "get RNL service charge", notes = "get RNL service charge", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableCharges(
            @ApiParam(value = "get RNL service charge", required = true) WSRequestDTO wsRequestDTO) {
        LOGGER.info("brms RNL getApplicableCharges execution start..");
        LOGGER.info("RNL servicecharge  Request ::  INPUT = {}", wsRequestDTO);
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.BRMSMappingURL.RNL_SERVICE_CHARGE_URI);
            if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                responseDTO = setServiceChargeDTO(responseDTO);
            } else {
                return responseDTO;
            }
            LOGGER.info("RNL servicecharge  Response ::  OUTPUT = {}", responseDTO);
        } catch (Exception ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        LOGGER.info("brms RNL getApplicableCharges execution End..");
        return responseDTO;
    }

    /*
     * get property list by search filter with total rent(BRMS call)
     */

    @POST
    @Path("/getFilteredRentedProperties")
    @ApiOperation(value = "get RNL service charge", notes = "get RNL service charge", response = WSResponseDTO.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public PropertyResDTO getFilteredRentedProperties(@RequestBody EstatePropReqestDTO reqDto) {
        Organisation org = new Organisation();
        org.setOrgid(reqDto.getOrgId());
        return iEstateBookingService.getFilteredRentedProperties(reqDto.getCategoryTypeId(), reqDto.getEventId(),
                reqDto.getCapcityFrom(), reqDto.getCapcityTo(), reqDto.getRentFrom(), reqDto.getRentTo(), org);
    }

    private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
        LOGGER.info("setServiceChargeDTO execution start..");
        ChargeDetailDTO chargedto = null;
        final List<?> charges = RestClient.castResponse(responseDTO, RNLRateMaster.class);
        final List<RNLRateMaster> finalRateMaster = new ArrayList<>();
        for (final Object rate : charges) {
            final RNLRateMaster masterRate = (RNLRateMaster) rate;
            finalRateMaster.add(masterRate);
        }
        final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
        for (final RNLRateMaster rateCharge : finalRateMaster) {
            chargedto = new ChargeDetailDTO();
            chargedto.setChargeCode(rateCharge.getTaxId());
            chargedto.setChargeAmount(rateCharge.getFlatRate());
            chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
            chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
            chargedto.setPercentageRate(rateCharge.getPercentageRate());
            detailDTOs.add(chargedto);
        }
        responseDTO.setResponseObj(detailDTOs);
        LOGGER.info("setServiceChargeDTO execution end..");
        return responseDTO;
    }

    /**
     * validating WaterRateMaster model
     * @param waterRateMaster
     * @param responseDTO
     * @return
     */
    private WSResponseDTO validateDataModel(RNLRateMaster rnlRateMaster, WSResponseDTO responseDTO) {
        LOGGER.info("validateDataModel execution start..");
        StringBuilder builder = new StringBuilder();
        if (rnlRateMaster.getServiceCode() == null ||
                rnlRateMaster.getServiceCode().isEmpty()) {
            builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
        }
        if (rnlRateMaster.getOrgId() == 0l) {
            builder.append(ORG_ID_CANT_BE_ZERO).append(",");
        }
        if (rnlRateMaster.getChargeApplicableAt() == null || rnlRateMaster.getChargeApplicableAt().isEmpty()) {
            builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
        } else if (!StringUtils.isNumeric(rnlRateMaster.getChargeApplicableAt())) {
            builder.append(CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC);
        }
        if (builder.toString().isEmpty()) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        } else {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(builder.toString());
        }

        return responseDTO;
    }

    public WSResponseDTO populateOtherFieldsForServiceCharge(RNLRateMaster rnlRateMaster, WSResponseDTO responseDTO)
            throws CloneNotSupportedException {
        LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
        List<RNLRateMaster> listOfCharges;
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rnlRateMaster.getServiceCode(),
                rnlRateMaster.getOrgId());
        Organisation organisation = new Organisation();
        organisation.setOrgid(rnlRateMaster.getOrgId());
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ChargeApplicableAt.RECEIPT,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);
        if (serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
            List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
                    serviceMas.getSmServiceId(),
                    rnlRateMaster.getOrgId(),
                    Long.parseLong(rnlRateMaster.getChargeApplicableAt()));
            listOfCharges = settingAllFields(applicableCharges, rnlRateMaster, organisation);
            responseDTO.setResponseObj(listOfCharges);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        } else if(rnlRateMaster.getChargeApplicableAt().equals(String.valueOf(chargeApplicableAt.getLookUpId()))){      	
            Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
            		.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RNL);
        	final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
        		 MainetConstants.Property.propPref.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
        		organisation);
        	List<TbTaxMas> indepenTaxList = taxMasService.fetchAllIndependentTaxes(organisation.getOrgid(),deptId ,
        			Long.valueOf(rnlRateMaster.getChargeApplicableAt()), null, chargeApplicableAtBillReceipt.getLookUpId());
        	TbTaxMasEntity charges;
        	List<TbTaxMasEntity> chargesList=new ArrayList<>();
        	for(TbTaxMas indepenTax: indepenTaxList) {
        		charges = new TbTaxMasEntity();
        		BeanUtils.copyProperties(indepenTax, charges);
        		chargesList.add(charges);
        	}
        	listOfCharges = settingAllFields(chargesList, rnlRateMaster, organisation);
            responseDTO.setResponseObj(listOfCharges);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        }else {
            responseDTO.setFree(true);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        }
        LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
        return responseDTO;
    }

    /**
     * 
     * @param applicableCharges
     * @param rateMaster
     * @return
     * @throws CloneNotSupportedException
     */
    private List<RNLRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, RNLRateMaster rateMaster,
            Organisation organisation) throws CloneNotSupportedException {
        LOGGER.info("settingAllFields execution start..");
        List<RNLRateMaster> list = new ArrayList<>();
        for (TbTaxMasEntity entity : applicableCharges) {
            RNLRateMaster rnlRateMaster = (RNLRateMaster) rateMaster.clone();
            // SLD for dependsOnFactor
            String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD, rateMaster.getOrgId(),
                    Long.parseLong(entity.getTaxMethod()));
            String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA, entity.getOrgid(),
                    entity.getTaxApplicable());
            rnlRateMaster.setTaxType(taxType);
            rnlRateMaster.setTaxCode(entity.getTaxCode());
            rnlRateMaster.setChargeApplicableAt(chargeApplicableAt);
            rnlRateMaster.setChargeDescEng(entity.getTaxDesc());
            settingTaxCategories(rnlRateMaster, entity, organisation);
            rnlRateMaster.setDependsOnFactorList(settingDependsOnFactor(entity.getListOfTbTaxDetMas(), organisation));
            rnlRateMaster.setTaxId(entity.getTaxId());
            list.add(rnlRateMaster);
        }
        LOGGER.info("settingAllFields execution end..");
        return list;
    }

    /**
     * 
     * @param waterRateMaster
     * @param enity
     * @param organisation
     * @return
     */
    private RNLRateMaster settingTaxCategories(RNLRateMaster rnlMaster, TbTaxMasEntity enity,
            Organisation organisation) {

        List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
                MainetConstants.NUMBERS.ONE,
                organisation);
        for (LookUp lookUp : taxCategories) {
            if (enity.getTaxCategory1() != null) {
                if (lookUp.getLookUpId() == enity.getTaxCategory1()) {
                    rnlMaster.setTaxCategory(lookUp.getDescLangFirst());
                    break;
                }
            }

        }
        List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
                MainetConstants.NUMBERS.TWO,
                organisation);
        for (LookUp lookUp : taxSubCategories) {
            if (enity.getTaxCategory2() != null) {
                if (lookUp.getLookUpId() == enity.getTaxCategory2()) {
                    rnlMaster.setTaxSubCategory(lookUp.getLookUpCode());
                    break;
                }
            }

        }
        return rnlMaster;

    }

    private List<String> settingDependsOnFactor(List<TbTaxDetMasEntity> taxDetList, Organisation orgId) {

        if (dependsOnFactorMap == null) {
            cacheDependsOnFactors(orgId);
        }
        List<String> dependsOnFactorList = new ArrayList<>();

        if (taxDetList != null) {
            for (TbTaxDetMasEntity entity : taxDetList) {
                if (entity.getStatus().equals(MainetConstants.FlagA)) {
                    dependsOnFactorList.add(dependsOnFactorMap.get(entity.getTdDependFact()));
                }
            }
        }

        return dependsOnFactorList;
    }

    /**
     * cache Depends on factor only once
     * @param orgId
     */
    private static void cacheDependsOnFactors(Organisation orgId) {
        List<LookUp> lookUps = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.RSD, orgId);
        dependsOnFactorMap = new HashMap<>();
        for (LookUp lookUp : lookUps) {
            dependsOnFactorMap.put(lookUp.getLookUpId(), lookUp.getLookUpCode());
        }

    }

    @Override
    @WebMethod(exclude = true)
    public double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

	@POST
    @Path("/getFilteredWaterTanker")
    @ApiOperation(value = "get RNL service charge", notes = "get RNL service charge", response = WSResponseDTO.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public PropertyResDTO getFilteredWaterTanker(@RequestBody EstatePropReqestDTO reqDto) {
        Organisation org = new Organisation();
        org.setOrgid(reqDto.getOrgId());
        return iEstateBookingService.getFilteredWaterTanker(reqDto.getCategoryTypeId(), reqDto.getEventId(),org);
    }
	
	@POST
    @Path("/saveWaterTanker")
    @ApiOperation(value = "saveWaterTanker", notes = "saveWaterTanker", response = WSResponseDTO.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
	public BookingResDTO saveWaterTanker(@RequestBody final BookingReqDTO bookingReqDTO) {
        BookingResDTO bookingResDTO = new BookingResDTO();
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = validateBookingSaveRequest(bookingReqDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.RNL_WATER_TANKER_BOOKING,
                        bookingReqDTO.getEstateBookingDTO().getOrgId());
                bookingResDTO = iEstateBookingService.saveWaterTanker(bookingReqDTO, null, serviceMaster.getSmServiceName());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(bookingResDTO);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEntity.getBody());
                LOGGER.error("Error while savingrecords for save method url: " + responseEntity.getBody());
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Esate Booking Service called failed due to" + ex.getMessage());
            LOGGER.error("Error while savingrecords for save method url: " + ex.getMessage(), ex);
        }

        return bookingResDTO;

    }
	
	public ResponseEntity<?> validateBookingSaveRequest(final BookingReqDTO bookingReqDTO) {
        final StringBuilder builder = new StringBuilder();
        if (bookingReqDTO != null) {
            final ApplicantDetailDTO applicantDetailDTO = bookingReqDTO.getApplicantDetailDto();
            final EstateBookingDTO bookingDTO = bookingReqDTO.getEstateBookingDTO();
            if (applicantDetailDTO == null) {
                builder.append(MainetConstants.EstateBookingRest.APP_DTO_EMPTY);
            } else {
                if ((applicantDetailDTO.getApplicantTitle() == null) || (applicantDetailDTO.getApplicantTitle() == 0L)) {
                    builder.append(MainetConstants.EstateBookingRest.TITLE_EMPTY);
                }
                if ((applicantDetailDTO.getApplicantFirstName() == null)
                        || applicantDetailDTO.getApplicantFirstName().isEmpty()) {
                    builder.append(MainetConstants.EstateBookingRest.FIRST_NAME);
                }
                if ((applicantDetailDTO.getApplicantLastName() == null) || applicantDetailDTO.getApplicantLastName().isEmpty()) {
                    builder.append(MainetConstants.EstateBookingRest.LAST_NAME);
                }
                if ((applicantDetailDTO.getGender() == null) || applicantDetailDTO.getGender().equals("0")) {
                    builder.append(MainetConstants.EstateBookingRest.GEN_EMPTY);
                }
                if ((applicantDetailDTO.getMobileNo() == null) || applicantDetailDTO.getMobileNo().isEmpty()) {
                    builder.append(MainetConstants.EstateBookingRest.MOBILE_NO);
                }
                if ((applicantDetailDTO.getAreaName() == null) || applicantDetailDTO.getAreaName().isEmpty()) {
                    builder.append(MainetConstants.EstateBookingRest.AREA_NO);
                }
                if ((applicantDetailDTO.getVillageTownSub() == null) || applicantDetailDTO.getVillageTownSub().isEmpty()) {
                    builder.append(MainetConstants.EstateBookingRest.VILLAGE_STUB);
                }
                if ((applicantDetailDTO.getPinCode() == null) || applicantDetailDTO.getPinCode().isEmpty()) {
                    builder.append(MainetConstants.EstateBookingRest.PIN_NO);
                }
            }
            if (bookingDTO == null) {
                builder.append(MainetConstants.EstateBookingRest.ESTATE);
            } else {
                if (bookingDTO.getFromDate() == null) {
                    builder.append(MainetConstants.EstateBookingRest.FROM_DATE);
                }
                if (bookingDTO.getToDate() == null) {
                    builder.append(MainetConstants.EstateBookingRest.TO_DATE);
                }
                if ((bookingDTO.getShiftId() == null) || (bookingDTO.getShiftId() == 0L)) {
                    builder.append(MainetConstants.EstateBookingRest.SHIFT_EMPTY);
                }
                if ((bookingDTO.getPurpose() == null)) {
                    builder.append(MainetConstants.EstateBookingRest.PURPOSE_EMPTY);
                }
                if (bookingDTO.getOrgId() == null) {
                    builder.append("OrgId Can not be empty, ");
                }
                if (bookingDTO.getLangId() == 0L) {
                    builder.append(MainetConstants.EstateBookingRest.lANG_EMPTY);
                }
                if (bookingDTO.getCreatedBy() == null) {
                    builder.append(MainetConstants.EstateBookingRest.CREATED_BY);
                }
                if (bookingDTO.getCreatedDate() == null) {
                    builder.append(MainetConstants.EstateBookingRest.CREATED_DATE);
                }
                if (bookingDTO.getLgIpMac() == null) {
                    builder.append(MainetConstants.EstateBookingRest.IP_MAC);
                }
            }

            if ((bookingReqDTO.getDocumentList() != null) && !bookingReqDTO.getDocumentList().isEmpty()) {
                for (final DocumentDetailsVO doc : bookingReqDTO.getDocumentList()) {
                    if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
                        if (doc.getDocumentByteCode() == null) {
                            builder.append(MainetConstants.EstateBookingRest.UPLOAD_DOCS);
                            break;
                        }
                    }
                }
            }

            if (bookingReqDTO.getPayAmount() == null) {
                builder.append(MainetConstants.EstateBookingRest.PAY_AMOUNTS);
            }
            if (bookingReqDTO.getServiceId() == null) {
                builder.append(MainetConstants.EstateBookingRest.SERVICE_EMPTY);
            }
            if (bookingReqDTO.getDeptId() == null) {
                builder.append(MainetConstants.EstateBookingRest.DEPT_EMPTY);
            }

        } else {
            builder.append(MainetConstants.EstateBookingRest.BOOK_DTO_EMPTY);

        }

        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }
	
	@POST
    @Path("/getWaterTankerDetailByAppId")
    @ApiOperation(value = "getWaterTankerDetailByAppId", notes = "getWaterTankerDetailByAppId", response = WSResponseDTO.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public BookingReqDTO getWaterTankerDetailByAppId(@RequestBody BookingReqDTO bookingReqDTO) {
        return iEstateBookingService.getWaterTankerDetailByAppId(bookingReqDTO.getApplicationId(), bookingReqDTO.getOrgId());
    }
	
	@POST
    @Path("/getPenaltyCharges")
    @ApiOperation(value = "get RNL Penalty charge", notes = "get RNL Penalty charge", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getPenaltyCharges(
            @ApiParam(value = "get RNL service charge", required = true) WSRequestDTO wsRequestDTO) {
        LOGGER.info("brms RNL getApplicableCharges execution start..");
        LOGGER.info("RNL servicecharge  Request ::  INPUT = {}", wsRequestDTO);
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.BRMSMappingURL.RNL_SERVICE_CHARGE_URI);
            if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                responseDTO = setPenaltyChargeDTO(responseDTO);
            } else {
                return responseDTO;
            }
            LOGGER.info("RNL servicecharge  Response ::  OUTPUT = {}", responseDTO);
        } catch (Exception ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        LOGGER.info("brms RNL getApplicableCharges execution End..");
        return responseDTO;
    }

	private WSResponseDTO setPenaltyChargeDTO(WSResponseDTO responseDTO) {
		 LOGGER.info("setServiceChargeDTO execution start..");
	        final List<?> charges = RestClient.castResponse(responseDTO, RNLRateMaster.class);
	        final List<RNLRateMaster> finalRateMaster = new ArrayList<>();
	        for (final Object rate : charges) {
	            final RNLRateMaster masterRate = (RNLRateMaster) rate;
	            finalRateMaster.add(masterRate);
	        }
	       
	        responseDTO.setResponseObj(finalRateMaster);
	        LOGGER.info("setServiceChargeDTO execution end..");
	        return responseDTO;
	}

}
