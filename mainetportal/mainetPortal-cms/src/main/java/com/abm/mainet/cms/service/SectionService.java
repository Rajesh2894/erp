package com.abm.mainet.cms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.ISubLinkMasterDAO;
import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.PortalPhotoDTO;
import com.abm.mainet.common.dto.PortalVideoDTO;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.utility.UtilitySupportService;

/**
 * @author Pranit.Mhatre
 * @since 17 February, 2014
 */
@Service
public class SectionService implements ISectionService {

    private static final long serialVersionUID = 2231194593635921830L;

    private static final Logger LOG = Logger.getLogger(SectionService.class);
    @Autowired
    private ISubLinkMasterDAO subLinkMasterDAO;
    
    @Autowired
    private IOrganisationDAO iOrganisationDAO;

    private final UtilitySupportService utilityService = new UtilitySupportService();

    @Override
    @Transactional
    public List<SubLinkMaster> findSublinks(String flag) {
        final List<SubLinkMaster> subLinkMasters = subLinkMasterDAO.getAllSublinkList(MainetConstants.IsDeleted.NOT_DELETE, flag);

        return subLinkMasters;

    }

    @Override
    @Transactional
    public List<SubLinkMaster> findSublinks(final String status, final long moduleId, final long functionId, String flag) {
        return subLinkMasterDAO.getAllSublinkList(moduleId, functionId, status, flag);
    }

    @Override
    @Transactional
    public SubLinkMaster findSublinks(final long subLinkId) {
        return subLinkMasterDAO.findById(subLinkId, MainetConstants.IsDeleted.NOT_DELETE);

    }

    @Override
    @Transactional
    public List<SubLinkFieldDetails> getSubLinkFieldDetails(final long subLinkMasId) {
        return subLinkMasterDAO.getSubLinkFieldDetails(subLinkMasId);
    }
    
    @Override
    @Transactional
    public SubLinkFieldDetails getSubLinkFieldDtlObj(final long subLinkId) {
        return subLinkMasterDAO.getSubLinkFieldDtlsObj(subLinkId);
    }

    @Override
    public SubLinkMaster findSublinksbyename(final String subLinkname) {
        return subLinkMasterDAO.findSublinksbyename(subLinkname, MainetConstants.IsDeleted.NOT_DELETE);

    }

    @Override
    @Transactional
    public List<SubLinkMaster> findSublinks(final LinksMaster linksMaster) {
        return subLinkMasterDAO.getAllSublinkList(linksMaster, MainetConstants.IsDeleted.NOT_DELETE);
    }

    @SuppressWarnings("unused")
    @Override
    @Transactional
    public void saveSection(final SubLinkMaster subLinkMaster) {
    	
    	List<SubLinkFieldMapping> sortedLinkField = subLinkMaster.getSubLinkFieldMappings().stream().sorted(Comparator.comparing(SubLinkFieldMapping::getId)).collect(Collectors.toList());
        subLinkMaster.setSubLinkFieldMappings(sortedLinkField);
        
        for (final SubLinkFieldMapping sb1 : subLinkMaster.getSubLinkFieldMappings()) {
            if (sb1.getFieldType() == 6) {
                sb1.setFiledNameMap("ATT_VIDEO_PATH");
            }
        }

        subLinkMaster.updateAuditFields();

        setFieldMappingName(subLinkMaster.getSubLinkFieldMappings());
        int count = 0;
        final List<Long> NodeList = new ArrayList<>();
        if (subLinkMaster.getSectionType0() != 0) {
            NodeList.add(subLinkMaster.getSectionType0());
        }
        if ((subLinkMaster.getSectionType1() != null) && (subLinkMaster.getSectionType1() != 0)) {
            NodeList.add(subLinkMaster.getSectionType1());
        }
        if ((subLinkMaster.getSectionType2() != null) && (subLinkMaster.getSectionType2() != 0)) {
            NodeList.add(subLinkMaster.getSectionType2());
        }
        if ((subLinkMaster.getSectionType3() != null) && (subLinkMaster.getSectionType3() != 0)) {
            NodeList.add(subLinkMaster.getSectionType3());
        }
        if ((subLinkMaster.getSectionType4() != null) && (subLinkMaster.getSectionType4() != 0)) {
            NodeList.add(subLinkMaster.getSectionType4());
        }

        if (NodeList.size() == subLinkMaster.getSubLinkFieldMappings().size()) {
            for (final SubLinkFieldMapping subLinkFieldMapping : subLinkMaster.getSubLinkFieldMappings()) {
                if (NodeList.size() >= count) {
                    subLinkMaster.getSubLinkFieldMappings().get(count).setSectionType(NodeList.get(count));
                }
                subLinkMaster.getSubLinkFieldMappings().get(count).setSubLinkMaster(subLinkMaster);
                count++;
            }
        }
        if (NodeList.size() < subLinkMaster.getSubLinkFieldMappings().size()) {
            for (final SubLinkFieldMapping subLinkFieldMapping : subLinkMaster.getSubLinkFieldMappings()) {
                subLinkMaster.getSubLinkFieldMappings().get(count).setSectionType(NodeList.get(0));
                subLinkMaster.getSubLinkFieldMappings().get(count).setSubLinkMaster(subLinkMaster);
                count++;
            }
        }
        subLinkMasterDAO.save(subLinkMaster);

    }

