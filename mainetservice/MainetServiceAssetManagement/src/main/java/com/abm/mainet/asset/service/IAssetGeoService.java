package com.abm.mainet.asset.service;

import javax.jws.WebService;

import com.abm.mainet.common.dto.GeoPositionDTO;

/**
 * This service provides methods to fetch/update geo information about
 * an asset
 * @author Vardan.Savarde
 *
 */
@WebService
public interface IAssetGeoService {
	/**
	 * Returns the GeoPositionDTO for the asset
	 * @param assetId assetId whose Geo Position is required
	 * @return Returns the GeoPositionDTO for the asset
	 */
	GeoPositionDTO getGeoPositionByAssetId(Long assetId);
	
	/**
	 * Updates the geo position of the asset
	 * @param assetId asset whose geo position is to be updated
	 * @param positionDTO geo position details
	 * @return true if successfully updated else false
	 */
	boolean updateGeoPosition(Long assetId, GeoPositionDTO positionDTO);
	/*
	 * Returns the GeoPositionDTO for the asset
	 * @param barcode barcode whose Geo Position is required
	 * @return Returns the GeoPositionDTO for the asset
	 *//*
	GeoPositionDTO getGeoPositionByBarcode(String barcode);

	GeoPositionDTO getGeoPositionParamByBarcode(String barcode);*/

}
