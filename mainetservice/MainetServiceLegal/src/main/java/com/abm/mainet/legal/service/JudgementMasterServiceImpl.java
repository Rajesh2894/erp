package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.domain.CaseEntry;
import com.abm.mainet.legal.domain.CourtMaster;
import com.abm.mainet.legal.domain.JudgeDetailMaster;
import com.abm.mainet.legal.domain.JudgementMaster;
import com.abm.mainet.legal.domain.JudgementMasterHistory;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.JudgementMasterDto;
import com.abm.mainet.legal.repository.CourtMasterRepository;
import com.abm.mainet.legal.repository.JudgementMasterRepository;

@Service
public class JudgementMasterServiceImpl implements IJudgementMasterService {

    @Autowired
    JudgementMasterRepository judgementMasterRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    private CourtMasterRepository courtMasterRepository;

    @Autowired
    private AttachDocsRepository attachDocsRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private  ICaseEntryService caseEntryService;
    
    @Autowired
   	TbOrganisationService  tbOrganisationService;

    private static final Logger LOGGER = Logger.getLogger(JudgementMasterServiceImpl.class);

    @Override
    public List<JudgementMasterDto> fetchJudgementDataByIds(Long cseId, Long crtId, Long orgId) {
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        CaseEntry caseObj = new CaseEntry();
        CaseEntryDTO caseEntryById = caseEntryService.getCaseEntryById(cseId);
        caseObj.setCseId(cseId);
        List<JudgementMaster> judementMaster = new ArrayList<>();
        if (orgId.equals(org.getOrgid())) {     
        	judementMaster = judgementMasterRepository
         .findByCaseEntryAndCrtIdAndOrgIdAndJudgementStatus(caseObj, crtId, caseEntryById.getConcernedUlb(),
                MainetConstants.Common_Constant.ACTIVE_FLAG);
        
        }else {
        	judementMaster = judgementMasterRepository.findByCaseEntryAndCrtIdAndOrgIdAndJudgementStatus(caseObj,
					crtId, orgId, MainetConstants.Common_Constant.ACTIVE_FLAG);

        }
        List<JudgementMasterDto> list = StreamSupport
                .stream(judementMaster
                        .spliterator(), false)
                .map(entity -> {
                    JudgementMasterDto dto = new JudgementMasterDto();
                    // make data for displaying data on UI page
                    CaseEntry caseEntry = entity.getCaseEntry();
                    String cseDeptName = departmentService.fetchDepartmentDescById(caseEntry.getCseDeptid());
                    dto.setCseDeptName(cseDeptName);
                    CourtMaster courtMaster = courtMasterRepository.findOne(caseEntry.getCrtId());
                    dto.setCseCourtDesc(courtMaster.getCrtName());
                    dto.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(caseEntry.getCseDate()));
                    // benchName ask to madam
                    // bench name iterate
                    String benchName = "";
                    // get data from TB_LGL_JUDGE_DET
                    List<JudgeDetailMaster> judgeDetailsList = ApplicationContextProvider.getApplicationContext()
                            .getBean(IJudgeMasterService.class).fetchJudgeDetailsByCrtId(crtId,cseId,orgId);
                    for (JudgeDetailMaster judgeData : judgeDetailsList) {
                        if (!StringUtils.isEmpty(judgeData.getJudge().getJudgeBenchName())) {
                            benchName += judgeData.getJudge().getJudgeBenchName() + ",";
                        }
                    }
                    if (benchName != "") {
                        dto.setCseBenchName(benchName.substring(0, benchName.lastIndexOf(",")));
                    }
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
       
        return list;
    }

    @Transactional
    public void saveJudgementData(List<JudgementMasterDto> judgementMasterList, List<DocumentDetailsVO> attachments) {
        List<JudgementMaster> entityList = new ArrayList<>();
        List<Object> historyList = new ArrayList<Object>();
        try {
            if (!CollectionUtils.isEmpty(judgementMasterList)) {
                judgementMasterList.forEach(dto -> {
                    JudgementMaster entity = new JudgementMaster();
                    BeanUtils.copyProperties(dto, entity);
                    CaseEntry caseEntry = new CaseEntry();
                    caseEntry.setCseId(dto.getCseId());
                    entity.setCaseEntry(caseEntry);
                    entity.setJudCaseDate(dto.getCseDate());
                    entity.setJudBenchName(dto.getCseBenchName());
                    entityList.add(entity);
                });

                judgementMasterRepository.save(entityList);
                int count = 0;
                for (JudgementMaster jmEntity : entityList) {
                    List<DocumentDetailsVO> attach = new ArrayList<>();
                    attach.add(attachments.get(count));
                    RequestDTO requestDTO = new RequestDTO();
                    requestDTO.setOrgId(jmEntity.getOrgId());
                    requestDTO.setDepartmentName(MainetConstants.Legal.SHORT_CODE);
                    requestDTO.setStatus(MainetConstants.FlagA);
                    requestDTO.setIdfId("JDM" + MainetConstants.WINDOWS_SLASH + jmEntity.getJudId());
                    requestDTO.setUserId(jmEntity.getCreatedBy());
                    saveDocuments(attach, requestDTO);
                    count++;
                    // history table insert
                    JudgementMasterHistory history = new JudgementMasterHistory();
                    history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                    BeanUtils.copyProperties(jmEntity, history);
                    history.setCseId(jmEntity.getCaseEntry().getCseId());
                    historyList.add(history);
                }
                auditService.createHistoryForListObj(historyList);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception ocours to saveJudgementData()" + "  " + exception);
            throw new FrameworkException("Exception occours WHEN saveJudgementData method" + exception);
        }
    }

    public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
                requestDTO);
    }

