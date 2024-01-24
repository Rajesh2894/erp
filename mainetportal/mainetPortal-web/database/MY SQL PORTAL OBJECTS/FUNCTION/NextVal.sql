DROP FUNCTION IF EXISTS NextVal;
CREATE FUNCTION NextVal(vname VARCHAR(30)) RETURNS int(11)
BEGIN
     -- Retrieve and update in single statement
     UPDATE _sequences
       SET next = (@next := next) + 1
       WHERE name = vname;
 
     RETURN @next;
  END;
