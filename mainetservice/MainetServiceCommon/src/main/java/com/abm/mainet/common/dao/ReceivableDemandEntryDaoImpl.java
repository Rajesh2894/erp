package com.abm.mainet.common.dao;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ReceivableDemandEntry;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationDatasourceLoader;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Repository
public class ReceivableDemandEntryDaoImpl extends AbstractDAO<ReceivableDemandEntry> implements IReceivableDemandEntryDao {

    private static final Logger logger = Logger.getLogger(ReceivableDemandEntryDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> searchDemandEntry(String refNumber, String billNo, Long orgid, String wardCode , Long locId) {
        List<Object[]> list = null;
        Query query = createNativeQuery(buildQuery(refNumber, billNo, wardCode , locId));
        query.setParameter("orgid", orgid);
        query.setParameter("wardCode", wardCode);
        query.setParameter("locId", locId);
        if (StringUtils.isNoneBlank(billNo) || StringUtils.isNoneBlank(refNumber)) { 
            query.setParameter("refNumber", refNumber);        
            query.setParameter("billNo", billNo);
        }
        query.setMaxResults(500);
        try {
            list = (List<Object[]>) query.getResultList();

        } catch (final Exception exception) {
            logger.error("Exception occur while fetching data for supplementry bill ", exception);
        }

        return list;
    }

    private String buildQuery(String refNumber, String billNo, String wardCode, Long locId) {
        StringBuilder searchQuery = new StringBuilder(
                "select a.* from \r\n" + 
                "(select bill.BM_ID,bill.CM_REFNO,csmr.CS_ID,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE billcreatedDate,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE rpCreatedDate\r\n" +
                        "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" +
                        "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" +
                        "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID \r\n" +
                        "where  bill.ORGID=:orgid ");

        if (StringUtils.isNoneBlank(billNo) || StringUtils.isNoneBlank(refNumber)) {       
            searchQuery.append(" and bill.BM_BILLNO=:billNo union select bill.BM_ID,bill.CM_REFNO,csmr.CS_ID,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE billcreatedDate,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE rpCreatedDate \r\n" +
                    "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" +
                    "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" +
                    "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID \r\n" +
                    "where bill.ORGID=:orgid and csmr.CS_CCN=:refNumber\r\n" +
                    "union \r\n" +
                    "select bill.BM_ID,bill.CM_REFNO,csmr.CS_ID,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE billcreatedDate,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE rpCreatedDate \r\n" +
                    "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" +
                    "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" +
                    "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID \r\n" +
                    "where bill.ORGID=:orgid and ap.REF_NO=:refNumber");
        }

        searchQuery.append(") a ,(\r\n" + 
                "select bill.BM_ID,csmr.CS_CCN,ap.REF_NO,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE billcreatedDate,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE rpCreatedDate\r\n" + 
                "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" + 
                "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" + 
                "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID where bill.ORGID=:orgid and csmr.LOC_ID=:locId OR ap.REF_NO like :wardCode\r\n" + 
                ") b\r\n" + 
                "where a.BM_ID=b.BM_ID ORDER BY 1 DESC");
        return searchQuery.toString();
    }

    @Override
    public byte[] generateJasperReportPDF(ByteArrayOutputStream outputStream, Map oParms, String fileName) {
        JRPdfExporter exporter = new JRPdfExporter();
        try {
            Connection conn = ApplicationContextProvider.getApplicationContext()
                    .getBean(ApplicationDatasourceLoader.class).getConnection();
            final JasperReport jasperReport = ReportUtility.getJasperReportLookUp().get(fileName);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, oParms, conn);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); //
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            logger.info("END OF Method");
        } catch (Exception e) {
            logger.error("Error in generate Report..." + e);
        }
        return outputStream.toByteArray();
    }
}
