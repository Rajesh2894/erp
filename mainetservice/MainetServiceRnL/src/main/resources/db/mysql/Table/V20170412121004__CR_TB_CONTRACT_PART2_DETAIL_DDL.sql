--liquibase formatted sql
--changeset nilima:V20170412121004__CR_TB_CONTRACT_PART2_DETAIL_DDL.sql
create table TB_CONTRACT_PART2_DETAIL
( contp2_id                   NUMBER(12) not null,
  cont_id                     NUMBER(12) not null,
  contp2v_type                NUMBER(12),
  vm_vendorid                 NUMBER(12),
  contp2_name                 NVARCHAR2(200),
  contp2_address              NVARCHAR2(500),
  contp2_proof_id_no          NVARCHAR2(50),
  contv_active                CHAR(1) not null,
  contp2_parent_id            NUMBER(12),
  contp2_type                 CHAR(1) not null,
  orgid                       NUMBER(12) not null,
  created_by                  NUMBER(12) not null,
  lang_id                     NUMBER(7) not null,
  created_date                DATE,
  updated_by                  NUMBER(12),
  updated_date                DATE,
  lg_ip_mac                   VARCHAR2(100) not null,
  lg_ip_mac_upd               VARCHAR2(100),
  contp2_primary              VARCHAR2(1) default 'N',
  contp2_photo_file_name      VARCHAR2(200),
  contp2_photo_file_path_name VARCHAR2(500),
  contp2_thumb_file_name      VARCHAR2(200),
  contp2_thumb_file_path_name VARCHAR2(500));
comment on table TB_CONTRACT_PART2_DETAIL
  is 'table use for storing details contracted Vendor & Witness Details';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_id
  is 'primary key';
comment on column TB_CONTRACT_PART2_DETAIL.cont_id
  is 'Foregin Key (TB_CONTRACT_MAST)';
comment on column TB_CONTRACT_PART2_DETAIL.contp2v_type
  is '(Value from Prefix ''VNT'')';
comment on column TB_CONTRACT_PART2_DETAIL.vm_vendorid
  is 'Foregin Key(TB_VENDORMASTER)';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_name
  is 'Representer Name/Witness Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_address
  is 'Witness Address';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_proof_id_no
  is 'Proof Id No.';
comment on column TB_CONTRACT_PART2_DETAIL.contv_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) .';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_parent_id
  is 'Party2 Parent id for type Witness';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_type
  is 'W->Witness , V-> Vendor';
comment on column TB_CONTRACT_PART2_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_PART2_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_PART2_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_PART2_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_PART2_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_PART2_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_PART2_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART2_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_primary
  is 'Primary Vendor (''Y''->YES)';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_photo_file_name
  is 'Photo Image File Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_photo_file_path_name
  is 'Photo Image File Path Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_thumb_file_name
  is 'Thumb Image File Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_thumb_file_path_name
  is 'Thumb Image File Path Name';
alter table TB_CONTRACT_PART2_DETAIL
  add constraint PK_CONTP2_ID primary key (CONTP2_ID);
alter table TB_CONTRACT_PART2_DETAIL
  add constraint FK_P2_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);
alter table TB_CONTRACT_PART2_DETAIL
  add constraint FK_VENDER_ID foreign key (VM_VENDORID)
  references TB_VENDORMASTER (VM_VENDORID);
