--liquibase formatted sql
--changeset Kanchan:V20220905192119__CR_vw_voucher_detail_05092022.sql
CREATE
    ALGORITHM = UNDEFINED
    SQL SECURITY DEFINER
VIEW `vw_voucher_detail` AS
    SELECT
        `ac`.`VOU_ID` AS `VOU_ID`,
        `vd`.`VOUDET_ID` AS `VOUDET_ID`,
        `ac`.`VOU_NO` AS `VOU_NO`,
        `ac`.`VOU_DATE` AS `VOU_DATE`,
        `ac`.`VOU_POSTING_DATE` AS `VOU_POSTING_DATE`,
        `ac`.`ORGID` AS `ORGID`,
        `ac`.`VOU_TYPE_CPD_ID` AS `VOU_TYPE_CPD_ID`,
        `ac`.`VOU_SUBTYPE_CPD_ID` AS `VOU_SUBTYPE_CPD_ID`,
        `vd`.`SAC_HEAD_ID` AS `SAC_HEAD_ID`,
        `b`.`AC_HEAD_CODE` AS `AC_HEAD_CODE`,
        `b`.`BA_ACCOUNTID` AS `BA_ACCOUNTID`,
        (CASE
            WHEN
                (`vd`.`DRCR_CPD_ID` = (SELECT
                        `cd`.`CPD_ID`
                    FROM
                        (`tb_comparam_det` `cd`
                        JOIN `tb_comparam_mas` `cm`)
                    WHERE
                        ((`cm`.`CPM_ID` = `cd`.`CPM_ID`)
                            AND (`cd`.`CPD_VALUE` = 'CR')
                            AND (`cm`.`CPM_PREFIX` = 'DCR'))))
            THEN
                COALESCE(`vd`.`VOUDET_AMT`, 0)
            ELSE 0
        END) AS `VAMT_CR`,
        (CASE
            WHEN
                (`vd`.`DRCR_CPD_ID` = (SELECT
                        `cd`.`CPD_ID`
                    FROM
                        (`tb_comparam_det` `cd`
                        JOIN `tb_comparam_mas` `cm`)
                    WHERE
                        ((`cm`.`CPM_ID` = `cd`.`CPM_ID`)
                            AND (`cd`.`CPD_VALUE` = 'DR')
                            AND (`cm`.`CPM_PREFIX` = 'DCR'))))
            THEN
                COALESCE(`vd`.`VOUDET_AMT`, 0)
            ELSE 0
        END) AS `VAMT_DR`,
        `vd`.`VOUDET_AMT` AS `VOUCHER_AMOUNT`,
        (SELECT `tb_comparam_det`.`CPD_VALUE`
            FROM `tb_comparam_det`
            WHERE
                (`tb_comparam_det`.`CPD_ID` = `vd`.`DRCR_CPD_ID`)) AS `DRCR`,
        `ac`.`VOU_REFERENCE_NO` AS `REFERENCE_NO`,
        `ac`.`PAYER_PAYEE` AS `PAYER_PAYEE`,
        `ac`.`NARRATION` AS `PARTICULARS`,
        `b`.`PAC_HEAD_ID` AS `PAC_HEAD_ID`,
        `b`.`FUNCTION_ID` AS `FUNCTION_ID`,
        `ac`.`FIELD_ID` AS `FIELD_ID`
    FROM
        (((`tb_ac_voucher` `ac`
        JOIN `tb_ac_voucher_det` `vd`)
        JOIN `tb_comparam_det` `cd`)
        JOIN `tb_ac_secondaryhead_master` `b`)
 
    WHERE
        ((`ac`.`ORGID` = `vd`.`ORGID`)
            AND (`ac`.`VOU_ID` = `vd`.`VOU_ID`)
            AND (`ac`.`VOU_TYPE_CPD_ID` = `cd`.`CPD_ID`)
            AND (`cd`.`CPD_STATUS` = 'A')
            AND (`vd`.`ORGID` = `b`.`ORGID`)
            AND (`vd`.`SAC_HEAD_ID` = `b`.`SAC_HEAD_ID`)
            AND (`ac`.`AUTHO_FLG` = 'Y'))
