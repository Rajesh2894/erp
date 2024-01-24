package com.abm.mainet.cms.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IEIPHomeImagesDAO;
import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.cms.domain.EIPHomeImagesHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;

/**
 * @author vinay.jangir
 *
 */

@Service
public class EIPHomePageImageService implements IEIPHomePageImageService {

    @Autowired
    private IEIPHomeImagesDAO EIPHomeImagesDAO;

    @Override
    @Transactional
    public List<String> getListOfImagePaths(final Organisation orgid) {
        return EIPHomeImagesDAO.fetchImgPathsOnOrgId(orgid);
    }

    @Override
    @Transactional
    public List<EIPHomeImagesHistory> getImagesList(final Organisation organisation, final String moduleType) {

        return EIPHomeImagesDAO.getAllImagesListhomepage(organisation, MainetConstants.IsDeleted.NOT_DELETE, moduleType);
    }

    @Override
    @Transactional
    public List<EIPHomeImages> getEIPImagesList(final Organisation organisation, final String moduleType,final String flag) {

        return EIPHomeImagesDAO.getAllImagesList(organisation, MainetConstants.IsDeleted.NOT_DELETE, moduleType,flag);
    }

    @Override
    @Transactional
    public EIPHomeImages getImagesDetails(final long rowId) {

        return EIPHomeImagesDAO.getImagesByID(rowId);

    }

    @Override
    @Transactional
    public void saveEIPHomeImages(final EIPHomeImages entity) {

        EIPHomeImagesDAO.persistEIPHomeImageObj(entity);
    }

    @Override
    @Transactional
    public boolean isAccessToAdd(final String moduleType, final Organisation organisation) {

        return EIPHomeImagesDAO.isAccess(organisation, MainetConstants.IsDeleted.NOT_DELETE, moduleType);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistingSequence(final Long hmImgOrder, final String moduleType, final Organisation organisation) {

        return EIPHomeImagesDAO.checkExistingSequence(hmImgOrder, moduleType, organisation);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkAuthorisedkSequence(final Long hmImgOrder, final String moduleType, final Organisation organisation) {

        return EIPHomeImagesDAO.checkAuthorisedkSequence(hmImgOrder, moduleType, organisation);
    }

    @Override
    @Transactional
    public EIPHomeImages getEIPHomeImagesDetails(final long rowId, final Organisation organisation) {

        return EIPHomeImagesDAO.gettEIPHomeImagesMaster(rowId, organisation, MainetConstants.IsDeleted.NOT_DELETE);
    }

    @Override
    @Transactional
    public void deleteHomeImages(final EIPHomeImages eipHomeImages) {
        eipHomeImages.setIsDeleted(MainetConstants.IsDeleted.DELETE);

        EIPHomeImagesDAO.saveSchemeMaster(eipHomeImages);

    }

    @Override
    @Transactional
    public List<String> getProfileImages(final String downloadPath, final FileNetApplicationClient filenet,
            final Organisation organisation, final String moduleType) throws Exception {

        final List<String> lookUp = new ArrayList<>();
        final List<EIPHomeImagesHistory> list = getImagesList(organisation, moduleType);
        if ((list != null) && !list.isEmpty()) {
            final Iterator<EIPHomeImagesHistory> iterator = list.iterator();
            while (iterator.hasNext()) {

                final EIPHomeImagesHistory eipHomeImages = iterator.next();
                String data = Utility.downloadedFileUrl(eipHomeImages.getImagePath(), downloadPath, filenet);
                if (!data.isEmpty()) {
                    if (moduleType.equals(MainetConstants.FlagL)) {
                        data = data + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK + eipHomeImages.getHmImgOrder();
                    }

                    lookUp.add(data + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK
                            + removeNullCaption(eipHomeImages.getCaption()) + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK
                            + removeNullCaption(eipHomeImages.getCaptionReg()));
                }

            }
        }

        return lookUp;
    }

    private String removeNullCaption(String caption) {
        return caption != null
                ? caption
                : "";
    }

	@Override
	public Set<Long> getAllImagesOrderSeqBasedOnModuleType(Organisation organisation, String notDelete,
			String moduleType) {
		return EIPHomeImagesDAO.getAllImagesOrderSeqBasedOnModuleType(organisation, notDelete, moduleType);
	}

}
