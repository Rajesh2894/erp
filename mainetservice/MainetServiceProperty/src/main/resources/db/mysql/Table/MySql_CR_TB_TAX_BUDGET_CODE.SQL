create table TB_TAX_BUDGET_CODE
( taxb_id       BIGINT not null comment 'primary key',
  tax_id        BIGINT not null comment 'foregin key (TB_TAX_MAS)',
  orgid         BIGINT comment 'orgnisation id',
  created_by    BIGINT comment 'user id who created the record',
  created_date  DATETIME comment 'record creation date',
  updated_by    INT comment 'user id who update the data',
  updated_date  DATETIME comment 'date on which data is going to update',
  budgetcode_id BIGINT not null comment 'foregin key (TB_AC_BUDGETCODE_MAS)',
  taxb_active   CHAR(1) comment 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.',
  lg_ip_mac     VARCHAR(100) comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address');