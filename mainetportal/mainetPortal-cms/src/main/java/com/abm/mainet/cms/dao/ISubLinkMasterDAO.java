package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.dao.IAbstractDAO;
import com.abm.mainet.common.domain.Organisation;

public interface ISubLinkMasterDAO extends IAbstractDAO<SubLinkMaster> {

    public abstract List<SubLinkMaster> getAllSublinkList(String activeStatus, String flag);

    public abstract List<SubLinkMaster> getAllSublinkList(long moduleId, long functionId,
            String activeStatus, String flag);

    public abstract List<SubLinkMaster> getAllSublinkList(LinksMaster linksMaster,
            String activeStatus);

    /**
     * @param subLinkId
     * @param activeStatus
     * @return
     */
    public abstract SubLinkMaster findById(long subLinkId, String activeStatus);
    
    public abstract SubLinkMaster findById(long subLinkId, String activeStatus, long lookupId);
	 public List<SubLinkFieldDetails> getSubLinkFieldDetails(Long subLinkMasId);
    
     public SubLinkFieldDetails getSubLinkFieldDtlsObj(Long subLinkMasId);

    public abstract SubLinkMaster findSublinksbyename(String subLinkname, String activeStatus);

    public abstract Long getCommitteInfo(String prefix);

    public abstract List<SubLinkMaster> isSubLinkIsExist(SubLinkMaster sublinksMaster);

    public abstract Double getMaxLinkOrderCount(long linkId);

    List<SubLinkMaster> getSerchContentList(String searchText);
    
    List<SubLinkMaster> getSerchContentCKeditorList(String searchText);
    
    public abstract SubLinkMaster findBymapId(long subLinkId, String idformap, String notDelete);

    public boolean save(SubLinkMaster linkMaster);

    public List<Object[]> findhomepagephotos(long orgId, String homepagephotos);

    public List<Object[]> findhomepagevideos(long orgId, String videogallery);

    public List<SubLinkFieldDetailsHistory> getAllhtml(long orgId, String CK_Editer3);
    
    public List<SubLinkFieldDetailsHistory> getAllhtmlByOrgId(Organisation organisation, String CK_Editer3);
    

    List<SubLinkFieldDetails> getNewsLetter(Long newsletterId);

    Long getNewsLetterId(Organisation orgId, String section);

	public abstract Double getSubLinkFieldMaxOrder(Long id, Long orgid, String zero);
	
	public List<SubLinkFieldDetailsHistory> getAllDetailHistorysByDetailId(Long detailId,String orderByColumn,Organisation organisation);

	public SubLinkFieldDetails getSubLinkFieldDetail(Long detailId);
}