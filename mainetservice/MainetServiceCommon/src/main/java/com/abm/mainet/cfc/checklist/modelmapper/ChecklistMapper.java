package com.abm.mainet.cfc.checklist.modelmapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.dto.ChecklistServiceDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.model.mapping.AbstractModelMapper;

/**
 * @author Lalit.Prusti
 *
 */
@Component
public class ChecklistMapper extends AbstractModelMapper {

    private ModelMapper modelMapper;

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ChecklistMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(
                MatchingStrategies.STANDARD);
    }

    public ChecklistServiceDTO mapChecklistStatusViewToChecklistServiceDTO(
            final ChecklistStatusView applicantDetail) {
        if (applicantDetail == null) {
            return null;
        }
        final ChecklistServiceDTO dto = map(applicantDetail,
                ChecklistServiceDTO.class);

        return dto;

    }

    public List<CFCAttachmentsDTO> mapCFCAttachmentToCFCAttachmentDTO(
            final List<CFCAttachment> cfcAttachment) {
        if (cfcAttachment == null) {
            return null;
        }
        final List<CFCAttachmentsDTO> cfcAttachmentsDTO = map(cfcAttachment,
                CFCAttachmentsDTO.class);

        return cfcAttachmentsDTO;
    }

}
