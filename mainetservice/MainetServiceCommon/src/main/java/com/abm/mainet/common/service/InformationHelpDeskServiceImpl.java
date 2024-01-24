package com.abm.mainet.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.InformationDeskDto;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.ui.controller.InformationHelpDeskController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@WebService(endpointInterface = "com.abm.mainet.common.service.InformationHelpDeskService")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Api(value = "/informationHelpDeskService")
@Path("/informationHelpDeskService")
@Service
public class InformationHelpDeskServiceImpl implements InformationHelpDeskService{
	
	private static final Logger LOGGER = Logger.getLogger(InformationHelpDeskController.class);
	
	@Autowired
	TbDepartmentService departmentService;
	
	@Autowired
	private TbServicesMstService tbServicesMstService;

	@Autowired
	ServiceMasterService  serviceMaster;
	
	@Autowired
	private BRMSCommonService brmsCommonService;
	

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "To Get Department List", notes = "To Get Department List", response = TbDepartment.class)
	@Path("/getDeptarmetnList")
	public List<TbDepartment> getDeptarmetnList() {
		List<TbDepartment> deptList = departmentService.findAll();
		return deptList;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "To Get Service List", notes = "To Get Service List", response = TbServicesMst.class)
	@Path("/findServiceListByDeptId/{deptId}/{orgId}")
	public List<TbServicesMst> findServiceListByDeptId(@PathParam("deptId") Long deptId, @PathParam("orgId") Long orgId) {
		 List<TbServicesMst>   serviceMstList = tbServicesMstService.findALlActiveServiceByDeptId(deptId, orgId);
	     return serviceMstList;
	}
	
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
    @Consumes("application/json")
	@POST
	@ApiOperation(value = "To Get Service List", notes = "To Get Service List", response = InformationDeskDto.class)
	@Path("/getServiceInfo/{deptId}/{serviceId}/{orgId}")
	public InformationDeskDto getServiceInfo(@PathParam("deptId") Long deptId,@PathParam("serviceId")  Long serviceId, @PathParam("orgId")  Long orgId,
			@PathParam("categoryId") Long categoryId){
		
		InformationDeskDto dto = new InformationDeskDto();
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		if (serviceId != null && orgId != null) {
		ServiceMaster sm = serviceMaster.getServiceMaster(serviceId,orgId);
		String deptCode = departmentService.findDepartmentShortCodeByDeptId(deptId,orgId);
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		if (sm != null) {
			dto.setSmServiceDuration(sm.getSmServiceDuration());
			if (sm.getSmFeesSchedule() == 1) 
				dto.setChargeApplicable(ApplicationSession.getInstance().getMessage("flag.No"));
			else 
				dto.setChargeApplicable(ApplicationSession.getInstance().getMessage("flag.yes"));
			
			if (sm.getSmAppliChargeFlag().equals(MainetConstants.Y_FLAG)) 
				dto.setSmAppliChargeFlag(ApplicationSession.getInstance().getMessage("flag.yes"));
			else 
				dto.setSmAppliChargeFlag(ApplicationSession.getInstance().getMessage("flag.No"));
			if (sm.getSmScrutinyChargeFlag().equals(MainetConstants.Y_FLAG))
				dto.setSmScrutinyChargeFlag(ApplicationSession.getInstance().getMessage("flag.yes")); 
			else
				dto.setSmScrutinyChargeFlag(ApplicationSession.getInstance().getMessage("flag.No"));
		}
		 LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmDurationUnit());
		 if(UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) 
			 dto.setSmDurationUnit(lookUp.getDescLangFirst());
		 else
			 dto.setSmDurationUnit(lookUp.getDescLangSecond());
			
	 
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		List<DocumentDetailsVO> checkListList =  Collections.emptyList();
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(sm.getSmShortdesc());
			if (MainetConstants.TradeLicense.MARKET_LICENSE.equals(deptCode) && MainetConstants.TradeLicense.SERVICE_CODE.equals(sm.getSmShortdesc()) && categoryId !=null) {	
				LookUp lookUps = CommonMasterUtility.getHierarchicalLookUp(categoryId,
						orgId);
				checkListModel.setUsageSubtype1(lookUps.getLookUpCode());
			}else if(MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(deptCode) && MainetConstants.Property.MUT.equals(sm.getSmShortdesc()) && categoryId !=null) {
				LookUp lookUps = CommonMasterUtility.getNonHierarchicalLookUpObject(categoryId, org);
				checkListModel.setFactor4(lookUps.getDescLangFirst());
			}else if(MainetConstants.DEPT_SHORT_NAME.WATER.equals(deptCode) && MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION.equals(sm.getSmShortdesc()) && categoryId !=null) {
				LookUp lookUps = CommonMasterUtility.getHierarchicalLookUp(categoryId,orgId);
				checkListModel.setUsageSubtype2(lookUps.getDescLangFirst());	
				List<LookUp> lookUps1 = CommonMasterUtility.getNextLevelData(MainetConstants.NewWaterServiceConstants.CCG, 1,orgId);
				if(CollectionUtils.isNotEmpty(lookUps1)) {
				lookUps1.forEach(l->{
					if(MainetConstants.FlagP.equals(l.getLookUpCode()))
						checkListModel.setUsageSubtype1(l.getDescLangFirst());
				});
				}
			}
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName("ChecklistModel");
			checklistReqDto.setDataModel(checkListModel);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
				
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
					if ((checkListList != null) && !checkListList.isEmpty()) {
						long cnt = 1;
						for (final DocumentDetailsVO doc : checkListList) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
					 dto.setCheckList(checkListList);
					}
				}
			}
		}
		 }
		return dto;
		
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}
	   
}
