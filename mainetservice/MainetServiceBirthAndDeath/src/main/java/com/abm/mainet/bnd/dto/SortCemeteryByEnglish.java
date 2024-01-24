package com.abm.mainet.bnd.dto;

import java.util.Comparator;

import com.abm.mainet.bnd.domain.CemeteryMaster;

public class SortCemeteryByEnglish implements Comparator<CemeteryMaster>{

	@Override
	public int compare(CemeteryMaster obj1, CemeteryMaster obj2) {
		if(obj1.getCeName()!=null && obj2.getCeName()!=null) {
			 return obj1.getCeName().toUpperCase().compareTo(obj2.getCeName().toUpperCase());
			 }
			return 0;
	}

}
