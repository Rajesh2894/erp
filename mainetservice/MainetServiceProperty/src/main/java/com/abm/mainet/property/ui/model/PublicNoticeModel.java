package com.abm.mainet.property.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PublicNoticeDto;

@Component
@Scope("session")
public class PublicNoticeModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private PublicNoticeDto publicNoticeDto = new PublicNoticeDto();

    public PublicNoticeDto getPublicNoticeDto() {
        return publicNoticeDto;
    }

    public void setPublicNoticeDto(PublicNoticeDto publicNoticeDto) {
        this.publicNoticeDto = publicNoticeDto;
    }
}