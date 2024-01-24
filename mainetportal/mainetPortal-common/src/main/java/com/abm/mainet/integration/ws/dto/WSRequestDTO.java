package com.abm.mainet.integration.ws.dto;

import java.io.Serializable;

/**
 * DTO made for Web Service call, to hold request processing related common request data.this dto must be passed while making web
 * service call in order to get the response
 *
 * @author Vivek.Kumar
 * @since 26-Feb-2016
 */
public class WSRequestDTO implements Serializable {

    private static final long serialVersionUID = 7962955595884665363L;

    /**
     * set Model name,which need to be initialized Ex- suppose you want to initialize CheckListModel and ChargeMasterModel so you
     * need to create pipe data as String like {"CheckListModel|ChargeMasterModel"} and you will get initialized object for
     * requested model
     */
    private String modelName;

    /*
     * all below fields need to populate by caller before making Web Service call as per Service requirement.
     */

    private Object dataModel;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public Object getDataModel() {
        return dataModel;
    }

    public void setDataModel(final Object dataModel) {
        this.dataModel = dataModel;
    }

}
