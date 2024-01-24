package com.abm.mainet.rnl.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.repository.ReportSummaryRepository;

@Service
public class AppreciationInContractAggrementServiceImpl implements IAppreciationInContractAggrementService{
	
	@Resource
	TbDepartmentService departmentService;
	
	@Autowired
    private ContractAgreementRepository contractAgreementRepository;
	
	@Autowired
	private IContractAgreementService contractAgreementService;

	@Autowired
	private IRLBILLMasterService iRLBILLMasterService;
	
	@Autowired
    private ReportSummaryRepository reportSummaryRepository;
	
	
   

    private static final Logger LOGGER = Logger.getLogger(AppreciationInContractAggrementServiceImpl.class);
	
	@Override
	@Transactional
	public void appreciationInRent(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		LOGGER.info("Method Started---------------------------------------->");
		Organisation org1 = new Organisation();
		org1.setOrgid(runtimeBean.getOrgId().getOrgid());
		
        if (Utility.isEnvPrefixAvailable(org1, MainetConstants.ENV_TSCL)) {
        	
        	
        	
		Organisation org = new Organisation();
		org.setOrgid(runtimeBean.getOrgId().getOrgid());
		LOGGER.info("Organisation --------------------------->"+org.getOrgid());
		
		Long lookUp = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("A", "APA",org.getOrgid());
		LOGGER.info("APA Perfix value----------------------------->"+lookUp);
		Long deptId = departmentService.findDepartmentByCode("RL").getDpDeptid();
		LOGGER.info("Department id----------------------------->"+deptId);
		List<ContractMastEntity> masEntity = contractAgreementRepository.findAllContractUnder(deptId,org.getOrgid(), lookUp);
		
		masEntity.forEach(entity -> {
			boolean value =false;
			Date contractdate =null;
			Double newAmt=null;
			Double newLastInstAmount =null;
			LOGGER.info("Agreement record found ------------------>");
			Double newContractAmt = null;
			Double newAppAmount = null;
			Double newAppAmount1 = null;
			Date newDueDate2= null;
			Date newDueDateRl2=null;
			Double newLastInstAmountRL=null;
			ContractDetailDTO contractDetailDTO = null;
			contractDetailDTO = contractAgreementService.getContractDetail(entity.getContId());
			if(contractDetailDTO.getContDefectLiabilityPer()!=null) {
			String lastDate= Utility.dateToString(Utility.getAddedYearsBy(contractDetailDTO.getContFromDate(), contractDetailDTO.getContDefectLiabilityPer().intValue()));
			String currentDate=Utility.dateToString(new Date());
			if (lastDate.compareTo(currentDate) > 0 || (lastDate.compareTo(currentDate) == 0)) {
			for (int i = contractDetailDTO.getContToPeriod().intValue(); i <= contractDetailDTO.getContDefectLiabilityPer().intValue(); i++) {
				contractdate= Utility.getAddedYearsBy(contractDetailDTO.getContFromDate(), i);
				Calendar c = Calendar.getInstance();
				c.setTime(contractdate);
			
				int day = c.get(Calendar.DAY_OF_MONTH);
				
				contractdate.setDate(day-1);
				value = Utility.comapreDates(new Date(), contractdate);
				if (value)
					break;
				i = i + (contractDetailDTO.getContToPeriod().intValue()-1);
			}
			
			if (value) {
				LOGGER.info("Date validated---------------------"+contractdate);
				final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntities = contractAgreementRepository
						.finAllRecord(entity.getContId(), MainetConstants.RnLCommon.Y_FLAG);
				String lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
						Long.valueOf(entity.getApprType()), org.getOrgid(),
						MainetConstants.WorksManagement.VTY).getLookUpCode();
				LOGGER.info("VTY perfix value---------------------"+lokkup);
					LookUp lookUp1 = CommonMasterUtility.getValueFromPrefixLookUp("AIL", "ADD", org);
					if (lookUp1 != null && lookUp1.getOtherField().equals("Y")) {
						//newAppAmount1= contractDetailDTO.getContAmount();
						newAppAmount = contractInstalmentDetailEntities.get(1).getConitAmount();
						//newAppAmount = newAppAmount1 / 12;
					} else {
						newAppAmount = contractDetailDTO.getContAmount();
					}
					
					LookUp lookUp2 = CommonMasterUtility.getValueFromPrefixLookUp("DW", "IDD", org);
					LookUp lookUp3 = CommonMasterUtility.getValueFromPrefixLookUp("MW", "IDD", org1);
					
					if (lookUp2 != null && lookUp2.getOtherField().equals("13") && lookUp2.getDefaultVal().equals("Y")) {
						
						contractDetailDTO.setContInstallmentPeriod(13l);
					}else if(lookUp3 != null && lookUp3.getOtherField().equals("12") && lookUp3.getDefaultVal().equals("Y")) {
						contractDetailDTO.setContInstallmentPeriod(12l);
						
					}
					
				if (lokkup.equals("PER")) {
					newContractAmt = newAppAmount+((newAppAmount / 100) * (entity.getApprAmt()));
				} else if (lokkup.equals("AMT")) {
					newContractAmt = newAppAmount + entity.getApprAmt();
				} else if (lokkup.equals("MUL")) {
					newContractAmt = newAppAmount * entity.getApprAmt();
				}
				LOGGER.info("New Amount---------------------"+newContractAmt);
				if (newContractAmt != null) {
					if (lookUp1 != null && lookUp1.getOtherField().equals("Y")) {
						newAmt = newContractAmt;
					} else {
						 newAmt = newContractAmt / contractDetailDTO.getContInstallmentPeriod();
					}
					LOGGER.info("Amount after installment---------------------"+newAmt);
					final List<RLBillMaster> rLBillMasterList = new ArrayList<>();
					List<ContractInstalmentDetailEntity> list = new ArrayList<>();
					RLBillMaster billMaster = null;
					Date date1 = Utility.getAddedMonthsBy(contractInstalmentDetailEntities.get(0).getConitDueDate(), 0);
					ContractInstalmentDetailEntity contractInstt = null;
					
					if(lookUp3 != null && lookUp3.getOtherField().equals("12") && lookUp3.getDefaultVal().equals("Y")) {
						
						for (int i = 0; i < contractDetailDTO.getContInstallmentPeriod(); i++) {
							Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
							Date newDueDate = Utility.getAddedMonthsBy(date1, (i+1));
							contractInstt = new ContractInstalmentDetailEntity();
							contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
							contractInstt.setContId(entity);
							contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
							contractInstt.setConitValue(newAmt);
							contractInstt.setConitDueDate(newDueDate);
							contractInstt.setConttActive("Y");
							contractInstt.setOrgId(org.getOrgid());
							contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
							contractInstt.setLmodDate(new Date());
							contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
							contractInstt.setConitAmount(newAmt);
							contractInstt.setConitStartDate(newStartDate);
							list.add(contractInstt);
						}
						
					}else {
					for (int i = 0; i < contractDetailDTO.getContInstallmentPeriod(); i++) {
						if(i==0) {
							
							Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
							
							newDueDate2 = Utility.getAddedMonthsBy(date1, (i));
							newDueDate2 =DateUtils.addDays(newDueDate2, 1);
							Calendar calendar = Calendar.getInstance(); 
							calendar.setTime(newDueDate2);
							//calendar.add(Calendar.DATE, 1);
							//c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
							 
					         

					        calendar.add(Calendar.MONTH, 1);  
					        calendar.set(Calendar.DAY_OF_MONTH, 1);  
					        calendar.add(Calendar.DATE, -1);  

					       
							Date lastDayOfMonth = calendar.getTime(); 
							 
							int day = calendar.get(Calendar.DAY_OF_MONTH);
							Double firstInstallmentAmt=newAmt/day;
							 
							int difference =Utility.getDaysBetDates(newDueDate2, lastDayOfMonth);
						
		                	Double newAmount =firstInstallmentAmt*difference;
		                	
		                	newLastInstAmount =newAmt-newAmount;
							contractInstt = new ContractInstalmentDetailEntity();
							contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
							contractInstt.setContId(entity);
							contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
							contractInstt.setConitValue(newAmount);
							contractInstt.setConitDueDate(newDueDate2);
							contractInstt.setConttActive("Y");
							contractInstt.setOrgId(org.getOrgid());
							contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
							contractInstt.setLmodDate(new Date());
							contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
							contractInstt.setConitAmount(newAmount);
							contractInstt.setConitStartDate(newStartDate);
							list.add(contractInstt);
							
						}else if(i == contractDetailDTO.getContInstallmentPeriod()-1) {
							
							Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
							Date newDueDate = Utility.getAddedMonthsBy(date1, (i));
							
							Calendar c = Calendar.getInstance();
							c.setTime(newDueDate2);
						
							int day = c.get(Calendar.DAY_OF_MONTH);
							//newDueDate.setDate(day);
							//Date newDueDateDay =DateUtils.addDays(newDueDate, day);
							newDueDate.setDate(day-1);
							/*Calendar c2 = Calendar.getInstance();
							c2.setTime(newDueDate);
							c2.set(Calendar.DAY_OF_MONTH,day);*/
							
						
							
							contractInstt = new ContractInstalmentDetailEntity();
							contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
							contractInstt.setContId(entity);
							contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
							contractInstt.setConitValue(newLastInstAmount);
							contractInstt.setConitDueDate(newDueDate);
							contractInstt.setConttActive("Y");
							contractInstt.setOrgId(org.getOrgid());
							contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
							contractInstt.setLmodDate(new Date());
							contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
							contractInstt.setConitAmount(newLastInstAmount);
							contractInstt.setConitStartDate(newStartDate);
							list.add(contractInstt);
						}else {
							
						Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
						Date newDueDate1 = Utility.getAddedMonthsBy(date1, (i));
						
						Calendar c = Calendar.getInstance();
						c.setTime(newDueDate1);
						c.set(Calendar.DAY_OF_MONTH,1);
						Date newDate = c.getTime(); 
					
						contractInstt = new ContractInstalmentDetailEntity();
						contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
						contractInstt.setContId(entity);
						contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
						contractInstt.setConitValue(newAmt);
						contractInstt.setConitDueDate(newDate);
						contractInstt.setConttActive("Y");
						contractInstt.setOrgId(org.getOrgid());
						contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
						contractInstt.setLmodDate(new Date());
						contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
						contractInstt.setConitAmount(newAmt);
						contractInstt.setConitStartDate(newStartDate);
						list.add(contractInstt);
						}
					}
					
					}
					entity.setContractInstalmentDetailList(list);
					contractAgreementRepository.save(entity);

					/*final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntitie = reportSummaryRepository
							.finAllRecordsNotInBill(entity.getContId(), MainetConstants.RnLCommon.Y_FLAG);*/

					/*for (int i = 0; i < list.size(); i++) {*/
						
						
						/*
							
							Iterator<ContractInstalmentDetailEntity> iterator = list.iterator();
							while (iterator.hasNext()) {*/
								for(	ContractInstalmentDetailEntity instData : list) {
									billMaster = new RLBillMaster();
								/*ContractInstalmentDetailEntity instData = iterator.next();*/
								
								billMaster.setActive(MainetConstants.RnLCommon.Y_FLAG);
								billMaster.setCreatedDate(new Date());
								billMaster.setLgIpMac(instData.getLgIpMac());
								billMaster.setOrgId(org.getOrgid());
								billMaster.setCreatedBy(instData.getCreatedBy());
								billMaster.setContId(entity.getContId());
								billMaster.setBillDate(new Date());
								billMaster.setBalanceAmount(instData.getConitAmount());
								billMaster.setAmount(instData.getConitAmount());
								billMaster.setConitId(instData.getContId().getContId());
								billMaster.setPaidFlag(MainetConstants.RnLCommon.N_FLAG);
								billMaster.setTaxId(instData.getTaxId());
								billMaster.setStartDate(instData.getConitStartDate());
								billMaster.setDueDate(instData.getConitDueDate());
								billMaster.setBmType("B");
								billMaster.setPartialPaidFlag(MainetConstants.RnLCommon.N_FLAG);
								rLBillMasterList.add(billMaster);
							}
							
						
						
					
					iRLBILLMasterService.save(rLBillMasterList);
				}
			}
			}
			}
		});
		
		
		Long lookUpNA = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("NA", "APA",org.getOrgid());
		LOGGER.info("APA Perfix value----------------------------->"+lookUpNA);
		
	
		List<ContractMastEntity> masEntityNA = contractAgreementRepository.findAllContractUnder(deptId,org.getOrgid(), lookUpNA);
		
			masEntityNA.forEach(entity -> {
				boolean value =false;
				Date contractdate =null;
				Double newAmt=null;
				Double newLastInstAmount =null;
				LOGGER.info("Agreement record found ------------------>");
				Double newContractAmt = null;
				Double newAppAmount = null;
				Date newDueDate2= null;
				Date newDueDateRl2=null;
				Double newLastInstAmountRL=null;
				ContractDetailDTO contractDetailDTO = null;
				contractDetailDTO = contractAgreementService.getContractDetail(entity.getContId());
				
				/*final ContractMastEntity entityContMas = contractAgreementRepository.getContractByContId(entity.getContId(),org.getOrgid());*/
				
				if(entity.getContRenewal().equals(MainetConstants.FlagY)) {
					
				
				if(contractDetailDTO.getContDefectLiabilityPer()!=null) {
				String lastDate= Utility.dateToString(Utility.getAddedYearsBy(contractDetailDTO.getContFromDate(), contractDetailDTO.getContDefectLiabilityPer().intValue()));
				String currentDate=Utility.dateToString(new Date());
				if (lastDate.compareTo(currentDate) > 0 || (lastDate.compareTo(currentDate) == 0)) {
				for (int i = contractDetailDTO.getContToPeriod().intValue(); i <= contractDetailDTO.getContDefectLiabilityPer().intValue(); i++) {
					contractdate= Utility.getAddedYearsBy(contractDetailDTO.getContFromDate(), i);
					Calendar c = Calendar.getInstance();
					c.setTime(contractdate);
				
					int day = c.get(Calendar.DAY_OF_MONTH);
					
					contractdate.setDate(day-1);
					value = Utility.comapreDates(new Date(), contractdate);
					if (value)
						break;
					i = i + (contractDetailDTO.getContToPeriod().intValue()-1);
				}
				
				if (value) {
					LOGGER.info("Date validated---------------------"+contractdate);
					final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntities = contractAgreementRepository
							.finAllRecord(entity.getContId(), MainetConstants.RnLCommon.Y_FLAG);
					/*String lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
							Long.valueOf(entity.getApprType()), org.getOrgid(),
							MainetConstants.WorksManagement.VTY).getLookUpCode();
					LOGGER.info("VTY perfix value---------------------"+lokkup);*/
					       newAppAmount = contractDetailDTO.getContAmount();
					
					       newContractAmt = newAppAmount / 12;
					 
							//newAppAmount = contractInstalmentDetailEntities.get(1).getConitAmount();
	                    
					
					        LookUp lookUp5 = CommonMasterUtility.getValueFromPrefixLookUp("MW", "IDD", org1);
					
							LookUp lookUp4 = CommonMasterUtility.getValueFromPrefixLookUp("DW", "IDD", org);
							if (lookUp4 != null && lookUp4.getOtherField().equals("13")) {
								
								contractDetailDTO.setContInstallmentPeriod(13l);
							}else if(lookUp5 != null && lookUp5.getOtherField().equals("12") && lookUp5.getDefaultVal().equals("Y")) {
								contractDetailDTO.setContInstallmentPeriod(12l);
								
							}
					if (newContractAmt != null) {
					
							newAmt = newContractAmt;
						
						LOGGER.info("Amount after installment---------------------"+newAmt);
						final List<RLBillMaster> rLBillMasterList = new ArrayList<>();
						List<ContractInstalmentDetailEntity> list = new ArrayList<>();
						RLBillMaster billMaster = null;
						Date date1 = Utility.getAddedMonthsBy(contractInstalmentDetailEntities.get(0).getConitDueDate(), 0);
						ContractInstalmentDetailEntity contractInstt = null;
						
						if(lookUp5 != null && lookUp5.getOtherField().equals("12") && lookUp5.getDefaultVal().equals("Y")) {
							
							for (int i = 0; i < contractDetailDTO.getContInstallmentPeriod(); i++) {
								Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
								Date newDueDate = Utility.getAddedMonthsBy(date1, (i+1));
								contractInstt = new ContractInstalmentDetailEntity();
								contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
								contractInstt.setContId(entity);
								contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
								contractInstt.setConitValue(newAmt);
								contractInstt.setConitDueDate(newDueDate);
								contractInstt.setConttActive("Y");
								contractInstt.setOrgId(org.getOrgid());
								contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
								contractInstt.setLmodDate(new Date());
								contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
								contractInstt.setConitAmount(newAmt);
								contractInstt.setConitStartDate(newStartDate);
								list.add(contractInstt);
							}
							
						}else {
					
						for (int i = 0; i < contractDetailDTO.getContInstallmentPeriod(); i++) {
							if(i==0) {
								
								Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
								
								newDueDate2 = Utility.getAddedMonthsBy(date1, (i));
								newDueDate2 =DateUtils.addDays(newDueDate2, 1);
								Calendar calendar = Calendar.getInstance(); 
								calendar.setTime(newDueDate2);
								//calendar.add(Calendar.DATE, 1);
								//c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
								 
						         

						        calendar.add(Calendar.MONTH, 1);  
						        calendar.set(Calendar.DAY_OF_MONTH, 1);  
						        calendar.add(Calendar.DATE, -1);  

						       
								Date lastDayOfMonth = calendar.getTime(); 
								 
								int day = calendar.get(Calendar.DAY_OF_MONTH);
								Double firstInstallmentAmt=newAmt/day;
								 
								int difference =Utility.getDaysBetDates(newDueDate2, lastDayOfMonth);
							
			                	Double newAmount =firstInstallmentAmt*difference;
			                	
			                	newLastInstAmount =newAmt-newAmount;
								contractInstt = new ContractInstalmentDetailEntity();
								contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
								contractInstt.setContId(entity);
								contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
								contractInstt.setConitValue(newAmount);
								contractInstt.setConitDueDate(newDueDate2);
								contractInstt.setConttActive("Y");
								contractInstt.setOrgId(org.getOrgid());
								contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
								contractInstt.setLmodDate(new Date());
								contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
								contractInstt.setConitAmount(newAmount);
								contractInstt.setConitStartDate(newStartDate);
								list.add(contractInstt);
								
							}else if(i == contractDetailDTO.getContInstallmentPeriod()-1) {
								
								Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
								Date newDueDate = Utility.getAddedMonthsBy(date1, (i));
								
								Calendar c = Calendar.getInstance();
								c.setTime(newDueDate2);
							
								int day = c.get(Calendar.DAY_OF_MONTH);
								//newDueDate.setDate(day);
								//Date newDueDateDay =DateUtils.addDays(newDueDate, day);
								newDueDate.setDate(day-1);
								/*Calendar c2 = Calendar.getInstance();
								c2.setTime(newDueDate);
								c2.set(Calendar.DAY_OF_MONTH,day);*/
								
							
								
								contractInstt = new ContractInstalmentDetailEntity();
								contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
								contractInstt.setContId(entity);
								contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
								contractInstt.setConitValue(newLastInstAmount);
								contractInstt.setConitDueDate(newDueDate);
								contractInstt.setConttActive("Y");
								contractInstt.setOrgId(org.getOrgid());
								contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
								contractInstt.setLmodDate(new Date());
								contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
								contractInstt.setConitAmount(newLastInstAmount);
								contractInstt.setConitStartDate(newStartDate);
								list.add(contractInstt);
							}else {
								
							Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
							Date newDueDate1 = Utility.getAddedMonthsBy(date1, (i));
							
							Calendar c = Calendar.getInstance();
							c.setTime(newDueDate1);
							c.set(Calendar.DAY_OF_MONTH,1);
							Date newDate = c.getTime(); 
						
							contractInstt = new ContractInstalmentDetailEntity();
							contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
							contractInstt.setContId(entity);
							contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
							contractInstt.setConitValue(newAmt);
							contractInstt.setConitDueDate(newDate);
							contractInstt.setConttActive("Y");
							contractInstt.setOrgId(org.getOrgid());
							contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
							contractInstt.setLmodDate(new Date());
							contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
							contractInstt.setConitAmount(newAmt);
							contractInstt.setConitStartDate(newStartDate);
							list.add(contractInstt);
							}
						}
						
						}
						entity.setContractInstalmentDetailList(list);
						contractAgreementRepository.save(entity);

						/*final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntitie = reportSummaryRepository
								.finAllRecordsNotInBill(entity.getContId(), MainetConstants.RnLCommon.Y_FLAG);*/

						/*for (int i = 0; i < list.size(); i++) {*/
							
							
							/*
								
								Iterator<ContractInstalmentDetailEntity> iterator = list.iterator();
								while (iterator.hasNext()) {*/
									for(	ContractInstalmentDetailEntity instData : list) {
										billMaster = new RLBillMaster();
									/*ContractInstalmentDetailEntity instData = iterator.next();*/
									
									billMaster.setActive(MainetConstants.RnLCommon.Y_FLAG);
									billMaster.setCreatedDate(new Date());
									billMaster.setLgIpMac(instData.getLgIpMac());
									billMaster.setOrgId(org.getOrgid());
									billMaster.setCreatedBy(instData.getCreatedBy());
									billMaster.setContId(entity.getContId());
									billMaster.setBillDate(new Date());
									billMaster.setBalanceAmount(instData.getConitAmount());
									billMaster.setAmount(instData.getConitAmount());
									billMaster.setConitId(instData.getContId().getContId());
									billMaster.setPaidFlag(MainetConstants.RnLCommon.N_FLAG);
									billMaster.setTaxId(instData.getTaxId());
									billMaster.setStartDate(instData.getConitStartDate());
									billMaster.setDueDate(instData.getConitDueDate());
									billMaster.setBmType("B");
									billMaster.setPartialPaidFlag(MainetConstants.RnLCommon.N_FLAG);
									rLBillMasterList.add(billMaster);
								}
								
							
							
						
						iRLBILLMasterService.save(rLBillMasterList);
					}
				}
				}
				}
				
				}
			});
			
			
		
	}else {
		Organisation org = new Organisation();
		org.setOrgid(runtimeBean.getOrgId().getOrgid());
		LOGGER.info("Organisation --------------------------->"+org.getOrgid());
		Long lookUp = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("A", "APA",org.getOrgid());
		LOGGER.info("APA Perfix value----------------------------->"+lookUp);
		Long deptId = departmentService.findDepartmentByCode("RL").getDpDeptid();
		LOGGER.info("Department id----------------------------->"+deptId);
		List<ContractMastEntity> masEntity = contractAgreementRepository.findAllContractUnder(deptId,org.getOrgid(), lookUp);
		
		masEntity.forEach(entity -> {
			boolean value =false;
			Date contractdate =null;
			Double newAmt=null;
			LOGGER.info("Agreement record found ------------------>");
			Double newContractAmt = null;
			Double newAppAmount = null;
			ContractDetailDTO contractDetailDTO = null;
			contractDetailDTO = contractAgreementService.getContractDetail(entity.getContId());
			if(contractDetailDTO.getContDefectLiabilityPer()!=null) {
			String lastDate= Utility.dateToString(Utility.getAddedYearsBy(contractDetailDTO.getContFromDate(), contractDetailDTO.getContDefectLiabilityPer().intValue()));
			String currentDate=Utility.dateToString(new Date());
			if (lastDate.compareTo(currentDate) > 0 || (lastDate.compareTo(currentDate) == 0)) {
			for (int i = contractDetailDTO.getContToPeriod().intValue(); i <= contractDetailDTO.getContDefectLiabilityPer().intValue(); i++) {
				contractdate= Utility.getAddedYearsBy(contractDetailDTO.getContFromDate(), i);
				value = Utility.comapreDates(new Date(), contractdate);
				if (value)
					break;
				i = i + (contractDetailDTO.getContToPeriod().intValue()-1);
			}
			
			if (value) {
				LOGGER.info("Date validated---------------------"+contractdate);
				final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntities = contractAgreementRepository
						.finAllRecord(entity.getContId(), MainetConstants.RnLCommon.Y_FLAG);
				String lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
						Long.valueOf(entity.getApprType()), org.getOrgid(),
						MainetConstants.WorksManagement.VTY).getLookUpCode();
				LOGGER.info("VTY perfix value---------------------"+lokkup);
					LookUp lookUp1 = CommonMasterUtility.getValueFromPrefixLookUp("AIL", "ADD", org);
					if (lookUp1 != null && lookUp1.getOtherField().equals("Y")) {
						newAppAmount = contractInstalmentDetailEntities.get(0).getConitAmount();
					} else {
						newAppAmount = contractDetailDTO.getContAmount();
					}
					
					
				if (lokkup.equals("PER")) {
					newContractAmt = newAppAmount+((newAppAmount / 100) * (entity.getApprAmt()));
				} else if (lokkup.equals("AMT")) {
					newContractAmt = newAppAmount + entity.getApprAmt();
				} else if (lokkup.equals("MUL")) {
					newContractAmt = newAppAmount * entity.getApprAmt();
				}
				LOGGER.info("New Amount---------------------"+newContractAmt);
				if (newContractAmt != null) {
					if (lookUp1 != null && lookUp1.getOtherField().equals("Y")) {
						newAmt = newContractAmt;
					} else {
						 newAmt = newContractAmt / contractDetailDTO.getContInstallmentPeriod();
					}
					LOGGER.info("Amount after installment---------------------"+newAmt);
					final List<RLBillMaster> rLBillMasterList = new ArrayList<>();
					List<ContractInstalmentDetailEntity> list = new ArrayList<>();
					RLBillMaster billMaster = null;
					Date date1 = Utility.getAddedMonthsBy(contractInstalmentDetailEntities.get(0).getConitDueDate(), 0);
					ContractInstalmentDetailEntity contractInstt = null;
					for (int i = 0; i < contractDetailDTO.getContInstallmentPeriod(); i++) {
						Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
						Date newDueDate = Utility.getAddedMonthsBy(date1, (i+1));
						contractInstt = new ContractInstalmentDetailEntity();
						contractInstt.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
						contractInstt.setContId(entity);
						contractInstt.setConitAmtType(contractInstalmentDetailEntities.get(0).getConitAmtType());
						contractInstt.setConitValue(newAmt);
						contractInstt.setConitDueDate(newDueDate);
						contractInstt.setConttActive("Y");
						contractInstt.setOrgId(org.getOrgid());
						contractInstt.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
						contractInstt.setLmodDate(new Date());
						contractInstt.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
						contractInstt.setConitAmount(newAmt);
						contractInstt.setConitStartDate(newStartDate);
						list.add(contractInstt);
					}
					entity.setContractInstalmentDetailList(list);
					contractAgreementRepository.save(entity);

					final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntitie = reportSummaryRepository
							.finAllRecordsNotInBill(entity.getContId(), MainetConstants.RnLCommon.Y_FLAG);

					for (int i = 0; i < contractInstalmentDetailEntitie.size(); i++) {
						billMaster = new RLBillMaster();
						Date newStartDate = Utility.getAddedMonthsBy(date1, (i));;
						Date newDueDate = Utility.getAddedMonthsBy(date1, (i+1));
						billMaster.setActive(MainetConstants.RnLCommon.Y_FLAG);
						billMaster.setCreatedDate(new Date());
						billMaster.setLgIpMac(contractInstalmentDetailEntities.get(0).getLgIpMac());
						billMaster.setOrgId(org.getOrgid());
						billMaster.setCreatedBy(contractInstalmentDetailEntities.get(0).getCreatedBy());
						billMaster.setContId(entity.getContId());
						billMaster.setBillDate(new Date());
						billMaster.setBalanceAmount(newAmt);
						billMaster.setAmount(newAmt);
						billMaster.setConitId(contractInstalmentDetailEntitie.get(i).getConitId());
						billMaster.setPaidFlag(MainetConstants.RnLCommon.N_FLAG);
						billMaster.setTaxId(contractInstalmentDetailEntities.get(0).getTaxId());
						billMaster.setStartDate(newStartDate);
						billMaster.setDueDate(newDueDate);
						billMaster.setBmType("B");
						rLBillMasterList.add(billMaster);
					}
					iRLBILLMasterService.save(rLBillMasterList);
				}
			}
			}
			}
		});
		
		
		
	}
	}

}
