package com.abm.mainet.care.utility;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.abm.mainet.care.domain.CareDepartmentAction;
import com.abm.mainet.care.domain.CareFeedback;
import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.dto.CareFeedbackDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.DepartmentDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * 
 * @author sanket.joshi
 *
 */
public class CareUtility {

    public static CareRequest toCareRequest(CareRequestDTO crdto) {
        if (crdto == null)
            return null;
        CareRequest careRequest = new CareRequest();
        careRequest.setApplicationId(crdto.getApplicationId());
        careRequest.setComplaintId(crdto.getComplaintId());
        careRequest.setExtReferNumber(crdto.getExtReferNumber());
        careRequest.setOrgId(crdto.getOrgId());
        careRequest.setCreatedBy(crdto.getCreatedBy());
        careRequest.setCreatedDate(crdto.getCreatedDate());
        careRequest.setRequestType(crdto.getRequestType());
        careRequest.setDescription(crdto.getDescription());
        careRequest.setReferenceCategory(crdto.getReferenceCategory());
        careRequest.setReferenceMode(crdto.getReferenceMode());
        careRequest.setReferenceDate(crdto.getReferenceDate());
        careRequest.setDateOfRequest(crdto.getDateOfRequest());
        careRequest.setLandmark(crdto.getLandmark());
        careRequest.setLatitude(crdto.getLatitude());
        careRequest.setLongitude(crdto.getLongitude());
        careRequest.setWard1(crdto.getWard1());
        if (crdto.getApplnType() != null) {
            careRequest.setApplnType(crdto.getApplnType());
        }
        if (crdto.getWard2() != null) {
            careRequest.setWard2(crdto.getWard2());
        }
        if (crdto.getWard3() != null) {
            careRequest.setWard3(crdto.getWard3());
        }
        if (crdto.getWard4() != null) {
            careRequest.setWard4(crdto.getWard4());
        }
        if (crdto.getWard5() != null) {
            careRequest.setWard5(crdto.getWard5());
        }
        careRequest.setDistrict(crdto.getDistrict());
        careRequest.setResidentId(crdto.getResidentId());
        return careRequest;
    }

    public static LocationMasEntity toLocationMasEntityCommon(LocationDTO ldto) {
        if (ldto == null)
            return null;
        LocationMasEntity l = new LocationMasEntity();
        l.setLocId(ldto.getLocId());
        l.setLocNameEng(ldto.getLocNameEng());
        l.setLocNameReg(ldto.getLocNameReg());
        l.setPincode(ldto.getPincode());
        return l;
    }

