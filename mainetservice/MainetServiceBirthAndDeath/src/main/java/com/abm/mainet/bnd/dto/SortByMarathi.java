package com.abm.mainet.bnd.dto;

import java.util.Comparator;

import com.abm.mainet.bnd.domain.HospitalMaster;

public class SortByMarathi implements Comparator<HospitalMaster>{

	@Override
	public int compare(HospitalMaster obj1, HospitalMaster obj2) {
	
		   if(obj1.getHiNameMar()!=null && obj2.getHiNameMar()!=null) {
			   return obj1.getHiNameMar().toUpperCase().compareTo(obj2.getHiNameMar().toUpperCase());
		   }
		  return 0;
		
	}

}
