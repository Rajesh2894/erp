--liquibase formatted sql
--changeset nilima:V20170412120759__CR_TB_CONTRACT_PART1_DETAIL_DDL.sql
create table TB_CONTRACT_PART1_DETAIL
( contp1_id                   NUMBER(12) not null,
  cont_id                     NUMBER(12) not null,
  dp_deptid                   NUMBER(12),
  dsgid                       NUMBER(12),
  empid                       NUMBER(12),
  contp1_name                 NVARCHAR2(200),
  contp1_address              NVARCHAR2(500),
  contp1_proof_id_no          NVARCHAR2(50),
  contp1_parent_id            NUMBER(12),
  contp1_type                 CHAR(1) not null,
  contp1_active               CHAR(1) not null,
  orgid                       NUMBER(12) not null,
  created_by                  NUMBER(12) not null,
  lang_id                     NUMBER(7) not null,
  created_date                DATE not null,
  updated_by                  NUMBER(12),
  updated_date                DATE,
  lg_ip_mac                   VARCHAR2(100) not null,
  lg_ip_mac_upd               VARCHAR2(100),
  contp1_photo_file_name      VARCHAR2(200),
  contp1_photo_file_path_name VARCHAR2(500),
  contp1_thumb_file_name      VARCHAR2(200),
  contp1_thumb_file_path_name VARCHAR2(500));
comment on table TB_CONTRACT_PART1_DETAIL
  is 'table used to store contract ulb & witness details';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_id
  is 'primary key';
comment on column TB_CONTRACT_PART1_DETAIL.cont_id
  is 'Foregin Key (TB_CONTRACT_MAST)';
comment on column TB_CONTRACT_PART1_DETAIL.dp_deptid
  is 'Foregin Key(TB_DEPARTMENT)';
comment on column TB_CONTRACT_PART1_DETAIL.dsgid
  is 'Foregin Key(Designation)';
comment on column TB_CONTRACT_PART1_DETAIL.empid
  is 'Foregin Key(Employee)';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_name
  is 'Representer Name/Witness Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_address
  is 'Witness Address';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_proof_id_no
  is 'Proof Id No.';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_parent_id
  is 'Party1 Parent id for type Witness';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_type
  is 'W->Witness , U->Ulb';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) .';
comment on column TB_CONTRACT_PART1_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_PART1_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_PART1_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_PART1_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_PART1_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_PART1_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_PART1_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART1_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_photo_file_name
  is 'Photo Image File Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_photo_file_path_name
  is 'Photo Image File Path Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_thumb_file_name
  is 'Thumb Image File Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_thumb_file_path_name
  is 'Thumb Image File Path Name';
alter table TB_CONTRACT_PART1_DETAIL
  add constraint PK_CONTP1_ID primary key (CONTP1_ID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_DEPT_ID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_DSG_ID foreign key (DSGID)
  references DESIGNATION (DSGID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_EMP_ID foreign key (EMPID)
  references EMPLOYEE (EMPID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_P1_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);
