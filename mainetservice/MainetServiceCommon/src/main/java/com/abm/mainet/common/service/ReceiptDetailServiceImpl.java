package com.abm.mainet.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.master.dto.BankMasterDTO;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@WebService(endpointInterface = "com.abm.mainet.common.service.ReceiptDetailService")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Api(value = "/receiptService")
@Path("/receiptService")
@Service
public class ReceiptDetailServiceImpl implements ReceiptDetailService {

	@Autowired
	IReceiptEntryService receiptEntryService;
	
    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Resource
   	private BankMasterService bankMasterService;
    
	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "To fetch receipt details", notes = "To fetch receipt details", response = TbServiceReceiptMasBean.class)
	@Path("/findReceiptDet")
	public List<TbServiceReceiptMasBean> findReceiptDet(@RequestBody TbServiceReceiptMasBean receiptMasBean) {
		List<TbServiceReceiptMasBean> findReceiptDet = new ArrayList<TbServiceReceiptMasBean>();
		 receiptMasBean.setRmRcptno(Utility.getReceiptIdFromCustomRcptNO(receiptMasBean.getRmReceiptNo()));
		 findReceiptDet = receiptEntryService.findReceiptDet(receiptMasBean);
		 return findReceiptDet;
	
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "To fetch receipt details", notes = "To fetch receipt details", response = TbServiceReceiptMasBean.class)
	@Path("/findReceiptById/{rmRcptid}/{orgId}/{langId}")
	public TbServiceReceiptMasBean findReceiptById(@PathParam("rmRcptid") Long rmRcptid,@PathParam("orgId") Long orgId,@PathParam("langId")  int langId) {
		TbServiceReceiptMasBean  receiptMasBean = receiptEntryService.findReceiptById(rmRcptid,orgId);
		Map<String, BigDecimal> taxdto = new HashMap<>();
		String taxDesc = null;
		if (receiptMasBean != null &&  receiptMasBean.getReceiptFeeDetail() != null) {
		for (TbSrcptFeesDetBean feeDto : receiptMasBean.getReceiptFeeDetail()) {
			if (langId > 0 && langId == MainetConstants.REGIONAL_LANGUAGE_ID) {
				taxDesc = tbTaxMasService.findTaxDescRegByTaxIdAndOrgId(feeDto.getTaxId(), orgId);
			} else
				taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(feeDto.getTaxId(),
						receiptMasBean.getOrgId());
			receiptMasBean.setDetails(taxDesc);
			taxdto.put(taxDesc, feeDto.getRfFeeamount());
		}
		   receiptMasBean.setRmDatetemp(UtilityService.convertDateToDDMMYYYY(receiptMasBean.getRmDate()));
	     	receiptMasBean.setTaxdetails(taxdto);
		 }
		
		if (receiptMasBean != null && receiptMasBean.getReceiptModeDetailList() != null) {
		if(receiptMasBean.getReceiptModeDetailList().getCpdFeemode() != null &&!receiptMasBean.getReceiptModeDetailList().getCpdFeemode().equals(MainetConstants.CommonConstant.BLANK)) {
			LookUp lookUp = new LookUp();
			lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(receiptMasBean.getReceiptModeDetailList().getCpdFeemode());
			receiptMasBean.getReceiptModeDetailList().setCpdFeemodeDesc(lookUp.getLookUpDesc());
			receiptMasBean.getReceiptModeDetailList().setCpdFeemodeCode(lookUp.getLookUpCode());
		}
		
		if (receiptMasBean.getReceiptModeDetailList().getCbBankid() != null) {
			BankMasterDTO dto = new BankMasterDTO();
			 dto.setBankId(receiptMasBean.getReceiptModeDetailList().getCbBankid());
			 dto = bankMasterService.getDetailsUsingBankId(dto);
			if (StringUtils.isNotEmpty(dto.getBank()) && StringUtils.isNotEmpty(dto.getBranch())) {
				final String bankName = dto.getBank() + " :: " + dto.getBranch();
			receiptMasBean.getReceiptModeDetailList().setCbBankidDesc(bankName);
			}
		}

		if (receiptMasBean.getReceiptModeDetailList().getRdChequeddno() != null) {
			receiptMasBean.getReceiptModeDetailList()
					.setTranRefNumber(receiptMasBean.getReceiptModeDetailList().getRdChequeddno().toString());
		}
		if(receiptMasBean.getReceiptModeDetailList().getRdChequedddate() != null) {
		final String chkDate = Utility
				.dateToString(receiptMasBean.getReceiptModeDetailList().getRdChequedddate());
		receiptMasBean.getReceiptModeDetailList().setRdChequedddatetemp(chkDate);
		  }
		}
		return receiptMasBean;
	}

 
	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "To fetch receipt details", notes = "To fetch receipt details", response = TbServiceReceiptMasBean.class)
	@Path("/setValuesAndPrintReport/{rmRcptid}/{orgId}/{langId}")
	public ChallanReceiptPrintDTO setValuesAndPrintReport(@PathParam("rmRcptid") Long rmRcptid, @PathParam("orgId") Long orgId,@PathParam("langId") int langId) {
		ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
		printDTO = receiptEntryService.setValuesAndPrintReport(rmRcptid,orgId,langId);
        //#134426
       if (printDTO != null && printDTO.getRmReceiptcategoryId() != null && StringUtils.isEmpty(printDTO.getSubject())) {
			String category = CommonMasterUtility.getCPDDescription(printDTO.getRmReceiptcategoryId(), MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV);
			if (StringUtils.isNotEmpty(category))
			printDTO.setSubject(category);
        }
       //US#134797
       if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			if (printDTO.getDeptId() > 0 && printDTO.getReceiptNo() != null)
				printDTO.setReceiptNo(receiptEntryService.getCustomReceiptNo(printDTO.getDeptId(),Long.valueOf(printDTO.getReceiptNo())));
		}
		return printDTO;
	}
	
}
