CREATE OR REPLACE TRIGGER TG_PAYBILL_REVERSAL
  AFTER UPDATE of BM_DEL_FLAG
  ON TB_AC_BILL_MAS
  FOR EACH ROW

DECLARE
  /*-------------------------------------------------------------------------------------------------------------------------------
  Created By: Balaji Venugopal     Created On: 14-FEB-2018
  Purpose   : THIS TRIGGER IS USED TO REVERSE THE PROCESSED PAY BILLS
  -------------------------------------------------------------------------------------------------------------------------------*/




BEGIN

 IF :OLD.checker_autho is not null then
   
    UPDATE TAX_CB.TB_AC_BILL_MAS SET BM_DEL_FLAG='Y' WHERE BM_ID=:OLD.INT_REF_ID AND ORGID=:OLD.ORGID;
    
 end if;    
    

END;
/
