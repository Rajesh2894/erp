--liquibase formatted sql
--changeset nilima:V20170327132927__CR_TB_CHECKLIST_MST_DDL.sql
create table TB_CHECKLIST_MST
( cl_id         NUMBER(12) not null,
  sm_service_id NUMBER(12) not null,
  doc_group     VARCHAR2(200) not null,
  cl_status     NVARCHAR2(1) default 'A' not null,
  orgid         NUMBER(12) not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  created_by    NUMBER(12) not null,
  created_date  DATE not null,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100));
comment on column TB_CHECKLIST_MST.orgid
  is 'Organisation Id';
comment on column TB_CHECKLIST_MST.cl_id
  is 'Primary Key';
comment on column TB_CHECKLIST_MST.sm_service_id
  is 'Service Id';
comment on column TB_CHECKLIST_MST.doc_group
  is 'document group (prefix ''CLG'')';
comment on column TB_CHECKLIST_MST.cl_status
  is 'Checklist Acive/Inactive (''A''->Active,''I''->Inactive)';
comment on column TB_CHECKLIST_MST.updated_by
  is 'user id who updated the record';
comment on column TB_CHECKLIST_MST.updated_date
  is 'record updation date';
comment on column TB_CHECKLIST_MST.created_by
  is 'user id who created the record';
comment on column TB_CHECKLIST_MST.created_date
  is 'record creation date.';
comment on column TB_CHECKLIST_MST.lg_ip_mac
  is 'machine ip address from where user has created the record';
comment on column TB_CHECKLIST_MST.lg_ip_mac_upd
  is 'machine ip address from where user has updated the record';
alter table TB_CHECKLIST_MST
  add constraint PK_CL_ID primary key (CL_ID)
  using index;
alter table TB_CHECKLIST_MST
  add constraint FK_CL_SM_SERVICE_ID foreign key (SM_SERVICE_ID)
  references TB_SERVICES_MST (SM_SERVICE_ID);
