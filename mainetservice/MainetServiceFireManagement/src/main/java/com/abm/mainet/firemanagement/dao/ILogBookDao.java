package com.abm.mainet.firemanagement.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.FmVehicleLogBook;

@Repository
public interface ILogBookDao {

	List<FmVehicleLogBook> searchFireCallRegisterwithDate(Date fromDate, Date toDate,String veNo,Long orgid);

}
