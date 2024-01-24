--liquibase formatted sql
--changeset nilima:V20170412185102__CR_TB_RL_BILL_MAST_DDL.sql
create table TB_RL_BILL_MAST
( bm_bmno         NUMBER(12) not null,
  cont_id         NUMBER(12) not null,
  bm_billdate     DATE not null,
  bm_amount       NUMBER(15,2) not null,
  bm_paid_amt     NUMBER(15,2),
  bm_balance_amt  NUMBER(15,2),
  bm_paid_flag    CHAR(1) default 'N' not null,
  bm_active       CHAR(1) not null,
  orgid           NUMBER(12) not null,
  created_by      NUMBER(12) not null,
  lang_id         NUMBER(7),
  created_date    DATE not null,
  updated_by      NUMBER(12),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  conit_id        NUMBER(12) not null,
  bm_billno       NUMBER(12),
  bm_due_date     DATE,
  bm_paymnet_date DATE
);
comment on column TB_RL_BILL_MAST.bm_bmno
  is 'Primary Key';
comment on column TB_RL_BILL_MAST.cont_id
  is 'Contract id Foregin Key (TB_CONTRACT_MAST)';
comment on column TB_RL_BILL_MAST.bm_billdate
  is 'Bill Date (Same as Installment Date)';
comment on column TB_RL_BILL_MAST.bm_amount
  is 'Installment Amount';
comment on column TB_RL_BILL_MAST.bm_paid_amt
  is 'Installment Paid Amount';
comment on column TB_RL_BILL_MAST.bm_balance_amt
  is 'Installment Balance Amount';
comment on column TB_RL_BILL_MAST.bm_paid_flag
  is 'Bill Paid Flag';
comment on column TB_RL_BILL_MAST.bm_active
  is 'Active Bill';
comment on column TB_RL_BILL_MAST.orgid
  is 'orgnisation id';
comment on column TB_RL_BILL_MAST.created_by
  is 'user identity';
comment on column TB_RL_BILL_MAST.lang_id
  is 'language identity';
comment on column TB_RL_BILL_MAST.created_date
  is 'created date';
comment on column TB_RL_BILL_MAST.updated_by
  is 'user id who update the data';
comment on column TB_RL_BILL_MAST.updated_date
  is 'date on which data is going to update';
comment on column TB_RL_BILL_MAST.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_RL_BILL_MAST.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_RL_BILL_MAST.conit_id
  is 'Contract Installment id Foregin key(TB_CONTRACT_INSTALMENT_DETAIL)';
comment on column TB_RL_BILL_MAST.bm_billno
  is 'Bill no';
comment on column TB_RL_BILL_MAST.bm_due_date
  is 'Bill Date Flag';
comment on column TB_RL_BILL_MAST.bm_paymnet_date
  is 'Bill Payment Date';
