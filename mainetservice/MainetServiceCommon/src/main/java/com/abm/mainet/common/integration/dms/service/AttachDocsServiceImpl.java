/**
 *
 */
package com.abm.mainet.common.integration.dms.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;

/**
 * It is Not for Application specific document. It is used to persist Master specific document to TB_ATTACH_DOCUMENT.
 *
 */
@Service
@Repository
public class AttachDocsServiceImpl implements IAttachDocsService {

    @Autowired
    private AttachDocsRepository attachDocsRepository;

    @Autowired
    private IAttachDocsDao iAttachDocsDao;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.core.service.IAttachDocsService#saveEstateDocs()
     */
    private static Logger LOG = Logger.getLogger(AttachDocsServiceImpl.class);

    @Override
    @Transactional
    public boolean saveMasterDocuments(final List<AttachDocs> entities) {
        boolean flag = false;
        try {
            attachDocsRepository.save(entities);
            flag = true;
        } catch (final Exception exception) {
            LOG.error("Exception occur in saveMasterDocuments() ", exception);
            return flag;
        }
        return flag;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.core.service.IAttachDocsService#findByEstateCode(java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttachDocs> findByCode(final Long orgId,
            final String identifier) {
        return attachDocsRepository.findByCode(orgId, identifier, MainetConstants.FlagA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachDocs> findByIdfInQuery(final Long orgId,
            final List<String> identifier) {
        return attachDocsRepository.findByIdfInQuery(orgId, identifier, MainetConstants.FlagA);
    }

    @Override
    @Transactional
    public int updateMasterDocumentStatus(String uniqueId, String status) {
        return attachDocsRepository.updateMasterDocumentStatus(uniqueId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachDocs> findAllDocLikeReferenceId(final Long orgId,
            final String identifier) {
        return attachDocsRepository.findAllDocLikeReferenceId(orgId, identifier, MainetConstants.FlagA);
    }

    @Override
    @Transactional
    public boolean updateRecordByUniqueId(final List<Long> ids, final Long empId, final String flag) {
        return iAttachDocsDao.updateRecord(ids, empId, flag);
    }

    @Override
    @Transactional
    public void deleteDocFileById(List<Long> enclosureRemoveById, Long empId) {
        iAttachDocsDao.updateRecord(enclosureRemoveById, empId, MainetConstants.Common_Constant.DELETE_FLAG);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AttachDocs findDocLikeReferenceId(final Long orgId,
            final String identifier) {
        return attachDocsRepository.findDocLikeReferenceId(orgId, identifier, MainetConstants.FlagA);
    }

}
