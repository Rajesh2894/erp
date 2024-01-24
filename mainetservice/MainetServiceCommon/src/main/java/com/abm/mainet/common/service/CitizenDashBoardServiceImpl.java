package com.abm.mainet.common.service;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ICitizenDashBoardDAO;
import com.abm.mainet.common.domain.CitizenDashboardView;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CitizenDashBoardResDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.payment.dto.RePaymentDTO;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.repository.CitizenDashBoardRepository;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.sun.tools.xjc.model.Model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author ritesh.patil
 *
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.common.service.CitizenDashBoardService")
@Api("/citizenDashboard")
@Path("/citizenDashboard")
@Transactional(readOnly = true)
public class CitizenDashBoardServiceImpl implements CitizenDashBoardService {

	private static final Logger log = LoggerFactory.getLogger(CitizenDashBoardServiceImpl.class);

	@Autowired
	private CitizenDashBoardRepository citizenDashBoardRepository;

	@Autowired
	private ICitizenDashBoardDAO citizenDashBoardDAO;

	@Autowired
	private IAttachDocsService attachDocsService;

	@POST
	@Path("/getDashBoardData")
	@ApiOperation(value = "Return dash board data", notes = "Return dash board data", response = CitizenDashBoardResDTO.class)
	@Override
	public List<CitizenDashBoardResDTO> getAllApplicationsOfCitizen(
			@ApiParam(value = "Citizen dash board request", required = true) final CitizenDashBoardReqDTO request) {
		final List<CitizenDashBoardResDTO> dashBoardResDTOs = new ArrayList<>();
		if (request.isEmpty()) {
			log.error("Invalid request to fetch citizen dashboard data. " + request);
			return dashBoardResDTOs;
		}
		final List<CitizenDashboardView> appList = citizenDashBoardDAO.getAllApplicationsOfCitizen(request);
		appList.forEach(app -> {
			CitizenDashBoardResDTO application = new CitizenDashBoardResDTO();
			List<AttachDocs> att1 = new ArrayList<>();
			application.setAppId(app.getApmApplicationId());
			/*
			 * Earlier getting both referenceId & applicationId from same
			 * column(apm_application_id) Now we have added one more column reference_id and
			 * now we are getting both values in respective columns.
			 */
			application.setRefId(app.getReferenceId());
			// application.setAppDate(app.getApmApplicationDate().toString());
			String format = MainetConstants.DATE_FORMAT_WITH_HOURS_TIME;
			application.setAppDate(Utility.dateToString(app.getApmApplicationDate(), format));
			application.setDeptName(app.getDpDeptdesc());
            application.setDeptNameMar(app.getDpNameMar());
            application.setServiceName(app.getSmServiceName());
			application.setSmServiceNameMar(app.getSmServiceNameMar());
			application.setLastDecision(app.getLastDecision());
			application.setOrgId(app.getOrgId());
			application.setSmShortdesc(app.getSmShortdesc());
			application.setStatus(app.getStatus());
			application.setTaskId(app.getTaskId());
			application.setTaskSlaDurationInMS(app.getTaskSlaDurationInMS());
			application.setTaskStatus(app.getTaskStatus());
			application.setServiceEventId(app.getServiceEventId());
			application.setServiceEventName(app.getServiceEventName());
			application.setServiceEventNameReg(app.getServiceEventNameReg());
			application.setServiceEventUrl(app.getServiceEventUrl());
			application.setServiceType(app.getServiceType());
			application.setDpDeptcode(app.getDpDeptcode());
			application.setSmServiceDuration(app.getSmServiceDuration());
			if (application.getStatus().equals(MainetConstants.DASHBOARD.CLOSED)
					&& (application.getDpDeptcode().equals(MainetConstants.RightToService.RTS_DEPT_CODE)
							|| application.getDpDeptcode().equals(MainetConstants.DEPT_SHORT_NAME.BND))) {
				att1 = (attachDocsService.findByCode(application.getOrgId(), application.getAppId()));
				if (!att1.isEmpty()) {
					application.setDocName(att1.get(0).getAttFname());
					String existingPath = null;

					if (att1.get(0).getAttPath().contains(MainetConstants.operator.DOUBLE_BACKWARD_SLACE)) {
						existingPath = att1.get(0).getAttPath().replace('\\', '/');
					} else {
						existingPath = att1.get(0).getAttPath();
					}
					application.setDocPath(existingPath);
					// application.setDocPath(att1.get(0).getAttPath());
					application.setFilePath(
							application.getDocPath() + MainetConstants.WINDOWS_SLASH + application.getDocName());
				}
			}
			dashBoardResDTOs.add(application);
		});

		return dashBoardResDTOs;
	}

