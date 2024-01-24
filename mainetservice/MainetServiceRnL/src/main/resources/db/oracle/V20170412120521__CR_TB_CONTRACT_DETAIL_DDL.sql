--liquibase formatted sql
--changeset nilima:V20170412120521__CR_TB_CONTRACT_DETAIL_DDL.sql
create table TB_CONTRACT_DETAIL
(
  contd_id                NUMBER(12) not null,
  cont_id                 NUMBER(12) not null,
  cont_from_date          DATE not null,
  cont_to_date            DATE not null,
  cont_amount             NUMBER(20,2),
  cont_sec_amount         NUMBER(20,2),
  cont_sec_rec_no         NVARCHAR2(20),
  cont_sec_rec_date       DATE,
  cont_pay_period         NUMBER(12),
  cont_installment_period NUMBER(12),
  cont_entry_type         CHAR(2) not null,
  contd_active            CHAR(1) not null,
  orgid                   NUMBER(12) not null,
  created_by              NUMBER(12) not null,
  lang_id                 NUMBER(7) not null,
  created_date            DATE not null,
  updated_by              NUMBER(12),
  updated_date            DATE,
  lg_ip_mac               VARCHAR2(100) not null,
  lg_ip_mac_upd           VARCHAR2(100)
);
comment on table TB_CONTRACT_DETAIL
  is 'Storing contract details for Contract Creation,Contract renewal,contract revision';
comment on column TB_CONTRACT_DETAIL.contd_id
  is 'primary key';
comment on column TB_CONTRACT_DETAIL.cont_id
  is 'fk key (TB_CONTRACT_MAST)';
comment on column TB_CONTRACT_DETAIL.cont_from_date
  is 'Contract From Date';
comment on column TB_CONTRACT_DETAIL.cont_to_date
  is 'Contract To Date';
comment on column TB_CONTRACT_DETAIL.cont_amount
  is 'Contracted Amount applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_sec_amount
  is 'Security Deposite Amount applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_sec_rec_no
  is 'Security Deposite Receipt No. applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_sec_rec_date
  is 'Security Deposite Receipt Date applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_pay_period
  is 'Contract Payment Terms (Non Hirarchey Prefix ''PTR'') applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_installment_period
  is 'No. of Installment (Non Hirarchey Prefix ''NOI'') applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_entry_type
  is '(O->Original R->Renew V->Revise S->Sub lease)';
comment on column TB_CONTRACT_DETAIL.contd_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) .';
comment on column TB_CONTRACT_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
alter table TB_CONTRACT_DETAIL
  add constraint PK_CONTD_ID primary key (CONTD_ID);
alter table TB_CONTRACT_DETAIL
  add constraint FK_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);
