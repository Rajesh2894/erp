package com.abm.mainet.common.dao;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public interface IReceivableDemandEntryDao {

    List<Object[]> searchDemandEntry(String refNumber, String billNo, Long orgId, String ward , Long locID);
    
    byte[] generateJasperReportPDF(ByteArrayOutputStream outputStream, Map oParms, String fileName);


}
