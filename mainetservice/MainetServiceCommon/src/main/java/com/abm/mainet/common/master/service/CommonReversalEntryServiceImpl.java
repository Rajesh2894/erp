package com.abm.mainet.common.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.master.dto.TrasactionReversalDTO;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

@Component
public class CommonReversalEntryServiceImpl implements ICommonReversalEntry 
{

	@Autowired
	ReceiptRepository receiptRepository;
	
	
	@Override
	@Transactional
	public List<TbServiceReceiptMasBean> getReceiptByDeptAndDate(TrasactionReversalDTO trasactionReversalDTO,
			Long orgId, Long deptId) {

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<TbServiceReceiptMasBean> ReceiptBeanList = new ArrayList<>();
		
		List<TbServiceReceiptMasEntity> receiptByDeptAndDateList = receiptRepository.getReceiptByDeptAndDate(trasactionReversalDTO.getTransactionNo(), orgId,	trasactionReversalDTO.getTransactionDate(), deptId);
		
		
		for (TbServiceReceiptMasEntity ReceiptMasEntity : receiptByDeptAndDateList) {
			
			TbSrcptModesDetEntity receiptModeDetail = ReceiptMasEntity.getReceiptModeDetail().get(0);
			String payMode = CommonMasterUtility.getNonHierarchicalLookUpObject(receiptModeDetail.getCpdFeemode(),
					organisation).getDescLangFirst();
			TbServiceReceiptMasBean ReceiptMasBean = new TbServiceReceiptMasBean();
	
			BeanUtils.copyProperties(ReceiptMasEntity, ReceiptMasBean);
			
			List<TbSrcptFeesDetBean>  tbSrcptFeesDetBeanList= new ArrayList<>();
			List<TbSrcptFeesDetEntity> receiptFeeDetail = ReceiptMasEntity.getReceiptFeeDetail();
			for (TbSrcptFeesDetEntity tbSrcptFeesDetEntity : receiptFeeDetail) 
			{
				TbSrcptFeesDetBean tbSrcptFeesDetBean=new TbSrcptFeesDetBean();
				BeanUtils.copyProperties(tbSrcptFeesDetEntity,tbSrcptFeesDetBean);
				
				tbSrcptFeesDetBeanList.add(tbSrcptFeesDetBean);
			}
			ReceiptMasBean.setReceiptFeeDetail(tbSrcptFeesDetBeanList);
			
			/*if (!assoOwnerName.isEmpty() || assoOwnerName != null) {
				ReceiptMasBean.setVmVendorIdDesc(assoOwnerName);
			}*/

			ReceiptMasBean.setAdditionalRefNo(ReceiptMasEntity.getAdditionalRefNo());
			ReceiptMasBean.setRmRcptid(ReceiptMasEntity.getRmRcptid());
			ReceiptMasBean.setRmRcptno(ReceiptMasEntity.getRmRcptno());
			ReceiptMasBean.setRmAmount(ReceiptMasEntity.getRmAmount().toString());
			ReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeDesc(payMode);
			ReceiptMasBean.setRmDatetemp(UtilityService.convertDateToDDMMYYYY(ReceiptMasEntity.getRmDate()));
			
			ReceiptMasBean.setModeId(receiptModeDetail.getCpdFeemode());
			
			ReceiptMasBean.getTbAcFieldMaster().setFieldId(ReceiptMasEntity.getFieldId());
			
			ReceiptMasBean.setRmReceivedfrom(ReceiptMasEntity.getRmReceivedfrom());
			
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)) {
				List<TbSrcptModesDetBean> receiptModeList = new ArrayList<>();
				for(TbSrcptModesDetEntity modeEntity : ReceiptMasEntity.getReceiptModeDetail()) {
					TbSrcptModesDetBean modesBean = new TbSrcptModesDetBean();
					BeanUtils.copyProperties(modeEntity, modesBean);
					receiptModeList.add(modesBean);
				}
				ReceiptMasBean.setReceiptModeList(receiptModeList);
			}
			
			
			ReceiptBeanList.add(ReceiptMasBean);
		}
	
		return ReceiptBeanList;
	}

	@Override
	@Transactional
	public int[] validateReceipt(TrasactionReversalDTO trasactionReversalDTO, Long orgId, Long deptId,long receiptNo) {
	
		
		int[] count =new int[2];
		
		//Long faYearId =iFinancialYearService.getFinanceYearId(trasactionReversalDTO.getTransactionDate());
		
		count[0] =receiptRepository.getCountAllNextGeneratedReceipt(trasactionReversalDTO.getReferenceNo(), orgId,receiptNo,deptId);
		
		return count;
	}

	@Override
	@Transactional
	public int updateReceipt(TbServiceReceiptMasBean ReceiptMasBean, long orgid, long userId) {
	
		
		
		receiptRepository.updateSetForReceiptDelFlag("Y",ReceiptMasBean.getRmRcptid(), orgid, ReceiptMasBean.getReceiptDelRemark(), userId,ReceiptMasBean.getDpDeptId(),ReceiptMasBean.getLgIpMacUpd(), new Date());
		
		return 0;
	}

	
	@Override
	@Transactional
	public List<TbServiceReceiptMasBean> getReceiptByDeptAndDateAndTransactionId(TrasactionReversalDTO trasactionReversalDTO,
			Long orgId, Long deptId, String transactionId) {

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		List<TbServiceReceiptMasBean> ReceiptBeanList = new ArrayList<>();
		
		List<TbServiceReceiptMasEntity> receiptByDeptAndDateList = receiptRepository.getReceiptByDeptAndDateAndTransactionId(transactionId, orgId,	trasactionReversalDTO.getTransactionDate(), deptId);
		
		
		for (TbServiceReceiptMasEntity ReceiptMasEntity : receiptByDeptAndDateList) {
			
			TbSrcptModesDetEntity receiptModeDetail = ReceiptMasEntity.getReceiptModeDetail().get(0);
			String payMode = CommonMasterUtility.getNonHierarchicalLookUpObject(receiptModeDetail.getCpdFeemode(),
					organisation).getDescLangFirst();
			TbServiceReceiptMasBean ReceiptMasBean = new TbServiceReceiptMasBean();
	
			BeanUtils.copyProperties(ReceiptMasEntity, ReceiptMasBean);
			
			List<TbSrcptFeesDetBean>  tbSrcptFeesDetBeanList= new ArrayList<>();
			List<TbSrcptFeesDetEntity> receiptFeeDetail = ReceiptMasEntity.getReceiptFeeDetail();
			for (TbSrcptFeesDetEntity tbSrcptFeesDetEntity : receiptFeeDetail) 
			{
				TbSrcptFeesDetBean tbSrcptFeesDetBean=new TbSrcptFeesDetBean();
				BeanUtils.copyProperties(tbSrcptFeesDetEntity,tbSrcptFeesDetBean);
				
				tbSrcptFeesDetBeanList.add(tbSrcptFeesDetBean);
			}
			ReceiptMasBean.setReceiptFeeDetail(tbSrcptFeesDetBeanList);
			
			/*if (!assoOwnerName.isEmpty() || assoOwnerName != null) {
				ReceiptMasBean.setVmVendorIdDesc(assoOwnerName);
			}*/

			ReceiptMasBean.setAdditionalRefNo(ReceiptMasEntity.getAdditionalRefNo());
			ReceiptMasBean.setRmRcptid(ReceiptMasEntity.getRmRcptid());
			ReceiptMasBean.setRmRcptno(ReceiptMasEntity.getRmRcptno());
			ReceiptMasBean.setRmAmount(ReceiptMasEntity.getRmAmount().toString());
			ReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeDesc(payMode);
			ReceiptMasBean.setRmDatetemp(UtilityService.convertDateToDDMMYYYY(ReceiptMasEntity.getRmDate()));
			
			ReceiptMasBean.setModeId(receiptModeDetail.getCpdFeemode());
			
			ReceiptMasBean.getTbAcFieldMaster().setFieldId(ReceiptMasEntity.getFieldId());
			
			ReceiptMasBean.setRmReceivedfrom(ReceiptMasEntity.getRmReceivedfrom());
			
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)) {
				List<TbSrcptModesDetBean> receiptModeList = new ArrayList<>();
				for(TbSrcptModesDetEntity modeEntity : ReceiptMasEntity.getReceiptModeDetail()) {
					TbSrcptModesDetBean modesBean = new TbSrcptModesDetBean();
					BeanUtils.copyProperties(modeEntity, modesBean);
					receiptModeList.add(modesBean);
				}
				ReceiptMasBean.setReceiptModeList(receiptModeList);
			}
			
			
			ReceiptBeanList.add(ReceiptMasBean);
		}
	
		return ReceiptBeanList;
	}
}
