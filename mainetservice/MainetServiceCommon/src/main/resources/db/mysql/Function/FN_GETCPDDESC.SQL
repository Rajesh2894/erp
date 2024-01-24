DROP FUNCTION IF EXISTS FN_GETCPDDESC;
CREATE FUNCTION `FN_GETCPDDESC`(n_id INT(10),
                               v_data  varchar(10), 
                               n_orgid INT(10)
                               ) RETURNS varchar(100) CHARSET latin1
begin
 DECLARE  v_value varchar(100);

    SELECT CASE v_data
    WHEN 'E'  THEN CPD_DESC
    WHEN 'R'  THEN CPD_DESC_MAR
    WHEN 'V'  THEN CPD_VALUE
    WHEN 'O'  THEN CPD_OTHERS
    ELSE NULL 
    END
    INTO v_value
FROM TB_COMPARAM_DET
   WHERE CPD_STATUS = 'A'
     AND ORGID = n_orgid
     AND CPD_ID = N_ID;

   return (v_value);

end;
