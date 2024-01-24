package com.abm.mainet.cms.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.AttachDocs;

public interface IExcelImportExportForDashboardDao {

    public abstract void saveAttachment(AttachDocs entity);

    public abstract AttachDocs getExcelDocument(String scheme, Long orgid);

    public abstract List<Long> getData(Class className, Date attachDt);

    public abstract boolean saveExcelData(Class className, List<Object> objList, Long empId, Date date, List<Long> ids);

}
