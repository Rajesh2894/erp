package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.integration.dto.ResponseDTO;

public class WaterDisconnectionResponseDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = 317382262011631206L;

    private List<CustomerInfoDTO> connectionList;
    
    private double billoutstandingAmoount;

    public List<CustomerInfoDTO> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(final List<CustomerInfoDTO> connectionList) {
        this.connectionList = connectionList;
    }

	public double getBilloutstandingAmoount() {
		return billoutstandingAmoount;
	}

	public void setBilloutstandingAmoount(double billoutstandingAmoount) {
		this.billoutstandingAmoount = billoutstandingAmoount;
	}
    
    

}
