package com.abm.mainet.vehiclemanagement.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.vehiclemanagement.dao.IOEMWarrantyServiceDAO;
import com.abm.mainet.vehiclemanagement.dao.IVehicleMasterDAO;
import com.abm.mainet.vehiclemanagement.domain.OEMWarranty;
import com.abm.mainet.vehiclemanagement.domain.OEMWarrantyDetails;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDetDTO;
import com.abm.mainet.vehiclemanagement.mapper.GeVehicleMasterMapper;
import com.abm.mainet.vehiclemanagement.mapper.OEMWarrantyMapper;
import com.abm.mainet.vehiclemanagement.repository.OEMWarrantyServiceRepository;

@Service
public class OEMWarrantyService implements IOEMWarrantyService {

	@Autowired
	private OEMWarrantyServiceRepository oemWarrantyServiceRepository;

	@Autowired
	private OEMWarrantyMapper oemWarrantyMapper;

	@Autowired
	private IOEMWarrantyServiceDAO iOemWarrantyServiceDAO;

	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
    private IVehicleMasterDAO vehicleMasterDAO;
	
	@Autowired
    private GeVehicleMasterMapper vehicleMasterMapper;
	
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public OEMWarrantyDTO saveOemWarranty(OEMWarrantyDTO oemWarrantyDto) {

		List<OEMWarrantyDetails> parentEntityList = new ArrayList<OEMWarrantyDetails>();
		OEMWarranty oemWarranty = new OEMWarranty();
		BeanUtils.copyProperties(oemWarrantyDto, oemWarranty);
		oemWarrantyDto.getTbvmoemwarrantydetails().forEach(data -> {
			OEMWarrantyDetails oemDet = new OEMWarrantyDetails();
			BeanUtils.copyProperties(data, oemDet);
			oemDet.setTboemwarranty(oemWarranty);
			parentEntityList.add(oemDet);
		});
		oemWarranty.setTbvmoemwrrantydetails(parentEntityList);
		oemWarrantyServiceRepository.save(oemWarranty);
		return oemWarrantyDto;
	}
	/*
	 * OEMWarranty
	 * oemWarranty=oemWarrantyMapper.mapVehicleScheduleDTOToVehicleSchedule(
	 * oemWarrantyDto);
	 * 
	 * oemWarranty=oemWarrantyServiceRepository.save(oemWarranty); return
	 * oemWarrantyMapper.mapVehicleScheduleToVehicleScheduleDTO(oemWarranty);
	 */

	@Override
	public OEMWarrantyDTO updateOemWarranty(OEMWarrantyDTO oemWarrantyDto) {
		OEMWarranty master = oemWarrantyMapper.mapVehicleScheduleDTOToVehicleSchedule(oemWarrantyDto);
        master = oemWarrantyServiceRepository.save(master);
       // saveHistory(master, MainetConstants.Transaction.Mode.UPDATE);
        return oemWarrantyMapper.mapVehicleScheduleToVehicleScheduleDTO(master);
	}

