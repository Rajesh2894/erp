package com.abm.mainet.swm.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.swm.dao.ExcelImportExportForLogBookReportDAO;
import com.abm.mainet.swm.dto.FineChargeCollectionDTO;
import com.abm.mainet.swm.dto.VehicleLogBookMainPageDTO;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.repository.FineChargeCollectionRepository;

@Service
public class LogBookReportService implements ILogBookReportService {

    @Autowired
    private FineChargeCollectionRepository FineChargeCollectionRepository;

    @Autowired
    private ExcelImportExportForLogBookReportDAO excelImportExportForLogBookReportDAO;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ILogBookReportService#getFineChargeByMonthNo(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public FineChargeCollectionDTO getFineChargeByMonthNo(Long orgId, Long monthNo) {
        List<Object[]> allFineChargesDetails = FineChargeCollectionRepository.getAllFineByMonthNo(orgId, monthNo);
        FineChargeCollectionDTO fineChargeDTO = null;
        List<FineChargeCollectionDTO> fineChargeCollectionList = new ArrayList<>();
        if (allFineChargesDetails != null && !allFineChargesDetails.isEmpty()) {
            for (Object[] allFinecharges : allFineChargesDetails) {
                fineChargeDTO = new FineChargeCollectionDTO();

                if (allFinecharges[0] != null) {
                    fineChargeDTO.setFchEntrydate((Date) allFinecharges[0]);
                }
                if (allFinecharges[1] != null) {
                    fineChargeDTO.setFchName(allFinecharges[1].toString());
                }
                if (allFinecharges[2] != null) {
                    fineChargeDTO.setFchMobno(allFinecharges[2].toString());
                }
                if (allFinecharges[3] != null) {
                    fineChargeDTO.setAddress(allFinecharges[3].toString());
                }
                if (allFinecharges[4] != null) {
                    fineChargeDTO.setWardEng(allFinecharges[4].toString());
                }
                if (allFinecharges[5] != null) {
                    fineChargeDTO.setWardhnd(allFinecharges[5].toString());
                }
                if (allFinecharges[6] != null) {
                    fineChargeDTO.setChargeDescEng(allFinecharges[6].toString());
                }
                if (allFinecharges[7] != null) {
                    fineChargeDTO.setChargeDescHnd(allFinecharges[7].toString());
                }
                if (allFinecharges[8] != null) {
                    fineChargeDTO.setReiceptNo(Long.valueOf(allFinecharges[8].toString()));
                }
                if (allFinecharges[9] != null) {
                    fineChargeDTO.setChargeAmount(allFinecharges[9].toString());
                }
                fineChargeCollectionList.add(fineChargeDTO);
            }
            fineChargeDTO.setFineChargeCollectionList(fineChargeCollectionList);
        }
        return fineChargeDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ILogBookReportService#getVehicleMainPageByVeIdAndMonth(java.lang.Long, java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleLogBookMainPageDTO getVehicleMainPageByVeIdAndMonth(Long orgId, Long veId, Long monthNo) {
        // TODO Auto-generated method stub
        VehicleLogBookMainPageDTO vehicleMain = null;
        List<Object[]> vehicleMainPageData = FineChargeCollectionRepository.getAllVehicleLogMainPageReportDataBy(orgId, veId,
                monthNo);
        List<Object[]> vehicle1 = FineChargeCollectionRepository.getAllVehicleLogMainPageReportFirstDataBy(orgId, veId);

        List<Object[]> vehicle2 = FineChargeCollectionRepository
                .getAllVehicleLogMainPageReportSecondDataBy(orgId, veId);
        List<VehicleLogBookMainPageDTO> vehicleList = new ArrayList<>();

