/**
 * 
 */
package com.abm.mainet.additionalservices.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.dao.EChallanEntryDao;
import com.abm.mainet.additionalservices.domain.EChallanItemDetailsEntity;
import com.abm.mainet.additionalservices.domain.EChallanMasterEntity;
import com.abm.mainet.additionalservices.dto.EChallanItemDetailsDto;
import com.abm.mainet.additionalservices.dto.EChallanMasterDto;
import com.abm.mainet.additionalservices.repository.EChallanEntryRepository;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;


@Service
public class EChallanEntryServiceImpl implements EChallanEntryService{
	
	private static final Logger LOGGER = Logger.getLogger(EChallanEntryServiceImpl.class);
	
	@Autowired
	private EChallanEntryRepository eChallanEntryRepository;
	
	@Autowired
	private EChallanEntryDao challanDao;
	
	@Resource
	private IFileUploadService fileUploadService;
	
	@Autowired
	private IReceiptEntryService  iReceiptEntryService;
	
	@Autowired
	private IDuplicateReceiptService iDuplicateReceiptService;
	
	@Autowired
	private ServiceMasterService seviceMasterService;

	@Override
	@Transactional
	public void saveEChallanEntry(EChallanMasterDto challanMasterDto,List<EChallanItemDetailsDto> challanItemDetailsDtoList, List<Long> removeItemIdsList) {
		EChallanMasterEntity eChallanMasterEntity = new EChallanMasterEntity();
		
		final RequestDTO commonRequest = new RequestDTO();
		
		if (challanMasterDto.getChallanType() != null && challanMasterDto.getChallanType().equals("OS")) {
			commonRequest.setReferenceId(challanMasterDto.getChallanNo());
		}else {
			commonRequest.setReferenceId(challanMasterDto.getRaidNo());
		}
		
		commonRequest.setOrgId(challanMasterDto.getOrgid());
		commonRequest.setLangId(challanMasterDto.getLangId());
		commonRequest.setServiceId(challanMasterDto.getServiceId());
		commonRequest.setDeptId(challanMasterDto.getDeptId());
		
		if ((challanMasterDto.getDocList() != null) && !challanMasterDto.getDocList().isEmpty()) {
			fileUploadService.doFileUpload(challanMasterDto.getDocList(), commonRequest);
		}
		
		BeanUtils.copyProperties(challanMasterDto,eChallanMasterEntity);
		List<EChallanItemDetailsEntity> itemDetailsEntityList = new ArrayList();
		
		AtomicInteger seqNo = new AtomicInteger(1);
		challanItemDetailsDtoList.forEach(itemDto -> {
			EChallanItemDetailsEntity itemEntity = new EChallanItemDetailsEntity();
			BeanUtils.copyProperties(itemDto,itemEntity);
			
			//to generate the item number on edit
			if(itemDto.getItemNo() == null) {
				String raidNo="";
				if (challanMasterDto.getChallanType().equals("OS")) {
				 raidNo = challanMasterDto.getChallanNo();
				}
				else {
					raidNo = challanMasterDto.getRaidNo();
				}
					StringBuilder itemNo = new StringBuilder();
					itemNo.append(raidNo);
					itemNo.append("00");
					seqNo.getAndAdd(1);
					itemNo.append(seqNo.toString());

					itemDto.setItemNo(itemNo.toString());		
			}
			
			itemEntity.setItemNo(itemDto.getItemNo());
			itemEntity.setChallanMaster(eChallanMasterEntity);
			itemEntity.setStatus("A");
			itemEntity.setOrgid(1L);
			itemEntity.setCreatedBy(challanMasterDto.getCreatedBy());
			itemEntity.setCreatedDate(challanMasterDto.getCreatedDate());
			itemDetailsEntityList.add(itemEntity);
		});
		eChallanMasterEntity.setChallanItemDetails(itemDetailsEntityList);
		
		eChallanEntryRepository.save(eChallanMasterEntity);
		if (removeItemIdsList != null && removeItemIdsList.size() != 0) {
			eChallanEntryRepository.removeItemIds(challanMasterDto.getUpdatedBy(), removeItemIdsList);
        }
		
	}
	
