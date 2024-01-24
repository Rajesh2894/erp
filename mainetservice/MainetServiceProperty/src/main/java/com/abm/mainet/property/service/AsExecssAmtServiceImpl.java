package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.AsExcessAmtEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.repository.AsExecssAmtRepository;
import com.google.common.util.concurrent.AtomicDouble;

@Service
public class AsExecssAmtServiceImpl implements AsExecssAmtService {

    @Autowired
    private AsExecssAmtRepository asExecssAmtRepository;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Override
    @Transactional
    public void saveAndUpdateAsExecssAmt(AsExcessAmtEntity asExcessAmtEntity, Long orgId, Long empId) {
        final String ipAddress = Utility.getMacAddress();
        asExcessAmtEntity.setOrgid(orgId);
        if (asExcessAmtEntity.getExcessId() == 0L) {
            asExcessAmtEntity.setCreatedBy(empId);
            asExcessAmtEntity.setLgIpMac(ipAddress);
            asExcessAmtEntity.setCreatedDate(new Date());
        } else {
            asExcessAmtEntity.setUpdatedBy(empId);
            asExcessAmtEntity.setLgIpMacUpd(ipAddress);
            asExcessAmtEntity.setUpdatedDate(new Date());
        }
        asExecssAmtRepository.save(asExcessAmtEntity);

    }

