package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.TbMrdataJpaRepository;
import com.abm.mainet.water.domain.TbMrdataEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillGenErrorDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.ui.validator.WaterBillGenerationValidator;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class WaterBillGenerationModel extends
        AbstractFormModel {

    private static final long serialVersionUID = 2640492035595639136L;

    @Autowired
    private NewWaterConnectionService newWaterConnectionService;

    @Autowired
    private TbWtBillMasService tbWtBillMasService;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    private Map<Long, WaterBillGenErrorDTO> errorListMap = new HashMap<>(
            0);

    private TbCsmrInfoDTO entity = null;

    private List<TbCsmrInfoDTO> entityList = null;

    private List<TbTaxMas> taxMas = null;

    private String billRemarks;

    private Date billDate;

    private String billCcnType;

    private Long billFrequency;

    private int billSize;

    private int errorSize;

    private int billselectSize;

    @Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.TRF:
            return "entity.trmGroup";

        case PrefixConstants.NewWaterServiceConstants.WWZ:
            return "entity.codDwzid";

        default:
            return null;

        }
    }

    /**
     * @return
     */
    public boolean searchWaterBillRecords() {
        if (getBillCcnType().equals(null)) {
            addValidationError(getAppSession()
                    .getMessage("water.billGen.ccnType"));
        } else if (MainetConstants.SEARCH.equals(getBillCcnType())) {
            if ((getEntity().getCsCcn().equals(null))
                    || getEntity().getCsCcn()
                            .isEmpty()) {
                addValidationError(getAppSession()
                        .getMessage(
                                "water.billGen.ccnNumber"));
            }else {
            	Long checkValidConn = newWaterConnectionService.checkValidConnectionNumber(getEntity().getCsCcn(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (checkValidConn == null || checkValidConn <= 0) {
                    addValidationError(getAppSession()
                            .getMessage(
                                    "water.invalidConnection"));
                }
            }

			TbCsmrInfoDTO connectionDetailsByConnNo = newWaterConnectionService.fetchConnectionDetailsByConnNo(getEntity().getCsCcn(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			// #131368 No need to generate the bills if connection status find as disconnected For all Project.
			if (connectionDetailsByConnNo != null && connectionDetailsByConnNo.getCsCcnstatus() != null) {
					LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
							connectionDetailsByConnNo.getCsCcnstatus(), UserSession.getCurrent().getOrganisation());
					if (lookUp != null && lookUp.getLookUpCode().equals(MainetConstants.FlagD))
						addValidationError(getAppSession().getMessage("water.dissconnection.validation"));
				}
			if(connectionDetailsByConnNo != null && connectionDetailsByConnNo.getCsMeteredccn() != null) {
				String meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(connectionDetailsByConnNo.getCsMeteredccn(),
						UserSession.getCurrent().getOrganisation()).getLookUpCode();
				if(StringUtils.isNotBlank(meterType) && StringUtils.equals(meterType, MainetConstants.NewWaterServiceConstants.METER)) {
					List<Long> csIdnList = new ArrayList<Long>();
					csIdnList.add(connectionDetailsByConnNo.getCsIdn());
					List<TbMrdataEntity> meterReadingDetailsList = ApplicationContextProvider.getApplicationContext().getBean(TbMrdataJpaRepository.class)
							.meterReadingDataByCsidnAndOrgId(csIdnList,
									UserSession.getCurrent().getOrganisation().getOrgid());
					if(CollectionUtils.isNotEmpty(meterReadingDetailsList)) {
						TbMrdataEntity meterReadingData = meterReadingDetailsList.get(0);
						if(Utility.compareDate(meterReadingData.getMrdTo(), new Date()) && StringUtils.equals(meterReadingData.getBillGen(), MainetConstants.FlagY)) {
							addValidationError(getAppSession()
			                        .getMessage("Please do meter reading against connection number :"+" "+getEntity().getCsCcn()));
						}
					}else {
						addValidationError(getAppSession()
		                        .getMessage("Please do meter reading against connection number :"+" "+getEntity().getCsCcn()));
					}
					
			}
			
			}
        } else {
            validateBean(getEntity(),
                    WaterBillGenerationValidator.class);
        }

        if (hasValidationErrors()) {
            return false;
        }
        getEntity().setOrgId(
                UserSession.getCurrent().getOrganisation()
                        .getOrgid());

        final List<TbCsmrInfoDTO> entityList = newWaterConnectionService
                .getwaterRecordsForBill(getEntity(),
                        getBillCcnType(), getBillFrequency(),
                        UserSession.getCurrent()
                                .getFinYearId());

        setEntityList(entityList);
        TbCsmrInfoDTO dto = null;
        if ((entityList == null) || entityList.isEmpty()) {
            setBillSize(0);
            addValidationError(getAppSession()
                    .getMessage("water.bill.already.generated"));
            return false;
        } else {
            setBillSize(entityList.size());
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.ui.model.AbstractFormModel#saveForm()
     */
    @Override
    public boolean saveForm() {
        if (searchWaterBillRecords()) {
            setErrorListMap(new HashMap<Long, WaterBillGenErrorDTO>(0));
            UserSession userSession = UserSession.getCurrent();
            final Employee empId = userSession.getEmployee();
            final List<Long> csIdn = getEntityList().parallelStream()
                    // .filter(water -> MainetConstants.NewWaterServiceConstants.YES.equals(water.getPcFlg()))
                    .map(TbCsmrInfoDTO::getCsIdn).collect(Collectors.toList());
            // if (csIdn == null || csIdn.isEmpty()) {
            // addValidationError(getAppSession().getMessage("water.bill.atleast.one.record"));
            // return false;
            // }
            final String remark = getBillRemarks();
            final Organisation org = userSession.getOrganisation();
            final int langId = userSession.getLanguageId();
            Long loggedLocId = UserSession.getCurrent().getLoggedLocId();
            // This is calling bill generation method
            /*
             * List<Long> billNumbers = tbWtBillMasService.billCalculationAndGeneration(org, getErrorListMap(), getEntityList(),
             * remark, empId.getEmpId(), langId, csIdn, empId.getEmppiservername());
             */
            // final Organisation orgId = UserSession.getCurrent().getOrganisation();
            tbWtBillMasService.billCalculationAndGeneration(org,
                    getErrorListMap(), getEntityList(), remark, empId.getEmpId(), langId, csIdn,
                    empId.getEmppiservername(),loggedLocId);

            // Account posting
            /*
             * billMasterCommonService.doVoucherPosting(billNumbers, orgId, MainetConstants.DEPT_SHORT_NAME.WATER,
             * empId.getEmpId(), UserSession.getCurrent().getLoggedLocId());
             */
            setBillselectSize(csIdn.size());
            setErrorSize(getErrorListMap().size());
            setSuccessMessage(getAppSession().getMessage("water.billGen.seeLog"));
            return true;
        } else {
            return false;
        }

    }

    public TbCsmrInfoDTO getEntity() {
        return entity;
    }

    public void setEntity(final TbCsmrInfoDTO entity) {
        this.entity = entity;
    }

    public List<TbCsmrInfoDTO> getEntityList() {
        return entityList;
    }

    public void setEntityList(final List<TbCsmrInfoDTO> entityList) {
        this.entityList = entityList;
    }

    public List<TbTaxMas> getTaxMas() {
        return taxMas;
    }

    public void setTaxMas(final List<TbTaxMas> taxMas) {
        this.taxMas = taxMas;
    }

    public String getBillRemarks() {
        return billRemarks;
    }

    public void setBillRemarks(final String billRemarks) {
        this.billRemarks = billRemarks;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(final Date billDate) {
        this.billDate = billDate;
    }

    public String getBillCcnType() {
        return billCcnType;
    }

    public void setBillCcnType(final String billCcnType) {
        this.billCcnType = billCcnType;
    }

    public Long getBillFrequency() {
        return billFrequency;
    }

    public void setBillFrequency(final Long billFrequency) {
        this.billFrequency = billFrequency;
    }

    public Map<Long, WaterBillGenErrorDTO> getErrorListMap() {
        return errorListMap;
    }

    public void setErrorListMap(
            final Map<Long, WaterBillGenErrorDTO> errorListMap) {
        this.errorListMap = errorListMap;
    }

    public int getBillSize() {
        return billSize;
    }

    public void setBillSize(final int billSize) {
        this.billSize = billSize;
    }

    public int getErrorSize() {
        return errorSize;
    }

    public void setErrorSize(final int errorSize) {
        this.errorSize = errorSize;
    }

    public int getBillselectSize() {
        return billselectSize;
    }

    public void setBillselectSize(final int billselectSize) {
        this.billselectSize = billselectSize;
    }

}
