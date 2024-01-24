package com.abm.mainet.swm.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.swm.service.ILogBookReportService;

@Repository
public class iExcelImportExportForLogBookReportDAO extends AbstractDAO<AttachDocs>
        implements ExcelImportExportForLogBookReportDAO {

    private static final Logger logger = Logger.getLogger(ILogBookReportService.class);

    @Override
    public void saveAttachment(AttachDocs entity) {
        create(entity);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean saveExcelData(Class className, List<Object> objList, Long empId, Date date, Long orgId, List<Long> list) {
        boolean status = false;
        if (list.size() > 0) {
            Query q = createQuery("DELETE FROM " + className.getName() + " ye WHERE ye.id IN (:ids)");
            q.setParameter("ids", list);
            q.executeUpdate();
        }
        if (objList != null && !objList.isEmpty()) {
            for (Object obj : objList) {
                Method method1 = null;
                Method method2 = null;
                Method method3 = null;
                Method method4 = null;
                Method method5 = null;
                try {
                    method1 = obj.getClass().getMethod("setUpdatedBy", Long.class);
                    method2 = obj.getClass().getMethod("setUpdatedDate", Date.class);
                    method3 = obj.getClass().getMethod("setCreatedBy", Long.class);
                    method4 = obj.getClass().getMethod("setCreatedDate", Date.class);
                    method5 = obj.getClass().getMethod("setOrgid", Long.class);
                } catch (NoSuchMethodException e) {
                    throw new FrameworkException("Exception Occured While Saving ExcelSheet Data", e);
                } catch (SecurityException e) {
                    throw new FrameworkException("Exception Occured While Saving ExcelSheet Data", e);
                }
                try {
                    method1.invoke(obj, empId);
                    method2.invoke(obj, date);
                    method3.invoke(obj, empId);
                    method4.invoke(obj, new Date());
                    method5.invoke(obj, orgId);
                } catch (IllegalAccessException e) {
                    throw new FrameworkException("Exception Occured While Saving ExcelSheet Data", e);
                } catch (IllegalArgumentException e) {
                    throw new FrameworkException("Exception Occured While Saving ExcelSheet Data", e);
                } catch (InvocationTargetException e) {
                    throw new FrameworkException("Exception Occured While Saving ExcelSheet Data", e);
                }
                entityManager.persist(obj);
            }
            status = true;
        }
        return status;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Long> getData(Class className) {

        Query query = createQuery("select a.id from " + className.getName() + " a");

        List<Long> list = query.getResultList();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AttachDocs getExcelDocument(String scheme, Long orgid) {
        List<AttachDocs> documentList = new ArrayList<>();
        AttachDocs document = new AttachDocs();
        final Query query = createQuery(
                "select f from AttachDocs f where f.attId in (select max(a.attId) from AttachDocs a where a.idfId=?1 and a.orgid=?2)");

        query.setParameter(1, scheme);
        query.setParameter(2, orgid);
        List<AttachDocs> list = query.getResultList();
        if (list != null && !list.isEmpty()) {
            documentList = list;
            document = documentList.get(0);
        }
        return document;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<T> getRecord(Class className, Long orgId) {
        Query query = createQuery("select a from " + className.getName() + " a where a.orgid=?1");
        query.setParameter(1, orgId);

        @SuppressWarnings("unchecked")
        List<T> objList1 = query.getResultList();
        return objList1;
    }

}
