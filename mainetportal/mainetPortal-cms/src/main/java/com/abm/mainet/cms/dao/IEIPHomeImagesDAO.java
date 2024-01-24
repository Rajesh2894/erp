package com.abm.mainet.cms.dao;

import java.util.List;
import java.util.Set;

import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.cms.domain.EIPHomeImagesHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IEIPHomeImagesDAO {

    public abstract List<String> fetchImgPathsOnOrgId(Organisation orgid);

    public abstract boolean persistEIPHomeImageObj(EIPHomeImages eipHomeImages);

    public abstract List<EIPHomeImages> getAllImagesList(Organisation organisation, String notDelete, String moduleType, String flag);

    public abstract List<EIPHomeImagesHistory> getAllImagesListhomepage(Organisation organisation, String notDelete, String moduleType);

    public abstract EIPHomeImages getImagesByID(long rowId);

    public abstract boolean isAccess(Organisation organisation, String notDelete, String moduleType);

    public abstract boolean checkExistingSequence(Long hmImgOrder, String moduleType, Organisation organisation);

    public abstract EIPHomeImages gettEIPHomeImagesMaster(long rowId, Organisation organisation,
            String notDelete);

    public abstract void saveSchemeMaster(EIPHomeImages eipHomeImages);

    boolean checkAuthorisedkSequence(Long hmImgOrder, String moduleType, Organisation organisation);
    
    public abstract Set<Long> getAllImagesOrderSeqBasedOnModuleType(Organisation organisation, String notDelete, String moduleType);
}