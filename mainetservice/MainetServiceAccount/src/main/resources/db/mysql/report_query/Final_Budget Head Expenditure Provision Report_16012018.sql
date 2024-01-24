SELECT 
    bm.budget_code, pe.ORGINAL_ESTAMT, md.Expenses_Balance
FROM
    tb_ac_budgetcode_mas bm,
    tb_financialyear fy,
    tb_ac_projected_expenditure pe
        LEFT OUTER JOIN
    (SELECT 
        bm.budgetcode_id, bm.budget_code, md.Expenses_Balance
    FROM
        tb_ac_budgetcode_mas bm, tb_ac_primaryhead_master pm, Tb_Ac_Function_Master fm, Tb_Ac_Secondaryhead_Master sm, (SELECT 
        pd.BUDGETCODE_ID,
            pm.orgid,
            SUM(pm.PAYMENT_AMOUNT) Expenses_Balance
    FROM
        tb_ac_payment_mas pm, tb_ac_payment_det pd
    WHERE
        pm.PAYMENT_ID = pd.PAYMENT_ID
            AND pm.orgid = pd.orgid
            AND pm.ORGID = 1515
            AND pm.PAYMENT_DEL_FLAG IS NULL
            AND pm.PAYMENT_DATE BETWEEN '2017-04-01' AND '2018-03-31'
    GROUP BY pd.BUDGETCODE_ID , pm.orgid) md
    WHERE
        bm.function_id = fm.function_id
            AND bm.pac_head_id = pm.pac_head_id
            AND pm.pac_head_id = sm.pac_head_id
            AND fm.function_id = sm.function_id
            AND sm.sac_head_id = md.BUDGETCODE_ID) md ON pe.BUDGETCODE_ID = md.BUDGETCODE_ID
WHERE
    bm.BUDGETCODE_ID = pe.BUDGETCODE_ID
        AND pe.FA_YEARID = fy.FA_YEARID
        AND pe.orgid = 1515
        AND pe.FA_YEARID = 2000000002;