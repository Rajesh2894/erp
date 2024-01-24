package com.abm.mainet.swm.ui.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.DisposalMasterDTO;
import com.abm.mainet.swm.dto.VendorContractMappingDTO;
import com.abm.mainet.swm.service.IVendorContractMappingService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class ContractMappingModel extends AbstractFormModel {

    private static final long serialVersionUID = -8633691823517406361L;

    @Autowired
    private IVendorContractMappingService vendorContractMappingService;

    VendorContractMappingDTO vendorContractMappingDTO = new VendorContractMappingDTO();

    private ContractMastDTO contractMastDTO = new ContractMastDTO();

    List<VendorContractMappingDTO> VendorContractMappingList;

    List<VendorContractMappingDTO> ulbWitnessMappingList;

    List<VendorContractMappingDTO> vendorWitnessMappingList;

    List<BeatMasterDTO> routeList;

    DisposalMasterDTO disposalMasterDTO = new DisposalMasterDTO();

    private List<TbAcVendormaster> vendors;

    List<ContractMappingDTO> ContractMappingDTOList;
    private ContractMappingDTO contractMappingDTO = new ContractMappingDTO();
    private String saveMode;

    @Override
    public boolean saveForm() {
        boolean status = false;
        List<VendorContractMappingDTO> vendorMappingList = getVendorContractMappingList();
        for (VendorContractMappingDTO vendorContractMappingDTO : vendorMappingList) {
            if (vendorContractMappingDTO.getMapId() == null) {
                vendorContractMappingDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vendorContractMappingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vendorContractMappingDTO.setCreatedDate(new Date());
                vendorContractMappingDTO.setLgIpMac(Utility.getMacAddress());
                vendorContractMappingDTO.setContId(this.vendorContractMappingDTO.getContId());
            }
        }
        status = vendorContractMappingService.validateContractMapping(vendorMappingList);
        if (status) {
            vendorContractMappingService.save(vendorMappingList);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("contractMastDTO.save"));
            status = true;
        } else {
            addValidationError(ApplicationSession.getInstance().getMessage("Contract of Task  Already exists"));
            status = false;
        }
        return status;

    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {
        case "SWZ":
            return "VendorContractMappingList[0].codWard";
        case "WTY":
            return MainetConstants.SOLID_WATSE_FLOWTYPE.CONTRACT_MAPPING_WASTE_LEVEL;
        default:
            return "";
        }
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<VendorContractMappingDTO> getVendorContractMappingList() {
        return VendorContractMappingList;
    }

    public void setVendorContractMappingList(List<VendorContractMappingDTO> vendorContractMappingList) {
        VendorContractMappingList = vendorContractMappingList;
    }

    public VendorContractMappingDTO getVendorContractMappingDTO() {
        return vendorContractMappingDTO;
    }

    public void setVendorContractMappingDTO(VendorContractMappingDTO vendorContractMappingDTO) {
        this.vendorContractMappingDTO = vendorContractMappingDTO;
    }

    public DisposalMasterDTO getDisposalMasterDTO() {
        return disposalMasterDTO;
    }

    public void setDisposalMasterDTO(DisposalMasterDTO disposalMasterDTO) {
        this.disposalMasterDTO = disposalMasterDTO;
    }

    public List<ContractMappingDTO> getContractMappingDTOList() {
        return ContractMappingDTOList;
    }

    public void setContractMappingDTOList(List<ContractMappingDTO> contractMappingDTOList) {
        ContractMappingDTOList = contractMappingDTOList;
    }

    public ContractMappingDTO getContractMappingDTO() {
        return contractMappingDTO;
    }

    public void setContractMappingDTO(ContractMappingDTO contractMappingDTO) {
        this.contractMappingDTO = contractMappingDTO;
    }

    public List<TbAcVendormaster> getVendors() {
        return vendors;
    }

    public void setVendors(List<TbAcVendormaster> vendors) {
        this.vendors = vendors;
    }

    public ContractMastDTO getContractMastDTO() {
        return contractMastDTO;
    }

    public void setContractMastDTO(ContractMastDTO contractMastDTO) {
        this.contractMastDTO = contractMastDTO;
    }

    public List<VendorContractMappingDTO> getUlbWitnessMappingList() {
        return ulbWitnessMappingList;
    }

    public void setUlbWitnessMappingList(List<VendorContractMappingDTO> ulbWitnessMappingList) {
        this.ulbWitnessMappingList = ulbWitnessMappingList;
    }

    public List<VendorContractMappingDTO> getVendorWitnessMappingList() {
        return vendorWitnessMappingList;
    }

    public void setVendorWitnessMappingList(List<VendorContractMappingDTO> vendorWitnessMappingList) {
        this.vendorWitnessMappingList = vendorWitnessMappingList;
    }

    public List<BeatMasterDTO> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<BeatMasterDTO> routeList) {
        this.routeList = routeList;
    }

}