    @Override
    @Transactional(readOnly = true)
    public List<AsExcessAmtEntity> getExcessAmtEntByPropNo(String propNo, Long orgId) {
        return asExecssAmtRepository.getExcessAmtEntByPropNo(propNo, orgId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AsExcessAmtEntity> getExcessAmtEntByPropNoAndFlatNo(String propNo, Long orgId,String flatNo) {
        return asExecssAmtRepository.getExcessAmtEntByPropNoAndFlatNo(propNo, orgId,flatNo);
    }

    @Override
    @Transactional
    public void addEntryInExcessAmt(Long orgId, String propNo, double excAmt, Long rmRcptid, Long userId) {
        AsExcessAmtEntity excAmtEnt = new AsExcessAmtEntity();
        final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId,
                MainetConstants.Property.PROP_DEPT_SHORT_CODE, null);
        excAmtEnt.setExcAmt(excAmt);
        excAmtEnt.setPropNo(propNo);
        excAmtEnt.setRmRcptid(rmRcptid);
        excAmtEnt.setExcadjFlag(MainetConstants.MENU.N);
        excAmtEnt.setExcessActive(MainetConstants.STATUS.ACTIVE);
        excAmtEnt.setTaxId(advanceTaxId);
        saveAndUpdateAsExecssAmt(excAmtEnt, orgId, userId);

    }

    @Override
    @Transactional(readOnly = true)
    public double getAdvanceAmount(String propNo, Long orgId) {
        List<AsExcessAmtEntity> excAmtList = getExcessAmtEntByPropNo(propNo, orgId);
        double excamt = 0;
        if (excAmtList != null && !excAmtList.isEmpty()) {
            for (AsExcessAmtEntity exAmt : excAmtList) {
                excamt += (exAmt.getExcAmt() - exAmt.getAdjAmt());
            }
        }
        return excamt;
    }

    @Override
    @Transactional
    public void inactiveAllAdvPayEnrtyByPropNo(String propNo, Long orgId) {
        asExecssAmtRepository.inactiveAllAdvPayEnrtyByPropNo(propNo, orgId);
    }

    @Override
    @Transactional
    public void inactiveAdvPayEnrtyByExcessId(Long excessId, Long orgId) {
        asExecssAmtRepository.inactiveAdvPayEnrtyByExcessId(excessId, orgId);
    }

    @Override
    @Transactional
    public AsExcessAmtEntity getAdvanceEntryByRecptId(Long rmRcptid, String propNo, Long orgId) {
        return asExecssAmtRepository.getAdvanceEntryByRecptId(rmRcptid, propNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public BillDisplayDto getBillDisplayDtoWithAdvanceAmt(String propNo, Long orgId,String flatNo) {
        BillDisplayDto advanceAmtDto = null;
        List<AsExcessAmtEntity> excAmtList = null;
		if (StringUtils.isNotBlank(flatNo)) {
			excAmtList = getExcessAmtEntByPropNoAndFlatNo(propNo, orgId, flatNo);
		} else {
			excAmtList = getExcessAmtEntByPropNo(propNo, orgId);
		}
        AtomicDouble totAmt = new AtomicDouble(0);
        if (excAmtList != null && !excAmtList.isEmpty()) {
            AsExcessAmtEntity advEnt = excAmtList.get(0);
            for (AsExcessAmtEntity exAmt : excAmtList) {
                totAmt.addAndGet(exAmt.getExcAmt() - exAmt.getAdjAmt());
            }
            if (totAmt.doubleValue() > 0) {
                advanceAmtDto = new BillDisplayDto();
                TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(advEnt.getTaxId(), orgId);
                advanceAmtDto.setCurrentYearTaxAmt(BigDecimal.valueOf(totAmt.doubleValue()));
                advanceAmtDto.setTotalTaxAmt(BigDecimal.valueOf(totAmt.doubleValue()));
                advanceAmtDto.setTaxId(taxMas.getTaxId());
                advanceAmtDto.setTaxCategoryId(taxMas.getTaxCategory1());
                advanceAmtDto.setDisplaySeq(taxMas.getTaxDisplaySeq());
                advanceAmtDto.setTaxDesc(taxMas.getTaxDesc());
            }
        }
        return advanceAmtDto;
    }

    @Transactional
    @Override
    public void updateExecssAmtByAdjustedAmt(String propNo, Long orgId, double ajustedAmt, Long userId, String ipAddress, String flatNo) {
        List<AsExcessAmtEntity> excAmtList = null;
        if(ajustedAmt > 0) {
        	if (StringUtils.isNotBlank(flatNo)) {
    			excAmtList = getExcessAmtEntByPropNoAndFlatNo(propNo, orgId, flatNo);
    		} else {
    			excAmtList = getExcessAmtEntByPropNo(propNo, orgId);
    		}
            double totAmt = 0;
            if (excAmtList != null && !excAmtList.isEmpty()) {
                for (AsExcessAmtEntity exAmt : excAmtList) {
                    totAmt = exAmt.getExcAmt() - exAmt.getAdjAmt();
                    if(StringUtils.isNotBlank(exAmt.getExtraCol2()) && StringUtils.equals(MainetConstants.FlagY, exAmt.getExtraCol2())) {
                    	exAmt.setExtraCol2(MainetConstants.FlagN);
                    }
                    if (totAmt <= ajustedAmt) {
                    	double adjustedAmnt = exAmt.getExcAmt() - exAmt.getAdjAmt();
                        exAmt.setExcadjFlag(MainetConstants.FlagY);
                        exAmt.setAdjAmt(exAmt.getExcAmt());
                        exAmt.setUpdatedBy(userId);
                        exAmt.setLgIpMacUpd(ipAddress);
                        exAmt.setUpdatedDate(new Date());
                        ajustedAmt -= adjustedAmnt;
                    } else {
                        exAmt.setAdjAmt(exAmt.getAdjAmt() + ajustedAmt);
                        exAmt.setUpdatedBy(userId);
                        exAmt.setLgIpMacUpd(ipAddress);
                        exAmt.setUpdatedDate(new Date());
                        break;
                    }
                }
            }
            asExecssAmtRepository.save(excAmtList);
        }
		

    }

	@Override
	public List<AsExcessAmtEntity> getExcessAmtEntByActivePropNo(String propNo, Long orgId) {
		return asExecssAmtRepository.getExcessAmtEntByActivePropNo(propNo, orgId);
	}

	@Override
	public double getAdvanceAmountByFlatNo(String propNo, String flatNo, Long orgId) {
		 List<AsExcessAmtEntity> excAmtList = getExcessAmtEntByPropNoAndFlatNo(propNo, orgId,flatNo);
	        double excamt = 0;
	        if (excAmtList != null && !excAmtList.isEmpty()) {
	            for (AsExcessAmtEntity exAmt : excAmtList) {
	                excamt += (exAmt.getExcAmt() - exAmt.getAdjAmt());
	            }
	        }
	        return excamt;
	}
}
