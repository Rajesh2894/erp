/**
 *
 */
package com.abm.mainet.common.formbuilder.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLabelDTO;
import com.abm.mainet.cfc.scrutiny.dto.ViewCFCScrutinyLabelValue;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.formbuilder.dao.IFormBuilderDataDAO;
import com.abm.mainet.common.formbuilder.domain.FormBuilderValueEntity;
import com.abm.mainet.common.formbuilder.domain.FormBuilderValueEntityHistory;
import com.abm.mainet.common.formbuilder.dto.FormBuilderValueDTO;
import com.abm.mainet.common.formbuilder.repository.FormBuilderValueJpaRepository;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * The Class ScrutinyServiceImpl.
 *
 * @author Lalit.Prusti
 */
@Service
public class FormBuilderUtilityService implements IFormBuilderUtilityService {

    private static Logger LOG = Logger.getLogger(FormBuilderUtilityService.class);

    @Resource
    private IFormBuilderDataDAO formBuilderDataDAO;

    @Resource
    private EmployeeJpaRepository employeeJpaRepository;

    @Resource
    private FormBuilderValueJpaRepository formBuilderValueJpaRepository;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Override
    @Transactional(readOnly = true)
    public ScrutinyLabelDTO populateFormBuilderLabelData(final Long applicationId, final Long empId, final Long gmId,
            final Long orgId,
            final String shortDesc, final int langId) {

        LOG.info("Start the populateScrutinyLabelData()");
        final ScrutinyLabelDTO scrutinyLabelDTO = new ScrutinyLabelDTO();
        try {
            scrutinyLabelDTO.setApplicationId(applicationId + MainetConstants.BLANK);
            if (null != shortDesc) {
                final String servicename = serviceMasterService.getServiceMasterByShortCode(shortDesc,orgId).getSmServiceName();
                scrutinyLabelDTO.setServiceName(servicename);
               // scrutinyLabelDTO.setSmServiceId(shortDesc);
            }
            final List<ViewCFCScrutinyLabelValue> scrutinyLabelValues = new ArrayList<>();
            final List<Object> fndAllscrutinyLabelValue = formBuilderDataDAO.getfindAllFormLabelValueList(orgId, shortDesc,
                    null, applicationId);

            if (fndAllscrutinyLabelValue != null) {

                final int listSize = fndAllscrutinyLabelValue.size();

                for (int iCounter = 0; iCounter < listSize; iCounter++) {

                    final Object[] obj = (Object[]) fndAllscrutinyLabelValue.get(iCounter);
                    ViewCFCScrutinyLabelValue labelValue = null;

                    labelValue = new ViewCFCScrutinyLabelValue();

                    labelValue.setSmShortDesc(obj[0].toString());

                    if ((obj[1].toString() != null) && !obj[1].toString().isEmpty()) {
                        labelValue.setSlLabel((obj[1].toString()));
                    }

                    if ((obj[2] != null) && !obj[2].toString().isEmpty()) {
                        labelValue.setSlAuthorising(obj[2].toString());
                    }

                    if ((obj[3] != null) && !obj[3].toString().isEmpty()) {
                        labelValue.setSlDatatype(obj[3].toString());
                    }

                    if ((obj[4] != null) && !obj[4].toString().isEmpty()) {
                        labelValue.setSlDisplayFlag(obj[4].toString());
                    }

                    if ((obj[5] != null) && !obj[5].toString().isEmpty()) {
                        labelValue.setSlFormMode(obj[5].toString());
                    }

                    if ((obj[6] != null) && !obj[6].toString().isEmpty()) {
                        labelValue.setSlFormName(obj[6].toString());
                    }

                    if (obj[7] != null) {
                        labelValue.setSlLabelId(Long.valueOf(obj[7].toString()));
                    }

                    if ((obj[8] != null) && !obj[8].toString().isEmpty()) {
                        labelValue.setSlLabelMar(obj[8].toString());
                    }

                    if ((obj[9] != null) && !obj[9].toString().isEmpty()) {
                        labelValue.setSlPreValidation(obj[9].toString());
                    }

                    if ((obj[10] != null) && !obj[10].toString().isEmpty()) {
                        labelValue.setSlValidationText(obj[10].toString());
                    }

                    if ((obj[11] != null) && !obj[11].toString().isEmpty()) {
                        labelValue.setSlTableColumn(obj[11].toString());
                    }

                    if ((obj[12] != null) && !obj[12].toString().isEmpty()) {
                        labelValue.setSlWhereClause(obj[12].toString());
                    }

                    if ((obj[13] != null) && !obj[13].toString().isEmpty()) {
                        labelValue.setSlDsgid(Long.valueOf(obj[13].toString()));
                    }

                    if ((obj[14] != null) && !obj[14].toString().isEmpty()) {
                        labelValue.setLevels(Long.valueOf(obj[14].toString()));
                    }

                    if ((obj[15] != null) && !obj[15].toString().isEmpty()) {
                        labelValue.setSvValue(obj[15].toString());
                    }
                    if ((obj[16] != null) && !obj[16].toString().isEmpty()) {
                        labelValue.setApplicationId(Long.valueOf(obj[16].toString()));
                    } else {

                        labelValue.setApplicationId(applicationId);
                    }
                    if ((obj[17] != null) && !obj[17].toString().isEmpty()) {
                        labelValue.setFormId(Long.valueOf(obj[17].toString()));
                    } 

                    labelValue.setOrgId(orgId);
                    scrutinyLabelValues.add(labelValue);
                }
            }

          /*  final GroupMaster gmMaster = formBuilderDataDAO.getDesignationName(gmId);          
            scrutinyLabelDTO.setRoleId(gmMaster.getGrCode());*/
            scrutinyLabelDTO.setRoleId(String.valueOf(gmId));
            if ((scrutinyLabelValues != null) && (scrutinyLabelValues.size() > 0)) {
                final Map<Long, List<ViewCFCScrutinyLabelValue>> desgLabelMap = filterListAsPerDesignation(
                        scrutinyLabelDTO, scrutinyLabelValues, langId, orgId);
                scrutinyLabelDTO.setDesgWiseScrutinyLabelMap(desgLabelMap);
            }

        } catch (final Exception exception) {
            LOG.error("Exception occur in populateScrutinyLabelData() ", exception);

        }
        return scrutinyLabelDTO;
    }

