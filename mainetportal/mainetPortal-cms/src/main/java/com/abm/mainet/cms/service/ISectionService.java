/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.PortalPhotoDTO;
import com.abm.mainet.common.dto.PortalVideoDTO;

/**
 * @author Pranit.Mhatre
 * @since 17 February, 2014
 */
public interface ISectionService extends Serializable {
    /**
     * To find all the sub links list.
     * @param flag
     * @param functionId
     * @param moduleId
     * @param notDelete
     * @return {@link List} of {@link SubLinkMaster} objects.
     */

    public List<SubLinkMaster> findSublinks(String flag);

    public List<SubLinkMaster> findSublinks(String status, long moduleId, long functionId, String flag);

    public SubLinkMaster findSublinks(long subLinkId);

    public SubLinkMaster findSublinksbyename(String subLinkname);

    public List<SubLinkMaster> findSublinks(LinksMaster linksMaster);

    /**
     * To add/update section entry.
     * @param subLinkMaster the {@link SubLinkMaster} object containing section and it's element(s).
     */
    public void saveSection(SubLinkMaster subLinkMaster);

    public Long findCommiteeInfo(String prefixvalue);

    public List<SubLinkMaster> isSubLinkIsExist(SubLinkMaster linkMaster);

    public Double getMaxLinkOrderCount(long linkId);

    List<SubLinkMaster> getSerchContentList(String searchText);
    
    List<SubLinkMaster> getSerchContentCKeditorList(String searchText);

    public SubLinkMaster findSublinks(long rowId, String idformap);
    
    public SubLinkMaster findSublinks(long subLinkId, long lookupId);

    public List<PortalVideoDTO> findhomepagevideos(String videogallery);

    public List<String> getAllhtml(String CK_Editer3);
    
    public List<String> getAllhtml(final String CK_Editer3,final long langid,final long orgid);
    
    public List<String> getPhotoGallery(String photoGalleryLinkName);
    
    public List<String> getVideoGallery(String videoGalleryLinkName);

    public List<PortalPhotoDTO> findhomepagephotos(String photogallery);

    Long getNewsLetterId(String section, Organisation orgid);

    List<String> getCurrentNewsLetter(Long newsletterId, Long langid);
    
    Double getSubLinkFieldMaxOrder(Long id,Long orgid);
    
    public List<SubLinkFieldDetails> getSubLinkFieldDetails(final long subLinkMasId);
    
    public SubLinkFieldDetails getSubLinkFieldDtlObj(final long subLinkId);
    
    public StringBuilder levelOrderMenu(SubLinkMaster mas,StringBuilder subMenu,int langId);

    public List<SubLinkFieldDetailsHistory> getAllDetailHistorysByDetailId(Long detailId,String orderByColumn,Organisation organisation);
    
    public SubLinkFieldDetails getSubLinkFieldDetail(final long detailId);

}
