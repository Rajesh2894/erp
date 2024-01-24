package com.abm.mainet.common.workflow.service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.workflow.domain.BpmBrmDeploymentMaster;
import com.abm.mainet.common.workflow.domain.BpmBrmDeploymentMasterHistory;
import com.abm.mainet.common.workflow.dto.BpmBrmDeploymentMasterDTO;
import com.abm.mainet.common.workflow.repository.BpmBrmDeploymentMasterRepository;

/**
 * This class provides services to manage BPM/BRM deployments and their runtime. This master services allow you to manage
 * integration of MainetServices and BPM/BRM tools.
 * 
 * @author sanket.joshi
 *
 */
@Service
public class BpmBrmDeploymentServiceImpl implements BpmBrmDeploymentService {

    private static final Logger LOGGER = Logger.getLogger(BpmBrmDeploymentServiceImpl.class);

    @Autowired
    private BpmBrmDeploymentMasterRepository bpmBrmDeploymentMasterRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public void saveBpmBrmDeploymentMaster(BpmBrmDeploymentMasterDTO bpmBrmDeploymentMasterDto) {
        BpmBrmDeploymentMaster bpmBrmDeploymentMaster = new BpmBrmDeploymentMaster();
        BeanUtils.copyProperties(bpmBrmDeploymentMasterDto, bpmBrmDeploymentMaster);
        bpmBrmDeploymentMasterRepository.updateDeploymentMasterStatus(bpmBrmDeploymentMasterDto.getArtifactId(), bpmBrmDeploymentMasterDto.getGroupId());
        bpmBrmDeploymentMasterRepository.save(bpmBrmDeploymentMaster);        
        BpmBrmDeploymentMasterHistory history = new BpmBrmDeploymentMasterHistory();
        history.setHstatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(bpmBrmDeploymentMaster, history);

    }

    @Override
    @Transactional
    public void updateBpmBrmDeploymentMaster(BpmBrmDeploymentMasterDTO bpmBrmDeploymentMasterDto) {
        BpmBrmDeploymentMaster bpmBrmDeploymentMaster = new BpmBrmDeploymentMaster();
        BeanUtils.copyProperties(bpmBrmDeploymentMasterDto, bpmBrmDeploymentMaster);
        bpmBrmDeploymentMasterRepository.save(bpmBrmDeploymentMaster);

        BpmBrmDeploymentMasterHistory history = new BpmBrmDeploymentMasterHistory();
        history.setHstatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(bpmBrmDeploymentMaster, history);

        sendChangeNotifications(bpmBrmDeploymentMasterDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BpmBrmDeploymentMasterDTO> getAllBpmBrmDeploymentMaster() {

        List<BpmBrmDeploymentMasterDTO> bpmBrmDeploymentMasterDTO = StreamSupport
                .stream(bpmBrmDeploymentMasterRepository.findAll().spliterator(), false)
                .map(entity -> {
                    BpmBrmDeploymentMasterDTO dto = new BpmBrmDeploymentMasterDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return bpmBrmDeploymentMasterDTO;

    }

    @Transactional(readOnly = true)
    @Override
    public BpmBrmDeploymentMasterDTO getBpmBrmDeploymentMasterrById(Long id) {
        BpmBrmDeploymentMaster bpmBrmDeploymentMaster = bpmBrmDeploymentMasterRepository.findOne(id);
        BpmBrmDeploymentMasterDTO dto = new BpmBrmDeploymentMasterDTO();
        BeanUtils.copyProperties(bpmBrmDeploymentMaster, dto);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isDeploymentExist(BpmBrmDeploymentMasterDTO deployment) {
        boolean deploymentExist = false;
        List<BpmBrmDeploymentMaster> dbDeployments = bpmBrmDeploymentMasterRepository.findByGroupIdAndArtifactIdAndVersionAndStatus(
                deployment.getGroupId(), deployment.getArtifactId(), deployment.getVersion(), deployment.getStatus());
        deploymentExist = !dbDeployments.isEmpty();
        return deploymentExist;
    }

    /**
     * This method will send notification to selected department user using department short code. Depending on the department the
     * specific modules will be responsible to provide implementation of BpmBrmDeploymentChnageListener.
     * 
     * Using system properties defined under "service-classes-configuration.properties" with pattern
     * "mainet.bpm.deploymentChangeListener.{DEPT_SHORT_CODE}=class name" target implementation class will be invoked to send
     * notification to specific department.
     * 
     * 
     * @param bpmBrmDeploymentMasterDto
     */
    private void sendChangeNotifications(BpmBrmDeploymentMasterDTO bpmBrmDeploymentMasterDto) {
        if (!bpmBrmDeploymentMasterDto.isNotifyUsers())
            return;
        String serviceClassName = null;
        String classNameKey = MainetConstants.WorkFlow.BpmBrmDeployment.DEPLOYMENT_CHANGE_LISTENER_ROOT
                + bpmBrmDeploymentMasterDto.getNotifyToDepartment().toLowerCase();
        serviceClassName = messageSource.getMessage(classNameKey, new Object[] {},
                StringUtils.EMPTY, Locale.ENGLISH);
        if (serviceClassName != null && !serviceClassName.isEmpty()) {
            try {
                Class<?> clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
                        .getClassLoader());
                BpmBrmDeploymentChnageListener dynamicServiceInstance = (BpmBrmDeploymentChnageListener) ApplicationContextProvider
                        .getApplicationContext().getAutowireCapableBeanFactory()
                        .autowire(clazz, 4, false);
                dynamicServiceInstance.notifyUsers(bpmBrmDeploymentMasterDto.getNotifyToDepartment());
            } catch (Exception e) {
                LOGGER.error("Exception while sending change notification with cause " + e.getCause() + " and exception "
                        + e.getMessage());
            }
        }
    }
}
