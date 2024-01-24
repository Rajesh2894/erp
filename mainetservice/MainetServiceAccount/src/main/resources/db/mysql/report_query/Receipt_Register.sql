select  a.Receipt_No,
    a.Receipt_Date,
    a.Mode_of_Receipt,
    a.Name_of_Depositor,
    a.Cheque_or_Draft_No,
          a.Bank_AMT,
     a.Cash_AMT,
     b.Bank_Account_No,
     b.Date_of_Deposit,
     a.Date_of_Realisation,
     a.Whether_Returned_Y_or_N,
     a.Remarks
      from
    (SELECT rm.RM_RCPTID,rm.ORGID,
    rm.RM_RCPTNO AS Receipt_No,
    rm.RM_DATE AS Receipt_Date,
    cd.CPD_DESC AS Mode_of_Receipt,
    rm.RM_RECEIVEDFROM AS Name_of_Depositor,
    rmo.RD_CHEQUEDDNO AS Cheque_or_Draft_No,
    rm.RM_NARRATION AS Remarks,
    (case when cd.CPD_VALUE in ('D','Q','B') then
     rmo.RD_AMOUNT
     else 0 end) AS Bank_AMT,
    (case when cd.CPD_VALUE in ('C') then
     rmo.RD_AMOUNT
     else 0 end) AS Cash_AMT,
      rmo.RD_SR_CHK_DATE AS Date_of_Realisation,
    rmo.RD_SR_CHK_DIS AS Whether_Returned_Y_or_N,
     rd.DPS_SLIPID
    FROM
    tb_receipt_mas rm,
    tb_comparam_det cd,
    tb_receipt_mode rmo,
    tb_receipt_det rd
    where rm.RM_RCPTID = rmo.RM_RCPTID
    and rmo.CPD_FEEMODE = cd.CPD_ID
    and rm.RM_RCPTID = rd.RM_RCPTID 
        and rm.ORGID = 1515
    AND rm.RM_DATE BETWEEN '2017-12-01' AND '2017-12-31') a
    left join
    (select 
    ba.BA_ACCOUNT_NO AS Bank_Account_No,
    bd.DPS_DEPOSIT_DATE AS Date_of_Deposit,bd.DPS_SLIPID
    from 
    tb_bank_account ba, tb_ac_bank_depositslip_master bd 
     where bd.BA_ACCOUNTID = ba.BA_ACCOUNTID AND ba.ORGID = 1515)b
     on a.DPS_SLIPID= b.DPS_SLIPID
     ORDER BY a.Receipt_Date , a.Receipt_No;