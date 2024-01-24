package com.abm.mainet.tradeLicense.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IDmsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.dto.LicenseReprintDetailsDto;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Rajesh.Das
 *
 */

@Service(value = "TradeLicenseReprintService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.ITradeLicenseReprintService")
@Api(value = "/tradeLicenseApplicationReprintService")
@Path("/tradeLicenseApplicationReprintService")
public class TradeLicenseReprintServiceImpl implements ITradeLicenseReprintService {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private TbApprejMasService tbApprejMasService;

	@Autowired
	ICFCApplicationMasterService cfcApplicationService;

	@Autowired
	private TbLoiMasService tbLoiMasService;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private IDmsService iDmsService;

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.FETCH_LIC_NO_APP_NO, notes = MainetConstants.TradeLicense.FETCH_LIC_NO_APP_NO, response = TradeMasterDetailDTO.class)
	@Path("/fetchLicenseAndAppNo/{orgId}")
	@Transactional(readOnly = true)
	public List<TradeMasterDetailDTO> getTradeLicenseNoAndAppNo(
			@PathParam(value = "orgId") Long orgId) {
		Organisation org=ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getOrganisationById(orgId);
		
		LookUp trasitStatuslookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
				org);
		List<TradeMasterDetailDTO> trdMstDto=tradeLicenseApplicationService.getActiveApplicationIdByOrgId(orgId).stream()
				.filter(k -> k.getTrdStatus() == trasitStatuslookUp.getLookUpId()).collect(Collectors.toList());
		return trdMstDto;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.FETCH_TRADE_LICENSE_REPRINT_DETAILS, notes = MainetConstants.TradeLicense.FETCH_TRADE_LICENSE_REPRINT_DETAILS, response = LicenseReprintDetailsDto.class)
	@Path("/getTradeLicenseApplication/{appNo}/{orgId}")
	public LicenseReprintDetailsDto fetchLicenseViewDetails(@PathParam(value = "appNo") Long appNo,
			@PathParam(value = "orgId") Long orgId) {
		// TODO Auto-generated method stub
		LicenseReprintDetailsDto licReprintDto = new LicenseReprintDetailsDto();
		TradeMasterDetailDTO tradeMasterDetailDTO = null;

		try {
			tradeMasterDetailDTO = tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(appNo);
		} catch (Exception e) {
			return null;

		}
		TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
				.getCFCApplicationByApplicationId(appNo, orgId);
		Long smServiceId = cfcApplicationService.getServiceIdByApplicationId(appNo, orgId);

		licReprintDto.setTradeDetailDTO(tradeMasterDetailDTO);
		Date licenseEndDate = tradeMasterDetailDTO.getTrdLictoDate();
		if (licenseEndDate != null) {
			tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(licenseEndDate));
		}
		// licReprintDto.setCfcEntity(cfcApplicationDetails);
		licReprintDto.setDateDesc(Utility.dateToString(cfcApplicationDetails.getApmApplicationDate()));
		tradeMasterDetailDTO.setCreatedDateDesc(Utility.dateToString(new Date()));

		List<TradeLicenseOwnerDetailDTO> newOwnerDetailDTOList = licReprintDto.getTradeDetailDTO()
				.getTradeLicenseOwnerdetailDTO().stream().filter(k -> k.getTroPr() != null)
				.filter(k -> k.getTroPr().equalsIgnoreCase(MainetConstants.FlagA)).collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(newOwnerDetailDTOList)) {
			List<CFCAttachment> imgList = checklistVerificationService
					.getDocumentUploadedByRefNo(newOwnerDetailDTOList.get(0).getTroId().toString(), orgId);
			licReprintDto.setDocumentsList(imgList);
			if (!imgList.isEmpty() && imgList != null) {
				licReprintDto.setImagePath(getPropImages(imgList.get(0)));
			}
		}
		/*
		 * Date licenseIssuanceDate = tradeMasterDetailDTO.getTrdLicisdate(); if
		 * (licenseIssuanceDate != null) {
		 */
		licReprintDto.setIssuanceDateDesc(Utility.dateToString(new Date()));

		Long artId = 0l;
		Organisation org = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
				.getOrganisationById(orgId);
		UserSession session = new UserSession();
		session.getCurrent().setOrganisation(org);
		final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp Lookup1 : lookUpList) {

			if (Lookup1.getLookUpCode().equals(PrefixConstants.WATERMODULEPREFIX.APP)) {
				artId = Lookup1.getLookUpId();
			}

		}
		List<TbLoiMas> tbLoiMas = null;
		try {
			tbLoiMas = tbLoiMasService.getloiByApplicationId(appNo, smServiceId, orgId);
			if (tbLoiMas != null && !tbLoiMas.isEmpty()) {
				licReprintDto.setTbLoiMasList(tbLoiMas);
				licReprintDto.setLoiDateDesc(Utility.dateToString(tbLoiMas.get(0).getLoiDate()));
				List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentMode(orgId,
						tbLoiMas.get(0).getLoiNo());
				licReprintDto.setRmRcptno(dto.get(0).getRmRcptno());
				licReprintDto.setRmAmount(dto.get(0).getRmAmount());
				licReprintDto.setPayMode(dto.get(0).getCpdDesc());
			}
		} catch (Exception e) {
			// throw new FrameworkException("unable to fetch LOI details", e);
		}
		if (smServiceId != null && (artId != null && artId != 0L)) {
			List<TbApprejMas> apprejMasList = tbApprejMasService.findByRemarkType(smServiceId, artId);

			licReprintDto.setApprejMasList(apprejMasList);
		}

		licReprintDto.setLicenseFromDateDesc(Utility.dateToString(tradeMasterDetailDTO.getTrdLicfromDate()));

		return licReprintDto;
	}

	private String getPropImages(final CFCAttachment attachDocs) {

		new ArrayList<String>();
		final UUID uuid = UUID.randomUUID();
		final String randomUUIDString = uuid.toString();
		final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
				+ MainetConstants.FILE_PATH_SEPARATOR + UserSession.getCurrent().getOrganisation().getOrgid()
				+ MainetConstants.FILE_PATH_SEPARATOR + randomUUIDString + MainetConstants.FILE_PATH_SEPARATOR
				+ "PROPERTY";
		final String path1 = attachDocs.getAttPath();
		final String name = attachDocs.getAttFname();
		final String data = Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, outputPath,
				FileNetApplicationClient.getInstance());
		return data;
	}

}
