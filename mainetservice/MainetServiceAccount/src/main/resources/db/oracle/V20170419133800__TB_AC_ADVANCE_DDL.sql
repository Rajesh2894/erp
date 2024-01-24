--liquibase formatted sql
--changeset Vivek:V20170419133800
create table TB_AC_ADVANCE
(
  advance_id                NUMBER(12) not null,
  orgid                     NUMBER(4) not null,
  pay_adv_entrydate         DATE not null,
  cpd_advance_type          NUMBER(12) not null,
  pay_advance_no            NUMBER(12) not null,
  dp_deptid                 NUMBER(12),
  vm_vendorid               NUMBER(12) not null,
  vm_vendorname             NVARCHAR2(200),
  ah_headid                 NUMBER(12) not null,
  pay_adv_particulars       NVARCHAR2(200) not null,
  pay_adv_voucherno         NUMBER(12) not null,
  pay_adv_voucherdt         DATE not null,
  pay_adv_amount            NUMBER(12,2) not null,
  pay_adv_bal_to_adjust     NUMBER(12,2) not null,
  pay_adv_settlement_date   DATE,
  created_by                NUMBER(7) not null,
  lang_id                   NUMBER(7),
  created_date              DATE not null,
  updated_by                NUMBER(7),
  updated_date              DATE,
  adv_status                CHAR(1),
  seas_deas                 NUMBER(12,2),
  lg_ip_mac                 VARCHAR2(100),
  lg_ip_mac_upd             VARCHAR2(100),
  adv_flg                   CHAR(1),
  pay_adv_settlement_number NUMBER(12)
);
alter table TB_AC_ADVANCE
  add constraint PK_ADVANCE_ID primary key (ADVANCE_ID);
