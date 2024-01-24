package com.abm.mainet.intranet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.intranet.dao.IntranetDao;
import com.abm.mainet.intranet.domain.IntranetMaster;
import com.abm.mainet.intranet.dto.report.IntranetDTO;
import com.abm.mainet.intranet.repository.IntranetRepository;

import io.swagger.annotations.Api;

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.intranet.service.IIntranetUploadService")
@Path(value = "/intranetService")
@Api(value = "/intranetService")
public class IntranetUploadService implements IIntranetUploadService {

	@Resource
	private IntranetRepository intranetRepository;
	
	@Autowired
	private IntranetDao intranetDao;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Override
	public IntranetDTO saveIntranetData(IntranetDTO intranetDTO, Long langId, Long userId, Long orgId, List<AttachDocs> attachDocs) {
		IntranetMaster intranetUploadEntity = new IntranetMaster();
		BeanUtils.copyProperties(intranetDTO, intranetUploadEntity);
		intranetUploadEntity.setUserId(userId);
		intranetUploadEntity.setLangId(langId);
		intranetUploadEntity.setLmoddate(new Date());
		intranetUploadEntity.setOrgid(orgId);
		if(attachDocs!=null) {
			if(attachDocs.get(0).getAttPath()!=null) {
				intranetUploadEntity.setAttPath(attachDocs.get(0).getAttPath());
			}
			if(attachDocs.get(0).getAttFname()!=null) {
				intranetUploadEntity.setAttFname(attachDocs.get(0).getAttFname());
			}
		}
		intranetRepository.save(intranetUploadEntity);
		intranetDTO.setInId(intranetUploadEntity.getInId());
		return intranetDTO;
	}

	@Override
	@Transactional
	public List<IntranetDTO> getIntranetData(Long orgId, Long docCateType) {
		List<IntranetMaster> IntranetEntity = intranetDao.getIntranetData(orgId, docCateType);
		List<IntranetDTO> listDTO = new ArrayList<IntranetDTO>();
		if (IntranetEntity != null) {
			IntranetEntity.forEach(entity->{
				IntranetDTO dto = new IntranetDTO();
				  if(entity!=null) {
					  BeanUtils.copyProperties(entity, dto);
				  }
				  listDTO.add(dto);
			});
		}
		return listDTO;
	}
	
	
	@Override
	@Transactional
	public IntranetDTO getIntranetDataByInId(Long inId ,Long orgId) {
		IntranetMaster IntranetEntity = intranetDao.getIntranetDataByInId(inId, orgId);
		IntranetDTO IntranetDTO = new IntranetDTO();
		BeanUtils.copyProperties(IntranetEntity, IntranetDTO);
		return IntranetDTO;
	}

	@Override
	public String getdeptdesc(Long deptId, Long orgid) {
		String intranetDeptDesc = null;
		intranetDeptDesc = departmentService.fetchDepartmentDescById(deptId);
		return intranetDeptDesc;
	}

	@Override
	@Transactional
	public List<IntranetMaster> getAllIntranetData(Long orgId) {
		List<IntranetMaster> IntranetEntity = intranetDao.getAllIntranetData(orgId);
		List<IntranetMaster> listDTO = new ArrayList<IntranetMaster>();
		if (IntranetEntity != null) {
			IntranetEntity.forEach(entity->{
				IntranetMaster dto = new IntranetMaster();
				  if(entity!=null) {
					  BeanUtils.copyProperties(entity, dto);
				  }
				  listDTO.add(dto);
			});
		}
		return listDTO;
	}


}
