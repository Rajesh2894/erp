package com.abm.mainet.bnd.service;

import java.util.List;

import com.abm.mainet.bnd.dto.TbBdCertCopyDTO;

public interface PrintBNDCertificateService {

  List<TbBdCertCopyDTO>	 getPrintCertificateDetails(Long ApplicationNo,Long orgId);
 
  public String findServiceBirthOrDeath(Long ApplicationNo,Long orgId,Long serviceId);

  boolean generateCertificate(String docPath, Long applicationNo, String certificateNo);
  
}
