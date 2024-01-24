package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.service.ICommonHelpDocsService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

/**
 * @author vinay.jangir
 *
 */
@Component
@Scope("session")
public class CommonHelpDocsModel extends AbstractEntryFormModel<CommonHelpDocs> implements Serializable {

    private static final long serialVersionUID = -6811464704393633470L;

    @Autowired
    private ICommonHelpDocsService iHelpDocService;

    private Map<Long, String> serviceNames;

    private Map<String, String> nodes;

    private String selectedService;

    private List<CommonHelpDocs> helpDocs = new ArrayList<>();

    public Map<Long, String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(final Map<Long, String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public List<CommonHelpDocs> getHelpDocs() {

        return helpDocs;
    }

    public void setHelpDocs(final List<CommonHelpDocs> helpDocs) {
        this.helpDocs = helpDocs;

    }

    public boolean deleteDoc(final long rowId) {
        return iHelpDocService.delete(rowId, UserSession.getCurrent().getOrganisation());

    }

    public void emptyGrid() {
        if (getHelpDocs() != null) {
            getHelpDocs().clear();
        }
    }

    @Override
    protected void initializeModel() {
        super.initializeModel();
        setNodes(iHelpDocService.getAllNodes(UserSession.getCurrent().getLanguageId()));
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
    }



    public Map<String, String> getNodes() {
		return nodes;
	}

	public void setNodes(Map<String, String> nodes) {
		this.nodes = nodes;
	}

	public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(final String selectedService) {
        this.selectedService = selectedService;
    }

}