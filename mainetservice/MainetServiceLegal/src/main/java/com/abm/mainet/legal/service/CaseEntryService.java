package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.dao.ICaseEntryDAO;
import com.abm.mainet.legal.domain.CaseEntry;
import com.abm.mainet.legal.domain.CaseEntryArbitoryFee;
import com.abm.mainet.legal.domain.CaseEntryDetail;
import com.abm.mainet.legal.domain.CaseEntryDetailHistory;
import com.abm.mainet.legal.domain.CaseEntryHistory;
import com.abm.mainet.legal.domain.CaseHearing;
import com.abm.mainet.legal.domain.JudgeMaster;
import com.abm.mainet.legal.domain.OfficerInchargeDetails;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryArbitoryFeeDto;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseEntryDetailDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.dto.OfficerInchargeDetailsDTO;
import com.abm.mainet.legal.repository.CaseEntryRepository;
import com.abm.mainet.legal.repository.CaseHearingRepository;
import com.abm.mainet.legal.repository.JudgeMasterRepository;


@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.ICaseEntryService")
@Path(value = "/caseentryservice")
public class CaseEntryService implements ICaseEntryService {

    @Autowired
    private CaseEntryRepository caseEntryRepository;

    @Autowired
    private AuditService auditService;
    
    @Autowired
    private  IAdvocateMasterService advocateMasterService;
   
    @Autowired
    private ICaseEntryDAO caseEntryDAO;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    IAttachDocsDao iAttachDocsDao;

    @Autowired
    JudgeMasterRepository judgeMasterRepository;

    @Autowired
    CaseHearingRepository caseHearingRepository;
    
    @Autowired
    ICourtMasterService courtMasterService;
    
    @Autowired
	private IOrganisationService organisatonService;

    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public CaseEntryDTO saveCaseEntry(@RequestBody CaseEntryDTO caseEntryDto) {
        CaseEntry caseEntry = new CaseEntry();
        BeanUtils.copyProperties(caseEntryDto, caseEntry);
        List<CaseEntryDetail> details = new ArrayList<>();
        CaseEntryDetail detail = null;
        for (CaseEntryDetailDTO det : caseEntryDto.getTbLglCasePddetails()) {
            detail = new CaseEntryDetail();
            BeanUtils.copyProperties(det, detail);
            detail.setTbLglCaseMa(caseEntry);
            details.add(detail);
        }
        /**
         * Set all Officer Incharge details
         */
        List<OfficerInchargeDetails> Oicdetails = new ArrayList<>();
        if (caseEntryDto.getTbLglCaseOICdetails() != null) {
            caseEntryDto.getTbLglCaseOICdetails().forEach(det -> {
                OfficerInchargeDetails Oicdetail = new OfficerInchargeDetails();
                BeanUtils.copyProperties(det, Oicdetail);
                Oicdetail.setTbLglCaseMa(caseEntry);
                Oicdetails.add(Oicdetail);
            });
        }

        Set<CaseEntryArbitoryFee> arbitoryfee = new HashSet<>();
        caseEntryDto.getTbLglArbitoryFee().forEach(det -> {
            CaseEntryArbitoryFee feeDto = new CaseEntryArbitoryFee();
            BeanUtils.copyProperties(det, feeDto);
            arbitoryfee.add(feeDto);
        });
        caseEntry.setTbLglCasePddetails(details);
        caseEntry.setTbLglCaseOICdetails(Oicdetails);
        caseEntry.setTbArbitoryFee(arbitoryfee);
        for (CaseEntryArbitoryFee det : caseEntry.getTbArbitoryFee()) {
            det.setTbCaseEntry(caseEntry);
        }
        String caseNo = getNewGeneratedCaseNo();
        caseEntry.setCaseNo(caseNo);
        caseEntryDto.setCaseNo(caseNo);
        caseEntryRepository.save(caseEntry);

        CaseEntryHistory history = new CaseEntryHistory();
        history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(caseEntry, history);
        CaseEntryDetailHistory detHist = null;
        for (CaseEntryDetail det : caseEntry.getTbLglCasePddetails()) {
            detHist = new CaseEntryDetailHistory();
            detHist.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            detHist.setCseId(caseEntry.getCseId());
            BeanUtils.copyProperties(det, detHist);
            auditService.createHistory(det, detHist);
        }
        caseEntryDto.setCseId(caseEntry.getCseId());
        return caseEntryDto;
    }

