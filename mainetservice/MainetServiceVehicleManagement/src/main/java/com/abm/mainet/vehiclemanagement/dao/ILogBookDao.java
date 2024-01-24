package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VehicleLogBookDetails;

@Repository
public interface ILogBookDao {

	List<VehicleLogBookDetails> searchVehicleLogBook(Date fromDate, Date toDate,Long veNo,Long orgid);

}
