DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `NextVal`(vname VARCHAR(30)) RETURNS int(11)
BEGIN
     
     UPDATE _sequences
       SET next = (@next := next) + 1
       WHERE name = vname;
 
     RETURN @next;
  END$$
DELIMITER ;