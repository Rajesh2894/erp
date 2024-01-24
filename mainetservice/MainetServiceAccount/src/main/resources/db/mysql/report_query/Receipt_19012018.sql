SELECT 
    scm.SAC_HEAD_ID,
    scm.AC_HEAD_CODE,
    SUM(pr.ORGINAL_ESTAMT) ORGINAL_ESTAMT,
    md.RF_FEEAMOUNT,
    SUM(pr.REVISED_ESTAMT)
FROM
    tb_ac_budgetcode_mas bm,
    tb_ac_projectedrevenue pr,
    tb_ac_primaryhead_master pm,
    tb_financialyear fn,
    Tb_Ac_Secondaryhead_Master scm
        LEFT OUTER JOIN
    (SELECT 
        sm.SAC_HEAD_ID, rms.orgid, SUM(rd.RF_FEEAMOUNT) RF_FEEAMOUNT
    FROM
        tb_receipt_mas rms, tb_receipt_det rd, tb_receipt_mode rm, tb_ac_voucher vc, Tb_Ac_Secondaryhead_Master sm
    WHERE
        rms.RM_RCPTID = rd.RM_RCPTID
            AND rms.RM_RCPTID = rm.RM_RCPTID
            AND rm.RM_RCPTID = rd.RM_RCPTID
            AND rd.SAC_HEAD_ID = sm.SAC_HEAD_ID
            AND vc.VOU_REFERENCE_NO = rms.RM_RCPTNO
            AND rms.orgid = rd.orgid
            AND rms.ORGID = vc.ORGID
            AND vc.VOU_DATE = rms.RM_DATE
            AND rm.CPD_FEEMODE IN (SELECT 
                a.Cpd_id
            FROM
                tb_comparam_det a, tb_comparam_mas b
            WHERE
                b.CPM_PREFIX = 'PAY'
                    AND a.CPD_VALUE IN ('C' , 'Q', 'D', 'B', 'RT', 'W')
                    AND a.cpm_id = b.cpm_id)
            AND vc.AUTHO_FLG = 'Y'
            AND rms.RECEIPT_DEL_FLAG IS NULL
            AND rms.ORGID = 1515
            AND rms.RM_DATE BETWEEN '2017-04-01' AND '2018-03-31'
    GROUP BY sm.SAC_HEAD_ID , rms.orgid) md ON scm.sac_head_id = md.sac_head_id
WHERE
    bm.BUDGETCODE_ID = pr.BUDGETCODE_ID
        AND bm.PAC_HEAD_ID = pm.PAC_HEAD_ID
        AND scm.PAC_HEAD_ID = pm.PAC_HEAD_ID
        AND pr.FA_YEARID = fn.FA_YEARID
        AND scm.orgid = 1515
        AND fn.FA_YEARID = 2000000002
GROUP BY scm.SAC_HEAD_ID , scm.AC_HEAD_CODE , md.RF_FEEAMOUNT
