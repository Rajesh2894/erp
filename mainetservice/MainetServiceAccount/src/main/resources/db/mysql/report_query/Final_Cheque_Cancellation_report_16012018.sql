SELECT 
    cd.CHEQUE_NO,
    cd.ISSUANCE_DATE,
    pm.PAYMENT_AMOUNT,
    cd.CANCELLATION_DATE,
    cd.CANCELLATION_REASON,
    cf.CHEQUE_NO NEW_CHEQUE_NO,
    pm.PAYMENT_NO,
    pm.NARRATION,
    ba.BA_ACCOUNTNAME,
    ba.BA_ACCOUNT_NO
FROM
    tb_ac_chequebookleaf_det cd,
    tb_ac_payment_mas pm,
    tb_bank_account ba,
    (SELECT 
        cd.CHEQUE_NO, cd.CHEQUEBOOK_DETID, cd.orgid
    FROM
        tb_ac_chequebookleaf_det cd) cf
WHERE
    pm.PAYMENT_ID = cd.PAYMENT_ID
        AND cf.CHEQUEBOOK_DETID = cd.NEW_ISSUE_CHEQUEBOOK_DETID
        AND pm.BA_ACCOUNTID = ba.BA_ACCOUNTID
        AND cf.ORGID = cd.orgid
        AND pm.ORGID = cd.orgid
        AND cd.CPD_IDSTATUS = (SELECT 
            a.CPD_ID
        FROM
            tb_comparam_det a,
            tb_comparam_mas b
        WHERE
            b.cpm_id = a.cpm_id
                AND b.cpm_prefix = 'CLR'
                AND a.CPD_VALUE = 'CND')
        AND pm.orgid = 1515
        AND cd.CANCELLATION_DATE BETWEEN '2017-04-01' AND '2018-03-31';