DELIMITER $$
CREATE PROCEDURE `UPPASSMBA`()
BEGIN
	declare n_orgid bigint(12);
    declare n_empid bigint(12);
    declare v_emploginname varchar(100);
    declare v_orgshortnm varchar(100);
    declare userName varchar(100);
    
    declare v_Password varchar(100);
    declare v_Encrypt varchar(100);
	DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE lengthOfusername INTEGER DEFAULT 0;
    DECLARE loginnumber INTEGER DEFAULT 0;
    DECLARE i INTEGER DEFAULT 0;
	DECLARE j INTEGER DEFAULT 0;
	DECLARE k INTEGER DEFAULT 0;	
	
    declare cu_upmba cursor for select orgid,empid,emploginname from employee where emploginname in ('SysAdmin');
    declare continue handler for not found set v_finished=1;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

	update employee set empname='Administrator',emplname='SysAdmin',EMPOSLOGINNAME='SysAdmin',
    EMPLOGINNAME='SysAdmin' where emploginname in ('MBA') ;
    commit;
   
   open cu_upmba;
	get_mba : loop
	fetch cu_upmba into n_orgid,n_empid,v_emploginname;
        
		set j=j+1;
        
        set userName = upper(v_emploginname);
		set lengthOfusername = length(userName);
    
		label1: loop
      
				if i > (lengthOfusername - 1) then
					Leave label1;
				end if;   
				set  loginnumber = (loginnumber+Ascii(SUBSTRING(userName,i,1)));
                set i=i+1;
			end loop;

		if v_finished=1 then
	        Leave get_mba;
		end if;	
        
        select ORG_SHORT_NM into v_orgshortnm
        from tb_organisation WHERE orgid=n_orgid;
        
              
        set v_Password = CONCAT('A', SUBSTRING(lower(v_orgshortnm),1,2),'$',round((loginnumber+(j+j)+(loginnumber/18)),0),'@',n_empid);
        
        set v_Encrypt  =encryptPassword(v_emploginname,v_Password);
        
        insert into emppassword(empid,emploginname,Password,encryptpass) values(n_empid,v_emploginname,v_Password,v_Encrypt); 
		commit;
        update employee set emppassword=v_Encrypt where empid=n_empid;
        commit;
	end loop get_mba;
    close cu_upmba;
END$$
DELIMITER ;
