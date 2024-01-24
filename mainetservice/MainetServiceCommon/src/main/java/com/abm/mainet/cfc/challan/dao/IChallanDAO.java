package com.abm.mainet.cfc.challan.dao;

import java.util.List;

import com.abm.mainet.cfc.challan.domain.ChallanDetails;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;

public interface IChallanDAO {

    public abstract ChallanMaster getChallanMasters(Long challanNo, Long applNo);

    public abstract boolean getChallanMasterTransId(String bankTransId);

    public abstract ChallanMaster getChallanMasterById(Long challanid);

    public abstract ChallanMaster updateChallanDetails(ChallanMaster challanMaster);

    public abstract ChallanMaster InvokeGenerateChallan(ChallanMaster challanMaster);

    /**
     * @param challanDetails
     */
    public abstract boolean saveChallanDetails(List<ChallanDetails> challanDetails);

    /**
     * @param challanNo
     * @param orgid
     * @return
     */
    public abstract List<ChallanDetails> getChallanDetails(String challanNo,
            long orgid);

    /**
     * @param applicationId
     * @param orgid
     * @return
     */
    public abstract ChallanMaster getchallanOfflineType(long applicationId, long orgid);

    /**
     * @param serviceId
     * @param organisation
     * @return
     */
    int getdurationDays(Long serviceId, Long organisation);

    public abstract List<String> getBankDetailsList(Long bankAccID, long organisation);

    /**
     * @param ids
     * @param orgId
     * @return
     */
    public abstract List<TbTaxMasEntity> getChargeDescByChgId(
            List<Long> ids, long orgId);

}