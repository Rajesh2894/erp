package com.abm.mainet.common.rest.ui.controller;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.domain.OrganisationEntity;
import com.abm.mainet.common.dto.TbOrganisationRest;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.ApplicationSession;

/**
 * Common REST Controller exposed for Organization related services
 * 
 * @author Harsha.Ramachandran
 * @since 22 June 2017
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL),
        @HttpMethodConstraint(value = "GET", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/tbOrganisation")
public class OrganisationRestController {

    @Autowired
    private IOrganisationService organisationService;

    @RequestMapping(value = "/postOrganisation", method = RequestMethod.POST, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> createOrganisation(@RequestBody TbOrganisationRest tbOrganisationRest) {
        ResponseEntity<?> responseEntity = null;
        try {

            OrganisationEntity org = new OrganisationEntity();
            org.setAppStartDate(tbOrganisationRest.getTranStartDate());
            if(tbOrganisationRest.getOLogo() != null) {
            org.setOLogo((tbOrganisationRest.getOLogo().getBytes()));
            }
            BeanUtils.copyProperties(tbOrganisationRest, org);
            OrganisationEntity orgSaved = organisationService.create(org);
            ApplicationSession appSession = ApplicationSession.getInstance();
            organisationService.createDefault(orgSaved, appSession, tbOrganisationRest);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(orgSaved);
        } catch (Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return responseEntity;
    }

    @RequestMapping(value = "/postUpdateOrganisation", method = RequestMethod.POST, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> updateOrganisation(@RequestBody TbOrganisationRest organisationRest) {
        ResponseEntity<?> responseEntity = null;
        try {
            OrganisationEntity organisationEntity = new OrganisationEntity();
            organisationEntity.setAppStartDate(organisationRest.getTranStartDate());
            if(organisationRest.getOLogo() != null) {
            organisationEntity.setOLogo((organisationRest.getOLogo().getBytes()));
            }
            BeanUtils.copyProperties(organisationRest, organisationEntity);
            OrganisationEntity entity = organisationService.create(organisationEntity);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (Exception exception) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        return responseEntity;
    }
}
