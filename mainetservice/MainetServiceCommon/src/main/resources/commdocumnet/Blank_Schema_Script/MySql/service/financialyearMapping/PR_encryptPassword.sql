CREATE encryptPassword(userName varchar(100),upassword varchar(100)) RETURNS varchar(100) CHARSET utf8
BEGIN
DECLARE encrypted varchar(100) DEFAULT '';
 DECLARE passwordlength INTEGER DEFAULT 0;
 DECLARE loginnumber INTEGER DEFAULT 0;
 DECLARE loginname varchar(100) DEFAULT null;
 DECLARE ascii INTEGER DEFAULT 0;
 DECLARE lengthOfusername INTEGER DEFAULT 0;
 DECLARE i INTEGER DEFAULT 1;
 DECLARE j INTEGER DEFAULT 1;
 
 set userName = upper(userName);
  set lengthOfusername = length(userName);
 
 
   label1: loop
      
      if i > (lengthOfusername - 1) then
         Leave label1;
      end if;   
       set  loginname = SUBSTRING(userName,i,1);
       set  loginnumber = (loginnumber+Ascii(loginname));
       set i=i+1;
      end loop;

    set passwordlength = length(upassword);

   label2: loop
       
      if j > passwordlength then
          
          Leave label2;
      end if;   
        set    ascii = Ascii(substring(upassword,j,1));
        set    ascii = ascii+(loginnumber / 256)+(j-2);
        set    encrypted =concat(encrypted,char(ascii));
       set j=j+1;
       
        
     End loop; 

RETURN encrypted;
END