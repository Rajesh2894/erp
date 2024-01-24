insert into
role_entitlement
(role_et_id, 
role_id, 
smfid, 
et_parent_id,
is_active, 
org_id)
values
(fn_java_sq_generation('AUT','ROLE_ENTITLEMENT','ROLE_ET_ID',NULL,NULL),
(select gm_id from TB_GROUP_MAST where GR_CODE='SUPER_ADMIN'),
(select smfid from TB_SYSMODFUNCTION where smfflag='S'),
0,
0,
(select orgid from TB_ORGANISATION where orgid=31)
);
commit;

insert into
role_entitlement
(role_et_id, 
role_id, 
smfid, 
et_parent_id,
is_active, 
org_id)
values
(fn_java_sq_generation('AUT','ROLE_ENTITLEMENT','ROLE_ET_ID',NULL,NULL),
(select gm_id from TB_GROUP_MAST where GR_CODE='SUPER_ADMIN'),
(select smfid from TB_SYSMODFUNCTION where smfaction='entitlement.html'),
(select smfid from TB_SYSMODFUNCTION where smfflag='S'),
0,
(select orgid from TB_ORGANISATION where orgid=31)
);
commit;