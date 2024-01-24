package com.abm.mainet.care.ui.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.DepartmentDTO;
import com.abm.mainet.care.dto.report.ComplaintDTO;
import com.abm.mainet.care.dto.report.ComplaintReportDTO;
import com.abm.mainet.care.dto.report.ComplaintReportRequestDTO;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.domain.SmsEmailTransactionDTO;

@Component
@Scope("session")
public class ComplaintReportModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1290607451978320244L;

    @Resource
    private ICareRequestService careRequestService;

    @Resource
    private IOrganisationService organisationService;

    private Set<LookUp> organisations = new HashSet<>();
    private Set<LookUp> departments = new LinkedHashSet<>();
    private Set<LookUp> complaintTypes = new HashSet<>();
    private ComplaintReportRequestDTO careReportRequest;
    private ComplaintReportDTO complaintReport;
    private String prefixName;
    private List<SmsEmailTransactionDTO> smsEmailDTO;
    private String kdmcEnv;
    private String dsclEnv;
    private String asclEnv;
    private List<EmployeeBean> empList = new ArrayList<>();
    private String envFlag;
    public List<SmsEmailTransactionDTO> getSmsEmailDTO() {
        return smsEmailDTO;
    }

    public void setSmsEmailDTO(List<SmsEmailTransactionDTO> smsEmailDTO) {
        this.smsEmailDTO = smsEmailDTO;
    }

    @Override
    protected void initializeModel() {
        Set<DepartmentDTO> dpts = careRequestService.getCareWorkflowMasterDefinedDepartmentListByOrgId(
                UserSession.getCurrent().getOrganisation().getOrgid());
		Set<DepartmentDTO> sortetDepts = new LinkedHashSet<DepartmentDTO>(dpts);

		if (null != sortetDepts) {
			sortetDepts.stream().sorted(Comparator.comparing(DepartmentDTO::getDpDeptdesc)).forEach(d -> {
				LookUp detData = new LookUp();
				detData.setDescLangFirst(d.getDpDeptdesc());
				detData.setDescLangSecond(d.getDpNameMar());
				detData.setLookUpId(d.getDpDeptid());
				departments.add(detData);
			});
		}
		departments.forEach(a->{
	        logger.info("Sorted Dept:" +a.getDescLangFirst());
	        });

        List<Organisation> orgs = organisationService.findAllActiveOrganization("A");
        orgs.forEach(o -> {
            LookUp org = new LookUp();
            org.setDescLangFirst(o.getONlsOrgname());
            org.setDescLangSecond(o.getONlsOrgnameMar());
            org.setLookUpId(o.getOrgid());
            organisations.add(org);
        });

    }

    public void displayWardZoneColoumns() {
        // #129060 
    	if (complaintReport.getComplaints() != null) {
        Stream<ComplaintDTO> filter = complaintReport.getComplaints().stream().filter(c -> {
            return !(c.getCodIdOperLevel1() != null && c.getCodIdOperLevel1().isEmpty())
                    || !(c.getCodIdOperLevel2() != null && c.getCodIdOperLevel2().isEmpty())
                    || !(c.getCodIdOperLevel3() != null && c.getCodIdOperLevel3().isEmpty())
                    || !(c.getCodIdOperLevel4() != null && c.getCodIdOperLevel4().isEmpty())
                    || !(c.getCodIdOperLevel5() != null && c.getCodIdOperLevel5().isEmpty());
        });
	

        /*
         * public void displayWardZoneColoumns() { Stream<ComplaintDTO> filter = complaintReport.getComplaints().stream().filter(c
         * -> { return !(c.getCareWardNo() != null && c.getCareWardNo().isEmpty()) || !(c.getCareWardNo1() != null &&
         * c.getCareWardNo1().isEmpty()) || !(c.getCareWardNoReg() != null && c.getCareWardNoReg().isEmpty()) ||
         * !(c.getCareWardNoReg1() != null && c.getCareWardNoReg1().isEmpty()) || !(c.getCareWardNoEng() != null &&
         * c.getCareWardNoEng().isEmpty()) || !(c.getCareWardNoEng1() != null && c.getCareWardNoEng1().isEmpty()); });
         */

        if (!filter.findFirst().isPresent())
            prefixName = null;
    	}
    }

    public Set<LookUp> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Set<LookUp> organisations) {
        this.organisations = organisations;
    }

    public Set<LookUp> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<LookUp> departments) {
        this.departments = departments;
    }

    public Set<LookUp> getComplaintTypes() {
        return complaintTypes;
    }

    public void setComplaintTypes(Set<LookUp> complaintTypes) {
        this.complaintTypes = complaintTypes;
    }

    public ComplaintReportRequestDTO getCareReportRequest() {
        return careReportRequest;
    }

    public void setCareReportRequest(ComplaintReportRequestDTO careReportRequest) {
        this.careReportRequest = careReportRequest;
    }

    public ComplaintReportDTO getComplaintReport() {
        return complaintReport;
    }

    public void setComplaintReport(ComplaintReportDTO complaintReport) {
        this.complaintReport = complaintReport;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    @Override
    protected String findPropertyPathPrefix(String parentCode) {
        return "careReportRequest.careWardNo";
    }

    public String getKdmcEnv() {
        return kdmcEnv;
    }

    public void setKdmcEnv(String kdmcEnv) {
        this.kdmcEnv = kdmcEnv;
    }

    public List<EmployeeBean> getEmpList() {
        return empList;
    }

    public void setEmpList(List<EmployeeBean> empList) {
        this.empList = empList;
    }

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public String getDsclEnv() {
		return dsclEnv;
	}

	public void setDsclEnv(String dsclEnv) {
		this.dsclEnv = dsclEnv;
	}

	public String getAsclEnv() {
		return asclEnv;
	}

	public void setAsclEnv(String asclEnv) {
		this.asclEnv = asclEnv;
	}

}
