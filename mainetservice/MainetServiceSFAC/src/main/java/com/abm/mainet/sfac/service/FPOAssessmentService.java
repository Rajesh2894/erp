/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.FpoAssessmentMasterDto;
import com.abm.mainet.sfac.ui.model.FPOAssApprovalModel;
import com.abm.mainet.sfac.ui.model.FPOAssessmentEntryModel;

/**
 * @author pooja.maske
 *
 */
public interface FPOAssessmentService {

	/**
	 * @param dto
	 * @param fpoAssessmentEntryModel
	 * @return
	 */
	FpoAssessmentMasterDto saveDetails(FpoAssessmentMasterDto dto, FPOAssessmentEntryModel fpoAssessmentEntryModel);



	/**
	 * @param valueOf
	 * @return
	 */
	FpoAssessmentMasterDto fetchAssessmentDetByAppId(Long applicationId);



	/**
	 * @param dto
	 * @param fpoAssApprovalModel
	 */
	void updateApprovalStatusAndRemark(FpoAssessmentMasterDto dto, FPOAssApprovalModel fpoAssApprovalModel);



	/**
	 * @param fpoId
	 * @param assStatus
	 * @return
	 */
	List<FpoAssessmentMasterDto> findByFpoIdAndAssStatus(Long fpoId, String assStatus);



	/**
	 * @return
	 */
	List<FpoAssessmentMasterDto> findAll();



	/**
	 * @param valueOf
	 * @return
	 */
	FpoAssessmentMasterDto fetchAssessmentDetByAssId(Long assId);

}
