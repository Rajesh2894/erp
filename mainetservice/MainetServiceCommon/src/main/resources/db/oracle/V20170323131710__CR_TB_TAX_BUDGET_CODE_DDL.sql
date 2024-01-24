-- Create table
create table TB_TAX_BUDGET_CODE
(
  taxb_id       NUMBER(12) not null,
  tax_id        NUMBER(12) not null,
  orgid         NUMBER(12),
  created_by    NUMBER(12),
  created_date  DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  budgetcode_id NUMBER(12) not null,
  taxb_active   CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_TAX_BUDGET_CODE.taxb_id
  is 'primary key';
comment on column TB_TAX_BUDGET_CODE.tax_id
  is 'foregin key (TB_TAX_MAS)';
comment on column TB_TAX_BUDGET_CODE.orgid
  is 'orgnisation id';
comment on column TB_TAX_BUDGET_CODE.created_by
  is 'user id who created the record';
comment on column TB_TAX_BUDGET_CODE.created_date
  is 'record creation date';
comment on column TB_TAX_BUDGET_CODE.updated_by
  is 'user id who update the data';
comment on column TB_TAX_BUDGET_CODE.updated_date
  is 'date on which data is going to update';
comment on column TB_TAX_BUDGET_CODE.budgetcode_id
  is 'foregin key (TB_AC_BUDGETCODE_MAS)';
comment on column TB_TAX_BUDGET_CODE.taxb_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_TAX_BUDGET_CODE.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_TAX_BUDGET_CODE.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