	@Override
	public boolean oemWarrantyValidate(OEMWarrantyDTO OEMWarrantyDTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<OEMWarrantyDTO> searchOemWarrantyDetails(Long department, Long vehicleType, Long veNo, Long orgid) {

		List<OEMWarrantyDTO> listLogBookDTOs = new ArrayList<OEMWarrantyDTO>();
		List<OEMWarranty> list = iOemWarrantyServiceDAO.searchOemWarrantyDetails(department, vehicleType, veNo, orgid);

		for (int i = 0; i < list.size(); i++) {
			for(int j=0;j<list.get(i).getTbvmoemwrrantydetails().size();j++) {
				OEMWarrantyDTO dto = new OEMWarrantyDTO();
				dto.setOemId(list.get(i).getOemId());
				dto.setPartName(list.get(i).getTbvmoemwrrantydetails().get(j).getPartName());
				dto.setWarrantyPeriod((list.get(i).getTbvmoemwrrantydetails().get(j).getWarrantyPeriod()));
				dto.setLastDateOfWarranty((list.get(i).getTbvmoemwrrantydetails().get(j).getLastDateOfWarranty()));
				dto.setPurchaseDate((list.get(i).getTbvmoemwrrantydetails().get(j).getPurchaseDate()));
				dto.setPartType((list.get(i).getTbvmoemwrrantydetails().get(j).getPartType()));
				listLogBookDTOs.add(dto);
			}
		}

		return listLogBookDTOs;
	}

	@Override
	public List<OEMWarrantyDTO> getAllVehicles(Long orgid) {
		List<Object[]> list = vehicleMasterService.getAllVehiclesWithoutEmp(orgid);
		List<OEMWarrantyDTO> listDto = new ArrayList<OEMWarrantyDTO>();
		for (Object[] obj : list) {
			OEMWarrantyDTO dto = new OEMWarrantyDTO();
			// dto.setDeptDesc(obj[0].toString());
			dto.setVeNo(obj[0].toString());
			dto.setVehicleTypeDesc(obj[1].toString());
			listDto.add(dto);
		}

		return listDto;
	}

	@Override
	@GET
	@Transactional(readOnly = true)
	public OEMWarrantyDTO getOemWarrantyDetails(@PathParam("id") Long oemId) {
		OEMWarranty oem = oemWarrantyServiceRepository.findOne(oemId);
		OEMWarrantyDTO oemDto = new OEMWarrantyDTO();
		BeanUtils.copyProperties(oem, oemDto);
		List<OEMWarrantyDetDTO> dtoList = new ArrayList<OEMWarrantyDetDTO>();
		oem.getTbvmoemwrrantydetails().forEach(entity -> {
			OEMWarrantyDetDTO dto = new OEMWarrantyDetDTO();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
		});
		oemDto.setTbvmoemwarrantydetails(dtoList);
		oemDto.setDeptDesc(departmentService.fetchDepartmentDescById(oem.getDepartment()));
		// return oemWarrantyMapper.mapVehicleScheduleToVehicleScheduleDTO(oem);
		return oemDto;
	}

	@Override
	public List<OEMWarrantyDTO> getVehicleByNo(Long orgid, Long veNo) {
		List<Object[]> list = vehicleMasterService.getVehicleByNo(veNo, orgid);
		List<OEMWarrantyDTO> listDto = new ArrayList<OEMWarrantyDTO>();
		for (Object[] obj : list) {
			OEMWarrantyDTO dto = new OEMWarrantyDTO();
			dto.setVeId(Long.valueOf((Long)obj[0]));
			dto.setVeNo(obj[1].toString());
			listDto.add(dto);
		}
		return listDto;
	}

	@Override
	@Transactional
	public Boolean searchVehicleByVehicleTypeAndVehicleRegNo(OEMWarrantyDTO dto, long orgid) {
		boolean flag=true;
		List<GenVehicleMasterDTO> vehList = vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(dto.getVeId(), null, MainetConstants.FlagY, null, null, null, orgid));           
		for (int i = 0; i < vehList.size(); i++) {
			for(int j=0; j<dto.getTbvmoemwarrantydetails().size(); j++) {
				if ((vehList.get(i).getVePurDate()).after(dto.getTbvmoemwarrantydetails().get(j).getPurchaseDate())) {
					flag = false;
					break;
				}
			}			
		}
		return flag;
	}

	@Override
	public OEMWarrantyDTO findByrefNoAndOrgId(String refNo, Long orgId) {
		OEMWarranty oem = oemWarrantyServiceRepository.findByRefNoAndOrgid(refNo, orgId);
		OEMWarrantyDTO oemDto = new OEMWarrantyDTO();
		if(null != oem) {
			BeanUtils.copyProperties(oem, oemDto);
			List<OEMWarrantyDetDTO> dtoList = new ArrayList<OEMWarrantyDetDTO>();
			oem.getTbvmoemwrrantydetails().forEach(entity -> {
				OEMWarrantyDetDTO dto = new OEMWarrantyDetDTO();
				BeanUtils.copyProperties(entity, dto);
				dtoList.add(dto);
			});
			oemDto.setTbvmoemwarrantydetails(dtoList);
			oemDto.setDeptDesc(departmentService.fetchDepartmentDescById(oem.getDepartment()));
			// return oemWarrantyMapper.mapVehicleScheduleToVehicleScheduleDTO(oem);
		}
		return oemDto;
	}

	/*@Override
	@Transactional(readOnly = true)
	public List<OEMWarrantyDTO> getVehicleByNo(Long orgid, Long veNo) {
		List<Object[]> list = vehicleMasterService.getVehicleByNo(veNo, orgid);
		List<OEMWarrantyDTO> listDto = new ArrayList<OEMWarrantyDTO>();
		for (Object[] obj : list) {
			OEMWarrantyDTO dto = new OEMWarrantyDTO();
			dto.setVeId(Long.valueOf((Long)obj[0]));
			dto.setVeNo(obj[1].toString());
			listDto.add(dto);
		}
		return listDto;
	}*/
	
	/*@Override
	@Transactional(readOnly = true)
	public List<OEMWarrantyDTO> getVehicleByNumber(Long orgid, Long veid) {
		List<Object[]> list = vehicleMasterService.getVehicleByNumber(veid, orgid);
		List<OEMWarrantyDTO> listDto = new ArrayList<OEMWarrantyDTO>();
		for (Object[] obj : list) {
			OEMWarrantyDTO dto = new OEMWarrantyDTO();
			dto.setVeId(Long.valueOf((Long)obj[0]));
			dto.setVeNo(obj[1].toString());
			listDto.add(dto);
		}
		return listDto;
	}*/
		
	
}
