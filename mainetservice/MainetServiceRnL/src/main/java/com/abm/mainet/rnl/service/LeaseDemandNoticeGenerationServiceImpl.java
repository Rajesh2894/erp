/**
 * 
 */
package com.abm.mainet.rnl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.objection.domain.NoticeMasterEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.LeaseDemandNoticeGenerationDTO;
import com.abm.mainet.rnl.repository.LeaseDemandNoticeGenerationRepository;

/**
 * @author divya.marshettiwar
 *
 */
@Service
public class LeaseDemandNoticeGenerationServiceImpl implements LeaseDemandNoticeGenerationService{
	
	@Resource
    private LeaseDemandNoticeGenerationRepository leaseDemandNoticeGenerationRepository;
	
	@Autowired
    private TbDepartmentService iTbDepartmentService;
	
	@Override
	public LeaseDemandNoticeGenerationDTO saveNoticeForm(LeaseDemandNoticeGenerationDTO demandNoticeDto,Organisation orgId) {
		LeaseDemandNoticeGenerationDTO dto = new LeaseDemandNoticeGenerationDTO();
		NoticeMasterEntity noticeEntity = new NoticeMasterEntity();

		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long dpDeptId = dept.getDpDeptid();

		BeanUtils.copyProperties(demandNoticeDto, noticeEntity);
		
		LookUp noticeTypeByLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(demandNoticeDto.getNotTyp(), demandNoticeDto.getOrgId(), "RNG");

		noticeEntity.setNotTyp(demandNoticeDto.getNotTyp());
		noticeEntity.setDpDeptid(dpDeptId);
		noticeEntity.setNotDate(new Date());
		noticeEntity.setOrgId(demandNoticeDto.getOrgId());
		noticeEntity.setCreationDate(demandNoticeDto.getCreatedDate());
		
		String generateNoticeNumber = generateNoticeNumber(noticeTypeByLookup, orgId);
		noticeEntity.setNotNo(generateNoticeNumber);
		
		BeanUtils.copyProperties(noticeEntity, dto);
		
		leaseDemandNoticeGenerationRepository.save(noticeEntity);
		return dto;
	}
	
	@Override
	public void saveNoticeFormList(List<LeaseDemandNoticeGenerationDTO> demandNoticeDtoList, Long notTyp, Organisation orgId) {
		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long dpDeptId = dept.getDpDeptid();
	
		List<NoticeMasterEntity> list = new ArrayList<>();
		
		for(LeaseDemandNoticeGenerationDTO demandDto : demandNoticeDtoList) {
			NoticeMasterEntity noticeEntityList = new NoticeMasterEntity();
			BeanUtils.copyProperties(demandDto, noticeEntityList);
			noticeEntityList.setNotTyp(notTyp);
			noticeEntityList.setDpDeptid(dpDeptId);
			noticeEntityList.setNotDate(new Date());
			noticeEntityList.setOrgId(demandDto.getOrgId());
			noticeEntityList.setCreationDate(demandDto.getCreatedDate());
			
			LookUp noticeTypeByLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(notTyp, demandDto.getOrgId(), "RNG");
			String generateNoticeNumber = generateNoticeNumber(noticeTypeByLookup, orgId);
			noticeEntityList.setNotNo(generateNoticeNumber);
			
			list.add(noticeEntityList);
		}
		leaseDemandNoticeGenerationRepository.save(list);
	}

	@Override
    @WebMethod(exclude = true)
    public String generateNoticeNumber(LookUp noticeTypeByLookup, Organisation org) {
		String noticeNumber = null;
		Long noticeType = null;
		
		String noticeFinYear = null;
        StringBuilder demandNoticeNumber = new StringBuilder();
        
		FinancialYear financialYear = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
        if (financialYear != null) {
        	noticeFinYear = Utility.getCurrentFinancialYear();
        }
        
        if(noticeTypeByLookup.getLookUpCode().equals("RRN1")) {
        	demandNoticeNumber.append("Notice");
        	noticeType = noticeTypeByLookup.getLookUpId();
        }else {
        	demandNoticeNumber.append("Reminder");
        	noticeType = noticeTypeByLookup.getLookUpId();
        }   	
		Long generateSequenceNo = ApplicationContextProvider.getApplicationContext()
                .getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(MainetConstants.RNL_DEPT_CODE, "tb_notice_mas",
                		"NOT_NO", org.getOrgid(), null, noticeType);
		          
        demandNoticeNumber.append("/");
        demandNoticeNumber.append(noticeFinYear);
        demandNoticeNumber.append("/");
        demandNoticeNumber.append(String.format("%06d", generateSequenceNo));
        
        noticeNumber = demandNoticeNumber.toString();
		return noticeNumber;
	}
	
