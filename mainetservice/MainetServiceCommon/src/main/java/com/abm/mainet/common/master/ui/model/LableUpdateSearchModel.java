package com.abm.mainet.common.master.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.PropertyFiles;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
@Scope("session")
public class LableUpdateSearchModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(LableUpdateSearchModel.class);

    private PropertyFiles propFile = new PropertyFiles();

    private Map<Integer, String> fileList = new LinkedHashMap<>(0);

    private String value;

    private long selectBoxValue = 0L;

    private String directoryPath = MainetConstants.BLANK;

    private List<PropertyFiles> propList = new ArrayList<>(0);

    public List<PropertyFiles> getPropList() {
        return propList;
    }

    public void setPropList(final List<PropertyFiles> propList) {
        this.propList = propList;
    }

    public PropertyFiles getPropFile() {
        return propFile;
    }

    public void setPropFile(final PropertyFiles propFile) {
        this.propFile = propFile;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    protected void initializeModel() {

        //super.setCommonHelpDocs("LableUpdateSearch.html");
        File file = null;
        directoryPath = ApplicationSession.getInstance().getMessage("lablpath");
        final StringTokenizer tokenizer = new StringTokenizer(directoryPath, MainetConstants.operator.COMA);
        directoryPath = MainetConstants.BLANK;
        while (tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextElement().toString();
            directoryPath += token + File.separator;
        }

        try {
            file = new File(directoryPath);
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        int count = 1;
        for (final File eachFile : file.listFiles()) {

            if (eachFile.isFile() && eachFile.getName().endsWith(".properties")) {

                fileList.put(count, eachFile.getName());
                count++;
            }
        }

    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(final String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public Map<Integer, String> getFileList() {
        return fileList;
    }

    public void setFileList(final Map<Integer, String> fileList) {
        this.fileList = fileList;
    }

    public long getSelectBoxValue() {
        return selectBoxValue;
    }

    public void setSelectBoxValue(final long selectBoxValue) {
        this.selectBoxValue = selectBoxValue;
    }
}
