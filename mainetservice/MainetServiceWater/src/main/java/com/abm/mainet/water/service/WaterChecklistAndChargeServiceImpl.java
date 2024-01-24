package com.abm.mainet.water.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.utility.Utility;

@Service
public class WaterChecklistAndChargeServiceImpl implements WaterChecklistAndChargeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterChecklistAndChargeServiceImpl.class);

	/*
	 * @Resource private NewWaterConnectionService waterConnectionService;
	 */

	/*
	 * @Override public WSResponseDTO initializeModel() { final WSRequestDTO dto =
	 * new WSRequestDTO();
	 * dto.setModelName(MainetConstants.NewWaterServiceConstants.
	 * CHECKLIST_WATERRATEMASTER_MODEL); final WSResponseDTO response =
	 * RestClient.callBRMS(dto,
	 * ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL); return response; }
	 */

	/*
	 * @Override
	 * 
	 * @SuppressWarnings("unchecked") public List<DocumentDetailsVO>
	 * getChecklist(final CheckListModel checklistModel) { return null;
	 * List<DocumentDetailsVO> checklist = Collections.emptyList(); final
	 * WSRequestDTO dto = new WSRequestDTO(); dto.setDataModel(checklistModel);
	 * final WSResponseDTO response = RestClient.callBRMS(dto,
	 * ServiceEndpoints.BRMSMappingURI.CHECKLIST_URI); if
	 * (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.
	 * getWsStatus())) { final List<?> docs = RestClient.castResponse(response,
	 * DocumentDetailsVO.class); checklist = (List<DocumentDetailsVO>) docs; }
	 * return checklist; }
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public WSResponseDTO getApplicableTaxes(final WaterRateMaster rate,
	 * final long orgid, final String serviceShortCode) { return null; WSResponseDTO
	 * responseDTO = null; final WSRequestDTO dto = new WSRequestDTO();
	 * rate.setOrgId(orgid); rate.setServiceCode(serviceShortCode);
	 * rate.setChargeApplicableAt(Long.toString(CommonMasterUtility
	 * .getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
	 * MainetConstants.NewWaterServiceConstants.CAA) getLookUpId()));
	 * dto.setDataModel(rate); // get applicable changes . Object responseObj =
	 * null; try { ResponseEntity<?> responseEntity = waterConnectionService
	 * .listOfApplicableServiceCharge(dto); responseObj = responseEntity.getBody();
	 * if (responseObj instanceof WSResponseDTO) { responseDTO = (WSResponseDTO)
	 * responseObj; } else { final LinkedHashMap<Long, Object> responseVo =
	 * (LinkedHashMap<Long, Object>) responseObj; final String jsonString = new
	 * JSONObject(responseVo).toString(); responseDTO = new
	 * ObjectMapper().readValue(jsonString, WSResponseDTO.class); } }catch(
	 * Exception ex) { throw new FrameworkException(ex); } // final WSResponseDTO
	 * applicableTaxes = RestClient.callBRMS(dto, //
	 * ServiceEndpoints.WaterBRMSMappingURI.DEPENDENT_PARAM_URI);return responseDTO;
	 * }
	 */

	/*
	 * @Override public List<ChargeDetailDTO> getApplicableCharges(final
	 * List<WaterRateMaster> requiredCHarges) { return null; final WSRequestDTO
	 * request = new WSRequestDTO(); request.setDataModel(requiredCHarges); final
	 * WSResponseDTO response = RestClient.callBRMS(request,
	 * ServiceEndpoints.WaterBRMSMappingURI.SERVICE_CHARGE_URI); if
	 * (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.
	 * getWsStatus())) { final List<?> charges = RestClient.castResponse(response,
	 * WaterRateMaster.class); final List<WaterRateMaster> finalRateMaster = new
	 * ArrayList<>(); for (final Object rate : charges) { final WaterRateMaster
	 * masterRate = (WaterRateMaster) rate; finalRateMaster.add(masterRate); } final
	 * ChargeDetailDTO chargedto = new ChargeDetailDTO(); final
	 * List<ChargeDetailDTO> detailDTOs = new ArrayList<>(); for (final
	 * WaterRateMaster rateCharge : finalRateMaster) {
	 * chargedto.setChargeCode(rateCharge.getTaxId());
	 * chargedto.setChargeAmount(rateCharge.getFlatRate());
	 * chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
	 * chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
	 * detailDTOs.add(chargedto); } return detailDTOs; } else { throw new
	 * RuntimeException("Rule not Match : " + response.getErrorMessage()); } }
	 */

	@Override
	public double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		if (!CollectionUtils.isEmpty(charges)) {
			for (final ChargeDetailDTO charge : charges) {
				amountSum = amountSum + charge.getChargeAmount();
			}
		}
		return amountSum;
	}

	@Override
	public List<DocumentDetailsVO> getFileUploadList(final List<DocumentDetailsVO> checkList,
			final Map<Long, Set<File>> fileMap) {

		final Map<Long, String> listString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();

		try {
			final List<DocumentDetailsVO> docs = checkList;
			if ((fileMap != null) && !fileMap.isEmpty()) {
				for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

					final List<File> list = new ArrayList<>(entry.getValue());
					for (final File file : list) {
						try {
							final Base64 base64 = new Base64();

							final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
							fileName.put(entry.getKey(), file.getName());
							listString.put(entry.getKey(), bytestring);

						} catch (final IOException e) {
							LOGGER.error("Exception found in getFileUploadList during file I/O :", e);
							throw new FrameworkException(e);
						}
					}
				}
			}
			if (docs != null) {
				if (!docs.isEmpty() && !listString.isEmpty()) {
					for (final DocumentDetailsVO d : docs) {
						final long count = d.getDocumentSerialNo() - 1;
						if (listString.containsKey(count) && fileName.containsKey(count)) {
							d.setDocumentByteCode(listString.get(count));
							d.setDocumentName(fileName.get(count));

							if ((d.getDoc_DESC_Mar() != null) && !d.getDoc_DESC_Mar().isEmpty()) {

								d.setDoc_DESC_Mar(Utility.encodeField(d.getDoc_DESC_Mar()));
							}

						}
						if ((d.getDoc_DESC_ENGL() == null) || d.getDoc_DESC_ENGL().equals(MainetConstants.BLANK)) {
							d.setDoc_DESC_ENGL(d.getDocumentSerialNo().toString());
						}

					}

				}
			}
			return docs;
		} catch (final Exception e) {
			throw new FrameworkException("FileUploading Exception occur in getFileUploadList", e);
		}
	}

}
