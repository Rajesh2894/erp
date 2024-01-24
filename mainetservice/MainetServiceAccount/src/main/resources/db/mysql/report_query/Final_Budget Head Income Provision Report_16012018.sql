SELECT 
    bm.budget_code, pr.ORGINAL_ESTAMT, md.Collected_Balance
FROM
    tb_ac_budgetcode_mas bm,
    tb_financialyear fy,
    tb_ac_projectedrevenue pr
        LEFT OUTER JOIN
    (SELECT 
        bm.budgetcode_id, bm.budget_code, Collected_Balance
    FROM
        tb_ac_budgetcode_mas bm, tb_ac_primaryhead_master pm, Tb_Ac_Function_Master fm, Tb_Ac_Secondaryhead_Master sm, (SELECT 
        rd.SAC_HEAD_ID,
            rm.orgid,
            SUM(rm.RM_AMOUNT) Collected_Balance
    FROM
        tb_receipt_mas rm, tb_receipt_det rd
    WHERE
        rm.RM_RCPTID = rd.RM_RCPTID
            AND rm.orgid = rd.orgid
            AND rm.ORGID = 1515
            AND rm.RM_DATE BETWEEN '2017-04-01' AND '2018-03-31'
            AND rm.RECEIPT_DEL_FLAG IS NULL
    GROUP BY rd.SAC_HEAD_ID , rm.orgid) md
    WHERE
        bm.function_id = fm.function_id
            AND bm.pac_head_id = pm.pac_head_id
            AND pm.pac_head_id = sm.pac_head_id
            AND fm.function_id = sm.function_id
            AND sm.sac_head_id = md.sac_head_id) md ON pr.BUDGETCODE_ID = md.BUDGETCODE_ID
WHERE
    bm.BUDGETCODE_ID = pr.BUDGETCODE_ID
        AND pr.FA_YEARID = fy.FA_YEARID
        AND pr.FA_YEARID = 2000000002
        AND pr.orgid = 1515;
