package com.abm.mainet.rnl.rest.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.rnl.dto.BookingCalanderDTO;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.CalanderDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.EstatePropertyShiftDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;

/**
 * @author ritesh.patil
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/estateBooking")
public class EstateBookingRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstateBookingRestController.class);
    private final static String FAILED = "Esate Booking Service called failed due to ";
    private final static String ESTATE_PROP_REQ_DTO_EMPTY = "EstatePropReqestDTO Can not be empty, ";
    private final static String ORG_EMPTY = "OrgId Can not be empty, ";
    private final static String PROP_ID_EMPTY = "propertyId Can not be empty, ";
    private final static String PROP_CATEGORY_ID = "categoryId Can not be empty, ";
    private final static String BOOKING_NO = "Booking No Can not be empty, ";

    @Autowired
    private IEstateBookingService iEstateBookingService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private TbServicesMstService servicesMstService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @Autowired
    private IAttachDocsService iAttachDocsService;

    @RequestMapping(value = "/getAllRentedProperties", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllRentedProperties(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        try {
            PropertyResDTO responseDTO = new PropertyResDTO();
            responseEntity = validateAllRentedPropertiesRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                responseDTO = iEstateBookingService.getAllRentedProperties(PrefixConstants.CPD_VALUE_RENT,
                        PrefixConstants.CATEGORY_PREFIX_NAME, requestDTO.getOrgId(), null);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for getAllRentedProperties method url: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/property/filterList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getFilterProperties(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        try {
            PropertyResDTO responseDTO = new PropertyResDTO();
            responseEntity = validateFilterRentedPropertiesRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                responseDTO = iEstateBookingService.getAllRentedProperties(PrefixConstants.CPD_VALUE_RENT,
                        PrefixConstants.CATEGORY_PREFIX_NAME, requestDTO.getOrgId(), requestDTO.getType());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch filter records for getFilterProperties method url: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/getProperty", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ResponseEntity<?> getProperty(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        try {
            EstatePropResponseDTO estatePropResponseDTO = new EstatePropResponseDTO();
            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(requestDTO.getPropId(),
                        requestDTO.getOrgId());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(estatePropResponseDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for getProperty method url: " + ex.getMessage(), ex);
        }

        return responseEntity;

    }

    @RequestMapping(value = "/getDisableDates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllDisableDates(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        try {
            final CalanderDTO calanderDTO = new CalanderDTO();
            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final List<String> fromAndtoDate = iEstateBookingService.getEstateBookingFromAndToDates(requestDTO.getPropId(),
                        requestDTO.getOrgId(), false);
                calanderDTO.setDatesList(fromAndtoDate);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(calanderDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for getAllDisableDates method url: " + ex.getMessage(), ex);
        }

        return responseEntity;

    }

    @RequestMapping(value = "/getCalendarData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getCalanderList(@RequestBody final EstatePropReqestDTO requestDTO) {
        ResponseEntity<?> responseEntity = null;
        try {
            final BookingCalanderDTO bookingCalanderDTO = new BookingCalanderDTO();
            List<EstateBookingDTO> estateBookingDTOs = new ArrayList<>();
            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                estateBookingDTOs = iEstateBookingService.findByPropId(requestDTO.getPropId(), requestDTO.getOrgId());
                bookingCalanderDTO.setBookingDTOs(estateBookingDTOs);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(bookingCalanderDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for getCalanderList method url: " + ex.getMessage(), ex);
        }

        return responseEntity;

    }

    // 99721
    @RequestMapping(value = "/getBookingDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public @ResponseBody ResponseEntity<?> getFilterdList(@RequestBody final EstatePropReqestDTO requestDTO)
            throws ParseException {
        ResponseEntity<?> responseEntity = null;
        try {
            Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            List<EstatePropResponseDTO> estatePropResponseDTO = new ArrayList<>();
            responseEntity = validatePropertyRequest(requestDTO);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date frmDate = df.parse(requestDTO.getFromDate());
            Date toDate = df.parse(requestDTO.getToDate());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                estatePropResponseDTO = iEstateBookingService.getBookingDetails(frmDate, toDate, requestDTO.getPropertyName(),
                        orgId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(estatePropResponseDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for booking details method url: " + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getMapData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getMapList(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        EstatePropMaster estatePropMaster = new EstatePropMaster();
        try {

            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                estatePropMaster = iEstatePropertyService.findLatLong(requestDTO.getPropId(), requestDTO.getOrgId());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(estatePropMaster);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for getMapList method url: " + ex.getMessage(), ex);
        }

        return responseEntity;

    }

    @RequestMapping(value = "/saveEstateBooking", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody final BookingReqDTO bookingReqDTO) {
        BookingResDTO bookingResDTO = new BookingResDTO();
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = validateBookingSaveRequest(bookingReqDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.RNL_ESTATE_BOOKING,
                        bookingReqDTO.getEstateBookingDTO().getOrgId());
                bookingResDTO = iEstateBookingService.saveEstateBooking(bookingReqDTO, null, serviceMaster.getSmServiceName());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(bookingResDTO);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEntity.getBody());
                LOGGER.error("Error while savingrecords for save method url: " + responseEntity.getBody());
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while savingrecords for save method url: " + ex.getMessage(), ex);
        }

        return responseEntity;

    }

    /**
     *
     * @param requestDTO
     * @return
     *
     * Date must be in /(SLASH) format example "15/2/2017"
     *//*
        * @RequestMapping(value = "/getShiftsBasedOnDate", method = RequestMethod.POST, produces =
        * MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
        * @ResponseStatus(value = HttpStatus.OK)
        * @ResponseBody public List<LookUp> getShiftsBasedOnDate(@RequestBody final EstatePropReqestDTO requestDTO) { final
        * List<LookUp> lookups = iEstateBookingService.getEstateBookingShifts(requestDTO.getPropId(), requestDTO.getFromDate(),
        * requestDTO.getToDate(), requestDTO.getOrgId()); return lookups; }
        */

    @RequestMapping(value = "/getShiftsBasedOnDate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<EstatePropertyShiftDTO> getPropertyShiftsBasedOnDate(@RequestBody final EstatePropReqestDTO requestDTO) {

        final List<EstatePropertyShiftDTO> shiftList = iEstateBookingService.getShiftDetailsFromDateAndTodate(
                requestDTO.getPropId(),
                requestDTO.getFromDate(), requestDTO.getToDate(), requestDTO.getOrgId());
        return shiftList;
    }

    /**
     * Date should be in slash format "15/2/2017" SLAS FORMAT SPECIFIC
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/dateRangBetBookedDate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> dateRangBetBookedDate(@RequestBody final EstatePropReqestDTO requestDTO) {
        ResponseEntity<?> responseEntity = null;
        String flag = MainetConstants.EstateBooking.PASS;
        try {
            responseEntity = validatePropertyRequestWithDate(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final List<String> fromAndtoDate = iEstateBookingService.getEstateBookingFromAndToDatesForGeneral(
                        requestDTO.getPropId(),
                        requestDTO.getOrgId());
                final Calendar calendar = new GregorianCalendar();
                final Date dateFrom = UtilityService.convertStringDateToDateFormat(requestDTO.getFromDate());
                /**
                 * "15/2/2017" SLAS FORMAT SPECIFIC
                 **/
                final Date dateTo = UtilityService.convertStringDateToDateFormat(requestDTO.getToDate());
                calendar.setTime(dateFrom);
                final List<String> bookedDate = new ArrayList<>();
                while (calendar.getTime().before(dateTo) || calendar.getTime().equals(dateTo)) {
                    final Date result = calendar.getTime();
                    bookedDate.add(new SimpleDateFormat("d-M-yyyy").format(result));
                    calendar.add(Calendar.DATE, 1);
                }
                for (final String date : bookedDate) {
                    if (fromAndtoDate.contains(date)) {
                        flag = MainetConstants.EstateBooking.FAIL;
                        break;
                    }
                }
            }
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(flag);

        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for dateRangBetBookedDate method url: " + ex.getMessage(), ex);
        }

        return responseEntity;

    }

    private ResponseEntity<?> validateAllRentedPropertiesRequest(final EstatePropReqestDTO estatePropReqestDTO) {
        final StringBuilder builder = new StringBuilder();
        if (estatePropReqestDTO == null) {
            builder.append(ESTATE_PROP_REQ_DTO_EMPTY);
        } else if (estatePropReqestDTO.getOrgId() == null) {
            builder.append(ORG_EMPTY);
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }

    private ResponseEntity<?> validateFilterRentedPropertiesRequest(final EstatePropReqestDTO estatePropReqestDTO) {
        final StringBuilder builder = new StringBuilder();
        if (estatePropReqestDTO == null) {
            builder.append(ESTATE_PROP_REQ_DTO_EMPTY);
        } else {
            if (estatePropReqestDTO.getOrgId() == null) {
                builder.append(ORG_EMPTY);
            }
            if (estatePropReqestDTO.getType() == null) {
                builder.append(MainetConstants.EstateBookingRest.FILTER_TYPE);
            }
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());

    }

    private ResponseEntity<?> validatePropertyRequest(final EstatePropReqestDTO estatePropReqestDTO) {
        final StringBuilder builder = new StringBuilder();
        if (estatePropReqestDTO == null) {
            builder.append(ESTATE_PROP_REQ_DTO_EMPTY);
        } else {
            if (estatePropReqestDTO.getOrgId() == null) {
                builder.append(ORG_EMPTY);
            }
            if (estatePropReqestDTO.getPropId() == null) {
                builder.append(PROP_ID_EMPTY);
            }
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }

    private ResponseEntity<?> validateProperty(final EstatePropertyEventDTO estatePropReqestDTO) {
        final StringBuilder builder = new StringBuilder();
        if (estatePropReqestDTO == null) {
            builder.append(ESTATE_PROP_REQ_DTO_EMPTY);
        } else {
            if (estatePropReqestDTO.getOrgId() == null) {
                builder.append(ORG_EMPTY);
            }
            if (estatePropReqestDTO.getCategoryId() == null) {
                builder.append(PROP_CATEGORY_ID);
            }
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }

    private ResponseEntity<?> validateForOpendate(final EstateBookingDTO estateBookingDTO) {
        final StringBuilder builder = new StringBuilder();
        if (estateBookingDTO == null) {
            builder.append(ESTATE_PROP_REQ_DTO_EMPTY);
        } else {
            if (estateBookingDTO.getOrgId() == null) {
                builder.append(ORG_EMPTY);
            }
            if (estateBookingDTO.getPropId() == null) {
                builder.append(PROP_ID_EMPTY);
            }
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }

    private ResponseEntity<?> openStatus(final EstateBookingDTO estateBookingDTO) {
        final StringBuilder builder = new StringBuilder();
        if (estateBookingDTO == null) {
            builder.append(ESTATE_PROP_REQ_DTO_EMPTY);
        } else {
            if (estateBookingDTO.getOrgId() == null) {
                builder.append(ORG_EMPTY);
            }
            if (estateBookingDTO.getBookingNo() == null) {
                builder.append(BOOKING_NO);
            }
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }

    private ResponseEntity<?> validatePropertyRequestWithDate(final EstatePropReqestDTO estatePropReqestDTO) {
        final StringBuilder builder = new StringBuilder();
        if (estatePropReqestDTO == null) {
            builder.append(ESTATE_PROP_REQ_DTO_EMPTY);
        } else {
            if (estatePropReqestDTO.getOrgId() == null) {
                builder.append(ORG_EMPTY);
            }
            if (estatePropReqestDTO.getPropId() == null) {
                builder.append(PROP_ID_EMPTY);
            }
            if (estatePropReqestDTO.getFromDate() == null) {
                builder.append(MainetConstants.EstateBookingRest.FROM_DATE);
            }
            if (estatePropReqestDTO.getToDate() == null) {
                builder.append(MainetConstants.EstateBookingRest.TO_DATE);
            }
        }
        return builder.toString().isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body("")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }

    private ResponseEntity<?> validateBookingSaveRequest(final BookingReqDTO bookingReqDTO) {
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
                    builder.append(ORG_EMPTY);
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

    @RequestMapping(value = "/propertyInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> propertyInfo(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        PropInfoDTO propInfoDTO = new PropInfoDTO();
        try {

            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                propInfoDTO = iEstateBookingService.findBooking(requestDTO.getBooingId(), requestDTO.getOrgId());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(propInfoDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for propertyInfo method url: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/term", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> findTermAndConditionDoc(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        try {

            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService
                        .findPropertyForBooking(requestDTO.getPropId(), requestDTO.getOrgId());
                final List<AttachDocs> attachDocsList = iAttachDocsService.findByCode(requestDTO.getOrgId(),
                        estatePropResponseDTO.getPropertyNo());
                for (final AttachDocs attachDocs : attachDocsList) {
                    if (attachDocs.getSerialNo() == 1) {
                        final String value = attachDocs.getAttPath() + "\\" + attachDocs.getAttFname();
                        final String actualPath = value.replace("\\", "\\\\");
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(actualPath);
                    }
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for propertyInfo method url: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/propImages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findPropImages(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        try {

            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService
                        .findPropertyForBooking(requestDTO.getPropId(), requestDTO.getOrgId());
                final List<AttachDocs> attachDocsList = iAttachDocsService.findByCode(requestDTO.getOrgId(),
                        estatePropResponseDTO.getPropertyNo());
                final List<String> imagePaths = new ArrayList<>();
                for (final AttachDocs attachDocs : attachDocsList) {
                    if (attachDocs.getSerialNo() == 0) {
                        imagePaths.add(getPropImages(attachDocs));
                    }
                }
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(imagePaths);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for propertyInfo method url: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    private String getPropImages(final AttachDocs attachDocs) {

        new ArrayList<String>();
        final UUID uuid = UUID.randomUUID();
        final String randomUUIDString = uuid.toString();
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                attachDocs.getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
                randomUUIDString + MainetConstants.FILE_PATH_SEPARATOR + "PROPERTY";
        final String path1 = attachDocs.getAttPath();
        final String name = attachDocs.getAttFname();
        final String data = Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, outputPath,
                FileNetApplicationClient.getInstance());
        return data;
    }

    /**
     * get Property Aminity Facility
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/getPropertyAmenityFacility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getPropertyAminityFacility(@RequestBody final EstatePropReqestDTO requestDTO) {

        ResponseEntity<?> responseEntity = null;
        try {
            EstatePropResponseDTO estatePropResponseDTO = new EstatePropResponseDTO();
            responseEntity = validatePropertyRequest(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                estatePropResponseDTO = iEstatePropertyService.findFacilityAndAmenities(requestDTO.getPropId(),
                        requestDTO.getOrgId());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(estatePropResponseDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for getPropertyAminityFacility method url: " + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getCategoryEvent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getCategoryEvent(@RequestBody final EstatePropertyEventDTO requestDTO) {
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = validateProperty(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                List<EstatePropertyEventDTO> estatePropResponseDTO = new ArrayList<EstatePropertyEventDTO>();
                estatePropResponseDTO = iEstateBookingService.getEventOrPropertyId(requestDTO.getCategoryId(),
                        requestDTO.getOrgId());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(estatePropResponseDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records for getCategoryEvent method url: " + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getOpenDateChallanNotPaid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> openClender(@RequestBody final EstateBookingDTO requestDTO) {
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = validateForOpendate(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                List<EstateBookingDTO> estaeBookingDto = iEstateBookingService.checkedReceiptValiadtion(requestDTO.getOrgId(),
                        requestDTO.getPropId());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(estaeBookingDto);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetch records whose proppert Flag N and C method url: " + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/updateDateBookingStatusFlagN", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> updateStstus(@RequestBody final EstateBookingDTO requestDTO) {
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = openStatus(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                iEstateBookingService.enableEstateBookingStatus(requestDTO.getBookingNo(), requestDTO.getOrgId(),
                        requestDTO.getFromDate(), requestDTO.getToDate());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseEntity);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while updation status in EstateBooking Flag method url: " + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/callServcieCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> serviceCode(@RequestBody final EstateBookingDTO requestDTO) {
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = openStatus(requestDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                ServiceMaster service = servicesMstService.findShortCodeByOrgId(requestDTO.getSortCode(), requestDTO.getOrgId());
                Long challanValidity = service.getComN1();
                LOGGER.info("servcie {}", service);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(challanValidity);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while updation ServiceMaster Call method url: " + ex.getMessage(), ex);
        }
        return responseEntity;
    }

}
