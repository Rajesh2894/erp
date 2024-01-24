DROP FUNCTION IF EXISTS FN_GET_ROWNUM;
CREATE FUNCTION `FN_GET_ROWNUM`() RETURNS int(11)
begin
    
    SET @var := IFNULL(@var,0) + 1;
    return @var;
    
 end;