        // we getting from first half form data
        if (vehicleMainPageData != null && !vehicleMainPageData.isEmpty()) {
            vehicleMain = new VehicleLogBookMainPageDTO();
            if (vehicle1 != null) {
                if (vehicle1.get(0)[1] != null) {
                    vehicleMain.setVehicleNo(vehicle1.get(0)[1].toString());
                }

                if (vehicle1.get(0)[2] != null) {
                    vehicleMain.setDryCapacity(new BigDecimal(vehicle1.get(0)[2].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }

                if (vehicle1.get(0)[3] != null) {
                    vehicleMain.setWetCapacity(new BigDecimal(vehicle1.get(0)[3].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }

                if (vehicle1.get(0)[4] != null) {
                    vehicleMain
                            .setHazardiusCapacity(
                                    new BigDecimal(vehicle1.get(0)[4].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
            }

            // we getting from second half form data.
            for (Object[] obj : vehicle2) {

                if (obj[0] != null) {
                    vehicleMain.setToatalPopInbeatCount(
                            new BigDecimal(obj[0].toString()));
                }
                if (obj[1] != null) {
                    vehicleMain.setToatalHouseInbeatCount(new BigDecimal(obj[1].toString()));
                }

                if (obj[3] != null) {
                    vehicleMain.setTotalAnimalCount(
                            new BigDecimal(obj[3].toString()));
                }
                if (obj[2] != null) {
                    vehicleMain.setTotalHouseForCompost(new BigDecimal(obj[2].toString()));
                }
                if (obj[4] != null) {
                    vehicleMain.setTotalEstInbeatCount(
                            new BigDecimal(obj[4].toString()));
                }

            }
            for (Object[] allvehicledata : vehicleMainPageData) {
                VehicleLogBookMainPageDTO vehicleMain1 = new VehicleLogBookMainPageDTO();

                if (allvehicledata[0] != null) {
                    vehicleMain1.setTripDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) allvehicledata[0]));
                }
                if (allvehicledata[1] != null) {
                    vehicleMain1.setTimeIn(new SimpleDateFormat("HH:mm a").format((Date) allvehicledata[1]));
                }
                if (allvehicledata[2] != null) {
                    vehicleMain1.setTimeOut(new SimpleDateFormat("HH:mm a").format((Date) allvehicledata[2]));
                }
                if (allvehicledata[3] != null) {
                    vehicleMain1.setDry(allvehicledata[3].toString());
                }
                if (allvehicledata[4] != null) {
                    vehicleMain1
                            .setDryOutPutwait(new BigDecimal(allvehicledata[4].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (allvehicledata[5] != null) {
                    vehicleMain1.setWet(allvehicledata[5].toString());
                }
                if (allvehicledata[6] != null) {
                    vehicleMain1
                            .setWetOutPutwait(new BigDecimal(allvehicledata[6].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (allvehicledata[7] != null) {
                    vehicleMain1.setHazardous(allvehicledata[7].toString());
                }
                if (allvehicledata[8] != null) {
                    vehicleMain1.setHazardousOutPutwait(
                            new BigDecimal(allvehicledata[8].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (allvehicledata[9] != null) {
                    vehicleMain1.setApproved(allvehicledata[9].toString());
                }
                vehicleList.add(vehicleMain1);
            }
            vehicleMain.setVehicleLogBookMainPageList(vehicleList);
        }
        return vehicleMain;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ILogBookReportService#getCAndDWasteCenterInputByMrfIdAndMonthNo(java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public WasteCollectorDTO getCAndDWasteCenterInputByMrfIdAndMonthNo(Long orgId, Long mrfId, Long monthNo) {
        // TODO Auto-generated method stub
        List<Object[]> cAnddWasteReportData = FineChargeCollectionRepository.getAllCAndDWasteCenterInputReportDataBy(orgId, mrfId,
                monthNo);
        WasteCollectorDTO wasteCollectorDTO = null;
        List<WasteCollectorDTO> wasteCollectorList = new ArrayList<>();
        if (cAnddWasteReportData != null && !cAnddWasteReportData.isEmpty()) {

            for (Object[] cAnddWasteData : cAnddWasteReportData) {
                if (cAnddWasteData[0] != null) {
                    wasteCollectorDTO = new WasteCollectorDTO();
                    wasteCollectorDTO.setCollDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) cAnddWasteData[0]));
                }
                if (cAnddWasteData[1] != null) {
                    wasteCollectorDTO.setAreaName(cAnddWasteData[1].toString());
                }
                if (cAnddWasteData[3] != null) {
                    wasteCollectorDTO.setvType(cAnddWasteData[3].toString());
                }

                if (cAnddWasteData[5] != null) {
                    wasteCollectorDTO.setVehicleNoStr(cAnddWasteData[5].toString());
                }

                if (cAnddWasteData[6] != null) {
                    wasteCollectorDTO
                            .setVeCapacity(new BigDecimal(cAnddWasteData[15].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (cAnddWasteData[7] != null) {
                    wasteCollectorDTO
                            .setCollectionAmount(
                                    new BigDecimal(cAnddWasteData[6].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (cAnddWasteData[8] != null) {
                    wasteCollectorDTO.setBldgPermission(cAnddWasteData[8].toString());
                }
                if (cAnddWasteData[9] != null) {
                    wasteCollectorDTO.setComplainNo(cAnddWasteData[9].toString());
                }
                if (cAnddWasteData[11] != null) {
                    wasteCollectorDTO.setCenterName(cAnddWasteData[11].toString());
                }
                if (cAnddWasteData[14] != null) {
                    wasteCollectorDTO.setWorkName(cAnddWasteData[14].toString());
                }
                wasteCollectorList.add(wasteCollectorDTO);
            }
            wasteCollectorDTO.setWasteCollectorDTOList(wasteCollectorList);
        }
        return wasteCollectorDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ILogBookReportService#saveExcelData(java.lang.Class, java.util.List, java.lang.Long,
     * java.util.Date, java.lang.Long, java.util.List)
     */
    @Override
    @Transactional
    public boolean saveExcelData(Class className, List<Object> parseData, Long empId, Date date, Long orgId, List<Long> list) {
        return excelImportExportForLogBookReportDAO.saveExcelData(className, parseData, empId, date, orgId, list);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.ILogBookReportService#saveAttachment(com.abm.mainet.common.integration.dms.domain.AttachDocs)
     */
    @Override
    @Transactional
    public void saveAttachment(AttachDocs entity) {
        excelImportExportForLogBookReportDAO.saveAttachment(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ILogBookReportService#getData(java.lang.Class)
     */
    @Override
    @Transactional
    public List<Long> getData(Class className) {
        return excelImportExportForLogBookReportDAO.getData(className);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ILogBookReportService#getExcelDocument(java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public AttachDocs getExcelDocument(String scheme, Long orgid) {
        AttachDocs attachDocs = excelImportExportForLogBookReportDAO.getExcelDocument(scheme, orgid);
        return attachDocs;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ILogBookReportService#getRecord(java.lang.Class, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> getRecord(Class className, Long orgId) {
        return excelImportExportForLogBookReportDAO.getRecord(className, orgId);
    }

}
