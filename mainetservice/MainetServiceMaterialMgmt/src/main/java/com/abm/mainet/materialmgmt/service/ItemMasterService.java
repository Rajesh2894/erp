
package com.abm.mainet.materialmgmt.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.materialmgmt.dto.ItemMasterUploadDTO;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;

@WebService
public interface ItemMasterService {

	ItemMasterDTO saveItemMasterFormData(ItemMasterDTO tbMGItemMaster, final Organisation org, final int langId,
			final Employee emp);

	List<ItemMasterDTO> findItemMasterDetailsByOrgId(Long orgId);

	List<ItemMasterDTO> findByAllGridSearchData(Long category, Long type, Long itemgroup, Long itemsubgroup,
			String name, Long orgId);

	ItemMasterDTO getDetailsByUsingItemId(ItemMasterDTO tbMGItemMaster);

	public void saveItemMasterExcelData(ItemMasterUploadDTO itemMasterUploadDto) throws ParseException;

	public Long ItemNameCount(String name, Long orgId);

	List<Object[]> getItemIdNameListByOrgId(Long orgId);

	Object[] getItemDetailObjectByItemId(Long itenId);

	long getUomByitemCode(Long orgId, Long itemId);

	@POST
	@Path("/fetchItemData")
	@Produces(MediaType.APPLICATION_JSON)
	Map<String, String> getItemIdNameListByItemIdsOrgId(@RequestBody PurchaseRequistionDto dto);

	List<Object[]> getActiveItemIdNameListByOrgId(Long orgId);

}
