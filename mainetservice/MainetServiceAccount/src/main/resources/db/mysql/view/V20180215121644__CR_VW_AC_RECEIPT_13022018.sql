--liquibase formatted sql
--changeset priya:V20180215121644__CR_VW_AC_RECEIPT_13022018.sql
CREATE OR REPLACE VIEW VW_AC_PAYMENT AS
    SELECT 
        RMS.PAYMENT_ID,
        RMS.PAYMENT_DATE,
        RD.PAYMENT_AMT,
        RMS.ORGID,
        SM.SAC_HEAD_ID,
		SM.AC_HEAD_CODE,
        PM.PAC_HEAD_ID,
        PM.PAC_HEAD_COMPO_CODE,
        PM.PAC_HEAD_DESC,
        PM.PAC_HEAD_PARENT_ID,
        FM.FUNCTION_ID,
        FM.FUNCTION_DESC,
        FM.FUNCTION_COMPCODE,
        FM.FUNCTION_PARENT_ID,
        BM.BUDGETCODE_ID,
        BM.BUDGET_CODE
    FROM
        TB_AC_PAYMENT_MAS RMS,
        TB_AC_PAYMENT_DET RD,
        TB_AC_SECONDARYHEAD_MASTER SM,
        TB_AC_FUNCTION_MASTER FM,
        TB_AC_BUDGETCODE_MAS BM,
        TB_AC_PRIMARYHEAD_MASTER PM
    WHERE
        RMS.PAYMENT_ID = RD.PAYMENT_ID
            AND RD.BUDGETCODE_ID = SM.SAC_HEAD_ID
            AND PM.PAC_HEAD_ID = SM.PAC_HEAD_ID
            AND FM.FUNCTION_ID = SM.FUNCTION_ID
            AND BM.FUNCTION_ID = FM.FUNCTION_ID
            AND BM.PAC_HEAD_ID = PM.PAC_HEAD_ID
            AND RMS.ORGID = RD.ORGID
            AND RMS.PAYMENT_DEL_FLAG IS NULL
            AND PM.CODCOFDET_ID = (SELECT 
                T.CODCOFDET_ID
            FROM
                TB_COMPARAM_MAS M,
                TB_COMPARAM_DET D,
                TB_AC_CODINGSTRUCTURE_MAS S,
                TB_AC_CODINGSTRUCTURE_DET T
            WHERE
                M.CPM_ID = D.CPM_ID
                    AND M.CPM_PREFIX = 'CMD'
                    AND D.CPD_VALUE = 'AHP'
                    AND D.CPD_ID = S.COM_CPD_ID
                    AND D.ORGID = S.ORGID
                    AND S.CODCOF_ID = T.CODCOF_ID
                    AND T.COD_LEVEL = (SELECT 
                        MAX(T.COD_LEVEL)
                    FROM
                        TB_AC_CODINGSTRUCTURE_DET T
                    WHERE
                        T.ORGID = S.ORGID)
                    AND D.ORGID = (SELECT 
                        TB_ORGANISATION.ORGID
                    FROM
                        TB_ORGANISATION
                    WHERE
                        TB_ORGANISATION.DEFAULT_STATUS = 'Y'));

                      
                        