/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.service.IAssetRegisterUploadService;
import com.abm.mainet.asset.service.IChartOfDepreciationMasterService;
import com.abm.mainet.asset.service.IInformationService;
import com.abm.mainet.asset.ui.dto.AssetRegisterUploadDto;
import com.abm.mainet.asset.ui.dto.AssetUploadErrorDetailDto;
import com.abm.mainet.asset.ui.dto.ITAssetRegisterUploadDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author satish.rathore
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AssetRegisterUploadModel extends AbstractFormModel {

    /**
     * 
     */
    @Autowired
    private IInformationService informationService;
    @Autowired
    private IAssetRegisterUploadService assetRegisterUploadService;
    @Resource
    private TbDepartmentService iTbDepartmentService;
    @Resource
    private TbAcVendormasterService vendorMasterService;
    @Autowired
    private IChartOfDepreciationMasterService cdmService;
    @Resource
    private ILocationMasService iLocationMasService;

    private static final long serialVersionUID = -1574056490502826546L;

    private List<AssetRegisterUploadDto> astRegUploadDtoList = new ArrayList<>();;
     
    private List<ITAssetRegisterUploadDto> itAssetRegisterUploadDto = new ArrayList<>();;
   

	// give exported excel file name.
    private String uploadFileName;
    private List<AssetUploadErrorDetailDto> errDetails;
    // flag for successful create or update AssetExcelupload records.
    private String successFlag;
    private List<AssetRegisterUploadDto> astUploadDto;
    private String ipMacAddress;
    private List<ITAssetRegisterUploadDto> itAstUploadDto;
    public List<ITAssetRegisterUploadDto> getItAstUploadDto() {
		return itAstUploadDto;
	}

	public void setItAstUploadDto(List<ITAssetRegisterUploadDto> itAstUploadDto) {
		this.itAstUploadDto = itAstUploadDto;
	}

	/**
     * @return the ipMacAddress
     */
    public String getIpMacAddress() {
        return ipMacAddress;
    }

    /**
     * @param ipMacAddress the ipMacAddress to set
     */
    public void setIpMacAddress(String ipMacAddress) {
        this.ipMacAddress = ipMacAddress;
    }

    /**
     * @return the uploadFileName
     */
    public String getUploadFileName() {
        return uploadFileName;
    }

    /**
     * @param uploadFileName the uploadFileName to set
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * @return the astRegUploadDtoList
     */
    public List<AssetRegisterUploadDto> getAstRegUploadDtoList() {
        return astRegUploadDtoList;
    }

    /**
     * @param astRegUploadDtoList the astRegUploadDtoList to set
     */
    public void setAstRegUploadDtoList(List<AssetRegisterUploadDto> astRegUploadDtoList) {
        this.astRegUploadDtoList = astRegUploadDtoList;
    }

    /**
     * @return the errDetails
     */
    public List<AssetUploadErrorDetailDto> getErrDetails() {
        return errDetails;
    }

    /**
     * @param errDetails the errDetails to set
     */
    public void setErrDetails(List<AssetUploadErrorDetailDto> errDetails) {
        this.errDetails = errDetails;
    }

    /**
     * @return the successFlag
     */
    public String getSuccessFlag() {
        return successFlag;
    }

    /**
     * @param successFlag the successFlag to set
     */
    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    /**
     * @return the astUploadDto
     */
    public List<AssetRegisterUploadDto> getAstUploadDto() {
        return astUploadDto;
    }

    /**
     * @param astUploadDto the astUploadDto to set
     */
    public void setAstUploadDto(List<AssetRegisterUploadDto> astUploadDto) {
        this.astUploadDto = astUploadDto;
    }
   

    public List<ITAssetRegisterUploadDto> getItAssetRegisterUploadDto() {
		return itAssetRegisterUploadDto;
	}

	public void setItAssetRegisterUploadDto(List<ITAssetRegisterUploadDto> itAssetRegisterUploadDto) {
		this.itAssetRegisterUploadDto = itAssetRegisterUploadDto;
	}

	@Override
    public boolean saveForm() {
        boolean uploadStatus = false;
        /* Task #5318 */
        String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        if(UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
        	 uploadStatus = assetRegisterUploadService.saveUploadData(getAstUploadDto().stream().parallel(), orgId,
                     getIpMacAddress(), empId, ulbName, UserSession.getCurrent().getModuleDeptCode());
        }else {
        	 uploadStatus = assetRegisterUploadService.saveUploadITAsssetData(getItAstUploadDto().stream().parallel(), orgId,
                     getIpMacAddress(), empId, ulbName, UserSession.getCurrent().getModuleDeptCode());
        }
       
        return uploadStatus;

    }

    public List<ITAssetRegisterUploadDto> itAssetprepareDto(List<ITAssetRegisterUploadDto> dtos, Long orgId, Long langID,
            final List<AssetUploadErrorDetailDto> errorDetails) {
        List<ITAssetRegisterUploadDto> astList = new ArrayList<>();
        try {
            Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
			/* List<TbDepartment> departmentsList = iTbDepartmentService.findAll(); */
            List<LookUp> astClsList = CommonMasterUtility.getListLookup(
                    UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "ASC"
                            : "ISC",
                    organisation);
            List<LookUp> astTypList = CommonMasterUtility.getListLookup(
                    UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "ACL"
                            : "ICL",
                    organisation);            
            final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    AccountConstants.AC.getValue(), PrefixConstants.VSS, langID.intValue(),
                    organisation);
            final Long vendorStatus = lookUpVendorStatus.getLookUpId();
            List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(orgId, vendorStatus);
            List<LookUp> acquiMethod = CommonMasterUtility.getLookUps(
                    UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "AQM"
                            : "IQM",
                    organisation);
            final List<LookUp> astStatusList = CommonMasterUtility.getLookUps(MainetConstants.AssetManagement.ASSET_STATUS_PREFIX,
                    organisation);
          List<LookUp> ramSizeList = CommonMasterUtility.getLookUps("IRM", organisation);
            List<LookUp> processorList = CommonMasterUtility.getLookUps("IPR", organisation);
            List<LookUp> osList = CommonMasterUtility.getLookUps("IOS", organisation);
            List<LookUp> screenList = CommonMasterUtility.getLookUps("ISZ", organisation);
            List<LookUp> hardDiskList = CommonMasterUtility.getLookUps("IHD", organisation);
            List<LookUp> modeOfPaymentList = CommonMasterUtility.getLookUps("PAY", organisation);
            List<LookUp> countryOfOrigniList = CommonMasterUtility.getLevelData("CAS",1 ,organisation);

            if (dtos != null && !dtos.isEmpty()) {
                // this will work if your are not getting value using User session if you use user session it gives you exception
                // i used ordered for each method because i need row number where the problem occur
                dtos.parallelStream().parallel().forEachOrdered(detDto -> {
                    // for (AssetRegisterUploadDto detDto : dtos) {
                    
                    
                    if (StringUtils.isNotEmpty(detDto.getAssetClass2())) {
                        List<LookUp> astTypList1 = astTypList.stream()
                                .filter(clList -> clList != null
                                        && clList.getDescLangFirst().equalsIgnoreCase(detDto.getAssetClass2()))
                                .collect(Collectors.toList());
                        if (astTypList1 != null && !astTypList1.isEmpty()) {
                            detDto.setAssetClass2Id(astTypList1.get(0).getLookUpId());
                            detDto.setAstClassCode(astTypList1.get(0).getLookUpCode());
                        }
                    }
					
					
					if (detDto.getAssetClass2Id() != null) {
						
						for (LookUp astClassification : astClsList) {
							List<LookUp> astTypLis = astTypList.stream()
							.filter(i -> i != null
									&& i.getLookUpId() == detDto.getAssetClass2Id())
							.collect(Collectors.toList());
							if (astTypLis != null && !astTypLis.isEmpty()) {
								detDto.setAssetClassId(astClassification.getLookUpId());
						}
						}
					}
                    if (StringUtils.isNotEmpty(detDto.getFromWhomAcquired())) {
                        List<TbAcVendormaster> vendorList1 = vendorList.stream()
                                .filter(vendor -> vendor != null
                                        && vendor.getVmVendorname().trim().equalsIgnoreCase(detDto.getFromWhomAcquired().trim()))
                                .collect(Collectors.toList());
                        if (vendorList1 != null && !vendorList1.isEmpty())
                            detDto.setVenderId(vendorList1.get(0).getVmVendorid());
                    }

                    if (StringUtils.isNotEmpty(detDto.getAcquisitionMethod())) {
                        List<LookUp> acquiMethod1 = acquiMethod.stream()
                                .filter(clList -> clList != null
                                        && clList.getDescLangFirst().equalsIgnoreCase(detDto.getAcquisitionMethod()))
                                .collect(Collectors.toList());
                        if (acquiMethod1 != null && !acquiMethod1.isEmpty()) {
                            detDto.setAcquisiMethodId(acquiMethod1.get(0).getLookUpId());
                        }

                    }
                    if(StringUtils.isNotEmpty(detDto.getRamSize())) {
                    	List<LookUp> ram = ramSizeList.stream().filter(i -> i!= null  && i.getDescLangFirst().equalsIgnoreCase(detDto.getRamSize())).collect(Collectors.toList());
                    	if (ram != null && !ram.isEmpty()) {
                            detDto.setRamSizeId(ram.get(0).getLookUpId());
                        }
                    }
					if (StringUtils.isNotEmpty(detDto.getProcessor())) {
						List<LookUp> pro = processorList.stream()
								.filter(i -> i != null && i.getDescLangFirst().equalsIgnoreCase(detDto.getProcessor()))
								.collect(Collectors.toList());
						if (pro != null && !pro.isEmpty()) {
							detDto.setProcessorId(pro.get(0).getLookUpId());
						}
					}
					if (StringUtils.isNotEmpty(detDto.getScreenSize())) {
						List<LookUp> scr = screenList.stream()
								.filter(i -> i != null && i.getDescLangFirst().equalsIgnoreCase(detDto.getScreenSize()))
								.collect(Collectors.toList());
						if (scr != null && !scr.isEmpty()) {
							detDto.setScreenSizeId(scr.get(0).getLookUpId());
						}
					}
					if (StringUtils.isNotEmpty(detDto.getOsName())) {
						List<LookUp> os = osList.stream()
								.filter(i -> i != null && i.getDescLangFirst().equalsIgnoreCase(detDto.getOsName()))
								.collect(Collectors.toList());
						if (os != null && !os.isEmpty()) {
							detDto.setOsNameId(os.get(0).getLookUpId());
						}
					}
					if (StringUtils.isNotEmpty(detDto.getHardDiskSize())) {
						List<LookUp> hd = hardDiskList.stream().filter(
								i -> i != null && i.getDescLangFirst().equalsIgnoreCase(detDto.getHardDiskSize()))
								.collect(Collectors.toList());
						if (hd != null && !hd.isEmpty()) {
							detDto.setHardDiskSizeId(hd.get(0).getLookUpId());
						}
					}
					if (StringUtils.isNotEmpty(detDto.getModeOfPayment())) {
						List<LookUp> payMode = modeOfPaymentList.stream().filter(
								i -> i != null && i.getDescLangFirst().equalsIgnoreCase(detDto.getModeOfPayment()))
								.collect(Collectors.toList());
						if (payMode != null && !payMode.isEmpty()) {
							detDto.setModeOfPaymentId(payMode.get(0).getLookUpId());
						}
					}
					if (StringUtils.isNotEmpty(detDto.getCountryOfOrigin1())) {
						List<LookUp> cof = countryOfOrigniList.stream().filter(
								i -> i != null && i.getDescLangFirst().equalsIgnoreCase(detDto.getCountryOfOrigin1()))
								.collect(Collectors.toList());
						if (cof != null && !cof.isEmpty()) {
							detDto.setCountryOfOrigin1Id(cof.get(0).getLookUpId());
						}
					}
                    detDto.setAssetStatus(astStatusList.get(0).getLookUpId());
                    astList.add(detDto);
                });
            }
        } catch (Exception e) {
            AssetUploadErrorDetailDto errDetails = new AssetUploadErrorDetailDto();
            errDetails.setAssetType(UserSession.getCurrent().getModuleDeptCode());
            errDetails.setFileName(this.getUploadFileName());
            errDetails.setErrDescription("It might be Some prefix issue or department issue possible so kindly check it");
            errDetails.setErrData("Configuration Problem Please reslove and Try Again");
            errDetails.setOrgId(orgId);
            errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            errDetails.setCreationDate(new Date());
            // errDetails.setLgIpMac(Utility.getClientIpAddress(request));
            errorDetails.add(errDetails);
            logger.error("Configuration Problem Please reslove and Try Again");
        }
        return astList;

    }

    public List<AssetRegisterUploadDto> prepareDto(List<AssetRegisterUploadDto> dtos, Long orgId, Long langID,
            final List<AssetUploadErrorDetailDto> errorDetails) {
        List<AssetRegisterUploadDto> astList = new ArrayList<>();
        try {
            Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
            List<TbDepartment> departmentsList = iTbDepartmentService.findAll();
            List<LookUp> astClsList = CommonMasterUtility.getListLookup(
                    UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "ASC"
                            : "ISC",
                    organisation);
            List<LookUp> astTypList = CommonMasterUtility.getListLookup(
                    UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "ACL"
                            : "ICL",
                    organisation);
            final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    AccountConstants.AC.getValue(), PrefixConstants.VSS, langID.intValue(),
                    organisation);
            final Long vendorStatus = lookUpVendorStatus.getLookUpId();
            List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(orgId, vendorStatus);
            List<LookUp> acquiMethod = CommonMasterUtility.getLookUps(
                    UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "AQM"
                            : "IQM",
                    organisation);
            List<LookUp> asetGroupList = CommonMasterUtility.getLookUps("ASG", organisation);
            List<LookUp> astTypeList = CommonMasterUtility.getLookUps("TNG", organisation);
            List<TbLocationMas> locList = iLocationMasService.fillAllActiveLocationByOrgId(orgId);
            final List<LookUp> astStatusList = CommonMasterUtility.getLookUps(MainetConstants.AssetManagement.ASSET_STATUS_PREFIX,
                    organisation);
            List<LookUp> lookuplist3 = CommonMasterUtility.getLookUps("UOA", organisation);
            List<LookUp> lookuplist4 = CommonMasterUtility.getLookUps("UOL", organisation);
            List<LookUp> lookuplist5 = CommonMasterUtility.getLookUps("UOV", organisation);
            if (dtos != null && !dtos.isEmpty()) {
                // this will work if your are not getting value using User session if you use user session it gives you exception
                // i used ordered for each method because i need row number where the problem occur
                dtos.parallelStream().parallel().forEachOrdered(detDto -> {
                    // for (AssetRegisterUploadDto detDto : dtos) {
                    if (StringUtils.isNotEmpty(detDto.getDepartment())) {
                        List<TbDepartment> departmentsList1 = departmentsList.stream()
                                .filter(dep -> dep != null && dep.getDpDeptdesc().equalsIgnoreCase(detDto.getDepartment()))
                                .collect(Collectors.toList());
                        if (departmentsList1 != null && !departmentsList1.isEmpty()) {
                            detDto.setDepId(departmentsList1.get(0).getDpDeptid());
                        } else {
                            List<TbDepartment> departmentsList2 = departmentsList.stream()
                                    .filter(dep -> dep != null
                                            && dep.getDpDeptcode()
                                                    .equalsIgnoreCase(UserSession.getCurrent().getModuleDeptCode()))
                                    .collect(Collectors.toList());
                            detDto.setDepId(departmentsList2.get(0).getDpDeptid());

                        }
                    }
                    if (StringUtils.isNotEmpty(detDto.getAssetClass1())) {
                        List<LookUp> astClsList1 = astClsList.stream()
                                .filter(clList -> clList != null
                                        && clList.getDescLangFirst().equalsIgnoreCase(detDto.getAssetClass1()))
                                .collect(Collectors.toList());
                        if (astClsList1 != null && !astClsList1.isEmpty())
                            detDto.setAssetClassId(astClsList1.get(0).getLookUpId());
                    }
                    if (StringUtils.isNotEmpty(detDto.getAssetClass2()) && detDto.getAssetClassId() != null) {
                        List<LookUp> astTypList1 = astTypList.stream()
                                .filter(clList -> clList != null
                                        && clList.getDescLangFirst().equalsIgnoreCase(detDto.getAssetClass2()))
                                .collect(Collectors.toList());
                        if (astTypList1 != null && !astTypList1.isEmpty()) {
                            detDto.setAssetClass2Id(astTypList1.get(0).getLookUpId());
                            detDto.setAstClassCode(astTypList1.get(0).getLookUpCode());
                        }
                    }
                    if (detDto.getDeprApplicable() != null && detDto.getDeprApplicable().trim().equalsIgnoreCase("YES")) {
                        if (detDto.getAssetClass2Id() != null) {
                            Long acquiMode = cdmService.findAllByOrgIdAstCls(orgId, detDto.getAssetClass2Id()).get(0)
                                    .getLookUpId();
                            if (acquiMode != null)
                                detDto.setAcquiModeId(acquiMode);
                        }
                    }
                    if (StringUtils.isNotEmpty(detDto.getFromWhomAcquired())) {
                        List<TbAcVendormaster> vendorList1 = vendorList.stream()
                                .filter(vendor -> vendor != null
                                        && vendor.getVmVendorname().trim().equalsIgnoreCase(detDto.getFromWhomAcquired().trim()))
                                .collect(Collectors.toList());
                        if (vendorList1 != null && !vendorList1.isEmpty())
                            detDto.setVenderId(vendorList1.get(0).getVmVendorid());
                    }

                    if (StringUtils.isNotEmpty(detDto.getAcquisitionMethod())) {
                        List<LookUp> acquiMethod1 = acquiMethod.stream()
                                .filter(clList -> clList != null
                                        && clList.getDescLangFirst().equalsIgnoreCase(detDto.getAcquisitionMethod()))
                                .collect(Collectors.toList());
                        if (acquiMethod1 != null && !acquiMethod1.isEmpty()) {
                            detDto.setAcquisiMethodId(acquiMethod1.get(0).getLookUpId());
                        }

                    }

                    if (StringUtils.isNotEmpty(detDto.getAssetGroup())) {
                        if (asetGroupList != null && !asetGroupList.isEmpty()) {
                            List<LookUp> asetGroupList1 = asetGroupList.stream()
                                    .filter(clList -> clList != null
                                            && clList.getDescLangFirst().equalsIgnoreCase(detDto.getAssetGroup()))
                                    .collect(Collectors.toList());
                            if (asetGroupList1 != null && !asetGroupList1.isEmpty()) {
                                detDto.setAssetGroupId(asetGroupList1.get(0).getLookUpId());
                            }
                        }

                    }
                    if (StringUtils.isNotEmpty(detDto.getAssetType())) {
                        if (astTypeList != null && !astTypeList.isEmpty()) {
                            List<LookUp> astTypeList1 = astTypeList.stream()
                                    .filter(clList -> clList != null
                                            && clList.getDescLangFirst().equalsIgnoreCase(detDto.getAssetType()))
                                    .collect(Collectors.toList());
                            if (astTypeList1 != null && !astTypeList1.isEmpty()) {
                                detDto.setAssetTypeId(astTypeList1.get(0).getLookUpId());
                            }
                        }
                    }
                    if (StringUtils.isNotEmpty(detDto.getLocation())) {
                        List<TbLocationMas> locList1 = locList.stream()
                                .filter(clList -> clList != null
                                        && clList.getLocNameEng().equalsIgnoreCase(detDto.getLocation()))
                                .collect(Collectors.toList());
                        if (locList1 != null && !locList1.isEmpty()) {
                            detDto.setLocationId(locList1.get(0).getLocId());

                        }
                    }

                    detDto.setAssetStatus(astStatusList.get(0).getLookUpId());
                    if (lookuplist3 != null && !lookuplist3.isEmpty()) {
                        List<LookUp> lookuplistUOA = lookuplist3.stream()
                                .filter(kl -> kl.getDescLangFirst() != null
                                        && kl.getDescLangFirst().equalsIgnoreCase("Square Meter"))
                                .collect(Collectors.toList());
                        if (lookuplistUOA != null && !lookuplistUOA.isEmpty()) {
                            if (detDto.getTotalArea() != null) {
                                detDto.setAreaUnit(lookuplistUOA.get(0).getLookUpId());
                            }
                            if (detDto.getCarpetArea() != null) {
                                detDto.setCarpetUnit(lookuplistUOA.get(0).getLookUpId());
                            }
                        }
                    }
                    if (lookuplist4 != null && !lookuplist4.isEmpty()) {
                        List<LookUp> lookuplistUOL = lookuplist4.stream()
                                .filter(kl -> kl.getDescLangFirst() != null && kl.getDescLangFirst().equalsIgnoreCase("Meter"))
                                .collect(Collectors.toList());
                        if (lookuplistUOL != null && !lookuplistUOL.isEmpty()) {
                            if (detDto.getLength() != null) {
                                detDto.setLengthUnit(lookuplistUOL.get(0).getLookUpId());
                            }
                            if (detDto.getBreadth() != null) {
                                detDto.setBreadthUnit(lookuplistUOL.get(0).getLookUpId());
                            }
                            if (detDto.getHeight() != null) {
                                detDto.setHeightUnit(lookuplistUOL.get(0).getLookUpId());
                            }
                        }

                    }
                    if (lookuplist5 != null && !lookuplist5.isEmpty()) {
                        List<LookUp> lookuplistUOV = lookuplist5.stream()
                                .filter(kl -> kl.getDescLangFirst() != null
                                        && kl.getDescLangFirst().equalsIgnoreCase("Cubic Meter"))
                                .collect(Collectors.toList());
                        if (lookuplistUOV != null && !lookuplistUOV.isEmpty()) {
                            if (detDto.getVolume() != null) {
                                detDto.setVolumeUnit(lookuplistUOV.get(0).getLookUpId());
                            }
                        }
                    }
                    astList.add(detDto);
                });
            }
        } catch (Exception e) {
            AssetUploadErrorDetailDto errDetails = new AssetUploadErrorDetailDto();
            errDetails.setAssetType(UserSession.getCurrent().getModuleDeptCode());
            errDetails.setFileName(this.getUploadFileName());
            errDetails.setErrDescription("It might be Some prefix issue or department issue possible so kindly check it");
            errDetails.setErrData("Configuration Problem Please reslove and Try Again");
            errDetails.setOrgId(orgId);
            errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            errDetails.setCreationDate(new Date());
            // errDetails.setLgIpMac(Utility.getClientIpAddress(request));
            errorDetails.add(errDetails);
            logger.error("Configuration Problem Please reslove and Try Again");
        }
        return astList;

    }

    public List<AssetUploadErrorDetailDto> validateDto(final List<AssetRegisterUploadDto> dtos,
            final AssetRegisterUploadModel model,
            final Long orgId, final Long empId, final List<AssetUploadErrorDetailDto> errorDetails, HttpServletRequest request) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        AssetUploadErrorDetailDto errDetails = null;
        /*
         * List<LookUp> asetGroupList = CommonMasterUtility.getLookUps("ASG", organisation); List<LookUp> astTypeList =
         * CommonMasterUtility.getLookUps("TNG", organisation);
         */
        // i used count start from 1 because in first row we have heading so the errors start from 2 row
        int columnCount = 1;
        if (dtos != null && !dtos.isEmpty()) {
            int countError = 0;
            for (AssetRegisterUploadDto detDto : dtos) {
                countError = 0;
                columnCount++;
                errDetails = new AssetUploadErrorDetailDto();
                String Errors = "Errors:-";
                if (StringUtils.isEmpty(detDto.getAssetName())) {
                    String astNameError = getAppSession().getMessage("ast.invalid.name");
                    countError++;
                    Errors = Errors + " | " + astNameError + " Row Number " + columnCount;
                }
                String astSerialNoError = null;
                if (StringUtils.isEmpty(detDto.getSerialNo())) {
                    countError++;
                    astSerialNoError = getAppSession().getMessage("ast.invalid.number");
                    Errors = Errors + " | " + astSerialNoError + " Row Number " + columnCount;
                    ;
                } else {
                    boolean check = informationService.isDuplicateSerialNo(orgId, detDto.getSerialNo(), null);
                    if (check) {
                        countError++;
                        astSerialNoError = getAppSession().getMessage("ast.duplicate.number");
                        Errors = Errors + " | " + astSerialNoError + " Row Number " + columnCount;
                        ;
                    }
                }
                if (StringUtils.isEmpty(detDto.getDiscription())) {
                    countError++;
                    String discriptionError = getAppSession().getMessage("ast.invalid.des");
                    Errors = Errors + " | " + discriptionError + " Row Number " + columnCount;
                    ;
                }
                if (StringUtils.isEmpty(detDto.getPurpose())) {
                    countError++;
                    String astPurposeError = getAppSession().getMessage("ast.invalid.pur");
                    Errors = Errors + " | " + astPurposeError + " Row Number " + columnCount;
                    ;
                }

                /*
                 * if (asetGroupList != null && !asetGroupList.isEmpty()) { if (StringUtils.isEmpty(detDto.getAssetGroup()) &&
                 * detDto.getAssetGroupId() == null) { countError++; String astAstGroupError =
                 * getAppSession().getMessage("ast.invalid.group"); Errors = Errors + " | " + astAstGroupError; } } if
                 * (astTypeList != null && !astTypeList.isEmpty()) { if (StringUtils.isEmpty(detDto.getAssetType()) &&
                 * detDto.getAssetTypeId() == null) { countError++; String astAstTypeError =
                 * getAppSession().getMessage("ast.invalid.assettype"); Errors = Errors + " | " + astAstTypeError; } }
                 */
                if (StringUtils.isEmpty(detDto.getAssetClass1()) && detDto.getAssetClassId() == null) {
                    countError++;
                    String astClassificationError = getAppSession().getMessage("ast.invalid.classification");
                    Errors = Errors + " | " + astClassificationError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getAssetClass2Id() == null
                        && detDto.getAssetClassId() != null) {
                    countError++;
                    String astClassError = getAppSession().getMessage("ast.invalid.class");
                    Errors = Errors + " | " + astClassError + " Row Number " + columnCount;
                    ;
                }
                if (StringUtils.isEmpty(detDto.getAcquisitionMethod()) && detDto.getAcquisiMethodId() == null) {
                    countError++;
                    String astAcquiError = getAppSession().getMessage("ast.invalid.acqui");
                    Errors = Errors + " | " + astAcquiError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getDateofAcquisition() == null) {
                    countError++;
                    String astDateAcquiError = getAppSession().getMessage("ast.invalid.date ");
                    Errors = Errors + " | " + astDateAcquiError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getCostOfAcquisition() == null) {
                    countError++;
                    String astCostAcquiError = getAppSession().getMessage("ast.invalid.costOf ");
                    Errors = Errors + " | " + astCostAcquiError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getWrittenDownValueTillDate() == null) {
                    countError++;
                    String astwrittenDownValueError = getAppSession().getMessage("ast.invalid.writtendown");
                    Errors = Errors + " | " + astwrittenDownValueError + " Row Number " + columnCount;
                    ;
                }
                if (StringUtils.isNotBlank(detDto.getDeprApplicable())) {
                    if (detDto.getDeprApplicable().equalsIgnoreCase("YES")) {
                        if (detDto.getLifeInYear() == null) {
                            countError++;
                            String usefullifeError = getAppSession().getMessage("ast.invalid.lifeyear");
                            Errors = Errors + " | " + usefullifeError + " Row Number " + columnCount;
                            ;
                        }
                        if (detDto.getAccuDepreciationPrevious() == null) {
                            countError++;
                            String accumulatedDepEr = getAppSession().getMessage("ast.invalid.accudepamt");
                            Errors = Errors + " | " + accumulatedDepEr + " Row Number " + columnCount;
                            ;
                        }
                        if (detDto.getLastDepreciationDate() == null) {
                            countError++;
                            String accumulatedDepDateEr = getAppSession().getMessage("ast.invalid.accudepDate");
                            Errors = Errors + " | " + accumulatedDepDateEr + " Row Number " + columnCount;
                            ;
                        }
                        if (detDto.getWrittenDownDate() != null && detDto.getLastDepreciationDate() != null) {
                            if (!detDto.getWrittenDownDate().equals(detDto.getLastDepreciationDate())) {
                                countError++;
                                String writtendowndate = getAppSession().getMessage("ast.invalid.writtendowndate");
                                Errors = Errors + " | " + writtendowndate + " Row Number " + columnCount;
                                ;
                            }
                        }

                    }
                }
                if (detDto.getWrittenDownDate() == null) {
                    countError++;
                    String writtendowndate = getAppSession().getMessage("ast.invalid.requirewrittendate");
                    Errors = Errors + " | " + writtendowndate + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getCostOfAcquisition() != null && detDto.getAccuDepreciationPrevious() != null) {
                    int check = detDto.getAccuDepreciationPrevious().compareTo(detDto.getCostOfAcquisition());
                    if (check == 1) {
                        countError++;
                        String costCompareError = getAppSession().getMessage("valid.dep.amount");
                        Errors = Errors + " | " + costCompareError + " Row Number " + columnCount;
                        ;
                    }
                }
                if (detDto.getDateofAcquisition() != null && detDto.getLastDepreciationDate() != null) {
                    if (detDto.getDateofAcquisition().after(detDto.getLastDepreciationDate())) {
                        countError++;
                        String costDateError = getAppSession().getMessage("valid.dep.date");
                        Errors = Errors + " | " + costDateError + " Row Number " + columnCount;
                        ;
                    }
                }
                if (StringUtils.isNotEmpty(detDto.getFromWhomAcquired()) && detDto.getVenderId() == null) {
                    countError++;
                    String venderError = getAppSession().getMessage("ast.invalid.vander");
                    Errors = Errors + " | " + venderError + " Row Number " + columnCount;
                    ;
                }
                if (StringUtils.isNotEmpty(detDto.getLocation()) && detDto.getLocationId() == null) {
                    countError++;
                    String locationError = getAppSession().getMessage("ast.invalid.location");
                    Errors = Errors + " | " + locationError + " Row Number " + columnCount;
                    ;
                }
                if (StringUtils.isBlank(detDto.getDeprApplicable())) {
                    countError++;
                    String astApplicableError = getAppSession().getMessage("ast.invalid.deprapplicable");
                    Errors = Errors + " | " + astApplicableError + " Row Number " + columnCount;
                    ;
                }

                if (countError > 0) {
                    errDetails.setAssetType(UserSession.getCurrent().getModuleDeptCode());
                    errDetails.setFileName(model.getUploadFileName());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.NULL_CHECK);
                    errDetails.setErrData(Errors);
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(empId);
                    errDetails.setCreationDate(new Date());
                    errDetails.setLgIpMac(Utility.getClientIpAddress(request));
                    errorDetails.add(errDetails);
                    continue;
                }

            }
        }
        return errorDetails;
    }

    public List<AssetUploadErrorDetailDto> iTAssetvalidateDto(final List<ITAssetRegisterUploadDto> dtos,
            final AssetRegisterUploadModel model,
            final Long orgId, final Long empId, final List<AssetUploadErrorDetailDto> errorDetails, HttpServletRequest request) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        AssetUploadErrorDetailDto errDetails = null;
        /*
         * List<LookUp> asetGroupList = CommonMasterUtility.getLookUps("ASG", organisation); List<LookUp> astTypeList =
         * CommonMasterUtility.getLookUps("TNG", organisation);
         */
        // i used count start from 1 because in first row we have heading so the errors start from 2 row
        int columnCount = 1;
        if (dtos != null && !dtos.isEmpty()) {
            int countError = 0;
            for (ITAssetRegisterUploadDto detDto : dtos) {
                countError = 0;
                columnCount++;
                errDetails = new AssetUploadErrorDetailDto();
                String Errors = "Errors:-";
                
                String astSerialNoError = null;
                if (StringUtils.isEmpty(detDto.getSerialNo())) {
					/*
					 * countError++; astSerialNoError =
					 * getAppSession().getMessage("ast.invalid.number"); Errors = Errors + " | " +
					 * astSerialNoError + " Row Number " + columnCount; ;
					 */
                	//Registered serialNo is not mandatory
                } else {
                    boolean check = informationService.isDuplicateSerialNo(orgId, detDto.getSerialNo(), null);
                    if (check) {
                        countError++;
                        astSerialNoError = getAppSession().getMessage("ast.duplicate.reg.number");
                        Errors = Errors + " | " + astSerialNoError + " Row Number " + columnCount;
                        ;
                    }
                }
                if (detDto.getAssetClassId() == null) {
                    countError++;
                    String astClassificationError = getAppSession().getMessage("ast.invalid.classification");
                    Errors = Errors + " | " + astClassificationError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getPurchaseOrderNo() == null) {
                    countError++;
                    String astClassificationError = getAppSession().getMessage("asset.purchase.purchaseOrderNo");
                    Errors = Errors + " | " + astClassificationError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getAssetClass2Id() == null
                        && detDto.getAssetClassId() != null) {
                    countError++;
                    String astClassError = getAppSession().getMessage("ast.invalid.class");
                    Errors = Errors + " | " + astClassError + " Row Number " + columnCount;
                    ;
                }
                if (StringUtils.isEmpty(detDto.getAcquisitionMethod()) && detDto.getAcquisiMethodId() == null) {
                    countError++;
                    String astAcquiError = getAppSession().getMessage("ast.invalid.purchase");
                    Errors = Errors + " | " + astAcquiError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getDateOfAcquisition() == null) {
                    countError++;
                    String astDateAcquiError = getAppSession().getMessage("ast.invalid.date.purchase ");
                    Errors = Errors + " | " + astDateAcquiError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getAssetModelIdentifier() == null) {
                    countError++;
                    String e = getAppSession().getMessage("ast.invalid.serialNo");
                    Errors = Errors + " | " + e + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getCostOfAcquisition() == null) {
                    countError++;
                    String astCostAcquiError = getAppSession().getMessage("ast.invalid.Purchase.value");
                    Errors = Errors + " | " + astCostAcquiError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getDateOfAcquisition() != null) {
                	if(detDto.getDateOfAcquisition().after(new Date())) {
                		countError++;
                        String astCostAcquiError = getAppSession().getMessage("ast.invalid.Purchase.DateOfAcquisition");
                        Errors = Errors + " | " + astCostAcquiError + " Row Number " + columnCount;
                        ;
                	}
                	if(detDto.getWarrantyTillDate() !=null) {
                		if(detDto.getDateOfAcquisition().after(detDto.getWarrantyTillDate())) {
                     		countError++;
                             String astCostAcquiError = getAppSession().getMessage("asset.invalid.purchase.warrantytilldate");
                             Errors = Errors + " | " + astCostAcquiError + " Row Number " + columnCount;
                             ;
                     	}
                	}
                    
                }
                if (detDto.getManufacturingYear() != null) {
                	if(detDto.getManufacturingYear().after(new Date())) {
                		countError++;
                        String astCostAcquiError = getAppSession().getMessage("ast.invalid.manufacturingYear");
                        Errors = Errors + " | " + astCostAcquiError + " Row Number " + columnCount;
                        ;
                	}
                	
                	 if (detDto.getDateOfAcquisition() != null) {
                     	if(detDto.getManufacturingYear().after(detDto.getDateOfAcquisition())) {
                     		countError++;
                             String astCostAcquiError = getAppSession().getMessage("asset.manifYear.purchaseDate");
                             Errors = Errors + " | " + astCostAcquiError + " Row Number " + columnCount;
                             ;
                     	}
                         
                     } 
                }
                
                if (StringUtils.isNotBlank(detDto.getIsServiceAplicable())) {
                    if (detDto.getIsServiceAplicable().equalsIgnoreCase("YES")) {
                        if (detDto.getServiceProvider() == null) {
                            countError++;
                            String serviceProviderError = getAppSession().getMessage("ast.invalid.serviceProvider");
                            Errors = Errors + " | " + serviceProviderError + " Row Number " + columnCount;
                            ;
                        }
                        if(detDto.getServiceStartDate() != null ) {
                        	if(detDto.getServiceStartDate().after(new Date())) {
                        		countError++;
                                String astCostAcquiError = getAppSession().getMessage("ast.invalid.serviceStartDate");
                                Errors = Errors + " | " + astCostAcquiError + " Row Number " + columnCount;
                                ;
                        	}	
                        }
                        if (detDto.getServiceStartDate() != null && detDto.getServiceExpiryDate()!=null) {
                        	//need to add validation for dates
                        	if (detDto.getServiceStartDate().after(detDto.getServiceExpiryDate())) {
                                countError++;
                                String serviceDateError = getAppSession().getMessage("asset.service.vldn.startDateAndExpiryDate");
                                Errors = Errors + " | " + serviceDateError + " Row Number " + columnCount;
                                ;
                            }
                        }
                    }
                }
                if (StringUtils.isNotEmpty(detDto.getFromWhomAcquired()) && detDto.getVenderId() == null) {
                    countError++;
                    String venderError = getAppSession().getMessage("ast.invalid.vandor.name");
                    Errors = Errors + " | " + venderError + " Row Number " + columnCount;
                    ;
                }
                if (detDto.getCountryOfOrigin1() !=null && detDto.getCountryOfOrigin1Id() == null) {
                    countError++;
                    String RamSizeError = getAppSession().getMessage("ast.invalid.contry.origin");
                    Errors = Errors + " | " + RamSizeError + " Row Number " + columnCount;
                    
                }
                if (detDto.getRamSize() !=null && detDto.getRamSizeId() == null) {
                    countError++;
                    String RamSizeError = getAppSession().getMessage("ast.invalid.ramSize");
                    Errors = Errors + " | " + RamSizeError + " Row Number " + columnCount;
                    
                }
                if (detDto.getProcessor()!=null && detDto.getProcessorId() == null) {
                    countError++;
                    String e = getAppSession().getMessage("ast.invalid.Processor");
                    Errors = Errors + " | " + e + " Row Number " + columnCount;
                    
                }
                if (detDto.getOsName() !=null && detDto.getOsNameId() == null) {
                    countError++;
                    String e = getAppSession().getMessage("ast.invalid.osName");
                    Errors = Errors + " | " + e + " Row Number " + columnCount;
                    
                }
                if (detDto.getScreenSize() != null && detDto.getScreenSizeId() == null) {
                    countError++;
                    String e = getAppSession().getMessage("ast.invalid.screenSize");
                    Errors = Errors + " | " + e + " Row Number " + columnCount;
                    
                }
                if (detDto.getHardDiskSize() != null && detDto.getHardDiskSizeId() == null) {
                    countError++;
                    String e = getAppSession().getMessage("ast.invalid.hardDisk");
                    Errors = Errors + " | " + e + " Row Number " + columnCount;
                    
                }
                if (detDto.getModeOfPayment() != null && detDto.getModeOfPaymentId() == null) {
                    countError++;
                    String e = getAppSession().getMessage("ast.invalid.modeOfPayment");
                    Errors = Errors + " | " + e + " Row Number " + columnCount;
                    
                }
                
               
                if (countError > 0) {
                    errDetails.setAssetType(UserSession.getCurrent().getModuleDeptCode());
                    errDetails.setFileName(model.getUploadFileName());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.NULL_CHECK);
                    errDetails.setErrData(Errors);
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(empId);
                    errDetails.setCreationDate(new Date());
                    errDetails.setLgIpMac(Utility.getClientIpAddress(request));
                    errorDetails.add(errDetails);
                    continue;
                }

            }
        }
        return errorDetails;
    }

}