    /**
     * To set mapping filed text. e.g. TXT_01,TXTA_01
     * @param mapping the {@link SubLinkFieldMapping} object whose mapping text to be set.
     * @param count the int literal containing current count for type of field.
     */
    private void setFieldMap(final SubLinkFieldMapping mapping, final int count) {
        final int fieldType = mapping.getFieldType();

        switch (fieldType) {
        case MainetConstants.TEXT_FIELD:
            if (count >= 10) {
                mapping.setFiledNameMap("TXT_" + count);
            } else {
                mapping.setFiledNameMap("TXT_0" + count);
            }
            break;
        case MainetConstants.TEXT_AREA:
            mapping.setFiledNameMap("TXTA_0" + count);
            break;
        case MainetConstants.DROP_DOWN_BOX:
        	if (count >= 10) {
        	mapping.setFiledNameMap("DD_" + count);
        	} else {
        	mapping.setFiledNameMap("DD_0" + count);
        	}
            break;
        case MainetConstants.DATE_PICKER:
            mapping.setFiledNameMap("DATE_0" + count);
            break;
        case MainetConstants.ATTACHMENT_FIELD:
            mapping.setFiledNameMap("ATT_0" + count);
            break;
        case MainetConstants.PROFILE_IMG:
            mapping.setFiledNameMap("PROFILE_IMG_PATH");
            break;
        case MainetConstants.TEXT_AREA_HTML:
            mapping.setFiledNameMap("TEXT_AREA_HTML");
            break;
        case MainetConstants.LINK_FIELD:
                 mapping.setFiledNameMap("LINK_0" + count);
            break;
            

        default:
            break;
        }
    }

    /**
     * Set/Update data for the list of {@link SubLinkFieldMapping}.
     * @param fieldMappings the {@link List} of {@link SubLinkFieldMapping} objects.
     */
    private void setFieldMappingName(final List<SubLinkFieldMapping> fieldMappings) {
        int cntTF = 0;
        int cntTA = 0;
        int cntDD = 0;
        int cntDP = 0;
        int cntAT = 0;
        int cntPI = 0;
        int cntV = 0;
        int cntHT = 0;
        int cntLINK =0;
        /* PROFILE_IMG_PATH */
        int tempFieldCount = 0;

        int sequence = 0;

        final ListIterator<SubLinkFieldMapping> listIterator = fieldMappings.listIterator();
        while (listIterator.hasNext()) {
            final SubLinkFieldMapping mapping = listIterator.next();

            mapping.updateAuditFields();

            if (mapping.getId() == 0) {
                mapping.setFieldSequence(++sequence);
            }

            if (mapping.getIsMandatory() == null) {
                mapping.setIsMandatory(MainetConstants.IsDeleted.NOT_DELETE);
            }

            if (mapping.getIsUsed().equals(MainetConstants.IsDeleted.DELETE)) {
                if (mapping.getFieldType() == MainetConstants.TEXT_FIELD) {
                    cntTF++;
                    tempFieldCount = cntTF;
                } else if (mapping.getFieldType() == MainetConstants.TEXT_AREA) {
                    cntTA++;
                    tempFieldCount = cntTA;
                } else if (mapping.getFieldType() == MainetConstants.DROP_DOWN_BOX) {
                    cntDD++;
                    tempFieldCount = cntDD;
                } else if (mapping.getFieldType() == MainetConstants.DATE_PICKER) {
                    cntDP++;
                    tempFieldCount = cntDP;
                } else if (mapping.getFieldType() == MainetConstants.ATTACHMENT_FIELD) {
                    cntAT++;
                    tempFieldCount = cntAT;
                } else if (mapping.getFieldType() == MainetConstants.PROFILE_IMG) {
                    cntPI++;
                    tempFieldCount = cntPI;
                } else if (mapping.getFieldType() == MainetConstants.VIDEO) {
                    cntV++;
                    tempFieldCount = cntV;
                }

                else if (mapping.getFieldType() == MainetConstants.TEXT_AREA_HTML) {
                    cntHT++;
                    tempFieldCount = cntHT;
                }else if (mapping.getFieldType() == MainetConstants.LINK_FIELD) {
                	cntLINK++;
                    tempFieldCount = cntLINK;
                }

                setFieldMap(mapping, tempFieldCount);

                if (mapping.getId() != 0) {
                    sequence = mapping.getFieldSequence();
                }
            }

        }
    }