    private Map<Long, List<ViewCFCScrutinyLabelValue>> filterListAsPerDesignation(final ScrutinyLabelDTO scrutinyLabelDTO,
            final List<ViewCFCScrutinyLabelValue> cfcScrutinyLabelValues, final int langId, final long orgId) {

        final Map<Long, String> desgNameMap = new HashMap<>(0);

        final Map<Long, List<ViewCFCScrutinyLabelValue>> desgLabelMap = new LinkedHashMap<>(
                0);

        List<ViewCFCScrutinyLabelValue> viewCFCScrutinyLabelValues = null;

        for (final ViewCFCScrutinyLabelValue viewCFCScrutinyLabelValue : cfcScrutinyLabelValues) {
            viewCFCScrutinyLabelValues = null;
            if (desgLabelMap.containsKey(Long.valueOf(scrutinyLabelDTO.getRoleId()))) {
                viewCFCScrutinyLabelValues = desgLabelMap.get(Long.valueOf(scrutinyLabelDTO.getRoleId()));
            }
            if (viewCFCScrutinyLabelValues == null) {
                viewCFCScrutinyLabelValues = new ArrayList<>();
            }
            viewCFCScrutinyLabelValues.add(viewCFCScrutinyLabelValue);

            desgLabelMap.put(Long.valueOf(scrutinyLabelDTO.getRoleId()), viewCFCScrutinyLabelValues);

            if ((Long.valueOf(scrutinyLabelDTO.getRoleId()) != null)
                    && !desgNameMap.containsKey(Long.valueOf(scrutinyLabelDTO.getRoleId()))) {

                final GroupMaster gmMaster = formBuilderDataDAO.getDesignationName(Long.valueOf(scrutinyLabelDTO.getRoleId()));
                if (gmMaster != null) {
                    if (langId == 1) {
                        desgNameMap.put(Long.valueOf(scrutinyLabelDTO.getRoleId()), gmMaster.getGrDescEng());
                    } else {
                        desgNameMap.put(Long.valueOf(scrutinyLabelDTO.getRoleId()), gmMaster.getGrDescEng());
                    }
                }
            }
        }

        scrutinyLabelDTO.setDesgNameMap(desgNameMap);

        /*
         * scrutinyLabelDTO.setDsgWiseScrutinyDocMap(
         * getAllDesgWiseScrutinyDoc(Long.parseLong(scrutinyLabelDTO.getApplicationId()), orgId));
         */

        final List<String> list = new ArrayList<>();
        list.add(MainetConstants.YESL);
        list.add(MainetConstants.NOL);
        scrutinyLabelDTO.setDislist(list);

        return desgLabelMap;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.scrutiny.service.ScrutinyService#
     * saveCompleteScrutinyLabel(com.abm.mainetservice.web.common.entity. FormBuilderValueEntity)
     */
    @Override
    @Transactional
    public boolean saveCompleteFormBuilderLabel(final List<ViewCFCScrutinyLabelValue> list, final UserSession userSession,
            final ScrutinyLabelDTO scrutinyLabelDTO, final boolean updateFlag, Long taskId, List<Long> attachmentId) {
        boolean flag = false;
        LOG.info("Start the saveScrutinyValueBylabelId ");
        FormBuilderValueEntity labelValue = null;
        final Date date = new Date();
        try {
            List<FormBuilderValueEntity> valueList = new ArrayList<>();
            List<Object> historyList = new ArrayList<>();
            Long surveyNumber = null;
            for (final ViewCFCScrutinyLabelValue value : list) {

                labelValue = new FormBuilderValueEntity();


                if (value.getApplicationId() != null
                        && value.getApplicationId() != 0L) {
                	labelValue.setSaApplicationId(value.getApplicationId());

                } else {
                    if (null == surveyNumber) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
                        String formatedDate = LocalDate.now().format(formatter).toString();

                        final Long sequence = seqGenFunctionUtility.generateSequenceNo(
                                MainetConstants.SolidWasteManagement.SHORT_CODE,
                                "TB_FORMBUILDER_VALUES", "FORM_VID", value.getOrgId(),
                                MainetConstants.FlagD, null);

                        final String paddingAppNo = String.format(MainetConstants.LOI.LOI_NO_FORMAT,
                                Integer.parseInt(sequence.toString()));
                        final String orgId = value.getOrgId().toString();
                        final String appNumber = orgId.concat(formatedDate).concat(paddingAppNo);
                        surveyNumber = Long.valueOf(appNumber);
                        userSession.setCurrentAppid(surveyNumber);
                    }

                    labelValue.setSaApplicationId(surveyNumber);
                }
                labelValue.setFormId(value.getFormId());
                labelValue.setSlLabelId(value.getSlLabelId());
                labelValue.setLevels(value.getLevels());
                labelValue.setSvValue(value.getSvValue());
                // scrutinyLabelValue.setLangId(userSession.getLanguageId());
             
                labelValue.setOrgId(userSession.getOrganisation());
                if(value.getFormId() != null) {
                	  // todo 
                	labelValue.setUpdatedBy(userSession.getEmployee());
  	                labelValue.setUpdatedDate(date);
  	                labelValue.setLgIpMacUpd(userSession.getEmployee().getEmppiservername());
                }
                // todo 
                labelValue.setLmodDate(date);
                labelValue.setUserId(userSession.getEmployee());
                labelValue.setLgIpMac(userSession.getEmployee().getEmppiservername());
                valueList.add(labelValue);

            }
            formBuilderValueJpaRepository.save(valueList);

            valueList.forEach(entity -> {
                FormBuilderValueEntityHistory history = new FormBuilderValueEntityHistory();
                BeanUtils.copyProperties(entity, history);
                history.setOrgid(entity.getOrgId().getOrgid());
                history.setUserId(entity.getUserId().getEmpId());
                if (entity.getSlLabelId() == null) {
                    history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                } else {
                    history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }

                historyList.add(history);
            });

            try {
                auditService.createHistoryForListObj(historyList);
            } catch (Exception e) {
                LOG.error("Could not make audit entry ", e);
            }

        }

        catch (final Exception exception) {
            LOG.error("Exception occur while calling jbpm workflow in saveCompleteScrutinyLabel() ", exception);
            return flag;
        }
        return flag;

    }

