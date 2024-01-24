package com.abm.mainet.cms.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.AttachDocs;

public interface IExcelImportExportForDashboardService {

    public abstract void saveAttachment(AttachDocs entity);

    public abstract AttachDocs getExcelDocument(String scheme, Long orgid);

    public abstract boolean saveExcelData(Class className, List<Object> parseData, Long empId, Date date, List<Long> list);

    public abstract List<Long> getData(Class className, Date attachDt);

}
