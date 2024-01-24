--liquibase formatted sql
--changeset nilima:V20170412121026__CR_TB_CONTRACT_TERMS_DETAIL_DDL.sql
create table TB_CONTRACT_TERMS_DETAIL
(
  contt_id          NUMBER(12) not null,
  cont_id           NUMBER(12) not null,
  contt_description NVARCHAR2(200) not null,
  contt_active      CHAR(1) not null,
  orgid             NUMBER(12) not null,
  created_by        NUMBER(12) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100)
);
comment on column TB_CONTRACT_TERMS_DETAIL.contt_id
  is 'primary key';
comment on column TB_CONTRACT_TERMS_DETAIL.cont_id
  is 'Foregin key(TB_CONTRACT_MAS)';
comment on column TB_CONTRACT_TERMS_DETAIL.contt_description
  is 'Term Description';
comment on column TB_CONTRACT_TERMS_DETAIL.contt_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) .';
comment on column TB_CONTRACT_TERMS_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_TERMS_DETAIL.created_by
  is 'user id who created the record
';
comment on column TB_CONTRACT_TERMS_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_TERMS_DETAIL.created_date
  is 'record creation date
';
comment on column TB_CONTRACT_TERMS_DETAIL.updated_by
  is 'user id who update the data
';
comment on column TB_CONTRACT_TERMS_DETAIL.updated_date
  is 'date on which data is going to update
';
comment on column TB_CONTRACT_TERMS_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address
';
comment on column TB_CONTRACT_TERMS_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address
';
alter table TB_CONTRACT_TERMS_DETAIL
  add constraint PK_CONTT_ID primary key (CONTT_ID);
alter table TB_CONTRACT_TERMS_DETAIL
  add constraint FK_TERM_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);
