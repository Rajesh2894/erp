/**
 * 
 */
package com.abm.mainet.water.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.dto.WaterBillGenerationMap;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Component
@Scope(value = "session")
public class WaterBillGenerationLogModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4691388957720481246L;

	private WaterBillGenerationMap waterBillGenerationMap = new WaterBillGenerationMap();

	public WaterBillGenerationMap getWaterBillGenerationMap() {
		return waterBillGenerationMap;
	}

	public void setWaterBillGenerationMap(WaterBillGenerationMap waterBillGenerationMap) {
		this.waterBillGenerationMap = waterBillGenerationMap;
	}

}
