package com.abm.mainet.common.constant;

public interface QueryConstants {

    interface BILL_DET_QUERY {
        String BILLDET_BY_COLLSEQ = "select d from TbWtBillDetEntity d where d.bmIdno=:bmIdno and d.orgid=:orgId order by d.collSeq asc ";
    }

    interface FUNCTION_MASTER {
        String QUERY_TO_FIND_PARENT_FUN = "select am from AccountFunctionMasterEntity am  WHERE am.functionParentId is null and am.orgid=:orgId order by functionCompcode desc";
        String QUERY_TO_FIND_PARENT_DET_USING_FUN_ID = "select am from AccountFunctionMasterEntity am  WHERE am.functionId =:functionId";
        String QUERY_TO_GET_CHILD_DET_USING_PARENT_FUN_ID = "select am from AccountFunctionMasterEntity am  WHERE am.functionParentId =:parentId";
        String QUERY_TO_GET_ENTITY_USING_FUNCODE = "select am from AccountFunctionMasterEntity am  WHERE am.functionCode =:functionCode";
        String QUERY_TO_GET_CONFIG_DET_USING_LVL = "select am.codcofdetId,am.codLevel from TbAcCodingstructureDetEntity am where am.tbAcCodingstructureMasEntity=:id";
        String QUERY_TO_GET_PARENT_LVL_CODES = "select am.functionCode from AccountFunctionMasterEntity am  WHERE am.orgid =:orgId and am.functionParentId is null";
        String QEURY_TO_GET_LAST_FUNCTION_LEVEL = "select af from AccountFunctionMasterEntity af,TbAcCodingstructureMasEntity ac  WHERE af.tbAcCodingstructureDet.tbAcCodingstructureMasEntity = ac.codcofId and af.tbAcCodingstructureDet.codLevel = ac.codNoLevel and af.orgid  =:orgId and ac.comCpdId =:prefixId order by af.functionCompcode asc";
        String QEURY_TO_GET_LAST_FUNCTION_LEVEL2 = "SELECT PM.primaryAcHeadId, PM.primaryAcHeadCompcode, PM.primaryAcHeadDesc FROM TbComparamMasEntity G, TbComparamDetEntity N, TbAcCodingstructureMasEntity  J, TbAcCodingstructureDetEntity J1, AccountHeadPrimaryAccountCodeMasterEntity  PM WHERE G.cpmPrefix = :AccountMasterConstantsCMD AND  J.codcofId = J1.tbAcCodingstructureMasEntity AND J.codNoLevel = J1.codLevel  AND N.cpdValue = :prefixId AND J1.codcofdetId = PM.tbAcCodingstructureDet.codcofdetId";
    }

    // the bellow given interface fields are added by prasad 28062016
    interface FUND_MASTER {

        String QUERY_TO_FIND_PARENT_FUND = "select am from AccountFundMasterEntity am  WHERE am.fundParentId is null and am.orgid=:orgId order by 1 desc";
        String QUERY_TO_FIND_PARENT_DET_USING_FUND_ID = "select am from AccountFundMasterEntity am  WHERE am.fundId =:fundId";
        String QUERY_TO_GET_CHILD_DET_USING_PARENT_FUND_ID = "select am from AccountFundMasterEntity am  WHERE am.fundParentId =:parentId";
        String QUERY_TO_GET_CONFIG_DET_USING_LVLS = "select am.codcofdetId,am.codLevel from TbAcCodingstructureDetEntity am where am.tbAcCodingstructureMasEntity=:id";
        String QUERY_TO_GET_ENTITY_USING_FUNDCODE = "select am from AccountFundMasterEntity am  WHERE am.fundCode =:fundCode";
        String QUERY_TO_GET_PARENT_LVLS_CODES = "select am.fundCode from AccountFundMasterEntity am  WHERE am.fundParentId =:parentId";
        String QUERY_TO_GET_ENTITY_USING_PARENTCODE = "select af from AccountFundMasterEntity af  WHERE af.fundCode =:parentCode and af.orgid =:orgId and af.fundParentId.fundId is null";
        String QEURY_TO_GET_LAST_FUND_LEVEL = "select af from AccountFundMasterEntity af,TbAcCodingstructureMasEntity ac  WHERE af.tbAcCodingstructureDet.tbAcCodingstructureMasEntity = ac.codcofId and af.tbAcCodingstructureDet.codLevel = ac.codNoLevel and af.orgid  =:orgId and ac.comCpdId =:prefixId order by af.fundCompositecode asc";
    }

    interface FIELD_MASTER {
        String QUERY_TO_FIND_PARENT_FIELD = "select af from AccountFieldMasterEntity af  WHERE af.fieldParentId.fieldId is null and af.orgid=:orgId order by af.fieldCompcode ASC";
        String QUERY_TO_GET_CODCOFDET_ID = "select am.codcofdetId,am.codLevel from TbAcCodingstructureDetEntity am where am.tbAcCodingstructureMasEntity =:codCofId";
        String QUERY_TO_GET_PARENT_DET_USING_FIELD_ID = "select am from AccountFieldMasterEntity am  WHERE am.fieldId =:fieldId";
        String QUERY_TO_GET_CHILD_DET_USING_PARENT_FIELD_ID = "select am from AccountFieldMasterEntity am  WHERE am.fieldParentId.fieldId =:parentId";
        String QUERY_TO_CHECK_IF_PARENT_CODE_EXISTS = "select af from AccountFieldMasterEntity af  WHERE af.fieldCode =:parentCode and af.orgid =:orgId and af.fieldParentId.fieldId is null";
        String QEURY_TO_GET_LAST_FIELD_LEVEL = "select af from AccountFieldMasterEntity af,TbAcCodingstructureMasEntity ac  WHERE af.tbAcCodingstructureDet.tbAcCodingstructureMasEntity = ac.codcofId and af.tbAcCodingstructureDet.codLevel = ac.codNoLevel and af.orgid  =:orgId and ac.comCpdId =:prefixId order by af.fieldCompcode asc";
        String QUERY_TO_CHECK_CHILD_COMPOSITE_CODE_EXISTS = "select af from AccountFieldMasterEntity af  WHERE af.fieldCompcode =:compositeCode and af.orgid=:orgId";

    }

