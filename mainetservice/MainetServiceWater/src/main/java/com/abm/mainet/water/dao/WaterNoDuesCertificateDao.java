package com.abm.mainet.water.dao;

import java.util.List;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.WaterNoDuesEntity;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface WaterNoDuesCertificateDao {

    /**
     * this method are used for get ApplicantInformationById using application ID and OrgId
     * @param applicationId
     * @param orgId
     * @return WaterNoDuesEntity
     */
    WaterNoDuesEntity getApplicantInformationById(Long applicationId, Long orgId);

    /**
     * this method are used for get WaterConnByConsNo using NoDuesCertificateReqDTO
     * @param requestDTO
     * @return TbKCsmrInfoMH
     */
    TbKCsmrInfoMH getWaterConnByConsNo(NoDuesCertificateReqDTO requestDTO);

    /**
     * this method are used for get WaterDues using csIdn ID and OrgId
     * @param csIdn
     * @param orgId
     * @return WaterBillMasterEntity
     */
    TbWtBillMasEntity getWaterDues(long csIdn, Long orgId);

    /**
     * this method are used for save Form Data using WaterNoDuesEntity
     * @param entity
     * @return WaterNoDuesEntity
     */
    WaterNoDuesEntity saveFormData(WaterNoDuesEntity entity);

    /**
     * this method are used for update Form Data using WaterNoDuesEntity
     * @param entity
     * @return Boolean
     */
    Boolean update(WaterNoDuesEntity entity);
    
    
    /**
     * @param requestDTO
     * @return List<TbKCsmrInfoMH>
     */
    List<TbKCsmrInfoMH> getWaterConnByPropNoNFlatNo(String propNo, String flatNo , Long orgId);

}
