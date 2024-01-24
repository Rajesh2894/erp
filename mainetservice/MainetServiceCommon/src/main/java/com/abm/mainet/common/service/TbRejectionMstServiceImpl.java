/*
 * Created on 5 Apr 2016 ( Time 11:55:14 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbRejectionMstEntity;
import com.abm.mainet.common.domain.TbRejectionMstEntityKey;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.dto.TbRejectionMst;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.TbRejectionMstJpaRepository;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Implementation of TbRejectionMstService
 */
@Service
public class TbRejectionMstServiceImpl extends AbstractService implements TbRejectionMstService {

    @Resource
    private TbRejectionMstJpaRepository tbRejectionMstJpaRepository;

    @Autowired
    private CFCAttechmentRepository cFCAttechmentRepository;

    @Resource
    private TbRejectionMstServiceMapper tbRejectionMstServiceMapper;

    @Autowired
    SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Resource
    private DepartmentService departmentService;
    
    @Autowired
    private MessageSource messageSource;
    
    @Override
    public List<TbRejectionMst> findByApplicationID(final TbRejectionMst tbRejectionMst) {
        final Iterable<TbRejectionMstEntity> tbRejectionMstEntity = tbRejectionMstJpaRepository.finbyApplicationId(
                tbRejectionMst.getRejType(), UserSession.getCurrent().getOrganisation().getOrgid(),
                tbRejectionMst.getRejServiceId(), tbRejectionMst.getRejApplicationId());
        final List<TbRejectionMst> beans = new ArrayList<>();
        for (final TbRejectionMstEntity tbRejectionMstEntity1 : tbRejectionMstEntity) {
            beans.add(tbRejectionMstServiceMapper.mapTbRejectionMstEntityToTbRejectionMst(tbRejectionMstEntity1));
        }
        return beans;
    }

    @Override
    public List<TbRejectionMst> create(final TbRejectionMst tbRejectionMst) {
        final TbRejectionMstEntityKey id = new TbRejectionMstEntityKey(tbRejectionMst.getRejId(), tbRejectionMst.getOrgid());
        TbRejectionMstEntity tbRejectionMstEntity = tbRejectionMstJpaRepository.findOne(id);
        TbRejectionMstEntity tbRejectionMstEntitySaved = null;
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();
        lableValueDTO.setApplicationId(Long.valueOf(tbRejectionMst.getRejApplicationId() + ""));
        lableValueDTO.setLableId(tbRejectionMst.getLabelId());
        lableValueDTO.setUserId(UserSession.getCurrent().getEmployee().getUserId());
        lableValueDTO.setOrgId(orgId);
        lableValueDTO.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
        lableValueDTO.setLevel(tbRejectionMst.getLevel());
        lableValueDTO.setLableValue(tbRejectionMst.getLableValue());
        final List<TbRejectionMst> beans = new ArrayList<>();
        final List<CFCAttachment> documentList = iChecklistVerificationService
                .getDocumentUploaded(Long.valueOf(tbRejectionMst.getRejApplicationId() + ""), orgId);
        for (final TbApprejMas tabapprejmas : tbRejectionMst.getRejectionlist()) {
            if (tabapprejmas.getIsSelected() != null) {
                tbRejectionMstEntity = new TbRejectionMstEntity();
                tbRejectionMstServiceMapper.mapTbRejectionMstToTbRejectionMstEntity(tbRejectionMst, tbRejectionMstEntity);
                if (tabapprejmas.getArtId() == null) {
                    final Long rejId = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM, MainetConstants.CommonMasterUi.TB_REJECTION_MST,MainetConstants.CommonMasterUi.REJ_ID, orgId, null,
                            null);
                    tbRejectionMstEntity.setRejId(rejId);
                }
                tbRejectionMstEntity.setRejRefId(tabapprejmas.getIsSelected());
                tbRejectionMstEntity.setRejLetterDate(new Date());
                tbRejectionMstEntity.setLmoddate(new Date());
                tbRejectionMstEntity.setUpdatedDate(new Date());
                tbRejectionMstEntity.setUserid(UserSession.getCurrent().getEmployee().getEmpId());
                tbRejectionMstEntity.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                tbRejectionMstEntity.setLangid(Long.valueOf(UserSession.getCurrent().getLanguageId()));
                tbRejectionMstEntity.setLgIpMac(Utility.getMacAddress());
                tbRejectionMstEntitySaved = tbRejectionMstJpaRepository.save(tbRejectionMstEntity);
                beans.add(tbRejectionMstServiceMapper.mapTbRejectionMstEntityToTbRejectionMst(tbRejectionMstEntitySaved));

            }

        }
        int count = 0;
        if (!tbRejectionMst.getDocumentList().isEmpty()) {
            for (final CFCAttachment cAttachment : tbRejectionMst.getDocumentList()) {
                final CFCAttachment cAttachment2 = documentList.get(count);
                cAttachment2.setClmRemark(cAttachment.getClmRemark());
                cFCAttechmentRepository.save(cAttachment2);
                count++;
            }
        }

        saveScrutinyValue(lableValueDTO);
        return beans;
    }

    @Override
    public TbRejectionMst update(final TbRejectionMst tbRejectionMst) {
        final TbRejectionMstEntityKey id = new TbRejectionMstEntityKey(tbRejectionMst.getRejId(), tbRejectionMst.getOrgid());
        final TbRejectionMstEntity tbRejectionMstEntity = tbRejectionMstJpaRepository.findOne(id);
        tbRejectionMstServiceMapper.mapTbRejectionMstToTbRejectionMstEntity(tbRejectionMst, tbRejectionMstEntity);
        final TbRejectionMstEntity tbRejectionMstEntitySaved = tbRejectionMstJpaRepository.save(tbRejectionMstEntity);
        return tbRejectionMstServiceMapper.mapTbRejectionMstEntityToTbRejectionMst(tbRejectionMstEntitySaved);
    }

    public TbRejectionMstJpaRepository getTbRejectionMstJpaRepository() {
        return tbRejectionMstJpaRepository;
    }

    public void setTbRejectionMstJpaRepository(final TbRejectionMstJpaRepository tbRejectionMstJpaRepository) {
        this.tbRejectionMstJpaRepository = tbRejectionMstJpaRepository;
    }

    public TbRejectionMstServiceMapper getTbRejectionMstServiceMapper() {
        return tbRejectionMstServiceMapper;
    }

    public void setTbRejectionMstServiceMapper(final TbRejectionMstServiceMapper tbRejectionMstServiceMapper) {
        this.tbRejectionMstServiceMapper = tbRejectionMstServiceMapper;
    }

	@Override
	public String setConnectionNumber(Department dept, Long applicationId, Organisation org,
			ServiceMaster serviceMaster) {

	 String connectionNumber = null;
        try {
            CFCCommonService dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean("NewWaterConnection",
            		CFCCommonService.class);
            connectionNumber = dynamicServiceInstance.getConnectionDetailsByAppIdAndOrgId(applicationId, org.getOrgid(),  
            		serviceMaster.getSmShortdesc());
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in fetching bill for Adjustment entry for Department: " + dept.getDpDeptid(), e);
        }
	return connectionNumber;
	}

}
