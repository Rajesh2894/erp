/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.sfac.dto.AssessmentMasterDto;
import com.abm.mainet.sfac.ui.model.AssessmentApprovalModel;
import com.abm.mainet.sfac.ui.model.CBBOAssessmentEntryModel;

/**
 * @author pooja.maske
 *
 */
public interface CBBOAssesementEntryService {

	/**
	 * @param dtoList
	 * @param cbboAssesementEntryModel
	 */
	public AssessmentMasterDto  saveDetails(AssessmentMasterDto masDto,CBBOAssessmentEntryModel cbboAssesementEntryModel);

	/**
	 * @param dto
	 * @param cbboAssesementEntryModel
	 * @return
	 */
/*	public AssessmentMasterDto saveAssMastDetail(AssessmentMasterDto dto,
			CBBOAssessmentEntryModel cbboAssesementEntryModel);*/

	/**
	 * @param assId
	 * @param cbboId
	 * @param flagp
	 */
	public void updateAssementStatus(Long assId,String flag);

	/**
	 * @param assessmentNo
	 * @return
	 */
	public String getCbboAssNo(String assessmentNo,Long orgId);

	/**
	 * @return
	 */
	public List<AssessmentMasterDto> findAll();

	/**
	 * @param valueOf
	 * @return
	 */
	public AssessmentMasterDto fetchAssessmentDetByAppId(Long valueOf);

	/**
	 * @param dto
	 * @param assessmentApprovalModel
	 */
	public void updateApprovalStatusAndRemark(AssessmentMasterDto dto, AssessmentApprovalModel assessmentApprovalModel);

	/**
	 * @param valueOf
	 * @return
	 */
	public AssessmentMasterDto fetchAssessmentDetByAssId(Long valueOf);

	/**
	 * @param cbboId
	 * @param assStatus
	 * @param assDate
	 * @return
	 */
	public List<AssessmentMasterDto> findByIds(Long cbboId, String assStatus, Date assDate);

}