    interface PRIMARY_CODE_MASTER {
        String QUERY_PRIMARY_CODE_ENTITY = "select am from AccountHeadPrimaryAccountCodeMasterEntity am  WHERE am.primaryAcHeadId =:primaryAcHeadId";
        String QUERY_FOR_ID_NULL = "select am.primaryAcHeadCode from AccountHeadPrimaryAccountCodeMasterEntity am  WHERE am.orgid= :orgId and am.primaryAcHeadParentId.primaryAcHeadId is null";
        String QUERY_FOR_NULL_ID_WITH_ORG = "select am from AccountHeadPrimaryAccountCodeMasterEntity am  WHERE am.orgid= :orgId and am.primaryAcHeadParentId.primaryAcHeadId is null";
        String QUERY_FOR_LAST_LEVEL = "select af from AccountHeadPrimaryAccountCodeMasterEntity af,TbAcCodingstructureMasEntity ac  WHERE af.tbAcCodingstructureDet.tbAcCodingstructureMasEntity = ac.codcofId and af.tbAcCodingstructureDet.codLevel = ac.codNoLevel and af.orgid  =:orgId and ac.comCpdId =:prefixId order by af.primaryAcHeadCompcode asc ";
        String QUERY_FOR_LAST_LEVEL_WITHOUT_BANK_AND_VENDOR = "select af from AccountHeadPrimaryAccountCodeMasterEntity af,TbAcCodingstructureMasEntity ac where af.tbAcCodingstructureDet.tbAcCodingstructureMasEntity = ac.codcofId and af.tbAcCodingstructureDet.codLevel = ac.codNoLevel and af.orgid = :orgId and ac.comCpdId = :prefixId and af.primaryAcHeadId in (select sm.tbAcPrimaryheadMaster.primaryAcHeadId from AccountHeadSecondaryAccountCodeMasterEntity sm where sm.sacLeddgerTypeCpdId in(select cd.cpdId from TbComparamMasEntity cm, TbComparamDetEntity cd where cm.cpmId = cd.tbComparamMas.cpmId and cm.cpmPrefix = 'FTY' and cd.cpdValue = 'OT' and cd.orgid = :orgId ) and sm.orgid = :orgId) order by af.primaryAcHeadCompcode asc";
        String QUERY_FOR_NULL_PARENTS = "select am from AccountHeadPrimaryAccountCodeMasterEntity am  WHERE am.primaryAcHeadParentId is null and am.orgid =:orgId order by primaryAcHeadCode desc ";
        String QUERY_FOR_ALL_COMPOSITECODE_ORG = "select am from AccountHeadPrimaryAccountCodeMasterEntity am  WHERE am.orgid= :orgId ";
        String QUERY_FOR_LAST_ACTIVE_LEVEL = "select af from AccountHeadPrimaryAccountCodeMasterEntity af,TbAcCodingstructureMasEntity ac  WHERE af.tbAcCodingstructureDet.tbAcCodingstructureMasEntity = ac.codcofId and af.tbAcCodingstructureDet.codLevel = ac.codNoLevel and af.orgid  =:orgId and ac.comCpdId =:prefixId  and af.pacStatusCpdId =:pacStatusCpdId ";
    }

    interface BUGOPEN_BALANCE {

        String QUERY_TO_FIND_ASSETS_DATA = "select s.sacHeadId,s.acHeadCode,s.sacStatusCpdId,s.tbAcPrimaryheadMaster.pacStatusCpdId from AccountHeadSecondaryAccountCodeMasterEntity s where  s.orgid=:orgId and substring(s.tbAcPrimaryheadMaster.primaryAcHeadCompcode, 1, 1) = '4' order by s.acHeadCode asc";

        String QUERY_TO_FIND_LIABILITY_DATA = "select s.sacHeadId,s.acHeadCode,s.sacStatusCpdId,s.tbAcPrimaryheadMaster.pacStatusCpdId from AccountHeadSecondaryAccountCodeMasterEntity s where  s.orgid=:orgId and substring(s.tbAcPrimaryheadMaster.primaryAcHeadCompcode, 1, 1) = '3' order by s.acHeadCode asc";
    }

    interface QUERY {
        String CHALLAN_DETAILS = "select c.challanId, c.smServiceId, c.challanNo, c.organisationId, c.apmApplicationId, c.challanDate, c.challanAmount, s.smServiceName, s.smServiceNameMar, s.smSpecificInfoForm, s.smShortdesc,c.userempId,c.smServiceId,c.dpDeptid, c.challanValiDate, c.challanServiceType ,c.loiNo,c.uniquePrimaryId,c.paymentReceiptCategory "
                + "from ChallanMaster c, ServiceMaster s "
                + "where c.status = :challanStatus "
                + "and c.oflPaymentMode = :paymentMode "
                + "and c.challanRcvdFlag = :receivedFlag "
                + "and c.smServiceId = s.smServiceId "
                + "and c.organisationId = s.orgid "
                + "and c.organisationId = :orgId ";
        String CHALLAN_RESTRICTION = " and  c.challanNo = :challanNo ";
        String APPLICATION_RESTRICTION = " and  c.apmApplicationId = :applicationNo ";

        String UPDATE_CHALLAN_RECEIVE_FLAG = "update ChallanMaster as  c set c.challanRcvdFlag = :receivedFlag, c.challanRcvdDate =:challanRcvdDate, c.challanRcvdBy=:challanRcvdBy, c.updatedBy =:updatedBy, c.updatedDate=:updatedDate, c.lgIpMacUpd=:lgIpMacUpd where c.organisationId = :orgId and c.challanNo = :challanNo and  c.challanId = :challanId ";

        String CHALLAN_AT_BANK = "  select c from  ChallanMaster AS c "
                + " where c.organisationId = :orgId "
                + " and c.oflPaymentMode = :paymentMode "
                + " and c.challanRcvdFlag = :receivedFlag "
                + " and c.status = :challanStatus ";

        String SERVICE_RESTRICTION = " and c.apmApplicationId = :apmApplicationId ";
        String CHALLAN_ORDER = " order by c.challanNo desc ";

        String UPDATE_CHECKLIST_VERIFY = "UPDATE TbCfcApplicationMstEntity cs SET cs.apmChklstVrfyFlag=:apmChklstVrfyFlag,cs.apmApplSuccessFlag=:apmApplSuccessFlag, cs.updatedBy =:updatedBy, cs.updatedDate=:updatedDate, cs.lgIpMacUpd=:lgIpMacUpd  WHERE cs.apmApplicationId = :apmApplicationId AND cs.tbOrganisation.orgid = :orgId";
        // Defect#122930 change due to set org id condition dynamically
        String CFC_ATTACHMENT_QUERY_BY_STATUS = "select c.attId,c.userId,c.orgid,c.attFname,c.attPath,c.attBy,c.attFromPath,c.clmId,c.clmDesc,c.clmStatus,c.clmSrNo,c.chkStatus,c.clmAprStatus,c.serviceId,c.applicationId,c.attDate,c.dept,c.clmRemark,c.clmDescEngl,c.mandatory,c.dmsDocId,c.docDescription "
                + "from CFCAttachment c where c.clmAprStatus = :clmAprStatus and c.attId in (select max(d.attId) from CFCAttachment d "
                + "where d.applicationId=:applicationId";
        String CFC_ATTACHMENT_QUERY_RESUBMIT = "select c.attId,c.userId,c.orgid,c.attFname,c.attPath,c.attBy,c.attFromPath,c.clmId,c.clmDesc,c.clmStatus,c.clmSrNo,c.chkStatus,c.clmAprStatus,c.serviceId,c.applicationId,c.attDate,c.dept,c.clmRemark,c.clmDescEngl,c.mandatory,c.dmsDocId,c.docDescription "
                + "from CFCAttachment c where c.clmAprStatus is null and c.attId in (select max(d.attId) from CFCAttachment d "
                + "where d.applicationId=:applicationId";
    }

    interface PLUMBER_QUERY {

