package com.abm.mainet.firemanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.firemanagement.domain.FireCallRegister;

public class OccuranceBookDao extends AbstractDAO<FireCallRegister> implements IOccuranceBookDao{

	@Override
	public List<FireCallRegister> searchFireCallRegister(Date fromdate, Date todate, Long fireStation, Long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	}



