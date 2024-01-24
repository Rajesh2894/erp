package com.abm.mainet.bnd.dto;

import java.util.Comparator;

import com.abm.mainet.bnd.domain.CemeteryMaster;

public class SortCemeteryByMarathi implements Comparator<CemeteryMaster>{

	@Override
	public int compare(CemeteryMaster obj1, CemeteryMaster obj2) {
		if(obj1.getCeNameMar()!=null && obj2.getCeNameMar()!=null) {
			 return obj1.getCeNameMar().toUpperCase().compareTo(obj2.getCeNameMar().toUpperCase());
			 }
			return 0;
	}

}
