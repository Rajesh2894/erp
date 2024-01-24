package com.abm.mainet.dms.service;

import java.lang.reflect.Field;
import java.util.Set;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author Vikrant.Thakur
 * @since 4 October 2014. Used to generate Rum Time File-Upload Tag
 *
 */
public class FileUploadClass extends AbstractHtmlInputElementTag {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(FileUploadClass.class);
    private String fileType;

    private String validnfn;

    private String code;

    private Long currentCount;

    private String fileSize;

    private String fileErrorMsg;

    private String maxFileCount;

    private String folderName;

    private boolean removeDeleteOption;

    private boolean allowClassicStyle;

    private String checkListMandatoryDoc;

    private String checkListDesc;

    private Long checkListId;

    private String checkListMStatus;

    private Long checkListSStatus;

    private Long checkListSrNo;
    
    private String callbackOtherTask;
    
    private String checklistDocSize;
    
    private String checkListDocDesc;// use to store UDN Prefix value

    @Override
    protected int writeTagContent(final TagWriter tagWriter) throws JspException {
        tagWriter.startTag(MainetConstants.HTML_CHARACTER.INPUT);

        writeDefaultAttributes(tagWriter);

        tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.TYPE, getFileType());

        tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.CODE,
                getFileType() + MainetConstants.operator.UNDER_SCORE + getCurrentCount());

        setCode(getFileType() + MainetConstants.operator.UNDER_SCORE + getCurrentCount());

        String cssClassApply = MainetConstants.operator.EMPTY;

        if (isAllowClassicStyle()) {
            cssClassApply = MainetConstants.HTML_CHARACTER.CLASS_APPLY_1;
        } else {
            cssClassApply = MainetConstants.HTML_CHARACTER.CLASS_APPLY_1 + MainetConstants.HTML_CHARACTER.CLASS_APPLY_2;
        }

        tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.CLASS, cssClassApply);

        tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.STYLE, MainetConstants.HTML_CHARACTER.STYLE_APPLY);

        String[] fieldValue;
        try {

        	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
        		final Field field = MainetConstants.Validation_Constant.class.getField(MainetConstants.Common_Constant.PORTAL_UPLOAD_KDMC_EXTENSION);
	            fieldValue = (String[]) field.get(MainetConstants.Validation_Constant.class);
	            final StringBuilder str = new StringBuilder();
	            for (final String string : fieldValue) {
	                str.append(string);
	                str.append(',');
	            }
	            tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.EXT, str.deleteCharAt(str.length() - 1).toString());

    		} else {
    			if ((getValidnfn() == null) || getValidnfn().equals(StringUtils.EMPTY)) {
		            tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.EXT, StringUtils.EMPTY);
		        } else {
		
		            final Field field = MainetConstants.Validation_Constant.class.getField(getValidnfn());
		            fieldValue = (String[]) field.get(MainetConstants.Validation_Constant.class);
		            final StringBuilder str = new StringBuilder();
		            for (final String string : fieldValue) {
		                str.append(string);
		                str.append(',');
		            }
		            tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.EXT, str.deleteCharAt(str.length() - 1).toString());
		        }
    		}

            if (getFileSize().equals(StringUtils.EMPTY)) {
                tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.MAXSIZE, StringUtils.EMPTY);

            } else {
                final Field field1 = MainetConstants.CheckList_Size.class.getField(getFileSize());
                final int maxFileSize = (int) field1.get(MainetConstants.CheckList_Size.class);
                tagWriter.writeAttribute(MainetConstants.HTML_CHARACTER.MAXSIZE, maxFileSize + StringUtils.EMPTY);
            }

        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        
        if(getCallbackOtherTask() != null && !getCallbackOtherTask().isEmpty()) {
        	tagWriter.writeAttribute("data-callback", getCallbackOtherTask());
        }
        
        tagWriter.writeAttribute("checklistDocSize", getChecklistDocSize());
        tagWriter.endTag();

        final int size = FileUploadUtility.getCurrent().getFileUploadSet().size();

        if (size == getCurrentCount()) {
            FileUploadUtility.getCurrent().getFileUploadSet().add(this);

        }
        
        Set<FileUploadClass> fileSet = FileUploadUtility.getCurrent().getFileUploadSet();
        boolean isPresent = false;
        for(FileUploadClass uploadClass : fileSet) {
        	if(uploadClass.getCode().equals(code)) {
        		isPresent = true;
        		break;
        	}
        }
        if(!isPresent) {
        	FileUploadUtility.getCurrent().getFileUploadSet().add(this);
        }
        
        return SKIP_BODY;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(final String fileType) {
        this.fileType = fileType;
    }

    public String getValidnfn() {
        return validnfn;
    }

    public void setValidnfn(final String validnfn) {
        this.validnfn = validnfn;
    }

    public Long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(final Long currentCount) {
        this.currentCount = currentCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(final String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileErrorMsg() {
        return fileErrorMsg;
    }

    public void setFileErrorMsg(final String fileErrorMsg) {
        this.fileErrorMsg = fileErrorMsg;
    }

    public String getMaxFileCount() {
        return maxFileCount;
    }

    public void setMaxFileCount(final String maxFileCount) {
        this.maxFileCount = maxFileCount;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(final String folderName) {
        this.folderName = folderName;
    }

    public boolean isRemoveDeleteOption() {
        return removeDeleteOption;
    }

    public void setRemoveDeleteOption(final boolean removeDeleteOption) {
        this.removeDeleteOption = removeDeleteOption;
    }

    public boolean isAllowClassicStyle() {
        return allowClassicStyle;
    }

    public void setAllowClassicStyle(final boolean allowClassicStyle) {
        this.allowClassicStyle = allowClassicStyle;
    }

    public String getCheckListMandatoryDoc() {
        return checkListMandatoryDoc;
    }

    public void setCheckListMandatoryDoc(final String checkListMandatoryDoc) {
        this.checkListMandatoryDoc = checkListMandatoryDoc;
    }

    public String getCheckListDesc() {
        return checkListDesc;
    }

    public void setCheckListDesc(final String checkListDesc) {
        this.checkListDesc = checkListDesc;
    }

    public Long getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(final Long checkListId) {
        this.checkListId = checkListId;
    }

    public Long getCheckListSrNo() {
        return checkListSrNo;
    }

    public void setCheckListSrNo(final Long checkListSrNo) {
        this.checkListSrNo = checkListSrNo;
    }

    public String getCheckListMStatus() {
        return checkListMStatus;
    }

    public void setCheckListMStatus(final String checkListMStatus) {
        this.checkListMStatus = checkListMStatus;
    }

    public Long getCheckListSStatus() {
        return checkListSStatus;
    }

    public void setCheckListSStatus(final Long checkListSStatus) {
        this.checkListSStatus = checkListSStatus;
    }
    public String getCallbackOtherTask() {
		return callbackOtherTask;
	}

	public void setCallbackOtherTask(String callbackOtherTask) {
		this.callbackOtherTask = callbackOtherTask;
	}
	
	

    public String getChecklistDocSize() {
		return checklistDocSize;
	}

	public void setChecklistDocSize(String checklistDocSize) {
		this.checklistDocSize = checklistDocSize;
	}
	
	

	public String getCheckListDocDesc() {
		return checkListDocDesc;
	}

	public void setCheckListDocDesc(String checkListDocDesc) {
		this.checkListDocDesc = checkListDocDesc;
	}

	@Override
    public String toString() {
        return "FileUploadClass [fileType=" + fileType + ", validnfn=" + validnfn + ", code=" + code + ", currentCount="
                + currentCount + ", fileSize=" + fileSize + ", fileErrorMsg=" + fileErrorMsg + ", maxFileCount=" + maxFileCount
                + ", folderName=" + folderName + ", removeDeleteOption=" + removeDeleteOption + ", allowClassicStyle="
                + allowClassicStyle + ", checkListMandatoryDoc=" + checkListMandatoryDoc + ", checkListDesc=" + checkListDesc
                + ", checkListId=" + checkListId + ", checkListMStatus=" + checkListMStatus + ", checkListSStatus="
                + checkListSStatus + ", checkListSrNo=" + checkListSrNo + "]";
    }

	


}