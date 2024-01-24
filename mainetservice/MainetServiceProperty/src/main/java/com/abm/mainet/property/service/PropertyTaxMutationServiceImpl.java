/**
 * 
 */
package com.abm.mainet.property.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.repository.TbOrganisationJpaRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.dto.MutationDetailDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.MainAssessmentDetailRepository;
import com.abm.mainet.property.repository.MainAssessmentOwnerRepository;

import io.swagger.annotations.Api;

/**
 * @author Anwarul.Hassan
 * @since 04-Mar-2021
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.property.service.PropertyTaxMutationService")
@Api(value = "/PropertyTax")
@Path("/PropertyTax")
public class PropertyTaxMutationServiceImpl implements PropertyTaxMutationService {

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private MainAssessmentOwnerRepository ownerRepository;

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

    @Autowired
    private MainAssessmentDetailRepository assessmentDetailRepository;

    @Autowired
    private TbOrganisationJpaRepository organisationJpaRepository;

    @Autowired
    private IFinancialYearService financialYearService;

    @Override
    @GET
    @Path("/MutationCheck/{uniquePropertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String MutationCheck(@PathParam(value = "uniquePropertyId") String uniquePropertyId) {
        MutationDetailDto detailDto = new MutationDetailDto();
        Format f = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String asOnDate = f.format(new Date());

        AssesmentMastEntity mastEntity = assesmentMstRepository.fetchPropertyByUniquePropId(uniquePropertyId);

        if (mastEntity != null) {
            List<AssesmentDetailEntity> detailEntitiyList = assessmentDetailRepository.fetchAssdIdByAssId(mastEntity);
            List<AssesmentOwnerDtlEntity> ownerEntityList = ownerRepository.fetchOwnerDetailListByPropNo(
                    mastEntity.getAssNo(),
                    mastEntity.getOrgId());
            List<Double> totalARV = new ArrayList<Double>();
            for (AssesmentDetailEntity dto : detailEntitiyList) {
                if (dto.getAssdRv() != null) {
                    totalARV.add(dto.getAssdRv());
                }
            }
            detailDto.setValid(MainetConstants.YES);
            detailDto.setAsOnDateTime(asOnDate);
            detailDto.setPropertyID(uniquePropertyId);
            if (mastEntity.getAssWard1() != null) {
                detailDto.setZoneName(
                        CommonMasterUtility.getHierarchicalLookUp(mastEntity.getAssWard1(), mastEntity.getOrgId())
                                .getDescLangFirst());
                detailDto.setZoneID(mastEntity.getAssWard1().toString());
            }
            if (mastEntity.getAssWard2() != null) {
                detailDto.setWardName(
                        CommonMasterUtility.getHierarchicalLookUp(mastEntity.getAssWard2(), mastEntity.getOrgId())
                                .getDescLangFirst());
            }
            if (mastEntity.getAssWard3() != null) {
                detailDto.setMohallaName(
                        CommonMasterUtility.getHierarchicalLookUp(mastEntity.getAssWard3(), mastEntity.getOrgId())
                                .getDescLangFirst());
            } else {
                detailDto.setMohallaName(MainetConstants.CommonConstants.NA);
            }
            for (AssesmentOwnerDtlEntity ownerEntity : ownerEntityList) {
                if (ownerEntity.getAssoOType() != null
                        && ownerEntity.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                    detailDto.setOwnerName(ownerEntity.getAssoOwnerName());
                    detailDto.setFatherName(ownerEntity.getAssoGuardianName());
                    if (ownerEntity.getAssoMobileno().isEmpty() || ownerEntity.getAssoMobileno() == null) {
                        detailDto.setMobile(MainetConstants.ZERO);
                    } else {
                        detailDto.setMobile(ownerEntity.getAssoMobileno());
                    }
                }
            }
            String houseNo = "";
            if (mastEntity.getTppPlotNo() != null) {
                houseNo = mastEntity.getTppPlotNo();
            }
            detailDto.setAddress(houseNo + " " + mastEntity.getAssAddress());
            Double areaOf = mastEntity.getAssPlotArea();
            long area = areaOf.longValue();
            detailDto.setAreaOfLand(String.valueOf(area));
            List<TbBillMas> paidFlagList = propertyMainBillService.fetchAllBillByPropNo(mastEntity.getAssNo(),
                    mastEntity.getOrgId());
            Long yearId = financialYearService.getFinanceYearId(new Date());
            if (CollectionUtils.isNotEmpty(paidFlagList)) {
                TbBillMas billMas = paidFlagList.get(paidFlagList.size() - 1);
                if (StringUtils.equals(billMas.getBmPaidFlag(), MainetConstants.FlagY)
                        && (yearId.equals(billMas.getBmYear()))) {
                    detailDto.setFullHouseTaxPaid(MainetConstants.YES);
                } else {
                    detailDto.setFullHouseTaxPaid(MainetConstants.NO);
                }
            } else {
                detailDto.setFullHouseTaxPaid(MainetConstants.NO);
            }
            Long ulbCode = organisationJpaRepository.findOrgShortNameByOrgId(mastEntity.getOrgId());
            detailDto.setUlbCode(ulbCode.toString());
            if (CollectionUtils.isNotEmpty(totalARV)) {
                Double arv = totalARV.stream().mapToDouble(Double::doubleValue).sum();
                long av = arv.longValue();
                detailDto.setArv(String.valueOf(av));
            } else {
                detailDto.setArv(MainetConstants.ZERO);
            }

        } else {
            detailDto.setValid(MainetConstants.NO);
            detailDto.setAsOnDateTime(asOnDate);
            detailDto.setPropertyID(uniquePropertyId);
        }
        JSONObject jsonObject = new JSONObject(detailDto);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }
}
