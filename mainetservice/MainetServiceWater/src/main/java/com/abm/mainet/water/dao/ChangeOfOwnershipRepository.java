package com.abm.mainet.water.dao;

import java.util.List;

import com.abm.mainet.water.domain.AdditionalOwnerInfo;
import com.abm.mainet.water.domain.ChangeOfOwnerMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;

/**
 * @author Rahul.Yadav
 *
 */
public interface ChangeOfOwnershipRepository {

    /**
     * @param orgnId
     * @param empId
     * @return
     */
    List<TbKCsmrInfoMH> getConnectionData(long orgnId, Long empId);

    /**
     * 
     * @param connectionNo
     * @param orgnId
     * @return
     */
    TbKCsmrInfoMH getConnectionData(String connectionNo, long orgnId);

    /**
     * @param master
     */
    void saveNewData(ChangeOfOwnerMas master);

    long findCsidnFromChangeOfOwner(long applicationId);

    TbKCsmrInfoMH findOldOwnerConnectionInfoByCsidn(long csidn, long orgId);

    ChangeOfOwnerMas fetchWaterConnectionOwnerDetail(long applicationId);

    void updateChangeOfOwnershipMas(ChangeOfOwnerMas mas);

    /**
     * used to save additional owners for change of ownership
     * @param additionalOwnerInfo
     */
    void saveAdditionalOwners(List<AdditionalOwnerInfo> additionalOwnerList);

    List<AdditionalOwnerInfo> fetchAdditionalOwners(long applicationId);

    /**
     * this method is used to check whether entered connection no is valid for change of ownership application or not,
     * @param connectionNo
     * @return
     */
    String enquiryConnectionNo(String connectionNo);

}
