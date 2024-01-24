update employee x set gm_id=
(select GM_ID from 
suda_portal1.tb_group_mast where GR_CODE='SUPER_ADMIN' 
and orgid=x.orgid)
WHERE empname not in ('GUEST','MBA_CK');

delete from employee where empname='MBA_CK';
commit;

delete from role_entitlement where smfid not in(1,2)
commit;

UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='9' WHERE `ROLE_ET_ID`='95';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='10' WHERE `ROLE_ET_ID`='96';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='11' WHERE `ROLE_ET_ID`='175';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='12' WHERE `ROLE_ET_ID`='176';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='13' WHERE `ROLE_ET_ID`='177';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='14' WHERE `ROLE_ET_ID`='178';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='15' WHERE `ROLE_ET_ID`='285';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='16' WHERE `ROLE_ET_ID`='286';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='17' WHERE `ROLE_ET_ID`='327';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='18' WHERE `ROLE_ET_ID`='328';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='19' WHERE `ROLE_ET_ID`='372';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='20' WHERE `ROLE_ET_ID`='373';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='21' WHERE `ROLE_ET_ID`='374';
UPDATE `suda_portal1`.`role_entitlement` SET `ROLE_ET_ID`='22' WHERE `ROLE_ET_ID`='375';

update _sequences set next=23 where name='SQ_ROLE_E_8';
commit;


