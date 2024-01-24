package com.abm.mainet.asset.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.asset.ui.dto.SummaryDTO;

import io.swagger.annotations.Api;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.asset.service.ISearchService")
@Path("/search")
@Api(value = "/search")
public interface ISearchService {

    /**
     * It is used to search assets
     * 
     * @return list of Object as join is applied on it
     */
    @POST
    @Path("/advanced")
    @Consumes("application/json")
    public List<SummaryDTO> search(@RequestBody SearchDTO searchDTO);

    /**
     * Search as per the department id passed in argument
     * @param deptId the department id
     * @return summary of the assets
     */
    // List<AssetSummaryDTO> searchByDeptId(Long deptId);
    /**
     * Search as per the department code passed in argument
     * @param deptCd the department code
     * @return summary of the assets
     */
    // List<AssetSummaryDTO> searchByDeptCode(Long deptCd);
    /**
     * Search as per the asset id passed in argument
     * @param id the asset id
     * @return summary of the assets
     */
    // AssetSummaryDTO searchById(Long id);
    /**
     * Search as per the asset number passed in argument
     * @param num the asset number
     * @return summary of the asset
     */
    // List<AssetSummaryDTO> searchByNum(Long num);
    /**
     * Search as per the asset class id passed in argument
     * @param astClassId the asset class
     * @return summary of the assets
     */
    // List<AssetSummaryDTO> searchByClass(Long astClassId);
    /**
     * Search as per the location id passed in argument
     * @param locId the location id
     * @return summary of the assets
     */

    // List<AssetSummaryDTO> searchByLocation(Long locId);
    /**
     * Search as per the cost center id passed in argument
     * @param costCenterId the cost center id
     * @return summary of the assets
     */
    // List<AssetSummaryDTO> searchByCostCenter(Long costCenterId);
    // List<AssetSummaryDTO> advancedSearch(SearchDTO searchDTO, PagingDTO pageDTO, List<SortingDTO> sortList);

    /**
     * @param barcodeList this method will take a SummaryDto and return
     */
    public List<SummaryDTO> barcodeGenerate(List<SummaryDTO> barcodeList);

}
