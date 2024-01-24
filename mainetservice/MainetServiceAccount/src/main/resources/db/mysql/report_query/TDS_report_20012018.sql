SELECT 
    vm.VM_VENDORID,
    vm.vm_vendorname,
    bd.SAC_HEAD_ID,
    SUM(bd.DEDUCTION_AMT) DEDUCTION_AMT
    FROM
    tb_vendormaster vm,
    tb_ac_bill_mas bm,
    tb_ac_bill_deduction_detail bd,
    tb_ac_payment_mas pm,
    tb_ac_payment_det pd,
    tb_ac_bank_tds_details td
WHERE
    vm.VM_VENDORID = bm.VM_VENDORID
        AND bm.BM_ID = bd.BM_ID
        AND pm.PAYMENT_ID = pd.PAYMENT_ID
        and bd.SAC_HEAD_ID=td.sac_head_id
        AND bd.bm_id = pd.bm_id
        AND pm.ORGID = pd.orgid
        AND vm.orgid = bd.orgid
        AND bd.orgid = bm.ORGID
        AND vm.ORGID = bm.orgid
        AND vm.orgid = 1515
        and pm.PAYMENT_DATE BETWEEN '2017-04-01' AND '2018-01-17'
		AND PM.PAYMENT_DEL_FLAG IS NULL
        GROUP BY vm.VM_VENDORID , vm.vm_vendorname , bd.SAC_HEAD_ID;
        
        