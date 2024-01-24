/**
 *
 */
package com.abm.mainet.common.integration.dms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadataDet;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.repository.IFileUploadCrudRepository;
import com.abm.mainet.common.integration.dms.repository.IMetadataRepository;

/**
 * @author vishnu.jagdale
 *
 */

@Repository
public class FileUploadRepository implements IFileUploadRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IFileUploadCrudRepository crudRepository;

    @Autowired
    private AttachDocsRepository attachUploadRepository;
    
    @Autowired
    IMetadataRepository metadataRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetsource.upload.repository.IFileUploadRepository#saveFilePathToDB(com.abm.mainet.common.integration.dms.domain.
     * CFCAttachment)
     */
    @Override
    public boolean saveFilePathToDB(final List<CFCAttachment> saveFilePathList) {
        boolean status = false;
        try {
            crudRepository.save(saveFilePathList);
            status = true;
        } catch (final Exception e) {
            LOGGER.error("Error During getCheckListData call : ", e);

        }

        return status;
    }

    @Override
    public boolean masterSaveFilePathToDB(List<AttachDocs> masterSavePathList) {
        boolean status = false;
        try {

            attachUploadRepository.save(masterSavePathList);
            status = true;
        } catch (final Exception exception) {
            LOGGER.error("Error Occured During file Saved : " + exception);
        }

        return status;
    }
    
	@Override
	public boolean saveInMetadata(String dmsDocId, String docName, DmsDocsMetadataDto dmsMetadaDto) {
		boolean status = false;
		try {
			DmsDocsMetadata entity1 = new DmsDocsMetadata();
			entity1 = metadataRepository.findOne(dmsMetadaDto.getDmsId());
			if (entity1 != null) {
				if (entity1.getDmsDocId() != null) {
					dmsDocId += MainetConstants.operator.COMMA + entity1.getDmsDocId();
				}
				if (entity1.getDmsDocName() != null) {
					docName += MainetConstants.operator.COMMA + entity1.getDmsDocName();
				}
				if (CollectionUtils.isNotEmpty(entity1.getDmsDocsMetadataDetList())) {
					metadataRepository.deleteByDmsId(dmsMetadaDto.getDmsId());
				}
			}
			DmsDocsMetadata entity = new DmsDocsMetadata();
			List<DmsDocsMetadataDet> detEntityList = new ArrayList<DmsDocsMetadataDet>();
			BeanUtils.copyProperties(dmsMetadaDto, entity);
			dmsMetadaDto.getDmsDocsMetadataDetList().forEach(data -> {
				DmsDocsMetadataDet detEntity = new DmsDocsMetadataDet();
				BeanUtils.copyProperties(data, detEntity);
				detEntity.setDmsDocsMetadata(entity);
				if (data.getOrgId() == null)
					detEntity.updateAuditFields();
				detEntityList.add(detEntity);
			});
			if (dmsMetadaDto.getOrgId() == null)
				entity.updateAuditFields();
			entity.setDmsDocId(dmsDocId);
			entity.setDmsDocName(docName);
			entity.setDmsDocsMetadataDetList(detEntityList);
			metadataRepository.save(entity);
		} catch (Exception e) {
			LOGGER.error("Error Occured During file Save in Metadata Table: " + e);
		}
		return status;
	}

}
