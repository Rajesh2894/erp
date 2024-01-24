package com.abm.mainet.workManagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.AdvanceRequisitionDto;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.ContractPart1DetailDTO;
import com.abm.mainet.common.master.dto.ContractPart2DetailDTO;
import com.abm.mainet.common.service.OrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.workManagement.service.WorksContractDetailsService")
@Api(value = "/worksContractService")
@Path("/worksContractService")
public class WorksContractDetailsServiceImpl implements WorksContractDetailsService {

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.WorksManagement.FETCH_CONTRACT_DETAILS, notes = MainetConstants.WorksManagement.FETCH_CONTRACT_DETAILS, response = ContractMastDTO.class)
	@Path("/loaNumber/orgId/{orgId}/referenceId/{referenceId}")
	public ContractMastDTO getContractDetailsByLoaNumber(
			@ApiParam(value = MainetConstants.WorksManagement.CONTRACT_DETAILS, required = true) @PathParam(MainetConstants.Common_Constant.ORGID) Long orgId,
			@PathParam(MainetConstants.WorksManagement.REF_ID) String loaNumber) {

		ContractMastDTO contractMastDTO = new ContractMastDTO();
		ContractPart1DetailDTO part1 = new ContractPart1DetailDTO();
		ContractPart2DetailDTO part2 = new ContractPart2DetailDTO();
		ContractDetailDTO detDto = new ContractDetailDTO();
		List<ContractPart2DetailDTO> part2List = new ArrayList<>();
		List<ContractPart1DetailDTO> part1List = new ArrayList<>();
		List<ContractDetailDTO> contractDetailList = new ArrayList<>();

		TenderWorkDto tenderWorkDto = ApplicationContextProvider.getApplicationContext()
				.getBean(TenderInitiationService.class).getTenderDetailsByLoaNumber(loaNumber, orgId);
		if (tenderWorkDto != null) {
			contractMastDTO.setContTndNo(tenderWorkDto.getTenderNo());

			contractMastDTO.setContTndDate(tenderWorkDto.getTenderDate());
			if (tenderWorkDto.getTndAwdResNo() != null && !tenderWorkDto.getTndAwdResNo().isEmpty()) {
				contractMastDTO.setContRsoNo(tenderWorkDto.getTndAwdResNo());
			}
			if (tenderWorkDto.getTndAwdResDate() != null) {
				contractMastDTO.setContRsoDate(tenderWorkDto.getTndAwdResDate());
			}
			contractMastDTO.setContDept(tenderWorkDto.getTenderDeptId());
			contractMastDTO.setLoaNo(tenderWorkDto.getTndLOANo());
			contractMastDTO.setContType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					MainetConstants.WorksManagement.CW, MainetConstants.WorksManagement.CNT, orgId));
			detDto.setContractAmt(tenderWorkDto.getTenderAmount());
			detDto.setContToPeriod(Long.parseLong(tenderWorkDto.getVendorWorkPeriod()));
			detDto.setContToPeriodUnit(tenderWorkDto.getVendorWorkPeriodUnit());
			part1.setDpDeptid(tenderWorkDto.getTenderDeptId());
			part1.setDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
			part2.setVmVendorid(tenderWorkDto.getVenderId());
			part2.setContp2vType(tenderWorkDto.getVenderTypeId());
			part2.setContp2Name(tenderWorkDto.getVendorName());
			part2.setContp2Primary(MainetConstants.FlagY);
			contractDetailList.add(detDto);
			part1List.add(part1);
			part2List.add(part2);

			contractMastDTO.setContractPart1List(part1List);
			contractMastDTO.setContractPart2List(part2List);

		}
		WorkDefinitionDto definitionDto = ApplicationContextProvider.getApplicationContext()
				.getBean(WorkDefinitionService.class).findWorkDefinitionByWorkId(tenderWorkDto.getWorkId(), orgId);
		if (definitionDto != null) {
			if (definitionDto.getWorkSubType() != null) {
				LookUp lookup = CommonMasterUtility
						.getNonHierarchicalLookUpObjectByPrefix(definitionDto.getWorkSubType(), orgId, "WRS");
				Organisation organisation = ApplicationContextProvider.getApplicationContext()
						.getBean(IOrganisationDAO.class).getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);

				String lookupField = lookup.getOtherField();
				Map<Long, Long> contMap = new HashMap<Long, Long>();
				LookUp lookUp2 = null;
				if (lookupField.contains("H")) {
					contMap = getContDefectPerUnt(lookupField, "H", organisation);
					lookUp2 = getLookup("H", organisation);
				}
				if (lookupField.contains("D")) {
					contMap = getContDefectPerUnt(lookupField, "D", organisation);
					lookUp2 = getLookup("D", organisation);
				}
				if (lookupField.contains("M")) {
					contMap = getContDefectPerUnt(lookupField, "M", organisation);
					lookUp2 = getLookup("M", organisation);
				}
				if (lookupField.contains("Y")) {
					contMap = getContDefectPerUnt(lookupField, "Y", organisation);
					lookUp2 = getLookup("Y", organisation);
				}

				for (Map.Entry<Long, Long> entry : contMap.entrySet()) {
					contractDetailList.get(0).setContDefectLiabilityPer(entry.getValue());
					contractDetailList.get(0).setContDefectLiabilityUnit(entry.getKey());
				}
				if (lookUp2!=null && lookUp2.getLookUpDesc() != null) {
					contractDetailList.get(0).setDefectLiaPeriodUnitDesc(lookUp2.getLookUpDesc());
				}
			}
		}
		contractMastDTO.setContractDetailList(contractDetailList);
		return contractMastDTO;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.WorksManagement.FETCH_WORK_ORDER, notes = MainetConstants.WorksManagement.FETCH_WORK_ORDER, response = Object.class)
	@Path("/workOrderDetails/orgId/{orgId}/venderId/{venderId}")
	public List<AdvanceRequisitionDto> getOrderDetails(
			@ApiParam(value = MainetConstants.WorksManagement.CONTRACT_DETAILS, required = true) @PathParam(MainetConstants.Common_Constant.ORGID) Long orgId,
			@PathParam(MainetConstants.WorksManagement.VENDOR_ID) Long vederId) {

		List<AdvanceRequisitionDto> advReqDetails = new ArrayList<>();
		AdvanceRequisitionDto requisitionDto = null;

		List<WorkOrderDto> orderDtos = ApplicationContextProvider.getApplicationContext()
				.getBean(WorkOrderService.class).workOrderList(vederId, orgId);

		if (orderDtos != null && !orderDtos.isEmpty()) {
			for (WorkOrderDto workOrderDto : orderDtos) {
				requisitionDto = new AdvanceRequisitionDto();
				requisitionDto.setReferenceNo(workOrderDto.getWorkOrderNo());
				requisitionDto.setReferenceAmount(workOrderDto.getContractAmt());
				advReqDetails.add(requisitionDto);
			}
		}
		return advReqDetails;
	}

	private Map<Long, Long> getContDefectPerUnt(String field, String sep, Organisation org) {
		Map<Long, Long> contMap = new HashMap<Long, Long>();
		String[] arr = field.split(sep);
		Long l = Long.parseLong(arr[0]);

		LookUp lookUp2 = CommonMasterUtility.getLookUpFromPrefixLookUpValue(sep, "UTS",
				UserSession.getCurrent().getLanguageId(), org);
		contMap.put(lookUp2.getLookUpId(), l);
		return contMap;
	}

	private LookUp getLookup(String sep, Organisation org) {
		LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(sep, "UTS",
				UserSession.getCurrent().getLanguageId(), org);

		return lookUp;
	}

}
