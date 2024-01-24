SELECT 
    rm.RM_RCPTNO Transation_No,
    rm.RM_DATE Transaction_Date,
    rm.RM_AMOUNT Amount,
    rm.RM_NARRATION Narration,
    rm.RM_RECEIVEDFROM Receiver_or_PayerName,
    rm.UPDATED_BY Reversed_By,
    rm.RECEIPT_DEL_DATE Reversed_Date,
    rm.RECEIPT_DEL_REMARK Reversal_Reason,
    rm.RECEIPT_DEL_AUTH_BY Authorized_By
FROM
    tb_receipt_mas rm
WHERE
    rm.RECEIPT_DEL_FLAG = 'Y'
        AND rm.ORGID = 1515
        AND rm.RM_DATE = '2018-01-01';

