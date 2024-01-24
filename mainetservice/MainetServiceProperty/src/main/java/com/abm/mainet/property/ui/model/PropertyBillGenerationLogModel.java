/**
 * 
 */
package com.abm.mainet.property.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.dto.PropertyBillGenerationMap;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Anwarul.Hassan
 *
 * @since 28-Apr-2020
 */
@Component
@Scope("session")
public class PropertyBillGenerationLogModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private PropertyBillGenerationMap propertyBillGenerationMap = new PropertyBillGenerationMap();

    public PropertyBillGenerationMap getPropertyBillGenerationMap() {
        return propertyBillGenerationMap;
    }

    public void setPropertyBillGenerationMap(PropertyBillGenerationMap propertyBillGenerationMap) {
        this.propertyBillGenerationMap = propertyBillGenerationMap;
    }

}
