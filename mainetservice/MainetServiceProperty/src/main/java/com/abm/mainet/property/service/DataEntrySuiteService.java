/**
 * 
 */
package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;

/**
 * @author kemburu.jagadeesh
 *
 */
@WebService
public interface DataEntrySuiteService {

    List<FinancialYear> getFinYearListForDataEntry(Date AcqDate, Long orgId);

    Long getFinYearOfDataEntryAssessment();

    SelfAssessmentSaveDTO mapDescriptionAndFactor(SelfAssessmentSaveDTO selfAssessmentSaveDTO);

    SelfAssessmentSaveDTO displayArrearBillForEntry(PropertyInputDto propertyInputDto);

    ProperySearchDto saveDataEntryAndCallWorkFlow(SelfAssessmentSaveDTO selfAssessmentSaveDTO);

    List<LookUp> getTaxCollectorList(Long deptId, Long orgId);

    List<LookUp> getScheduleListForArrEntry(PropertyInputDto propertyInputDto);

    List<LookUp> getAllTaxesForBillGeneration(Long deptId, Long orgId);

    void updateDataEntryForm(List<TbBillMas> billMasList, ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId,
            Long empId, String ipAddress, String dataFrom);

    boolean validateDataEntrySuite(String propNo, Long orgId, Long serviceId);

    // List<TbTaxMas> getAllTaxesForBillGeneration(Long deptId, Long orgId);

    List<LookUp> findDistrictByLandType(LandTypeApiDetailRequestDto dto);

    List<LookUp> getTehsilListByDistrict(LandTypeApiDetailRequestDto dto);

    List<LookUp> getVillageListByTehsil(LandTypeApiDetailRequestDto dto);

    List<LookUp> getMohallaListByVillageId(LandTypeApiDetailRequestDto dto);

    List<LookUp> getStreetListByMohallaId(LandTypeApiDetailRequestDto dto);

    ArrayOfKhasraDetails getKhasraDetails(LandTypeApiDetailRequestDto dto);

    ArrayOfPlotDetails getNajoolDetails(LandTypeApiDetailRequestDto dto);

    ArrayOfDiversionPlotDetails getDiversionDetails(LandTypeApiDetailRequestDto dto);

    List<LookUp> getKhasraNoList(LandTypeApiDetailRequestDto dto);

    List<LookUp> getNajoolPlotList(LandTypeApiDetailRequestDto dto);

    List<LookUp> getDiversionPlotList(LandTypeApiDetailRequestDto dto);

    Map<Long, String> getYearListForDES() throws Exception;

    List<LookUp> getLocationList(Long orgId, Long deptId);

    void saveDataEntryForm(List<TbBillMas> billMasList, ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long lastFinId,
            Long empId, Long processId);

    List<LookUp> getTaxDescription(Organisation org, Long deptId);

    Long getCurrentFinYearId();

    List<TbBillMas> createProvisionalBillForDataEntry(Long deptId, Long scheduleId, Long orgId, Long finYearId, String flatNo);

    List<LookUp> setScheduleListForArrEntry(Date acqDate, Long orgId, Long finYearId);

    void setFactorMappingToAssDto(List<ProvisionalAssesmentFactorDtlDto> provFactList, ProvisionalAssesmentMstDto dto);

    LandTypeApiDetailRequestDto getVsrNo(LandTypeApiDetailRequestDto dto);

    Long getDeptIdByServiceShortName(Long orgId);

    ProvisionalAssesmentMstDto getDataEntryByPropNoOrOldPropNo(long orgId, String assNo, String assOldpropno);

    ProvisionalAssesmentOwnerDtlDto getOwnerDetailByPropNo(String assNo);

	boolean validateDataEntrySuiteWithFlatNo(String rowId, String flatNo, long orgid, Long smServiceId);
	
	Map<Long, String> getConfiguredYearListForDES(String year, Long orgId) throws Exception;
}