	@Override
	public List<CitizenDashBoardResDTO> getAllFaliuredOrCancelledOnlineList(String mobNo, Long orgId) {

		final List<CitizenDashBoardResDTO> dashBoardResDTOs = new ArrayList<>();
		final List<Object[]> appList = citizenDashBoardDAO.getAllFaliuredOrCancelledOnlineList(mobNo, orgId);
		appList.forEach(app -> {
			CitizenDashBoardResDTO application = new CitizenDashBoardResDTO();
			application.setServiceName(app[0].toString());
			application.setSmServiceNameMar(app[1].toString());
			application.setSmShortdesc(app[2].toString());
			application.setDeptName(app[3].toString());
			application.setDeptNameMar(app[4].toString());
			application.setOnlTransactionId(Long.valueOf(app[5].toString()));
			application.setRefId(app[6].toString());
			// application.setAppDate(app[7].toString());
			application.setAppDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_WITH_HOURS_TIME).format(app[7]));
			// set db present orgId D#136928
			application.setStatus(app[8].toString());
			//Defect #131746
			if (StringUtils.isNotEmpty(app[8].toString())
					&& app[8].toString().equalsIgnoreCase(MainetConstants.PAYU_STATUS.CANCEL)) {
				application.setStatusDesc(ApplicationSession.getInstance().getMessage("common.dashboard.status.cancelStatus"));

			} else if (StringUtils.isNotEmpty(app[8].toString())
					&& app[8].toString().equalsIgnoreCase(MainetConstants.TASK_STATUS_PENDING)) {
				application
						.setStatusDesc(ApplicationSession.getInstance().getMessage("common.dashboard.status.pendingStatus"));
			}
			if (app[9].toString() != null) {
				application.setOrgId(Long.valueOf(app[9].toString()));
			} else {
				application.setOrgId(orgId);
			}

			dashBoardResDTOs.add(application);
		});
		return dashBoardResDTOs;
	}

	@Override
	public RePaymentDTO getPayPendingDataByONLTransId(Long onlTransNo) {
		final Object[] data = citizenDashBoardRepository.getPayPendingDataByONLTransId(onlTransNo);
		RePaymentDTO dto = new RePaymentDTO();
		Object[] app = (Object[]) data[0];
		dto.setServiceName(app[0].toString());
		dto.setServiceNameMar(app[1].toString());
		dto.setRefId(app[2].toString());
		dto.setPayeeName(app[3].toString());
		dto.setEmail(app[4].toString());
		dto.setPhoneNo(app[5].toString());
		dto.setAmount(Double.parseDouble(app[6].toString()));
		dto.setServiceId(Long.valueOf(app[7].toString()));
		dto.setFeeIds(app[8].toString());
		return dto;
	}

	@SuppressWarnings("static-access")
	@POST
	@Path("/getDashBoardDocument")
	@ApiOperation(value = "Return dash board data", notes = "Return dash board data", response = CitizenDashBoardResDTO.class)
	@Override
	public CitizenDashBoardResDTO getDocument(
			@ApiParam(value = "Citizen dash board request", required = true) final CitizenDashBoardResDTO request) {
		final CitizenDashBoardResDTO dashBoardResDTOs = new CitizenDashBoardResDTO();

		FileNetApplicationClient fileNetApplicationClient = null;

		String existingPath = null;
		if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
			existingPath = request.getDocPath().replace('/', '\\');
		} else {
			existingPath = request.getDocPath().replace('\\', '/');
		}

		String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);

		try {

			final byte[] image = fileNetApplicationClient.getInstance().getFileByte(request.getDocName(),
					directoryPath);

			Base64 base64 = new Base64();
			String pdfDoc = base64.encodeToString(image);
			// dashBoardResDTOs.setDocument(image);
			dashBoardResDTOs.setDoc(pdfDoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return dashBoardResDTOs;
	}

	@Override
	public List<PaymentTransactionMas> getCitizenPayPendingDataByDateAndStatus(Date validDate, Long serviceId, String flatNo,Long orgId,String refNo) {
		return citizenDashBoardDAO.getCitizenPayPendingDataByDateAndStatus(validDate,serviceId,flatNo,orgId,refNo);
		
	}
}
