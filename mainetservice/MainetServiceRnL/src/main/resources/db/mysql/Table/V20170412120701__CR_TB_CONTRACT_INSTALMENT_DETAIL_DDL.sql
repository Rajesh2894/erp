--liquibase formatted sql
--changeset nilima:V20170412120701__CR_TB_CONTRACT_INSTALMENT_DETAIL_DDL.sql
create table TB_CONTRACT_INSTALMENT_DETAIL
( conit_id        NUMBER(12) not null,
  cont_id         NUMBER(12) not null,
  conit_amt_type  NUMBER(12) not null,
  conit_value     NUMBER(15,2) not null,
  conit_date      DATE not null,
  conit_milestone NVARCHAR2(500),
  contt_active    CHAR(1) not null,
  conit_pr_flag   CHAR(1),
  orgid           NUMBER(12) not null,
  created_by      NUMBER(12) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(12),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100) not null,
  lg_ip_mac_upd   VARCHAR2(100)
);
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_id
  is 'primary key';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.cont_id
  is 'Foregin key(TB_CONTRACT_MAS)';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_amt_type
  is 'Instalment Amount Type';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_value
  is 'Instalment Value';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_date
  is 'Instalment Date';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_milestone
  is 'Instalment Milestone';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.contt_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_pr_flag
  is 'Installment Paid or received flag';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
alter table TB_CONTRACT_INSTALMENT_DETAIL
  add constraint PK_CONIT_ID primary key (CONIT_ID)
  using index ;
alter table TB_CONTRACT_INSTALMENT_DETAIL
  add constraint FK_INSTALL_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);
alter table TB_CONTRACT_INSTALMENT_DETAIL add conit_amt NUMBER(15,2);
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_amt
  is 'Amount';