        String PLUMBER_LICENSE_DETAIL = "select p from PlumberMaster p where p.apmApplicationId=:applicationId AND p.orgid=:orgId ";
        String PLUMBER_QUALIFICATION_DETAIL = "select p from TbPlumberQualification p where p.plumId=:plumId AND p.orgId=:orgId AND "
                + "p.isDeleted=:isDeleted";
        String PLUMBER_EXPERIENCE_DETAIL = "select p from TbPlumberExperience p where p.plumId=:plumId AND p.orgId=:orgId AND "
                + "p.isDeleted=:isDeleted";
        String UPDATE_PLUM_QUAL_ISDELETED = "UPDATE TbPlumberQualification q set q.isDeleted=:isDeleted"
                + " WHERE q.plumQualId = :plumQualId";
        String UPDATE_PLUM_EXP_ISDELETED = "UPDATE TbPlumberExperience e set e.isDeleted=:isDeleted"
                + " WHERE e.plumExpId = :plumExpId ";
        String PLUM_RENEWAL_SCHEDULAR_DETAIL = "SELECT p FROM TbPlumRenewalScheduler p WHERE p.serviceid=:serviceId AND p.orgId=:orgId";

    }

    interface MASTERS {

        interface BUGOPEN_BALANCE {

            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountBudgetOpenBalanceEntity te where te.faYearid =:faYearid and  te.opnBalType =:cpdIdDrcr and te.tbAcSecondaryheadMaster.sacHeadId =:sacHeadId and te.orgid=:orgId";
        }

        interface TAX_HEADS_MASER {
            String QUERY_TO_GET_ALL_TAX_HEAD_DETAILS = "select te from AccountTDSTaxHeadsEntity te where te.orgid =:orgId";
            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountTDSTaxHeadsEntity te where te.tbAcFundMaster.fundId =:fundId or te.tbAcFundMaster.fundId is null and te.tbAcFunctionMaster.functionId =:functionId or te.tbAcFunctionMaster.functionId is null and te.tbAcFieldMaster.fieldId =:fieldId or te.tbAcFieldMaster.fieldId is null and te.tbAcPrimaryheadMaster.primaryAcHeadId =:pacHeadId and te.tbAcSecondaryheadMaster.sacHeadId =:sacHeadId and te.tbComparamDet.cpdId =:comparamDet";
            String QUERY_TO_GET_TAX_DETAILS = "select t from AccountTDSTaxHeadsEntity t where t.orgid =:orgId";
        }

        interface BUDGET_PROJECTED_REVENUE_ENTRY_MASTER {

            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountBudgetProjectedRevenueEntryEntity te where te.faYearid=:faYearid and te.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid and te.tbDepartment.dpDeptid=:deptId and te.orgid =:orgId and te.fieldId =:fieldId";
            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AccountBudgetProjectedRevenueEntryEntity te where te.orgid =:orgId";
            String QUERY_TO_GET_ALL_BUDGET_IDS = "select  m.tbAcBudgetCodeMaster.prBudgetCodeid,m.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetCodeEntity s,AccountBudgetProjectedRevenueEntryEntity m where m.tbAcBudgetCodeMaster.prBudgetCodeid =s.prBudgetCodeid and m.orgid=s.orgid and s.orgid=:orgId";
            String QUERY_TO_LASTFINYEARS_ORGAMOUNT = "select r.orginalEstamt,r.revisedEstamt from AccountBudgetProjectedRevenueEntryEntity r where r.faYearid=:faYearid and r.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid and r.orgid=:orgId";
            String QUERY_TO_GET_ALL_TRANSACTIONS = "select te from AccountBudgetAdditionalSupplementalEntity te where te.prProjectionid=:prProjectionId and te.faYearid=:faYearId and te.orgid =:orgId";
        }

        interface BUDGET_PROJECTED_EXPENDITURE_MASTER {

            String QUERY_TO_GET_BUDGET_PROJ_EXP_ENTRY_BY_FN_YEAR_BUDGET_CODE = "select te from AccountBudgetProjectedExpenditureEntity te where te.faYearid=:faYearid and te.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid and te.tbDepartment.dpDeptid=:deptId and te.orgid =:orgId and te.fieldId =:fieldId";
            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AccountBudgetProjectedExpenditureEntity te where te.orgid =:orgId";
            String QUERY_TO_GET_ALL_BUDGET_IDS = "select  m.tbAcBudgetCodeMaster.prBudgetCodeid,m.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetCodeEntity s,AccountBudgetProjectedExpenditureEntity m where m.tbAcBudgetCodeMaster.prBudgetCodeid =s.prBudgetCodeid and m.orgid=s.orgid and s.orgid=:orgId";
            // String QUERY_TO_GET_ALL_BUDGET_IDS = "select
            // m.tbAcBudgetCodeMaster.prBudgetCodeid,m.tbAcBudgetCodeMaster.prBudgetCode from
            // AccountBudgetProjectedExpenditureEntity m where m.tbAcBudgetCodeMaster.orgid=:orgId";
            String QUERY_TO_LASTFINYEARS_ORGAMOUNT = "select r.orginalEstamt,r.revisedEstamt from AccountBudgetProjectedExpenditureEntity r where r.faYearid=:faYearid and r.tbAcBudgetCodeMaster.prBudgetCodeid=:prBudgetCodeid and r.orgid=:orgId";
        }

        interface BUDGET_ALLOCATION_MASTER {

            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountBudgetAllocationEntity te where te.financialYear =:faYearid and te.tbAcBudgetCodeMaster.prBudgetCodeid=:prRevBudgetCode and te.orgid =:orgId";
            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AccountBudgetAllocationEntity te where te.orgid =:orgId";
            String QUERY_TO_GET_ALL_BUDGET_IDS = "select  m.tbAcBudgetCodeMaster.prBudgetCodeid,m.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetAllocationEntity m where m.tbAcBudgetCodeMaster.orgid=:orgId";
        }

        interface DEPOSIT_ADVN_MAPPING {
            String SELECT_ENTITY_PART1 = "select entity from AccountDepositeAndAdvnMasterEntity entity where entity.orgid =";
            String SELECT_ENTITY_PART2 = " and entity.tbComparamDetHdm.cpdId= ";
            String SELECT_ENTITY_PART3 = " and entity.tbComparamDetDtyAty.cpdId= ";
            String SELECT_ENTITY_PART4 = " and entity.dept=";
            String SELECT_ENTITY_PART5 = " and entity.dept is null";
        }

        interface LIABILITY_BOOKING {

            String CHECK_IF_LIABILITY_EXISTS = "select lb from AccountLiabilityBookingEntity lb where lb.tbAcTenderMaster.trTenderId =:trTenderId and lb.orgid =:orgId";

        }

        interface TENDER_ENTRY {
            String QUERY_TO_GET_ALL_TENDER_ENTRY_DETAILS = "select te from AccountTenderEntryEntity te where te.orgid =:orgId order by 1 desc";
            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountTenderDetEntity te where te.tbAcFundMaster.fundId =:fundId or te.tbAcFundMaster.fundId is null and te.tbAcFunctionMaster.functionId =:functionId or te.tbAcFunctionMaster.functionId is null and te.tbAcFieldMaster.fieldId =:fieldId or te.tbAcFieldMaster.fieldId is null and te.tbAcPrimaryheadMaster.primaryAcHeadId =:pacHeadId and te.tbAcSecondaryheadMaster.sacHeadId =:sacHeadId";
            String QUERY_TO_GET_DISTINCT_TENDER_ENTRY = "select distinct te from AccountTenderEntryEntity te,AccountTenderDetEntity td where te.orgid =:orgId and te.trTenderId=td.tbAcTenderMaster.trTenderId and te.orgid=td.orgid";
        }

