package com.abm.mainet.swm.rest.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.CollectorResDTO;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.service.IWasteCollectorService;

/**
 * @author sarojkumar.yadav
 *
 */
@RestController
@RequestMapping("/wasteCollector")
public class WasteCollectorRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WasteCollectorRestController.class);
    private static final String FAILED = "Waste Collector Service called failed due to";

    @Autowired
    private IWasteCollectorService iWasteCollectorService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @RequestMapping(value = "/saveWasteCollector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> saveWasteCollector(@RequestBody final CollectorReqDTO collectorReqDTO) {
        CollectorResDTO collectorResDTO = new CollectorResDTO();
        ResponseEntity<?> responseEntity = null;

        try {
            String validationString = validateWasteCollectoeSaveRequest(collectorReqDTO);
            if (validationString.isEmpty()) {
                final ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
                        MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE,
                        collectorReqDTO.getCollectorDTO().getOrgId());
                collectorResDTO = iWasteCollectorService.saveCnDApplicantForm(collectorReqDTO, null, serviceMaster.getSmServiceName(), null, null);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(collectorResDTO);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationString);
                LOGGER.error(
                        "Error while saving waste collector records for save method url: " + responseEntity.getBody());
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while saving waste collector records for save method url: " + ex.getMessage(), ex);
        }

        return responseEntity;

    }

    @RequestMapping(value = "/getChecklist/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getChecklist(@RequestBody WSRequestDTO requestDTO, @PathVariable("orgId") Long orgId) {
        ResponseEntity<?> responseEntity = null;
        try {
            String validationString = validateWasteCollectoeRequest(requestDTO);
            if (validationString.isEmpty()) {
                WSResponseDTO responseDTO = iWasteCollectorService.getChecklist(requestDTO, orgId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationString);
                LOGGER.error("Error while fetching waste collector checklist: " + responseEntity.getBody());
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetching waste collector checklist: " + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getApplicableCharges/{orgId}/{vehicleId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getApplicableCharges(@RequestBody WSRequestDTO requestDTO,
            @PathVariable("vehicleId") Long vehicleId, @PathVariable("orgId") Long orgId) {
        ResponseEntity<?> responseEntity = null;
        try {
            String validationString = validateWasteCollectoeRequest(requestDTO);
            if (validationString.isEmpty()) {
                WSResponseDTO responseDTO = new WSResponseDTO();
                List<ChargeDetailDTO> responseList = iWasteCollectorService.getApplicableCharges(requestDTO, vehicleId,
                        orgId);
                responseDTO.setCharges(responseList);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationString);
                LOGGER.error("Error while fetching waste collector applicable charges: " + responseEntity.getBody());
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetching waste collector applicable charges: " + ex.getMessage(), ex);
        }
        return responseEntity;

    }

    @RequestMapping(value = "/getComParamDetById/{orgId}/{cpdId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getComParamDetById(@PathVariable("cpdId") Long cpdId, @PathVariable("orgId") Long orgId) {
        ResponseEntity<?> responseEntity = null;
        try {
            if (cpdId != null && orgId != null && cpdId != 0l && orgId != 0l) {
                String response = iWasteCollectorService.getComParamDetById(cpdId, orgId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(FAILED + "cpdId or orgId is coming as null or zero");
                LOGGER.error("Error while fetching organisation Name description for Waste collector: "
                        + responseEntity.getBody());
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
            LOGGER.error("Error while fetching organisation Name description for Waste collector: " + ex.getMessage(),
                    ex);
        }
        return responseEntity;

    }

    private String validateWasteCollectoeSaveRequest(final CollectorReqDTO collectorReqDTO) {
        final StringBuilder builder = new StringBuilder();
        if (collectorReqDTO != null) {
            final ApplicantDetailDTO applicantDetailDTO = collectorReqDTO.getApplicantDetailDto();
            final WasteCollectorDTO collectorDTO = collectorReqDTO.getCollectorDTO();
            if (applicantDetailDTO == null) {
                builder.append(MainetConstants.EstateBookingRest.APP_DTO_EMPTY);
            } else {
                if ((applicantDetailDTO.getApplicantTitle() == null)
                        || (applicantDetailDTO.getApplicantTitle() == 0L)) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.APP_DTO_EMPTY);
                }
                if ((applicantDetailDTO.getApplicantFirstName() == null)
                        || applicantDetailDTO.getApplicantFirstName().isEmpty()) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.FIRST_NAME);
                }
                if ((applicantDetailDTO.getApplicantLastName() == null)
                        || applicantDetailDTO.getApplicantLastName().isEmpty()) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.LAST_NAME);
                }
                if ((applicantDetailDTO.getGender() == null) || applicantDetailDTO.getGender().equals("0")) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.GEN_EMPTY);
                }
                if ((applicantDetailDTO.getMobileNo() == null) || applicantDetailDTO.getMobileNo().isEmpty()) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.MOBILE_NO);
                }
                if ((applicantDetailDTO.getAreaName() == null) || applicantDetailDTO.getAreaName().isEmpty()) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.ADDRESS);
                }

                if ((applicantDetailDTO.getPinCode() == null) || applicantDetailDTO.getPinCode().isEmpty()) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.PIN_NO);
                }
            }
            if (collectorDTO == null) {
                builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.WasteCollector);
            } else {
                if ((collectorDTO.getCapacity() == null) || (collectorDTO.getCapacity() == 0d)) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.CAPACITY);
                }
                if ((collectorDTO.getLocAddress() == null) || (collectorDTO.getLocAddress().isEmpty())) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.CONSTRUCTION_SITE);
                }
                if ((collectorDTO.getLocationId() == null) || (collectorDTO.getLocationId() == 0L)) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.LOCATION);
                }
                if ((collectorDTO.getVehicleType() == null) || (collectorDTO.getVehicleType() == 0L)) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.VEHICLE);
                }

                if ((collectorDTO.getNoTrip() == null) || (collectorDTO.getNoTrip() == 0L)) {
                    builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.NO_OF_TRIP);
                }
            }
            if ((collectorReqDTO.getDocumentList() != null) && !collectorReqDTO.getDocumentList().isEmpty()) {
                for (final DocumentDetailsVO doc : collectorReqDTO.getDocumentList()) {
                    if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
                        if (doc.getDocumentByteCode() == null) {
                            builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.UPLOAD_DOCS);
                            break;
                        }
                    }
                }
            }

            if (collectorReqDTO.getPayAmount() == null) {
                builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.PAY_AMOUNTS);
            }
            if (collectorReqDTO.getServiceId() == null) {
                builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.SERVICE_EMPTY);
            }
            if (collectorReqDTO.getDeptId() == null) {
                builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.DEPT_EMPTY);
            }

        } else {
            builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.COLLECTOR_DTO_EMPTY);

        }

        return builder.toString();
    }

    private String validateWasteCollectoeRequest(final WSRequestDTO requestDTO) {
        final StringBuilder builder = new StringBuilder();
        if (requestDTO != null) {
            if ((requestDTO.getModelName() == null) || (requestDTO.getModelName().isEmpty())) {
                builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.REQUEST_DTO_MODEL_NAME);
            }
        } else {
            builder.append(MainetConstants.SolidWasteManagement.SolidWasteManagementRest.REQUEST_DTO_EMPTY);
        }
        return builder.toString();
    }
}
