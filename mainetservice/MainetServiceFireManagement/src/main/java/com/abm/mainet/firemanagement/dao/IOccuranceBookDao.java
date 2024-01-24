package com.abm.mainet.firemanagement.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.FireCallRegister;

@Repository
public interface IOccuranceBookDao {
	
	
	
	List<FireCallRegister> searchFireCallRegister(Date fromdate,Date todate, Long fireStation,
			Long orgid);


}