        interface DEPOSITE_SLIP {

            String DEPOSITE_SLIP_RECEIPT = "select sm.rmRcptno,sm.rmDate,sm.dpDeptId,sum(fd.rfFeeamount),sm.rmRcptid "
                    + "from TbServiceReceiptMasEntity sm,"
                    + "TbSrcptModesDetEntity md, "
                    + "TbSrcptFeesDetEntity fd "
                    + "where sm.rmRcptid = md.rmRcptid.rmRcptid "
                    + "and sm.orgId = md.orgId "
                    + "and sm.rmRcptid = fd.rmRcptid.rmRcptid "
                    + "and sm.orgId = fd.orgId "
                    + "and fd.depositeSlipId is null "
                    + "and md.cpdFeemode=:cpdMode "
                    + "and sm.rmDate between :fromDate and :toDate ";

            String DEPOSITE_SLIP_RECEIPT_VIEW = "select sm.rmRcptno,sm.rmDate,sm.dpDeptId,sum(fd.rfFeeamount),sm.rmRcptid "
                    + "from TbServiceReceiptMasEntity sm,"
                    + "TbSrcptModesDetEntity md, "
                    + "TbSrcptFeesDetEntity fd "
                    + "where sm.rmRcptid = md.rmRcptid.rmRcptid "
                    + "and sm.orgId = md.orgId "
                    + "and sm.rmRcptid = fd.rmRcptid.rmRcptid "
                    + "and sm.orgId = fd.orgId "
                    + "and fd.depositeSlipId=:depositSlipId "
                    + "and md.cpdFeemode=:cpdMode ";

            String QUERY_TO_SELECT_SLIP_MASTER = "select depSlipMaster from AccountBankDepositeSlipMasterEntity depSlipMaster where depSlipMaster.dps_del_flag IS NULL and depSlipMaster.orgid = :orgId ";

            String QUERY_TO_SELECT_DEP_SLIP_MASTER = "select depSlipMaster from AccountBankDepositeSlipMasterEntity depSlipMaster where depSlipMaster.depositeSlipId=:slipId";
        }

        interface BUDGET_CODE_CREATION {

            String QUERY_TO_SEARCH_ALL_GRID_DATA = "select te from AccountBudgetCodeEntity te where te.orgid =:orgId";
            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountBudgetCodeEntity te where te.orgid =:orgId and te.cpdIdStatusFlag='A'";
        }

        interface BUDGET_ADDITIONAL_SUPPLEMENTAL_MASTER {

            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AccountBudgetAdditionalSupplementalEntity te where te.orgid =:orgId and te.budgetIdentifyFlag =:budgIdentifyFlag";
        }

        interface BUDGET_REAPPROPRIATION_MASTER {

            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AccountBudgetReappropriationMasterEntity te where te.orgid =:orgId and te.budgetIdentifyFlag =:budgIdentifyFlag";
            String QUERY_TO_GET_AUTHORIZATION_GRID_DATA_ = "select te from AccountBudgetReappropriationMasterEntity te where te.orgid =:orgId and te.budgetIdentifyFlag =:budgIdentifyFlag ";

        }

        interface BUDGET_ESTIMATIONPREPARATION_MASTER {

            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountBudgetEstimationPreparationEntity te where te.faYearid =:faYearid and te.tbAcBudgetCodeMaster.prBudgetCodeid=:prRevBudgetCode and te.orgid =:orgId and te.dpDeptid=:dpDeptid";
            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AccountBudgetEstimationPreparationEntity te where te.orgid =:orgId";
            String QUERY_TO_GET_ALL_BUDGET_IDS = "select  m.tbAcBudgetCodeMaster.prBudgetCodeid,m.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetCodeEntity s,AccountBudgetEstimationPreparationEntity m where m.tbAcBudgetCodeMaster.prBudgetCodeid =s.prBudgetCodeid and s.orgid=:orgId";
        }

        interface BUDGETORY_REVISION_MASTER {

            String QUERY_TO_CHEK_DUPLICATE_ENTRY = "select te from AccountBudgetoryRevisionEntity te where te.faYearid =:faYearid and te.tbAcBudgetCodeMaster.prBudgetCodeid=:prRevBudgetCode and te.orgid =:orgId";
            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AccountBudgetoryRevisionEntity te where te.orgid =:orgId";
            // String QUERY_TO_GET_ALL_BUDGET_IDS = "select
            // m.tbAcBudgetCodeMaster.prBudgetCodeid,m.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetoryRevisionEntity m
            // where m.tbAcBudgetCodeMaster.orgid=:orgId";
            String QUERY_TO_GET_ALL_BUDGET_IDS = "select  m.tbAcBudgetCodeMaster.prBudgetCodeid,m.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetCodeEntity s,AccountBudgetoryRevisionEntity m where m.tbAcBudgetCodeMaster.prBudgetCodeid =s.prBudgetCodeid and s.orgid=:orgId";
        }

        interface AccountRecieptEntryMaster {

            String QUERY_TO_RECEIPT = "select aRcpt from TbServiceReceiptMasEntity aRcpt,Department dPart where aRcpt.orgId = ";
            String QUERY_TO_RECEIPT_ENTRY = " and dPart.dpDeptid=aRcpt.dpDeptId  AND  aRcpt.receiptDelFlag IS NULL  ";
            String QUERY_TO_RECEIPT_FLAG = " and dPart.dpDeptid=aRcpt.dpDeptId and dPart.dpDeptcode='AC'  AND  aRcpt.receiptDelFlag IS NULL  ";
        }

        interface AccountBillEntryMaster {

            String QUERY_TO_BILL_ENTRY = "select distinct m from AccountBillEntryMasterEnitity m,AccountBillEntryExpenditureDetEntity e where m.billDeletionFlag IS NULL and m.orgId=:orgId";

            String QUERY_TO_UPDATE_BILLENTRY_MASTER = "UPDATE AccountBillEntryMasterEnitity bm SET "
                    + "bm.billDeletionFlag=:billDeletionFlag, bm.billDeletionOrderNo=:billDeletionOrderNo , bm.billDeletionAuthorizedBy=:billDeletionAuthorizedBy"
                    + ",bm.billDeletionDate=:billDeletionDate, bm.billDeletionRemark=:billDeletionRemark, bm.updatedBy=:updatedBy, bm.updatedDate=:updatedDate"
                    + ", bm.lgIpMacAddressUpdated=:lgIpMacAddressUpdated, bm.deletionPostingDate=:deletionPostingDate WHERE bm.id=:id AND bm.orgId=:orgId";
        }

        interface AccountBudgetAdditionalSupplemental {
            String QUERY_TO_GET_AUTH_GRID_DATA = "select te from AccountBudgetAdditionalSupplementalEntity te where te.orgid =:orgId and te.budgetIdentifyFlag =:budgIdentifyFlag ";
        }

        interface AccountBudgetProjectedExpenditure {
            String QUERY_TO_CHK_TRANSACTION = "select te from AccountBudgetAdditionalSupplementalEntity te where te.prExpenditureid=:prExpenditureId and te.faYearid=:faYearId and te.orgid =:orgId";
        }

