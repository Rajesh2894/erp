package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.poi.ss.formula.functions.T;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.swm.dto.FineChargeCollectionDTO;
import com.abm.mainet.swm.dto.VehicleLogBookMainPageDTO;
import com.abm.mainet.swm.dto.WasteCollectorDTO;

/**
 * @author Ajay.Kumar
 *
 */
@WebService
public interface ILogBookReportService {

    /**
     * @param monthNo
     * @return
     */
    FineChargeCollectionDTO getFineChargeByMonthNo(Long orgId, Long monthNo);

    VehicleLogBookMainPageDTO getVehicleMainPageByVeIdAndMonth(Long orgId, Long veId, Long monthNo);

    WasteCollectorDTO getCAndDWasteCenterInputByMrfIdAndMonthNo(Long orgId, Long mrfId, Long monthNo);

    public abstract void saveAttachment(AttachDocs entity);

    public abstract boolean saveExcelData(Class className, List<Object> parseData, Long empId, Date date, Long orgId,List<Long> list);
    
    public abstract List<Long> getData(Class className);
    
    public abstract AttachDocs getExcelDocument(String scheme, Long orgid);
    
    public List<T> getRecord(Class className,Long orgId);
}
