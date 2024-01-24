select 
	cm.RCPT_CHQBOOK_DATE Date_of_receipt,
	bm.bank Bank_Name,
	bm.Branch Branch_Name,
	cm.FROM_CHEQUE_NO Number_of_first_leaf,
	cm.TO_CHEQUE_NO Number_of_last_leaf ,
	cm.ISSUED_DATE Date_of_issue,
	cm.ISSUER_EMPID Issued_to_whom,
	Null Signature_of_receipent,
	case when cm.CHECK_BOOK_RETURN = 'Y' then cm.CHKBOOK_RTN_DATE 
    else  null  end Date_of_return,
	case when cm.CHECK_BOOK_RETURN = 'Y' then
    cm.UPDATED_BY else  null  end Return_by_whom,
    case when cm.CHECK_BOOK_RETURN = 'Y' then
    (cm.FROM_CHEQUE_NO - cm.TO_CHEQUE_NO) 
    else   null  end Leave_cancelled,   
    cm.RETURN_REMARK Remarks_if_any
from 
    tb_ac_chequebookleaf_mas cm,tb_bank_master bm,tb_bank_account ba
where bm.BANKID=ba.BANKID
	and cm.BA_ACCOUNTID=ba.BA_ACCOUNTID 
    and cm.ORGID=1515
    and ba.BA_ACCOUNTID=24;