        interface ChequeDishonour {
            String QUERY_TO_SELECT_DIST_DEPOSIT_SLIP = "SELECT distinct te.depositeSlipId,te.depositeSlipNumber,te.depositeSlipDate,tm.rdModesid,tm.rdChequeddno,tm.rdChequedddate,tm.checkStatus,tm.rdAmount FROM AccountBankDepositeSlipMasterEntity te,TbSrcptModesDetEntity tm,TbSrcptFeesDetEntity td WHERE te.orgid =:orgId and te.depositeSlipId = td.depositeSlipId and te.orgid=td.orgId and td.rmRcptid.rmRcptid=tm.rmRcptid.rmRcptid and td.orgId=tm.orgId";
            String QUERY_TO_SELECT_DIST_DEPOSIT_SLIP_ID = "SELECT distinct te.depositeSlipId,te.depositeSlipNumber,te.depositeSlipDate,tm.rdModesid,tm.rdChequeddno,tm.rdChequedddate,tm.checkStatus,tm.rdAmount FROM AccountBankDepositeSlipMasterEntity te,TbSrcptModesDetEntity tm,TbSrcptFeesDetEntity td WHERE tm.orgId =:orgId and tm.rmRcptid.rmRcptid=td.rmRcptid.rmRcptid and tm.orgId=td.orgId and td.depositeSlipId=te.depositeSlipId and td.orgId=te.orgid";
            String QUERY_TO_UPDATE_SCRPT_MODE_ENTITY = "UPDATE TbSrcptModesDetEntity tm SET tm.checkStatus=:flag,tm.rdSrChkDate =:chequeDishonourDate,tm.rdSrChkDisChg =:chequeDisChgAmt,tm.rd_dishonor_remark =:remarks WHERE tm.rdModesid =:receiptModeId and tm.orgId =:orgId";
            String QUERY_TO_SELECT_DIST_BUDGET_CODE = "select distinct td.sacHeadId from TbTaxMasEntity tm,TbTaxAcMappingEntity td where tm.department.dpDeptid =:departmentId and tm.taxDescId =:voucherSubTypeId and tm.orgid =:orgId and tm.listOfTbTaxBudgetCodeMas.taxbId=td.taxbId and tm.orgid=td.orgId";
        }

        interface ChequeOrCashDepositeMaster {
            String QUERY_TO_SELECT_BANK_DETAIL = "select distinct t1.bankId,t1.bank from TbSrcptModesDetEntity t ,"
                    + "BankMasterEntity t1,TbServiceReceiptMasEntity rm,TbSrcptFeesDetEntity det where t.cbBankid=t1.bankId     and rm.rmRcptid=t.rmRcptid and rm.orgId=t.orgId and det.rmRcptid=rm.rmRcptid "
                    + "and rm.rmDate between :fromDate and :toDate and  t.orgId=:orgId and  det.depositeSlipId is null";

            String QUERY_TO_SELECT_DRAWEE_BANK_DETAIL = " SELECT distinct a.rmRcptno, a.rmDate,dp.dpDeptcode,cd.cpdDesc,b.tranRefNumber,b.tranRefDate,cb.baAccountName,b.tranRefDate,b.rdAmount,b.rdModesid,a.rmRcptid,cb.bankId.branch "
                    + " FROM TbSrcptModesDetEntity b ,BankAccountMasterEntity cb,TbComparamDetEntity cd,TbServiceReceiptMasEntity a,Department dp,TbSrcptFeesDetEntity det "
                    + " WHERE a.rmRcptid = b.rmRcptid "
                    + " AND a.orgId = b.orgId "
                    + " AND b.baAccountid=cb.baAccountId "
                    + " AND b.cpdFeemode=cd.cpdId "
                    + " AND dp.dpDeptid=a.dpDeptId "
                    + " AND a.rmRcptid = det.rmRcptid "
                    + " AND det.depositeSlipId =:depositeSlipId ";

            String QUERY_TO_SELECT_DRAWEE_BANK_DETAIL_VIEW = " SELECT distinct a.rmRcptno, a.rmDate,dp.dpDeptcode,cd.cpdDesc,b.rdChequeddno,b.rdChequedddate,cb.bank,b.rdChequedddate,b.rdAmount,b.rdModesid,a.rmRcptid,cb.branch "
                    + " FROM TbSrcptModesDetEntity b ,BankMasterEntity cb,TbComparamDetEntity cd,TbServiceReceiptMasEntity a,Department dp,TbSrcptFeesDetEntity det "
                    + " WHERE a.rmRcptid = b.rmRcptid "
                    + " AND a.orgId = b.orgId "
                    + " AND b.cbBankid=cb.bankId "
                    + " AND b.cpdFeemode=cd.cpdId "
                    + " AND dp.dpDeptid=a.dpDeptId "
                    + " AND a.rmRcptid = det.rmRcptid "
                    + " AND det.depositeSlipId =:depositeSlipId ";

            String QUERY_TO_SELECT_CHEQUE_DD_DETAIL = " SELECT distinct a.rmRcptno, a.rmDate,dp.dpDeptcode,cd.cpdDesc,b.rdChequeddno,b.rdChequedddate,cb.bank,b.rdChequedddate,b.rdAmount,b.rdModesid,a.rmRcptid,cb.branch "
                    + " FROM TbSrcptModesDetEntity b ,BankMasterEntity cb,TbComparamDetEntity cd,TbServiceReceiptMasEntity a,Department dp,TbSrcptFeesDetEntity det ,AccountHeadSecondaryAccountCodeMasterEntity sa,AccountFunctionMasterEntity fm"
                    + " WHERE a.rmRcptid = b.rmRcptid "
                    + " AND a.orgId = det.orgId "
                    + " AND det.orgId = b.orgId "
                    + " AND a.orgId = b.orgId "
                    + " AND b.cbBankid=cb.bankId "
                    + " AND b.cpdFeemode=cd.cpdId "
                    + " AND dp.dpDeptid=a.dpDeptId "
                    + " AND a.rmRcptid = det.rmRcptid "
                    +" AND det.sacHeadId=sa.sacHeadId AND  sa.tbAcFunctionMaster.functionId=fm.functionId"
                    + " AND a.receiptDelFlag IS NULL and"
                    + " b.cpdFeemode = :mode AND det.depositeSlipId is null ";

            String QUERY_TO_SELECT_RECEIPT_LEDGER = "select depSlipMaster "
                    + "from AccountBankDepositeSlipMasterEntity depSlipMaster "
                    + "where depSlipMaster.depositeFromDate >= :fromDate and depSlipMaster.depositeToDate <=:toDate "
                    + "and depSlipMaster.orgid = :orgId ";

            String QUERY_TO_SELECT_LEDGER_DETAIL = "select sum(fd.rfFeeamount),fd.rfFeeid,fd.sacHeadId "
                    + " from TbServiceReceiptMasEntity sm,TbSrcptModesDetEntity md,TbSrcptFeesDetEntity fd "
                    + " where sm.rmRcptid = md.rmRcptid.rmRcptid and sm.orgId = md.orgId and sm.rmRcptid = fd.rmRcptid.rmRcptid and sm.orgId = fd.orgId "
                    + " and md.cpdFeemode=:cpdMode and  fd.depositeSlipId =:depositSlipId "
                    + " group by fd.rfFeeid,fd.sacHeadId";

