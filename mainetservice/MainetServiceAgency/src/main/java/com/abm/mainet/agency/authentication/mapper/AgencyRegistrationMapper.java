package com.abm.mainet.agency.authentication.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.agency.authentication.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.agency.authentication.dto.AgencyEmployeeResDTO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.model.mapping.AbstractModelMapper;

/**
 * @author Arun.Chavda
 *
 */

@Component
public class AgencyRegistrationMapper extends AbstractModelMapper {

    private ModelMapper modelMapper;

    @Override
    protected ModelMapper getModelMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * Mapping from 'AgencyEmployeeResDTO' to 'TbEmployee'
     * @param licenceRenewal
     */
    public Employee mapAgencyEmployeeReqDTOToTbEmployee(final AgencyEmployeeReqDTO agnEmployeeReqDTO) {

        if (agnEmployeeReqDTO == null) {
            return null;
        }
        final Employee responseDTO = map(agnEmployeeReqDTO, Employee.class);

        return responseDTO;
    }

    /**
     * Mapping from 'AgencyEmployeeResDTO' to 'TbEmployee'
     * @param licenceRenewal
     */
    public AgencyEmployeeResDTO mapTbEmployeeToAgencyEmployeeResDTO(final Employee employee) {

        if (employee == null) {
            return null;
        }

        final AgencyEmployeeResDTO responseDTO = map(employee, AgencyEmployeeResDTO.class);

        return responseDTO;
    }

    public CFCAttachmentsDTO mapCFCAttachmentToCFCAttachmentDTO(final CFCAttachment cfcAttachment) {

        if (cfcAttachment == null) {
            return null;
        }

        final CFCAttachmentsDTO cfcAttachmentsDTO = map(cfcAttachment, CFCAttachmentsDTO.class);

        return cfcAttachmentsDTO;
    }

    public Employee mapEmployeeDTOTOEmployee(final EmployeeDTO employeeDto) {

        if (employeeDto == null) {
            return null;
        }

        final Employee employee = map(employeeDto, Employee.class);

        return employee;
    }

    public CFCAttachment mapCFCAttachmentsDTOTOCFCAttachment(final CFCAttachmentsDTO attachmentsDTO) {

        if (attachmentsDTO == null) {
            return null;
        }

        final CFCAttachment attachment = map(attachmentsDTO, CFCAttachment.class);

        return attachment;
    }

}
