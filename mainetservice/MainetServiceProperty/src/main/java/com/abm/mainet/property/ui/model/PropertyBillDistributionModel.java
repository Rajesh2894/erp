package com.abm.mainet.property.ui.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PropertyMainBillService;

/**
 * @author cherupelli.srikanth
 * @since 07 May 2021
 */
@Component
@Scope("session")
public class PropertyBillDistributionModel extends AbstractFormModel {
	
	@Autowired
	private PropertyMainBillService propertyMainBillService;
	
	private static final long serialVersionUID = -8364336345353873376L;

	NoticeGenSearchDto specialNotGenSearchDto = new NoticeGenSearchDto();
	
	private List<NoticeGenSearchDto> notGenSearchDtoList = new ArrayList<>();
	
	private List<String> flatNoList;
	
	@Override
	public boolean saveForm() {
		boolean status = false;
		List<NoticeGenSearchDto> distributionBillList = notGenSearchDtoList.stream()
				.filter(searchDto -> StringUtils.isNotBlank(searchDto.getGenNotCheck())
						&& searchDto.getGenNotCheck().equals("Y"))
				.collect(Collectors.toList());
		
		if(CollectionUtils.isNotEmpty(notGenSearchDtoList) && CollectionUtils.isEmpty(distributionBillList)) {
			addValidationError("Please select atleast one property.");
		}
		if(CollectionUtils.isEmpty(notGenSearchDtoList)) {
			addValidationError("Please enter property no / select atleast one criteria for searching");
		}
		notGenSearchDtoList.forEach(searchDto -> {
			if (StringUtils.isNotBlank(searchDto.getGenNotCheck()) && searchDto.getGenNotCheck().equals("Y")) {
				if(searchDto.getBillDistribDate() == null) {
					addValidationError(getAppSession().getMessage(
							"please select bill distribution date against property no: " + searchDto.getPropertyNo()));
				}else if(Utility.compareDate(searchDto.getBillDistribDate(), Utility.stringToDate(searchDto.getBillDate()))) {
					addValidationError(getAppSession().getMessage(
							"Bill distribution date should be greater than bill generation date" + searchDto.getPropertyNo()));
				}
			}
		});
		if (hasValidationErrors()) {
			return false;
		}
		
		LookUp billDueDate = CommonMasterUtility.getValueFromPrefixLookUp("BDD", "BDC",
					UserSession.getCurrent().getOrganisation());
		distributionBillList.forEach(dto->{
			Date dueDate = null;
			LocalDate convertCreateDateToLocalDate = dto.getBillDistribDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
             dueDate = Date.from(
                    convertCreateDateToLocalDate.plusDays(Long.valueOf(billDueDate.getOtherField()))
                            .atStartOfDay(ZoneId.systemDefault()).toInstant());
             propertyMainBillService.updateServeDateAndDueDate(dto.getBillDistribDate(), dueDate, dto.getBmIdNo());
			
		});
		setSuccessMessage(ApplicationSession.getInstance().getMessage("Distribution date updated succesfully"));
		status = true;
		return status;
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

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}
	
	
	
}
