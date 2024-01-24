package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.NoticeGenSearchDto;

@Component
@Scope("session")
public class SpecialNoticeGenerationModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 8960542477073785349L;

    NoticeGenSearchDto specialNotGenSearchDto = new NoticeGenSearchDto();

    private List<NoticeGenSearchDto> notGenSearchDtoList = new ArrayList<>();

    private List<LookUp> location = new ArrayList<>(0);

    private Long deptId;

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

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

}
