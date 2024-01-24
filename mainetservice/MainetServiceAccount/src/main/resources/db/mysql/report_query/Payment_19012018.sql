SELECT 
    scm.SAC_HEAD_ID,
    scm.AC_HEAD_CODE,
    SUM(pe.ORGINAL_ESTAMT) ORGINAL_ESTAMT,
    md.PAYMENT_AMT,
    SUM(pe.REVISED_ESTAMT)
FROM
    tb_ac_budgetcode_mas bm,
    tb_ac_projected_expenditure pe,
    tb_ac_primaryhead_master pm,
    tb_financialyear fn,
    Tb_Ac_Secondaryhead_Master scm
        LEFT OUTER JOIN
    (SELECT 
        sm.SAC_HEAD_ID, pms.orgid, SUM(pd.PAYMENT_AMT) PAYMENT_AMT
    FROM
        tb_ac_payment_mas pms, tb_ac_payment_det pd, tb_ac_voucher vc, Tb_Ac_Secondaryhead_Master sm
    WHERE
        pms.PAYMENT_ID = pd.PAYMENT_ID
            AND pd.BUDGETCODE_ID = sm.SAC_HEAD_ID
            AND vc.VOU_REFERENCE_NO = pms.PAYMENT_NO
            AND pms.ORGID = vc.ORGID
            AND vc.VOU_DATE = pms.PAYMENT_DATE
            AND vc.AUTHO_FLG = 'Y'
            AND pms.orgid = pd.orgid
            AND pms.ORGID = 1515
            AND pms.PAYMENT_DEL_FLAG IS NULL
            AND pms.CPD_ID_PAYMENT_MODE IN (SELECT 
                a.Cpd_id
            FROM
                tb_comparam_det a, tb_comparam_mas b
            WHERE
                b.CPM_PREFIX = 'PAY'
                    AND a.CPD_VALUE IN ('C' , 'Q', 'D', 'B', 'RT', 'W')
                    AND a.cpm_id = b.cpm_id)
            AND vc.VOU_TYPE_CPD_ID IN (SELECT 
                a.Cpd_id
            FROM
                tb_comparam_det a, tb_comparam_mas b
            WHERE
                b.CPM_PREFIX = 'VOT'
                    AND a.CPD_VALUE = 'PV'
                    AND a.cpm_id = b.cpm_id)
            AND pms.PAYMENT_DATE BETWEEN '2017-04-01' AND '2018-03-31'
    GROUP BY sm.SAC_HEAD_ID , pms.orgid) md ON scm.sac_head_id = md.sac_head_id
WHERE
    bm.BUDGETCODE_ID = pe.BUDGETCODE_ID
        AND bm.PAC_HEAD_ID = pm.PAC_HEAD_ID
        AND scm.PAC_HEAD_ID = pm.PAC_HEAD_ID
        AND pe.FA_YEARID = fn.FA_YEARID
        AND scm.orgid = 1515
        AND fn.FA_YEARID = 2000000002
GROUP BY scm.SAC_HEAD_ID , scm.AC_HEAD_CODE , md.PAYMENT_AMT