package com.abm.mainet.asset.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.service.IAssetFunctionalLocationMasterService;
import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.asset.service.ISearchService;
import com.abm.mainet.asset.ui.dto.AssetFunctionalLocationDTO;
import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.asset.ui.dto.SummaryDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope(value = "session")
public class AssetSearchModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5244885588544550736L;

    @Autowired
    private ISearchService searchService;

    @Resource
    private TbDepartmentService iTbDepartmentService;

    @Autowired
    private IAssetFunctionalLocationMasterService assetFuncLocMasterService;

    @Autowired
    private IAssetInformationService infoService;
    @Autowired
    private SecondaryheadMasterService shmService;

    // Retrive details
    List<AssetFunctionalLocationDTO> funcLocDTOList = null;

    private List<TbDepartment> departmentsList;

    private SearchDTO astSearchDTO = null;

    private List<LookUp> acHeadCode = new ArrayList<>(0);

    private List<SummaryDTO> barcodeList = new ArrayList<>(0);

    private List<EmployeeBean> empList = new ArrayList<>();

    private List<TbLocationMas> locList = new ArrayList<>();

    private String saveMode;
    private String orgname;

    /**
     * As scope is request so for every call we need to initialize the function_Location_Code List and department list
     */
    private final void initializeMasterDetails() {
        this.departmentsList = iTbDepartmentService.findAll();
        this.acHeadCode = shmService.findExpenditureAccountHeadOnly(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                UserSession.getCurrent().getLanguageId(), MainetConstants.FlagA);
        // Retrive function code for parent dropdown
        funcLocDTOList = assetFuncLocMasterService
                .retriveFunctionLocationDtoListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    }

    /**
     * inital call made here from index method
     * 
     * @param searchDTO
     * @return
     */
    public List<SummaryDTO> search(SearchDTO searchDTO) {
        initializeMasterDetails();
        List<SummaryDTO> summaryDTOList = searchService.search(searchDTO);
        return summaryDTOList;
    }

    /**
     * search on the basis of filter criteria provided by the user
     * 
     * @param searchDTO
     * @return
     */
    public List<SummaryDTO> filterAsset(SearchDTO searchDTO) {

        return searchService.search(searchDTO);
    }

    public SearchDTO getAstSearchDTO() {
        return astSearchDTO;
    }

    public void setAstSearchDTO(SearchDTO astSearchDTO) {
        this.astSearchDTO = astSearchDTO;
    }

    public List<AssetFunctionalLocationDTO> getFuncLocDTOList() {
        return funcLocDTOList;
    }

    public void setFuncLocDTOList(List<AssetFunctionalLocationDTO> funcLocDTOList) {
        this.funcLocDTOList = funcLocDTOList;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<LookUp> getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(List<LookUp> acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    /**
     * @return the barcodeList
     */
    public List<SummaryDTO> getBarcodeList() {
        return barcodeList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    /**
     * @param barcodeList the barcodeList to set
     */
    public void setBarcodeList(List<SummaryDTO> barcodeList) {
        this.barcodeList = barcodeList;
    }

    public List<SummaryDTO> barcodeGenerate() {

        return searchService.barcodeGenerate(getBarcodeList());

    }

    public List<EmployeeBean> getEmpList() {
        return empList;
    }

    public void setEmpList(List<EmployeeBean> empList) {
        this.empList = empList;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

}