            String QUERY_TO_SELECT_RECEIPT_LEDGER_DETAIL = "select distinct sm.rmRcptid,sm.rmDate,sm.rmRcptno,sm.manualReceiptNo,sm.rmAmount "
                    + " from TbServiceReceiptMasEntity sm,TbSrcptModesDetEntity md,TbSrcptFeesDetEntity fd "
                    + " where sm.rmRcptid = md.rmRcptid.rmRcptid and sm.orgId = md.orgId and sm.rmRcptid = fd.rmRcptid.rmRcptid and sm.orgId = fd.orgId "
                    + " and md.cpdFeemode=:cpdMode and  fd.depositeSlipId =:depositSlipId ";

            String QUERY_TO_SELECT_LEDGER_WISE_DATA = "select budget from AccountHeadSecondaryAccountCodeMasterEntity budget "
                    + " where budget.sacHeadId =:sacHeadId";

            String QUERY_TO_SELECT_BANK_DEPOSIT_SLIP_ENTRY = "select bm.depositeSlipNumber,bm.depositeSlipDate from AccountBankDepositeSlipMasterEntity bm,TbSrcptModesDetEntity md,TbSrcptFeesDetEntity fd "
                    + " where md.rdModesid =:receiptModeId and md.orgId =:orgId and md.rmRcptid.rmRcptid=fd.rmRcptid.rmRcptid and md.orgId=fd.orgId and fd.depositeSlipId=bm.depositeSlipId and fd.orgId=bm.orgid";

            String QUERY_TO_SELECT_RECEIPT_ENTRY = "select fd.rmRcptid.rmRcptno,fd.rmRcptid.rmDate,md.rdAmount,fd.rmRcptid.dpDeptId from TbSrcptModesDetEntity md,TbSrcptFeesDetEntity fd "
                    + " where md.rdModesid =:receiptModeId and md.orgId =:orgId and md.rmRcptid.rmRcptid=fd.rmRcptid.rmRcptid and md.orgId=fd.orgId";

            String QUERY_TO_UPDATE_DEPOSIT_SLIP = "UPDATE AccountBankDepositeSlipMasterEntity dsm SET "
                    + "dsm.dps_del_flag=:dps_del_flag, dsm.dps_del_order_no=:dps_del_order_no , dsm.dps_del_auth_by=:dps_del_auth_by"
                    + ",dsm.dps_del_date=:dps_del_date, dsm.dps_del_remark=:dps_del_remark, dsm.updatedBy=:updatedBy, dsm.updatedDate=:updatedDate"
                    + ", dsm.lgIpMacUpd=:lgIpMacUpd WHERE dsm.depositeSlipId=:depositeSlipId AND dsm.orgid=:orgId";

            String QUERY_TO_REMOVE_DEPOSIT_NO_REF = "UPDATE TbSrcptFeesDetEntity det SET det.depositeSlipId=:depositeNull"
                    + " WHERE det.depositeSlipId=:depositeSlipId AND det.orgId=:orgId";
        }

        interface AccountDepositMaster {
            String QUERY_TO_SELECT_ACC_DEPOSIT_ENTITY = "select de from AccountDepositEntity de where de.orgid =:orgId ";
        }

        interface AccountJournalVoucherEntryMaster {
            String QUERY_SEARCH_VOUCHER_DATA = "SELECT distinct details.master.vouId,details.master.vouNo,details.master.vouDate,details.master.vouPostingDate,details.master.vouTypeCpdId,"
                    + " details.master.vouReferenceNo,details.master.narration,details.master.authoFlg,details.master.entryType "
                    + " FROM AccountVoucherEntryDetailsEntity details "
                    + "WHERE details.master.authoFlg IN(:authStatus1,:authStatus2) and";

            String QUERY_TO_SELECT_VOUCHER = "SELECT voucher FROM AccountVoucherEntryEntity voucher WHERE voucher.vouId=:rowId";

            String QUERY_TO_DELETE_VOUCHER_ENTRY = "delete FROM AccountVoucherEntryDetailsEntity detail WHERE detail.voudetId=:voudetId";

            String QUERY_TO_DELETE_VOUCHER_ROWID = "delete FROM AccountVoucherEntryEntity entity,AccountVoucherEntryDetailsEntity det WHERE entity.vouId=:rowId";

            String QUERY_VOUCHER_DETAIL = "SELECT voucher FROM AccountVoucherEntryEntity voucher WHERE voucher.vouReferenceNo =:depositSlipNo and voucher.vouPostingDate =:depositDate and  voucher.vouTypeCpdId =:voucherTypeId and voucher.vouSubtypeCpdId =:voucherSubTypeId and voucher.org =:orgId";

            String QUERY_RECEIPT_VOUCHER_DETAIL = "SELECT voucher FROM AccountVoucherEntryEntity voucher WHERE voucher.vouReferenceNo =:depositSlipNo and voucher.vouPostingDate =:receiptDate and  voucher.vouTypeCpdId =:voucherTypeId and voucher.vouSubtypeCpdId =:voucherSubTypeId and voucher.org =:orgId";

            String QUERY_RECEIPT_REVERSAL_VOUCHER_DETAIL = "SELECT voucher FROM AccountVoucherEntryEntity voucher WHERE voucher.vouReferenceNo =:depositSlipNo and voucher.vouDate =:receiptDate and  voucher.vouTypeCpdId =:voucherTypeId and voucher.org =:orgId and voucher.dpDeptid =:dpDeptId and voucher.vouSubtypeCpdId NOT IN :vouSubTypes and voucher.entryType <> :entryTypeId";

            String QUERY_UPDATE_ACC_VOUCHER_ENTRY = "UPDATE AccountVoucherEntryEntity ve SET ve.authRemark =:authRemark WHERE ve.vouId =:vouId";

            String QUERY_TO_GET_ALL_TRANSACTIONS = "select ve from AccountVoucherEntryDetailsEntity ve where ve.sacHeadId=:sacHeadId and ve.orgId =:orgId";

        }

        interface AdvanceEntryMaster {
            String QUERY_TO_GET_ALL_GRID_DATA = "select te from AdvanceEntryEntity te where te.orgid =:orgId";
        }

        interface BankReconciliationMaster {
            // and bd.depositeTypeFlag =:depositeTypeFlag
            String QUERY_TO_GET_GRID_RECEIPT_SEARCH = "select distinct tm.rdModesid,bd.depositeSlipDate,bd.depositeSlipNumber,tm.rdChequeddno,tm.rdChequedddate,tm.rdAmount,tm.rdSrChkDis,bd.dps_del_flag,tm.rdSrChkDate,bd.depositeTypeFlag from AccountBankDepositeSlipMasterEntity bd,TbSrcptModesDetEntity tm,TbSrcptFeesDetEntity td where bd.depositeBAAccountId =:bankAccount  and bd.orgid =:orgId and bd.orgid=td.orgId and bd.depositeSlipId=td.depositeSlipId and td.rmRcptid.rmRcptid=tm.rmRcptid.rmRcptid and td.orgId=tm.orgId";