    @Transactional
    public void updateJudgementData(List<JudgementMasterDto> judgementMasterList, List<DocumentDetailsVO> attachments,
            List<AttachDocs> attachDocs, List<Long> removeIds) {
        List<JudgementMaster> entityList = new ArrayList<>();
        List<Object> historyList = new ArrayList<Object>();
        try {
            if (!CollectionUtils.isEmpty(judgementMasterList)) {
                judgementMasterList.forEach(dto -> {
                    JudgementMaster entity = new JudgementMaster();
                    BeanUtils.copyProperties(dto, entity);
                    CaseEntry caseEntry = new CaseEntry();
                    caseEntry.setCseId(dto.getCseId());
                    entity.setCaseEntry(caseEntry);
                    entity.setJudCaseDate(dto.getCseDate());
                    entity.setJudBenchName(dto.getCseBenchName());
                    entityList.add(entity);
                });
                judgementMasterRepository.save(entityList);
                if (!removeIds.isEmpty()) {
                    // update with DEACTIVE status on removeIds
                    judgementMasterRepository.deActiveJudgementMaster(removeIds,
                            UserSession.getCurrent().getEmployee().getEmpId());
                }

                int count = 0;
                for (JudgementMaster jmEntity : entityList) {
                    List<DocumentDetailsVO> attach = new ArrayList<>();
                    attach.add(attachments.get(count));
                    RequestDTO requestDTO = new RequestDTO();
                    requestDTO.setOrgId(jmEntity.getOrgId());
                    requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.WATER);
                    requestDTO.setStatus(MainetConstants.FlagA);
                    requestDTO.setIdfId("JDM" + MainetConstants.WINDOWS_SLASH + jmEntity.getJudId());
                    requestDTO.setUserId(jmEntity.getCreatedBy());
                    saveDocuments(attach, requestDTO);
                    count++;
                    // history table update
                    JudgementMasterHistory history = new JudgementMasterHistory();
                    history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                    BeanUtils.copyProperties(jmEntity, history);
                    history.setCseId(jmEntity.getCaseEntry().getCseId());
                    historyList.add(history);
                }
                auditService.createHistoryForListObj(historyList);
            }
            updateLegacyDocuments(attachDocs, UserSession.getCurrent().getEmployee().getEmpId());

        } catch (Exception exception) {
            LOGGER.error("Exception ocours to updateJudgementData()" + "  " + exception);
            throw new FrameworkException("Exception occours when updateJudgementData method" + exception);
        }
    }

    public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
        if (!attachDocs.isEmpty()) {
            for (AttachDocs savedDoc : attachDocs) {
                attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
            }
        }
    }

    //added by rahul.chaubey
    //Returns the date from judgementMaster to the judgementImplementation page
	@Override
	public Date getJudgementMasterdate(Long orgId, Long cseId) {
		Object dateObject = judgementMasterRepository.getJudgementMasterdate(orgId, cseId);
		Date judDate = Utility.converObjectToDate(dateObject);
		
		return judDate;
	}

	@Override
	public Date getJudgementMasterdateById(Long cseId) {
		Object dateObject = judgementMasterRepository.getJudgementMasterdateById(cseId);
		Date judDate = Utility.converObjectToDate(dateObject);
		
		return judDate;
	}
	
}
