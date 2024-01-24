package com.abm.mainet.property.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.utility.GridPaginationUtility;
import com.abm.mainet.property.domain.MutationRegistrationEntity;
import com.abm.mainet.property.dto.MutationIntimationDto;

@Repository
public class MutationIntimationDaoImpl extends AbstractDAO<Long> implements IMutationIntimationDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<MutationRegistrationEntity> searchMutationIntimation(MutationIntimationDto mutationIntimationDto,
            PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO) {
        String coloumnName = null;
        String referenceNumber = null;
        StringBuilder queryString = new StringBuilder(
                "from MutationRegistrationEntity mr where mr.mutId in( select max(m.mutId) from MutationRegistrationEntity m join m.mutRegdetEntityList r "
                        + "  WHERE m.orgId=:orgId and m.applicationNo is null");

        if (gridSearchDTO != null && gridSearchDTO.getSearchOper() != null) {
            coloumnName = MainetConstants.MuatationSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        if (mutationIntimationDto.getPropertyno() != null && !StringUtils.isEmpty(mutationIntimationDto.getPropertyno())) {
            queryString.append(" and m.propertyno=:propertyno ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimMobileNo())) {
            queryString.append(" and r.mobileno=:excuClaimMobileNo ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimName())) {
            queryString.append(" and r.personName=:excuClaimName ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getRegistrationNo())) {
            queryString.append(" and m.registrationNo=:registrationNo ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getMutationOrderNo())) {
            queryString.append(" and m.mutationOrderNo=:mutationNo ");
        }
        if (gridSearchDTO != null && pagingDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchOper())) {
                referenceNumber = "m.";
            } else if ("excuClaimName".equals(gridSearchDTO.getSearchOper())
                    || "excuClaimMobileNo".equals(gridSearchDTO.getSearchOper())) {
                referenceNumber = "r.";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }
        queryString.append(" group by m.mutId )");
        if (gridSearchDTO != null && pagingDTO != null) {
            GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                    MainetConstants.MuatationSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "mr.");
        }

        final Query query = createQuery(queryString.toString());

        query.setParameter("orgId", mutationIntimationDto.getOrgId());

        if (mutationIntimationDto.getPropertyno() != null && !StringUtils.isEmpty(mutationIntimationDto.getPropertyno())) {
            query.setParameter("propertyno", mutationIntimationDto.getPropertyno().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimMobileNo())) {
            query.setParameter("excuClaimMobileNo", mutationIntimationDto.getExcuClaimMobileNo().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimName())) {
            query.setParameter("excuClaimName", mutationIntimationDto.getExcuClaimName().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getRegistrationNo())) {
            query.setParameter("registrationNo", mutationIntimationDto.getRegistrationNo().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getMutationOrderNo())) {
            query.setParameter("mutationNo", mutationIntimationDto.getMutationOrderNo().trim());
        }
        if (pagingDTO != null) {
            GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getcountOfSearchMutationIntimation(MutationIntimationDto mutationIntimationDto,
            PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO) {
        String coloumnName = null;
        String referenceNumber = null;
        StringBuilder queryString = new StringBuilder(
                "from MutationRegistrationEntity mr where mr.mutId in( select max(m.mutId) from MutationRegistrationEntity m join m.mutRegdetEntityList r "
                        + "  WHERE m.orgId=:orgId and m.applicationNo is null");

        if (gridSearchDTO != null && gridSearchDTO.getSearchOper() != null) {
            coloumnName = MainetConstants.MuatationSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        if (mutationIntimationDto.getPropertyno() != null && !StringUtils.isEmpty(mutationIntimationDto.getPropertyno())) {
            queryString.append(" and m.propertyno=:propertyno ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimMobileNo())) {
            queryString.append(" and r.mobileno=:excuClaimMobileNo ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimName())) {
            queryString.append(" and r.personName=:excuClaimName ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getRegistrationNo())) {
            queryString.append(" and m.registrationNo=:registrationNo ");
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getMutationOrderNo())) {
            queryString.append(" and m.mutationOrderNo=:mutationNo ");
        }
        if (gridSearchDTO != null && pagingDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchOper())) {
                referenceNumber = "m.";
            } else if ("excuClaimName".equals(gridSearchDTO.getSearchOper())
                    || "excuClaimMobileNo".equals(gridSearchDTO.getSearchOper())) {
                referenceNumber = "r.";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }
        queryString.append(" group by m.mutId )");

        /*
         * if (gridSearchDTO != null && pagingDTO != null) { GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
         * MainetConstants.MuatationSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "mr."); }
         */
        final Query query = createQuery(queryString.toString());

        query.setParameter("orgId", mutationIntimationDto.getOrgId());

        if (mutationIntimationDto.getPropertyno() != null && !StringUtils.isEmpty(mutationIntimationDto.getPropertyno())) {
            query.setParameter("propertyno", mutationIntimationDto.getPropertyno().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimMobileNo())) {
            query.setParameter("excuClaimMobileNo", mutationIntimationDto.getExcuClaimMobileNo().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getExcuClaimName())) {
            query.setParameter("excuClaimName", mutationIntimationDto.getExcuClaimName().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getRegistrationNo())) {
            query.setParameter("registrationNo", mutationIntimationDto.getRegistrationNo().trim());
        }
        if (!StringUtils.isEmpty(mutationIntimationDto.getMutationOrderNo())) {
            query.setParameter("mutationNo", mutationIntimationDto.getMutationOrderNo().trim());
        }
        if (pagingDTO != null) {
            GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);
        }
        return query.getResultList().size();
    }

    /*
     * @SuppressWarnings("unchecked")
     * @Override public List<ProvisionalAssesmentMstEntity> searchPropetyForView(ProperySearchDto searchDto, PagingDTO pagingDTO,
     * GridSearchDTO gridSearchDTO) { String coloumnName = null; String referenceNumber = null; StringBuilder queryString = new
     * StringBuilder( "from ProvisionalAssesmentMstEntity p where p.proAssId in (" +
     * "select max(am.proAssId) from ProvisionalAssesmentMstEntity am join am.provisionalAssesmentOwnerDtlList o " + "  WHERE  ");
     * if (gridSearchDTO != null && gridSearchDTO.getSearchOper() != null) { coloumnName =
     * MainetConstants.PropertySearch.valueOf(gridSearchDTO.getSearchField()).getColName(); } if (searchDto.getOrgId() > 0) {
     * queryString.append(" am.orgId=:orgId "); } if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
     * queryString.append(" and am.assNo=:assNo "); } if (!StringUtils.isEmpty(searchDto.getOldPid())) {
     * queryString.append(" and am.assOldpropno=:assOldpropno "); } if (searchDto.getLocId() != null && searchDto.getLocId() > 0)
     * { queryString.append(" and am.locId=:locId "); } if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
     * queryString.append(" and am.assWard1=:assWard1 "); } if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
     * queryString.append(" and am.assWard2=:assWard2 "); } if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
     * queryString.append(" and am.assWard3=:assWard3 "); } if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
     * queryString.append(" and am.assWard4=:assWard4 "); } if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
     * queryString.append(" and am.assWard5=:assWard5 "); } if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
     * queryString.append(" and o.assoOwnerName=:assoOwnerName "); } if (!StringUtils.isEmpty(searchDto.getMobileno())) {
     * queryString.append(" and o.assoMobileno=:assoMobileno "); } if (gridSearchDTO != null) { if
     * ("proertyNo".equals(gridSearchDTO.getSearchOper()) || "oldPid".equals(gridSearchDTO.getSearchOper())) { referenceNumber =
     * "am."; } else if ("ownerName".equals(gridSearchDTO.getSearchOper()) || "mobileno".equals(gridSearchDTO.getSearchOper())) {
     * referenceNumber = "o."; } GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName,
     * referenceNumber); } queryString.append(" group by am.assNo )"); if (gridSearchDTO != null) {
     * GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
     * MainetConstants.PropertySearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "p."); } final Query query =
     * createQuery(queryString.toString()); if (searchDto.getOrgId() > 0) { query.setParameter("orgId", searchDto.getOrgId()); }
     * if (!StringUtils.isEmpty(searchDto.getProertyNo())) { query.setParameter("assNo", searchDto.getProertyNo().trim()); } if
     * (!StringUtils.isEmpty(searchDto.getOldPid())) { query.setParameter("assOldpropno", searchDto.getOldPid().trim()); } if
     * (searchDto.getLocId() != null && searchDto.getLocId() > 0) { query.setParameter("locId", searchDto.getLocId()); } if
     * (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) { query.setParameter("assWard1", searchDto.getAssWard1());
     * } if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) { query.setParameter("assWard2",
     * searchDto.getAssWard2()); } if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
     * query.setParameter("assWard3", searchDto.getAssWard3()); } if (searchDto.getAssWard4() != null && searchDto.getAssWard4() >
     * 0) { query.setParameter("assWard4", searchDto.getAssWard4()); } if (searchDto.getAssWard5() != null &&
     * searchDto.getAssWard5() > 0) { query.setParameter("assWard5", searchDto.getAssWard5()); } if
     * (!StringUtils.isEmpty(searchDto.getOwnerName())) { query.setParameter("assoOwnerName", searchDto.getOwnerName().trim()); }
     * if (!StringUtils.isEmpty(searchDto.getMobileno())) { query.setParameter("assoMobileno", searchDto.getMobileno().trim()); }
     * if (pagingDTO != null) { GridPaginationUtility.doGridPaginationLimit(query, pagingDTO); } return query.getResultList(); }
     */

}
