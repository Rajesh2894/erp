/**
 * 
 */
package com.abm.mainet.asset.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.dto.GeoCoordinatesDTO;
import com.abm.mainet.common.dto.GeoPositionDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Vardan.Savarde
 *
 */
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.asset.service.IAssetGeoService")
@Api(value = "/assetgeo")
@Path("/assetgeo")
public class AssetGeoServiceImpl implements IAssetGeoService {
	
	@Autowired
	private IClassificationInfoService classInfoService;

	/* (non-Javadoc)
	 * @see com.abm.mainet.asset.service.IAssetGeoService#getGeoPosition(java.lang.Long)
	 */
	@Override
	@GET
	@ApiOperation(
	        value = "Fetch assets geo position information based on assetId",
	        notes = "Fetch assets geo position information based on assetId",
	        response = GeoPositionDTO.class
	    )
	@Path("/id/{assetId}")
	@Transactional(readOnly=true)
	public GeoPositionDTO getGeoPositionByAssetId(@ApiParam(value = "Asset to fetch", required = true) @PathParam("assetId")Long assetId) {
		AssetClassificationDTO classDto = classInfoService.getclassByAssetId(assetId);
		GeoPositionDTO geoDto = null;
		if(classDto != null) {
			geoDto = new GeoPositionDTO();
			GeoCoordinatesDTO coordDto = new GeoCoordinatesDTO();
			coordDto.setLatitude(classDto.getLatitude());
			coordDto.setLongitude(classDto.getLongitude());
			geoDto.setCoordinates(coordDto);
		}
		return geoDto;
	}
	
	@ApiOperation(
	        value = "Updates the geo position of an asset",
	        notes = "Updates the geo position of an asset",
	        response = Boolean.class
	    )
	@Override
	@POST
	@Path("/{assetId}")
	@Transactional
	public boolean updateGeoPosition(@ApiParam(value = "Asset to fetch", required = true) @PathParam("assetId")Long assetId, @ApiParam(value = "Geo Position details", required = true)  @RequestBody GeoPositionDTO positionDTO) {
		if(positionDTO == null || positionDTO.getCoordinates() == null) {
			throw new FrameworkException("GeoPositionDTO cannot be null");
		}
		GeoCoordinatesDTO coordDto = positionDTO.getCoordinates();
		if(coordDto.getLatitude() == null || coordDto.getLongitude() == null) {
			throw new FrameworkException("Latitude or Longitude cannot be null");
		}
		return classInfoService.updateGeoInformation(assetId, coordDto);
	}

	/*@ApiOperation(
	        value = "Fetch assets geo position information based on barcode",
	        notes = "Fetch assets geo position information based on barcode",
	        response = GeoPositionDTO.class
	    )
	@Override
	@GET
	@Path("/barcode/{barcode}")
	public GeoPositionDTO getGeoPositionByBarcode(@ApiParam(value = "Barcode of Asset to fetch", required = false) @PathParam("barcode") String barcode) {
		return GeoPositionDTO.dummy();
	}
	
	@ApiOperation(
	        value = "Fetch assets geo position information based on barcode passed as query parameter",
	        notes = "Fetch assets geo position information based on barcode passed as query parameter",
	        response = GeoPositionDTO.class
	    )
	@Override
	@GET
	@Path("/barcodeparam")
	public GeoPositionDTO getGeoPositionParamByBarcode(@ApiParam(value = "Barcode of Asset to fetch", required = false) @QueryParam("barcode") String barcode) {
		return GeoPositionDTO.dummy();
	}*/

}
