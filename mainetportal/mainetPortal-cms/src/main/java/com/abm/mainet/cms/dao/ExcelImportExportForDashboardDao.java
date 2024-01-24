package com.abm.mainet.cms.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.service.ExcelImportExportForDashboardService;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.AttachDocs;

@Repository
public class ExcelImportExportForDashboardDao extends AbstractDAO<AttachDocs> implements IExcelImportExportForDashboardDao {

    private static final Logger logger = Logger.getLogger(ExcelImportExportForDashboardService.class);

    @Override
    public void saveAttachment(AttachDocs entity) {
        entityManager.persist(entity);
    }

    @Override
    public AttachDocs getExcelDocument(String scheme, Long orgid) {
        List<AttachDocs> documentList = new ArrayList<>();
        AttachDocs document = new AttachDocs();
        final Query query = entityManager
                .createQuery(
                        "select f from AttachDocs f where f.attId in (select max(a.attId) from AttachDocs a where a.idfId=?1 and a.orgid=?2)");

        query.setParameter(1, scheme);
        query.setParameter(2, orgid);

        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            documentList = query.getResultList();
            document = documentList.get(0);
        }
        return document;
    }

    public boolean saveExcelData(Class className, List<Object> objList, Long empId, Date date, List<Long> list) {
        boolean status = false;
        if (list.size() > 0) {
            Query q = entityManager.createQuery("DELETE FROM " + className.getName() + " ye WHERE ye.id IN (:ids)");
            q.setParameter("ids", list);
            q.executeUpdate();
        }
        if (objList != null && !objList.isEmpty()) {
            for (Object obj : objList) {
                Method method1 = null;
                Method method2 = null;
                Method method3 = null;
                try {
                    method1 = obj.getClass().getMethod("setUpldBy", Long.class);
                    method2 = obj.getClass().getMethod("setUpldDt", Date.class);
                    method3 = obj.getClass().getMethod("setCreatedDate", Date.class);
                } catch (NoSuchMethodException e) {
                    logger.error(e);
                } catch (SecurityException e) {
                    logger.error(e);
                }
                try {
                    method1.invoke(obj, empId);
                    method2.invoke(obj, date);
                    method3.invoke(obj, new Date());
                } catch (IllegalAccessException e) {
                    logger.error(e);
                } catch (IllegalArgumentException e) {
                    logger.error(e);
                } catch (InvocationTargetException e) {
                    logger.error(e);
                }
                entityManager.merge(obj);
            }
            status = true;
        }
        return status;
    }

    @Override
    public List<Long> getData(Class className, Date attachDt) {

        Query query1 = createNativeQuery(
                " SELECT date_add(date_add(LAST_DAY (:date),interval 1 DAY),interval -1 MONTH) AS first_day ,LAST_DAY (:date)");
        query1.setParameter("date", attachDt);
        Object[] obj = (Object[]) query1.getSingleResult();
        Date startDate = (Date) obj[0];
        Date endDate = (Date) obj[1];

        Query query = entityManager
                .createQuery("select a.id from " + className.getName() + " a where a.upldDt BETWEEN ?1 and ?2");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        List<Long> list = query.getResultList();
        return list;

    }

}
