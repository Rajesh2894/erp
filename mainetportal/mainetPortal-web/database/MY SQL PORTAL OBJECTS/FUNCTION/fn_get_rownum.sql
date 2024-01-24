CREATE FUNCTION fn_get_rownum()
RETURNS int(11)
 
 begin
    
    SET @var := IFNULL(@var,0) + 1;
    return @var;
    
 end;
 