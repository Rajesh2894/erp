create table TB_AC_ADVANCE
(  advance_id               BIGINT(12) not null,
  orgid                     BIGINT(12) not null,
  pay_adv_entrydate         DATE not null,
  cpd_advance_type          BIGINT(12) not null,
  pay_advance_no            BIGINT(12) not null,
  dp_deptid                 BIGINT(12),
  vm_vendorid               BIGINT(12) not null,
  vm_vendorname             NVARCHAR(200),
  ah_headid                 BIGINT(12) not null,
  pay_adv_particulars       NVARCHAR(200) not null,
  pay_adv_voucherno         BIGINT(12) not null,
  pay_adv_voucherdt         DATETIME not null,
  pay_adv_amount            DECIMAL(12,2) not null,
  pay_adv_bal_to_adjust     DECIMAL(12,2) not null,
  pay_adv_settlement_date   DATE,
  created_by                BIGINT(12) not null,
  created_date              DATETIME not null,
  updated_by                BIGINT(12),
  updated_date              DATETIME,
  adv_status                CHAR(1),
  seas_deas                 DECIMAL(12,2),
  lg_ip_mac                 VARCHAR(100),
  lg_ip_mac_upd             VARCHAR(100),
  adv_flg                   CHAR(1),
  pay_adv_settlement_number BIGINT(12));
  
  alter table TB_AC_ADVANCE
  add constraint PK_ADVANCE_ID primary key (ADVANCE_ID);
  