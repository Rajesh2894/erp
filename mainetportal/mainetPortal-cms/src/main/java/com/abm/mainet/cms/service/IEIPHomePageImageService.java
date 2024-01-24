/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;
import java.util.Set;

import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.cms.domain.EIPHomeImagesHistory;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.dms.client.FileNetApplicationClient;

/**
 * @author vinay.jangir
 *
 */
public interface IEIPHomePageImageService {
    public List<String> getListOfImagePaths(Organisation orgid);

    public List<EIPHomeImagesHistory> getImagesList(Organisation organisation, String moduleType);

    public List<EIPHomeImages> getEIPImagesList(Organisation organisation, String moduleType, String flag);

    public EIPHomeImages getImagesDetails(long rowId);

    public void saveEIPHomeImages(EIPHomeImages entity);

    public boolean isAccessToAdd(String moduleType, Organisation organisation);

    public boolean checkExistingSequence(Long hmImgOrder, String moduleType, Organisation organisation);

    public EIPHomeImages getEIPHomeImagesDetails(long rowId, Organisation organisation);

    public void deleteHomeImages(EIPHomeImages eipHomeImages);

    public List<String> getProfileImages(String downloadPath, FileNetApplicationClient filenet, Organisation organisation,
            String moduleType) throws Exception;

    boolean checkAuthorisedkSequence(Long hmImgOrder, String moduleType, Organisation organisation);
    
    public  Set<Long> getAllImagesOrderSeqBasedOnModuleType(Organisation organisation, String notDelete, String moduleType);
    
}