	@Override
	public List<LeaseDemandNoticeGenerationDTO> findPropertyNo(Long orgId, String refNo) {
		List<LeaseDemandNoticeGenerationDTO> propDtoList = new ArrayList<LeaseDemandNoticeGenerationDTO>();
		try {
			List<Object[]> propentity = leaseDemandNoticeGenerationRepository.findByPropertyNo(orgId, refNo);			
			if (propentity != null) {
				propentity.forEach(entity -> {
					LeaseDemandNoticeGenerationDTO propDto = new LeaseDemandNoticeGenerationDTO();
					if (entity != null) {
						//BeanUtils.copyProperties(entity, propDto);
						propDto.setRefNo(String.valueOf(entity[0]));
						if(entity[1] != null) {
							propDto.setFromDate((Date) entity[1]);
						}
						if(entity[2] != null) {
							propDto.setToDate((Date) entity[2]);
						}
						if(entity[3] != null) {
							propDto.setPendingAmount(String.valueOf(entity[3]));
						}
					}
					propDtoList.add(propDto);
				});
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the Property Number", exception);
		}
		return propDtoList;
	}
	
	@Override
	public List<LeaseDemandNoticeGenerationDTO> findByLocationId(Long orgId, Long locationId) {
		List<LeaseDemandNoticeGenerationDTO> list = new ArrayList<>();
		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long dpDeptId = dept.getDpDeptid();
		try {
			List<Object[]> propentity = leaseDemandNoticeGenerationRepository.findByLocationId(orgId, locationId);
			if (propentity != null) {
				
				for(Object[] entity : propentity) {
					LeaseDemandNoticeGenerationDTO propDto = new LeaseDemandNoticeGenerationDTO();
					if (entity != null) {
						propDto.setRefNo(String.valueOf(entity[0]));
						if(entity[1] != null) {
							propDto.setFromDate((Date) entity[1]);
						}
						if(entity[2] != null) {
							propDto.setToDate((Date) entity[2]);
						}
						if(entity[3] != null) {
							propDto.setPendingAmount(String.valueOf(entity[3]));
						}
					}
					list.add(propDto);
				}
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the data", exception);
		}
		return list;
	}
	
	@Override
	public List<LeaseDemandNoticeGenerationDTO> findSecondReminderNotice(Long notTyp, Long orgId) {
		List<LeaseDemandNoticeGenerationDTO> list = new ArrayList<>();
		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long dpDeptId = dept.getDpDeptid();
		try {
			List<NoticeMasterEntity> noticeentity = leaseDemandNoticeGenerationRepository
					.getSecondReminderNotice(notTyp, dpDeptId, orgId);
			
			if(noticeentity != null) {
				noticeentity.forEach(entity -> {
					LeaseDemandNoticeGenerationDTO propDto = new LeaseDemandNoticeGenerationDTO();
					if (entity != null) {
						BeanUtils.copyProperties(entity, propDto);
						LeaseDemandNoticeGenerationDTO propertyData = findPropertyNo(orgId, propDto.getRefNo()).get(0);
						propDto.setFromDate(propertyData.getFromDate());
						propDto.setToDate(propertyData.getToDate());
						propDto.setPendingAmount(propertyData.getPendingAmount());
					}
					list.add(propDto);
				});
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the data", exception);
		}
		return list;
	}
	
	@Override
	public LeaseDemandNoticeGenerationDTO findFirstReminderNotice(Long notTyp, String refNo, Long orgId) {
		LeaseDemandNoticeGenerationDTO propDto= new LeaseDemandNoticeGenerationDTO();
		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long dpDeptId = dept.getDpDeptid();
		try {
			NoticeMasterEntity noticeentity = leaseDemandNoticeGenerationRepository.getfirstReminderNotice(notTyp, refNo, dpDeptId, orgId);
			if(noticeentity != null) {
				BeanUtils.copyProperties(noticeentity, propDto);
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the data", exception);
		}
		return propDto;
	}

	@Override
	public List<LeaseDemandNoticeGenerationDTO> getfirstReminderNoticeByNoticeType(Long notTyp, Long orgId){
		List<LeaseDemandNoticeGenerationDTO> list = new ArrayList<>();
		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long dpDeptId = dept.getDpDeptid();
		try {
			List<NoticeMasterEntity> noticeentity = leaseDemandNoticeGenerationRepository
					.getfirstReminderNoticeByNoticeType(notTyp, dpDeptId, orgId);
			if(noticeentity != null) {
				noticeentity.forEach(entity -> {
					LeaseDemandNoticeGenerationDTO propDto = new LeaseDemandNoticeGenerationDTO();
					if (entity != null) {
						BeanUtils.copyProperties(entity, propDto);
					}
					list.add(propDto);
				});
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the data", exception);
		}
		return list;
	}
}
