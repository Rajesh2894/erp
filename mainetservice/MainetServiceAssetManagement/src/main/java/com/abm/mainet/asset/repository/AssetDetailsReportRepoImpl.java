/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AssetDetailsReportRepoImpl extends AbstractDAO<Long> implements AssetDetailsReportRepoCustom {

    private final String GET_SERIAL_NO = " SELECT SMK.ASSET_ID,DESCRIPTION,PAYMENT_ORDER_NO,REF_NO_OF_CASH_BOOK,'NA' as REF_NO_OF_REGISTER_IMMOVABLE_property,COST_OF_ACQUISITIONS,TO_WHOM_PAID,PURPOSE_OF_EXPENDITURES,'NA' as SOURCE_OF_FUNDS,'NA' as SPECIFY_HOW_LAND_CURRENTLY_USED,DATE_OF_DISPOSAL,'NA' as RECEIPT_VOUCHER_NO,'NA' as NAME_OF_THE_PERSON_TO_WHOM_LAND_IS_DISPOSED,SALE_VALUE,INITIAL_OF_THE_AUTHORISED_OFFICER,'NA' as REMARKS FROM ( SELECT E.*,F.* FROM (SELECT C.*,D.* FROM(SELECT A.*,B.*FROM (SELECT SERIAL_NO,A.ASSET_ID,A.REMARK AS DESCRIPTION,A.ORGID,(SELECT LOC_NAME_ENG FROM tb_location_mas WHERE LOC_ID IN (SELECT LOC_ID FROM TB_AST_CLASSFCTN WHERE ASSET_ID=A.ASSET_ID)) AS LOCATION_OF_PROPERTY, 'NA' AS NUMBER_OF_QUANTITY,B.CPD_DESC AS CPD_DESC1,B.cpd_id as cpd_id1,C.CPD_DESC AS CPD_DESC2,D.CPD_DESC AS CPD_DESC3,E.CPD_DESC AS CPD_DESC4,E.CPD_id as CPD_id4,F.COD_DESC AS CPD_DESC5,F.cod_id as cod_id5,G.COD_DESC AS CPD_DESC6,G.cod_id as cod_id6,PURPOSE_USAGE AS PURPOSE_OF_EXPENDITURES FROM TB_AST_INFO_MST A,TB_COMPARAM_DET B,TB_COMPARAM_DET C,TB_COMPARAM_DET D,TB_COMPARAM_DET E,TB_COMPARENT_DET F,TB_COMPARENT_DET G WHERE B.CPD_ID=A.ASSET_GROUP AND C.CPD_ID=A.ASSET_STATUS AND D.CPD_ID=A.ACQUISITION_METHOD AND E.CPD_ID=A.ASSET_TYPE AND F.COD_ID=A.ASSET_CLASSIFICATION AND G.COD_ID=A.ASSET_CLASS) A  LEFT JOIN  (SELECT ASSET_ID AS ASSET_ID1,H.PURCHASE_DATE AS FIRST_ACQUISITION_ON,H.MODE_OF_PAYMENT AS MODE_OF_ACQUISITION,H.PURCHASE_DATE AS ACQUISITION_YEAR,H.PURCHASE_ORDER_NO AS PAYMENT_ORDER_NO,H.PURCHASE_COST AS COST_OF_ACQUISITIONS,H.VENDOR AS TO_WHOM_PAID,'NA' AS SOURCE_OF_FUNDS,H.VENDOR AS NAME_OF_CONTRACTOR FROM TB_AST_PURCHASER H) B ON A.ASSET_ID=B.ASSET_ID1) C  LEFT JOIN (SELECT ASSET_ID AS ASSET_ID2,'NA' AS REF_NO_OF_CASH_BOOK,'NA' AS IN_CASE_OF_BUILDING_SPECIFY_USENESS,I.BOOK_VALUE AS OPENING_BOOK_VALUE,I.BOOK_FINYEAR AS YEAR_OF_DEPRICIATION,I.DEPR_VALUE AS DEPRICIATION_PROVIDED,I.BOOK_END_VALUE AS CLOSING_BOOK_VALUE FROM TB_AST_VALUATION_DET I) D ON C.ASSET_ID=D.ASSET_ID2) E LEFT JOIN (SELECT ASSET_ID AS ASSET_ID3,J.DISPOSITION_DATE AS DATE_OF_DISPOSAL,'NA' AS DISPOSAL_PERSON,J.AMOUNT AS SALE_VALUE,'NA' AS NO_OF_QUANTITY_DISPOSE,'NA' AS AMOUNT_RELEASED_IF_SOLD_DATE_OF_CREDIT_IN_TREASURY_BANK_ACCOUNT,'NA' AS BALANCE_QUERY,'COMMING SOON' AS SEQUERITY_DEPOSITE_RETAINED,'NA' AS DATE_AND_AMOUNT_OF_SEQURITY_DEPOSITE_REALEASED,'NA' AS INITIAL_OF_THE_AUTHORISED_OFFICER,'NA' AS INITIAL_AUTHORISED_OFFICER FROM TB_AST_RETIRE J) F ON E.ASSET_ID=F.ASSET_ID3) SMK where cpd_desc5='Immovable' and cpd_desc6='Land'and ASSET_ID in (select ASSET_ID from TB_AST_PURCHASER where PURCHASE_DATE between (select fa_fromdate from tb_financialyear where FA_YEARID=:faYearId) and (select fa_todate from tb_financialyear where FA_YEARID=:faYearId))  and SMK.SERIAL_NO=:serialNo ";

    @Override
    public List<Object[]> findAssetInfo(final Long assetGroup, final Long assetType, final Long assetClass1,
            final Long assetClass2, final Long orgId) {
        final Query query = createNativeQuery(buildUpdateDataQuery(assetGroup, assetType, assetClass1, assetClass2));
        if (assetGroup != null && assetGroup != 0) {
            query.setParameter("assetgroup", assetGroup);
        }
        if (assetType != null && assetType != 0) {
            query.setParameter("assettype", assetType);
        }
        if (assetClass1 != null && assetClass1 != 0) {
            query.setParameter("assetclass1", assetClass1);
        }
        if (assetClass2 != null && assetClass2 != 0) {
            query.setParameter("assetclass2", assetClass2);
        }
        query.setParameter("orgid", orgId);
        return query.getResultList();
    }

    private String buildUpdateDataQuery(final Long assetGroup, final Long assetType, final Long assetClass1,
            final Long assetClass2) {
        final StringBuilder builder = new StringBuilder();
        builder.append(
                " select astinfo.ASSET_ID, astinfo.ASSET_NAME, astinfo.ASSET_GROUP , astinfo.ASSET_TYPE , astinfo.ASSET_CLASSIFICATION, astinfo.ASSET_CLASS, pc.PURCHASE_DATE, dp.ORIGINAL_USEFUL_LIFE , vd.BOOK_END_VALUE,astinfo.SERIAL_NO,astinfo.ASSET_CODE, pc.PURCHASE_COST from  TB_AST_INFO_MST astinfo left outer join  TB_AST_CHART_OF_DEPRETN dp on astinfo.ASSET_ID = dp.ASSET_ID left outer join  TB_AST_PURCHASER pc on astinfo.ASSET_ID = pc.ASSET_ID left outer join  TB_AST_VALUATION_DET vd on astinfo.ASSET_ID = vd.ASSET_ID  where  astinfo.ORGID=:orgid and astinfo.ASSET_APP_STATUS='A'");
        if (assetGroup != null && assetGroup != 0) {
            builder.append(" and  astinfo.ASSET_GROUP =:assetgroup ");
        }
        if (assetType != null && assetType != 0) {
            builder.append(" and astinfo.ASSET_TYPE =:assettype ");
        }
        if (assetClass1 != null && assetClass1 != 0) {
            builder.append(" and astinfo.ASSET_CLASSIFICATION=:assetclass1  ");
        }
        if (assetClass2 != null && assetClass2 != 0) {
            builder.append(" and astinfo.ASSET_CLASS =:assetclass2 ");
        }
        return builder.toString();
    }

    @Override
    public List<Object[]> getAssetValuation(Long assetId, Long faYearId, Long orgId) {
        final Query query = createQuery(buildUpdateDataQuery2(assetId, faYearId, orgId));
        if (assetId != null) {
            query.setParameter("assetId", assetId);
        }
        if (faYearId != null) {
            query.setParameter("bookFinYear", faYearId);
        }
        query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    private String buildUpdateDataQuery2(Long assetId, Long faYearId, Long orgId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(
                "select avd.bookValue,avd.bookFinYear,avd.deprValue,avd.bookEndValue from AssetValuationDetails avd where avd.orgId=:orgId");
        if (assetId != null) {
            builder.append(" and  avd.assetId =:assetId ");
        }
        if (faYearId != null) {
            builder.append(" and  avd.bookFinYear =:bookFinYear ");
        }
        builder.append(" order by 1 asc ");
        return builder.toString();
    }

    @Override
    public List<Object[]> getRetireValue(Long assetId, Long orgId) {
        final Query query = createQuery(buildUpdateDataQuery3(assetId, orgId));
        if (assetId != null) {
            query.setParameter("assetId", assetId);
        }

        query.setParameter("orgId", orgId);
        query.setParameter("authStatus", "A");
        return query.getResultList();
    }

    private String buildUpdateDataQuery3(Long assetId, Long orgId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(
                "select ar.creationDate,ar.personToWhomSold,ar.amount,ar.createdBy from AssetRetire ar where ar.orgId=:orgId");
        if (assetId != null) {
            builder.append(" and  ar.assetId.assetId =:assetId  and ar.authStatus=:authStatus");
        }

        return builder.toString();
    }

    @Override
    public List<Object[]> registerOfMovableReport(Long assetClass1,
            Long orgId, Long faYearId, Long prefixId) {

        final Query query = createNativeQuery(buildUpdateDataQuery3(assetClass1, faYearId, orgId, prefixId));

        if (assetClass1 != null) {
            query.setParameter("assetClass1", assetClass1);
        }

        if (faYearId != null) {
            query.setParameter("faYearId", faYearId);
        }
        query.setParameter("prefixId", prefixId);
        query.setParameter("orgId", orgId);

        return query.getResultList();

    }

    private String buildUpdateDataQuery3(Long assetClass1, Long faYearId,
            Long orgId, Long prefixId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(
                "SELECT SMK.ASSET_ID,\r\n" +
                        "       SERIAL_NO AS SERIAL_NO,\r\n" +
                        "       LOCATION_OF_PROPERTY,\r\n" +
                        "       NUMBER_OF_QUANTITY,\r\n" +
                        "       FIRST_ACQUISITION_ON,\r\n" +
                        "       (SELECT CPD_DESC\r\n" +
                        "          FROM tb_comparam_det\r\n" +
                        "         WHERE cpd_id = MODE_OF_ACQUISITION)\r\n" +
                        "          AS MODE_OF_ACQUISITIONS,\r\n" +
                        "       PAYMENT_ORDER_NO,\r\n" +
                        "       REF_NO_OF_CASH_BOOK,\r\n" +
                        "       COST_OF_ACQUISITIONS,\r\n" +
                        "       (SELECT concat(concat(concat(concat(EMPNAME, ' '), EMPMNAME), ' '),\r\n" +
                        "                      EMPLNAME)\r\n" +
                        "          FROM employee\r\n" +
                        "         WHERE empid = TO_WHOM_PAID)\r\n" +
                        "          AS TO_WHOM_PAIDED,\r\n" +
                        "       PURPOSE_OF_EXPENDITURES,\r\n" +
                        "       SOURCE_OF_FUNDS,\r\n" +
                        "       OPENING_BOOK_VALUE,\r\n" +
                        "       (SELECT concat(concat(year(fa_fromdate), '-'), year(fa_todate))\r\n" +
                        "          FROM tb_financialyear\r\n" +
                        "         WHERE fa_yearid = YEAR_OF_DEPRICIATION)\r\n" +
                        "          AS YEAR_OF_DEPRICIATIONS,\r\n" +
                        "       DEPRICIATION_PROVIDED,\r\n" +
                        "       CLOSING_BOOK_VALUE,\r\n" +
                        "       DATE_OF_DISPOSAL,\r\n" +
                        "       ADDRESS_TO_WHOM_SOLD AS TO_WHO_DISPOSED_AND_NATURE_OF_DISPOSAL,\r\n" +
                        "       DISPOSITION_DATE AS NUMBER_AND_DATE_OF_DISPOSAL_ORDER,\r\n" +
                        "       NO_OF_QUANTITY_DISPOSE,\r\n" +
                        "       SALE_VALUE,\r\n" +
                        "       'NA' AS BALANCE_QUANTITY,\r\n" +
                        "       'NA' AS SEQURITY_DEPOSITE,\r\n" +
                        "       DATE_AND_AMOUNT_OF_SEQURITY_DEPOSITE_REALEASED,\r\n" +
                        "       INITIAL_OF_THE_AUTHORISED_OFFICER,\r\n" +
                        "       ASSET_CODE,\r\n" +
                        "       OLD_DEP_VALUE,\r\n" +
                        "       DETAILS,\r\n" +
                        "       AQU_METHOD\r\n" +
                        "  FROM (SELECT E.*, F.*\r\n" +
                        "          FROM (SELECT C.*, D.*\r\n" +
                        "                  FROM (SELECT A.*, B.*\r\n" +
                        "                          FROM (SELECT SERIAL_NO,\r\n" +
                        "                                       A.ASSET_ID,\r\n" +
                        "                                       A.REMARK AS DESCRIPTION,\r\n" +
                        "                                       A.ORGID,\r\n" +
                        "                                       (SELECT LOC_NAME_ENG\r\n" +
                        "                                          FROM tb_location_mas\r\n" +
                        "                                         WHERE LOC_ID IN\r\n" +
                        "                                                  (SELECT LOC_ID\r\n" +
                        "                                                     FROM TB_AST_CLASSFCTN\r\n" +
                        "                                                    WHERE ASSET_ID =\r\n" +
                        "                                                             A.ASSET_ID))\r\n" +
                        "                                          AS LOCATION_OF_PROPERTY,\r\n" +
                        "                                       NO_OF_SIMILAR_ASSET\r\n" +
                        "                                          AS NUMBER_OF_QUANTITY,\r\n" +
                        "                                       REMARK AS SOURCE_OF_FUNDS,\r\n" +
                        "                                       C.CPD_DESC AS CPD_DESC2,\r\n" +
                        "                                       D.CPD_DESC AS CPD_DESC3,\r\n" +
                        "                                       F.CpD_DESC AS CPD_DESC5,\r\n" +
                        "                                       F.cpd_id AS cod_id5,\r\n" +
                        "                                       G.CpD_DESC AS CPD_DESC6,\r\n" +
                        "                                       G.cpd_id AS cod_id6,\r\n" +
                        "                                       A.DETAILS,\r\n" +
                        "                                       B.CpD_DESC AS AQU_METHOD,\r\n" +
                        "                                       B.cpd_id AS cod_id7,\r\n" +
                        "                                       PURPOSE_USAGE\r\n" +
                        "                                          AS PURPOSE_OF_EXPENDITURES,\r\n" +
                        "                                          A.ASSET_CODE\r\n" +
                        "                                  FROM TB_AST_INFO_MST A,\r\n" +
                        "                                       TB_COMPARAM_DET B,\r\n" +
                        "                                       TB_COMPARAM_DET C,\r\n" +
                        "                                       TB_COMPARAM_DET D,                                       \r\n" +
                        "                                       TB_COMPARAM_DET F,\r\n" +
                        "                                       TB_COMPARAM_DET G\r\n" +
                        "                                 WHERE B.CPD_ID = A.ACQUISITION_METHOD\r\n" +
                        "                                       AND C.CPD_ID = A.ASSET_STATUS\r\n" +
                        "                                       AND D.CPD_ID = A.ACQUISITION_METHOD\r\n" +
                        "                                       AND F.CPD_ID = A.ASSET_CLASSIFICATION\r\n" +
                        "                                       AND G.CPD_ID = A.ASSET_CLASS\r\n" +
                        "                                       and A.ASSET_CODE is not null) A\r\n" +
                        "                               LEFT JOIN\r\n" +
                        "                               (SELECT ASSET_ID AS ASSET_ID1,\r\n" +
                        "                                       H.PURCHASE_DATE\r\n" +
                        "                                          AS FIRST_ACQUISITION_ON,\r\n" +
                        "                                       H.MODE_OF_PAYMENT\r\n" +
                        "                                          AS MODE_OF_ACQUISITION,\r\n" +
                        "                                       H.PURCHASE_DATE AS ACQUISITION_YEAR,\r\n" +
                        "                                       H.PURCHASE_ORDER_NO\r\n" +
                        "                                          AS PAYMENT_ORDER_NO,\r\n" +
                        "                                       H.PURCHASE_COST\r\n" +
                        "                                          AS COST_OF_ACQUISITIONS,\r\n" +
                        "                                       H.VENDOR AS TO_WHOM_PAID,\r\n" +
                        "                                       H.VENDOR AS NAME_OF_CONTRACTOR\r\n" +
                        "                                  FROM TB_AST_PURCHASER H) B\r\n" +
                        "                                  ON A.ASSET_ID = B.ASSET_ID1) C\r\n" +
                        "                       LEFT JOIN\r\n" +
                        "                       (SELECT ASSET_ID AS ASSET_ID2,\r\n" +
                        "                               'NA' AS REF_NO_OF_CASH_BOOK,\r\n" +
                        "                               'NA' AS IN_CASE_OF_BUILDING_SPECIFY_USENESS,\r\n" +
                        "                               I.BOOK_VALUE AS OPENING_BOOK_VALUE,\r\n" +
                        "                               I.BOOK_FINYEAR AS YEAR_OF_DEPRICIATION,\r\n" +
                        "                               I.DEPR_VALUE AS DEPRICIATION_PROVIDED,\r\n" +
                        "                               I.BOOK_END_VALUE AS CLOSING_BOOK_VALUE,\r\n" +
                        "                               I.ACCUM_DEPR_VALUE AS OLD_DEP_VALUE\r\n" +
                        "                          FROM TB_AST_VALUATION_DET I) D\r\n" +
                        "                          ON C.ASSET_ID = D.ASSET_ID2) E\r\n" +
                        "               LEFT JOIN\r\n" +
                        "               (SELECT ASSET_ID AS ASSET_ID3,\r\n" +
                        "                       J.DISPOSITION_DATE AS DATE_OF_DISPOSAL,\r\n" +
                        "                       'NA' AS DISPOSAL_PERSON,\r\n" +
                        "                       J.AMOUNT AS SALE_VALUE,\r\n" +
                        "                       'NA' AS NO_OF_QUANTITY_DISPOSE,\r\n" +
                        "                       'NA'\r\n" +
                        "                          AS AMOUNT_RELEASED_IF_SOLD_DATE_OF_CREDIT_IN_TREASURY_BANK_ACCOUNT,\r\n" +
                        "                       'NA' AS BALANCE_QUERY,\r\n" +
                        "                       'NA' AS SEQUERITY_DEPOSITE_RETAINED,\r\n" +
                        "                       'NA' AS DATE_AND_AMOUNT_OF_SEQURITY_DEPOSITE_REALEASED,\r\n" +
                        "                       j.SOLD_PERSON_ADDR AS ADDRESS_TO_WHOM_SOLD,\r\n" +
                        "                       j.DISPOSITION_DATE,\r\n" +
                        "                       'NA' AS INITIAL_OF_THE_AUTHORISED_OFFICER,\r\n" +
                        "                       'NA' AS INITIAL_AUTHORISED_OFFICER\r\n" +
                        "                  FROM TB_AST_RETIRE J) F\r\n" +
                        "                  ON E.ASSET_ID = F.ASSET_ID3) SMK\r\n" +
                        " WHERE     ORGID =:orgId");
        builder.append(" and  cod_id5=:prefixId ");
        if (assetClass1 != null) {
            builder.append(" and  cod_id6 =:assetClass1 ");
        }
        builder.append("and ASSET_ID in (\r\n" +
                "select ASSET_ID from TB_AST_PURCHASER\r\n" +
                "where PURCHASE_DATE < (select fa_todate from tb_financialyear where FA_YEARID=:faYearId))");
        return builder.toString();
    }

    @Override
    public List<Object[]> getLandByAssetId(final String serialNo, final Long faYearId, final Long orgId) {
        final Query query = createNativeQuery(buildLandByAssetIdQuery(serialNo, faYearId));

        query.setParameter("faYearId", faYearId);

        if (serialNo != null && !serialNo.isEmpty()) {
            query.setParameter("serialNo", serialNo);
        }
        // query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    private String buildLandByAssetIdQuery(final String serialNo, final Long faYearId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(GET_SERIAL_NO);

        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.repository.AssetDetailsReportRepoCustom#getAssetCodeByCategory(java.lang.Long, java.lang.Long)
     * this method will return list of asset code and that is the criteria Query
     */
    @Override
    public List<AssetInformationDTO> getAssetCodeByCategory(Long assetClass, Long orgId) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery();
        Root<AssetInformation> astinfo = criteriaQuery.from(AssetInformation.class);
        criteriaQuery.select(astinfo.get("astCode"));
        criteriaQuery.where(criteriaBuilder.equal(astinfo.get("assetClass2"), assetClass),
                criteriaBuilder.equal(astinfo.get("orgId"), orgId),
                criteriaBuilder.isNotNull((astinfo.get("astCode"))));
        Query query = this.entityManager.createQuery(criteriaQuery);
        List<AssetInformationDTO> result = query.getResultList();
        return result;
    }

}
