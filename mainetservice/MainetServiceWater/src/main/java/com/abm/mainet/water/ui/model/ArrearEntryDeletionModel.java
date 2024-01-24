package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbWtBillScheduleDetail;
import com.abm.mainet.water.dto.WaterPenaltyDto;
import com.abm.mainet.water.service.IWaterPenaltyService;
import com.abm.mainet.water.service.TbWtBillMasService;

@Component
@Scope("session")
public class ArrearEntryDeletionModel extends AbstractFormModel {

	private static final long serialVersionUID = -2116190578645311117L;

	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();
	private Long deptId;

	private List<TbBillMas> tbBillMas = new LinkedList<>();

	private NewWaterConnectionReqDTO newConnectionDto = new NewWaterConnectionReqDTO();

	private List<TbBillMas> billMasList = new LinkedList<>();// Bill Generation List

	private Map<Integer, String> monthprefix = new HashMap<>(0);

	private List<LookUp> schedule = new ArrayList<>(0);

	private List<TbTaxMas> taxesMaster = new ArrayList<>(0);

	private Long schduleId;// for data entry suit

	private List<LookUp> sortedschedule = new ArrayList<>(0);

	private List<TbWtBillScheduleDetail> billScheduleDetail = new ArrayList<>(0);
	
	private Long billNo;
	
	private List<TbServiceReceiptMasEntity> receiptDetails = new ArrayList<TbServiceReceiptMasEntity>();
	
	private List<FinancialYear> finYearMasterList = new ArrayList<FinancialYear>();
	
	private Map<Long, String> finYearData = new LinkedHashMap<>();

	@Autowired
	private TbWtBillMasService tbWtBillMasService;
	
	@Autowired
	private IWaterPenaltyService waterPenaltyService;
	
	@Autowired
	private DepartmentService departmentService;

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<TbBillMas> getTbBillMas() {
		return tbBillMas;
	}

	public void setTbBillMas(List<TbBillMas> tbBillMas) {
		this.tbBillMas = tbBillMas;
	}

	public NewWaterConnectionReqDTO getNewConnectionDto() {
		return newConnectionDto;
	}

	public void setNewConnectionDto(NewWaterConnectionReqDTO newConnectionDto) {
		this.newConnectionDto = newConnectionDto;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public Map<Integer, String> getMonthprefix() {
		return monthprefix;
	}

	public void setMonthprefix(Map<Integer, String> monthprefix) {
		this.monthprefix = monthprefix;
	}

	public List<LookUp> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<LookUp> schedule) {
		this.schedule = schedule;
	}

	public List<TbTaxMas> getTaxesMaster() {
		return taxesMaster;
	}

	public void setTaxesMaster(List<TbTaxMas> taxesMaster) {
		this.taxesMaster = taxesMaster;
	}

	public Long getSchduleId() {
		return schduleId;
	}

	public void setSchduleId(Long schduleId) {
		this.schduleId = schduleId;
	}

	public List<LookUp> getSortedschedule() {
		return sortedschedule;
	}

	public void setSortedschedule(List<LookUp> sortedschedule) {
		this.sortedschedule = sortedschedule;
	}

	public List<TbWtBillScheduleDetail> getBillScheduleDetail() {
		return billScheduleDetail;
	}

