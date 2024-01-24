package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CustomerMasterEntity;
import com.abm.mainet.common.master.dto.CustomerMasterDTO;
import com.abm.mainet.common.master.service.ICustomerMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class CustomerMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    @Autowired
    private ICustomerMasterService customerMasterService;

    CustomerMasterDTO custMasterDTO = new CustomerMasterDTO();

    private List<CustomerMasterDTO> custMasterDtos = new ArrayList<>();

    private String saveMode;

    private String uploadFileName;

    private List<LookUp> vendorStatus = new ArrayList<>();

    @Override
    public boolean saveForm() {

        boolean status = false;
        validateCustomer();
        if (hasValidationErrors()) {
            return false;
        }
        if (getSaveMode().equals(MainetConstants.MODE_EDIT)) {
            custMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            custMasterDTO.setUpdatedDate(new Date());
            custMasterDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

            customerMasterService.updateCustomerMaster(custMasterDTO);
            this.setSuccessMessage(getAppSession().getMessage("accounts.customerMaster.edit"));
            status = true;

        } else {
    /*        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_SEQ_DEPARTMENT_TYPE, PrefixConstants.VSS,
                    UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            custMasterDTO.setCustStatus(statusLookup.getLookUpId());*/

            custMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            custMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            custMasterDTO.setCreatedDate(new Date());
            custMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            LookUp cpdstatus = CommonMasterUtility.getDefaultValue(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
            custMasterDTO.setCustStatus(cpdstatus.getLookUpId());// Active
            customerMasterService.saveCustomerMaster(custMasterDTO);
            this.setSuccessMessage(getAppSession().getMessage("accounts.customerMaster.save"));
            status = true;

        }
        return status;

    }

    private void validateCustomer() {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<CustomerMasterEntity> customerByPanNo = null;
        List<CustomerMasterEntity> customerByMobileNo = null;
        List<CustomerMasterEntity> customerByUidNo = null;
        List<CustomerMasterEntity> customerByTinNo = null;
        List<CustomerMasterEntity> customerByGstNo = null;
        List<CustomerMasterEntity> customerByName = null;
        List<CustomerMasterEntity> customerByEmail = null;


        if ((custMasterDTO.getCustPANNo() != null) && (custMasterDTO.getCustPANNo() != MainetConstants.CommonConstant.BLANK) && !custMasterDTO.getCustPANNo().isEmpty()) {
            customerByPanNo = customerMasterService.getcustomerByPanNo(custMasterDTO.getCustPANNo(), orgId);
        }
        if ((custMasterDTO.getCustMobNo() != null) && (custMasterDTO.getCustMobNo() != MainetConstants.CommonConstant.BLANK) && !custMasterDTO.getCustMobNo().isEmpty()) {
            customerByMobileNo = customerMasterService.getcustomerByMobileNo(custMasterDTO.getCustMobNo(), orgId);
        }
        if ((custMasterDTO.getCustUIDNo() != null)) {
            customerByUidNo = customerMasterService.getcustomerByUidNo(custMasterDTO.getCustUIDNo(), orgId);
        }
        if ((custMasterDTO.getCustTINNo() != null) && (custMasterDTO.getCustTINNo() != MainetConstants.CommonConstant.BLANK) && !custMasterDTO.getCustTINNo().isEmpty()) {
            customerByTinNo = customerMasterService.getCustomerByTINNo(custMasterDTO.getCustTINNo(), orgId);
        }
        if ((custMasterDTO.getCustGSTNo() != null) && (custMasterDTO.getCustGSTNo() != MainetConstants.CommonConstant.BLANK) && !custMasterDTO.getCustGSTNo().isEmpty()) {
            customerByGstNo = customerMasterService.getCustomerByGstNo(custMasterDTO.getCustGSTNo(), orgId);
        }
        if ((custMasterDTO.getCustName() != null) && (custMasterDTO.getCustName() != MainetConstants.CommonConstant.BLANK) && !custMasterDTO.getCustName().isEmpty()) {
            customerByName = customerMasterService.getCustomerByName(custMasterDTO.getCustName(), orgId);
        }
        if ((custMasterDTO.getCustEmailId() != null) && (custMasterDTO.getCustEmailId() != MainetConstants.CommonConstant.BLANK) && !custMasterDTO.getCustEmailId().isEmpty()) {
            customerByEmail = customerMasterService.getCustomerByEmail(custMasterDTO.getCustEmailId(), orgId);
        }

        if (getSaveMode().equals(MainetConstants.MODE_CREATE)) {
            if ((customerByPanNo != null) && !customerByPanNo.isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.panNo"));
            }
            if ((customerByMobileNo != null) && !customerByMobileNo.isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custMobNo"));
            }
            if ((customerByUidNo != null) && !customerByUidNo.isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custUidNo"));
            }
            if ((customerByTinNo != null) && !customerByTinNo.isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custTinNo"));
            }
            if ((customerByGstNo != null) && !customerByGstNo.isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custGstNo"));
            }
            if ((customerByName != null) && !customerByName.isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custName"));
            }

            if ((customerByEmail != null) && !customerByEmail.isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custEmailId"));
            }

        } else {
            // PanNo Validation
            if ((customerByPanNo != null) && !customerByPanNo.isEmpty()) {
                Boolean isduplicatePancard = false;
                for (final CustomerMasterEntity customer : customerByPanNo) {
                    if (!custMasterDTO.getCustId().equals(customer.getCustId())) {
                        isduplicatePancard = true;
                        break;
                    }
                }
                if (isduplicatePancard) {
                    addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.panNo"));
                }
            }

            // mobileNo Validation
            if ((customerByMobileNo != null) && !customerByMobileNo.isEmpty()) {
                Boolean isduplicateMobile = false;
                for (final CustomerMasterEntity customer : customerByMobileNo) {
                    if (!custMasterDTO.getCustId().equals(customer.getCustId())) {
                        isduplicateMobile = true;
                        break;
                    }
                }
                if (isduplicateMobile) {
                    addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custMobNo"));
                }
            }

            // uidNo Validation
            if ((customerByUidNo != null) && !customerByUidNo.isEmpty()) {
                Boolean isduplicateUid = false;
                for (final CustomerMasterEntity customer : customerByUidNo) {
                    if (!custMasterDTO.getCustId().equals(customer.getCustId())) {
                        isduplicateUid = true;
                        break;
                    }
                }
                if (isduplicateUid) {
                    addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custUidNo"));
                }
            }

            // vat Validation
            if ((customerByTinNo != null) && !customerByTinNo.isEmpty()) {
                Boolean isduplicateVat = false;
                for (final CustomerMasterEntity customer : customerByTinNo) {
                    if (!custMasterDTO.getCustId().equals(customer.getCustId())) {
                        isduplicateVat = true;
                        break;
                    }
                }
                if (isduplicateVat) {
                    addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custTinNo"));
                }
            }

            // gst Validation
            if ((customerByGstNo != null) && !customerByGstNo.isEmpty()) {
                Boolean isduplicateGst = false;
                for (final CustomerMasterEntity customer : customerByGstNo) {
                    if (!custMasterDTO.getCustId().equals(customer.getCustId())) {
                        isduplicateGst = true;
                        break;
                    }
                }
                if (isduplicateGst) {
                    addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custGstNo"));
                }
            }

            // Customer Name
            if ((customerByName != null) && !customerByName.isEmpty()) {
                Boolean isduplicateName = false;
                for (final CustomerMasterEntity customer : customerByName) {
                    if (!custMasterDTO.getCustId().equals(customer.getCustId())) {
                        isduplicateName = true;
                        break;
                    }
                }
                if (isduplicateName) {
                    addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custName"));
                }
            }

            // Email Id
            if ((customerByEmail != null) && !customerByEmail.isEmpty()) {
                Boolean isduplicateEmail = false;
                for (final CustomerMasterEntity customer : customerByEmail) {
                    if (!custMasterDTO.getCustId().equals(customer.getCustId())) {
                        isduplicateEmail = true;
                        break;
                    }
                }
                if (isduplicateEmail) {
                    addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.custEmailId"));
                }
            }

            if ((custMasterDTO.getRemark() == null) || (custMasterDTO.getRemark() == MainetConstants.CommonConstant.BLANK) || custMasterDTO.getRemark().isEmpty()) {
                addValidationError(getAppSession().getMessage("accounts.customerMaster.validation.remark"));
            }
        }


    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public CustomerMasterDTO getCustMasterDTO() {
        return custMasterDTO;
    }

    public void setCustMasterDTO(CustomerMasterDTO custMasterDTO) {
        this.custMasterDTO = custMasterDTO;
    }

    public List<CustomerMasterDTO> getCustMasterDtos() {
        return custMasterDtos;
    }

    public void setCustMasterDtos(List<CustomerMasterDTO> custMasterDtos) {
        this.custMasterDtos = custMasterDtos;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<LookUp> getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(List<LookUp> vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

}
