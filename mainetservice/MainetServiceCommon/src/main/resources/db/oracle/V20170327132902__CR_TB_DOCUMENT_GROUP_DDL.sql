--liquibase formatted sql
--changeset nilima:V20170327132902__CR_TB_DOCUMENT_GROUP_DDL.sql
create table TB_DOCUMENT_GROUP
( 
  dg_id             NUMBER(12) not null,
  group_cpd_id      NUMBER(12) not null,
  doc_name          NVARCHAR2(100),
  doc_type          NVARCHAR2(20),
  doc_size          NUMBER(5),
  doc_sr_no         NUMBER(3),
  doc_status        NVARCHAR2(1),
  CCM_DOCUMENT_FLAG NUMBER(12),
  orgid             NUMBER(4) not null,
  created_by        NUMBER(12) not null,
  created_date      DATE not null,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100)
  );
comment on column TB_DOCUMENT_GROUP.dg_id
  is 'Primary Key';
comment on column TB_DOCUMENT_GROUP.group_cpd_id
  is 'Reference key  --TB_COMPARAM_DET';
comment on column TB_DOCUMENT_GROUP.doc_name
  is 'Document Name';
comment on column TB_DOCUMENT_GROUP.doc_type
  is 'Document Type';
comment on column TB_DOCUMENT_GROUP.doc_size
  is 'Document Size';
comment on column TB_DOCUMENT_GROUP.orgid
  is 'Organization Id';
comment on column TB_DOCUMENT_GROUP.created_by
  is 'User Identity';
comment on column TB_DOCUMENT_GROUP.created_date
  is 'Created Date';
comment on column TB_DOCUMENT_GROUP.updated_by
  is 'User id who update the data';
comment on column TB_DOCUMENT_GROUP.updated_date
  is 'Date on which data is going to update';
comment on column TB_DOCUMENT_GROUP.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_DOCUMENT_GROUP.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_DOCUMENT_GROUP.doc_sr_no
  is 'Display Sequence no';
comment on column TB_DOCUMENT_GROUP.doc_status
  is 'Document Status ("A''"-> Active,"I" ->Inactive)';
alter table TB_DOCUMENT_GROUP
  add constraint PK_DOCUMENT_GROUP primary key (DG_ID)
  using index;
alter table TB_DOCUMENT_GROUP
  add constraint FK_CPD_GROUP_ID foreign key (GROUP_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);
