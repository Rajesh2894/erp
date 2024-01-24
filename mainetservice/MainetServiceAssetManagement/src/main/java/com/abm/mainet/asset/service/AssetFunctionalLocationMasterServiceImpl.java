package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.asset.dao.AssetFunctionalLocationMasterDAO;
import com.abm.mainet.asset.domain.AssetFunctionalLocation;
import com.abm.mainet.asset.ui.dto.AssetFunctionalLocationDTO;

import io.swagger.annotations.Api;

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.asset.service.IAssetFunctionalLocationMasterService")
@Path("/asset/functionalLocMaster")
@Api(value = "/asset/functionalLocMaster")
@Service
public class AssetFunctionalLocationMasterServiceImpl implements IAssetFunctionalLocationMasterService{

	@Resource
	AssetFunctionalLocationMasterDAO assetFuncLocMasterDAO;
	
	@WebMethod(exclude=true)
	@Override
	public boolean addFunctionalLocationCode(List<AssetFunctionalLocation> assetFunctionalLocationList) {
		return false;
	}

	/**
	 * returns All AssetFunctionalLocation object List by default use to display parent dropdwn values 
	 * 
	 * @return AssetFunctionalLocation List of objects
	 */
	@WebMethod(exclude=true)
	@Override
	@Transactional(readOnly=true)
	public List<AssetFunctionalLocation> retriveFunctionLocationListByOrgId(final Long orgId) {
		return assetFuncLocMasterDAO.retriveList(orgId);
	}
	
	/**
	 * returns All AssetFunctionalLocation object List by default use to display parent dropdwn values 
	 * 
	 * @return AssetFunctionalLocation List of objects
	 */
	@WebMethod
	@GET
	@Path("/id/{orgId}")
	@Override
	@Transactional(readOnly=true)
	public List<AssetFunctionalLocationDTO> retriveFunctionLocationDtoListByOrgId(@PathParam("orgId") final Long orgId) {
		
		return convertEntityToFunctionalDTOList(assetFuncLocMasterDAO.retriveList(orgId));
	}

	/**
	 * used for convert entity List to DTO List form
	 * 
	 * @param asstFuncLocList
	 * @return List<AssetFunctionalLocationDTO>
	 */
	public List<AssetFunctionalLocationDTO> convertEntityToFunctionalDTOList(List<AssetFunctionalLocation> asstFuncLocList)
	{
		List<AssetFunctionalLocationDTO> funcLocDTOList	=	new ArrayList<AssetFunctionalLocationDTO>();
		
		for(int i=0;i<asstFuncLocList.size();i++)
		{
			AssetFunctionalLocationDTO funcLocDTO	=	convertToFuncLocDTO(asstFuncLocList.get(i));
			funcLocDTOList.add(funcLocDTO);
		}
		return funcLocDTOList;
	}
	
	
	/**
	 * used for convert entity object to DTO object form
	 * 
	 * @param asstFuncLocList
	 * @return List<AssetFunctionalLocationDTO>
	 */
	private AssetFunctionalLocationDTO convertToFuncLocDTO(AssetFunctionalLocation asstFuncLocEnty)
	{
		AssetFunctionalLocationDTO funcLocDTO	=	new AssetFunctionalLocationDTO();
		
		//it is used to display in drop down list
		funcLocDTO.setFuncLocationCode(asstFuncLocEnty.getFuncLocationCode());
		funcLocDTO.setFuncLocationId(asstFuncLocEnty.getFuncLocationId());;
		funcLocDTO.setDescription(asstFuncLocEnty.getDescription());
		if(asstFuncLocEnty.getFlcObject()!=null)
		{
			funcLocDTO.setParentCode(asstFuncLocEnty.getFlcObject().getFuncLocationCode());
			funcLocDTO.setParentId(asstFuncLocEnty.getFlcObject().getFuncLocationId());
		}
		/*else
		{
			funcLocDTO.setParentCode(MainetConstants.AssetManagement.NONE);
			if(modeType!=null && modeType.equalsIgnoreCase("E"))
			funcLocDTO.setParentId(0l);
		}*/
		funcLocDTO.setStartPoint(asstFuncLocEnty.getStartPoint());
		funcLocDTO.setEndPoint(asstFuncLocEnty.getEndPoint());
		funcLocDTO.setUnit(asstFuncLocEnty.getUnit());
		
		return funcLocDTO;
	}
	
	
	/**
	 * save AssetFunctionalLocation object present inside the List to database 
	 * 
	 * @return true
	 */
	@WebMethod(exclude=true)
	@Override
	@Transactional
	public boolean saveFunctionLocationList(List<AssetFunctionalLocation> funcLocCodeList) {
		return assetFuncLocMasterDAO.save(funcLocCodeList);
	}

	/**
	 * update AssetFunctionalLocation object present inside the List to database 
	 * 
	 * @param AssetFunctionalLocation object
	 * @return true if successfully saved in database.
	 */
	@WebMethod(exclude=true)
	@Override
	@Transactional
	public boolean updateFunctionLocation(AssetFunctionalLocation funcLocCode) {
		return assetFuncLocMasterDAO.update(funcLocCode);
	}
	
	
	/**
	 * used validate the function location code based onblur function 
	 * 
	 * @param funcLocCode
	 * @return true when duplicate
	 */
	@WebMethod(exclude=true)
	@Override
	@Transactional(readOnly=true)
	public boolean isDuplicate(String funcLocCode, Long orgId) {
		
		return assetFuncLocMasterDAO.isDuplicate(funcLocCode, orgId);
	}

	/**
	 * returns search result 
	 * 
	 * @param funcLocCode
	 * @param description
	 * @param orgId
	 * 
	 * @return List<AssetFunctionalLocation> 
	 */
	@WebMethod(exclude=true)
	@Override
	@Transactional(readOnly=true)
	public List<AssetFunctionalLocation> searchFunLocByLocCodeAndDescriptionAndOrgId(String funcLocCode, String description,Long orgId) {
		return assetFuncLocMasterDAO.search(funcLocCode, description,orgId);
	}

	
	/**
	 * returns search result 
	 * 
	 * @param funcLocId
	 * @param orgId
	 * 
	 * @return AssetFunctionalLocation object corresponding to funcLocId and orgId
	 */
	@WebMethod(exclude=true)
	@Override
	@Transactional(readOnly=true)
	public AssetFunctionalLocation findByFuncLocId(Long funcLocId ,Long orgId) {
		return assetFuncLocMasterDAO.findByFuncLocId(funcLocId ,orgId);
	}
	
}
