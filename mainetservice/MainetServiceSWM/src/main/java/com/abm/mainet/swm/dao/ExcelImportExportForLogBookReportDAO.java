package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;

public interface ExcelImportExportForLogBookReportDAO {

    public abstract void saveAttachment(AttachDocs entity);

    public abstract boolean saveExcelData(Class className, List<Object> parseData, Long empId, Date date,Long orgId, List<Long> list);
    
    public abstract List<Long> getData(Class className);
    
    public abstract AttachDocs getExcelDocument(String scheme, Long orgid);
    
    public List<T> getRecord(Class className,Long orgId);

}