	@Override
    @WebMethod(exclude = true)
    public String generateChallanNumber(Organisation org,List<EChallanItemDetailsDto> challanItemDetailsDtoList) {
		String challanNumber = null;
		 Long generateSequenceNo = ApplicationContextProvider.getApplicationContext()
	                .getBean(SeqGenFunctionUtility.class)
	                .generateSequenceNo(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE, "Tb_Echallan_Mst",
	                		"CHLN_NO", org.getOrgid(), null, null);
		 
		 FinancialYear financialYear = ApplicationContextProvider.getApplicationContext()
	                .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
	        int chnFinYear = 0;
	        StringBuilder chnNumber = new StringBuilder();
	       
	        if (financialYear != null) {
	        	chnFinYear = Utility.getCurrentYear();
	        }
	        chnNumber.append(chnFinYear);
	        chnNumber.append(String.format("%06d", generateSequenceNo));
	        challanNumber = chnNumber.toString();
	    return challanNumber;
	}
	
	@Override
    @WebMethod(exclude = true)
    public String generateRaidNumber(Organisation org,List<EChallanItemDetailsDto> challanItemDetailsDtoList) {
		String raidNumber = null;
		Long generateSequenceNo = ApplicationContextProvider.getApplicationContext()
                .getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(MainetConstants.EncroachmentChallan.RAID_NO, "Tb_Echallan_Itemdet",
                		"RAID_NO", org.getOrgid(), null, null);
		StringBuilder raidno = new StringBuilder();
		raidno.append(Utility.getCurrentYear());
		raidno.append(String.format("%01d", generateSequenceNo));
		
		//AtomicDouble itemnoSeq = new AtomicDouble(1);
		AtomicInteger seqNo = new AtomicInteger(1);
		challanItemDetailsDtoList.forEach(item ->{
			StringBuilder itemNo = new StringBuilder();
			itemNo.append(raidno);
			itemNo.append("00");
			itemNo.append(seqNo.toString());
			seqNo.getAndAdd(1);
			
			 //itemNo.append(String.format("%02d", generateSequenceNo));
			 //itemNo.append(String.valueOf(itemnoSeq));
			// itemnoSeq += 1;
			 item.setItemNo(itemNo.toString());
		});
		raidNumber = raidno.toString();
		return raidNumber;
	}
	
