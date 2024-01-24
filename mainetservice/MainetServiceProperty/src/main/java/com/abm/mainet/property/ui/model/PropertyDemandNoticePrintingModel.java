package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PropertyNoticeService;

@Component
@Scope("session")
public class PropertyDemandNoticePrintingModel extends AbstractFormModel {

    @Autowired
    private PropertyNoticeService propertyNoticeService;

    private static final long serialVersionUID = 2479397350651234861L;

    NoticeGenSearchDto specialNotGenSearchDto = new NoticeGenSearchDto();

    private List<NoticeGenSearchDto> notGenSearchDtoList = new ArrayList<>();

    private List<LookUp> location = new ArrayList<>(0);

    private List<LookUp> demandType = new ArrayList<>(0);

    @Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        switch (parentCode) {

        case "USA":
            return "specialNotGenSearchDto.assdUsagetype";

        case "WZB":
            return "specialNotGenSearchDto.assWard";

        default:
            return null;

        }
    }

    @Override
    public boolean saveForm() {
        Optional<NoticeGenSearchDto> data = getNotGenSearchDtoList().stream().filter(dto -> "Y".equals(dto.getGenNotCheck()))
                .findAny();
        if (data == null || !data.isPresent()) {
            addValidationError("Please select atleast one property.");
            return false;
        }
        Organisation orgid = UserSession.getCurrent().getOrganisation();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        propertyNoticeService.saveDemandAndWarrantNoticeGeneration(getNotGenSearchDtoList(),
                orgid.getOrgid(), empId, getSpecialNotGenSearchDto().getNoticeType());
        setSuccessMessage("Demand/Warrant notice generated successfully!");
        return true;
    }

    public NoticeGenSearchDto getSpecialNotGenSearchDto() {
        return specialNotGenSearchDto;
    }

    public void setSpecialNotGenSearchDto(NoticeGenSearchDto specialNotGenSearchDto) {
        this.specialNotGenSearchDto = specialNotGenSearchDto;
    }

    public List<NoticeGenSearchDto> getNotGenSearchDtoList() {
        return notGenSearchDtoList;
    }

    public void setNotGenSearchDtoList(List<NoticeGenSearchDto> notGenSearchDtoList) {
        this.notGenSearchDtoList = notGenSearchDtoList;
    }

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public List<LookUp> getDemandType() {
        return demandType;
    }

    public void setDemandType(List<LookUp> demandType) {
        this.demandType = demandType;
    }

}
