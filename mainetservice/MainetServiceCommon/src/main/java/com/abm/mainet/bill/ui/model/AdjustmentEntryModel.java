package com.abm.mainet.bill.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.service.AdjustmentEntryService;
import com.abm.mainet.cfc.challan.dto.AdjustmentDetailDTO;
import com.abm.mainet.cfc.challan.dto.AdjustmentMasterDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class AdjustmentEntryModel extends AbstractFormModel {

    private static final long serialVersionUID = -4255932371514311593L;

    private List<TbBillMas> billList = null;

    private AdjustmentMasterDTO adjustmentDto = null;

    private List<TbDepartment> department = null;

    private List<AdjustmentMasterDTO> adjustmentHistoryDto = null;

    private String historyMsg = null;

    private TbBillMas bill;
    
    private String totalAdjValue;
    
    private String billingMethod;
    
    private List<String> flatNoList;

    @Resource
    private AdjustmentEntryService adjustmentEntryService;
    
    @Resource
    private IFileUploadService fileUpload;

    private String address;

    private String bmCcnOwner;
    
    @Override
    public boolean saveForm() {
    	
    	/*
    	 * User Story #130566
    	 */
    	List<DocumentDetailsVO> docs = new ArrayList<>(0);
    	
    	 if ((FileUploadUtility.getCurrent().getFileMap() != null)
                 && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
             for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                 final List<File> list = new ArrayList<>(entry.getValue());
                 for (final File file : list) {
                     try {
                         DocumentDetailsVO d = new DocumentDetailsVO();
                         final Base64 base64 = new Base64();
                         final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                         d.setDocumentByteCode(bytestring);
                         d.setDocumentName(file.getName());
                         docs.add(d);
                     } catch (final IOException e) {
                     }
                 }
             }
         } else {
             addValidationError("Please upload adjustment entry document");
             return false;
         }
    	 
    	 
        if (!validateData(getAdjustmentDto())) {
        	String lastBillFlag = null;
        	LookUp lastBillActive = null;
        	try {
        		lastBillActive = CommonMasterUtility.getValueFromPrefixLookUp("LB",
                        "PAE", UserSession.getCurrent().getOrganisation());
        	}catch (Exception e) {
        		
			}
        	if(lastBillActive != null) {
        		lastBillFlag = lastBillActive.getDefaultVal();
        	}
        	
			if (PrefixConstants.IsLookUp.STATUS.YES.equals(lastBillFlag)
					&& StringUtils.equals(getAdjustmentDto().getAdjType(), MainetConstants.FlagP)) {
				adjustmentEntryService.saveAdjustmentEntryDataForLastBill(getBillList(), getAdjustmentDto());
        	}else {
        		adjustmentEntryService.saveAdjustmentEntryData(getBillList(), getAdjustmentDto());
        	}
            
            RequestDTO dto = new RequestDTO();
			dto.setDeptId(ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDepartmentIdByDeptCode("COM"));
			dto.setServiceId(ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceIdByShortName(UserSession.getCurrent().getOrganisation().getOrgid(), "MTD"));
			dto.setReferenceId(getAdjustmentDto().getAdjRefNo());
            dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            fileUpload.doFileUpload(docs, dto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("adjustment.entry.success.msg"));
            return true;
        }
        return false;
    }

    private boolean validateData(final AdjustmentMasterDTO adjustmentEntryDto) {
        int count = 0;
        if ((adjustmentEntryDto.getAdjType() == null) || adjustmentEntryDto.getAdjType().isEmpty()) {
            addValidationError(ApplicationSession.getInstance().getMessage("adjustment.type.select"));
            return true;
        } else {
            for (final AdjustmentDetailDTO dto : adjustmentEntryDto.getAdjDetailDto()) {
                if (dto.getAdjAmount() > 0d) {
                    count++;
                    if ((dto.getAdjRemark() == null) || dto.getAdjRemark().isEmpty()) {
                        addValidationError(ApplicationSession.getInstance().getMessage("adjustment.remark.enter"));
                        return true;
                    }
                    
					List<TbBillDet> billDetList = getBill().getTbWtBillDet().stream()
							.filter(billDet -> billDet.getTaxId().equals(dto.getTaxId())).collect(Collectors.toList());
					if(StringUtils.equals(MainetConstants.FlagN,adjustmentEntryDto.getAdjType()) && dto.getAdjAmount() > (billDetList.get(0).getBdCurBalTaxamt()+billDetList.get(0).getBdPrvBalArramt())) {
						 addValidationError(ApplicationSession.getInstance().getMessage("adjustment.greater.than.balance.amount"));
						 return true;
					}
                }
            }
            if (count <= 0) {
                addValidationError(ApplicationSession.getInstance().getMessage("adjustment.tax.select.one"));
                return true;
            }
        }
        return false;
    }

    public List<TbDepartment> getDepartment() {
        return department;
    }

    public void setDepartment(final List<TbDepartment> department) {
        this.department = department;
    }

    public AdjustmentMasterDTO getAdjustmentDto() {
        return adjustmentDto;
    }

    public void setAdjustmentDto(final AdjustmentMasterDTO adjustmentDto) {
        this.adjustmentDto = adjustmentDto;
    }

    public List<AdjustmentMasterDTO> getAdjustmentHistoryDto() {
        return adjustmentHistoryDto;
    }

    public void setAdjustmentHistoryDto(final List<AdjustmentMasterDTO> adjustmentHistoryDto) {
        this.adjustmentHistoryDto = adjustmentHistoryDto;
    }

    public boolean validateData() {
        boolean result = true;
        if ((getAdjustmentDto().getDpDeptId() == null) || (getAdjustmentDto().getDpDeptId() <= 0)) {
            addValidationError(ApplicationSession.getInstance().getMessage("adjustment.department.select"));
            result = false;
        }
        if ((getAdjustmentDto().getAdjRefNo() == null) || getAdjustmentDto().getAdjRefNo().isEmpty()) {
            addValidationError(ApplicationSession.getInstance().getMessage("adjustment.conno.enter"));
            result = false;
        }
        return result;
    }

    public String getHistoryMsg() {
        return historyMsg;
    }

    public void setHistoryMsg(final String historyMsg) {
        this.historyMsg = historyMsg;
    }

    public List<TbBillMas> getBillList() {
        return billList;
    }

    public void setBillList(final List<TbBillMas> billList) {
        this.billList = billList;
    }

    public TbBillMas getBill() {
        return bill;
    }

    public void setBill(final TbBillMas bill) {
        this.bill = bill;
    }

	public String getTotalAdjValue() {
		return totalAdjValue;
	}

	public void setTotalAdjValue(String totalAdjValue) {
		this.totalAdjValue = totalAdjValue;
	}
	public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod;
    }
    
    public List<String> getFlatNoList() {
        return flatNoList;
    }

    public void setFlatNoList(List<String> flatNoList) {
        this.flatNoList = flatNoList;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBmCcnOwner() {
		return bmCcnOwner;
	}

	public void setBmCcnOwner(String bmCcnOwner) {
		this.bmCcnOwner = bmCcnOwner;
	}
    
}
