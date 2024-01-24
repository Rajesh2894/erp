/**
 * 
 */
package com.abm.mainet.workManagement.roadcutting.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.roadcutting.domain.RoadCuttingEntity;
import com.abm.mainet.workManagement.roadcutting.domain.RoadRouteDetailEntity;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadRouteDetailsDto;

/**
 * @author satish.rathore
 *
 */
@Component
public class RoadCuttingMapper {

	public static RoadCuttingEntity dtoToEntitymapper(RoadCuttingDto dto) {

		RoadCuttingEntity entity = new RoadCuttingEntity();
		/* applicant Information */
		if (dto.getRcId() != null) {
			entity.setRcId(dto.getRcId());
		}
		entity.setApplicantCompName1(dto.getApplicantCompName1());
		entity.setCompanyAddress1(dto.getCompanyAddress1());
		entity.setPersonName1(dto.getPersonName1());
		entity.setPersonAddress1(dto.getPersonAddress1());
		entity.setFaxNumber1(dto.getFaxNumber1());
		entity.setTelephoneNo1(dto.getTelephoneNo1());
		entity.setPersonMobileNo1(dto.getPersonMobileNo1());
		entity.setPersonEmailId1(dto.getPersonEmailId1());
		entity.setAlterContact1(dto.getAlterContact1());
		/* local office detail */
		entity.setCompanyName2(dto.getCompanyName2());
		entity.setCompanyAddress2(dto.getCompanyAddress2());
		entity.setPersonName2(dto.getPersonName2());
		entity.setPersonAddress2(dto.getPersonAddress2());
		entity.setFaxNumber2(dto.getFaxNumber2());
		entity.setTelephoneNo2(dto.getTelephoneNo2());
		entity.setPersonMobileNo2(dto.getPersonMobileNo2());
		entity.setPersonEmailId2(dto.getPersonEmailId2());
		entity.setAlterContact2(dto.getAlterContact2());
		/* contractor datails */
		entity.setContractorName(dto.getContractorName());
		entity.setContractorAddress(dto.getContractorAddress());
		entity.setContracterContactPerMobileNo(dto.getContracterContactPerMobileNo());
		entity.setContractorEmailId(dto.getContractorEmailId());
		entity.setContractorContactPerName(dto.getContractorContactPerName());
		entity.setTotalCostOfproject(dto.getTotalCostOfproject());
		entity.setEstimteForRoadDamgCharge(dto.getEstimteForRoadDamgCharge());
		entity.setApmApplicationId(dto.getApplicationId());
		entity.setRefId(dto.getReferenceId());
		entity.setCodWard1(dto.getCodWard1());
		entity.setCodWard2(dto.getCodWard2());
		entity.setCodWard3(dto.getCodWard3());
		entity.setCodWard4(dto.getCodWard4());
		entity.setCodWard5(dto.getCodWard5());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreatedDate(dto.getCreatedDate());
		entity.setUpdatedDate(dto.getUpdatedDate());
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setOrgId(dto.getOrgId());
		entity.setLgIpMac(dto.getLgIpMac());
		entity.setLgIpMacUpd(dto.getLgIpMacUpd());
		/** Applicant Info*/
		entity.setApplicantAddress(dto.getApplicantDetailDto().getBuildingName());
		entity.setApplicantAreaName(dto.getApplicantDetailDto().getAreaName());
		entity.setApplicantHouseNo(dto.getApplicantDetailDto().getHouseNumber());
		if(StringUtils.isNotBlank(dto.getApplicantDetailDto().getMobileNo()) && !"null".equalsIgnoreCase(dto.getApplicantDetailDto().getMobileNo()))
		  entity.setApplicantMobileNo(Long.parseLong(dto.getApplicantDetailDto().getMobileNo()));
		else
			entity.setApplicantMobileNo(null);
		entity.setApplicantName(dto.getApplicantDetailDto().getApplicantFirstName());
		entity.setApplicantMName(dto.getApplicantDetailDto().getApplicantMiddleName());
		entity.setApplicantLName(dto.getApplicantDetailDto().getApplicantLastName());
		entity.setPurpose(dto.getPurpose());
		entity.setPurposeValue(dto.getPurposeValue());
		
		// Asign engineer
		entity.setRcAssignEng(dto.getRcAssignEng());
		Organisation org = new Organisation();
		org.setOrgid(dto.getOrgId());
		if (dto.getRoadList() != null && !dto.getRoadList().isEmpty()) {
			final List<RoadRouteDetailEntity> detailList = new ArrayList<>();
			dto.getRoadList().stream().forEachOrdered(dtos -> {
				RoadRouteDetailEntity roaddetailsEntity = new RoadRouteDetailEntity();
				roaddetailsEntity.setBreadth(dtos.getBreadth());
				roaddetailsEntity.setLength(dtos.getLength());
				roaddetailsEntity.setHeight(dtos.getHeight());
				roaddetailsEntity.setDaimeter(dtos.getDiameter());
				roaddetailsEntity.setNumbers(dtos.getNumbers());
				if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
					roaddetailsEntity.setRcdQuantiy(dtos.getQuantity());
					roaddetailsEntity.setTypeOfTechnology(dtos.getTypeOfTechnology());
				}
				roaddetailsEntity.setRoadRouteDesc(dtos.getRoadRouteDesc());
				roaddetailsEntity.setRoadType(dtos.getRoadType());
				roaddetailsEntity.setCreatedBy(dto.getCreatedBy());
				roaddetailsEntity.setCreatedDate(dto.getCreatedDate());
				roaddetailsEntity.setUpdatedBy(dto.getUpdatedBy());
				roaddetailsEntity.setUpdatedDate(dto.getUpdatedDate());
				roaddetailsEntity.setLgIpMac(dto.getLgIpMac());
				roaddetailsEntity.setLgIpMacUpd(dto.getLgIpMacUpd());
				roaddetailsEntity.setRoadAreaName(dtos.getRoadAreaName());
				roaddetailsEntity.setOrgId(dto.getOrgId());
				roaddetailsEntity.setCodZoneward1(dtos.getCodZoneward1());
                roaddetailsEntity.setCodZoneward2(dtos.getCodZoneward2());
                roaddetailsEntity.setCodZoneward3(dtos.getCodZoneward3());
                roaddetailsEntity.setCodZoneward4(dtos.getCodZoneward4());
                roaddetailsEntity.setCodZoneward5(dtos.getCodZoneward5());
				roaddetailsEntity.setRcdEndpoint(dtos.getRcdEndpoint());
				roaddetailsEntity.setRcdCompletionDate(dtos.getRcdCompletionDate());
				roaddetailsEntity.setRcdStartlogitude(dtos.getRcdStartlogitude());
				roaddetailsEntity.setRcdStartlatitude(dtos.getRcdStartlatitude());
				roaddetailsEntity.setRcdEndlogitude(dtos.getRcdEndlogitude());
				roaddetailsEntity.setRcdEndlatitude(dtos.getRcdEndlatitude());
				roaddetailsEntity.setRcdQuantiy(dtos.getQuantity());

				if (dtos.getRcdId() != null) {
					roaddetailsEntity.setRcdId(dtos.getRcdId());
				}
				roaddetailsEntity.setRoadCuttingEntity(entity);
				detailList.add(roaddetailsEntity);
			});
			entity.setRoadRouteList(detailList);
		}
		return entity;

	}

	public static RoadCuttingDto entityToDtoMapper(RoadCuttingEntity entity) {

		RoadCuttingDto dto = new RoadCuttingDto();

		/* applicant Information */
		dto.setRcId(entity.getRcId());
		dto.setApplicantCompName1(entity.getApplicantCompName1());
		dto.setCompanyAddress1(entity.getCompanyAddress1());
		dto.setPersonName1(entity.getPersonName1());
		dto.setPersonAddress1(entity.getPersonAddress1());
		dto.setFaxNumber1(entity.getFaxNumber1());
		dto.setTelephoneNo1(entity.getTelephoneNo1());
		dto.setPersonMobileNo1(entity.getPersonMobileNo1());
		dto.setPersonEmailId1(entity.getPersonEmailId1());
		/* local office detail */
		dto.setCompanyName2(entity.getCompanyName2());
		dto.setCompanyAddress2(entity.getCompanyAddress2());
		dto.setPersonName2(entity.getPersonName2());
		dto.setPersonAddress2(entity.getPersonAddress2());
		dto.setFaxNumber2(entity.getFaxNumber2());
		dto.setTelephoneNo2(entity.getTelephoneNo2());
		dto.setPersonMobileNo2(entity.getPersonMobileNo2());
		dto.setPersonEmailId2(entity.getPersonEmailId2());
		/* contractor datails */
		dto.setContractorName(entity.getContractorName());
		dto.setContractorAddress(entity.getContractorAddress());
		dto.setContracterContactPerMobileNo(entity.getContracterContactPerMobileNo());
		dto.setContractorEmailId(entity.getContractorEmailId());
		dto.setContractorContactPerName(entity.getContractorContactPerName());
		dto.setTotalCostOfproject(entity.getTotalCostOfproject());
		dto.setEstimteForRoadDamgCharge(entity.getEstimteForRoadDamgCharge());

		dto.setApplicationId(entity.getApmApplicationId());
		dto.setCodWard1(entity.getCodWard1());
		dto.setCodWard2(entity.getCodWard2());
		dto.setCodWard3(entity.getCodWard3());
		dto.setCodWard4(entity.getCodWard4());
		dto.setCodWard5(entity.getCodWard5());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setUpdatedBy(entity.getUpdatedBy());
		dto.setUpdatedDate(entity.getUpdatedDate());
		dto.setLgIpMacUpd(entity.getLgIpMacUpd());
		dto.setLgIpMac(entity.getLgIpMac());
		dto.setOrgId(entity.getOrgId());
		// Asign engineer
		dto.setRcAssignEng(entity.getRcAssignEng());
		
		dto.getApplicantDetailDto().setApplicantFirstName(entity.getApplicantName());
		dto.getApplicantDetailDto().setApplicantMiddleName(entity.getApplicantMName());
		dto.getApplicantDetailDto().setApplicantLastName(entity.getApplicantLName());
		dto.getApplicantDetailDto().setAreaName(entity.getApplicantAreaName());
		dto.getApplicantDetailDto().setBuildingName(entity.getApplicantAddress());
		dto.getApplicantDetailDto().setHouseNumber(entity.getApplicantHouseNo());
		dto.getApplicantDetailDto().setMobileNo(""+entity.getApplicantMobileNo());
		dto.setPurpose(entity.getPurpose());
		dto.setPurposeValue(entity.getPurposeValue());
		dto.setReferenceId(entity.getRefId());
		Organisation org = new Organisation();
		org.setOrgid(dto.getOrgId());
		if (entity.getRoadRouteList() != null && !entity.getRoadRouteList().isEmpty()) {
			final List<RoadRouteDetailsDto> detailList = new ArrayList<>();
			entity.getRoadRouteList().stream().forEachOrdered(detail -> {
				RoadRouteDetailsDto detDto = new RoadRouteDetailsDto();
				detDto.setRcdId(detail.getRcdId());
				detDto.setBreadth(detail.getBreadth());
				detDto.setLength(detail.getLength());
				detDto.setHeight(detail.getHeight());
				// TODO: Correct spelling mistake
				detDto.setDiameter(detail.getDaimeter());
				detDto.setNumbers(detail.getNumbers());
				if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
					detDto.setQuantity(detail.getRcdQuantiy());
					detDto.setTypeOfTechnology(detail.getTypeOfTechnology());
				}
				detDto.setRoadRouteDesc(detail.getRoadRouteDesc());
				detDto.setRoadType(detail.getRoadType());

				detDto.setRcdEndpoint(detail.getRcdEndpoint());
				detDto.setRcdCompletionDate(detail.getRcdCompletionDate());
				detDto.setRcdStartlogitude(detail.getRcdStartlogitude());
				detDto.setRcdStartlatitude(detail.getRcdStartlatitude());
				detDto.setRcdEndlogitude(detail.getRcdEndlogitude());
				detDto.setRcdEndlatitude(detail.getRcdEndlatitude());
				detDto.setQuantity(detail.getRcdQuantiy());
				detDto.setRoadAreaName(detail.getRoadAreaName());

				detDto.setCreatedBy(entity.getCreatedBy());
				detDto.setCreatedDate(entity.getCreatedDate());
				detDto.setUpdatedBy(entity.getUpdatedBy());
				detDto.setUpdatedDate(entity.getUpdatedDate());
				detDto.setLgIpMacUpd(entity.getLgIpMacUpd());
				detDto.setLgIpMac(entity.getLgIpMac());
				detDto.setOrgId(entity.getOrgId());
				
				detDto.setCodZoneward1(detail.getCodZoneward1());
				detDto.setCodZoneward2(detail.getCodZoneward2());
				detDto.setCodZoneward3(detail.getCodZoneward3());
				detDto.setCodZoneward4(detail.getCodZoneward4());
				detDto.setApmAppStatus(detail.getApmAppStatus());
				detDto.setRefId(detail.getRefId());
				
				detailList.add(detDto);
			});
			dto.setRoadList(detailList);
		}
		return dto;

	}

}