    /*
     * @Override
     * @Transactional(readOnly = true) public String getValueByLabelQuery(final String query) { return
     * formBuilderDataDAO.getValueByLabelQuery(query); }
     */
    @Override
    public List<FormBuilderValueDTO> searchFormValue(Long orgId, String serviceCode) {
        List<Object> searchList = formBuilderDataDAO.searchFormValue(orgId, serviceCode);

        Map<Long, String> empMap = employeeJpaRepository.getAllEmployee(orgId).stream()
                .collect(Collectors.toMap(Employee::getEmpId, Employee::getFullName));
        List<FormBuilderValueDTO> list = new ArrayList<>();
        FormBuilderValueDTO dto = null;

        for (Object entity : searchList) {
            dto = new FormBuilderValueDTO();
            Object[] data = (Object[]) entity;
            dto.setReferenceNo(Long.valueOf(data[0].toString()));
            dto.setOrgId(Long.valueOf(data[1].toString()));
            dto.setCreatedBy(Long.valueOf(data[2].toString()));
            dto.setEmpName(empMap.get(Long.valueOf(data[2].toString())));
            dto.setCreatedDate((Date) data[3]);
            list.add(dto);
        }

        return list;
    }

	@Override
	@Transactional
	public boolean saveFormBuilder(List<FormBuilderValueEntity> list) {
		    	formBuilderValueJpaRepository.save(list);
		    	   List<Object> historyList = new ArrayList<>();
		    	list.forEach(entity -> {
		              FormBuilderValueEntityHistory history = new FormBuilderValueEntityHistory();
		              BeanUtils.copyProperties(entity, history);
		              history.setOrgid(entity.getOrgId().getOrgid());
		              history.setUserId(entity.getUserId().getEmpId());
		              if (entity.getSlLabelId() == null) {
		                  history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
		              } else {
		                  history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
		              }

		              historyList.add(history);
		          });

		          try {
		              auditService.createHistoryForListObj(historyList);
		          } catch (Exception e) {
		              LOG.error("Could not make audit entry ", e);
		          }
				return true;
	}
    
   

}
