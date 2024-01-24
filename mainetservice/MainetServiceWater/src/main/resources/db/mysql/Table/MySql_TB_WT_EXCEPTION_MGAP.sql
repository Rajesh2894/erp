create table TB_WT_EXCEPTION_MGAP
( mgap_exgid    BIGINT(12) not null comment 'Primary Key',
  cs_idn        BIGINT(12) not null comment 'Connection number',
  cpd_mtrstatus BIGINT(12) comment 'Meter Status',
  cpd_gap       BIGINT(12) comment 'Gap Code',
  mgap_from     DATETIME not null comment 'Applicable From',
  mgap_to       DATETIME not null comment 'Applicable To',
  mgap_bill_gen NVARCHAR(1) default 'N' comment 'Bill generated ("Y"->"Yes","N"->"No")',
  bm_idno       BIGINT(12) comment 'Bill number',
  mgap_active   CHAR(1) comment 'Active->"Y'',"InActive->"N"',
  orgid         BIGINT(12) comment 'orgnisation id',
  created_by    BIGINT(12) comment 'user id who created the record',
  created_date  DATETIME comment 'record creation date',
  updated_by    BIGINT(12) comment 'user id who update the data',
  updated_date  DATETIME comment 'date on which data is going to update',
  lg_ip_mac     VARCHAR(100) comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address'
  );

alter table TB_WT_EXCEPTION_MGAP add mgap_remark VARCHAR(500) comment 'Remark for Exception';