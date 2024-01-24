Select a.Receipt_Date,a.Receipt_No,a.SAC_HEAD_ID, a.RM_NARRATION,a.Cash_AMT,a.Bank_AMT,b.PAYMENT_DATE, b.PAYMENT_NO,b.SAC_HEAD_ID, b.NARRATION,b.Cash_AMT1,b.Bank_AMT1
from
(
SELECT  rm.RM_DATE AS Receipt_Date,rm.ORGID,rd.SAC_HEAD_ID,cd.CPD_ID,
     rm.RM_RCPTNO AS Receipt_No,
     rm.RM_NARRATION,
     (case when cd.CPD_VALUE in ('D','Q','B') then
     rmo.RD_AMOUNT
     else 0 end) AS Bank_AMT,
    (case when cd.CPD_VALUE in ('C') then
     rmo.RD_AMOUNT
     else 0 end) AS Cash_AMT
    FROM
    tb_receipt_mas rm,
    tb_comparam_det cd,
    tb_receipt_mode rmo,
    tb_receipt_det rd,
    tb_ac_secondaryhead_master s
    where rm.RM_RCPTID = rmo.RM_RCPTID
    and rm.RM_RCPTID= rd.RM_RCPTID
    and rmo.RM_RCPTID = rd.RM_RCPTID
    and rd.SAC_HEAD_ID= s.SAC_HEAD_ID
    and rmo.CPD_FEEMODE = cd.CPD_ID
    and rm.RECEIPT_DEL_FLAG is null
    and rm.ORGID=1515
    and rm.RM_DATE BETWEEN '2017-12-31' AND '2018-01-01' )a
    right join
    (Select pm.PAYMENT_DATE, pm.PAYMENT_NO,pm.NARRATION,pm.ORGID,s.SAC_HEAD_ID,pd.BUDGETCODE_ID,cd.CPD_ID,
 (case when cd.CPD_VALUE in ('D','Q','B') then
  pm.PAYMENT_AMOUNT
  else 0 end) AS Bank_AMT1,
 (case when cd.CPD_VALUE in ('C') then
  pm.PAYMENT_AMOUNT
  else 0 end) AS Cash_AMT1
  from tb_ac_payment_mas pm,
       tb_ac_payment_det pd,
      tb_comparam_det cd,
       tb_ac_secondaryhead_master s
       where pm.CPD_ID_PAYMENT_MODE= cd.CPD_ID
       and pd.BUDGETCODE_ID=s.SAC_HEAD_ID
       and cd.CPD_VALUE in ('D','Q','B','C')
       and pm.PAYMENT_DEL_FLAG is null
       and pm.ORGID=1515
       and pm.PAYMENT_DATE BETWEEN '2017-12-31' AND '2018-01-01')b
       on  a.ORGID = b.ORGID
       and b.PAYMENT_DATE=a.Receipt_Date