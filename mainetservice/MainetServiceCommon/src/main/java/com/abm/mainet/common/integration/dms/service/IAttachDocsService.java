/**
 *
 */
package com.abm.mainet.common.integration.dms.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;

/**
 * @author ritesh.patil
 *
 */
public interface IAttachDocsService {

    boolean saveMasterDocuments(List<AttachDocs> entities);

    List<AttachDocs> findByCode(Long orgId, String Identifier);

    List<AttachDocs> findByIdfInQuery(Long orgId, List<String> identifier);

    int updateMasterDocumentStatus(String uniqueId, String status);

    List<AttachDocs> findAllDocLikeReferenceId(Long orgId, String identifier);

    boolean updateRecordByUniqueId(List<Long> ids, Long empId, String flag);

    void deleteDocFileById(List<Long> enclosureRemoveById, Long empId);

	AttachDocs findDocLikeReferenceId(Long orgId, String identifier);
}
