/**
 *
 */
package com.abm.mainet.common.integration.dms.dao;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.dto.DmsMetadataDto;

/**
 * @author vishnu.jagdale
 *
 */
public interface IFileUploadRepository {

    public boolean saveFilePathToDB(List<CFCAttachment> saveFilePathList);

    public boolean masterSaveFilePathToDB(List<AttachDocs> masterSavePathList);

	boolean saveInMetadata(String dmsDocID, String docName, DmsDocsMetadataDto dmsDocsMetadataDto);

}