    public static CareRequestDTO toCareRequestDTO(CareRequest cr) {
        if (cr == null)
            return null;
        CareRequestDTO crdto = new CareRequestDTO();
        Organisation org = new Organisation(cr.getOrgId());

        crdto.setId(cr.getId());
        crdto.setRequestType(cr.getRequestType());
        crdto.setDescription(cr.getDescription());
        crdto.setApplicationId(cr.getApplicationId());
        crdto.setExtReferNumber(cr.getExtReferNumber());
        crdto.setResidentId(cr.getResidentId());
        crdto.setComplaintId(cr.getComplaintId());
        crdto.setOrgId(cr.getOrgId());
        crdto.setCreatedDate(cr.getCreatedDate());
        crdto.setCreatedBy(cr.getCreatedBy());
        crdto.setModifiedDate(cr.getModifiedDate());
        crdto.setModifiedBy(cr.getModifiedBy());

        crdto.setDepartmentComplaint(cr.getDepartmentComplaint());
        String departmentDescById = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                .fetchDepartmentDescById(cr.getDepartmentComplaint());
        crdto.setDepartmentComplaintDesc(departmentDescById);
        /*D#123544- Addding regional lang departmentdescription for mobile*/
        Department department = ApplicationContextProvider.getApplicationContext().getBean(DepartmentJpaRepository.class)
        		.findDeptById(cr.getDepartmentComplaint());
        if(department!=null) {
        	crdto.setDepartmentComplaintDescReg(department.getDpNameMar());
        }

        if (cr.getApplnType().equalsIgnoreCase(MainetConstants.FlagC)) {
            crdto.setComplaintType(cr.getComplaintType());
        } else {
            crdto.setComplaintType(cr.getSmServiceId());
        }
        // only for Application type C
        if (cr.getApplnType().equalsIgnoreCase(MainetConstants.FlagC)) {
            ComplaintType complaintType = ApplicationContextProvider.getApplicationContext().getBean(IComplaintTypeService.class)
                    .findComplaintTypeById(cr.getComplaintType());
            if(null !=complaintType && null != complaintType.getComplaintDesc()){
                crdto.setComplaintTypeDesc(complaintType.getComplaintDesc());
                crdto.setComplaintTypeDescReg(complaintType.getComplaintDescreg());
                }
                /*D#123544- Addding regional lang complainttypedesc for mobile*/
                
        }
        crdto.setLocation(cr.getLocation().getLocId());

        // crdto.setDepartmentComplaintDesc(cr.getDepartmentComplaint().getDepartment().getDpDeptdesc());
        // crdto.setDepartmentComplaintDescReg(cr.getDepartmentComplaint().getDepartment().getDpNameMar());
        // crdto.setComplaintTypeDesc(cr.getComplaintType().getComplaintDesc());
        // crdto.setComplaintTypeDescReg(cr.getComplaintType().getComplaintDescreg());
        crdto.setLocationEngName(cr.getLocation().getLocNameEng());
        crdto.setLocationRegName(cr.getLocation().getLocNameReg());

        Long pincode = cr.getLocation().getPincode();
        crdto.setPincode((pincode != null) ? pincode.toString() : "");
        crdto.setReferenceCategory(cr.getReferenceCategory());
        if (cr.getReferenceCategory() != null) {
            crdto.setReferenceCategoryDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(cr.getReferenceCategory()).longValue(), org)
                            .getDescLangFirst());
            //#14867
            crdto.setReferenceCategoryDescReg(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(cr.getReferenceCategory()).longValue(), org)
                            .getDescLangSecond());
        }
        crdto.setReferenceMode(cr.getReferenceMode());
        if (cr.getReferenceMode() != null) {
            crdto.setReferenceModeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(Long.valueOf(cr.getReferenceMode()).longValue(), org).getDescLangFirst());
            //#14867
            crdto.setReferenceModeDescReg(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(Long.valueOf(cr.getReferenceMode()).longValue(), org).getDescLangSecond());
        	
        }
        crdto.setReferenceDate(cr.getReferenceDate());
        if (cr.getReferenceDate() != null) {
            String input = cr.getReferenceDate().toString();
            String output = input.substring(0, 10);
            crdto.setReferenceDateDesc(output);
        }
        // age of request
        Date currentdate = new Date();
        Long diff = Math.abs(currentdate.getTime() - cr.getDateOfRequest().getTime());
        Long ageOfRequest = diff / (24 * 60 * 60 * 1000);
        crdto.setAgeOfRequest(ageOfRequest);

        crdto.setDateOfRequest(cr.getDateOfRequest());
        crdto.setDateOfRequestDesc(cr.getDateOfRequest().toString());
        String landMark = cr.getLandmark();
        if (StringUtils.isNotEmpty(landMark) && landMark.endsWith(",")) {
            landMark = landMark.substring(0, landMark.length() - 1);
        }
        crdto.setLandmark(landMark);
        crdto.setLatitude(cr.getLatitude());
        crdto.setLongitude(cr.getLongitude());
        crdto.setWard1(cr.getWard1());
        
        //#135509,#142867
        if (crdto.getWard1() != null) {
            crdto.setWard1Desc(CommonMasterUtility.getHierarchicalLookUp(cr.getWard1(), cr.getOrgId()).getDescLangFirst());
            
            crdto.setWard1DescReg(CommonMasterUtility.getHierarchicalLookUp(cr.getWard1(),cr.getOrgId()).getDescLangSecond());            
        }
        crdto.setWard2(cr.getWard2());
        if (crdto.getWard2() != null) {
            crdto.setWard2Desc(CommonMasterUtility.getHierarchicalLookUp(cr.getWard2(), cr.getOrgId()).getDescLangFirst());
        
            crdto.setWard2DescReg(CommonMasterUtility.getHierarchicalLookUp(cr.getWard2(),cr.getOrgId()).getDescLangSecond());
        }
        crdto.setWard3(cr.getWard3());
        if (crdto.getWard3() != null) {
            crdto.setWard3Desc(CommonMasterUtility.getHierarchicalLookUp(cr.getWard3(), cr.getOrgId()).getDescLangFirst());
            
            crdto.setWard3DescReg(CommonMasterUtility.getHierarchicalLookUp(cr.getWard3(),cr.getOrgId()).getDescLangSecond());
            
        }
        crdto.setWard4(cr.getWard4());
        if (crdto.getWard4() != null) {
            crdto.setWard4Desc(CommonMasterUtility.getHierarchicalLookUp(cr.getWard4(), cr.getOrgId()).getDescLangFirst());
            
            crdto.setWard4DescReg(CommonMasterUtility.getHierarchicalLookUp(cr.getWard4(),cr.getOrgId()).getDescLangSecond());            
        }
        crdto.setWard5(cr.getWard5());
        if (crdto.getWard5() != null) {
            crdto.setWard5Desc(CommonMasterUtility.getHierarchicalLookUp(cr.getWard5(), cr.getOrgId()).getDescLangFirst());
      
            crdto.setWard5DescReg(CommonMasterUtility.getHierarchicalLookUp(cr.getWard5(),cr.getOrgId()).getDescLangSecond());
        }
        crdto.setApplnType(cr.getApplnType());
        crdto.setPrefixName(ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDepartmentPrefixName(cr.getDepartmentComplaint(), cr.getOrgId()));
        crdto.setDistrict(cr.getDistrict());
        return crdto;
    }

    @SuppressWarnings("unused")
    private static LocationDTO toLocationDTO(LocationMasEntity l) {
        if (l == null)
            return null;
        LocationDTO ldto = new LocationDTO();
        ldto.setLocId(l.getLocId());
        ldto.setLocNameEng(l.getLocNameEng());
        ldto.setLocNameReg(l.getLocNameReg());
        ;
        ldto.setPincode(l.getPincode());
        return ldto;
    }

    public static DepartmentComplaintDTO toDepartmentComplaintDTO(DepartmentComplaint dc) {
        DepartmentComplaintDTO dcdto = new DepartmentComplaintDTO();
        dcdto.setDeptCompId(dc.getDeptCompId());
        dcdto.setOrgId(dc.getOrgId());
        dcdto.setStatus(dc.getStatus());
        /*
         * if (dc.getComplaintTypes() != null) dc.getComplaintTypes().forEach(dct -> {
         * dcdto.addComplaintType(toDepartmentComplaintTypeDTO(dct)); });
         */
        dcdto.setDepartment(toDepartmentDTO(dc.getDepartment()));
        return dcdto;
    }

    public static DepartmentComplaintTypeDTO toDepartmentComplaintTypeDTO(ComplaintType dct) {
        DepartmentComplaintTypeDTO dctdto = new DepartmentComplaintTypeDTO();
        dctdto.setCompId(dct.getCompId());
        dctdto.setOrgId(dct.getOrgId());
        dctdto.setComplaintDesc(dct.getComplaintDesc());
        dctdto.setComplaintDescReg(dct.getComplaintDescreg());
        dctdto.setResidentId(dct.getResidentId());
        dctdto.setAmtDues(dct.getAmtDues());
        dctdto.setIsActive(dct.getIsActive());
        dctdto.setStatus(dct.getStatus());
        return dctdto;
    }

    public static DepartmentDTO toDepartmentDTO(Department d) {
        if (d == null)
            return null;
        DepartmentDTO ddto = new DepartmentDTO();
        ddto.setDpDeptid(d.getDpDeptid());
        ddto.setDpDeptcode(d.getDpDeptcode());
        ddto.setDpDeptdesc(d.getDpDeptdesc());
        ddto.setUserId(d.getUserId());
        ddto.setLangId(d.getLangId());
        ddto.setLmoddate(d.getLmoddate());
        ddto.setStatus(d.getStatus());
        ddto.setDpNameMar(d.getDpNameMar());
        ddto.setSubDeptFlg(d.getSubDeptFlg());
        ddto.setUpdatedBy(d.getUpdatedBy());
        ddto.setUpdatedDate(d.getUpdatedDate());
        ddto.setDpSmfid(d.getDpSmfid());
        ddto.setDpCheck(d.getDpCheck());
        ddto.setDpPrefix(d.getDpPrefix());
        ddto.setLgIpMac(d.getLgIpMac());
        ddto.setLgIpMacUpd(d.getLgIpMacUpd());
        return ddto;
    }

    public static LocationDTO toLocationMasEntityDTO(LocationMasEntity location, int lang) {
        if (location == null)
            return null;
        LocationDTO ldto = new LocationDTO();
        ldto.setLocId(location.getLocId());
        ldto.setLocNameEng(location.getLocNameEng());
        ldto.setLocNameReg(location.getLocNameReg());
        if (lang > 0) {
            // Language 1 for English, 2 for Regional
            String lname = (lang == 1) ? location.getLocNameEng() : (lang == 2) ? location.getLocNameReg() : "";
            ldto.setLocName(lname);
        }
        ldto.setPincode(location.getPincode());
        return ldto;
    }

    public static CareFeedback toCareFeedback(CareFeedbackDTO cfd) {
        if (cfd == null)
            return null;
        CareFeedback cf = new CareFeedback();
        cf.setId(cfd.getId());
        cf.setRatings(cfd.getRatings());
        cf.setTokenNumber(cfd.getTokenNumber());
        cf.setRatingsContent(cfd.getRatingsContent());
        cf.setRatingsStarCount(cfd.getRatingsStarCount());
        return cf;
    }

    public static WorkflowAction toAction(WorkflowTaskAction adto) {
        if (adto == null)
            return null;
        WorkflowAction action = new WorkflowAction();
        action.setId(adto.getId());
        action.setApplicationId(adto.getApplicationId());
        action.setDecision(adto.getDecision());
        action.setComments(adto.getComments());
        action.setOrgId(adto.getOrgId());
        action.setEmpId(adto.getEmpId());
        action.setEmpType(adto.getEmpType());
        action.setTaskId(adto.getTaskId());
        action.setTaskName(adto.getTaskName());
        action.setDateOfAction(adto.getDateOfAction());
        action.setCreatedDate(adto.getCreatedDate());
        action.setCreatedBy(adto.getCreatedBy());
        action.setModifiedDate(adto.getModifiedDate());
        action.setModifiedBy(adto.getModifiedBy());
        return action;
    }

    public static WorkflowTaskAction toActionDTO(WorkflowAction action) {
        if (action == null)
            return null;
        WorkflowTaskAction adto = new WorkflowTaskAction();
        adto.setId(action.getId());
        adto.setApplicationId(action.getApplicationId());
        adto.setDecision(action.getDecision());
        adto.setComments(action.getComments());
        adto.setOrgId(action.getOrgId());
        adto.setEmpId(action.getEmpId());
        adto.setEmpType(action.getEmpType());
        adto.setTaskId(action.getTaskId());
        adto.setTaskName(action.getTaskName());
        adto.setDateOfAction(action.getDateOfAction());
        adto.setCreatedDate(action.getCreatedDate());
        adto.setCreatedBy(action.getCreatedBy());
        adto.setModifiedDate(action.getModifiedDate());
        adto.setModifiedBy(action.getModifiedBy());
        return adto;
    }

    public static CareDepartmentAction toCareDepartmentAction(WorkflowTaskAction adto) {
        if (adto == null)
            return null;
        CareDepartmentAction careDepartmentAction = new CareDepartmentAction();

        if (adto.getForwardDepartment() != null && !adto.getForwardDepartment().isEmpty())
            careDepartmentAction.setDepartment(Long.parseLong(adto.getForwardDepartment()));
        if (adto.getForwardComplaintType() != null && !adto.getForwardComplaintType().isEmpty())
            careDepartmentAction.setComplaintType(Long.parseLong(adto.getForwardComplaintType()));
        if (adto.getForwardToEmployee() != null && !adto.getForwardToEmployee().isEmpty())
            careDepartmentAction.setForwardToEmployee(adto.getForwardToEmployee());
        if (adto.getReasonToForwardId() != null)
            careDepartmentAction.setReasonToForwardId(adto.getReasonToForwardId());
        careDepartmentAction.setForwardToEmployeeType(careDepartmentAction.getForwardToEmployeeType());
        careDepartmentAction.setCodIdOperLevel1(careDepartmentAction.getCodIdOperLevel1());
        careDepartmentAction.setCodIdOperLevel2(careDepartmentAction.getCodIdOperLevel2());
        careDepartmentAction.setCodIdOperLevel3(careDepartmentAction.getCodIdOperLevel3());
        careDepartmentAction.setCodIdOperLevel4(careDepartmentAction.getCodIdOperLevel4());
        careDepartmentAction.setCodIdOperLevel5(careDepartmentAction.getCodIdOperLevel5());
        careDepartmentAction.setOrgId(adto.getOrgId());
        careDepartmentAction.setCreatedBy(adto.getCreatedBy());
        careDepartmentAction.setCreatedDate(adto.getCreatedDate());
        return careDepartmentAction;
    }

    public static RequestDTO getApplicationDetails(TbCfcApplicationMstEntity app, CFCApplicationAddressEntity add) {
        RequestDTO cad = new RequestDTO();
        cad.setTitleId(app.getApmTitle());
        cad.setfName(app.getApmFname());
        cad.setmName(app.getApmMname());
        cad.setlName(app.getApmLname());
        cad.setGender(app.getApmSex());
        cad.setLangId(app.getLangId());
        cad.setMobileNo(add.getApaMobilno());
        cad.setEmail(add.getApaEmail());
        cad.setFlatBuildingNo(add.getApaFlatBuildingNo());
        cad.setBldgName(add.getApaBldgnm());
        cad.setRoadName(add.getApaRoadnm());
        cad.setBlockName(add.getApaBlockName());
        String areaName = add.getApaAreanm();
        if (StringUtils.isNotEmpty(add.getApaAreanm()) && add.getApaAreanm().endsWith(",")) {
            areaName = areaName.substring(0, areaName.length() - 1);
        }
        cad.setAreaName(areaName);
        cad.setCityName(add.getApaCityName());
        cad.setPincodeNo(add.getApaPincode());
        cad.setOrgId(app.getTbOrganisation().getOrgid());
        cad.setApplicationId(app.getApmApplicationId());
        cad.setServiceId(app.getTbServicesMst().getSmServiceId());
        return cad;
    }

    public static OrganisationDTO toOrganisationDTO(Organisation org) {
        OrganisationDTO orgDto = new OrganisationDTO();
        BeanUtils.copyProperties(org, orgDto);
        return orgDto;
    }

    public static List<CareRequestDTO> toCareRequestDTOS(List<Object[]> objList) {
        List<CareRequestDTO> careRequests = new ArrayList<>();
        for (Object[] obj : objList) {
            CareRequestDTO crdto = new CareRequestDTO();
            crdto.setId(Long.parseLong(obj[0].toString()));
            crdto.setComplaintId((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
            crdto.setApplicationId(Long.parseLong(obj[2].toString()));

            crdto.setDepartmentComplaint(Long.parseLong(obj[3].toString()));
            crdto.setDepartmentComplaintDesc(obj[4].toString());
            crdto.setDepartmentComplaintDescReg((obj[5] != null) ? obj[5].toString() : MainetConstants.BLANK);

            if (obj[6] != null) {
                crdto.setComplaintType(Long.parseLong(obj[6].toString()));
            }

            if (obj[7] != null) {
                crdto.setComplaintTypeDesc(obj[7].toString());
            }
            crdto.setComplaintTypeDescReg((obj[8] != null) ? obj[8].toString() : MainetConstants.BLANK);

            crdto.setLocation(Long.parseLong(obj[9].toString()));
            crdto.setLocationEngName(obj[10].toString());
            crdto.setLocationRegName(obj[11].toString());
            crdto.setPincode((obj[12] != null) ? obj[16].toString() : MainetConstants.BLANK);

            crdto.setDateOfRequest((Date) obj[13]);
            if (obj[14].toString() != null) {
                crdto.setStatus(
                        obj[14].toString().replaceAll(MainetConstants.operator.UNDER_SCORE, MainetConstants.WHITE_SPACE)
                                .replaceAll(MainetConstants.HYPHEN, MainetConstants.WHITE_SPACE));
            }
            crdto.setLastDecision((obj[15] != null) ? obj[15].toString() : MainetConstants.BLANK);

            crdto.setDescription(obj[16].toString());
            crdto.setOrgId(Long.parseLong(obj[17].toString()));
            crdto.setCreatedDate((Date) obj[18]);
            crdto.setCreatedBy(Long.parseLong(obj[19].toString()));
            crdto.setModifiedDate((obj[20] != null) ? ((Date) obj[20]) : null);
            crdto.setModifiedBy((obj[21] != null) ? Long.parseLong(obj[21].toString()) : null);
            crdto.setApmName((obj[22] != null) ? obj[22].toString() : MainetConstants.BLANK);
            careRequests.add(crdto);
        }
        return careRequests;
    }

    public static boolean isENVCodePresent(String envCode, Long orgId) {

        List<LookUp> envLookUpList = null;
        try {
            envLookUpList = CommonMasterUtility.lookUpListByPrefix("ENV", orgId);

            return envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equalsIgnoreCase(envCode)
                    && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
        } catch (Exception e) {
            // LOGGER.error("No Prefix found for ENV :"+envCode);
        }
        return false;

        // return true;
    }
    
    public static boolean isENVCodePresent(String [] envCode, Long orgId) {

        List<LookUp> envLookUpList = null;
        try {
            envLookUpList = CommonMasterUtility.lookUpListByPrefix("ENV", orgId);

            return envLookUpList.stream().anyMatch(env -> Arrays.asList(envCode).contains(env.getLookUpCode().toUpperCase())
                    && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
        } catch (Exception e) {
            // LOGGER.error("No Prefix found for ENV :"+envCode);
        }
        return false;
        
        // return true;
    }

    public static boolean isCallCenterApplicable(Long orgId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        LookUp ccaLookup = null;
        try {
            ccaLookup = CommonMasterUtility.getValueFromPrefixLookUp("CCA", "CCA", org);
        } catch (Exception e) {

        }
        if (ccaLookup != null && ccaLookup.getOtherField().equals("N")) {
            return false;
        }

        return true;
    }

    public static String convertInByteCode(String docName, String docPath) {
        String base64String = null;
        String existingPath = null;
        if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
            existingPath = docPath.replace('/', '\\');
        } else {
            existingPath = docPath.replace('\\', '/');
        }
        String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
        try {
            final byte[] image = FileNetApplicationClient.getInstance().getFileByte(docName, directoryPath);
            
            //base64String = base64.encodeToString(image);
            base64String = new String(Base64.getEncoder().encode(image));
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return base64String;
    }
    
    public static List<CareRequestDTO> toCareRequestDTOSForCareOperator(List<Object[]> objList) {
        List<CareRequestDTO> careRequests = new ArrayList<>();
        for (Object[] obj : objList) {
            CareRequestDTO crdto = new CareRequestDTO();
            
            crdto.setOrgId((obj[1] != null) ? Long.parseLong(obj[1].toString()) : null);
            crdto.setApplicationId((obj[0] != null) ? Long.parseLong(obj[0].toString()) : null);
            crdto.setDepartmentComplaint((obj[2] != null) ? Long.parseLong(obj[2].toString()) : null);
            crdto.setDepartmentComplaintDesc((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
            crdto.setDepartmentComplaintDescReg((obj[4] != null) ? obj[4].toString() : MainetConstants.BLANK);
            if (obj[6] != null) {
                crdto.setComplaintTypeDesc((obj[6] != null) ? obj[6].toString() : MainetConstants.BLANK);
            }
            crdto.setComplaintTypeDescReg((obj[7] != null) ? obj[7].toString() : MainetConstants.BLANK);
            
            
            crdto.setDateOfRequest((Date) obj[14]);
            crdto.setWorkflowReqId((obj[15] != null) ? Long.parseLong(obj[15].toString()) : null);
            crdto.setStatus((obj[16] != null) ? obj[16].toString() : MainetConstants.BLANK);
            crdto.setLastDecision((obj[17] != null) ? obj[17].toString() : MainetConstants.BLANK);
            crdto.setLevel((obj[22] != null) ? Long.parseLong(obj[22].toString()) : null);
            crdto.setActorName((obj[23] != null) ? obj[23].toString() : MainetConstants.BLANK);
            crdto.setComment((obj[24] != null) ? obj[24].toString() : MainetConstants.BLANK);
            crdto.setComplaintId((obj[19] != null) ? obj[19].toString() : MainetConstants.BLANK);
            careRequests.add(crdto);
        }
        return careRequests;
    }
    
    /*getting bytecode from dms if dms flag is Y*/
    public static String convertInByteCodeByDmsId(String dmsDocId) {
    	String byteCode = RestClient.dmsGetDoc(dmsDocId);
    	return byteCode;
    }
}
