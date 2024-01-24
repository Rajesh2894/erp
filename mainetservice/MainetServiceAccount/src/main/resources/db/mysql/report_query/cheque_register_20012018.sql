SELECT 
    pm.PAYMENT_NO,
    pm.PAYMENT_DATE,
    bm.BM_BILLNO,
    bm.BM_ENTRYDATE,
    vm.VM_VENDORNAME,
    pm.NARRATION,
    cd.CHEQUE_NO,
    pm.INSTRUMENT_DATE,
    pm.PAYMENT_AMOUNT,
    cd.ISSUANCE_DATE,
    pm.CHEQUE_CLEARANCE_DATE
FROM
    tb_ac_bill_mas bm,
    tb_ac_bill_exp_detail bd,
    tb_ac_payment_mas pm,
    tb_ac_payment_det pd,
    tb_vendormaster vm,
    tb_ac_chequebookleaf_det cd
WHERE
		bm.BM_ID = bd.BM_ID
        AND vm.VM_VENDORID = bm.VM_VENDORID
        AND BM.ORGID = 1515
        AND PAYMENT_DATE BETWEEN '2017-04-01' AND '2018-01-17'
        AND PM.PAYMENT_DEL_FLAG IS NULL
        AND cd.CANCELLATION_DATE IS NULL
        AND bd.BM_ID = pd.BM_ID
        AND pm.PAYMENT_ID = pd.PAYMENT_ID
        AND pm.PAYMENT_ID = cd.PAYMENT_ID
        AND PM.ORGID = PD.ORGID
        AND PD.ORGID = BM.ORGID
        AND PM.ORGID = 1515 
UNION aLL

SELECT 
    pm.PAYMENT_NO,
    pm.PAYMENT_DATE,
    bm.BM_BILLNO,
    bm.BM_ENTRYDATE,
    vm.VM_VENDORNAME,
    pm.NARRATION,
    cd.CHEQUE_NO,
    pm.INSTRUMENT_DATE,
    pm.PAYMENT_AMOUNT,
    cd.ISSUANCE_DATE,
    pm.CHEQUE_CLEARANCE_DATE
FROM
    tb_ac_bill_mas bm,
    tb_ac_bill_exp_detail bd,
    tb_ac_payment_mas pm,
    tb_ac_payment_det pd,
    tb_vendormaster vm,
    tb_ac_chequebookleaf_det cd
WHERE
		bm.BM_ID = bd.BM_ID
        AND vm.VM_VENDORID = bm.VM_VENDORID
        AND BM.ORGID = 1515
        AND pm.PAYMENT_DATE BETWEEN '2017-04-01' AND '2018-01-17'
        AND PM.PAYMENT_DEL_FLAG IS NULL
        AND cd.CANCELLATION_DATE IS NULL
        AND pm.PAYMENT_ID = pd.PAYMENT_ID
        AND pm.PAYMENT_ID = cd.PAYMENT_ID
        AND PM.ORGID = PD.ORGID
        AND PD.ORGID = BM.ORGID
         AND PD.PAYMENT_ID NOT IN (SELECT 
									P.PAYMENT_ID
								FROM
									tb_ac_bill_mas BM,
									tb_ac_payment_det P
								WHERE
									BM.BM_ID = P.BM_ID
									AND BM.ORGID=P.ORGID
                                   AND BM.ORGID=1515)
        AND PM.ORGID = 1515 