CREATE FUNCTION fn_reset_id()
RETURNS int(11)
 
 begin
    
    SET @var=0;
    return @var;
    
 end;