            // and pm.paymentModeId.cpdId =:tranMode
            String QUERY_TO_GET_GRID_PAYMENT_ENTRY = "select distinct pm.paymentId,pm.paymentDate,pm.paymentNo,cb.chequeNo,pm.instrumentDate,pm.paymentAmount,pm.paymentDeletionFlag,pm.chequeClearanceDate, cb.cpdIdstatus, (select cpdValue from TbComparamDetEntity x where x.cpdId=pm.paymentModeId.cpdId) as transactionMode from AccountPaymentMasterEntity pm,TbAcChequebookleafDetEntity cb where pm.paymentId=cb.paymentId and pm.baBankAccountId.baAccountId =:bankAccount  and pm.orgId =:orgId and pm.paymentId=cb.paymentId and pm.orgId=cb.orgid";

            String QUERY_TO_UPDATE_RECEIPT_DATA = "UPDATE TbSrcptModesDetEntity tm SET tm.rdSrChkDate =:tranDate, tm.checkStatus =:receiptTypeFlag, tm.updatedBy=:updatedBy, tm.updatedDate=:updatedDate, tm.lgIpMacUpd=:updatedIpmacId  WHERE tm.rdModesid =:receiptModeId  and tm.orgId =:orgId";

            String QUERY_TO_UPDATE_BANK_DEPOSITE_SLIP_DATA = "UPDATE AccountBankDepositeSlipMasterEntity  tm SET tm.depositeAuthFlag =:depositeAuthFlag,tm.depositeAuthDate=:depositeAuthDate, tm.updatedBy=:updatedBy,tm.updatedDate=:updatedDate,tm.lgIpMacUpd=:lgIpMacUpd WHERE tm.depositeSlipId =:depositeSlipId and tm.orgid =:orgId";
        }

        interface ContraVoucherEntryMaster {

            String QUERY_TO_GET_CONTRA_ENTRY_DETAIL_ONE = "select a.depositeSlipId, a.depositeSlipNumber, a.depositeSlipDate, d.cpdFeemode, a.depositeAmount, a.coTypeFlag from AccountBankDepositeSlipMasterEntity a, "
                    + "TbServiceReceiptMasEntity  b, TbSrcptFeesDetEntity c, TbSrcptModesDetEntity d "
                    + "where b.rmRcptid = c.rmRcptid.rmRcptid and b.rmRcptid = d.rmRcptid.rmRcptid "
                    + "and a.depositeSlipId = c.depositeSlipId and a.depositTypeFlag = 'C' and a.coTypeFlag in ('T', 'D') "
                    + "and a.orgid=c.orgId  and b.orgId=c.orgId   and b.orgId=d.orgId and a.orgid =:orgId ";

            String QUERY_TO_GET_CONTRA_ENTRY_DETAIL_TWO = "select a1.depositeSlipId, a1.depositeSlipNumber, a1.depositeSlipDate, b1.paymentModeId.cpdId,  a1.depositeAmount, a1.coTypeFlag "
                    + "from AccountBankDepositeSlipMasterEntity a1, AccountPaymentMasterEntity b1 where a1.depositeSlipId = b1.depositeSlipId.depositeSlipId "
                    + "and a1.depositTypeFlag = 'C' and a1.coTypeFlag in ('W') and a1.orgid=b1.orgId and a1.orgid =:orgId ";

            String QUERY_TO_GET_CONTRA_ENTRY_DATA_ONE = "select a.depositeSlipId, a.depositeSlipNumber, a.depositeSlipDate, d.cpdFeemode, a.depositeAmount from AccountBankDepositeSlipMasterEntity a, "
                    + "TbServiceReceiptMasEntity  b, TbSrcptFeesDetEntity c, TbSrcptModesDetEntity d "
                    + "where b.rmRcptid = c.rmRcptid.rmRcptid and b.rmRcptid = d.rmRcptid.rmRcptid "
                    + "and a.depositeSlipId = c.depositeSlipId and a.depositTypeFlag = 'C' and a.coTypeFlag in ('T', 'D') "
                    + "and a.orgid=c.orgId  and b.orgId=c.orgId   and b.orgId=d.orgId and a.orgid =:orgId ";

            String QUERY_TO_GET_CONTRA_ENTRY_DATA_TWO = "select a1.depositeSlipId, a1.depositeSlipNumber, a1.depositeSlipDate, b1.paymentModeId.cpdId,  a1.depositeAmount  "
                    + "from AccountBankDepositeSlipMasterEntity a1, AccountPaymentMasterEntity b1 where a1.depositeSlipId = b1.depositeSlipId.depositeSlipId "
                    + "and a1.depositTypeFlag = 'C' and a1.coTypeFlag in ('W') and a1.orgid=b1.orgId and a1.orgid =:orgId ";

            String QUERY_TO_GET_CONTRA_ENTRY_DATA_BY_ID = "select a.depositeSlipNumber,a.depositeSlipDate,d.cpdFeemode,a.depositeAmount,d.baAccountid,b.rmReceivedfrom, a.coTypeFlag "
                    + "from AccountBankDepositeSlipMasterEntity a, TbServiceReceiptMasEntity  b, TbSrcptFeesDetEntity c, TbSrcptModesDetEntity d "
                    + "where b.rmRcptid = c.rmRcptid.rmRcptid and b.rmRcptid = d.rmRcptid.rmRcptid  "
                    + "and a.depositeSlipId = c.depositeSlipId and a.depositeSlipId =:transactionId "
                    + "and a.orgid=c.orgId  and b.orgId=c.orgId and b.orgId=d.orgId and a.orgid =:orgId ";

            String QUERY_TO_GET_CONTRA_ENTRY_TRANSFER_QUERY_TWO = "select b1.narration, b1.baBankAccountId.baAccountId, b1.instrumentNumber, b1.instrumentDate "
                    + "from AccountBankDepositeSlipMasterEntity a1, AccountPaymentMasterEntity b1 where a1.depositeSlipId = b1.depositeSlipId.depositeSlipId "
                    + "and a1.orgid=b1.orgId and a1.orgid =:orgId and a1.depositeSlipId =:transactionId";

            String QUERY_TO_GET_CONTRA_ENTRY_WITHDRAW_QUERY_ONE = "select a.depositeSlipNumber,a.depositeSlipDate,a.depositeAmount,a.coTypeFlag "
                    + "from AccountBankDepositeSlipMasterEntity a where a.depositeSlipId =:transactionId and a.orgid =:orgId ";

            String QUERY_TO_GET_CONTRA_ENTRY_WITHDRAW_QUERY_TWO = "select b1.narration, b1.baBankAccountId.baAccountId, b1.instrumentNumber, b1.instrumentDate, b1.paymentModeId.cpdId "
                    + "from AccountBankDepositeSlipMasterEntity a1, AccountPaymentMasterEntity b1 where a1.depositeSlipId = b1.depositeSlipId.depositeSlipId "
                    + "and a1.orgid=b1.orgId and a1.orgid =:orgId and a1.depositeSlipId =:transactionId";

            String QUERY_TO_GET_CONTRA_ENTRY_DEPOSITE_QUERY_ONE = "select a.depositeSlipNumber,a.depositeSlipDate,a.depositeAmount, a.depositeRemark, a.depositeBAAccountId, a.coTypeFlag "
                    + "from AccountBankDepositeSlipMasterEntity a where a.depositeSlipId =:transactionId and a.orgid =:orgId ";

