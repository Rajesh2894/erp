package com.abm.mainet.cfc.checklist.service;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Base64;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.cfc.checklist.dao.IChecklistSearchDAO;
import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.modelmapper.ChecklistMapper;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.utility.ApplicationContextProvider;

import io.swagger.annotations.Api;

@WebService(endpointInterface = "com.abm.mainet.cfc.checklist.service.IChecklistSearchService")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/checklistSearchService")
@Path("/checklistSearchService")
@Service
public class ChecklistSearchService implements IChecklistSearchService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistSearchService.class);

    @Autowired
    private IChecklistSearchDAO checklistSearchDAO;

    @Resource
    private ChecklistMapper mapper;
    
    @Autowired
   	private IAttachDocsService attachDocsService;
    
    @Autowired
	private IChecklistVerificationService ichckService;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<ChecklistStatusView> serachChecklist(final long orgId, final Long applicationId, final Long serviceId,
            final String name,
            final Date fromDate, final Date toDate, final String applicationStatus) {
        final List<ChecklistStatusView> result = checklistSearchDAO.queryChecklist(orgId, applicationId, serviceId, name,
                fromDate, toDate, applicationStatus);
        return result.isEmpty() ? Collections.EMPTY_LIST : result;
    }

    @Override
    @Transactional
    public ChecklistStatusView getCheckListDataByApplication(final long orgId, final long applicationId) {
        final ChecklistStatusView applicantDetails = checklistSearchDAO.queryChecklistByApplication(orgId, applicationId);
        return applicantDetails != null ? applicantDetails : new ChecklistStatusView();

    }

    @Override
    @Transactional
    public boolean updateApplicationChecklistStatus(final long applicationId, final long orgId, final String checklistFlag) {

        return checklistSearchDAO.updateChecklistFlag(applicationId, orgId, checklistFlag);

    }
    
    @Override
    public CommonChallanDTO getApplicantName(Long applicationId,Long orgId) {
    	Class<?> clazz = null;
    	Object runtimeService = null;
    	CommonChallanDTO challanDto = null;
    	final String serviceName = "com.abm.mainet.tradeLicense.service.TradeLicenseApplicationServiceImpl";
    	//Defect #111802
    	if (!StringUtils.isEmpty(serviceName)) {
    		try {
    		clazz = ClassUtils.forName(serviceName,
    				ApplicationContextProvider.getApplicationContext().getClassLoader());
    		runtimeService = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
    				.autowire(clazz, 2, false);
    		final Method method = ReflectionUtils.findMethod(clazz,
    				"getLicenseDetailsByAppIdAndOrgId",
    				new Class[] { Long.class, Long.class });
    		challanDto = (CommonChallanDTO) ReflectionUtils.invokeMethod(method, runtimeService,
    				new Object[] { applicationId,orgId });
    	 } catch (Exception e) {
             e.printStackTrace();
         }
    	}
    	return challanDto;
    }
    
    @Override
	@POST
	@Path("/getCheckListDocument/orgId/{orgId}/application/{application}/checkListFlag/{checkListFlag}")
	public List<DocumentDetailsVO> getCheckListDocument(@PathParam("orgId") String orgId,@PathParam("application") String application,@PathParam("checkListFlag") String checkListFlag) {
		List<DocumentDetailsVO> finalCheckList = new ArrayList<>();
		List<AttachDocs> att1 = new ArrayList<>();
		//FileNetApplicationClient fileNetApplicationClient = null;
		List<CFCAttachment> cfcAtt = new ArrayList<>();
		// For CheckList Document
		if (checkListFlag.equals(MainetConstants.Y_FLAG)) {
			cfcAtt = ichckService.findAttachmentsForAppId(Long.valueOf(application), null, Long.valueOf(orgId));
			if (!cfcAtt.isEmpty()) {
				for (final CFCAttachment app : cfcAtt) {
					DocumentDetailsVO doc = new DocumentDetailsVO();
					doc.setDocumentName(app.getAttFname());
					doc.setDoc_DESC_ENGL(app.getClmDescEngl());
					doc.setDoc_DESC_Mar(app.getClmDesc());
					doc.setUploadedDocumentPath(app.getAttPath());
					//D#128836
					doc.setDocDescription(app.getDocDescription());
					doc.setClmAprStatus(app.getClmAprStatus());
					String existingPath = null;
					if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
						existingPath = app.getAttPath().replace('/', '\\');
					} else {
						existingPath = app.getAttPath().replace('\\', '/');
					}
					String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
							MainetConstants.operator.COMA);
					try {
						final byte[] image = FileNetApplicationClient.getInstance().getFileByte(app.getAttFname(),
								directoryPath);
						Base64 base64 = new Base64();
						String pdfDoc = base64.encodeToString(image);
						doc.setDocumentByteCode(pdfDoc);
					} catch (FileNotFoundException e) {
						 LOGGER.error("problem occurred while  getting document:", e);
					}
					finalCheckList.add(doc);
				}
			}
		} else {
			// For Attach Document
			att1 = attachDocsService.findByCode(Long.valueOf(orgId), application);
			if (!att1.isEmpty()) {
				for (final AttachDocs app : att1) {
					DocumentDetailsVO doc1 = new DocumentDetailsVO();
					doc1.setDocumentName(app.getAttFname());
					doc1.setUploadedDocumentPath(app.getAttPath());
					String existingPath = null;
					if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
						existingPath = app.getAttPath().replace('/', '\\');
					} else {
						existingPath = app.getAttPath().replace('\\', '/');
					}
					String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
							MainetConstants.operator.COMA);
					try {
						final byte[] image = FileNetApplicationClient.getInstance().getFileByte(app.getAttFname(),
								directoryPath);
						Base64 base64 = new Base64();
						String pdfDoc = base64.encodeToString(image);
						doc1.setDocumentByteCode(pdfDoc);
					} catch (FileNotFoundException e) {
						LOGGER.error("problem occurred while  getting document:", e);
					}
					finalCheckList.add(doc1);
				}
			}
		}
		return finalCheckList;

	}
}
