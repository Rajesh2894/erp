SELECT 
    rm.RM_RCPTID,
    rm.ORGID,
    rm.RM_RCPTNO Receipt_No,
    rm.RM_DATE Receipt_Date,
    rmo.RD_CHEQUEDDNO Cheque_No,
    rmo.RD_CHEQUEDDDATE,
    rmo.RD_AMOUNT,
    bm.bank,
    (SELECT 
            CPD_DESC
        FROM
            tb_comparam_det a,
            tb_comparam_mas b
        WHERE
            b.cpm_id = a.cpm_id
                AND cpm_prefix = 'CLR'
                AND CPD_OTHERS = rmo.RD_SR_CHK_DIS) RD_SR_CHK_DIS
FROM
    tb_receipt_mas rm,
    tb_receipt_mode rmo,
    tb_bank_master bm
WHERE
    rm.RM_RCPTID = rmo.RM_RCPTID
        AND rmo.BANKID = bm.BANKID
        AND rm.orgid = rmo.orgid
        AND rm.ORGID = 1515
        AND rm.RM_DATE BETWEEN '2017-12-01' AND '2017-12-31'
