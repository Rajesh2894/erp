package com.abm.mainet.cms.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IExcelImportExportForDashboardDao;
import com.abm.mainet.common.domain.AttachDocs;

@Service
public class ExcelImportExportForDashboardService implements IExcelImportExportForDashboardService {

    private static final Logger logger = Logger.getLogger(ExcelImportExportForDashboardService.class);

    @Autowired
    private IExcelImportExportForDashboardDao iExcelImportExportForDashboardDao;

    @Override
    @Transactional
    public void saveAttachment(AttachDocs entity) {
        iExcelImportExportForDashboardDao.saveAttachment(entity);
    }

    @Override
    @Transactional
    public AttachDocs getExcelDocument(String scheme, Long orgid) {
        AttachDocs attachDocs = iExcelImportExportForDashboardDao.getExcelDocument(scheme, orgid);
        return attachDocs;
    }

    @Override
    @Transactional
    public boolean saveExcelData(Class className, List<Object> parseData, Long empId, Date date, List<Long> list) {
        return iExcelImportExportForDashboardDao.saveExcelData(className, parseData, empId, date, list);
    }

    @Override
    @Transactional
    public List<Long> getData(Class className, Date attachDt) {
        return iExcelImportExportForDashboardDao.getData(className, attachDt);
    }
}