            String QUERY_TO_GET_CONTRA_ENTRY_DEPOSITE_QUERY_TWO = "select d.cpdDenomId, d.denominationCount from AccountBankDepositeSlipDenomEntity d where d.depositeSlipId.depositeSlipId =:transactionId and d.orgid =:orgId";
        }

        interface PaymentEntryMaster {
            String QUERY_TO_GET_PAYMENT_DETAIL = "select distinct m from AccountPaymentMasterEntity m, AccountPaymentDetEntity d where m.paymentDeletionFlag IS NULL and m.orgId=:orgId and m.paymentTypeFlag =:paymentTypeFlag";

            String QUERY_TO_REVERSE_PAYMENT_ENTRY = "UPDATE AccountPaymentMasterEntity bm SET "
                    + "bm.paymentDeletionFlag=:paymentDeletionFlag, bm.paymentDeletionOrderNo=:paymentDeletionOrderNo , bm.deletionAuthorizedBy=:deletionAuthorizedBy"
                    + ",bm.paymentDeletionDate=:paymentDeletionDate, bm.deletionRemark=:deletionRemark, bm.updatedBy=:updatedBy, bm.updatedDate=:updatedDate"
                    + ", bm.lgIpMacUpd=:lgIpMacUpd, bm.deletionPostingDate=:deletionPostingDate WHERE bm.paymentId=:paymentId AND bm.orgId=:orgId";
        }

        interface TbAcVendormaster {
            String QUERY_TO_GET_VENDOR_DATA = "select te from TbAcVendormasterEntity te where te.orgid =:orgid";

        }

        interface TransactionTrackingMaster {
            String QUERY_TO_GET_COUNT_PRIMARY_HEAD = "select count(*) from AccountVoucherEntryDetailsEntity vd, AccountBudgetCodeEntity bm, AccountHeadSecondaryAccountCodeMasterEntity sm,"
                    + " AccountHeadPrimaryAccountCodeMasterEntity pm where vd.budgetCode.prBudgetCodeid =:budgetCodeId and bm.prBudgetCodeid = vd.budgetCode.prBudgetCodeid"
                    + " and bm.tbAcSecondaryheadMaster.sacHeadId = sm.sacHeadId and sm.tbAcPrimaryheadMaster.primaryAcHeadId = pm.primaryAcHeadId"
                    + " and pm.primaryAcHeadCompcode like(:compCode) and vd.orgId=bm.orgid and bm.orgid=sm.orgid and sm.orgid=pm.orgid and vd.orgId =:orgId";

            String QUERY_TO_GET_OPENING_BALANCE = "select sum(ob.openbalAmt), ob.tbComparamDet.cpdId from AccountBudgetCodeEntity bc, AccountBudgetOpenBalanceEntity ob where "
                    + "bc.tbAcSecondaryheadMaster.sacHeadId = ob.tbAcSecondaryheadMaster.sacHeadId and bc.orgid = ob.orgid and bc.prBudgetCodeid =:budgetCodeId";

            String QUERY_TO_GET_MONTHWISE_TRANSACTION_DETAIL = "select sum(ob.openbalAmt), ob.tbComparamDet.cpdId from AccountBudgetCodeEntity bc, AccountBudgetOpenBalanceEntity ob where "
                    + "bc.tbAcSecondaryheadMaster.sacHeadId = ob.tbAcSecondaryheadMaster.sacHeadId and bc.orgid = ob.orgid and bc.prBudgetCodeid =:budgetCodeId and ob.faYearid =:finYearId";

            String QUERY_TO_GET_ACCOUNT_HEAD_INFO = "SELECT pm.primaryAcHeadCompcode, sm.sacHeadCode, sm.sacHeadDesc FROM AccountBudgetCodeEntity bm,"
                    + "AccountHeadSecondaryAccountCodeMasterEntity sm, AccountHeadPrimaryAccountCodeMasterEntity pm WHERE bm.tbAcSecondaryheadMaster.sacHeadId = sm.sacHeadId "
                    + "and sm.tbAcPrimaryheadMaster.primaryAcHeadId = pm.primaryAcHeadId and sm.orgid = bm.orgid and sm.orgid = pm.orgid and bm.orgid =:orgId "
                    + "and bm.prBudgetCodeid =:budgetCodeId";

            String QUERY_TO_GET_CALCULATED_DEBIT_SUM = "select sum(vd.voudetAmt) from AccountVoucherEntryDetailsEntity vd, AccountBudgetCodeEntity bm"
                    + " where vd.budgetCode.prBudgetCodeid =:budgetCodeId and bm.prBudgetCodeid = vd.budgetCode.prBudgetCodeid"
                    + " and vd.drcrCpdId =:drId and vd.orgId=bm.orgid and vd.orgId=:orgId";

            String QUERY_TO_GET_CALCULATED_CREDIT_SUM = "select sum(vd.voudetAmt) from AccountVoucherEntryDetailsEntity vd, AccountBudgetCodeEntity bm"
                    + " where vd.budgetCode.prBudgetCodeid =:budgetCodeId and bm.prBudgetCodeid = vd.budgetCode.prBudgetCodeid"
                    + " and vd.drcrCpdId =:crId and vd.orgId=bm.orgid and vd.orgId=:orgId";

            String QUERY_TO_GET_MONTHWISE_DETAIL_OPEN_BALANCE_ONE = "select sum(case when B.drcrCpdId=:drId THEN B.voudetAmt else 0 end), SUM(case when B.drcrCpdId=:crId THEN B.voudetAmt else 0 end),"
                    + " (SUM(case when B.drcrCpdId=:crId THEN B.voudetAmt else 0 end)-SUM(case when B.drcrCpdId=:drId THEN B.voudetAmt else 0 end)),"
                    + " month(A.vouPostingDate) from AccountVoucherEntryEntity A , AccountVoucherEntryDetailsEntity B"
                    + " WHERE A.vouId=B.master.vouId AND B.budgetCode.prBudgetCodeid=:budgetCodeId AND A.org=:orgId AND A.org=B.orgId AND A.vouPostingDate IS NOT NULL"
                    + " AND A.vouPostingDate between :fromDate AND :toDate GROUP BY B.budgetCode.prBudgetCodeid,B.orgId,month(A.vouPostingDate)";

            String QUERY_TO_GET_MONTHWISE_DETAIL_OPEN_BALANCE_TWO = "select sum(case when B.drcrCpdId=:drId THEN B.voudetAmt else 0 end), SUM(case when B.drcrCpdId=:crId THEN B.voudetAmt else 0 end),"
                    + " (SUM(case when B.drcrCpdId=:drId THEN B.voudetAmt else 0 end)-SUM(case when B.drcrCpdId=:crId THEN B.voudetAmt else 0 end)),"
                    + " month(A.vouPostingDate) from AccountVoucherEntryEntity A , AccountVoucherEntryDetailsEntity B"
                    + " WHERE A.vouId=B.master.vouId AND B.budgetCode.prBudgetCodeid=:budgetCodeId AND A.org=:orgId AND A.org=B.orgId AND A.vouPostingDate IS NOT NULL"
                    + " AND A.vouPostingDate between :fromDate AND :toDate GROUP BY B.budgetCode.prBudgetCodeid,B.orgId,month(A.vouPostingDate)";

        }
    }

}
