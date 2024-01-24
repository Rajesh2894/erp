package com.abm.mainet.vehiclemanagement.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;


@WebService
public interface IOEMWarrantyService {

	OEMWarrantyDTO saveOemWarranty(OEMWarrantyDTO oemWarrantyDto);
	OEMWarrantyDTO updateOemWarranty(OEMWarrantyDTO oemWarrantyDto);
	boolean oemWarrantyValidate(OEMWarrantyDTO OEMWarrantyDTO);
	public	List<OEMWarrantyDTO> searchOemWarrantyDetails(Long department, Long vehicleType, Long veNo, Long orgid);
	List<OEMWarrantyDTO> getAllVehicles(Long orgid);
    public OEMWarrantyDTO getOemWarrantyDetails(Long id);
    List<OEMWarrantyDTO> getVehicleByNo(Long orgid, Long veNo);
	Boolean searchVehicleByVehicleTypeAndVehicleRegNo(OEMWarrantyDTO dto, long orgid);
	OEMWarrantyDTO findByrefNoAndOrgId(String refNo,Long orgId);

}
