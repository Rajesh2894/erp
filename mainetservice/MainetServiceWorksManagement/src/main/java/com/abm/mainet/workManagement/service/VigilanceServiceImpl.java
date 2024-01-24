package com.abm.mainet.workManagement.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.workManagement.domain.Vigilance;
import com.abm.mainet.workManagement.dto.InspectionRaBillDto;
import com.abm.mainet.workManagement.dto.VigilanceDto;

import com.abm.mainet.workManagement.dto.WorkDefinitionDto;

import com.abm.mainet.workManagement.repository.VigilanceRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

@WebService(endpointInterface = "com.abm.mainet.workManagement.service.VigilanceService")
@Api(value = "/vigilanceService")
@Path("/vigilanceService")
@Service
public class VigilanceServiceImpl implements VigilanceService {

    private static final Logger LOGGER = Logger.getLogger(VigilanceServiceImpl.class);
    @Autowired
    private VigilanceRepository vigilanceRepository;

    @Autowired
    private WorkDefinitionService workDefinitionService;

    @Autowired
    private WmsProjectMasterService iProjectMasterService;
    @Autowired
    WorksRABillService worksrabillservice;

    @Autowired
    private IFileUploadService fileUploadService;

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<VigilanceDto> getFilterVigilanceList(String refType, String refNumber, Date memoDate,
            Date inspectionDate, Long orgId) {
        List<VigilanceDto> list = new ArrayList<>();
        VigilanceDto dto = null;

        List<Vigilance> vigilanceList = vigilanceRepository.getFilterVigilanceList(refType, refNumber, memoDate,
                inspectionDate, orgId);

        if (!vigilanceList.isEmpty()) {
            for (Vigilance vigilance : vigilanceList) {
                dto = new VigilanceDto();
                BeanUtils.copyProperties(vigilance, dto);
                if (vigilance.getMemoDate() != null) {
                    dto.setMemoDateDesc(
                            new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(vigilance.getMemoDate()));
                }
                if (vigilance.getInspectionDate() != null) {
                    dto.setInspectDateDesc(
                            new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(vigilance.getInspectionDate()));
                }
                if (vigilance.getWorkId() != null) {
                    WorkDefinitionDto definitionDto = ApplicationContextProvider.getApplicationContext()
                            .getBean(WorkDefinitionService.class).findAllWorkDefinitionById(vigilance.getWorkId());
                    dto.setWorkName(definitionDto.getWorkName());
                    dto.setProjName(definitionDto.getProjName());
                }
				
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public VigilanceDto getVigilanceById(Long vigilanceId) {
        VigilanceDto vigilanceDto = new VigilanceDto();

        Vigilance vigilance = vigilanceRepository.findOne(vigilanceId);
        if (vigilance.getWorkId() != null) {
            WorkDefinitionDto definitionDto = ApplicationContextProvider.getApplicationContext()
                    .getBean(WorkDefinitionService.class).findAllWorkDefinitionById(vigilance.getWorkId());

            definitionDto.setProjId(vigilance.getProjId());
            definitionDto.setWorkId(vigilance.getWorkId());
        }
        BeanUtils.copyProperties(vigilance, vigilanceDto);

        return vigilanceDto;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void saveVigilance(VigilanceDto vigilanceDto, RequestDTO requestDTO, List<DocumentDetailsVO> attachments) {
        Vigilance vigilance = new Vigilance();

        BeanUtils.copyProperties(vigilanceDto, vigilance);

        vigilanceRepository.save(vigilance);
        requestDTO.setIdfId(vigilanceDto.getReferenceNumber());
        fileUploadService.doMasterFileUpload(attachments, requestDTO);

    }

    @POST
    @ApiOperation(value = "save inspection details", notes = "save inspection details", response = InspectionRaBillDto.class)
    @Path("/saveInspectionDetails")
    @Transactional
    public InspectionRaBillDto saveInspection(InspectionRaBillDto inspectionRaBillDto) {
        InspectionRaBillDto response = new InspectionRaBillDto();

        String message ;

        WorkDefinitionDto projCodeAndWorkCode;

        projCodeAndWorkCode = workDefinitionService.getProjCodeAndWorkCode(inspectionRaBillDto.getVigilanceDto().getProjCode(),
                inspectionRaBillDto.getVigilanceDto().getWorkCode());

        if (projCodeAndWorkCode != null) {

            Vigilance vigilance = new Vigilance();
            vigilance.setProjId(projCodeAndWorkCode.getProjId());
            vigilance.setWorkId(projCodeAndWorkCode.getWorkId());
            vigilance.setReferenceType(inspectionRaBillDto.getVigilanceDto().getReferenceType());
            vigilance.setCreatedBy(inspectionRaBillDto.getVigilanceDto().getCreatedBy());
            vigilance.setCreatedDate(inspectionRaBillDto.getVigilanceDto().getCreatedDate());
            vigilance.setReferenceNumber(inspectionRaBillDto.getVigilanceDto().getReferenceNumber());
            vigilance.setMemoType(inspectionRaBillDto.getVigilanceDto().getMemoType());
            vigilance.setUpdatedBy(inspectionRaBillDto.getVigilanceDto().getUpdatedBy());
            vigilance.setUpdatedDate(inspectionRaBillDto.getVigilanceDto().getUpdatedDate());
            vigilance.setOrgId(projCodeAndWorkCode.getOrgId());
            BeanUtils.copyProperties(vigilance,inspectionRaBillDto.getVigilanceDto());
            vigilanceRepository.save(vigilance);

            inspectionRaBillDto.getRequestDTO().setIdfId(inspectionRaBillDto.getVigilanceDto().getReferenceNumber());

            fileUploadService.doFileUpload(inspectionRaBillDto.getAttachments(), inspectionRaBillDto.getRequestDTO());
            message = "Inspection is submitted successfully";
           

          } else {
            message = "Project code or/and Work code is not matched";

        }
        response.setStatus(message);
        return response;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void updateVigilance(VigilanceDto vigilanceDto, List<DocumentDetailsVO> attachments, RequestDTO requestDTO,
            List<Long> removeFileList) {

        Vigilance vigilance = new Vigilance();
        BeanUtils.copyProperties(vigilanceDto, vigilance);
        vigilanceRepository.save(vigilance);
        inactiveDeletedDocuments(vigilanceDto.getUpdatedBy(), removeFileList);

        requestDTO.setIdfId(vigilanceDto.getReferenceNumber());
        fileUploadService.doMasterFileUpload(attachments, requestDTO);

    }

    private void inactiveDeletedDocuments(Long updatedBy, List<Long> removeFileId) {

        if (removeFileId != null && !removeFileId.isEmpty()) {
            ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(removeFileId,
                    updatedBy, MainetConstants.FlagD);
        }

    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<VigilanceDto> getVigilanceByReferenceNo(String referenceNo, Long orgId) {
        List<VigilanceDto> list = new ArrayList<>();

        List<Vigilance> vigilanceList = vigilanceRepository.findInspectionByReference(referenceNo, orgId);
        vigilanceList.forEach(vigLance -> {
            VigilanceDto dto = new VigilanceDto();
            BeanUtils.copyProperties(vigLance, dto);
            if (vigLance.getMemoDate() != null) {
                dto.setMemoDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(vigLance.getMemoDate()));
            }
            if (vigLance.getInspectionDate() != null) {
                dto.setInspectDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(vigLance.getInspectionDate()));
            }
            if (dto.getMemoType() != null) {
                if (dto.getMemoType().equals(MainetConstants.FlagA)) {
                    dto.setMemoTypeDesc(MainetConstants.WorksManagement.ACTIONABLE);
                }
                if (dto.getMemoType().equals(MainetConstants.FlagI)) {
                    dto.setMemoTypeDesc(MainetConstants.WorksManagement.INFORMATIONAL);
                }
            }
            if (dto.getCreatedBy() != null) {
                EmployeeBean employeeBean = ApplicationContextProvider.getApplicationContext()
                        .getBean(IEmployeeService.class).findById(dto.getCreatedBy());
                dto.setDsgName(employeeBean.getDesignName());
            }
            list.add(dto);
        });
        return list;
    }

	@Override
	public List<VigilanceDto> getVigilanceDetByProjIdAndWorkId(Long projectId, Long workId, Long orgid) {
		List<VigilanceDto> vigilanceDto = new ArrayList<>();

        List<Vigilance> vigilanceList = vigilanceRepository.findInspectionByProjIdAndWorkId(projectId, workId,orgid);
        if (!vigilanceList.isEmpty()) {
        vigilanceList.forEach(list->{
        	  VigilanceDto dto = new VigilanceDto();
              BeanUtils.copyProperties(list, dto);
              if (list.getMemoDate() != null) {
                  dto.setMemoDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(list.getMemoDate()));
              }
              if (list.getInspectionDate() != null) {
                  dto.setInspectDateDesc(
                          new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(list.getInspectionDate()));
              }
              if (dto.getMemoType() != null) {
                  if (dto.getMemoType().equals(MainetConstants.FlagA)) {
                      dto.setMemoTypeDesc(MainetConstants.WorksManagement.ACTIONABLE);
                  }
                  if (dto.getMemoType().equals(MainetConstants.FlagI)) {
                      dto.setMemoTypeDesc(MainetConstants.WorksManagement.INFORMATIONAL);
                  }
              }
              if (list.getWorkId() != null) {
                  WorkDefinitionDto definitionDto = ApplicationContextProvider.getApplicationContext()
                          .getBean(WorkDefinitionService.class).findAllWorkDefinitionById(list.getWorkId());
                  dto.setWorkName(definitionDto.getWorkName());
                  dto.setProjName(definitionDto.getProjName());
              }
              vigilanceDto.add(dto);
        	
        });
        }
		return vigilanceDto;
	}

}