	@Override
	public List<EChallanMasterDto> searchChallanDetailsList(String challanNo, String raidNo, String offenderName, Date challanFromDate,
			Date challanToDate, String offenderMobNo, String challanType, Long orgid) {

		if (challanFromDate != null && challanToDate != null) {
			List<EChallanMasterEntity> dateentity = challanDao.challanDetailsFromDates(challanFromDate, challanToDate, challanType);
			List<EChallanMasterDto> challanDateDto = new ArrayList<EChallanMasterDto>();
			
			if(dateentity != null) {
				dateentity.forEach(entity -> {
					EChallanMasterDto dto = new EChallanMasterDto();
					
					if (entity != null) {
						BeanUtils.copyProperties(entity, dto);
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						String chlnDate = dateFormat.format(dto.getChallanDate());
						dto.setDatechallan(chlnDate);
					}
					
					// to search table data
					List<EChallanItemDetailsDto> itemDtolist = new ArrayList<EChallanItemDetailsDto>();
					entity.getChallanItemDetails().forEach(itementity -> {
						EChallanItemDetailsDto itemddto = new EChallanItemDetailsDto();
						if (itementity != null) {
							BeanUtils.copyProperties(itementity, itemddto); 
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							String chlnDate = dateFormat.format(dto.getChallanDate());
							dto.setDatechallan(chlnDate);
						}
						itemDtolist.add(itemddto);
					});
					dto.setEchallanItemDetDto(itemDtolist);
					challanDateDto.add(dto);
				});			
			}
			
			return challanDateDto;
		} else {

			List<EChallanMasterEntity> challanEntity = challanDao.searchData(challanNo, raidNo, 
					offenderName, offenderMobNo, challanType, orgid);
			List<EChallanMasterDto> challanDto = new ArrayList<EChallanMasterDto>();
			if (challanEntity != null) {
				challanEntity.forEach(entity -> {
					EChallanMasterDto dto = new EChallanMasterDto();
					
					if (entity != null) {
						BeanUtils.copyProperties(entity, dto);
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						String chlnDate = dateFormat.format(dto.getChallanDate());
						dto.setDatechallan(chlnDate);
					}
					
					// to search table data
					List<EChallanItemDetailsDto> itemDtolist = new ArrayList<EChallanItemDetailsDto>();
					entity.getChallanItemDetails().forEach(itementity -> {
						EChallanItemDetailsDto itemddto = new EChallanItemDetailsDto();
						if (itementity != null) {
							BeanUtils.copyProperties(itementity, itemddto); 
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							String chlnDate = dateFormat.format(dto.getChallanDate());
							dto.setDatechallan(chlnDate);
						}
						itemDtolist.add(itemddto);
					});
					dto.setEchallanItemDetDto(itemDtolist);
					challanDto.add(dto);
				});
			}
			return challanDto;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public EChallanMasterDto getEChallanMasterByOrgidAndChallanId(Long orgid, Long challanId) {
		EChallanMasterDto challanMasterDto = null;
		List<EChallanMasterDto> challanMasterDtoList = new ArrayList<>();
		
		List<EChallanItemDetailsDto> itemDtoList = new ArrayList<>();	
		EChallanItemDetailsDto itemDto;
		
		try {
			EChallanMasterEntity entity = eChallanEntryRepository.findByOrgIdAndChallanId(orgid, challanId);
			if (entity != null) {
				challanMasterDto = new EChallanMasterDto();
				BeanUtils.copyProperties(entity, challanMasterDto);
				
				if(challanMasterDto.getChallanDate() != null) {
				  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
				  String datechallan = dateFormat.format(challanMasterDto.getChallanDate());
				  challanMasterDto.setDatechallan(datechallan);
				}
				
				for(EChallanItemDetailsEntity itemEntity : entity.getChallanItemDetails()) {
					itemDto = new EChallanItemDetailsDto();
					BeanUtils.copyProperties(itemEntity, itemDto);
					
					itemDtoList.add(itemDto);
				}
				challanMasterDto.getEchallanItemDetDto().addAll(itemDtoList);
			}
			
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the EChallan challanId", exception);
		}
		return challanMasterDto;
	}
	
	@Override
	@Transactional
	public List<EChallanMasterDto> searchRaidDetailsList(String raidNo, String offenderName, Date challanFromDate,
			Date challanToDate, String offenderMobNo,String challanType, Long orgid) {
		//EChallanMasterDto masterdto = new EChallanMasterDto();

		if (challanFromDate != null && challanToDate != null) {
			List<EChallanMasterEntity> dateentity = challanDao.raidDetailsFromDates(challanFromDate, challanToDate, challanType);
			List<EChallanMasterDto> raidDateDto = new ArrayList<EChallanMasterDto>();

			if (dateentity != null) {
				dateentity.forEach(entity -> {
					EChallanMasterDto dto = new EChallanMasterDto();

					if (entity != null) {
						BeanUtils.copyProperties(entity, dto);
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						String chlnDate = dateFormat.format(dto.getChallanDate());
						dto.setDatechallan(chlnDate);
					}
					raidDateDto.add(dto);
				});
			}

			// BeanUtils.copyProperties(dateentity, raidDateDto);

			return raidDateDto;
		} else {
			List<EChallanMasterEntity> challanEntity = challanDao.searchRaidData(raidNo, offenderName, offenderMobNo, challanType, orgid);
				List<EChallanMasterDto> raidDto = new ArrayList<EChallanMasterDto>();

				if (challanEntity != null) {
					challanEntity.forEach(entity -> {
						EChallanMasterDto dto = new EChallanMasterDto();
						if (entity != null) {
							BeanUtils.copyProperties(entity, dto);
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							String chlnDate = dateFormat.format(dto.getChallanDate());
							dto.setDatechallan(chlnDate);
						}

						List<EChallanItemDetailsDto> itemDtolist = new ArrayList<EChallanItemDetailsDto>();
						entity.getChallanItemDetails().forEach(itementity -> {
							EChallanItemDetailsDto itemddto = new EChallanItemDetailsDto();
							if (itementity != null) {
								BeanUtils.copyProperties(itementity, itemddto);
								SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
								String chlnDate = dateFormat.format(dto.getChallanDate());
								dto.setDatechallan(chlnDate);
							}
							itemDtolist.add(itemddto);
						});
						dto.setEchallanItemDetDto(itemDtolist);
						raidDto.add(dto);
					});
				}
				return raidDto;
			}
	}
	
	@Override
	@Consumes("application/json")
	@POST
	@Path("/getDuplicateReceiptDetail/{challanId}/{orgId}")
	@Transactional(readOnly = true)
	public ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long challanId, Long orgId) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		TbServiceReceiptMasEntity receiptMasterEntity = new TbServiceReceiptMasEntity();
		ChallanReceiptPrintDTO receiptDto = new ChallanReceiptPrintDTO();
		
		ServiceMaster serviceMas = seviceMasterService.getServiceByShortName(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE,
				orgId);
		Long deptId = serviceMas.getTbDepartment().getDpDeptid();
		
		EChallanMasterDto masterDto = getEChallanMasterByOrgidAndChallanId(orgId, challanId);
		
		if(masterDto.getChallanType().equals("OS")) {
			receiptMasterEntity  = iReceiptEntryService.getMaxReceiptIdByAdditinalRefNoAndDeptId(masterDto.getChallanNo(),deptId);
		}else {
			receiptMasterEntity  = iReceiptEntryService.getMaxReceiptIdByAdditinalRefNoAndDeptId(masterDto.getRaidNo(),deptId);
			if(receiptMasterEntity == null) { 
				return null; 
			}
			 		
		}	
		
		TbServiceReceiptMasBean receiptMasBean = iReceiptEntryService.findReceiptById(receiptMasterEntity.getRmRcptid(), orgId);		 
		receiptDto = iDuplicateReceiptService.getRevenueReceiptDetails(receiptMasBean.getRmRcptid(), 
				receiptMasBean.getRmRcptno(), receiptMasBean.getAdditionalRefNo(),orgId,0);
		if(receiptDto != null) {
			receiptDto.setPaymentMode(CommonMasterUtility
					.getNonHierarchicalLookUpObject(receiptMasBean.getReceiptModeDetailList().getCpdFeemode(), organisation)
					.getLookUpDesc());
		}		
		return receiptDto;
	}
	
	@Override
	public boolean findFromReceiptMaster(Long orgid, Long raidNo) {
		TbServiceReceiptMasEntity receiptMasterEntity = null;
		ServiceMaster serviceMas = seviceMasterService.getServiceByShortName(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE,
				orgid);
		Long deptId = serviceMas.getTbDepartment().getDpDeptid();
		receiptMasterEntity  = iReceiptEntryService.getMaxReceiptIdByAdditinalRefNoAndDeptId(raidNo.toString(), deptId);
		
		if(receiptMasterEntity == null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePaymentStatus(Long challanId, String status, Long orgId) {
		eChallanEntryRepository.updatePaymentStatus(challanId, status, orgId);
	}

}