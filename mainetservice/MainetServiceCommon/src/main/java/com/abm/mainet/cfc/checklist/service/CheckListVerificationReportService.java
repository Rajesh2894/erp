package com.abm.mainet.cfc.checklist.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.checklist.dao.ICheckListVerificationReportDAO;
import com.abm.mainet.cfc.checklist.domain.CheckListReportEntity;
import com.abm.mainet.cfc.checklist.dto.CheckListVerificationReportChildDTO;
import com.abm.mainet.cfc.checklist.dto.CheckListVerificationReportParentDTO;
import com.abm.mainet.cfc.checklist.ui.model.ChecklistVerificationSearchModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Service
public class CheckListVerificationReportService implements ICheckListVerificationReportService {

    @Autowired
    ICheckListVerificationReportDAO iCheckListVerificationReportDAO;
    
    @Autowired
    ServiceMasterService serviceMaster;
    
    @Autowired
    private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

    @Override
    @Transactional
    public List<CheckListReportEntity> getRejLetterList(final Long applicationId, final String statusVariable,
            final Organisation orgId) {
        return iCheckListVerificationReportDAO.getRejLetterList(applicationId, statusVariable, orgId);
    }

    @Override
    @Transactional
    public List<CheckListVerificationReportParentDTO> getRejectedAppList(final ChecklistVerificationSearchModel model,
            final CheckListReportEntity checkListReportEntity, final Organisation orgId) {
        final List<CheckListVerificationReportParentDTO> dtoList = new ArrayList<>(0);
        final CheckListVerificationReportParentDTO dto = new CheckListVerificationReportParentDTO();

        SimpleDateFormat tf1 = new SimpleDateFormat(MainetConstants.TIME_FORMAT);
        final DateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        //Defect Id 132562 and 132551 this method is called at ChecklistVerificationSearchModel just before sendin  SmsAndEmail
        /*final long rejno = iCheckListVerificationReportDAO
                .updateApplicationMastrRejection(model.getApplicationDetails().getApmApplicationId(), orgId);*/
        final long rejno = tbCfcApplicationMstJpaRepository.getRejctionNoByAppId(model.getApplicationDetails().getApmApplicationId(), orgId.getOrgid());
        List<CheckListReportEntity> rejAppList = new ArrayList<>(0);
        rejAppList = model.getCheckListReportEntitylist();
        /***************************
         * Setting labels in the single DTO object
         ************************/

        final ApplicationSession appSession = ApplicationSession.getInstance();
        dto.setTletterno(String.valueOf(rejno));
        dto.setLletterNo(appSession.getMessage("cfc.letterNo"));
        dto.setLdate(appSession.getMessage("cfc.date"));
        dto.setlTo(appSession.getMessage("cfc.To"));
        dto.setlRejSub(appSession.getMessage("cfc.RejSub"));
        dto.setlRejRef(appSession.getMessage("cfc.RejRef"));
        dto.setlSirMadam(appSession.getMessage("cfc.Sir/Madam"));
        dto.setlSrNo(appSession.getMessage("cfc.SrNo"));
        dto.setlDocName(appSession.getMessage("cfc.DocName"));
        dto.setlObsDetail(appSession.getMessage("cfc.ObsDetail"));
        dto.setPage(appSession.getMessage("cfc.page"));
        dto.setOf(appSession.getMessage("cfc.of"));
        dto.setColon(":-");
        if (UserSession.getCurrent().getLanguageId() == 1) {
            tf1 = new SimpleDateFormat(MainetConstants.TIME_FORMAT);
            final Date date = new Date();
            dto.setParent(ApplicationSession.getInstance().getMessage("uwms.rep.bottomTitle") + " "
                    + dateFormat.format(date).toString() + " " + tf1.format(date.getTime()).toString());
        } else {
            tf1 = new SimpleDateFormat(MainetConstants.CommonConstants.DATE_FORMAT_HH_mm_ss);
            final Date date = new Date();
            dto.setParent(ApplicationSession.getInstance().getMessage("uwms.rep.bottomTitle") + " "
                    + dateFormat.format(date).toString() + " " + tf1.format(date.getTime()).toString() + " "
                    + ApplicationSession.getInstance().getMessage("uwms.rnwl.a"));
        }

        if (model.getAppSession().getLangId() == 1) {
            dto.setOrgLabel(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            dto.setOrgLabel(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }
        if (model.getAppSession().getLangId() == 1) {
            dto.setlUlbAddr(UserSession.getCurrent().getOrganisation().getOrgAddress());
        } else {
            dto.setlUlbAddr(UserSession.getCurrent().getOrganisation().getOrgAddressMar());
        }

        if (model.getStatusVariable().equalsIgnoreCase(MainetConstants.FlagR)) {
            dto.setlHeading(appSession.getMessage("cfc.reportheading"));
            ServiceMaster sm = serviceMaster.getServiceMaster(model.getCurrentServiceId(), orgId.getOrgid());
            if ((rejAppList != null) && !rejAppList.isEmpty()) {
                final CheckListReportEntity list = rejAppList.get(0);
              
                if (list.getAname() != null) {
                    final String applicantName = list.getAname();
                    dto.setApplicantName(applicantName);
                } else {
                    dto.setApplicantName(MainetConstants.CommonConstant.BLANK);
                }
                if (list.object1.get(1) != null) {
                    dto.setApplicationAdd((String) list.object1.get(1));
                }if (model.getAppAddress() != null) {
					dto.setApplicationAdd(model.getAppAddress());
				} else {
					dto.setApplicationAdd(MainetConstants.CommonConstant.BLANK);
				}
                dto.setSubject(appSession.getMessage("cfc.Subject"));
                if (list.getService() != null) {
                //#129760 to set service name in reg language
                if (UserSession.getCurrent().getLanguageId() == 1) 
                   dto.setSubject1(list.getService()); 
                else
                    dto.setSubject1(sm.getSmServiceNameMar());
                } else {
                    dto.setSubject1(MainetConstants.CommonConstant.BLANK);
                }
                dto.setSubject2(appSession.getMessage("cfc.subject1"));
                dto.setRefFill(appSession.getMessage("cfc.refFillmsg1"));
                if (list.getApmApplicationId() != null) {
                    dto.setRefFill1(list.getApmApplicationId().toString());
                } else {
                    dto.setRefFill1(MainetConstants.CommonConstant.BLANK);
                }
                dto.setRefFill2(appSession.getMessage("cfc.refFillmsg2"));
                if (list.getAdate() != null) {
                    dto.setRefFill3(list.getAdate());
                } else {
                    dto.setRefFill3(MainetConstants.CommonConstant.BLANK);
                }
                dto.setlRefLine(appSession.getMessage("cfc.RefLinemsg1"));
                if (list.getService() != null) {
                    // dto.setlRefLine1(list.getService());
                    dto.setlRefLine1(model.getApplicationDetails().getApplicationService());
                } else {
                    dto.setlRefLine1(MainetConstants.CommonConstant.BLANK);
                }
                dto.setlRefLine2(appSession.getMessage("cfc.RefLinemsg2"));
                dto.setlLastLine(appSession.getMessage("cfc.LastLine"));
                if (list.object.get(0) != null) {
                    dto.setSignPath((String) list.object.get(0));
                } else {
                    dto.setSignPath(MainetConstants.CommonConstant.BLANK);
                }
            }
           // #129760 to get dept name on rejection letter
            if(model.getAppSession().getLangId() == 1)
            dto.setDeptName(sm.getTbDepartment().getDpDeptdesc());
            else
             dto.setDeptName(sm.getTbDepartment().getDpNameMar());
        } else if (model.getStatusVariable().equalsIgnoreCase(MainetConstants.FlagH)) {
            dto.setlHeading(appSession.getMessage("cfc.Hheading"));
            if ((rejAppList != null) && !rejAppList.isEmpty()) {
                final CheckListReportEntity list = rejAppList.get(0);
                {
                    if (list.getAname() != null) {
                        final String applicantName = list.getAname();
                        dto.setApplicantName(applicantName);
                    } else {
                        dto.setApplicantName(MainetConstants.CommonConstant.BLANK);
                    }
                    if (list.object1.get(1) != null) {
                        dto.setApplicationAdd((String) list.object1.get(1));
                    } else {
                    dto.setApplicationAdd(MainetConstants.CommonConstant.BLANK);
                    }

                    dto.setSubject(appSession.getMessage("cfc.HSubject"));
                    if (list.getService() != null) {
                        // dto.setSubject1(list.getService());
                        dto.setSubject1(model.getApplicationDetails().getApplicationService());
                    } else {
                        dto.setSubject1(MainetConstants.CommonConstant.BLANK);
                    }

                    dto.setSubject2(appSession.getMessage("cfc.HSubject1"));
                    dto.setRefFill(appSession.getMessage("cfc.refFillmsg1"));
                    if (list.getApmApplicationId() != null) {
                        dto.setRefFill1(list.getApmApplicationId().toString());
                    } else {
                        dto.setRefFill1(MainetConstants.CommonConstant.BLANK);
                    }

                    dto.setRefFill2(appSession.getMessage("cfc.refFillmsg2"));
                    if (list.getAdate() != null) {
                        dto.setRefFill3(list.getAdate());
                    } else {
                        dto.setRefFill3(MainetConstants.CommonConstant.BLANK);
                    }

                    dto.setlRefLine(appSession.getMessage("cfc.HRefLinemsg1"));
                    if (list.getService() != null) {
                        // dto.setlRefLine1(list.getService());
                        dto.setlRefLine1(model.getApplicationDetails().getApplicationService());
                    } else {
                        dto.setlRefLine1(MainetConstants.CommonConstant.BLANK);
                    }

                    dto.setlRefLine2(appSession.getMessage("cfc.HRefLinemsg2"));
                    dto.setlLastLine(appSession.getMessage("cfc.HLastLine1"));
                    dto.setlLastLine2(appSession.getMessage("cfc.HLastLine2"));
                    if (list.object.get(0) != null) {
                        dto.setSignPath((String) list.object.get(0));
                    } else {
                        dto.setSignPath(MainetConstants.CommonConstant.BLANK);
                    }
                }
            }
        }
        /************************************
         * Setting Dynamic fields
         ****************************************/
        final List<CheckListVerificationReportChildDTO> ChildDTOList = new ArrayList<>(0);

        if ((rejAppList != null) && !rejAppList.isEmpty()) {
            int srNo = 1;
            CheckListVerificationReportChildDTO ChildDTO = null;
            List<LookUp> rejectedDocs = null;
            for (final CheckListReportEntity List : rejAppList) {
                rejectedDocs = List.getRejectedDocs();
                if ((rejectedDocs != null) && !rejectedDocs.isEmpty()) {
                    for (final LookUp lookUp : rejectedDocs) {
                        ChildDTO = new CheckListVerificationReportChildDTO();
                        {
                            if (lookUp.getLookUpDesc() != null) {
                                ChildDTO.settDocName(lookUp.getLookUpDesc());
                            } else {
                                ChildDTO.settDocName(MainetConstants.CommonConstant.BLANK);
                            }
                            if (lookUp.getOtherField() != null) {
                                ChildDTO.settObsDetail(lookUp.getOtherField());
                            }
                            ChildDTO.settSrNo(String.valueOf(srNo));
                            srNo = srNo + 1;
                            ChildDTOList.add(ChildDTO);
                        }
                    }
                }
            }
            dto.setChildDTO(ChildDTOList);
            dtoList.add(dto);
        }

        return dtoList;
    }

	@Override
	@Transactional
	public long updateApplicationMastrRejection(Long apmApplicationId, Organisation  org) {
		 long rejno =iCheckListVerificationReportDAO
          .updateApplicationMastrRejection(apmApplicationId, org);
		return rejno;
	}

}
