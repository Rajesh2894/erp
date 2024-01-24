package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PropertyNoticeService;

@Component
@Scope("session")
public class PropertyDemandNoticeGenerationModel extends AbstractFormModel {

    @Autowired
    private PropertyNoticeService propertyNoticeService;
    
    @Autowired
    private IFinancialYearService iFinancialYear;

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
    	
    	
    	
    	 if (specialNotGenSearchDto.getNoticeType() == null || specialNotGenSearchDto.getNoticeType() <= 0) {
            addValidationError("Please select notice type.");
         } else {
             if (specialNotGenSearchDto.getSpecNotSearchType().equals("SM")) {
                 if ((specialNotGenSearchDto.getPropertyNo() == null
                         || specialNotGenSearchDto.getPropertyNo().isEmpty())
                         && (specialNotGenSearchDto.getOldPropertyNo() == null
                                 || specialNotGenSearchDto.getOldPropertyNo().isEmpty())) {
                    addValidationError("Please enter valid property number or Old property number.");
                 }
 			} else if (specialNotGenSearchDto.getSpecNotSearchType().equals("AL")) {
 				if ((specialNotGenSearchDto.getLocId() == null
 						|| specialNotGenSearchDto.getLocId() <= 0)
 						&& (specialNotGenSearchDto.getAssWard1() == null
 								|| specialNotGenSearchDto.getAssWard1() <= 0)
 						&& (specialNotGenSearchDto.getAssdUsagetype1() == null
 								|| specialNotGenSearchDto.getAssdUsagetype1() <= 0)
 						&& (specialNotGenSearchDto.getFromAmount() <= 0)
 								&& (specialNotGenSearchDto.getToAmount() <= 0)
 								&& (specialNotGenSearchDto.getParshadAssWard1() == null
 								|| specialNotGenSearchDto.getParshadAssWard1() <= 0)) {
                    addValidationError("Please select any mandatory search criteria.");
                 } 
             }
    	
         }
    	
    	if(hasValidationErrors()) {
    		return false;
    	}
		List<NoticeGenSearchDto> notGenShowList;
		notGenShowList = propertyNoticeService.fetchPropertyAfterDueDate(specialNotGenSearchDto,
				UserSession.getCurrent().getOrganisation().getOrgid());
		
		Long finYearId = iFinancialYear.getFinanceYearId(new Date());
		FinancialYear currentFinYear = iFinancialYear.getFinincialYearsById(finYearId, UserSession.getCurrent().getOrganisation().getOrgid());
		List<String> alreadyGenList = propertyNoticeService.fetchPropertyDemandNoticeofCurrentYear(specialNotGenSearchDto.getNoticeType(),
				UserSession.getCurrent().getOrganisation().getOrgid(), currentFinYear.getFaFromDate(),
				currentFinYear.getFaToDate());
		
		List<NoticeGenSearchDto> noticeNotGenList = new ArrayList<NoticeGenSearchDto>();
		notGenShowList.forEach( notGen ->{
			List<String> existList = alreadyGenList.stream().filter( a-> a.equals(notGen.getPropertyNo())).collect(Collectors.toList());
			if(existList.isEmpty()) {
				noticeNotGenList.add(notGen);
			} else if (specialNotGenSearchDto.getSpecNotSearchType().equals("SM")) {
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
						MainetConstants.ENV_ASCL)) {
					addValidationError(getAppSession().getMessage("property.already.demand.notice") + " "
							+ (StringUtils.isNotBlank(specialNotGenSearchDto.getPropertyNo()) ? specialNotGenSearchDto.getPropertyNo()
									: specialNotGenSearchDto.getOldPropertyNo()));
				} else {
					addValidationError("Already demand notice generated");
				}

			}
		});
		if(hasValidationErrors()) {
    		return false;
    	}
		setNotGenSearchDtoList(noticeNotGenList);
    	if(CollectionUtils.isEmpty(noticeNotGenList)) {
    		if(specialNotGenSearchDto.getSpecNotSearchType().equals("SM")) {
    			addValidationError("Property number is not valid");
    		}else {
    			addValidationError("No record found");
    		}
    		return false;
    	}
    	
    	if(CollectionUtils.isNotEmpty(notGenShowList) && specialNotGenSearchDto.getSpecNotSearchType().equals("SM")) {
    		NoticeGenSearchDto noticeGenSearchDto = notGenShowList.get(0);
    		if(StringUtils.equals(MainetConstants.FlagY,noticeGenSearchDto.getCurrentBillNotExistFlag())) {
    			addValidationError("Current Year Bill Doesn't Exist");
    			return false;
    		}
    		if(StringUtils.equals(MainetConstants.FlagY,noticeGenSearchDto.getDueDateNotCrossFlag())) {
    			addValidationError("Due date not yet be crossed");
    			return false;
    		}
    	}
    	
        Optional<NoticeGenSearchDto> data = notGenShowList.stream().filter(dto -> "Y".equals(dto.getGenNotCheck()))
                .findAny();
        if (data == null || !data.isPresent()) {
            addValidationError("Please select atleast one property.");
            return false;
        }
        Organisation orgid = UserSession.getCurrent().getOrganisation();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        new Thread(() -> propertyNoticeService.saveDemandAndWarrantNoticeGeneration(getNotGenSearchDtoList(),
                orgid.getOrgid(), empId, getSpecialNotGenSearchDto().getNoticeType())).start();
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
