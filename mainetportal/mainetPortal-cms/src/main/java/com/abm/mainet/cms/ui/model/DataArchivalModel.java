package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.service.IDataArchivalService;
import com.abm.mainet.cms.service.IEipAnnouncementService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Component
public class DataArchivalModel extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<PublicNotices> publicNotices = new ArrayList<>(0);
    private static final Logger LOG = Logger.getLogger(DataArchivalModel.class);
    @Autowired
    IDataArchivalService dataArchivalService;

    @Autowired
    IEipAnnouncementService iEipAnnouncementService;

    private boolean isHighlighted;
    private boolean isUsefull;
    private boolean isScheme;
    private String[] imgpath;
    SubLinkMaster master = new SubLinkMaster();
    private List<EIPAnnouncementHistory> eipAnnouncement = new ArrayList<>(0);

    public List<PublicNotices> getPublicNotices() {
        return publicNotices;
    }

    public void setPublicNotices(List<PublicNotices> publicNotices) {
        this.publicNotices = publicNotices;
    }

    public void getPublicNotice() {
        int highlightCount = 0;
        int schemeCount = 0;
        int usefulLinkCount = 0;
        publicNotices = dataArchivalService.getArchivedDataForPublicNotice(UserSession.getCurrent().getOrganisation());
        String FileName = null;
        for (final PublicNotices publicNotice : publicNotices) {
            final String attachmentPath = publicNotice.getProfileImgPath();
            FileName = StringUtility.staticStringAfterChar(File.separator, attachmentPath);
            publicNotice.setAttachmentName(FileName);
        }

        final ListIterator<PublicNotices> listIterator = publicNotices.listIterator();
        PublicNotices publicNotices = null;
        String[] pathArr = null;
        String directoryTree = null;
        FileStorageCache cache = null;
        while (listIterator.hasNext()) {
            publicNotices = listIterator.next();

            directoryTree = publicNotices.getProfileImgPath();

            publicNotices.setImagePath(downloadNewsImages(publicNotices));

            if (directoryTree != null) {
                pathArr = directoryTree.split(MainetConstants.operator.COMA);

                directoryTree = (pathArr.length > 0) ? pathArr[0] : directoryTree;

                directoryTree = StringUtility.staticStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, directoryTree);

                directoryTree = directoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
            }

            cache = publicNotices.getFileStorageCache();

            try {
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                    getFileNetClient().uploadFileList(cache.getFileList(), directoryTree);
                }
            } catch (final Exception ex) {
                throw new FrameworkException(ex.getLocalizedMessage());
            } finally {
                cache.flush();
            }

            if (publicNotices.getIsHighlighted() != null
                    && publicNotices.getIsHighlighted().equalsIgnoreCase(MainetConstants.YES)) {
                highlightCount++;//Important Link
            } else if (publicNotices.getIsUsefullLink() != null
                    && publicNotices.getIsUsefullLink().equalsIgnoreCase(MainetConstants.YES)) {
                usefulLinkCount++;//New Link
            } else {
                schemeCount++;
            }
        }

        if (highlightCount > 0) {
            setHighlighted(true);

        } else {

            setHighlighted(false);
        }
        if (schemeCount > 0) {
            setScheme(true);
        } else {
            setScheme(false);

        }
        if (usefulLinkCount > 0) {
            setUsefull(true);
        } else {
            setUsefull(false);
        }
    }

    private String downloadNewsImages(PublicNotices newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.NEWS_IMAGES;
        if (newsImages.getImagePath() != null) {
            try {
                return Utility.downloadedFileUrl(newsImages.getImagePath(), outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }

    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public boolean isUsefull() {
        return isUsefull;
    }

    public void setUsefull(boolean isUsefull) {
        this.isUsefull = isUsefull;
    }

    public boolean isScheme() {
        return isScheme;
    }

    public void setScheme(boolean isScheme) {
        this.isScheme = isScheme;
    }
    
    public List<LookUp> getArchiveSection() {
    	return	dataArchivalService.getArchiveSectionName(UserSession.getCurrent().getOrganisation(),UserSession.getCurrent().getLanguageId());
    			//.stream().sorted((name1, name2) -> name1.compareTo(name2)).collect(Collectors.toList()); 
    }
    
     public List<List<LookUp>> getSectionInformation(long sectionId) {
     try {

            this.setMaster(dataArchivalService.getArchivedSections(sectionId));

            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                    UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
                    getMaster().getLinksMaster().getLinkTitleEg().trim() + MainetConstants.FILE_PATH_SEPARATOR +
                    getMaster().getSubLinkNameEn().trim();

            final List<List<LookUp>> dataList = new ArrayList<>();

            String fieldLabel;
            int fieldType=0;
            final List<KeyValue> fields = new ArrayList<>(0);

            for (final SubLinkFieldMapping temp : getMaster().getSubLinkFieldMappings()) {
                if (MainetConstants.IsDeleted.DELETE.equals(temp.getIsUsed())
                        && MainetConstants.IsDeleted.NOT_DELETE.equals(temp.getIsDeleted())) {


                    if (getUserSession().getLanguageId() == MainetConstants.ENGLISH) {

                        fieldLabel = temp.getFieldNameEn();
                    } else {
                        fieldLabel = temp.getFieldNameRg();
                    }
                    
                    if(temp.getFieldType()==MainetConstants.TEXT_AREA_HTML) {
                    	fields.clear();
                    fields.add(this.new KeyValue(temp.getFieldType(), temp.getFiledNameMap().toLowerCase(), fieldLabel,
                            temp.getSectionType()));
                        break;
                }
                    
                    fields.add(this.new KeyValue(temp.getFieldType(), temp.getFiledNameMap().toLowerCase(), fieldLabel,
                            temp.getSectionType()));
            }
            }

            if (getUserSession().getLanguageId() == MainetConstants.ENGLISH) {
            	fields.add(this.new KeyValue(MainetConstants.VALIDITY_DATE_CODE, MainetConstants.VALIDITY_DATE, ApplicationSession.getInstance().getMessage("portal.archivaldate"),MainetConstants.VALIDITY_DATE_CODE));
            } else {
            	fields.add(this.new KeyValue(MainetConstants.VALIDITY_DATE_CODE, MainetConstants.VALIDITY_DATE, ApplicationSession.getInstance().getMessage("portal.archivaldate"),MainetConstants.VALIDITY_DATE_CODE));
            }
            
            // Check whether profile image has been uploaded or not.
            final boolean hasPhoto = hasProfileImage(fields);
            // Check whether documents has been uploaded or not.
            final boolean hasDocs = hasDownloadDocs(fields);
            int count = 0;
            List<LookUp> lookUps = null;
            for (final SubLinkFieldDetails details : getMaster().getSubLinkFieldDetails()) {
                if (MainetConstants.IsDeleted.NOT_DELETE.equals(details.getIsDeleted())) {
                    lookUps = new ArrayList<>();
                    LookUp lookUp = null;
                    // To add details of text_filed,text_area and date picker only.
                    for (final KeyValue keyValue : fields) {
                        String filepath1 = null;
                        if (keyValue.getKey() == MainetConstants.VALIDITY_DATE_CODE) {
                            lookUp = new LookUp();
                            DateFormat format = new SimpleDateFormat(MainetConstants.FORMAT_DDMMYY_HHMMSS_A);
                            String date =format.format(details.getValidityDate());
							
                            lookUp.setDescLangFirst(String.valueOf(date));
                            lookUp.setDescLangSecond(String.valueOf(date));
                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongTwo(count);
                            lookUps.add(lookUp);

                        }
                        if ((keyValue.getKey() == MainetConstants.TEXT_FIELD)
                                || (keyValue.getKey() == MainetConstants.TEXT_AREA)) {
                            lookUp = new LookUp();

                            lookUp.setDescLangFirst(String.valueOf(getPropertyVal(keyValue.getValue() + "_en", details)));
                            lookUp.setDescLangSecond(String.valueOf(getPropertyVal(keyValue.getValue() + "_rg", details)));
                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongTwo(count);
                            lookUps.add(lookUp);

                        }

                        if (keyValue.getKey() == MainetConstants.TEXT_AREA_HTML) {
                            lookUp = new LookUp();
                            String str = null;
                            str = new String(details.getTxta_03_ren_blob());

                            final String s1 = MainetConstants.PARENT_PATH;
                            final String s3 = MainetConstants.CURRENT_PATH;
                            if (str.contains(s1)) {
                            }
                            final String str2 = str.replace(s1, MainetConstants.CHILD_PATH);
                            final String str3 = str2.replace(s3, MainetConstants.BLANK);

                            lookUp.setDescLangFirst(str3);
                            // Setting hindi data
                            if (null != details.getTxta_03_en_nnclob() && !details.getTxta_03_en_nnclob().isEmpty()) {
                                String hindiData = details.getTxta_03_en_nnclob();
                                hindiData.replace(s1, MainetConstants.CHILD_PATH);
                                hindiData.replace(s3, MainetConstants.BLANK);
                                lookUp.setDescLangSecond(hindiData);
                            }

                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUp.setLookUpExtraLongTwo(count);
                            lookUps.add(lookUp);
                        }
                        if (keyValue.getKey() == MainetConstants.DATE_PICKER) {
                            lookUp = new LookUp(details.getId(), String.valueOf(getPropertyVal(keyValue.getValue(), details)));
                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                            lookUps.add(lookUp);
                        }
                        if (hasPhoto) {
                            count = 0;
                            File file = null;
                            FileOutputStream fos = null;
                            if ((keyValue.getKey() == MainetConstants.PROFILE_IMG)
                                    || (keyValue.getKey() == MainetConstants.VIDEO)) {
                                final String filePath = String.valueOf(getPropertyVal(keyValue.getValue(), details));
                                imgpath = filePath.split(MainetConstants.operator.COMA);
                                String finString = MainetConstants.BLANK;

                                if (imgpath != null) {
                                    for (int i = 0; i < imgpath.length; i++) {
                                        lookUp = new LookUp();
                                        if (!imgpath[i].isEmpty()) {
                                            final String fileName = StringUtility
                                                    .staticStringAfterChar(MainetConstants.WINDOWS_SLASH, imgpath[i]);

                                            if ((fileName != null) && (fileName.length() > 0)) {
                                                String directoryPath = StringUtility
                                                        .staticStringBeforeChar(MainetConstants.WINDOWS_SLASH, imgpath[i]);

                                                directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                        MainetConstants.operator.COMA);

                                                final byte[] image = getFileNetClient().getFileByte(fileName, directoryPath);

                                                filepath1 = outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName;
                                                Utility.createDirectory(Filepaths.getfilepath() + outputPath
                                                        + MainetConstants.FILE_PATH_SEPARATOR);
                                                file = new File(Filepaths.getfilepath() + outputPath
                                                        + MainetConstants.FILE_PATH_SEPARATOR + fileName);

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
                                                    } catch (final Exception ex) {
                                                        LOG.error("Error while reading value from response: ", ex);
                                                    }
                                                }

                                            }
                                            if (filepath1 != null) {
                                                lookUp.setLookUpDesc(filepath1.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                        MainetConstants.WINDOWS_SLASH));
                                                lookUp.setDescLangFirst(filepath1.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                        MainetConstants.WINDOWS_SLASH));
                                                lookUp.setDescLangSecond(filepath1.replace(MainetConstants.FILE_PATH_SEPARATOR,
                                                        MainetConstants.WINDOWS_SLASH));
                                            }
                                            lookUp.setLookUpId(details.getId());
                                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                                            lookUp.setLookUpExtraLongTwo(count);

                                            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                                                lookUp.setOtherField(details.getTxt_01_en() != null ? details.getTxt_01_en()
                                                        : MainetConstants.BLANK);
                                            } else {
                                                lookUp.setOtherField(details.getTxt_01_rg() != null ? details.getTxt_01_rg()
                                                        : MainetConstants.BLANK);
                                            }

                                            count++;

                                            lookUps.add(lookUp);

                                        }else {
                                            lookUp.setLookUpId(details.getId());
                                            lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                            lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                            lookUp.setLookUpCode(keyValue.getFieldLabel());
                                            lookUps.add(lookUp);
                                        }
                                    }
                                }

                                // lookUp.setD

                            }
                        }
                        if (hasDocs) {
                            if (keyValue.getKey() == MainetConstants.ATTACHMENT_FIELD) {
                                lookUp = new LookUp(details.getId(),
                                        String.valueOf(getPropertyVal(keyValue.getValue(), details)));
                                lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                lookUp.setLookUpCode(keyValue.getFieldLabel());
                                lookUps.add(lookUp);
                            }
                        }
                        count++;
                    }

                    // Add details of Profile Image.

                    dataList.add(lookUps);
                }

            }
            // below method is called for setting the label according to language Id.
            getSubLinkLabel();
            return dataList;
        } catch (final NullPointerException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return null;
    }

    public String getSubLinkLabel() {
        try {

            String sublinkName = MainetConstants.BLANK;
            if (UserSession.getCurrent().getLanguageId() == 1) {
                sublinkName = getMaster().getSubLinkNameEn();
            } else {
                sublinkName = getMaster().getSubLinkNameRg();
            }
            return sublinkName;
        } catch (final NullPointerException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return null;
    }

    private class KeyValue implements Serializable {

        private static final long serialVersionUID = -4413589667550488856L;
        private final int key;
        private final String value;
        private final String fieldLabel;
        private final long sectiontype;

        public long getSectiontype() {
            return sectiontype;
        }

        public KeyValue(final int key, final String value, final String fieldLabel, final long sectiontype) {
            this.key = key;
            this.value = value;
            this.fieldLabel = fieldLabel;
            this.sectiontype = sectiontype;
        }

        /**
         * @return the key
         */
        public int getKey() {
            return key;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        public String getFieldLabel() {
            return fieldLabel;
        }
    }

    private boolean hasProfileImage(final List<KeyValue> fields) {
        for (final KeyValue keyValue : fields) {
            if (keyValue.getKey() == MainetConstants.PROFILE_IMG) {
                return true;
            }

            if (keyValue.getKey() == MainetConstants.VIDEO) {
                return true;
            }
        }

        return false;
    }

    private boolean hasDownloadDocs(final List<KeyValue> fields) {
        for (final KeyValue keyValue : fields) {
            if (keyValue.getKey() == MainetConstants.ATTACHMENT_FIELD) {
                return true;
            }
        }
        return false;
    }

    public SubLinkMaster getMaster() {
        return master;
    }

    public void setMaster(SubLinkMaster master) {
        this.master = master;
    }

    private Object getPropertyVal(final String propertyName, final Object clazz) {
        final BeanWrapper wrapper = new BeanWrapperImpl(clazz);

        if (wrapper.isReadableProperty(propertyName)) {
            final Object val = wrapper.getPropertyValue(propertyName);

            if (val != null) {
                if (val instanceof Date) {
                    return Utility.dateToString(Utility.converObjectToDate(val));
                } else if (val instanceof byte[]) {
                    final byte[] bytes = (byte[]) val;

                    return bytes;
                }
                return val.toString();
            } else {
                return MainetConstants.BLANK;
            }
        }

        return null;
    }

    public  List<EIPAnnouncementHistory> getEipAnnouncement() {

        eipAnnouncement = dataArchivalService.getGuestHomeEIPAnnouncement(UserSession.getCurrent().getOrganisation());

        final ListIterator<EIPAnnouncementHistory> listIterator = eipAnnouncement.listIterator();

        while (listIterator.hasNext()) {
            final EIPAnnouncementHistory eipAnnouncement = listIterator.next();

            final String directoryTree = eipAnnouncement.getAttach();
            /*eipAnnouncement.setAttachImage(downloadNewsImages(eipAnnouncement));*/
            eipAnnouncement.setAttachImage(downloadRecentAnno(eipAnnouncement));
            final FileStorageCache cache = eipAnnouncement.getFileStorageCache();

            try

            {
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                    getFileNetClient().uploadFileList(cache.getFileList(), directoryTree);
                }
            } catch (final Exception ex) {
                throw new FrameworkException(ex.getLocalizedMessage());
            } finally {
                cache.flush();
            }

        }
		return eipAnnouncement;
    }

    private String downloadNewsImages(EIPAnnouncementHistory newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.NEWS_IMAGES;
        if (newsImages.getAttachImage() != null) {
            try {
                return Utility.downloadedFileUrl(newsImages.getAttachImage(), outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }

    }
    
    private String downloadRecentAnno(EIPAnnouncementHistory newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + newsImages.getAnnounceDescEng();
        String imgArr[];
        String attachImg=newsImages.getAttachImage();
        String img="";
        String cacheImg="";
        if(attachImg!=null && attachImg.contains(",")) {
        	imgArr=attachImg.split(",");
        	for(String arr:imgArr) {
            	img=Utility.downloadedFileUrl(arr, outputPath, getFileNetClient()); 
            	cacheImg+=img+",";
        	}
        	if(cacheImg.length()!= 0) {	
        		cacheImg=cacheImg.substring(0, cacheImg.length()-1);
        	}
        }
        else {
        if (newsImages.getAttachImage() != null) {
            try {
                return Utility.downloadedFileUrl(newsImages.getAttachImage(), outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }
        }
        return cacheImg;
    }

    public void setEipAnnouncement(List<EIPAnnouncementHistory> eipAnnouncement) {
        this.eipAnnouncement = eipAnnouncement;
    }

}
