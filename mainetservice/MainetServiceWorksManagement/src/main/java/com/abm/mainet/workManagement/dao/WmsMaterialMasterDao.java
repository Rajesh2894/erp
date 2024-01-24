package com.abm.mainet.workManagement.dao;

import java.util.List;

import com.abm.mainet.workManagement.domain.TbWmsError;
import com.abm.mainet.workManagement.domain.TbWmsMaterialMaster;

public interface WmsMaterialMasterDao {

	void saveUpdateMaterialList(TbWmsMaterialMaster entity);

	List<TbWmsMaterialMaster> getMaterialListBySorId(Long sorId);

	void updateDeletedFlag(List<Long> deletedMaterialId);

	void deleteMaterialById(Long sorId);

	List<TbWmsMaterialMaster> findAllActiveMaterialBySorMas(long orgId);

	String checkDuplicateExcelData(Long maTypeId, String maItemNo,Long sorId, long orgid);

	void saveErrorDetails(TbWmsError entity);

	void deleteErrorLog(Long orgId, String masterType);

	List<TbWmsError> getErrorLog(Long orgId,String masterType);

}
