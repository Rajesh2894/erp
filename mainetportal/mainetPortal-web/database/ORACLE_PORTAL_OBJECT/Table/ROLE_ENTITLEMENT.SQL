-- Create table
create table ROLE_ENTITLEMENT
(
  role_et_id   NUMBER not null,
  role_id      NUMBER,
  smfid        NUMBER,
  updated_by   NUMBER,
  updated_date DATE,
  is_active    NVARCHAR2(1),
  et_parent_id NUMBER,
  org_id       NUMBER,
  parent_id    NUMBER
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table ROLE_ENTITLEMENT
  add constraint PK_ROLE_ET_ID primary key (ROLE_ET_ID);
alter table ROLE_ENTITLEMENT
  add constraint FKE45ED6C4511F3CC9 foreign key (ROLE_ID)
  references TB_GROUP_MAST (GM_ID);
alter table ROLE_ENTITLEMENT
  add constraint FKE45ED6C4C396A42C foreign key (ORG_ID)
  references TB_ORGANISATION (ORGID);
alter table ROLE_ENTITLEMENT
  add constraint FK_SMFID_SYSMODFUNCTION foreign key (SMFID)
  references TB_SYSMODFUNCTION (SMFID);
