
package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class LocationMasModel extends AbstractFormModel {

    private static final long serialVersionUID = 7459121617001255856L;

    @Resource
    private ILocationMasService iLocationMasService;
    private TbLocationMas tbLocationMas = new TbLocationMas();
    private List<TbLocationMas> locationMasList;
    private List<TbDepartment> deptList;
    private String prefixName;
    String modeType;
    Map<Long, String> mapRenewalList;
    List<String> locationNameList;
    List<String> locationAreaList;
    @Transient
	private String uploadFileName;
    private String cpdMode;
    private List<TbFinancialyear> faYears = new ArrayList<>();
    Map<Long, String> budgetMap = new HashMap<>();
    private String removeYearIds;

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

    @Override
    protected void initializeModel() {

        initializeLookupFields(PrefixConstants.prefixName.ElectrolWZ);

    }

    public void initializeModelEdit(final String prefix) {
        initializeLookupFields(prefix);
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        String result = MainetConstants.BLANK;
        switch (parentCode) {

            case PrefixConstants.prefixName.ElectrolWZ:
                result = MainetConstants.fieldName.EWZFieldName;
                break;

            default:
                result = MainetConstants.fieldName.OWZFieldName;
                break;
        }
        return result;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    @Override
    public boolean saveForm() {
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final String lgIPMacId = UserSession.getCurrent().getEmployee().getEmppiservername();
        if (modeType.equals(MainetConstants.MODE_CREATE)) {
            tbLocationMas.setLgIpMac(lgIPMacId);
            tbLocationMas.setLangId(langId);
            tbLocationMas.setUserId(userId);
            iLocationMasService.createLocationMas(getTbLocationMas(), org, FileNetApplicationClient.getInstance());
            setSuccessMessage("Record saved Successfully");
        } else {
        	List<Long> removeYearIdList = getRemovedYearIdAsList();
            tbLocationMas.setUpdatedBy(userId);
            tbLocationMas.setUpdatedDate(new Date());
            tbLocationMas.setLgIpMacUpd(lgIPMacId);
            iLocationMasService.createLocationMasEdit(getTbLocationMas(), org, FileNetApplicationClient.getInstance());
            if(removeYearIdList!=null && !removeYearIdList.isEmpty())
            {
            	iLocationMasService.inactiveRemovedYearDetails(getTbLocationMas(), removeYearIdList);
            }
            setSuccessMessage("Record updated Successfully");
        }

        return true;
    }
    
    private List<Long> getRemovedYearIdAsList() {
        List<Long> removeYearIdList = null;
        String yearIds = getRemoveYearIds();
        if (yearIds != null && !yearIds.isEmpty()) {
            removeYearIdList = new ArrayList<>();
            String yearArray[] = yearIds.split(MainetConstants.operator.COMMA);
            for (String yearId : yearArray) {
                removeYearIdList.add(Long.valueOf(yearId));
            }
        }
        return removeYearIdList;
    }

    public TbLocationMas getTbLocationMas() {
        return tbLocationMas;
    }

    public void setTbLocationMas(final TbLocationMas tbLocationMas) {
        this.tbLocationMas = tbLocationMas;
    }

    public List<TbLocationMas> getLocationMasList() {
        return locationMasList;
    }

    public void setLocationMasList(final List<TbLocationMas> locationMasList) {
        this.locationMasList = locationMasList;
    }

    public List<TbDepartment> getDeptList() {
        return deptList;
    }

    public void setDeptList(final List<TbDepartment> deptList) {
        this.deptList = deptList;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(final String prefixName) {
        this.prefixName = prefixName;
    }

    public Map<Long, String> getMapRenewalList() {
        return mapRenewalList;
    }

    public void setMapRenewalList(
            final Map<Long, String> mapRenewalList) {
        this.mapRenewalList = mapRenewalList;
    }

    public List<String> getLocationNameList() {
        return locationNameList;
    }

    public void setLocationNameList(final List<String> locationNameList) {
        this.locationNameList = locationNameList;
    }

    public List<String> getLocationAreaList() {
        return locationAreaList;
    }

    public void setLocationAreaList(final List<String> locationAreaList) {
        this.locationAreaList = locationAreaList;
    }

	public String getCpdMode() {
		return cpdMode;
	}

	public void setCpdMode(String cpdMode) {
		this.cpdMode = cpdMode;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public String getRemoveYearIds() {
		return removeYearIds;
	}

	public void setRemoveYearIds(String removeYearIds) {
		this.removeYearIds = removeYearIds;
	}

	public Map<Long, String> getBudgetMap() {
		return budgetMap;
	}

	public void setBudgetMap(Map<Long, String> budgetMap) {
		this.budgetMap = budgetMap;
	}

}
