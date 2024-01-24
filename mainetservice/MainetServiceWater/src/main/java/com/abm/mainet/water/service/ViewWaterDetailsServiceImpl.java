package com.abm.mainet.water.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbReceiptDuplicateEntity;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.DuplicateReceiptRepository;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.domain.TbWtBillDetEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.WaterPenaltyEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.repository.BillMasterJpaRepository;
import com.abm.mainet.water.repository.WaterPenaltyRepository;
import com.abm.mainet.water.rest.dto.ViewBillDet;
import com.abm.mainet.water.rest.dto.ViewBillMas;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

/**
 * @author cherupelli.srikanth
 * @since 29 July 2020
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.IViewWaterDetailsService")
@Api(value = "/viewwaterdetailservice")
@Path("/viewwaterdetailservice")
public class ViewWaterDetailsServiceImpl implements IViewWaterDetailsService{

	@Resource
	private NewWaterRepository waterRepository;
	
	@Autowired
	NewWaterConnectionService newWaterConnectionService;
	
	@Autowired
	private TbFinancialyearService financialyearService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
	private IReceiptEntryService iReceiptEntryService;
	
	@Resource
    IOrganisationDAO orgDAO;
	
	@Autowired
    private DuplicateReceiptRepository repository;

	
	@Override
	public ViewCsmrConnectionDTO getWaterViewByConnectionNumber(String connectionNum, Long orgId,String OldConnectionNum) {

		TbCsmrInfoDTO csrinfodto = null;
		ViewCsmrConnectionDTO viewdto = null;
		ViewBillMas mas = null;
		ViewBillDet Billdet = null;
		Long csidn=null;
		if(connectionNum!= null && (!connectionNum.equals(MainetConstants.BLANK)))
		{
			 csidn = waterRepository.getconnIdByConnNo(connectionNum, orgId);
		}
		else if(OldConnectionNum!= null && (!OldConnectionNum.equals(MainetConstants.BLANK)))
		{
			 csidn = waterRepository.getconnIdByOldConnNo(OldConnectionNum, orgId);
		}
		
		if (csidn != null) {
			viewdto = new ViewCsmrConnectionDTO();
			csrinfodto = newWaterConnectionService.getConnectionDetailsById(csidn);
			BeanUtils.copyProperties(csrinfodto, viewdto);
			if (csrinfodto.getCodDwzid1() != null)
				viewdto.setCodDwzid1(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid1(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid2() != null)
				viewdto.setCodDwzid2(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid2(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid3() != null)
				viewdto.setCodDwzid3(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid3(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid4() != null)
				viewdto.setCodDwzid4(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid4(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid5() != null)
				viewdto.setCodDwzid5(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid5(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup1() != null)
				viewdto.setTrmGroup1(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup1(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup2() != null)
				viewdto.setTrmGroup2(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup2(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup3() != null)
				viewdto.setTrmGroup3(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup3(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup4() != null)
				viewdto.setTrmGroup4(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup4(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup5() != null)
				viewdto.setTrmGroup5(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup5(), orgId).getDescLangFirst());
			if(csrinfodto.getCsCcnstatus() != null) {
				viewdto.setCsCcnstatus(CommonMasterUtility.findLookUpDesc("CNS", orgId, csrinfodto.getCsCcnstatus()));
			}
			if(csrinfodto.getCsMeteredccn() != null)
			viewdto.setCsMeteredccn(CommonMasterUtility.findLookUpDesc("WMN", orgId, csrinfodto.getCsMeteredccn()));
			if ("P".equals(csrinfodto.getTypeOfApplication()))
				viewdto.setTypeOfApplication("Permanent");
			else if ("T".equals(csrinfodto.getTypeOfApplication()))
				viewdto.setTypeOfApplication("Temporary");
			viewdto.setCsCcnsize(CommonMasterUtility.findLookUpDesc("CSZ", orgId, csrinfodto.getCsCcnsize()));
			if (csrinfodto.getCsOname() != null) {
				viewdto.setCsOname(csrinfodto.getCsOname());
			} else {
				viewdto.setCsOname(csrinfodto.getCsName());
			}
			if (csrinfodto.getCsOGender() != null) {
				viewdto.setCsOGender(CommonMasterUtility.findLookUpDesc("GEN", orgId, csrinfodto.getCsOGender()));
			} else if(csrinfodto.getCsGender() != null) {
				viewdto.setCsOGender(CommonMasterUtility.findLookUpDesc("GEN", orgId, csrinfodto.getCsGender()));
			}
			
			//billList = billMasterService.getBillMasterListByUniqueIdentifier(csidn, orgId);

			List<TbWtBillMasEntity> fetchBillMasData = ApplicationContextProvider.getApplicationContext().getBean(BillMasterJpaRepository.class).fetchBillMasData(csidn, orgId);
			List<WaterPenaltyEntity> waterPenaltyDtoList = ApplicationContextProvider.getApplicationContext()
					.getBean(WaterPenaltyRepository.class).getWaterPenaltyByCCnNo(String.valueOf(csidn), orgId);
			
//			List<WaterPenaltyDto> waterPenaltyDtoList = ApplicationContextProvider.getApplicationContext()
//			.getBean(IWaterPenaltyService.class).getWaterPenaltyHistoryByCsIdnoAndOrgId(String.valueOf(csidn), orgId);
			if (fetchBillMasData != null && !fetchBillMasData.isEmpty()) {
				for (TbWtBillMasEntity viewBillMas : fetchBillMasData) {
					double totalOutStansing =0;
					mas = new ViewBillMas();
					List<ViewBillDet> listViewBillDet = new ArrayList<>();
					BeanUtils.copyProperties(viewBillMas, mas);
					String financialyear = financialyearService.findYearById(viewBillMas.getBmYear(), orgId)
							.getFaFromDate().toString().substring(0, 4);
					mas.setBmYear(financialyear + "-" + financialyearService
							.findYearById(viewBillMas.getBmYear(), orgId).getFaToDate().toString().substring(2, 4));
					mas.setBmCcnOwner(new SimpleDateFormat("MMM").format(mas.getBmFromdt()) + " "
	                        + Utility.getYearByDate(mas.getBmFromdt()) + "-"
	                        + new SimpleDateFormat("MMM").format(mas.getBmTodt()) + " "
	                        + Utility.getYearByDate(mas.getBmTodt()));
					mas.setBmRemarks(Utility.dateToString(mas.getBmDuedate()));
					WaterPenaltyEntity waterSurchargeDto = null;
					Long bmIdno = mas.getBmIdno();
					if(CollectionUtils.isNotEmpty(waterPenaltyDtoList)) {
						List<WaterPenaltyEntity> waterPenaly = waterPenaltyDtoList.stream()
								.filter(penalty -> penalty.getBmIdNo().equals(bmIdno)).collect(Collectors.toList());
						if(CollectionUtils.isNotEmpty(waterPenaly) && (waterPenaly.get(0).getBillGenAmount() == null || waterPenaly.get(0).getBillGenAmount() == 0)){
							waterSurchargeDto = waterPenaly.get(0);
						}else {
							for (WaterPenaltyEntity penaltyDto : waterPenaltyDtoList) {
								Date createdDate = penaltyDto.getCreateddate();
								if (penaltyDto.getUpdatedDate() != null) {
									createdDate = penaltyDto.getUpdatedDate();
								}
								if (createdDate.compareTo(viewBillMas.getBmFromdt()) >= 0
										&& createdDate.compareTo(viewBillMas.getBmTodt()) <= 0) {
									waterSurchargeDto = penaltyDto;
									break;
								}
							}
						}
					}
					if(waterSurchargeDto != null && waterSurchargeDto.getActualAmount() > 0) {
						if(mas.getWtN1() != null) {
							mas.setSurcharge(waterSurchargeDto.getActualAmount());
						}else {
							mas.setSurcharge(waterSurchargeDto.getPendingAmount());
						}
						Billdet = new ViewBillDet();
						if(mas.getWtN1() != null) {
							Billdet.setBdCurTaxamt(waterSurchargeDto.getActualAmount());
						}else {
							Billdet.setBdCurTaxamt(waterSurchargeDto.getPendingAmount());
						}
						Billdet.setBdPrvArramt(0);
						Billdet.setTaxDesc(tbTaxMasService.findTaxDescByTaxIdAndOrgId(waterSurchargeDto.getTaxId(), orgId));
						Billdet.setTaxName(tbTaxMasService.findTaxDescByTaxIdAndOrgId(waterSurchargeDto.getTaxId(), orgId));
						listViewBillDet.add(Billdet);
					}else {
						mas.setSurcharge(0.0);
					}
					for (TbWtBillDetEntity det : viewBillMas.getBillDetEntity()) {
						Billdet = new ViewBillDet();
						BeanUtils.copyProperties(det, Billdet);
						totalOutStansing = totalOutStansing +(det.getBdPrvArramt() + det.getBdCurTaxamt());
						if (det.getTaxCategory() != null)
							Billdet.setTaxCategory(CommonMasterUtility
									.getHierarchicalLookUp(det.getTaxCategory(), orgId).getDescLangFirst());
						Billdet.setTaxName(tbTaxMasService.findTaxDescByTaxIdAndOrgId(det.getTaxId(), orgId));
						Billdet.setTaxDesc(tbTaxMasService.findTaxDescByTaxIdAndOrgId(det.getTaxId(), orgId));
						listViewBillDet.add(Billdet);
					}
					mas.setBmTotalOutstanding(totalOutStansing);
					mas.setViewBillDet(listViewBillDet);
					viewdto.getViewBillMasList().add(mas);
				}
				
			}
		}

		return viewdto;
	
	}

	@Override
	public List<LookUp> getCollectionDetails(String connectionNo, Organisation org,Long deptId) {
        List<LookUp> dtoList = new ArrayList<>();
        List<TbServiceReceiptMasEntity> entity = iReceiptEntryService.getCollectionDetails(connectionNo,
        		deptId, org.getOrgid());
        if (entity != null && !entity.isEmpty()) {
            entity.forEach(receipt -> {
                LookUp data = new LookUp();
                if (!"RB".equals(receipt.getReceiptTypeFlag())) {
                    data.setLookUpParentId(receipt.getRmRcptno());
                    data.setLookUpId(receipt.getRmRcptid());
                    data.setLookUpCode(Utility.dateToString(receipt.getRmDate()));
                    data.setLookUpType(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(receipt.getReceiptModeDetail().get(0).getCpdFeemode(), org)
                            .getDescLangFirst());
                    data.setOtherField(receipt.getRmAmount().toString());
                    dtoList.add(data);
                }
            });
        }
        return dtoList;
    }

	@Override
	@POST
	@Path("/getWaterViewDet")
	@Transactional(readOnly = true)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public ViewCsmrConnectionDTO getWaterViewDet(TbCsmrInfoDTO infoDto) {
		
		return getWaterViewByConnectionNumber(infoDto.getCsCcn(), infoDto.getOrgId(), infoDto.getCsOldccn());
	}

	@Override
	@POST
	@Path("/getWaterCollDetails")
	@Transactional(readOnly = true)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public List<LookUp> getCollectionViewDetails(TbCsmrInfoDTO infoDto) {
		Organisation orgn =  orgDAO.getOrganisationById(infoDto.getOrgId(), "A");
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		return getCollectionDetails(String.valueOf(infoDto.getCsIdn()), orgn, deptId);
	}
	
	@Override
    @POST
    @Path("/getWaterRevenueReceiptDetails")
    @Transactional(readOnly = true)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
    public ChallanReceiptPrintDTO getRevenueReceiptDetails(TbCsmrInfoDTO requestDto) {
        ChallanReceiptPrintDTO dataDTO = null;
        try {
            TbReceiptDuplicateEntity entity = repository.findByDupRcptByRcptIdAndrcptNoAndRefNo(requestDto.getReceiptId(),
                    Long.valueOf(requestDto.getReceiptNumber()), String.valueOf(requestDto.getCsIdn()));
            if (entity != null) {
                TbReceiptDuplicateDTO rcptPrintDto = new TbReceiptDuplicateDTO();
                BeanUtils.copyProperties(entity, rcptPrintDto);
                dataDTO = new ObjectMapper().readValue(rcptPrintDto.getDupReceiptData(),
                        ChallanReceiptPrintDTO.class);
                final ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class).getServiceByShortName(PrefixConstants.NewWaterServiceConstants.BPW,
                		entity.getOrgId());
                if(requestDto.getLangId() == MainetConstants.ENGLISH){
                	 dataDTO.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
         					+ MainetConstants.WHITE_SPACE + service.getSmServiceName());
                }else {
                	dataDTO.setSubject(ApplicationSession.getInstance().getMessage("receipt.label.receipt.subject")
         					+ MainetConstants.WHITE_SPACE + service.getSmServiceNameMar());
                }
            }
        } catch (IOException e) {
            throw new FrameworkException("Problem while getting respose in duplicate receipt", e);
        }
        return dataDTO;

    }

	
	@Override
	public ViewCsmrConnectionDTO getWaterViewByConnectionNumberForSkdcl(String connectionNum, Long orgId,String OldConnectionNum) {

		TbCsmrInfoDTO csrinfodto = null;
		ViewCsmrConnectionDTO viewdto = null;
		ViewBillMas mas = null;
		ViewBillDet Billdet = null;
		Long csidn=null;
		if(connectionNum!= null && (!connectionNum.equals(MainetConstants.BLANK)))
		{
			 csidn = waterRepository.getconnIdByConnNo(connectionNum, orgId);
		}
		else if(OldConnectionNum!= null && (!OldConnectionNum.equals(MainetConstants.BLANK)))
		{
			 csidn = waterRepository.getconnIdByOldConnNo(OldConnectionNum, orgId);
		}
		
		if (csidn != null) {
			viewdto = new ViewCsmrConnectionDTO();
			csrinfodto = newWaterConnectionService.getConnectionDetailsById(csidn);
			BeanUtils.copyProperties(csrinfodto, viewdto);
			if (csrinfodto.getCodDwzid1() != null)
				viewdto.setCodDwzid1(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid1(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid2() != null)
				viewdto.setCodDwzid2(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid2(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid3() != null)
				viewdto.setCodDwzid3(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid3(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid4() != null)
				viewdto.setCodDwzid4(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid4(), orgId).getDescLangFirst());
			if (csrinfodto.getCodDwzid5() != null)
				viewdto.setCodDwzid5(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getCodDwzid5(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup1() != null)
				viewdto.setTrmGroup1(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup1(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup2() != null)
				viewdto.setTrmGroup2(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup2(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup3() != null)
				viewdto.setTrmGroup3(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup3(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup4() != null)
				viewdto.setTrmGroup4(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup4(), orgId).getDescLangFirst());
			if (csrinfodto.getTrmGroup5() != null)
				viewdto.setTrmGroup5(
						CommonMasterUtility.getHierarchicalLookUp(csrinfodto.getTrmGroup5(), orgId).getDescLangFirst());
			if(csrinfodto.getCsCcnstatus() != null) {
				viewdto.setCsCcnstatus(CommonMasterUtility.findLookUpDesc("CNS", orgId, csrinfodto.getCsCcnstatus()));
			}
			viewdto.setCsMeteredccn(CommonMasterUtility.findLookUpDesc("WMN", orgId, csrinfodto.getCsMeteredccn()));
			if ("P".equals(csrinfodto.getTypeOfApplication()))
				viewdto.setTypeOfApplication("Permanent");
			else if ("T".equals(csrinfodto.getTypeOfApplication()))
				viewdto.setTypeOfApplication("Temporary");
			viewdto.setCsCcnsize(CommonMasterUtility.findLookUpDesc("CSZ", orgId, csrinfodto.getCsCcnsize()));
			if (csrinfodto.getCsOGender() != null) {
				viewdto.setCsOGender(CommonMasterUtility.findLookUpDesc("GEN", orgId, csrinfodto.getCsOGender()));
			}
			
			//billList = billMasterService.getBillMasterListByUniqueIdentifier(csidn, orgId);

			List<TbWtBillMasEntity> fetchBillMasData = ApplicationContextProvider.getApplicationContext().getBean(BillMasterJpaRepository.class).fetchBillMasData(csidn, orgId);
			List<WaterPenaltyEntity> waterPenaltyDtoList = ApplicationContextProvider.getApplicationContext()
					.getBean(WaterPenaltyRepository.class).getWaterPenaltyByCCnNo(String.valueOf(csidn), orgId);
			
//			List<WaterPenaltyDto> waterPenaltyDtoList = ApplicationContextProvider.getApplicationContext()
//			.getBean(IWaterPenaltyService.class).getWaterPenaltyHistoryByCsIdnoAndOrgId(String.valueOf(csidn), orgId);
			if (fetchBillMasData != null && !fetchBillMasData.isEmpty()) {
				for (TbWtBillMasEntity viewBillMas : fetchBillMasData) {
					double totalOutStansing =0;
					mas = new ViewBillMas();
					List<ViewBillDet> listViewBillDet = new ArrayList<>();
					BeanUtils.copyProperties(viewBillMas, mas);
					String financialyear = financialyearService.findYearById(viewBillMas.getBmYear(), orgId)
							.getFaFromDate().toString().substring(0, 4);
					mas.setBmYear(financialyear + "-" + financialyearService
							.findYearById(viewBillMas.getBmYear(), orgId).getFaToDate().toString().substring(2, 4));
					mas.setBmCcnOwner(new SimpleDateFormat("MMM").format(mas.getBmFromdt()) + " "
	                        + Utility.getYearByDate(mas.getBmFromdt()) + "-"
	                        + new SimpleDateFormat("MMM").format(mas.getBmTodt()) + " "
	                        + Utility.getYearByDate(mas.getBmTodt()));
					mas.setBmRemarks(Utility.dateToString(mas.getBmDuedate()));
					WaterPenaltyEntity waterSurchargeDto = null;
					Long bmIdno = mas.getBmIdno();
					if(CollectionUtils.isNotEmpty(waterPenaltyDtoList)) {
						List<WaterPenaltyEntity> waterPenaly = waterPenaltyDtoList.stream()
								.filter(penalty -> penalty.getBmIdNo().equals(bmIdno)).collect(Collectors.toList());
						if(CollectionUtils.isNotEmpty(waterPenaly)) {
							waterSurchargeDto = waterPenaly.get(0);
						}
					}
					if(waterSurchargeDto != null && waterSurchargeDto.getActualAmount() > 0) {
						if(mas.getWtN1() != null) {
							mas.setSurcharge(waterSurchargeDto.getActualAmount());
						}else {
							mas.setSurcharge(waterSurchargeDto.getPendingAmount());
						}
						Billdet = new ViewBillDet();
						if(mas.getWtN1() != null) {
							Billdet.setBdCurTaxamt(waterSurchargeDto.getActualAmount());
						}else {
							Billdet.setBdCurTaxamt(waterSurchargeDto.getPendingAmount());
						}
						Billdet.setBdPrvArramt(0);
						Billdet.setTaxDesc(tbTaxMasService.findTaxDescByTaxIdAndOrgId(waterSurchargeDto.getTaxId(), orgId));
						Billdet.setTaxName(tbTaxMasService.findTaxDescByTaxIdAndOrgId(waterSurchargeDto.getTaxId(), orgId));
						listViewBillDet.add(Billdet);
					}else {
						mas.setSurcharge(0.0);
					}
					for (TbWtBillDetEntity det : viewBillMas.getBillDetEntity()) {
						Billdet = new ViewBillDet();
						BeanUtils.copyProperties(det, Billdet);
						totalOutStansing = totalOutStansing +(det.getBdPrvArramt() + det.getBdCurTaxamt());
						if (det.getTaxCategory() != null)
							Billdet.setTaxCategory(CommonMasterUtility
									.getHierarchicalLookUp(det.getTaxCategory(), orgId).getDescLangFirst());
						Billdet.setTaxName(tbTaxMasService.findTaxDescByTaxIdAndOrgId(det.getTaxId(), orgId));
						Billdet.setTaxDesc(tbTaxMasService.findTaxDescByTaxIdAndOrgId(det.getTaxId(), orgId));
						listViewBillDet.add(Billdet);
					}
					mas.setBmTotalOutstanding(totalOutStansing);
					mas.setViewBillDet(listViewBillDet);
					viewdto.getViewBillMasList().add(mas);
				}
				
			}
		}

		return viewdto;
	
	}
	
}
