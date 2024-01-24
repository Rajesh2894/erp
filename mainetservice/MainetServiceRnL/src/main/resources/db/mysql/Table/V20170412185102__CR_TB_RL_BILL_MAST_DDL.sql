--liquibase formatted sql
--changeset nilima:V20170412185102__CR_TB_RL_BILL_MAST_DDL.sql
create table TB_RL_BILL_MAST
( bm_bmno         BIGINT(12) not null comment 'Primary Key',
  cont_id         BIGINT(12) not null comment 'Contract id Foregin Key (TB_CONTRACT_MAST)',
  bm_billdate     DATE not null comment 'Bill Date (Same as Installment Date)',
  bm_amount       DECIMAL(15,2) not null comment 'Installment Amount',
  bm_paid_amt     DECIMAL(15,2) comment 'Installment Paid Amount',
  bm_balance_amt  DECIMAL(15,2) comment 'Installment Balance Amount',
  bm_paid_flag    CHAR(1) default 'N' not null comment 'Bill Paid Flag',
  bm_active       CHAR(1) not null comment 'Active Bill',
  orgid           BIGINT(12) not null  ,
  created_by      BIGINT(12) not null comment 'user identity,
  created_date    DATETIME not null ',
  updated_by      BIGINT(12) comment 'user id who update the data',
  updated_date    DATETIME comment 'date on which data is going to update',
  lg_ip_mac       VARCHAR(100) comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd   VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address',
  conit_id        BIGINT(12) not null comment 'Contract Installment id Foregin key(TB_CONTRACT_INSTALMENT_DETAIL)',
  bm_billno       BIGINT(12) comment 'Bill no',
  bm_due_date     DATE comment 'Bill Date Flag',
  bm_paymnet_date DATE comment 'Bill Payment Date');