    @Transactional
    @Override
    public Long findCommiteeInfo(final String prefix) {

        return subLinkMasterDAO.getCommitteInfo(prefix);
    }

    @Override
    @Transactional
    public List<SubLinkMaster> isSubLinkIsExist(final SubLinkMaster linksMaster) {

        return subLinkMasterDAO.isSubLinkIsExist(linksMaster);
    }

    @Override
    @Transactional
    public Double getMaxLinkOrderCount(final long linkId) {

        return subLinkMasterDAO.getMaxLinkOrderCount(linkId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubLinkMaster> getSerchContentList(final String searchText) {

        return subLinkMasterDAO.getSerchContentList(searchText);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SubLinkMaster> getSerchContentCKeditorList(final String searchText) {

        return subLinkMasterDAO.getSerchContentCKeditorList(searchText);
    }

    @Override
    @Transactional
    public SubLinkMaster findSublinks(final long subLinkId, final String idformap) {

        return subLinkMasterDAO.findBymapId(subLinkId, idformap, MainetConstants.IsDeleted.NOT_DELETE);
    }

    @Override
    @Transactional
    public List<PortalPhotoDTO> findhomepagephotos(final String homepagephotos) {
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
                MainetConstants.DirectoryTree.QUICK_LINK;
        File file = null;
        FileOutputStream fos = null;
        String[] imgpath = null;
        PortalPhotoDTO photo = null;
        String filepath = null;
        final List<PortalPhotoDTO> PortalPhoto = new ArrayList<>();
        final List<Object[]> portalPhotoList = subLinkMasterDAO
                .findhomepagephotos(UserSession.getCurrent().getOrganisation().getOrgid(), homepagephotos);
        if ((portalPhotoList != null) && !portalPhotoList.isEmpty()) {
            for (final Object photoes[] : portalPhotoList) {
                if (photoes != null) {
                    photo = new PortalPhotoDTO();
                    imgpath = photoes[0].toString().split(MainetConstants.operator.COMA);
                    String finString = MainetConstants.operator.EMPTY;
                    new LookUp();
                    if (imgpath != null) {
                        for (int i = 0; i < imgpath.length; i++) {
                            if (!imgpath[i].isEmpty()) {
                                final String fileName = StringUtility
                                        .staticStringAfterChar(MainetConstants.operator.FORWARD_SLACE, imgpath[i]);

                                if ((fileName != null) && (fileName.length() > 0)) {
                                    String directoryPath = StringUtility
                                            .staticStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, imgpath[i]);

                                    directoryPath = directoryPath.replace(MainetConstants.operator.FORWARD_SLACE,
                                            MainetConstants.operator.COMA);

                                    final byte[] image = utilityService.getFile(fileName, directoryPath);

                                    filepath = outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName;
                                    Utility.createDirectory(
                                            Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);
                                    file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR
                                            + fileName);

                                    if (finString.length() == 0) {
                                        finString = file.getName();
                                    } else {
                                        finString += MainetConstants.operator.ORR + file.getName();
                                    }

                                    if (image != null) {
                                        try {
                                            fos = new FileOutputStream(file);
                                            fos.write(image);
                                            fos.close();
                                        } catch (final IOException e) {

                                            LOG.error(MainetConstants.FINDHOMEPAGE_PHOTOS_ERROR, e);

                                        }

                                    }

                                }
                            }
                        }
                    }

                    photo.setImagePath(
                            filepath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.FORWARD_SLACE));
                    photo.setCaption(setCaptions(photoes));

                    PortalPhoto.add(photo);
                }

            }
        }

        return PortalPhoto;
    }

