package com.abm.mainet.property.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;

@Component
@Scope("session")
public class ViewPropertyDetailModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long deptId;
	private String intgrtionWithBP;// Integration with Building Permission
	private String assType;
	private String ownershipPrefix;
	private String ownershipTypeValue;
	private String landTypePrefix;
	private String assMethod;
	private ProperySearchDto searchDto = new ProperySearchDto();
	private List<LookUp> location = new ArrayList<>(0);
	private List<ProperySearchDto> searchDtoResult = new ArrayList<>();
	private List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto = new LinkedList<>();
	private Map<Long, String> financialYearMap = new LinkedHashMap<>();
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();
	private List<DocumentDetailsVO> updateDataEntryDocs = new ArrayList<>();
	private List<LookUp> mohalla = new ArrayList<>(0);
	private List<LookUp> blockStreet = new ArrayList<>(0);
	private List<LookUp> district = new ArrayList<>(0);
	private List<LookUp> tehsil = new ArrayList<>(0);
	private List<LookUp> village = new ArrayList<>(0);
	private List<TbBillMas> billMasList = new LinkedList<>();
	private List<LookUp> collectionDetails = new ArrayList<>(0);
	private Map<Long, List<DocumentDetailsVO>> appDocument = new LinkedHashMap<>();
	private List<NoticeMasterDto> noticeDetails = new ArrayList<>();
	private List<TbBillMas> authComBillList = null;
	private ArrayOfKhasraDetails arrayOfKhasraDetails = new ArrayOfKhasraDetails();
    private ArrayOfPlotDetails arrayOfPlotDetails = new ArrayOfPlotDetails();
    private ArrayOfDiversionPlotDetails arrayOfDiversionPlotDetails = new ArrayOfDiversionPlotDetails();
    private List<LookUp> parentPropLookupList = new ArrayList<>();
    private List<CFCAttachment> documentList = new ArrayList<>();
    
    // to get text description of the selected value of a dropdownList
    public void setDropDownValues(Organisation org) {
        this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(), org).getDescLangFirst());

        if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setProAssdRoadfactorDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
                            .getDescLangFirst());
        }

        this.setAssMethod(
                CommonMasterUtility.getDefaultValue(MainetConstants.Property.propPref.ASS, org).getLookUpCode());

        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode();
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentOwnerDtlDtoList()) {
            	if(dto.getGenderId() != null && dto.getGenderId() > 0) {
            		dto.setProAssGenderId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
            	}
            	if(dto.getRelationId() != null && dto.getRelationId() > 0) {
            		 dto.setProAssRelationId(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org)
                             .getDescLangFirst());
            	}
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }
        /*
         * if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase( MainetConstants.Property.MANUAL)) {
         * this.getSchedule().forEach(schedule -> { if (schedule.getLookUpId() == provisionalAssesmentMstDto.getAssLpYear()) {
         * this.getProvisionalAssesmentMstDto().setProAssLpYearDesc(schedule. getLookUpCode()); } }); }
         */
        this.getLocation().forEach(loca -> {
            if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                this.getProvisionalAssesmentMstDto().setLocationName(loca.getDescLangFirst());
            }
        });

        this.getDistrict().forEach(dis -> {
            if (Long.valueOf(dis.getLookUpId()).toString().equals(provisionalAssesmentMstDto.getAssDistrict())) {
                this.getProvisionalAssesmentMstDto().setAssDistrictDesc(dis.getDescLangFirst());
            }
        });

        this.getTehsil().forEach(teh -> {
            if (teh.getLookUpCode().equals(provisionalAssesmentMstDto.getAssTahasil())) {
                this.getProvisionalAssesmentMstDto().setAssTahasilDesc(teh.getDescLangFirst());
            }
        });

        this.getVillage().forEach(vil -> {
            if (vil.getLookUpCode().equals(provisionalAssesmentMstDto.getTppVillageMauja())) {
                this.getProvisionalAssesmentMstDto().setTppVillageMaujaDesc(vil.getDescLangFirst());
            }
        });

        for (LookUp moh : this.getMohalla()) {
            if (moh.getLookUpCode().equals(provisionalAssesmentMstDto.getMohalla())) {
                this.getProvisionalAssesmentMstDto().setMohallaDesc(moh.getDescLangFirst());
                break;
            }
        }

        for (LookUp sheet : this.getBlockStreet()) {
            if (sheet.getLookUpCode().equals(provisionalAssesmentMstDto.getAssStreetNo())) {
                this.getProvisionalAssesmentMstDto().setAssStreetNoDesc(sheet.getDescLangFirst());
                break;
            }
        }

        if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setProAssdRoadfactorDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
                            .getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssLandType() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setAssLandTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssLandType(), org)
                            .getDescLangFirst());

        }

        this.getProvisionalAssesmentMstDto().setAssWardDesc1(CommonMasterUtility
                .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1(), org).getDescLangFirst());

        if (provisionalAssesmentMstDto.getAssWard2() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc2(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard2(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard3() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc3(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard3(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard4() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc4(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard4(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard5() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc5(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard5(), org).getDescLangFirst());
        }
        
		if (provisionalAssesmentMstDto.getSurveyType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setSurveyTypeDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getSurveyType(), org)
							.getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType1() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType1Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType1(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType2() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType2Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType2(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType3() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType3Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType3(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType4() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType4Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType4(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType5() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType5Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType5(), org).getDescLangFirst());
		}
        for (ProvisionalAssesmentDetailDto detaildto : getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList()) {
            if (detaildto.getAssdBuildupArea() != null) {
                for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
                    if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                        detaildto.setProFaYearIdDesc(entry.getValue());
                    }
                    // SimpleDateFormat formatter = new
                    // SimpleDateFormat(MainetConstants.DATE_FRMAT);
                    // detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    // Defect_40635 Construction date need to remove depend on ULB Currently it is
                    // mandatory
                    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

                    if (detaildto.getAssdYearConstruction() != null) {
                        detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    } else {
                        detaildto.setAssdYearConstruction(new Date());
                        detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    }
                    if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
                    	if (detaildto.getFirstAssesmentDate() != null) {
                            detaildto.setFirstAssesmentStringDate(formatter.format(detaildto.getFirstAssesmentDate()));
                        } else {
                            detaildto.setFirstAssesmentDate(new Date());
                            detaildto.setFirstAssesmentStringDate(formatter.format(detaildto.getFirstAssesmentDate()));
                        }
                    }                   
                    detaildto.setProAssdUsagetypeDesc(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(),
                                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());

                    if (detaildto.getAssdUsagetype2() != null) {
                        detaildto.setProAssdUsagetypeDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype3() != null) {
                        detaildto.setProAssdUsagetypeDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype4() != null) {
                        detaildto.setProAssdUsagetypeDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype5() != null) {
                        detaildto.setProAssdUsagetypeDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getDescLangFirst());
                    }
                    if(detaildto.getAssdNatureOfproperty1() != null) {
                    	detaildto.setAssdNatureOfpropertyDesc1(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty1(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty2() != null) {
                        detaildto.setAssdNatureOfpropertyDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty2(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty3() != null) {
                        detaildto.setAssdNatureOfpropertyDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty3(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty4() != null) {
                        detaildto.setAssdNatureOfpropertyDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty4(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty5() != null) {
                        detaildto.setAssdNatureOfpropertyDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty5(), org).getDescLangFirst());
                    }if(detaildto.getAssdFloorNo() != null) {
                        detaildto.setProFloorNo(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org).getDescLangFirst());
                    }
                    detaildto.setProAssdConstruTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org).getDescLangFirst());

                    detaildto.setProAssdOccupancyTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org).getDescLangFirst());
                }
                for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : this.getProvAsseFactDtlDto()) {
                    if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                        provisionalAssesmentFactorDtlDto
                                .setProAssfFactorValueDesc(CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(
                                                provisionalAssesmentFactorDtlDto.getAssfFactorValueId(), org)
                                        .getDescLangFirst());
                        provisionalAssesmentFactorDtlDto.setProAssfFactorIdDesc(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorId(), org)
                                .getDescLangFirst());
                    }
                }
            }
        }
        // D#140426
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
	        List<String> usageTypeList = new ArrayList<>();
	        Set<String> unique= new HashSet<>();
	        provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach( det ->{
	        	if(det.getProAssdUsagetypeDesc() != null) {
	        		usageTypeList.add(det.getProAssdUsagetypeDesc());
	            	unique.add(det.getProAssdUsagetypeDesc());
	        	}
	        });
	        if(unique.size() == 1) {
	        	provisionalAssesmentMstDto.setProAssPropType(usageTypeList.get(0));
	        }else {
	        	provisionalAssesmentMstDto.setProAssPropType(MainetConstants.Property.MIXED_USAGE);
	        }
        }//end of D#140426
        setAddressDetails();
        formatDate();
    }
    
    private void setAddressDetails() {
        if (getProvisionalAssesmentMstDto().getProAsscheck().equalsIgnoreCase(MainetConstants.Y_FLAG)) {
            getProvisionalAssesmentMstDto().setAssCorrAddress(getProvisionalAssesmentMstDto().getAssAddress());
            getProvisionalAssesmentMstDto().setAssCorrPincode(getProvisionalAssesmentMstDto().getAssPincode());
            getProvisionalAssesmentMstDto().setAssCorrEmail(getProvisionalAssesmentMstDto().getAssEmail());
        }
    }
    
	private void formatDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		this.getProvisionalAssesmentMstDto()
				.setProAssAcqDateFormat(formatter.format(getProvisionalAssesmentMstDto().getAssAcqDate()));
		if(getProvisionalAssesmentMstDto().getReviseAssessmentDate() != null) {
			this.getProvisionalAssesmentMstDto().setReviseAssessmentDateFormat(
					formatter.format(getProvisionalAssesmentMstDto().getReviseAssessmentDate()));
		}
	}
    
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getIntgrtionWithBP() {
		return intgrtionWithBP;
	}

	public void setIntgrtionWithBP(String intgrtionWithBP) {
		this.intgrtionWithBP = intgrtionWithBP;
	}

	public String getAssType() {
		return assType;
	}

	public void setAssType(String assType) {
		this.assType = assType;
	}

	public String getOwnershipPrefix() {
		return ownershipPrefix;
	}

	public void setOwnershipPrefix(String ownershipPrefix) {
		this.ownershipPrefix = ownershipPrefix;
	}

	public String getOwnershipTypeValue() {
		return ownershipTypeValue;
	}

	public void setOwnershipTypeValue(String ownershipTypeValue) {
		this.ownershipTypeValue = ownershipTypeValue;
	}

	public String getLandTypePrefix() {
		return landTypePrefix;
	}

	public void setLandTypePrefix(String landTypePrefix) {
		this.landTypePrefix = landTypePrefix;
	}

	public ProperySearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(ProperySearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public List<LookUp> getLocation() {
		return location;
	}

	public void setLocation(List<LookUp> location) {
		this.location = location;
	}

	public List<ProperySearchDto> getSearchDtoResult() {
		return searchDtoResult;
	}

	public void setSearchDtoResult(List<ProperySearchDto> searchDtoResult) {
		this.searchDtoResult = searchDtoResult;
	}

	public List<ProvisionalAssesmentFactorDtlDto> getProvAsseFactDtlDto() {
		return provAsseFactDtlDto;
	}

	public void setProvAsseFactDtlDto(List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto) {
		this.provAsseFactDtlDto = provAsseFactDtlDto;
	}

	public Map<Long, String> getFinancialYearMap() {
		return financialYearMap;
	}

	public void setFinancialYearMap(Map<Long, String> financialYearMap) {
		this.financialYearMap = financialYearMap;
	}

	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}

	public List<DocumentDetailsVO> getUpdateDataEntryDocs() {
		return updateDataEntryDocs;
	}

	public void setUpdateDataEntryDocs(List<DocumentDetailsVO> updateDataEntryDocs) {
		this.updateDataEntryDocs = updateDataEntryDocs;
	}

	public List<LookUp> getMohalla() {
		return mohalla;
	}

	public void setMohalla(List<LookUp> mohalla) {
		this.mohalla = mohalla;
	}

	public List<LookUp> getBlockStreet() {
		return blockStreet;
	}

	public void setBlockStreet(List<LookUp> blockStreet) {
		this.blockStreet = blockStreet;
	}
	
	public List<LookUp> getDistrict() {
		return district;
	}

	public void setDistrict(List<LookUp> district) {
		this.district = district;
	}

	public List<LookUp> getTehsil() {
		return tehsil;
	}

	public void setTehsil(List<LookUp> tehsil) {
		this.tehsil = tehsil;
	}

	public List<LookUp> getVillage() {
		return village;
	}

	public void setVillage(List<LookUp> village) {
		this.village = village;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public List<LookUp> getCollectionDetails() {
		return collectionDetails;
	}

	public void setCollectionDetails(List<LookUp> collectionDetails) {
		this.collectionDetails = collectionDetails;
	}

	public Map<Long, List<DocumentDetailsVO>> getAppDocument() {
		return appDocument;
	}

	public void setAppDocument(Map<Long, List<DocumentDetailsVO>> appDocument) {
		this.appDocument = appDocument;
	}

	public List<NoticeMasterDto> getNoticeDetails() {
		return noticeDetails;
	}

	public void setNoticeDetails(List<NoticeMasterDto> noticeDetails) {
		this.noticeDetails = noticeDetails;
	}

	public List<TbBillMas> getAuthComBillList() {
		return authComBillList;
	}

	public void setAuthComBillList(List<TbBillMas> authComBillList) {
		this.authComBillList = authComBillList;
	}

	public ArrayOfKhasraDetails getArrayOfKhasraDetails() {
		return arrayOfKhasraDetails;
	}

	public void setArrayOfKhasraDetails(ArrayOfKhasraDetails arrayOfKhasraDetails) {
		this.arrayOfKhasraDetails = arrayOfKhasraDetails;
	}

	public ArrayOfPlotDetails getArrayOfPlotDetails() {
		return arrayOfPlotDetails;
	}

	public void setArrayOfPlotDetails(ArrayOfPlotDetails arrayOfPlotDetails) {
		this.arrayOfPlotDetails = arrayOfPlotDetails;
	}

	public ArrayOfDiversionPlotDetails getArrayOfDiversionPlotDetails() {
		return arrayOfDiversionPlotDetails;
	}

	public void setArrayOfDiversionPlotDetails(ArrayOfDiversionPlotDetails arrayOfDiversionPlotDetails) {
		this.arrayOfDiversionPlotDetails = arrayOfDiversionPlotDetails;
	}

	public String getAssMethod() {
		return assMethod;
	}

	public void setAssMethod(String assMethod) {
		this.assMethod = assMethod;
	}

	public List<LookUp> getParentPropLookupList() {
		return parentPropLookupList;
	}

	public void setParentPropLookupList(List<LookUp> parentPropLookupList) {
		this.parentPropLookupList = parentPropLookupList;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}
	
	
}
