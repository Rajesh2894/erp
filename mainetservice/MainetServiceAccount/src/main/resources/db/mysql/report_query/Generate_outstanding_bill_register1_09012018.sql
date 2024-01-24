SELECT 
    vm.VM_VENDORNAME Name_of_the_supplier,
    bm.BM_NARRATION Nature_of_payable,
    sm.AC_HEAD_CODE Code_of_account,
    bm.BM_ENTRYDATE Date_of_Bill,
    bm.BM_TOT_AMT Bill_Amount_Rs,
    (BM_TOT_AMT - pt.sum_payment_amount) Outstanding_liablity_amount
FROM
    tb_vendormaster vm,
    tb_ac_bill_mas bm,
    tb_ac_bill_exp_detail bd,
    tb_ac_secondaryhead_master sm,
    (SELECT 
        pd.BM_ID,
            pd.ORGID,
            SUM(pm.payment_amount) sum_payment_amount
    FROM
        tb_ac_payment_mas pm, tb_ac_payment_det pd
    WHERE
        pm.payment_id = pd.payment_id
            AND pm.ORGID = 1515
            AND pm.payment_date = '2017-12-30'
    GROUP BY pd.BM_ID , pd.ORGID) pt
WHERE
    vm.VM_VENDORID = bm.VM_VENDORID
        AND sm.sac_head_id = bd.sac_head_id
        AND bm.CHECKER_AUTHO='Y'
        AND bm.bm_id = bd.BM_ID
        AND bm.bm_id = pt.BM_ID
        AND pt.ORGID = bd.ORGID
        AND vm.ORGID = bm.ORGID
        AND bm.ORGID = bd.ORGID
        AND bd.ORGID = sm.ORGID
        AND sm.ORGID = bd.ORGID
        AND bm.orgid = 1515;