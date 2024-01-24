     
    select d.budget_code,d.avg,d.LastYear,d.REVISED_ESTAMT,b.CurrYear,d.ESTIMATE_FOR_NEXTYEAR,d.CREATED_BY from   
  (     select c.* , a.avg from
       ( SELECT b.BUDGETCODE_ID,2000000002 as id_fin_year,
    budget_code,
    b.ORGINAL_ESTAMT as LastYear,
    b.REVISED_ESTAMT,
    d.ESTIMATE_FOR_NEXTYEAR,
    a.CREATED_BY
FROM
    tb_ac_budgetcode_mas a,
    tb_ac_projectedrevenue b,
    tb_financialyear c,
    tb_ac_budgetory_estimate d

    WHERE
     b.FA_YEARID=c.FA_YEARID
     AND d.BUDGETCODE_ID=b.BUDGETCODE_ID
        AND a.BUDGETCODE_ID = b.BUDGETCODE_ID
        and  a.BUDGETCODE_ID in (select distinct BUDGETCODE_ID as BUDGETCODE_ID from tb_ac_projectedrevenue)
        AND b.FA_YEARID = 2000000001) c
    
    left join
    
     (SELECT BUDGETCODE_ID,2000000002 as id_fin_year        ,(SUM(a.ORGINAL_ESTAMT) / COUNT(*)) as avg  FROM tb_ac_projectedrevenue a, tb_financialyear b 
 WHERE  BUDGETCODE_ID in (select distinct BUDGETCODE_ID as BUDGETCODE_ID from tb_ac_projectedrevenue)
  AND a.FA_YEARID = b.FA_YEARID AND b.fa_fromdate BETWEEN '2014-04-01' AND '2017-03-31' 
  group by BUDGETCODE_ID) a
  on       a.id_fin_year=c.id_fin_year
     AND a.BUDGETCODE_ID=c.BUDGETCODE_ID) d
     
     left join
     (SELECT b.BUDGETCODE_ID,2000000002 as id_fin_year,
    b.ORGINAL_ESTAMT as CurrYear  
FROM
    tb_ac_budgetcode_mas a,
    tb_ac_projectedrevenue b,
    tb_financialyear c,
    tb_ac_budgetory_estimate d
WHERE
     b.FA_YEARID=c.FA_YEARID
     AND d.BUDGETCODE_ID=b.BUDGETCODE_ID
        AND a.BUDGETCODE_ID = b.BUDGETCODE_ID
        and  a.BUDGETCODE_ID in (select distinct BUDGETCODE_ID as BUDGETCODE_ID from tb_ac_projectedrevenue)
        AND b.FA_YEARID = 2000000002)b
         on d.id_fin_year = b.id_fin_year
         and d.BUDGETCODE_ID=b.BUDGETCODE_ID
    