	public void setBillScheduleDetail(List<TbWtBillScheduleDetail> billScheduleDetail) {
		this.billScheduleDetail = billScheduleDetail;
	}

	
	public Long getBillNo() {
		return billNo;
	}

	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}
	
	

	public List<TbServiceReceiptMasEntity> getReceiptDetails() {
		return receiptDetails;
	}

	public void setReceiptDetails(List<TbServiceReceiptMasEntity> receiptDetails) {
		this.receiptDetails = receiptDetails;
	}

	
	public List<FinancialYear> getFinYearMasterList() {
		return finYearMasterList;
	}

	public void setFinYearMasterList(List<FinancialYear> finYearMasterList) {
		this.finYearMasterList = finYearMasterList;
	}

	public Map<Long, String> getFinYearData() {
		return finYearData;
	}

	public void setFinYearData(Map<Long, String> finYearData) {
		this.finYearData = finYearData;
	}

	@Override
	public boolean saveForm() {
		List<TbBillMas> billMasList = getBillMasList();

		List<TbServiceReceiptMasEntity> billPaymentDoneList = ApplicationContextProvider
				.getApplicationContext().getBean(ReceiptRepository.class)
				.getCollectionDetails(String.valueOf(billMasList.get(0).getCsIdn()), getDeptId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		if(checkPayDetail(billMasList)) {
			addValidationError(getAppSession().getMessage("Bill payment have been done. Cannot delete bills"));
		}
		if (hasValidationErrors()) {
			return false;
		}
		if (billMasList != null) {
			String ipAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
			Long empId = UserSession.getCurrent().getEmployee().getEmpId();
			tbWtBillMasService.deleteArrearInDetByBmId(billMasList, ipAddress, empId);
			List<WaterPenaltyDto> penaltyDtoList = new ArrayList<WaterPenaltyDto>();
			if (getBillNo() != null && getBillNo() > 0) {
				penaltyDtoList = waterPenaltyService.getWaterPenaltyByCsIdnoAndOrgIdAndBmIdno(
						String.valueOf(billMasList.get(0).getCsIdn()),
						UserSession.getCurrent().getOrganisation().getOrgid(), billMasList.get(0).getBmIdno());
			}else {
				List<Long> conIds = new ArrayList<Long>();
				conIds.add(billMasList.get(0).getCsIdn()); 
				penaltyDtoList = waterPenaltyService.getWaterPenaltyByconIds(conIds, UserSession.getCurrent().getOrganisation().getOrgid());
			}
				if(CollectionUtils.isNotEmpty(penaltyDtoList)) {
					penaltyDtoList.forEach(penalty ->{
						penalty.setActiveFlag('I');
						penalty.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						penalty.setLgIpMacUpd(getClientIpAddress());
						waterPenaltyService.updateWaterPenalty(penalty);
					});
				}else {
				penaltyDtoList = waterPenaltyService.getWaterPenaltyCurrBillAmountByCsIdnoAndOrgId(
						String.valueOf(billMasList.get(0).getCsIdn()),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(penaltyDtoList)) {
					penaltyDtoList.forEach(penalty -> {
						billMasList.forEach(billMas ->{
							if ((penalty.getCreateddate().compareTo(billMas.getBmFromdt()) >= 0
									&& penalty.getCreateddate().compareTo(billMas.getBmTodt()) <= 0)
									|| (penalty.getUpdatedDate() != null
											&& penalty.getUpdatedDate().compareTo(billMas.getBmFromdt()) >= 0
											&& penalty.getUpdatedDate().compareTo(billMas.getBmTodt()) <= 0)) {
								penalty.setActiveFlag('I');
								penalty.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
								penalty.setLgIpMacUpd(getClientIpAddress());
								waterPenaltyService.updateWaterPenalty(penalty);
							}
						});
						
					});
				}
					
				}
			
		}
		setSuccessMessage(getAppSession().getMessage("water.arrears.delete"));
		return true;

	}
	
	public boolean checkPayDetail(List<TbBillMas> billMasList) {
    	AtomicBoolean payStatus = new AtomicBoolean(false);
    	final Department dept = departmentService.getDepartment(MainetConstants.DEPT_SHORT_NAME.WATER,
				MainetConstants.STATUS.ACTIVE);
        List<TbServiceReceiptMasEntity> billPaymentDoneList = ApplicationContextProvider
				.getApplicationContext().getBean(ReceiptRepository.class)
				.getCollectionDetails(String.valueOf(billMasList.get(0).getCsIdn()), dept.getDpDeptid(),
						UserSession.getCurrent().getOrganisation().getOrgid());
        setReceiptDetails(billPaymentDoneList);
        if(CollectionUtils.isNotEmpty(billPaymentDoneList)) {
        	billMasList.forEach(billMas ->{
        		billPaymentDoneList.forEach(payDetail ->{
        			if(StringUtils.equals(payDetail.getReceiptTypeFlag(), MainetConstants.FlagR)) {
        				payDetail.getReceiptFeeDetail().forEach(feeDetail ->{
            				if(feeDetail.getBmIdNo() != null && feeDetail.getBmIdNo().equals(billMas.getBmIdno())) {
            					payStatus.getAndSet(true);
            				}
            			});
        			}
        			
        		});
        	});
        }
        
        return payStatus.get();
    }

}
