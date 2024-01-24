SELECT 
    a.BM_ENTRYDATE,
    a.VM_VENDORNAME,
    a.BM_BILLNO,
    a.BM_W_P_ORDER_NUMBER,
    a.BM_INVOICEVALUE,
    a.BM_NARRATION,
    a.CHECKER_DATE,
    a.ACT_AMT,
    a.DISALLOWED_AMT,
    a.DISALLOWED_REMARK,
    b.PAYMENT_DATE,
    b.PAYMENT_NO,
    b.PAYMENT_AMT
FROM
    (SELECT 
        a.BM_ID,
            a.BM_ENTRYDATE,
            a.VM_VENDORNAME,
            a.BM_BILLNO,
            a.BM_W_P_ORDER_NUMBER,
            a.BM_INVOICEVALUE,
            a.BM_NARRATION,
            a.CHECKER_DATE,
            SUM(D.ACT_AMT) ACT_AMT,
            SUM(d.DISALLOWED_AMT) DISALLOWED_AMT,
            d.DISALLOWED_REMARK
    FROM
        tb_ac_bill_mas a, tb_ac_bill_exp_detail d, tb_vendormaster V
    WHERE
        a.VM_VENDORID = V.VM_VENDORID
            AND a.ORGID = d.ORGID
            AND a.orgid = V.ORGID
            AND a.BM_ID = d.BM_ID
            AND a.CHECKER_AUTHO = 'Y'
            AND a.BM_DEL_FLAG IS NULL
            AND a.BM_ENTRYDATE BETWEEN '2017-04-01' AND '2018-01-17'
            AND a.ORGID = 1515
    GROUP BY a.VM_VENDORNAME , a.BM_INVOICENUMBER , a.BM_BILLNO , a.BM_NARRATION , a.BM_BAL_AMT , d.DISALLOWED_REMARK , a.BM_ENTRYDATE , a.BM_W_P_ORDER_NUMBER , a.BM_ID) a
        LEFT OUTER JOIN
    (SELECT 
        pm.PAYMENT_DATE,
            PM.PAYMENT_NO,
            SUM(pd.PAYMENT_AMT) PAYMENT_AMT,
            bm.BM_ID
    FROM
        tb_ac_payment_mas PM, tb_ac_payment_det PD, tb_ac_bill_mas bm
    WHERE
        PM.ORGID = 1515
            AND PM.PAYMENT_ID = PD.PAYMENT_ID
            AND PM.ORGID = PD.ORGID
            AND PD.ORGID = bm.ORGID
            AND PD.BM_ID = bm.BM_ID
            AND PM.PAYMENT_DATE BETWEEN '2017-04-01' AND '2018-01-17'
            AND PM.PAYMENT_DEL_FLAG IS NULL
    GROUP BY bm.BM_ID , pm.PAYMENT_DATE , PM.PAYMENT_NO) b ON a.BM_ID = b.bm_id;
     