    @Override
    @Transactional
    public void deleteContractDocFileById(List<Long> enclosureRemoveById, Long empId) {
        iAttachDocsDao.updateRecord(enclosureRemoveById, empId, MainetConstants.Common_Constant.DELETE_FLAG);
    }

    @Override
    @Transactional
    @POST
    @Path(value = "/update")
    public void updateCaseEntry(@RequestBody CaseEntryDTO caseEntryDto) {
        CaseEntry caseEntry = new CaseEntry();
        BeanUtils.copyProperties(caseEntryDto, caseEntry);
        List<CaseEntryDetail> details = new ArrayList<>();
        CaseEntryDetail detail = null;

        List<OfficerInchargeDetails> Oicdetails = new ArrayList<>();

        if (caseEntryDto.getTbLglCaseOICdetails() != null) {
            caseEntryDto.getTbLglCaseOICdetails().forEach(det -> {
                OfficerInchargeDetails Oicdetail = new OfficerInchargeDetails();
                BeanUtils.copyProperties(det, Oicdetail);
                Oicdetail.setTbLglCaseMa(caseEntry);
                Oicdetails.add(Oicdetail);
            });
        }

        caseEntry.setTbLglCaseOICdetails(Oicdetails);

        for (CaseEntryDetailDTO det : caseEntryDto.getTbLglCasePddetails()) {
            detail = new CaseEntryDetail();
            BeanUtils.copyProperties(det, detail);
            detail.setTbLglCaseMa(caseEntry);
            details.add(detail);
        }
        caseEntry.setTbLglCasePddetails(details);

        Set<CaseEntryArbitoryFee> arbitoryFeeList = new HashSet<>();
        caseEntryDto.getTbLglArbitoryFee().forEach(det -> {
            CaseEntryArbitoryFee arbitoryfee = new CaseEntryArbitoryFee();
            BeanUtils.copyProperties(det, arbitoryfee);
            arbitoryFeeList.add(arbitoryfee);
        });
        caseEntry.setTbArbitoryFee(arbitoryFeeList);

        for (CaseEntryArbitoryFee det : caseEntry.getTbArbitoryFee()) {
            det.setTbCaseEntry(caseEntry);
        }
        caseEntryRepository.save(caseEntry);

        CaseEntryHistory history = new CaseEntryHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(caseEntry, history);

        CaseEntryDetailHistory detHist = null;
        for (CaseEntryDetail det : caseEntry.getTbLglCasePddetails()) {
            detHist = new CaseEntryDetailHistory();
            detHist.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            detHist.setCseId(caseEntry.getCseId());
            BeanUtils.copyProperties(det, detHist);
            auditService.createHistory(det, detHist);
        }

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean deleteCaseEntry(CaseEntryDTO caseEntryDto) {
        CaseEntry CaseEntry = caseEntryRepository.findOne(caseEntryDto.getCseId());
        if (CaseEntry == null)
            return false;

        caseEntryRepository.delete(caseEntryDto.getCseId());

        CaseEntryHistory history = new CaseEntryHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(CaseEntry, history);

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/getAll/{orgId}")
    public List<CaseEntryDTO> getAllCaseEntry(@PathParam("orgId") Long orgId) {
        List<CaseEntryDTO> activeUserListDTOs = StreamSupport
                .stream(caseEntryRepository.findByOrgid(orgId).spliterator(), false).map(entity -> {
                    CaseEntryDTO dto = new CaseEntryDTO();
                    // get department name
                    String cseDeptName = departmentService.fetchDepartmentDescById(entity.getCseDeptid());
                    dto.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(entity.getCseDate()));
                    dto.setCseDeptName(cseDeptName);
                    BeanUtils.copyProperties(entity, dto);
                    String crtName = courtMasterService.getCourtNameById(entity.getCrtId());
                    if (StringUtils.isNotBlank(crtName))
                    dto.setCourtName(crtName);
                    List<CaseEntryDetailDTO> detailList = new ArrayList<>();
                    entity.getTbLglCasePddetails().forEach(det -> {
                        CaseEntryDetailDTO detailsDTO = new CaseEntryDetailDTO();
                        BeanUtils.copyProperties(det, detailsDTO);
                        detailList.add(detailsDTO);
                    });
                    dto.setTbLglCasePddetails(detailList);

                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<CaseEntryDTO> getAllCaseEntrySummary(@PathParam("orgId") Long orgId) {
        List<CaseEntryDTO> activeUserListDTOs = StreamSupport
                .stream(caseEntryRepository.findByOrgid(orgId).spliterator(), false).map(entity -> {
                    CaseEntryDTO dto = new CaseEntryDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }


    @Override
    public List<CaseEntryDTO> getAllCaseEntryBasedOnHearing(Long hrStatus, Long orgId) {
        List<CaseEntryDTO> caseEntries = new ArrayList<>();
        // 1st find out hearing based on hrStatus
        // get distinct case id from TB_LGL_HEARING
        List<Long> caseIds = caseHearingRepository.findCaseIdsByStatus(hrStatus, orgId);
        // iterate case IDS and get data
        for (int i = 0; i < caseIds.size(); i++) {
            CaseEntryDTO dto = new CaseEntryDTO();
            CaseEntry caseEntry = caseEntryRepository.findOne(caseIds.get(i));
            BeanUtils.copyProperties(caseEntry, dto);
            // get department name
            String cseDeptName = departmentService.fetchDepartmentDescById(dto.getCseDeptid());
            dto.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(dto.getCseDate()));
            dto.setCseDeptName(cseDeptName);
            caseEntries.add(dto);
        }
        return caseEntries;
    }

    @Override
    public List<CaseEntryDTO> getAllCaseEntryBasedOnHearingStatus(Long hrStatus) {
        List<CaseEntryDTO> caseEntries = new ArrayList<>();
        // 1st find out hearing based on hrStatus
        // get distinct case id from TB_LGL_HEARING
        List<Long> caseIds = caseHearingRepository.findCaseIdByStatus(hrStatus);
        // iterate case IDS and get data
        for (int i = 0; i < caseIds.size(); i++) {
            CaseEntryDTO dto = new CaseEntryDTO();
            CaseEntry caseEntry = caseEntryRepository.findOne(caseIds.get(i));
            BeanUtils.copyProperties(caseEntry, dto);
            // get department name
            String cseDeptName = departmentService.fetchDepartmentDescById(dto.getCseDeptid());
            dto.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(dto.getCseDate()));
            dto.setCseDeptName(cseDeptName);
            caseEntries.add(dto);
        }
        return caseEntries;
    }
    
    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{id}")
    public CaseEntryDTO getCaseEntryById(@PathParam("id") Long id) {
        CaseEntry caseEntry = caseEntryRepository.findOne(id);
        CaseEntryDTO caseEntryDto = new CaseEntryDTO();
        BeanUtils.copyProperties(caseEntry, caseEntryDto);

        List<CaseEntryDetailDTO> detailList = new ArrayList<>();
        caseEntry.getTbLglCasePddetails().forEach(det -> {
            CaseEntryDetailDTO detailsDTO = new CaseEntryDetailDTO();
            BeanUtils.copyProperties(det, detailsDTO);
            detailList.add(detailsDTO);
        });
        List<CaseEntryArbitoryFeeDto> arbitoryFeeList = new ArrayList<>();
        caseEntry.getTbArbitoryFee().forEach(det -> {
            CaseEntryArbitoryFeeDto arbitoryFeeDto = new CaseEntryArbitoryFeeDto();
            BeanUtils.copyProperties(det, arbitoryFeeDto);
            arbitoryFeeList.add(arbitoryFeeDto);
        });
        caseEntryDto.setTbLglCasePddetails(detailList);
        caseEntryDto.setTbLglArbitoryFee(arbitoryFeeList);

        List<OfficerInchargeDetailsDTO> officerInchargeDetailDTOList = new ArrayList<OfficerInchargeDetailsDTO>();

        caseEntry.getTbLglCaseOICdetails().forEach(det -> {

            OfficerInchargeDetailsDTO officerInchargeDetailsDTO = new OfficerInchargeDetailsDTO();

            BeanUtils.copyProperties(det, officerInchargeDetailsDTO);
            officerInchargeDetailDTOList.add(officerInchargeDetailsDTO);
        });

        caseEntryDto.setTbLglCaseOICdetails(officerInchargeDetailDTOList);
        return caseEntryDto;
    }

    @Override
    public List<CaseEntryDTO> searchCaseEntry(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, Long hrStatus,String flag) {
        List<Long> caseIds = null;
        List<CaseEntryDTO> caseEntryDTOList = new ArrayList<>();
        Organisation org = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
                .findDefaultOrganisation();
        List<CaseEntry> caseEntryList = new ArrayList<>();
        if (orgId.equals(org.getOrgid())) {
            caseEntryList = caseEntryDAO.searchCaseEntryUAD(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId,
                    cseDate, crtId, cseCaseStatusId, null, caseIds,flag);
        } else {
            if (hrStatus != null) { // this case USED is only for JUDGEMENT DETAILS TILL TILME
                caseIds = caseHearingRepository.findCaseIdsByStatus(hrStatus, orgId);
            }
            caseEntryList = caseEntryDAO.searchCaseEntry(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId,
                    cseDate, crtId, cseCaseStatusId, orgId, caseIds);
            // caseEntryList.stream().map(dto -> mapCaseEntryToCaseEntryDTO(dto)).collect(Collectors.toList());
        }
        for (CaseEntry caseEntry : caseEntryList) {
            caseEntryDTOList.add(mapCaseEntryToCaseEntryDTO(caseEntry));
        }
        return caseEntryDTOList;
    }
    
    @Override
    public List<CaseEntryDTO> searchCaseEntrys(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, Long hrStatus,String flag,String caseNo) {
        List<Long> caseIds = null;
        List<CaseEntryDTO> caseEntryDTOList = new ArrayList<>();
        Organisation org = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
                .findDefaultOrganisation();
        List<CaseEntry> caseEntryList = new ArrayList<>();
        if (orgId.equals(org.getOrgid())) {
            caseEntryList = caseEntryDAO.searchCaseEntryUADs(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId,
                    cseDate, crtId, cseCaseStatusId, null, caseIds,flag,caseNo);
        } else {
            if (hrStatus != null) { // this case USED is only for JUDGEMENT DETAILS TILL TILME
                caseIds = caseHearingRepository.findCaseIdsByStatus(hrStatus, orgId);
            }
            caseEntryList = caseEntryDAO.searchCaseEntrys(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId,
                    cseDate, crtId, cseCaseStatusId, orgId, caseIds,caseNo);
            // caseEntryList.stream().map(dto -> mapCaseEntryToCaseEntryDTO(dto)).collect(Collectors.toList());
        }
        for (CaseEntry caseEntry : caseEntryList) {
            caseEntryDTOList.add(mapCaseEntryToCaseEntryDTO(caseEntry));
        }
        return caseEntryDTOList;
    }


    private CaseEntryDTO mapCaseEntryToCaseEntryDTO(CaseEntry caseEntry) {

        CaseEntryDTO caseEntryDTO = new CaseEntryDTO();
        BeanUtils.copyProperties(caseEntry, caseEntryDTO);
       
        caseEntryDTO.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(caseEntry.getCseDate()));
        // set cseTypeName
        String cseTypeName = CommonMasterUtility
                .getNonHierarchicalLookUpObject(caseEntry.getCseTypId(), UserSession.getCurrent().getOrganisation())
                .getLookUpDesc();
        caseEntryDTO.setCseTypeName(cseTypeName);
        // get department name
        String cseDeptName = departmentService.fetchDepartmentDescById(caseEntry.getCseDeptid());
        caseEntryDTO.setCseDeptName(cseDeptName);
        if(caseEntry.getAdvId() != null) {
        	//Defect #39310 -> to get advocateName by Id
        AdvocateMasterDTO advocateName = advocateMasterService.getAdvocateMasterById(caseEntry.getAdvId());
        caseEntryDTO.setAdvName(advocateName.getAdvFirstNm());
        }
        /*
         * caseEntryDTO.setCseTypId1(caseEntry.getCseTypId1()); caseEntryDTO.setCseTypId2(caseEntry.getCseTypId2());
         */

        return caseEntryDTO;

    }
    
    

    @Override
    @Transactional(readOnly = true)
    public List<JudgeMasterDTO> getAllJudgebyCaseId(long caseId, long orgId) {

        List<JudgeMaster> allJudgeBycase = judgeMasterRepository.getAllJudgeBycaseId(caseId, orgId);
        List<JudgeMasterDTO> allJudgeDTO = new ArrayList<>();

        for (JudgeMaster judgeMaster : allJudgeBycase)

        {
            JudgeMasterDTO judgeMasterDTO = new JudgeMasterDTO();

            BeanUtils.copyProperties(judgeMaster, judgeMasterDTO);
            allJudgeDTO.add(judgeMasterDTO);
        }

        return allJudgeDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaseEntryDTO> getCaseEntryByStatus(Long id, Long orgid) {
        List<CaseEntryDTO> caseEntryDTOList = new ArrayList<>();
        List<CaseEntry> caseEntryList = caseEntryRepository.findByCseCaseStatusIdAndOrgid(id, orgid);
        for (CaseEntry caseEntry : caseEntryList) {
            caseEntryDTOList.add(mapCaseEntryToCaseEntryDTO(caseEntry));
        }
        return caseEntryDTOList;
    }
    
    private String getNewGeneratedCaseNo() {
		String caseNo = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();;
		String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());

		if (financiaYear != null) {
			// get financial year from date & end date and generate financial year as like:
			// 2018-19 format for new case code
			String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

			// generate sequence number.
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class)
					.generateSequenceNo(MainetConstants.Legal.CASE_ENTRY_CODE,
							MainetConstants.Legal.TB_LGL_CASE_MAS, MainetConstants.Legal.CASE_NO,
							orgId, MainetConstants.FlagC, financiaYear.getFaYear());
			// generate new case number.
			caseNo =ulbName + MainetConstants.WINDOWS_SLASH +MainetConstants.Legal.SHORT_CODE 
					+ MainetConstants.WINDOWS_SLASH+finacialYear
					+ MainetConstants.WINDOWS_SLASH	+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence);
		}
		return caseNo;

	}

	
	
	
	    @Consumes("application/json")
	    @Transactional(readOnly = true)
	    @Override
	    @POST
	    @Path(value = "/getCaseEntryByCaseNumber")
	    public CaseEntryDTO getCaseEntryByCaseNumber(@RequestBody CaseEntryDTO cseEntryDto) {	  
	    	CaseEntryDTO caseEntryDto = new CaseEntryDTO();
	        CaseEntry caseEntry = caseEntryRepository.getCaseEntryByCaseNumber(cseEntryDto.getCseSuitNo());	        
	        if(caseEntry != null) {	 
	        CourtMasterDTO courtMasterDto = courtMasterService.getCourtMasterById(caseEntry.getCrtId());
            AdvocateMasterDTO advocateMasterdto = advocateMasterService.getAdvocateMasterById(caseEntry.getAdvId());
            String advName = advocateMasterdto.getAdvFirstNm() + " " + advocateMasterdto.getAdvLastNm();     	        
	        BeanUtils.copyProperties(caseEntry, caseEntryDto);	        
	        caseEntryDto.setAdvName(advName);
	        caseEntryDto.setCrtName(courtMasterDto.getCrtName());
	        LookUp cseTypeLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(caseEntry.getCseTypId(),caseEntry.getOrgid(),MainetConstants.Legal.TOC_PREFIX);
	        LookUp cseStatusLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(caseEntry.getCseCaseStatusId(),caseEntry.getOrgid(), MainetConstants.Legal.CSS_PREFIX);
	        LookUp categoryLookup = CommonMasterUtility.getHierarchicalLookUp(caseEntry.getCseCatId1(), caseEntry.getOrgid());
	        caseEntryDto.setCseTypeName(cseTypeLookup.getLookUpDesc());
	        caseEntryDto.setStatus(cseStatusLookup.getLookUpDesc());
	        caseEntryDto.setCseCategoryDesc(categoryLookup.getLookUpDesc());         
	    	                        
	        List<CaseHearing> hearingEntity = caseHearingRepository.findByCseId(caseEntry.getCseId());
	          hearingEntity.forEach(dto -> {
	        	String hearingDate = UtilityService.convertDateToDDMMYYYY(dto.getHrDate());
	        	caseEntryDto.setHearingDate(hearingDate.toString());
	        });	
	               
	        List<CaseEntryDetailDTO> detailList = new ArrayList<>();
	        caseEntry.getTbLglCasePddetails().forEach(det -> {
	            CaseEntryDetailDTO detailsDTO = new CaseEntryDetailDTO();
	            BeanUtils.copyProperties(det, detailsDTO);	           	        
	            detailList.add(detailsDTO);
	        });
	        
	        caseEntryDto.setTbLglCasePddetails(detailList);
	       

	        List<OfficerInchargeDetailsDTO> officerInchargeDetailDTOList = new ArrayList<OfficerInchargeDetailsDTO>();

	        caseEntry.getTbLglCaseOICdetails().forEach(det -> {

	            OfficerInchargeDetailsDTO officerInchargeDetailsDTO = new OfficerInchargeDetailsDTO();

	            BeanUtils.copyProperties(det, officerInchargeDetailsDTO);
	            officerInchargeDetailDTOList.add(officerInchargeDetailsDTO);
	        });
	        caseEntryDto.setTbLglCaseOICdetails(officerInchargeDetailDTOList);
	        }	        
	        return caseEntryDto;
	    }

        //#117454
		@Override
		public boolean checkCaseNoAlreadyPresent(String cseSuitNo, Long orgid) {
			Boolean caseNoPresent =  caseEntryRepository.checkCasePresentOrNot(cseSuitNo,orgid);
			if(caseNoPresent) {
				return true;
			}
			return false;
		}

		@Override
		public List<CaseEntryDTO> getcaseEntryByAdvId(Long advId, Long orgId) {
			List<CaseEntryDTO> caseEntryDTOList = new ArrayList<>();
			List<CaseEntry> list = caseEntryRepository.getCaseDetailsByAdvId(advId,orgId);
			list.forEach(dto -> {
				CaseEntryDTO caseEntry = new CaseEntryDTO();
		        BeanUtils.copyProperties(dto, caseEntry);
		        caseEntryDTOList.add(caseEntry);
			});
			return caseEntryDTOList;
		}
		
		//120788 to check tscl env
		@Override
		public boolean isTSCLEnvPresent() {
			Organisation org = organisatonService.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
			List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,org);
			return  envLookUpList.stream().anyMatch(
	                env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.TSCL) && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
			//return true;
		}
		
	@Transactional(readOnly = true)
	@Override
	public List<CaseEntryDTO> getCaseByRefCaseNumber(@PathParam("cseRefsuitNo") String cseRefsuitNo,
			@PathParam("orgId") Long orgId) {
		List<CaseEntryDTO> activeUserListDTOs = StreamSupport
				.stream(caseEntryRepository.getCaseDetailsByRefCaseNumber(cseRefsuitNo, orgId).spliterator(), false)
				.map(entity -> {
					CaseEntryDTO dto = new CaseEntryDTO();
					dto.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(entity.getCseDate()));
					BeanUtils.copyProperties(entity, dto);
					String crtName = courtMasterService.getCourtNameById(entity.getCrtId());
					if (StringUtils.isNotBlank(crtName))
						dto.setCourtName(crtName);
					return dto;
				}).collect(Collectors.toList());
		return activeUserListDTOs;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<String> findSuitNoByOrgid(@PathParam("orgId") Long orgId) {
		List<String> activeUserListDTOs = caseEntryRepository.findSuitNoByOrgid(orgId);
		return activeUserListDTOs;
	}
}
