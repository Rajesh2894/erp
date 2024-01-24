delete from EMPLOYEE where emploginname<>'MBA' AND
orgid in (select orgid from tb_organisation where ORG_STATUS='A');
commit;

UPDATE employee SET EMPID='1', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin', EMPPASSWORD='UxtjxG9;=' WHERE EMPID='2';
UPDATE employee SET EMPID='2', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='3';
UPDATE employee SET EMPID='3', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='10';
UPDATE employee SET EMPID='4', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='26';
UPDATE employee SET EMPID='5', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='27';
UPDATE employee SET EMPID='6', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='30';
UPDATE employee SET EMPID='7', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='31';
UPDATE employee SET EMPID='8', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='32';
UPDATE employee SET EMPID='9', CPD_TTL_ID='1', EMPNAME='SysAdmin', EMPLNAME='SysAdmin', EMPOSLOGINNAME='SysAdmin', EMPLOGINNAME='SysAdmin' WHERE EMPID='33';

UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='1';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='2';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='3';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='4';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='5';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='6';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='7';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='8';
UPDATE employee SET EMPPASSWORD='UxtjxG9;=' WHERE EMPID='9';
commit;


update _sequences set next=10 where name='SQ_EMPID_9';
commit;

delete from role_entitlement where ROLE_ID not in
(select gm_id from employee)
commit;

delete from role_entitlement  where SMFID not in (1,2);
commit;

UPDATE role_entitlement SET ROLE_ET_ID='1' WHERE ROLE_ET_ID='3';
UPDATE role_entitlement SET ROLE_ET_ID='2' WHERE ROLE_ET_ID='4';
UPDATE role_entitlement SET ROLE_ET_ID='3' WHERE ROLE_ET_ID='33';
UPDATE role_entitlement SET ROLE_ET_ID='4' WHERE ROLE_ET_ID='34';
UPDATE role_entitlement SET ROLE_ET_ID='5' WHERE ROLE_ET_ID='337';
UPDATE role_entitlement SET ROLE_ET_ID='6' WHERE ROLE_ET_ID='338';
UPDATE role_entitlement SET ROLE_ET_ID='7' WHERE ROLE_ET_ID='989';
UPDATE role_entitlement SET ROLE_ET_ID='8' WHERE ROLE_ET_ID='990';
UPDATE role_entitlement SET ROLE_ET_ID='9' WHERE ROLE_ET_ID='991';
UPDATE role_entitlement SET ROLE_ET_ID='10' WHERE ROLE_ET_ID='992';
UPDATE role_entitlement SET ROLE_ET_ID='11' WHERE ROLE_ET_ID='998';
UPDATE role_entitlement SET ROLE_ET_ID='12' WHERE ROLE_ET_ID='999';
UPDATE role_entitlement SET ROLE_ET_ID='13' WHERE ROLE_ET_ID='1061';
UPDATE role_entitlement SET ROLE_ET_ID='14' WHERE ROLE_ET_ID='1062';
UPDATE role_entitlement SET ROLE_ET_ID='15' WHERE ROLE_ET_ID='1063';
UPDATE role_entitlement SET ROLE_ET_ID='16' WHERE ROLE_ET_ID='1064';
UPDATE role_entitlement SET ROLE_ET_ID='17' WHERE ROLE_ET_ID='1095';
UPDATE role_entitlement SET ROLE_ET_ID='18' WHERE ROLE_ET_ID='1096';
commit;

update _sequences set next=19 where name='SQ_ROLE_E_14';
commit;

update _sequences set next=14
where name='SQ_ORGID_1';
commit;
