package com.abm.mainet.cfc.loi.dao;

import java.util.List;

import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;

/**
 * @author Rahul.Yadav
 *
 */
public interface ILoiMasterPaymentDAO {

    /**
     * @param searchDto
     * @param status 
     * @return
     */
    TbLoiMasEntity findLoiMasBySearchDTO(LoiPaymentSearchDTO searchDto, String status);

    /**
     * @param loiId
     * @param orgid
     * @return
     */
    void updateLoiMaster(Long loiId, Long orgid);

    /**
     * @param loiNo
     * @param orgid
     */
    void updateLoiPaidByLoiNo(String loiNo, long orgid);

    void updateLoiMasterStatus(String loiNo, long orgId, Long empId, String deleteRemark);

	List<Object[]> findLoiInformation(Long orgId, String mobileNo, Long empId);

	List<TbLoiDetEntity> findLoiDetailsByLoiMasAndOrgId(Long loiId, Long orgId);

	List<Object[]> getLoiMasData(Long orgid,Long deptId, Long serviceId, Long appId, String loiNo,String apmFname, String apmLname);

	public void updateIssuanceDataInLoiMas(Long applicationIds, Long issuedBy, Long orgId);
	

}
