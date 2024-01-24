package com.abm.mainet.swm.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.ISLRMEmployeeDAO;
import com.abm.mainet.swm.domain.SLRMEmployeeMaster;
import com.abm.mainet.swm.domain.SLRMEmployeeMasterHistory;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.swm.mapper.SLRMEmployeeMapper;
import com.abm.mainet.swm.repository.SLRMEmployeeMasterRepository;

@Service
public class SLRMEmployeeMasterService implements ISLRMEmployeeMasterService {

    private static Logger log = Logger.getLogger(SLRMEmployeeMasterService.class);

    @Autowired
    SLRMEmployeeMasterRepository sLRMEmployeeMasterRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    ISLRMEmployeeDAO sLRMEmployeeDAO;

    @Autowired
    private SLRMEmployeeMapper sLRMEmployeeMapper;

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISLRMEmployeeMasterService#saveEmployeeDetails(com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO)
     */
    @Override
    @Transactional
    public void saveEmployeeDetails(SLRMEmployeeMasterDTO sLRMEmployeeMasterDTO) {
        SLRMEmployeeMaster sLRMEmployeeMaster = new SLRMEmployeeMaster();
        BeanUtils.copyProperties(sLRMEmployeeMasterDTO, sLRMEmployeeMaster);
        sLRMEmployeeMasterRepository.save(sLRMEmployeeMaster);

        SLRMEmployeeMasterHistory sLRMEmployeeMasterHistory = new SLRMEmployeeMasterHistory();
        sLRMEmployeeMasterHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());

        try {
            auditService.createHistory(sLRMEmployeeMaster, sLRMEmployeeMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + sLRMEmployeeMaster, e);
        }

    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISLRMEmployeeMasterService#updateEmployeeDetails(com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO)
     */
    @Override
    @Transactional
    public void updateEmployeeDetails(SLRMEmployeeMasterDTO sLRMEmployeeMasterDTO) {
        SLRMEmployeeMaster sLRMEmployeeMaster = new SLRMEmployeeMaster();
        BeanUtils.copyProperties(sLRMEmployeeMasterDTO, sLRMEmployeeMaster);
        sLRMEmployeeMasterRepository.save(sLRMEmployeeMaster);
        SLRMEmployeeMasterHistory sLRMEmployeeMasterHistory = new SLRMEmployeeMasterHistory();
        sLRMEmployeeMasterHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());

        try {
            auditService.createHistory(sLRMEmployeeMaster, sLRMEmployeeMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + sLRMEmployeeMaster, e);
        }

    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISLRMEmployeeMasterService#searchEmployeeDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public SLRMEmployeeMasterDTO searchEmployeeDetails(Long empId, Long orgId) {
        SLRMEmployeeMasterDTO sLRMEmployeeMasterDTO = new SLRMEmployeeMasterDTO();
        SLRMEmployeeMaster sLRMEmployeeMaster = sLRMEmployeeMasterRepository.getEmployeeDetails(orgId, empId);
        if (sLRMEmployeeMaster != null) {
            BeanUtils.copyProperties(sLRMEmployeeMaster, sLRMEmployeeMasterDTO);
        }
        return sLRMEmployeeMasterDTO;
    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISLRMEmployeeMasterService#searchEmployeeList(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SLRMEmployeeMasterDTO> searchEmployeeList(Long empId, Long empUId, Long mrfId, Long orgId) {
        return sLRMEmployeeMapper.mapSLRMEmployeeMasterListToSLRMEmployeeMasterDTOList(
                sLRMEmployeeDAO.searchEmployeeList(empId, empUId, mrfId, orgId));

    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISLRMEmployeeMasterService#checkDuplicateMobileNo(java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Long checkDuplicateMobileNo(Long orgId, String empMobNo) {
        return (long) sLRMEmployeeMasterRepository.checkDuplicateMobileNo(orgId, empMobNo);
    }

}
