package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.InstituteEntity;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbCsmrrCmdDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterServiceMapper;

/**
 * @author deepika.pimpale
 *
 */

@Component
@Scope("session")
public class DistributionConnectionFormModel extends AbstractFormModel {
    private static final long serialVersionUID = -4937360492558998919L;

    private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

    private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

    private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();

    private String applicantFullName;

    private String serviceName;

    @Autowired
    private NewWaterConnectionService waterService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private WaterServiceMapper waterMapper;

    @Autowired
    private WaterCommonService waterCommonService;

    @Resource
    private ServiceMasterService serviceMasterService;

    private List<InstituteEntity> instituteList = new ArrayList<>();

    private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();

    private Long serviceId;

    private String hasError = MainetConstants.BLANK;

    private Long codId1;

    private Long codId2;

    private Long codId3;

    private Long codId4;

    private Long codId5;

    private String noOfUsageLabel;

    private Double diameter;
    
    private String csCcntype;
    
    public void setConnectionDetailsInfo(final Long appId, final long serviceId, final long orgId) {
        setCfcAddressEntity(cfcService.getApplicantsDetails(appId));
        setCfcEntity(cfcService.getCFCApplicationByApplicationId(appId, orgId));
        final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
        setServiceName(serviceMaster.getSmServiceName());
        TbCsmrInfoDTO csmrInfoByAppIdAndOrgId = waterCommonService.getCsmrInfoByAppIdAndOrgId(appId, orgId);
        setCsmrInfo(csmrInfoByAppIdAndOrgId != null ? waterCommonService.getCsmrInfoByAppIdAndOrgId(appId, orgId) :
        	waterCommonService.getApplicantInformationById(appId, orgId));
        if (csmrInfo.getDistribution() != null) {
            setCodId1(csmrInfo.getDistribution().getCodId1());
            setCodId2(csmrInfo.getDistribution().getCodId2());
            setCodId3(csmrInfo.getDistribution().getCodId3());
            setCodId4(csmrInfo.getDistribution().getCodId4());
            setCodId5(csmrInfo.getDistribution().getCodId5());
            setDiameter(csmrInfo.getDistribution().getDiameter());
       
        }
        
        String usageType1 = CommonMasterUtility
                .getHierarchicalLookUp(csmrInfo.getTrmGroup1(), UserSession.getCurrent().getOrganisation()).getLookUpCode();
        if (MainetConstants.FlagH.equals(usageType1)) {
            setNoOfUsageLabel(ApplicationSession.getInstance().getMessage("No of Rooms"));
        } else if (MainetConstants.FlagR.equals(usageType1)) {
            setNoOfUsageLabel(ApplicationSession.getInstance().getMessage("No of Tables"));
        }

        final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
        if (csmrInfo.getCsMname() != null) {
			String userName = (getCfcEntity().getApmFname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmFname() + MainetConstants.WHITE_SPACE);
			userName += getCfcEntity().getApmMname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmMname() + MainetConstants.WHITE_SPACE;
			userName += getCfcEntity().getApmLname() == null ? MainetConstants.BLANK : getCfcEntity().getApmLname();
			setApplicantFullName(userName);
		} else {
			String userName = (getCfcEntity().getApmFname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmFname() + MainetConstants.WHITE_SPACE);
			userName += getCfcEntity().getApmMname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmMname() + MainetConstants.WHITE_SPACE;
			userName += getCfcEntity().getApmLname() == null ? MainetConstants.BLANK : getCfcEntity().getApmLname();
			setApplicantFullName(userName);
		}

    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.WWZ:
            return "csmrInfo.codDwzid";

        case PrefixConstants.WATERMODULEPREFIX.SLN:
            return "codId";

        default:
            return null;

        }

    }

    public Double getcalculateQuantityRequired(final TbCsmrInfoDTO master) {
        final TbCsmrrCmdDTO distrubution = master.getDistribution();
        final Long avearageConsumtion = waterService.getAvgConsumptionById(distrubution.getInstId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        final Double Q = (double) master.getNoOfFamilies() * master.getCsNoofusers() * avearageConsumtion;
        final Double qty = Q / (1000 * distrubution.getRcTotdisttime());
        return qty;

    }

    public Double calculateDiameter(final TbCsmrInfoDTO master, final Double quantityReqd) {
        final TbCsmrrCmdDTO distrubution = master.getDistribution();
        final Double slopeValue = waterService.getSlopeValueByRoadLength(distrubution.getRcLength(),
                UserSession.getCurrent().getOrganisation());
        final Double slopeByLen = slopeValue / distrubution.getRcLength();
        final Double numerator = (quantityReqd * Math.pow(10, 3));
        final Double denominator = 1.292 * Math.pow(slopeByLen, 0.54);
        final Double ccnSize = (double) Math.round(Math.pow((numerator / denominator), 1 / 2.63));
        return ccnSize;

    }

    /**
     * @param csmrrCmd2
     */
    public boolean validateFieldCalculation(final DistributionConnectionFormModel model) {
        final TbCsmrInfoDTO csmrInfo = model.getCsmrInfo();
        final TbCsmrrCmdDTO dist = csmrInfo.getDistribution();
        if ((csmrInfo.getNoOfFamilies() == null) || csmrInfo.getNoOfFamilies().equals(MainetConstants.BLANK)) {
            model.addValidationError(getAppSession().getMessage("water.noOfFamily.validn"));
        }
        if ((csmrInfo.getCsNoofusers() != null) || csmrInfo.getCsNoofusers().equals(MainetConstants.BLANK)) {
            model.addValidationError(getAppSession().getMessage("water.noOfUsers.validn"));
        }
        if ((dist.getCodId1() != null) || (dist.getCodId1() == 0L)) {
            model.addValidationError(getAppSession().getMessage("dist.line"));
        }
        if ((dist.getRcDistccndif() == null) || (dist.getRcDistccndif() == 0.0d)) {
            model.addValidationError(getAppSession().getMessage("dist.discharge"));
        }
        if ((dist.getRcDistpres() != null) && (dist.getRcDistpres() == 0.0d)) {
            model.addValidationError(getAppSession().getMessage("dist.pressure"));
        }
        if ((dist.getRcLength() != null) && (dist.getRcLength() == 0L)) {
            model.addValidationError(getAppSession().getMessage("dist.length"));
        }
        if ((dist.getRcTotdisttime() != null) && (dist.getRcTotdisttime() == 0.0d)) {
            model.addValidationError(getAppSession().getMessage("dist.period"));
        }
        if ((dist.getInstId() != null) && (dist.getInstId() == 0L)) {
            model.addValidationError(getAppSession().getMessage("dist.institute"));
        }
        if (model.hasValidationErrors()) {
            return false;
        }
        return true;

    }

    @Override
    public boolean saveForm() {
        final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
        final TbKCsmrInfoMH master = waterMapper.mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(csmrInfo);
        final Employee emp = UserSession.getCurrent().getEmployee();
        setMasterUpdateFields(master);
        if (master.getDistribution() != null) {
        	master.getDistribution().setCsIdnO(master);
            master.getDistribution().setUserId(emp);
            master.getDistribution().setOrgId(UserSession.getCurrent().getOrganisation());
            master.getDistribution().setCodId1(codId1);
            master.getDistribution().setCodId2(codId2);
            master.getDistribution().setCodId3(codId3);
            master.getDistribution().setCodId4(codId4);
            master.getDistribution().setCodId5(codId5);
            master.getDistribution().setLmodDate(new Date());
            master.getDistribution().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            master.getDistribution().setCsIdn(master.getCsIdn());
            master.getDistribution().setDiameter(diameter);
        }
        waterCommonService.updateCsmrInfo(master, getLableValueDTO());
        return true;
    }

    /**
     * @param master
     * 
     */
    private void setMasterUpdateFields(final TbKCsmrInfoMH master) {
        master.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        master.setUpdatedDate(new Date());
        master.setLmodDate(new Date());
        master.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getLgIpMac());

    }

    public TbCsmrInfoDTO getCsmrInfo() {
        return csmrInfo;
    }

    public void setCsmrInfo(final TbCsmrInfoDTO csmrInfo) {
        this.csmrInfo = csmrInfo;
    }

    public TbCfcApplicationMstEntity getCfcEntity() {
        return cfcEntity;
    }

    public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
        this.cfcEntity = cfcEntity;
    }

    public CFCApplicationAddressEntity getCfcAddressEntity() {
        return cfcAddressEntity;
    }

    public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
        this.cfcAddressEntity = cfcAddressEntity;
    }

    public String getApplicantFullName() {
        return applicantFullName;
    }

    public void setApplicantFullName(final String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;

    }

    public List<InstituteEntity> getInstituteList() {
        return instituteList;
    }

    public void setInstituteList(final List<InstituteEntity> instituteList) {
        this.instituteList = instituteList;
    }

    public ScrutinyLableValueDTO getLableValueDTO() {
        return lableValueDTO;
    }

    public void setLableValueDTO(final ScrutinyLableValueDTO lableValueDTO) {
        this.lableValueDTO = lableValueDTO;
    }

    @Override
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public boolean validateInputs() {
        final TbCsmrInfoDTO dto = getCsmrInfo();
        if ((dto.getCsListatus() != null) && (dto.getCsListatus() == 0L)) {
            addValidationError(getAppSession().getMessage("sel.status"));
        }
        if ((dto.getCsCcntype() != null) && dto.getCsCcntype().equals(MainetConstants.ZERO)) {
            addValidationError(getAppSession().getMessage("water.dist.sel.connType"));
        }
        if (dto.getNoOfFamilies() == null) {
            addValidationError(getAppSession().getMessage("water.noOfFamily.validn"));
        }else if(csmrInfo.getNoOfFamilies() != null && csmrInfo.getNoOfFamilies() >= 100) {
            addValidationError(getAppSession().getMessage("water.noOfFamily.tooLarge.validn"));
        }
        if (dto.getCsCcnsize() == null || dto.getCsCcnsize() <= 0) {
            addValidationError(getAppSession().getMessage("water.ccnSize.validn"));
        }
        String usageType1 = CommonMasterUtility
                .getHierarchicalLookUp(csmrInfo.getTrmGroup1(), UserSession.getCurrent().getOrganisation()).getLookUpCode();
        if (MainetConstants.FlagH.equals(usageType1)) {
            if (dto.getCsNoofusers() == null || dto.getCsNoofusers() <= 0) {
                addValidationError(getAppSession().getMessage("water.NoofRooms.validn"));
            }
        } else if (MainetConstants.FlagR.equals(usageType1)) {
            if (dto.getCsNoofusers() == null || dto.getCsNoofusers() <= 0) {
                addValidationError(getAppSession().getMessage("water.NoofTables.validn"));
            }
        }

        if (csmrInfo.getDistribution().getRcLength() != null && csmrInfo.getDistribution().getRcLength() == 0L) {
        	addValidationError(getAppSession().getMessage("dist.length"));
        }else if(csmrInfo.getDistribution().getRcLength() != null &&csmrInfo.getDistribution().getRcLength()>=1000) {
            addValidationError(getAppSession().getMessage("water.length.tooLarge.validn"));
        }
        
        if (hasValidationErrors()) {
            setHasError(MainetConstants.MENU.Y);
            return false;
        } else {
            setHasError(MainetConstants.MENU.N);
        }
        return true;
    }

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    /**
     * @return the codId1
     */
    public Long getCodId1() {
        return codId1;
    }

    /**
     * @param codId1 the codId1 to set
     */
    public void setCodId1(final Long codId1) {
        this.codId1 = codId1;
    }

    /**
     * @return the codId2
     */
    public Long getCodId2() {
        return codId2;
    }

    /**
     * @param codId2 the codId2 to set
     */
    public void setCodId2(final Long codId2) {
        this.codId2 = codId2;
    }

    /**
     * @return the codId3
     */
    public Long getCodId3() {
        return codId3;
    }

    /**
     * @param codId3 the codId3 to set
     */
    public void setCodId3(final Long codId3) {
        this.codId3 = codId3;
    }

    /**
     * @return the codId4
     */
    public Long getCodId4() {
        return codId4;
    }

    /**
     * @param codId4 the codId4 to set
     */
    public void setCodId4(final Long codId4) {
        this.codId4 = codId4;
    }

    /**
     * @return the codId5
     */
    public Long getCodId5() {
        return codId5;
    }

    /**
     * @param codId5 the codId5 to set
     */
    public void setCodId5(final Long codId5) {
        this.codId5 = codId5;
    }

    public String getNoOfUsageLabel() {
        return noOfUsageLabel;
    }

    public void setNoOfUsageLabel(String noOfUsageLabel) {
        this.noOfUsageLabel = noOfUsageLabel;
    }
    
    public Double[] calculateDischargeRateAndCcnSize(final TbCsmrInfoDTO master) {
    	Organisation org = new Organisation();
    	org.setOrgid(master.getOrgId());

    	String prefixStr = "APF,ACP,ANH,ALB,ARF";
    	HashMap<String, Double> dischargePrefixMap = waterService.getDischargePrefixMap(prefixStr, master.getOrgId());
    	
    	Double discharge = null;
    	
    	String commercial = "COM";
    	LookUp val = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(master.getCsCcntype()), org);
    	
    	if(commercial.equals(val.getLookUpCode())) {
    		discharge = waterService.getDischargeRateForCommercial(master, dischargePrefixMap);
    	} else {
    		discharge = waterService.getDischargeRateForDomestic(master, dischargePrefixMap);
    	}
    	
    	Long rcLength = master.getDistribution().getRcLength();
    	Double slope_value = waterService.getSlopeValueByRoadLengthAndOrg(rcLength, org);
    	Long d_Factor = Math.round(Math.pow(((discharge * Math.pow(10, 3)) / (1.292 * Math.pow((slope_value / rcLength), 0.54))) , 1/2.63));
    	Double connectionSize = waterService.getConnectionSizeByDFactor(d_Factor, org);
    	Double[] resultStr = new Double[2];
    	resultStr[0] = discharge ;
    	resultStr[1] = connectionSize;
    	return resultStr;
    }

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public String getCsCcntype() {
		return csCcntype;
	}

	public void setCsCcntype(String csCcntype) {
		this.csCcntype = csCcntype;
	}
	
}