    @Override
    public List<PortalVideoDTO> findhomepagevideos(final String videogallery) {

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
                MainetConstants.DirectoryTree.QUICK_LINK;
        File file = null;
        FileOutputStream fos = null;
        FileOutputStream fos1=null;
        String[] imgpath = null;
        PortalVideoDTO video = null;
       
        String filepath = null;
        final List<PortalVideoDTO> portalVideo = new ArrayList<>();
        final List<Object[]> portalVideoList = subLinkMasterDAO
                .findhomepagevideos(UserSession.getCurrent().getOrganisation().getOrgid(), videogallery);
        if ((portalVideoList != null) && !portalVideoList.isEmpty()) {
            for (final Object[] videos : portalVideoList) {
            	String subtitle=null;
                video = new PortalVideoDTO();
                videos[0].toString().indexOf('/');
                imgpath = videos[0].toString().split(MainetConstants.operator.COMA);
                String finString = MainetConstants.operator.EMPTY;
                new LookUp();
                if (imgpath != null) {
                    for (int i = 0; i < imgpath.length; i++) {
                        if (!imgpath[i].isEmpty()) {
                            final String fileName = StringUtility.staticStringAfterChar(MainetConstants.operator.FORWARD_SLACE,
                                    imgpath[i]);

                            if ((fileName != null) && (fileName.length() > 0)) {
                                String directoryPath = StringUtility
                                        .staticStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, imgpath[i]);

                                directoryPath = directoryPath.replace(MainetConstants.operator.FORWARD_SLACE,
                                        MainetConstants.operator.COMA);

                                final byte[] image = utilityService.getFile(fileName, directoryPath);

                                filepath = outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName;
                                Utility.createDirectory(
                                        Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);
                                file = new File(
                                        Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);
                                video.setFileSize((file.length() / 1024) + " KB");
                                video.setFileType(fileName);
                                
                                final String filePath2 = (String) videos[3];
                                byte[] image1=null;
                                File file1=null;
                                if((filePath2 != null) && (filePath2.length()>0)){
                               String[] imgpath2 = filePath2.split(MainetConstants.operator.COMA);
                                
                                final String fileName1 = StringUtility
                                        .staticStringAfterChar(MainetConstants.WINDOWS_SLASH, imgpath2[0]);
                                 image1 =  FileNetApplicationClient.getInstance().getFileByte(fileName1, directoryPath);
                                file1=new File(Filepaths.getfilepath()+outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName1);
                                subtitle = outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName1;
                                }
                                
                                if (finString.length() == 0) {
                                    finString = file.getName();
                                } else {
                                    finString += "|" + file.getName();
                                }

                                if (image != null) {
                                    try {
                                        fos = new FileOutputStream(file);
                                        fos.write(image);
                                        fos.close();
                                    } catch (final IOException e) {

                                        LOG.error(MainetConstants.FINDHOMEPAGE_VIDEOS_ERROR, e);

                                    }
                                }
                                    if (image1 != null) {
                                        try {
                                            fos1=new FileOutputStream(file1);
                                            fos1.write(image1);
                                            fos1.close();
                                        } catch (final IOException e) {
                                            LOG.error(MainetConstants.FINDHOMEPAGE_VIDEOS_ERROR, e);
                                        }
                                }

                            }
                        }
                    }
                }

                video.setImagePath(
                        filepath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.FORWARD_SLACE));
                video.setCaption(setCaptions(videos));
                video.setFileSize(video.getFileSize());
                video.setFileType(video.getFileType());
                if(subtitle!=null) {
                video.setSubtitlePath(subtitle.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.FORWARD_SLACE));
                }
                portalVideo.add(video);

            }
        }

        return portalVideo;
    }

    private String setCaptions(Object[] videos) {

        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            return videos[1] != null ? videos[1].toString() : MainetConstants.BLANK;
        } else {
            return videos[2] != null ? videos[2].toString() : MainetConstants.BLANK;
        }
    }

    @Override
    public List<String> getAllhtml(final String CK_Editer3) {

        final long langid = UserSession.getCurrent().getLanguageId();
        final List<String> s = new ArrayList<>();
        final List<SubLinkFieldDetailsHistory> Allhtml = subLinkMasterDAO
                .getAllhtml(UserSession.getCurrent().getOrganisation().getOrgid(), CK_Editer3);
        if (Allhtml != null) {
            for (final SubLinkFieldDetailsHistory subLinkFieldDetails : Allhtml) {
                if (langid == 1) {
                    s.add(subLinkFieldDetails.getTxta_03_ren_blob());
                } else {
                    s.add(subLinkFieldDetails.getTxta_03_en_nnclob());
                }
            }
            return s;
        } else {
            return null;
        }
    }
    
    @Override
    public List<String> getAllhtml(final String CK_Editer3,final long langid,final long orgid) {

    	Organisation organisation = iOrganisationDAO.getOrganisationById(orgid, MainetConstants.STATUS.ACTIVE);
        final List<String> s = new ArrayList<>();
        final List<SubLinkFieldDetailsHistory> Allhtml = subLinkMasterDAO
                .getAllhtmlByOrgId(organisation, CK_Editer3);
        if (Allhtml != null) {
            for (final SubLinkFieldDetailsHistory subLinkFieldDetails : Allhtml) {
                if (langid == 1) {
                    s.add(subLinkFieldDetails.getTxta_03_ren_blob());
                } else {
                    s.add(subLinkFieldDetails.getTxta_03_en_nnclob());
                }
            }
            return s;
        } else {
            return null;
        }
    }
    
    @Override
    public List<String> getPhotoGallery(final String photoGalleryLinkName) {

        final long langid = UserSession.getCurrent().getLanguageId();
        final List<String> s = new ArrayList<>();
        final List<SubLinkFieldDetailsHistory> Allhtml = subLinkMasterDAO
                .getAllhtml(UserSession.getCurrent().getOrganisation().getOrgid(), photoGalleryLinkName);
        if (Allhtml != null) {
            for (final SubLinkFieldDetailsHistory subLinkFieldDetails : Allhtml) {
                s.add(subLinkFieldDetails.getProfile_img_path());
            }
            return s;
        } else {
            return null;
        }
    }
    
    @Override
    public List<String> getVideoGallery(final String videoGalleryLinkName) {

        final long langid = UserSession.getCurrent().getLanguageId();
        final List<String> s = new ArrayList<>();
        final List<SubLinkFieldDetailsHistory> Allhtml = subLinkMasterDAO
                .getAllhtml(UserSession.getCurrent().getOrganisation().getOrgid(), videoGalleryLinkName);
        if (Allhtml != null) {
            for (final SubLinkFieldDetailsHistory subLinkFieldDetails : Allhtml) {
            	s.add(subLinkFieldDetails.getAtt_video_path());
            }
            return s;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCurrentNewsLetter(final Long newsletterId, Long langid) {

        final List<String> s = new ArrayList<>();
        final List<SubLinkFieldDetails> Allhtml = subLinkMasterDAO
                .getNewsLetter(newsletterId);
        if (Allhtml != null) {
            for (final SubLinkFieldDetails subLinkFieldDetails : Allhtml) {
                if (langid == 1L) {
                    s.add(subLinkFieldDetails.getTxta_03_ren_blob());
                } else {
                    s.add(subLinkFieldDetails.getTxta_03_en_nnclob());
                }
            }
            return s;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNewsLetterId(final String section, Organisation orgid) {

        return subLinkMasterDAO.getNewsLetterId(orgid, section);
    }

	@Override
	public Double getSubLinkFieldMaxOrder(Long id,Long orgid) {
		
		return  subLinkMasterDAO.getSubLinkFieldMaxOrder(id,orgid,MainetConstants.IsDeleted.NOT_DELETE);
	}

	@Override
	public SubLinkMaster findSublinks(long subLinkId, long lookupId) {
		return subLinkMasterDAO.findById(subLinkId, MainetConstants.IsDeleted.NOT_DELETE, lookupId);
	}
	
	@Override
	public StringBuilder levelOrderMenu(SubLinkMaster mas, StringBuilder subMenu, int langId) {

        
    	if(mas.getSubLinkMaster()!=null) {    		
    		levelOrderMenu(mas.getSubLinkMaster(),subMenu,langId);
    	}
    	if(langId == MainetConstants.ENGLISH) {
    		subMenu.append(MainetConstants.MENU_ARROW+mas.getSubLinkNameEn());
    	}else{
    		subMenu.append(MainetConstants.MENU_ARROW+mas.getSubLinkNameRg());
    	}
    	
    	return subMenu;

    	
    
	}

	@Override
	@Transactional
	public List<SubLinkFieldDetailsHistory> getAllDetailHistorysByDetailId(Long detailId, String orderByColumn,
			Organisation organisation) {
	return	subLinkMasterDAO.getAllDetailHistorysByDetailId(detailId, orderByColumn, organisation);
	}

	@Override
	@Transactional
	public SubLinkFieldDetails getSubLinkFieldDetail(long detailId) {
		
		return subLinkMasterDAO.getSubLinkFieldDetail(detailId);
	}

}
