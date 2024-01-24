package com.abm.mainet.firemanagement.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.dao.ILogBookDao;
import com.abm.mainet.firemanagement.domain.FmVehicleLogBook;
import com.abm.mainet.firemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.firemanagement.repository.LogBookRepository;
//import com.abm.mainet.vehicle.management.service.IGenVehicleMasterService;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

@Service
public class LogBookServiceImpl implements ILogBookService {

	private static final Logger LOGGER = Logger.getLogger(FireCallRegisterService.class);
	
	@Autowired
	private LogBookRepository logBookRepo;

	@Autowired
	private ILogBookDao iLogBookDao;

	//@Autowired
	//private IGenVehicleMasterService vehicleMasterService;

	@Override
	// @Transactional(rollbackFor = Exception.class)
	public VehicleLogBookDTO saveVehicleDetails(VehicleLogBookDTO vehicleLogBookDto) {
		FmVehicleLogBook vlbook = new FmVehicleLogBook();
		BeanUtils.copyProperties(vehicleLogBookDto, vlbook);
		vlbook.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		vlbook.setVeNo(vehicleLogBookDto.getVeNo());
		vlbook.setDriverId(vehicleLogBookDto.getDriverId());
		// vlbook.setVeNo(vehicleLogBookDto.getVeNo());
		vlbook = logBookRepo.save(vlbook);
		BeanUtils.copyProperties(vlbook, vehicleLogBookDto);
		return vehicleLogBookDto;
	}

	@Override
	public List<VehicleLogBookDTO> getAllRecord(Long orgid) {
		List<FmVehicleLogBook> listOccuranceBookEntities = logBookRepo.findByOrgid(orgid);
		// @SuppressWarnings("unused")
		// List<Object[]> listss=vehicleMasterService.getAllVehicles(orgid);
		List<VehicleLogBookDTO> dtoList = new ArrayList<VehicleLogBookDTO>();
		listOccuranceBookEntities.forEach(entity -> {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();
			BeanUtils.copyProperties(entity, dto);
			EmployeeBean employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
					.findById((entity.getDriverId()));
			if(employee!=null && employee.getFullName()!=null)
			dto.setDriverName(employee.getFullName());
			dtoList.add(dto);
		});

		return dtoList;
	}

	public List<VehicleLogBookDTO> searchFireCallRegisterwithDate(Date fromDate, Date toDate, String veNo, Long orgid) {
		List<VehicleLogBookDTO> listLogBookDTOs = new ArrayList<VehicleLogBookDTO>();
		List<FmVehicleLogBook> list = iLogBookDao.searchFireCallRegisterwithDate(fromDate, toDate, veNo, orgid);
		list.forEach(entity -> {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();
			BeanUtils.copyProperties(entity, dto);
			EmployeeBean employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
					.findById((entity.getDriverId()));
			dto.setDriverName(employee.getFullName());
			listLogBookDTOs.add(dto);
		});
		return listLogBookDTOs;
	}
	
	@Override
	public List<String> getVehicleNoListByFromTodate(Date fromDate, Date toDate, Long orgid) {
		return logBookRepo.getVehicleNoList(fromDate, toDate, orgid);
	}
	

	@Override
	public VehicleLogBookDTO getVehicleById(Long veID) {
		FmVehicleLogBook vehiclBook = logBookRepo.findOne(veID);
		VehicleLogBookDTO vehicleLogBookDTO = new VehicleLogBookDTO();
		BeanUtils.copyProperties(vehiclBook, vehicleLogBookDTO);

		return vehicleLogBookDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleLogBookDTO> getAllVehicles(Long orgid) {
		List<Object[]> list =null; //vehicleMasterService.getAllVehicles(orgid);
		List<VehicleLogBookDTO> listDto = new ArrayList<VehicleLogBookDTO>();
		String serverURI=ServiceEndpoints.VehicleManagement.VEHICLE_MASTER_All+orgid;
		 try {
				if(new URI(serverURI).isAbsolute()) {
					  ResponseEntity<?> Response = RestClient.callRestTemplateClient(null, serverURI, HttpMethod.GET,Object.class);
					   if(Response!=null  && Response.getStatusCode()==HttpStatus.OK && Response.getBody()!=null) {
					        list=(List<Object[]>) Response.getBody();
					    }else {
					    	LOGGER.info("Vehicle Detail Not found");
					    } 
				  }else {
					  LOGGER.info("URL is incorrect ");
				  }
			} catch (URISyntaxException e) {
				 LOGGER.info("Error while getting vehicle detail ",e);
			}
		/*for (Object[] obj : list) {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();
			dto.setVeNo(obj[1].toString());
			// dto.setDriverId(obj[1].toString());
			dto.setDriverName(obj[2].toString() + " " + obj[3].toString());
			listDto.add(dto);
		}*/
		if(CollectionUtils.isNotEmpty(list)) {
		for (Object object : list) {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();
			int count=0;
			List<Object[]>  objectArray=(List<Object[]>)object;
			for(Object obj:objectArray) {
				if(count==1)
			    dto.setVeNo(obj.toString());
				if(count==2)
					if(obj!=null)
				dto.setDriverName(obj.toString());
				count++;
			}
			listDto.add(dto);
    		}	
		}
		return listDto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleLogBookDTO> getAllVehiclesByDept(Long orgid ,Long deptId) {
		List<VehicleLogBookDTO> listDto = new ArrayList<VehicleLogBookDTO>();
		RestTemplate restTemplate = new RestTemplate();
		VehicleLogBookDTO callRegister=null;
		Map<String, String> requestParam = new HashMap<>();
		   requestParam.put("orgId", String.valueOf(orgid));
		   requestParam.put("deptId", String.valueOf(deptId));
		   DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
			dd.setParsePath(true);
			URI uri = dd.expand(ServiceEndpoints.VehicleManagement.VEHICLE_MASTER_BY_DEPT, requestParam);
			ResponseEntity<List<GenVehicleMasterDTO>> rateResponse =
			        restTemplate.exchange(uri,
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<GenVehicleMasterDTO>>() {
			            });
			List<GenVehicleMasterDTO> masters = rateResponse.getBody();
			
			for (GenVehicleMasterDTO genVehicleMasterDTO : masters) {
				callRegister =new VehicleLogBookDTO();
				callRegister.setVeNo(genVehicleMasterDTO.getVeNo());
				listDto.add(callRegister);
			}
		
		return listDto;
	}

}


