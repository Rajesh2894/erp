package com.abm.mainet.bnd.dto;

import java.util.Comparator;

import com.abm.mainet.bnd.domain.HospitalMaster;

public class SortByEnglish implements Comparator<HospitalMaster>{

	@Override
	public int compare(HospitalMaster obj1, HospitalMaster obj2) {
		 if(obj1.getHiName()!=null && obj2.getHiName()!=null) {
		 return obj1.getHiName().toUpperCase().compareTo(obj2.getHiName().toUpperCase());
		 }
		return 0;
	}
}
